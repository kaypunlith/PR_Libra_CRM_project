package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.TermConditionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.entity.TermCondition.TermCondition;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.TermConditionFilter;
import com.ut.nlSystemAPi.model.request.TermCondition.TermConditionRequest;
import com.ut.nlSystemAPi.model.request.TermCondition.TermConditionUpdateRequest;
import com.ut.nlSystemAPi.model.response.TermCondition.TermConditionResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.TermConditionService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class TermConditionServiceImpl implements TermConditionService {

    @Autowired
    private TermConditionMapper termConditionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ResponseMessage<BaseResult> getList(TermConditionFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Terms & Condition (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(termConditionMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<TermConditionResponse> responses = termConditionMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition/list", null, null, "Terms & Condition", "Terms & Condition (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition/list", line, error.toString(), "Terms & Condition", "Terms & Condition (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Terms & Condition (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<TermConditionResponse> responses = termConditionMapper.getOne(id);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition/find/{id}", null, null, "Terms & Condition", "Terms & Condition (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition/find/{id}", line, error.toString(), "Terms & Condition", "Terms & Condition (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(TermConditionRequest termConditionRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Terms & Condition (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check Duplicate
            if(termConditionMapper.checkDuplicate(termConditionRequest.getName(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            // Check Data
            TermCondition termCondition = new TermCondition();
            termCondition.setName(termConditionRequest.getName());
            termCondition.setTypeId(termConditionRequest.getTypeId());
            termCondition.setDescription(termConditionRequest.getDescription());
            termCondition.setCreatedBy(userId);
            termCondition.setIsActive(1);
            Boolean result = termConditionMapper.insert(termCondition);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/term-condition/add", null, null, "Terms & Condition", "Terms & Condition (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition/add", line, error.toString(), "Terms & Condition", "Terms & Condition (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
  @Override
    public ResponseMessage<BaseResult> update(TermConditionUpdateRequest termConditionUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Terms & Condition (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check Duplicate
            if(termConditionMapper.checkDuplicate(termConditionUpdateRequest.getName(), termConditionUpdateRequest.getId()) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            TermCondition termCondition = new TermCondition();
            termCondition.setId(termConditionUpdateRequest.getId());
            termCondition.setName(termConditionUpdateRequest.getName());
            termCondition.setTypeId(termConditionUpdateRequest.getTypeId());
            termCondition.setDescription(termConditionUpdateRequest.getDescription());
            termCondition.setModifiedBy(userId);

            Boolean result = termConditionMapper.update(termCondition);

            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/term-condition/update", null, null, "Terms & Condition", "Terms & Condition (Update)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/term-condition/update", line, error.toString(), "Terms & Condition", "Terms & Condition (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Terms & Condition (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = termConditionMapper.delete(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/term-condition/delete/{id}",null,null,"Terms & Condition","Terms & Condition (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-condition/delete/{id}",line, error.toString(),"Terms & Condition","Terms & Condition (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


}
