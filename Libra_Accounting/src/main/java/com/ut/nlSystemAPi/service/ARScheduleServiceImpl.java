package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ARScheduleMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.FilterBase;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ARAPScheduleFilter;
import com.ut.nlSystemAPi.model.filter.ARAPScheduleWeekFilter;
import com.ut.nlSystemAPi.model.request.Login.ARAPSchedule.ARAPScheduleRequest;
import com.ut.nlSystemAPi.model.request.Login.ARAPSchedule.ARAPScheduleUpdateRequest;
import com.ut.nlSystemAPi.model.response.ARSchedule.ARScheduleResponse;
import com.ut.nlSystemAPi.model.response.ARSchedule.ARScheduleResponseDetails;
import com.ut.nlSystemAPi.model.response.ARSchedule.ARScheduleTotalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ARScheduleServiceImpl implements ARScheduleService {

    @Autowired
    private ARScheduleMapper arScheduleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    Environment environment;

    @Override
    public ResponseMessage<BaseResult> getListARSchedule(ARAPScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "AR Schedule (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(arScheduleMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ARScheduleResponse> arScheduleResponses = arScheduleMapper.getList(filter);

            if(arScheduleResponses.size() > 0){
                for (int i = 0; i <arScheduleResponses.size(); i++){
                    Long arScheduleId = arScheduleResponses.get(i).getId();
                    List<ARScheduleResponseDetails> arScheduleResponseDetails = arScheduleMapper.getDetails(arScheduleId);
                    arScheduleResponses.get(i).setDetails(arScheduleResponseDetails);

                    if(arScheduleResponseDetails.get(0).getAmountDue() == 0){
                        arScheduleResponses.get(i).setBackgroundColor("#808080");
                    }
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ar-schedule/list",null,null,"AR Schedule","AR Schedule (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", arScheduleResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ar-schedule/list",line, error.toString(),"AR Schedule","AR Schedule (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "AR Schedule (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<ARScheduleResponse> arScheduleResponses= arScheduleMapper.getOne(id);

            if(arScheduleResponses.size() > 0){
                for (int i = 0; i <arScheduleResponses.size(); i++){
                    Long arScheduleId = arScheduleResponses.get(i).getId();
                    List<ARScheduleResponseDetails> arScheduleResponseDetails = arScheduleMapper.getDetails(arScheduleId);
                    arScheduleResponses.get(i).setDetails(arScheduleResponseDetails);
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ar-schedule/find/{id}",null,null,"AR Schedule","AR Schedule (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", arScheduleResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ar-schedule/find/{id}",line, error.toString(),"AR Schedule","AR Schedule (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ARAPScheduleRequest arScheduleRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "AR Schedule (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
//            if(arScheduleMapper.checkDuplicate(arScheduleRequest.getAccountCodes(), null) > 0){
//                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate chart account code", false));
//            }

            // Check Data
            ARSchedule arSchedule = new ARSchedule();
          arSchedule.setBranchId(arScheduleRequest.getBranchId());
            arSchedule.setStartDate(arScheduleRequest.getStartDate());
            arSchedule.setEndDate(arScheduleRequest.getEndDate());
            arSchedule.setStartTime(arScheduleRequest.getStartTime());
            arSchedule.setEndTime(arScheduleRequest.getEndTime());
            arSchedule.setTitle(arScheduleRequest.getTitle());
            arSchedule.setBackgroundColor(arScheduleRequest.getBackgroundColor());
            arSchedule.setPrivacy(arScheduleRequest.getPrivacy());
            arSchedule.setCreatedBy(userId);
            arSchedule.setIsActive(1);
            Boolean result = arScheduleMapper.insert(arSchedule);
            if (result) {
                for (int i = 0; i< arScheduleRequest.getInvoices().size(); i++) {
                    ARScheduleDetails arScheduleDetails = new ARScheduleDetails();
                    arScheduleDetails.setArScheduleId(arSchedule.getId());
                    arScheduleDetails.setSalesOrderId(arScheduleRequest.getInvoices().get(i).getInvoiceId());
                    arScheduleDetails.setAmountDue(arScheduleRequest.getInvoices().get(i).getAmountDue());
                    arScheduleMapper.insertDetails(arScheduleDetails);
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/ar-schedule/add",null,null,"AR Schedule","AR Schedule (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ar-schedule/add",line, error.toString(),"AR Schedule","AR Schedule (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> update(ARAPScheduleUpdateRequest arScheduleUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "AR Schedule (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

//            // Check Duplicate
//            if(arScheduleMapper.checkDuplicate(arScheduleUpdateRequest.getAccountCodes(), chartOfAccountUpdateRequest.getId()) > 0){
//                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate chart account code", false));
//            }

            // Check Data
            ARSchedule arSchedule = new ARSchedule();
            arSchedule.setId(arScheduleUpdateRequest.getId());
            arSchedule.setBranchId(arScheduleUpdateRequest.getBranchId());
            arSchedule.setStartDate(arScheduleUpdateRequest.getStartDate());
            arSchedule.setEndDate(arScheduleUpdateRequest.getEndDate());
            arSchedule.setStartTime(arScheduleUpdateRequest.getStartTime());
            arSchedule.setEndTime(arScheduleUpdateRequest.getEndTime());
            arSchedule.setTitle(arScheduleUpdateRequest.getTitle());
            arSchedule.setBackgroundColor(arScheduleUpdateRequest.getBackgroundColor());
            arSchedule.setPrivacy(arScheduleUpdateRequest.getPrivacy());
            arSchedule.setModifiedBy(userId);
            Boolean result = arScheduleMapper.update(arSchedule);
            if (result) {
                // Delete Details
                arScheduleMapper.deleteDetails(arSchedule.getId());

                // Insert New Details
                for (int i = 0; i < arScheduleUpdateRequest.getInvoices().size(); i++){
                    ARScheduleDetails arScheduleDetails = new ARScheduleDetails();
                    arScheduleDetails.setArScheduleId(arSchedule.getId());
                    arScheduleDetails.setSalesOrderId(arScheduleUpdateRequest.getInvoices().get(i).getInvoiceId());
                    arScheduleDetails.setAmountDue(arScheduleUpdateRequest.getInvoices().get(i).getAmountDue());
                    arScheduleMapper.insertDetails(arScheduleDetails);
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/ar-schedule/update",null,null,"AR Schedule","AR Schedule (Update)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ar-schedule/update",line, error.toString(),"AR Schedule","AR Schedule (Update)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
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

            if (permissionMapper.checkPermission(userId, "AR Schedule (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = arScheduleMapper.delete(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/ar-schedule/delete/{id}", null, null, "AR Schedule", "AR Schedule (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ar-schedule/delete/{id}", line, error.toString(), "AR Schedule", "AR Schedule (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getTotalList(ARAPScheduleWeekFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "AR Schedule (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            System.out.println("filter: " + filter);


            List<ARScheduleTotalResponse> arScheduleTotalResponsesArray = new ArrayList<>();

            // List data
            List<ARScheduleTotalResponse> totalBookingOverdue = arScheduleMapper.getInvoiceBookingOverdue(filter);
            List<ARScheduleTotalResponse> totalBooked = arScheduleMapper.getInvoiceBooked(filter);
            List<ARScheduleTotalResponse> totalBookedByUserId = arScheduleMapper.getInvoiceBookedByUser(filter, userId);
            List<ARScheduleTotalResponse> invoiceBookedByWeek = arScheduleMapper.getInvoiceBookedByWeek(filter);

            List<ARScheduleTotalResponse> totalOrg = arScheduleMapper.getTotalCustomer(filter);


            ARScheduleTotalResponse totalBookingOverdueTmp = new ARScheduleTotalResponse();
            arScheduleTotalResponsesArray.add(totalBookingOverdueTmp);

            arScheduleTotalResponsesArray.get(0).setBookingOverdue(totalBookingOverdue);

            arScheduleTotalResponsesArray.get(0).setInvoiceBooked(totalBooked);

            arScheduleTotalResponsesArray.get(0).setInvoiceBookedByUser(totalBookedByUserId);

            arScheduleTotalResponsesArray.get(0).setInvoiceByWeek(invoiceBookedByWeek);

            if(totalOrg.size() > 0){
                arScheduleTotalResponsesArray.get(0).setTotalOrganization(totalOrg.get(0).getTotalOrganization());
                arScheduleTotalResponsesArray.get(0).setTotalOrganizationAmount(totalOrg.get(0).getAmount());
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ar-schedule/booking-information/total",null,null,"AR Schedule","AR Schedule (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", arScheduleTotalResponsesArray , pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ar-schedule/booking-information/total",line, error.toString(),"AR Schedule","AR Schedule (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getTotalInvoiceByWeek(ARAPScheduleWeekFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "AR Schedule (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ARScheduleTotalResponse> invoiceBookedByWeek = arScheduleMapper.getInvoiceBookedByWeek(filter);

            List<ARScheduleTotalResponse> responses = new ArrayList<>();

            if(invoiceBookedByWeek.size() > 0){
                ARScheduleTotalResponse arScheduleTotalResponse = new ARScheduleTotalResponse();

                // Total Booking Overdue
                Double totalBookedByWeekAmount = 0D;

                for (int i = 0; i <invoiceBookedByWeek.size(); i++){
                    totalBookedByWeekAmount += invoiceBookedByWeek.get(i).getAmount();
                }
                arScheduleTotalResponse.setAmount(totalBookedByWeekAmount);

                // Add to data response
                responses.add(arScheduleTotalResponse);
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ar-schedule/invoice-booked-by-weeks",null,null,"AR Schedule","AR Schedule (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses , pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ar-schedule/invoice-booked-by-weeks",line, error.toString(),"AR Schedule","AR Schedule (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}