package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.PurchasingReportMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.model.response.PurchasingReport.*;
import com.ut.nlSystemAPi.model.response.StockAvailableForSale.StockAvailableForSaleReportDetailResponse;
import com.ut.nlSystemAPi.model.response.TransferOrderReport.TransferOrderReportByItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class PurchasingReportServiceImpl implements PurchasingReportService {
    @Autowired
    private PurchasingReportMapper transferOrderReportMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private PurchasingReportMapper purchasingReportMapper;

    @Override
    public ResponseMessage<BaseResult> getList(PurchasingReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            filter.setUserId(userId);

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(purchasingReportMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<PurchaseBillBarcodeResponse> purchaseBillBarcodeResponses = purchasingReportMapper.getList(filter);

            if(!purchaseBillBarcodeResponses.isEmpty()) {
                   for(int i = 0; i < purchaseBillBarcodeResponses.size(); i++) {
                       Long purchaseBillId = purchaseBillBarcodeResponses.get(i).getId();
                       List<PurchaseBillBarcodeDetailResponse> purchaseBillBarcodeDetailResponses = purchasingReportMapper.purchaseBillBarcodeDetail(purchaseBillId);
                       purchaseBillBarcodeResponses.get(i).setPurchaseBillBarcodeDetailResponseList(purchaseBillBarcodeDetailResponses);
                   }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/TransferOrderReport/list", null, null, "transferOrderReport", "transferOrderReport(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", purchaseBillBarcodeResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/transferOrderReport/list", line, error.toString(), "transferOrderReport", "transferOrderReport(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getInvoicePurchaseBill(InvoicePurchaseBillReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            filter.setUserId(userId);

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(purchasingReportMapper.countListInvoicePurchaseBill(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<InvoicePurchaseBillResponse> invoicePurchaseBillResponses = purchasingReportMapper.getListInvoicePurchaseBill(filter);
            Double totalSumSubTotal = 0D;
            Double totalSumTotalAmount = 0D;
            Double totalSumTotalVat = 0D;
            Double totalSumBalance = 0D;

            if (!invoicePurchaseBillResponses.isEmpty()) {
                for (int i = 0; i < invoicePurchaseBillResponses.size(); i++) {
                    totalSumSubTotal += invoicePurchaseBillResponses.get(i).getSubTotal();
                    totalSumTotalAmount += invoicePurchaseBillResponses.get(i).getTotalAmount();
                    totalSumTotalVat += invoicePurchaseBillResponses.get(i).getTotalVat();
                    totalSumBalance += invoicePurchaseBillResponses.get(i).getBalance();
                }
                invoicePurchaseBillResponses.get(0).setTotalSumSubTotal(totalSumSubTotal);
                invoicePurchaseBillResponses.get(0).setTotalSumTotalAmount(totalSumTotalAmount);
                invoicePurchaseBillResponses.get(0).setTotalSumTotalVat(totalSumTotalVat);
                invoicePurchaseBillResponses.get(0).setTotalSumBalance(totalSumBalance);
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/TransferOrderReportByItem/list", null, null, "TransferOrderReportByItem", "TransferOrderReportByItem(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", invoicePurchaseBillResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/TransferOrderReportByItem/list", line, error.toString(), "TransferOrderReportByItem", "TransferOrderReportByItem(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getInvoicePurchaseBillReturn(InvoicePurchaseBillReturnReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            filter.setUserId(userId);

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(purchasingReportMapper.countListInvoicePurchaseBillReturn(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<InvoicePurchaseBillReturnResponse> invoicePurchaseBillReturnResponses = purchasingReportMapper.getListInvoicePurchaseBillReturn(filter);
            Double totalSumSubTotal = 0D;
            Double totalSumTotalAmount = 0D;
            Double totalSumTotalVat = 0D;
            Double totalSumBalance = 0D;

            if (!invoicePurchaseBillReturnResponses.isEmpty()) {
                for (int i = 0; i < invoicePurchaseBillReturnResponses.size(); i++) {
                    totalSumSubTotal += invoicePurchaseBillReturnResponses.get(i).getSubTotal();
                    totalSumTotalAmount += invoicePurchaseBillReturnResponses.get(i).getTotalAmount();
                    totalSumTotalVat += invoicePurchaseBillReturnResponses.get(i).getTotalVat();
                    totalSumBalance += invoicePurchaseBillReturnResponses.get(i).getBalance();
                }
                invoicePurchaseBillReturnResponses.get(0).setTotalSumSubTotal(totalSumSubTotal);
                invoicePurchaseBillReturnResponses.get(0).setTotalSumTotalAmount(totalSumTotalAmount);
                invoicePurchaseBillReturnResponses.get(0).setTotalSumTotalVat(totalSumTotalVat);
                invoicePurchaseBillReturnResponses.get(0).setTotalSumBalance(totalSumBalance);
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/TransferOrderReportByItem/list", null, null, "TransferOrderReportByItem", "TransferOrderReportByItem(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", invoicePurchaseBillReturnResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/TransferOrderReportByItem/list", line, error.toString(), "TransferOrderReportByItem", "TransferOrderReportByItem(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListPurchaseByItem(PurchaseByItemFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Purchase By Item)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<PurchaseByItemResponse> responses;
            // View Parent Summary
            if (filter.getView() == 1) {
//                pagination.setTotal(purchasingReportMapper.countListParentSummary(filter));
                if (filter.getType() == 1) {
                    // Get Product
                    responses = purchasingReportMapper.getListParentSummaryProduct(filter);
                    if (!responses.isEmpty()) {
                        responses.get(0).setSubTotal(purchasingReportMapper.sumTotalParentSummaryProduct(filter));
                    }

                } else if (filter.getType() == 2) {
                    // Get Service
                    responses = purchasingReportMapper.getListParentSummaryService(filter);
                    if (!responses.isEmpty()) {
                        responses.get(0).setSubTotal(purchasingReportMapper.sumTotalParentSummaryService(filter));
                    }

                } else {
                    // Get Miscellaneous
                    responses = purchasingReportMapper.getListParentSummaryMiscellaneous(filter);
                    if (!responses.isEmpty()) {
                        responses.get(0).setSubTotal(purchasingReportMapper.sumTotalParentSummaryMiscellaneous(filter));
                    }
                }
            }


            // View Item Summary
            else if (filter.getView() == 2) {
//                pagination.setTotal(purchasingReportMapper.countListItemSummary(filter));
                if (filter.getType() == 1) {
                    // Get Product
                    responses = purchasingReportMapper.getListItemSummaryProduct(filter);
                    if (!responses.isEmpty()) {
                        responses.get(0).setTotalQty(purchasingReportMapper.sumTotalQtyItemSummaryProduct(filter));
                        responses.get(0).setSubTotal(purchasingReportMapper.sumTotalItemSummaryProduct(filter));
                    }
                } else if (filter.getType() == 2) {
                    // Get Service
                    responses = purchasingReportMapper.getListItemSummaryService(filter);
                    if (!responses.isEmpty()) {
                        responses.get(0).setSubTotal(purchasingReportMapper.sumTotalItemSummaryService(filter));
                    }
                } else {
                    // Get Miscellaneous
                    responses = purchasingReportMapper.getListItemSummaryMiscellaneous(filter);
                    if (!responses.isEmpty()) {
                        responses.get(0).setSubTotal(purchasingReportMapper.sumTotalItemSummaryMiscellaneous(filter));
                    }
                }
            }


            // View Item Detail
            else {
//                pagination.setTotal(purchasingReportMapper.countListItemDetailPurchaseByItem(filter));
                if (filter.getType() == 1) {
                    // Get Product
                    responses = purchasingReportMapper.getListItemDetailProduct(filter);
                    if (!responses.isEmpty()) {
                        for (int i = 0; i < responses.size(); i++) {
                            responses.get(i).setDetails(purchasingReportMapper.getListItemDetailProductDetail(filter, responses.get(i).getItemId()));
                            responses.get(0).setTotalQty(purchasingReportMapper.sumTotalQtyItemDetailProduct(filter));
                            responses.get(i).setSubTotal(purchasingReportMapper.sumTotalItemDetailProduct(filter));
                        }
                    }
                } else if (filter.getType() == 2) {
                    // Get Service
                    responses = purchasingReportMapper.getListItemDetailService(filter);
                    if (!responses.isEmpty()) {
                        responses.get(0).setSubTotal(purchasingReportMapper.sumTotalItemDetailService(filter));
                    }
                } else {
                    // Get Miscellaneous
                    responses = purchasingReportMapper.getListItemDetailMiscellaneous(filter);
                    if (!responses.isEmpty()) {
                        responses.get(0).setSubTotal(purchasingReportMapper.sumTotalItemDetailMiscellaneous(filter));
                    }
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/TransferOrderReportByItem/list", null, null, "TransferOrderReportByItem", "TransferOrderReportByItem(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/TransferOrderReportByItem/list", line, error.toString(), "TransferOrderReportByItem", "TransferOrderReportByItem(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListPayBill(PayBillReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(purchasingReportMapper.countListPayBill(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<PayBillReportResponse> responses = purchasingReportMapper.getListPayBill(filter, userId);
            if (!responses.isEmpty()) {
                for (int i = 0; i < responses.size(); i++) {
                    List<PayBillReportDetailResponse> details = purchasingReportMapper.getListPayBillDetail(filter, responses.get(i).getVendorId(), userId);
                    responses.get(i).setDetails(details);
                    //! Sum Grand Total
                    responses.get(0).setGrandTotalAmount(purchasingReportMapper.sumGrandTotalPayBill(filter, userId));

                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/TransferOrderReportByItem/list", null, null, "TransferOrderReportByItem", "TransferOrderReportByItem(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/TransferOrderReportByItem/list", line, error.toString(), "TransferOrderReportByItem", "TransferOrderReportByItem(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
