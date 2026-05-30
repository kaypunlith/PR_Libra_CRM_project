package com.ut.nlSystemAPi.config;

import com.ut.nlSystemAPi.helper.TelegramBotService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class ActivityLogTelegramErrorInterceptor implements MethodInterceptor {

  private static final int ENDPOINT_INDEX = 0;
  private static final int LINE_INDEX = 1;
  private static final int BUG_INDEX = 2;
  private static final int MODULE_NAME_INDEX = 3;
  private static final int MODULE_ID_INDEX = 4;
  private static final int ACTION_INDEX = 5;
  private static final int STATUS_INDEX = 6;
  private static final int REQUEST_INDEX = 10;

  private final TelegramBotService telegramBotService;

  public ActivityLogTelegramErrorInterceptor(TelegramBotService telegramBotService) {
    this.telegramBotService = telegramBotService;
  }

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    Object result = invocation.proceed();
    notifyIfErrorActivityLog(invocation);
    return result;
  }

  private void notifyIfErrorActivityLog(MethodInvocation invocation) {
    Method method = invocation.getMethod();
    Object[] args = invocation.getArguments();
    if (!"insert".equals(method.getName()) || args == null || args.length <= REQUEST_INDEX) {
      return;
    }

    Integer status = asInteger(args[STATUS_INDEX]);
    String bug = asString(args[BUG_INDEX]);
    if (status == null || status != 2 || isBlank(bug)) {
      return;
    }

    HttpServletRequest request = args[REQUEST_INDEX] instanceof HttpServletRequest
        ? (HttpServletRequest) args[REQUEST_INDEX]
        : null;
    String endpoint = firstNonBlank(asString(args[ENDPOINT_INDEX]), request == null ? null : request.getRequestURI());
    String type = isSqlError(bug) ? "Database/SQL Error Detected" : "Global Exception Detected";
    telegramBotService.sendErrorMessageOnce(request, type, bug, endpoint, buildPayload(args));
  }

  private String buildPayload(Object[] args) {
    StringBuilder payload = new StringBuilder();
    appendPayload(payload, "Module", asString(args[MODULE_NAME_INDEX]));
    appendPayload(payload, "Screen", asString(args[MODULE_ID_INDEX]));
    appendPayload(payload, "Action", asString(args[ACTION_INDEX]));
    appendPayload(payload, "Line", asString(args[LINE_INDEX]));
    return payload.toString();
  }

  private void appendPayload(StringBuilder payload, String label, String value) {
    if (isBlank(value)) {
      return;
    }
    if (payload.length() > 0) {
      payload.append('\n');
    }
    payload.append(label).append(": ").append(value);
  }

  private Integer asInteger(Object value) {
    if (value instanceof Number) {
      return ((Number) value).intValue();
    }
    if (value == null) {
      return null;
    }
    try {
      return Integer.valueOf(String.valueOf(value));
    } catch (NumberFormatException ex) {
      return null;
    }
  }

  private String asString(Object value) {
    return value == null ? null : String.valueOf(value);
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

  private boolean isSqlError(String value) {
    String lower = value.toLowerCase();
    return lower.contains("sql")
        || lower.contains("mybatis")
        || lower.contains("dataaccess")
        || lower.contains("bad grammar")
        || lower.contains("syntax");
  }

  private boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }
}
