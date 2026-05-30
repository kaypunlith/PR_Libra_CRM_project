package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.helper.TelegramUtils;
import com.ut.nlSystemAPi.mapper.primary.CodeMapper;
import com.ut.nlSystemAPi.mapper.primary.DropdownMapper;
import com.ut.nlSystemAPi.mapper.primary.ExpenseRequestMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.utscrum.ScrumMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.Telegram.TelegramResponse;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.DepartmentFilter;
import com.ut.nlSystemAPi.model.filter.ExpenseRequestFilter;
import com.ut.nlSystemAPi.model.request.Login.ExpenseRequest.*;
import com.ut.nlSystemAPi.model.response.Dropdown.DepartmentDropDownResponse;
import com.ut.nlSystemAPi.model.response.ExpenseRequest.ExpenseRequestApproveResponse;
import com.ut.nlSystemAPi.model.response.ExpenseRequest.ExpenseRequestDetailResponse;
import com.ut.nlSystemAPi.model.response.ExpenseRequest.ExpenseRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class ExpenseRequestServiceImpl implements ExpenseRequestService {

    @Autowired
    private ScrumMapper scrumMapper;

    @Autowired
    private ExpenseRequestMapper expenseRequestMapper;

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

    @Autowired
    private GenerateCode generateCode;
    @Autowired
    private CodeMapper codeMapper;
    @Autowired
    private DropdownMapper dropdownMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseMessage<BaseResult> getList(ExpenseRequestFilter expenseRequestFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {

            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Expense Request (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (permissionMapper.checkPermission(userId, "Expense Request (View By User)") > 0) {
                expenseRequestFilter.setViewByUser(1L);
            }
            if (permissionMapper.checkPermission(userId, "Expense Request (Refer Sales Order)") > 0) {
                expenseRequestFilter.setIsReferSaleOrder(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(expenseRequestFilter.getPage());
            pagination.setRowsPerPage(expenseRequestFilter.getRowsPerPage());
            pagination.setTotal(expenseRequestMapper.countList(expenseRequestFilter, userId));

            expenseRequestFilter.setPage((expenseRequestFilter.getPage() - 1) * expenseRequestFilter.getRowsPerPage());

            List<ExpenseRequestResponse> expenseRequestResponses = expenseRequestMapper.getList(expenseRequestFilter, userId);
            if (!expenseRequestResponses.isEmpty()) {
                DepartmentFilter departmentFilter = new DepartmentFilter();
                for (int i = 0; i < expenseRequestResponses.size(); i++) {
                    departmentFilter.setId(expenseRequestResponses.get(i).getDepartmentId());
                    List<DepartmentDropDownResponse> department = dropdownMapper.getListDepartment(departmentFilter);
                    expenseRequestResponses.get(i).setDepartmentName(department.get(0).getName());
                }
            }
//
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/expense-request/list", null, null, "Expense Request", "Expense Request (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", expenseRequestResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/expense-request/list", line, error.toString(), "Expense Request", "Expense Request (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, ExpenseRequestFilter expenseRequestFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Expense Request (view)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (permissionMapper.checkPermission(userId, "Expense Request (Refer Sales Order)") > 0) {
                expenseRequestFilter.setIsReferSaleOrder(1L);
            }

            List<ExpenseRequestResponse> expenseRequestResponses = expenseRequestMapper.getOne(id, userId);
            if (!expenseRequestResponses.isEmpty()){
                for(int i=0;i<expenseRequestResponses.size();i++){
                    List<ExpenseRequestDetailResponse> expenseRequestDetailResponses = expenseRequestMapper.getListReceiptDetail(expenseRequestResponses.get(i).getId());
                    expenseRequestResponses.get(i).setDetails(expenseRequestDetailResponses);
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/expense-request/find/{id}", null, null, "Expense Request", "Expense Request (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", expenseRequestResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/expense-request/find/{id}", line, error.toString(), "Expense Request", "Expense Request (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
//
    @Override
    public ResponseMessage<BaseResult> insert(ExpenseRequestRequest expenseRequestRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Expense Request (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            ExpenseRequest expenseRequest = new ExpenseRequest();
            expenseRequest.setCompanyId(expenseRequestRequest.getCompanyId());
            expenseRequest.setVendorId(expenseRequestRequest.getVendorId());
            expenseRequest.setErDate(expenseRequestRequest.getErDate());
            expenseRequest.setTotalVat(expenseRequestRequest.getTotalVat());
            expenseRequest.setVatSettingId(expenseRequestRequest.getVatSettingId());
            expenseRequest.setDepartmentId(expenseRequestRequest.getDepartmentId());
            expenseRequest.setTotalAmount(expenseRequestRequest.getTotalAmount());
            expenseRequest.setExpectedErDate(expenseRequestRequest.getExpectedErDate());
            expenseRequest.setIsCashAdvance(expenseRequestRequest.getIsCashAdvance());
            expenseRequest.setRefDocName(expenseRequestRequest.getRefDoc().getName());
            expenseRequest.setRefDocUrl(expenseRequestRequest.getRefDoc().getUrl());
            expenseRequest.setNote(expenseRequestRequest.getNote());
            expenseRequest.setCreatedBy(userId);
            expenseRequest.setStatus(1);

            Boolean result = expenseRequestMapper.insert(expenseRequest);

            if (result) {
            //! Get the reference code
             String code = (generateCode.generateAutoCode("pv_requests", "code", 7, "", true, "status != -1"));
             codeMapper.updateCode("pv_requests", "code", code, expenseRequest.getId());

            if (expenseRequestRequest.getPurchaseId() != null && expenseRequestRequest.getPurchaseType() != null){
                if (expenseRequestRequest.getPurchaseType() == 3) {
                    expenseRequestMapper.updatePurchaseBill(expenseRequest.getId(), expenseRequestRequest.getPurchaseId());
                    expenseRequest.setPurchaseType(expenseRequestRequest.getPurchaseType());
                    expenseRequest.setPurchaseId(expenseRequestRequest.getPurchaseId());
                    expenseRequestMapper.insertPurchaseExpenseRequest(expenseRequest);
                } else {
                    expenseRequest.setPurchaseType(expenseRequestRequest.getPurchaseType());
                    expenseRequest.setPurchaseId(expenseRequestRequest.getPurchaseId());
                    expenseRequestMapper.insertPurchaseExpenseRequest(expenseRequest);
                    expenseRequestMapper.updatePurchaseOrder(expenseRequest.getId(), expenseRequestRequest.getPurchaseId());
                }
            }

             List<PvRequestDetailsRequest> pvRequestDetailsRequests = expenseRequestRequest.getDetails();
             if (!pvRequestDetailsRequests.isEmpty()) {
                 for (int i = 0; i < pvRequestDetailsRequests.size(); i++) {
                     if(pvRequestDetailsRequests.get(i).getType() == 1){
                             ExpenseRequestDetail expenseRequestDetail = new ExpenseRequestDetail();
                             expenseRequestDetail.setPvRequestId(expenseRequest.getId());
                             expenseRequestDetail.setProductId(pvRequestDetailsRequests.get(i).getItemId());
                             expenseRequestDetail.setNote(pvRequestDetailsRequests.get(i).getNote());
                             expenseRequestDetail.setQty(pvRequestDetailsRequests.get(i).getQty());
                             expenseRequestDetail.setQtyUomId(pvRequestDetailsRequests.get(i).getUomId());
                             expenseRequestDetail.setUnitCost(pvRequestDetailsRequests.get(i).getUnitCost());
                             expenseRequestDetail.setTotalCost(pvRequestDetailsRequests.get(i).getTotalCost());
                             expenseRequestDetail.setConversion(pvRequestDetailsRequests.get(i).getConversion());
                             expenseRequestMapper.insertPvRequestDetail(expenseRequestDetail);

                     } else if(pvRequestDetailsRequests.get(i).getType() == 2){
                             ExpenseRequestServiceModel expenseRequestServiceModel =new ExpenseRequestServiceModel();
                             expenseRequestServiceModel.setPvRequestId(expenseRequest.getId());
                             expenseRequestServiceModel.setServiceId(pvRequestDetailsRequests.get(i).getItemId());
                             expenseRequestServiceModel.setNote(pvRequestDetailsRequests.get(i).getNote());
                             expenseRequestServiceModel.setQty(pvRequestDetailsRequests.get(i).getQty());
                             expenseRequestServiceModel.setUnitCost(pvRequestDetailsRequests.get(i).getUnitCost());
                             expenseRequestServiceModel.setTotalCost(pvRequestDetailsRequests.get(i).getTotalCost());
                             expenseRequestMapper.insertPvRequestService(expenseRequestServiceModel);
                     }
                 }
             }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/expense-request/add", null, null, "Expense Request", "Expense Request (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/expense-request/add", line, error.toString(), "Expense Request", "Expense Request (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

  @Override
    public ResponseMessage<BaseResult> update(ExpenseRequestUpdateRequest expenseRequestUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Expense Request (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            ExpenseRequest expenseRequest = new ExpenseRequest();
            expenseRequest.setCompanyId(expenseRequestUpdateRequest.getCompanyId());
            expenseRequest.setCode(expenseRequestMapper.getPvCode(expenseRequestUpdateRequest.getId()));
            expenseRequest.setVendorId(expenseRequestUpdateRequest.getVendorId());
            expenseRequest.setErDate(expenseRequestUpdateRequest.getErDate());
            expenseRequest.setTotalVat(expenseRequestUpdateRequest.getTotalVat());
            expenseRequest.setVatSettingId(expenseRequestUpdateRequest.getVatSettingId());
            expenseRequest.setDepartmentId(expenseRequestUpdateRequest.getDepartmentId());
            expenseRequest.setTotalAmount(expenseRequestUpdateRequest.getTotalAmount());
            expenseRequest.setExpectedErDate(expenseRequestUpdateRequest.getExpectedErDate());
            expenseRequest.setIsCashAdvance(expenseRequestUpdateRequest.getIsCashAdvance());
            expenseRequest.setRefDocName(expenseRequestUpdateRequest.getRefDoc().getName());
            expenseRequest.setRefDocUrl(expenseRequestUpdateRequest.getRefDoc().getUrl());
            expenseRequest.setNote(expenseRequestUpdateRequest.getNote());
            expenseRequest.setModifiedBy(userId);
            expenseRequestMapper.archive(expenseRequestUpdateRequest.getId(), userId);
            expenseRequestMapper.updatePurchaseRequest(expenseRequestUpdateRequest.getId(), userId);
            expenseRequestMapper.deletePurchaseExpenseRequest(expenseRequestUpdateRequest.getId());
            Boolean result = expenseRequestMapper.update(expenseRequest);
            if (result) {

                if (expenseRequestUpdateRequest.getPurchaseId() != null && expenseRequestUpdateRequest.getPurchaseType() != null){
                    if (expenseRequestUpdateRequest.getPurchaseType() == 3) {
                        expenseRequestMapper.updatePurchaseBill(expenseRequest.getId(), expenseRequestUpdateRequest.getPurchaseId());
                        expenseRequest.setPurchaseType(expenseRequestUpdateRequest.getPurchaseType());
                        expenseRequest.setPurchaseId(expenseRequestUpdateRequest.getPurchaseId());
                        expenseRequestMapper.insertPurchaseExpenseRequest(expenseRequest);
                    } else {
                        expenseRequest.setPurchaseType(expenseRequestUpdateRequest.getPurchaseType());
                        expenseRequest.setPurchaseId(expenseRequestUpdateRequest.getPurchaseId());
                        expenseRequestMapper.insertPurchaseExpenseRequest(expenseRequest);
                        expenseRequestMapper.updatePurchaseOrder(expenseRequest.getId(), expenseRequestUpdateRequest.getPurchaseId());
                    }
                }

                List<PvRequestDetailsRequest> pvRequestDetailsRequests = expenseRequestUpdateRequest.getDetails();
                if (!pvRequestDetailsRequests.isEmpty()) {
                    for (PvRequestDetailsRequest pvRequestDetailsRequest : pvRequestDetailsRequests) {
                        if (pvRequestDetailsRequest.getType() == 1) {
                            ExpenseRequestDetail expenseRequestDetail = new ExpenseRequestDetail();
                            expenseRequestDetail.setPvRequestId(expenseRequest.getId());
                            expenseRequestDetail.setProductId(pvRequestDetailsRequest.getItemId());
                            expenseRequestDetail.setNote(pvRequestDetailsRequest.getNote());
                            expenseRequestDetail.setQty(pvRequestDetailsRequest.getQty());
                            expenseRequestDetail.setQtyUomId(pvRequestDetailsRequest.getUomId());
                            expenseRequestDetail.setUnitCost(pvRequestDetailsRequest.getUnitCost());
                            expenseRequestDetail.setTotalCost(pvRequestDetailsRequest.getTotalCost());
                            expenseRequestDetail.setConversion(pvRequestDetailsRequest.getConversion());
                            expenseRequestMapper.insertPvRequestDetail(expenseRequestDetail);

                        } else if (pvRequestDetailsRequest.getType() == 2) {
                            ExpenseRequestServiceModel expenseRequestServiceModel = new ExpenseRequestServiceModel();
                            expenseRequestServiceModel.setPvRequestId(expenseRequest.getId());
                            expenseRequestServiceModel.setServiceId(pvRequestDetailsRequest.getItemId());
                            expenseRequestServiceModel.setNote(pvRequestDetailsRequest.getNote());
                            expenseRequestServiceModel.setQty(pvRequestDetailsRequest.getQty());
                            expenseRequestServiceModel.setUnitCost(pvRequestDetailsRequest.getUnitCost());
                            expenseRequestServiceModel.setTotalCost(pvRequestDetailsRequest.getTotalCost());
                            expenseRequestMapper.insertPvRequestService(expenseRequestServiceModel);
                        }
                    }
                }
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/expense-request/update", null, null, "Expense Request", "Expense Request (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/expense-request/update", line, error.toString(), "Expense Request", "Expense Request (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateClose(CloseRequest closeRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Expense Request (Close)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            CloseRequest close = new CloseRequest();
            close.setId(closeRequest.getId());
            close.setClosedBy(userId);

            Boolean result = expenseRequestMapper.updateClose(close);

            LocalTime endDuration = LocalTime.now();
            if (result) {
                activityLogService.insert("/expense-request/close", null, null, "Expense Request", "Expense Request (Close)", "Close", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/expense-request/close", line, error.toString(), "Expense Request", "Expense Request (Close)", "Close", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateApprove(ApproveRequest approveRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;

        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Expense Request (Approve)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            if (expenseRequestMapper.getCreatedBy(approveRequest.getId()) == userId) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("អ្នកមិនមានសិទ្ធិបង្កើត ER ហើយអនុញ្ញាតដោយខ្លួនឯងទេ", false));
            }
            List<ExpenseRequestResponse> response = expenseRequestMapper.getOne(approveRequest.getId(), userId);
            if (approveRequest.getTypeApproval() == 1) {
                ApproveRequest approve = new ApproveRequest();
                approve.setId(approveRequest.getId());
                approve.setTotalAmountApprove(approveRequest.getTotalAmountApprove());
                approve.setApproveBy(userId);
                expenseRequestMapper.updateApprove(approve);
                expenseRequestMapper.updateStatus(2L, approve.getId());

                String expenseRequestApprovalMessage = "💸 <b>< Ó<u>ER Approval</u></b>\n\n" +
                        "<b>Date: " + response.get(0).getErDate() + "</b>\n" +
                        "<b>ER Number: " + response.get(0).getErNumber() + "</b>\n\n" +
                        "<i>Request By: " + response.get(0).getCreatedBy() + "</i>\n\n" +
                        "<b>" + response.get(0).getVendorName() + "</b>\n\n" +
                        "Total Approved: <b>" + response.get(0).getCurrencySymbol() + " " +approveRequest.getTotalAmountApprove() + "</b> <i>" + approveRequest.getTypeApproval() + "</i>\n\n" +
                        "Memo: " + response.get(0).getNote() + "\n\n" +
                        "<pre>If you have any questions concerning this approval contact:\n" +
                        response.get(0).getApprovedBy() + "\n</pre>";

                pushTelegram(
                        expenseRequestApprovalMessage,
                        environment.getProperty("telegram.chatId.expense-request"),
                        environment.getProperty("telegram.botToken.expense-request"),
                        null
                );
            } else {
                if (approveRequest.getDeposit() != null && !approveRequest.getDeposit().isEmpty()) {
                    for (DepositApproveRequest deposit : approveRequest.getDeposit()) {
                        DepositApproveRequest depositApprove = new DepositApproveRequest();
                        depositApprove.setId(deposit.getId());
                        depositApprove.setPvRequestId(approveRequest.getId());
                        depositApprove.setDepositPercentage(deposit.getDepositPercentage());
                        depositApprove.setDepositAmount(deposit.getDepositAmount());
                        depositApprove.setPayBillApply(deposit.getPayBillApply());

                        if (deposit.getIsApprove() == 1) {
                            depositApprove.setIsApprove(deposit.getIsApprove());
                            depositApprove.setApproveBy(userId);
                        } else {
                            depositApprove.setIsApprove(0L);
                        }

                        if (null == depositApprove.getId()) {
                            expenseRequestMapper.insertDeposit(depositApprove);
                        } else {
                            expenseRequestMapper.updateDeposit(depositApprove);
                        }

                        if (Objects.equals(expenseRequestMapper.sumTotalAmountApprove(approveRequest.getId()), approveRequest.getTotalAmountApprove())){
                            expenseRequestMapper.updateStatus(2L, approveRequest.getId());
                        } else {
                            expenseRequestMapper.updateStatus(3L, approveRequest.getId());
                        }

                    }
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/expense-request/approve", null, null, "Expense Request", "Expense Request (Approve)", "Approve", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));

        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/expense-request/approve", line, error.toString(), "Expense Request", "Expense Request (Approve)", "Approve", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Expense Request (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = expenseRequestMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/expense-request/delete/{id}",null,null,"Expense Request","Expense Request (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/expense-request/delete/{id}",line, error.toString(),"Expense Request","Expense Request (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListApprove(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Expense Request (Approve)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<ExpenseRequestApproveResponse> responses = expenseRequestMapper.getListApprove(id);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/expense-request/get-list-approve/{id}", null, null, "Expense Request", "Expense Request (Approve)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/expense-request/get-list-approve/{id}", line, error.toString(), "Expense Request", "Expense Request (Approve)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    //! Push Telegram
    public Long pushTelegram(String sendMessage, String chatId, String apiToken, Long replyToMessageId) {
        return TelegramUtils.sendHtmlMessage(sendMessage, chatId, apiToken, replyToMessageId);
    }
}
