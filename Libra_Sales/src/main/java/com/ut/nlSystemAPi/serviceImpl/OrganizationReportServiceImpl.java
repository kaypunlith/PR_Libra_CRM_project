package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.OrganizationReportMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.SaleOrderMapper;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.entity.Organization.CustomerHistoryPrintTracking;
import com.ut.nlSystemAPi.model.entity.Quotation.QuotationLog;
import com.ut.nlSystemAPi.model.filter.Report.Organization.*;
import com.ut.nlSystemAPi.model.request.Organization.CustomerHistoryPrintRequest;
import com.ut.nlSystemAPi.model.response.Quotation.QuotationDetailResponse;
import com.ut.nlSystemAPi.model.response.Quotation.QuotationResponse;
import com.ut.nlSystemAPi.model.response.Report.Organization.*;
import com.ut.nlSystemAPi.model.response.Organization.CustomerHistoryReportResponse;
import com.ut.nlSystemAPi.model.response.Organization.CustomerHistoryMemoResponse;
import com.ut.nlSystemAPi.model.response.Report.ReportGrandTotalResponse;
import com.ut.nlSystemAPi.model.response.Service.ServiceShiftResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.OrganizationReportService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrganizationReportServiceImpl implements OrganizationReportService {

    @Autowired
    private OrganizationReportMapper organizationReportMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private com.ut.nlSystemAPi.mapper.primary.QuotationMapper quotationMapper;

    @Autowired
    private SaleOrderMapper saleOrderMapper;
    
    @Autowired
    private com.ut.nlSystemAPi.mapper.primary.QuotationLogMapper quotationLogMapper;
    
    @Autowired
    private com.ut.nlSystemAPi.mapper.primary.CustomerHistoryPrintTrackingMapper customerHistoryPrintTrackingMapper;

    @Autowired
    private com.ut.nlSystemAPi.mapper.primary.ServiceMapper serviceMapper;

    @Override
    public ResponseMessage<BaseResult> getListAccountReceivableAging(AccountReceivableAgingReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Customer Summary)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Validate aging parameters
            if (filter.getIntervalDay() == null || filter.getIntervalDay() <= 0 || filter.getThroughDay() == null || filter.getThroughDay() <= 0) {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Interval and through days must be greater than 0.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(organizationReportMapper.countListAccountReceivableAging(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            long column = filter.getThroughDay() / filter.getIntervalDay();

            long fromValue = 0L;
            long toValue = 0L;

            List<AccountReceivableAgingReportResponse> responses = organizationReportMapper.getListAccountReceivableAging(filter, userId);

            if (!responses.isEmpty()) {
                for (int i = 0; i < responses.size(); i++) {
                    List<AccountReceivableAgingReportDetailResponse> details = new ArrayList<>();
                    AccountReceivableAgingReportDetailResponse detailCurrent = new AccountReceivableAgingReportDetailResponse();
                    Double current = organizationReportMapper.getCurrentAmount(filter, userId, responses.get(i).getOrganizationId());
                    detailCurrent.setLabel("Current");
                    detailCurrent.setBalance(current);
                    details.add(detailCurrent);

                    for (int j = 0; j < column; j++) {
                        AccountReceivableAgingReportDetailResponse detailsColumn = new AccountReceivableAgingReportDetailResponse();
                        fromValue = j * filter.getIntervalDay() + 1;
                        toValue = (j + 1) * filter.getIntervalDay();
                        Double dynamic = organizationReportMapper.getAgingAmount(filter, userId, responses.get(i).getOrganizationId(), fromValue, toValue);
                        detailsColumn.setLabel(fromValue + "-" + toValue);
                        detailsColumn.setBalance(dynamic);
                        details.add(detailsColumn);
                    }

                    AccountReceivableAgingReportDetailResponse detailOver = new AccountReceivableAgingReportDetailResponse();
                    Double over = organizationReportMapper.getOverAgingAmount(filter, userId, responses.get(i).getOrganizationId(), filter.getThroughDay());
                    detailOver.setLabel("> " + filter.getThroughDay());
                    detailOver.setBalance(over);
                    details.add(detailOver);

                    // Set to response data
                    responses.get(i).setDetails(details);

                }

            }


            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/account-receivable-aging", null, null, "Report (Account Receivable Aging)", "Report (Account Receivable Aging)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/account-receivable-aging", line, error.toString(), "Report (Account Receivable Aging)", "Report (Account Receivable Aging)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error: " + error.getMessage(), null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCustomerBalance(CustomerBalanceReportFilter filter,
                                                              HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Customer Balance)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true,
                        messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(organizationReportMapper.countListCustomerBalance(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CustomerBalanceReportResponse> flatResponses = organizationReportMapper.getListCustomerBalance(filter,
                    userId);
            Map<String, List<CustomerBalanceReportResponse>> groupedMap = flatResponses.stream()
                    .collect(Collectors.groupingBy(
                            payment -> payment.getOrganizationName() != null ? payment.getOrganizationName()
                                    : "Unknown",
                            LinkedHashMap::new,
                            Collectors.toList()));

            List<CustomerBalanceReportResponse> responses = groupedMap.entrySet().stream()
                    .map(entry -> {
                        CustomerBalanceReportResponse response = new CustomerBalanceReportResponse();
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
            if (filter.getPage() != null && filter.getPage() == 1 && !responses.isEmpty()) {
                ReportGrandTotalResponse grandTotal = organizationReportMapper.sumGrandTotalCustomerBalance(filter, userId);
                responses.get(0).setGrandTotals(grandTotal);
            }
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-balance", null, null, "Report (Customer Summary)",
                    "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true,
                    messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-balance", line, error.toString(),
                    "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration,
                    endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCustomerBalanceByInvoice(CustomerBalanceByInvoiceReportFilter filter,
                                                                       HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Customer Balance By Invoice)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true,
                        messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(organizationReportMapper.countListCustomerBalanceByInvoice(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CustomerBalanceByInvoiceReportResponse> flatResponses = organizationReportMapper.getListCustomerBalanceByInvoice(filter, userId);
            Map<String, List<CustomerBalanceByInvoiceReportResponse>> groupedMap = flatResponses.stream()
                    .collect(Collectors.groupingBy(
                            payment -> payment.getReference() != null ? payment.getReference() : "",
                            LinkedHashMap::new,
                            Collectors.toList()));

            List<CustomerBalanceByInvoiceReportResponse> responses = groupedMap.entrySet().stream()
                    .map(entry -> {
                        CustomerBalanceByInvoiceReportResponse response = new CustomerBalanceByInvoiceReportResponse();
                        response.setReference(entry.getKey());

                        if (!entry.getValue().isEmpty()) {
                            response.setOrganizationId(entry.getValue().get(0).getId());
                        }
                        entry.getValue().forEach(payment -> {
                            payment.setId(null);
                            payment.setReference(null);
                        });
                        response.setDetails(entry.getValue());
                        return response;
                    })
                    .collect(Collectors.toList());
            if (filter.getPage() != null && filter.getPage() == 1 && !responses.isEmpty()) {
                ReportGrandTotalResponse grandTotal = organizationReportMapper.sumGrandTotalCustomerBalanceByInvoice(filter, userId);
                responses.get(0).setGrandTotals(grandTotal);
            }
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-balance-by-invoice", null, null, "Report (Customer Summary)",
                    "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true,
                    messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-balance-by-invoice", line, error.toString(),
                    "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration,
                    endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListStatement(StatementReportFilter filter,
                                                        HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Statement)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true,
                        messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(organizationReportMapper.countListStatement(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<StatementReportResponse> responses = organizationReportMapper.getListStatement(filter);

            if (!responses.isEmpty()) {
                long column = 90 / 30;
                long fromValue;
                long toValue;
                List<StatementReportDetailResponse> details = new ArrayList<>();
                StatementReportDetailResponse detailCurrent = new StatementReportDetailResponse();
                Double current = organizationReportMapper.getCurrentAmountStatement(filter, userId, responses.get(0).getOrganizationId());
                detailCurrent.setLabel("Current");
                detailCurrent.setBalance(current);
                details.add(detailCurrent);

                for (int j = 0; j < column; j++) {
                    StatementReportDetailResponse detailsColumn = new StatementReportDetailResponse();
                    fromValue = j * 30L + 1;
                    toValue = (j + 1) * 30L;
                    Double dynamic = organizationReportMapper.getAgingAmountStatement(filter, userId, responses.get(0).getOrganizationId(), fromValue, toValue);
                    detailsColumn.setLabel(fromValue + "-" + toValue);
                    detailsColumn.setBalance(dynamic);
                    details.add(detailsColumn);
                }

                StatementReportDetailResponse detailOver = new StatementReportDetailResponse();
                Double over = organizationReportMapper.getOverAgingAmountStatement(filter, userId, responses.get(0).getOrganizationId(), 90L);
                detailOver.setLabel("> " + 90);
                detailOver.setBalance(over);
                details.add(detailOver);

                responses.get(0).setDetails(details);
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/statement", null, null, "Report (Customer Summary)",
                    "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true,
                    messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/statement", line, error.toString(),
                    "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration,
                    endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCustomerAddress(CustomerAddressReportFilter filter,
                                                              HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Customer Address)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true,
                        messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(organizationReportMapper.countListCustomerAddress(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CustomerAddressReportResponse> flatResponses = organizationReportMapper.getListCustomerAddress(filter);
            Map<String, List<CustomerAddressReportResponse>> groupedMap = flatResponses.stream()
                    .collect(Collectors.groupingBy(
                            payment -> payment.getGroupName() != null ? payment.getGroupName() : "",
                            LinkedHashMap::new,
                            Collectors.toList()));

            List<CustomerAddressReportResponse> responses = groupedMap.entrySet().stream()
                    .map(entry -> {
                        CustomerAddressReportResponse response = new CustomerAddressReportResponse();
                        response.setGroupName(entry.getKey());

                        if (!entry.getValue().isEmpty()) {
                            response.setGroupId(entry.getValue().get(0).getId());
                        }
                        entry.getValue().forEach(payment -> {
                            payment.setGroupId(null);
                            payment.setGroupName(null);
                        });
                        response.setDetails(entry.getValue());
                        return response;
                    })
                    .collect(Collectors.toList());
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-address", null, null, "Report (Customer Summary)",
                    "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true,
                    messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-address", line, error.toString(),
                    "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration,
                    endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCustomerAddressList(CustomerAddressListReportFilter filter,
                                                                  HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Customer Address List)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true,
                        messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(organizationReportMapper.countListCustomerAddressList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CustomerAddressReportResponse> responses = organizationReportMapper.getListCustomerAddressList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-address-list", null, null, "Report (Customer Summary)",
                    "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true,
                    messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-address-list", line, error.toString(),
                    "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration,
                    endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCustomerAddressDetail(CustomerAddressListReportFilter filter,
                                                                    HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Customer Address Detail)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true,
                        messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(organizationReportMapper.countListCustomerAddressDetail(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CustomerAddressReportResponse> responses = organizationReportMapper
                    .getListCustomerAddressDetail(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-address-detail", null, null, "Report (Customer Summary)",
                    "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true,
                    messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-address-detail", line, error.toString(),
                    "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration,
                    endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSOBalance(SOBalanceReportFilter filter,
                                                        HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (SO Balance)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(organizationReportMapper.countListSOBalance(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SOBalanceReportResponse> flatResponses = organizationReportMapper.getListSOBalance(filter);
            Map<String, List<SOBalanceReportResponse>> groupedMap = flatResponses.stream()
                    .collect(Collectors.groupingBy(
                            payment -> payment.getOrganizationName() != null ? payment.getOrganizationName() : "",
                            LinkedHashMap::new,
                            Collectors.toList()));

            List<SOBalanceReportResponse> responses = groupedMap.entrySet().stream()
                    .map(entry -> {
                        SOBalanceReportResponse response = new SOBalanceReportResponse();
                        response.setOrganizationName(entry.getKey());

                        if (!entry.getValue().isEmpty()) {
                            response.setOrganizationId(entry.getValue().get(0).getOrganizationId());
                        }

                        response.setDetails(entry.getValue());
                        return response;
                    })
                    .collect(Collectors.toList());

            if (!flatResponses.isEmpty()) {
                for (SOBalanceReportResponse response : flatResponses) {
                    if (response.getType() == 1) {
                        if (response.getStatus() == 1) {
                            // Check if the SO is already close and approve
                            if (response.getIsClose() == 1 && response.getIsApprove() == 1) {
                                // Check if the SO is already have invoice
                                Long checkInvoice = saleOrderMapper.checkInvoice(response.getSoId());
                                if (checkInvoice > 0) {
                                    // Check if the SO is already have invoice and already delivery
                                    Long checkDelivery = saleOrderMapper.checkDelivery(response.getSoId());
                                    if (checkDelivery > 0) {
                                        response.setSoStatus(
                                                "SO បានចេញវិក្ក័យប័ត្រ (Sales Invoice) ទៅតាមមុខទំនិញអស់ហើយ និង បានដឹកជញ្ជូនរួចរាល់ហើយ។");
                                        response.setSoStatusColor(1);
                                    } else {
                                        response.setSoStatus(
                                                "SO បានចេញវិក្ក័យប័ត្រ (Sales Invoice) ទៅតាមមុខទំនិញអស់ហើយ ប៉ុន្តែមិនទាន់ដឹកជញ្ជូន (Not Yet Delivery)។");
                                        response.setSoStatusColor(3);
                                    }
                                }
                            } else {
                                if (response.getDateDiff() > 3 && response.getIsClose() == 0
                                        && response.getIsApprove() == 0) {
                                    response.setSoStatus("SO ដែលបានបង្កើតហើយលើសពី៣ថ្ងៃហើយគ្មានប្រតិបត្តិការណ៏។");
                                    response.setSoStatusColor(4);
                                } else {
                                    response.setSoStatus(
                                            "SO ដែលបានបង្កើតហើយមិនលើសពី ៣ថ្ងៃ ហើយបានអនុម័ត្ត (Approved) រួចរាល់។");
                                    response.setSoStatusColor(0);
                                }
                            }
                        } else if (response.getStatus() == 2) {
                            response.setSoStatus(
                                    "SO បានចេញវិក្ក័យប័ត្រ (Sales Invoice) ទៅតាមមុខទំនិញខ្លះៗហើយតែមិនទាន់អស់មុខទំនិញ (Status = Partial)។");
                            response.setSoStatusColor(2);
                        } else if (response.getStatus() == 3) {
                            response.setSoStatus(
                                    "SO បានចេញវិក្ក័យប័ត្រ (Sales Invoice) ទៅតាមមុខទំនិញអស់ហើយ ប៉ុន្តែមិនទាន់ដឹកជញ្ជូន (Not Yet Delivery)។");
                            response.setSoStatusColor(3);
                        } else if (response.getStatus() == 4) {
                            response.setSoStatus(
                                    "SO បានចេញវិក្ក័យប័ត្រ (Sales Invoice) ទៅតាមមុខទំនិញអស់ហើយ និង បានដឹកជញ្ជូនរួចរាល់ហើយ។");
                            response.setSoStatusColor(1);
                        }
                        response.setIsApprove(null);
                        response.setIsClose(null);
                        response.setStatus(null);
                    }
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/so-balance", null, null, "Report (Customer Summary)",
                    "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true,
                    messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/so-balance", line, error.toString(),
                    "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration,
                    endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListActivityCardTracking(ActivityCardTrackingReportFilter filter,
                                                                   HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Activity Card Tracking)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true,
                        messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(organizationReportMapper.countListActivityCardTracking(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ActivityCardTrackingReportResponse> flatResponses = organizationReportMapper
                    .getListActivityCardTracking(filter);
            Map<String, List<ActivityCardTrackingReportResponse>> groupedMap = flatResponses.stream()
                    .collect(Collectors.groupingBy(
                            payment -> payment.getOrganizationName() != null ? payment.getOrganizationName() : "",
                            LinkedHashMap::new,
                            Collectors.toList()));

            List<ActivityCardTrackingReportResponse> responses = groupedMap.entrySet().stream()
                    .map(entry -> {
                        ActivityCardTrackingReportResponse response = new ActivityCardTrackingReportResponse();
                        response.setOrganizationName(entry.getKey());

                        if (!entry.getValue().isEmpty()) {
                            response.setOrganizationId(entry.getValue().get(0).getId());
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
            activityLogService.insert("/organization-report/activity-card-tracking", null, null, "Report (Customer Summary)",
                    "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true,
                    messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/activity-card-tracking", line, error.toString(),
                    "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration,
                    endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCustomerHistory(CustomerHistoryReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1010L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Customer Summary)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(organizationReportMapper.countListCustomerHistory(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CustomerHistoryReportResponse> flatResponses = organizationReportMapper.getListCustomerHistory(filter);
            
            // Grouping logic for Customer History
            Map<Long, CustomerHistoryReportResponse> groupedMap = new LinkedHashMap<>();
            for (CustomerHistoryReportResponse flat : flatResponses) {
                Long customerId = flat.getCustomerId();
                if (!groupedMap.containsKey(customerId)) {
                    CustomerHistoryReportResponse newResponse = new CustomerHistoryReportResponse();
                    newResponse.setCustomerId(flat.getCustomerId());
                    newResponse.setCustomerName(flat.getCustomerName());
                    newResponse.setCustomerCode(flat.getCustomerCode());
                    newResponse.setMemos(new ArrayList<>());
                    groupedMap.put(customerId, newResponse);
                }
                
                CustomerHistoryMemoResponse memo = new CustomerHistoryMemoResponse();
                memo.setStatus(flat.getStatus());
                memo.setQty(flat.getQty());
                memo.setMemoStatus(flat.getMemoStatus());
                memo.setDate(flat.getDate());
                memo.setServices(flat.getServices());
                memo.setQuotationId(flat.getQuotationId());
                memo.setTerminateId(flat.getTerminateId());
                memo.setPrintCount(flat.getPrintCount());
                
                CustomerHistoryReportResponse group = groupedMap.get(customerId);
                group.getMemos().add(memo);
                
                int qtyDelta = flat.getQty() != null ? flat.getQty() : 0;
                if ("Reduce".equalsIgnoreCase(flat.getStatus()) || "Terminate".equalsIgnoreCase(flat.getStatus())) {
                    group.setLatestQty(group.getLatestQty() - qtyDelta);
                } else {
                    group.setLatestQty(group.getLatestQty() + qtyDelta);
                }
            }
            List<CustomerHistoryReportResponse> responses = new ArrayList<>(groupedMap.values());
            
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-history", null, null, "Report (Customer Summary)",
                    "Report (Customer Summary)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true,
                    messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-history", line, error.toString(),
                    "Report (Customer Summary)", "Report (Customer Summary)", "View", 2, "Error", startDuration,
                    endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateMemoStatus(CustomerHistoryPrintRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1011L;
        try {
            Long userId = userService.getUserAuth().getId();
            
          CustomerHistoryPrintTracking tracking = customerHistoryPrintTrackingMapper.findByTypeAndReferenceId(request.getType(), request.getReferenceId());
            if (tracking == null) {
                tracking = new CustomerHistoryPrintTracking();
                tracking.setType(request.getType());
                tracking.setReferenceId(request.getReferenceId());

                customerHistoryPrintTrackingMapper.insert(tracking);
            } else {
                customerHistoryPrintTrackingMapper.updateStatus(request.getType(), request.getReferenceId());
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/update-memo-status", null, null, "Report (Customer Summary)",
                    "Update Memo Status", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/update-memo-status", line, error.toString(),
                    "Report (Customer Summary)", "Update Memo Status", "Update", 2, "Error", startDuration,
                    endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> printCustomerHistoryQuotation(Long quotationIds, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1001L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Report (Customer History)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<QuotationResponse> resultList = new  java.util.ArrayList<>();
            if (quotationIds != null) {

                    List<QuotationResponse> data = quotationMapper.getOneIncludeArchived(quotationIds, userId);
                    if (data != null && !data.isEmpty()) {
                        QuotationResponse response = data.get(0);
                        response.setTermConditions(quotationMapper.getTermCondition(response.getId()));

                        List<QuotationDetailResponse> allDetails = quotationMapper.getListDetail(response.getId());
                        List<QuotationLog> logs = quotationLogMapper.getLogsByQuotationId(response.getId());
                        java.util.Set<String> loggedServiceIds = new java.util.HashSet<>();
                        if (logs != null && !logs.isEmpty()) {
                            // Find the most recent UPDATE log, or ADD log
                            QuotationLog relevantLog = null;
                            for (QuotationLog l : logs) {
                                if ("UPDATE".equals(l.getAction())) {
                                    relevantLog = l;
                                    break;
                                }
                            }
                            if (relevantLog == null) relevantLog = logs.get(0);

                            if (relevantLog.getServiceId() != null && !relevantLog.getServiceId().isEmpty()) {
                                String[] sIds = relevantLog.getServiceId().split(",");
                                for (String sId : sIds) {
                                    loggedServiceIds.add(sId.trim());
                                }
                            }
                        }

                        List<QuotationDetailResponse> filteredDetails = new java.util.ArrayList<>();
                        if (allDetails != null) {
                            for (QuotationDetailResponse detail : allDetails) {
                                if (detail.getType() != null && detail.getType() == 2) {
                                    // It's a service, check if it's in the loggedServiceIds
                                    if (detail.getItemId() != null && loggedServiceIds.contains(String.valueOf(detail.getItemId()))) {
                                        List<ServiceShiftResponse> serviceShiftResponses = serviceMapper.getServiceShift(detail.getItemId());
                                        detail.setServiceShiftResponse(serviceShiftResponses);
                                        filteredDetails.add(detail);
                                    }
                                } else {
                                    // Not a service (e.g. product), keep it
                                    filteredDetails.add(detail);
                                }
                            }
                        }
                        response.setDetails(filteredDetails);

                        resultList.add(response);
                    }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-history-print", null, null, "Report (Customer History)", "Report (Customer History)", "Print", 1, "Success", startDuration, endDuration, httpServletRequest);

            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", resultList, true));

        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-report/customer-history-print", line, error.toString(), "Report (Customer History)", "Report (Customer History)", "Print", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error: " + error.getMessage(), null, false));
        }

    }
}
