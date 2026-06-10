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
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportTicketRequest;
import com.ut.nlSystemAPi.model.request.SupportTicket.SupportTicketUpdateRequest;
import com.ut.nlSystemAPi.model.response.SupportTicket.SupportTicketResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.SupportTicketService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

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
}
