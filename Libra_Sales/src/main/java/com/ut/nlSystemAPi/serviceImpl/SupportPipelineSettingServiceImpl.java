package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.SupportPipelineSettingMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.SupportTicket.SupportPipelineSetting;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportPipelineSettingRequest;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportPipelineSettingStageRequest;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportPipelineSettingUpdateRequest;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportPipelineSettingResponse;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportPipelineSettingStageResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.SupportPipelineSettingService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class SupportPipelineSettingServiceImpl implements SupportPipelineSettingService {

    @Autowired
    private SupportPipelineSettingMapper supportPipelineSettingMapper;

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
            if (permissionMapper.checkPermission(userId, "Support Ticket Pipeline Setting (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(supportPipelineSettingMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SupportPipelineSettingResponse> responses = supportPipelineSettingMapper.getList(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-pipeline-setting/list", null, null, "Support Ticket Pipeline Setting", "Support Ticket Pipeline Setting (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-pipeline-setting/list", line, error.toString(), "Support Ticket Pipeline Setting", "Support Ticket Pipeline Setting (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket Pipeline Setting (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<SupportPipelineSettingResponse> responses = supportPipelineSettingMapper.getOne(id);
            for (SupportPipelineSettingResponse response : responses) {
                List<SupportPipelineSettingStageResponse> stages = supportPipelineSettingMapper.getStages(response.getId());
                for (SupportPipelineSettingStageResponse stage : stages) {
                    stage.setActivityIds(supportPipelineSettingMapper.getStageActivityIds(response.getId(), stage.getStageId()));
                }
                response.setStages(stages);
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-pipeline-setting/find/{id}", null, null, "Support Ticket Pipeline Setting", "Support Ticket Pipeline Setting (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-pipeline-setting/find/{id}", line, error.toString(), "Support Ticket Pipeline Setting", "Support Ticket Pipeline Setting (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(SupportPipelineSettingRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket Pipeline Setting (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            SupportPipelineSetting pipeline = toPipeline(request);
            pipeline.setCreatedBy(userId);
            pipeline.setIsActive(1);
            Boolean result = supportPipelineSettingMapper.insert(pipeline);
            if (result) {
                saveDetails(pipeline.getId(), request, false);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/support-pipeline-setting/add", null, null, "Support Ticket Pipeline Setting", "Support Ticket Pipeline Setting (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-pipeline-setting/add", line, error.toString(), "Support Ticket Pipeline Setting", "Support Ticket Pipeline Setting (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(SupportPipelineSettingUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket Pipeline Setting (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            SupportPipelineSetting pipeline = toPipeline(request);
            pipeline.setId(request.getId());
            pipeline.setModifiedBy(userId);
            Boolean result = supportPipelineSettingMapper.update(pipeline);
            if (result) {
                supportPipelineSettingMapper.deleteEmployeeGroup(request.getId());
                supportPipelineSettingMapper.deleteStage(request.getId());
                saveDetails(request.getId(), request, true);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/support-pipeline-setting/update", null, null, "Support Ticket Pipeline Setting", "Support Ticket Pipeline Setting (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-pipeline-setting/update", line, error.toString(), "Support Ticket Pipeline Setting", "Support Ticket Pipeline Setting (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Support Ticket Pipeline Setting (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = supportPipelineSettingMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/support-pipeline-setting/delete/{id}", null, null, "Support Ticket Pipeline Setting", "Support Ticket Pipeline Setting (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-pipeline-setting/delete/{id}", line, error.toString(), "Support Ticket Pipeline Setting", "Support Ticket Pipeline Setting (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private SupportPipelineSetting toPipeline(SupportPipelineSettingRequest request) {
        SupportPipelineSetting pipeline = new SupportPipelineSetting();
        pipeline.setTypeId(request.getTypeId());
        pipeline.setDescription(request.getDescription());
        pipeline.setResolutionNumber(request.getResolutionNumber());
        pipeline.setBaseOnId(request.getBaseOnId());
        return pipeline;
    }

    private void saveDetails(Long pipelineId, SupportPipelineSettingRequest request, Boolean replaceStageActivities) {
        if (request.getEmployeeGroupIds() != null) {
            for (Long employeeGroupId : request.getEmployeeGroupIds()) {
                supportPipelineSettingMapper.insertEmployeeGroup(pipelineId, employeeGroupId);
            }
        }

        if (request.getStages() != null) {
            for (SupportPipelineSettingStageRequest stage : request.getStages()) {
                supportPipelineSettingMapper.insertStage(pipelineId, stage.getStageId(), stage.getPercent(), stage.getOrdering(), stage.getSkippable());
                if (replaceStageActivities) {
                    supportPipelineSettingMapper.deleteStageActivity(pipelineId, stage.getStageId());
                }
                if (stage.getActivityIds() != null) {
                    for (Long activityId : stage.getActivityIds()) {
                        supportPipelineSettingMapper.insertStageActivity(pipelineId, stage.getStageId(), activityId);
                        if (request.getEmployeeGroupIds() != null) {
                            for (Long employeeGroupId : request.getEmployeeGroupIds()) {
                                supportPipelineSettingMapper.insertActivityEmployeeGroup(activityId, employeeGroupId);
                            }
                        }
                    }
                }
            }
        }
    }
}
