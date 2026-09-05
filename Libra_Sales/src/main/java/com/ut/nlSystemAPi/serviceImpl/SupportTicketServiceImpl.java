package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.SupportTicketMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.SupportTicket.SupportTicket;
import com.ut.nlSystemAPi.model.entity.SupportTicket.SupportTicketDetail;
import com.ut.nlSystemAPi.model.filter.SupportTicketFilter;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportTicketAddMoreRequest;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportTicketRequest;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportTicketUpdateRequest;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTicketAddMoreDetailResponse;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTicketAddMoreResponse;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTicketAddMoreSaveResponse;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTicketAddMoreStageResponse;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTicketResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.SupportTicketService;
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
public class SupportTicketServiceImpl implements SupportTicketService {

    @Autowired
    private SupportTicketMapper supportTicketMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    public ResponseMessage<BaseResult> getList(SupportTicketFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (filter.getStatus() == null) {
                filter.setStatus(1);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(supportTicketMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SupportTicketResponse> responses = supportTicketMapper.getList(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket/list", null, null, "Support Ticket", "Support Ticket (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket/list", line, error.toString(), "Support Ticket", "Support Ticket (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<SupportTicketResponse> responses = supportTicketMapper.getOne(id, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket/find/{id}", null, null, "Support Ticket", "Support Ticket (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket/find/{id}", line, error.toString(), "Support Ticket", "Support Ticket (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(SupportTicketRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            SupportTicket support = toSupport(request);
            support.setCreatedBy(userId);
            support.setIsActive(1);
            Boolean result = supportTicketMapper.insert(support);
            if (result) {
                supportTicketMapper.updateCode(support.getId(), String.format("CRM-SUP%07d", support.getId()));
                insertDetail(support.getId(), request.getPipelineId(), request.getStageId(), userId);
                if (request.getEmployeeGroupId() != null) {
                    supportTicketMapper.insertEmployeeGroup(support.getId(), request.getEmployeeGroupId());
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/support-ticket/add", null, null, "Support Ticket", "Support Ticket (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket/add", line, error.toString(), "Support Ticket", "Support Ticket (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(SupportTicketUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            SupportTicket support = toSupport(request);
            support.setId(request.getId());
            support.setModifiedBy(userId);
            Boolean result = supportTicketMapper.update(support);
            if (result) {
                supportTicketMapper.closeDetail(request.getId(), request.getPipelineId(), request.getOldStageId());
                insertDetail(request.getId(), request.getPipelineId(), request.getStageId(), userId);
                supportTicketMapper.deleteEmployeeGroup(request.getId());
                if (request.getEmployeeGroupId() != null) {
                    supportTicketMapper.insertEmployeeGroup(request.getId(), request.getEmployeeGroupId());
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/support-ticket/update", null, null, "Support Ticket", "Support Ticket (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket/update", line, error.toString(), "Support Ticket", "Support Ticket (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getAddMore(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (id == null) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }

            SupportTicketAddMoreDetailResponse detail = supportTicketMapper.getAddMoreDetail(id);
            if (detail == null) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }

            SupportTicketAddMoreResponse response = new SupportTicketAddMoreResponse();
            response.setDetail(detail);
            response.setCurrentStage(supportTicketMapper.getStageInPipeline(detail.getPipelineId(), detail.getStageId()));
            response.setPreviousStage(supportTicketMapper.getStageByOrdering(detail.getPipelineId(), detail.getOrdering() - 1));
            response.setNextStage(supportTicketMapper.getStageByOrdering(detail.getPipelineId(), detail.getOrdering() + 1));
            response.setSkipStages(supportTicketMapper.getSkipStages(detail.getPipelineId(), detail.getOrdering(), userId));
            response.setActivities(supportTicketMapper.getAddMoreActivities(id, detail.getPipelineId(), detail.getStageId(), userId));
            response.setTasks(supportTicketMapper.getAddMoreTasks(id, detail.getPipelineId(), detail.getStageId()));
            response.setLogs(supportTicketMapper.getListLog(id));

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket/list-add-more/{id}", null, null, "Support Ticket", "Support Ticket (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", Collections.singletonList(response), true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket/list-add-more/{id}", line, error.toString(), "Support Ticket", "Support Ticket (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Transactional
    public ResponseMessage<BaseResult> addMore(Long id, SupportTicketAddMoreRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket (Edit)") == 0) {
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
                result = moveBack(id, userId);
            } else if ("SKIP".equals(action)) {
                result = skipStage(id, request, userId);
            } else if ("SAVE".equals(action)) {
                result = saveCurrentStage(id, request, userId);
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }

            if (result.getSuccess()) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/support-ticket/add-more/{id}", null, null, "Support Ticket", "Support Ticket (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", Collections.singletonList(new SupportTicketAddMoreSaveResponse(result.getActivityTaskSaved())), true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", Collections.singletonList(new SupportTicketAddMoreSaveResponse(false)), false));
        } catch (Exception error) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket/add-more/{id}", line, error.toString(), "Support Ticket", "Support Ticket (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = supportTicketMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/support-ticket/delete/{id}", null, null, "Support Ticket", "Support Ticket (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket/delete/{id}", line, error.toString(), "Support Ticket", "Support Ticket (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private AddMoreResult saveCurrentStage(Long id, SupportTicketAddMoreRequest request, Long userId) {
        if (!hasStageWork(request)) {
            return AddMoreResult.fail();
        }
        SupportTicketAddMoreDetailResponse detail = supportTicketMapper.getAddMoreDetail(id);
        if (detail == null) {
            return AddMoreResult.fail();
        }

        Boolean activityTaskSaved = saveActivities(id, detail.getStageId(), request.getActivityIds(), userId, true);
        activityTaskSaved = saveTasks(id, detail.getStageId(), request.getTaskIds(), userId, true) || activityTaskSaved;
        supportTicketMapper.updateDetailDescription(detail.getId(), request.getDescription());

        if (!isBlank(request.getDescription())) {
            String logDescription = "Saved";
            if (!sameText(detail.getDescription(), request.getDescription())) {
                logDescription = "Saved and Change description " + nullToBlank(detail.getDescription()) + " to new description " + request.getDescription();
            }
            supportTicketMapper.insertLog(id, logDescription, 6, 1, userId);
        }
        supportTicketMapper.touchSupport(id, userId);
        return AddMoreResult.success(activityTaskSaved);
    }

    private AddMoreResult saveAndMoveNext(Long id, SupportTicketAddMoreRequest request, Long userId) {
        if (!hasStageWork(request)) {
            return AddMoreResult.fail();
        }
        SupportTicketAddMoreDetailResponse detail = supportTicketMapper.getAddMoreDetail(id);
        if (detail == null) {
            return AddMoreResult.fail();
        }

        SupportTicketAddMoreStageResponse nextStage = supportTicketMapper.getNextStage(detail.getPipelineId(), detail.getStageId());
        if (nextStage == null || nextStage.getStageId() == null) {
            return AddMoreResult.fail();
        }

        Boolean activityTaskSaved = saveActivities(id, detail.getStageId(), request.getActivityIds(), userId, false);
        activityTaskSaved = saveTasks(id, detail.getStageId(), request.getTaskIds(), userId, false) || activityTaskSaved;
        supportTicketMapper.convertSupportDetails(id, request.getDescription(), userId);
        insertDetail(id, detail.getPipelineId(), nextStage.getStageId(), nextStage.getPercent(), userId);

        String logDescription = "Next Stag: " + nullToBlank(detail.getStageName()) + " to Stag: " + nullToBlank(nextStage.getStageName());
        if (!isBlank(request.getDescription())) {
            logDescription += "\n" + request.getDescription();
        }
        supportTicketMapper.insertLog(id, logDescription, 1, 1, userId);
        supportTicketMapper.touchSupport(id, userId);
        return AddMoreResult.success(activityTaskSaved);
    }

    private AddMoreResult moveBack(Long id, Long userId) {
        SupportTicketAddMoreDetailResponse detail = supportTicketMapper.getAddMoreDetail(id);
        if (detail == null) {
            return AddMoreResult.fail();
        }

        SupportTicketAddMoreStageResponse previousStage = supportTicketMapper.getPreviousStage(detail.getPipelineId(), detail.getStageId());
        if (previousStage == null || previousStage.getStageId() == null) {
            return AddMoreResult.fail();
        }

        supportTicketMapper.deactivateDetail(detail.getId());
        Long previousDetailId = supportTicketMapper.getPreviousConvertedDetailId(id, previousStage.getStageId());
        if (previousDetailId != null) {
            supportTicketMapper.reopenDetail(previousDetailId);
        } else {
            insertDetail(id, detail.getPipelineId(), previousStage.getStageId(), previousStage.getPercent(), userId);
        }

        supportTicketMapper.insertLog(id, "Back Stags " + nullToBlank(detail.getStageName()) + " to Stags " + nullToBlank(previousStage.getStageName()), 4, 1, userId);
        supportTicketMapper.touchSupport(id, userId);
        return AddMoreResult.success(false);
    }

    private AddMoreResult skipStage(Long id, SupportTicketAddMoreRequest request, Long userId) {
        if (request.getSkipStageId() == null) {
            return AddMoreResult.fail();
        }
        SupportTicketAddMoreDetailResponse detail = supportTicketMapper.getAddMoreDetail(id);
        if (detail == null) {
            return AddMoreResult.fail();
        }

        SupportTicketAddMoreStageResponse skipStage = supportTicketMapper.getStageInPipeline(detail.getPipelineId(), request.getSkipStageId());
        if (skipStage == null || skipStage.getStageId() == null) {
            return AddMoreResult.fail();
        }

        supportTicketMapper.skipSupportDetails(id, userId);
        insertDetail(id, detail.getPipelineId(), skipStage.getStageId(), skipStage.getPercent(), userId);
        supportTicketMapper.insertLog(id, "Skip Stags " + nullToBlank(detail.getStageName()) + " to Stags " + nullToBlank(skipStage.getStageName()), 5, 1, userId);
        supportTicketMapper.touchSupport(id, userId);
        return AddMoreResult.success(false);
    }

    private Boolean saveActivities(Long supportId, Long stageId, List<Long> activityIds, Long userId, Boolean logNewOnly) {
        Boolean saved = false;
        Set<Long> activeIds = new HashSet<>(supportTicketMapper.getActiveActivityIds(supportId));
        supportTicketMapper.archiveActivityDetails(supportId);
        if (activityIds == null) {
            return false;
        }
        Set<Long> requestIds = new HashSet<>(activityIds);
        for (Long activityId : requestIds) {
            if (activityId == null) {
                continue;
            }
            Boolean detailInserted = supportTicketMapper.insertActivityDetail(supportId, stageId, activityId, userId);
            if (!logNewOnly || !activeIds.contains(activityId)) {
                String name = supportTicketMapper.getActivityName(activityId);
                Boolean logInserted = supportTicketMapper.insertLog(supportId, "Name: " + nullToBlank(name), 2, 1, userId);
                saved = saved || (Boolean.TRUE.equals(detailInserted) && Boolean.TRUE.equals(logInserted));
            }
        }
        return saved;
    }

    private Boolean saveTasks(Long supportId, Long stageId, List<Long> taskIds, Long userId, Boolean logNewOnly) {
        Boolean saved = false;
        Set<Long> activeIds = new HashSet<>(supportTicketMapper.getActiveTaskIds(supportId));
        supportTicketMapper.archiveTaskDetails(supportId);
        if (taskIds == null) {
            return false;
        }
        Set<Long> requestIds = new HashSet<>(taskIds);
        for (Long taskId : requestIds) {
            if (taskId == null) {
                continue;
            }
            Boolean detailInserted = supportTicketMapper.insertTaskDetail(supportId, stageId, taskId, userId);
            if (!logNewOnly || !activeIds.contains(taskId)) {
                String name = supportTicketMapper.getTaskName(taskId);
                Boolean logInserted = supportTicketMapper.insertLog(supportId, "Name: " + nullToBlank(name), 3, 1, userId);
                saved = saved || (Boolean.TRUE.equals(detailInserted) && Boolean.TRUE.equals(logInserted));
            }
        }
        return saved;
    }

    private SupportTicket toSupport(SupportTicketRequest request) {
        SupportTicket support = new SupportTicket();
        support.setCaseTitle(request.getCaseTitle());
        support.setSummary(request.getSummary());
        support.setTypeId(request.getTypeId());
        support.setPriorityId(request.getPriorityId());
        support.setCustomerId(request.getCustomerId());
        support.setCustomerContactId(request.getCustomerContactId());
        support.setAssignedTo(request.getAssignedTo());
        return support;
    }

    private void insertDetail(Long supportId, Long pipelineId, Long stageId, Long userId) {
        SupportTicketDetail detail = new SupportTicketDetail();
        detail.setSupportId(supportId);
        detail.setPipelineId(pipelineId);
        detail.setStageId(stageId);
        Double percent = supportTicketMapper.getStagePercent(stageId);
        detail.setProbability(percent == null ? 0D : percent);
        detail.setCreatedBy(userId);
        detail.setIsActive(1);
        supportTicketMapper.insertDetail(detail);
    }

    private void insertDetail(Long supportId, Long pipelineId, Long stageId, Double probability, Long userId) {
        SupportTicketDetail detail = new SupportTicketDetail();
        detail.setSupportId(supportId);
        detail.setPipelineId(pipelineId);
        detail.setStageId(stageId);
        detail.setProbability(probability == null ? 0D : probability);
        detail.setCreatedBy(userId);
        detail.setIsActive(1);
        supportTicketMapper.insertDetail(detail);
    }

    private Boolean hasStageWork(SupportTicketAddMoreRequest request) {
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
