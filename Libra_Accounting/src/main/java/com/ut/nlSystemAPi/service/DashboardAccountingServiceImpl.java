package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.DashboardAccountingMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ReportMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.ProfitLossDashboardReport;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ARDetailReportFilter;
import com.ut.nlSystemAPi.model.filter.DashboardFilter;
import com.ut.nlSystemAPi.model.response.DashboradAccounting.*;
import com.ut.nlSystemAPi.model.response.Report.ARListDetailReportResponse;
import com.ut.nlSystemAPi.model.response.Report.ApListDetailReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class DashboardAccountingServiceImpl implements DashboardAccountingService {

    @Autowired
    private DashboardAccountingMapper dashboardAccountingMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    
    public ResponseMessage<BaseResult> getListGrowthRate(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            List<GrowthRateDashboardResponse> growthRateDashboardResponses = dashboardAccountingMapper.getListGrowthRate(filter);

            // Compute grand totals over the selected period and growth vs last year
            List<GrowthRateDetailDashboardResponse> growthRateDetailDashboardResponses = dashboardAccountingMapper.getListGrowthRateDetail(filter);
            double sumThis = 0D;
            double sumLast = 0D;
            if (growthRateDetailDashboardResponses != null && !growthRateDetailDashboardResponses.isEmpty()) {
                for (GrowthRateDetailDashboardResponse d : growthRateDetailDashboardResponses) {
                    if (d != null) {
                        sumThis += d.getGrandTotalThisYear() != null ? d.getGrandTotalThisYear() : 0D;
                        sumLast += d.getGrandTotalLastYear() != null ? d.getGrandTotalLastYear() : 0D;
                    }
                }
            }
            double grandGrowth = (sumLast != 0D) ? ((sumThis - sumLast) / sumLast) * 100D : 0D;

            if (growthRateDashboardResponses == null) {
                growthRateDashboardResponses = new ArrayList<>();
            }
            if (growthRateDashboardResponses.isEmpty()) {
                growthRateDashboardResponses.add(new GrowthRateDashboardResponse());
            }
            growthRateDashboardResponses.get(0).setGrandTotalThisYear(sumThis);
            growthRateDashboardResponses.get(0).setGrandTotalLastYear(sumLast);
            growthRateDashboardResponses.get(0).setGrandGrowthRate(grandGrowth);
            

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Growth rate/list", null, null, "Growth Rate", "Growth Rate(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", growthRateDashboardResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Growth rate/list", line, error.toString(), "Growth rate", "Growth rate (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    public ResponseMessage<BaseResult> getList(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long userId = userService.getUserAuth().getId();
        String tableName = "general_ledger_detail_bl_dash_" + userId;

        try {
            System.out.println("tableName: " + tableName);
            dashboardAccountingMapper.createTable(tableName);
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            System.out.println("Finish profitbose + 01");

            List<ProfileAndLostDashboardResponse> responses = fetchDashboardData(filter);

            List<ListProfitAndLostResponse> listProfitAndLostResponses = new ArrayList<>();

            System.out.println("responses: PL" + responses);
            if(responses.size() > 0){
                insertDataIntoGeneralLedger(responses, tableName);
                listProfitAndLostResponses = dashboardAccountingMapper.getListProfitAndLost(filter, tableName);
            }

            System.out.println("Finish profitbose");
            dashboardAccountingMapper.dropTable(tableName);

            System.out.println("FINISHED");
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/journal-entry/list", null, null, "Journal Entry", "Journal Entry (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", listProfitAndLostResponses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("Date format Type/add", 1033L, error.toString(), "Date format Type", "Date format Type (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
// **Helper Functions**

    private List<ProfileAndLostDashboardResponse> fetchDashboardData(DashboardFilter filter) {
        return dashboardAccountingMapper.getList(filter);
    }

    private void insertDataIntoGeneralLedger(List<ProfileAndLostDashboardResponse> responses, String tableName) {
        if (responses != null && !responses.isEmpty()) {
            for (ProfileAndLostDashboardResponse response : responses) {
                ProfitLossDashboardReport report = mapToProfitLossDashboardReport(response);
                dashboardAccountingMapper.insertGeneralLedgerDetail(report, tableName);
            }
        }
    }

    private ProfitLossDashboardReport mapToProfitLossDashboardReport(ProfileAndLostDashboardResponse response) {
        ProfitLossDashboardReport report = new ProfitLossDashboardReport();
        report.setDate(response.getDate());
        report.setBranchId(response.getBranchId());
        report.setChartAccountId(response.getChartAccountId());
        report.setCompanyId(response.getCompanyId());
        report.setLocationId(response.getLocationId());
        report.setCustomerId(response.getCustomerId());
        report.setVendorId(response.getVendorId());
        report.setEmployeeId(response.getEmployeeId());
        report.setOtherId(response.getOtherId());
        report.setClassId(response.getClassId());
        report.setDebit(response.getDebit());
        report.setCredit(response.getCredit());
        return report;
    }

    public ResponseMessage<BaseResult> getListRevenue(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            List<RevenueDashboardResponse> revenueDashboardResponses = dashboardAccountingMapper.getListRevenue(filter);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Revenue/list", null, null, "Revenue", "Revenue (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", revenueDashboardResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Revenue/list", line, error.toString(), "Revenue", "Revenue (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    public ResponseMessage<BaseResult> getListExpenditure(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            System.out.println(filter);

            List<ExpenditureDashboardResponse> expenditureDashboardResponses = dashboardAccountingMapper.getListExpenditure(filter);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Expenditure/list", null, null, "Expenditure", "Expenditure (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", expenditureDashboardResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Expenditure/list", line, error.toString(), "Expenditure", "Expenditure (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    
    public ResponseMessage<BaseResult> getListTotalByQuarter(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            List<TotalSaleByQuarterDashboardResponse> totalSaleByQuarterDashboardResponses = dashboardAccountingMapper.getListTotalSaleByQuarter(filter);

            if(totalSaleByQuarterDashboardResponses.size() > 0){
                Double grandTotal = 0D;
                for (int i = 0; i < totalSaleByQuarterDashboardResponses.size(); i++) {
                    String monthFrom = totalSaleByQuarterDashboardResponses.get(i).getMonthFrom();
                    String monthTo = totalSaleByQuarterDashboardResponses.get(i).getMonthTo();
                    List<TotalSaleByQuarterDetailDashboardResponse> totalDetail = dashboardAccountingMapper.getListDetailSaleByQuarter(filter, monthFrom, monthTo);
                    totalSaleByQuarterDashboardResponses.get(i).setDetailResponses(totalDetail);

                    // Find quater total amount!
                    if(totalDetail.size() > 0){
                        Double quaterAmount = 0D;
                        for(int j=0; j <totalDetail.size(); j++){
                            quaterAmount +=  totalDetail.get(j).getTotalAmount();
                        }
                        grandTotal += quaterAmount;
                    }

                    totalSaleByQuarterDashboardResponses.get(0).setGrandTotalAmount(grandTotal);
                }
            }
            
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/get list total quarter/list", null, null, "get list total quarter", "get list total quarter(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", totalSaleByQuarterDashboardResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase currencies/list", line, error.toString(), "purchase currencies", "purchase to top vendor (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getListCustomerSegmentation(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);
            List<CustomerSegmentDashboardResponse> customerSegmentDashboardResponses = dashboardAccountingMapper.getListCustomerSegmentation(filter);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/CustomerSegmentation/list", null, null, "CustomerSegmentation", "CustomerSegmentation (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", customerSegmentDashboardResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/CustomerSegmentation/list", line, error.toString(), "CustomerSegmentation", "CustomerSegmentation (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    
    public ResponseMessage<BaseResult> getListSaleTopCustomer(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            List<CustomerSegmentDashboardResponse> customerSegmentDashboardResponses = dashboardAccountingMapper.getListSaleTopCustomer(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/SaleTOpCustomer/list", null, null, "SaleTOpCustomer", "SaleTOpCustomer (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", customerSegmentDashboardResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/SaleTOpCustomer/list", line, error.toString(), "SaleTOpCustomer", "SaleTOpCustomer (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    
    public ResponseMessage<BaseResult> listSaleStatus(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            List<ExpenditureDashboardResponse> customerSegmentDashboardResponses = new ArrayList<>();

            List<ExpenditureDashboardResponse> invoice = dashboardAccountingMapper.getListInvoice(filter);
            List<ExpenditureDashboardResponse> voidInvoice = dashboardAccountingMapper.getListVoidInvoice(filter);
            List<ExpenditureDashboardResponse> cmInvoice = dashboardAccountingMapper.getListCmInvoice(filter);
            List<ExpenditureDashboardResponse> discountInvoice = dashboardAccountingMapper.getListDiscountInvoice(filter);

            customerSegmentDashboardResponses.addAll(invoice);
            customerSegmentDashboardResponses.addAll(cmInvoice);
            customerSegmentDashboardResponses.addAll(discountInvoice);
            customerSegmentDashboardResponses.addAll(voidInvoice);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/SaleTOpCustomer/list", null, null, "SaleTOpCustomer", "SaleTOpCustomer (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", customerSegmentDashboardResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/SaleTOpCustomer/list", line, error.toString(), "SaleTOpCustomer", "SaleTOpCustomer (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    
    public ResponseMessage<BaseResult> listSaleTopItem(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            List<InvoiceCurrenciesDashboardResponse> customerSegmentDashboardResponses = dashboardAccountingMapper.getListSaleTopItem(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/SaleTOpCustomer/list", null, null, "SaleTOpCustomer", "SaleTOpCustomer (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", customerSegmentDashboardResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/SaleTOpCustomer/list", line, error.toString(), "SaleTOpCustomer", "SaleTOpCustomer (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getListInvoiceCurrencies(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            List<InvoiceCurrenciesDashboardResponse> invoiceCurrenciesDashboardResponses = dashboardAccountingMapper.getInvoiceCurrencies(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/InvoiceCurrencies/list", null, null, "InvoiceCurrencies", "InvoiceCurrencies (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", invoiceCurrenciesDashboardResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/InvoiceCurrencies/list", line, error.toString(), "InvoiceCurrencies", "InvoiceCurrencies (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getListSaleByProductGroup(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            List<SaleByproductGroupDashboardResponse> saleByproductGroupDashboardResponses = dashboardAccountingMapper.getListSaleByProductGroup(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/SaleByProductGroup/list", null, null, "SaleByProductGroup", "SaleByProductGroup (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", saleByproductGroupDashboardResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/SaleByProductGroup/list", line, error.toString(), "SaleByProductGroup", "SaleByProductGroup (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getListSaleYearToDate(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);
            List<SaleYearToDateDashboardResponse> saleYearToDateDashboardResponses = dashboardAccountingMapper.getListSaleYearToDate(filter);

            List<YearToDateDetailResponse> yearToDateDetailResponses = new ArrayList<>();

            if(saleYearToDateDashboardResponses.size() > 0){
                yearToDateDetailResponses = dashboardAccountingMapper.getListSaleYearToDateDetail(filter);
                if(yearToDateDetailResponses.size() > 0){
                    yearToDateDetailResponses.get(0).setLargestAmount(saleYearToDateDashboardResponses.get(0).getTotalAmount());
                    yearToDateDetailResponses.get(0).setTotalInvoice(saleYearToDateDashboardResponses.get(0).getTotalInvoice());
                    yearToDateDetailResponses.get(0).setAverage(saleYearToDateDashboardResponses.get(0).getAverage());
                    yearToDateDetailResponses.get(0).setLargest(saleYearToDateDashboardResponses.get(0).getLargest());
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/tSaleYearToDate/list", null, null, "tSaleYearToDate", "tSaleYearToDate (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", yearToDateDetailResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/tSaleYearToDate/list", line, error.toString(), "tSaleYearToDate", "tSaleYearToDate (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getListPurchaseToVendor(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);
            List<PurchaseTopVendorResponse> purchaseTopVendorResponses = dashboardAccountingMapper.getListPurchaseToVendor(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase to top vendor/list", null, null, "purchase to top vendor", "purchase to top vendor (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", purchaseTopVendorResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase to top vendor/list", line, error.toString(), "purchase to top vendor", "purchase to top vendor (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getListPurchaseCurrencies(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            filter.setUserId(userId);
            Pagination pagination = new Pagination();
            Integer page = filter.getPage() == null ? 1 : filter.getPage();
            Integer rpp = filter.getRowsPerPage() == null ? 10 : filter.getRowsPerPage();
            pagination.setPage(page);
            pagination.setRowsPerPage(rpp);
            // Optional: this count is not meaningful for this aggregate; set to size after fetch
            // pagination.setTotal(dashboardAccountingMapper.countList(filter));
            if (page != null && rpp != null) {
                filter.setPage((page - 1) * rpp);
            } else {
                filter.setPage(0);
            }

            List<PurchaseCurrenciesResponse> purchaseCurrenciesResponses = dashboardAccountingMapper.getListPurchaseCurrencies(filter);

            // Set total to number of rows returned
            pagination.setTotal((long) (purchaseCurrenciesResponses != null ? purchaseCurrenciesResponses.size() : 0));
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase currencies/list", null, null, "purchase currencies", "purchase currencies(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", purchaseCurrenciesResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase currencies/list", line, error.toString(), "purchase currencies", "purchase to top vendor (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getListTotalPurchaseYearToDate(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            List<PurchaseYearToDateResponse> purchaseCurrenciesResponses = dashboardAccountingMapper.getPurchaseYearToDate(filter);

            List<PurchaseYearToDateDetailResponse> purchaseYearToDateDetailResponses = new ArrayList<>();

            if(purchaseCurrenciesResponses.size() > 0) {
                purchaseYearToDateDetailResponses = dashboardAccountingMapper.getPurchaseYearToDateDetail(filter);
                if(purchaseYearToDateDetailResponses.size() > 0){
                    purchaseCurrenciesResponses.get(0).setBills(purchaseYearToDateDetailResponses.get(0).getBills());
                    purchaseCurrenciesResponses.get(0).setTotalAmount(purchaseYearToDateDetailResponses.get(0).getTotalAmount());
                    purchaseCurrenciesResponses.get(0).setAverage(purchaseYearToDateDetailResponses.get(0).getAverage());
                    purchaseCurrenciesResponses.get(0).setLargest(purchaseYearToDateDetailResponses.get(0).getLargest());
                }
            }
            
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("TotalPurchaseYearToDate/list", null, null, "TotalPurchaseYearToDate", "TotalPurchaseYearToDate(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", purchaseCurrenciesResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/TotalPurchaseYearToDate/list", line, error.toString(), "TotalPurchaseYearToDate", "TotalPurchaseYearToDate(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getListSaleTargetVsActual(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            List<SaleTargetVsActualResponse> saleTargetVsActualResponses = dashboardAccountingMapper.getSaleTargetVsActual(filter);
            List<SaleTargetResponse> saleTargetResponses = dashboardAccountingMapper.getListTarget(filter);
            Double grandTotalActual = 0D;
            Double grandTotalTarget = 0D;

            boolean hasTargets = saleTargetResponses != null && !saleTargetResponses.isEmpty();
            if (hasTargets) {
                double totalTarget = 0D;
                for (SaleTargetResponse tr : saleTargetResponses) {
                    double q1 = tr.getAmountQ1() != null ? tr.getAmountQ1() : 0D;
                    double q2 = tr.getAmountQ2() != null ? tr.getAmountQ2() : 0D;
                    double q3 = tr.getAmountQ3() != null ? tr.getAmountQ3() : 0D;
                    double q4 = tr.getAmountQ4() != null ? tr.getAmountQ4() : 0D;
                    totalTarget += (q1 + q2 + q3 + q4);
                }
                grandTotalTarget = totalTarget;
            }

            if (saleTargetVsActualResponses.size() > 0) {
                for (int i = 0; i < saleTargetVsActualResponses.size(); i++) {
                    SaleTargetVsActualResponse actualResponse = saleTargetVsActualResponses.get(i);
                    grandTotalActual += actualResponse.getActualAmount() != null ? actualResponse.getActualAmount() : 0D;

                    double targetAmount = 0.0;
                    if (hasTargets) {
                        SaleTargetResponse targetResponse = saleTargetResponses.get(i % saleTargetResponses.size());
                        switch (i % 4) {
                            case 0:
                                targetAmount = targetResponse.getAmountQ1() != null ? targetResponse.getAmountQ1() : 0D;
                                break;
                            case 1:
                                targetAmount = targetResponse.getAmountQ2() != null ? targetResponse.getAmountQ2() : 0D;
                                break;
                            case 2:
                                targetAmount = targetResponse.getAmountQ3() != null ? targetResponse.getAmountQ3() : 0D;
                                break;
                            case 3:
                                targetAmount = targetResponse.getAmountQ4() != null ? targetResponse.getAmountQ4() : 0D;
                                break;
                        }
                    }
                    actualResponse.setTargetAmount(targetAmount);

                    if (targetAmount != 0) {
                        double actual = actualResponse.getActualAmount() != null ? actualResponse.getActualAmount() : 0D;
                        Double average = (actual / targetAmount) * 100;
                        actualResponse.setAverage(average);
                    } else {
                        actualResponse.setAverage(0.0);
                    }
                }
                saleTargetVsActualResponses.get(0).setGrandActualAmount(grandTotalActual);
                saleTargetVsActualResponses.get(0).setGrandTargetAmount(grandTotalTarget);
                if (grandTotalTarget != 0) {
                    Double AverageGrandActual = (grandTotalActual / grandTotalTarget) * 100;
                    saleTargetVsActualResponses.get(0).setAverageGrandActualAmount(AverageGrandActual);
                } else {
                    saleTargetVsActualResponses.get(0).setAverageGrandActualAmount(0D);
                }
                saleTargetVsActualResponses.get(0).setAverageGrandTargetAmount(100D);
            }
            
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("SaleTargetVsActual/list", null, null, "SaleTargetVsActual", "SaleTargetVsActual(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", saleTargetVsActualResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/SaleTargetVsActual/list", line, error.toString(), "SaleTargetVsActual", "SaleTargetVsActual(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getListGraph(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);
            List<GraphCostOfGoodsRaw> rawRows = dashboardAccountingMapper.getListGraph(filter);

            List<GraphDashboardResponse> graphDashboardResponse = buildCostOfGoodsGraph(rawRows);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/get list total graph/list", null, null, "get list total graph", "get list total quarter(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", graphDashboardResponse, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/get list total graph/list", line, error.toString(), "total graph", "total graph (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private List<GraphDashboardResponse> buildCostOfGoodsGraph(List<GraphCostOfGoodsRaw> rawRows) {
        Map<Integer, GraphDashboardResponse> monthMap = new TreeMap<>();

        if (rawRows != null) {
            for (GraphCostOfGoodsRaw row : rawRows) {
                if (row == null || row.getMonth() == null || row.getAmount() == null) {
                    continue;
                }

                Integer month = row.getMonth();
                if (month == null || month < 1 || month > 12) {
                    continue;
                }

                GraphDashboardResponse monthData = monthMap.computeIfAbsent(month, key -> {
                    GraphDashboardResponse response = new GraphDashboardResponse();
                    response.setMonth(Long.valueOf(key));
                    response.setSmc(0D);
                    response.setOmRon(0D);
                    response.setGeneral(0D);
                    return response;
                });

                double amount = row.getAmount() != null ? row.getAmount() : 0D;
                Long parentGroupId = row.getParentGroupId();
                String parentGroupName = row.getParentGroupName();

                if (isSmc(parentGroupId, parentGroupName)) {
                    monthData.setSmc(monthData.getSmc() + amount);
                } else if (isOmRon(parentGroupId, parentGroupName)) {
                    monthData.setOmRon(monthData.getOmRon() + amount);
                } else {
                    monthData.setGeneral(monthData.getGeneral() + amount);
                }
            }
        }

        return new ArrayList<>(monthMap.values());
    }

    private boolean isSmc(Long parentGroupId, String parentGroupName) {
        if (parentGroupId != null && Long.valueOf(1).equals(parentGroupId)) {
            return true;
        }
        return parentGroupName != null && parentGroupName.trim().equalsIgnoreCase("SMC");
    }

    private boolean isOmRon(Long parentGroupId, String parentGroupName) {
        if (parentGroupId != null && Long.valueOf(2).equals(parentGroupId)) {
            return true;
        }
        if (parentGroupName == null) {
            return false;
        }
        String normalized = parentGroupName.replaceAll("\\s+", "").toLowerCase();
        return "omron".equals(normalized);
    }

    public ResponseMessage<BaseResult> getAccountPayableAging(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            LocalDate targetDate = LocalDate.now();
            List<GraphicNetDayResponse> netDayResponses = dashboardAccountingMapper.getListNetDayGraphic(filter);
            System.out.println("netDayResponses: " + netDayResponses);

//            long throughDay = resolveThroughDay(netDayResponses);
            long throughDay = 90;
            System.out.println("throughDay: " + throughDay);
            long intervalDay = 30L;
            if (throughDay < intervalDay) {
                throughDay = intervalDay;
            }

            ARDetailReportFilter apFilter = new ARDetailReportFilter();
            apFilter.setDate(String.valueOf(targetDate));
            apFilter.setIntervalDay(intervalDay);
            apFilter.setThroughDay(throughDay);
            apFilter.setBranchId(filter.getBranchId());
            apFilter.setUserId(userId);

            List<GraphicResponseResult> graphicResponseResult = new ArrayList<>();
            double totalOutstanding = 0D;
            double totalOverdue = 0D;

            // Today bucket (net day = 0)
            GraphicResponseResult todayBucket = buildApBucket(apFilter, 0L, 0L, targetDate, 0L);
            graphicResponseResult.add(todayBucket);
            totalOutstanding += safeAmount(todayBucket.getOutstandingAmount());
            totalOverdue += safeAmount(todayBucket.getOverdueAmount());

            long iterations = (long) Math.ceil((double) throughDay / intervalDay);
            long currentStart = 1L;
            for (int i = 0; i < iterations; i++) {
                long rangeEnd = Math.min(currentStart + intervalDay - 1, throughDay);
                GraphicResponseResult bucket = buildApBucket(apFilter, currentStart, rangeEnd, targetDate, rangeEnd);
                totalOutstanding += safeAmount(bucket.getOutstandingAmount());
                totalOverdue += safeAmount(bucket.getOverdueAmount());
                graphicResponseResult.add(bucket);
                currentStart = rangeEnd + 1;
            }

            // Over bucket (> throughDay)
            GraphicResponseResult overBucket = buildApBucket(apFilter, throughDay + 1, null, targetDate, throughDay + 1);
            if (safeAmount(overBucket.getOutstandingAmount()) > 0D || safeAmount(overBucket.getOverdueAmount()) > 0D) {
                totalOutstanding += safeAmount(overBucket.getOutstandingAmount());
                totalOverdue += safeAmount(overBucket.getOverdueAmount());
                graphicResponseResult.add(overBucket);
            }

            if (!graphicResponseResult.isEmpty()) {
                GraphicResponseResult summary = graphicResponseResult.get(0);
                summary.setGrandOutstandingAmount(totalOutstanding);
                summary.setGrandOverdueAmount(totalOverdue);
                summary.setGrandTotalAmount(totalOutstanding + totalOverdue);
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Account payable aging/list", null, null, "Account payable aging", "get list total quarter(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", graphicResponseResult, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/get list total graph/list", line, error.toString(), "total graph", "total graph (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getAccountReceivable(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            filter.setUserId(userId);

            LocalDate targetDate = LocalDate.now();
            List<GraphicNetDayResponse> netDayResponses = dashboardAccountingMapper.getListNetDayGraphic(filter);
            System.out.println("netDayResponses: " + netDayResponses);

//            long throughDay = resolveThroughDay(netDayResponses);
            long throughDay = 90;
            System.out.println("throughDay: " + throughDay);
            long intervalDay = 30L;
            if (throughDay < intervalDay) {
                throughDay = intervalDay;
            }

            ARDetailReportFilter arFilter = new ARDetailReportFilter();
            arFilter.setDate(String.valueOf(targetDate));
            arFilter.setIntervalDay(intervalDay);
            arFilter.setThroughDay(throughDay);
            arFilter.setBranchId(filter.getBranchId());
            arFilter.setUserId(userId);

            List<GraphicResponseResult> graphicResponseResult = new ArrayList<>();
            double totalOutstanding = 0D;
            double totalOverdue = 0D;

            // Today bucket (net day = 0)
            GraphicResponseResult todayBucket = buildArBucket(arFilter, 0L, 0L, targetDate, 0L);
            graphicResponseResult.add(todayBucket);
            totalOutstanding += safeAmount(todayBucket.getOutstandingAmount());
            totalOverdue += safeAmount(todayBucket.getOverdueAmount());

            long iterations = (long) Math.ceil((double) throughDay / intervalDay);
            long currentStart = 1L;
            for (int i = 0; i < iterations; i++) {
                long rangeEnd = Math.min(currentStart + intervalDay - 1, throughDay);
                GraphicResponseResult bucket = buildArBucket(arFilter, currentStart, rangeEnd, targetDate, rangeEnd);
                totalOutstanding += safeAmount(bucket.getOutstandingAmount());
                totalOverdue += safeAmount(bucket.getOverdueAmount());
                graphicResponseResult.add(bucket);
                currentStart = rangeEnd + 1;
            }

            // Over bucket (> throughDay)
            GraphicResponseResult overBucket = buildArBucket(arFilter, throughDay + 1, null, targetDate, throughDay + 1);
            if (safeAmount(overBucket.getOutstandingAmount()) > 0D || safeAmount(overBucket.getOverdueAmount()) > 0D) {
                totalOutstanding += safeAmount(overBucket.getOutstandingAmount());
                totalOverdue += safeAmount(overBucket.getOverdueAmount());
                graphicResponseResult.add(overBucket);
            }

            if (!graphicResponseResult.isEmpty()) {
                GraphicResponseResult summary = graphicResponseResult.get(0);
                summary.setGrandOutstandingAmount(totalOutstanding);
                summary.setGrandOverdueAmount(totalOverdue);
                summary.setGrandTotalAmount(totalOutstanding + totalOverdue);
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Account Reconcile aging/list", null, null, "Account Reconcile aging", "getList Account reconcile(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", graphicResponseResult, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Account Reconcile aging/list", line, error.toString(), "total graph", "getList account reconcile (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getAccountGraphicPayable(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
//           Outstanding Account Payable Aging

            filter.setUserId(userId);
            List<GraphicNetDayResponse> graphicNetDayResponses = dashboardAccountingMapper.getListNetDayGraphic(filter);

            System.out.println(graphicNetDayResponses);
            List<GraphicResponseResult> graphicResponseResult = new ArrayList<>();

            for(int i=0;i<graphicNetDayResponses.size();i++) {
                List<GraphicResponseAP> amountGraphicOutStandingFirst = dashboardAccountingMapper.getLisAmountGraphicFirstOutStanding(filter,graphicNetDayResponses.get(i).getNetDay());

                String saleOrderIdStringAR = amountGraphicOutStandingFirst.get(0).getPurchaseOrderId();
                String arrMainGlId = amountGraphicOutStandingFirst.get(0).getArrMainGlId();

                List<GraphicResponseAP> amountGraphicOutStandingSecond = dashboardAccountingMapper.getLisAmountGraphicSecondOutStanding(filter, saleOrderIdStringAR, arrMainGlId,graphicNetDayResponses.get(i).getNetDay());

                List<GraphicResponseAP> amountGraphicOutStandingThird = dashboardAccountingMapper.getLisAmountGraphicThirdOutStanding(filter, graphicNetDayResponses.get(i).getNetDay());
                String purchaseReturnId = amountGraphicOutStandingThird.get(0).getPurchaseReturnId();

                List<GraphicResponseAP> amountGraphicOutStandingFourth = dashboardAccountingMapper.getLisAmountGraphicFourthOutStanding(filter,purchaseReturnId,graphicNetDayResponses.get(i).getNetDay());
                if (amountGraphicOutStandingFourth.isEmpty()) {
                    GraphicResponseAP graphicResponseAP = new GraphicResponseAP();
                    graphicResponseAP.setAmount(0D);
                }
                
                Double AmountOutstanding = amountGraphicOutStandingFirst.get(0).getAmount() + 0 - amountGraphicOutStandingSecond.get(0).getAmount() - amountGraphicOutStandingThird.get(0).getAmount();



                //Overdue Account Payable Aging
                List<GraphicResponseAP> resultAmountGraphicFirstOverdue = dashboardAccountingMapper.getAmountGraphicFirstOverdue(filter,graphicNetDayResponses.get(i).getNetDay());
                String purchaseOrderId = resultAmountGraphicFirstOverdue.get(0).getPurchaseOrderId();
                String arrMainGlIdOverdue = resultAmountGraphicFirstOverdue.get(0).getArrMainGlId();


                List<GraphicResponseAP> resultAmountGraphicSecondOverdue = dashboardAccountingMapper.getAmountGraphicSecondOverdue(filter, purchaseOrderId, arrMainGlIdOverdue,graphicNetDayResponses.get(i).getNetDay());
                List<GraphicResponseAP> resultAmountGraphicThirdOverdue = dashboardAccountingMapper.getAmountGraphicThirdOverdue(filter,graphicNetDayResponses.get(i).getNetDay());
                String purchaseReturnIdThird = resultAmountGraphicThirdOverdue.get(0).getPurchaseReturnId();
                List<GraphicResponseAP> resultAmountGraphicFourthOverdue = dashboardAccountingMapper.getAmountGraphicFourthOverdue(filter, purchaseReturnIdThird,graphicNetDayResponses.get(i).getNetDay());
                if (resultAmountGraphicFourthOverdue.isEmpty()) {
                    AgingReportResult agingReportResult = new AgingReportResult();
                    agingReportResult.setAmount(0D);
                }
                Double OverDueAmountAR = resultAmountGraphicFirstOverdue.get(0).getAmount() + 0 -resultAmountGraphicSecondOverdue.get(0).getAmount() - resultAmountGraphicThirdOverdue.get(0).getAmount();


                // Final Response

                GraphicResponseResult graphicResponseResult1 = new GraphicResponseResult();
                graphicResponseResult.add(graphicResponseResult1);
                graphicResponseResult.get(i).setNetDay(graphicNetDayResponses.get(i).getNetDay());
                graphicResponseResult.get(i).setOutstandingAmount(AmountOutstanding);
                graphicResponseResult.get(i).setOverdueAmount(OverDueAmountAR);

            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Account graphic payable/list", null, null, "Account graphic payable", "getList Account graphic payable(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", graphicResponseResult, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Account graphic payable/list", line, error.toString(), "total graphic payable ", "getList account graphic payable (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getAccountGraphicReconcile(DashboardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Currency Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(dashboardAccountingMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
//           Outstanding Account Reconcile Aging
            filter.setUserId(userId);
            List<GraphicNetDayResponse> graphicNetDayResponses = dashboardAccountingMapper.getListNetDayGraphic(filter);
            List<GraphicResponseResult> graphicResponseResult = new ArrayList<>();

            System.out.println(graphicNetDayResponses);

            for(int i=0;i<graphicNetDayResponses.size();i++) {
                List<GraphicResponseAR> amountGraphicOutStandingFirstAR = dashboardAccountingMapper.getLisAmountGraphicFirstOutStandingAR(filter,graphicNetDayResponses.get(i).getNetDay());

                Double AmountOutstanding = 0D;

                String saleOrderIdStringAR = "";

                if(amountGraphicOutStandingFirstAR.get(0) != null){
                    System.out.println(amountGraphicOutStandingFirstAR);
                    saleOrderIdStringAR = amountGraphicOutStandingFirstAR.get(0).getSaleOrderId();
                    String arrMainGlId = amountGraphicOutStandingFirstAR.get(0).getArrMainGlId();
    
                    List<GraphicResponseAR> amountGraphicOutStandingSecondAR = dashboardAccountingMapper.getLisAmountGraphicSecondOutStandingAR(filter,saleOrderIdStringAR,arrMainGlId,graphicNetDayResponses.get(i).getNetDay());
    
                    List<GraphicResponseAR> amountGraphicOutStandingThirdAR = dashboardAccountingMapper.getLisAmountGraphicThirdOutStandingAR(filter,saleOrderIdStringAR);
    
                    String creditMemoId = amountGraphicOutStandingThirdAR.get(0).getCreditMemo();
    
                    List<GraphicResponseAR> amountGraphicOutStandingFourthAR = dashboardAccountingMapper.getLisAmountGraphicFourthOutStandingAR(filter,creditMemoId);
                    if (amountGraphicOutStandingFourthAR.isEmpty()) {
                        GraphicResponseAR graphicResponseAR = new GraphicResponseAR();
                        graphicResponseAR.setAmount(0D);
                    }
                    AmountOutstanding = amountGraphicOutStandingFirstAR.get(0).getAmount() + 0 - amountGraphicOutStandingSecondAR.get(0).getAmount() - amountGraphicOutStandingThirdAR.get(0).getAmount();
                }

                System.out.println("AmountOutstanding: " + AmountOutstanding);
                
                // Overdue Account Payable Aging


                List<GraphicResponseAR> resultAmountGraphicFirstOverdueAR = dashboardAccountingMapper.getAmountGraphicFirstOverdueAR(filter,graphicNetDayResponses.get(i).getNetDay());

                Double OverDueAmountAR = 0D;
                if(resultAmountGraphicFirstOverdueAR.get(0) != null){
                    String saleOrderId = resultAmountGraphicFirstOverdueAR.get(0).getSaleOrderId();
                    String arrGlIdOverdueAr = resultAmountGraphicFirstOverdueAR.get(0).getArrMainGlId();

                    List<GraphicResponseAR> resultAmountGraphicSecondOverdueAR = dashboardAccountingMapper.getAmountGraphicSecondOverdueAR(filter, saleOrderId, arrGlIdOverdueAr,graphicNetDayResponses.get(i).getNetDay());
                    List<GraphicResponseAR> resultAmountGraphicThirdOverdueAR = dashboardAccountingMapper.getAmountGraphicThirdOverdueAR(filter,saleOrderIdStringAR);
                    String creditMemo = resultAmountGraphicThirdOverdueAR.get(0).getCreditMemo();
                    List<GraphicResponseAR> resultAmountGraphicFourthOverdueAR = dashboardAccountingMapper.getAmountGraphicFourthOverdueAR(filter,creditMemo);
                    if (resultAmountGraphicFourthOverdueAR.isEmpty()) {
                        AgingReportResult agingReportResult = new AgingReportResult();
                        agingReportResult.setAmount(0D);
                    }
                    OverDueAmountAR = resultAmountGraphicFirstOverdueAR.get(0).getAmount() + 0 -resultAmountGraphicSecondOverdueAR.get(0).getAmount() - resultAmountGraphicThirdOverdueAR.get(0).getAmount();
                }

                System.out.println("OverDueAmountAR: " + OverDueAmountAR);
                
                // Final Response
                GraphicResponseResult graphicResponseResult1 = new GraphicResponseResult();
                graphicResponseResult.add(graphicResponseResult1);
                graphicResponseResult.get(i).setNetDay(graphicNetDayResponses.get(i).getNetDay());
                graphicResponseResult.get(i).setOutstandingAmount(AmountOutstanding);
                graphicResponseResult.get(i).setOverdueAmount(OverDueAmountAR);
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Account graphic Reconcile/list", null, null, "Account graphic reconcile", "getList Account graphic reconcile(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", graphicResponseResult, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Account graphic reconcile/list", line, error.toString(), "total graphic reconcile ", "getList account graphic reconcile (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private LocalDate resolveTargetDate(DashboardFilter filter) {
        if (hasText(filter.getDateTo())) {
            return parseDate(filter.getDateTo());
        }
        if (hasText(filter.getDateFrom())) {
            return parseDate(filter.getDateFrom());
        }
        return LocalDate.now();
    }

    private long resolveThroughDay(List<GraphicNetDayResponse> netDayResponses) {
        long max = 0L;
        if (netDayResponses != null) {
            for (GraphicNetDayResponse response : netDayResponses) {
                if (response != null && response.getNetDay() != null) {
                    max = Math.max(max, response.getNetDay());
                }
            }
        }
        return Math.max(max, 60L);
    }

    private GraphicResponseResult buildApBucket(ARDetailReportFilter filter, Long fromNetDay, Long toNetDay, LocalDate targetDate, Long netDayLabel) {
        List<ApListDetailReportResponse> records = reportMapper.getListAPDetailByTermRange(filter, fromNetDay, toNetDay);
        double outstanding = 0D;
        double overdue = 0D;
        if (records != null) {
            for (ApListDetailReportResponse record : records) {
                Double balance = record != null ? safeAmount(record.getOpenBalance()) : 0D;
                if (balance == 0D) {
                    continue;
                }
                LocalDate dueDate = resolveApDueDate(record);
                if (dueDate != null && (dueDate.isAfter(targetDate) || dueDate.isEqual(targetDate))) {
                    outstanding += balance;
                } else {
                    overdue += balance;
                }
            }
        }
        outstanding = Math.max(outstanding, 0D);
        overdue = Math.max(overdue, 0D);
        GraphicResponseResult bucket = new GraphicResponseResult();
        bucket.setNetDay(netDayLabel);
        bucket.setOutstandingAmount(outstanding);
        bucket.setOverdueAmount(overdue);
        return bucket;
    }

    private LocalDate resolveApDueDate(ApListDetailReportResponse record) {
        if (record == null) {
            return null;
        }
        LocalDate dueDate = parseDate(record.getDueDate());
        if (dueDate != null) {
            return dueDate;
        }
        LocalDate orderDate = parseDate(record.getDate());
        Long netDays = record.getNetDays();
        if (orderDate == null) {
            return null;
        }
        long additional = netDays != null ? netDays : 0L;
        try {
            return orderDate.plusDays(additional);
        } catch (Exception ignored) {
            return null;
        }
    }

    private GraphicResponseResult buildArBucket(ARDetailReportFilter filter, Long fromNetDay, Long toNetDay, LocalDate targetDate, Long netDayLabel) {
        List<ARListDetailReportResponse> records = reportMapper.getListARDetailByTermRange(filter, fromNetDay, toNetDay);
        double outstanding = 0D;
        double overdue = 0D;
        if (records != null) {
            for (ARListDetailReportResponse record : records) {
                Double balance = record != null ? safeAmount(record.getOpenBalance()) : 0D;
                LocalDate dueDate = record != null ? parseDate(record.getDueDate()) : null;
                if (dueDate != null && (dueDate.isAfter(targetDate) || dueDate.isEqual(targetDate))) {
                    outstanding += balance;
                } else {
                    overdue += balance;
                }
            }
        }
        outstanding = Math.max(outstanding, 0D);
        overdue = Math.max(overdue, 0D);
        GraphicResponseResult bucket = new GraphicResponseResult();
        bucket.setNetDay(netDayLabel);
        bucket.setOutstandingAmount(outstanding);
        bucket.setOverdueAmount(overdue);
        return bucket;
    }

    private LocalDate parseDate(String value) {
        if (!hasText(value)) {
            return null;
        }
        try {
            return LocalDate.parse(value);
        } catch (Exception ignored) {
            return null;
        }
    }

    private <T> T firstOrNull(List<T> list) {
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    private Double safeAmount(Double value) {
        return value == null ? 0D : value;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String safeIdList(String value) {
        return hasText(value) ? value : "-1";
    }


}
