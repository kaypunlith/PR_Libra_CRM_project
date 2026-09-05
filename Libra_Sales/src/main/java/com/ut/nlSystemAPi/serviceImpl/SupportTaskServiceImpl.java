package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.SupportTaskMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.SupportTicket.SupportTask;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportTaskRequest;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportTaskUpdateRequest;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTaskResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.SupportTaskService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class SupportTaskServiceImpl implements SupportTaskService {

    @Autowired
    private SupportTaskMapper supportTaskMapper;

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
            if (permissionMapper.checkPermission(userId, "Support Ticket Task (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(supportTaskMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<SupportTaskResponse> responses = supportTaskMapper.getList(filter);
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-task/list", null, null, "Support Ticket Task", "Support Ticket Task (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-task/list", line, error.toString(), "Support Ticket Task", "Support Ticket Task (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket Task (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<SupportTaskResponse> responses = supportTaskMapper.getOne(id);
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-task/find/{id}", null, null, "Support Ticket Task", "Support Ticket Task (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-task/find/{id}", line, error.toString(), "Support Ticket Task", "Support Ticket Task (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(SupportTaskRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket Task (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (supportTaskMapper.checkDuplicate(request.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            SupportTask task = toTask(request);
            task.setCreatedBy(userId);
            task.setIsActive(1);
            Boolean result = supportTaskMapper.insert(task);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/support-task/add", null, null, "Support Ticket Task", "Support Ticket Task (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-task/add", line, error.toString(), "Support Ticket Task", "Support Ticket Task (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(SupportTaskUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket Task (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (supportTaskMapper.checkDuplicate(request.getName(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            SupportTask task = toTask(request);
            task.setId(request.getId());
            task.setModifiedBy(userId);
            Boolean result = supportTaskMapper.update(task);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/support-task/update", null, null, "Support Ticket Task", "Support Ticket Task (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-task/update", line, error.toString(), "Support Ticket Task", "Support Ticket Task (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket Task (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Boolean result = supportTaskMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/support-task/delete/{id}", null, null, "Support Ticket Task", "Support Ticket Task (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-task/delete/{id}", line, error.toString(), "Support Ticket Task", "Support Ticket Task (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private SupportTask toTask(SupportTaskRequest request) {
        SupportTask task = new SupportTask();
        task.setActivityId(request.getActivityId());
        task.setName(request.getName());
        return task;
    }
}


