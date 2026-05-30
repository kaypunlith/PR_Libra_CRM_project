//package com.ut.nlSystemAPi.controller;
//
//import com.ut.nlSystemAPi.base.UserAuthSession;
//import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
//import com.ut.nlSystemAPi.mapper.primary.AuthMapper;
//import com.ut.nlSystemAPi.model.base.OauthToken;
//import com.ut.nlSystemAPi.model.base.ResponseMessage;
//import com.ut.nlSystemAPi.service.AuthService;
//import io.micrometer.core.annotation.Timed;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.Authorization;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//@RestController
//@RequestMapping("/auth")
//@Api(tags = "01. Auth", description = "Auth Resource")
//@Timed
//public class AuthController {
//
//  @Autowired
//  Environment environment;
//
//  @Autowired
//  private AuthMapper authMapper;
//
//  @Autowired
//  private AuthService authService;
//
//  @PostMapping("/login")
//  @ApiOperation(value = "login", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success")
//  public ResponseMessage<OauthToken> login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("deviceId") String deviceId, @RequestParam("deviceName") String deviceName) {
//
//    RestTemplate restTemplate = new RestTemplate();
//    HttpHeaders headers = new HttpHeaders();
//    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//    String clientSecret = "#TKPI232873IU";
//    int tokenValidate   = 60 * 60 * 24 * 30; // 30 Days
//    int refreshTokenValidate   = 60 * 60 * 24 * 90; // 90 Days
//    // Check User
//    if(!username.isEmpty() && !password.isEmpty() && !deviceName.isEmpty() && !deviceId.isEmpty()){
//      Long checkUser = authMapper.checkUserValid(username);
//      if(checkUser > 0){
//        // Check Device Insert
//        if(authMapper.insertClientId(deviceId, deviceName, clientSecret, tokenValidate, refreshTokenValidate)){
//          MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//          map.add("grant_type", "password");
//          map.add("client_id", deviceId);
//          map.add("client_secret", clientSecret);
//          map.add("username", username);
//          map.add("password", password);
//          HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//          String authUrl = environment.getProperty("api.authUrl");
//          return authService.responseToken(authUrl, restTemplate, request);
//        } else {
//          return ResponseMessageUtils.makeResponse(false, 400, "Bad Request", "Invalid Parameter");
//        }
//      } else {
//        return ResponseMessageUtils.makeResponse(false, 401, "401", "Invalid username and password");
//      }
//    } else {
//      return ResponseMessageUtils.makeResponse(false, 400, "Bad Request", "Invalid Parameter");
//    }
//  }
//
//  @PostMapping("/login-with-refresh-token")
//  @ApiOperation(value = "Login with refresh token", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success")
//  public ResponseMessage<OauthToken> loginWithRefreshToken(@RequestParam("refreshToken") String refreshToken, @RequestParam("deviceId") String deviceId) {
//    if(!refreshToken.isEmpty() && !deviceId.isEmpty()){
//      RestTemplate restTemplate = new RestTemplate();
//      HttpHeaders headers = new HttpHeaders();
//      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//      MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//      map.add("grant_type", "refresh_token");
//      map.add("client_id", deviceId);
//      map.add("client_secret", "#TKPI232873IU");
//      map.add("refresh_token", refreshToken);
//      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//      String authUrl = environment.getProperty("api.authUrl");
//      return authService.responseToken(authUrl, restTemplate, request);
//    } else {
//      return ResponseMessageUtils.makeResponse(false, 400, "Bad Request", "Invalid Parameter");
//    }
//  }
//
//  @PostMapping("/logout")
//  @ApiOperation(value = "Logout", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//  public ResponseMessage<String> loginWithRefreshToken(@RequestParam("deviceId") String deviceId) {
//    if (UserAuthSession.getUserAuth() != null && deviceId != null && !deviceId.isEmpty()) {
//      String tokenId = authMapper.checkUserLogout(UserAuthSession.getUserAuth().getUsername(), deviceId);
//      if(tokenId != null && !tokenId.isEmpty()){
//        authMapper.clearUserToken(UserAuthSession.getUserAuth().getUsername(), deviceId, tokenId);
//      }
//      return ResponseMessageUtils.makeResponse(true, "Success");
//    } else {
//      return ResponseMessageUtils.makeResponse(false, 400, "Bad Request", "Invalid Parameter");
//    }
//  }
//
//  @PostMapping("/checkToken")
//  @ApiOperation(value = "Check Token", notes = "401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
//  public ResponseMessage<String> checkToken() {
//    if (UserAuthSession.getUserAuth() != null) {
//      return ResponseMessageUtils.makeResponse(true, "Token Valid");
//    } else {
//      return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//    }
//  }
//
//}