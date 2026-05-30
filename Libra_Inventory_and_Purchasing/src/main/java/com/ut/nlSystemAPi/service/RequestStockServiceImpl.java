package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.CodeMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.PurchaseOrderMapper;
import com.ut.nlSystemAPi.mapper.primary.RequestStockMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.RequestStockFilter;
import com.ut.nlSystemAPi.model.request.Login.RequestStock.RequestStockApproveStatus;
import com.ut.nlSystemAPi.model.request.Login.RequestStock.RequestStockDetailRequest;
import com.ut.nlSystemAPi.model.request.Login.RequestStock.RequestStockRequest;
import com.ut.nlSystemAPi.model.request.Login.RequestStock.RequestStockUpdateRequest;
import com.ut.nlSystemAPi.model.response.Dropdown.RequestStockDetailDropdownResponse;
import com.ut.nlSystemAPi.model.response.RequestStock.RequestStockDetailResponse;
import com.ut.nlSystemAPi.model.response.RequestStock.RequestStockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class RequestStockServiceImpl implements RequestStockService {
    @Autowired
    private RequestStockMapper requestStockMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private GenerateCode generateCode;
    @Autowired
    private CodeMapper codeMapper;

    @Override
    public ResponseMessage<BaseResult> getList(RequestStockFilter requestStockFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Request (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            if (permissionMapper.checkPermission(userId, "Request (View By User)") > 0) {
                requestStockFilter.setViewByUser(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(requestStockFilter.getPage());
            pagination.setRowsPerPage(requestStockFilter.getRowsPerPage());
            pagination.setTotal(requestStockMapper.countList(requestStockFilter, userId));
            requestStockFilter.setPage((requestStockFilter.getPage() - 1) * requestStockFilter.getRowsPerPage());

            List<RequestStockResponse> requestStockResponses = requestStockMapper.getList(requestStockFilter, userId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Request stock/list", null, null, "Request stock", "Request stock(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", requestStockResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Request stock/list", line, error.toString(), "Request stock", "(Request stock (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
//             Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Request (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<RequestStockResponse> requestStockResponses = requestStockMapper.getOne(id, userId);
            if (!requestStockResponses.isEmpty()) {
                for (int i = 0; i < requestStockResponses.size(); i++) {
                    List<RequestStockDetailResponse> requestStockDetailResponses = requestStockMapper.getRequestStockDetail(requestStockResponses.get(i).getId());
                    requestStockResponses.get(i).setRequestStockDetailResponses(requestStockDetailResponses);
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/request stock/{id}", null, null, "System request stock", "System request stock (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", requestStockResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Request stock/find/{id}", line, error.toString(), "System request stock", "System request stock (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> insert(RequestStockRequest requestStockRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Request (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check Data
            RequestStock requestStock = new RequestStock();
            requestStock.setDate(requestStockRequest.getDate());
            requestStock.setCompanyId(requestStockRequest.getCompanyId());
            requestStock.setMemo(requestStockRequest.getMemo());
            requestStock.setFormLocationGroupId(requestStockRequest.getFromWarehouseId());
            requestStock.setToLocationGroupId(requestStockRequest.getToWarehouseId());
            requestStock.setCreatedBy(userId);
            requestStock.setIsApprove(0L);
            Boolean result = requestStockMapper.insert(requestStock);
            if (result) {
                //! Get the reference code
                String code = (generateCode.generateAutoCode("request_stocks", "code", 7, "RS", true, "status >= 0"));
                codeMapper.updateCode("request_stocks", "code", code, requestStock.getId());

                List<RequestStockDetailRequest> requestStockDetailRequests=requestStockRequest.getRequestStockDetails();
                for (int i = 0; i < requestStockDetailRequests.size(); i++) {
                    Long smallValUom = purchaseOrderMapper.getSmallValUom(requestStockRequest.getRequestStockDetails().get(i).getProductId());
                    RequestStockDetail requestStockDetail =new RequestStockDetail();
                    requestStockDetail.setRequestStockId(requestStock.getId());
                    requestStockDetail.setProductId(requestStockRequest.getRequestStockDetails().get(i).getProductId());
                    requestStockDetail.setQty(requestStockRequest.getRequestStockDetails().get(i).getQty());
                    requestStockDetail.setQtyUomId(requestStockRequest.getRequestStockDetails().get(i).getQtyUomId());
                    requestStockDetail.setConversion(smallValUom / requestStockRequest.getRequestStockDetails().get(i).getConversion());
                    requestStockMapper.insertRequestStockDetail(requestStockDetail);
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/Request Stock/add", null, null, "Request stock", "Request stock (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Request stock/add", line, error.toString(), "Request stock", "Request stock (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }

    }

    @Override
    public ResponseMessage<BaseResult> update(RequestStockUpdateRequest requestStockUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Request (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check Data
            RequestStock requestStock = new RequestStock();
            requestStock.setDate(requestStockUpdateRequest.getDate());
            requestStock.setCode(requestStockMapper.getCode(requestStockUpdateRequest.getId()));
            requestStock.setCompanyId(requestStockUpdateRequest.getCompanyId());
            requestStock.setNote(requestStockUpdateRequest.getMemo());
            requestStock.setFormLocationGroupId(requestStockUpdateRequest.getFromWarehouseId());
            requestStock.setToLocationGroupId(requestStockUpdateRequest.getToWarehouseId());
            requestStock.setMemo(requestStockUpdateRequest.getMemo());
            requestStock.setModifiedBy(userId);
            requestStockMapper.archive(requestStockUpdateRequest.getId(), userId);
            Boolean result = requestStockMapper.update(requestStock);
            if (result) {
                List<RequestStockDetailRequest> requestStockDetailRequests=requestStockUpdateRequest.getRequestStockDetails();
                for (int i = 0; i < requestStockDetailRequests.size(); i++) {
                    Long smallValUom = purchaseOrderMapper.getSmallValUom(requestStockUpdateRequest.getRequestStockDetails().get(i).getProductId());
                    RequestStockDetail requestStockDetail =new RequestStockDetail();
                    requestStockDetail.setRequestStockId(requestStock.getId());
                    requestStockDetail.setProductId(requestStockUpdateRequest.getRequestStockDetails().get(i).getProductId());
                    requestStockDetail.setQty(requestStockUpdateRequest.getRequestStockDetails().get(i).getQty());
                    requestStockDetail.setQtyUomId(requestStockUpdateRequest.getRequestStockDetails().get(i).getQtyUomId());
                    requestStockDetail.setConversion(smallValUom / requestStockUpdateRequest.getRequestStockDetails().get(i).getConversion());
                    requestStockMapper.insertRequestStockDetail(requestStockDetail);
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/Request stock/delete/{id}",null,null,"Request stock","Request stock (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/Request stock/update", line, error.toString(), "Request stock", "product Request stock (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Request (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = requestStockMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/Request stock/delete/{id}",null,null,"Request stock","Request stock (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/Request stock/delete/{id}",line, error.toString(),"Request stock","Request stock (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateApproveStatus(RequestStockApproveStatus requestStockApproveStatus, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Request Stock (Approve)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check Data

            RequestStockApproveStatus stockApprove = new RequestStockApproveStatus();
            stockApprove.setId(requestStockApproveStatus.getId());
            stockApprove.setStatus(requestStockApproveStatus.getStatus());
            stockApprove.setApprovedBy(userId);

            Boolean result = requestStockMapper.updateApproveStatus(stockApprove);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/Update approve status/delete/{id}",null,null,"Update approve request","Update approve request (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/Request stock/update", line, error.toString(), "Request stock", "product Request stock (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    public static String incrementNumericPart(String input) {
        // Use a regular expression to split the input into prefix and numeric parts
        String prefix = input.replaceAll("\\d+$", ""); // Extract prefix part
        String numericPart = input.substring(prefix.length()); // Extract numeric part

        // Convert numeric part to an integer
        int number = Integer.parseInt(numericPart);

        // Increment the number
        number += 1;

        // Determine the number of digits in the original numeric part
        int numericPartLength = numericPart.length();

        // Format the incremented number with leading zeros
        String incrementedNumericPart = String.format("%0" + numericPartLength + "d", number);

        // Concatenate the prefix and the incremented numeric part
        return prefix + incrementedNumericPart;
    }

    public static String generateReference(String input) {
        return input + "0000001";
    }

}


