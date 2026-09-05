package com.ut.nlSystemAPi.serviceImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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

    private final ObjectMapper objectMapper = new ObjectMapper();

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
            response.setCheckedData(readCheckedData(opportunitiesMapper.getOpportunityChecklistData(id, detail.getStageId())));

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

            Map<String, Object> checkedData = normalizeCheckedData(request);
            String checkedDataJson = objectMapper.writeValueAsString(checkedData);
            Integer updatedRows = opportunitiesMapper.updateOpportunityChecklist(id, detail.getStageId(), checkedDataJson, userId);
            if (updatedRows == null || updatedRows == 0) {
                opportunitiesMapper.insertOpportunityChecklist(id, detail.getStageId(), checkedDataJson, userId);
            }
            opportunitiesMapper.updateDetailDescription(detail.getId(), request.getDescription());
            opportunitiesMapper.touchOpportunity(id, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-checklist/save/{id}", null, null, "Opportunity Checklist", "Opportunities (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } catch (Exception error) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-checklist/save/{id}", line, error.toString(), "Opportunity Checklist", "Opportunities (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private Map<String, Object> normalizeCheckedData(ChecklistSaveRequest request) {
        if (request.getCheckedData() != null) {
            return request.getCheckedData();
        }
        Map<String, Object> checkedData = new HashMap<>();
        checkedData.put("checkedActivities", request.getActivityIds() == null ? Collections.emptyList() : request.getActivityIds());
        checkedData.put("checkedTasks", request.getTaskIds() == null ? Collections.emptyList() : request.getTaskIds());
        return checkedData;
    }

    private Map<String, Object> readCheckedData(String checkedData) throws java.io.IOException {
        if (checkedData == null || checkedData.trim().isEmpty()) {
            return defaultCheckedData();
        }
        return objectMapper.readValue(checkedData, new TypeReference<Map<String, Object>>() {});
    }

    private Map<String, Object> defaultCheckedData() {
        Map<String, Object> checkedData = new HashMap<>();
        checkedData.put("checkedActivities", Collections.emptyMap());
        checkedData.put("checkedTasks", Collections.emptyMap());
        return checkedData;
    }
}
