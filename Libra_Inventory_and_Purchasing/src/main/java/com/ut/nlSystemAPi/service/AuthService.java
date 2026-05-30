package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.OauthToken;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface AuthService {

//  ResponseMessage<OauthToken> responseToken(Long statusLogin, String authUrl, RestTemplate restTemplate, HttpEntity<MultiValueMap<String, String>> request, HttpServletRequest httpServletRequest, String username, String password) throws UnknownHostException;

  ResponseMessage<OauthToken> responseToken(String authUrl, RestTemplate restTemplate, HttpEntity<MultiValueMap<String, String>> request, Long userId);

}