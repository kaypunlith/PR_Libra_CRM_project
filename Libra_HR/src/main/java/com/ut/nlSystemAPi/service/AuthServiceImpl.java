//package com.ut.nlSystemAPi.service;
//
//import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
//import com.ut.nlSystemAPi.model.base.OauthToken;
//import com.ut.nlSystemAPi.model.base.ResponseMessage;
//import org.json.JSONObject;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class AuthServiceImpl implements AuthService {
//
//  public ResponseMessage<OauthToken> responseToken(String authUrl, RestTemplate restTemplate, HttpEntity<MultiValueMap<String, String>> request){
//    int responseResult;
//    String errorCode  = "401";
//    String errorDesc  = "Invalid username and password (link server)";
//    OauthToken oauth2 = new OauthToken();
//    try {
//      // Access user token
//      ResponseEntity<String> response = restTemplate.postForEntity(authUrl, request, String.class);
//      // Return Token Value
//      JSONObject jsonObject = new JSONObject(response.getBody());
//      oauth2.setAccessToken(jsonObject.getString("access_token"));
//      oauth2.setTokenType(jsonObject.getString("token_type"));
//      oauth2.setRefreshToken(jsonObject.getString("refresh_token"));
//      oauth2.setExpiresIn(jsonObject.getLong("expires_in"));
//      oauth2.setScope(jsonObject.getString("scope"));
//      responseResult = 1;
//    } catch (Exception e) {
//      responseResult = 2;
//    }
//
//    if(responseResult == 1){
//      return ResponseMessageUtils.makeSuccessResponse(oauth2);
//    } else {
//      return ResponseMessageUtils.makeResponse(false, 401, errorCode, errorDesc);
//    }
//  }
//
//}