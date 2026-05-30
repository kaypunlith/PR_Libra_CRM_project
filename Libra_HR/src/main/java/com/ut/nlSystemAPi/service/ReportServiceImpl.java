package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.Month;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.EmployeesMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ReportMapper;
import com.ut.nlSystemAPi.mapper.primary.StaffLoanMapper;
import com.ut.nlSystemAPi.model.EmployeeFilter;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseFile;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.LeaveReportFilter;
import com.ut.nlSystemAPi.model.filter.ReportAttendanceFilter;
import com.ut.nlSystemAPi.model.response.*;
import com.ut.nlSystemAPi.model.response.StaffLoan.StaffLoanReportResponse;
import com.ut.nlSystemAPi.model.response.StaffLoan.StaffLoanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private EmployeesMapper employeesMapper;

    @Autowired
    private StaffLoanMapper staffLoanMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private MessageService messageService;

    public ResponseMessage<BaseResult> getReportStaffProfile(EmployeeFilter filter) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
//        if (permissionMapper.checkPermission(userId, "Staff Profile (View)") == 0) {
//            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
//        }
        Pagination pagination = new Pagination();
        pagination.setPage(filter.getPage());
        pagination.setRowsPerPage(filter.getRowsPerPage());
        filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

        List<EmployeesResponse> employeesResponses = employeesMapper.getReportStaffProfile(filter, userId);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", employeesResponses, true));
    }

    @Override
    public ResponseMessage<BaseResult> getReportStaffProfileFind(Long id) {
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId, "Employee (View)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        List<EmployeesResponse> employees = employeesMapper.getOne(id);
        if (employees != null && !employees.isEmpty()) {
            for (EmployeesResponse employee : employees) {
                employee.setQrCode("{\"A\":\"?\",\"B\":\"a\",\"C\":\"3\",\"D\":\"%\",\"E\":\"0\",\"F\":\"C\",\"G\":\"7\",\"H\":\"e\",\"I\":\"a\",\"J\":\"9\",\"K\":\"g\",\"L\":\"%\",\"M\":\"<\",\"N\":\"6\",\"O\":\"7\",\"P\":\"!\",\"Q\":\"&\",\"R\":\"C\",\"S\":\"@\",\"T\":\"c\",\"A6Z2\":\""+employee.getSysCode()+"\"}");
                employee.setProfilePhoto(employeesMapper.getProfilePhoto(employee.getId()));
                applyDocuments(employee);
                employee.setDeviceResponses(employeesMapper.getConnectedDevice(employee.getId()));
                employee.setEmployeePositionHistoryList(employeesMapper.listEmpPositonHistory(employee.getId()));
                employee.setEmployeeDepartmentHistoryList(employeesMapper.listEmpDepartmentHistory(employee.getId()));
            }
        }

        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", employees, true));
    }

    private void applyDocuments(EmployeesResponse employee) {
        List<EmployeeDocumentResponse> employeeDocuments = employeesMapper.listEmpDocuments(employee.getId());
        List<BaseFile> documents = new ArrayList<>();
        for (EmployeeDocumentResponse employeeDocument : employeeDocuments) {
            BaseFile file = new BaseFile();
            file.setName(employeeDocument.getFileDocument());
            file.setUrl(employeeDocument.getFilePath());
            documents.add(file);
        }
        employee.setDocuments(documents);
    }

    @Override
    public ResponseMessage<BaseResult> getDeposit(DepositReportFilter filter) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
//        if (permissionMapper.checkPermission(userId, "Report Monthly Deposit (View)") == 0) {
//            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
//        }

        List<DepositReportListYear> yearList = reportMapper.getDepositYear(filter);

        if (yearList != null && yearList.size() > 0) {
            for (int j = 0; j < yearList.size(); j++) {
                yearList.get(j).setDate(filter.getDate());

                // list deposit report
                yearList.get(j).setDepositReports(reportMapper.getDeposit(filter));
                List<DepositRequestReport> depositRequestReportList = yearList.get(j).getDepositReports();
                if (depositRequestReportList != null && depositRequestReportList.size() > 0) {
                    for (int i = 0; i < depositRequestReportList.size(); i++) {
                        Long employeesId = depositRequestReportList.get(i).getId();

                        depositRequestReportList.get(i).setAdjustBeginningAmount(
                                reportMapper.getMonthlyAdjustBeginningAmount(employeesId, filter.getDate()));

                        // check history deposit by payroll
                        Float oldAmountHistory = reportMapper.historyAmountDeposit(employeesId, filter.getDate(),
                                filter.getGroupId(), filter.getDepartmentId());

                        // check history adjust beginning amount
                        Float oldAmountAdjustBeginning = reportMapper
                                .getHistoryMonthlyAdjustBeginningAmount(employeesId, filter.getDate());
                        float oldAmountDeposit = (oldAmountHistory + oldAmountAdjustBeginning);

                        // check history deposit request
                        Float oldDepositRequest = reportMapper.oldAmountDepositRequest(employeesId, filter.getDate(),
                                filter.getGroupId(), filter.getDepartmentId());

                        if (oldDepositRequest != null && oldDepositRequest > 0) {
                            depositRequestReportList.get(i).setOldSalary(oldAmountDeposit - oldDepositRequest);
                        } else {
                            depositRequestReportList.get(i).setOldSalary(oldAmountDeposit);
                        }

                        // check deposit salary
                        Float depositSalary = reportMapper.depositSalary(employeesId, filter.getDate(),
                                filter.getGroupId(), filter.getDepartmentId());

                        // check deposit salary
                        if (depositSalary == null) {
                            Float cashAmountDeposit = (depositRequestReportList.get(i).getCashAmount()
                                    / depositRequestReportList.get(i).getNumOfMonth());
                            depositRequestReportList.get(i).setDepositSalary(cashAmountDeposit);
                        } else {
                            depositRequestReportList.get(i).setDepositSalary(depositSalary);
                        }

                        // Total new salary
                        Float newSalary = (depositRequestReportList.get(i).getAdjustBeginningAmount()
                                + depositRequestReportList.get(i).getOldSalary()
                                + depositRequestReportList.get(i).getDepositSalary());
                        depositRequestReportList.get(i).setNewSalary(newSalary);
                    }
                }
            }
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", yearList, true));
    }

    // report withdraw deposit
    public ResponseMessage<BaseResult> getDepositRequest(DepositReportFilter filter) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
//        if (permissionMapper.checkPermission(userId, "Report Monthly Deposit (View)") == 0) {
//            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
//        }

        LocalDate dateStatic = LocalDate.parse(filter.getDate());
        LocalDate filterDate = dateStatic.plusDays(24);

        // list year title of UI display on table
        List<DepositReportListYear> yearList = reportMapper.getDepositYear(filter);
        if (yearList != null && yearList.size() > 0) {
            for (int j = 0; j < yearList.size(); j++) {
                yearList.get(j).setDate(filter.getDate());

                // list deposit report
                yearList.get(j).setDepositReports(reportMapper.getDepositRequest(filter));
                List<DepositRequestReport> depositRequestReportList = yearList.get(j).getDepositReports();
                if (depositRequestReportList != null && depositRequestReportList.size() > 0) {
                    for (int i = 0; i < depositRequestReportList.size(); i++) {
                        Long employeesId = depositRequestReportList.get(i).getId();
                        // check old Salary
                        Float oldSalary = reportMapper.oldSalaryRequest(employeesId, String.valueOf(filterDate),
                                filter.getGroupId(), filter.getDepartmentId());
                        Float amountAdjustBeginning = reportMapper.getDepositAdjustBeginning(employeesId,
                                String.valueOf(filterDate));
                        depositRequestReportList.get(i).setOldSalary(oldSalary + amountAdjustBeginning);

                        // check deposit salary
                        Float depositSalary = reportMapper.depositSalaryRequest(employeesId, filter.getDate(),
                                filter.getGroupId(), filter.getDepartmentId());
                        depositRequestReportList.get(i).setDepositSalary(depositSalary);
                        // check old history deposit request
                        Float oldDepositRequest = reportMapper.oldAmountDepositRequest(employeesId, filter.getDate(),
                                filter.getGroupId(), filter.getDepartmentId());
                        // Total new salary
                        Float newSalary = (depositRequestReportList.get(i).getOldSalary() - depositSalary)
                                - oldDepositRequest;
                        depositRequestReportList.get(i).setNewSalary(newSalary);

                        // Calculate Amount Depoist
                        Float lastAmountDeposit = (depositRequestReportList.get(i).getOldSalary() - oldDepositRequest);
                        depositRequestReportList.get(i).setOldSalary(lastAmountDeposit);
                    }
                }
            }
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", yearList, true));
    }

    // provident fund report
    public ResponseMessage<BaseResult> getProvidentFund(ProFundReportFilter filter) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
//        if (permissionMapper.checkPermission(userId, "Report Provident Fund (View)") == 0) {
//            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
//        }

        // list groups
        List<ProFundListGroup> groupList = reportMapper.listGroup(filter);
        if (groupList != null && groupList.size() > 0) {
            for (int g = 0; g < groupList.size(); g++) {
                Long groupId = groupList.get(g).getId();
                groupList.get(g).setDepartmentList(reportMapper.listDepartment(filter, groupId, userId));

                // list departments
                List<ProFundListDepartment> departmentList = groupList.get(g).getDepartmentList();
                if (departmentList != null && departmentList.size() > 0) {
                    for (int i = 0; i < departmentList.size(); i++) {
                        Long departmentId = departmentList.get(i).getId();

                        // List Employees
                        departmentList.get(i).setProvidentFundReports(reportMapper.listEmployees(departmentId, filter));
                        List<ProvidentFundReport> providentFundReports = departmentList.get(i)
                                .getProvidentFundReports();
                        if (providentFundReports != null && providentFundReports.size() > 0) {
                            for (int j = 0; j < providentFundReports.size(); j++) {
                                Long employeesId = providentFundReports.get(j).getId();

                                // Calculate balance of provident funt by years
                                Float balance = reportMapper.getProFundBalance(employeesId, filter.getDate());
                                Float proFundRequest = reportMapper.getProFundRequest(employeesId, filter.getDate());
                                Float totalBalance = (balance - proFundRequest);
                                providentFundReports.get(j).setBalance(totalBalance);
                                providentFundReports.get(j).setProvidentRequest(proFundRequest);

                                // list total amount of years
                                providentFundReports.get(j).setTotalAmountList(
                                        reportMapper.listTotalAmountByYear(employeesId, filter.getDate()));
                                List<ProFundTotalYearList> totalYearLists = providentFundReports.get(j)
                                        .getTotalAmountList();
                                if (totalYearLists != null && totalYearLists.size() > 0) {
                                    for (int y = 0; y < totalYearLists.size(); y++) {
                                        String year = totalYearLists.get(y).getYear();
                                        Float totalAmount = reportMapper.totalProFundByYear(employeesId, year);
                                        totalYearLists.get(y).setTotalAmount(totalAmount);
                                    }
                                }

                                // list years of provident fund
                                providentFundReports.get(j)
                                        .setYearLists(reportMapper.listProFundYear(employeesId, filter.getDate()));
                                List<ProFundYearList> proFundYearLists = providentFundReports.get(j).getYearLists();
                                // list Month detail of year
                                if (proFundYearLists != null && proFundYearLists.size() > 0) {
                                    for (int k = 0; k < proFundYearLists.size(); k++) {
                                        // check date by month
                                        List<ProFundMonthList> monthLists = new ArrayList<>();

                                        // Sum total of month
                                        Long index = 1L;
                                        for (int m = 0; m < 12; m++) {
                                            Float amount = reportMapper.listProFundMonth(employeesId, index, filter);
                                            ProFundMonthList monthList = new ProFundMonthList();
                                            monthList.setId(index++);
                                            monthList.setMonth(Month.name(m));
                                            if (amount == null) {
                                                monthList.setAmount((float) 0);
                                            } else {
                                                monthList.setAmount(amount);
                                            }
                                            Collections.addAll(monthLists, monthList);
                                        }
                                        proFundYearLists.get(k).setMonthLists(monthLists);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", groupList, true));
    }

    // yearly deposit report
    public ResponseMessage<BaseResult> getYearlyDeposit(DepositReportMonthlyFilter filter) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
//        if (permissionMapper.checkPermission(userId, "Report Yearly Deposit (View)") == 0) {
//            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
//        }

        // list employees have deposit
        List<ReportDeposit> empDeposit = reportMapper.getEmpDeposit(filter);
        if (empDeposit != null && empDeposit.size() > 0) {
            for (ReportDeposit reportDeposit : empDeposit) {
                Long employeesId = reportDeposit.getId();

                String filterDate = filter.getDate();
                String lastDateRequest = reportMapper.getLastDateDepositRequest(employeesId, filterDate);

                /* Have request */
                if (reportDeposit.getCheckDateRequest() > 0) {
                    reportDeposit.setAdjustBeginningAmount(0.0f);
                    reportDeposit.setMonthlyDeposit(reportMapper.getDepositDateHaveRequest(employeesId, lastDateRequest, filterDate));
                    List<ReportDepositMonthly> monthlyList = reportDeposit.getMonthlyDeposit();
                    if (monthlyList != null && monthlyList.size() > 0) {
                        for (ReportDepositMonthly reportDepositMonthly : monthlyList) {
                            String date = reportDepositMonthly.getDepositDate();
                            float amount = reportMapper.getAmountMonthlyDeposit(employeesId, date);
                            reportDepositMonthly.setAmount(amount);
                        }
                    }
                } else {
                    reportDeposit.setMonthlyDeposit(reportMapper.getDepositDate(employeesId, filterDate));
                    reportDeposit.setAdjustBeginningAmount(reportMapper.getYearlyAdjustBeginningAmount(employeesId, filter.getDate()));
                    List<ReportDepositMonthly> monthlyList = reportDeposit.getMonthlyDeposit();
                    if (monthlyList != null && monthlyList.size() > 0) {
                        for (ReportDepositMonthly reportDepositMonthly : monthlyList) {
                            String date = reportDepositMonthly.getDepositDate();
                            float amount = reportMapper.getAmountMonthlyDeposit(employeesId, date);
                            reportDepositMonthly.setAmount(amount);
                        }
                    }
                }

                reportDeposit.setMonthlyDepositRequest(reportMapper.getDateRequest(employeesId, filterDate));
                List<ReportDepositRequest> requestDate = reportDeposit.getMonthlyDepositRequest();
                if (requestDate != null && requestDate.size() > 0) {
                    for (ReportDepositRequest reportDepositRequest : requestDate) {
                        Long depositRequestId = reportDepositRequest.getId();
                        float amountRequest = reportMapper.getAmountDepositRequest(employeesId, depositRequestId);
                        reportDepositRequest.setAmount(amountRequest);
                    }
                }
            }
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", empDeposit, true));
    }

    @Override
    public ResponseMessage<BaseResult> getAnnualLeave(LeaveReportFilter filter) {
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId, "HR Leave Request (View)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        if (filter != null) {
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
        }

        List<AnnualLeaveResponse> annualLeaveResponses = reportMapper.getAnnualLeave(filter);
        List<AnnualLeaveDetailResponse> activeLeaveTypes = reportMapper.getActiveLeaveTypes();

        if (annualLeaveResponses != null && !annualLeaveResponses.isEmpty()) {
            for (AnnualLeaveResponse annualLeaveResponse : annualLeaveResponses) {
                List<AnnualLeaveDetailResponse> leaveTypes = new ArrayList<>();
                if (activeLeaveTypes != null && !activeLeaveTypes.isEmpty()) {
                    for (AnnualLeaveDetailResponse activeLeaveType : activeLeaveTypes) {
                        AnnualLeaveDetailResponse leaveType = reportMapper.calculateLeaveByType(
                                filter,
                                annualLeaveResponse.getEmployeeId(),
                                activeLeaveType.getId()
                        );
                        if (leaveType == null) {
                            leaveType = new AnnualLeaveDetailResponse();
                            leaveType.setId(activeLeaveType.getId());
                            leaveType.setName(activeLeaveType.getName());
                            leaveType.setNumberOfDay(0F);
                        }
                        leaveTypes.add(leaveType);
                    }
                }
                annualLeaveResponse.setLeaveTypes(leaveTypes);
            }
        }

        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", annualLeaveResponses, true));
    }

    @Override
    public ResponseMessage<BaseResult> getStaffLoan(Filter filter) {
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId, "Staff Loan (View)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        Pagination pagination = new Pagination();
        pagination.setPage(filter.getPage());
        pagination.setRowsPerPage(filter.getRowsPerPage());
        pagination.setTotal(reportMapper.countListStaffLoan(filter));
        filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

        List<StaffLoanReportResponse> responses = reportMapper.getListStaffLoan(filter);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
    }

    @Override
    public ResponseMessage<BaseResult> getAttendance(ReportAttendanceFilter filter) {
        Pagination pagination = new Pagination();
        pagination.setPage(filter.getPage());
        pagination.setRowsPerPage(filter.getRowsPerPage());
        filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

        List<AttendanceResponse> responses = new ArrayList<>();
        if (filter.getShow() == null) {
            pagination.setTotal(0L);
        } else {
            if (filter.getShow() == 1L) {
                pagination.setTotal(reportMapper.getCountAttendanceDetail(filter));
                responses = reportMapper.getListAttendanceDetail(filter);
            } else if (filter.getShow() == 2L) {
                pagination.setTotal(reportMapper.getCountAttendanceSummary(filter));
                responses = reportMapper.getListAttendanceSummary(filter);
                System.out.println(responses);
            } else {
                pagination.setTotal(0L);
            }
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
    }

}
