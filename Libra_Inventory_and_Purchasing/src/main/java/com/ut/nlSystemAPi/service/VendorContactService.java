package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.VendorContactFilter;
import com.ut.nlSystemAPi.model.request.Login.VendorContact.VendorContactRequest;
import com.ut.nlSystemAPi.model.request.Login.VendorContact.VendorContactUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface VendorContactService {

  ResponseMessage<BaseResult> getList(VendorContactFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(VendorContactRequest vendorContactRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> update(VendorContactUpdateRequest vendorContactUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

}