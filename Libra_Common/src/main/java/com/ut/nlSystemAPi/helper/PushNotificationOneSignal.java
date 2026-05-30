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
    public static final String REST_API_KEY = "NzViNTYyOWYtMjgyMi00YjJhLWJhNDktMDU2M2ViZjQwYmE5";

    //! Work Permit
//  public static final String APP_ID = "586c45ab-1051-4533-8fa6-62b51ac89abb";

    //! UT-EMR Mobile
    public static final String APP_ID = "23d9e3fe-14cb-447e-9d20-4ee0d38aac4d";

    public static void pushAndroid(List<String> deviceTokens, String title, String body, Long feedId) {
        List<String> safeDeviceTokens = safeDeviceTokens(deviceTokens);
        if (safeDeviceTokens.isEmpty()) {
            return;
        }

        try {
            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic " + REST_API_KEY); //REST API
            con.setRequestMethod("POST");

            String strJsonBody = buildPayload(safeDeviceTokens, title, body, feedId);
            byte[] sendBytes = strJsonBody.getBytes(StandardCharsets.UTF_8);
            con.setFixedLengthStreamingMode(sendBytes.length);

            try (OutputStream outputStream = con.getOutputStream()) {
                outputStream.write(sendBytes);
            }

            int httpResponse = con.getResponseCode();
            if (httpResponse >= HttpURLConnection.HTTP_MULT_CHOICE) {
                System.err.println("OneSignal push failed: HTTP " + httpResponse);
            }

        } catch (Throwable t) {
            System.err.println("OneSignal push failed: " + t.getMessage());
        }
    }

    private static String buildPayload(List<String> deviceTokens, String title, String body, Long feedId) {
        JSONObject payload = new JSONObject();
        payload.put("app_id", APP_ID);
        payload.put("include_player_ids", new JSONArray(deviceTokens));
        payload.put("headings", new JSONObject().put("en", firstNonBlank(title, body)));
        payload.put("contents", new JSONObject().put("en", safeText(body)));

        JSONObject data = new JSONObject();
        data.put("feedId", feedId == null ? "" : String.valueOf(feedId));
        payload.put("data", data);
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
