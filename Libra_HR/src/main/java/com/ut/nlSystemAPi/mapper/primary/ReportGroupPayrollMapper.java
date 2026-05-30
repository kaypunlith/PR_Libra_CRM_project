package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.response.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportGroupPayrollMapper {

    List<PayrollDepartmentList> listDepartment(@Param("filter") PayrollReportFilter filter, @Param("userId") Long userId);

    List<PayrollEmployeesList> listEmployees(@Param("departmentId") Long departmentId, @Param("payDate") String payDate);

    List<ReportPayrollItemType> listPayrollType();

    List<ReportPayrollItem> listPayrollItem(@Param("payrollTypeId") Long payrollTypeId, @Param("employeesId") Long employeesId);

    Float getAmount(@Param("payrollItemId") Long payrollItemId, @Param("employeesId") Long employeesId, @Param("payDate") String payDate);

}

