package com.ut.nlSystemAPi.service;


import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.InventorySampleFilter;
import com.ut.nlSystemAPi.model.filter.InventorySampleProductFilter;
import com.ut.nlSystemAPi.model.request.Login.InventorySample.InventorySampleRequest;
import com.ut.nlSystemAPi.model.request.Login.InventorySample.InventorySampleRequestUpdate;
import com.ut.nlSystemAPi.model.request.Login.InventorySample.InventorySampleStatusUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface InventorySampleService {

    ResponseMessage<BaseResult> getList(InventorySampleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListProductDetail(InventorySampleProductFilter filter,HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> insert(InventorySampleRequest inventorySampleRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> update(InventorySampleRequestUpdate inventorySampleRequestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> updateStatus(InventorySampleStatusUpdateRequest statusUpdateRequest, HttpServletRequest httpServletRequest) throws UnknownHostException;

}
