package com.ut.nlSystemAPi.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramBotService {

  public static final String ERROR_NOTIFICATION_SENT_ATTRIBUTE =
      TelegramBotService.class.getName() + ".ERROR_NOTIFICATION_SENT";

  private static final int TELEGRAM_MAX_MESSAGE_LENGTH = 4096;
  private static final int SAFE_MESSAGE_LENGTH = 3900;
  private static final int MESSAGE_CHUNK_LENGTH = 2500;

  private final Environment environment;
  private final RestTemplate restTemplate = new RestTemplate();

  @Autowired
  public TelegramBotService(Environment environment) {
    this.environment = environment;
  }

  public void sendErrorMessage(String message) {
    sendErrorMessage(message, null);
  }

  public void sendErrorMessage(String message, String payload) {
    sendErrorMessage("System Error Alert", message, null, payload);
  }

  public void sendErrorMessage(String type, String message, String url) {
    sendErrorMessage(type, message, url, null);
  }

  public void sendErrorMessage(String type, String message, String url, String payload) {
    String botToken = firstNonBlank(
        environment.getProperty("telegram.botToken"),
        environment.getProperty("telegram.botToken.sales-invoice"),
        environment.getProperty("telegram.botToken.sales-order"),
        environment.getProperty("telegram.botToken.quotation"),
        environment.getProperty("telegram.botToken.price-request")
    );
    String chatIds = firstNonBlank(
        environment.getProperty("telegram.chatId"),
        environment.getProperty("telegram.chatId.sales-invoice"),
        environment.getProperty("telegram.chatId.sales-order"),
        environment.getProperty("telegram.chatId.quotation"),
        environment.getProperty("telegram.chatId.price-request")
    );
    String parseMode = environment.getProperty("telegram.parseMode.error", "html");
    String contextPath = environment.getProperty("server.servlet.context-path");
    String projectName = firstNonBlank(
        environment.getProperty("project.name"),
        environment.getProperty("spring.application.name"),
        environment.getProperty("spring.jmx.default-domain"),
        contextPath
    );
    String baseUrl = firstNonBlank(
        environment.getProperty("project.url"),
        resolveModuleBaseUrl(contextPath, projectName)
    );
    String resolvedUrl = resolveUrl(baseUrl, contextPath, url);

    if (isBlank(botToken) || isBlank(chatIds)) {
      System.err.println("Telegram error notifications are not configured.");
      return;
    }

    boolean html = isHtml(parseMode);
    String sendUrl = String.format("https://api.telegram.org/bot%s/sendMessage", botToken);
    List<String> fullMessages = buildErrorMessages(projectName, type, message, resolvedUrl, payload);

    for (String chatId : chatIds.split(",")) {
      String trimmedChatId = chatId.trim();
      if (trimmedChatId.isEmpty()) {
        continue;
      }
      for (String fullMessage : fullMessages) {
        String safeMessage = html ? TelegramUtils.safeHtmlMessage(fullMessage) : TelegramUtils.safePlainText(fullMessage);
        boolean sendAsHtml = html;
        if (safeMessage.length() > TELEGRAM_MAX_MESSAGE_LENGTH) {
          safeMessage = TelegramUtils.safePlainText(fullMessage);
          sendAsHtml = false;
        }
        for (String messagePart : splitText(safeMessage, SAFE_MESSAGE_LENGTH)) {
          postTelegramMessage(sendUrl, trimmedChatId, messagePart, sendAsHtml);
        }
      }
    }
  }

  public void sendErrorMessageOnce(HttpServletRequest request, String type, String message, String url, String payload) {
    if (request == null) {
      sendErrorMessage(type, message, url, payload);
      return;
    }
    if (Boolean.TRUE.equals(request.getAttribute(ERROR_NOTIFICATION_SENT_ATTRIBUTE))) {
      return;
    }
    request.setAttribute(ERROR_NOTIFICATION_SENT_ATTRIBUTE, Boolean.TRUE);
    sendErrorMessage(type, message, url, payload);
  }

  private void postTelegramMessage(String sendUrl, String chatId, String safeMessage, boolean html) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("chat_id", chatId);
      body.add("text", safeMessage);
      if (html) {
        body.add("parse_mode", "HTML");
      }

      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
      restTemplate.postForObject(sendUrl, request, String.class);
    } catch (Exception e) {
      System.err.println("Failed to send Telegram message to " + chatId + ": " + e.getMessage());
    }
  }

  private List<String> buildErrorMessages(String projectName, String type, String message, String resolvedUrl, String payload) {
    List<String> messageChunks = splitText(message, MESSAGE_CHUNK_LENGTH);
    if (messageChunks.isEmpty()) {
      messageChunks.add("");
    }

    List<String> messages = new ArrayList<>();
    int totalParts = messageChunks.size();
    for (int index = 0; index < totalParts; index++) {
      StringBuilder fullMessage = new StringBuilder();
      appendHeader(fullMessage, projectName);
      if (!isBlank(type)) {
        fullMessage.append("\u2022 Type: ").append(type).append("\n");
      }
      if (!isBlank(message)) {
        fullMessage.append("\u2022 Message");
        if (totalParts > 1) {
          fullMessage.append(" (").append(index + 1).append("/").append(totalParts).append(")");
        }
        fullMessage.append(": ").append(messageChunks.get(index)).append("\n");
      }
      if (!isBlank(resolvedUrl)) {
        fullMessage.append("\u2022 URL: ").append(resolvedUrl).append("\n");
      }
      if (index == totalParts - 1 && !isBlank(payload)) {
        fullMessage.append("\n\u2022 Payload:\n").append(payload).append("\n");
      }
      messages.add(fullMessage.toString());
    }
    return messages;
  }

  private void appendHeader(StringBuilder fullMessage, String projectName) {
    if (!isBlank(projectName)) {
      fullMessage.append("\uD83D\uDEA8 ").append(projectName).append(" \uD83D\uDEA8\n\n");
    } else {
      fullMessage.append("\uD83D\uDEA8 System Error Alert \uD83D\uDEA8\n\n");
    }
  }

  private boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }

  private boolean isHtml(String parseMode) {
    return parseMode != null && "html".equalsIgnoreCase(parseMode.trim());
  }

  private List<String> splitText(String value, int maxLength) {
    List<String> parts = new ArrayList<>();
    if (value == null) {
      return parts;
    }
    if (value.length() <= maxLength) {
      parts.add(value);
      return parts;
    }

    int start = 0;
    while (start < value.length()) {
      int end = Math.min(start + maxLength, value.length());
      if (end > start && Character.isHighSurrogate(value.charAt(end - 1))) {
        end--;
      }
      parts.add(value.substring(start, end));
      start = end;
    }
    return parts;
  }

  private String firstNonBlank(String... values) {
    if (values == null) {
      return null;
    }
    for (String value : values) {
      if (!isBlank(value)) {
        return value;
      }
    }
    return null;
  }

  private String resolveUrl(String baseUrl, String contextPath, String pathOrUrl) {
    if (isBlank(baseUrl)) {
      return pathOrUrl;
    }
    if (isBlank(pathOrUrl)) {
      return baseUrl;
    }
    String trimmedBase = baseUrl.trim();
    String trimmedPath = pathOrUrl.trim();
    if (trimmedPath.startsWith("http://") || trimmedPath.startsWith("https://")) {
      return trimmedPath;
    }
    if (!isBlank(contextPath)) {
      String normalizedContextPath = contextPath.trim();
      if (!normalizedContextPath.startsWith("/")) {
        normalizedContextPath = "/" + normalizedContextPath;
      }
      if (trimmedPath.startsWith(normalizedContextPath)) {
        trimmedPath = trimmedPath.substring(normalizedContextPath.length());
      }
    }
    if (!trimmedPath.startsWith("/")) {
      trimmedPath = "/" + trimmedPath;
    }
    if (trimmedBase.endsWith("/")) {
      trimmedBase = trimmedBase.substring(0, trimmedBase.length() - 1);
    }
    return trimmedBase + trimmedPath;
  }

  private String resolveModuleBaseUrl(String contextPath, String projectName) {
    String moduleKey = resolveModuleKey(contextPath, projectName);
    if (isBlank(moduleKey)) {
      return null;
    }
    String host = environment.getProperty("global.api.base-url." + moduleKey);
    if (isBlank(host)) {
      return null;
    }
    String trimmedHost = environment.resolvePlaceholders(host.trim());
    if (trimmedHost.endsWith("/")) {
      trimmedHost = trimmedHost.substring(0, trimmedHost.length() - 1);
    }
    if (isBlank(contextPath)) {
      return trimmedHost;
    }
    String trimmedContextPath = contextPath.trim();
    if (!trimmedContextPath.startsWith("/")) {
      trimmedContextPath = "/" + trimmedContextPath;
    }
    return trimmedHost + trimmedContextPath;
  }

  private String resolveModuleKey(String contextPath, String projectName) {
    String source = firstNonBlank(contextPath, projectName);
    if (isBlank(source)) {
      return null;
    }
    String normalized = source.toLowerCase();
    if (normalized.contains("sales")) {
      return "sales";
    }
    if (normalized.contains("hr")) {
      return "hr";
    }
    if (normalized.contains("inventory")) {
      return "inventory";
    }
    if (normalized.contains("purchasing")) {
      return "purchasing";
    }
    if (normalized.contains("account")) {
      return "accounting";
    }
    return null;
  }
}
