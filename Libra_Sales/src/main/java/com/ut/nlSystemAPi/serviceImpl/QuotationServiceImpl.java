package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.helper.Telegram.CheckNull;
import com.ut.nlSystemAPi.helper.TelegramUtils;
import com.ut.nlSystemAPi.mapper.primary.HelperMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.QuotationMapper;
import com.ut.nlSystemAPi.model.base.*;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.entity.Quotation.*;
import com.ut.nlSystemAPi.model.filter.QuotationFilter;
import com.ut.nlSystemAPi.model.request.Quotation.*;
import com.ut.nlSystemAPi.model.response.Quotation.*;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.QuotationService;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuotationServiceImpl implements QuotationService {

    @Autowired
    private QuotationMapper quotationMapper;

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
    public ResponseMessage<BaseResult> getList(QuotationFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (filter.getType() == 1) {
                if (permissionMapper.checkPermission(userId, "Quotataion (View)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
                if (permissionMapper.checkPermission(userId, "Quotataion (View By User)") > 0) {
                    filter.setViewByUser(1L);
                }
            } else {
                if (permissionMapper.checkPermission(userId, "Quotataion Software (View)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
                if (permissionMapper.checkPermission(userId, "Quotataion Software (View By User)") > 0) {
                    filter.setViewByUser(1L);
                }
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(quotationMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<QuotationResponse> responses = quotationMapper.getList(filter, userId);
            if (!responses.isEmpty()) {
                for (QuotationResponse response : responses) {
                    if (response.getType() == 1) {
                        Long amountItem = quotationMapper.countItems(response.getId());
                        response.setTotalItem(amountItem);
                        List<StatusInformationResponse> status = quotationMapper.getStatusInformation(response.getId(), 1L);
                        if (status != null && !status.isEmpty()) {
                            response.setCrmQuotationStatusId(status.get(0).getStatus());
                            response.setCrmQuotationStatusName(status.get(0).getStatusName());
                            if (status.get(0).getStatus() == 2) {
                                response.setCrmQuotationStatusPercent(status.get(0).getExpected());
                                List<CRMPercentResponse> crm = quotationMapper.getCrm();
                                if (crm != null && !crm.isEmpty()){
                                    if (response.getCrmQuotationStatusPercent() <= crm.get(0).getRed()) {
                                        response.setCrmQuotationColor("red");
                                    } else if (response.getCrmQuotationStatusPercent() <= crm.get(0).getYellow()) {
                                        response.setCrmQuotationColor("yellow");
                                    } else {
                                        response.setCrmQuotationColor("green");
                                    }
                                }
                            }
                        }
                    }

                    // User share
                    response.setShareUser(quotationMapper.getUserShare(response.getShareUserIds()));

                    // User except
                    response.setShareExceptUser(quotationMapper.getUserShare(response.getShareExceptUserIds()));

                }

            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/list", null, null, "Quotation", "Quotation (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/list", line, error.toString(), "Quotation", "Quotation (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            List<QuotationResponse> responses = quotationMapper.getOne(id, userId);

            if (!responses.isEmpty()) {
                for (QuotationResponse response : responses) {
                    // Check Permission
                    if (response.getType() == 1) {
                        if (permissionMapper.checkPermission(userId, "Quotataion (View)") == 0) {
                            return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                        }
                    } else {
                        if (permissionMapper.checkPermission(userId, "Quotataion Software (View)") == 0) {
                            return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                        }
                    }
                    response.setTermConditions(quotationMapper.getTermCondition(response.getId()));
                    response.setDetails(quotationMapper.getListDetail(response.getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/find/{id}", null, null, "Quotation", "Quotation (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/find/{id}", line, error.toString(), "Quotation", "Quotation (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(QuotationRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            LocalDateTime now = LocalDateTime.now();
            String formattedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (request.getType() == 1) {
                if (permissionMapper.checkPermission(userId, "Quotataion (Add)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            } else {
                if (permissionMapper.checkPermission(userId, "Quotataion Software (Add)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            }

            // Check Data
            Quotation quotation = new Quotation();
            quotation.setType(request.getType());
            quotation.setCompanyId(request.getCompanyId());
            quotation.setDate(request.getDate());
            quotation.setOrganizationId(request.getOrganizationId());
            quotation.setOrganizationContactId(request.getOrganizationContactId());
            quotation.setCurrencyId(request.getCurrencyId());
            quotation.setPriceTypeId(request.getPriceTypeId());
            quotation.setNote(request.getNote());
            quotation.setTotalVat(request.getTotalVat());
            quotation.setVatPercent(request.getVatPercent());
            quotation.setVatSettingId(request.getVatSettingId());
            quotation.setVatCalculate(helperMapper.getVatCalculate(request.getCompanyId()));
            quotation.setDiscountAmount(request.getDiscountAmount());
            quotation.setDiscountPercent(request.getDiscountPercent());
            quotation.setTotalAmount(request.getSubTotal());
            quotation.setMarginPercent(request.getMarginPercent());
            quotation.setShareSaveOption(request.getShareSaveOption());
            if (request.getShareOption() == 3) {
                List<Long> shareUserIds = request.getShareUser();
                if (!shareUserIds.isEmpty()) {
                    String shareUserIdsString = request.getShareUser()
                            .stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
                    quotation.setShareUser(shareUserIdsString);
                }
            } else if (request.getShareOption() == 4) {
                List<Long> shareExceptUserIds = request.getShareExceptUser();
                if (!shareExceptUserIds.isEmpty()) {
                    String shareExceptUserIdsString = request.getShareExceptUser()
                            .stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
                    quotation.setShareUser(shareExceptUserIdsString);
                }
            }
            quotation.setShareOption(request.getShareOption());
            quotation.setCreated(formattedNow);
            quotation.setCreatedBy(userId);
            quotation.setIsApply(request.getIsApply());
            if ((permissionMapper.checkPermission(userId, "Quotataion (Approve Auto)") == 1 && request.getType() == 1) || request.getType() == 2) {
                quotation.setIsApproved(1);
            } else {
                quotation.setIsApproved(0);
            }
            quotation.setIsNoneVat(request.getIsNoneVat());
            Boolean result = quotationMapper.insert(quotation);

            if (result) {
                // Get the reference code
                if (quotation.getIsNoneVat() == 1) {
                    String code = generateCode.generateAutoCode("quotations", "quotation_code", 7, "N", false, "status >= 0");
                    helperMapper.updateCode("quotations", "quotation_code", code, quotation.getId());
                } else {
                    String code = generateCode.generateAutoCode("quotations", "quotation_code", 7, request.getModuleCode(), false, "status >= 0");
                    helperMapper.updateCode("quotations", "quotation_code", code, quotation.getId());
                }

                if (request.getTermConditions() != null && !request.getTermConditions().isEmpty()) {
                    QuotationTermCondition termCondition = new QuotationTermCondition();
                    for (int i = 0; i < request.getTermConditions().size(); i++) {
                        termCondition.setTermConditionTypeId(request.getTermConditions().get(i).getTermConditionTypeId());
                        termCondition.setTermConditionId(request.getTermConditions().get(i).getTermConditionId());
                        termCondition.setQuotationId(quotation.getId());
                        quotationMapper.insertTermCondition(termCondition);
                    }
                }

                List<QuotationDetailRequest> details = request.getDetails();
                if (details != null && !details.isEmpty()) {
                    for (QuotationDetailRequest quotationDetailRequest : details) {
                        if (quotationDetailRequest.getType() == 1) {
                            QuotationDetail detail = new QuotationDetail();
                            Long conversion = helperMapper.getSmallValUom(quotationDetailRequest.getItemId()) / quotationDetailRequest.getConversion();
                            Double unitCost = helperMapper.getUnitCost(quotationDetailRequest.getItemId()) / quotationDetailRequest.getConversion();
                            detail.setQuotationId(quotation.getId());
                            detail.setItemId(quotationDetailRequest.getItemId());
                            detail.setQty(quotationDetailRequest.getQty());
                            detail.setUomId(quotationDetailRequest.getUomId());
                            detail.setConversion(conversion);
                            detail.setDiscountId(quotationDetailRequest.getDiscountId());
                            detail.setDiscountAmount(quotationDetailRequest.getDiscountAmount());
                            detail.setDiscountPercent(quotationDetailRequest.getDiscountPercent());
                            detail.setUnitCost(unitCost);
                            detail.setUnitPrice(quotationDetailRequest.getUnitPrice());
                            detail.setTotalPrice(quotationDetailRequest.getTotalPrice());
                            detail.setIsBreak(0);
                            quotationMapper.insertDetail(detail);
                        } else if (quotationDetailRequest.getType() == 2) {
                            QuotationServices service = new QuotationServices();
                            service.setQuotationId(quotation.getId());
                            service.setItemId(quotationDetailRequest.getItemId());
                            service.setQty(quotationDetailRequest.getQty());
                            service.setConversion(quotationDetailRequest.getConversion());
                            service.setDiscountId(quotationDetailRequest.getDiscountId());
                            service.setDiscountAmount(quotationDetailRequest.getDiscountAmount());
                            service.setDiscountPercent(quotationDetailRequest.getDiscountPercent());
                            service.setUnitPrice(quotationDetailRequest.getUnitPrice());
                            service.setTotalPrice(quotationDetailRequest.getTotalPrice());
                            quotationMapper.insertService(service);
                        } else if (quotationDetailRequest.getType() == 3) {
                            QuotationMisc misc = new QuotationMisc();
                            misc.setQuotationId(quotation.getId());
                            misc.setItemName(quotationDetailRequest.getItemName());
                            misc.setQty(quotationDetailRequest.getQty());
                            misc.setUomId(quotationDetailRequest.getUomId());
                            misc.setConversion(quotationDetailRequest.getConversion());
                            misc.setDiscountId(quotationDetailRequest.getDiscountId());
                            misc.setDiscountAmount(quotationDetailRequest.getDiscountAmount());
                            misc.setDiscountPercent(quotationDetailRequest.getDiscountPercent());
                            misc.setUnitPrice(quotationDetailRequest.getUnitPrice());
                            misc.setTotalPrice(quotationDetailRequest.getTotalPrice());
                            quotationMapper.insertMisc(misc);
                        } else {
                            QuotationDetail isBreak = new QuotationDetail();
                            isBreak.setQuotationId(quotation.getId());
                            isBreak.setItemId(quotationDetailRequest.getItemId());
                            isBreak.setItemName(quotationDetailRequest.getItemName());
                            isBreak.setIsBreak(1);
                            quotationMapper.insertDetail(isBreak);
                        }
                    }
                }

                String title;
                if (request.getType() == 1) {
                    title = "QUOTATION iAES";
                } else {
                    title = "QUOTATION EBi";
                }

                List<QuotationResponse> data = quotationMapper.getOne(quotation.getId(), userId);

                if (!data.isEmpty()) {
                    Long amountItem = quotationMapper.countItems(quotation.getId());

                    String message = "\uD83D\uDCDD <b><u>" + CheckNull.safe(title) + "</u></b>" +
                            "\n\n<b>Date: " + CheckNull.safe(data.get(0).getDate()) + "</b>" +
                            "\n<b>Quote ID: " + CheckNull.safe(data.get(0).getCode()) + "</b>" +
                            "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                            "\nTel: " + CheckNull.safe(data.get(0).getOrganizationTelephone()) +
                            "\n<i>Attn: " + CheckNull.safe(data.get(0).getOrganizationContactName()) + "</i>" +
                            "\n<i>Tel: " + CheckNull.safe(data.get(0).getOrganizationContactTelephone()) + "</i>" +
                            "\n\nTotal Item: <b>" + CheckNull.safe(String.valueOf(amountItem)) + "</b>" +
                            "\nTotal Amount: <b>" + CheckNull.safe(data.get(0).getCurrencySymbol()) + " " + CheckNull.safe(String.valueOf(data.get(0).getTotalAmount())) + "</b>" +
                            "\n\nMemo: \n" + CheckNull.safe(data.get(0).getNote()) +
                            "\n\n<pre>If you have any questions concerning this quote contact:\n" +
                            CheckNull.safe(data.get(0).getCreatedBy()) + "</pre>";

                    Long messageId = telegramUtils.pushTelegram(
                            message,
                            environment.getProperty("telegram.chatId.quotation"),
                            environment.getProperty("telegram.botToken.quotation"),
                            null
                    );
                    helperMapper.updateMessageId("quotations", messageId, quotation.getId());
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/quotation/add", null, null, "Quotation", "Quotation (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/add", line, error.toString(), "Quotation", "Quotation (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(QuotationUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            LocalDateTime now = LocalDateTime.now();
            String formattedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Long userId = userService.getUserAuth().getId();
            if (request.getType() == 1) {
                if (permissionMapper.checkPermission(userId, "Quotataion (Edit)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            } else {
                if (permissionMapper.checkPermission(userId, "Quotataion Software (Edit)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            }

            QuotationCreator creator = quotationMapper.getCreator(request.getId());

            // Check Data
            Quotation quotation = new Quotation();
            quotation.setType(request.getType());
            quotation.setCompanyId(request.getCompanyId());
            quotation.setDate(request.getDate());
            quotation.setOrganizationId(request.getOrganizationId());
            quotation.setOrganizationContactId(request.getOrganizationContactId());
            quotation.setCurrencyId(request.getCurrencyId());
            quotation.setPriceTypeId(request.getPriceTypeId());
            quotation.setNote(request.getNote());
            quotation.setTotalVat(request.getTotalVat());
            quotation.setVatPercent(request.getVatPercent());
            quotation.setVatSettingId(request.getVatSettingId());
            quotation.setVatCalculate(helperMapper.getVatCalculate(request.getCompanyId()));
            quotation.setDiscountAmount(request.getDiscountAmount());
            quotation.setDiscountPercent(request.getDiscountPercent());
            quotation.setTotalAmount(request.getSubTotal());
            quotation.setMarginPercent(request.getMarginPercent());
            quotation.setShareSaveOption(request.getShareSaveOption());
            if (request.getShareOption() == 3) {
                List<Long> shareUserIds = request.getShareUser();
                if (!shareUserIds.isEmpty()) {
                    String shareUserIdsString = request.getShareUser()
                            .stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
                    quotation.setShareUser(shareUserIdsString);
                }
            } else if (request.getShareOption() == 4) {
                List<Long> shareExceptUserIds = request.getShareExceptUser();
                if (!shareExceptUserIds.isEmpty()) {
                    String shareExceptUserIdsString = request.getShareExceptUser()
                            .stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(","));
                    quotation.setShareUser(shareExceptUserIdsString);
                }
            }
            quotation.setShareOption(request.getShareOption());
            quotation.setCreated(creator.getCreated());
            quotation.setCreatedBy(creator.getCreatedBy());
            quotation.setEdited(formattedNow);
            quotation.setEditedBy(userId);
            quotation.setModified(formattedNow);
            quotation.setModifiedBy(userId);
            quotation.setIsApply(request.getIsApply());
            if ((permissionMapper.checkPermission(userId, "Quotataion (Approve Auto)") == 1 && request.getType() == 1) || request.getType() == 2) {
                quotation.setIsApproved(1);
            } else {
                quotation.setIsApproved(0);
            }
            quotation.setIsNoneVat(request.getIsNoneVat());
            Boolean result = quotationMapper.insert(quotation);

            if (result) {
                helperMapper.archive("quotations", "status", -1, request.getId(), userId);

                // Get the reference code
                String code = helperMapper.getCurrentCode("quotations", "quotation_code", request.getId());
                helperMapper.updateCode("quotations", "quotation_code", code, quotation.getId());

                if (request.getTermConditions() != null && !request.getTermConditions().isEmpty()) {
                    QuotationTermCondition termCondition = new QuotationTermCondition();
                    for (int i = 0; i < request.getTermConditions().size(); i++) {
                        termCondition.setTermConditionTypeId(request.getTermConditions().get(i).getTermConditionTypeId());
                        termCondition.setTermConditionId(request.getTermConditions().get(i).getTermConditionId());
                        termCondition.setQuotationId(quotation.getId());
                        quotationMapper.insertTermCondition(termCondition);
                    }
                }

                System.out.println("request.getDetails() : " + request.getDetails());
                List<QuotationDetailRequest> details = request.getDetails();
                if (details != null && !details.isEmpty()) {
                    for (QuotationDetailRequest quotationDetailRequest : details) {
                        if (quotationDetailRequest.getType() == 1) {
                            QuotationDetail detail = new QuotationDetail();
                            Long conversion = helperMapper.getSmallValUom(quotationDetailRequest.getItemId()) / quotationDetailRequest.getConversion();
                            Double unitCost = helperMapper.getUnitCost(quotationDetailRequest.getItemId()) / quotationDetailRequest.getConversion();
                            detail.setQuotationId(quotation.getId());
                            detail.setItemId(quotationDetailRequest.getItemId());
                            detail.setQty(quotationDetailRequest.getQty());
                            detail.setUomId(quotationDetailRequest.getUomId());
                            detail.setConversion(conversion);
                            detail.setDiscountId(quotationDetailRequest.getDiscountId());
                            detail.setDiscountAmount(quotationDetailRequest.getDiscountAmount());
                            detail.setDiscountPercent(quotationDetailRequest.getDiscountPercent());
                            detail.setUnitCost(unitCost);
                            detail.setUnitPrice(quotationDetailRequest.getUnitPrice());
                            detail.setTotalPrice(quotationDetailRequest.getTotalPrice());
                            detail.setIsBreak(0);
                            quotationMapper.insertDetail(detail);
                        } else if (quotationDetailRequest.getType() == 2) {
                            QuotationServices service = new QuotationServices();
                            service.setQuotationId(quotation.getId());
                            service.setItemId(quotationDetailRequest.getItemId());
                            service.setQty(quotationDetailRequest.getQty());
                            service.setConversion(quotationDetailRequest.getConversion());
                            service.setDiscountId(quotationDetailRequest.getDiscountId());
                            service.setDiscountAmount(quotationDetailRequest.getDiscountAmount());
                            service.setDiscountPercent(quotationDetailRequest.getDiscountPercent());
                            service.setUnitPrice(quotationDetailRequest.getUnitPrice());
                            service.setTotalPrice(quotationDetailRequest.getTotalPrice());
                            quotationMapper.insertService(service);
                        } else if (quotationDetailRequest.getType() == 3) {
                            QuotationMisc misc = new QuotationMisc();
                            misc.setQuotationId(quotation.getId());
                            misc.setItemName(quotationDetailRequest.getItemName());
                            misc.setQty(quotationDetailRequest.getQty());
                            misc.setUomId(quotationDetailRequest.getUomId());
                            misc.setConversion(quotationDetailRequest.getConversion());
                            misc.setDiscountId(quotationDetailRequest.getDiscountId());
                            misc.setDiscountAmount(quotationDetailRequest.getDiscountAmount());
                            misc.setDiscountPercent(quotationDetailRequest.getDiscountPercent());
                            misc.setUnitPrice(quotationDetailRequest.getUnitPrice());
                            misc.setTotalPrice(quotationDetailRequest.getTotalPrice());
                            quotationMapper.insertMisc(misc);
                        } else {
                            QuotationDetail isBreak = new QuotationDetail();
                            isBreak.setQuotationId(quotation.getId());
                            isBreak.setItemId(quotationDetailRequest.getItemId());
                            isBreak.setItemName(quotationDetailRequest.getItemName());
                            isBreak.setIsBreak(1);
                            quotationMapper.insertDetail(isBreak);
                        }
                    }
                }

                String title;
                if (request.getType() == 1) {
                    title = "QUOTATION iAES";
                } else {
                    title = "QUOTATION EBi";
                }
                List<QuotationResponse> data = quotationMapper.getOne(quotation.getId(), userId);

                if (!data.isEmpty()) {

                    Long amountItem = quotationMapper.countItems(quotation.getId());

                    String message = "\uD83D\uDCDD <b><u>" + CheckNull.safe(title) + "</u></b>" +
                            "\n\n<b>Date: " + CheckNull.safe(data.get(0).getDate()) + "</b>" +
                            "\n<b>Quote ID: " + CheckNull.safe(data.get(0).getCode()) + "</b>" +
                            "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                            "\nTel: " + CheckNull.safe(data.get(0).getOrganizationTelephone()) +
                            "\n<i>Attn: " + CheckNull.safe(data.get(0).getOrganizationContactName()) + "</i>" +
                            "\n<i>Tel: " + CheckNull.safe(data.get(0).getOrganizationContactTelephone()) + "</i>" +
                            "\n\nTotal Item: <b>" + CheckNull.safe(String.valueOf(amountItem)) + "</b>" +
                            "\nTotal Amount: <b>" + CheckNull.safe(data.get(0).getCurrencySymbol()) + " " + CheckNull.safe(String.valueOf(data.get(0).getTotalAmount())) + "</b>" +
                            "\n\nMemo: \n" + CheckNull.safe(data.get(0).getNote()) +
                            "\n<i>Modified By: " + CheckNull.safe(data.get(0).getModifiedBy()) + "</i>\n\n" +
                            "\n\n<pre>If you have any questions concerning this quote contact:\n" +
                            CheckNull.safe(data.get(0).getCreatedBy()) + "</pre>";

                    Long messageId = telegramUtils.pushTelegram(
                            message,
                            environment.getProperty("telegram.chatId.quotation"),
                            environment.getProperty("telegram.botToken.quotation"),
                            helperMapper.getMessageId("quotations", quotation.getId())
                    );
                    helperMapper.updateMessageId("quotations", messageId, quotation.getId());
                }

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/quotation/update", null, null, "Quotation", "Quotation (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/quotation/update", line, error.toString(), "Quotation", "Quotation (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            List<QuotationResponse> data = quotationMapper.getOne(id, userId);
            if (!data.isEmpty()) {
                if (data.get(0).getType() == 1) {
                    if (permissionMapper.checkPermission(userId, "Quotataion (Delete)") == 0) {
                        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                    }
                } else {
                    if (permissionMapper.checkPermission(userId, "Quotataion Software (Delete)") == 0) {
                        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                    }
                }
            }

            Boolean result = helperMapper.archive("quotations", "status", 0, id, userId);

            if (result && !data.isEmpty()) {
                String title;
                if (data.get(0).getType() == 1) {
                    title = "QUOTATION iAES";
                } else {
                    title = "QUOTATION EBi";
                }
                Long amountItem = quotationMapper.countItems(id);

                String message = "\uD83D\uDCDD <b><u>" + CheckNull.safe(title) + "</u></b>" +
                        "\n\n<b>Date: " + CheckNull.safe(data.get(0).getDate()) + "</b>" +
                        "\n<b>Quote ID: " + CheckNull.safe(data.get(0).getCode()) + "</b>" +
                        "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                        "\nTel: " + CheckNull.safe(data.get(0).getOrganizationTelephone()) +
                        "\n<i>Attn: " + CheckNull.safe(data.get(0).getOrganizationContactName()) + "</i>" +
                        "\n<i>Tel: " + CheckNull.safe(data.get(0).getOrganizationContactTelephone()) + "</i>" +
                        "\n\nTotal Item: <b>" + CheckNull.safe(String.valueOf(amountItem)) + "</b>" +
                        "\nTotal Amount: <b>" + CheckNull.safe(data.get(0).getCurrencySymbol()) + " " + CheckNull.safe(String.valueOf(data.get(0).getTotalAmount())) + "</b>" +
                        "\n\nMemo: \n" + CheckNull.safe(data.get(0).getNote()) +
                        "\n\n<b>Status: Void</b>\n\n" +
                        "<i>Void By: " + CheckNull.safe(data.get(0).getModifiedBy()) + "</i>\n\n" +
                        "\n\n<pre>If you have any questions concerning this quote contact:\n" +
                        CheckNull.safe(data.get(0).getCreatedBy()) + "</pre>";

                Long messageId = telegramUtils.pushTelegram(
                        message,
                        environment.getProperty("telegram.chatId.quotation"),
                        environment.getProperty("telegram.botToken.quotation"),
                        helperMapper.getMessageId("quotations", id)
                );
                helperMapper.updateMessageId("quotations", messageId, id);
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/quotation/delete/{id}", null, null, "Quotation", "Quotation (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/delete/{id}", line, error.toString(), "Quotation", "Quotation (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            List<QuotationResponse> data = quotationMapper.getOne(request.getId(), userId);
            System.out.println(data.get(0).getType());
            if (!data.isEmpty()) {
                if (data.get(0).getType() == 1) {
                    if (request.getStatus() == 2) {
                        if (permissionMapper.checkPermission(userId, "Quotataion (Approve)") == 0) {
                            return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                        }
                    } else {
                        if (permissionMapper.checkPermission(userId, "Quotataion (Disapprove)") == 0) {
                            System.out.println(permissionMapper.checkPermission(userId, "Quotataion (Disapprove)"));
                            return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                        }
                    }
                } else {
                    if (request.getStatus() == 2) {
                        if (permissionMapper.checkPermission(userId, "Quotataion Software (Approve)") == 0) {
                            return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                        }
                    } else {
                        if (permissionMapper.checkPermission(userId, "Quotataion Software (Disapprove)") == 0) {
                            return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                        }
                    }
                }
            }

            Boolean result = quotationMapper.approve(request.getId(), request.getStatus(), userId);

            if (result && !data.isEmpty()) {
                if (request.getStatus() == 2) {
                    String title;
                    if (data.get(0).getType() == 1) {
                        title = "QUOTATION iAES";
                    } else {
                        title = "QUOTATION EBi";
                    }
                    Long amountItem = quotationMapper.countItems(request.getId());

                    String message = "\uD83D\uDCDD <b><u>" + CheckNull.safe(title) + "</u></b>" +
                            "\n\n<b>Date: " + CheckNull.safe(data.get(0).getDate()) + "</b>" +
                            "\n<b>Quote ID: " + CheckNull.safe(data.get(0).getCode()) + "</b>" +
                            "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                            "\nTel: " + CheckNull.safe(data.get(0).getOrganizationTelephone()) +
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
                            environment.getProperty("telegram.chatId.quotation"),
                            environment.getProperty("telegram.botToken.quotation"),
                            helperMapper.getMessageId("quotations", request.getId())
                    );
                    helperMapper.updateMessageId("quotations", messageId, request.getId());
                } else {
                    String title;
                    if (data.get(0).getType() == 1) {
                        title = "QUOTATION iAES";
                    } else {
                        title = "QUOTATION EBi";
                    }
                    Long amountItem = quotationMapper.countItems(request.getId());

                    String message = "\uD83D\uDCDD <b><u>" + CheckNull.safe(title) + "</u></b>" +
                            "\n\n<b>Date: " + CheckNull.safe(data.get(0).getDate()) + "</b>" +
                            "\n<b>Quote ID: " + CheckNull.safe(data.get(0).getCode()) + "</b>" +
                            "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                            "\nTel: " + CheckNull.safe(data.get(0).getOrganizationTelephone()) +
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
                            environment.getProperty("telegram.chatId.quotation"),
                            environment.getProperty("telegram.botToken.quotation"),
                            helperMapper.getMessageId("quotations", request.getId())
                    );
                    helperMapper.updateMessageId("quotations", messageId, request.getId());
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/quotation/approve", null, null, "Quotation", "Quotation (Approve)", "Approve", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/approve", line, error.toString(), "Quotation", "Quotation (Approve)", "Approve", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            if (request.getStatus() == 2) {
                if (permissionMapper.checkPermission(userId, "Quotataion (Close)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            } else {
                if (permissionMapper.checkPermission(userId, "Quotataion (Close)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            }
            List<QuotationResponse> data = quotationMapper.getOne(request.getId(), userId);
            Boolean result = quotationMapper.close(request.getId(), request.getStatus(), userId);

            if (result && !data.isEmpty()) {
                if (request.getStatus() == 1) {
                    String title;
                    if (data.get(0).getType() == 1) {
                        title = "QUOTATION iAES";
                    } else {
                        title = "QUOTATION EBi";
                    }
                    Long amountItem = quotationMapper.countItems(request.getId());

                    String message = "\uD83D\uDCDD <b><u>" + CheckNull.safe(title) + "</u></b>" +
                            "\n\n<b>Date: " + CheckNull.safe(data.get(0).getDate()) + "</b>" +
                            "\n<b>Quote ID: " + CheckNull.safe(data.get(0).getCode()) + "</b>" +
                            "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                            "\nTel: " + CheckNull.safe(data.get(0).getOrganizationTelephone()) +
                            "\n<i>Attn: " + CheckNull.safe(data.get(0).getOrganizationContactName()) + "</i>" +
                            "\n<i>Tel: " + CheckNull.safe(data.get(0).getOrganizationContactTelephone()) + "</i>" +
                            "\n\nTotal Item: <b>" + CheckNull.safe(String.valueOf(amountItem)) + "</b>" +
                            "\nTotal Amount: <b>" + CheckNull.safe(data.get(0).getCurrencySymbol()) + " " + CheckNull.safe(String.valueOf(data.get(0).getTotalAmount())) + "</b>" +
                            "\n\n<b>Memo:</b> \n" + CheckNull.safe(data.get(0).getNote()) +
                            "\n\n<b>Status: Closed</b>\n\n" +
                            "<i>Closed By: " + CheckNull.safe(data.get(0).getModifiedBy()) + "</i>" +
                            "\n\n<pre>If you have any questions concerning this quote contact:\n" +
                            CheckNull.safe(data.get(0).getCreatedBy()) + "</pre>";

                    Long messageId = telegramUtils.pushTelegram(
                            message,
                            environment.getProperty("telegram.chatId.quotation"),
                            environment.getProperty("telegram.botToken.quotation"),
                            helperMapper.getMessageId("quotations", request.getId())
                    );
                    helperMapper.updateMessageId("quotations", messageId, request.getId());
                } else {
                    String title;
                    if (data.get(0).getType() == 1) {
                        title = "QUOTATION iAES";
                    } else {
                        title = "QUOTATION EBi";
                    }
                    Long amountItem = quotationMapper.countItems(request.getId());

                    String message = "\uD83D\uDCDD <b><u>" + CheckNull.safe(title) + "</u></b>" +
                            "\n\n<b>Date: " + CheckNull.safe(data.get(0).getDate()) + "</b>" +
                            "\n<b>Quote ID: " + CheckNull.safe(data.get(0).getCode()) + "</b>" +
                            "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                            "\nTel: " + CheckNull.safe(data.get(0).getOrganizationTelephone()) +
                            "\n<i>Attn: " + CheckNull.safe(data.get(0).getOrganizationContactName()) + "</i>" +
                            "\n<i>Tel: " + CheckNull.safe(data.get(0).getOrganizationContactTelephone()) + "</i>" +
                            "\n\nTotal Item: <b>" + CheckNull.safe(String.valueOf(amountItem)) + "</b>" +
                            "\nTotal Amount: <b>" + CheckNull.safe(data.get(0).getCurrencySymbol()) + " " + CheckNull.safe(String.valueOf(data.get(0).getTotalAmount())) + "</b>" +
                            "\n\n<b>Memo: </b>\n" + CheckNull.safe(data.get(0).getNote()) +
                            "\n\n<b>Status: Open</b>\n\n" +
                            "<i>Open By: " + CheckNull.safe(data.get(0).getModifiedBy()) + "</i>" +
                            "\n\n<pre>If you have any questions concerning this quote contact:\n" +
                            CheckNull.safe(data.get(0).getCreatedBy()) + "</pre>";

                    Long messageId = telegramUtils.pushTelegram(
                            message,
                            environment.getProperty("telegram.chatId.quotation"),
                            environment.getProperty("telegram.botToken.quotation"),
                            helperMapper.getMessageId("quotations", request.getId())
                    );
                    helperMapper.updateMessageId("quotations", messageId, request.getId());
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/quotation/approve", null, null, "Quotation", "Quotation (Approve)", "Approve", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/approve", line, error.toString(), "Quotation", "Quotation (Approve)", "Approve", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateCrm(CRMPercentRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Quotataion (CRM Update Status Percent)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = quotationMapper.updateCrm(request);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/quotation/update-crm-percent", null, null, "Quotation", "Quotataion (CRM Update Status Percent)", "Update CRM Percent", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/update-crm-percent", line, error.toString(), "Quotation", "Quotataion (CRM Update Status Percent)", "Update CRM Percent", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getCrm(HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            List<CRMPercentResponse> response = quotationMapper.getCrm();

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/list-crm-percent", null, null, "Quotation", "Quotation (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", response, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/list-crm-percent", line, error.toString(), "Quotation", "Quotation (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateShareOptions(QuotationShareOptionRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            Long userId = userService.getUserAuth().getId();

            System.out.println("request: " + request);

            Boolean result = quotationMapper.updateShareOption(request.getId(), request.getShareOption(), request.getShareSaveOption());

            if (result) {
                if (request.getShareOption() == 3) {
                    List<Long> shareUserIds = request.getShareUser();
                    if (!shareUserIds.isEmpty()) {
                        String shareUserIdsString = shareUserIds.stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(","));
                        Map<String, Object> params = new HashMap<>();
                        params.put("userIds", shareUserIdsString);
                        params.put("id", request.getId());
                        quotationMapper.insertShareUser(params);
                    }
                } else if (request.getShareOption() == 4) {
                    List<Long> shareExceptUserIds = request.getShareExceptUser();
                    if (!shareExceptUserIds.isEmpty()) {
                        String shareExceptUserIdsString = shareExceptUserIds.stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(","));
                        Map<String, Object> params = new HashMap<>();
                        params.put("userIds", shareExceptUserIdsString);
                        params.put("id", request.getId());
                        quotationMapper.insertShareExceptUser(params);
                    }
                }
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/quotation/update-share-options", null, null, "Quotation", "Share Options Update", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Failed", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/update-share-options", null, error.toString(), "Quotation", "Share Options Update", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateStatusInformation(QuotationStatusInformationRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            Long userId = userService.getUserAuth().getId();
            // Check Data
            QuotationStatusInformation statusInformation = new QuotationStatusInformation();
            statusInformation.setQuotationId(request.getQuotationId());
            statusInformation.setDivisionId(request.getDivisionId());
            statusInformation.setQuotationStatusId(request.getQuotationStatusId());
            statusInformation.setDescription(request.getDescription());
            statusInformation.setNeedName(request.getNeedName());
            statusInformation.setExpectedPercent(request.getExpectedPercent());
            statusInformation.setExpectedWeek(request.getExpectedWeek());
            statusInformation.setExpectedMonth(request.getExpectedMonth());
            statusInformation.setCreatedBy(userId);
            Boolean result = quotationMapper.insertStatusInformation(statusInformation);
            if (result) {
                if (request.getReasons() != null) {
                    for (int i = 0; i < request.getReasons().size(); i++) {
                        Long reasonId = request.getReasons().get(i);
                        quotationMapper.insertStatusInformationDetail(statusInformation.getId(), reasonId);
                    }
                }
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/quotation/update-status-information", null, null, "Quotation", "Update Status Information", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Failed", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/update-status-information", null, error.toString(), "Quotation", "Update Status Information", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listStatusInformation(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            List<StatusInformationResponse> responses = quotationMapper.getStatusInformation(id, null);
            if (!responses.isEmpty()) {
                for (StatusInformationResponse response : responses) {
                    response.setReasons(quotationMapper.getStatusInformationDetail(response.getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/find/{id}", null, null, "Quotation", "Quotation (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/find/{id}", line, error.toString(), "Quotation", "Quotation (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> findProductInfo(Long productId, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            Long userId = userService.getUserAuth().getId();
            List<QuotationProductInfoResponse> flatResponses = quotationMapper.findProductInfo(productId, userId);

            Map<String, List<QuotationProductInfoResponse>> groupedMap = flatResponses.stream()
                    .collect(Collectors.groupingBy(
                            detail -> detail.getProductName() != null ? detail.getProductName() : "",
                            LinkedHashMap::new,
                            Collectors.toList()
                    ));

            flatResponses = groupedMap.entrySet().stream()
                    .map(entry -> {
                        QuotationProductInfoResponse response = new QuotationProductInfoResponse();
                        // Set parent (product-level) fields
                        response.setProductName(entry.getKey());
                        QuotationProductInfoResponse first = entry.getValue().get(0);
                        response.setSku(first.getSku());
                        response.setUpc(first.getUpc());
                        response.setSpec(first.getSpec());
                        response.setProductPhotoUrl(first.getProductPhotoUrl());
                        response.setProductPhotoName(first.getProductPhotoName());

                        // Clear product-level fields from details to avoid duplication
                        entry.getValue().forEach(detail -> {
                            detail.setProductName(null);
                            detail.setSku(null);
                            detail.setUpc(null);
                            detail.setSpec(null);
                            detail.setProductPhotoUrl(null);
                            detail.setProductPhotoName(null);
                        });
                        response.setDetails(entry.getValue());
                        return response;
                    })
                    .collect(Collectors.toList());

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/find-product-info/{id}", null, null, "Quotation", "Find Product Info", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", flatResponses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/find-product-info/{id}", null, error.toString(), "Quotation", "Find Product Info", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updatePagination(QuotationPaginationRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Data
            QuotationPaginationRequest model = new QuotationPaginationRequest();
            model.setId(request.getId());
            model.setPage(request.getPage());
            model.setItemPerPage(request.getItemPerPage());
            Boolean result = quotationMapper.updatePagination(model);
            if (result) {
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/quotation/update-pagination", null, null, "Quotation", "Update Status Information", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Failed", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/quotation/update-pagination", null, error.toString(), "Quotation", "Update Status Information", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", false));
        }
    }

}
