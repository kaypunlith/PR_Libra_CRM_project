package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.entity.TermConditionApply.TermConditionApply;
import com.ut.nlSystemAPi.model.filter.TermConditionApplyFilter;
import com.ut.nlSystemAPi.model.request.TermConditionApply.TermConditionApplyRequest;
import com.ut.nlSystemAPi.model.request.TermConditionApply.TermConditionApplyUpdateRequest;
import com.ut.nlSystemAPi.model.response.TermConditionApply.TermConditionApplyResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.TermConditionApplyService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;
@Service
public class TermConditionApplyServiceImpl implements TermConditionApplyService {

    @Autowired
    private TermConditionApplyMapper termConditionApplyMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ResponseMessage<BaseResult> getList(TermConditionApplyFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Terms & Condition Apply (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(termConditionApplyMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<TermConditionApplyResponse> responses = termConditionApplyMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition-apply/list", null, null, "Term Condition Apply", "Term Condition Apply(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition-apply/list", line, error.toString(), "Term Condition Apply", "Term Condition Apply(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Terms & Condition Apply (View)") == 0) {
               return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<TermConditionApplyResponse> responses = termConditionApplyMapper.getOne(id);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition-apply/find/{id}", null, null, "System term-condition-apply", "System term-condition-apply (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition-apply/find/{id}", line, error.toString(), "System term-condition-apply", "System term-condition-apply (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(TermConditionApplyRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Terms & Condition Apply (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            boolean result = false;

            TermConditionApply termConditionApply = new TermConditionApply();
            termConditionApply.setModuleTypeId(request.getModuleTypeId());
            termConditionApply.setCreatedBy(userId);
            termConditionApply.setIsActive(1);
            if (request.getDetails() != null) {
                for (int i = 0; i < request.getDetails().size(); i++) {
                    termConditionApply.setTermConditionTypeId(request.getDetails().get(i).getTermConditionTypeId());
                    termConditionApply.setTermConditionId(request.getDetails().get(i).getTermConditionId());
                    termConditionApplyMapper.insert(termConditionApply);
                }
                result = true;
            }
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/term-condition-apply/add", null, null, "Term Condition Apply", "Term Condition Apply (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition-apply/add", line, error.toString(), "Term Condition Apply", "Term Condition Apply (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(TermConditionApplyUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Terms & Condition Apply (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            TermConditionApply termConditionApply = new TermConditionApply();
            termConditionApply.setId(request.getId());
            termConditionApply.setModuleTypeId(request.getModuleTypeId());
            termConditionApply.setTermConditionTypeId(request.getTermConditionTypeId());
            termConditionApply.setTermConditionId(request.getTermConditionId());
            termConditionApply.setModifiedBy(userId);

            Boolean result = termConditionApplyMapper.update(termConditionApply);;

            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/term-condition-apply/update", null, null, "Term Condition Apply", "Term Condition Apply (Update)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition-apply/update", line, error.toString(), "Term Condition Apply", "Term Condition Apply (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Terms & Condition Apply (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = termConditionApplyMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/term-condition-apply/delete/{id}",null,null,"Term Condition Apply","Term Condition Apply (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition-apply/delete/{id}",line, error.toString(),"Term Condition Apply","Term Condition Apply (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}
