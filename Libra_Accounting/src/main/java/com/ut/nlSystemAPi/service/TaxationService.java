package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.TaxationFilter;
import com.ut.nlSystemAPi.model.request.Login.Taxation.TaxationExchangeRateRequest;
import com.ut.nlSystemAPi.model.request.Login.Taxation.TaxationRequest;
import com.ut.nlSystemAPi.model.request.Login.Taxation.TaxationUpdateRequest;
import com.ut.nlSystemAPi.model.response.Dropdown.CompanyChartAccountResponse;
import com.ut.nlSystemAPi.model.response.Taxation.TaxationResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.List;

public interface TaxationService {

    ResponseMessage<BaseResult> getList(TaxationFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> insert(TaxationRequest taxationRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> update(TaxationUpdateRequest taxationUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> updateExchangeRate(TaxationExchangeRateRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException;

}
