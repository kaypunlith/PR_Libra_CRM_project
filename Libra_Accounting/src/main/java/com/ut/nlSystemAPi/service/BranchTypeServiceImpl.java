package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.BranchTypeMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.BranchType;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.BranchType.BranchTypeRequest;
import com.ut.nlSystemAPi.model.request.Login.BranchType.BranchTypeUpdateRequest;
import com.ut.nlSystemAPi.model.response.BranchType.BranchTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class BranchTypeServiceImpl implements BranchTypeService {

    @Autowired
    private BranchTypeMapper branchTypeMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(branchTypeMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<BranchTypeResponse> branchTypeResponses = branchTypeMapper.getList(filter);


            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branchType/list", null, null, "branchType", " branchType (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", branchTypeResponses, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branchType/list", 1033L, error.toString(), "branchType", "branchType (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            List<BranchTypeResponse> branchTypeResponses = branchTypeMapper.getOne(id);

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branchType/find/" + id, null, null, "branchType", "branchType (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", branchTypeResponses, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branchType/find/" + id, 1033L, error.toString(), "branchType", "branchType (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(BranchTypeRequest branchTypeRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            // Check for duplicate name
            System.out.println(branchTypeRequest);
            if (branchTypeMapper.checkDuplicate(branchTypeRequest.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate branch type  Name", false));
            }

            BranchType branchType = new BranchType();
            branchType.setName(branchTypeRequest.getName());
            branchType.setCreatedBy(userId);
            branchType.setIsActive(1);
            System.out.println(branchType);
            boolean result = branchTypeMapper.insert(branchType);

            System.out.println("result: " + result);

            if (result) {
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/branchType/add", null, null, "branchType", "branchType (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            // System Activity for Error
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branchType/add", 1033L, error.toString(), "branchType", "branchType (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(BranchTypeUpdateRequest branchTypeUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (Edit)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
//            // Check Duplicate name
            if (branchTypeMapper.checkDuplicate(branchTypeUpdateRequest.getName(), branchTypeUpdateRequest.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate name of  company", false));
            }

            BranchType branchType  = new BranchType();
            branchType.setId(branchTypeUpdateRequest.getId());
            branchType.setName(branchTypeUpdateRequest.getName());
            branchType.setModifiedBy(userId);
            branchType.setIsActive(1);
            boolean result = branchTypeMapper.update(branchType);
            if (result) {
                // System Activity
                LocalTime endDuration = LocalTime.now();
                 activityLogService.insert("/branchType/update", null, null, "branchType", "branchType (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
            } else {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/branchType/update", 1033L, "Failed to update branchType", "branchType", "branchType (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail to update branchType", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branchType/update", 1033L, error.toString(), "branchType", "branchType (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error occurred during update", false));
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (Delete)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Boolean result = branchTypeMapper.delete(id, userId);
            LocalTime endDuration = LocalTime.now();

            // System Activity
            activityLogService.insert("/branchType/delete/{id}", null, null, "branchType", "branchType (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);

            if (result) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (RuntimeException error) {
            // System Activity for Error
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branchType/delete/{id}", 1033L, error.toString(), "branchType", "branchType (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}
