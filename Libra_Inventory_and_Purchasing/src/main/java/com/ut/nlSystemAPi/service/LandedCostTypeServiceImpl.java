package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.LandedCostTypeMapper;
import com.ut.nlSystemAPi.mapper.primary.ModuleMapper;
import com.ut.nlSystemAPi.mapper.primary.ModuleTypeMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.LandedCostType.LandedCostTypeRequest;
import com.ut.nlSystemAPi.model.request.Login.LandedCostType.LandedCostTypeUpdateRequest;
import com.ut.nlSystemAPi.model.response.LandedCostType.LandedCostTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class LandedCostTypeServiceImpl implements LandedCostTypeService {

    @Autowired
    private LandedCostTypeMapper landedCostTypeMapper;

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
             if (permissionMapper.checkPermission(userId, "Landed Cost Type (View)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(landedCostTypeMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<LandedCostTypeResponse> responses = landedCostTypeMapper.getList(filter);

            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost-type/list", null, null, "Landed Cost Type", "Landed Cost Type (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost-type/list", line, error.toString(), "Landed Cost Type", "Landed Cost Type (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Landed Cost Type (View)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }

            List<LandedCostTypeResponse> responses = landedCostTypeMapper.getOne(id);

            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost-type/find/{id}", null, null, "Landed Cost Type", "Landed Cost Type (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost-type/find/{id}", line, error.toString(), "Landed Cost Type", "Landed Cost Type (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(LandedCostTypeRequest landedCostTypeRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Landed Cost Type (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check data
            LandedCostType landedCostType = new LandedCostType();
            landedCostType.setCompanyId(landedCostTypeRequest.getCompanyId());
            landedCostType.setName(landedCostTypeRequest.getName());
            landedCostType.setCreatedBy(userId);
            landedCostType.setIsActive(1);

            Boolean result = landedCostTypeMapper.insert(landedCostType);

            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/landed-cost-type/add", null, null, "Landed Cost Type", "Landed Cost Type (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost-type/add", line, error.toString(), "Landed Cost Type", "Landed Cost Type (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(LandedCostTypeUpdateRequest landedCostTypeUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Landed Cost Type (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check data
            LandedCostType landedCostType = new LandedCostType();
            landedCostType.setId(landedCostTypeUpdateRequest.getId());
            landedCostType.setCompanyId(landedCostTypeUpdateRequest.getCompanyId());
            landedCostType.setName(landedCostTypeUpdateRequest.getName());
            landedCostType.setModifiedBy(userId);

            Boolean result = landedCostTypeMapper.update(landedCostType);

            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/landed-cost/update", null, null, "Landed Cost ", "Landed Cost (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost/update", line, error.toString(), "Landed Cost ", "Landed Cost (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Landed Cost Type (Delete)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }

            Boolean result = landedCostTypeMapper.delete(id, userId);
            if (result) {
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/landed-cost-type/delete/{id}", null, null, "Landed Cost Type", "Landed Cost Type (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost-type/delete/{id}", line, error.toString(), "Landed Cost Type", "Landed Cost Type (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}

