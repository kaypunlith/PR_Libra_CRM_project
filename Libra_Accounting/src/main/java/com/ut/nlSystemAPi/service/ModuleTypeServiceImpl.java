package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ModuleMapper;
import com.ut.nlSystemAPi.mapper.primary.ModuleTypeMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.ModuleTypeFilter;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.ModuleType.ModuleTypeRequest;
import com.ut.nlSystemAPi.model.request.Login.ModuleType.ModuleTypeUpdateRequest;
import com.ut.nlSystemAPi.model.response.ModuleType.ModuleTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class ModuleTypeServiceImpl implements ModuleTypeService {

    @Autowired
    private ModuleTypeMapper moduleTypeMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityLogService activityLogService;

    public ResponseMessage<BaseResult> getList(ModuleTypeFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(moduleTypeMapper.countList(filter));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ModuleTypeResponse> moduleTypeList = moduleTypeMapper.getListResponse(filter);
            if (moduleTypeList != null) {
                for (ModuleTypeResponse moduleType : moduleTypeList) {
                    Long id = moduleType.getId();
                    if (filter.getRoleId() == null || filter.getRoleId() == 0) {
                        moduleType.setModuleList(moduleMapper.getOne(id));
                    } else {
                        moduleType.setModuleList(moduleMapper.getOneByRoleId(id, filter.getRoleId()));
                    }
                }
            } /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module-type/list",null,null,"Moudle Type","Moudle Type (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", moduleTypeList, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module-type/list",line, error.toString(),"Moudle Type","Moudle Type (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            List<ModuleTypeResponse> moduleTypes = moduleTypeMapper.getOneResponse(id);
            if (moduleTypes != null) {
                for (ModuleTypeResponse moduleType : moduleTypes) {
                    moduleType.setModuleList(moduleMapper.getOne(id));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module-type/find/{id}",null,null,"Moudle Type","Moudle Type (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", moduleTypes, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module-type/find/{id}",line, error.toString(),"Moudle Type","Moudle Type (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ModuleTypeRequest moduleTypeRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (moduleTypeMapper.checkDuplicate(moduleTypeRequest.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate module type name", false));
            }

            Boolean result = moduleTypeMapper.insert(moduleTypeRequest, userId);

            LocalTime endDuration = LocalTime.now();
            if (result) {
                activityLogService.insert("/module-type/add", null, null, "Module Type", "Module Type (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }

            activityLogService.insert("/module-type/add", line, "Failed to add module type", "Module Type", "Module Type (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module-type/add", line, error.toString(), "Module Type", "Module Type (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(ModuleTypeUpdateRequest moduleTypeUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (moduleTypeMapper.checkDuplicate(moduleTypeUpdateRequest.getName(), moduleTypeUpdateRequest.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate module type name", false));
            }

            Boolean result = moduleTypeMapper.update(moduleTypeUpdateRequest, userId);

            LocalTime endDuration = LocalTime.now();
            if (result) {
                activityLogService.insert("/module-type/update", null, null, "Module Type", "Module Type (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }

            activityLogService.insert("/module-type/update", line, "Failed to update module type", "Module Type", "Module Type (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module-type/update", line, error.toString(), "Module Type", "Module Type (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            Boolean result = moduleTypeMapper.delete(id, userId);

            LocalTime endDuration = LocalTime.now();
            if (result) {
                activityLogService.insert("/module-type/delete/" + id, null, null, "Module Type", "Module Type (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }

            activityLogService.insert("/module-type/delete/" + id, line, "Failed to delete module type", "Module Type", "Module Type (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (RuntimeException error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module-type/delete/" + id, line, error.toString(), "Module Type", "Module Type (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
