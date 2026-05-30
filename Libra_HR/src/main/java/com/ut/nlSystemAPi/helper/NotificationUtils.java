package com.ut.nlSystemAPi.helper;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.ApnsService;
import com.ut.nlSystemAPi.model.notification.ApnsProvider;
import com.ut.nlSystemAPi.model.notification.PushMessage;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class NotificationUtils {

  public enum PushType {
    DEV
  }

  private static void pushAndroid(String deviceToken, PushMessage pushMessage, PushType pushType,  ApnsProvider apnsProvider) {
    if(apnsProvider != null){
      try {
        String androidFcmKey;
        if (pushType.equals(PushType.DEV)) {
          androidFcmKey = apnsProvider.getDevKey();
        } else {
          androidFcmKey = apnsProvider.getProdKey();
        }
        String androidFcmUrl = apnsProvider.getApiUrl();
        String token = deviceToken;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json; charset=UTF-8;");
        httpHeaders.set("Authorization", "key=" + androidFcmKey);

        JSONObject notification = new JSONObject();
        notification.put("title", pushMessage.getTitle());
        notification.put("body", pushMessage.getBody());

        JSONObject data = new JSONObject();
        data.put("title", pushMessage.getTitle());
        data.put("message", pushMessage.getBody());
        data.put("type", pushMessage.getType());
        data.put("id", pushMessage.getGoodsTransferId());
        data.put("image", pushMessage.getImage());

        JSONObject json = new JSONObject();
        json.put("to", token);
        json.put("notification", notification);
        json.put("data", data);

        HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(), httpHeaders);
        String response = restTemplate.postForObject(androidFcmUrl, httpEntity, String.class);
        System.out.println(response);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }

  private static void pushIos(String deviceToken, PushMessage pushMessage, PushType pushType) throws IOException {
    ApnsService service;
    String certificate;
    String certPassword;
    Boolean isProduction;
    if (pushType.equals(PushType.DEV)) {
      certificate  = "certificate/DevelopmentCertificates.p12";
      certPassword = "mengly1234";
      isProduction = false;
    } else {
      certificate  = "certificate/ProductionCertificates.p12";
      certPassword = "mengly1234";
      isProduction = true;
    }

    service = APNS.newService()
      .withCert(new ClassPathResource(certificate).getInputStream(), certPassword)
      .withAppleDestination(isProduction)
      .build();

    String payload = APNS.newPayload()
      .category(pushMessage.getCategory())
      .alertTitle(pushMessage.getTitle())
      .alertBody(pushMessage.getBody())
      .badge(pushMessage.getBadge())
      .sound(pushMessage.getSound())
      .customField("type", pushMessage.getType())
      .customField("id", pushMessage.getGoodsTransferId())
      .customField("image", pushMessage.getImage())
      .build();

    String token = deviceToken;
    ApnsNotification response = service.push(token, payload);
    System.out.println(response);
  }

}