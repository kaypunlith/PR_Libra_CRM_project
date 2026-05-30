package com.ut.nlSystemAPi.model;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.response.DateResponse;

@Service
public class MessageService {

  public BaseResult message(String text, List array, Boolean status) {
    BaseResult baseResult = new BaseResult();
    baseResult.setMessage(text);
    baseResult.setStatus(status);
    baseResult.setData(array);
    return baseResult;
  }

  public BaseResult message(String text, Boolean status) {
    BaseResult baseResult = new BaseResult();
    baseResult.setMessage(text);
    baseResult.setStatus(status);
    return baseResult;
  }

  public BaseResult messageResponseDateReport(String text, DateResponse date, List array, Boolean status) {
    BaseResult baseResult = new BaseResult();
    baseResult.setMessage(text);
    baseResult.setDate(date);
    baseResult.setStatus(status);
    baseResult.setData(array);
    return baseResult;
  }

  public BaseResult messageResponseDate(String text, String date, List array, Boolean status) {
    BaseResult baseResult = new BaseResult();
    baseResult.setMessage(text);
    baseResult.setDates(date);
    baseResult.setStatus(status);
    baseResult.setData(array);
    return baseResult;
  }

  public BaseResult message(String text, List array, Pagination pagination, Boolean status) {
    BaseResult baseResult = new BaseResult();
    baseResult.setMessage(text);
    baseResult.setStatus(status);
    baseResult.setData(array);
    baseResult.setPagination(pagination);
    return baseResult;
  }

  public BaseResult message(String text, Pagination pagination, List array,  Boolean status) {
    BaseResult baseResult = new BaseResult();
    baseResult.setMessage(text);
    baseResult.setStatus(status);
    baseResult.setData(array);
    baseResult.setPagination(pagination);
    return baseResult;
  }

}