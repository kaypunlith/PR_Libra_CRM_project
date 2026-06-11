package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.CrmCategoryMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.CrmCategory.CrmCategory;
import com.ut.nlSystemAPi.model.request.CrmCategory.CrmCategoryRequest;
import com.ut.nlSystemAPi.model.request.CrmCategory.CrmCategoryUpdateRequest;
import com.ut.nlSystemAPi.model.response.CrmCategory.CrmCategoryResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.CrmCategoryService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class CrmCategoryServiceImpl implements CrmCategoryService {

    @Autowired
    private CrmCategoryMapper crmCategoryMapper;

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
            if (permissionMapper.checkPermission(userId, "CRM Category (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(crmCategoryMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<CrmCategoryResponse> responses = crmCategoryMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/crm-category/list", null, null, "CRM Category", "CRM Category (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/crm-category/list", line, error.toString(), "CRM Category", "CRM Category (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "CRM Category (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<CrmCategoryResponse> responses = crmCategoryMapper.getOne(id);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/crm-category/find/{id}", null, null, "CRM Category", "CRM Category (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/crm-category/find/{id}", line, error.toString(), "CRM Category", "CRM Category (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(CrmCategoryRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "CRM Category (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (crmCategoryMapper.checkDuplicate(request.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            CrmCategory category = new CrmCategory();
            category.setName(request.getName());
            category.setPhoto(request.getPhoto());
            category.setCreatedBy(userId);
            category.setIsActive(1);
            Boolean result = crmCategoryMapper.insert(category);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/crm-category/add", null, null, "CRM Category", "CRM Category (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/crm-category/add", line, error.toString(), "CRM Category", "CRM Category (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(CrmCategoryUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "CRM Category (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (crmCategoryMapper.checkDuplicate(request.getName(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            CrmCategory category = new CrmCategory();
            category.setId(request.getId());
            category.setName(request.getName());
            category.setPhoto(request.getPhoto());
            category.setModifiedBy(userId);
            Boolean result = crmCategoryMapper.update(category);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/crm-category/update", null, null, "CRM Category", "CRM Category (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/crm-category/update", line, error.toString(), "CRM Category", "CRM Category (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "CRM Category (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = crmCategoryMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/crm-category/delete/{id}", null, null, "CRM Category", "CRM Category (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/crm-category/delete/{id}", line, error.toString(), "CRM Category", "CRM Category (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
