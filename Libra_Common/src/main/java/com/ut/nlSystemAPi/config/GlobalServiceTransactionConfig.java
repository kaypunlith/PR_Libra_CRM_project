package com.ut.nlSystemAPi.config;

import com.ut.nlSystemAPi.helper.TelegramBotService;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ConditionalOnProperty(prefix = "global.transaction", name = "enabled", havingValue = "true", matchIfMissing = true)
public class GlobalServiceTransactionConfig {

  @Bean(name = "globalServiceTransactionInterceptor")
  public MethodInterceptor globalServiceTransactionInterceptor(
      @Qualifier("primaryTransactionManager") PlatformTransactionManager transactionManager) {
    return new RollbackAwareTransactionInterceptor(transactionManager);
  }

  @Bean(name = "activityLogTelegramErrorInterceptor")
  public MethodInterceptor activityLogTelegramErrorInterceptor(TelegramBotService telegramBotService) {
    return new ActivityLogTelegramErrorInterceptor(telegramBotService);
  }

  @Bean
  public static BeanNameAutoProxyCreator globalServiceTransactionProxyCreator() {
    BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
    creator.setBeanNames("*ServiceImpl");
    creator.setInterceptorNames("activityLogTelegramErrorInterceptor", "globalServiceTransactionInterceptor");
    creator.setProxyTargetClass(true);
    creator.setOrder(Ordered.LOWEST_PRECEDENCE - 100);
    return creator;
  }
}
