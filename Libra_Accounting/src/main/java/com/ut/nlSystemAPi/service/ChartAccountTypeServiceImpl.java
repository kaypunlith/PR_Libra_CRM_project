package com.ut.nlSystemAPi.service;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ut.nlSystemAPi.model.request.Login.ChartAccountType.ChartAccountTypeUpdateStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ChartAccountTypeMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.UserMapper;
import com.ut.nlSystemAPi.model.ChartAccountType;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountType.ChartAccountTypeRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountType.ChartAccountTypeUpdateRequest;
import com.ut.nlSystemAPi.model.response.ChartAccountType.ChartAccountTypeResponse;

@Service
public class ChartAccountTypeServiceImpl implements ChartAccountTypeService {

    @Autowired
    private ChartAccountTypeMapper chartAccountTypeMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account Type (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(chartAccountTypeMapper.countList(filter));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ChartAccountTypeResponse> chartAccountTypeResponse = chartAccountTypeMapper.getList(filter);

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-type/list", null, null, "Chart of Account Type", "Chart of Account Type (view)", "View", 1,"Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", chartAccountTypeResponse, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-type/list", 1033L, error.toString(), "Chart of Account Type", "Chart of Account Type (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateStatus(ChartAccountTypeUpdateStatusRequest chartAccountTypeUpdateStatusRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account Type (change status)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            ChartAccountType chartAccountType = new ChartAccountType();
            chartAccountType.setId(chartAccountTypeUpdateStatusRequest.getId());
            chartAccountType.setStatus(chartAccountTypeUpdateStatusRequest.getStatus());
            chartAccountType.setModifiedBy(userId);
            Boolean result = chartAccountTypeMapper.updateStatus(chartAccountType);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/chart-account-type/update-status", null, null, "Chart of Account Type", "Chart of Account Type (change status)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-type/update-status", line, error.toString(), "Chart of Account Type", "Chart of Account Type (change status)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account Type (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<ChartAccountTypeResponse> chartAccountTypeResponse = chartAccountTypeMapper.getOne(id);

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-type/find/{id}", null, null, "Chart of Account Type", "Chart of Account Type (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", chartAccountTypeResponse, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-type/find/{id}", 1033L, error.toString(), "Chart of Account Type", "Chart of Account Type (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ChartAccountTypeRequest chartAccountRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission | use static permission module
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account Type (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if (chartAccountTypeMapper.checkDuplicate(chartAccountRequest.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate chart account type name", false));
            }

            // Insert Data
            ChartAccountType chartAccountType = new ChartAccountType();
            chartAccountType.setName(chartAccountRequest.getName());
            chartAccountType.setCreatedBy(userId);
            chartAccountType.setIsActive(1);
            Boolean result = chartAccountTypeMapper.insert(chartAccountType);

            if (result) {
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/chart-account-type/add", null, null, "Chart of Account Type", "Chart of Account Type (add)  ", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-type/add", 1033L, error.toString(), "Chart of Account Type", "Chart of Account Type (add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(ChartAccountTypeUpdateRequest chartAccountTypeUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account Type (edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if (chartAccountTypeMapper.checkDuplicate(chartAccountTypeUpdateRequest.getName(), chartAccountTypeUpdateRequest.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate name chart account type", false));
            }

            // Update Data
            ChartAccountType chartAccountType = new ChartAccountType();
            chartAccountType.setId(chartAccountTypeUpdateRequest.getId());
            chartAccountType.setName(chartAccountTypeUpdateRequest.getName());
            chartAccountType.setIsActive(chartAccountTypeUpdateRequest.getStatus());
            chartAccountType.setModifiedBy(userId);
            Boolean result = chartAccountTypeMapper.update(chartAccountType);

            if (result) {
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/chart-account-type/update", null, null, "Chart of Account Type", "Chart of Account Type (edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-type/update", 1033L, error.toString(), "Chart of Account Type", "Chart of Account Type (edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest)
            throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account Type (delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true,
                        messageService.message("No Permission access.", false));
            }

            Boolean result = chartAccountTypeMapper.delete(id, userId);
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-type/delete/{id}", null, null, "Chart of Account Type", "Chart of Account Type (delete)", "Delete",1, "Success", startDuration, endDuration, httpServletRequest);
            if (result) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-type/delete/{id}", line, error.toString(), "Chart of Account Type",
                    "Chart of Account Type (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
