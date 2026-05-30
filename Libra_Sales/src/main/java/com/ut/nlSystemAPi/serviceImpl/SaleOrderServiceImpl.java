package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.helper.Telegram.CheckNull;
import com.ut.nlSystemAPi.helper.TelegramUtils;
import com.ut.nlSystemAPi.mapper.primary.HelperMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.SaleOrderMapper;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.base.StatusRequest;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.entity.SaleOrder.*;
import com.ut.nlSystemAPi.model.filter.SaleOrderFilter;
import com.ut.nlSystemAPi.model.request.SaleOrder.SaleOrderDetailRequest;
import com.ut.nlSystemAPi.model.request.SaleOrder.SaleOrderRequest;
import com.ut.nlSystemAPi.model.request.SaleOrder.SaleOrderUpdateRequest;
import com.ut.nlSystemAPi.model.response.Quotation.QuotationCreator;
import com.ut.nlSystemAPi.model.response.SaleOrder.SaleOrderResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.SaleOrderService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SaleOrderServiceImpl implements SaleOrderService {

    @Autowired
    private SaleOrderMapper saleOrderMapper;

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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private TelegramUtils telegramUtils;

    @Override
    public ResponseMessage<BaseResult> getList(SaleOrderFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales Order (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (permissionMapper.checkPermission(userId, "Sales Order (View By User)") > 0) {
                filter.setViewByUser(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(saleOrderMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SaleOrderResponse> responses = saleOrderMapper.getList(filter, userId);

            if (!responses.isEmpty()) {
                for (SaleOrderResponse response : responses) {
                    if (response.getType() == 1) {
                        response.setEditCount(saleOrderMapper.countEdit(response.getSoNo()));
                        if (response.getStatus() == 1) {
                            // Check if the SO is already close and approve
                            if (response.getIsClose() == 1 && response.getIsApprove() == 1) {
                                // Check if the SO is already have invoice
                                Long checkInvoice = saleOrderMapper.checkInvoice(response.getId());
                                if (checkInvoice > 0) {
                                    // Check if the SO is already have invoice and already delivery
                                    Long checkDelivery = saleOrderMapper.checkDelivery(response.getId());
                                    if (checkDelivery > 0) {
                                        response.setSoStatus("SO បានចេញវិក្ក័យប័ត្រ (Sales Invoice) ទៅតាមមុខទំនិញអស់ហើយ និង បានដឹកជញ្ជូនរួចរាល់ហើយ។");
                                        response.setSoStatusColor(1);
                                    } else {
                                        response.setSoStatus("SO បានចេញវិក្ក័យប័ត្រ (Sales Invoice) ទៅតាមមុខទំនិញអស់ហើយ ប៉ុន្តែមិនទាន់ដឹកជញ្ជូន (Not Yet Delivery)។");
                                        response.setSoStatusColor(3);
                                    }
                                }
                            } else {
                                if (response.getDateDiff() > 3 && response.getIsClose() == 0 && response.getIsApprove() == 0) {
                                    response.setSoStatus("SO ដែលបានបង្កើតហើយលើសពី៣ថ្ងៃហើយគ្មានប្រតិបត្តិការណ៏។");
                                    response.setSoStatusColor(4);
                                } else {
                                    response.setSoStatus("SO ដែលបានបង្កើតហើយមិនលើសពី ៣ថ្ងៃ ហើយបានអនុម័ត្ត (Approved) រួចរាល់។");
                                    response.setSoStatusColor(0);
                                }
                            }
                        } else if (response.getStatus() == 2) {
                            response.setSoStatus("SO បានចេញវិក្ក័យប័ត្រ (Sales Invoice) ទៅតាមមុខទំនិញខ្លះៗហើយតែមិនទាន់អស់មុខទំនិញ (Status = Partial)។");
                            response.setSoStatusColor(2);
                        }
                        else if (response.getStatus() == 3) {
                            response.setSoStatus("SO បានចេញវិក្ក័យប័ត្រ (Sales Invoice) ទៅតាមមុខទំនិញអស់ហើយ ប៉ុន្តែមិនទាន់ដឹកជញ្ជូន (Not Yet Delivery)។");
                            response.setSoStatusColor(3);
                        }
                        else if (response.getStatus() == 4) {
                            response.setSoStatus("SO បានចេញវិក្ក័យប័ត្រ (Sales Invoice) ទៅតាមមុខទំនិញអស់ហើយ និង បានដឹកជញ្ជូនរួចរាល់ហើយ។");
                            response.setSoStatusColor(1);
                        }
                    }
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-order/list", null, null, "Sales Order", "Sales Order (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-order/list", line, error.toString(), "Sales Order", "Sales Order (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, Long warehouseId, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales Order (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<SaleOrderResponse> responses = saleOrderMapper.getOne(id, userId);

            if (!responses.isEmpty()) {
                for (SaleOrderResponse response : responses) {
                    response.setQuotations(saleOrderMapper.getSaleOrderQuotation(response.getId()));
                    response.setTermConditions(saleOrderMapper.getTermCondition(response.getId()));
                    response.setDetails(saleOrderMapper.getListDetail(response.getId(), warehouseId));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-order/find/{id}", null, null, "Sales Order", "Sales Order (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-order/find/{id}", line, error.toString(), "Sales Order", "Sales Order (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(SaleOrderRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            LocalDateTime now = LocalDateTime.now();
            String formattedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Check Permission
            Long userId = userService.getUserAuth().getId();

            String code = "";

            if (request.getIsUpdate() != null && request.getIsUpdate() == 1 && request.getId() != null) {
                if (permissionMapper.checkPermission(userId, "Sales Order (Edit)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }

                //! Get current reference code
                code = (helperMapper.getCurrentCode("orders", "order_code", request.getId()));

                // Check Edit Count
                if (saleOrderMapper.countEdit(code) == 5 && request.getType() == 1) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("Cannot edit more than 5 times.", false));
                }

            } else {
                if (permissionMapper.checkPermission(userId, "Sales Order (Add)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            }

            // Check Data
            SaleOrder saleOrder = new SaleOrder();
            saleOrder.setType(request.getType());
            saleOrder.setCompanyId(request.getCompanyId());
            saleOrder.setDate(request.getDate());
            saleOrder.setOrganizationId(request.getOrganizationId());
            saleOrder.setOrganizationContactId(request.getOrganizationContactId());
            saleOrder.setOrganizationPoNo(request.getOrganizationPoNo());
            saleOrder.setOrganizationPoFile(request.getOrganizationPoFile());
            saleOrder.setDeliveryDate(request.getDeliveryDate());
            saleOrder.setCurrencyId(helperMapper.getCompanyCurrencyCenter(request.getCompanyId()));
            saleOrder.setPriceTypeId(request.getPriceTypeId());
            saleOrder.setNote(request.getNote());
            saleOrder.setTotalVat(request.getTotalVat());
            saleOrder.setVatPercent(request.getVatPercent());
            saleOrder.setVatSettingId(request.getVatSettingId());
            saleOrder.setVatCalculate(helperMapper.getVatCalculate(request.getCompanyId()));
            saleOrder.setDiscountAmount(request.getDiscountAmount());
            saleOrder.setDiscountPercent(request.getDiscountPercent());
            saleOrder.setTotalAmount(request.getSubTotal());
            saleOrder.setCreated(formattedNow);
            saleOrder.setCreatedBy(userId);
            saleOrder.setIsApply(request.getIsApply());
            saleOrder.setIsRequiredPo(request.getIsRequiredPo());
            saleOrder.setIsNoneVat(request.getIsNoneVat());
            saleOrder.setIsWso(request.getIsWso());
            Boolean result = saleOrderMapper.insert(saleOrder);

            if (result) {
                if (request.getIsUpdate() != null && request.getIsUpdate() == 1 && request.getId() != null){
                    helperMapper.archive("orders", "status", -1, request.getId(), userId);
                } else {
                    //! Generate reference code
                    code = (generateCode.generateAutoCode("orders", "order_code", 7, request.getModuleCode(), true, "status >= 0"));
                }

                if (code != null && !code.isEmpty()){
                    helperMapper.updateCode("orders", "order_code", code, saleOrder.getId());
                }

                if (request.getQuotations() != null && !request.getQuotations().isEmpty()) {
                    for (int i = 0; i < request.getQuotations().size(); i++) {
                        Long quotationId = request.getQuotations().get(i);
                        saleOrderMapper.insertSaleOrderQuotation(saleOrder.getId(), quotationId);
                    }
                }

                if (request.getTermConditions() != null && !request.getTermConditions().isEmpty()) {
                    SaleOrderTermCondition termCondition = new SaleOrderTermCondition();
                    for (int i = 0; i < request.getTermConditions().size(); i++) {
                        termCondition.setTermConditionTypeId(request.getTermConditions().get(i).getTermConditionTypeId());
                        termCondition.setTermConditionId(request.getTermConditions().get(i).getTermConditionId());
                        termCondition.setSaleOrderId(saleOrder.getId());
                        saleOrderMapper.insertTermCondition(termCondition);
                    }
                }

                List<SaleOrderDetailRequest> details = request.getDetails();
                if (details != null && !details.isEmpty()) {
                    for (SaleOrderDetailRequest detailRequest : details) {
                        if (detailRequest.getType() == 1) {
                            Long conversion = helperMapper.getSmallValUom(detailRequest.getItemId()) / detailRequest.getConversion();
                            SaleOrderDetail detail = new SaleOrderDetail();
                            detail.setSaleOrderId(saleOrder.getId());
                            detail.setQuotationId(detailRequest.getQuotationId());
                            detail.setItemId(detailRequest.getItemId());
                            detail.setPoNo(detailRequest.getPoNo());
                            detail.setQty(detailRequest.getQty());
                            detail.setQtyFree(detailRequest.getFoc());
                            detail.setUomId(detailRequest.getUomId());
                            detail.setConversion(conversion);
                            detail.setDiscountId(detailRequest.getDiscountId());
                            detail.setDiscountAmount(detailRequest.getDiscountAmount());
                            detail.setDiscountPercent(detailRequest.getDiscountPercent());
                            detail.setUnitPrice(detailRequest.getUnitPrice());
                            detail.setTotalPrice(detailRequest.getTotalPrice());
                            saleOrderMapper.insertDetail(detail);
                        } else if (detailRequest.getType() == 2) {
                            SaleOrderServices service = new SaleOrderServices();
                            service.setSaleOrderId(saleOrder.getId());
                            service.setQuotationId(detailRequest.getQuotationId());
                            service.setItemId(detailRequest.getItemId());
                            service.setPoNo(detailRequest.getPoNo());
                            service.setQty(detailRequest.getQty());
                            service.setQtyFree(detailRequest.getFoc());
                            service.setConversion(detailRequest.getConversion());
                            service.setDiscountId(detailRequest.getDiscountId());
                            service.setDiscountAmount(detailRequest.getDiscountAmount());
                            service.setDiscountPercent(detailRequest.getDiscountPercent());
                            service.setUnitPrice(detailRequest.getUnitPrice());
                            service.setTotalPrice(detailRequest.getTotalPrice());
                            saleOrderMapper.insertService(service);
                        } else if (detailRequest.getType() == 3) {
                            SaleOrderMisc misc = new SaleOrderMisc();
                            misc.setSaleOrderId(saleOrder.getId());
                            misc.setQuotationId(detailRequest.getQuotationId());
                            misc.setItemName(detailRequest.getItemName());
                            misc.setPoNo(detailRequest.getPoNo());
                            misc.setQty(detailRequest.getQty());
                            misc.setQtyFree(detailRequest.getFoc());
                            misc.setConversion(detailRequest.getConversion());
                            misc.setDiscountId(detailRequest.getDiscountId());
                            misc.setDiscountAmount(detailRequest.getDiscountAmount());
                            misc.setDiscountPercent(detailRequest.getDiscountPercent());
                            misc.setUnitPrice(detailRequest.getUnitPrice());
                            misc.setTotalPrice(detailRequest.getTotalPrice());
                            saleOrderMapper.insertMisc(misc);
                        }
                    }
                }

                String title;
                if (request.getType() == 1){
                    title = "Sales Order iAES";
                } else {
                    title = "Sales Order EBi";
                }

                List<SaleOrderResponse> data = saleOrderMapper.getOne(saleOrder.getId(), userId);

                if (!data.isEmpty()){
                    Long amountItem = saleOrderMapper.countItems(saleOrder.getId());

                    String message = "\uD83D\uDCB0 <b><u>" + title + "</u></b>" +
                            "\n\n<b>Date: " + CheckNull.safe(data.get(0).getSoDate()) + "</b>" +
                            "\n<b>SO ID: " + CheckNull.safe(data.get(0).getSoNo()) + "</b>" +
                            "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                            "\nTel: " + CheckNull.safe(data.get(0).getOrganizationTelephone()) +
                            "\n<i>Attn: " + CheckNull.safe(data.get(0).getOrganizationContactName()) + "</i>" +
                            "\n<i>Tel: " + CheckNull.safe(data.get(0).getOrganizationContactTelephone()) + "</i>" +
                            "\n\nTotal Item: <b>" + CheckNull.safe(String.valueOf(amountItem)) + "</b>" +
                            "\nTotal Amount: <b>" + CheckNull.safe(data.get(0).getCurrencySymbol()) + " " + CheckNull.safe(String.valueOf(data.get(0).getTotalAmount())) + "</b>" +
                            "\n\nMemo: \n" + CheckNull.safe(data.get(0).getNote()) +
                            "\n\n<pre>If you have any questions concerning this sales order contact:\n" +
                            CheckNull.safe(data.get(0).getCreatedBy()) + "</pre>";

                    Long messageId = telegramUtils.pushTelegram(
                            message,
                            environment.getProperty("telegram.chatId.sales-order"),
                            environment.getProperty("telegram.botToken.sales-order"),
                            null
                    );
                    helperMapper.updateMessageId("orders", messageId, saleOrder.getId());
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-order/add", null, null, "Sales Order", "Sales Order (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-order/add", line, error.toString(), "Sales Order", "Sales Order (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(SaleOrderUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
            request.setIsUpdate(1);
            return this.insert(request, bindingResult, httpServletRequest);
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales Order (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<SaleOrderResponse> data = saleOrderMapper.getOne(id, userId);
            Boolean result = helperMapper.archive("orders", "status", 0, id, userId);

            if (result) {
                Long catalogOrderId = saleOrderMapper.checkCatalogOrder(id);

                if (catalogOrderId != null){
                    helperMapper.archive("catalog_orders", "status", 0, catalogOrderId, userId);
                }

                String title;
                if (data.get(0).getType() == 1){
                    title = "Sales Order iAES";
                } else {
                    title = "Sales Order EBi";
                }
                Long amountItem = saleOrderMapper.countItems(id);

                String message = "\uD83D\uDCDD <b><u>" + title + "</u></b>" +
                        "\n\n<b>Date: " + CheckNull.safe(data.get(0).getSoDate()) + "</b>" +
                        "\n<b>Quote ID: " + CheckNull.safe(data.get(0).getSoNo()) + "</b>" +
                        "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                        "\nTel: " + CheckNull.safe(data.get(0).getOrganizationContactName()) +
                        "\n<i>Attn: " + CheckNull.safe(data.get(0).getOrganizationContactName()) + "</i>" +
                        "\n<i>Tel: " + CheckNull.safe(data.get(0).getOrganizationContactTelephone()) + "</i>" +
                        "\n\nTotal Item: <b>" + CheckNull.safe(String.valueOf(amountItem)) + "</b>" +
                        "\nTotal Amount: <b>" + CheckNull.safe(data.get(0).getCurrencySymbol()) + " " + CheckNull.safe(String.valueOf(data.get(0).getTotalAmount())) + "</b>" +
                        "\n\nMemo: \n" + CheckNull.safe(data.get(0).getNote()) +
                        "\n\n<b>Status: Cancel</b>\n\n" +
                        "<i>Cancel By: " + CheckNull.safe(data.get(0).getModifiedBy()) + "</i>\n\n" +
                        "\n\n<pre>If you have any questions concerning this quote contact:\n" +
                        CheckNull.safe(data.get(0).getCreatedBy()) + "</pre>";

                Long messageId = telegramUtils.pushTelegram(
                        message,
                        environment.getProperty("telegram.chatId.sales-order"),
                        environment.getProperty("telegram.botToken.sales-order"),
                        helperMapper.getMessageId("orders", id)
                );
                helperMapper.updateMessageId("orders", messageId, id);
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-order/delete/{id}",null,null,"Sales Order","Sales Order (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-order/delete/{id}",line, error.toString(),"Sales Order","Sales Order (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> approve(StatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (request.getStatus() == 2) {
                if (permissionMapper.checkPermission(userId, "Sales Order (Approve)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            } else {
                if (permissionMapper.checkPermission(userId, "Sales Order (Disapprove)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            }
            List<SaleOrderResponse> data = saleOrderMapper.getOne(request.getId(), userId);
            Boolean result = saleOrderMapper.approve(request.getId(), request.getStatus(), userId);

            if (result) {
                if (request.getStatus() == 2) {
                    String title;
                    if (data.get(0).getType() == 1){
                        title = "Sales Order iAES";
                    } else {
                        title = "Sales Order EBi";
                    }
                    Long amountItem = saleOrderMapper.countItems(request.getId());

                    String message = "\uD83D\uDCDD <b><u>" + title + "</u></b>" +
                            "\n\n<b>Date: " + CheckNull.safe(data.get(0).getSoDate()) + "</b>" +
                            "\n<b>Quote ID: " + CheckNull.safe(data.get(0).getSoNo()) + "</b>" +
                            "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                            "\nTel: " + CheckNull.safe(data.get(0).getOrganizationContactName()) +
                            "\n<i>Attn: " + CheckNull.safe(data.get(0).getOrganizationContactName()) + "</i>" +
                            "\n<i>Tel: " + CheckNull.safe(data.get(0).getOrganizationContactTelephone()) + "</i>" +
                            "\n\nTotal Item: <b>" + CheckNull.safe(String.valueOf(amountItem)) + "</b>" +
                            "\nTotal Amount: <b>" + CheckNull.safe(data.get(0).getCurrencySymbol()) + " " + CheckNull.safe(String.valueOf(data.get(0).getTotalAmount())) + "</b>" +
                            "\n\nMemo: \n" + CheckNull.safe(data.get(0).getNote()) +
                            "\n\n<b>Status: Approved</b>\n\n" +
                            "<i>Approved By: " + CheckNull.safe(data.get(0).getModifiedBy()) + "</i>\n\n" +
                            "\n\n<pre>If you have any questions concerning this quote contact:\n" +
                            CheckNull.safe(data.get(0).getCreatedBy()) + "</pre>";

                    Long messageId = telegramUtils.pushTelegram(
                            message,
                            environment.getProperty("telegram.chatId.sales-order"),
                            environment.getProperty("telegram.botToken.sales-order"),
                            helperMapper.getMessageId("orders", request.getId())
                    );
                    helperMapper.updateMessageId("orders", messageId, request.getId());
                } else {
                    String title;
                    if (data.get(0).getType() == 1){
                        title = "Sales Order iAES";
                    } else {
                        title = "Sales Order EBi";
                    }
                    Long amountItem = saleOrderMapper.countItems(request.getId());

                    String message = "\uD83D\uDCDD <b><u>" + title + "</u></b>" +
                            "\n\n<b>Date: " + CheckNull.safe(data.get(0).getSoDate()) + "</b>" +
                            "\n<b>Quote ID: " + CheckNull.safe(data.get(0).getSoNo()) + "</b>" +
                            "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                            "\nTel: " + CheckNull.safe(data.get(0).getOrganizationContactName()) +
                            "\n<i>Attn: " + CheckNull.safe(data.get(0).getOrganizationContactName()) + "</i>" +
                            "\n<i>Tel: " + CheckNull.safe(data.get(0).getOrganizationContactTelephone()) + "</i>" +
                            "\n\nTotal Item: <b>" + CheckNull.safe(String.valueOf(amountItem)) + "</b>" +
                            "\nTotal Amount: <b>" + CheckNull.safe(data.get(0).getCurrencySymbol()) + " " + CheckNull.safe(String.valueOf(data.get(0).getTotalAmount())) + "</b>" +
                            "\n\nMemo: \n" + CheckNull.safe(data.get(0).getNote()) +
                            "\n\n<b>Status: Disapproved</b>\n\n" +
                            "<i>Disapproved By: " + CheckNull.safe(data.get(0).getModifiedBy()) + "</i>\n\n" +
                            "\n\n<pre>If you have any questions concerning this quote contact:\n" +
                            CheckNull.safe(data.get(0).getCreatedBy()) + "</pre>";

                    Long messageId = telegramUtils.pushTelegram(
                            message,
                            environment.getProperty("telegram.chatId.sales-order"),
                            environment.getProperty("telegram.botToken.sales-order"),
                            helperMapper.getMessageId("orders", request.getId())
                    );
                    helperMapper.updateMessageId("orders", messageId, request.getId());
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-order/approve",null,null,"Sales Order","Sales Order (Approve)","Approve",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-order/approve",line, error.toString(),"Sales Order","Sales Order (Approve)","Approve",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> close(StatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Sales Order (Close)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<SaleOrderResponse> data = saleOrderMapper.getOne(request.getId(), userId);
            Boolean result = saleOrderMapper.close(request.getId(), request.getStatus(), userId);

            if (result) {
                if (request.getStatus() == 2) {
                    String title;
                    if (data.get(0).getType() == 1){
                        title = "Sales Order iAES";
                    } else {
                        title = "Sales Order EBi";
                    }
                    Long amountItem = saleOrderMapper.countItems(request.getId());

                    String message = "\uD83D\uDCDD <b><u>" + title + "</u></b>" +
                            "\n\n<b>Date: " + CheckNull.safe(data.get(0).getSoDate()) + "</b>" +
                            "\n<b>Quote ID: " + CheckNull.safe(data.get(0).getSoNo()) + "</b>" +
                            "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                            "\nTel: " + CheckNull.safe(data.get(0).getOrganizationContactName()) +
                            "\n<i>Attn: " + CheckNull.safe(data.get(0).getOrganizationContactName()) + "</i>" +
                            "\n<i>Tel: " + CheckNull.safe(data.get(0).getOrganizationContactTelephone()) + "</i>" +
                            "\n\nTotal Item: <b>" + CheckNull.safe(String.valueOf(amountItem)) + "</b>" +
                            "\nTotal Amount: <b>" + CheckNull.safe(data.get(0).getCurrencySymbol()) + " " + CheckNull.safe(String.valueOf(data.get(0).getTotalAmount())) + "</b>" +
                            "\n\nMemo: \n" + CheckNull.safe(data.get(0).getNote()) +
                            "\n\n<b>Status: Closed</b>\n\n" +
                            "<i>Closed By: " + CheckNull.safe(data.get(0).getModifiedBy()) + "</i>\n\n" +
                            "\n\n<pre>If you have any questions concerning this quote contact:\n" +
                            CheckNull.safe(data.get(0).getCreatedBy()) + "</pre>";

                    Long messageId = telegramUtils.pushTelegram(
                            message,
                            environment.getProperty("telegram.chatId.sales-order"),
                            environment.getProperty("telegram.botToken.sales-order"),
                            helperMapper.getMessageId("orders", request.getId())
                    );
                    helperMapper.updateMessageId("orders", messageId, request.getId());
                } else {
                    String title;
                    if (data.get(0).getType() == 1){
                        title = "Sales Order iAES";
                    } else {
                        title = "Sales Order EBi";
                    }
                    Long amountItem = saleOrderMapper.countItems(request.getId());

                    String message = "\uD83D\uDCDD <b><u>" + title + "</u></b>" +
                            "\n\n<b>Date: " + CheckNull.safe(data.get(0).getSoDate()) + "</b>" +
                            "\n<b>Quote ID: " + CheckNull.safe(data.get(0).getSoNo()) + "</b>" +
                            "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                            "\nTel: " + CheckNull.safe(data.get(0).getOrganizationContactName()) +
                            "\n<i>Attn: " + CheckNull.safe(data.get(0).getOrganizationContactName()) + "</i>" +
                            "\n<i>Tel: " + CheckNull.safe(data.get(0).getOrganizationContactTelephone()) + "</i>" +
                            "\n\nTotal Item: <b>" + CheckNull.safe(String.valueOf(amountItem)) + "</b>" +
                            "\nTotal Amount: <b>" + CheckNull.safe(data.get(0).getCurrencySymbol()) + " " + CheckNull.safe(String.valueOf(data.get(0).getTotalAmount())) + "</b>" +
                            "\n\nMemo: \n" + CheckNull.safe(data.get(0).getNote()) +
                            "\n\n<b>Status: Opened</b>\n\n" +
                            "<i>Opened By: " + CheckNull.safe(data.get(0).getModifiedBy()) + "</i>\n\n" +
                            "\n\n<pre>If you have any questions concerning this quote contact:\n" +
                            CheckNull.safe(data.get(0).getCreatedBy()) + "</pre>";

                    Long messageId = telegramUtils.pushTelegram(
                            message,
                            environment.getProperty("telegram.chatId.sales-order"),
                            environment.getProperty("telegram.botToken.sales-order"),
                            helperMapper.getMessageId("orders", request.getId())
                    );
                    helperMapper.updateMessageId("orders", messageId, request.getId());
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-order/close",null,null,"Sales Order","Sales Order (Approve)","Approve",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-order/close",line, error.toString(),"Sales Order","Sales Order (Approve)","Approve",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

}
