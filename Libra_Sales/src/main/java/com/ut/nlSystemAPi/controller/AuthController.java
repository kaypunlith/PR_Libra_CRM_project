package com.ut.nlSystemAPi.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.config.authenticator.ValidateCodeDto;
import com.ut.nlSystemAPi.helper.GeneratePasswordPDF;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.AuthMapper;
import com.ut.nlSystemAPi.model.CheckUserLogin;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.OauthToken;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.AccessTokenRequest;
import com.ut.nlSystemAPi.model.request.Login.LoginRequest;
import com.ut.nlSystemAPi.model.response.AccessTokenResponse;
import com.ut.nlSystemAPi.model.response.User.CaptchaResponse;
import com.ut.nlSystemAPi.service.AuthService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import de.taimos.totp.TOTP;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Api(tags = "01. Auth", description = "Auth Resource")
@Timed
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final GoogleAuthenticator gAuth;

    @Autowired
    Environment environment;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private MessageService messageService;

//    @Value("${recaptcha.secretQA}")
//    private String recaptchaSecretQA;

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    @Value("${recaptcha.url}")
    private String recaptchaServerURL;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    @Autowired
    private RestTemplate restTemplate;

//    private final GoogleAuthenticator gAuth;

    private static final String ROOT;
    private static final String PROJECT_NAME;
    private static final String FOLDER_UPLOAD;

    static {
        ROOT = System.getProperty("catalina.base");
        PROJECT_NAME = "logs/update-share/nlSystemSalesAPi";
        FOLDER_UPLOAD = "upload";
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "login", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success")
    public ResponseMessage<OauthToken> login(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest) throws IOException {

//    if(loginRequest.getSiteKey() != null){
//      if(!verifyReCAPTCHA(loginRequest.getSiteKey())){
//        return ResponseMessageUtils.makeResponse(true, messageService.message("Invalid Re-Captcha", false));
//      }
//    }else {
//      return ResponseMessageUtils.makeResponse(true, messageService.message("Re-Captcha Required", false));
//    }

        String deviceIds = GeneratePasswordPDF.generatePasswordPDF();

        String referer = httpServletRequest.getRemoteAddr();
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String deviceId = deviceIds;
        String deviceName = loginRequest.getDeviceName();

        CheckUserLogin checkUserLogin = new CheckUserLogin();
        String hostName = Inet4Address.getLocalHost().getHostName();
        String hostAddress = httpServletRequest.getRemoteAddr();
        checkUserLogin.setHostName(hostName);
        checkUserLogin.setHostAddress(hostAddress);

        if(authMapper.checkIpAddress(hostName, hostAddress) > 0){
            authMapper.updateCheckUserLogin(hostName, hostAddress);
        }else {
            authMapper.insertCheckUserLogin(checkUserLogin);
        }

        Long countNumberOfLogin = authMapper.countNumberOfLoginByIpAddress(hostName, hostAddress);
//    if(countNumberOfLogin >= 3){
//
////      String gRecahtchaResponse = httpServletRequest.getParameter("g-recaptcha-response");
////      verifyReCAPTCHA(gRecahtchaResponse);
//      return ResponseMessageUtils.makeResponse(true, messageService.message(countNumberOfLogin,"Please to verify Rechaptcha", false));
//    }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String clientSecret = "#JTIPY092312MT";
        //int tokenValidate   = 10;
        int tokenValidate   = 60 * 60 * 24 * 30; // 30 Days
        int refreshTokenValidate   = 60 * 60 * 24 * 90; // 90 Days
//    int tokenValidate = 60 * 30; // 30 Minutes
//    int refreshTokenValidate = 60 * 30; // 30 Minutes
        // Check User
        if (!username.isEmpty() && !password.isEmpty() && !deviceName.isEmpty() && !deviceId.isEmpty()) {
            Long checkUser = authMapper.checkUserValid(username);
            if (checkUser > 0) {
                // Check Device Insert
                if (authMapper.insertClientId(deviceId, deviceName, clientSecret, tokenValidate, refreshTokenValidate)) {
                    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                    map.add("grant_type", "password");
                    map.add("client_id", deviceId);
                    map.add("client_secret", clientSecret);
                    map.add("username", username);
                    map.add("password", password);
                    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
                    String authUrl = environment.getProperty("api.authUrl");
                    ResponseMessage<OauthToken> oauthTokenResponseMessage = authService.responseToken(authUrl, restTemplate, request);
                    if (oauthTokenResponseMessage.getHeader().getStatusCode() == 200) {
                        // Login Success
                        AccessTokenRequest accessTokenRequest = new AccessTokenRequest();
                        boolean result = false;
                        // generate token
                        final String token = UUID.randomUUID().toString();
                        accessTokenRequest.setUsername(username);
                        accessTokenRequest.setAccessToken(oauthTokenResponseMessage.getBody().getAccessToken());
                        accessTokenRequest.setTokenType(oauthTokenResponseMessage.getBody().getTokenType());
                        accessTokenRequest.setRefreshToken(oauthTokenResponseMessage.getBody().getRefreshToken());
                        accessTokenRequest.setExpiresIn(oauthTokenResponseMessage.getBody().getExpiresIn());
                        accessTokenRequest.setScope(oauthTokenResponseMessage.getBody().getScope());
                        accessTokenRequest.setLoginVerifyToken(token);
                        // Check duplicate after insert to access token
                        if(authMapper.checkDuplicateUsername(username) > 0){
                            result = authMapper.updateAccessToken(accessTokenRequest);
                        } else {
                            result = authMapper.insertAccessToken(accessTokenRequest);
                        }
                        if(result){
                            // logged in
                            if(authMapper.checkUserLoginOrNot(username, token) != null && authMapper.checkUserLoginOrNot(username, token) == 1){
                                authMapper.clearRechaptcha(hostName, hostAddress);
                                return ResponseMessageUtils.makeResponse(true, messageService.message(1L, token, true, 1L));
                            } else {
                                //Never Login
                                return ResponseMessageUtils.makeResponse(true, messageService.message(0L, token, true, 0L));
                            }
                        } else {
                            return ResponseMessageUtils.makeResponse(true, messageService.message(countNumberOfLogin,"Invalid username and password", false));
                        }
                    } else {
                        return ResponseMessageUtils.makeResponse(true, messageService.message(countNumberOfLogin,"Invalid username and password", false));
                    }
                } else {
                    return ResponseMessageUtils.makeResponse(false, 400, "Bad Request", "Invalid Parameter");
                }
            } else {
                return ResponseMessageUtils.makeResponse(false, 401, "401", "Invalid username and password");
            }
        } else {
            return ResponseMessageUtils.makeResponse(false, 400, "Bad Request", "Invalid Parameter");
        }
    }

    private Boolean verifyReCAPTCHA(String gRecaptchaResponse) {

        String URL = "https://www.google.com/recaptcha/api/siteverify";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("secret", environment.getProperty("google.recaptcha"));
        map.add("response", gRecaptchaResponse);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        CaptchaResponse response = restTemplate.postForObject(recaptchaServerURL, request ,CaptchaResponse.class);

        return response.getSuccess();
    }

//    @PostMapping("/login-with-refresh-token")
//    @ApiOperation(value = "Login with refresh token", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success")
//    public ResponseMessage<OauthToken> loginWithRefreshToken(@RequestParam("refreshToken") String refreshToken, @RequestParam("deviceId") String deviceId, HttpServletRequest httpServletRequest) throws UnknownHostException {
//        if (!refreshToken.isEmpty() && !deviceId.isEmpty()) {
//            RestTemplate restTemplate = new RestTemplate();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//            map.add("grant_type", "refresh_token");
//            map.add("client_id", deviceId);
//            map.add("client_secret", "#EMRM056312MT");
//            map.add("refresh_token", refreshToken);
//            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//            String authUrl = environment.getProperty("api.authUrl");
//            return authService.responseToken( 2L,authUrl, restTemplate, request, httpServletRequest);
//        } else {
//            return ResponseMessageUtils.makeResponse(false, 400, "Bad Request", "Invalid Parameter");
//        }
//    }


    @SneakyThrows
    @PostMapping("/generate-qr-code/{username}")
    public ResponseMessage<BaseResult> generate(@PathVariable String username) {
        log.info("Starting QR code generation for username: {}", username);
        if (username == null || username.trim().isEmpty()) {
            log.error("Invalid username: {}", username);
            throw new IllegalArgumentException("Username cannot be empty");
        }

        String filePath = null;
        try {
            final GoogleAuthenticatorKey key = gAuth.createCredentials(username);
            log.info("Created Google Authenticator key for user: {}", username);
            String secretKey = generateSecretKey();
            String barCodeUrl = getGoogleAuthenticatorBarCode(secretKey, username, "UDAYA");
            log.info("Generated barcode URL: {}", barCodeUrl);

            String uuid = UUID.randomUUID().toString();
            filePath = ROOT + File.separator + PROJECT_NAME + File.separator + FOLDER_UPLOAD + File.separator + uuid + ".JPG";
            log.info("Attempting to save QR code to: {}", filePath);

            // Generate and save QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(barCodeUrl, BarcodeFormat.QR_CODE, 200, 200);
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            MatrixToImageWriter.writeToFile(bitMatrix, "JPG", file);
            log.info("Successfully saved QR code to: {}", filePath);

            String fileSignature = "/" + FOLDER_UPLOAD + "/" + uuid + ".JPG";
            log.info("Generated fileSignature: {}", fileSignature);

            // Update database
            try {
                authMapper.updateUser(fileSignature, username, secretKey);
                log.info("Updated user {} with fileSignature {}", username, fileSignature);
            } catch (Exception e) {
                log.error("Failed to update user {}: {}", username, e.getMessage(), e);
                throw new RuntimeException("Database update failed", e);
            }

            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", Collections.singletonList(fileSignature), true));
        } catch (WriterException e) {
            log.error("Failed to generate QR code: {}", e.getMessage(), e);
            throw new RuntimeException("QR code generation failed", e);
        } catch (IOException e) {
            log.error("Failed to save QR code to {}: {}", filePath, e.getMessage(), e);
            throw new RuntimeException("Failed to upload cache file", e);
        }
    }

    @PostMapping(value = "/validate/google-authenticator", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage<BaseResult> validateKey(@RequestBody ValidateCodeDto body) {
        if(authMapper.checkLoginVerifyToken(body.getUsername(), body.getLoginVerifyToken()) > 0){
            String checkVerify = environment.getProperty("google-authenticator");

            if (Objects.equals(checkVerify, "true")){
                String code = body.getCode();
                String secretKey = authMapper.getSecretKey(body.getUsername());
                if (code.equals(getTOTPCode(secretKey))) {
                    Filter filter = new Filter();
                    filter.setSearchText(body.getUsername());
                    List<AccessTokenResponse> accessTokenResponses = authMapper.getListAccessToken(filter);
                    authMapper.updateUserLoginAuth(body.getUsername());
                    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", accessTokenResponses, true));
                } else {
                    return ResponseMessageUtils.makeResponse(false, messageService.message("Invalid Code", false));
                }
            } else {
                Filter filter = new Filter();
                filter.setSearchText(body.getUsername());
                List<AccessTokenResponse> accessTokenResponses = authMapper.getListAccessToken(filter);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", accessTokenResponses, true));
            }
        }else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Token Invalid", false));
        }
    }

    @PostMapping("/logout")
    @ApiOperation(value = "Logout", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<String> loginWithRefreshToken(@RequestParam("deviceId") String deviceId) {
        if (UserAuthSession.getUserAuth() != null && deviceId != null && !deviceId.isEmpty()) {
            String tokenId = authMapper.checkUserLogout(UserAuthSession.getUserAuth().getUsername(), deviceId);
            if (tokenId != null && !tokenId.isEmpty()) {
                authMapper.clearUserToken(UserAuthSession.getUserAuth().getUsername(), deviceId, tokenId);
            }
            return ResponseMessageUtils.makeResponse(true, "Success");
        } else {
            return ResponseMessageUtils.makeResponse(false, 400, "Bad Request", "Invalid Parameter");
        }
    }

    @PostMapping("/checkToken")
    @ApiOperation(value = "Check Token", notes = "401: Unauthorized (Token Expired or Invalid); 200: Success", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<String> checkToken() {
        if (UserAuthSession.getUserAuth() != null) {
            return ResponseMessageUtils.makeResponse(true, "Token Valid");
        } else {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
    }

    public static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
        try {
            String url = "otpauth://totp/"
                    + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
            log.info("Generated barcode URL: {}", url);
            return url;
        } catch (UnsupportedEncodingException e) {
            log.error("Failed to encode barcode URL: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to encode barcode URL", e);
        }
    }

    public static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }
}

