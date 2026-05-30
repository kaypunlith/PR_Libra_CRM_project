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
import com.ut.nlSystemAPi.mapper.freedom.FreedomMapper;
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

    @Autowired
    private FreedomMapper freedomMapper;

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
                    response.setFreedomCustomers(mapFreedomCustomersToOrganizationDetails(freedomMapper.getCustomerLocationGroupCustomers(id)));
                    applyFreedomBranchModuleCodes(response);
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
            Organization organization = new Organization();
            organization.setPhoto(request.getPhoto());
            organization.setOrganizationCode(request.getOrganizationCode());
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
                    upsertFreedomWarehouseAndLocation(organization);
                    createFreedomTables(organization.getId(), true);
                    syncFreedomCustomerLocationGroups(organization.getId(), request.getFreedomCustomers());
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
                    freedomMapper.updateCustomer(toFreedomCustomerMap(organization));
                }
                if (isFreedom != null && isFreedom == 1) {
                    upsertFreedomWarehouseAndLocation(organization);
                } else if (existingIsFreedom != null && existingIsFreedom == 1) {
                    deleteFreedomWarehouseAndLocation(organization.getId(), userId);
                }

                if (isFreedom != null && isFreedom == 1 && request.getFreedomCustomers() != null){
                    syncFreedomCustomerLocationGroups(organization.getId(), request.getFreedomCustomers());
                }

                if (request.getCompanies() != null) {
                    if (syncFreedom) {
                        freedomMapper.deleteCustomerCompanies(request.getId());
                    }
                    organizationMapper.deleteCustomerCompanies(request.getId());
                    for (Long companyId : request.getCompanies()) {
                        organization.setCompanyId(companyId);
                        organizationMapper.insertCustomerCompanies(organization);
                        if (syncFreedom) {
                            freedomMapper.insertCustomerCompanies(toFreedomOrgRelationMap(organization));
                        }
                    }
                }

                if (request.getOrganizationGroups() != null) {
                    if (syncFreedom) {
                        freedomMapper.deleteCustomerCgroups(request.getId());
                    }
                    organizationMapper.deleteCustomerCgroups(request.getId());
                    for (Long groupId : request.getOrganizationGroups()) {
                        organization.setOrganizationGroupId(groupId);
                        organizationMapper.insertCustomerCgroups(organization);
                        if (syncFreedom) {
                            freedomMapper.insertCustomerCgroups(toFreedomOrgRelationMap(organization));
                        }
                    }
                }

                if (request.getOrganizationContacts() != null) {
                    if (syncFreedom) {
                        freedomMapper.deleteCustomerContacts(request.getId());
                    }
                    organizationMapper.deleteCustomerContacts(request.getId());
                    for (Long contactId : request.getOrganizationContacts()) {
                        organization.setOrganizationContactId(contactId);
                        organizationMapper.insertCustomerContact(organization);
                        if (syncFreedom) {
                            freedomMapper.insertCustomerContact(toFreedomOrgRelationMap(organization));
                        }
                    }
                }

                if (request.getCustomerContract() != null) {
                    for (FileBaseEntity contract : request.getCustomerContract()){
                        organization.setCustomerContract(contract.getName());
                        organization.setCustomerContractUrl(contract.getUrl());
                        organizationMapper.insertCustomerContracts(organization);
                        if (syncFreedom) {
                            freedomMapper.insertCustomerContracts(toFreedomOrgRelationMap(organization));
                        }
                    }
                }

                if (request.getCustomerTerminate() != null) {
                    for (FileBaseEntity terminate : request.getCustomerTerminate()) {
                        organization.setCustomerTerminate(terminate.getName());
                        organization.setCustomerTerminateUrl(terminate.getUrl());
                        organizationMapper.insertCustomerTerminates(organization);
                        if (syncFreedom) {
                            freedomMapper.insertCustomerTerminates(toFreedomOrgRelationMap(organization));
                        }
                    }
                }

                if (request.getCustomerSurvey() != null) {
                    for (FileBaseEntity survey : request.getCustomerSurvey()) {
                        organization.setCustomerSurvey(survey.getName());
                        organization.setCustomerSurveyUrl(survey.getUrl());
                        organizationMapper.insertCustomerSurveys(organization);
                        if (syncFreedom) {
                            freedomMapper.insertCustomerSurveys(toFreedomOrgRelationMap(organization));
                        }
                    }
                }

                if (request.getDivisionInformation() != null) {
                    if (syncFreedom) {
                        freedomMapper.deleteDivisionInformationDetail(request.getId());
                        freedomMapper.deleteDivisionInformation(request.getId());
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
                            freedomMapper.insertDivisionInformation(toFreedomDivisionMap(division));
                        }

                        if (divisionInfo.getNegotiations() != null) {
                            for (Long negotiationId : divisionInfo.getNegotiations()) {
                                division.setNegotiationId(negotiationId);
                                organizationMapper.insertDivisionInformationDetail(division);
                                if (syncFreedom) {
                                    freedomMapper.insertDivisionInformationDetail(toFreedomDivisionMap(division));
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
                    freedomMapper.deleteCustomer(id, userId);
                }
                if (syncFreedomBranch) {
                    deleteFreedomWarehouseAndLocation(id, userId);
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
    public ResponseMessage<BaseResult> convertToFreedom(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<OrganizationResponse> existing = organizationMapper.getOne(id, userId);
            if (existing == null || existing.isEmpty()) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Data Not Found", false));
            }

            Organization organization = toOrganizationEntityForFreedom(existing.get(0), userId);
            Map<String, Object> freedomCustomer = toFreedomCustomerMap(organization);
            freedomMapper.insertCustomer(freedomCustomer);
            upsertFreedomWarehouseAndLocation(organization);
            createFreedomTables(organization.getId(), true);
            syncFreedomCustomerRelationsFromPrimary(organization);

            organizationMapper.markCustomerSyncedToFreedom(id, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/convert-to-freedom/{id}", null, null, "Customer", "Customer (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/convert-to-freedom/{id}", line, error.toString(), "Customer", "Customer (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
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

    private Organization toOrganizationEntityForFreedom(OrganizationResponse response, Long userId) {
        Organization organization = new Organization();
        organization.setId(response.getId());
        organization.setPhoto(response.getPhoto());
        organization.setOrganizationCode(response.getOrganizationCode());
        organization.setBusinessTypeId(response.getBusinessTypeId());
        organization.setBusinessActivityId(response.getBusinessActivityId());
        organization.setCustomerType(response.getCustomerType() != null ? response.getCustomerType() : 1);
        organization.setIsFreedom(1);
        organization.setName(response.getOrganizationName());
        organization.setNameKh(response.getOrganizationNameKh());
        organization.setShopName(response.getShopName());
        organization.setLats(response.getLats());
        organization.setLongs(response.getLongs());
        organization.setCountryId(response.getCountryId());
        organization.setHouseNo(response.getHouseNo());
        organization.setStreetId(response.getStreetId());
        organization.setProvinceId(response.getProvinceId());
        organization.setDistrictId(response.getDistrictId());
        organization.setCommuneId(response.getCommuneId());
        organization.setVillageId(response.getVillageId());
        organization.setAddress(response.getAddress());
        organization.setTelephone(response.getTelephone());
        organization.setMobile(response.getMobile());
        organization.setAlternateMobile(response.getAlternateMobile());
        organization.setEmail(response.getEmail());
        organization.setFax(response.getFax());
        organization.setVat(response.getVat());
        organization.setPaymentTermId(response.getPaymentTermId());
        organization.setPriceTypeId(response.getPriceTypeId());
        organization.setPaymentMonthly(response.getPaymentMonthly());
        organization.setLimitCredit(response.getLimitCredit() != null ? response.getLimitCredit() : 0D);
        organization.setLimitNumberInvoice(response.getLimitNumberInvoice() != null ? response.getLimitNumberInvoice() : 0L);
        organization.setPeriodFrom(response.getPeriodFrom());
        organization.setPeriodTo(response.getPeriodTo());
        organization.setRecurrence(response.getRecurrence() != null ? response.getRecurrence() : 0);
        organization.setDisplayOnInvoice(response.getDisplayOnInvoice() != null ? response.getDisplayOnInvoice() : 0);
        organization.setSettingInvoiceTax(response.getSettingInvoiceTax() != null ? response.getSettingInvoiceTax() : 1);
        organization.setCreatedBy(userId);
        organization.setModifiedBy(userId);
        return organization;
    }

    private void createFreedomTables(Long customerId, boolean syncFreedom) {
        if (!syncFreedom) {
            return;
        }

        String id = String.valueOf(customerId);

        if (freedomMapper.checkTableExists(id + "_group_totals") == 0) {
            freedomMapper.createGroupTotals(customerId);
        }

        if (freedomMapper.checkTableExists(id + "_group_total_details") == 0) {
            freedomMapper.createGroupTotalDetails(customerId);
        }

        if (freedomMapper.checkTableExists(id + "_inventories") == 0) {
            freedomMapper.createInventories(customerId);
        }

        if (freedomMapper.checkTableExists(id + "_inventory_totals") == 0) {
            freedomMapper.createInventoryTotals(customerId);
        }

        if (freedomMapper.checkTableExists(id + "_inventory_total_details") == 0) {
            freedomMapper.createInventoryTotalDetails(customerId);
        }
    }

    private void upsertFreedomWarehouseAndLocation(Organization organization) {
        if (organization == null || organization.getId() == null) {
            return;
        }
        freedomMapper.upsertCustomerWarehouse(toFreedomWarehouseMap(organization));
        freedomMapper.upsertCustomerLocation(toFreedomLocationMap(organization));
        freedomMapper.upsertCustomerBranch(toFreedomBranchMap(organization));
    }

    private void deleteFreedomWarehouseAndLocation(Long organizationId, Long userId) {
        if (organizationId == null) {
            return;
        }
        freedomMapper.deleteCustomerLocationGroups(organizationId);
        freedomMapper.deleteCustomerLocation(organizationId, userId);
        freedomMapper.deleteCustomerWarehouse(organizationId, userId);
        freedomMapper.deleteCustomerBranch(organizationId, userId);
    }

    private void syncFreedomCustomerLocationGroups(Long locationGroupId, List<Long> customers) {
        if (locationGroupId == null) {
            return;
        }
        freedomMapper.deleteCustomerLocationGroups(locationGroupId);
        if (customers == null || customers.isEmpty()) {
            return;
        }

        for (Long customerId : customers) {
            if (customerId == null) {
                continue;
            }
            freedomMapper.insertCustomerLocationGroup(toFreedomCustomerLocationGroupMap(customerId, locationGroupId));
        }
    }

    private void syncFreedomCustomerRelationsFromPrimary(Organization organization) {
        if (organization == null || organization.getId() == null) {
            return;
        }

        Long organizationId = organization.getId();
        Organization relation = new Organization();
        relation.setId(organizationId);

        freedomMapper.deleteCustomerCompanies(organizationId);
        List<OrganizationDetailResponse> companies = organizationMapper.getCustomerCompanies(organizationId);
        if (companies != null) {
            for (OrganizationDetailResponse company : companies) {
                if (company.getId() == null) {
                    continue;
                }
                relation.setCompanyId(company.getId());
                freedomMapper.insertCustomerCompanies(toFreedomOrgRelationMap(relation));
            }
        }

        freedomMapper.deleteCustomerCgroups(organizationId);
        List<OrganizationDetailResponse> cgroups = organizationMapper.getCustomerCgroups(organizationId);
        if (cgroups != null) {
            for (OrganizationDetailResponse cgroup : cgroups) {
                if (cgroup.getId() == null) {
                    continue;
                }
                relation.setOrganizationGroupId(cgroup.getId());
                freedomMapper.insertCustomerCgroups(toFreedomOrgRelationMap(relation));
            }
        }

        freedomMapper.deleteCustomerContacts(organizationId);
        List<OrganizationDetailResponse> contacts = organizationMapper.getCustomerContact(organizationId);
        if (contacts != null) {
            for (OrganizationDetailResponse contact : contacts) {
                if (contact.getId() == null) {
                    continue;
                }
                relation.setOrganizationContactId(contact.getId());
                freedomMapper.insertCustomerContact(toFreedomOrgRelationMap(relation));
            }
        }

        freedomMapper.deleteCustomerContracts(organizationId);
        List<OrganizationDetailResponse> contracts = organizationMapper.getCustomerContracts(organizationId);
        if (contracts != null) {
            for (OrganizationDetailResponse contract : contracts) {
                relation.setCustomerContract(contract.getName());
                relation.setCustomerContractUrl(contract.getUrl());
                freedomMapper.insertCustomerContracts(toFreedomOrgRelationMap(relation));
            }
        }

        freedomMapper.deleteCustomerTerminates(organizationId);
        List<OrganizationDetailResponse> terminates = organizationMapper.getCustomerTerminates(organizationId);
        if (terminates != null) {
            for (OrganizationDetailResponse terminate : terminates) {
                relation.setCustomerTerminate(terminate.getName());
                relation.setCustomerTerminateUrl(terminate.getUrl());
                freedomMapper.insertCustomerTerminates(toFreedomOrgRelationMap(relation));
            }
        }

        freedomMapper.deleteCustomerSurveys(organizationId);
        List<OrganizationDetailResponse> surveys = organizationMapper.getCustomerSurveys(organizationId);
        if (surveys != null) {
            for (OrganizationDetailResponse survey : surveys) {
                relation.setCustomerSurvey(survey.getName());
                relation.setCustomerSurveyUrl(survey.getUrl());
                freedomMapper.insertCustomerSurveys(toFreedomOrgRelationMap(relation));
            }
        }

        freedomMapper.deleteDivisionInformationDetail(organizationId);
        freedomMapper.deleteDivisionInformation(organizationId);
        List<OrganizationDetailResponse> divisions = organizationMapper.getDivisionInformation(organizationId);
        if (divisions != null) {
            for (OrganizationDetailResponse divisionData : divisions) {
                OrganizationDivision division = new OrganizationDivision();
                division.setId(divisionData.getId());
                division.setOrganizationId(organizationId);
                division.setTitle(divisionData.getTitle());
                division.setMakingProcess(divisionData.getMakingProcess());
                freedomMapper.insertDivisionInformation(toFreedomDivisionMap(division));

                List<OrganizationDetailResponse> negotiations = organizationMapper.getDivisionInformationDetail(divisionData.getId());
                if (negotiations != null) {
                    for (OrganizationDetailResponse negotiation : negotiations) {
                        if (negotiation.getId() == null) {
                            continue;
                        }
                        division.setNegotiationId(negotiation.getId());
                        freedomMapper.insertDivisionInformationDetail(toFreedomDivisionMap(division));
                    }
                }
            }
        }
    }

    private Map<String, Object> toFreedomCustomerMap(Organization organization) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", organization.getId());
        map.put("photo", organization.getPhoto());
        map.put("organizationCode", organization.getOrganizationCode());
        map.put("sysCode", organization.getOrganizationCode());
        map.put("leadCode", null);
        map.put("businessTypeId", organization.getBusinessTypeId());
        map.put("businessActivityId", organization.getBusinessActivityId());
        map.put("customerType", organization.getCustomerType());
        map.put("isFreedom", organization.getIsFreedom());
        map.put("freedomType", organization.getFreedomType());
        map.put("name", organization.getName());
        map.put("nameKh", organization.getNameKh());
        map.put("sex", null);
        map.put("countryId", organization.getCountryId());
        map.put("houseNo", organization.getHouseNo());
        map.put("streetId", organization.getStreetId());
        map.put("provinceId", organization.getProvinceId());
        map.put("districtId", organization.getDistrictId());
        map.put("communeId", organization.getCommuneId());
        map.put("villageId", organization.getVillageId());
        map.put("address", organization.getAddress());
        map.put("lats", organization.getLats());
        map.put("longs", organization.getLongs());
        map.put("zoneId", null);
        map.put("marketId", null);
        map.put("priceTypeId", organization.getPriceTypeId());
        map.put("inactivePeriod", null);
        map.put("telephone", organization.getTelephone());
        map.put("mobile", organization.getMobile());
        map.put("alternateMobile", organization.getAlternateMobile());
        map.put("email", organization.getEmail());
        map.put("fax", organization.getFax());
        map.put("vat", organization.getVat());
        map.put("paymentTermId", organization.getPaymentTermId());
        map.put("paymentMonthly", organization.getPaymentMonthly());
        map.put("limitCredit", organization.getLimitCredit());
        map.put("limitNumberInvoice", organization.getLimitNumberInvoice());
        map.put("periodFrom", organization.getPeriodFrom());
        map.put("periodTo", organization.getPeriodTo());
        map.put("recurrence", organization.getRecurrence());
        map.put("displayOnInvoice", organization.getDisplayOnInvoice());
        map.put("settingInvoiceTax", organization.getSettingInvoiceTax());
        map.put("note", null);
        map.put("createdBy", organization.getCreatedBy());
        map.put("modifiedBy", organization.getModifiedBy());
        return map;
    }

    private Map<String, Object> toFreedomWarehouseMap(Organization organization) {
        Map<String, Object> map = new HashMap<>();
        Long userId = organization.getCreatedBy() != null ? organization.getCreatedBy() : organization.getModifiedBy();
        map.put("id", organization.getId());
        map.put("name", getFreedomLocationName(organization));
        map.put("shopName", organization.getShopName());
        map.put("stockLevelId", null);
        map.put("description", null);
        map.put("createdBy", userId);
        return map;
    }

    private Map<String, Object> toFreedomLocationMap(Organization organization) {
        Map<String, Object> map = new HashMap<>();
        Long userId = organization.getCreatedBy() != null ? organization.getCreatedBy() : organization.getModifiedBy();
        map.put("id", organization.getId());
        map.put("name", getFreedomLocationName(organization));
        map.put("locationGroupId", organization.getId());
        map.put("isForSale", 0);
        map.put("createdBy", userId);
        map.put("modifiedBy", organization.getModifiedBy());
        return map;
    }

    private Map<String, Object> toFreedomBranchMap(Organization organization) {
        Map<String, Object> map = new HashMap<>();
        Long userId = organization.getCreatedBy() != null ? organization.getCreatedBy() : organization.getModifiedBy();
        map.put("id", organization.getId());
        map.put("companyId", organization.getCompanyId());
        map.put("branchTypeId", 1L);
        map.put("code", organization.getCode() != null ? organization.getCode() : organization.getOrganizationCode());
        map.put("abbr", organization.getAbbr());
        map.put("name", getFreedomLocationName(organization));
        map.put("nameKh", organization.getNameKh());
        map.put("photoUrl", organization.getPhotoUrl() != null ? organization.getPhotoUrl() : organization.getPhoto());
        map.put("photoName", organization.getPhotoName());
        map.put("telephone", organization.getTelephone());
        map.put("fax", organization.getFax());
        map.put("email", organization.getEmail());
        map.put("startWorkHour", organization.getStartWorkHour());
        map.put("endWorkHour", organization.getEndWorkHour());
        map.put("countryId", organization.getCountryId());
        map.put("lats", organization.getLats());
        map.put("longs", organization.getLongs());
        map.put("address", resolveFreedomBranchAddress(organization));
        map.put("addressKh", organization.getAddressKh());
        map.put("adjCode", organization.getAdjCode());
        map.put("productPriceListCode", organization.getProductPriceListCode());
        map.put("priceRequestNoCode", organization.getPriceRequestNoCode());
        map.put("requestStockCode", organization.getRequestStockCode());
        map.put("transferCode", organization.getTransferCode());
        map.put("transferReceiveCode", organization.getTransferReceiveCode());
        map.put("purchaseOrderCode", organization.getPurchaseOrderCode());
        map.put("purchaseOrderReceiptCode", organization.getPurchaseOrderReceiptCode());
        map.put("saleOrderCode", organization.getSaleOrderCode());
        map.put("expenseRequestCode", organization.getExpenseRequestCode());
        map.put("quotationCode", organization.getQuotationCode());
        map.put("invoiceCode", organization.getInvoiceCode());
        map.put("posCode", organization.getPosCode());
        map.put("invoiceReceiptCode", organization.getInvoiceReceiptCode());
        map.put("dnCode", organization.getDnCode());
        map.put("creditMemoCode", organization.getCreditMemoCode());
        map.put("creditReceiptCode", organization.getCreditReceiptCode());
        map.put("purchaseBillCode", organization.getPurchaseBillCode());
        map.put("purchaseBillReceiptCode", organization.getPurchaseBillReceiptCode());
        map.put("billReturnCode", organization.getBillReturnCode());
        map.put("billReceiptCode", organization.getBillReceiptCode());
        map.put("bomCode", organization.getBomCode());
        map.put("goodReceiptNoteCode", organization.getGoodReceiptNoteCode());
        map.put("landedCostCode", organization.getLandedCostCode());
        map.put("journalEntryCode", organization.getJournalEntryCode());
        map.put("receivePaymentCode", organization.getReceivePaymentCode());
        map.put("receivePaymentOrgCode", organization.getReceivePaymentOrgCode());
        map.put("receivePaymentEmpCode", organization.getReceivePaymentEmpCode());
        map.put("payBillCode", organization.getPayBillCode());
        map.put("payJournalCode", organization.getPayJournalCode());
        map.put("createdBy", userId);
        map.put("modifiedBy", organization.getModifiedBy());
        return map;
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

    private String getFreedomLocationName(Organization organization) {
        if (organization.getName() != null && !organization.getName().trim().isEmpty()) {
            return organization.getName().trim();
        }
        return String.valueOf(organization.getId());
    }

    private String resolveFreedomBranchAddress(Organization organization) {
        if (organization == null) {
            return null;
        }
        if (!isBlank(organization.getAddress())) {
            return organization.getAddress().trim();
        }

        Map<String, Object> addressParts = organizationMapper.getLocationAddressParts(
                organization.getStreetId(),
                organization.getProvinceId(),
                organization.getDistrictId(),
                organization.getCommuneId(),
                organization.getVillageId()
        );

        StringJoiner joiner = new StringJoiner(", ");
        appendAddressPart(joiner, organization.getHouseNo());
        appendAddressPart(joiner, getStringValue(addressParts, "streetName"));
        appendAddressPart(joiner, getStringValue(addressParts, "provinceName"));
        appendAddressPart(joiner, getStringValue(addressParts, "districtName"));
        appendAddressPart(joiner, getStringValue(addressParts, "communeName"));
        appendAddressPart(joiner, getStringValue(addressParts, "villageName"));

        String resolvedAddress = joiner.toString();
        return resolvedAddress.isEmpty() ? null : resolvedAddress;
    }

    private void applyFreedomBranchModuleCodes(OrganizationResponse response) {
        if (response == null || response.getId() == null) {
            return;
        }

        Map<String, Object> branchCodes = freedomMapper.getCustomerBranchModuleCodes(response.getId());
        if (branchCodes == null || branchCodes.isEmpty()) {
            return;
        }

        response.setAdjCode(getStringValue(branchCodes, "adjCode"));
        response.setProductPriceListCode(getStringValue(branchCodes, "productPriceListCode"));
        response.setPriceRequestNoCode(getStringValue(branchCodes, "priceRequestNoCode"));
        response.setRequestStockCode(getStringValue(branchCodes, "requestStockCode"));
        response.setTransferCode(getStringValue(branchCodes, "transferCode"));
        response.setTransferReceiveCode(getStringValue(branchCodes, "transferReceiveCode"));
        response.setPurchaseOrderCode(getStringValue(branchCodes, "purchaseOrderCode"));
        response.setPurchaseOrderReceiptCode(getStringValue(branchCodes, "purchaseOrderReceiptCode"));
        response.setSaleOrderCode(getStringValue(branchCodes, "saleOrderCode"));
        response.setExpenseRequestCode(getStringValue(branchCodes, "expenseRequestCode"));
        response.setQuotationCode(getStringValue(branchCodes, "quotationCode"));
        response.setInvoiceCode(getStringValue(branchCodes, "invoiceCode"));
        response.setPosCode(getStringValue(branchCodes, "posCode"));
        response.setInvoiceReceiptCode(getStringValue(branchCodes, "invoiceReceiptCode"));
        response.setDnCode(getStringValue(branchCodes, "dnCode"));
        response.setCreditMemoCode(getStringValue(branchCodes, "creditMemoCode"));
        response.setCreditReceiptCode(getStringValue(branchCodes, "creditReceiptCode"));
        response.setPurchaseBillCode(getStringValue(branchCodes, "purchaseBillCode"));
        response.setPurchaseBillReceiptCode(getStringValue(branchCodes, "purchaseBillReceiptCode"));
        response.setBillReturnCode(getStringValue(branchCodes, "billReturnCode"));
        response.setBillReceiptCode(getStringValue(branchCodes, "billReceiptCode"));
        response.setBomCode(getStringValue(branchCodes, "bomCode"));
        response.setGoodReceiptNoteCode(getStringValue(branchCodes, "goodReceiptNoteCode"));
        response.setLandedCostCode(getStringValue(branchCodes, "landedCostCode"));
        response.setJournalEntryCode(getStringValue(branchCodes, "journalEntryCode"));
        response.setReceivePaymentCode(getStringValue(branchCodes, "receivePaymentCode"));
        response.setReceivePaymentOrgCode(getStringValue(branchCodes, "receivePaymentOrgCode"));
        response.setReceivePaymentEmpCode(getStringValue(branchCodes, "receivePaymentEmpCode"));
        response.setPaybillsCode(getStringValue(branchCodes, "paybillsCode"));
        response.setPaybillsJournalCode(getStringValue(branchCodes, "paybillsJournalCode"));
    }

    private String getStringValue(Map<String, Object> values, String key) {
        if (values == null) {
            return null;
        }
        Object value = values.get(key);
        return value != null ? String.valueOf(value) : null;
    }

    private void appendAddressPart(StringJoiner joiner, String value) {
        if (!isBlank(value)) {
            joiner.add(value.trim());
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private Map<String, Object> toFreedomCustomerLocationGroupMap(Long customerId, Long locationGroupId) {
        Map<String, Object> map = new HashMap<>();
        map.put("customerId", customerId);
        map.put("locationGroupId", locationGroupId);
        return map;
    }

    private List<OrganizationDetailResponse> mapFreedomCustomersToOrganizationDetails(List<VendorDropdownResponse> freedomCustomers) {
        List<OrganizationDetailResponse> details = new ArrayList<>();
        if (freedomCustomers == null) {
            return details;
        }
        for (VendorDropdownResponse customer : freedomCustomers) {
            if (customer == null || customer.getId() == null) {
                continue;
            }
            OrganizationDetailResponse detail = new OrganizationDetailResponse();
            detail.setId(customer.getId());
            detail.setName(customer.getName());
            details.add(detail);
        }
        return details;
    }

    private Map<String, Object> toFreedomOrgRelationMap(Organization organization) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", organization.getId());
        map.put("companyId", organization.getCompanyId());
        map.put("organizationGroupId", organization.getOrganizationGroupId());
        map.put("organizationContactId", organization.getOrganizationContactId());
        map.put("customerContract", organization.getCustomerContract());
        map.put("customerContractUrl", organization.getCustomerContractUrl());
        map.put("customerTerminate", organization.getCustomerTerminate());
        map.put("customerTerminateUrl", organization.getCustomerTerminateUrl());
        map.put("customerSurvey", organization.getCustomerSurvey());
        map.put("customerSurveyUrl", organization.getCustomerSurveyUrl());
        return map;
    }

    private Map<String, Object> toFreedomDivisionMap(OrganizationDivision division) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", division.getId());
        map.put("organizationId", division.getOrganizationId());
        map.put("title", division.getTitle());
        map.put("makingProcess", division.getMakingProcess());
        map.put("negotiationId", division.getNegotiationId());
        return map;
    }
}
