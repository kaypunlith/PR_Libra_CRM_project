package com.ut.nlSystemAPi.config;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseHeader;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class RollbackAwareTransactionInterceptor implements MethodInterceptor {

  private final PlatformTransactionManager transactionManager;

  public RollbackAwareTransactionInterceptor(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    Method method = invocation.getMethod();
    if (isObjectMethod(method) || !Modifier.isPublic(method.getModifiers())) {
      return invocation.proceed();
    }

    TransactionStatus status = transactionManager.getTransaction(createDefinition(method));
    try {
      Object result = invocation.proceed();
      if (shouldRollback(result)) {
        status.setRollbackOnly();
      }
      transactionManager.commit(status);
      return result;
    } catch (Throwable error) {
      if (!status.isCompleted()) {
        transactionManager.rollback(status);
      }
      throw error;
    }
  }

  private DefaultTransactionDefinition createDefinition(Method method) {
    DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
    definition.setName(method.getDeclaringClass().getSimpleName() + "." + method.getName());
    definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    return definition;
  }

  private boolean shouldRollback(Object result) {
    if (result instanceof ResponseMessage) {
      ResponseMessage response = (ResponseMessage) result;
      ResponseHeader header = response.getHeader();
      if (Boolean.FALSE.equals(header.getResult())) {
        return true;
      }
      if (header.getStatusCode() != null && header.getStatusCode() >= 400) {
        return true;
      }
      return shouldRollback(response.getBody());
    }

    if (result instanceof BaseResult) {
      BaseResult baseResult = (BaseResult) result;
      return Boolean.FALSE.equals(baseResult.getStatus());
    }

    if (result instanceof Boolean) {
      return Boolean.FALSE.equals(result);
    }

    return false;
  }

  private boolean isObjectMethod(Method method) {
    return method.getDeclaringClass() == Object.class;
  }
}
