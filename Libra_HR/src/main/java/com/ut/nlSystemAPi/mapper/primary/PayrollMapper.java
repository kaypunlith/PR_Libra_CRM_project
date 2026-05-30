package com.ut.nlSystemAPi.mapper.primary;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ut.nlSystemAPi.model.Payroll;
import com.ut.nlSystemAPi.model.PayrollDetail;
import com.ut.nlSystemAPi.model.PayrollFilter;
import com.ut.nlSystemAPi.model.PayrollLock;
import com.ut.nlSystemAPi.model.PayrollRevert;
import com.ut.nlSystemAPi.model.PayrollSendTelegram;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.OtherPayFilter;
import com.ut.nlSystemAPi.model.filter.ViewOtFilter;
import com.ut.nlSystemAPi.model.request.InsertOtherPay;
import com.ut.nlSystemAPi.model.response.BonusDetail;
import com.ut.nlSystemAPi.model.response.DepartmentListResponse;
import com.ut.nlSystemAPi.model.response.EmployeeStatusHistory;
import com.ut.nlSystemAPi.model.response.EmployeeOtRate;
import com.ut.nlSystemAPi.model.response.OtRquestResponse;
import com.ut.nlSystemAPi.model.response.OtherPayResponse;
import com.ut.nlSystemAPi.model.response.PayrollItem;
import com.ut.nlSystemAPi.model.response.PayrollItemType;
import com.ut.nlSystemAPi.model.response.PayrollResponse;
import com.ut.nlSystemAPi.model.response.ViewAttendanceResponse;

@Repository
public interface PayrollMapper {

    List<DepartmentListResponse> listDepartment(@Param("filter") PayrollFilter filter, @Param("userId") Long userId);

    List<PayrollResponse> getList(@Param("departmentId") Long departmentId, @Param("payDate") String payDate, @Param("filter") PayrollFilter filter);

    List<PayrollItemType> listPayrollType();

    List<PayrollItem> listPayrollItem(@Param("payrollTypeId") Long payrollTypeId);

    Float checkAmountDeposit(@Param("employeesId") Long employeesId, @Param("employeeStatusDate") String employeeStatusDate);

    Float checkAmountDepositRequest(@Param("employeesId") Long employeesId);

    List<PayrollResponse> getOne(@Param("id") Long id);

    List<OtRquestResponse> viewHistoryBonus(@Param("filter") ViewOtFilter filter);

    List<ViewAttendanceResponse> viewAttendance(@Param("filter") ViewOtFilter filter);

    List<OtRquestResponse> viewOnTimeAttendanceBonus(@Param("filter") ViewOtFilter filter);



    List<OtherPayResponse> getListOtherPay(@Param("filter") Filter filter);



    List<BonusDetail> getBonusDetail(@Param("bonusId") Long bonusId, @Param("filter") OtherPayFilter filter);

    Long checkDuplicate(@Param("employeesId") Long employeesId, @Param("payDate") String payDate);

    Boolean insert(@Param("payroll") Payroll payroll);

    Boolean insertPayrollSendTelegram(@Param("payrollSendTelegram") PayrollSendTelegram payrollSendTelegram);

    String getEmployeeName(@Param("employeesId") Long employeesId);

    String getEmployeeChartId(@Param("employeesId") Long employeesId);

    String getEmployeeChatId(@Param("employeesId") Long employeesId);

    String getCreatorName(@Param("userId") Long userId);

    Boolean insertOtherPay(@Param("insertOtherPay") InsertOtherPay insertOtherPay);

    Boolean insertPayrollDetail(@Param("payrollDetail")PayrollDetail payrollDetail);

    Boolean updatePayrollDetail(@Param("payrollDetail")PayrollDetail payrollDetail);

    Boolean update(@Param("payroll") Payroll payroll);

    Boolean delete(@Param("payroll") Payroll payroll);

    Long getPayrollId(@Param("employeesId") Long employeesId, @Param("payDate") String payDate);

    Float getOldSalary(@Param("payrollId") Long payrollId);

    Long getPayrollIdFilterPayDate(@Param("employeesId") Long employeesId, @Param("payDate") String payDate);

    Float getPayrollDetailAmount(@Param("payrollId") Long payrollId, @Param("payrollItemId") Long payrollItemId);

    Long getStatusPayrollDetail(@Param("payrollId") Long payrollId, @Param("payrollItemId") Long payrollItemId);

    List<PayrollDetail> listPayrollDetailByPayrollId(@Param("payrollId") Long payrollId);

    Float getIncreasesalary(@Param("employeesId") Long employeesId, @Param("payDate") String payDate);

    Float getEmployeeCurrentSalary(@Param("employeesId") Long employeesId);

    EmployeeOtRate getEmployeeOtRate(@Param("employeeId") Long employeeId);

    Long getTotalWorkingMinuteOt(@Param("employeeId") Long employeeId, @Param("payDate") String payDate);

    Long isPublicHoliday(@Param("date") String date);

    Boolean lock(@Param("payrollLock") PayrollLock payrollLock);

    Boolean unLock(@Param("payrollLock") PayrollLock payrollLock);

    Long checkLock(@Param("departmentId") Long departmentId, @Param("payDate") String payDate);

    Long getIsLock(@Param("departmentId") Long departmentId, @Param("payDate") String payDate);

    // check payroll increase Salary when revert payroll
    PayrollRevert checkIncreaseSalry(@Param("employeesId") Long employeesId, @Param("payDate") String payDate);

    Float checkEmployeeCurrentSalary(@Param("employeesId") Long employeesId);

    Boolean updateCurrentSalary(@Param("payrollRevert") PayrollRevert payrollRevert);

    // Check employee saved payroll duplicated
    List<PayrollResponse> checkPayrollDuplicate(@Param("filter") PayrollFilter filter);

    Boolean updatePayrollDuplicate(@Param("employeeId") Long employeeId, @Param("payDate") String payDate);

    Boolean updatePayrollDuplicateByEmployeeIds(@Param("employeeIds") List<Long> employeeIds, @Param("payDate") String payDate);

    String getEmployeeLastStatusDate(@Param("employeesId") Long employeesId);

    Long ifEmployeePending(@Param("employeeId") Long employeeId, @Param("payDate") String payDate);

    List<EmployeeStatusHistory> listEmployeeStatusHistory(@Param("employeeId") Long employeeId, @Param("payDate") String payDate);

    Long countLateMinute(@Param("employeeId") Long employeeId, @Param("payDate") String payDate, @Param("status") Long status);

    Long getShiftDurationMin(@Param("employeeId") Long employeeId, @Param("status") Long status);

    Float getShiftDurationDay(@Param("employeeId") Long employeeId, @Param("status") Long status);

    Long getAttendDay(@Param("employeeId") Long employeeId, @Param("payDate") String payDate, @Param("status") Long status);

    Long getPaidLeaveDay(@Param("employeeId") Long employeeId, @Param("payDate") String payDate);

}

