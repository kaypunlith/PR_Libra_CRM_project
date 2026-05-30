package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ModuleMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ModuleFilter;
import com.ut.nlSystemAPi.model.request.Login.Module.ModuleRequest;
import com.ut.nlSystemAPi.model.request.Login.Module.ModuleUpdateRequest;
import com.ut.nlSystemAPi.model.response.Module.ModuleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(ModuleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(moduleMapper.countList(filter));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ModuleResponse> moduleResponses = moduleMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module/list", null, null, "Module", "Module (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", moduleResponses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module/list", line, error.toString(), "Module", "Module (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            List<ModuleResponse> moduleResponses = moduleMapper.getOneModule(id);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module/find/" + id, null, null, "Module", "Module (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", moduleResponses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module/find/" + id, line, error.toString(), "Module", "Module (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ModuleRequest moduleRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (moduleMapper.checkDuplicate(moduleRequest.getName(), moduleRequest.getModuleTypeId(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate module name", false));
            }

            Boolean result = moduleMapper.insert(moduleRequest, userId);

            LocalTime endDuration = LocalTime.now();
            if (result) {
                activityLogService.insert("/module/add", null, null, "Module", "Module (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }

            activityLogService.insert("/module/add", line, "Failed to add module", "Module", "Module (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module/add", line, error.toString(), "Module", "Module (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(ModuleUpdateRequest moduleUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            Long moduleTypeId = moduleUpdateRequest.getModuleTypeId();
            if (moduleTypeId == null) {
                List<ModuleResponse> currentModules = moduleMapper.getOneModule(moduleUpdateRequest.getId());
                if (currentModules != null && !currentModules.isEmpty()) {
                    moduleTypeId = currentModules.get(0).getModuleTypeId();
                }
            }

            if (moduleUpdateRequest.getName() != null && moduleMapper.checkDuplicate(moduleUpdateRequest.getName(), moduleTypeId, moduleUpdateRequest.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate module name", false));
            }

            Boolean result = moduleMapper.update(moduleUpdateRequest, userId);

            LocalTime endDuration = LocalTime.now();
            if (result) {
                activityLogService.insert("/module/update", null, null, "Module", "Module (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }

            activityLogService.insert("/module/update", line, "Failed to update module", "Module", "Module (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module/update", line, error.toString(), "Module", "Module (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            Boolean result = moduleMapper.delete(id, userId);

            LocalTime endDuration = LocalTime.now();
            if (result) {
                activityLogService.insert("/module/delete/" + id, null, null, "Module", "Module (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }

            activityLogService.insert("/module/delete/" + id, line, "Failed to delete module", "Module", "Module (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (RuntimeException error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module/delete/" + id, line, error.toString(), "Module", "Module (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}
