package com.ut.nlSystemAPi.service;


import com.ut.nlSystemAPi.helper.valuation.InventoryValuationGateway;
import com.ut.nlSystemAPi.helper.valuation.InventoryValuationRunner;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.InventoryReportMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.model.response.ExpiryDateReport.ExpiryDateReportResponse;
import com.ut.nlSystemAPi.model.response.GlobalInventory.GlobalInventoryReportDetailResponse;
import com.ut.nlSystemAPi.model.response.GlobalInventory.GlobalInventoryReportResponse;
import com.ut.nlSystemAPi.model.response.InventoryActivity.InventoryActivityReportDetailResponse;
import com.ut.nlSystemAPi.model.response.InventoryActivity.InventoryActivityReportResponse;
import com.ut.nlSystemAPi.model.response.InventoryAdjustmentReport.InventoryAdjustmentReportByItemDetailResponse;
import com.ut.nlSystemAPi.model.response.InventoryAdjustmentReport.InventoryAdjustmentReportByItemResponse;
import com.ut.nlSystemAPi.model.response.InventoryAdjustmentReport.InventoryAdjustmentReportResponse;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportPriceRequestResponse;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportQuotationResponse;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportResponse;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportSaleOrderResponse;
import com.ut.nlSystemAPi.model.response.ProductAverageCost.ProductAverageCostReportResponse;
import com.ut.nlSystemAPi.model.response.ProductPriceList.ProductPriceListDetailReportResponse;
import com.ut.nlSystemAPi.model.response.ProductPriceList.ProductPriceListReportResponse;
import com.ut.nlSystemAPi.model.response.StockAvailableForSale.StockAvailableForSaleReportDetailResponse;
import com.ut.nlSystemAPi.model.response.StockAvailableForSale.StockAvailableForSaleReportResponse;
import com.ut.nlSystemAPi.model.response.Valuation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.*;

@Service
public class InventoryReportServiceImpl implements InventoryReportService {
    @Autowired
    private InventoryReportMapper inventoryReportMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private InventoryValuationGateway inventoryValuationGateway;

    @Override
    public ResponseMessage<BaseResult> getListInventoryAdjustment(InventoryAdjustmentReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Inventory Adjustment)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            InventoryValuationRunner.runValuation(inventoryValuationGateway);
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(inventoryReportMapper.countListInventoryAdjustment(filter, userId));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<InventoryAdjustmentReportResponse> inventoryAdjustmentReportResponses = inventoryReportMapper.getListInventoryAdjustment(filter, userId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/adjustment/list", null, null, "Inventory Adjustment", "Report (Inventory Adjustment)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", inventoryAdjustmentReportResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/adjustment/list", line, error.toString(), "Inventory Adjustment", "Report (Inventory Adjustment)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListInventoryAdjustmentByItem(InventoryAdjustmentReportByItemFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Inventory Adjustment)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            InventoryValuationRunner.runValuation(inventoryValuationGateway);
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());


            List<InventoryAdjustmentReportByItemResponse> response = null;
            // Item Summary
            if (filter.getView() == 2) {
                pagination.setTotal(inventoryReportMapper.countListInventoryAdjustmentByItemItemSummary(filter, userId));
                response = inventoryReportMapper.getListInventoryAdjustmentByItemItemSummary(filter, userId);
                if (!response.isEmpty()) {
                    // Set details for paginated response
                    for (int i = 0; i < response.size(); i++) {
                        List<InventoryAdjustmentReportByItemDetailResponse> detail = inventoryReportMapper.getListInventoryAdjustmentByItemItemSummaryDetail(response.get(i).getParentId(), userId);
                        response.get(i).setDetails(detail);
                    }
                    Double grandTotal = inventoryReportMapper.sumGrandTotalAdjustmentByItem(filter, userId);
                    response.get(0).setGrandTotal(grandTotal);
                }

                // Item Detail
            } else if (filter.getView() == 3) {
                pagination.setTotal(inventoryReportMapper.countListInventoryAdjustmentByItemDetail(filter, userId));
                response = inventoryReportMapper.getListInventoryAdjustmentByItemDetail(filter, userId);

                if (response != null && !response.isEmpty()) {
                    for (int i = 0; i < response.size(); i++) {
                        InventoryAdjustmentReportByItemResponse item = response.get(i);
                        if (item == null || item.getProductId() == null) {
                            continue;
                        }
                        List<InventoryAdjustmentReportByItemDetailResponse> detail = inventoryReportMapper.getListInventoryAdjustmentByItemDetailDetail(item.getProductId(), userId);
                        if (detail == null) {
                            detail = new ArrayList<>();
                        }
                        item.setDetails(detail);
                        for (int j = 0; j < detail.size(); j++) {
                            InventoryAdjustmentReportByItemDetailResponse detailItem = detail.get(j);
                            if (detailItem == null || detailItem.getQty() == null || detailItem.getUnitCost() == null) {
                                assert detailItem != null;
                                detailItem.setTotalCost(0.0);
                            } else {
                                detailItem.setTotalCost(detailItem.getQty() * detailItem.getUnitCost());
                            }
                        }
                    }
                    Double grandTotal = inventoryReportMapper.sumGrandTotalAdjustmentByItem(filter, userId);
                    response.get(0).setGrandTotal(grandTotal);
                } else {
                    response = new ArrayList<>();
                }
            } else if (filter.getView() == 1) {
                pagination.setTotal(inventoryReportMapper.countListInventoryAdjustmentByItemParentSummary(filter, userId));
                response = inventoryReportMapper.getListInventoryAdjustmentByItemParentSummary(filter, userId);

                if (response != null && !response.isEmpty()) {
                    Double grandTotal = inventoryReportMapper.sumGrandTotalAdjustmentByItem(filter, userId);
                    response.get(0).setGrandTotal(grandTotal);
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/adjustment-by-item/list", null, null, "Inventory Adjustment By Item", "Report (Inventory Adjustment)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", response, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/adjustment-by-item/list", line, error.toString(), "Inventory Adjustment By Item", "Report (Inventory Adjustment)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listStockAvailableForSale(StockAvailableForSaleReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Stock Available For Sales)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(Long.valueOf(inventoryReportMapper.countListStockAvailableForSale(filter).size()));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<StockAvailableForSaleReportResponse> responses = inventoryReportMapper.getListStockAvailableForSale(filter);

            //! Set The Table Name
            String tableName;
            if (filter.getWarehouseId() != null) {
                tableName = filter.getWarehouseId()+"_group_total_details";
            } else {
                tableName = "inventory_totals";
            }

            if (responses.size() > 0) {
                Double grandTotal = 0D;
                for (int i = 0; i < responses.size(); i++) {

                    Long parentId = responses.get(i).getParentId();

                    List<StockAvailableForSaleReportDetailResponse> details = inventoryReportMapper.getListStockAvailableForSaleDetail(filter, tableName, parentId);
                    responses.get(i).setProducts(details);
                    //! Sum Total
                    if(!details.isEmpty()){
                        Double total = 0D;
                        for (int j = 0; j < details.size(); j++) {
                            total += details.get(j).getTotalQtyAvailable();
                        }
                        responses.get(i).setTotal(total);
                    }
                }
                responses.get(0).setGrandTotal(inventoryReportMapper.sumGrandTotalStockAvailableForSale(filter, tableName));
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/adjustment-by-item/list", null, null, "Stock Available For Sales", "Report (Stock Available For Sales)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/adjustment-by-item/list", line, error.toString(), "Stock Available For Sales", "Report (Stock Available For Sales)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listGlobalInventory(GlobalInventoryReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(inventoryReportMapper.countListGlobalInventory(filter) + 1);
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<GlobalInventoryReportResponse> responses = new ArrayList<>();

            GlobalInventoryReportResponse noParentResponse = new GlobalInventoryReportResponse();
            noParentResponse.setParentId(null);
            noParentResponse.setParentName("No Parent");
            List<GlobalInventoryReportDetailResponse> noParentDetails = inventoryReportMapper.getListGlobalInventoryDetailNoParent(filter);
            noParentResponse.setDetails(noParentDetails);

            Double noParentSubTotal = 0D;
            if (noParentDetails != null && !noParentDetails.isEmpty()) {
                for (GlobalInventoryReportDetailResponse detail : noParentDetails) {
                    detail.setTotalQtyAvailable(detail.getEndingQty() - detail.getQtyOrder());
                    noParentSubTotal += detail.getTotalQtyAvailable();
                }
            }
            noParentResponse.setSubTotal(noParentSubTotal);
            responses.add(noParentResponse);

            List<GlobalInventoryReportResponse> parentResponses = inventoryReportMapper.getListGlobalInventory(filter);
            if (parentResponses != null && !parentResponses.isEmpty()) {
                for (GlobalInventoryReportResponse parent : parentResponses) {
                    List<GlobalInventoryReportDetailResponse> details = inventoryReportMapper.getListGlobalInventoryDetail(filter, parent.getParentId());
                    parent.setDetails(details);

                    Double subTotal = 0D;
                    if (details != null && !details.isEmpty()) {
                        for (GlobalInventoryReportDetailResponse detail : details) {
                            detail.setTotalQtyAvailable(detail.getEndingQty() - detail.getQtyOrder());
                            subTotal += detail.getTotalQtyAvailable();
                        }
                    }
                    parent.setSubTotal(subTotal);
                    responses.add(parent);
                }
            }

            responses.get(0).setGrandTotal(inventoryReportMapper.sumGrandTotalGlobalInventory(filter));

            // Log system activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/global-inventory/list", null, null, "Global Inventory", "Report (Global Inventory)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            // Log error
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/global-inventory/list", line, error.toString(), "Global Inventory", "Report (Global Inventory)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listInventoryActivity(InventoryActivityReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;

        try {
            Long userId = userService.getUserAuth().getId();
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<InventoryActivityReportResponse> responses = new ArrayList<>();

            if (filter.getView() == 1) {
                if (filter.getType() == 1) {
                    List<InventoryActivityReportResponse> parentResponses = inventoryReportMapper.getListInventoryActivityParentSummary(filter);
                    List<InventoryActivityReportResponse> noParentDetails = inventoryReportMapper.getListInventoryActivityParentSummaryNoParent(filter);
                    if (noParentDetails.get(0).getQty() != null) {
                        responses.addAll(noParentDetails);
                    }
                    if (!parentResponses.isEmpty()) {
                        responses.addAll(parentResponses);
                    }
                    pagination.setTotal(inventoryReportMapper.countListInventoryActivityParentSummary(filter));
                }
            }



            else if (filter.getView() == 2) {
                if (filter.getType() == 1) {
                    pagination.setTotal(inventoryReportMapper.countListInventoryActivityItemSummary(filter));
                    responses = inventoryReportMapper.getListInventoryActivityItemSummary(filter);
                    if (!responses.isEmpty()) {
                        for (InventoryActivityReportResponse response : responses) {
                            List<InventoryActivityReportDetailResponse> detail = inventoryReportMapper.getListInventoryActivityItemSummaryDetail(filter, response.getItemId());
                            response.setDetails(detail);
                            detail.get(0).setSubTotalQty(inventoryReportMapper.sumSubTotal(filter, response.getItemId()));
                        }
                    }
                }
            }



            else if (filter.getView() == 3) {
                if (filter.getType() == 1) {
                    pagination.setTotal(inventoryReportMapper.countListInventoryActivityItemActivitySummary(filter));
                    responses = inventoryReportMapper.getListInventoryActivityItemActivitySummary(filter);
                    if (!responses.isEmpty()) {
                        for (InventoryActivityReportResponse response : responses) {
                            Long parentId = response.getItemId();
                            List<InventoryActivityReportDetailResponse> detail = inventoryReportMapper.getListInventoryActivityItemActivitySummaryDetail(filter, parentId);
                            response.setDetails(detail);
                        }
                    }
                }
            }



            else if (filter.getView() == 4) {
                if (filter.getType() == 1) {
                    pagination.setTotal(inventoryReportMapper.countListInventoryActivityItemActivityDetail(filter));
                    responses = inventoryReportMapper.getListInventoryActivityItemActivityDetail(filter);
                    if (!responses.isEmpty()) {
                        for (InventoryActivityReportResponse response : responses) {
                            Long parentId = response.getItemId();
                            List<InventoryActivityReportDetailResponse> details = inventoryReportMapper.getListInventoryActivityItemActivityDetailDetail(filter, parentId);
                            if (details != null && !details.isEmpty()) {
                                for (InventoryActivityReportDetailResponse detail : details) {
                                    detail.setEnding(detail.getSale() + detail.getAdjustment() + detail.getPo() + detail.getBr() + detail.getCm() + detail.getTransfer());
                                }
                                response.setDetails(details);
                            }
                        }
                    }
                }
            }



            else if (filter.getView() == 5) {
                if (filter.getType() == 1) {
                    pagination.setTotal(inventoryReportMapper.countListInventoryActivityItemDetail(filter));
                    responses = inventoryReportMapper.getListInventoryActivityItemDetail(filter);
                    if (!responses.isEmpty()) {
                        for (InventoryActivityReportResponse respons : responses) {
                            List<InventoryActivityReportDetailResponse> detail = inventoryReportMapper.getListInventoryActivityItemDetailDetail(filter, respons.getItemId());
                            respons.setDetails(detail);
                            if (detail != null && !detail.isEmpty()) {
                                for (InventoryActivityReportDetailResponse inventoryActivityReportDetailResponse : detail) {
                                    if(inventoryActivityReportDetailResponse.getQty() == null) {
                                        inventoryActivityReportDetailResponse.setUnitCost(0D);
                                    }
                                    if(inventoryActivityReportDetailResponse.getUnitCost() == null) {
                                        inventoryActivityReportDetailResponse.setUnitCost(0D);
                                    }
                                    if (inventoryActivityReportDetailResponse.getType().equals("Sale") || inventoryActivityReportDetailResponse.getType().equals("Invoice")) {
                                        inventoryActivityReportDetailResponse.setAmount(inventoryActivityReportDetailResponse.getUnitCost() * inventoryActivityReportDetailResponse.getQty() * -1);
                                    } else {
                                        inventoryActivityReportDetailResponse.setAmount(inventoryActivityReportDetailResponse.getUnitCost() * inventoryActivityReportDetailResponse.getQty());
                                    }
                                }
                                detail.get(0).setSubTotalQty(inventoryReportMapper.sumSubTotal(filter, respons.getItemId()));
                            }
                        }
                    }
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/activity/list", null, null, "Activity", "Activity (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/activity/list", line, error.toString(), "Activity", "Activity (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listProductAverageCost(ProductAverageCostReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Report (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(inventoryReportMapper.countListProductAverageCost(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ProductAverageCostReportResponse> responses = inventoryReportMapper.getListProductAverageCost(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/adjustment-by-item/list", null, null, "adjustment-by-item", "adjustment-by-item(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/adjustment-by-item/list", line, error.toString(), "adjustment-by-item", "adjustment-by-item(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listProductPriceList(ProductPriceListReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Report (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(inventoryReportMapper.countListProductPriceList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ProductPriceListReportResponse> responses = inventoryReportMapper.getListProductPriceList(filter, userId);

            if (!responses.isEmpty()) {
                for (int i = 0; i < responses.size(); i++) {
                    List<ProductPriceListDetailReportResponse> details = inventoryReportMapper.getListProductPriceListDetail(responses.get(i).getUnitCost(), responses.get(i).getProductId(), responses.get(i).getUomId());
                    responses.get(i).setDetails(details);
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/adjustment-by-item/list", null, null, "adjustment-by-item", "adjustment-by-item(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/adjustment-by-item/list", line, error.toString(), "adjustment-by-item", "adjustment-by-item(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listValuation(ValuationReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Inventory Valuation)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            InventoryValuationRunner.runValuation(inventoryValuationGateway);

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ValuationReportResponse> responses;

            if (filter.getView() == 1) {
                pagination.setTotal(inventoryReportMapper.countListValuationSummary(filter));
                responses = inventoryReportMapper.getListValuationSummary(filter);
                //! Set Grand Total
                if (responses!= null && !responses.isEmpty()) {
                    Double subTotalAssetValue = 0D;
                    for (int i = 0; i < responses.size(); i++) {
                        subTotalAssetValue += responses.get(i).getAssetValue();
                        responses.get(i).setTotalAsset(responses.get(i).getAssetValue() / subTotalAssetValue * 100);
                    }
                }
            } else {
                pagination.setTotal(inventoryReportMapper.countListValuationDetail(filter));
                responses = inventoryReportMapper.getListValuationDetail(filter);

                if (responses != null && !responses.isEmpty()) {
                    for (int i = 0; i < responses.size(); i++) {
                        List<ValuationReportDetailResponse> details = inventoryReportMapper.getListValuationDetailDetail(responses.get(i).getProductId());
                        responses.get(i).setDetails(details);
                    }
                }
                assert responses != null;
                if (!responses.isEmpty()) {
                    responses.get(0).setGrandTotalQty(inventoryReportMapper.sumTotalQtyValuation(filter));
                    responses.get(0).setGrandTotalAssetValue(inventoryReportMapper.sumTotalAssetValueValuation(filter));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/valuation/list", null, null, "Inventory Valuation", "Report (Inventory Valuation)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/valuation/list", line, error.toString(), "Inventory Valuation", "Report (Inventory Valuation)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listPriceRequestTracking(PriceRequestTrackingReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Price Request Tracking)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(inventoryReportMapper.countListPriceRequestTracking(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<PriceRequestTrackingReportResponse> responses = inventoryReportMapper.getListPriceRequestTracking(filter);

            if (responses!= null && !responses.isEmpty()) {
                for (int i = 0; i < responses.size(); i++) {
                    List<PriceRequestTrackingReportPriceRequestResponse> priceRequests = inventoryReportMapper.getListPriceRequestTrackingPriceRequest(filter, responses.get(i).getCreatedBy());
                    responses.get(i).setPriceRequest(priceRequests);

                    List<PriceRequestTrackingReportSaleOrderResponse> saleOrders = inventoryReportMapper.getListPriceRequestTrackingSaleOrder(filter, responses.get(i).getCreatedBy());
                    responses.get(i).setSaleOrder(saleOrders);

                    List<PriceRequestTrackingReportQuotationResponse> quotations = inventoryReportMapper.getListPriceRequestTrackingQuotation(filter, responses.get(i).getCreatedBy());
                    responses.get(i).setQuotation(quotations);
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request-tracking/list", null, null, "Price Request Tracking", "Report (Price Request Tracking)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request-tracking/list", line, error.toString(), "Price Request Tracking", "Report (Price Request Tracking)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> productExpiryDate(ProductExpiryDate filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Report (Inventory Valuation)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(inventoryReportMapper.countProductExpiryDate(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ExpiryDateReportResponse> responses = inventoryReportMapper.getProductExpiryDate(filter, userId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/valuation/list", null, null, "Inventory Valuation", "Report (Inventory Valuation)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/valuation/list", line, error.toString(), "Inventory Valuation", "Report (Inventory Valuation)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
