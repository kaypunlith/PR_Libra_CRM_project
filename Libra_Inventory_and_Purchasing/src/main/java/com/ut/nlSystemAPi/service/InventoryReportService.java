package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface InventoryReportService {

    ResponseMessage<BaseResult> getListInventoryAdjustment(InventoryAdjustmentReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListInventoryAdjustmentByItem(InventoryAdjustmentReportByItemFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> listStockAvailableForSale(StockAvailableForSaleReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> listGlobalInventory(GlobalInventoryReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> listInventoryActivity(InventoryActivityReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> listProductAverageCost(ProductAverageCostReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> listProductPriceList(ProductPriceListReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> listValuation(ValuationReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> listPriceRequestTracking(PriceRequestTrackingReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> productExpiryDate(ProductExpiryDate filter, HttpServletRequest httpServletRequest) throws UnknownHostException;


}