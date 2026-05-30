package com.ut.nlSystemAPi;

import com.ut.nlSystemAPi.config.GlobalConfigLoader;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.ut.nlSystemAPi.mapper.primary")
@MapperScan("com.ut.nlSystemAPi.mapper.utscrum")
@SpringBootApplication
@EntityScan(basePackages = "com.ut.nlSystemAPi.mapper.primary")
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

}
