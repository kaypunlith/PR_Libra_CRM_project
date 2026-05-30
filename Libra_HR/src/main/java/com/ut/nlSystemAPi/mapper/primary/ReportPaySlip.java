package com.ut.nlSystemAPi.mapper.primary;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ut.nlSystemAPi.model.response.DateResponse;
import com.ut.nlSystemAPi.model.response.OtherPayReportResponse;
import com.ut.nlSystemAPi.model.response.PaySlipResponse;
import com.ut.nlSystemAPi.model.response.PayrollReportFilter;
import com.ut.nlSystemAPi.model.response.ReportPayrollItem;

@Repository
public interface ReportPaySlip {


    List<PaySlipResponse> getReportPaySlip(@Param("filter") PayrollReportFilter filter);

    Float getDayRate(@Param("employeeId") Long employeeId);

    Float getDayRateNight(@Param("employeeId") Long employeeId);

    
    Long getPayrollId(@Param("filter")PayrollReportFilter filter);

    List<OtherPayReportResponse> getOtherPayReport(@Param("filter")PayrollReportFilter filter,@Param("payrollId") Long payrollId);
    

    Float getListSumDayMonFri(@Param("filter") PayrollReportFilter filter);

    Float getListSumDaySatSun(@Param("filter") PayrollReportFilter filter);

    Float getListSumNight(@Param("filter") PayrollReportFilter filter);


}

