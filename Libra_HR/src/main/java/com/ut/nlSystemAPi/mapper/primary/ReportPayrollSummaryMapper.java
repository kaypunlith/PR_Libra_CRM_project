package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.response.DateResponse;
import com.ut.nlSystemAPi.model.response.PayrollReportFilter;
import com.ut.nlSystemAPi.model.response.PayrollSummary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportPayrollSummaryMapper {

    DateResponse getDate(@Param("filter") PayrollReportFilter filter);

    List<PayrollSummary> getList(@Param("filter") PayrollReportFilter filter);

    Float amountByATM(@Param("departmentId") Long departmentId, @Param("date") String date);

    Float amountByManual(@Param("departmentId") Long departmentId,  @Param("date") String date);

    Float lastMonthAmount(@Param("departmentId") Long departmentId, @Param("date") String date);

}

