package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.LeadContactMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.LeadContact.LeadContact;
import com.ut.nlSystemAPi.model.filter.LeadContactFilter;
import com.ut.nlSystemAPi.model.request.LeadContact.LeadContactRequest;
import com.ut.nlSystemAPi.model.request.LeadContact.LeadContactUpdateRequest;
import com.ut.nlSystemAPi.model.response.LeadContact.LeadContactResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.LeadContactService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class LeadContactServiceImpl implements LeadContactService {

    @Autowired
    private LeadContactMapper leadContactMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    public ResponseMessage<BaseResult> getList(LeadContactFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead Contact (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(leadContactMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<LeadContactResponse> responses = leadContactMapper.getList(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-contact/list", null, null, "Lead Contact", "Lead Contact (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-contact/list", line, error.toString(), "Lead Contact", "Lead Contact (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead Contact (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<LeadContactResponse> responses = leadContactMapper.getOne(id, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-contact/find/{id}", null, null, "Lead Contact", "Lead Contact (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-contact/find/{id}", line, error.toString(), "Lead Contact", "Lead Contact (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(LeadContactRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead Contact (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (leadContactMapper.checkDuplicate(request.getContactName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            LeadContact leadContact = toLeadContact(request);
            leadContact.setContactName(formatContactName(request.getSalutation(), request.getContactName()));
            leadContact.setCreatedBy(userId);
            leadContact.setIsActive(1);
            Boolean result = leadContactMapper.insert(leadContact);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/lead-contact/add", null, null, "Lead Contact", "Lead Contact (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-contact/add", line, error.toString(), "Lead Contact", "Lead Contact (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(LeadContactUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead Contact (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (leadContactMapper.checkDuplicate(request.getContactName(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            LeadContact leadContact = toLeadContact(request);
            leadContact.setId(request.getId());
            leadContact.setContactName(formatContactName(request.getSalutation(), request.getContactName()));
            leadContact.setModifiedBy(userId);
            Boolean result = leadContactMapper.update(leadContact);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/lead-contact/update", null, null, "Lead Contact", "Lead Contact (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-contact/update", line, error.toString(), "Lead Contact", "Lead Contact (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead Contact (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = leadContactMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/lead-contact/delete/{id}", null, null, "Lead Contact", "Lead Contact (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-contact/delete/{id}", line, error.toString(), "Lead Contact", "Lead Contact (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private LeadContact toLeadContact(LeadContactRequest request) {
        LeadContact leadContact = new LeadContact();
        leadContact.setLeadId(request.getLeadId());
        leadContact.setSalutation(request.getSalutation());
        leadContact.setGender(request.getGender());
        leadContact.setContactTelephone(request.getContactTelephone());
        leadContact.setContactEmail(request.getContactEmail());
        leadContact.setPosition(request.getPosition());
        leadContact.setPersonalizeIndexId(request.getPersonalizeIndexId());
        leadContact.setRole(request.getRole());
        leadContact.setWorkFlow(request.getWorkFlow());
        leadContact.setNeeds(request.getNeeds());
        leadContact.setWants(request.getWants());
        leadContact.setPainPoint(request.getPainPoint());
        leadContact.setNote(request.getNote());
        return leadContact;
    }

    private String formatContactName(String salutation, String contactName) {
        if (salutation == null || salutation.trim().isEmpty()) {
            return contactName;
        }
        return salutation.trim() + " " + contactName;
    }
}
