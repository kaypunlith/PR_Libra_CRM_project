package com.ut.nlSystemAPi.helper;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class TelegramUtils {

    private static final String DEFAULT_API_TOKEN = "7184951726:AAHth32ZgP9R685UXTGfBXFSZMopjCojwbw";
    private static final String DOCUMENT_API_TOKEN = "5037681481:AAEjHkKHndPd8Fi9bS1pgWG7J6VSHvZ49sE";
    private static final int TELEGRAM_MAX_MESSAGE_LENGTH = 4096;
    private static final int SAFE_MESSAGE_LENGTH = 3900;

    public TelegramUtils() {}

    public static Boolean sendMessage(String sendMessage, String chatId) {
        return sendMessage(sendMessage, chatId, DEFAULT_API_TOKEN);
    }

    public static Boolean sendMessage(String sendMessage, String chatId, String apiToken) {
        return sendHtmlMessage(sendMessage, chatId, apiToken, null) != null;
    }

    public static Boolean sendMessage1(String sendMessage, String chatId) {
        return sendHtmlMessage(sendMessage, chatId, DEFAULT_API_TOKEN, null) != null;
    }

    public static Boolean sendMessageToPersonal(String message, String employeeChatId, String apiToken) {
        return sendHtmlMessage(message, employeeChatId, apiToken, null) != null;
    }

    public static Long sendHtmlMessage(String sendMessage, String chatId, String apiToken, Long replyToMessageId) {
        if (isBlank(apiToken)) {
            System.err.println("Telegram send failed: bot token is empty");
            return null;
        }

        if (isBlank(chatId)) {
            System.err.println("Telegram send failed: chat id is empty");
            return null;
        }

        String normalizedMessage = limitMessage(safeHtmlMessage(sendMessage));
        Long messageId = postMessage(apiToken, chatId, normalizedMessage, "HTML", replyToMessageId);
        if (messageId != null) {
            return messageId;
        }

        return postMessage(apiToken, chatId, limitMessage(stripHtmlTags(normalizeMessage(sendMessage))), null, replyToMessageId);
    }

    public Long pushTelegram(String sendMessage, String chatId, String apiToken, Long replyToMessageId) {
        return sendHtmlMessage(sendMessage, chatId, apiToken, replyToMessageId);
    }

    public static String safeText(Object value) {
        String text = normalizeMessage(value == null ? "" : String.valueOf(value));
        return escapeHtmlText(text);
    }

    public static String safeHtmlMessage(Object value) {
        String text = normalizeMessage(value == null ? "" : String.valueOf(value));
        StringBuilder builder = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (current == '<') {
                int tagEnd = text.indexOf('>', i + 1);
                if (tagEnd > i && isAllowedTelegramHtmlTag(text.substring(i, tagEnd + 1))) {
                    builder.append(text, i, tagEnd + 1);
                    i = tagEnd;
                } else {
                    builder.append("&lt;");
                }
            } else if (current == '>') {
                builder.append("&gt;");
            } else if (current == '&') {
                int entityEnd = findHtmlEntityEnd(text, i);
                if (entityEnd > i) {
                    builder.append(text, i, entityEnd + 1);
                    i = entityEnd;
                } else {
                    builder.append("&amp;");
                }
            } else if (current == '"') {
                builder.append("&quot;");
            } else {
                builder.append(current);
            }
        }
        return builder.toString();
    }

    public static String safePlainText(Object value) {
        return stripHtmlTags(normalizeMessage(value == null ? "" : String.valueOf(value)));
    }

    private static Long postMessage(String apiToken, String chatId, String message, String parseMode, Long replyToMessageId) {
        String urlString = String.format("https://api.telegram.org/bot%s/sendMessage", apiToken);

        StringBuilder postData = new StringBuilder();
        postData.append("chat_id=").append(urlEncode(chatId));
        postData.append("&text=").append(urlEncode(message));
        if (!isBlank(parseMode)) {
            postData.append("&parse_mode=").append(urlEncode(parseMode));
        }
        if (replyToMessageId != null) {
            postData.append("&reply_to_message_id=").append(urlEncode(String.valueOf(replyToMessageId)));
        }

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(postData.toString().getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            String responseBody;
            if (responseCode < HttpURLConnection.HTTP_OK || responseCode >= HttpURLConnection.HTTP_MULT_CHOICE) {
                responseBody = readResponse(conn.getErrorStream());
                System.err.println("Telegram send failed: HTTP " + responseCode + " " + responseBody);
                return null;
            }

            responseBody = readResponse(conn.getInputStream());
            return extractMessageId(responseBody);
        } catch (Exception e) {
            System.err.println("Telegram send failed: " + e.getMessage());
            return null;
        }
    }

    public static boolean sendMessageWithFile(String file, String chatId) {
        return sendMessageWithFile(file, chatId, DOCUMENT_API_TOKEN);
    }

    public static boolean sendMessageWithFile(String file, String chatId, String apiToken) {
        String urlString = "https://api.telegram.org/bot%s/sendDocument?chat_id=%s&document=%s";

        if (isBlank(chatId) || isBlank(file)) {
            return false;
        }

        urlString = String.format(urlString, apiToken, urlEncode(chatId), urlEncode(file));
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();


            StringBuilder sb = new StringBuilder();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            return  true;
        } catch (IOException e) {
            System.err.println("Telegram document send failed: " + e.getMessage());
            return false;
        }
    }

    private static String normalizeMessage(String sendMessage) {
        if (sendMessage == null) {
            return "";
        }
        return sendMessage
                .replace("%0A", "\n")
                .replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
    }

    private static String limitMessage(String message) {
        if (message == null) {
            return "";
        }
        if (message.length() <= SAFE_MESSAGE_LENGTH) {
            return message;
        }

        int end = Math.min(SAFE_MESSAGE_LENGTH, TELEGRAM_MAX_MESSAGE_LENGTH - 20);
        if (Character.isHighSurrogate(message.charAt(end - 1))) {
            end--;
        }
        return message.substring(0, end) + "\n...";
    }

    private static String stripHtmlTags(String message) {
        if (message == null) {
            return "";
        }
        return message
                .replaceAll("<[^>]+>", "")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&")
                .replace("&quot;", "\"")
                .replace("&#39;", "'");
    }

    private static String escapeHtmlText(String text) {
        if (text == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (current == '&') {
                int entityEnd = findHtmlEntityEnd(text, i);
                if (entityEnd > i) {
                    builder.append(text, i, entityEnd + 1);
                    i = entityEnd;
                } else {
                    builder.append("&amp;");
                }
            } else if (current == '<') {
                builder.append("&lt;");
            } else if (current == '>') {
                builder.append("&gt;");
            } else if (current == '"') {
                builder.append("&quot;");
            } else {
                builder.append(current);
            }
        }
        return builder.toString();
    }

    private static int findHtmlEntityEnd(String text, int ampersandIndex) {
        int end = text.indexOf(';', ampersandIndex + 1);
        if (end < 0 || end - ampersandIndex > 12) {
            return -1;
        }

        String entity = text.substring(ampersandIndex + 1, end).toLowerCase(Locale.ROOT);
        if (entity.matches("#\\d+") || entity.matches("#x[0-9a-f]+")) {
            return end;
        }
        if ("amp".equals(entity) || "lt".equals(entity) || "gt".equals(entity)
                || "quot".equals(entity) || "apos".equals(entity)) {
            return end;
        }
        return -1;
    }

    private static boolean isAllowedTelegramHtmlTag(String tag) {
        String normalizedTag = tag.toLowerCase(Locale.ROOT).trim();
        if (normalizedTag.matches("</?(b|strong|i|em|u|ins|s|strike|del|code|pre|tg-spoiler)>")) {
            return true;
        }
        if (normalizedTag.matches("<a\\s+href\\s*=\\s*\"[^\"]+\">")) {
            return true;
        }
        return normalizedTag.matches("<span\\s+class\\s*=\\s*\"tg-spoiler\">|</span>");
    }

    private static Long extractMessageId(String responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            if (!jsonObject.optBoolean("ok", false)) {
                return null;
            }
            JSONObject result = jsonObject.optJSONObject("result");
            if (result == null) {
                return null;
            }
            return result.has("message_id") ? result.getLong("message_id") : null;
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding is not supported", e);
        }
    }

    private static String readResponse(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        try (InputStream is = new BufferedInputStream(inputStream);
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
        }
        return sb.toString();
    }

}
