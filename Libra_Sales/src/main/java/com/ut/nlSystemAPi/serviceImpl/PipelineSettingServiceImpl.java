package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.PipelineSettingMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Opportunities.PipelineSetting;
import com.ut.nlSystemAPi.model.request.Opportunities.PipelineSettingRequest;
import com.ut.nlSystemAPi.model.request.Opportunities.PipelineSettingStageRequest;
import com.ut.nlSystemAPi.model.request.Opportunities.PipelineSettingUpdateRequest;
import com.ut.nlSystemAPi.model.response.Opportunities.PipelineSettingResponse;
import com.ut.nlSystemAPi.model.response.Opportunities.PipelineSettingStageResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.PipelineSettingService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class PipelineSettingServiceImpl implements PipelineSettingService {

    @Autowired
    private PipelineSettingMapper pipelineSettingMapper;

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
            if (permissionMapper.checkPermission(userId, "Pipeline Setting (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(pipelineSettingMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            System.out.println(" userid sale 1 "+userId);



            List<PipelineSettingResponse> responses = pipelineSettingMapper.getList(filter, userId);
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pipeline-setting/list", null, null, "Pipeline Setting", "Pipeline Setting (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pipeline-setting/list", line, error.toString(), "Pipeline Setting", "Pipeline Setting (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Pipeline Setting (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<PipelineSettingResponse> responses = pipelineSettingMapper.getOne(id);
            for (PipelineSettingResponse response : responses) {
                List<PipelineSettingStageResponse> stages = pipelineSettingMapper.getStages(response.getId());
                for (PipelineSettingStageResponse stage : stages) {
                    stage.setActivityIds(pipelineSettingMapper.getStageActivityIds(response.getId(), stage.getStageId()));
                }
                response.setStages(stages);
            }
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pipeline-setting/find/{id}", null, null, "Pipeline Setting", "Pipeline Setting (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pipeline-setting/find/{id}", line, error.toString(), "Pipeline Setting", "Pipeline Setting (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(PipelineSettingRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Pipeline Setting (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            PipelineSetting pipeline = new PipelineSetting();
            pipeline.setDescription(request.getDescription());
            pipeline.setCreatedBy(userId);
            pipeline.setIsActive(1);
            Boolean result = pipelineSettingMapper.insert(pipeline);
            if (result) {
                System.out.println("pipline Id "+pipeline.getId());
                System.out.println("request "+request);
                saveDetails(pipeline.getId(), request, false);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/pipeline-setting/add", null, null, "Pipeline Setting", "Pipeline Setting (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pipeline-setting/add", line, error.toString(), "Pipeline Setting", "Pipeline Setting (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(PipelineSettingUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Pipeline Setting (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            PipelineSetting pipeline = new PipelineSetting();
            pipeline.setId(request.getId());
            pipeline.setDescription(request.getDescription());
            pipeline.setModifiedBy(userId);
            Boolean result = pipelineSettingMapper.update(pipeline);
            if (result) {
                pipelineSettingMapper.deleteEmployeeGroup(request.getId());
                pipelineSettingMapper.deleteStage(request.getId());
                saveDetails(request.getId(), request, true);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/pipeline-setting/update", null, null, "Pipeline Setting", "Pipeline Setting (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pipeline-setting/update", line, error.toString(), "Pipeline Setting", "Pipeline Setting (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Pipeline Setting (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Boolean result = pipelineSettingMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/pipeline-setting/delete/{id}", null, null, "Pipeline Setting", "Pipeline Setting (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/pipeline-setting/delete/{id}", line, error.toString(), "Pipeline Setting", "Pipeline Setting (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private void saveDetails(Long pipelineId, PipelineSettingRequest request, Boolean replaceStageActivities) {
        if (request.getEmployeeGroupIds() != null) {
            for (Long employeeGroupId : request.getEmployeeGroupIds()) {
                pipelineSettingMapper.insertEmployeeGroup(pipelineId, employeeGroupId);
            }
        }
        if (request.getStages() != null) {
            for (PipelineSettingStageRequest stage : request.getStages()) {
                pipelineSettingMapper.insertStage(pipelineId, stage.getStageId(), stage.getPercent(), stage.getOrdering(), stage.getSkippable());
                if (replaceStageActivities) {
                    pipelineSettingMapper.deleteStageActivity(pipelineId, stage.getStageId());
                }
                if (stage.getActivityIds() != null) {
                    for (Long activityId : stage.getActivityIds()) {
                        pipelineSettingMapper.insertStageActivity(pipelineId, stage.getStageId(), activityId);
                        if (request.getEmployeeGroupIds() != null) {
                            for (Long employeeGroupId : request.getEmployeeGroupIds()) {
                                pipelineSettingMapper.insertActivityEmployeeGroup(activityId, employeeGroupId);
                            }
                        }
                    }
                }
            }
        }
    }
}
