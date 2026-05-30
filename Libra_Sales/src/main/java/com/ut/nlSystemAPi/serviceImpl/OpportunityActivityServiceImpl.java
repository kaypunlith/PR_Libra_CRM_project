package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.OpportunityActivityMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Opportunities.OpportunityActivity;
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityActivityRequest;
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityActivityUpdateRequest;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityActivityResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.OpportunityActivityService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class OpportunityActivityServiceImpl implements OpportunityActivityService {

    @Autowired
    private OpportunityActivityMapper opportunityActivityMapper;

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
            if (permissionMapper.checkPermission(userId, "Opportunities Activity (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(opportunityActivityMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<OpportunityActivityResponse> responses = opportunityActivityMapper.getList(filter);
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-activity/list", null, null, "Opportunities Activity", "Opportunities Activity (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-activity/list", line, error.toString(), "Opportunities Activity", "Opportunities Activity (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities Activity (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<OpportunityActivityResponse> responses = opportunityActivityMapper.getOne(id);
            for (OpportunityActivityResponse response : responses) {
                response.setEmployeeGroupIds(opportunityActivityMapper.getEmployeeGroupIds(response.getId()));
                response.setStageIds(opportunityActivityMapper.getStageIds(response.getId()));
                response.setTasks(opportunityActivityMapper.getTasks(response.getId()));
            }
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-activity/find/{id}", null, null, "Opportunities Activity", "Opportunities Activity (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-activity/find/{id}", line, error.toString(), "Opportunities Activity", "Opportunities Activity (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(OpportunityActivityRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities Activity (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (opportunityActivityMapper.checkDuplicate(request.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            OpportunityActivity activity = new OpportunityActivity();
            activity.setName(request.getName());
            activity.setCreatedBy(userId);
            activity.setIsActive(1);
            Boolean result = opportunityActivityMapper.insert(activity);
            if (result) {
                saveDetails(activity.getId(), request, userId);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/opportunity-activity/add", null, null, "Opportunities Activity", "Opportunities Activity (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-activity/add", line, error.toString(), "Opportunities Activity", "Opportunities Activity (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(OpportunityActivityUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities Activity (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (opportunityActivityMapper.checkDuplicate(request.getName(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            OpportunityActivity activity = new OpportunityActivity();
            activity.setId(request.getId());
            activity.setName(request.getName());
            activity.setModifiedBy(userId);
            Boolean result = opportunityActivityMapper.update(activity);
            if (result) {
                opportunityActivityMapper.deleteEmployeeGroup(request.getId());
                opportunityActivityMapper.deleteStage(request.getId());
                opportunityActivityMapper.deleteTask(request.getId(), userId);
                saveDetails(request.getId(), request, userId);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/opportunity-activity/update", null, null, "Opportunities Activity", "Opportunities Activity (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-activity/update", line, error.toString(), "Opportunities Activity", "Opportunities Activity (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities Activity (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Boolean result = opportunityActivityMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/opportunity-activity/delete/{id}", null, null, "Opportunities Activity", "Opportunities Activity (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-activity/delete/{id}", line, error.toString(), "Opportunities Activity", "Opportunities Activity (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private void saveDetails(Long activityId, OpportunityActivityRequest request, Long userId) {
        if (request.getEmployeeGroupIds() != null) {
            for (Long employeeGroupId : request.getEmployeeGroupIds()) {
                opportunityActivityMapper.insertEmployeeGroup(activityId, employeeGroupId);
            }
        }
        if (request.getStageIds() != null) {
            for (Long stageId : request.getStageIds()) {
                opportunityActivityMapper.insertStage(activityId, stageId);
            }
        }
        if (request.getTasks() != null) {
            for (String task : request.getTasks()) {
                if (task != null && !task.trim().isEmpty()) {
                    opportunityActivityMapper.insertTask(activityId, task, userId);
                }
            }
        }
    }
}
