package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.OpportunitiesMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Checklist.ChecklistSaveRequest;
import com.ut.nlSystemAPi.model.response.Checklist.ChecklistResponse;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityAddMoreDetailResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.OpportunityChecklistService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OpportunityChecklistServiceImpl implements OpportunityChecklistService {

    @Autowired
    private OpportunitiesMapper opportunitiesMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    public ResponseMessage<BaseResult> find(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            OpportunityAddMoreDetailResponse detail = opportunitiesMapper.getActiveDetail(id);
            if (detail == null) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }

            ChecklistResponse response = new ChecklistResponse();
            response.setPipelineId(detail.getPipelineId());
            response.setStageId(detail.getStageId());
            response.setStageName(detail.getStageName());
            response.setDescription(detail.getDescription());
            response.setActivities(opportunitiesMapper.getChecklistActivities(id, detail.getPipelineId(), detail.getStageId(), userId));
            response.setTasks(opportunitiesMapper.getChecklistTasks(id, detail.getPipelineId(), detail.getStageId()));

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-checklist/find/{id}", null, null, "Opportunity Checklist", "Opportunities (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", Collections.singletonList(response), true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-checklist/find/{id}", line, error.toString(), "Opportunity Checklist", "Opportunities (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Transactional
    public ResponseMessage<BaseResult> save(Long id, ChecklistSaveRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            OpportunityAddMoreDetailResponse detail = opportunitiesMapper.getActiveDetail(id);
            if (detail == null || request == null) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }

            Boolean saved = saveActivities(id, detail.getStageId(), request.getActivityIds(), userId);
            saved = saveTasks(id, detail.getStageId(), request.getTaskIds(), userId) || saved;
            opportunitiesMapper.updateDetailDescription(detail.getId(), request.getDescription());
            opportunitiesMapper.touchOpportunity(id, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-checklist/save/{id}", null, null, "Opportunity Checklist", "Opportunities (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", saved));
        } catch (Exception error) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-checklist/save/{id}", line, error.toString(), "Opportunity Checklist", "Opportunities (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private Boolean saveActivities(Long opportunityId, Long stageId, List<Long> activityIds, Long userId) {
        Boolean saved = false;
        Set<Long> activeIds = new HashSet<>(opportunitiesMapper.getActiveActivityIds(opportunityId));
        opportunitiesMapper.archiveActivityDetails(opportunityId, userId);
        if (activityIds == null) {
            return false;
        }
        for (Long activityId : new HashSet<>(activityIds)) {
            if (activityId == null) {
                continue;
            }
            Boolean detailInserted = opportunitiesMapper.insertActivityDetail(opportunityId, stageId, activityId, userId);
            if (!activeIds.contains(activityId)) {
                String name = opportunitiesMapper.getActivityName(activityId);
                Boolean logInserted = opportunitiesMapper.insertLog(opportunityId, "Name: " + nullToBlank(name), 2, 1, userId);
                saved = saved || (Boolean.TRUE.equals(detailInserted) && Boolean.TRUE.equals(logInserted));
            }
        }
        return saved;
    }

    private Boolean saveTasks(Long opportunityId, Long stageId, List<Long> taskIds, Long userId) {
        Boolean saved = false;
        Set<Long> activeIds = new HashSet<>(opportunitiesMapper.getActiveTaskIds(opportunityId));
        opportunitiesMapper.archiveTaskDetails(opportunityId, userId);
        if (taskIds == null) {
            return false;
        }
        for (Long taskId : new HashSet<>(taskIds)) {
            if (taskId == null) {
                continue;
            }
            Boolean detailInserted = opportunitiesMapper.insertTaskDetail(opportunityId, stageId, taskId, userId);
            if (!activeIds.contains(taskId)) {
                String name = opportunitiesMapper.getTaskName(taskId);
                Boolean logInserted = opportunitiesMapper.insertLog(opportunityId, "Name: " + nullToBlank(name), 3, 1, userId);
                saved = saved || (Boolean.TRUE.equals(detailInserted) && Boolean.TRUE.equals(logInserted));
            }
        }
        return saved;
    }

    private String nullToBlank(String value) {
        return value == null ? "" : value;
    }
}
