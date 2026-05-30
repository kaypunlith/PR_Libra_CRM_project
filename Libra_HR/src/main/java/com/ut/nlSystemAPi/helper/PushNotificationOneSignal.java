package com.ut.nlSystemAPi.helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PushNotificationOneSignal {


  //!     https://onesignal.com/api/v1/notifications
  public static final String REST_API_KEY = "NTRjY2MzYjAtYzEyMS00Y2UzLWFlNWYtODM2MjIwZTE4YjJi";

  //! VET Attendance System
  public static final String APP_ID = "249bbdad-46ba-47a8-9b90-814f6a2af69a";

  public static void pushAndroid(List<String> deviceTokens, String title, String body) {
    List<String> safeDeviceTokens = safeDeviceTokens(deviceTokens);
    if (safeDeviceTokens.isEmpty()) {
      return;
    }

    try {
      URL url = new URL("https://onesignal.com/api/v1/notifications");
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setUseCaches(false);
      con.setDoOutput(true);
      con.setDoInput(true);

      con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
      con.setRequestProperty("Authorization","Basic " + REST_API_KEY); //REST API
      con.setRequestMethod("POST");

      String strJsonBody = buildPayload(safeDeviceTokens, title, body);

      byte[] sendBytes = strJsonBody.getBytes(StandardCharsets.UTF_8);
      con.setFixedLengthStreamingMode(sendBytes.length);

      try (OutputStream outputStream = con.getOutputStream()) {
        outputStream.write(sendBytes);
      }

      int httpResponse = con.getResponseCode();
      if (httpResponse >= HttpURLConnection.HTTP_MULT_CHOICE) {
        System.err.println("OneSignal push failed: HTTP " + httpResponse);
      }

    } catch(Throwable t) {
      System.err.println("OneSignal push failed: " + t.getMessage());
    }
  }

  private static String buildPayload(List<String> deviceTokens, String title, String body) {
    JSONObject payload = new JSONObject();
    payload.put("app_id", APP_ID);
    payload.put("include_player_ids", new JSONArray(deviceTokens));
    payload.put("headings", new JSONObject().put("en", firstNonBlank(title, body)));
    payload.put("contents", new JSONObject().put("en", safeText(body)));
    payload.put("data", new JSONObject().put("type", "1"));
    return payload.toString();
  }

  private static List<String> safeDeviceTokens(List<String> deviceTokens) {
    List<String> result = new ArrayList<>();
    if (deviceTokens == null) {
      return result;
    }
    for (String deviceToken : deviceTokens) {
      if (deviceToken != null && !deviceToken.trim().isEmpty()) {
        result.add(deviceToken.trim());
      }
    }
    return result;
  }

  private static String firstNonBlank(String first, String second) {
    String safeFirst = safeText(first);
    if (!safeFirst.isEmpty()) {
      return safeFirst;
    }
    return safeText(second);
  }

  private static String safeText(Object value) {
    if (value == null) {
      return "";
    }
    String text = String.valueOf(value).replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "").trim();
    int maxLength = 2000;
    return text.length() <= maxLength ? text : text.substring(0, maxLength);
  }

}
