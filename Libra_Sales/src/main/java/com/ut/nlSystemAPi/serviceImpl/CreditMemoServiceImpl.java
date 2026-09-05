package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.Inventory;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.base.*;
import com.ut.nlSystemAPi.model.GeneralLedger;
import com.ut.nlSystemAPi.model.GeneralLedgerDetail;
import com.ut.nlSystemAPi.model.entity.CreditMemo.*;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.filter.CreditMemoReceiptFilter;
import com.ut.nlSystemAPi.model.filter.CreditMemoFilter;
import com.ut.nlSystemAPi.model.request.CreditMemo.*;
import com.ut.nlSystemAPi.model.response.CreditMemo.CreditMemoDetailResponse;
import com.ut.nlSystemAPi.model.response.CreditMemo.CreditMemoReceiptResponse;
import com.ut.nlSystemAPi.model.response.CreditMemo.CreditMemoResponse;
import com.ut.nlSystemAPi.model.base.ProductCodeNameResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.CreditMemoService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreditMemoServiceImpl implements CreditMemoService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private GenerateCode generateCode;

    @Autowired
    private HelperMapper helperMapper;

    @Autowired
    private Inventory inventory;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private CreditMemoMapper creditMemoMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    public ResponseMessage<BaseResult> getList(CreditMemoFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Credit Memo (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (permissionMapper.checkPermission(userId, "Credit Memo (View By User)") > 0) {
                filter.setViewByUser(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(creditMemoMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CreditMemoResponse> responses = creditMemoMapper.getList(filter,userId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/credit-memo/list", null, null, "Credit Memo", "Credit Memo (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/credit-memo/list", line, error.toString(), "Credit Memo", "Credit Memo (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Credit Memo (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<CreditMemoResponse> responses = creditMemoMapper.getOne(id, userId);

            if (!responses.isEmpty()) {
                for (CreditMemoResponse response : responses) {
                    List<CreditMemoDetailResponse> details = creditMemoMapper.getListDetail(id);
                    response.setDetails(details);
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/credit-memo/find/{id}", null, null, "Credit Memo", "Credit Memo (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/credit-memo/find/{id}", line, error.toString(), "Credit Memo", "Credit Memo (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(CreditMemoRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            LocalDateTime now = LocalDateTime.now();
            String formattedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Credit Memo (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Calculate Total Balance
            Double totalBalance = request.getSubTotal() + request.getMarkUp() + request.getTotalVat() - request.getDiscount();

            //! Credit Memo
            CreditMemo creditMemo = new CreditMemo();
            creditMemo.setCompanyId(request.getCompanyId());
            creditMemo.setLocationGroupId(request.getLocationGroupId());
            creditMemo.setLocationId(request.getLocationId());
            creditMemo.setCustomerId(request.getCustomerId());
            creditMemo.setCurrencyCenterId(helperMapper.getCompanyCurrencyCenter(request.getCompanyId()));
            creditMemo.setReasonId(request.getCmTermId());
            creditMemo.setSaleOrderId(request.getSaleInvoiceId());
            creditMemo.setInvoiceCode(request.getInvoiceCode());
            creditMemo.setInvoiceDate(request.getInvoiceDate());
            creditMemo.setNote(request.getNote());
            if (request.getChartAccountId() != null){
                creditMemo.setChartAccountId(request.getChartAccountId());
            } else {
                creditMemo.setChartAccountId(helperMapper.getCmChartAccountId());
            }
            creditMemo.setVatSettingId(request.getVatSettingId());
            creditMemo.setVatCalculate(helperMapper.getVatCalculate(request.getCompanyId()));
            creditMemo.setVatChartAccountId(helperMapper.getVatChartAccountId(request.getVatSettingId()));
            creditMemo.setDiscount(request.getDiscount());
            creditMemo.setDiscountPercent(request.getDiscountPercent());
            creditMemo.setTotalAmount(request.getSubTotal());
            creditMemo.setPriceTypeId(request.getPriceTypeId());
            creditMemo.setOrderDate(request.getOrderDate());
            creditMemo.setBalance(totalBalance);
            creditMemo.setDueDate(request.getDueDate());
            creditMemo.setTotalVat(request.getTotalVat());
            creditMemo.setVatPercent(request.getVatPercent());
            creditMemo.setMarkUp(request.getMarkUp());
            creditMemo.setCreated(formattedNow);
            creditMemo.setCreatedBy(userId);
            creditMemo.setStatus(1);
            Boolean result = creditMemoMapper.insert(creditMemo);

            if (result) {

                //! Get the reference code
                String code = generateCode.generateAutoCode("credit_memos", "cm_code", 7, request.getModuleCode(), true, "status >= 0");
                helperMapper.updateCode("credit_memos", "cm_code", code, creditMemo.getId());

                creditMemo.setCmCode(code);

                //! General Ledger
                GeneralLedger generalLedger = new GeneralLedger();
                generalLedger.setCreditMemoId(creditMemo.getId());
                generalLedger.setDate(request.getOrderDate());
                generalLedger.setReference(creditMemo.getCmCode());
                generalLedger.setIsActive(1);
                generalLedger.setIsAdj(0);
                generalLedger.setIsSys(1);
                generalLedger.setCreated(formattedNow);
                generalLedger.setCreatedBy(userId);
                helperMapper.insertGeneralLedger(generalLedger);

                //! General Ledger Detail (A/R)
                GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                generalLedgerDetail.setCompanyId(request.getCompanyId());
                generalLedgerDetail.setLocationId(request.getLocationId());
                generalLedgerDetail.setType("Credit Memo");
                generalLedgerDetail.setDebit(0D);
                generalLedgerDetail.setCredit(totalBalance);
                generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode());
                generalLedgerDetail.setCustomerId(request.getCustomerId());
                generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                //!// General Ledger Detail (Total Discount)
                if (creditMemo.getDiscount() > 0) {
                    GeneralLedgerDetail generalLedgerDetailDiscount = new GeneralLedgerDetail();
                    generalLedgerDetailDiscount.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetailDiscount.setChartAccountId(helperMapper.getDiscountChartAccountId(11L));
                    generalLedgerDetailDiscount.setCompanyId(request.getCompanyId());
                    generalLedgerDetailDiscount.setLocationId(request.getLocationId());
                    generalLedgerDetailDiscount.setType("Invoice");
                    generalLedgerDetailDiscount.setDebit(0D);
                    generalLedgerDetailDiscount.setCredit(creditMemo.getDiscount());
                    generalLedgerDetailDiscount.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + "Discount");
                    generalLedgerDetailDiscount.setCustomerId(request.getCustomerId());
                    generalLedgerDetailDiscount.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                    helperMapper.insertGeneralLedgerDetail(generalLedgerDetailDiscount);
                }

                //!// General Ledger Detail (Total Mark Up)
                if (creditMemo.getMarkUp() > 0) {
                    GeneralLedgerDetail generalLedgerDetailMarkUp = new GeneralLedgerDetail();
                    generalLedgerDetailMarkUp.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetailMarkUp.setChartAccountId(helperMapper.getMarkUpChartAccountId(17L));
                    generalLedgerDetailMarkUp.setCompanyId(request.getCompanyId());
                    generalLedgerDetailMarkUp.setLocationId(request.getLocationId());
                    generalLedgerDetailMarkUp.setType("Invoice");
                    generalLedgerDetailMarkUp.setDebit(creditMemo.getMarkUp());
                    generalLedgerDetailMarkUp.setCredit(0D);
                    generalLedgerDetailMarkUp.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + "Mark Up");
                    generalLedgerDetailMarkUp.setCustomerId(request.getCustomerId());
                    generalLedgerDetailMarkUp.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                    helperMapper.insertGeneralLedgerDetail(generalLedgerDetailMarkUp);
                }

                //!// General Ledger Detail (Total VAT)
                if (creditMemo.getTotalVat() > 0) {
                    GeneralLedgerDetail generalLedgerDetailVat = new GeneralLedgerDetail();
                    generalLedgerDetailVat.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetailVat.setChartAccountId(helperMapper.getVatChartAccountId(request.getVatSettingId()));
                    generalLedgerDetailVat.setCompanyId(request.getCompanyId());
                    generalLedgerDetailVat.setLocationId(request.getLocationId());
                    generalLedgerDetailVat.setType("VAT");
                    generalLedgerDetailVat.setCustomerId(request.getCustomerId());
                    generalLedgerDetailVat.setDebit(creditMemo.getTotalVat());
                    generalLedgerDetailVat.setCredit(0D);
                    generalLedgerDetailVat.setMemo("ICS: PO # " + creditMemo.getCmCode());
                    generalLedgerDetailVat.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                    helperMapper.insertGeneralLedgerDetail(generalLedgerDetailVat);
                }

                if (!request.getDetails().isEmpty()) {
                    for (int i = 0; i < request.getDetails().size(); i++) {
                        //! Details
                        CreditMemoRequestDetail detailRequest = request.getDetails().get(i);
                        if (request.getDetails().get(i).getType() == 1) {
                            CreditMemoDetail detail = new CreditMemoDetail();
                            Long conversionId = helperMapper.getSmallValUom(request.getDetails().get(i).getItemId()) / request.getDetails().get(i).getConversion();
                            detail.setCreditMemoId(creditMemo.getId());
                            detail.setDiscountId(request.getDetails().get(i).getDiscountId());
                            detail.setDiscountAmount(request.getDetails().get(i).getDiscountAmount());
                            detail.setDiscountPercent(request.getDetails().get(i).getDiscountPercent());
                            detail.setProductId(request.getDetails().get(i).getItemId());
                            detail.setQty(request.getDetails().get(i).getQty());
                            detail.setQtyFree(request.getDetails().get(i).getQtyFree());
                            detail.setUomId(request.getDetails().get(i).getUomId());
                            detail.setUnitPrice(request.getDetails().get(i).getUnitPrice());
                            detail.setTotalPrice(request.getDetails().get(i).getTotalPrice());
                            detail.setLotsNumber(request.getDetails().get(i).getLotsNumber());
                            detail.setExpiredDate(request.getDetails().get(i).getExpiredDate());
                            detail.setConversion(conversionId);
                            detail.setNote(request.getDetails().get(i).getNote());
                            creditMemoMapper.insertDetail(detail);

                            Integer checkSettingOption = helperMapper.checkSettingOption();

                            Long smallValUom = helperMapper.getSmallValUom(detail.getProductId());

                            Long conversion = detail.getConversion();

                            if (smallValUom == null || smallValUom == 0 || conversion == null || conversion == 0) {
                                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("Invalid UOM or conversion value: smallValUom=" + smallValUom + ", conversion=" + conversion, false));
                            }

                            long qtyValue = detail.getQty() != null ? detail.getQty() : 0L;
                            long qtyFree = detail.getQtyFree() != null ? detail.getQtyFree() : 0L;
                            long totalQty = qtyValue + qtyFree;

                            double baseQtyDenominator = (double) smallValUom / conversion;
                            double qty = baseQtyDenominator != 0 ? totalQty / baseQtyDenominator : 0D;
                            long smallQty = totalQty * conversion;

                            //! Global Variable
                            String productCodeName;
                            Double inventoryAsset;
                            Double cogs;
                            Long inventoryValuationId;

                            if (checkSettingOption == 1) {
                                //! Inventory Valuation
                                InventoryValuation inventoryValuation = new InventoryValuation();
                                inventoryValuation.setCreditMemoId(creditMemo.getId());
                                inventoryValuation.setCompanyId(request.getCompanyId());
                                inventoryValuation.setType("Credit Memo");
                                inventoryValuation.setDate(creditMemo.getOrderDate());
                                inventoryValuation.setProductId(request.getDetails().get(i).getItemId());
                                inventoryValuation.setSmallQty((double) smallQty);
                                inventoryValuation.setQty(qty);
                                inventoryValuation.setCost(request.getDetails().get(i).getUnitPrice());
                                inventoryValuation.setIsVarCost(1);
                                inventoryValuation.setIsActive(1);
                                helperMapper.insertInventoryValuation(inventoryValuation);

                                //! Get Product Data
                                ProductCodeNameResponse productCodeNames = helperMapper.getProductCodeName(request.getDetails().get(i).getItemId());
                                productCodeName = productCodeNames.getName();
                                inventoryValuationId = inventoryValuation.getId();
                                inventoryAsset = 0D;
                                cogs = 0D;
                            } else {
                                ProductCodeNameResponse productCodeNames = helperMapper.getProductCodeName(detail.getProductId());
                                inventoryValuationId = null;
                                productCodeName = productCodeNames.getName();
                                inventoryAsset = productCodeNames.getUnitCost();
                                cogs = productCodeNames.getUnitCost();
                            }

                            //! General Ledger Detail (Product Income)
                            generalLedgerDetail.setChartAccountId(helperMapper.getProductIncomeChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setLocationId(request.getLocationId());
                            generalLedgerDetail.setProductId(request.getDetails().get(i).getItemId());
                            generalLedgerDetail.setType("Credit Memo");
                            generalLedgerDetail.setDebit(request.getDetails().get(i).getTotalPrice());
                            generalLedgerDetail.setCredit(0D);
                            generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() +" "+ generalLedgerDetail.getProductId());
                            generalLedgerDetail.setCustomerId(request.getCustomerId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            //! General Ledger Detail (Product Discount)
                            if (request.getDetails().get(i).getDiscountAmount() > 0) {
                                generalLedgerDetail.setChartAccountId(helperMapper.getDiscountChartAccountId(11L));
                                generalLedgerDetail.setCompanyId(request.getCompanyId());
                                generalLedgerDetail.setLocationId(request.getLocationId());
                                generalLedgerDetail.setProductId(request.getDetails().get(i).getItemId());
                                generalLedgerDetail.setType("Invoice");
                                generalLedgerDetail.setDebit(0D);
                                generalLedgerDetail.setCredit(request.getDetails().get(i).getDiscountAmount());
                                generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + generalLedgerDetail.getProductId() + " " + " Discount");
                                generalLedgerDetail.setCustomerId(request.getCustomerId());
                                generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                                helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                            }

                            //! Update GL for Inventory
                            generalLedgerDetail.setChartAccountId(helperMapper.getProductInventoryChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setLocationId(request.getLocationId());
                            generalLedgerDetail.setProductId(request.getDetails().get(i).getItemId());
                            generalLedgerDetail.setInventoryValuationId(inventoryValuationId);
                            generalLedgerDetail.setInventoryValuationIsDebit(1);
                            generalLedgerDetail.setType("Credit Memo");
                            generalLedgerDetail.setDebit(inventoryAsset);
                            generalLedgerDetail.setCredit(0D);
                            generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + productCodeName);
                            generalLedgerDetail.setCustomerId(request.getCustomerId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            //! Update GL for COGS
                            generalLedgerDetail.setChartAccountId(helperMapper.getProductCOGSChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setLocationId(request.getLocationId());
                            generalLedgerDetail.setProductId(request.getDetails().get(i).getItemId());
                            generalLedgerDetail.setInventoryValuationId(inventoryValuationId);
                            generalLedgerDetail.setInventoryValuationIsDebit(0);
                            generalLedgerDetail.setType("Credit Memo");
                            generalLedgerDetail.setDebit(0D);
                            generalLedgerDetail.setCredit(cogs);
                            generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + productCodeName);
                            generalLedgerDetail.setCustomerId(request.getCustomerId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                        }

                        else if(request.getDetails().get(i).getType() == 2){
                            //! Service
                            CreditMemoServices services = new CreditMemoServices();
                            services.setCreditMemoId(creditMemo.getId());
                            services.setDiscountId(request.getDetails().get(i).getDiscountId());
                            services.setDiscountAmount(request.getDetails().get(i).getDiscountAmount());
                            services.setDiscountPercent(request.getDetails().get(i).getDiscountPercent());
                            services.setServiceId(request.getDetails().get(i).getItemId());
                            services.setQty(request.getDetails().get(i).getQty());
                            services.setQtyFree(request.getDetails().get(i).getQtyFree());
                            services.setUnitPrice(request.getDetails().get(i).getUnitPrice());
                            services.setTotalPrice(request.getDetails().get(i).getTotalPrice());
                            services.setNote(request.getDetails().get(i).getNote());
                            creditMemoMapper.insertService(services);

                            //! General Ledger Detail (Service)
                            generalLedgerDetail.setChartAccountId(helperMapper.getServiceChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setLocationId(request.getLocationId());
                            generalLedgerDetail.setServiceId(services.getServiceId());
                            generalLedgerDetail.setType("Credit Memo");
                            generalLedgerDetail.setDebit(request.getDetails().get(i).getTotalPrice());
                            generalLedgerDetail.setCredit(0D);
                            generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + services.getServiceId());
                            generalLedgerDetail.setCustomerId(request.getCustomerId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            //! General Ledger Detail (Service Discount)
                            if (services.getDiscountAmount() > 0) {
                                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                                generalLedgerDetail.setChartAccountId(helperMapper.getDiscountChartAccountId(11L));
                                generalLedgerDetail.setCompanyId(request.getCompanyId());
                                generalLedgerDetail.setLocationId(request.getLocationId());
                                generalLedgerDetail.setServiceId(request.getDetails().get(i).getItemId());
                                generalLedgerDetail.setType("Invoice");
                                generalLedgerDetail.setDebit(0D);
                                generalLedgerDetail.setCredit(request.getDetails().get(i).getDiscountAmount());
                                generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + services.getServiceId() + " Discount");
                                generalLedgerDetail.setCustomerId(request.getCustomerId());
                                generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                                helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                            }

                        } else{
                            //! Misc
                            CreditMemoMisc creditMemoMisc = new CreditMemoMisc();
                            creditMemoMisc.setCreditMemoId(creditMemo.getId());
                            creditMemoMisc.setDiscountId(request.getDetails().get(i).getDiscountId());
                            creditMemoMisc.setDiscountAmount(request.getDetails().get(i).getDiscountAmount());
                            creditMemoMisc.setDiscountPercent(request.getDetails().get(i).getDiscountPercent());
                            creditMemoMisc.setDescription(request.getDetails().get(i).getItemName());
                            creditMemoMisc.setUomId(request.getDetails().get(i).getUomId());
                            creditMemoMisc.setQty(request.getDetails().get(i).getQty());
                            creditMemoMisc.setQtyFree(request.getDetails().get(i).getQtyFree());
                            creditMemoMisc.setUnitPrice(request.getDetails().get(i).getUnitPrice());
                            creditMemoMisc.setTotalPrice(request.getDetails().get(i).getTotalPrice());
                            creditMemoMisc.setNote(request.getDetails().get(i).getNote());
                            creditMemoMapper.insertMisc(creditMemoMisc);

                            //! General Ledger Detail (Misc)
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            generalLedgerDetail.setChartAccountId(helperMapper.getMiscChartAccountId());
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setLocationId(request.getLocationId());
                            generalLedgerDetail.setType("Credit Memo");
                            generalLedgerDetail.setDebit(request.getDetails().get(i).getTotalPrice());
                            generalLedgerDetail.setCredit(0D);
                            generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + creditMemoMisc.getDescription());
                            generalLedgerDetail.setCustomerId(request.getCustomerId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            //! General Ledger Detail (Misc Discount)
                            if (creditMemoMisc.getDiscountAmount() > 0) {
                                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                                generalLedgerDetail.setChartAccountId(helperMapper.getDiscountChartAccountId(11L));
                                generalLedgerDetail.setCompanyId(request.getCompanyId());
                                generalLedgerDetail.setLocationId(request.getLocationId());
                                generalLedgerDetail.setType("Invoice");
                                generalLedgerDetail.setDebit(0D);
                                generalLedgerDetail.setCredit(creditMemoMisc.getDiscountAmount());
                                generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + creditMemoMisc.getDescription() + " Discount");
                                generalLedgerDetail.setCustomerId(request.getCustomerId());
                                generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                                helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                            }
                        }
                    }
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/credit-memo/add", null, null, "Credit Memo", "Credit Memo (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/credit-memo/add", line, error.toString(), "Credit Memo", "Credit Memo (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(CreditMemoUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalDateTime now = LocalDateTime.now();
        String formattedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Credit Memo (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Calculate Total Balance
            Double totalBalance = request.getSubTotal() + request.getMarkUp() + request.getTotalVat() - request.getDiscount();

            //! Credit Memo
            CreditMemo creditMemo = new CreditMemo();
            creditMemo.setCompanyId(request.getCompanyId());
            creditMemo.setLocationGroupId(request.getLocationGroupId());
            creditMemo.setLocationId(request.getLocationId());
            creditMemo.setCustomerId(request.getCustomerId());
            creditMemo.setCurrencyCenterId(helperMapper.getCompanyCurrencyCenter(request.getCompanyId()));
            creditMemo.setReasonId(request.getCmTermId());
            creditMemo.setSaleOrderId(request.getSaleInvoiceId());
            creditMemo.setInvoiceCode(request.getInvoiceCode());
            creditMemo.setInvoiceDate(request.getInvoiceDate());
            creditMemo.setNote(request.getNote());
            if (request.getChartAccountId() != null){
                creditMemo.setChartAccountId(request.getChartAccountId());
            } else {
                creditMemo.setChartAccountId(helperMapper.getCmChartAccountId());
            }
            creditMemo.setVatSettingId(request.getVatSettingId());
            creditMemo.setVatCalculate(helperMapper.getVatCalculate(request.getCompanyId()));
            creditMemo.setVatChartAccountId(helperMapper.getVatChartAccountId(request.getVatSettingId()));
            creditMemo.setDiscount(request.getDiscount());
            creditMemo.setDiscountPercent(request.getDiscountPercent());
            creditMemo.setTotalAmount(request.getSubTotal());
            creditMemo.setPriceTypeId(request.getPriceTypeId());
            creditMemo.setOrderDate(request.getOrderDate());
            creditMemo.setBalance(totalBalance);
            creditMemo.setDueDate(request.getDueDate());
            creditMemo.setTotalVat(request.getTotalVat());
            creditMemo.setVatPercent(request.getVatPercent());
            creditMemo.setMarkUp(request.getMarkUp());
            creditMemo.setCreated(formattedNow);
            creditMemo.setCreatedBy(userId);
            creditMemo.setStatus(1);
            Boolean result = creditMemoMapper.insert(creditMemo);

            if (result) {
                helperMapper.archive("credit_memos", "status", -1, request.getId(), userId);

                //! Get the reference code
                String code = (helperMapper.getCurrentCode("credit_memos", "cm_code", request.getId()));
                helperMapper.updateCode("credit_memos", "cm_code", code, creditMemo.getId());

                creditMemo.setCmCode(code);

                //! General Ledger
                GeneralLedger generalLedger = new GeneralLedger();
                generalLedger.setCreditMemoId(creditMemo.getId());
                generalLedger.setDate(request.getOrderDate());
                generalLedger.setReference(creditMemo.getCmCode());
                generalLedger.setIsActive(1);
                generalLedger.setIsAdj(0);
                generalLedger.setIsSys(1);
                generalLedger.setCreated(formattedNow);
                generalLedger.setCreatedBy(userId);
                helperMapper.insertGeneralLedger(generalLedger);

                //! General Ledger Detail (A/R)
                GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                generalLedgerDetail.setCompanyId(request.getCompanyId());
                generalLedgerDetail.setLocationId(request.getLocationId());
                generalLedgerDetail.setType("Credit Memo");
                generalLedgerDetail.setDebit(0D);
                generalLedgerDetail.setCredit(totalBalance);
                generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode());
                generalLedgerDetail.setCustomerId(request.getCustomerId());
                generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                //!// General Ledger Detail (Total Discount)
                if (creditMemo.getDiscount() > 0) {
                    GeneralLedgerDetail generalLedgerDetailDiscount = new GeneralLedgerDetail();
                    generalLedgerDetailDiscount.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetailDiscount.setChartAccountId(helperMapper.getDiscountChartAccountId(11L));
                    generalLedgerDetailDiscount.setCompanyId(request.getCompanyId());
                    generalLedgerDetailDiscount.setLocationId(request.getLocationId());
                    generalLedgerDetailDiscount.setType("Invoice");
                    generalLedgerDetailDiscount.setDebit(0D);
                    generalLedgerDetailDiscount.setCredit(creditMemo.getDiscount());
                    generalLedgerDetailDiscount.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + "Discount");
                    generalLedgerDetailDiscount.setCustomerId(request.getCustomerId());
                    generalLedgerDetailDiscount.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                    helperMapper.insertGeneralLedgerDetail(generalLedgerDetailDiscount);
                }

                //!// General Ledger Detail (Total Mark Up)
                if (creditMemo.getMarkUp() > 0) {
                    GeneralLedgerDetail generalLedgerDetailMarkUp = new GeneralLedgerDetail();
                    generalLedgerDetailMarkUp.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetailMarkUp.setChartAccountId(helperMapper.getMarkUpChartAccountId(17L));
                    generalLedgerDetailMarkUp.setCompanyId(request.getCompanyId());
                    generalLedgerDetailMarkUp.setLocationId(request.getLocationId());
                    generalLedgerDetailMarkUp.setType("Invoice");
                    generalLedgerDetailMarkUp.setDebit(creditMemo.getMarkUp());
                    generalLedgerDetailMarkUp.setCredit(0D);
                    generalLedgerDetailMarkUp.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + "Mark Up");
                    generalLedgerDetailMarkUp.setCustomerId(request.getCustomerId());
                    generalLedgerDetailMarkUp.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                    helperMapper.insertGeneralLedgerDetail(generalLedgerDetailMarkUp);
                }

                //!// General Ledger Detail (Total VAT)
                if (creditMemo.getTotalVat() > 0) {
                    GeneralLedgerDetail generalLedgerDetailVat = new GeneralLedgerDetail();
                    generalLedgerDetailVat.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetailVat.setChartAccountId(helperMapper.getVatChartAccountId(request.getVatSettingId()));
                    generalLedgerDetailVat.setCompanyId(request.getCompanyId());
                    generalLedgerDetailVat.setLocationId(request.getLocationId());
                    generalLedgerDetailVat.setType("VAT");
                    generalLedgerDetailVat.setCustomerId(request.getCustomerId());
                    generalLedgerDetailVat.setDebit(creditMemo.getTotalVat());
                    generalLedgerDetailVat.setCredit(0D);
                    generalLedgerDetailVat.setMemo("ICS: PO # " + creditMemo.getCmCode());
                    generalLedgerDetailVat.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                    helperMapper.insertGeneralLedgerDetail(generalLedgerDetailVat);
                }

                if (!request.getDetails().isEmpty()) {
                    for (int i = 0; i < request.getDetails().size(); i++) {
                        //! Details
                        CreditMemoRequestDetail detailRequest = request.getDetails().get(i);
                        if (request.getDetails().get(i).getType() == 1) {
                            CreditMemoDetail detail = new CreditMemoDetail();
                            Long conversionId = helperMapper.getSmallValUom(request.getDetails().get(i).getItemId()) / request.getDetails().get(i).getConversion();
                            detail.setCreditMemoId(creditMemo.getId());
                            detail.setDiscountId(request.getDetails().get(i).getDiscountId());
                            detail.setDiscountAmount(request.getDetails().get(i).getDiscountAmount());
                            detail.setDiscountPercent(request.getDetails().get(i).getDiscountPercent());
                            detail.setProductId(request.getDetails().get(i).getItemId());
                            detail.setQty(request.getDetails().get(i).getQty());
                            detail.setQtyFree(request.getDetails().get(i).getQtyFree());
                            detail.setUomId(request.getDetails().get(i).getUomId());
                            detail.setUnitPrice(request.getDetails().get(i).getUnitPrice());
                            detail.setTotalPrice(request.getDetails().get(i).getTotalPrice());
                            detail.setLotsNumber(request.getDetails().get(i).getLotsNumber());
                            detail.setExpiredDate(request.getDetails().get(i).getExpiredDate());
                            detail.setConversion(conversionId);
                            detail.setNote(request.getDetails().get(i).getNote());
                            creditMemoMapper.insertDetail(detail);

                            Integer checkSettingOption = helperMapper.checkSettingOption();

                            Long smallValUom = helperMapper.getSmallValUom(detail.getProductId());

                            Long conversion = detail.getConversion();

                            if (smallValUom == null || smallValUom == 0 || conversion == null || conversion == 0) {
                                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("Invalid UOM or conversion value: smallValUom=" + smallValUom + ", conversion=" + conversion, false));
                            }

                            long qtyValue = detail.getQty() != null ? detail.getQty() : 0L;
                            long qtyFree = detail.getQtyFree() != null ? detail.getQtyFree() : 0L;
                            long totalQty = qtyValue + qtyFree;

                            double baseQtyDenominator = (double) smallValUom / conversion;
                            double qty = baseQtyDenominator != 0 ? totalQty / baseQtyDenominator : 0D;
                            long smallQty = totalQty * conversion;

                            //! Global Variable
                            String productCodeName;
                            Double inventoryAsset;
                            Double cogs;
                            Long inventoryValuationId;

                            if (checkSettingOption == 1) {
                                //! Inventory Valuation
                                InventoryValuation inventoryValuation = new InventoryValuation();
                                inventoryValuation.setCreditMemoId(creditMemo.getId());
                                inventoryValuation.setCompanyId(request.getCompanyId());
                                inventoryValuation.setType("Credit Memo");
                                inventoryValuation.setDate(creditMemo.getOrderDate());
                                inventoryValuation.setProductId(request.getDetails().get(i).getItemId());
                                inventoryValuation.setSmallQty((double) smallQty);
                                inventoryValuation.setQty(qty);
                                inventoryValuation.setCost(request.getDetails().get(i).getUnitPrice());
                                inventoryValuation.setIsVarCost(1);
                                inventoryValuation.setIsActive(1);
                                helperMapper.insertInventoryValuation(inventoryValuation);

                                //! Get Product Data
                                ProductCodeNameResponse productCodeNames = helperMapper.getProductCodeName(request.getDetails().get(i).getItemId());
                                productCodeName = productCodeNames.getName();
                                inventoryValuationId = inventoryValuation.getId();
                                inventoryAsset = 0D;
                                cogs = 0D;
                            } else {
                                ProductCodeNameResponse productCodeNames = helperMapper.getProductCodeName(detail.getProductId());
                                inventoryValuationId = null;
                                productCodeName = productCodeNames.getName();
                                inventoryAsset = productCodeNames.getUnitCost();
                                cogs = productCodeNames.getUnitCost();
                            }

                            //! General Ledger Detail (Product Income)
                            generalLedgerDetail.setChartAccountId(helperMapper.getProductIncomeChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setLocationId(request.getLocationId());
                            generalLedgerDetail.setProductId(request.getDetails().get(i).getItemId());
                            generalLedgerDetail.setType("Credit Memo");
                            generalLedgerDetail.setDebit(request.getDetails().get(i).getTotalPrice());
                            generalLedgerDetail.setCredit(0D);
                            generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() +" "+ generalLedgerDetail.getProductId());
                            generalLedgerDetail.setCustomerId(request.getCustomerId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            //! General Ledger Detail (Product Discount)
                            if (request.getDetails().get(i).getDiscountAmount() > 0) {
                                generalLedgerDetail.setChartAccountId(helperMapper.getDiscountChartAccountId(11L));
                                generalLedgerDetail.setCompanyId(request.getCompanyId());
                                generalLedgerDetail.setLocationId(request.getLocationId());
                                generalLedgerDetail.setProductId(request.getDetails().get(i).getItemId());
                                generalLedgerDetail.setType("Invoice");
                                generalLedgerDetail.setDebit(0D);
                                generalLedgerDetail.setCredit(request.getDetails().get(i).getDiscountAmount());
                                generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + generalLedgerDetail.getProductId() + " " + " Discount");
                                generalLedgerDetail.setCustomerId(request.getCustomerId());
                                generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                                helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                            }

                            //! Update GL for Inventory
                            generalLedgerDetail.setChartAccountId(helperMapper.getProductInventoryChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setLocationId(request.getLocationId());
                            generalLedgerDetail.setProductId(request.getDetails().get(i).getItemId());
                            generalLedgerDetail.setInventoryValuationId(inventoryValuationId);
                            generalLedgerDetail.setInventoryValuationIsDebit(1);
                            generalLedgerDetail.setType("Credit Memo");
                            generalLedgerDetail.setDebit(inventoryAsset);
                            generalLedgerDetail.setCredit(0D);
                            generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + productCodeName);
                            generalLedgerDetail.setCustomerId(request.getCustomerId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            //! Update GL for COGS
                            generalLedgerDetail.setChartAccountId(helperMapper.getProductCOGSChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setLocationId(request.getLocationId());
                            generalLedgerDetail.setProductId(request.getDetails().get(i).getItemId());
                            generalLedgerDetail.setInventoryValuationId(inventoryValuationId);
                            generalLedgerDetail.setInventoryValuationIsDebit(0);
                            generalLedgerDetail.setType("Credit Memo");
                            generalLedgerDetail.setDebit(0D);
                            generalLedgerDetail.setCredit(cogs);
                            generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + productCodeName);
                            generalLedgerDetail.setCustomerId(request.getCustomerId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                        }

                        else if(request.getDetails().get(i).getType() == 2){
                            //! Service
                            CreditMemoServices services = new CreditMemoServices();
                            services.setCreditMemoId(creditMemo.getId());
                            services.setDiscountId(request.getDetails().get(i).getDiscountId());
                            services.setDiscountAmount(request.getDetails().get(i).getDiscountAmount());
                            services.setDiscountPercent(request.getDetails().get(i).getDiscountPercent());
                            services.setServiceId(request.getDetails().get(i).getItemId());
                            services.setQty(request.getDetails().get(i).getQty());
                            services.setQtyFree(request.getDetails().get(i).getQtyFree());
                            services.setUnitPrice(request.getDetails().get(i).getUnitPrice());
                            services.setTotalPrice(request.getDetails().get(i).getTotalPrice());
                            services.setNote(request.getDetails().get(i).getNote());
                            creditMemoMapper.insertService(services);

                            //! General Ledger Detail (Service)
                            generalLedgerDetail.setChartAccountId(helperMapper.getServiceChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setLocationId(request.getLocationId());
                            generalLedgerDetail.setServiceId(services.getServiceId());
                            generalLedgerDetail.setType("Credit Memo");
                            generalLedgerDetail.setDebit(request.getDetails().get(i).getTotalPrice());
                            generalLedgerDetail.setCredit(0D);
                            generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + services.getServiceId());
                            generalLedgerDetail.setCustomerId(request.getCustomerId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            //! General Ledger Detail (Service Discount)
                            if (services.getDiscountAmount() > 0) {
                                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                                generalLedgerDetail.setChartAccountId(helperMapper.getDiscountChartAccountId(11L));
                                generalLedgerDetail.setCompanyId(request.getCompanyId());
                                generalLedgerDetail.setLocationId(request.getLocationId());
                                generalLedgerDetail.setServiceId(request.getDetails().get(i).getItemId());
                                generalLedgerDetail.setType("Invoice");
                                generalLedgerDetail.setDebit(0D);
                                generalLedgerDetail.setCredit(request.getDetails().get(i).getDiscountAmount());
                                generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + services.getServiceId() + " Discount");
                                generalLedgerDetail.setCustomerId(request.getCustomerId());
                                generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                                helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                            }

                        } else{
                            //! Misc
                            CreditMemoMisc creditMemoMisc = new CreditMemoMisc();
                            creditMemoMisc.setCreditMemoId(creditMemo.getId());
                            creditMemoMisc.setDiscountId(request.getDetails().get(i).getDiscountId());
                            creditMemoMisc.setDiscountAmount(request.getDetails().get(i).getDiscountAmount());
                            creditMemoMisc.setDiscountPercent(request.getDetails().get(i).getDiscountPercent());
                            creditMemoMisc.setDescription(request.getDetails().get(i).getItemName());
                            creditMemoMisc.setUomId(request.getDetails().get(i).getUomId());
                            creditMemoMisc.setQty(request.getDetails().get(i).getQty());
                            creditMemoMisc.setQtyFree(request.getDetails().get(i).getQtyFree());
                            creditMemoMisc.setUnitPrice(request.getDetails().get(i).getUnitPrice());
                            creditMemoMisc.setTotalPrice(request.getDetails().get(i).getTotalPrice());
                            creditMemoMisc.setNote(request.getDetails().get(i).getNote());
                            creditMemoMapper.insertMisc(creditMemoMisc);

                            //! General Ledger Detail (Misc)
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            generalLedgerDetail.setChartAccountId(helperMapper.getMiscChartAccountId());
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setLocationId(request.getLocationId());
                            generalLedgerDetail.setType("Credit Memo");
                            generalLedgerDetail.setDebit(request.getDetails().get(i).getTotalPrice());
                            generalLedgerDetail.setCredit(0D);
                            generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + creditMemoMisc.getDescription());
                            generalLedgerDetail.setCustomerId(request.getCustomerId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            //! General Ledger Detail (Misc Discount)
                            if (creditMemoMisc.getDiscountAmount() > 0) {
                                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                                generalLedgerDetail.setChartAccountId(helperMapper.getDiscountChartAccountId(11L));
                                generalLedgerDetail.setCompanyId(request.getCompanyId());
                                generalLedgerDetail.setLocationId(request.getLocationId());
                                generalLedgerDetail.setType("Invoice");
                                generalLedgerDetail.setDebit(0D);
                                generalLedgerDetail.setCredit(creditMemoMisc.getDiscountAmount());
                                generalLedgerDetail.setMemo("ICS: Credit Memo # " + creditMemo.getCmCode() + " " + creditMemoMisc.getDescription() + " Discount");
                                generalLedgerDetail.setCustomerId(request.getCustomerId());
                                generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getLocationGroupId()));
                                helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                            }
                        }
                    }
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/credit-memo/update", null, null, "Credit Memo", "Credit Memo (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/credit-memo/update", line, error.toString(), "Credit Memo", "Credit Memo (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Credit Memo (Void)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = helperMapper.archive("credit_memos", "status", 0, id, userId);

            if (result) {

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/credit-memo/delete/{id}", null, null, "Credit Memo", "Credit Memo (Void)", "Void", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/credit-memo/delete/{id}", line, error.toString(), "Credit Memo", "Credit Memo (Void)", "Void", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> receive(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Credit Memo (Receive Products)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            helperMapper.updateStatus("credit_memos", "status", 2L, userId, id);

            List<CreditMemoResponse> responses = creditMemoMapper.getOne(id, userId);

            if (!responses.isEmpty()) {
                List<CreditMemoDetailResponse> details = creditMemoMapper.getListDetail(responses.get(0).getId());

                for(CreditMemoDetailResponse detail : details) {
                    GlobalStock globalStock  = new GlobalStock();
                    globalStock.setCreditMemoId(responses.get(0).getId());
                    globalStock.setProductId(detail.getItemId());
                    globalStock.setLocationId(responses.get(0).getLocationId());
                    globalStock.setWarehouseId(responses.get(0).getWarehouseId());
                    globalStock.setLotsNumber("0");
                    if (detail.getExpiredDate() == null){
                        globalStock.setExpiredDate("0000-00-00");
                    } else {
                        globalStock.setExpiredDate(detail.getExpiredDate());
                    }
                    globalStock.setConversion(detail.getConversion());
                    globalStock.setTotalQty((detail.getQty() + detail.getQtyFree()) * detail.getConversion());
                    globalStock.setTotalCm(detail.getQty() * detail.getConversion());
                    globalStock.setTotalCmFree(detail.getQtyFree() * detail.getConversion());
                    globalStock.setType("Credit Memo");
                    globalStock.setCreatedBy(userId);
                    globalStock.setCustomerId(responses.get(0).getOrganizationId());
                    globalStock.setUnitCost(detail.getUnitPrice());
                    inventory.insertStock(globalStock, 4L,"total_cm", "total_cm_free");
                }

                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/credit-memo/receive", null, null, "Credit Memo", "Credit Memo (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/credit-memo/receive", line, error.toString(), "Credit Memo", "Credit Memo (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> pay(CreditMemoPayRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Credit Memo (Aging)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            CreditMemoReceipt receipt = new CreditMemoReceipt();
            receipt.setCreditMemoId(request.getCreditMemoId());
            receipt.setExchangeRateId(request.getExchangeRateId());
            receipt.setBalance(request.getBalance());
            receipt.setBalanceOther(request.getBalanceOther());
            receipt.setTotalAmount(request.getTotalAmount());
            receipt.setCurrencyCenterId(request.getCurrencyCenterId());
            receipt.setChartAccountId(request.getChartAccountId());
            receipt.setAmountUsd(request.getPaidUsd());
            receipt.setAmountOther(request.getPaidOther());
            receipt.setDate(request.getDate());
            receipt.setAging(request.getAging());
            receipt.setCreatedBy(userId);
            Boolean result = creditMemoMapper.insertReceipt(receipt);

            if (result) {
                //! Get the reference code
                String code = (generateCode.generateAutoCode("credit_memo_receipts", "receipt_code", 7, request.getModuleCode(), true, null));
                helperMapper.updateCode("credit_memo_receipts", "receipt_code", code, receipt.getId());
                receipt.setReceiptCode(code);

                creditMemoMapper.updateBalance(request.getCreditMemoId(), request.getBalance(), 3L);

                List<CreditMemoResponse> responses = creditMemoMapper.getOne(request.getCreditMemoId(), userId);
                if (responses!= null && !responses.isEmpty()) {
                    // Insert General Ledger
                    GeneralLedger generalLedger = new GeneralLedger();
                    generalLedger.setCreditMemoId(request.getCreditMemoId());
                    generalLedger.setCreditMemoReceiptId(receipt.getId());
                    generalLedger.setDate(request.getDate());
                    generalLedger.setReference(receipt.getReceiptCode());
                    generalLedger.setCreatedBy(userId);
                    generalLedger.setIsSys(1);
                    generalLedger.setIsAdj(0);
                    generalLedger.setIsActive(1);
                    helperMapper.insertGeneralLedger(generalLedger);

                    // Insert General Ledger Detail
                    GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                    generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetail.setType("Credit Memo Check");
                    generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                    generalLedgerDetail.setCustomerId(responses.get(0).getOrganizationId());
                    generalLedgerDetail.setCompanyId(responses.get(0).getCompanyId());
                    generalLedgerDetail.setClassId(helperMapper.getClassId(responses.get(0).getCompanyId(), responses.get(0).getWarehouseId()));
                    generalLedgerDetail.setMemo("ICS: Credit Memo Check for CM #  " + receipt.getReceiptCode());
                    generalLedgerDetail.setDebit(request.getPaidUsd());
                    generalLedgerDetail.setCredit(0D);
                    helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                    generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetail.setType("Credit Memo Check");
                    generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                    generalLedgerDetail.setCustomerId(responses.get(0).getOrganizationId());
                    generalLedgerDetail.setCompanyId(responses.get(0).getCompanyId());
                    generalLedgerDetail.setClassId(helperMapper.getClassId(responses.get(0).getCompanyId(), responses.get(0).getWarehouseId()));
                    generalLedgerDetail.setMemo("ICS: Credit Memo Check for CM #  " + receipt.getReceiptCode());
                    generalLedgerDetail.setDebit(0D);
                    generalLedgerDetail.setCredit(request.getPaidUsd());
                    helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                }

                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/credit-memo/pay", null, null, "Credit Memo", "Credit Memo (Aging)", "Aging", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/credit-memo/pay", line, error.toString(), "Credit Memo", "Credit Memo (Aging)", "Aging", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> applyWithInvoice(ApplyWithInvoiceRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Credit Memo (Aging)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            double totalAmountInvoice = 0D;
            boolean result = false;

            CreditMemoWithInvoiceReceipt receipt = new CreditMemoWithInvoiceReceipt();
            receipt.setCreditMemoId(request.getCreditMemoId());
            receipt.setApplyDate(request.getApplyDate());
            receipt.setCreatedBy(userId);

            List<ApplyWithInvoiceDetailRequest> details = request.getDetails();
            System.out.println(details);
            if (!details.isEmpty()) {
                for (ApplyWithInvoiceDetailRequest detail : details) {
                    receipt.setSalesInvoiceId(detail.getSalesInvoiceId());
                    receipt.setTotalCost(detail.getPaid());
                    creditMemoMapper.updateBalanceSaleInvoice(detail.getSalesInvoiceId(), detail.getPaid(), 1L, userId);
                    totalAmountInvoice += detail.getPaid();
                    creditMemoMapper.insertReceiptWithInvoice(receipt);
                }
                result = true;
            }
            System.out.println(result);
            if (result) {
                List<CreditMemoResponse> responses = creditMemoMapper.getOne(request.getCreditMemoId(), userId);
                System.out.println(responses);
                if (responses!= null && !responses.isEmpty()) {
                    CreditMemo creditMemo = new CreditMemo();
                    creditMemo.setId(responses.get(0).getId());
                    creditMemo.setBalance(responses.get(0).getBalance() - totalAmountInvoice);
                    creditMemo.setTotalAmount(responses.get(0).getTotalAmount() + totalAmountInvoice);
                    creditMemo.setModifiedBy(userId);
                    System.out.println(creditMemo);
                    creditMemoMapper.updateBalanceCreditMemo(creditMemo);
                }
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/credit-memo/apply-with-invoice", null, null, "Credit Memo", "Credit Memo (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        }catch(Exception error){
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/credit-memo/apply-with-invoice", line, error.toString(), "Credit Memo", "Credit Memo (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listReceipt(CreditMemoReceiptFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Credit Memo (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<CreditMemoReceiptResponse> responses;
            if (filter.getType() == 1){
                responses = creditMemoMapper.listReceipt(filter);
            } else {
                responses = creditMemoMapper.listReceiptApplyInvoice(filter);
            }
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/credit-memo/list-receipt/{id}", null, null, "Credit Memo", "Credit Memo (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/credit-memo/list-receipt/{id}", line, error.toString(), "Credit Memo", "Credit Memo (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> voidReceipt(CreditMemoReceiptFilter request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Credit Memo (Void)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Boolean result;

            if (request.getType() == 1){
                creditMemoMapper.updateBalance(request.getCreditMemoId(), request.getBalance(), 2L);
                result = creditMemoMapper.voidReceipt(request, userId);
            } else {
                creditMemoMapper.updateBalance(request.getCreditMemoId(), request.getBalance(), 2L);
                creditMemoMapper.updateBalanceSaleInvoice(request.getSalesInvoiceId(), request.getBalance(), 2L, userId);
                result = creditMemoMapper.voidReceiptApplyInvoice(request, userId);
            }

            if (result) {
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/credit-memo/void-receipt/{id}", null, null, "Credit Memo", "Credit Memo (Void)", "Void", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }

        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/credit-memo/void-receipt/{id}", line, error.toString(), "Credit Memo", "Credit Memo (Void)", "Void", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}
