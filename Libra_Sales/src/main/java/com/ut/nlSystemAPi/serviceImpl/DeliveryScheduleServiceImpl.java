package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.DeliveryScheduleMapper;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.DeliverySchedule.DeliverySchedule;
import com.ut.nlSystemAPi.model.entity.DeliverySchedule.DeliveryScheduleDetail;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.filter.DeliveryScheduleFilter;
import com.ut.nlSystemAPi.model.request.DeliverySchedule.DeliveryScheduleRequest;
import com.ut.nlSystemAPi.model.request.DeliverySchedule.DeliveryScheduleUpdateRequest;
import com.ut.nlSystemAPi.model.response.DeliverySchedule.DeliveryScheduleDetailResponse;
import com.ut.nlSystemAPi.model.response.DeliverySchedule.DeliveryScheduleResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.DeliveryScheduleService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class DeliveryScheduleServiceImpl implements DeliveryScheduleService {
    @Autowired
    private DeliveryScheduleMapper deliveryScheduleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ResponseMessage<BaseResult> getList(DeliveryScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Delivery Schedule (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (permissionMapper.checkPermission(userId, "Delivery Schedule (View By User)") > 0) {
                filter.setViewByUser(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(deliveryScheduleMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DeliveryScheduleResponse> deliveryScheduleResponses = deliveryScheduleMapper.getList(filter, userId);
            if(!deliveryScheduleResponses.isEmpty()) {
                for (int i = 0; i < deliveryScheduleResponses.size(); i++) {
                    List<DeliveryScheduleDetailResponse> details = deliveryScheduleMapper.getDetail(deliveryScheduleResponses.get(i).getId());
                    deliveryScheduleResponses.get(i).setDetails(details);
                    if (!details.isEmpty()) {
                        long totalSO = 0;
                        for (DeliveryScheduleDetailResponse detail : details) {
                            totalSO += detail.getTotalOrder();
                        }
                        deliveryScheduleResponses.get(0).setTotalSO(totalSO);
                    }
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-schedule/list", null, null, "Delivery Schedule", "Delivery Schedule(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", deliveryScheduleResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-schedule/list", line, error.toString(), "Delivery Schedule", "Delivery Schedule(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Delivery Schedule (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<DeliveryScheduleResponse> deliveryScheduleResponses = deliveryScheduleMapper.getOne(id);

            if(!deliveryScheduleResponses.isEmpty()) {
                for (DeliveryScheduleResponse response : deliveryScheduleResponses) {
                    response.setDetails(deliveryScheduleMapper.getDetail(response.getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-schedule/find/{id}", null, null, "System Delivery Schedule", "System Delivery Schedule (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", deliveryScheduleResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-schedule/find/{id}", line, error.toString(), "System Delivery Schedule", "System Delivery Schedule (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(DeliveryScheduleRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Delivery Schedule (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            DeliverySchedule deliverySchedule = new DeliverySchedule();
            deliverySchedule.setStartDate(request.getStartDate());
            deliverySchedule.setStartTime(request.getStartTime());
            deliverySchedule.setEndDate(request.getEndDate());
            deliverySchedule.setEndTime(request.getEndTime());
            deliverySchedule.setTitle(request.getTitle());
            deliverySchedule.setBackgroundColor(request.getBackgroundColor());
            deliverySchedule.setPrivacy(request.getPrivacy());
            deliverySchedule.setCreatedBy(userId);
            Boolean result = deliveryScheduleMapper.insert(deliverySchedule);

            if (result) {
                if(!request.getDetails().isEmpty()) {
                    DeliveryScheduleDetail detail = new DeliveryScheduleDetail();
                    for(int i=0;i<request.getDetails().size();i++){
                        detail.setDeliveryScheduleId(deliverySchedule.getId());
                        detail.setSaleOrderId(request.getDetails().get(i).getSaleOrderId());
                        detail.setAmountDue(request.getDetails().get(i).getAmountDue());
                        deliveryScheduleMapper.insertDetail(detail);
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/delivery-schedule/add", null, null, "Delivery Schedule", "Delivery Schedule (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-schedule/add", line, error.toString(), "Delivery Schedule", "Delivery Schedule (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(DeliveryScheduleUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Delivery Schedule (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            DeliverySchedule deliverySchedule = new DeliverySchedule();
            deliverySchedule.setId(request.getId());
            deliverySchedule.setStartDate(request.getStartDate());
            deliverySchedule.setStartTime(request.getStartTime());
            deliverySchedule.setEndDate(request.getEndDate());
            deliverySchedule.setEndTime(request.getEndTime());
            deliverySchedule.setTitle(request.getTitle());
            deliverySchedule.setBackgroundColor(request.getBackgroundColor());
            deliverySchedule.setPrivacy(request.getPrivacy());
            deliverySchedule.setComment(request.getComment());
            deliverySchedule.setModifiedBy(userId);
            Boolean result = deliveryScheduleMapper.update(deliverySchedule);

            if (result) {
                if(!request.getDetails().isEmpty()) {
                    deliveryScheduleMapper.deleteDetail(deliverySchedule.getId());
                    DeliveryScheduleDetail detail = new DeliveryScheduleDetail();
                    for(int i=0;i<request.getDetails().size();i++){
                        detail.setDeliveryScheduleId(deliverySchedule.getId());
                        detail.setSaleOrderId(request.getDetails().get(i).getSaleOrderId());
                        detail.setAmountDue(request.getDetails().get(i).getAmountDue());
                        deliveryScheduleMapper.insertDetail(detail);
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/delivery-schedule/add", null, null, "Delivery Schedule", "Delivery Schedule (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/delivery-schedule/update", line, error.toString(), "Delivery Schedule", "Delivery Schedule (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Delivery Schedule (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = deliveryScheduleMapper.delete(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/delivery-schedule/delete/{id}",null,null,"Delivery Schedule","Delivery Schedule (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-schedule/delete/{id}",line, error.toString(),"Delivery Schedule","Delivery Schedule (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}