package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ClassPartnerManagementMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.ClassPartnerManagement.ClassPartnerManagement;
import com.ut.nlSystemAPi.model.request.ClassPartnerManagement.ClassPartnerManagementRequest;
import com.ut.nlSystemAPi.model.request.ClassPartnerManagement.ClassPartnerManagementTypeNetworkEmployeeRequest;
import com.ut.nlSystemAPi.model.request.ClassPartnerManagement.ClassPartnerManagementUpdateRequest;
import com.ut.nlSystemAPi.model.response.ClassPartnerManagement.ClassPartnerManagementResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.ClassPartnerManagementService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class ClassPartnerManagementServiceImpl implements ClassPartnerManagementService {

    @Autowired
    private ClassPartnerManagementMapper classPartnerManagementMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Class Partner Management (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(classPartnerManagementMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ClassPartnerManagementResponse> responses = classPartnerManagementMapper.getList(filter);
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-partner-management/list", null, null, "Class Partner Management", "Class Partner Management (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-partner-management/list", line, error.toString(), "Class Partner Management", "Class Partner Management (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Class Partner Management (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<ClassPartnerManagementResponse> responses = classPartnerManagementMapper.getOne(id);
            if (!responses.isEmpty()) {
//                responses.get(0).setEgroupIds(classPartnerManagementMapper.getEgroupIds(id));
                responses.get(0).setTypeNetworkEmployees(classPartnerManagementMapper.getTypeNetworkEmployees(id));
            }
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-partner-management/find/{id}", null, null, "Class Partner Management", "Class Partner Management (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-partner-management/find/{id}", line, error.toString(), "Class Partner Management", "Class Partner Management (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    @Transactional
    public ResponseMessage<BaseResult> insert(ClassPartnerManagementRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Class Partner Management (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (classPartnerManagementMapper.checkDuplicate(request.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            ClassPartnerManagement classPartnerManagement = new ClassPartnerManagement();
            classPartnerManagement.setName(request.getName());
            classPartnerManagement.setCreatedBy(userId);
            classPartnerManagement.setIsActive(1);
            Boolean result = classPartnerManagementMapper.insert(classPartnerManagement);
            if (result) {
                saveRelations(classPartnerManagement.getId(), request);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/class-partner-management/add", null, null, "Class Partner Management", "Class Partner Management (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-partner-management/add", line, error.toString(), "Class Partner Management", "Class Partner Management (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    @Transactional
    public ResponseMessage<BaseResult> update(ClassPartnerManagementUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Class Partner Management (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (classPartnerManagementMapper.checkDuplicate(request.getName(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            ClassPartnerManagement classPartnerManagement = new ClassPartnerManagement();
            classPartnerManagement.setId(request.getId());
            classPartnerManagement.setName(request.getName());
            classPartnerManagement.setModifiedBy(userId);
            Boolean result = classPartnerManagementMapper.update(classPartnerManagement);
            if (result) {
                classPartnerManagementMapper.deleteEgroups(request.getId());
                classPartnerManagementMapper.deleteTypeNetworkEmployees(request.getId());
                saveRelations(request.getId(), request);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/class-partner-management/update", null, null, "Class Partner Management", "Class Partner Management (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-partner-management/update", line, error.toString(), "Class Partner Management", "Class Partner Management (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Class Partner Management (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = classPartnerManagementMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/class-partner-management/delete/{id}", null, null, "Class Partner Management", "Class Partner Management (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-partner-management/delete/{id}", line, error.toString(), "Class Partner Management", "Class Partner Management (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private void saveRelations(Long classId, ClassPartnerManagementRequest request) {
        if (request.getEmployeeGroupIds() != null) {
            for (Long egroupId : request.getEmployeeGroupIds()) {
                if (egroupId != null) {
                    classPartnerManagementMapper.insertEgroup(classId, egroupId);
                }
            }
        }
        if (request.getTypeNetworkEmployees() != null) {
            for (ClassPartnerManagementTypeNetworkEmployeeRequest item : request.getTypeNetworkEmployees()) {
                if (item.getTypeNetworkId() != null && item.getUserIds() != null) {
                    for (Long userId : item.getUserIds()) {
                        if (userId != null) {
                            classPartnerManagementMapper.insertTypeNetworkEmployee(classId, item.getTypeNetworkId(), userId);
                        }
                    }
                }
            }
        }
    }
}
