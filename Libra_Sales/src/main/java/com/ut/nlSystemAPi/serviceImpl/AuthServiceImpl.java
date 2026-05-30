package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.config.RestTemplateUtils;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.OauthToken;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.AuthService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private ActivityLogService activityLogService;

  @Autowired
  private MessageService messageService;

//  public ResponseMessage<OauthToken> responseToken(Long statusLogin,String authUrl, RestTemplate restTemplate, HttpEntity<MultiValueMap<String, String>> request, HttpServletRequest httpServletRequest, String username, String password) throws UnknownHostException {
//    LocalTime startDuration = LocalTime.now();
//    Long line = 1033L;
//    try {
//      int responseResult;
//      String errorCode  = "401";
//      String errorDesc  = "Unauthorized";
//      OauthToken oauth2 = new OauthToken();
//      try {
//        // Access user token
//        ResponseEntity<String> response = RestTemplateUtils.requestRestTemplate().postForEntity(authUrl, request, String.class);
//        // Check User Login with Billing System
////        Boolean checkLoginBilling = Billing.userLogin(username, password);
////        if(checkLoginBilling){
//        if(true){
//          // Return Token Value
//          JSONObject jsonObject = new JSONObject(response.getBody());
//          oauth2.setAccessToken(jsonObject.getString("access_token"));
//          oauth2.setTokenType(jsonObject.getString("token_type"));
//          oauth2.setRefreshToken(jsonObject.getString("refresh_token"));
//          oauth2.setExpiresIn(jsonObject.getLong("expires_in"));
//          oauth2.setScope(jsonObject.getString("scope"));
//          oauth2.setStatusLogin(statusLogin);
//          responseResult = 1;
//        } else {
//          responseResult = 2;
//        }
//      } catch (Exception e) {
//        responseResult = 2;
//      }
//      if(responseResult == 1){
//        /*System Activity*/
//        LocalTime endDuration = LocalTime.now();
//        activityLogService.insert("/auth/login",null,null,"Auth","Auth (Login)","Login",1,"Success",startDuration,endDuration, httpServletRequest);
//
//        return ResponseMessageUtils.makeSuccessResponse(oauth2);
//      } else {
//        return ResponseMessageUtils.makeResponse(false, 401, "401", "Invalid username or password");
//      }
//    } catch (Exception error) {
//      /*System Activity*/
//      LocalTime endDuration = LocalTime.now();
//      activityLogService.insert("/auth/login",line, error.toString(),"Auth","Auth (Login)","Login",2,"Error",startDuration,endDuration, httpServletRequest);
//      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
//    }
//  }


  public ResponseMessage<OauthToken> responseToken(String authUrl, RestTemplate restTemplate, HttpEntity<MultiValueMap<String, String>> request){
    int responseResult;
    String errorCode  = "401";
    String errorDesc  = "Unauthorized";
    OauthToken oauth2 = new OauthToken();
    try {
      // Access user token
      ResponseEntity<String> response = RestTemplateUtils.requestRestTemplate().postForEntity(authUrl, request, String.class);
      // Return Token Value
      JSONObject jsonObject = new JSONObject(response.getBody());
      oauth2.setAccessToken(jsonObject.getString("access_token"));
      oauth2.setTokenType(jsonObject.getString("token_type"));
      oauth2.setRefreshToken(jsonObject.getString("refresh_token"));
      oauth2.setExpiresIn(jsonObject.getLong("expires_in"));
      oauth2.setScope(jsonObject.getString("scope"));
      responseResult = 1;
    } catch (Exception e) {
      responseResult = 2;
    }

    if(responseResult == 1){
      return ResponseMessageUtils.makeSuccessResponse(oauth2);
    } else {
      return ResponseMessageUtils.makeResponse(false, 401, errorCode, errorDesc);
    }
  }

}
