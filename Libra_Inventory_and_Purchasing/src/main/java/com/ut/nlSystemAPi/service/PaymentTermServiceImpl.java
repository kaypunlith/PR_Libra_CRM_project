package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.freedom.FreedomMapper;
import com.ut.nlSystemAPi.mapper.primary.PaymentTermMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.PaymentTerm.PaymentTermRequest;
import com.ut.nlSystemAPi.model.request.Login.PaymentTerm.PaymentTermUpdateRequest;
import com.ut.nlSystemAPi.model.response.PaymentTerm.PaymentTermResponse;
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
public class PaymentTermServiceImpl implements PaymentTermService {
    @Autowired
    private PaymentTermMapper paymentTermMapper;

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
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Payment Terms (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(paymentTermMapper.countList(filter));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<PaymentTermResponse> responses = paymentTermMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/payment-term/list", null, null, "Payment Terms", "Payment Terms (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/payment-term/list", line, error.toString(), "Payment Terms", "Payment Terms (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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
              if (permissionMapper.checkPermission(userId, "Payment Terms (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            List<PaymentTermResponse> responses = paymentTermMapper.getOne(id);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/payment-term/find/{id}", null, null, "Payment Terms", "Payment Terms (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/payment-term/find/{id}", line, error.toString(), "Payment Terms", "Payment Terms (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(PaymentTermRequest paymentTermRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Payment Terms (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check Duplicate
            if(paymentTermMapper.checkDuplicate(paymentTermRequest.getName(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            // Check Data
            PaymentTerm paymentTerm = new PaymentTerm();
            paymentTerm.setTypeId(paymentTermRequest.getTypeId());
            paymentTerm.setName(paymentTermRequest.getName());
            paymentTerm.setNetDay(paymentTermRequest.getNetDay());
            paymentTerm.setCreatedBy(userId);
            paymentTerm.setIsActive(1);

            Boolean result = paymentTermMapper.insert(paymentTerm);

            if (result) {
                freedomMapper.insertPaymentTerm(toFreedomPaymentTermMap(paymentTerm));
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/payment-term/add", null, null, "Payment Terms", "Payment Terms (add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/payment-term/add", line, error.toString(), "Payment Terms", "Payment Terms (add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> update(PaymentTermUpdateRequest paymentTermUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Payment Terms (edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if(paymentTermMapper.checkDuplicate(paymentTermUpdateRequest.getName(), paymentTermUpdateRequest.getId()) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            PaymentTerm paymentTerm = new PaymentTerm();
            paymentTerm.setId(paymentTermUpdateRequest.getId());
            paymentTerm.setTypeId(paymentTermUpdateRequest.getTypeId());
            paymentTerm.setName(paymentTermUpdateRequest.getName());
            paymentTerm.setNetDay(paymentTermUpdateRequest.getNetDay());
            paymentTerm.setModifiedBy(userId);

            Boolean result = paymentTermMapper.update(paymentTerm);

            if (result) {
                freedomMapper.updatePaymentTerm(toFreedomPaymentTermMap(paymentTerm));
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/payment-term/update", null, null, "Payment Terms", "Payment Terms (edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/payment-term/update", line, error.toString(), "Payment Terms", "Payment Terms (edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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
              if (permissionMapper.checkPermission(userId, "Payment Terms (delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }
            Boolean result = paymentTermMapper.delete(id, userId);
            if (result) {
                freedomMapper.deletePaymentTerm(id, userId);
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/payment-term/delete/{id}",null,null,"Payment Terms","Payment Terms (delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/payment-term/delete/{id}",line, error.toString(),"Payment Terms","Payment Terms (delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private Map<String, Object> toFreedomPaymentTermMap(PaymentTerm paymentTerm) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", paymentTerm.getId());
        map.put("typeId", paymentTerm.getTypeId());
        map.put("name", paymentTerm.getName());
        map.put("netDay", paymentTerm.getNetDay());
        map.put("createdBy", paymentTerm.getCreatedBy());
        map.put("modifiedBy", paymentTerm.getModifiedBy());
        map.put("isActive", paymentTerm.getIsActive());
        return map;
    }


}
