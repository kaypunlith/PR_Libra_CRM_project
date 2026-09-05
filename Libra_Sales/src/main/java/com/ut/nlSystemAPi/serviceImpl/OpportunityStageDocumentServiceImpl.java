package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.OpportunityStageDocumentMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Opportunities.OpportunityStageDocument;
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityStageDocumentRequest;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityStageDocumentResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.OpportunityStageDocumentService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class OpportunityStageDocumentServiceImpl implements OpportunityStageDocumentService {

    @Autowired
    private OpportunityStageDocumentMapper opportunityStageDocumentMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(Long crmOpportunityId, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities Stage (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<OpportunityStageDocumentResponse> responses = opportunityStageDocumentMapper.getList(crmOpportunityId);
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage-document/list", null, null, "Opportunities Stage Document", "Opportunities Stage (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage-document/list", line, error.toString(), "Opportunities Stage Document", "Opportunities Stage (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(OpportunityStageDocumentRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities Stage (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            OpportunityStageDocument document = new OpportunityStageDocument();
            document.setCustomerId(request.getCustomerId());
            document.setCrmOpportunityId(request.getCrmOpportunityId());
            document.setImageName(request.getImageName());
            document.setImageUrl(request.getImageUrl());
            document.setStartDate(request.getStartDate());
            document.setEndDate(request.getEndDate());
            document.setAddress(request.getAddress());
            document.setCreatedBy(userId);

            Boolean result = opportunityStageDocumentMapper.insert(document);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/opportunity-stage-document/add", null, null, "Opportunities Stage Document", "Opportunities Stage (Edit)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage-document/add", line, error.toString(), "Opportunities Stage Document", "Opportunities Stage (Edit)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Opportunities Stage (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = opportunityStageDocumentMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/opportunity-stage-document/delete/{id}", null, null, "Opportunities Stage Document", "Opportunities Stage (Edit)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage-document/delete/{id}", line, error.toString(), "Opportunities Stage Document", "Opportunities Stage (Edit)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
