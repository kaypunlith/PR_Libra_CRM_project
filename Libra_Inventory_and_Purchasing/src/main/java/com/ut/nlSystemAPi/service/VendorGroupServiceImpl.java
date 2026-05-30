package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.VendorGroupMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.VendorGroup;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.VendorGroup.VendorGroupRequest;
import com.ut.nlSystemAPi.model.request.Login.VendorGroup.VendorGroupUpdateRequest;
import com.ut.nlSystemAPi.model.response.VendorGroup.VendorGroupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class VendorGroupServiceImpl implements VendorGroupService {

    @Autowired
    private VendorGroupMapper vendorGroupMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(vendorGroupMapper.countList(filter));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<VendorGroupResponse> responses = vendorGroupMapper.getList(filter);

            if (responses.size() > 0) {
                for (int i = 0; i < responses.size(); i++) {
                    responses.get(i).setCompanies(vendorGroupMapper.getVgroupCompany(responses.get(i).getId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/list", null, null, "vendorManagement", "vendorManagement(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/list", line, error.toString(), "vendorManagement", "vendorManagement(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//      if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//      }

            List<VendorGroupResponse> responses = vendorGroupMapper.getOne(id);

            if (responses.size() > 0) {
                for (int i = 0; i < responses.size(); i++) {
                    responses.get(i).setCompanies(vendorGroupMapper.getVgroupCompany(responses.get(i).getId()));
                }

                for (int i = 0; i < responses.size(); i++) {
                    responses.get(i).setVendors(vendorGroupMapper.getVendorVgroup(responses.get(i).getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/find/{id}", null, null, "System vendorManagement", "System vendorManagement (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/find/{id}", line, error.toString(), "System vendorManagement", "System vendorManagement (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(VendorGroupRequest vendorGroupRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Employee (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            // Check Duplicate
            if(vendorGroupMapper.checkDuplicate(vendorGroupRequest.getName(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            // Check Data
            VendorGroup vendorGroup = new VendorGroup();
            vendorGroup.setTypeId(vendorGroupRequest.getTypeId());
            vendorGroup.setName(vendorGroupRequest.getName());
            vendorGroup.setCreatedBy(userId);
            vendorGroup.setIsActive(1);
            Boolean result = vendorGroupMapper.insert(vendorGroup);
            if (result) {
                List<Long> companies = vendorGroupRequest.getCompanies();
                if (!companies.isEmpty()) {
                    for (Long company : companies) {
                        VendorGroup vGroupCompany = new VendorGroup();
                        vGroupCompany.setCompanyId(company);
                        vGroupCompany.setId(vendorGroup.getId());
                        vendorGroupMapper.insertVgroupCompany(vGroupCompany);
                    }
                }

                List<Long> vendors = vendorGroupRequest.getVendors();
                if (!vendors.isEmpty()) {
                    for (Long vendor : vendors) {
                        VendorGroup vendorVgroup = new VendorGroup();
                        vendorVgroup.setVendorId(vendor);
                        vendorVgroup.setId(vendorGroup.getId());
                        vendorGroupMapper.insertVendorVgroup(vendorVgroup);
                    }
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/vendorManagement/add", null, null, "vendorManagement", "vendorManagement (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/add", line, error.toString(), "vendorManagement", "vendorManagement (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(VendorGroupUpdateRequest vendorGroupUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            // Check Data
            VendorGroup vendorGroup = new VendorGroup();
            vendorGroup.setId(vendorGroupUpdateRequest.getId());
            vendorGroup.setTypeId(vendorGroupUpdateRequest.getTypeId());
            vendorGroup.setName(vendorGroupUpdateRequest.getName());
            vendorGroup.setModifiedBy(userId);
            Boolean result = vendorGroupMapper.update(vendorGroup);
            if (result) {
                vendorGroupMapper.deleteVgroupCompany(vendorGroup.getId());
                List<Long> companies = vendorGroupUpdateRequest.getCompanies();
                if (!companies.isEmpty()) {
                    for (Long company : companies) {
                        VendorGroup vGroupCompany = new VendorGroup();
                        vGroupCompany.setCompanyId(company);
                        vGroupCompany.setId(vendorGroup.getId());
                        vendorGroupMapper.insertVgroupCompany(vGroupCompany);
                    }
                }

                vendorGroupMapper.deleteVendorVgroup(vendorGroup.getId());
                List<Long> vendors = vendorGroupUpdateRequest.getVendors();
                if (!vendors.isEmpty()) {
                    for (Long vendor : vendors) {
                        VendorGroup vendorVgroup = new VendorGroup();
                        vendorVgroup.setVendorId(vendor);
                        vendorVgroup.setId(vendorGroup.getId());
                        vendorGroupMapper.insertVendorVgroup(vendorVgroup);
                    }
                }

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/vendorManagement/update", null, null, "vendorManagement", "vendorManagement (Update)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/vendorManagement/update", line, error.toString(), "vendorManagement", "vendorManagement (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//      if (permissionMapper.checkPermission(userId, "System Role (Delete)") == 0) {
//        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//      }

            Boolean result = vendorGroupMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/vendorManagement/delete/{id}",null,null,"vendorManagement","vendorManagement (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/delete/{id}",line, error.toString(),"vendorManagement","vendorManagement (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}
