package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateStringsAndNumbers;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.DepartmentMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.Department;
import com.ut.nlSystemAPi.model.DepartmentDetail;
import com.ut.nlSystemAPi.model.DepartmentUser;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.DepartmentRequest;
import com.ut.nlSystemAPi.model.request.DepartmentUpdateRequest;
import com.ut.nlSystemAPi.model.request.LocationPointRequest;
import com.ut.nlSystemAPi.model.response.DepartmentResponse;
import com.ut.nlSystemAPi.model.response.GroupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private PermissionMapper permissionMapper;

    public ResponseMessage<BaseResult> getList(Filter filter) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();

        if (permissionMapper.checkPermission(userId, "Department (View)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        Pagination pagination = new Pagination();
        pagination.setPage(filter.getPage());
        pagination.setRowsPerPage(filter.getRowsPerPage());

        filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

        List<DepartmentResponse> departmentResponseList = departmentMapper.getList(filter, userId);

        if (departmentResponseList != null && !departmentResponseList.isEmpty()) {
            for (DepartmentResponse departmentResponse : departmentResponseList) {
                departmentResponse.setApplyUsers(departmentMapper.listUser(departmentResponse.getId()));
                departmentResponse.setPaths(this.departmentMapper.listDepartmentDetails(departmentResponse.getId()));
            }
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", departmentResponseList, true));
    }

    public ResponseMessage<BaseResult> getOne(Long id) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId, "Department (View)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }
        List<DepartmentResponse> departmentResponses = departmentMapper.getOne(id);
        if (departmentResponses != null && !departmentResponses.isEmpty()) {
            for (DepartmentResponse departmentResponse : departmentResponses) {
                departmentResponse.setApplyUsers(departmentMapper.listUser(departmentResponse.getId()));
                departmentResponse.setPaths(this.departmentMapper.listDepartmentDetails(departmentResponse.getId()));
            }
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", departmentResponses, true));
    }

    public ResponseMessage<BaseResult> insert(DepartmentRequest departmentRequest) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId, "Department (Add)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        // Check Duplicate
        if (departmentMapper.checkDuplicate(departmentRequest.getName(), null) > 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate", false));
        }

        // Check Data
        Department department = new Department();
        department.setSettingGroupId(departmentRequest.getSettingGroupId());
        department.setName(departmentRequest.getName());
        department.setProvinceId(departmentRequest.getProvinceId());
        department.setDescription(departmentRequest.getDescription());
        department.setLongs(departmentRequest.getLongs());
        department.setLats(departmentRequest.getLats());
        department.setRadius(departmentRequest.getRadius());
        department.setCreatedBy(userId);
        department.setIsActive(1);
        department.setCode(GenerateStringsAndNumbers.generateRandomString(10));
        if (departmentRequest.getApplyUsers() == null || departmentRequest.getApplyUsers().isEmpty()) {
            department.setUserApply(0L);
        } else {
            department.setUserApply(1L);
        }

        Boolean result = departmentMapper.insert(department);
        if (result) {

            if (departmentRequest.getApplyUsers() != null) {
                insertDepartmentUser(department.getId(), departmentRequest.getApplyUsers());
            }

            if (departmentRequest.getPaths() != null && !departmentRequest.getPaths().isEmpty()) {
                for (LocationPointRequest point : departmentRequest.getPaths()) {
                    DepartmentDetail deptDetail = new DepartmentDetail();
                    deptDetail.setIsActive(1);
                    deptDetail.setDepartmentId(department.getId());
                    deptDetail.setLats(point.getLats());
                    deptDetail.setLongs(point.getLongs());
                    deptDetail.setCreatedBy(userId);
                    Boolean resultDeptDetail = departmentMapper.insertDepartmentDetail(deptDetail);
                    if (!resultDeptDetail) {
                        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
                    }
                }
            }

            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    private void insertDepartmentUser(Long departmentId, List<Long> departmentUserList) {
        if (departmentUserList != null) {
            departmentMapper.deleteDepartmentUser(departmentId);
            for (Long departmentUser : departmentUserList) {
                departmentMapper.insertDepartmentUser(departmentId, departmentUser);
            }
        }
    }

    public ResponseMessage<BaseResult> update(DepartmentUpdateRequest departmentUpdateRequest) {
        // Check Permission
        Long userId = userService.getUserAuth().getId();
        if (permissionMapper.checkPermission(userId, "Department (Edit)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        // Check Data
        Department department = new Department();
        department.setId(departmentUpdateRequest.getId());
        department.setSettingGroupId(departmentUpdateRequest.getSettingGroupId());
        department.setName(departmentUpdateRequest.getName());
        department.setProvinceId(departmentUpdateRequest.getProvinceId());
        department.setLats(departmentUpdateRequest.getLats());
        department.setLongs(departmentUpdateRequest.getLongs());
        department.setDescription(departmentUpdateRequest.getDescription());
        department.setRadius(departmentUpdateRequest.getRadius());
        department.setModifiedBy(userId);
        department.setIsActive(1);

        if (departmentUpdateRequest.getApplyUsers() == null || departmentUpdateRequest.getApplyUsers().isEmpty()) {
            department.setUserApply(0L);
        } else {
            department.setUserApply(1L);
        }

        Boolean result = departmentMapper.update(department);
        if (result) {
            if (departmentUpdateRequest.getApplyUsers() != null) {
                Department departments = departmentMapper.getDepartmentId(departmentUpdateRequest.getName());
                insertDepartmentUser(departments.getId(), departmentUpdateRequest.getApplyUsers());
            }

            departmentMapper.deleteDepartmentDetails(department.getId());

            if (departmentUpdateRequest.getPaths() != null && !departmentUpdateRequest.getPaths().isEmpty()) {
                for (LocationPointRequest point : departmentUpdateRequest.getPaths()) {
                    DepartmentDetail deptDetail = new DepartmentDetail();
                    deptDetail.setIsActive(1);
                    deptDetail.setDepartmentId(department.getId());
                    deptDetail.setLats(point.getLats());
                    deptDetail.setLongs(point.getLongs());
                    deptDetail.setCreatedBy(userId);
                    Boolean resultDeptDetail = departmentMapper.insertDepartmentDetail(deptDetail);
                    if (!resultDeptDetail) {
                        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
                    }
                }
            }

            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id) {

        Long userId = userService.getUserAuth().getId();

        if (permissionMapper.checkPermission(userId, "Department (Delete)") == 0) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        Boolean result = departmentMapper.delete(id, userId);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> getDepartmentList() {
        // Check Permission
        Long userId = userService.getUserAuth().getId();

        List<GroupResponse> groupResponses = departmentMapper.getGroupList(userId);
        if (groupResponses != null && !groupResponses.isEmpty()) {
            for (GroupResponse groupResponse : groupResponses) {
                groupResponse
                        .setDepartmentList(departmentMapper.getDepartmentList(groupResponse.getId(), userId));
            }
        }
        return ResponseMessageUtils.makeResponse(true,
                messageService.message("Success", groupResponses, true));
    }
}
