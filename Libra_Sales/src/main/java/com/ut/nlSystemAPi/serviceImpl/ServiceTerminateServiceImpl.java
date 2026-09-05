package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Service.Service;
import com.ut.nlSystemAPi.model.entity.ServiceTerminate.ServiceTerminate;
import com.ut.nlSystemAPi.model.entity.ServiceTerminate.ServiceTerminateDetail;
import com.ut.nlSystemAPi.model.filter.ServiceFilter;
import com.ut.nlSystemAPi.model.filter.ServiceTerminateFilter;
import com.ut.nlSystemAPi.model.request.Service.ServiceRequest;
import com.ut.nlSystemAPi.model.request.Service.ServiceUpdateRequest;
import com.ut.nlSystemAPi.model.request.serviceTerminate.ServiceTerminateRequest;
import com.ut.nlSystemAPi.model.request.serviceTerminate.ServiceUpdateTerminateRequest;
import com.ut.nlSystemAPi.model.response.Service.ServiceResponse;
import com.ut.nlSystemAPi.model.response.Service.ServiceShiftResponse;
import com.ut.nlSystemAPi.model.response.serviceTerminateResponse.ServiceTerminateDetailResponse;
import com.ut.nlSystemAPi.model.response.serviceTerminateResponse.ServiceTerminateResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.ServiceService;
import com.ut.nlSystemAPi.service.ServiceTerminateService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceTerminateServiceImpl implements ServiceTerminateService {

    @Autowired
    private ServiceTerminateMapper serviceTerminateMapper;

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

    public ResponseMessage<BaseResult> getList(ServiceTerminateFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Service (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(serviceTerminateMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ServiceTerminateResponse> responses = serviceTerminateMapper.getList(filter);
            if (!responses.isEmpty()) {
                for (int i = 0; i < responses.size(); i++) {

                    List<Long> serviceIds = serviceTerminateMapper.getServiceIdByServiceTerminate(responses.get(i).getId());

                    if (serviceIds != null && !serviceIds.isEmpty()) {
                        List<ServiceTerminateDetailResponse> allDetails = new ArrayList<>();
                        for (Long serviceId : serviceIds) {
                            List<ServiceTerminateDetailResponse> details = serviceTerminateMapper.getServiceTerminate(serviceId, responses.get(i).getId());
                            if (details != null) {
                                allDetails.addAll(details);
                            }
                        }
                        responses.get(i).setDetails(allDetails);
                    }
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/service/list", null, null, "Service", "Service (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/service/list", line, error.toString(), "Service", "Service (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    public ResponseMessage<BaseResult> getListCustomerQuotation(Long customerId, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Service (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<ServiceTerminateDetailResponse> responses = serviceTerminateMapper.getListCustomerQuotation(customerId);

            List<ServiceTerminateDetailResponse> reductions = serviceTerminateMapper.getReductionsByCustomer(customerId);
            if (reductions != null && !reductions.isEmpty()) {
                for (ServiceTerminateDetailResponse response : responses) {
                    for (ServiceTerminateDetailResponse reduction : reductions) {
                        if (response.getServiceId() != null && response.getServiceId().equals(reduction.getServiceId())) {
                            response.setSku(response.getSku() - reduction.getSku());
                        }
                    }
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/service/find/{id}", null, null, "Service", "Service (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/service/find/{id}", line, error.toString(), "Service", "Service (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Service (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<ServiceTerminateResponse> responses = serviceTerminateMapper.getOne(id);
            if (!responses.isEmpty()) {
                for (int i = 0; i < responses.size(); i++) {
                    List<Long> serviceIds = serviceTerminateMapper.getServiceIdByServiceTerminate(responses.get(i).getId());

                    if (serviceIds != null && !serviceIds.isEmpty()) {
                        List<ServiceTerminateDetailResponse> allDetails = new ArrayList<>();
                        for (Long serviceId : serviceIds) {
                            List<ServiceTerminateDetailResponse> details = serviceTerminateMapper.getServiceTerminate(serviceId, responses.get(i).getId());
                            if (details != null) {
                                allDetails.addAll(details);
                            }
                        }
                        responses.get(i).setDetails(allDetails);
                    }
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/service/find/{id}", null, null, "Service", "Service (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/service/find/{id}", line, error.toString(), "Service", "Service (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(ServiceTerminateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Service (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }


            // Check Data
            ServiceTerminate service = new ServiceTerminate();
            service.setCompanyId(1L);
            service.setCustomerId(request.getCustomerId());
            service.setType(request.getType());
            service.setDate(request.getDate());
            service.setReason(request.getReason());
            service.setCreatedBy(userId);
            service.setIsActive(1);
            Boolean result = serviceTerminateMapper.insert(service);

            if (result) {
                if(request.getDetailRequests().size() > 0){
                    for(int i=0;i<request.getDetailRequests().size();i++){
                        ServiceTerminateDetail serviceTerminateDetail = new ServiceTerminateDetail();
                        serviceTerminateDetail.setId(service.getId());
                        serviceTerminateDetail.setServiceId(request.getDetailRequests().get(i).getServiceId());
                        serviceTerminateDetail.setStatus(request.getDetailRequests().get(i).getStatus());
                        serviceTerminateDetail.setSku(request.getDetailRequests().get(i).getSku());
                        serviceTerminateDetail.setCreatedBy(userId);
                        serviceTerminateDetail.setIsActive(1);
                        serviceTerminateMapper.insertServiceTerminateDetail(serviceTerminateDetail);
                    }
                }



                if(request.getCustomerId() != null){
                    Long quotationId = serviceTerminateMapper.getQuotationIdByCustomer(request.getCustomerId());
                }
//                //insert quotation service
//                service.setQuotationId(quotation.getId());
//                service.setItemId(quotationDetailRequest.getItemId());
//                service.setQty(quotationDetailRequest.getQty());
//                service.setConversion(quotationDetailRequest.getConversion());
//                service.setDiscountId(quotationDetailRequest.getDiscountId());
//                service.setDiscountAmount(quotationDetailRequest.getDiscountAmount());
//                service.setDiscountPercent(quotationDetailRequest.getDiscountPercent());
//                service.setUnitPrice(quotationDetailRequest.getUnitPrice());
//                service.setTotalPrice(quotationDetailRequest.getTotalPrice());
//                quotationMapper.insertService(service);
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/service/add", null, null, "Service", "Service (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/service/add", line, error.toString(), "Service", "Service (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(ServiceUpdateTerminateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Service (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }


            // Check Data
            ServiceTerminate service = new ServiceTerminate();
            service.setId(request.getId());
            service.setCompanyId(1L);
            service.setCustomerId(request.getCustomerId());
            service.setType(request.getType());
            service.setDate(request.getDate());
            service.setReason(request.getReason());
            service.setModifiedBy(userId);
            service.setIsActive(1);
            Boolean result = serviceTerminateMapper.update(service);

            if (result) {
                   serviceTerminateMapper.deleteServiceTerminate(service.getId());
                if(request.getDetailRequests().size() > 0){
                    for(int i=0;i<request.getDetailRequests().size();i++){
                        ServiceTerminateDetail serviceTerminateDetail = new ServiceTerminateDetail();
                        serviceTerminateDetail.setId(service.getId());
                        serviceTerminateDetail.setServiceId(request.getDetailRequests().get(i).getServiceId());
                        serviceTerminateDetail.setStatus(request.getDetailRequests().get(i).getStatus());
                        serviceTerminateDetail.setSku(request.getDetailRequests().get(i).getSku());
                        serviceTerminateDetail.setCreatedBy(userId);
                        serviceTerminateDetail.setIsActive(1);
                        serviceTerminateMapper.insertServiceTerminateDetail(serviceTerminateDetail);
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/service/update", null, null, "Service", "Service (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/service/update", line, error.toString(), "Service", "Service (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Service (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = serviceTerminateMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/service/delete/{id}", null, null, "Service", "Service (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/service/delete/{id}", line, error.toString(), "Service", "Service (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}