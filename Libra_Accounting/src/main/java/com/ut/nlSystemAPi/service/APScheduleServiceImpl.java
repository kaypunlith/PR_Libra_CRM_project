package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.APScheduleMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ARAPScheduleFilter;
import com.ut.nlSystemAPi.model.request.Login.ARAPSchedule.ARAPScheduleRequest;
import com.ut.nlSystemAPi.model.request.Login.ARAPSchedule.ARAPScheduleUpdateRequest;
import com.ut.nlSystemAPi.model.response.APSchedule.*;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalBookingInformation.APScheduleTotalBookingInformationResponse;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBalance.APScheduleTopVendorTotalResponse;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBalance.APScheduleTopVendorTotalResponseDetails;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBalance.APScheduleVendorTotalResponse;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBooked.APScheduleTopVendorBookedTotalResponse;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBooked.APScheduleTopVendorBookedTotalResponseDetails;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBooked.APScheduleVendorBookedTotalResponse;
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
public class APScheduleServiceImpl implements APScheduleService {

    @Autowired
    private APScheduleMapper apScheduleMapper;

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
    public ResponseMessage<BaseResult> getListAPSchedule(ARAPScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(apScheduleMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<APScheduleResponse> apScheduleResponses = apScheduleMapper.getList(filter);

            if(apScheduleResponses.size() > 0){
                for (int i = 0; i <apScheduleResponses.size(); i++){
                    Long apScheduleId = apScheduleResponses.get(i).getId();
                    List<APScheduleResponseDetails> apScheduleResponseDetails = apScheduleMapper.getDetails(apScheduleId);

                    apScheduleResponses.get(i).setDetails(apScheduleResponseDetails);

                    if(apScheduleResponseDetails.get(0).getAmountDue() == 0){
                        apScheduleResponses.get(i).setBackgroundColor("#808080");
                    }
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/list",null,null,"AR Schedule","AR Schedule (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", apScheduleResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/list",line, error.toString(),"AR Schedule","AR Schedule (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<APScheduleResponse> apScheduleResponses = apScheduleMapper.getOne(id);

            if(apScheduleResponses.size() > 0){
                for (int i = 0; i <apScheduleResponses.size(); i++){
                    Long apScheduleId = apScheduleResponses.get(i).getId();
                    List<APScheduleResponseDetails> apScheduleResponseDetails = apScheduleMapper.getDetails(apScheduleId);
                    apScheduleResponses.get(i).setDetails(apScheduleResponseDetails);
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/find/{id}",null,null,"AR Schedule","AR Schedule (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", apScheduleResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/find/{id}",line, error.toString(),"AR Schedule","AR Schedule (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ARAPScheduleRequest apScheduleRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
//            if(arScheduleMapper.checkDuplicate(arScheduleRequest.getAccountCodes(), null) > 0){
//                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate chart account code", false));
//            }

            // Check Data
            APSchedule apSchedule = new APSchedule();
             apSchedule.setBranchId(apScheduleRequest.getBranchId());
            apSchedule.setStartDate(apScheduleRequest.getStartDate());
            apSchedule.setEndDate(apScheduleRequest.getEndDate());
            apSchedule.setStartTime(apScheduleRequest.getStartTime());
            apSchedule.setEndTime(apScheduleRequest.getEndTime());
            apSchedule.setTitle(apScheduleRequest.getTitle());
            apSchedule.setBackgroundColor(apScheduleRequest.getBackgroundColor());
            apSchedule.setPrivacy(apScheduleRequest.getPrivacy());
            apSchedule.setCreatedBy(userId);
            apSchedule.setIsActive(1);
            Boolean result = apScheduleMapper.insert(apSchedule);
            if (result) {
                for (int i = 0; i< apScheduleRequest.getInvoices().size(); i++) {
                    APScheduleDetails apScheduleDetails = new APScheduleDetails();
                    apScheduleDetails.setApScheduleId(apSchedule.getId());
                    apScheduleDetails.setPurchasesOrderId(apScheduleRequest.getInvoices().get(i).getInvoiceId());
                    apScheduleDetails.setAmountDue(apScheduleRequest.getInvoices().get(i).getAmountDue());
                    apScheduleMapper.insertDetails(apScheduleDetails);
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/ap-schedule/add",null,null,"AR Schedule","AR Schedule (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/add",line, error.toString(),"AR Schedule","AR Schedule (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> update(ARAPScheduleUpdateRequest apScheduleUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

//            // Check Duplicate
//            if(arScheduleMapper.checkDuplicate(arScheduleUpdateRequest.getAccountCodes(), chartOfAccountUpdateRequest.getId()) > 0){
//                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate chart account code", false));
//            }

            // Check Data
            APSchedule apSchedule = new APSchedule();
            apSchedule.setId(apScheduleUpdateRequest.getId());
            apSchedule.setBranchId(apScheduleUpdateRequest.getBranchId());
            apSchedule.setStartDate(apScheduleUpdateRequest.getStartDate());
            apSchedule.setEndDate(apScheduleUpdateRequest.getEndDate());
            apSchedule.setStartTime(apScheduleUpdateRequest.getStartTime());
            apSchedule.setEndTime(apScheduleUpdateRequest.getEndTime());
            apSchedule.setTitle(apScheduleUpdateRequest.getTitle());
            apSchedule.setBackgroundColor(apScheduleUpdateRequest.getBackgroundColor());
            apSchedule.setPrivacy(apScheduleUpdateRequest.getPrivacy());
            apSchedule.setModifiedBy(userId);
            Boolean result = apScheduleMapper.update(apSchedule);
            if (result) {
                // Delete Details
                apScheduleMapper.deleteDetails(apSchedule.getId());

                // Insert New Details
                for (int i = 0; i < apScheduleUpdateRequest.getInvoices().size(); i++){
                    APScheduleDetails apScheduleDetails = new APScheduleDetails();
                    apScheduleDetails.setApScheduleId(apSchedule.getId());
                    apScheduleDetails.setPurchasesOrderId(apScheduleUpdateRequest.getInvoices().get(i).getInvoiceId());
                    apScheduleDetails.setAmountDue(apScheduleUpdateRequest.getInvoices().get(i).getAmountDue());
                    apScheduleMapper.insertDetails(apScheduleDetails);
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/ap-schedule/update",null,null,"AR Schedule","AR Schedule (Update)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/update",line, error.toString(),"AR Schedule","AR Schedule (Update)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
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

            if (permissionMapper.checkPermission(userId, "Chart of Account (delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = apScheduleMapper.delete(id, userId);

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
    public ResponseMessage<BaseResult> getTotalList(ARAPScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {

            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<APScheduleTotalBookingInformationResponse> totalPBBookingOverdue = apScheduleMapper.getPBBookingOverdue(filter,userId);
            List<APScheduleTotalBookingInformationResponse> totalPBBooked = apScheduleMapper.getPBBooked(filter);
            List<APScheduleTotalBookingInformationResponse> totalPBBookedByUserId = apScheduleMapper.getPBBookedByUser(filter, userId);
            List<APScheduleTotalBookingInformationResponse> totalOrg = apScheduleMapper.getTotalVendorAmount(filter);
            List<APScheduleTotalBookingInformationResponse> totalAmountBookedByWeek = apScheduleMapper.getInvoiceBookingThisWeek(filter);

            System.out.println("totalOrg: " + totalAmountBookedByWeek);

            List<APScheduleTotalBookingInformationResponse> apScheduleTotalResponses = new ArrayList<>();

            APScheduleTotalBookingInformationResponse apScheduleTotalResponse = new APScheduleTotalBookingInformationResponse();

            apScheduleTotalResponses.add(apScheduleTotalResponse);

            apScheduleTotalResponses.get(0).setBookingOverdue(totalPBBookingOverdue);

            apScheduleTotalResponses.get(0).setInvoiceBooked(totalPBBooked);

            apScheduleTotalResponses.get(0).setInvoiceBookedByUser(totalPBBookedByUserId);

            apScheduleTotalResponses.get(0).setInvoiceByWeek(totalAmountBookedByWeek);

            if(totalOrg.size() > 0){
                apScheduleTotalResponses.get(0).setTotalOrganization(totalOrg.get(0).getTotalOrganization());
                apScheduleTotalResponses.get(0).setTotalOrganizationAmount(totalOrg.get(0).getTotalBalance());
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/list",null,null,"AR Schedule","AR Schedule (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", apScheduleTotalResponses , pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/list",line, error.toString(),"AR Schedule","AR Schedule (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getVendorTotalList(ARAPScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {

            // Check Permission
            Long userId = userService.getUserAuth().getId();

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(apScheduleMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<APScheduleVendorTotalResponse> apScheduleTotalResponses = apScheduleMapper.getVendorTotalList(filter);

            System.out.println("apScheduleTotalResponses: graph " + apScheduleTotalResponses);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendor-balance/total/list",null,null,"AR Schedule","AR Schedule (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", apScheduleTotalResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendor-balance/total/list",line, error.toString(),"AR Schedule","AR Schedule (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> getVendorTopTotalList(ARAPScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            System.out.println("filter : " + filter);
            List<APScheduleTopVendorTotalResponse> apScheduleTotalResponses = apScheduleMapper.getVendorTopTotalList(filter);

            System.out.println("apScheduleTotalResponses: " + apScheduleTotalResponses);
            if(apScheduleTotalResponses.size() > 0){
                for (int i = 0; i <apScheduleTotalResponses.size(); i++){
                    Long vendorId = apScheduleTotalResponses.get(i).getVendorId();
                    filter.setVendorId(vendorId);
                    List<APScheduleTopVendorTotalResponseDetails> apScheduleResponseDetails = apScheduleMapper.getVendorTopTotalDetails(filter);
                    apScheduleTotalResponses.get(i).setVendorDetails(apScheduleResponseDetails);
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/list",null,null,"AR Schedule","AR Schedule (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", apScheduleTotalResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/list",line, error.toString(),"AR Schedule","AR Schedule (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getVendorBookedTotalList(ARAPScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(apScheduleMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<APScheduleVendorBookedTotalResponse> apScheduleTotalResponses = apScheduleMapper.getVendorBookedTotalList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/list",null,null,"AR Schedule","AR Schedule (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", apScheduleTotalResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/list",line, error.toString(),"AR Schedule","AR Schedule (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> getVendorBookedTopTotalList(ARAPScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Chart of Account (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
//            pagination.setTotal(apScheduleMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<APScheduleTopVendorBookedTotalResponse> apScheduleTotalResponses = apScheduleMapper.getVendorBookedTopTotalList(filter);

            if(apScheduleTotalResponses.size() > 0){
                for (int i = 0; i <apScheduleTotalResponses.size(); i++){
                    Long vendorId = apScheduleTotalResponses.get(i).getVendorId();
                    filter.setVendorId(vendorId);
                    List<APScheduleTopVendorBookedTotalResponseDetails> apScheduleResponseDetails = apScheduleMapper.getVendorBookedTopTotalDetails(filter);
                    apScheduleTotalResponses.get(i).setVendorDetails(apScheduleResponseDetails);
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/list",null,null,"AR Schedule","AR Schedule (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", apScheduleTotalResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ap-schedule/list",line, error.toString(),"AR Schedule","AR Schedule (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}