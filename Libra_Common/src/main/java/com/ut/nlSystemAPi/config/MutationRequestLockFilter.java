package com.ut.nlSystemAPi.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 50)
public class MutationRequestLockFilter extends OncePerRequestFilter {

  private static final int DEFAULT_MAX_BODY_BYTES = 1024 * 1024;
  private static final Set<String> DEFAULT_MUTATION_PREFIXES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
      "add", "update", "edit", "delete", "remove", "insert", "save", "create", "approve", "apply",
      "close", "cancel", "reject", "void", "pay", "post", "convert", "merge", "pick", "receive",
      "return", "adjust", "submit", "change"
  )));

  private final ConcurrentMap<String, Boolean> inFlightRequests = new ConcurrentHashMap<>();
  private final Environment environment;

  public MutationRequestLockFilter(Environment environment) {
    this.environment = environment;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    if (!isEnabled()) {
      return true;
    }
    String method = request.getMethod();
    if ("GET".equalsIgnoreCase(method) || "HEAD".equalsIgnoreCase(method) || "OPTIONS".equalsIgnoreCase(method)) {
      return true;
    }
    if ("PUT".equalsIgnoreCase(method) || "PATCH".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) {
      return false;
    }
    return !hasMutationAction(request.getRequestURI());
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    RequestKey requestKey = buildRequestKey(request);
    if (inFlightRequests.putIfAbsent(requestKey.key, Boolean.TRUE) != null) {
      writeDuplicateResponse(response);
      return;
    }

    try {
      filterChain.doFilter(requestKey.request, response);
    } finally {
      inFlightRequests.remove(requestKey.key);
    }
  }

  private RequestKey buildRequestKey(HttpServletRequest request) throws IOException {
    String idempotencyKey = request.getHeader("X-Idempotency-Key");
    String baseKey = request.getMethod() + ":" + request.getRequestURI() + ":" + nullToEmpty(request.getQueryString());
    if (idempotencyKey != null && !idempotencyKey.trim().isEmpty()) {
      return new RequestKey(request, baseKey + ":idempotency:" + idempotencyKey.trim());
    }

    String contentType = nullToEmpty(request.getContentType()).toLowerCase(Locale.ROOT);
    if (contentType.startsWith("multipart/")) {
      return new RequestKey(request, baseKey + ":multipart:" + request.getContentLengthLong());
    }

    long contentLength = request.getContentLengthLong();
    int maxBodyBytes = getIntProperty("global.request-lock.max-body-bytes", DEFAULT_MAX_BODY_BYTES);
    if (contentLength < 0 || contentLength > maxBodyBytes) {
      return new RequestKey(request, baseKey + ":body-length:" + contentLength);
    }

    byte[] body = StreamUtils.copyToByteArray(request.getInputStream());
    CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request, body);
    return new RequestKey(wrappedRequest, baseKey + ":body:" + sha256(body));
  }

  private boolean hasMutationAction(String uri) {
    String path = nullToEmpty(uri).toLowerCase(Locale.ROOT);
    String[] segments = path.split("/");
    Set<String> prefixes = getMutationPrefixes();
    for (int i = segments.length - 1; i >= 0; i--) {
      String segment = segments[i];
      if (segment.isEmpty() || isPathValue(segment)) {
        continue;
      }
      for (String prefix : prefixes) {
        if (segment.startsWith(prefix)) {
          return true;
        }
      }
      return false;
    }
    return false;
  }

  private boolean isPathValue(String segment) {
    if ("true".equals(segment) || "false".equals(segment)) {
      return true;
    }
    return segment.matches("\\d+") || segment.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
  }

  private Set<String> getMutationPrefixes() {
    String configuredPrefixes = environment.getProperty("global.request-lock.mutation-prefixes");
    if (configuredPrefixes == null || configuredPrefixes.trim().isEmpty()) {
      return DEFAULT_MUTATION_PREFIXES;
    }

    Set<String> prefixes = new HashSet<>();
    for (String prefix : configuredPrefixes.split(",")) {
      String value = prefix.trim().toLowerCase(Locale.ROOT);
      if (!value.isEmpty()) {
        prefixes.add(value);
      }
    }
    return prefixes.isEmpty() ? DEFAULT_MUTATION_PREFIXES : prefixes;
  }

  private boolean isEnabled() {
    return Boolean.parseBoolean(environment.getProperty("global.request-lock.enabled", "true"));
  }

  private int getIntProperty(String name, int defaultValue) {
    String value = environment.getProperty(name);
    if (value == null || value.trim().isEmpty()) {
      return defaultValue;
    }
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException ignored) {
      return defaultValue;
    }
  }

  private String sha256(byte[] body) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(body);
      StringBuilder builder = new StringBuilder(hash.length * 2);
      for (byte value : hash) {
        builder.append(String.format("%02x", value));
      }
      return builder.toString();
    } catch (NoSuchAlgorithmException error) {
      throw new IllegalStateException("SHA-256 is not available", error);
    }
  }

  private void writeDuplicateResponse(HttpServletResponse response) throws IOException {
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write("{\"header\":{\"serverTimestamp\":" + System.currentTimeMillis()
        + ",\"result\":false,\"statusCode\":429,\"errorCode\":\"duplicate_request\","
        + "\"errorText\":\"The same request is already processing. Please wait.\"}}");
  }

  private String nullToEmpty(String value) {
    return value == null ? "" : value;
  }

  private static class RequestKey {
    private final HttpServletRequest request;
    private final String key;

    private RequestKey(HttpServletRequest request, String key) {
      this.request = request;
      this.key = key;
    }
  }
}
