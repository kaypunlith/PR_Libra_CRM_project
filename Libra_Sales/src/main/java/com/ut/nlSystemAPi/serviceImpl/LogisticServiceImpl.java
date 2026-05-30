package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.HelperMapper;
import com.ut.nlSystemAPi.mapper.primary.LogisticMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Logistic.LogisticReceive;
import com.ut.nlSystemAPi.model.entity.Logistic.LogisticReceiveResult;
import com.ut.nlSystemAPi.model.entity.Logistic.Logistics;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.filter.LogisticFilter;
import com.ut.nlSystemAPi.model.request.Logistic.LogisticDeliveryRequest;
import com.ut.nlSystemAPi.model.request.Logistic.LogisticMergeDetailRequest;
import com.ut.nlSystemAPi.model.request.Logistic.LogisticMergeRequest;
import com.ut.nlSystemAPi.model.response.Logistic.LogisticDetailResponse;
import com.ut.nlSystemAPi.model.response.Logistic.LogisticResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.LogisticService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogisticServiceImpl implements LogisticService {

    @Autowired
    private LogisticMapper logisticMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private GenerateCode generateCode;

    @Autowired
    private HelperMapper helperMapper;

    @Override
    public ResponseMessage<BaseResult> getList(LogisticFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Logistics (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(logisticMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<LogisticResponse> responses = logisticMapper.getList(filter);

            if (!responses.isEmpty()) {
                for (LogisticResponse response : responses) {
                    if (response.getStatusLogistic() != null && response.getStatusInvoice() != null) {
                        if (response.getStatusInvoice() == 2 && response.getStatusLogistic() > 0) {
                            response.setStatus(1);
                        }
                        else if (response.getStatusInvoice() == -5 && response.getStatusLogistic() == 2) {
                            response.setStatus(2);
                        }
                        else if (response.getStatusInvoice() == -5 && response.getStatusLogistic() == 3) {
                            response.setStatus(3);
                        }
                    } else {
                        response.setStatus(1);
                    }
                    // Remove temporary status
                    response.setStatusLogistic(null);
                    response.setStatusInvoice(null);
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/logistic/list", null, null, "Logistics", "Logistics (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/logistic/list", line, error.toString(), "Logistics", "Logistics (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Logistics (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<LogisticResponse> responses = logisticMapper.getOne(id);

            if(!responses.isEmpty()){
                List<LogisticDetailResponse> combinedList = new ArrayList<>();
                for (LogisticResponse response : responses) {
                    List<Long> salesInvoiceIds = logisticMapper.getSalesInvoiceIds(response.getId());
                    List<LogisticDetailResponse> alreadyDelivery = logisticMapper.getDetailDelivered(salesInvoiceIds);
                    combinedList.addAll(alreadyDelivery);
                    response.setDetails(combinedList);
                }

            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/logistic/find/{id}", null, null, "Logistics", "Logistics (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/logistic/find/{id}", line, error.toString(), "Logistics", "Logistics (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> merge(LogisticMergeRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            //  Merge  //
            if (request.getOperationType() == 1) {
                if (permissionMapper.checkPermission(userId, "Logistics (Merge)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }

                Logistics logistic = new Logistics();
                logistic.setCompanyId(request.getCompanyId());
                logistic.setOrganizationGroupId(request.getOrganizationGroupId());
                logistic.setOrganizationId(request.getOrganizationId());
                logistic.setDate(request.getDate());
                logistic.setCreatedBy(userId);

                Boolean result = logisticMapper.insert(logistic);

                if (result) {

                    //! Get the reference code
                    String code = (generateCode.generateAutoCode("logistics", "lg_code", 7, "LG", true, "statusInvoice >= 0"));
                    helperMapper.updateCode("logistics", "lg_code", code, logistic.getId());

                    if (request.getDetails() != null && !request.getDetails().isEmpty()){
                        for (LogisticMergeDetailRequest detail : request.getDetails()) {
                            if (detail.getType() == 1){
                                logisticMapper.insertDetail(logistic.getId(), detail.getId());
                            } else {
                                helperMapper.archive("logistics", "statusInvoice", 0,  detail.getId(), userId);

                                // Get All Invoices Merging
                                List<Long> salesInvoiceIds = logisticMapper.getSalesInvoiceIds(detail.getId());
                                for (Long salesInvoiceId : salesInvoiceIds) {
                                    logisticMapper.insertDetail(logistic.getId(), salesInvoiceId);
                                }
                            }
                        }
                    }

                    /*System Activity*/
                    LocalTime endDuration = LocalTime.now();
                    activityLogService.insert("/logistic/merge",null,null,"Logistics","Logistics (Merge)","Merge",1,"Success",startDuration,endDuration, httpServletRequest);
                    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
                } else {
                    return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
                }




            //  Unmerge  //
            } else {
                if (permissionMapper.checkPermission(userId, "Logistics (UnMerge)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }

                Boolean result = false;

                if (request.getDetails() != null && !request.getDetails().isEmpty()){
                    for (LogisticMergeDetailRequest detail : request.getDetails()) {
                        if (detail.getType() == 2){
                            result = helperMapper.archive("logistics", "statusInvoice", 0, detail.getId(), userId);
                        } else {
                            return ResponseMessageUtils.makeResponse(false, messageService.message("Wrong Type Parameter.", false));
                        }

                    }
                }

                if (result) {
                    /*System Activity*/
                    LocalTime endDuration = LocalTime.now();
                    activityLogService.insert("/logistic/merge",null,null,"Logistics","Logistics (UnMerge)","UnMerge",1,"Success",startDuration,endDuration, httpServletRequest);
                    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
                } else {
                    return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
                }
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/logistic/merge",line, error.toString(),"Logistics","Logistics (Merge)","Merge",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOneDelivery(Long id, Long type, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Logistics (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<LogisticResponse> responses = logisticMapper.getOneDelivery(id, type);

            if(!responses.isEmpty()){

                List<LogisticDetailResponse> combinedList = new ArrayList<>();
                for (LogisticResponse response : responses) {
                    if (type == 2){
                        List<Long> salesInvoiceIds = logisticMapper.getSalesInvoiceIds(response.getId());

                        List<LogisticDetailResponse> alreadyDelivery = logisticMapper.getDetailDelivered(salesInvoiceIds);

                        List<LogisticDetailResponse> notYetDelivery = logisticMapper.getDetailNotYetDelivery(salesInvoiceIds);

                        List<LogisticDetailResponse> remaining = logisticMapper.getDetailRemaining(salesInvoiceIds);

                        combinedList.addAll(alreadyDelivery);
                        combinedList.addAll(remaining);
                        combinedList.addAll(notYetDelivery);

                    } else {
                        List<Long> salesInvoiceIds = Collections.singletonList(response.getId());

                        List<LogisticDetailResponse> alreadyDelivery = logisticMapper.getDetailDelivered(salesInvoiceIds);

                        List<LogisticDetailResponse> notYetDelivery = logisticMapper.getDetailNotYetDelivery(salesInvoiceIds);

                        List<LogisticDetailResponse> remaining = logisticMapper.getDetailRemaining(salesInvoiceIds);

                        combinedList.addAll(alreadyDelivery);
                        combinedList.addAll(remaining);
                        combinedList.addAll(notYetDelivery);
                    }
                    response.setDetails(combinedList);
                }

            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/logistic/find/{id}", null, null, "Logistics", "Logistics (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/logistic/find/{id}", line, error.toString(), "Logistics", "Logistics (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delivery(LogisticDeliveryRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Logistics (Delivery)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }


            //  Global Initialization
            boolean result = false;
            String saleInvoiceIdString = null;
            String saleInvoiceDetailIdString = null;
            long totalQtyReceive = 0;
            long totalQtyOrder = 0;
            int statusInvoice;
            int statusLogistic;
            List<Long> salesInvoiceIds;
            List<Long> salesInvoiceDetailIds;
            LogisticReceiveResult logisticReceiveResult = new LogisticReceiveResult();
            LogisticReceive logisticReceive = new LogisticReceive();
            Logistics logistic = new Logistics();
            String organizationContactIdString = request.getOrganizationContactId()
                    .stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));;





            if (request.getType() == 1){

                salesInvoiceIds = Collections.singletonList(request.getId());

                salesInvoiceDetailIds = logisticMapper.getSalesInvoiceDetailIds(Collections.singletonList(request.getId()));

                if (salesInvoiceDetailIds != null && !salesInvoiceDetailIds.isEmpty()) {
                    saleInvoiceIdString = salesInvoiceIds
                            .stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
                    saleInvoiceDetailIdString = salesInvoiceDetailIds
                            .stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
                }

                logistic.setCompanyId(request.getCompanyId());
                logistic.setOrganizationGroupId(request.getOrganizationGroupId());
                logistic.setOrganizationId(request.getOrganizationId());
                logistic.setDate(request.getDate());
                logistic.setCreatedBy(userId);
                Boolean logisticInsert = logisticMapper.insert(logistic);

                if (logisticInsert){
                    //! Get the reference code
                    String code = (generateCode.generateAutoCode("logistics", "lg_code", 7, "LG", true, "status >= 0"));
                    helperMapper.updateCode("logistics", "lg_code", code, logistic.getId());
                    for (Long salesInvoiceId : salesInvoiceIds) {
                        logisticMapper.insertDetail(logistic.getId(), salesInvoiceId);
                    }
                }

                logisticReceiveResult.setSaleInvoiceId(saleInvoiceIdString);
                logisticReceiveResult.setOrganizationContactId(organizationContactIdString);
                logisticReceiveResult.setNote(request.getNote());
                logisticReceiveResult.setCreatedBy(userId);
                logisticReceiveResult.setDate(request.getDate());
                Boolean receiveResult = logisticMapper.insertReceiveResult(logisticReceiveResult);

                if (receiveResult){
                    //! Get the reference code
                    String code = (generateCode.generateAutoCode("logistic_receive_results", "code", 7, "LGR", true, null));
                    helperMapper.updateCode("logistic_receive_results", "code", code, logisticReceiveResult.getId());

                    if (!request.getDetails().isEmpty()) {
                        for (int i = 0; i < request.getDetails().size(); i++) {
                            logisticReceive.setLogisticId(logistic.getId());
                            logisticReceive.setLogisticReceiveResultId(logisticReceiveResult.getId());
                            logisticReceive.setSaleInvoiceId(saleInvoiceIdString);
                            logisticReceive.setType(request.getDetails().get(i).getType());
                            logisticReceive.setItemId(request.getDetails().get(i).getItemId());
                            logisticReceive.setSaleInvoiceDetailId(saleInvoiceDetailIdString);
                            logisticReceive.setQty(request.getDetails().get(i).getQty());
                            logisticReceive.setUomId(request.getDetails().get(i).getUomId());
                            logisticReceive.setConversion(request.getDetails().get(i).getConversion());
                            logisticReceive.setDeliveryDate(request.getDetails().get(i).getDeliveryDate());
                            logisticReceive.setCreatedBy(userId);
                            logisticMapper.insertReceive(logisticReceive);

                            Long qtyDelivery = logisticMapper.getTotalQtyDelivery(request.getDetails().get(i).getItemId(), saleInvoiceIdString);
                            System.out.println("qty deliver " +  qtyDelivery);
                            Long qtyOrder = logisticMapper.getTotalQtyOrder(request.getDetails().get(i).getItemId(), salesInvoiceIds);
                            System.out.println("qty order " + qtyOrder);
                            totalQtyReceive += (qtyDelivery != null ? qtyDelivery : 0);

                            totalQtyOrder += (qtyOrder != null ? qtyOrder : 0);

                        }
                        System.out.println("total order " +  totalQtyOrder);
                        System.out.println("total receive " + totalQtyReceive);

                         if (totalQtyReceive < totalQtyOrder) {
                            // Partial
                            System.out.println("partial");
                            statusInvoice = -5;
                            statusLogistic = 2;
                        } else if (totalQtyReceive == totalQtyOrder) {
                            // Fulfilled
                            System.out.println("fulfilled");
                            statusInvoice = -5;
                            statusLogistic = 3;
                        } else {
                            // Issued
                            System.out.println("issued");
                            statusInvoice = 2;
                            statusLogistic = 1;
                        }

                        logisticMapper.updateStatus(statusInvoice, salesInvoiceIds);
                        logisticMapper.updateStatusLogistic(statusLogistic, logistic.getId());
                        result = true;
                    }
                }
            }


            else {

                salesInvoiceIds = logisticMapper.getSalesInvoiceIds(request.getId());
                salesInvoiceDetailIds = logisticMapper.getSalesInvoiceDetailIds(salesInvoiceIds);
                if (salesInvoiceIds!= null && !salesInvoiceIds.isEmpty() && salesInvoiceDetailIds!= null && !salesInvoiceDetailIds.isEmpty()) {
                    saleInvoiceIdString = salesInvoiceIds
                            .stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
                    saleInvoiceDetailIdString = salesInvoiceDetailIds
                            .stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
                }

                logisticReceiveResult.setSaleInvoiceId(saleInvoiceIdString);
                logisticReceiveResult.setOrganizationContactId(organizationContactIdString);
                logisticReceiveResult.setNote(request.getNote());
                logisticReceiveResult.setCreatedBy(userId);
                logisticReceiveResult.setDate(request.getDate());
                Boolean receiveResult = logisticMapper.insertReceiveResult(logisticReceiveResult);

                if (receiveResult){

                    //! Get the reference code
                    String code = (generateCode.generateAutoCode("logistic_receive_results", "code", 7, "LGR", true, null));
                    helperMapper.updateCode("logistic_receive_results", "code", code, logisticReceiveResult.getId());

                    if (!request.getDetails().isEmpty()) {
                        for (int i = 0; i < request.getDetails().size(); i++) {
                            logisticReceive.setLogisticId(request.getId());
                            logisticReceive.setLogisticReceiveResultId(logisticReceiveResult.getId());
                            logisticReceive.setSaleInvoiceId(saleInvoiceIdString);
                            logisticReceive.setType(request.getDetails().get(i).getType());
                            logisticReceive.setItemId(request.getDetails().get(i).getItemId());
                            logisticReceive.setSaleInvoiceDetailId(saleInvoiceDetailIdString);
                            logisticReceive.setQty(request.getDetails().get(i).getQty());
                            logisticReceive.setUomId(request.getDetails().get(i).getUomId());
                            logisticReceive.setConversion(request.getDetails().get(i).getConversion());
                            logisticReceive.setDeliveryDate(request.getDetails().get(i).getDeliveryDate());
                            logisticReceive.setCreatedBy(userId);
                            logisticMapper.insertReceive(logisticReceive);

                            Long qtyDelivery = logisticMapper.getTotalQtyDelivery(request.getDetails().get(i).getItemId(), saleInvoiceIdString);

                            Long qtyOrder = logisticMapper.getTotalQtyOrder(request.getDetails().get(i).getItemId(), salesInvoiceIds);

                            totalQtyReceive += (qtyDelivery != null ? qtyDelivery : 0);

                            totalQtyOrder += (qtyOrder != null ? qtyOrder : 0);
                        }

                        if (totalQtyReceive == 0) {
                            // Issued
                            statusInvoice = 2;
                            statusLogistic = 1;
                        } else if (totalQtyReceive < totalQtyOrder) {
                            // Partial
                            statusInvoice = -5;
                            statusLogistic = 2;
                        } else {
                            // Fulfilled
                            statusInvoice = -5;
                            statusLogistic = 3;
                        }
                        logisticMapper.updateStatus(statusInvoice, salesInvoiceIds);
                        logisticMapper.updateStatusLogistic(statusLogistic, logistic.getId());
                        result = true;
                    }
                }
            }
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/logistic/delivery", null, null, "Logistics", "Logistics (Delivery)", "Delivery", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/logistic/delivery", line, error.toString(), "Logistics", "Logistics (Delivery)", "Delivery", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


}
