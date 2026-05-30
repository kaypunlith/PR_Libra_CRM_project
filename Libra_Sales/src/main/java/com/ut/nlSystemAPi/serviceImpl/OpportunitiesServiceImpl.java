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
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityRequest;
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityUpdateRequest;
import com.ut.nlSystemAPi.model.response.Opportunities.OpportunityResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.OpportunitiesService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

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
}
