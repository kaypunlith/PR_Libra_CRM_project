package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.LeadMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.FileBaseEntity;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Lead.Lead;
import com.ut.nlSystemAPi.model.entity.Lead.LeadActivityCard;
import com.ut.nlSystemAPi.model.entity.Lead.LeadDivision;
import com.ut.nlSystemAPi.model.filter.LeadActivityCardFilter;
import com.ut.nlSystemAPi.model.filter.LeadFilter;
import com.ut.nlSystemAPi.model.request.Lead.LeadActivityCardRequest;
import com.ut.nlSystemAPi.model.request.Lead.LeadConvertRequest;
import com.ut.nlSystemAPi.model.request.Lead.LeadDivisionRequest;
import com.ut.nlSystemAPi.model.request.Lead.LeadRequest;
import com.ut.nlSystemAPi.model.request.Lead.LeadUpdateRequest;
import com.ut.nlSystemAPi.model.response.Lead.LeadActivityCardResponse;
import com.ut.nlSystemAPi.model.response.Lead.LeadDetailResponse;
import com.ut.nlSystemAPi.model.response.Lead.LeadResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.LeadService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class LeadServiceImpl implements LeadService {

    @Autowired
    private LeadMapper leadMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private GenerateCode generateCode;

    public ResponseMessage<BaseResult> getList(LeadFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(leadMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<LeadResponse> responses = leadMapper.getList(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/list", null, null, "Lead", "Lead (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/list", line, error.toString(), "Lead", "Lead (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<LeadResponse> responses = leadMapper.getOne(id, userId);
            for (LeadResponse response : responses) {
                setLeadDetails(response);
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/find/{id}", null, null, "Lead", "Lead (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/find/{id}", line, error.toString(), "Lead", "Lead (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(LeadRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (leadMapper.checkDuplicate(request.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            Lead lead = toLead(request);
            lead.setLeadCode(generateCode.generateAutoCode("customers", "lead_code", 5, "L", true, "customer_type = 2 AND is_active = 3"));
            lead.setCreatedBy(userId);
            lead.setIsActive(3);

            Boolean result = leadMapper.insert(lead);
            if (result) {
                saveDetails(lead.getId(), request);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/lead/add", null, null, "Lead", "Lead (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/add", line, error.toString(), "Lead", "Lead (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(LeadUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (leadMapper.checkDuplicate(request.getName(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            Lead lead = toLead(request);
            lead.setId(request.getId());
            lead.setModifiedBy(userId);
            Boolean result = leadMapper.update(lead);
            if (result) {
                leadMapper.deleteCustomerCompanies(lead.getId());
                leadMapper.deleteCustomerCgroups(lead.getId());
                leadMapper.deleteCustomerContacts(lead.getId());
                leadMapper.deleteDivisionInformationDetail(lead.getId());
                leadMapper.deleteDivisionInformation(lead.getId());
                saveDetails(lead.getId(), request);

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/lead/update", null, null, "Lead", "Lead (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/update", line, error.toString(), "Lead", "Lead (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = leadMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/lead/delete/{id}", null, null, "Lead", "Lead (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/delete/{id}", line, error.toString(), "Lead", "Lead (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> addActivityCard(LeadActivityCardRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            LeadActivityCard activityCard = new LeadActivityCard();
            activityCard.setLeadContactId(request.getLeadContactId());
            activityCard.setPosition(request.getPosition());
            activityCard.setActionStatusId(request.getActionStatusId());
            activityCard.setIssueDate(request.getIssueDate());
            activityCard.setSubject(request.getSubject());
            activityCard.setResultAction(request.getResultAction());
            activityCard.setOther(request.getOther());
            activityCard.setIsActive(1);
            activityCard.setCreatedBy(userId);

            Boolean result = leadMapper.addActivityCard(activityCard);
            if (result) {
                if (request.getPosition() != null && !request.getPosition().trim().isEmpty()) {
                    leadMapper.updateLeadContactPosition(request.getLeadContactId(), request.getPosition().trim());
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/lead/add-activity-card", null, null, "Lead", "Lead (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/add-activity-card", line, error.toString(), "Lead", "Lead (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getListActivityCard(LeadActivityCardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(leadMapper.countListActivityCard(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<LeadActivityCardResponse> responses = leadMapper.getListActivityCard(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/list-activity-card", null, null, "Lead", "Lead (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/list-activity-card", line, error.toString(), "Lead", "Lead (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOneActivityCard(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<LeadActivityCardResponse> responses = leadMapper.getOneActivityCard(id, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/find-activity-card/{id}", null, null, "Lead", "Lead (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/find-activity-card/{id}", line, error.toString(), "Lead", "Lead (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getConvert(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<LeadResponse> responses = leadMapper.getConvert(id, userId);
            for (LeadResponse response : responses) {
                setLeadDetails(response);
                response.setCustomerContract(leadMapper.getCustomerContracts(response.getId()));
                response.setCustomerTerminate(leadMapper.getCustomerTerminates(response.getId()));
                response.setCustomerSurvey(leadMapper.getCustomerSurveys(response.getId()));
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/find-convert/{id}", null, null, "Lead", "Lead (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/find-convert/{id}", line, error.toString(), "Lead", "Lead (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> convert(LeadConvertRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Lead (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Lead lead = toConvertLead(request);
            String abbr = leadMapper.getBusinessActivityAbbr(request.getBusinessActivityId());
            if (abbr == null || abbr.trim().isEmpty()) {
                abbr = "C";
            }
            lead.setLeadCode(generateCode.generateAutoCode("customers", "customer_code", 3, abbr, false, "is_active = 1"));
            lead.setModifiedBy(userId);

            Boolean result = leadMapper.convert(lead);
            if (result) {
                leadMapper.deleteCustomerCompanies(lead.getId());
                leadMapper.deleteCustomerCgroups(lead.getId());
                leadMapper.deleteCustomerContracts(lead.getId());
                leadMapper.deleteCustomerTerminates(lead.getId());
                leadMapper.deleteCustomerSurveys(lead.getId());

                if (request.getCompanies() != null) {
                    for (Long companyId : request.getCompanies()) {
                        lead.setCompanyId(companyId);
                        leadMapper.insertCustomerCompanies(lead);
                    }
                }
                if (request.getOrganizationGroups() != null) {
                    for (Long organizationGroupId : request.getOrganizationGroups()) {
                        lead.setLeadGroupId(organizationGroupId);
                        leadMapper.insertCustomerCgroups(lead);
                    }
                }
                if (request.getOrganizationContacts() != null) {
                    for (Long contactId : request.getOrganizationContacts()) {
                        leadMapper.convertLeadContact(contactId, lead.getId());
                    }
                }
                saveCustomerFiles(lead, request);

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/lead/convert", null, null, "Lead", "Lead (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead/convert", line, error.toString(), "Lead", "Lead (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private Lead toLead(LeadRequest request) {
        Lead lead = new Lead();
        lead.setPhoto(request.getPhoto());
        lead.setName(request.getName());
        lead.setNameKh(request.getNameKh());
        lead.setLats(request.getLats());
        lead.setLongs(request.getLongs());
        lead.setTelephone(request.getTelephone());
        lead.setMobile(request.getMobile());
        lead.setAlternateMobile(request.getAlternateMobile());
        lead.setEmail(request.getEmail());
        lead.setAddress(request.getAddress());
        return lead;
    }

    private Lead toConvertLead(LeadConvertRequest request) {
        Lead lead = new Lead();
        lead.setId(request.getId());
        lead.setPhoto(request.getPhoto());
        lead.setBusinessTypeId(request.getBusinessTypeId());
        lead.setBusinessActivityId(request.getBusinessActivityId());
        lead.setCustomerType(request.getCustomerType());
        lead.setCountryId(request.getCountryId());
        lead.setName(request.getName());
        lead.setNameKh(request.getNameKh());
        lead.setLats(request.getLats());
        lead.setLongs(request.getLongs());
        lead.setTelephone(request.getTelephone());
        lead.setMobile(request.getMobile());
        lead.setAlternateMobile(request.getAlternateMobile());
        lead.setEmail(request.getEmail());
        lead.setFax(request.getFax());
        lead.setVat(request.getVat());
        lead.setPaymentTermId(request.getPaymentTermId());
        lead.setPaymentMonthly(request.getPaymentMonthly());
        lead.setLimitCredit(request.getLimitCredit());
        lead.setLimitNumberInvoice(request.getLimitNumberInvoice());
        lead.setRecurrence(request.getRecurrence());
        lead.setDisplayOnInvoice(request.getDisplayOnInvoice());
        lead.setSettingInvoiceTax(request.getSettingInvoiceTax());
        lead.setHouseNo(request.getHouseNo());
        lead.setStreetId(request.getStreetId());
        lead.setProvinceId(request.getProvinceId());
        lead.setDistrictId(request.getDistrictId());
        lead.setCommuneId(request.getCommuneId());
        lead.setVillageId(request.getVillageId());
        lead.setAddress(request.getAddress());
        return lead;
    }

    private void setLeadDetails(LeadResponse response) {
        response.setCompanies(leadMapper.getCustomerCompanies(response.getId()));
        response.setLeadGroups(leadMapper.getCustomerCgroups(response.getId()));
        response.setLeadContacts(leadMapper.getCustomerContact(response.getId()));
        List<LeadDetailResponse> divisions = leadMapper.getDivisionInformation(response.getId());
        for (LeadDetailResponse division : divisions) {
            division.setNegotiationIssues(leadMapper.getDivisionInformationDetail(division.getId()));
        }
        response.setDivisionInformation(divisions);
    }

    private void saveCustomerFiles(Lead lead, LeadConvertRequest request) {
        if (request.getCustomerContract() != null) {
            for (FileBaseEntity contract : request.getCustomerContract()) {
                lead.setCustomerContract(contract.getName());
                lead.setCustomerContractUrl(contract.getUrl());
                leadMapper.insertCustomerContracts(lead);
            }
        }
        if (request.getCustomerTerminate() != null) {
            for (FileBaseEntity terminate : request.getCustomerTerminate()) {
                lead.setCustomerTerminate(terminate.getName());
                lead.setCustomerTerminateUrl(terminate.getUrl());
                leadMapper.insertCustomerTerminates(lead);
            }
        }
        if (request.getCustomerSurvey() != null) {
            for (FileBaseEntity survey : request.getCustomerSurvey()) {
                lead.setCustomerSurvey(survey.getName());
                lead.setCustomerSurveyUrl(survey.getUrl());
                leadMapper.insertCustomerSurveys(lead);
            }
        }
    }

    private void saveDetails(Long leadId, LeadRequest request) {
        Lead lead = new Lead();
        lead.setId(leadId);

        if (request.getCompanies() != null) {
            for (Long companyId : request.getCompanies()) {
                lead.setCompanyId(companyId);
                leadMapper.insertCustomerCompanies(lead);
            }
        }
        if (request.getLeadGroups() != null) {
            for (Long leadGroupId : request.getLeadGroups()) {
                lead.setLeadGroupId(leadGroupId);
                leadMapper.insertCustomerCgroups(lead);
            }
        }
        if (request.getLeadContacts() != null) {
            for (Long leadContactId : request.getLeadContacts()) {
                lead.setLeadContactId(leadContactId);
                leadMapper.insertCustomerContact(lead);
            }
        }
        if (request.getDivisionInformation() != null) {
            for (LeadDivisionRequest divisionRequest : request.getDivisionInformation()) {
                LeadDivision division = new LeadDivision();
                division.setLeadId(leadId);
                division.setTitle(divisionRequest.getTitle());
                division.setMakingProcess(divisionRequest.getMakingProcess());
                leadMapper.insertDivisionInformation(division);
                if (divisionRequest.getNegotiationIds() != null) {
                    for (Long negotiationId : divisionRequest.getNegotiationIds()) {
                        division.setNegotiationId(negotiationId);
                        leadMapper.insertDivisionInformationDetail(division);
                    }
                }
            }
        }
    }
}
