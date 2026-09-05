package com.ut.nlSystemAPi.service;

import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.ut.nlSystemAPi.helper.GenerateQRCode;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.response.EmployeeGroupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.ut.nlSystemAPi.helper.GenerateStringsAndNumbers;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.EmployeesMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.UserMapper;
import com.ut.nlSystemAPi.model.Employee;
import com.ut.nlSystemAPi.model.EmployeeAchievement;
import com.ut.nlSystemAPi.model.EmployeeFilter;
import com.ut.nlSystemAPi.model.EmployeeMistake;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.Users.User;
import com.ut.nlSystemAPi.model.base.BaseFile;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.ApplyEmployeeTypesRequest;
import com.ut.nlSystemAPi.model.request.EmployeeRequest;
import com.ut.nlSystemAPi.model.request.EmployeeResetRequest;
import com.ut.nlSystemAPi.model.request.EmployeeTerminateSessionRequest;
import com.ut.nlSystemAPi.model.request.EmployeeUpdateRequest;
import com.ut.nlSystemAPi.model.response.EmployeeConnectedDeviceResponse;
import com.ut.nlSystemAPi.model.response.EmployeeDocumentResponse;
import com.ut.nlSystemAPi.model.response.EmployeesResponse;

@Service
public class EmployeesServiceImpl implements EmployeesService {

    @Autowired
    private EmployeesMapper employeesMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ActivityLogService activityLogService;

    public ResponseMessage<BaseResult> getList(EmployeeFilter filter) {
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId, "Employee (View)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }
        Pagination pagination = new Pagination();
        pagination.setPage(filter.getPage());
        pagination.setRowsPerPage(filter.getRowsPerPage());
        pagination.setTotal(employeesMapper.countList(filter));
        filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

        List<EmployeesResponse> employees = employeesMapper.getList(filter);
        for (EmployeesResponse employee : employees) {
            applyLocationGroups(employee);
            applySalesReps(employee);
            System.out.println("empId "+employee.getId());
            employee.setEmployeeGroupId(employeesMapper.getEmployeeGroup(employee.getId()));
        }

        return ResponseMessageUtils.makeResponse(true, messageService.message("Success",pagination, employees, true));
    }

    public ResponseMessage<BaseResult> getOne(Long id) {
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId, "Employee (View)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        List<EmployeesResponse> employees = employeesMapper.getOne(id);

        for (EmployeesResponse employee : employees) {
            employee.setQrCode("{\"A\":\"?\",\"B\":\"a\",\"C\":\"3\",\"D\":\"%\",\"E\":\"0\",\"F\":\"C\",\"G\":\"7\",\"H\":\"e\",\"I\":\"a\",\"J\":\"9\",\"K\":\"g\",\"L\":\"%\",\"M\":\"<\",\"N\":\"6\",\"O\":\"7\",\"P\":\"!\",\"Q\":\"&\",\"R\":\"C\",\"S\":\"@\",\"T\":\"c\",\"A6Z2\":\""+employee.getSysCode()+"\"}");
            employee.setProfilePhoto(employeesMapper.getProfilePhoto(employee.getId()));
            applyDocuments(employee);
            List<EmployeeConnectedDeviceResponse> deviceResponses = employeesMapper.getConnectedDevice(employee.getId());
            employee.setDeviceResponses(deviceResponses);
            applyLocationGroups(employee);
            applySalesReps(employee);

            Long userEmpId = employeesMapper.getUserIdByEmployeeId(employee.getId());

            System.out.println("employee");

            employee.setEmployeeGroupId(employeesMapper.getEmployeeGroup(userEmpId));
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", employees, true));
    }

    private void applyLocationGroups(EmployeesResponse employee) {
        employee.setWarehouses(employeesMapper.listEmployeeLocationGroups(employee.getId()));
    }

    private void applySalesReps(EmployeesResponse employee) {
        employee.setSalesReps(employeesMapper.listEmployeeSalesReps(employee.getId()));
    }

    private void applyDocuments(EmployeesResponse employee) {
        List<EmployeeDocumentResponse> employeeDocuments = employeesMapper.listEmpDocuments(employee.getId());
        List<BaseFile> documents = new ArrayList<>();
        for (EmployeeDocumentResponse employeeDocument : employeeDocuments) {
            BaseFile file = new BaseFile();
            file.setName(employeeDocument.getFileDocument());
            file.setUrl(employeeDocument.getFilePath());
            documents.add(file);
        }
        employee.setDocuments(documents);
    }

    private Employee mapToEmployee(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        if (employeeRequest instanceof EmployeeUpdateRequest) {
            employee.setId(((EmployeeUpdateRequest) employeeRequest).getId());
        }
        if (employeeRequest.getProfilePhoto() != null) {
            employee.setPhoto(employeeRequest.getProfilePhoto().getUrl());
            employee.setPhotoName(employeeRequest.getProfilePhoto().getName());
        }
        employee.setIdCard(employeeRequest.getIdCard());
        employee.setName(employeeRequest.getName());
        employee.setNameKh(employeeRequest.getNameKh());
        employee.setGender(employeeRequest.getGender());
        employee.setCode(employeeRequest.getEmployeeCode());
        employee.setDob(employeeRequest.getDob());
        employee.setAge(employeeRequest.getAge());
        employee.setPersonalNumber(employeeRequest.getPersonalNumber());
        employee.setPersonalEmail(employeeRequest.getPersonalEmail());
        employee.setOtherNumber(employeeRequest.getOtherNumber());
        employee.setEmail(employeeRequest.getEmail());
        employee.setPositionId(employeeRequest.getPositionId());
        employee.setAddress(employeeRequest.getAddress());
        employee.setTelephone(employeeRequest.getTelephone());
        employee.setEducation(employeeRequest.getEducation());
        employee.setWorkExperience(employeeRequest.getWorkExperience());
        employee.setReference(employeeRequest.getReference());
        employee.setIsShowInSales(employeeRequest.getIsShowInSales());
        employee.setIsDelivery(employeeRequest.getIsDelivery());
        employee.setIsCollector(employeeRequest.getIsCollector());
        employee.setIsAllowDiscount(employeeRequest.getIsAllowDiscount());
        employee.setEmployeeSysCode(GenerateStringsAndNumbers.generateRandomString(6));
        employee.setUnderSupervisorId(employeeRequest.getUnderSupervisorId());
        employee.setDepartmentId(employeeRequest.getDepartmentId());
        employee.setMaritalId(employeeRequest.getMaritalId());
        employee.setDateFrom(employeeRequest.getDateFrom());
        employee.setDateTo(employeeRequest.getDateTo());
        employee.setDate(employeeRequest.getDate());
        employee.setDateOfWork(employeeRequest.getDateOfWork());
        employee.setEmployeeStatusId(employeeRequest.getEmployeeStatusId());
        employee.setEmployeeTypeId(employeeRequest.getEmployeeTypeId());
        employee.setAttendanceStatus(employeeRequest.getAttendanceStatus());
        employee.setRemark(employeeRequest.getRemark());
        employee.setReason(employeeRequest.getReason());
        employee.setEmpReference(employeeRequest.getEmpReference());
        employee.setCurrentSalary(employeeRequest.getCurrentSalary());
        employee.setSalarySecondWorkshift(employeeRequest.getSalarySecondWorkshift());
        employee.setAllowance(employeeRequest.getAllowance());
        employee.setDayRate(employeeRequest.getDayRate());
        employee.setWeekendRate(employeeRequest.getWeekendRate());
        employee.setNightRate(employeeRequest.getNightRate());
        employee.setPublicHolidayRate(employeeRequest.getPublicHolidayRate());
        employee.setPaidBy(employeeRequest.getPaidType());
        employee.setIsPaid(employeeRequest.getIsPaid());
        employee.setAccountNumber(employeeRequest.getAccountNumber());
        employee.setAccountId(employeeRequest.getAccountId());
        employee.setBankImage(employeeRequest.getBankImage());
        employee.setBankImageName(employeeRequest.getBankImageName());
        employee.setIsDonated(employeeRequest.getIsDonated());
        employee.setIsProvidentFund(employeeRequest.getIsProvidentFund());
        employee.setIsDeposit(employeeRequest.getIsDeposit());
        employee.setNumMonth(employeeRequest.getNumMonth());
        employee.setAmountDeposit(employeeRequest.getAmountDeposit());
        employee.setDeadlineDate(employeeRequest.getDeadlineDate());
        employee.setChatId(employeeRequest.getChatId());
        employee.setIsActive(1);
        return employee;
    }

    private void updateWorkShifts(Long employeeId, EmployeeRequest employeeRequest, Long userId) {
        if (employeeRequest.getWorkShift1Ids() != null) {
            employeesMapper.deleteEmpWorkshift(employeeId, 1, userId);
            employeesMapper.insertEmpWorkshift(employeeId, employeeRequest.getWorkShift1Ids(), 1, userId);
        }

        if (employeeRequest.getWorkShift2Ids() != null) {
            employeesMapper.deleteEmpWorkshift(employeeId, 2, userId);
            employeesMapper.insertEmpWorkshift(employeeId, employeeRequest.getWorkShift2Ids(), 2, userId);
        }
    }

    private void updateDocuments(Long employeeId, EmployeeRequest employeeRequest, Long userId) {
        if (employeeRequest.getDocuments() != null) {
            employeesMapper.deleteEmpDocuments(employeeId);
            for (BaseFile document : employeeRequest.getDocuments()) {
                employeesMapper.insertEmpDocument(employeeId, document.getName(), document.getUrl());
            }
        }
    }

    private void updateLocationGroups(Long employeeId, List<Long> locationGroupIds) {
        if (locationGroupIds == null) {
            return;
        }
        employeesMapper.deleteEmployeeLocationGroups(employeeId);
        Set<Long> uniqueLocationGroupIds = new LinkedHashSet<>(locationGroupIds);
        for (Long locationGroupId : uniqueLocationGroupIds) {
            if (locationGroupId != null) {
                employeesMapper.insertEmployeeLocationGroup(employeeId, locationGroupId);
            }
        }
    }

    private void updateEmployeeSalesReps(Long employeeId, List<Long> salesRepIds) {
        if (salesRepIds == null) {
            return;
        }
        employeesMapper.deleteEmployeeSalesRep(employeeId);
        Set<Long> uniqueSalesRepIds = new LinkedHashSet<>(salesRepIds);
        for (Long salesRepId : uniqueSalesRepIds) {
            if (salesRepId != null) {
                employeesMapper.insertEmployeeSalesRep(employeeId, salesRepId);
            }
        }
    }

    private void applyEmployeeRequestToUser(User user, EmployeeRequest employeeRequest) {
        user.setFullName(employeeRequest.getName());
        user.setSex(employeeRequest.getGender() == null ? null : employeeRequest.getGender().toString());
        user.setDob(employeeRequest.getDob());
        user.setAddress(employeeRequest.getAddress());
        user.setTelephone(employeeRequest.getTelephone());
        user.setEmail(employeeRequest.getEmail());
        if (employeeRequest.getProfilePhoto() != null) {
            user.setPhoto(employeeRequest.getProfilePhoto().getUrl());
        }
    }

    public ResponseMessage<BaseResult> insert(EmployeeRequest employeeRequest) {
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId, "Employee (Add)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }
        if (employeesMapper.checkDuplicate(employeeRequest.getName(), employeeRequest.getNameKh(), employeeRequest.getIdCard(), null) > 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate employee", false));
        }

        User user = new User();
        Employee employee = mapToEmployee(employeeRequest);
        employee.setUserId(userId);
        if (employee.getEmployeeSysCode() == null || employee.getEmployeeSysCode().isEmpty()) {
            employee.setEmployeeSysCode(GenerateStringsAndNumbers.generateRandomString(10));
        }
        if (employee.getIsShowInSales() == null) {
            employee.setIsShowInSales(0);
        }
        if (employee.getCurrentSalary() == null) {
            employee.setCurrentSalary(0f);
        }
        if (employee.getAllowance() == null) {
            employee.setAllowance(0f);
        }
        if (employee.getDayRate() == null) {
            employee.setDayRate(0f);
        }
        if (employee.getWeekendRate() == null) {
            employee.setWeekendRate(0f);
        }
        if (employee.getNightRate() == null) {
            employee.setNightRate(0f);
        }
        if (employee.getPublicHolidayRate() == null) {
            employee.setPublicHolidayRate(0f);
        }
        if (employee.getPaidBy() == null) {
            employee.setPaidBy(1);
        }
        if (employee.getIsProvidentFund() == null) {
            employee.setIsProvidentFund(0);
        }
        if (employee.getIsPaid() == null) {
            employee.setIsPaid(0);
        }
        if (employee.getIsDeposit() == null) {
            employee.setIsDeposit(0);
        }
        if (employee.getIsDeposit() == null) {
            employee.setIsDeposit(0);
        }
        if (employee.getNumMonth() == null) {
            employee.setNumMonth(0);
        }
        Boolean result = employeesMapper.insert(employee);
        User userSystem = new User();
//        userSystem.setUsername(employeeRequest.getUsername());
//        userSystem.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
        userSystem.setEmployeeId(employee.getId());
        userSystem.setType(0L);
        applyEmployeeRequestToUser(userSystem, employeeRequest);
        userSystem.setCreatedBy(userId);
        userSystem.setIsActive(1);

        Boolean userSystemInsertResult = userMapper.insert(userSystem);
        System.out.println("user system sc"+userSystemInsertResult);
        if (!userSystemInsertResult || userSystem.getId() == null) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }

        if (result) {
            userMapper.updateEmployeeId(employee.getId(), user.getId());

            if (employee.getPositionId() != null) {
                employeesMapper.insertPositionHistory(employee.getId(), employee.getPositionId(), userId);
            }


            if (employeeRequest.getEmployeeGroupId() != null) {

                Long userIdEmp = employeesMapper.getUserIdByEmployeeId(employee.getId());
                for(int i=0;i<employeeRequest.getEmployeeGroupId().size();i++) {
                  employeesMapper.insertEmployeeEgroup(employeeRequest.getEmployeeGroupId().get(i), userIdEmp);
                  employeesMapper.insertDepartmentHistory(employee.getId(), employeeRequest.getEmployeeGroupId().get(i), userId);
              }
            }

            if (employee.getEmployeeTypeId() != null) {
                ApplyEmployeeTypesRequest employeeTypesRequest = new ApplyEmployeeTypesRequest();
                employeeTypesRequest.setEmployeeId(employee.getId());
                employeeTypesRequest.setEmployeeTypeId(employee.getEmployeeTypeId());
                employeeTypesRequest.setDateFrom(employee.getEmployeeTypeDateFrom());
                employeeTypesRequest.setDateTo(employee.getEmployeeTypeDateTo());
                employeeTypesRequest.setCreatedBy(userId);
                employeesMapper.insertEmployeeTypes(employeeTypesRequest);
            }

            updateWorkShifts(employee.getId(), employeeRequest, userId);

            updateDocuments(employee.getId(), employeeRequest, userId);

            updateLocationGroups(employee.getId(), employeeRequest.getWarehouses());

            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        }
        userMapper.delete(user.getId());
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
    }

    public ResponseMessage<BaseResult> update(EmployeeUpdateRequest employeeRequest) {
        System.out.println("EMPID "+employeeRequest.getId());
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId, "Employee (Edit)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        if (employeesMapper.checkDuplicate(employeeRequest.getName(), employeeRequest.getNameKh(),
                employeeRequest.getIdCard(), employeeRequest.getId()) > 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate employee", false));
        }

        Employee employee = mapToEmployee(employeeRequest);
        employee.setModifiedBy(userId);


        Boolean result = employeesMapper.update(employee);
        if (result) {
            Long userEmpId = employeesMapper.getUserIdByEmployeeId(employeeRequest.getId());
            if (userEmpId != null) {
                User userSystem = new User();
                userSystem.setId(userEmpId);
                applyEmployeeRequestToUser(userSystem, employeeRequest);
                userSystem.setModifiedBy(userId);
                userMapper.update(userSystem);
            }

            if (employee.getPositionId() != null) {
                employeesMapper.insertPositionHistory(employee.getId(), employee.getPositionId(), userId);
            }

            if (employeeRequest.getEmployeeGroupId() != null) {

//                Long userIdEmp = employeesMapper.getUserIdByEmployeeId(employeeRequest.getId());
//                System.out.println("userEmp Id "+userIdEmp);
                //remove old group
                employeesMapper.deleteEmployeeEgroup(userEmpId);

                for(int i=0;i<employeeRequest.getEmployeeGroupId().size();i++) {
                    employeesMapper.insertEmployeeEgroup(employeeRequest.getEmployeeGroupId().get(i), userEmpId);
                    employeesMapper.insertDepartmentHistory(employee.getId(), employeeRequest.getEmployeeGroupId().get(i), userId);
                }
            }
            if (employeeRequest.getDepartmentId() != null) {
                employeesMapper.insertDepartmentHistory(employee.getId(), employeeRequest.getDepartmentId(), userId);
            }


            updateWorkShifts(employee.getId(), employeeRequest, userId);

            updateDocuments(employee.getId(), employeeRequest, userId);

            updateLocationGroups(employee.getId(), employeeRequest.getWarehouses());

            updateEmployeeSalesReps(employee.getId(), employeeRequest.getSalesReps());

            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
    }

    public ResponseMessage<BaseResult> delete(Long id) {
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId, "Employee (Delete)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        Long userEmpId = employeesMapper.getUserIdByEmployeeId(id);
        Boolean result = employeesMapper.delete(id, userId);
        if (result) {
            if (userEmpId != null) {
                userMapper.delete(userEmpId);
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
    }

    public ResponseMessage<BaseResult> reset(EmployeeResetRequest employeeResetRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 0L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (bindingResult.hasErrors()) {
                return ResponseMessageUtils.makeResponse(true, bindingResult);
            }

            if (employeeResetRequest.getId() == null || employeeResetRequest.getId() == 0
                    || employeeResetRequest.getUsername() == null || employeeResetRequest.getUsername().isEmpty()
                    || employeeResetRequest.getPassword() == null || employeeResetRequest.getPassword().isEmpty()) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Required", false));
            }

            Long existingUsername = employeesMapper.checkDuplicateUsername(employeeResetRequest.getUsername(), employeeResetRequest.getId());

            if (existingUsername != null && existingUsername > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate User", false));
            }

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode(employeeResetRequest.getPassword());

            Employee employee = new Employee();
            employee.setId(employeeResetRequest.getId());
            employee.setUsername(employeeResetRequest.getUsername());
            employee.setPassword(password);
            employee.setModifiedBy(userId);

            Boolean result = employeesMapper.update(employee);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/employee/reset", null, null, "Employee", "Employee (Edit)", "Edit", 1,
                        "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee/reset", line, error.toString(), "Employee", "Employee (Edit)", "Edit",
                    2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> terminateSession(
            EmployeeTerminateSessionRequest employeeTerminateSessionRequest) {
        Boolean result = employeesMapper.terminateSession(employeeTerminateSessionRequest);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
    }

    public ResponseMessage<BaseResult> getEmployeeAchievement(Long id) {
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId, "Employee (View)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        List<EmployeeAchievement> employeeAchievementList = employeesMapper.getEmployeeAchievement(id);
        if (employeeAchievementList != null && !employeeAchievementList.isEmpty()) {
            for (EmployeeAchievement employeeAchievement : employeeAchievementList) {
                if (employeeAchievement.getCreatedBy().equals(userId)) {
                    employeeAchievement.setIsEditAchievementByUser(true);
                } else employeeAchievement.setIsEditAchievementByUser(employeesMapper.checkUserEditAchievement(userId) > 0);
            }
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", employeeAchievementList, true));
    }

    public ResponseMessage<BaseResult> getEmployeeMistake(Long id) {
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId, "Employee (View)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        List<EmployeeMistake> employeeMistakeList = employeesMapper.getEmployeeMistake(id);
        if (employeeMistakeList != null && !employeeMistakeList.isEmpty()) {
            for (EmployeeMistake employeeMistake : employeeMistakeList) {
                if (employeeMistake.getCreatedBy().equals(userId)) {
                    employeeMistake.setIsEditMistakeByUser(true);
                } else employeeMistake.setIsEditMistakeByUser(employeesMapper.checkUserEditMistake(userId) > 0);
            }
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", employeeMistakeList, true));
    }

}
