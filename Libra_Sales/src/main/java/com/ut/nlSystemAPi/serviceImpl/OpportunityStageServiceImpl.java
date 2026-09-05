package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.OpportunityStageMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Opportunities.OpportunityStage;
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityStageRequest;
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityStageUpdateRequest;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityStageResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.OpportunityStageService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class OpportunityStageServiceImpl implements OpportunityStageService {

    @Autowired
    private OpportunityStageMapper opportunityStageMapper;

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
            if (permissionMapper.checkPermission(userId, "Opportunities Stage (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(opportunityStageMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<OpportunityStageResponse> responses = opportunityStageMapper.getList(filter);
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage/list", null, null, "Opportunities Stage", "Opportunities Stage (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage/list", line, error.toString(), "Opportunities Stage", "Opportunities Stage (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities Stage (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<OpportunityStageResponse> responses = opportunityStageMapper.getOne(id);
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage/find/{id}", null, null, "Opportunities Stage", "Opportunities Stage (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage/find/{id}", line, error.toString(), "Opportunities Stage", "Opportunities Stage (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(OpportunityStageRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities Stage (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (opportunityStageMapper.checkDuplicate(request.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            OpportunityStage stage = new OpportunityStage();
            stage.setName(request.getName());
            stage.setApplyWith(request.getApplyWith());
            stage.setCreatedBy(userId);
            stage.setConvertLead(request.getConvertLead());
            stage.setIsUpload(request.getIsUpload());
            stage.setIsPrint(request.getIsPrint());
            stage.setIsActive(1);
            Boolean result = opportunityStageMapper.insert(stage);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/opportunity-stage/add", null, null, "Opportunities Stage", "Opportunities Stage (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage/add", line, error.toString(), "Opportunities Stage", "Opportunities Stage (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(OpportunityStageUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities Stage (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (opportunityStageMapper.checkDuplicate(request.getName(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            OpportunityStage stage = new OpportunityStage();
            stage.setId(request.getId());
            stage.setName(request.getName());
            stage.setApplyWith(request.getApplyWith());
            stage.setModifiedBy(userId);
            stage.setConvertLead(request.getConvertLead());
            stage.setIsUpload(request.getIsUpload());
            stage.setIsPrint(request.getIsPrint());
            Boolean result = opportunityStageMapper.update(stage);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/opportunity-stage/update", null, null, "Opportunities Stage", "Opportunities Stage (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage/update", line, error.toString(), "Opportunities Stage", "Opportunities Stage (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities Stage (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Boolean result = opportunityStageMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/opportunity-stage/delete/{id}", null, null, "Opportunities Stage", "Opportunities Stage (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage/delete/{id}", line, error.toString(), "Opportunities Stage", "Opportunities Stage (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
