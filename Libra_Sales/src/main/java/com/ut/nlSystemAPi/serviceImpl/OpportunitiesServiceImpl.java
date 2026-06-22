package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.OpportunitiesMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Opportunities.Opportunity;
import com.ut.nlSystemAPi.model.entity.Opportunities.OpportunityDetail;
import com.ut.nlSystemAPi.model.filter.OpportunitiesFilter;
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityAddMoreRequest;
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityRequest;
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityUpdateRequest;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityAddMoreDetailResponse;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityAddMoreSaveResponse;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityLogResponse;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityReferenceResponse;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityResponse;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityStageMoveResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.OpportunitiesService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OpportunitiesServiceImpl implements OpportunitiesService {

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

    public ResponseMessage<BaseResult> getList(OpportunitiesFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (filter.getStatus() == null) {
                filter.setStatus(1);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(opportunitiesMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<OpportunityResponse> responses = opportunitiesMapper.getList(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunities/list", null, null, "Opportunities", "Opportunities (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunities/list", line, error.toString(), "Opportunities", "Opportunities (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<OpportunityResponse> responses = opportunitiesMapper.getOne(id, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunities/find/{id}", null, null, "Opportunities", "Opportunities (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunities/find/{id}", line, error.toString(), "Opportunities", "Opportunities (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(OpportunityRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Opportunity opportunity = toOpportunity(request);
            applySource(opportunity, request);
            opportunity.setCreatedBy(userId);
            opportunity.setIsActive(1);
            Boolean result = opportunitiesMapper.insert(opportunity);
            if (result) {
                opportunitiesMapper.updateCode(opportunity.getId(), String.format("CRM-OPP%07d", opportunity.getId()));
                //!insert detail
                insertDetail(opportunity.getId(), request.getPipelineId(), request.getStageId(), request.getProbability(), userId);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/opportunities/add", null, null, "Opportunities", "Opportunities (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunities/add", line, error.toString(), "Opportunities", "Opportunities (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(OpportunityUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Opportunity opportunity = toOpportunity(request);
            opportunity.setId(request.getId());
            applySource(opportunity, request);
            opportunity.setModifiedBy(userId);
            Boolean result = opportunitiesMapper.update(opportunity);
            if (result) {
                //! remove after update
                opportunitiesMapper.closeDetail(request.getId(), request.getPipelineId(), request.getOldStageId());
                //! insert after remove
                insertDetail(request.getId(), request.getPipelineId(), request.getStageId(), request.getProbability(), userId);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/opportunities/update", null, null, "Opportunities", "Opportunities (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunities/update", line, error.toString(), "Opportunities", "Opportunities (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Transactional
    public ResponseMessage<BaseResult> addMore(Long id, OpportunityAddMoreRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (id == null || request == null) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }

            AddMoreResult result;
            String action = normalizeAction(request.getAction());
            if ("NEXT".equals(action)) {
                result = saveAndMoveNext(id, request, userId);
            } else if ("BACK".equals(action)) {
                result = moveBack(id, request, userId);
            } else if ("SKIP".equals(action)) {
                result = skipStage(id, request, userId);
            } else if ("SAVE".equals(action)) {
                result = saveCurrentStage(id, request, userId);
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }

            if (result.getSuccess()) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/opportunities/add-more/{id}", null, null, "Opportunities", "Opportunities (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", Collections.singletonList(new OpportunityAddMoreSaveResponse(result.getActivityTaskSaved())), true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", Collections.singletonList(new OpportunityAddMoreSaveResponse(false)), false));
        } catch (Exception error) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunities/add-more/{id}", line, error.toString(), "Opportunities", "Opportunities (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getListLog(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (id == null) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }

            List<OpportunityLogResponse> responses = opportunitiesMapper.getListLog(id);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunities/list-log/{id}", null, null, "Opportunities", "Opportunities (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunities/list-log/{id}", line, error.toString(), "Opportunities", "Opportunities (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = opportunitiesMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/opportunities/delete/{id}", null, null, "Opportunities", "Opportunities (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunities/delete/{id}", line, error.toString(), "Opportunities", "Opportunities (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private AddMoreResult saveCurrentStage(Long id, OpportunityAddMoreRequest request, Long userId) {
        if (!hasStageWork(request)) {
            return AddMoreResult.fail();
        }
        applyOpportunityReference(id, request, userId);

        OpportunityAddMoreDetailResponse detail = opportunitiesMapper.getActiveDetail(id);
        if (detail == null) {
            return AddMoreResult.fail();
        }

        Boolean activityTaskSaved = saveActivities(id, detail.getStageId(), request.getActivityIds(), userId, true);
        activityTaskSaved = saveTasks(id, detail.getStageId(), request.getTaskIds(), userId, true) || activityTaskSaved;
        opportunitiesMapper.updateDetailDescription(detail.getId(), request.getDescription());

        if (!isBlank(request.getDescription())) {
            String logDescription = "Saved";
            if (!sameText(detail.getDescription(), request.getDescription())) {
                logDescription = "Saved and Change description " + nullToBlank(detail.getDescription()) + " to new description " + request.getDescription();
            }
            opportunitiesMapper.insertLog(id, logDescription, 6, 1, userId);
        }
        opportunitiesMapper.touchOpportunity(id, userId);
        return AddMoreResult.success(activityTaskSaved);
    }

    private AddMoreResult saveAndMoveNext(Long id, OpportunityAddMoreRequest request, Long userId) {
        if (!hasStageWork(request)) {
            return AddMoreResult.fail();
        }
        applyOpportunityReference(id, request, userId);

        OpportunityAddMoreDetailResponse detail = opportunitiesMapper.getActiveDetail(id);
        if (detail == null) {
            return AddMoreResult.fail();
        }

        OpportunityStageMoveResponse nextStage = opportunitiesMapper.getNextStage(detail.getPipelineId(), detail.getStageId());
        if (nextStage == null || nextStage.getStageId() == null) {
            return AddMoreResult.fail();
        }

        Boolean activityTaskSaved = saveActivities(id, detail.getStageId(), request.getActivityIds(), userId, false);
        activityTaskSaved = saveTasks(id, detail.getStageId(), request.getTaskIds(), userId, false) || activityTaskSaved;
        opportunitiesMapper.convertOpportunityDetails(id, request.getDescription(), userId);
        insertDetail(id, detail.getPipelineId(), nextStage.getStageId(), nextStage.getPercent(), userId);

        String logDescription = "Next Stag: " + nullToBlank(detail.getStageName()) + " to Stag: " + nullToBlank(nextStage.getStageName());
        if (!isBlank(request.getDescription())) {
            logDescription += "\n" + request.getDescription();
        }
        opportunitiesMapper.insertLog(id, logDescription, 1, 1, userId);
        opportunitiesMapper.touchOpportunity(id, userId);
        return AddMoreResult.success(activityTaskSaved);
    }

    private AddMoreResult moveBack(Long id, OpportunityAddMoreRequest request, Long userId) {
        applyOpportunityReference(id, request, userId);

        OpportunityAddMoreDetailResponse detail = opportunitiesMapper.getActiveDetail(id);
        if (detail == null) {
            return AddMoreResult.fail();
        }

        OpportunityStageMoveResponse previousStage = opportunitiesMapper.getPreviousStage(detail.getPipelineId(), detail.getStageId());
        if (previousStage == null || previousStage.getStageId() == null) {
            return AddMoreResult.fail();
        }

        opportunitiesMapper.deactivateDetail(detail.getId());
        Long previousDetailId = opportunitiesMapper.getPreviousConvertedDetailId(id, previousStage.getStageId());
        if (previousDetailId != null) {
            opportunitiesMapper.reopenDetail(previousDetailId);
        } else {
            insertDetail(id, detail.getPipelineId(), previousStage.getStageId(), detail.getProbability(), userId);
        }

        opportunitiesMapper.insertLog(id, "Back Stags " + nullToBlank(detail.getStageName()) + " to Stags " + nullToBlank(previousStage.getStageName()), 4, 1, userId);
        opportunitiesMapper.touchOpportunity(id, userId);
        return AddMoreResult.success(false);
    }

    private AddMoreResult skipStage(Long id, OpportunityAddMoreRequest request, Long userId) {
        if (request.getSkipStageId() == null) {
            return AddMoreResult.fail();
        }
        applyOpportunityReference(id, request, userId);

        OpportunityAddMoreDetailResponse detail = opportunitiesMapper.getActiveDetail(id);
        if (detail == null) {
            return AddMoreResult.fail();
        }

        OpportunityStageMoveResponse skipStage = opportunitiesMapper.getStageInPipeline(detail.getPipelineId(), request.getSkipStageId());
        if (skipStage == null || skipStage.getStageId() == null) {
            return AddMoreResult.fail();
        }

        opportunitiesMapper.skipOpportunityDetails(id, userId);
        insertDetail(id, detail.getPipelineId(), skipStage.getStageId(), skipStage.getPercent(), userId);
        opportunitiesMapper.insertLog(id, "Skip Stags " + nullToBlank(detail.getStageName()) + " to Stags " + nullToBlank(skipStage.getStageName()), 5, 1, userId);
        opportunitiesMapper.touchOpportunity(id, userId);
        return AddMoreResult.success(false);
    }

    private void applyOpportunityReference(Long id, OpportunityAddMoreRequest request, Long userId) {
        if (request.getQuotationId() != null) {
            OpportunityReferenceResponse quotation = opportunitiesMapper.getQuotationReference(request.getQuotationId());
            if (quotation != null) {
                opportunitiesMapper.updateOpportunityReference(id, request.getQuotationId(), null, quotation.getCustomerId(), quotation.getCustomerContactId(), quotation.getTotalAmount(), userId);
            }
        }
        if (request.getSalesOrderId() != null) {
            OpportunityReferenceResponse salesOrder = opportunitiesMapper.getSalesOrderReference(request.getSalesOrderId());
            if (salesOrder != null) {
                opportunitiesMapper.updateOpportunityReference(id, null, request.getSalesOrderId(), salesOrder.getCustomerId(), salesOrder.getCustomerContactId(), salesOrder.getTotalAmount(), userId);
            }
        }
    }

    private Boolean saveActivities(Long opportunityId, Long stageId, List<Long> activityIds, Long userId, Boolean logNewOnly) {
        Boolean saved = false;
        Set<Long> activeIds = new HashSet<>(opportunitiesMapper.getActiveActivityIds(opportunityId));
        opportunitiesMapper.archiveActivityDetails(opportunityId, userId);
        if (activityIds == null) {
            return false;
        }
        Set<Long> requestIds = new HashSet<>(activityIds);
        for (Long activityId : requestIds) {
            if (activityId == null) {
                continue;
            }
            Boolean detailInserted = opportunitiesMapper.insertActivityDetail(opportunityId, stageId, activityId, userId);
            if (!logNewOnly || !activeIds.contains(activityId)) {
                String name = opportunitiesMapper.getActivityName(activityId);
                Boolean logInserted = opportunitiesMapper.insertLog(opportunityId, "Name: " + nullToBlank(name), 2, 1, userId);
                saved = saved || (Boolean.TRUE.equals(detailInserted) && Boolean.TRUE.equals(logInserted));
            }
        }
        return saved;
    }

    private Boolean saveTasks(Long opportunityId, Long stageId, List<Long> taskIds, Long userId, Boolean logNewOnly) {
        Boolean saved = false;
        Set<Long> activeIds = new HashSet<>(opportunitiesMapper.getActiveTaskIds(opportunityId));
        opportunitiesMapper.archiveTaskDetails(opportunityId, userId);
        if (taskIds == null) {
            return false;
        }
        Set<Long> requestIds = new HashSet<>(taskIds);
        for (Long taskId : requestIds) {
            if (taskId == null) {
                continue;
            }
            Boolean detailInserted = opportunitiesMapper.insertTaskDetail(opportunityId, stageId, taskId, userId);
            if (!logNewOnly || !activeIds.contains(taskId)) {
                String name = opportunitiesMapper.getTaskName(taskId);
                Boolean logInserted = opportunitiesMapper.insertLog(opportunityId, "Name: " + nullToBlank(name), 3, 1, userId);
                saved = saved || (Boolean.TRUE.equals(detailInserted) && Boolean.TRUE.equals(logInserted));
            }
        }
        return saved;
    }

    //Main model request
    private Opportunity toOpportunity(OpportunityRequest request) {
        Opportunity opportunity = new Opportunity();
        opportunity.setName(request.getName());
        opportunity.setExpectedDate(request.getExpectedDate());
        opportunity.setSourceId(request.getSourceId());
        opportunity.setAmount(request.getAmount());
        opportunity.setResponsibilityId(request.getResponsibilityId());
        opportunity.setDivisionId(request.getDivisionId());
        opportunity.setCustomerContactId(request.getContactId());
        opportunity.setQuotationId(request.getQuotationId());
        opportunity.setSalesOrderId(request.getSalesOrderId());
        opportunity.setLeadId(request.getLeadId());
        opportunity.setDescription(request.getDescription());
        return opportunity;
    }

    private void applySource(Opportunity opportunity, OpportunityRequest request) {
        Long sourceType = opportunitiesMapper.getSourceType(request.getSourceId());
        if (sourceType == null || sourceType == 1 || sourceType == 2) {
            opportunity.setCustomerId(request.getOrganizationId());
        } else if (sourceType == 3) {
            opportunity.setEmployeeId(request.getEmployeeId());
        } else if (sourceType == 4) {
            opportunity.setVendorId(request.getVendorId());
        }
    }

    private void insertDetail(Long opportunityId, Long pipelineId, Long stageId, Double probability, Long userId) {
        OpportunityDetail detail = new OpportunityDetail();
        detail.setOpportunityId(opportunityId);
        detail.setPipelineId(pipelineId);
        detail.setStageId(stageId);
        detail.setProbability(probability);
        detail.setCreatedBy(userId);
        detail.setIsActive(1);
        opportunitiesMapper.insertDetail(detail);
    }

    private Boolean hasStageWork(OpportunityAddMoreRequest request) {
        return !isBlank(request.getDescription())
                || (request.getActivityIds() != null && !request.getActivityIds().isEmpty())
                || (request.getTaskIds() != null && !request.getTaskIds().isEmpty());
    }

    private String normalizeAction(String action) {
        if (isBlank(action)) {
            return "SAVE";
        }
        return action.trim().toUpperCase();
    }

    private Boolean sameText(String first, String second) {
        return nullToBlank(first).equals(nullToBlank(second));
    }

    private Boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String nullToBlank(String value) {
        return value == null ? "" : value;
    }

    private static class AddMoreResult {
        private final Boolean success;
        private final Boolean activityTaskSaved;

        private AddMoreResult(Boolean success, Boolean activityTaskSaved) {
            this.success = success;
            this.activityTaskSaved = activityTaskSaved;
        }

        private static AddMoreResult success(Boolean activityTaskSaved) {
            return new AddMoreResult(true, activityTaskSaved);
        }

        private static AddMoreResult fail() {
            return new AddMoreResult(false, false);
        }

        private Boolean getSuccess() {
            return success;
        }

        private Boolean getActivityTaskSaved() {
            return activityTaskSaved;
        }
    }
}
