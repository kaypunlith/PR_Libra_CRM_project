package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.response.DateResponse;
import com.ut.nlSystemAPi.model.response.PayrollBalanceList;
import com.ut.nlSystemAPi.model.response.PayrollReportFilter;
import com.ut.nlSystemAPi.model.response.ReportPayrollItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportTotalManualPaidMapper {

    DateResponse getDate(@Param("filter") PayrollReportFilter filter);

    List<PayrollBalanceList> listPayrollManualPaid(@Param("filter") PayrollReportFilter filter);

    List<ReportPayrollItem> listPayrollItem();

    Float getAmount(@Param("payrollItemId") Long payrollItemId, @Param("payDate") String payDate);

    Float totalAmount(@Param("payDate") String payDate);

}

