package com.ut.nlSystemAPi.exception;

import com.ut.nlSystemAPi.helper.TelegramBotService;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private TelegramBotService telegramBotService;

  @ExceptionHandler(Exception.class)
  public final ResponseMessage handleAllExceptions(Exception ex, WebRequest request) {
    String url = resolveUrl(request);
    telegramBotService.sendErrorMessageOnce(resolveHttpRequest(request), "Global Exception Detected", ex.getMessage(), url, null);
    return ResponseMessageUtils.makeResponse(false, 200, "", ex.getMessage());
  }

  @ExceptionHandler(DataAccessException.class)
  public final ResponseMessage handleDatabaseExceptions(DataAccessException ex, WebRequest request) {
    String sqlError = ex.getMostSpecificCause() == null ? ex.getMessage() : ex.getMostSpecificCause().getMessage();
    String url = resolveUrl(request);
    telegramBotService.sendErrorMessageOnce(resolveHttpRequest(request), "Database/SQL Error Detected", sqlError, url, null);

    return ResponseMessageUtils.makeResponse(false, 200, "", "Database Error: " + sqlError);
  }

  private HttpServletRequest resolveHttpRequest(WebRequest request) {
    if (request instanceof ServletWebRequest) {
      return ((ServletWebRequest) request).getRequest();
    }
    return null;
  }

  private String resolveUrl(WebRequest request) {
    if (request instanceof ServletWebRequest) {
      return ((ServletWebRequest) request).getRequest().getRequestURI();
    }
    String description = request.getDescription(false);
    if (description.startsWith("uri=")) {
      return description.substring("uri=".length());
    }
    return description;
  }
}
