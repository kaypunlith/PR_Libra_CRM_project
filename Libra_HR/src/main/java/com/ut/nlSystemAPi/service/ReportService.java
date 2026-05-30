package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.EmployeeFilter;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.LeaveReportFilter;
import com.ut.nlSystemAPi.model.filter.ReportAttendanceFilter;
import com.ut.nlSystemAPi.model.response.DepositReportFilter;
import com.ut.nlSystemAPi.model.response.DepositReportMonthlyFilter;
import com.ut.nlSystemAPi.model.response.ProFundReportFilter;

public interface ReportService {

    ResponseMessage<BaseResult> getDeposit(DepositReportFilter filter);

    ResponseMessage<BaseResult> getDepositRequest(DepositReportFilter filter);

    ResponseMessage<BaseResult> getProvidentFund(ProFundReportFilter filter);

    ResponseMessage<BaseResult> getReportStaffProfile(EmployeeFilter filter);

    ResponseMessage<BaseResult> getReportStaffProfileFind(Long id);

    ResponseMessage<BaseResult> getYearlyDeposit(DepositReportMonthlyFilter filter);

    ResponseMessage<BaseResult> getAttendance(ReportAttendanceFilter filter);

    ResponseMessage<BaseResult> getAnnualLeave(LeaveReportFilter filter);

    ResponseMessage<BaseResult> getStaffLoan(Filter filter);

}
