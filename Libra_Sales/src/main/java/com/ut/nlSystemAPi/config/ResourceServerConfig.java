package com.ut.nlSystemAPi.config;

import com.ut.nlSystemAPi.exception.CustomAuthenticationEntryPoint;
import com.ut.nlSystemAPi.exception.CustomOAuth2AccessDeniedHandler;
import com.ut.nlSystemAPi.social.CustomOAuth2UserService;
import com.ut.nlSystemAPi.social.HttpCookieOAuth2AuthorizationRequestRepository;
import com.ut.nlSystemAPi.social.OAuth2AuthenticationFailureHandler;
import com.ut.nlSystemAPi.social.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  private static final String RESOURCE_ID = "ut-resource";

  @Autowired
  private CustomOAuth2UserService customOAuth2UserService;

  @Autowired
  private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

  @Autowired
  private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

  @Bean
  public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
    return new HttpCookieOAuth2AuthorizationRequestRepository();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources
      .resourceId(RESOURCE_ID).stateless(false)
      .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .anonymous().disable();

    // CORS
    http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);

    // Social
    http
      .oauth2Login()
      .authorizationEndpoint()
      .baseUri("/oauth2/authorize")
      .authorizationRequestRepository(cookieAuthorizationRequestRepository())
      .and()
      .redirectionEndpoint()
      .baseUri("/oauth2/callback/*")
      .and()
      .userInfoEndpoint()
      .userService(customOAuth2UserService)
      .and()
      .successHandler(oAuth2AuthenticationSuccessHandler)
      .failureHandler(oAuth2AuthenticationFailureHandler);

    // Role and permission
    http.authorizeRequests().antMatchers("/api-docs/**", "/dropdown/list-freedom-customer", "/dropdown/list-freedom-warehouse").permitAll();

    // Exception handling
    http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    http.exceptionHandling().accessDeniedHandler(new CustomOAuth2AccessDeniedHandler());
  }
}
