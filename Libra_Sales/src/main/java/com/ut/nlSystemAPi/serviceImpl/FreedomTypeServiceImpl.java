package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.freedom.FreedomMapper;
import com.ut.nlSystemAPi.mapper.primary.FreedomTypeMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.FreedomType.FreedomType;
import com.ut.nlSystemAPi.model.request.FreedomType.FreedomTypeRequest;
import com.ut.nlSystemAPi.model.request.FreedomType.FreedomTypeUpdateRequest;
import com.ut.nlSystemAPi.model.response.FreedomType.FreedomTypeResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.FreedomTypeService;
import com.ut.nlSystemAPi.service.UserService;
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
public class FreedomTypeServiceImpl implements FreedomTypeService {

    @Autowired
    private FreedomTypeMapper freedomTypeMapper;

    @Autowired
    private FreedomMapper freedomMapper;

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
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Freedom Type (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(freedomTypeMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<FreedomTypeResponse> responses = freedomTypeMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/freedom-type/list", null, null, "Freedom Type", "Freedom Type (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/freedom-type/list", line, error.toString(), "Freedom Type", "Freedom Type (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Freedom Type (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<FreedomTypeResponse> responses = freedomTypeMapper.getOne(id);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/freedom-type/find/{id}", null, null, "Freedom Type", "Freedom Type (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/freedom-type/find/{id}", line, error.toString(), "Freedom Type", "Freedom Type (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(FreedomTypeRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Freedom Type (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            FreedomType freedomType = new FreedomType();
            freedomType.setName(request.getName());
            freedomType.setPriceTypeId(request.getPriceTypeId());
            freedomType.setCreatedBy(userId);
            freedomType.setIsActive(1);
            Boolean result = freedomTypeMapper.insert(freedomType);

            if (result) {
                freedomMapper.insertFreedomType(toFreedomFreedomTypeMap(freedomType));
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/freedom-type/add", null, null, "Freedom Type", "Freedom Type (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/freedom-type/add", line, error.toString(), "Freedom Type", "Freedom Type (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(FreedomTypeUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Freedom Type (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            FreedomType freedomType = new FreedomType();
            freedomType.setId(request.getId());
            freedomType.setName(request.getName());
            freedomType.setPriceTypeId(request.getPriceTypeId());
            freedomType.setModifiedBy(userId);
            Boolean result = freedomTypeMapper.update(freedomType);

            if (result) {
                freedomMapper.updateFreedomType(toFreedomFreedomTypeMap(freedomType));
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/freedom-type/update", null, null, "Freedom Type", "Freedom Type (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/freedom-type/update", line, error.toString(), "Freedom Type", "Freedom Type (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Freedom Type (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = freedomTypeMapper.delete(id, userId);
            if (result) {
                freedomMapper.deleteFreedomType(id, userId);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/freedom-type/delete/{id}", null, null, "Freedom Type", "Freedom Type (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/freedom-type/delete/{id}", line, error.toString(), "Freedom Type", "Freedom Type (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private Map<String, Object> toFreedomFreedomTypeMap(FreedomType freedomType) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", freedomType.getId());
        map.put("name", freedomType.getName());
        map.put("priceTypeId", freedomType.getPriceTypeId());
        map.put("createdBy", freedomType.getCreatedBy());
        map.put("modifiedBy", freedomType.getModifiedBy());
        map.put("isActive", freedomType.getIsActive());
        return map;
    }
}
