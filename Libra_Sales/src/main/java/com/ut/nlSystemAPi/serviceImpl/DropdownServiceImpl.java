package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.DropdownMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.filter.Dropdown.*;
import com.ut.nlSystemAPi.model.filter.TermConditionFilter;
import com.ut.nlSystemAPi.model.response.Dropdown.*;
import com.ut.nlSystemAPi.model.response.Dropdown.DropdownResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.DropdownService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DropdownServiceImpl implements DropdownService {

    @Autowired
    private DropdownMapper dropdownMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getListModuleType(DropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1003L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListModuleType(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module-type/list", null, null, "ModuleType", "ModuleType (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module-type/list", line, error.toString(), "ModuleType", "ModuleType (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListTermCondition(TermConditionFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2001L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListTermCondition(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition/list", null, null, "TermCondition", "TermCondition (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition/list", line, error.toString(), "TermCondition", "TermCondition (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListActivityStatus(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2001L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListActivityStatus(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/activity-status/list", null, null, "Activity Status", "TermCondition (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/activity-status/list", line, error.toString(), "TermCondition", "TermCondition (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListTermConditionType(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2002L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListTermConditionType(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition-type/list", null, null, "TermConditionType", "TermConditionType (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition-type/list", line, error.toString(), "TermConditionType", "TermConditionType (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOrganization(OrganizationDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2003L;
        try {
            Long userId = userService.getUserAuth().getId();
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListOrganization(filter, userId);


            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/list", null, null, "Organization", "Organization (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/list", line, error.toString(), "Organization", "Organization (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListLead(OrganizationDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2003L;
        try {
            Long userId = userService.getUserAuth().getId();
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListLead(filter, userId);


            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/list", null, null, "Organization", "Organization (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization/list", line, error.toString(), "Organization", "Organization (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOrganizationContact(CustomerContactDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2004L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListOrganizationContact(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact/list", null, null, "OrganizationContact", "OrganizationContact (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact/list", line, error.toString(), "OrganizationContact", "OrganizationContact (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCustomerContact(CustomerContactDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2005L;
        try {
            Long userId = userService.getUserAuth().getId();
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListCustomerContact(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/customer-contact/list", null, null, "CustomerContact", "CustomerContact (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/customer-contact/list", line, error.toString(), "CustomerContact", "CustomerContact (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListEmployeeGroup(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2005L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListEmployeeGroup(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee-group/list", null, null, "EmployeeGroup", "EmployeeGroup (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee-group/list", line, error.toString(), "EmployeeGroup", "EmployeeGroup (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListTypeOfNetwork(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2006L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListTypeOfNetwork(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/type-of-network/list", null, null, "TypeOfNetwork", "TypeOfNetwork (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/type-of-network/list", line, error.toString(), "TypeOfNetwork", "TypeOfNetwork (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListPartnerManagementPosition(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2007L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListPartnerManagementPosition(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/partner-management-position/list", null, null, "PartnerManagementPosition", "PartnerManagementPosition (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/partner-management-position/list", line, error.toString(), "PartnerManagementPosition", "PartnerManagementPosition (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListPartnerManagementIndustry(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2008L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListPartnerManagementIndustry(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/partner-management-industry/list", null, null, "PartnerManagementIndustry", "PartnerManagementIndustry (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/partner-management-industry/list", line, error.toString(), "PartnerManagementIndustry", "PartnerManagementIndustry (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListClassPartnerManagement(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2009L;
        try {
            Long userId = userService.getUserAuth().getId();
            System.out.println("User "+userId);
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListClassPartnerManagement(filter,userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-partner-management/list", null, null, "ClassPartnerManagement", "ClassPartnerManagement (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-partner-management/list", line, error.toString(), "ClassPartnerManagement", "ClassPartnerManagement (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSupportTicketPipLine(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2009L;
        try {
            Long userId = userService.getUserAuth().getId();
            System.out.println("User "+userId);
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListSupportTicketPipLine(filter,userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-partner-management/list", null, null, "ClassPartnerManagement", "ClassPartnerManagement (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-partner-management/list", line, error.toString(), "ClassPartnerManagement", "ClassPartnerManagement (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOrganizationGroup(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2005L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListOrganizationGroup(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-group/list", null, null, "OrganizationGroup", "OrganizationGroup (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-group/list", line, error.toString(), "OrganizationGroup", "OrganizationGroup (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListLeadGroup(LeadGroupDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2006L;
        try {
            Long userId = userService.getUserAuth().getId();
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListLeadGroup(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-group/list", null, null, "LeadGroup", "LeadGroup (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/lead-group/list", line, error.toString(), "LeadGroup", "LeadGroup (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListZone(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2006L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListZone(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/zone/list", null, null, "Zone", "Zone (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/zone/list", line, error.toString(), "Zone", "Zone (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListMarket(MarketDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2007L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListMarket(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/market/list", null, null, "Customer Market", "Customer Market (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/market/list", line, error.toString(), "Customer Market", "Customer Market (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListBOQ(BOQDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2008L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<BOQDropdownResponse> responses = dropdownMapper.getListBOQ(filter);
            if (!responses.isEmpty()) {
                for (BOQDropdownResponse response : responses) {
                    response.setDetails(dropdownMapper.getListBOQDetail(response.getId()));
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/boq/list", null, null, "BOQ", "BOQ (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/boq/list", line, error.toString(), "BOQ", "BOQ (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListBOM(BOMDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2008L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<BOQDropdownResponse> responses = dropdownMapper.getListBOM(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/boq/list", null, null, "BOQ", "BOQ (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/boq/list", line, error.toString(), "BOQ", "BOQ (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListProjectEstimationTerm(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2010L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ProjectEstimationTermResponse> responses = dropdownMapper.getListProjectEstimationTerm(filter);
            if (!responses.isEmpty()) {
                for (ProjectEstimationTermResponse response : responses) {
                    List<ProjectEstimationTermDetailResponse> details = dropdownMapper.getListProjectEstimationTermDetail(response.getId());
                    if (details != null && !details.isEmpty()) {
                        response.setDetails(details);
                    } else {
                        response.setDetails(null);
                    }
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation-term/list", null, null, "VatSetting", "VatSetting (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation-term/list", line, error.toString(), "VatSetting", "VatSetting (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListQuotation(QuotationDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2011L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<QuotationDropdownResponse> responses = dropdownMapper.getListQuotation(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/list", null, null, "Quotation", "Quotation (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/list", line, error.toString(), "Quotation", "Quotation (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOpportunityQuotation(QuotationDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2011L;
        try {
            Long userId = userService.getUserAuth().getId();
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<QuotationDropdownResponse> responses = dropdownMapper.getListOpportunityQuotation(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/dropdown/list-opportunity-quotation", null, null, "Opportunity Quotation", "Opportunity Quotation (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/dropdown/list-opportunity-quotation", line, error.toString(), "Opportunity Quotation", "Opportunity Quotation (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOpportunitySaleOrder(QuotationDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2011L;
        try {
            Long userId = userService.getUserAuth().getId();
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<QuotationDropdownResponse> responses = dropdownMapper.getListOpportunitySaleOrder(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/dropdown/list-opportunity-sale-order", null, null, "Opportunity Sale Order", "Opportunity Sale Order (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/dropdown/list-opportunity-sale-order", line, error.toString(), "Opportunity Sale Order", "Opportunity Sale Order (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListActivityCardQuotation(QuotationDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2011L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<QuotationDropdownResponse> responses = dropdownMapper.getListActivityCardQuotation(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/dropdown/list-activity-card-quotation", null, null, "Activity Card", "Activity Card (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/dropdown/list-activity-card-quotation", line, error.toString(), "Activity Card", "Activity Card (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListTransferOrder(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3002L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListTransferOrder(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/transfer-order/list", null, null, "TransferOrder", "TransferOrder (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/transfer-order/list", line, error.toString(), "TransferOrder", "TransferOrder (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSalesInvoice(SalesInvoiceDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3003L;
        try {
            Long userId = userService.getUserAuth().getId();
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SalesInvoiceDropdownResponse> responses = dropdownMapper.getListSalesInvoice(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-detail/list", null, null, "SalesInvoice", "SalesInvoice (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-detail/list", line, error.toString(), "SalesInvoice", "SalesInvoice (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCMTerm(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3004L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListCMTerm(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/cm-term/list", null, null, "CMTerm", "CMTerm (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/cm-term/list", line, error.toString(), "CMTerm", "CMTerm (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListBusinessActivity(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3005L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListBusinessActivity(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/business-activity/list", null, null, "BusinessActivity", "BusinessActivity (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/business-activity/list", line, error.toString(), "BusinessActivity", "BusinessActivity (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListBusinessType(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3006L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListBusinessType(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/business-type/list", null, null, "BusinessType", "BusinessType (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/business-type/list", line, error.toString(), "BusinessType", "BusinessType (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListKeyNegotiationIssue(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3007L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListKeyNegotiationIssue(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/key-negotiation-issue/list", null, null, "KeyNegotiationIssue", "KeyNegotiationIssue (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/key-negotiation-issue/list", line, error.toString(), "KeyNegotiationIssue", "KeyNegotiationIssue (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOpportunityStage(OpportunityStageDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3008L;
        try {
            Long userId = userService.getUserAuth().getId();
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListOpportunityStage(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage/list", null, null, "OpportunityStage", "OpportunityStage (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage/list", line, error.toString(), "OpportunityStage", "OpportunityStage (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOpportunityStageProbability(OpportunityStageDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3008L;
        try {
            Long userId = userService.getUserAuth().getId();
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListOpportunityStageProbability(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage/list", null, null, "OpportunityStage", "OpportunityStage (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-stage/list", line, error.toString(), "OpportunityStage", "OpportunityStage (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOpportunitySource(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3009L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListOpportunitySource(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-source/list", null, null, "OpportunitySource", "OpportunitySource (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-source/list", line, error.toString(), "OpportunitySource", "OpportunitySource (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSource(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3009L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListSource(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/source/list", null, null, "Source", "Source (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/source/list", line, error.toString(), "Source", "Source (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOpportunityActivity(OpportunityStageDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3010L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListOpportunityActivity(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-activity/list", null, null, "OpportunityActivity", "OpportunityActivity (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/opportunity-activity/list", line, error.toString(), "OpportunityActivity", "OpportunityActivity (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSupportTicketActivity(SupportStageDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3011L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListSupportTicketActivity(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket-activity/list", null, null, "SupportTicketActivity", "SupportTicketActivity (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket-activity/list", line, error.toString(), "SupportTicketActivity", "SupportTicketActivity (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSupportTicketBaseOn(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3012L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListSupportTicketBaseOn(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket-base-on/list", null, null, "SupportTicketBaseOn", "SupportTicketBaseOn (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/support-ticket-base-on/list", line, error.toString(), "SupportTicketBaseOn", "SupportTicketBaseOn (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCountry(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3008L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListCountry(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/country/list", null, null, "Country", "Country (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/country/list", line, error.toString(), "Country", "Country (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListStreet(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3009L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListStreet(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/street/list", null, null, "Street", "Street (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/street/list", line, error.toString(), "Street", "Street (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListProvince(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3010L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListProvince(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/province/list", null, null, "Province", "Province (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/province/list", line, error.toString(), "Province", "Province (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListDistrict(DistrictDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3011L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListDistrict(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/district/list", null, null, "District", "District (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/district/list", line, error.toString(), "District", "District (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCommune(CommuneDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3012L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListCommune(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/commune/list", null, null, "Commune", "Commune (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/commune/list", line, error.toString(), "Commune", "Commune (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListVillage(VillageDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3013L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListVillage(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/village/list", null, null, "Village", "Village (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/village/list", line, error.toString(), "Village", "Village (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOrganizationContactFamily(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3014L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListOrganizationContactFamily(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact-family/list", null, null, "OrganizationContactFamily", "OrganizationContactFamily (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact-family/list", line, error.toString(), "OrganizationContactFamily", "OrganizationContactFamily (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOrganizationContactLocation(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3015L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListOrganizationContactLocation(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact-location/list", null, null, "OrganizationContactLocation", "OrganizationContactLocation (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact-location/list", line, error.toString(), "OrganizationContactLocation", "OrganizationContactLocation (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOrganizationContactCharacter(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3015L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListOrganizationContactCharacter(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact-location/list", null, null, "OrganizationContactLocation", "OrganizationContactLocation (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact-location/list", line, error.toString(), "OrganizationContactLocation", "OrganizationContactLocation (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    
    @Override
    public ResponseMessage<BaseResult> getListOrganizationContactProgress(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3017L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> flatResponses = dropdownMapper.getListOrganizationContactProgress(filter);
            Map<String, List<DropdownResponse>> groupedMap = flatResponses.stream()
                    .collect(Collectors.groupingBy(
                            detail -> detail.getTypeName() != null ? detail.getTypeName() : "",
                            LinkedHashMap::new,
                            Collectors.toList()
                    ));

            List<DropdownResponse> responses = groupedMap.entrySet().stream()
                    .map(entry -> {
                        DropdownResponse response = new DropdownResponse();
                        response.setTypeName(entry.getKey());

                        if (!entry.getValue().isEmpty()) {
                            response.setTypeId(entry.getValue().get(0).getTypeId());
                        }
                        entry.getValue().forEach(detail -> {
                            detail.setTypeId(null);
                            detail.setTypeName(null);
                        });
                        response.setDetails(entry.getValue());
                        return response;
                    })
                    .collect(Collectors.toList());

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact-progress/list", null, null, "OrganizationContactProgress", "OrganizationContactProgress (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact-progress/list", line, error.toString(), "OrganizationContactProgress", "OrganizationContactProgress (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListOrganizationContactBrand(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3018L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> flatResponses = dropdownMapper.getListOrganizationContactBrand(filter);

            Map<String, List<DropdownResponse>> groupedMap = flatResponses.stream()
                    .collect(Collectors.groupingBy(
                            invoice -> (invoice.getTypeId() != null ? invoice.getTypeId().toString() : "0") +
                                    "|" +
                                    (invoice.getTypeName() != null ? invoice.getTypeName() : "") +
                                    "|" +
                                    (invoice.getDescription() != null ? invoice.getDescription() : ""),
                            LinkedHashMap::new,
                            Collectors.toList()
                    ));

            flatResponses = groupedMap.entrySet().stream()
                    .map(entry -> {
                        String[] keys = entry.getKey().split("\\|");
                        DropdownResponse response = new DropdownResponse();

                        response.setTypeId(keys.length > 0 && !keys[0].equals("0")
                                ? Long.valueOf(keys[0]) : null);

                        response.setTypeName(keys.length > 1 ? keys[1] : null);
                        response.setDescription(keys.length > 2 ? keys[2] : null);

                        entry.getValue().forEach(invoice -> {
                            invoice.setTypeId(null);
                            invoice.setTypeName(null);
                            invoice.setDescription(null);
                        });

                        response.setDetails(entry.getValue());
                        return response;
                    })
                    .collect(Collectors.toList());

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact-brand/list", null, null, "OrganizationContactBrand", "OrganizationContactBrand (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", flatResponses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact-brand/list", line, error.toString(), "OrganizationContactBrand", "OrganizationContactBrand (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    
    @Override
    public ResponseMessage<BaseResult> getListOrganizationContactDescriptionType(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3021L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListOrganizationContactDescriptionType(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact-description-type/list", null, null, "OrganizationContactDescriptionType", "OrganizationContactDescriptionType (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-contact-description-type/list", line, error.toString(), "OrganizationContactDescriptionType", "OrganizationContactDescriptionType (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListSalesTargetPeriod(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3022L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListSalesTargetPeriod(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-target-period/list", null, null, "SalesTargetPeriod", "SalesTargetPeriod (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-target-period/list", line, error.toString(), "SalesTargetPeriod", "SalesTargetPeriod (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCompetitorBusinessType(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3023L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListCompetitorBusinessType(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-business-type/list", null, null, "CompetitorBusinessType", "CompetitorBusinessType (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-business-type/list", line, error.toString(), "CompetitorBusinessType", "CompetitorBusinessType (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCompetitorPriority(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3024L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListCompetitorPriority(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-priority/list", null, null, "CompetitorPriority", "CompetitorPriority (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-priority/list", line, error.toString(), "CompetitorPriority", "CompetitorPriority (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCompetitorEmployeeAmount(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3025L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListCompetitorEmployeeAmount(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-employee-amount/list", null, null, "CompetitorEmployeeAmount", "CompetitorEmployeeAmount (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-employee-amount/list", line, error.toString(), "CompetitorEmployeeAmount", "CompetitorEmployeeAmount (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCompetitorMainProduct(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3026L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListCompetitorMainProduct(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-main-product/list", null, null, "CompetitorMainProduct", "CompetitorMainProduct (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-main-product/list", line, error.toString(), "CompetitorMainProduct", "CompetitorMainProduct (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCompetitorStrength(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3027L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListCompetitorStrength(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-strength/list", null, null, "CompetitorStrength", "CompetitorStrength (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-strength/list", line, error.toString(), "CompetitorStrength", "CompetitorStrength (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCompetitorWeakness(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3028L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListCompetitorWeakness(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-weakness/list", null, null, "CompetitorWeakness", "CompetitorWeakness (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-weakness/list", line, error.toString(), "CompetitorWeakness", "CompetitorWeakness (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCompetitorStage(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3029L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListCompetitorStage(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-stage/list", null, null, "CompetitorStage", "CompetitorStage (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-stage/list", line, error.toString(), "CompetitorStage", "CompetitorStage (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCompetitorStatus(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3030L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListCompetitorStatus(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-status/list", null, null, "CompetitorStatus", "CompetitorStatus (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-status/list", line, error.toString(), "CompetitorStatus", "CompetitorStatus (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListQuotationDivision(QuotationDivisionDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3030L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListQuotationDivision(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation-status/list", null, null, "Quotation Status", "Quotation Status (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation-status/list", line, error.toString(), "Quotation Status", "Quotation Status (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListDivision(QuotationDivisionDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        return getListQuotationDivision(filter, httpServletRequest);
    }

    @Override
    public ResponseMessage<BaseResult> getListQuotationStatus(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3030L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListQuotationStatus(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation-status/list", null, null, "Quotation Status", "Quotation Status (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation-status/list", line, error.toString(), "Quotation Status", "Quotation Status (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListQuotationStatusReason(QuotationStatusReasonDropdownFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3030L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListQuotationStatusReason(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation-status/list", null, null, "Quotation Status", "Quotation Status (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation-status/list", line, error.toString(), "Quotation Status", "Quotation Status (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListYears(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 3030L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<String> responses = dropdownMapper.getListYears(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-status/list", null, null, "CompetitorStatus", "CompetitorStatus (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor-status/list", line, error.toString(), "CompetitorStatus", "CompetitorStatus (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> getListServiceShift(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 2003L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DropdownResponse> responses = dropdownMapper.getListServiceShift(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/service-shift/list", null, null, "ServiceShift", "ServiceShift (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/service-shift/list", line, error.toString(), "ServiceShift", "ServiceShift (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}
