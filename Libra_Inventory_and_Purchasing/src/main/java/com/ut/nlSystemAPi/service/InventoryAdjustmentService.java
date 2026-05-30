package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.BaseFilter;
import com.ut.nlSystemAPi.model.filter.InventoryAdjustmentFilter;
import com.ut.nlSystemAPi.model.filter.QtyOnHandFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.request.Login.InventoryAdjustment.InventoryAdjustmentRequest;
import com.ut.nlSystemAPi.model.request.Login.InventoryAdjustment.InventoryAdjustmentUpdateRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface InventoryAdjustmentService {

  ResponseMessage<BaseResult> getList(InventoryAdjustmentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(InventoryAdjustmentRequest inventoryAdjustmentRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(InventoryAdjustmentUpdateRequest inventoryAdjustmentUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> updateStatus(BaseFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> findQtyOnHand(QtyOnHandFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> listProductInStock(Long warehouseId, HttpServletRequest httpServletRequest) throws UnknownHostException;

}
