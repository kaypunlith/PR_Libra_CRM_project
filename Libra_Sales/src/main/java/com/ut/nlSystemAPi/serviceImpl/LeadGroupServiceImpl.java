package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.LeadGroupMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.LeadGroup.LeadGroup;
import com.ut.nlSystemAPi.model.request.LeadGroup.LeadGroupRequest;
import com.ut.nlSystemAPi.model.request.LeadGroup.LeadGroupUpdateRequest;
import com.ut.nlSystemAPi.model.response.LeadGroup.LeadGroupResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.LeadGroupService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class LeadGroupServiceImpl implements LeadGroupService {

    @Autowired
    private LeadGroupMapper leadGroupMapper;

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
            if (permissionMapper.checkPermission(userId, "Lead Group (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(leadGroupMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<LeadGroupResponse> responses = leadGroupMapper.getList(filter, userId);
            for (LeadGroupResponse response : responses) {
                response.setCompanies(leadGroupMapper.getCompany(response.getId()));
                response.setEmployeeGroups(leadGroupMapper.getEmployeeGroup(response.getId()));
            }


            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-group/list", null, null, "Lead Group", "Lead Group (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-group/list", line, error.toString(), "Lead Group", "Lead Group (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead Group (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<LeadGroupResponse> responses = leadGroupMapper.getOne(id, userId);
            for (LeadGroupResponse response : responses) {
                response.setCompanies(leadGroupMapper.getCompany(response.getId()));
                response.setEmployeeGroups(leadGroupMapper.getEmployeeGroup(response.getId()));
                response.setLeads(leadGroupMapper.getLead(response.getId()));
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-group/find/{id}", null, null, "Lead Group", "Lead Group (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-group/find/{id}", line, error.toString(), "Lead Group", "Lead Group (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(LeadGroupRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead Group (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (leadGroupMapper.checkDuplicate(request.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            LeadGroup leadGroup = new LeadGroup();
            leadGroup.setName(request.getName());
            leadGroup.setCreatedBy(userId);
            leadGroup.setIsActive(3);

            Boolean result = leadGroupMapper.insert(leadGroup);
            if (result) {
                //! insert detail
                saveDetails(leadGroup.getId(), request);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/lead-group/add", null, null, "Lead Group", "Lead Group (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-group/add", line, error.toString(), "Lead Group", "Lead Group (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(LeadGroupUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead Group (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (leadGroupMapper.checkDuplicate(request.getName(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            LeadGroup leadGroup = new LeadGroup();
            leadGroup.setId(request.getId());
            leadGroup.setName(request.getName());
            leadGroup.setModifiedBy(userId);
            Boolean result = leadGroupMapper.update(leadGroup);
            if (result) {
                leadGroupMapper.deleteCompany(leadGroup.getId());
                leadGroupMapper.deleteEmployeeGroup(leadGroup.getId());
                leadGroupMapper.deleteLead(leadGroup.getId());
                saveDetails(leadGroup.getId(), request);

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/lead-group/update", null, null, "Lead Group", "Lead Group (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-group/update", line, error.toString(), "Lead Group", "Lead Group (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead Group (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = leadGroupMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/lead-group/delete/{id}", null, null, "Lead Group", "Lead Group (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-group/delete/{id}", line, error.toString(), "Lead Group", "Lead Group (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private void saveDetails(Long leadGroupId, LeadGroupRequest request) {
        if (request.getCompanyId() != null) {
            leadGroupMapper.insertCompany(request.getCompanyId(), leadGroupId);
        }
        if (request.getEmployeeGroups() != null) {
            for (Long employeeGroupId : request.getEmployeeGroups()) {
                leadGroupMapper.insertEmployeeGroup(employeeGroupId, leadGroupId);
            }
        }
        if (request.getLeads() != null) {
            for (Long leadId : request.getLeads()) {
                leadGroupMapper.insertLead(leadId, leadGroupId);
            }
        }
    }
}
