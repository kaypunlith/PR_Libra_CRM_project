package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ChartAccountFilter;
import com.ut.nlSystemAPi.model.filter.ChartAccountNetIncomeFilter;
import com.ut.nlSystemAPi.model.request.Login.AccountClosingDate.AccountClosingDateSettingRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface AccountClosingDateSettingService {

  ResponseMessage<BaseResult> getListChartOfAccount(ChartAccountFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(AccountClosingDateSettingRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getNetIncome(ChartAccountNetIncomeFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

}