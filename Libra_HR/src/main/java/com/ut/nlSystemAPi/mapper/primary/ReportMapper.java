package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.LeaveReportFilter;
import com.ut.nlSystemAPi.model.filter.ReportAttendanceFilter;
import com.ut.nlSystemAPi.model.response.*;
import com.ut.nlSystemAPi.model.response.StaffLoan.StaffLoanReportResponse;
import com.ut.nlSystemAPi.model.response.StaffLoan.StaffLoanResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportMapper {

    // Report Deposit request
    List<DepositRequestReport> getDeposit(@Param("filter") DepositReportFilter filter);

    List<DepositReportListYear> getDepositYear(@Param("filter") DepositReportFilter filter);

    Float historyAmountDeposit(@Param("employeesId") Long employeesId, @Param("date") String date, @Param("groupId") Long groupId, @Param("departmentId") Long departmentId);

    Float depositSalary(@Param("employeesId") Long employeesId, @Param("date") String date, @Param("groupId") Long groupId, @Param("departmentId") Long departmentId);

    // Report Deposit With draws
    List<DepositRequestReport> getDepositRequest(@Param("filter") DepositReportFilter filter);

    Float getDepositAdjustBeginning(@Param("employeesId") Long employeesId, @Param("date") String date);

    Float oldSalaryRequest(@Param("employeesId") Long employeesId, @Param("date") String date, @Param("groupId") Long groupId, @Param("departmentId") Long departmentId);

    Float depositSalaryRequest(@Param("employeesId") Long employeesId, @Param("date") String date, @Param("groupId") Long groupId, @Param("departmentId") Long departmentId);

    Float oldAmountDepositRequest(@Param("employeesId") Long employeesId, @Param("date") String date, @Param("groupId") Long groupId, @Param("departmentId") Long departmentId);

    // Report Provident request
    List<ProFundListGroup> listGroup(@Param("filter") ProFundReportFilter filter);

    List<ProFundListDepartment> listDepartment(@Param("filter") ProFundReportFilter filter,@Param("groupId") Long groupId, @Param("userId") Long userId);

    List<ProvidentFundReport> listEmployees(@Param("departmentId") Long departmentId, @Param("filter") ProFundReportFilter filter);

    List<ProFundTotalYearList> listTotalAmountByYear(@Param("employeesId") Long employeesId, @Param("year") String year);

    Float totalProFundByYear(@Param("employeesId") Long employeesId, @Param("year") String year);

    List<ProFundYearList> listProFundYear(@Param("employeesId") Long employeesId, @Param("year") String year);

    Float listProFundMonth(@Param("employeesId") Long employeesId, @Param("month") Long month, @Param("filter") ProFundReportFilter filter);

    Float getProFundBalance(@Param("employeesId") Long employeesId, @Param("year") String year);

    Float getProFundRequest(@Param("employeesId") Long employeesId, @Param("year") String year);

    // Report Yearly Deposit
    List<ReportDeposit> getEmpDeposit(@Param("filter") DepositReportMonthlyFilter filter);

    List<ReportDepositMonthly> getDepositDate(@Param("employeesId") Long employeesId, @Param("dateFilter") String dateFilter);

    List<ReportDepositMonthly> getDepositDateHaveRequest(@Param("employeesId") Long employeesId, @Param("dateRequest") String dateRequest, @Param("dateFilter") String dateFilter);

    Float getAmountMonthlyDeposit(@Param("employeesId") Long employeesId, @Param("depositDate") String depositDate);

    List<ReportDepositRequest> getDateRequest(@Param("employeesId") Long employeesId, @Param("dateFilter") String dateFilter);

    Float getAmountDepositRequest(@Param("employeesId") Long employeesId, @Param("depositRequestId") Long depositRequestId);

    String getLastDateDepositRequest(@Param("employeesId") Long employeesId, @Param("dateFilter") String dateFilter);

    Float getHistoryMonthlyAdjustBeginningAmount(@Param("employeesId") Long employeesId, @Param("dateFilter") String dateFilter);

    Float getMonthlyAdjustBeginningAmount(@Param("employeesId") Long employeesId, @Param("dateFilter") String dateFilter);

    Float getYearlyAdjustBeginningAmount(@Param("employeesId") Long employeesId, @Param("dateFilter") String dateFilter);

    List<AnnualLeaveResponse> getAnnualLeave(@Param("filter") LeaveReportFilter filter);

    List<AnnualLeaveDetailResponse> getActiveLeaveTypes();

    AnnualLeaveDetailResponse calculateLeaveByType(@Param("filter") LeaveReportFilter filter, @Param("employeeId") Long employeeId, @Param("leaveTypeId") Long leaveTypeId);

    // * get list attendance Detail
    List<AttendanceResponse> getListAttendanceDetail(@Param("filter") ReportAttendanceFilter filter);

    // * get list attendance Summary
    List<AttendanceResponse> getListAttendanceSummary(@Param("filter") ReportAttendanceFilter filter);

    // * get count of attendance Detail
    Long getCountAttendanceDetail(@Param("filter") ReportAttendanceFilter filter);

    // * get count of attendance Summary
    Long getCountAttendanceSummary(@Param("filter") ReportAttendanceFilter filter);

    // * get count of attendance Detail
    List<StaffLoanReportResponse> getListStaffLoan(@Param("filter") Filter filter);

    // * get count of attendance Summary
    Long countListStaffLoan(@Param("filter") Filter filter);
}

