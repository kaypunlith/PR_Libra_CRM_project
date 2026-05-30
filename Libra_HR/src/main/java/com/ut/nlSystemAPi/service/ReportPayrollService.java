package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.response.PayrollReportFilter;

public interface ReportPayrollService {

    ResponseMessage<BaseResult> getListSummary(PayrollReportFilter filter);

    ResponseMessage<BaseResult> getBankPaid(PayrollReportFilter filter);

    ResponseMessage<BaseResult> getManualPaid(PayrollReportFilter filter);

    ResponseMessage<BaseResult> getGroupPayroll(PayrollReportFilter filter);

    ResponseMessage<BaseResult> getPayrollBalance(PayrollReportFilter filter);

    ResponseMessage<BaseResult> getTotalManualPaid(PayrollReportFilter filter);

    ResponseMessage<BaseResult> getPayslip(PayrollReportFilter filter);

}