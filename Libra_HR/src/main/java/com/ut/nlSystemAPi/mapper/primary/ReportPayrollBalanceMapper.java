package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.response.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportPayrollBalanceMapper {

    DateResponse getDate(@Param("filter") PayrollReportFilter filter);

    List<PayrollBalanceList> listPayrollBalance(@Param("filter") PayrollReportFilter filter);

    List<ReportPayrollItem> listPayrollItem();

    Float getAmount(@Param("payrollItemId") Long payrollItemId, @Param("payDate") String payDate);

    Float totalAmount(@Param("payDate") String payDate);

}

