package com.ut.nlSystemAPi.helper;

import com.ut.nlSystemAPi.model.base.SmsSetting;
import org.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class SendSms {

  public static Boolean sendSmsApi(SmsSetting smsSetting, String phoneNumber, String sms, String code) {
    String smsApi = "http://mvar6dpxpbj3wuxd.udaya.asia:8095/SmSGateW/sms/send-by-one";
    try {
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
      map.add("tc", smsSetting.getTc());
      map.add("sc", smsSetting.getSc());
      map.add("num", phoneNumber);
      map.add("msg", sms);
      map.add("sendId", code);
      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
      ResponseEntity<String> response = restTemplate.postForEntity(smsApi, request, String.class);
      System.out.println(response);
      return true;
    } catch (JSONException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static Boolean sendSmsApi(String phoneNumber, String sms, String code) {
    String tc = "tc=ut75167:6fbbe:d01357f04:";
    String sc = "sc=aa6cc727099444eef311f8209fe1682a";
    String num = "num="+phoneNumber;
    String msg = "msg="+sms;
    String sendId = "sendId="+code;
    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.command("curl","-k","-X","POST","http://mvar6dpxpbj3wuxd.udaya.asia:8095/SmSGateW/sms/send-by-one","-F",tc,"-F",sc,"-F",num,"-F",msg,"-F",sendId);

    try {
      processBuilder.start();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

}