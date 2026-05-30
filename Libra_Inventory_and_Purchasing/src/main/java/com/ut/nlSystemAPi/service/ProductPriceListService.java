package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ProductPriceListFilter;
import com.ut.nlSystemAPi.model.filter.ProductPriceListProductDetailFilter;
import com.ut.nlSystemAPi.model.request.Login.ProductPriceList.ProductListApproveStatus;
import com.ut.nlSystemAPi.model.request.Login.ProductPriceList.ProductPriceListRequest;
import com.ut.nlSystemAPi.model.request.Login.ProductPriceList.ProductPriceListUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface ProductPriceListService {
 ResponseMessage<BaseResult> getList(ProductPriceListFilter productPriceListFilter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(ProductPriceListRequest productPriceListRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> update(ProductPriceListUpdateRequest productPriceListUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> updateApproveStatus(ProductListApproveStatus productListApproveStatus, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> updateIsClose(ProductListApproveStatus productListApproveStatus, HttpServletRequest httpServletRequest) throws UnknownHostException;

 ResponseMessage<BaseResult> getListProductDetails(ProductPriceListProductDetailFilter productPriceListFilter, HttpServletRequest httpServletRequest) throws UnknownHostException;
}