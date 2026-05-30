package com.ut.nlSystemAPi;

import com.ut.nlSystemAPi.config.GlobalConfigLoader;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.ut.nlSystemAPi.mapper.primary")
public class Application extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(Application.class);
    application.setDefaultProperties(GlobalConfigLoader.loadDefaultProperties());
    application.run(args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(Application.class)
        .properties(GlobalConfigLoader.loadDefaultProperties());
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
