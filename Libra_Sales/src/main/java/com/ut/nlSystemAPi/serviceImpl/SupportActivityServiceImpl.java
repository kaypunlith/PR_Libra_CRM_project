package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.SupportActivityMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.SupportTicket.SupportActivity;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportActivityRequest;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportActivityUpdateRequest;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportActivityResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.SupportActivityService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class SupportActivityServiceImpl implements SupportActivityService {

    @Autowired
    private SupportActivityMapper supportActivityMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket Activity (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(supportActivityMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<SupportActivityResponse> responses = supportActivityMapper.getList(filter);
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-activity/list", null, null, "Support Ticket Activity", "Support Ticket Activity (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-activity/list", line, error.toString(), "Support Ticket Activity", "Support Ticket Activity (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket Activity (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<SupportActivityResponse> responses = supportActivityMapper.getOne(id);
            for (SupportActivityResponse response : responses) {
                response.setEmployeeGroupIds(supportActivityMapper.getEmployeeGroupIds(response.getId()));
                response.setStageIds(supportActivityMapper.getStageIds(response.getId()));
                response.setTasks(supportActivityMapper.getTasks(response.getId()));
            }
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-activity/find/{id}", null, null, "Support Ticket Activity", "Support Ticket Activity (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-activity/find/{id}", line, error.toString(), "Support Ticket Activity", "Support Ticket Activity (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(SupportActivityRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket Activity (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (supportActivityMapper.checkDuplicate(request.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            SupportActivity activity = new SupportActivity();
            activity.setName(request.getName());
            activity.setCreatedBy(userId);
            activity.setIsActive(1);
            Boolean result = supportActivityMapper.insert(activity);
            if (result) {
                saveDetails(activity.getId(), request, userId);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/support-activity/add", null, null, "Support Ticket Activity", "Support Ticket Activity (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-activity/add", line, error.toString(), "Support Ticket Activity", "Support Ticket Activity (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(SupportActivityUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket Activity (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (supportActivityMapper.checkDuplicate(request.getName(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            SupportActivity activity = new SupportActivity();
            activity.setId(request.getId());
            activity.setName(request.getName());
            activity.setModifiedBy(userId);
            Boolean result = supportActivityMapper.update(activity);
            if (result) {
                supportActivityMapper.deleteEmployeeGroup(request.getId());
                supportActivityMapper.deleteStage(request.getId());
                supportActivityMapper.deleteTask(request.getId(), userId);
                saveDetails(request.getId(), request, userId);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/support-activity/update", null, null, "Support Ticket Activity", "Support Ticket Activity (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-activity/update", line, error.toString(), "Support Ticket Activity", "Support Ticket Activity (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket Activity (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Boolean result = supportActivityMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/support-activity/delete/{id}", null, null, "Support Ticket Activity", "Support Ticket Activity (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-activity/delete/{id}", line, error.toString(), "Support Ticket Activity", "Support Ticket Activity (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private void saveDetails(Long activityId, SupportActivityRequest request, Long userId) {
        if (request.getEmployeeGroupIds() != null) {
            for (Long employeeGroupId : request.getEmployeeGroupIds()) {
                supportActivityMapper.insertEmployeeGroup(activityId, employeeGroupId);
            }
        }
        if (request.getStageIds() != null) {
            for (Long stageId : request.getStageIds()) {
                supportActivityMapper.insertStage(activityId, stageId);
            }
        }
        if (request.getTaskNames() != null) {
            for (String task : request.getTaskNames()) {
                if (task != null && !task.trim().isEmpty()) {
                    supportActivityMapper.insertTask(activityId, task, userId);
                }
            }
        }
    }
}


