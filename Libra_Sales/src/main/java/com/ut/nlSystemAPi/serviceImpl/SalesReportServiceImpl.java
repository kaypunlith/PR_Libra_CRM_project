package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.SalesReportMapper;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.filter.Report.Sales.*;
import com.ut.nlSystemAPi.model.response.Report.ReportGrandTotalResponse;
import com.ut.nlSystemAPi.model.response.Report.Sales.*;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.SalesReportService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SalesReportServiceImpl implements SalesReportService {

    private static final Pattern SQL_IDENTIFIER_PATTERN = Pattern.compile("^[A-Za-z0-9_]+$");

    @Autowired
    private SalesReportMapper salesReportMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getListCustomerSummary(CustomerSummaryFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Customer Summary)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListCustomerSummary(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CustomerSummaryResponse> responses = salesReportMapper.getListCustomerSummary(filter);

            if (!responses.isEmpty()){
                for (CustomerSummaryResponse response : responses) {
                    List<CustomerSummaryTransactionResponse> allTransactions = new ArrayList<>(); // Initialize empty list
                    if (filter.getYears() != null && !filter.getYears().isEmpty()){
                        for (String year : filter.getYears()) {
                            List<CustomerSummaryTransactionResponse> transactions = salesReportMapper.getListCustomerSummaryTransaction(response.getId(), year);
                            allTransactions.addAll(transactions);
                        }
                    }
                    response.setTransactions(allTransactions);
                }
            }
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/customer-summary", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/customer-summary", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListQuotation(QuotationReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Quotation)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<QuotationReportResponse> responses;

            if( filter.getView() == 1 ){

                pagination.setTotal(salesReportMapper.countListQuotationDetail(filter));
                responses = salesReportMapper.getListQuotationDetail(filter);

            } else if ( filter.getView() == 2 ) {

                pagination.setTotal(salesReportMapper.countListQuotationByProduct(filter));
                responses = salesReportMapper.getListQuotationByProduct(filter);
                if (!responses.isEmpty()){
                    for (QuotationReportResponse response : responses) {
                        response.setDetails(salesReportMapper.getListQuotationByProductDetail(response.getId()));
                    }
                }

            } else {

                pagination.setTotal(salesReportMapper.countListQuotationByProductSummary(filter));
                responses = salesReportMapper.getListQuotationByProductSummary(filter);

            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/quotation", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/quotation", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSalesOrder(SalesOrderReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Sales Order)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SalesOrderReportResponse> responses;

            if( filter.getView() == 1 ){

                pagination.setTotal(salesReportMapper.countListSalesOrderDetail(filter));
                responses = salesReportMapper.getListSalesOrderDetail(filter);

            } else if ( filter.getView() == 2 ) {

                pagination.setTotal(salesReportMapper.countListSalesOrderByProduct(filter));
                responses = salesReportMapper.getListSalesOrderByProduct(filter);
                if (!responses.isEmpty()){
                    for (SalesOrderReportResponse response : responses) {
                        response.setDetails(salesReportMapper.getListSalesOrderByProductDetail(response.getId()));
                    }
                }

            } else {

                pagination.setTotal(salesReportMapper.countListSalesOrderByProductSummary(filter));
                responses = salesReportMapper.getListSalesOrderByProductSummary(filter);

            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-order", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-order", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSalesTopButtonItem(SalesTopButtonReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Sale Top Item)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListSalesTopBottomItem(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SalesTopBottomItemReportResponse> responses = salesReportMapper.getListSalesTopBottomItem(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-top-button-item", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-top-button-item", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSalesTopButtonCustomer(SalesTopButtonReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Sales Top/Button Customer)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListSalesTopBottomCustomer(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SalesTopBottomCustomerReportResponse> responses = salesReportMapper.getListSalesTopBottomCustomer(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-top-button-customer", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-top-button-customer", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSalesByItem(SalesByItemReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Sales By Item)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SalesByItemReportResponse> flatResponses = new ArrayList<>();

            if (filter.getView() != null){
                if (filter.getView() == 1){
                    // View Parent Summary
                    pagination.setTotal(salesReportMapper.countListSalesByItem(filter, userId));
                    flatResponses = salesReportMapper.getListSalesByItem(filter, userId);
                    if (flatResponses != null && !flatResponses.isEmpty() && filter.getType() == 1 && pagination.getPage() == 1){
                        ReportGrandTotalResponse grandTotal = salesReportMapper.sumGrandTotalSalesByItem(filter, userId);
                        flatResponses.get(0).setGrandTotals(grandTotal);
                    }
                }
                else if (filter.getView() == 2) {
                    // View Item Summary
                    pagination.setTotal(salesReportMapper.countListSalesByItem(filter, userId));
                    flatResponses = salesReportMapper.getListSalesByItem(filter, userId);
                    if (filter.getType() == 1){
                        Map<String, List<SalesByItemReportResponse>> groupedMap = flatResponses.stream()
                                .collect(Collectors.groupingBy(
                                        invoice -> invoice.getParentName() != null ? invoice.getParentName() : "No Parent",
                                        LinkedHashMap::new,
                                        Collectors.toList()
                                ));

                        flatResponses = groupedMap.entrySet().stream()
                                .map(entry -> {
                                    SalesByItemReportResponse response = new SalesByItemReportResponse();
                                    response.setParentName(entry.getKey());
                                    entry.getValue().forEach(invoice -> {
                                        invoice.setParentName(null);
                                    });
                                    response.setDetails(entry.getValue());
                                    return response;
                                })
                                .collect(Collectors.toList());
                        if (!flatResponses.isEmpty() && pagination.getPage() == 1){
                            ReportGrandTotalResponse grandTotal = salesReportMapper.sumGrandTotalSalesByItem(filter, userId);
                            flatResponses.get(0).setGrandTotals(grandTotal);
                        }
                    }
                } else {
                    // View Item Detail
                    pagination.setTotal(salesReportMapper.countListSalesByItem(filter, userId));
                    flatResponses = salesReportMapper.getListSalesByItem(filter, userId);
                    Map<String, List<SalesByItemReportResponse>> groupedMap = flatResponses.stream()
                            .collect(Collectors.groupingBy(
                                    invoice -> invoice.getItemName() != null ? invoice.getItemName() : "",
                                    LinkedHashMap::new,
                                    Collectors.toList()
                            ));

                    flatResponses = groupedMap.entrySet().stream()
                            .map(entry -> {
                                SalesByItemReportResponse response = new SalesByItemReportResponse();
                                response.setItemName(entry.getKey());
                                entry.getValue().forEach(invoice -> {
                                    invoice.setItemName(null);
                                });
                                response.setDetails(entry.getValue());
                                return response;
                            })
                            .collect(Collectors.toList());
                    if (!flatResponses.isEmpty() && filter.getType() == 1 && pagination.getPage() == 1){
                        ReportGrandTotalResponse grandTotal = salesReportMapper.sumGrandTotalSalesByItem(filter, userId);
                        flatResponses.get(0).setGrandTotals(grandTotal);
                    }
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-by-item", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", flatResponses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-by-item", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSalesByItemType(SalesByItemTypeReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Sales By Item Type)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SalesByItemTypeReportResponse> flatResponses = new ArrayList<>();

            if (filter.getView() != null){
                if (filter.getView() == 1){
                    // View Parent Summary
                    pagination.setTotal(salesReportMapper.countListSalesByItemType(filter, userId));
                    flatResponses = salesReportMapper.getListSalesByItemType(filter, userId);
                    if (flatResponses != null && !flatResponses.isEmpty()){
                        ReportGrandTotalResponse grandTotal = salesReportMapper.sumGrandTotalSalesByItemType(filter, userId);
                        flatResponses.get(0).setGrandTotals(grandTotal);
                    }
                }
                else if (filter.getView() == 2) {
                    // View Item Summary
                    pagination.setTotal(salesReportMapper.countListSalesByItemType(filter, userId));
                    flatResponses = salesReportMapper.getListSalesByItemType(filter, userId);
                    if (filter.getType() == 1){
                        Map<String, List<SalesByItemTypeReportResponse>> groupedMap = flatResponses.stream()
                                .collect(Collectors.groupingBy(
                                        invoice -> invoice.getParentName() != null ? invoice.getParentName() : "No Parent",
                                        LinkedHashMap::new,
                                        Collectors.toList()
                                ));

                        flatResponses = groupedMap.entrySet().stream()
                                .map(entry -> {
                                    SalesByItemTypeReportResponse response = new SalesByItemTypeReportResponse();
                                    response.setParentName(entry.getKey());
                                    entry.getValue().forEach(invoice -> {
                                        invoice.setParentName(null);
                                    });
                                    response.setDetails(entry.getValue());
                                    return response;
                                })
                                .collect(Collectors.toList());

                        if (!flatResponses.isEmpty() && pagination.getPage() == 1){
                            ReportGrandTotalResponse grandTotal = salesReportMapper.sumGrandTotalSalesByItemType(filter, userId);
                            flatResponses.get(0).setGrandTotals(grandTotal);
                        }
                    }
                } else {
                    // View Item Detail
                    pagination.setTotal(salesReportMapper.countListSalesByItemType(filter, userId));
                    flatResponses = salesReportMapper.getListSalesByItemType(filter, userId);
                    Map<String, List<SalesByItemTypeReportResponse>> groupedMap = flatResponses.stream()
                            .collect(Collectors.groupingBy(
                                    invoice -> invoice.getItemName() != null ? invoice.getItemName() : "Unknown",
                                    LinkedHashMap::new,
                                    Collectors.toList()
                            ));

                    flatResponses = groupedMap.entrySet().stream()
                            .map(entry -> {
                                SalesByItemTypeReportResponse response = new SalesByItemTypeReportResponse();
                                response.setItemName(entry.getKey());
                                entry.getValue().forEach(invoice -> {
                                    invoice.setItemName(null);
                                });
                                response.setDetails(entry.getValue());
                                return response;
                            })
                            .collect(Collectors.toList());
                    if (!flatResponses.isEmpty() && pagination.getPage() == 1){
                        ReportGrandTotalResponse grandTotal = salesReportMapper.sumGrandTotalSalesByItemType(filter, userId);
                        flatResponses.get(0).setGrandTotals(grandTotal);
                    }
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-by-item-type", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", flatResponses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-by-item-type", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSalesByCustomer(SalesByCustomerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Sales By Customer)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListSalesByCustomer(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SalesByCustomerReportResponse> flatResponses = salesReportMapper.getListSalesByCustomer(filter, userId);
            if (flatResponses != null && !flatResponses.isEmpty()){
                ReportGrandTotalResponse grandTotal = salesReportMapper.sumGrandTotalSalesByCustomer(filter, userId);
                flatResponses.get(0).setGrandTotals(grandTotal);

                if (filter.getShowTotalOnly() != 1){
                    Map<String, List<SalesByCustomerReportResponse>> groupedMap = flatResponses.stream()
                            .collect(Collectors.groupingBy(
                                    invoice -> invoice.getOrganizationName() != null ? invoice.getOrganizationName() : "Unknown",
                                    LinkedHashMap::new,
                                    Collectors.toList()
                            ));
                    flatResponses = groupedMap.entrySet().stream()
                            .map(entry -> {
                                SalesByCustomerReportResponse response = new SalesByCustomerReportResponse();
                                response.setOrganizationName(entry.getKey());
                                entry.getValue().forEach(invoice -> {
                                    invoice.setOrganizationName(null);
                                });
                                response.setDetails(entry.getValue());
                                return response;
                            })
                            .collect(Collectors.toList());

                }
            }
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-by-customer", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", flatResponses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-by-customer", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSalesByRep(SalesByCustomerReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Sales By Rep)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListSalesByRep(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SalesByCustomerReportResponse> flatResponses = salesReportMapper.getListSalesByRep(filter, userId);
            if (flatResponses != null && !flatResponses.isEmpty()){
                ReportGrandTotalResponse grandTotal = salesReportMapper.sumGrandTotalSalesByRep(filter, userId);
                flatResponses.get(0).setGrandTotals(grandTotal);

                if (filter.getShowTotalOnly() != 1){
                    Map<String, List<SalesByCustomerReportResponse>> groupedMap = flatResponses.stream()
                            .collect(Collectors.groupingBy(
                                    invoice -> invoice.getOrganizationGroupName() != null ? invoice.getOrganizationGroupName() : "Unknown",
                                    LinkedHashMap::new,
                                    Collectors.toList()
                            ));
                    flatResponses = groupedMap.entrySet().stream()
                            .map(entry -> {
                                SalesByCustomerReportResponse response = new SalesByCustomerReportResponse();
                                response.setOrganizationGroupName(entry.getKey());
                                entry.getValue().forEach(invoice -> {
                                    invoice.setOrganizationGroupName(null);
                                });
                                response.setDetails(entry.getValue());
                                return response;
                            })
                            .collect(Collectors.toList());

                }
            }
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-by-rep", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", flatResponses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-by-rep", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListTotalSales(TotalSalesReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Total Sales)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListTotalSales(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<TotalSalesReportResponse> responses = salesReportMapper.getListTotalSales(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/total-sales", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/total-sales", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSalesInvoice(SalesInvoiceReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Customer Summary)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<SalesInvoiceReportResponse> flatResponses;
            if (filter.getView() == 1){
                flatResponses = salesReportMapper.getListSalesInvoiceDetail(filter, userId);
                pagination.setTotal(salesReportMapper.countListSalesInvoiceDetail(filter, userId));

                Map<String, List<SalesInvoiceReportResponse>> groupedMap = flatResponses.stream()
                        .collect(Collectors.groupingBy(
                                invoice -> (invoice.getInvoiceCode() != null ? invoice.getInvoiceCode() : "") +
                                        "|" +
                                        (invoice.getInvoiceDate() != null ? invoice.getInvoiceDate() : ""),
                                LinkedHashMap::new,
                                Collectors.toList()
                        ));

                flatResponses = groupedMap.entrySet().stream()
                        .map(entry -> {
                            String[] keys = entry.getKey().split("\\|");
                            SalesInvoiceReportResponse response = new SalesInvoiceReportResponse();
                            response.setInvoiceCode(keys[0]);
                            response.setInvoiceDate(keys.length > 1 ? keys[1] : null);

                            // Populate header fields from the first row
                            SalesInvoiceReportResponse first = entry.getValue().get(0);
                            response.setOrganizationName(first.getOrganizationName());
                            response.setOrganizationGroupName(first.getOrganizationGroupName());
                            response.setTotalAmount(first.getTotalAmount());
                            response.setTotalVat(first.getTotalVat());
                            response.setBalance(first.getBalance());
                            response.setAging(first.getAging());
                            response.setCurrencySymbol(first.getCurrencySymbol());
                            response.setSubTotal(first.getSubTotal());
                            response.setStatus(first.getStatus());

                            // Details contain description only
                            List<SalesInvoiceReportResponse> detailDescriptions = entry.getValue().stream()
                                    .map(item -> {
                                        SalesInvoiceReportResponse detail = new SalesInvoiceReportResponse();
                                        detail.setDescription(item.getDescription());
                                        return detail;
                                    })
                                    .collect(Collectors.toList());

                            response.setDetails(detailDescriptions);
                            return response;
                        })
                        .collect(Collectors.toList());
            } else {
                flatResponses = salesReportMapper.getListSalesInvoiceItem(filter, userId);
                pagination.setTotal(salesReportMapper.countListSalesInvoiceItem(filter, userId));
                if (!flatResponses.isEmpty()){
                    for (SalesInvoiceReportResponse response : flatResponses){
                        response.setDetails(salesReportMapper.getListSalesInvoiceItemDetail(response.getId(),userId));
                    }
                }
            }
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-invoice", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", flatResponses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/sales-invoice", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListInvoiceByRep(InvoiceByRepReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Open Invoice By Rep)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListInvoiceByRep(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<InvoiceByRepReportResponse> flatInvoices = salesReportMapper.getListInvoiceByRep(filter, userId);

            Map<String, List<InvoiceByRepReportResponse>> groupedMap = flatInvoices.stream()
                    .collect(Collectors.groupingBy(
                            invoice -> invoice.getOrganizationGroupName() != null ? invoice.getOrganizationGroupName() : "Unknown",
                            LinkedHashMap::new,
                            Collectors.toList()
                    ));

            List<InvoiceByRepReportResponse> responses = groupedMap.entrySet().stream()
                    .map(entry -> {
                        InvoiceByRepReportResponse response = new InvoiceByRepReportResponse();
                        response.setOrganizationGroupName(entry.getKey());

                        if (!entry.getValue().isEmpty()) {
                            response.setOrganizationGroupId(entry.getValue().get(0).getOrganizationGroupId());
                        }
                        entry.getValue().forEach(invoice -> {
                            invoice.setOrganizationGroupId(null);
                            invoice.setOrganizationGroupName(null);
                        });
                        response.setDetails(entry.getValue());
                        return response;
                    })
                    .collect(Collectors.toList());

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/invoice-by-rep", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/invoice-by-rep", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListInvoiceCreditMemo(InvoiceCreditMemoReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Invoice Credit Memo)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListInvoiceCreditMemo(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<InvoiceCreditMemoReportResponse> responses = salesReportMapper.getListInvoiceCreditMemo(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/invoice-credit-memo", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/invoice-credit-memo", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOpenInvoiceByRep(OpenInvoiceByRepReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Open Invoice By Rep)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListOpenInvoiceByRep(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<InvoiceByRepReportResponse> flatInvoices = salesReportMapper.getListOpenInvoiceByRep(filter, userId);
            Map<String, List<InvoiceByRepReportResponse>> groupedMap = flatInvoices.stream()
                    .collect(Collectors.groupingBy(
                            invoice -> invoice.getOrganizationGroupName() != null ? invoice.getOrganizationGroupName() : "",
                            LinkedHashMap::new,
                            Collectors.toList()
                    ));

            List<InvoiceByRepReportResponse> responses = groupedMap.entrySet().stream()
                    .map(entry -> {
                        InvoiceByRepReportResponse response = new InvoiceByRepReportResponse();
                        response.setOrganizationGroupName(entry.getKey());

                        if (!entry.getValue().isEmpty()) {
                            response.setOrganizationGroupId(entry.getValue().get(0).getOrganizationGroupId());
                        }
                        entry.getValue().forEach(invoice -> {
                            invoice.setOrganizationGroupId(null);
                            invoice.setOrganizationGroupName(null);
                        });
                        response.setDetails(entry.getValue());
                        return response;
                    })
                    .collect(Collectors.toList());
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/open-invoice-by-rep", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/open-invoice-by-rep", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListDiscountSummary(DiscountSummaryReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Discount Summary)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListDiscountSummary(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DiscountSummaryReportResponse> responses = salesReportMapper.getListDiscountSummary(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/discount-summary", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/discount-summary", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListDeliveryNote(DeliveryNoteReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Customer Summary)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListDeliveryNote(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DeliveryNoteReportResponse> responses = salesReportMapper.getListDeliveryNote(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/delivery-note", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/delivery-note", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListECommerceUser(ECommerceUserReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        String reportName = "Report (E-Commerce User)";
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "User (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListECommerceUser(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ECommerceUserReportResponse> responses = salesReportMapper.getListECommerceUser(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/e-commerce-user", null, null, reportName, "User (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/e-commerce-user", line, error.toString(), reportName, "User (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListReceivePayment(ReceivePaymentReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "(Receive Payments)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListReceivePayment(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ReceivePaymentReportResponse> flatResponses = salesReportMapper.getListReceivePayment(filter, userId);
            Map<String, List<ReceivePaymentReportResponse>> groupedMap = flatResponses.stream()
                    .collect(Collectors.groupingBy(
                            payment -> payment.getOrganizationName() != null ? payment.getOrganizationName() : "Unknown",
                            LinkedHashMap::new,
                            Collectors.toList()
                    ));

            List<ReceivePaymentReportResponse> responses = groupedMap.entrySet().stream()
                    .map(entry -> {
                        ReceivePaymentReportResponse response = new ReceivePaymentReportResponse();
                        response.setOrganizationName(entry.getKey());

                        if (!entry.getValue().isEmpty()) {
                            response.setOrganizationId(entry.getValue().get(0).getOrganizationId());
                        }
                        entry.getValue().forEach(payment -> {
                            payment.setOrganizationId(null);
                            payment.setOrganizationName(null);
                        });
                        response.setDetails(entry.getValue());
                        return response;
                    })
                    .collect(Collectors.toList());
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/receive-payment", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/receive-payment", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListReceivePaymentByRep(ReceivePaymentByRepReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Receive Payments By Rep)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(salesReportMapper.countListReceivePaymentByRep(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ReceivePaymentByRepReportResponse> flatResponses = salesReportMapper.getListReceivePaymentByRep(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/receive-payment-by-rep", null, null, "Report (Customer Summary)", "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", flatResponses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-report/receive-payment-by-rep", line, error.toString(), "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
