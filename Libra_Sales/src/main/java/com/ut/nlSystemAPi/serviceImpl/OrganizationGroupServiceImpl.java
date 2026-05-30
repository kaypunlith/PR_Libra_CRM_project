package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.freedom.FreedomMapper;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.OrganizationGroup.OrganizationGroup;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.request.OrganizationGroup.OrganizationGroupRequest;
import com.ut.nlSystemAPi.model.request.OrganizationGroup.OrganizationGroupUpdateRequest;
import com.ut.nlSystemAPi.model.response.OrganizationGroup.OrganizationGroupResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.UserService;
import com.ut.nlSystemAPi.service.OrganizationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationGroupServiceImpl implements OrganizationGroupService {

    @Autowired
    private OrganizationGroupMapper organizationGroupMapper;

    @Autowired
    private FreedomMapper freedomMapper;

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

    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer Group (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(organizationGroupMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<OrganizationGroupResponse> responses = organizationGroupMapper.getList(filter, userId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-group/list", null, null, "Customer Group", "Customer Group (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-group/list", line, error.toString(), "Customer Group", "Customer Group (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer Group (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<OrganizationGroupResponse> responses = organizationGroupMapper.getOne(id);

            if (!responses.isEmpty()) {
                for (OrganizationGroupResponse response : responses) {
                    response.setCompany(organizationGroupMapper.getCompany(response.getId()));

                    response.setEmployeeGroup(organizationGroupMapper.getEmployeeGroup(response.getId()));

                    response.setPriceType(organizationGroupMapper.getPriceType(response.getId()));

                    response.setOrganization(organizationGroupMapper.getOrganization(response.getId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-group/find/{id}", null, null, "Customer Group", "Customer Group (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-group/find/{id}", line, error.toString(), "Customer Group", "Customer Group (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(OrganizationGroupRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer Group (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if (organizationGroupMapper.checkDuplicate(request.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            // Check Data
            OrganizationGroup organizationGroup = new OrganizationGroup();
            organizationGroup.setName(request.getName());
            organizationGroup.setCreatedBy(userId);
            organizationGroup.setIsActive(1);

            Boolean result = organizationGroupMapper.insert(organizationGroup);

            if (result) {
                freedomMapper.insertCustomerGroup(toFreedomCustomerGroupMap(organizationGroup));
                if (request.getCompany() != null) {
                    for (Long company : request.getCompany()) {
                        organizationGroupMapper.insertCompany(company, organizationGroup.getId());
                        freedomMapper.insertCgroupCompany(toFreedomCgroupCompanyMap(company, organizationGroup.getId()));
                    }
                }

                if (request.getEmployeeGroup() != null) {
                    for (Long employeeGroup : request.getEmployeeGroup()) {
                        organizationGroupMapper.insertEmployeeGroup(employeeGroup, organizationGroup.getId());
                        freedomMapper.insertCgroupEgroup(toFreedomCgroupEgroupMap(employeeGroup, organizationGroup.getId()));
                    }
                }

                if (request.getPriceType() != null) {
                    for (Long priceType : request.getPriceType()) {
                        organizationGroupMapper.insertPriceType(priceType, organizationGroup.getId());
                        freedomMapper.insertCgroupPriceType(toFreedomCgroupPriceTypeMap(priceType, organizationGroup.getId()));
                    }
                }

                if (request.getOrganization() != null) {
                    for (Long organization : request.getOrganization()) {
                        organizationGroupMapper.insertOrganization(organization, organizationGroup.getId());
                        freedomMapper.insertCustomerCgroup(toFreedomCustomerCgroupMap(organization, organizationGroup.getId()));
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/organization-group/add", null, null, "Customer Group", "Customer Group (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-group/add", line, error.toString(), "Customer Group", "Customer Group (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(OrganizationGroupUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer Group (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if (organizationGroupMapper.checkDuplicate(request.getName(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            // Check Data
            OrganizationGroup organizationGroup = new OrganizationGroup();
            organizationGroup.setId(request.getId());
            organizationGroup.setName(request.getName());
            organizationGroup.setModifiedBy(userId);
            Boolean result = organizationGroupMapper.update(organizationGroup);

            if (result) {
                freedomMapper.updateCustomerGroup(toFreedomCustomerGroupMap(organizationGroup));
                if (request.getCompany() != null) {
                    organizationGroupMapper.deleteCompany(organizationGroup.getId());
                    freedomMapper.deleteCgroupCompanies(organizationGroup.getId());
                    for (Long company : request.getCompany()) {
                        organizationGroupMapper.insertCompany(company, organizationGroup.getId());
                        freedomMapper.insertCgroupCompany(toFreedomCgroupCompanyMap(company, organizationGroup.getId()));
                    }
                }

                if (request.getEmployeeGroup() != null) {
                    organizationGroupMapper.deleteEmployeeGroup(organizationGroup.getId());
                    freedomMapper.deleteCgroupEgroups(organizationGroup.getId());
                    for (Long employeeGroup : request.getEmployeeGroup()) {
                        organizationGroupMapper.insertEmployeeGroup(employeeGroup, organizationGroup.getId());
                        freedomMapper.insertCgroupEgroup(toFreedomCgroupEgroupMap(employeeGroup, organizationGroup.getId()));
                    }
                }

                if (request.getPriceType() != null) {
                    organizationGroupMapper.deletePriceType(organizationGroup.getId());
                    freedomMapper.deleteCgroupPriceTypes(organizationGroup.getId());
                    for (Long priceType : request.getPriceType()) {
                        organizationGroupMapper.insertPriceType(priceType, organizationGroup.getId());
                        freedomMapper.insertCgroupPriceType(toFreedomCgroupPriceTypeMap(priceType, organizationGroup.getId()));
                    }
                }

                if (request.getOrganization() != null) {
                    organizationGroupMapper.deleteOrganization(organizationGroup.getId());
                    freedomMapper.deleteCustomerCgroupsByCgroup(organizationGroup.getId());
                    for (Long organization : request.getOrganization()) {
                        organizationGroupMapper.insertOrganization(organization, organizationGroup.getId());
                        freedomMapper.insertCustomerCgroup(toFreedomCustomerCgroupMap(organization, organizationGroup.getId()));
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/organization-group/update", null, null, "Customer Group", "Customer Group (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-group/update", line, error.toString(), "Customer Group", "Customer Group (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer Group (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = organizationGroupMapper.delete(id, userId);

            if (result) {
                freedomMapper.deleteCustomerGroup(id, userId);
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/organization-group/delete/{id}", null, null, "Customer Group", "Customer Group (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/organization-group/delete/{id}", line, error.toString(), "Customer Group", "Customer Group (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private Map<String, Object> toFreedomCustomerGroupMap(OrganizationGroup organizationGroup) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", organizationGroup.getId());
        map.put("name", organizationGroup.getName());
        map.put("createdBy", organizationGroup.getCreatedBy());
        map.put("modifiedBy", organizationGroup.getModifiedBy());
        map.put("isActive", organizationGroup.getIsActive());
        return map;
    }

    private Map<String, Object> toFreedomCgroupCompanyMap(Long companyId, Long organizationGroupId) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("organizationGroupId", organizationGroupId);
        return map;
    }

    private Map<String, Object> toFreedomCgroupEgroupMap(Long employeeGroupId, Long organizationGroupId) {
        Map<String, Object> map = new HashMap<>();
        map.put("employeeGroupId", employeeGroupId);
        map.put("organizationGroupId", organizationGroupId);
        return map;
    }

    private Map<String, Object> toFreedomCgroupPriceTypeMap(Long priceTypeId, Long organizationGroupId) {
        Map<String, Object> map = new HashMap<>();
        map.put("priceTypeId", priceTypeId);
        map.put("organizationGroupId", organizationGroupId);
        return map;
    }

    private Map<String, Object> toFreedomCustomerCgroupMap(Long organizationId, Long organizationGroupId) {
        Map<String, Object> map = new HashMap<>();
        map.put("organizationId", organizationId);
        map.put("organizationGroupId", organizationGroupId);
        return map;
    }
}
