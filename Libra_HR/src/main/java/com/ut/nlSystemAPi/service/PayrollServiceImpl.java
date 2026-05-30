package com.ut.nlSystemAPi.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.ut.nlSystemAPi.helper.FileUploadUtils;
import com.ut.nlSystemAPi.helper.FormatDate;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.helper.TelegramUtils;
import com.ut.nlSystemAPi.mapper.primary.PayrollMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.Payroll;
import com.ut.nlSystemAPi.model.PayrollDetail;
import com.ut.nlSystemAPi.model.PayrollFilter;
import com.ut.nlSystemAPi.model.PayrollLock;
import com.ut.nlSystemAPi.model.PayrollRevert;
import com.ut.nlSystemAPi.model.PayrollSendTelegram;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.OtherPayFilter;
import com.ut.nlSystemAPi.model.filter.ViewOtFilter;
import com.ut.nlSystemAPi.model.request.InsertOtherPay;
import com.ut.nlSystemAPi.model.request.PayrollRequest;
import com.ut.nlSystemAPi.model.request.PayrollSendTelegramRequest;
import com.ut.nlSystemAPi.model.response.BonusDetail;
import com.ut.nlSystemAPi.model.response.DepartmentListResponse;
import com.ut.nlSystemAPi.model.response.EmployeeOtRate;
import com.ut.nlSystemAPi.model.response.EmployeeStatusHistory;
import com.ut.nlSystemAPi.model.response.OtRquestResponse;
import com.ut.nlSystemAPi.model.response.OtherPayResponse;
import com.ut.nlSystemAPi.model.response.PayrollItem;
import com.ut.nlSystemAPi.model.response.PayrollItemType;
import com.ut.nlSystemAPi.model.response.PayrollResponse;
import com.ut.nlSystemAPi.model.response.ViewAttendanceResponse;

@SuppressWarnings("ALL")
@Service
public class PayrollServiceImpl implements PayrollService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter PAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private PayrollMapper payrollMapper;

    @Autowired
    private UserService userService;

    @Autowired Environment environment;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private PermissionMapper permissionMapper;

    public ResponseMessage<BaseResult> getList(PayrollFilter filter) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Payroll (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        Pagination pagination = new Pagination();
        pagination.setPage(filter.getPage());
        pagination.setRowsPerPage(filter.getRowsPerPage());
        if (filter != null) {
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
        }

        //! Check payroll have duplicate data
        List<PayrollResponse> employeeDuplicate = payrollMapper.checkPayrollDuplicate(filter);
        deactivateDuplicatePayrolls(employeeDuplicate, filter.getPayDate());

        //! List Department
        List<DepartmentListResponse> departmentListResponseList = payrollMapper.listDepartment(filter, userId);
        if (departmentListResponseList != null && departmentListResponseList.size() > 0){
            List<PayrollItemType> payrollTypeTemplates = buildPayrollTypeTemplates();
            Long cachedAmountOfDayExcludeSunday = null;
            for (int d = 0; d < departmentListResponseList.size(); d++){
                Long departmentId = departmentListResponseList.get(d).getId();

                String payDate = filter.getPayDate();
                // check is lock
                Long isLock = payrollMapper.getIsLock(departmentId, payDate);
                if (isLock == 0){
                    departmentListResponseList.get(d).setIsLocked(false);
                } else if (isLock == 1){
                    departmentListResponseList.get(d).setIsLocked(true);
                }

                List<PayrollResponse> employeesList = payrollMapper.getList(departmentId, payDate, filter);

                departmentListResponseList.get(d).setEmployees(employeesList);
                if (employeesList != null && employeesList.size() > 0) {

                    for (int i = 0; i < employeesList.size(); i++){
                        Long employeesId = employeesList.get(i).getEmployeeId();

                        //! employee status ID
                        Long ifEmployeePending = payrollMapper.ifEmployeePending(employeesId, payDate);
                        if(ifEmployeePending != null && ifEmployeePending > 0) {
                            employeesList.get(i).setEmployeeStatusId(2L);
                            List<EmployeeStatusHistory> employeeStatusHistories = payrollMapper.listEmployeeStatusHistory(employeesId, payDate);
                            if(employeeStatusHistories != null && employeeStatusHistories.size() > 0) {
                                employeesList.get(i).setRemark(employeeStatusHistories.get(0).getDate() + " to " + employeeStatusHistories.get(0).getDateTo());
                            }
                        } else {
                            employeesList.get(i).setEmployeeStatusId(1L);
                        }

                        if (employeesList.get(i).getRemark() == null){
                            employeesList.get(i).setRemark(employeesList.get(i).getRemark());
                        }

                        // check increase salary
                        Float payrollIncreaseSalary = employeesList.get(i).getIncreaseSalary();
                        Float increaseSalary = payrollMapper.getIncreasesalary(employeesId, payDate);
                        // FIX: Handle null increaseSalary
                        if (increaseSalary == null) {
                            increaseSalary = 0F;
                        }

                        if (payrollIncreaseSalary == null || payrollIncreaseSalary == 0){
                            employeesList.get(i).setIncreaseSalary(increaseSalary);
                        }

                        // check total amount
                        if (employeesList.get(i).getTotalAmount() == null){
                            employeesList.get(i).setTotalAmount(Float.valueOf(0));
                        }

                        // Check Payroll id by filter pay date
                        Long payrollId = payrollMapper.getPayrollIdFilterPayDate(employeesId, filter.getPayDate());
                        if (payrollId == null) {
                            employeesList.get(i).setIsSaved(0);
                        } else {
                            employeesList.get(i).setIsSaved(1);
                        }

                        // check current salary
                        Float oldSalary = payrollMapper.getOldSalary(payrollId);
                        if (oldSalary != null){
                            employeesList.get(i).setCurrentSalary(oldSalary);
                        }

                        // FIX: Handle null currentSalary
                        Float currentSalary = employeesList.get(i).getCurrentSalary();
                        if (currentSalary == null) {
                            currentSalary = 0F;
                        }

                        Float newSalary = currentSalary + increaseSalary;

                        // FIX: Handle null values with default 0
                        Float secondWorkShiftSalary = employeesList.get(i).getSecondWorkShiftSalary();
                        if (secondWorkShiftSalary == null) {
                            secondWorkShiftSalary = 0F;
                        }

                        Float allowance = employeesList.get(i).getAllowance();
                        if (allowance == null) {
                            allowance = 0F;
                        }

                        Float shiftDurationDay = payrollMapper.getShiftDurationDay(employeesId, 1L);
                        if (shiftDurationDay == null) {
                            shiftDurationDay = 0F;
                        }

                        Float shiftDurationDayOt = payrollMapper.getShiftDurationDay(employeesId, 2L);
                        if (shiftDurationDayOt == null) {
                            shiftDurationDayOt = 0F;
                        }

                        Long shiftDurationMinuteOt = payrollMapper.getShiftDurationMin(employeesId, 2L);
                        if (shiftDurationMinuteOt == null) {
                            shiftDurationMinuteOt = 0L;
                        }

                        Long attendDay = payrollMapper.getAttendDay(employeesId, payDate, 1L);
                        if (attendDay == null) {
                            attendDay = 0L;
                        }

                        Long paidLeaveDay = payrollMapper.getPaidLeaveDay(employeesId, payDate);
                        if (paidLeaveDay == null) {
                            paidLeaveDay = 0L;
                        }

                        EmployeeOtRate employeeOtRate = normalizeEmployeeOtRate(payrollMapper.getEmployeeOtRate(employeesId));
                        Float totalOtBonus = calculateTotalOtAmount(employeesId, payDate, employeeOtRate);

                        Long employeeStatusId = employeesList.get(i).getEmployeeStatusId();
                        if (employeeStatusId != null && (employeeStatusId == 3 || employeeStatusId == 4)) {
                            Long totalWorkingMinuteOt = payrollMapper.getTotalWorkingMinuteOt(employeesId, payDate);
                            if (totalWorkingMinuteOt == null) {
                                totalWorkingMinuteOt = 0L;
                            }
                            if (shiftDurationDayOt > 0 && shiftDurationMinuteOt > 0) {
                                Float salaryPerMinuteOt = secondWorkShiftSalary / shiftDurationDayOt / shiftDurationMinuteOt;
                                totalOtBonus += totalWorkingMinuteOt * salaryPerMinuteOt;
                            }
                        }

                        // Calculate Amount of employees Deposit
                        Long deposit = employeesList.get(i).getDeposit();
                        if (deposit == null) {
                            deposit = 0L;
                        }

                        Long donate = employeesList.get(i).getDonate();
                        if (donate == null) {
                            donate = 0L;
                        }

                        Long proFund = employeesList.get(i).getProFund();
                        if (proFund == null) {
                            proFund = 0L;
                        }

                        Float cashAmount = employeesList.get(i).getCashAmount();
                        if (cashAmount == null) {
                            cashAmount = 0F;
                        }

                        Long numOfMonth = employeesList.get(i).getNumOfMonth();
                        if (numOfMonth == null) {
                            numOfMonth = 0L;
                        }


                        /*Employee Status date*/
                        Float amountDeposit = 0F;
                        String employeeLastStatusDate = payrollMapper.getEmployeeLastStatusDate(employeesId);
                        if(employeeLastStatusDate != null) {
                            String normalizedLastStatusDate = FormatDate.FormatMonthYear(employeeLastStatusDate, "yyyy-MM") + "-01";
                            // check Amount Deposit
                            Float depositAmount = payrollMapper.checkAmountDeposit(employeesId, normalizedLastStatusDate);
                            amountDeposit = (depositAmount != null) ? depositAmount : 0F;
                        }

                        //List Payroll Type
                        employeesList.get(i).setPayrollTypes(clonePayrollTypeTemplates(payrollTypeTemplates));
                        List<PayrollItemType> payrollItemTypes = employeesList.get(i).getPayrollTypes();
                        Map<Long, PayrollDetail> payrollDetailMap = getPayrollDetailMap(payrollId);
                        List<ViewAttendanceResponse> viewAttendance = null;

                        // List Payroll Item Type
                        if (payrollItemTypes != null && payrollItemTypes.size() > 0) {
                            for (int j = 0; j < payrollItemTypes.size(); j++) {
                                //List Payroll Item
                                List<PayrollItem> payrollItems = payrollItemTypes.get(j).getPayrollItemList();

                                if (payrollItems != null && payrollItems.size() > 0) {

                                    for (int k = 0; k < payrollItems.size(); k++) {
                                        PayrollDetail payrollDetail = payrollDetailMap.get(payrollItems.get(k).getId());
                                        Float amount = payrollDetail != null ? payrollDetail.getAmount() : null;


                                        //Check Amount
                                        if (amount == null){
                                            // new salary
                                            if (payrollItems.get(k).getId() == 1) {
                                                payrollItems.get(k).setAmount(newSalary);
                                            }
                                            // allowance
                                            else if (payrollItems.get(k).getId() == 2) {
                                                payrollItems.get(k).setAmount(allowance);
                                            } else if (payrollItems.get(k).getId() == 3){
                                                Float notEnoughDayAmount = calculateNotEnoughDay(newSalary, shiftDurationDay, attendDay, paidLeaveDay);
                                                payrollItems.get(k).setAmount(notEnoughDayAmount != null ? notEnoughDayAmount : 0F);
                                            } else if (payrollItems.get(k).getId() == 4){
                                                //! Late Attendance Scan
                                                if (viewAttendance == null) {
                                                    ViewOtFilter filterOt = new ViewOtFilter();
                                                    filterOt.setEmployeeId(employeesId);
                                                    filterOt.setDate(payDate);
                                                    viewAttendance = payrollMapper.viewAttendance(filterOt);
                                                }

                                                Float totalLateDeductionAmount = 0F;
                                                if(viewAttendance != null && viewAttendance.size() > 0){
                                                    for (ViewAttendanceResponse attendance : viewAttendance) {
                                                        Float totalShiftDay = attendance.getShiftDay();
                                                        Float salaryPerMin = 0F;
                                                        if (totalShiftDay != null && totalShiftDay > 0) {
                                                            Float shiftHour = (attendance.getShiftHour() != null && attendance.getShiftHour() > 0) ? attendance.getShiftHour() : 1F;
                                                            salaryPerMin = newSalary / totalShiftDay / shiftHour / 60;
                                                        }
                                                        long minute = attendance.getMinute() != null ? attendance.getMinute() : 0L;
                                                        totalLateDeductionAmount += (minute * salaryPerMin);
                                                    }
                                                }

                                                Long amountOfDayExcludeSunday = 0L;

                                                if(shiftDurationDay > 0){
                                                    //! Function to deduct absent 
                                                    Float absentDeductionAmount = calculateApsent(newSalary, shiftDurationDay, attendDay, paidLeaveDay, amountOfDayExcludeSunday);
                                                    if (absentDeductionAmount == null) absentDeductionAmount = 0F;

                                                    Float totalLateAbsentAmount = totalLateDeductionAmount + absentDeductionAmount;

                                                    payrollItems.get(k).setAmount(totalLateAbsentAmount);
                                                } else {
                                                    payrollItems.get(k).setAmount(0F);
                                                }

                                            } else if (payrollItems.get(k).getId() == 12){
                                                //! Late Attendance Scan OT
                                                Long minuteOfLate = payrollMapper.countLateMinute(employeesId, payDate, 2L);
                                                if (minuteOfLate == null) minuteOfLate = 0L;

                                                Long getAttendDay = payrollMapper.getAttendDay(employeesId, payDate, 2L);
                                                if (getAttendDay == null) getAttendDay = 0L;

                                                if(shiftDurationDayOt > 0){
                                                    if (cachedAmountOfDayExcludeSunday == null) {
                                                        cachedAmountOfDayExcludeSunday = findAmountOfDayExcludeSunday(payDate);
                                                    }
                                                    Long amountOfDayExcludeSunday = cachedAmountOfDayExcludeSunday;
                                                    if (amountOfDayExcludeSunday == null) {
                                                        amountOfDayExcludeSunday = 0L;
                                                    }

                                                    Float lateAndUpsentOt = calculateLateUpsentOt(secondWorkShiftSalary, minuteOfLate, shiftDurationDayOt, shiftDurationMinuteOt, getAttendDay, amountOfDayExcludeSunday);
                                                    if (lateAndUpsentOt == null) lateAndUpsentOt = 0F;

                                                    payrollItems.get(k).setAmount(lateAndUpsentOt);
                                                } else {
                                                    payrollItems.get(k).setAmount(0F);
                                                }

                                            }
                                            // deposit = 1 : Yes , deposit = 0 : No
                                            else if (payrollItems.get(k).getId() == 5 && deposit == 1) {
                                                // check deposit is already payment or not yet payment amountDeposit - cashAmount == 0
                                                if (amountDeposit - cashAmount == 0 || amountDeposit - cashAmount > 0 || amountDeposit >= 600){
                                                    payrollItems.get(k).setAmount(Float.valueOf(0));
                                                    employeesList.get(i).setDeposit(Long.valueOf(0));
                                                } else {
                                                    if (numOfMonth > 0) {
                                                        Float totalDepositByMonth = cashAmount / numOfMonth;
                                                        payrollItems.get(k).setAmount(totalDepositByMonth);
                                                    } else {
                                                        payrollItems.get(k).setAmount(Float.valueOf(0));
                                                    }
                                                }
                                            } // donate = 1 : Yes (3$) , donate = 0 : No
                                            else if (payrollItems.get(k).getId() == 8 && donate == 1) {
                                                payrollItems.get(k).setAmount(Float.valueOf(3));
                                            } // Calculate ProFund = 1 : Yes ( * 0.04 ), PorFund = 0 : No
                                            else if (payrollItems.get(k).getId() == 10 && proFund == 1) {
                                                String amountProFund = String.valueOf((newSalary * 0.04));
                                                payrollItems.get(k).setAmount(Float.valueOf(amountProFund));
                                            } else {
                                                payrollItems.get(k).setAmount(Float.valueOf(0));
                                            }
                                            //End
                                        } else {
                                            payrollItems.get(k).setAmount(Float.valueOf(amount));
                                        }

                                        // List money of cut
                                        if (payrollItems.get(k).getId() == 5) {
                                            float amtDeposit = employeesList.get(i).getPayrollTypes().get(j).getPayrollItemList().get(k).getAmount();
                                            employeesList.get(i).setMoneyDeposit(amtDeposit);
                                        }
                                        if (payrollItems.get(k).getId() == 8) {
                                            float amtDonate = employeesList.get(i).getPayrollTypes().get(j).getPayrollItemList().get(k).getAmount();
                                            employeesList.get(i).setMoneyDonate(amtDonate);
                                        }
                                        if (payrollItems.get(k).getId() == 10) {
                                            float amtProfund = employeesList.get(i).getPayrollTypes().get(j).getPayrollItemList().get(k).getAmount();
                                            employeesList.get(i).setMoneyProFund(amtProfund);
                                        }

                                        // second work shift salary
                                        if (payrollItems.get(k).getId() == 11) {
                                            payrollItems.get(k).setAmount(totalOtBonus);
                                        }

                                        // * Get payroll detail status
                                        Long status = payrollDetail != null ? payrollDetail.getPayrollStatus() : null;
                                        payrollItems.get(k).setStatus(status);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", departmentListResponseList, true));
    }

    private void deactivateDuplicatePayrolls(List<PayrollResponse> employeeDuplicate, String payDate) {
        if (employeeDuplicate == null || employeeDuplicate.isEmpty()) {
            return;
        }

        List<Long> employeeIds = new ArrayList<>(employeeDuplicate.size());
        for (PayrollResponse duplicate : employeeDuplicate) {
            if (duplicate != null && duplicate.getEmployeeId() != null) {
                employeeIds.add(duplicate.getEmployeeId());
            }
        }

        if (!employeeIds.isEmpty()) {
            payrollMapper.updatePayrollDuplicateByEmployeeIds(employeeIds, payDate);
        }
    }

    private List<PayrollItemType> buildPayrollTypeTemplates() {
        List<PayrollItemType> payrollTypes = payrollMapper.listPayrollType();
        if (payrollTypes == null || payrollTypes.isEmpty()) {
            return Collections.emptyList();
        }

        for (PayrollItemType payrollType : payrollTypes) {
            payrollType.setPayrollItemList(payrollMapper.listPayrollItem(payrollType.getId()));
        }
        return payrollTypes;
    }

    private List<PayrollItemType> clonePayrollTypeTemplates(List<PayrollItemType> templates) {
        if (templates == null || templates.isEmpty()) {
            return new ArrayList<>();
        }

        List<PayrollItemType> clonedTypes = new ArrayList<>(templates.size());
        for (PayrollItemType template : templates) {
            PayrollItemType clonedType = new PayrollItemType();
            clonedType.setId(template.getId());
            clonedType.setName(template.getName());
            clonedType.setPayrollItemList(clonePayrollItems(template.getPayrollItemList()));
            clonedTypes.add(clonedType);
        }

        return clonedTypes;
    }

    private List<PayrollItem> clonePayrollItems(List<PayrollItem> payrollItems) {
        if (payrollItems == null || payrollItems.isEmpty()) {
            return new ArrayList<>();
        }

        List<PayrollItem> clonedItems = new ArrayList<>(payrollItems.size());
        for (PayrollItem payrollItem : payrollItems) {
            PayrollItem clonedItem = new PayrollItem();
            clonedItem.setId(payrollItem.getId());
            clonedItem.setName(payrollItem.getName());
            clonedItem.setAmount(payrollItem.getAmount());
            clonedItem.setStatus(payrollItem.getStatus());
            clonedItems.add(clonedItem);
        }
        return clonedItems;
    }

    private Map<Long, PayrollDetail> getPayrollDetailMap(Long payrollId) {
        if (payrollId == null) {
            return Collections.emptyMap();
        }

        List<PayrollDetail> payrollDetails = payrollMapper.listPayrollDetailByPayrollId(payrollId);
        if (payrollDetails == null || payrollDetails.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Long, PayrollDetail> payrollDetailMap = new HashMap<>(payrollDetails.size());
        for (PayrollDetail payrollDetail : payrollDetails) {
            if (payrollDetail != null && payrollDetail.getPayrollItemId() != null) {
                payrollDetailMap.put(payrollDetail.getPayrollItemId(), payrollDetail);
            }
        }
        return payrollDetailMap;
    }

    private Long longOrZero(Long value) {
        return value != null ? value : 0L;
    }

    public ResponseMessage<BaseResult> getOne(Long id) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Payroll (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        List<PayrollResponse> payrolls = payrollMapper.getOne(id);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", payrolls, true));
    }



    public ResponseMessage<BaseResult> viewHistoryBonus(ViewOtFilter filter) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Payroll (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        List<OtRquestResponse> otRquestResponses = payrollMapper.viewHistoryBonus(filter);
        List<OtRquestResponse> onTimeAttendance = payrollMapper.viewOnTimeAttendanceBonus(filter);

        System.out.println("onTimeAttendance: " + onTimeAttendance);
        EmployeeOtRate employeeOtRate = normalizeEmployeeOtRate(payrollMapper.getEmployeeOtRate(filter.getEmployeeId()));

        System.out.println("filter.getEmployeeId(): " + filter.getEmployeeId());
        Map<LocalDate, Boolean> publicHolidayCache = new HashMap<>();

        System.out.println("employeeOtRate: " + employeeOtRate);

        List<OtRquestResponse> bonusHistory = new java.util.ArrayList<>();
        if (otRquestResponses != null) {
            bonusHistory.addAll(otRquestResponses);
        }
        if (onTimeAttendance != null) {
            bonusHistory.addAll(onTimeAttendance);
        }

        if (!bonusHistory.isEmpty()) {
            Float totalBonus = 0F;

            Float increaseSalary = payrollMapper.getIncreasesalary(filter.getEmployeeId(),filter.getDate());
            if (increaseSalary == null) increaseSalary = 0F;
            Float currentSalary = payrollMapper.getEmployeeCurrentSalary(filter.getEmployeeId());
            if (currentSalary == null) currentSalary = 0F;
            Float salary = currentSalary + increaseSalary;

            Long shiftDurationMinute = payrollMapper.getShiftDurationMin(filter.getEmployeeId(), 1L);
            if (shiftDurationMinute == null) shiftDurationMinute = 0L;

            for (OtRquestResponse response : bonusHistory) {
                Float bonusAmount = 0F;
                if ("ATTENDANCE_ON_TIME".equals(response.getRecordType())) {
                    System.out.println("bTesting 02");
                    Float shiftDay = response.getShiftDay();
                    Float salaryPerMin = 0F;

                    Long employeeStatusId = response.getEmployeeStatusId();
                    long minute = response.getMinute() != null ? response.getMinute() : 0L;

                    if (employeeStatusId != null && (employeeStatusId == 3 || employeeStatusId == 4)) {
                        // Split working time into day (7AM-7PM) and night (7PM-7AM)
                        LocalDateTime checkIn = parseDateTime(response.getDateTimeFrom());
                        LocalDateTime checkOut = parseDateTime(response.getDateTimeTo());

                        if (checkIn != null && checkOut != null) {
                            long dayMinutes = calculateDayMinutes(checkIn, checkOut);
                            long nightMinutes = calculateNightMinutes(checkIn, checkOut);

                            LocalDate date = checkIn.toLocalDate();
                            Float dayRate = 0F;
                            if (isPublicHoliday(date, publicHolidayCache)) {
                                dayRate = employeeOtRate.getPublicHolidayRate();
                            } else if (isWeekend(date)) {
                                dayRate = employeeOtRate.getWeekendRate();
                            } else {
                                dayRate = employeeOtRate.getDayRate();
                            }
                            Float nightRateValue = employeeOtRate.getNightRate() != null ? employeeOtRate.getNightRate() : 0F;

                            bonusAmount = (dayMinutes * (dayRate / 60F)) + (nightMinutes * (nightRateValue / 60F));
                        }
                    } else {
                        System.out.println("salary: " + salary);
                        System.out.println("shiftDay: " + shiftDay);
                        System.out.println("shiftDurationMinute: " + shiftDurationMinute);
                        if (shiftDay != null && shiftDay > 0 && shiftDurationMinute > 0) {
                            salaryPerMin = salary / shiftDay / shiftDurationMinute;
                        }
                        System.out.println("salaryPerMin: " + salaryPerMin);
                        bonusAmount = minute * salaryPerMin;
                        System.out.println("bonusAmountsdada: " + bonusAmount);
                    }
                } else {
                    System.out.println("bTesting 01");
                    LocalDateTime startDateTime = parseDateTime(response.getDateTimeFrom());
                    LocalDateTime endDateTime = parseDateTime(response.getDateTimeTo());
                    bonusAmount = calculateOtAmount(startDateTime, endDateTime, employeeOtRate, publicHolidayCache);
                }
                System.out.println("bonusAmount: " + bonusAmount);
                response.setBonusSalary(bonusAmount);
                totalBonus += bonusAmount;
            }
            bonusHistory.get(0).setTotalBonusSalary(totalBonus);
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", bonusHistory, true));
    }

    public ResponseMessage<BaseResult> viewAttendance(ViewOtFilter filter) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Payroll (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }


        System.out.println(filter);
        List<ViewAttendanceResponse> viewAttendance = payrollMapper.viewAttendance(filter);
        System.out.println("viewAttendance: " + viewAttendance);
        if(viewAttendance.size()>0){
            Long shiftDurationMinute = payrollMapper.getShiftDurationMin(filter.getEmployeeId(), 1L);

//            Long shiftHour = payrollMapper.getShiftHour(filter.getEmployeeId(), 1L);

            Float increaseSalary = payrollMapper.getIncreasesalary(filter.getEmployeeId(),filter.getDate());
            if (increaseSalary == null) increaseSalary = 0F;
            Float currentSalary = payrollMapper.getEmployeeCurrentSalary(filter.getEmployeeId());
            if (currentSalary == null) currentSalary = 0F;
            Float salary = currentSalary + increaseSalary;

            System.out.println("salary: " + salary);

            for (ViewAttendanceResponse attendance : viewAttendance) {
                Float totalShiftDay = attendance.getShiftDay();
                System.out.println("totalShiftDay: " + totalShiftDay);
                Float salaryPerMin = 0F;
                System.out.println("salary: " + salary);
                System.out.println("totalShiftDay: " + totalShiftDay);
                System.out.println("attendance.getShiftHour(): " + attendance.getShiftHour());
                if (totalShiftDay != null && totalShiftDay > 0 && attendance.getShiftHour() != null && attendance.getShiftHour() > 0) {
                    salaryPerMin = salary / totalShiftDay / attendance.getShiftHour() / 60;
                }

                long minute = attendance.getMinute() != null ? attendance.getMinute() : 0L;

                System.out.println("minute: " + minute);
                Float minuteAmount = minute * salaryPerMin;
                attendance.setSalaryDeduction(minuteAmount);
            }
        }

        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", viewAttendance, true));
    }

    public ResponseMessage<BaseResult> getListViewOtherPay(OtherPayFilter filter) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Payroll (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }
        List<OtherPayResponse> otherPayResponses = payrollMapper.getListOtherPay(filter);
        if (!otherPayResponses.isEmpty()) {
            for (OtherPayResponse response : otherPayResponses) {
                List<BonusDetail> bonusDetails = payrollMapper.getBonusDetail(response.getId(),filter);
                response.setBonusDetail(bonusDetails);
            }
        }

        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", otherPayResponses, true));
    }

    public ResponseMessage<BaseResult> insert(PayrollRequest payrollRequest) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Payroll (Add)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        // Check Data
        Payroll payroll = new Payroll();
        payroll.setCreatedBy(userId);
        payroll.setModifiedBy(userId);
        payroll.setIsActive(1);
        payroll.setEmployeesId(payrollRequest.getEmployeeId());
        payroll.setDepartmentId(payrollRequest.getDepartmentId());
        payroll.setPositionId(payrollRequest.getPositionId());
        payroll.setPaidType(payrollRequest.getPaidType());
        payroll.setAccountNumber(payrollRequest.getAccountNumber());
        payroll.setAccountId(payrollRequest.getAccountId());
        payroll.setPayDate(payrollRequest.getPayDate());
        payroll.setCurrentSalary(payrollRequest.getCurrentSalary());
        payroll.setIncreaseSalary(payrollRequest.getIncreaseSalary());
        payroll.setTotalAmount(payrollRequest.getTotalAmount());
        payroll.setNote(payrollRequest.getNote());
        // Check Duplicate
        if(payrollMapper.checkDuplicate(payrollRequest.getEmployeeId(), payrollRequest.getPayDate()) > 0){
            Boolean result = payrollMapper.update(payroll);
            long payrollId = payrollMapper.getPayrollId(payrollRequest.getEmployeeId(), payrollRequest.getPayDate());
            if (result) {
                //Update Payroll Detail
                if(!payrollRequest.getDetails().isEmpty()){
                    for(int i = 0; i < payrollRequest.getDetails().size(); i++){
                        // Check Data
                        PayrollDetail payrollDetail = new PayrollDetail();
                        payrollDetail.setPayrollId(payrollId);
                        payrollDetail.setPayrollItemId(payrollRequest.getDetails().get(i).getPayrollItemId());
                        payrollDetail.setAmount(payrollRequest.getDetails().get(i).getAmount());
                        payrollDetail.setCreatedBy(userId);
                        payrollDetail.setIsActive(1);
                        payrollMapper.updatePayrollDetail(payrollDetail);
                    }
                }
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
        }

        Boolean result = payrollMapper.insert(payroll);
        if (result) {
            //!Insert Payroll Detail
            if(payrollRequest.getDetails() != null && !payrollRequest.getDetails().isEmpty() ){
                for(int i = 0; i < payrollRequest.getDetails().size(); i++){
                    // Check Data
                    PayrollDetail payrollDetail = new PayrollDetail();
                    payrollDetail.setPayrollId(payroll.getId());
                    payrollDetail.setPayrollItemId(payrollRequest.getDetails().get(i).getPayrollItemId());
                    payrollDetail.setAmount(payrollRequest.getDetails().get(i).getAmount());
                    payrollDetail.setCreatedBy(userId);
                    payrollDetail.setIsActive(1);
                    if(payrollRequest.getDetails().get(i).getStatus() == null){
                        payrollDetail.setPayrollStatus(1L);
                    } else {
                        payrollDetail.setPayrollStatus(payrollRequest.getDetails().get(i).getStatus());
                    }
                    payrollMapper.insertPayrollDetail(payrollDetail);
                }
            }

            if(payrollRequest.getInsertOtherPay() != null && !payrollRequest.getInsertOtherPay().isEmpty()){
                for(int i=0;i<payrollRequest.getInsertOtherPay().size();i++){
                    InsertOtherPay insertOtherPay = new InsertOtherPay();
                    insertOtherPay.setPayrollId(payroll.getId());
                    insertOtherPay.setEmployeeId(payrollRequest.getEmployeeId());
                    insertOtherPay.setBranchId(payrollRequest.getInsertOtherPay().get(i).getBranchId());
                    insertOtherPay.setBonusId(payrollRequest.getInsertOtherPay().get(i).getBonusId());
                    insertOtherPay.setAmount(payrollRequest.getInsertOtherPay().get(i).getAmount());
                    insertOtherPay.setCreatedBy(userId);
                    result = payrollMapper.insertOtherPay(insertOtherPay);
                }
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> payrollsSendTelegram(PayrollSendTelegramRequest request, MultipartFile file) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Payroll (Add)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }
        PayrollSendTelegram payrollSendTelegram = new PayrollSendTelegram();
        if (file != null) {
            String ImageFile = FileUploadUtils.saveFileUploaded(file);
            payrollSendTelegram.setFileImageName( file.getOriginalFilename());
            payrollSendTelegram.setFileImageUrl(ImageFile);
        }

        // Check Data
        payrollSendTelegram.setPayDate(request.getPayDate());
        payrollSendTelegram.setEmployeesId(request.getEmployeesId());
        Boolean result = payrollMapper.insertPayrollSendTelegram(payrollSendTelegram);
        String employeeName = payrollMapper.getEmployeeName(payrollSendTelegram.getEmployeesId());
        String CreatorName = payrollMapper.getCreatorName(userId);
        Long telegramId;
        if (result) {
            String formattedDate = formatPayDate(payrollSendTelegram.getPayDate());
            String employeeChatId = payrollMapper.getEmployeeChartId(payrollSendTelegram.getEmployeesId());
            System.out.println("employeeId "+employeeChatId);
            telegramId = pushTelegramWithFileLink(
                    "✉️<b><u>Employee Monthly Payslip</u></b>" +
                            "\n\n<b>Payslip for: " + formattedDate + " </b>" +
                            "\n\n<b>Employee Name: " + employeeName + " </b>" +
                            "\n\n<pre>If you have any questions concerning this report contact: " + CreatorName + " </pre>"
                    , employeeChatId, environment.getProperty("telegram.token"),payrollSendTelegram.getFileImageUrl());
            System.out.println("telegram Id"+telegramId);

            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> update(PayrollRequest payrollRequest) {
        //Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Payroll (Edit)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        //Check Data
        Payroll payroll = new Payroll();
        payroll.setModifiedBy(userId);
        payroll.setIsActive(1);

        Boolean result = payrollMapper.update(payroll);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> delete(PayrollRequest payrollRequest) {
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId, "Payroll (Add)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        //Check Data
        Payroll payroll = new Payroll();
        payroll.setId(payrollRequest.getId());
        payroll.setPayDate(payrollRequest.getPayDate());
        payroll.setModifiedBy(userId);
        payroll.setIsActive(2);

        Boolean result = payrollMapper.delete(payroll);
        if (result) {

            // Check have increase salary payroll
            PayrollRevert payrollIncreaseSalary = payrollMapper.checkIncreaseSalry(payrollRequest.getId(), payrollRequest.getPayDate());

            if(payrollIncreaseSalary != null){
                Float checkCurrentSalary = payrollMapper.checkEmployeeCurrentSalary(payrollRequest.getId());

                Float payrollCurrentSalary = (payrollIncreaseSalary.getOldSalary() + payrollIncreaseSalary.getIncreaseSalary());

                // calculate current salary
                Float currentSalary = (checkCurrentSalary - payrollIncreaseSalary.getIncreaseSalary());
                System.out.println(currentSalary);
                PayrollRevert payrollRevert = new  PayrollRevert();
                payrollRevert.setId(payrollRequest.getId());
                payrollRevert.setCurrentSalary(currentSalary);
                payrollRevert.setModifiedBy(userId);
                // update current salary to old current
                payrollMapper.updateCurrentSalary(payrollRevert);
            }

            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> lock(PayrollLock payrollLock) {
        //Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId, "Payroll (lock)") == 0){
            if(permissionMapper.checkPermission(userId, "Payroll (unlock)") == 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
            }
        }

        // Check Duplicate
        if(payrollMapper.checkLock(payrollLock.getDepartmentId(), payrollLock.getPayDate()) > 0){
            payrollLock.setModifiedBy(userId);
            Boolean result = payrollMapper.unLock(payrollLock);
            if (result) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        }

        //Check Data
        payrollLock.setCreatedBy(userId);
        payrollLock.setIsActive(1);

        Boolean result = payrollMapper.lock(payrollLock);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    private EmployeeOtRate normalizeEmployeeOtRate(EmployeeOtRate rate) {
        if (rate == null) {
            return EmployeeOtRate.empty();
        }
        if (rate.getDayRate() == null) {
            rate.setDayRate(0F);
        }
        if (rate.getNightRate() == null) {
            rate.setNightRate(0F);
        }
        if (rate.getWeekendRate() == null) {
            rate.setWeekendRate(0F);
        }
        if (rate.getPublicHolidayRate() == null) {
            rate.setPublicHolidayRate(0F);
        }
        return rate;
    }

    private Float calculateTotalOtAmount(Long employeeId, String payDate, EmployeeOtRate employeeOtRate) {
        if (employeeId == null || payDate == null) {
            return 0F;
        }

        ViewOtFilter otFilter = new ViewOtFilter();
        otFilter.setEmployeeId(employeeId);
        otFilter.setDate(payDate);

        List<OtRquestResponse> overtimeRequests = payrollMapper.viewHistoryBonus(otFilter);
        List<OtRquestResponse> onTimeAttendance = payrollMapper.viewOnTimeAttendanceBonus(otFilter);

        List<OtRquestResponse> bonusEntries = new java.util.ArrayList<>();
        if (overtimeRequests != null) {
            bonusEntries.addAll(overtimeRequests);
        }
        if (onTimeAttendance != null) {
            bonusEntries.addAll(onTimeAttendance);
        }

        if (bonusEntries.isEmpty()) {
            return 0F;
        }

        Float increaseSalary = payrollMapper.getIncreasesalary(employeeId, payDate);
        if (increaseSalary == null) increaseSalary = 0F;
        Float currentSalary = payrollMapper.getEmployeeCurrentSalary(employeeId);
        if (currentSalary == null) currentSalary = 0F;
        Float salary = currentSalary + increaseSalary;

        Long shiftDurationMinute = payrollMapper.getShiftDurationMin(employeeId, 1L);
        if (shiftDurationMinute == null) shiftDurationMinute = 0L;

        Map<LocalDate, Boolean> publicHolidayCache = new HashMap<>();
        Float totalAmount = 0F;
        for (OtRquestResponse overtimeRequest : bonusEntries) {
            if ("ATTENDANCE_ON_TIME".equals(overtimeRequest.getRecordType())) {
                Float shiftDay = overtimeRequest.getShiftDay();
                Float salaryPerMin = 0F;

                Long employeeStatusId = overtimeRequest.getEmployeeStatusId();
                long minute = overtimeRequest.getMinute() != null ? overtimeRequest.getMinute() : 0L;

                if (employeeStatusId != null && (employeeStatusId == 3 || employeeStatusId == 4)) {
                    // Split working time into day (7AM-7PM) and night (7PM-7AM)
                    LocalDateTime checkIn = parseDateTime(overtimeRequest.getDateTimeFrom());
                    LocalDateTime checkOut = parseDateTime(overtimeRequest.getDateTimeTo());

                    if (checkIn != null && checkOut != null) {
                        long dayMinutes = calculateDayMinutes(checkIn, checkOut);
                        long nightMinutes = calculateNightMinutes(checkIn, checkOut);

                        LocalDate date = checkIn.toLocalDate();
                        Float dayRate = 0F;
                        if (isPublicHoliday(date, publicHolidayCache)) {
                            dayRate = employeeOtRate.getPublicHolidayRate();
                        } else if (isWeekend(date)) {
                            dayRate = employeeOtRate.getWeekendRate();
                        } else {
                            dayRate = employeeOtRate.getDayRate();
                        }
                        Float nightRateValue = employeeOtRate.getNightRate() != null ? employeeOtRate.getNightRate() : 0F;

                        totalAmount += (dayMinutes * (dayRate / 60F)) + (nightMinutes * (nightRateValue / 60F));
                    }
                } else {
                    if (shiftDay != null && shiftDay > 0 && shiftDurationMinute > 0) {
                        salaryPerMin = salary / shiftDay / shiftDurationMinute;
                    }
                    totalAmount += minute * salaryPerMin;
                }
            } else {
                LocalDateTime startDateTime = parseDateTime(overtimeRequest.getDateTimeFrom());
                LocalDateTime endDateTime = parseDateTime(overtimeRequest.getDateTimeTo());
                totalAmount += calculateOtAmount(startDateTime, endDateTime, employeeOtRate, publicHolidayCache);
            }
        }
        return totalAmount;
    }

    private Float calculateOtAmount(LocalDateTime start, LocalDateTime end, EmployeeOtRate employeeOtRate, Map<LocalDate, Boolean> publicHolidayCache) {
        if (start == null || end == null || !end.isAfter(start) || employeeOtRate == null) {
            return 0F;
        }

        Float totalAmount = 0F;
        LocalDateTime current = start;

        while (current.isBefore(end)) {
            LocalDate currentDate = current.toLocalDate();
            LocalDateTime endOfDay = currentDate.plusDays(1).atStartOfDay();

            Float rateToUse;
            LocalDateTime segmentEnd;

            if (isPublicHoliday(currentDate, publicHolidayCache)) {
                rateToUse = valueOrZero(employeeOtRate.getPublicHolidayRate());
                segmentEnd = minDateTime(end, endOfDay);
            } else if (isWeekend(currentDate)) {
                rateToUse = valueOrZero(employeeOtRate.getWeekendRate());
                segmentEnd = minDateTime(end, endOfDay);
            } else {
                LocalDateTime dayStart = currentDate.atTime(LocalTime.of(7, 0));
                LocalDateTime dayEnd = currentDate.atTime(LocalTime.of(19, 0));

                if (current.isBefore(dayStart)) {
                    rateToUse = valueOrZero(employeeOtRate.getNightRate());
                    segmentEnd = minDateTime(end, dayStart, endOfDay);
                } else if (current.isBefore(dayEnd)) {
                    rateToUse = valueOrZero(employeeOtRate.getDayRate());
                    segmentEnd = minDateTime(end, dayEnd, endOfDay);
                } else {
                    rateToUse = valueOrZero(employeeOtRate.getNightRate());
                    segmentEnd = minDateTime(end, endOfDay);
                }
            }

            if (!segmentEnd.isAfter(current)) {
                segmentEnd = current.plusMinutes(1);
                if (segmentEnd.isAfter(end)) {
                    segmentEnd = end;
                }
            }

            long minutes = ChronoUnit.MINUTES.between(current, segmentEnd);
            if (minutes > 0) {
                totalAmount += (minutes / 60F) * rateToUse;
            }

            current = segmentEnd;
        }

        return totalAmount;
    }

    private boolean isPublicHoliday(LocalDate date, Map<LocalDate, Boolean> cache) {
        if (date == null) {
            return false;
        }
        if (cache.containsKey(date)) {
            return cache.get(date);
        }
        Long isPublicHoliday = payrollMapper.isPublicHoliday(date.toString());
        boolean result = isPublicHoliday != null && isPublicHoliday > 0;
        cache.put(date, result);
        return result;
    }

    private boolean isWeekend(LocalDate date) {
        if (date == null) {
            return false;
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private LocalDateTime minDateTime(LocalDateTime first, LocalDateTime... others) {
        LocalDateTime min = first;
        if (min == null && others != null) {
            for (LocalDateTime candidate : others) {
                if (candidate != null) {
                    min = candidate;
                    break;
                }
            }
        }

        if (others != null) {
            for (LocalDateTime candidate : others) {
                if (candidate != null) {
                    if (min == null || candidate.isBefore(min)) {
                        min = candidate;
                    }
                }
            }
        }

        return min;
    }

    private Float valueOrZero(Float value) {
        return value != null ? value : 0F;
    }

    private LocalDateTime parseDateTime(String dateTime) {
        if (dateTime == null || dateTime.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException exception) {
            try {
                return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeParseException innerException) {
                return null;
            }
        }
    }
    /**
     * Calculate minutes within day period (7AM-7PM) between two datetimes.
     */
    private long calculateDayMinutes(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || !end.isAfter(start)) return 0L;
        LocalTime dayStart = LocalTime.of(7, 0);
        LocalTime dayEnd = LocalTime.of(19, 0);
        long totalDayMinutes = 0L;

        LocalDateTime current = start;
        while (current.isBefore(end)) {
            LocalDate currentDate = current.toLocalDate();
            // Day window for current date: 7AM to 7PM
            LocalDateTime windowStart = LocalDateTime.of(currentDate, dayStart);
            LocalDateTime windowEnd = LocalDateTime.of(currentDate, dayEnd);

            // Overlap between [current, end] and [windowStart, windowEnd]
            LocalDateTime overlapStart = current.isAfter(windowStart) ? current : windowStart;
            LocalDateTime overlapEnd = end.isBefore(windowEnd) ? end : windowEnd;

            if (overlapStart.isBefore(overlapEnd)) {
                totalDayMinutes += Duration.between(overlapStart, overlapEnd).toMinutes();
            }

            // Move to next day
            current = LocalDateTime.of(currentDate.plusDays(1), LocalTime.MIDNIGHT);
        }
        return totalDayMinutes;
    }

    /**
     * Calculate minutes within night period (7PM-7AM) between two datetimes.
     */
    private long calculateNightMinutes(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || !end.isAfter(start)) return 0L;
        long totalMinutes = Duration.between(start, end).toMinutes();
        long dayMinutes = calculateDayMinutes(start, end);
        return totalMinutes - dayMinutes;
    }

    public static Float calculateNotEnoughDay(Float salary, Float shiftDurationDay, Long attendDay, Long paidLeaveDay) {
        if (salary == null) {
            salary = 0F;
        }
        if (shiftDurationDay == null || shiftDurationDay <= 0) {
            return 0F;
        }
        if (attendDay == null) {
            attendDay = 0L;
        }
        if (paidLeaveDay == null) {
            paidLeaveDay = 0L;
        }

        // Formula: (total salary / total working day) * total working day - (total salary / total working day) * total attended day
        // = salary - (salary / shiftDurationDay) * (attendDay + paidLeaveDay)
        Float salaryPerDay = salary / shiftDurationDay;
        Float totalAttendedDaySalary = salaryPerDay * (attendDay + paidLeaveDay);
        Float notEnoughDayAmount = salary - totalAttendedDaySalary;

        if (notEnoughDayAmount < 0) {
            notEnoughDayAmount = 0F;
        }

        return notEnoughDayAmount;
    }

    public static Long FindSunday(LocalDate  startDate, LocalDate  endDate) {

        // Initialize the count of Sundays
        Long sundayCount = 0L;

        // Loop through the days between the start and end dates
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                sundayCount++;
            }
        }

        return sundayCount;
    }

    public static Float calculateLateUpsent(Float salary, Float shiftDurationDay, Long shiftDurationMinute, Long minuteOfLate) {

        if (shiftDurationDay == null || shiftDurationDay <= 0 || shiftDurationMinute == null || shiftDurationMinute <= 0) {
            return 0F;
        }

        // Find salary per minute based on actual shift duration
        Float salaryPerMin = salary / shiftDurationDay / 24 / 60;

        // Late and upsent cut
        Float lateAndUpsentCut = minuteOfLate * salaryPerMin;

        return lateAndUpsentCut;
    }

    public static Float calculateApsent(Float salary, Float shiftDurationDay, Long getAttendDay, Long paidLeaveDay, Long amountOfDayExcludeSunday) {


        // Find salary per hour
        Float salaryPerDay = salary / 26;


        // Get total working day
        Long totalAbsentDay = amountOfDayExcludeSunday - getAttendDay;

        // Long absentDay = (shiftDurationDay * 4) - getAttendDay;

        Float totalAbsentDeductAmount;

        // Find total deduction absent
        if (totalAbsentDay > 0){
            totalAbsentDeductAmount = (totalAbsentDay - paidLeaveDay) * salaryPerDay;
        } else {
            totalAbsentDeductAmount = 0F;
        }

        return totalAbsentDeductAmount;
    }

    public static Float calculateLateUpsentOt(Float salary, Long minuteOfLate, Float shiftDurationDay, Long shiftDurationMinute, Long getAttendDay, Long amountOfDayExcludeSunday) {

        // Find deduction late salary per minute using actual shift duration
        Float salaryPerMin = 0F;
        if (shiftDurationDay != null && shiftDurationDay > 0 && shiftDurationMinute != null && shiftDurationMinute > 0) {
            salaryPerMin = salary / shiftDurationDay / shiftDurationMinute;
        }


        // Find deduction by absentDay
        // Long absentDay = (shiftDurationDay * 4) - getAttendDay;
        Long absentDay = amountOfDayExcludeSunday - getAttendDay;
        Float deductSalaryPerDay;
        if(absentDay > 0){
            deductSalaryPerDay = (salary / 26) * absentDay;
        } else {
            deductSalaryPerDay = 0F;
        }


        // Late and upsent cut total deduction
        Float lateAndUpsentCut = (minuteOfLate * salaryPerMin) + deductSalaryPerDay;

        if(Float.isNaN(lateAndUpsentCut)){
            lateAndUpsentCut = 0F;
        }

        return lateAndUpsentCut;
    }

    public static Long findAmountOfDayExcludeSunday(String payDate) {

        //! Find the working day till now.

        LocalDate date = LocalDate.parse(payDate, PAY_DATE_FORMATTER);
        LocalDate newDate = date.minusMonths(1);
        LocalDate endDate = LocalDate.now();

        long totalDays = ChronoUnit.DAYS.between(newDate, endDate) + 1;

        int sundays = 0;
        LocalDate currentDate = newDate;

        while (!currentDate.isAfter(endDate)) {
            if (currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                sundays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        // Calculate days excluding Sundays
        long daysExcludingSundays = totalDays - sundays;

        return daysExcludingSundays;
    }

    private String formatPayDate(String payDate) {
        try {
            // Parse the date assuming it's in yyyy-MM-dd format
            LocalDate date = LocalDate.parse(payDate);

            // Create a formatter for "Month YYYY" format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);

            return date.format(formatter);
        } catch (Exception e) {
            // If parsing fails, return the original date
            System.err.println("Error formatting pay date: " + e.getMessage());
            return payDate;
        }
    }

    public Long pushTelegramWithFileLink(String caption, String chatId, String apiToken, String fileLink) {
        // Validate inputs first
        if (chatId == null || chatId.isEmpty()) {
            System.err.println("Error: chatId is null or empty");
            return null;
        }
        if (apiToken == null || apiToken.isEmpty()) {
            System.err.println("Error: apiToken is null or empty");
            return null;
        }
        if (fileLink == null || fileLink.isEmpty()) {
            System.err.println("Error: fileLink is null or empty");
            return null;
        }

        String fileUrl = environment.getProperty("api.Link") + fileLink;
        String safeCaption = TelegramUtils.safeHtmlMessage(caption);

        try {
            // Download the file first
            URL url = new URL(fileUrl);
            InputStream inputStream = url.openStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] fileBytes = outputStream.toByteArray();
            inputStream.close();
            outputStream.close();

            // Determine file extension
            String fileName = fileLink.substring(fileLink.lastIndexOf('/') + 1);
            String fileExtension = fileName.toLowerCase();


            // Try sending as photo first (for image files)
            if (fileExtension.endsWith(".jpg") || fileExtension.endsWith(".jpeg") ||
                    fileExtension.endsWith(".png") || fileExtension.endsWith(".webp") ||
                    fileExtension.endsWith(".gif")) {

                String photoUrlString = String.format("https://api.telegram.org/bot%s/sendPhoto", apiToken);

                HttpHeaders photoHeaders = new HttpHeaders();
                photoHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

                MultiValueMap<String, Object> photoBody = new LinkedMultiValueMap<>();
                photoBody.add("chat_id", chatId);
                photoBody.add("caption", safeCaption);
                photoBody.add("parse_mode", "HTML");

                ByteArrayResource photoResource = new ByteArrayResource(fileBytes) {
                    @Override
                    public String getFilename() {
                        return fileName;
                    }
                };
                photoBody.add("photo", photoResource);

                HttpEntity<MultiValueMap<String, Object>> photoRequestEntity = new HttpEntity<>(photoBody, photoHeaders);

                try {
                    ResponseEntity<String> photoResponse = restTemplate.postForEntity(photoUrlString, photoRequestEntity, String.class);

                    if (photoResponse.getStatusCode().is2xxSuccessful() && photoResponse.getBody() != null) {
                        if (photoResponse.getBody().contains("\"ok\":true")) {
                            System.out.println("Successfully sent photo to Telegram");
                            return 1L;
                        }
                    }
                    System.err.println("Photo send failed: " + photoResponse.getBody());
                } catch (Exception photoException) {
                    System.out.println("Failed to send as photo: " + photoException.getMessage());
                }
            }

            // Send as document (works for all file types)
            String documentUrlString = String.format("https://api.telegram.org/bot%s/sendDocument", apiToken);

            HttpHeaders documentHeaders = new HttpHeaders();
            documentHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> documentBody = new LinkedMultiValueMap<>();
            documentBody.add("chat_id", chatId);
            documentBody.add("caption", safeCaption);
            documentBody.add("parse_mode", "HTML");

            ByteArrayResource documentResource = new ByteArrayResource(fileBytes) {
                @Override
                public String getFilename() {
                    return fileName;
                }
            };
            documentBody.add("document", documentResource);

            HttpEntity<MultiValueMap<String, Object>> documentRequestEntity = new HttpEntity<>(documentBody, documentHeaders);

            try {
                ResponseEntity<String> documentResponse = restTemplate.postForEntity(documentUrlString, documentRequestEntity, String.class);

                if (documentResponse.getStatusCode().is2xxSuccessful() && documentResponse.getBody() != null) {
                    if (documentResponse.getBody().contains("\"ok\":true")) {
                        System.out.println("Successfully sent document to Telegram");
                        return 1L;
                    }
                }

            } catch (Exception documentException) {
                System.err.println("Failed to send as document: " + documentException.getMessage());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

}
