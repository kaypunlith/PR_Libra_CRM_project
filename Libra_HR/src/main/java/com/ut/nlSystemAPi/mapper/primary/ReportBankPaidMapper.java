package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.response.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportBankPaidMapper {

    List<PayrollDepartmentList> listDepatment(@Param("filter") PayrollReportFilter filter, @Param("payDate") String payDate, @Param("userId") Long userId);

    List<PayrollBankPaid> getListBankPaid(@Param("departmentId") Long departmentId, @Param("payDate") String payDate);

}

