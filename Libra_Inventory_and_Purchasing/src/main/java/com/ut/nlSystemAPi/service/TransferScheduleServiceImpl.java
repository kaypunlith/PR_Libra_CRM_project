package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ShipmentMapper;
import com.ut.nlSystemAPi.mapper.primary.TransferScheduleMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.Shipment;
import com.ut.nlSystemAPi.model.TransferSchedule;
import com.ut.nlSystemAPi.model.TransferScheduleDetail;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.TransferScheduleFilter;
import com.ut.nlSystemAPi.model.request.Login.Shipment.ShipmentRequest;
import com.ut.nlSystemAPi.model.request.Login.Shipment.ShipmentUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.TransferSchedule.TransferScheduleDetailRequest;
import com.ut.nlSystemAPi.model.request.Login.TransferSchedule.TransferScheduleRequest;
import com.ut.nlSystemAPi.model.request.Login.TransferSchedule.TransferScheduleUpdateRequest;
import com.ut.nlSystemAPi.model.response.Shipment.ShipementResponse;
import com.ut.nlSystemAPi.model.response.TransferScheduleResponse.TransferScheduleDetailResponse;
import com.ut.nlSystemAPi.model.response.TransferScheduleResponse.TransferScheduleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class TransferScheduleServiceImpl implements TransferScheduleService {
    @Autowired
    private TransferScheduleMapper transferScheduleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ResponseMessage<BaseResult> getList(TransferScheduleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Transfer Schedule (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (permissionMapper.checkPermission(userId, "Transfer Schedule (View By User)") > 0) {
                filter.setViewByUser(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(transferScheduleMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<TransferScheduleResponse> transferScheduleResponses = transferScheduleMapper.getList(filter, userId);

            if(transferScheduleResponses.size() > 0) {
                for (int i = 0; i < transferScheduleResponses.size(); i++) {
                    transferScheduleResponses.get(i).setTransferScheduleDetails(transferScheduleMapper.getTransferScheduleDetail(transferScheduleResponses.get(i).getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/transfer schedule/list", null, null, "transfer schedule", "transfer schedule(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", transferScheduleResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/transfer schedule/list", line, error.toString(), "transfer schedule", "transfer schedule(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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
          if (permissionMapper.checkPermission(userId, "Transfer Schedule (View)") == 0) {
            return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
          }

            List<TransferScheduleResponse> transferScheduleResponses = transferScheduleMapper.getOne(id);
            if(!transferScheduleResponses.isEmpty()) {
                for (int i = 0; i < transferScheduleResponses.size(); i++) {
                    transferScheduleResponses.get(i).setTransferScheduleDetails(transferScheduleMapper.getTransferScheduleDetail(transferScheduleResponses.get(i).getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/transfer schedule/find/{id}", null, null, "System transfer schedule", "System transfer schedule (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", transferScheduleResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/transfer schedule/find/{id}", line, error.toString(), "System transfer schedule", "System transfer schedule (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
//
    @Override
    public ResponseMessage<BaseResult> insert(TransferScheduleRequest transferScheduleRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Transfer Schedule (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            TransferSchedule transferSchedule=new TransferSchedule();
            transferSchedule.setStartDate(transferScheduleRequest.getStartDate());
            transferSchedule.setStartTime(transferScheduleRequest.getStartTime());
            transferSchedule.setEndDate(transferScheduleRequest.getEndDate());
            transferSchedule.setEndTime(transferScheduleRequest.getEndTime());
            transferSchedule.setTitle(transferScheduleRequest.getTitle());
            transferSchedule.setBackgroundColor(transferScheduleRequest.getBackgroundColor());
            transferSchedule.setPrivacy(transferScheduleRequest.getPrivacy());
            transferSchedule.setIsClose(0L);
            transferSchedule.setCreatedBy(userId);
            transferSchedule.setIsActive(1);
            Boolean result = transferScheduleMapper.insert(transferSchedule);
            if (result) {
                if(!transferScheduleRequest.getTransferScheduleDetails().isEmpty()) {
                    TransferScheduleDetail transferScheduleDetail = new TransferScheduleDetail();
                    for(int i=0;i<transferScheduleRequest.getTransferScheduleDetails().size();i++){
                        transferScheduleDetail.setTransferScheduleId(transferSchedule.getId());
                        transferScheduleDetail.setPvRequestId(transferScheduleRequest.getTransferScheduleDetails().get(i).getPvRequestId());
                        transferScheduleDetail.setIsActive(1);
                        transferScheduleMapper.insertTransferScheduleDetail(transferScheduleDetail);
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/transfer schedule/add", null, null, "transfer schedule", "transfer schedule (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/transfer schedule/add", line, error.toString(), "transfer schedule", "transfer schedule (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
  @Override
    public ResponseMessage<BaseResult> update(TransferScheduleUpdateRequest transferScheduleUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Transfer Schedule (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            TransferSchedule transferSchedule=new TransferSchedule();
            transferSchedule.setId(transferScheduleUpdateRequest.getId());
            transferSchedule.setStartDate(transferScheduleUpdateRequest.getStartDate());
            transferSchedule.setStartTime(transferScheduleUpdateRequest.getStartTime());
            transferSchedule.setEndDate(transferScheduleUpdateRequest.getEndDate());
            transferSchedule.setEndTime(transferScheduleUpdateRequest.getEndTime());
            transferSchedule.setTitle(transferScheduleUpdateRequest.getTitle());
            transferSchedule.setBackgroundColor(transferScheduleUpdateRequest.getBackgroundColor());
            transferSchedule.setPrivacy(transferScheduleUpdateRequest.getPrivacy());
            transferSchedule.setModifiedBy(userId);
            Boolean result = transferScheduleMapper.update(transferSchedule);
            LocalTime endDuration = LocalTime.now();

            if (result) {
                List<TransferScheduleDetailRequest> transferScheduleDetailRequests = transferScheduleUpdateRequest.getTransferScheduleDetails();
                   transferScheduleMapper.deleteTransferDetail(transferScheduleUpdateRequest.getId());
                for(int i=0;i<transferScheduleDetailRequests.size();i++){
                    TransferScheduleDetail transferScheduleDetail=new TransferScheduleDetail();
                    transferScheduleDetail.setTransferScheduleId(transferSchedule.getId());
                    transferScheduleDetail.setPvRequestId(transferScheduleUpdateRequest.getTransferScheduleDetails().get(i).getPvRequestId());
                    transferScheduleDetail.setIsActive(1);
                    transferScheduleMapper.insertTransferScheduleDetail(transferScheduleDetail);
                }

                activityLogService.insert("/transfer schedule/update", null, null, "transfer schedule", "transfer schedule (Update)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/shipment/update", line, error.toString(), "shipment", "shipment (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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
              if (permissionMapper.checkPermission(userId, "Transfer Schedule (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            Boolean result = transferScheduleMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/transfer schedule/delete/{id}",null,null,"transfer schedule","transfer schedule (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/transfer schedule/delete/{id}",line, error.toString(),"transfer schedule","transfer schedule (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


}
