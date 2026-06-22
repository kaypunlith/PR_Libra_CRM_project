package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.OutsidePolygonUtils;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.FileBaseEntity;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Organization.Organization;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.entity.Organization.OrganizationActivityCard;
import com.ut.nlSystemAPi.model.entity.Organization.OrganizationDivision;
import com.ut.nlSystemAPi.model.filter.OrganizationActivityCardFilter;
import com.ut.nlSystemAPi.model.filter.OrganizationFilter;
import com.ut.nlSystemAPi.model.request.Organization.OrganizationActivityCardRequest;
import com.ut.nlSystemAPi.model.request.Organization.OrganizationDivisionRequest;
import com.ut.nlSystemAPi.model.request.Organization.OrganizationRequest;
import com.ut.nlSystemAPi.model.request.Organization.OrganizationUpdateRequest;
import com.ut.nlSystemAPi.model.response.Dropdown.VendorDropdownResponse;
import com.ut.nlSystemAPi.model.response.Organization.OrganizationActivityCardResponse;
import com.ut.nlSystemAPi.model.response.Organization.OrganizationDetailResponse;
import com.ut.nlSystemAPi.model.response.Organization.OrganizationResponse;
import com.ut.nlSystemAPi.model.response.Organization.OrganizationZoneResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.UserService;
import com.ut.nlSystemAPi.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ModuleTypeMapper moduleTypeMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private GenerateCode generateCode;

    @Autowired
    private HelperMapper helperMapper;

    public ResponseMessage<BaseResult> getList(OrganizationFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (permissionMapper.checkPermission(userId, "Customer (View By User)") > 0) {
                filter.setViewByUser(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(organizationMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<OrganizationResponse> responses = organizationMapper.getList(filter, userId);

            if (!responses.isEmpty() && filter.getHavingBalance() == null) {
                for (OrganizationResponse response : responses) {

                    OrganizationActivityCardResponse activityCardResponse = organizationMapper.countListActivityCard(response.getId());

                    if (activityCardResponse.getTotalRecord() > 0) {
                        response.setActivityCardRecords(activityCardResponse);
                    }

                    // Check Zone
                    List<OrganizationZoneResponse> zones = organizationMapper.getListZone();
                    if (!zones.isEmpty()) {
                        boolean foundZone = false;
                        for (OrganizationZoneResponse zone : zones) {
                            // Ensure coordinates are not null
                            if (response.getLats() != null && response.getLongs() != null &&
                                    zone.getLats() != null && zone.getLongs() != null) {
                                try {
                                    boolean isOutside = OutsidePolygonUtils.isLocationOutsideOfZone(
                                            zone.getLats(), zone.getLongs(),
                                            response.getLats(), response.getLongs()
                                    );
                                    if (!isOutside) {
                                        response.setZoneInfo(zone.getName()); // Set zone name
                                        foundZone = true;
                                        break; // Stop once a zone is found
                                    }
                                } catch (Exception e) {
                                    response.setZoneInfo("OUT OF ZONE"); // Default if no zone matches
                                }
                            }
                        }
                        if (!foundZone) {
                            response.setZoneInfo("OUT OF ZONE"); // Default if no zone matches
                        }
                    } else {
                        response.setZoneInfo("OUT OF ZONE"); // Default if no zones exist
                    }
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/list", null, null, "Customer", "Customer (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/list", line, error.toString(), "Customer", "Customer (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<OrganizationResponse> responses = organizationMapper.getOne(id, userId);

            OrganizationActivityCardFilter filter = new OrganizationActivityCardFilter();

            if (!responses.isEmpty()){
                for (OrganizationResponse response : responses) {
                    filter.setId(id);
                    response.setActivityCards(organizationMapper.getListActivityCard(filter));
                    response.setCompanies(organizationMapper.getCustomerCompanies(id));
                    response.setOrganizationGroups(organizationMapper.getCustomerCgroups(id));
                    response.setOrganizationContacts(organizationMapper.getCustomerContact(id));
                    response.setCustomerContract(organizationMapper.getCustomerContracts(id));
                    response.setCustomerTerminate(organizationMapper.getCustomerTerminates(id));
                    response.setCustomerSurvey(organizationMapper.getCustomerSurveys(id));
                    response.setDivisionInformation(organizationMapper.getDivisionInformation(id));
                    if (response.getDivisionInformation() != null) {
                        for (OrganizationDetailResponse division : response.getDivisionInformation()) {
                            division.setNegotiations(organizationMapper.getDivisionInformationDetail(division.getId()));
                        }
                    }
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/find/{id}", null, null, "Customer", "Customer (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/find/{id}", line, error.toString(), "Customer", "Customer (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(OrganizationRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Integer customerType = request.getCustomerType() != null ? request.getCustomerType() : 1;
            Integer isFreedom = request.getIsFreedom() != null ? request.getIsFreedom() : 0;
            Integer recurrence = request.getRecurrence() != null ? request.getRecurrence() : 0;
            Integer displayOnInvoice = request.getDisplayOnInvoice() != null ? request.getDisplayOnInvoice() : 0;
            Integer settingInvoiceTax = request.getSettingInvoiceTax() != null ? request.getSettingInvoiceTax() : 1;
            Double limitCredit = request.getLimitCredit() != null ? request.getLimitCredit() : 0D;
            Long limitNumberInvoice = request.getLimitNumberInvoice() != null ? request.getLimitNumberInvoice() : 0L;

            // Check Data
            String customerCode = generateCode.generateCustomerCode();

            Organization organization = new Organization();
            organization.setPhoto(request.getPhoto());
            organization.setOrganizationCode(customerCode);

            organization.setSourceId(request.getSourceId());
            organization.setBusinessTypeId(request.getBusinessTypeId());
            organization.setBusinessActivityId(request.getBusinessActivityId());
            organization.setCustomerType(customerType);
            organization.setIsFreedom(isFreedom);
            organization.setFreedomType(request.getFreedomType());
            organization.setName(request.getName());
            organization.setNameKh(request.getNameKh());
            organization.setShopName(request.getShopName());
            organization.setLats(request.getLats());
            organization.setLongs(request.getLongs());
            organization.setTelephone(request.getTelephone());
            organization.setCountryId(request.getCountryId());
            organization.setHouseNo(request.getHouseNo());
            organization.setStreetId(request.getStreetId());
            organization.setProvinceId(request.getProvinceId());
            organization.setDistrictId(request.getDistrictId());
            organization.setCommuneId(request.getCommuneId());
            organization.setVillageId(request.getVillageId());
            organization.setAddress(request.getAddress());
            organization.setMobile(request.getMobile());
            organization.setAlternateMobile(request.getAlternateMobile());
            organization.setEmail(request.getEmail());
            organization.setFax(request.getFax());
            organization.setVat(request.getVat());
            organization.setPaymentTermId(request.getPaymentTermId());
            organization.setPriceTypeId(request.getPriceTypeId());
            organization.setPaymentMonthly(request.getPaymentMonthly());
            organization.setLimitCredit(limitCredit);
            organization.setLimitNumberInvoice(limitNumberInvoice);
            organization.setPeriodFrom(request.getPeriodFrom());
            organization.setPeriodTo(request.getPeriodTo());
            organization.setRecurrence(recurrence);
            organization.setDisplayOnInvoice(displayOnInvoice);
            organization.setSettingInvoiceTax(settingInvoiceTax);
            applyFreedomBranchRequest(organization, request);
            organization.setCreatedBy(userId);

            Boolean result = organizationMapper.insert(organization);

            if (result) {

                String code = request.getOrganizationCode();

                if (request.getCloneId() != null){
                    // Clone
                    helperMapper.archive("customers", "is_active", 0, request.getCloneId(), userId);

                    organizationMapper.updateCustomerContact(organization.getId(), request.getCloneId());

                    helperMapper.updateCode("customers", "customer_code", code, organization.getId());
                } else {
                    // Add
                    helperMapper.updateCode("customers", "customer_code", code, organization.getId());
                }

                if (request.getCompanies() != null) {
                    for (Long companyId : request.getCompanies()) {
                        organization.setCompanyId(companyId);
                        organizationMapper.insertCustomerCompanies(organization);
                    }
                }

                if (request.getOrganizationGroups() != null) {
                    for (Long groupId : request.getOrganizationGroups()) {
                        organization.setOrganizationGroupId(groupId);
                        organizationMapper.insertCustomerCgroups(organization);
                    }
                }

                if (request.getOrganizationContacts() != null) {
                    for (Long contactId : request.getOrganizationContacts()) {
                        organization.setOrganizationContactId(contactId);
                        organizationMapper.insertCustomerContact(organization);
                    }
                }

                if (request.getCustomerContract() != null) {
                    for (FileBaseEntity contract : request.getCustomerContract()){
                        organization.setCustomerContract(contract.getName());
                        organization.setCustomerContractUrl(contract.getUrl());
                        organizationMapper.insertCustomerContracts(organization);
                    }
                }

                if (request.getCustomerTerminate() != null) {
                    for (FileBaseEntity terminate : request.getCustomerTerminate()) {
                        organization.setCustomerTerminate(terminate.getName());
                        organization.setCustomerTerminateUrl(terminate.getUrl());
                        organizationMapper.insertCustomerTerminates(organization);
                    }
                }

                if (request.getCustomerSurvey() != null) {
                    for (FileBaseEntity survey : request.getCustomerSurvey()) {
                        organization.setCustomerSurvey(survey.getName());
                        organization.setCustomerSurveyUrl(survey.getUrl());
                        organizationMapper.insertCustomerSurveys(organization);
                    }
                }

                if (request.getDivisionInformation() != null) {
                    OrganizationDivision division = new OrganizationDivision();
                    for (OrganizationDivisionRequest divisionInfo : request.getDivisionInformation()) {
                        division.setOrganizationId(organization.getId());
                        division.setTitle(divisionInfo.getTitle());
                        division.setMakingProcess(divisionInfo.getMakingProcess());
                        organizationMapper.insertDivisionInformation(division);

                        if (divisionInfo.getNegotiations() != null) {
                            for (Long negotiationId : divisionInfo.getNegotiations()) {
                                division.setNegotiationId(negotiationId);
                                organizationMapper.insertDivisionInformationDetail(division);
                            }
                        }
                    }
                }

                if (isFreedom == 1) {
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/organization/add", null, null, "Customer", "Customer (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/add", line, error.toString(), "Customer", "Customer (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(OrganizationUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Integer isFreedom = request.getIsFreedom();
            if (isFreedom == null) {
                List<OrganizationResponse> existing = organizationMapper.getOne(request.getId(), userId);
                if (existing != null && !existing.isEmpty()) {
                    isFreedom = existing.get(0).getIsFreedom();
                }
            }
            Integer existingIsFreedom = organizationMapper.getIsFreedom(request.getId());
            Integer isSync = organizationMapper.getIsSync(request.getId());
            boolean syncFreedom = isSync != null && isSync == 1;

            // Check Data
            Organization organization = new Organization();
            organization.setId(request.getId());
            organization.setPhoto(request.getPhoto());
            organization.setOrganizationCode(request.getOrganizationCode());
            organization.setBusinessTypeId(request.getBusinessTypeId());
            organization.setBusinessActivityId(request.getBusinessActivityId());
            organization.setSourceId(request.getSourceId());
            organization.setCustomerType(request.getCustomerType());
            organization.setIsFreedom(isFreedom);
            organization.setFreedomType(request.getFreedomType());
            organization.setName(request.getName());
            organization.setNameKh(request.getNameKh());
            organization.setShopName(request.getShopName());
            organization.setLats(request.getLats());
            organization.setLongs(request.getLongs());
            organization.setCountryId(request.getCountryId());
            organization.setHouseNo(request.getHouseNo());
            organization.setStreetId(request.getStreetId());
            organization.setProvinceId(request.getProvinceId());
            organization.setDistrictId(request.getDistrictId());
            organization.setCommuneId(request.getCommuneId());
            organization.setVillageId(request.getVillageId());
            organization.setAddress(request.getAddress());
            organization.setTelephone(request.getTelephone());
            organization.setMobile(request.getMobile());
            organization.setAlternateMobile(request.getAlternateMobile());
            organization.setEmail(request.getEmail());
            organization.setFax(request.getFax());
            organization.setVat(request.getVat());
            organization.setPaymentTermId(request.getPaymentTermId());
            organization.setPriceTypeId(request.getPriceTypeId());
            organization.setPaymentMonthly(request.getPaymentMonthly());
            organization.setLimitCredit(request.getLimitCredit());
            organization.setLimitNumberInvoice(request.getLimitNumberInvoice());
            organization.setPeriodFrom(request.getPeriodFrom());
            organization.setPeriodTo(request.getPeriodTo());
            organization.setRecurrence(request.getRecurrence());
            organization.setDisplayOnInvoice(request.getDisplayOnInvoice());
            organization.setSettingInvoiceTax(request.getSettingInvoiceTax());
            applyFreedomBranchRequest(organization, request);
            organization.setModifiedBy(userId);
            Boolean result = organizationMapper.update(organization);

            if (result) {
                if (syncFreedom) {
                }
                if (isFreedom != null && isFreedom == 1) {
                } else if (existingIsFreedom != null && existingIsFreedom == 1) {
                }

                if (isFreedom != null && isFreedom == 1 && request.getFreedomCustomers() != null){
                }

                if (request.getCompanies() != null) {
                    if (syncFreedom) {
                    }
                    organizationMapper.deleteCustomerCompanies(request.getId());
                    for (Long companyId : request.getCompanies()) {
                        organization.setCompanyId(companyId);
                        organizationMapper.insertCustomerCompanies(organization);
                        if (syncFreedom) {
                        }
                    }
                }

                if (request.getOrganizationGroups() != null) {
                    if (syncFreedom) {
                    }
                    organizationMapper.deleteCustomerCgroups(request.getId());
                    for (Long groupId : request.getOrganizationGroups()) {
                        organization.setOrganizationGroupId(groupId);
                        organizationMapper.insertCustomerCgroups(organization);
                        if (syncFreedom) {
                        }
                    }
                }

                if (request.getOrganizationContacts() != null) {
                    if (syncFreedom) {
                    }
                    organizationMapper.deleteCustomerContacts(request.getId());
                    for (Long contactId : request.getOrganizationContacts()) {
                        organization.setOrganizationContactId(contactId);
                        organizationMapper.insertCustomerContact(organization);
                        if (syncFreedom) {
                        }
                    }
                }

                if (request.getCustomerContract() != null) {
                    for (FileBaseEntity contract : request.getCustomerContract()){
                        organization.setCustomerContract(contract.getName());
                        organization.setCustomerContractUrl(contract.getUrl());
                        organizationMapper.insertCustomerContracts(organization);
                        if (syncFreedom) {
                        }
                    }
                }

                if (request.getCustomerTerminate() != null) {
                    for (FileBaseEntity terminate : request.getCustomerTerminate()) {
                        organization.setCustomerTerminate(terminate.getName());
                        organization.setCustomerTerminateUrl(terminate.getUrl());
                        organizationMapper.insertCustomerTerminates(organization);
                        if (syncFreedom) {
                        }
                    }
                }

                if (request.getCustomerSurvey() != null) {
                    for (FileBaseEntity survey : request.getCustomerSurvey()) {
                        organization.setCustomerSurvey(survey.getName());
                        organization.setCustomerSurveyUrl(survey.getUrl());
                        organizationMapper.insertCustomerSurveys(organization);
                        if (syncFreedom) {
                        }
                    }
                }

                if (request.getDivisionInformation() != null) {
                    if (syncFreedom) {
                    }
                    organizationMapper.deleteDivisionInformationDetail(request.getId());
                    organizationMapper.deleteDivisionInformation(request.getId());
                    OrganizationDivision division = new OrganizationDivision();
                    for (OrganizationDivisionRequest divisionInfo : request.getDivisionInformation()) {
                        division.setOrganizationId(organization.getId());
                        division.setTitle(divisionInfo.getTitle());
                        division.setMakingProcess(divisionInfo.getMakingProcess());
                        organizationMapper.insertDivisionInformation(division);
                        if (syncFreedom) {
                        }

                        if (divisionInfo.getNegotiations() != null) {
                            for (Long negotiationId : divisionInfo.getNegotiations()) {
                                division.setNegotiationId(negotiationId);
                                organizationMapper.insertDivisionInformationDetail(division);
                                if (syncFreedom) {
                                }
                            }
                        }
                    }
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/organization/update", line, null, "Customer", "Customer (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/update", line, error.toString(), "Customer", "Customer (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Integer isFreedom = organizationMapper.getIsFreedom(id);
            Integer isSync = organizationMapper.getIsSync(id);
            boolean syncFreedomCustomer = isSync != null && isSync == 1;
            boolean syncFreedomBranch = isFreedom != null && isFreedom == 1;

            Boolean result = organizationMapper.delete(id, userId);
            if (result) {
                if (syncFreedomCustomer) {
                }
                if (syncFreedomBranch) {
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/organization/delete/{id}", null, null, "Customer", "Customer (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/delete/{id}", line, error.toString(), "Customer", "Customer (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> addActivityCard(OrganizationActivityCardRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Customer (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            // Check Data
            OrganizationActivityCard activityCard = new OrganizationActivityCard();
            activityCard.setOrganizationContactId(request.getOrganizationContactId());
            activityCard.setActionStatusId(request.getActionStatusId());
            activityCard.setPosition(request.getPosition());
            activityCard.setIssueDate(request.getIssueDate());
            activityCard.setSubject(request.getSubject());
            activityCard.setResultAction(request.getResultAction());
            activityCard.setOther(request.getOther());
            activityCard.setCreatedBy(userId);

            Boolean result = organizationMapper.addActivityCard(activityCard);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/organization/add-activity-card", null, null, "Customer", "Customer (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/add-activity-card", line, error.toString(), "Customer", "Customer (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListActivityCard(OrganizationActivityCardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<OrganizationActivityCardResponse> responses = organizationMapper.getListActivityCard(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/list", null, null, "Customer", "Customer (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/list", line, error.toString(), "Customer", "Customer (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private void applyFreedomBranchRequest(Organization organization, OrganizationRequest request) {
        organization.setBranchTypeId(1L);
        organization.setCode(request.getCode());
        organization.setAbbr(request.getAbbr());
        organization.setPhotoUrl(request.getPhotoUrl());
        organization.setPhotoName(request.getPhotoName());
        organization.setStartWorkHour(request.getStartWorkHour());
        organization.setEndWorkHour(request.getEndWorkHour());
        organization.setAddressKh(request.getAddressKh());
        organization.setAdjCode(request.getAdjCode());
        organization.setProductPriceListCode(request.getProductPriceListCode());
        organization.setPriceRequestNoCode(request.getPriceRequestNoCode());
        organization.setRequestStockCode(request.getRequestStockCode());
        organization.setTransferCode(request.getTransferCode());
        organization.setTransferReceiveCode(request.getTransferReceiveCode());
        organization.setPurchaseOrderCode(request.getPurchaseOrderCode());
        organization.setPurchaseOrderReceiptCode(request.getPurchaseOrderReceiptCode());
        organization.setSaleOrderCode(request.getSaleOrderCode());
        organization.setExpenseRequestCode(request.getExpenseRequestCode());
        organization.setQuotationCode(request.getQuotationCode());
        organization.setInvoiceCode(request.getInvoiceCode());
        organization.setPosCode(request.getPosCode());
        organization.setInvoiceReceiptCode(request.getInvoiceReceiptCode());
        organization.setDnCode(request.getDnCode());
        organization.setCreditMemoCode(request.getCreditMemoCode());
        organization.setCreditReceiptCode(request.getCreditReceiptCode());
        organization.setPurchaseBillCode(request.getPurchaseBillCode());
        organization.setPurchaseBillReceiptCode(request.getPurchaseBillReceiptCode());
        organization.setBillReturnCode(request.getBillReturnCode());
        organization.setBillReceiptCode(request.getBillReceiptCode());
        organization.setBomCode(request.getBomCode());
        organization.setGoodReceiptNoteCode(request.getGoodReceiptNoteCode());
        organization.setLandedCostCode(request.getLandedCostCode());
        organization.setJournalEntryCode(request.getJournalEntryCode());
        organization.setReceivePaymentCode(request.getReceivePaymentCode());
        organization.setReceivePaymentOrgCode(request.getReceivePaymentOrgCode());
        organization.setReceivePaymentEmpCode(request.getReceivePaymentEmpCode());
        organization.setPayBillCode(request.getPaybillsCode());
        organization.setPayJournalCode(request.getPaybillsJournalCode());
    }

}
