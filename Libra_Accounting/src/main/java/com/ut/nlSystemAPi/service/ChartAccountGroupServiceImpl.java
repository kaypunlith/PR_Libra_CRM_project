package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ChartAccountGroupMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.ChartAccountGroup;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ChartAccountGroupFilter;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountGroup.ChartAccountGroupRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountGroup.ChartAccountGroupUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartAccountGroup.ChartAccountGroupUpdateStatusRequest;
import com.ut.nlSystemAPi.model.response.ChartAccountGroup.ChartAccountGroupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class ChartAccountGroupServiceImpl implements ChartAccountGroupService {

    @Autowired
    private ChartAccountGroupMapper chartAccountGroupMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override

    public ResponseMessage<BaseResult> getList(ChartAccountGroupFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {

        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account Group (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(chartAccountGroupMapper.countList(filter));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ChartAccountGroupResponse> chartAccountGroupResponse = chartAccountGroupMapper.getList(filter);

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-group/list", null, null, "Chart of Account Group", "Chart of Account Group (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", chartAccountGroupResponse, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-group/list", 1033L, error.toString(), "Chart of Account Group", "Chart of Account Group (view)", "View",
                    2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateStatus(ChartAccountGroupUpdateStatusRequest updateStatusRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account Group (change status)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true,
                        messageService.message("No Permission access.", false));
            }

            // Check Data
            ChartAccountGroup chartAccountGroup = new ChartAccountGroup();
            chartAccountGroup.setId(updateStatusRequest.getId());
            chartAccountGroup.setStatus(updateStatusRequest.getStatus());
            chartAccountGroup.setModifiedBy(userId);
            Boolean result = chartAccountGroupMapper.updateStatus(chartAccountGroup);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/chart-account-group/update-status", null, null, "Chart of Account Group", "Chart of Account Group (change status)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-group/update-status", line, error.toString(), "Chart of Account Group", "Chart of Account Group (change status)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account Group (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<ChartAccountGroupResponse> chartAccountGroupResponse = chartAccountGroupMapper.getOne(id);

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-group/find/{id}", null, null, "CChart of Account Group", "Chart of Account Group (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", chartAccountGroupResponse, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-group/find/{id}", 1033L, error.toString(), "Chart of Account Group", "Chart of Account Group (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ChartAccountGroupRequest accountGroupRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission | use static permission module
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account Group (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if (chartAccountGroupMapper.checkDuplicate(accountGroupRequest.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate chart account group name", false));
            }

            // Insert Data
            ChartAccountGroup group = new ChartAccountGroup();
            group.setAccountTypeId(accountGroupRequest.getAccountTypeId());
            group.setExpense(accountGroupRequest.getExpense());
            group.setName(accountGroupRequest.getName());
            group.setCreatedBy(userId);
            group.setIsActive(1);
            Boolean result = chartAccountGroupMapper.insert(group);

            if (result) {
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/chart-account-group/add", null, null, "Chart of Account Group", "Chart of Account Group (add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-group/add", 1033L, error.toString(), "Chart of Account Group", "Chart of Account Group (add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(ChartAccountGroupUpdateRequest groupUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account Group (edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if (chartAccountGroupMapper.checkDuplicate(groupUpdateRequest.getName(), groupUpdateRequest.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate name chart account group", false));
            }

            // Update Data
            ChartAccountGroup chartAccountGroup = new ChartAccountGroup();
            chartAccountGroup.setId(groupUpdateRequest.getId());
            chartAccountGroup.setAccountTypeId(groupUpdateRequest.getAccountTypeId());
            chartAccountGroup.setName(groupUpdateRequest.getName());
            chartAccountGroup.setExpense(groupUpdateRequest.getExpense());
            chartAccountGroup.setModifiedBy(userId);
            Boolean result = chartAccountGroupMapper.update(chartAccountGroup);

            if (result) {
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/chart-account-group/update", null, null, "Chart of Account Group", "Chart of Account Group (edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-group/update", 1033L, error.toString(), "Chart of Account Group", "Chart of Account Group (edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission | use static permission (edit)
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account Group (delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = chartAccountGroupMapper.delete(id, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-group/delete/{id}", null, null, "Chart of Account Group", "Chart of Account Group (delete)", "Delete",
                    1, "Success", startDuration, endDuration, httpServletRequest);
            if (result) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-group/delete/{id}", line, error.toString(), "Chart of Account Group","Chart of Account Group (delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}
