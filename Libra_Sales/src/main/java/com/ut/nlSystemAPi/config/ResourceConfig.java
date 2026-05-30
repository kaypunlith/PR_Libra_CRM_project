package com.ut.nlSystemAPi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

  String apiPathUpload = System.getProperty("catalina.base");

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
      .addResourceHandler("/upload/**")
      .addResourceLocations("file:" + apiPathUpload + "/logs/update-share/nlSystemAPi/upload/");
  }

}
