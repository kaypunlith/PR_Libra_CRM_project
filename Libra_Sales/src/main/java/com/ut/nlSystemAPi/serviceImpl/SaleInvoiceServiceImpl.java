package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.Inventory;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.helper.Telegram.CheckNull;
import com.ut.nlSystemAPi.helper.TelegramUtils;
import com.ut.nlSystemAPi.mapper.freedom.FreedomMapper;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.base.*;
import com.ut.nlSystemAPi.model.GeneralLedger;
import com.ut.nlSystemAPi.model.GeneralLedgerDetail;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.entity.MembershipCard.MembershipCard;
import com.ut.nlSystemAPi.model.entity.MembershipCardLevel.MembershipCardLevel;
import com.ut.nlSystemAPi.model.entity.SaleInvoice.*;
import com.ut.nlSystemAPi.model.filter.DepartmentFilter;
import com.ut.nlSystemAPi.model.filter.SaleInvoiceFilter;
import com.ut.nlSystemAPi.model.filter.SalesInvoiceReceiptFilter;
import com.ut.nlSystemAPi.model.request.Delivery.DeliveryRequest;
import com.ut.nlSystemAPi.model.request.SaleInvoice.SaleInvoiceDetailRequest;
import com.ut.nlSystemAPi.model.request.SaleInvoice.SaleInvoiceRequest;
import com.ut.nlSystemAPi.model.request.SaleInvoice.SaleInvoiceUpdateRequest;
import com.ut.nlSystemAPi.model.base.ProductCodeNameResponse;
import com.ut.nlSystemAPi.model.request.SaleInvoice.SalesInvoicePayRequest;
import com.ut.nlSystemAPi.model.response.Dropdown.DropdownResponse;
import com.ut.nlSystemAPi.model.response.SaleInvoice.SaleInvoiceResponse;
import com.ut.nlSystemAPi.model.response.SaleInvoice.SalesInvoiceReceiptResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.DeliveryService;
import com.ut.nlSystemAPi.service.SaleInvoiceService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaleInvoiceServiceImpl implements SaleInvoiceService {

    @Autowired
    private SaleInvoiceMapper saleInvoiceMapper;

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

    @Autowired
    private FreedomMapper freedomMapper;

    @Autowired
    private OrganizationMapper organizationMapper;
    
    @Autowired
    private Inventory inventory;

    @Autowired
    private SaleOrderMapper saleOrderMapper;

    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DropdownMapper dropdownMapper;

    @Autowired
    private MembershipCardMapper membershipCardMapper;

    @Override
    public ResponseMessage<BaseResult> getList(SaleInvoiceFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales / Invoice (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (permissionMapper.checkPermission(userId, "Sales / Invoice (View By User)") > 0) {
                filter.setViewByUser(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(saleInvoiceMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SaleInvoiceResponse> responses = saleInvoiceMapper.getList(filter, userId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-invoice/list", null, null, "Sales / Invoice", "Sales / Invoice (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-invoice/list", line, error.toString(), "Sales / Invoice", "Sales / Invoice (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Sales / Invoice (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<SaleInvoiceResponse> responses = saleInvoiceMapper.getOne(id, userId);

            if (!responses.isEmpty()) {
                DepartmentFilter departmentFilter = new DepartmentFilter();
                for (SaleInvoiceResponse response : responses) {
                    departmentFilter.setId(response.getDepartmentId());
                    List<DropdownResponse> department = dropdownMapper.getListDepartment(departmentFilter);
                    response.setDepartmentName(department.get(0).getName());
                    response.setTermConditions(saleInvoiceMapper.getTermCondition(response.getId()));
                    response.setInvoiceDeposits(saleInvoiceMapper.getInvoiceDeposit(response.getId()));
                    response.setDetails(saleInvoiceMapper.getListDetail(response.getId()));
                    response.setSalesOrderDetails(saleOrderMapper.getListDetail(response.getSoId(), response.getWarehouseId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-invoice/find/{id}", null, null, "Sales / Invoice", "Sales / Invoice (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-invoice/find/{id}", line, error.toString(), "Sales / Invoice", "Sales / Invoice (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOneByCode(String code, HttpServletRequest httpServletRequest) throws UnknownHostException {

            Long id = saleInvoiceMapper.getIdByCode(code);

            return this.getOne(id, httpServletRequest);
    }

    @Override
    public ResponseMessage<BaseResult> insert(SaleInvoiceRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
                LocalDateTime now = LocalDateTime.now();
                String formattedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                // Check Permission
                Long userId = userService.getUserAuth().getId();
                if (request.getIsUpdate() != null && request.getIsUpdate() == 1){
                    if (permissionMapper.checkPermission(userId, "Sales / Invoice (Edit)") == 0) {
                        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                    }
                } else {
                    if (permissionMapper.checkPermission(userId, "Sales / Invoice (Add)") == 0) {
                        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                    }
                }

                Integer locationStatus = helperMapper.checkLocationSetting(4);

                String tableName = request.getWarehouseId() + "_group_totals";

                // CHECK STOCK
                if (!request.getDetails().isEmpty()) {
                    for (SaleInvoiceDetailRequest detail : request.getDetails()) {
                        if (detail.getType() == 1){
                            Long productId = detail.getItemId();

                            // Get total stock available
                            Double totalStock = saleInvoiceMapper.getTotalQtyStock(request.getWarehouseId(), tableName, productId, locationStatus);
                            if (totalStock == null) {
                                totalStock = 0D;
                            }


                            // Get total qty already order
                            Double totalOrder = saleInvoiceMapper.getTotalQtyOrder(request.getId(), request.getWarehouseId(), productId);
                            if (totalOrder == null) {
                                totalOrder = 0D;
                            }

                            Long smallValUom = helperMapper.getSmallValUom(productId);

                            // Compute remaining stock
                            double availableForSale = totalStock - totalOrder;

                            // Compare
                            long requestedQty = detail.getQty() * (smallValUom/ detail.getConversion());

                            if (requestedQty > availableForSale) {
                                ProductCodeNameResponse product = helperMapper.getProductCodeName(productId);
                                return ResponseMessageUtils.makeResponseByPermission(false, messageService.message("Product " + product.getName() + " out of stock! Available: " + availableForSale, false));
                            }
                        }
                    }
                }

         
                double totalDeposit = 0;
            
                double totalAmount;

                if (request.getInvoiceDeposits() != null && !request.getInvoiceDeposits().isEmpty()) {
                    for (Long depositId : request.getInvoiceDeposits()) {
                        if (depositId != null) {
                            Double depositAmount = saleInvoiceMapper.getDepositAmount(depositId);
                            if (depositAmount != null) {
                                totalDeposit += depositAmount;
                                // Close Deposit
                                saleInvoiceMapper.closeDeposit(depositId);
                            }
                        }
                    }
                    if (request.getTotalAmount() < totalDeposit) {
                        return ResponseMessageUtils.makeResponseByPermission(false, messageService.message("Total amount cannot be less than total deposit.", false));
                    }
                }


                if (totalDeposit > 0) {
                    if (request.getVatPercent() > 0) {
                        // Recalculate VAT ( Override the request )
                        request.setTotalVat(((request.getSubTotal() - totalDeposit) * 10) / 100);
                    }
                    totalAmount = (request.getSubTotal() - totalDeposit) + request.getTotalVat() - request.getDiscountAmount();
                } else {
                    totalAmount = (request.getSubTotal() + request.getTotalVat()) - request.getDiscountAmount();
                }

                // Check Data
                SaleInvoice saleInvoice = new SaleInvoice();
                saleInvoice.setType(request.getType());
                saleInvoice.setCompanyId(request.getCompanyId());
                saleInvoice.setWarehouseId(request.getWarehouseId());
                saleInvoice.setOrganizationId(request.getOrganizationId());
                saleInvoice.setOrganizationContactId(request.getOrganizationContactId());
                saleInvoice.setPaymentTermId(request.getPaymentTermId());
                saleInvoice.setChartAccountId(request.getChartAccountId());
                saleInvoice.setQuotationId(request.getQuotationId());
                saleInvoice.setQuotationNumber(request.getQuotationNumber());
                saleInvoice.setSaleRepId(request.getSaleRepId());
                saleInvoice.setSaleOrderId(request.getSaleOrderId());
                saleInvoice.setSaleOrderNumber(request.getSaleOrderNumber());
                saleInvoice.setTransferOrderId(request.getTransferOrderId());
                saleInvoice.setTransferOrderNumber(request.getTransferOrderNumber());
                saleInvoice.setBomId(request.getBomId());
                saleInvoice.setBomCode(request.getBomCode());
                saleInvoice.setInvoiceDate(request.getInvoiceDate());
                saleInvoice.setOrganizationPoNo(request.getOrganizationPoNo());
                saleInvoice.setDeliveryDate(request.getDeliveryDate());
                saleInvoice.setCurrencyId(helperMapper.getCompanyCurrencyCenter(request.getCompanyId()));
                saleInvoice.setPriceTypeId(request.getPriceTypeId());
                saleInvoice.setDepartmentId(request.getDepartmentId());
                saleInvoice.setNote(request.getNote());
                saleInvoice.setTotalVat(request.getTotalVat());
                saleInvoice.setVatPercent(request.getVatPercent());
                saleInvoice.setVatSettingId(request.getVatSettingId());
                saleInvoice.setRecurrence(request.getRecurrence());
                saleInvoice.setVatChartAccountId(helperMapper.getVatChartAccountId(request.getVatSettingId()));
                saleInvoice.setVatCalculate(helperMapper.getVatCalculate(request.getCompanyId()));
                saleInvoice.setVatSettingRateId(helperMapper.getVatSettingRateId(request.getVatSettingId()));
                saleInvoice.setDiscountAmount(request.getDiscountAmount());
                saleInvoice.setDiscountPercent(request.getDiscountPercent());
                saleInvoice.setBalance(totalAmount);
                if (request.getIsPos() != null && request.getIsPos() == 1){
                    saleInvoice.setIsPos(request.getIsPos());
                } else {
                    saleInvoice.setIsPos(0);
                }
                if (totalDeposit > 0) {
                    saleInvoice.setIsDepositReference(1);
                } else {
                    saleInvoice.setIsDepositReference(0);
                }
                // Check Organization Limit Setting
                OrganizationLimitSetting limitSetting = saleInvoiceMapper.getOrganizationLimitSetting(request.getOrganizationId());
                if (limitSetting != null){
                    if (limitSetting.getLimitBalance() > 0 || limitSetting.getLimitInvoice() > 0){
                        if (limitSetting.getLimitBalance() > limitSetting.getCurrentBalance() || limitSetting.getLimitInvoice() > limitSetting.getCurrentInvoice()) {
                            saleInvoice.setIsApprove(1);
                        } else {
                            saleInvoice.setIsApprove(0);
                        }
                    } else {
                        saleInvoice.setIsApprove(1);
                    }
                }
                if (saleInvoice.getIsApprove() == null) {
                    saleInvoice.setIsApprove(1);
                }
                // Check Approve
                if (saleInvoice.getIsApprove() == 1) {
                    saleInvoice.setStatus(1);
                } else {
                    saleInvoice.setStatus(-2);
                }
                saleInvoice.setIsApply(request.getIsApply());
                saleInvoice.setTotalDeposit(totalDeposit);
                saleInvoice.setTotalAmount(request.getSubTotal());
                saleInvoice.setCreated(formattedNow);
                saleInvoice.setCreatedBy(userId);
                saleInvoice.setIsDeposit(request.getIsDeposit());
                saleInvoice.setIsNoneVat(request.getIsNoneVat());
                if (saleInvoice.getType() == null) {
                    saleInvoice.setType(1);
                }
                if (saleInvoice.getTotalDeposit() == null) {
                    saleInvoice.setTotalDeposit(0D);
                }
                if (saleInvoice.getRecurrence() == null) {
                    saleInvoice.setRecurrence(0);
                }
                if (saleInvoice.getIsApply() == null) {
                    saleInvoice.setIsApply(0);
                }
                if (saleInvoice.getIsNoneVat() == null) {
                    saleInvoice.setIsNoneVat(0);
                }
                if (saleInvoice.getIsDeposit() == null) {
                    saleInvoice.setIsDeposit(0);
                }
                if (saleInvoice.getIsDepositReference() == null) {
                    saleInvoice.setIsDepositReference(0);
                }
                if (saleInvoice.getStatus() == null) {
                    saleInvoice.setStatus(1);
                }
                Boolean result = saleInvoiceMapper.insert(saleInvoice);

            if (result) {
                // Generate reference code
                String code;
                if (request.getIsUpdate() != null && request.getIsUpdate() == 1 && request.getId() != null){
                    helperMapper.deleteGeneralLedger("sales_order_id", request.getId(), userId);

                    helperMapper.archive("sales_orders", "status", -1, request.getId(), userId);

                    code = (helperMapper.getCurrentCode("sales_orders", "so_code", request.getId()));
                    helperMapper.updateCode("sales_orders", "so_code", code, saleInvoice.getId());
                } else {
                    if (request.getTotalVat()!= null && request.getTotalVat() == 0) {
                        if (request.getModuleCode() == null){
                            request.setModuleCode("");
                        }
                        code = (generateCode.generateAutoCode("sales_orders", "so_code", 7, request.getModuleCode(), true, "status >= 0"));
                        helperMapper.updateCode("sales_orders", "so_code", code, saleInvoice.getId());
                    }  else {
                        code = (generateCode.generateAutoCode("sales_orders", "so_code", 7, "", true, "status != -1 AND total_vat > 0 AND YEAR(order_date) = YEAR(NOW())"));
                        helperMapper.updateCode("sales_orders", "so_code", code, saleInvoice.getId());
                    }
                }

                //  Close Sales Order
                if (request.getSaleOrderId() != null && request.getIsDeposit() == 0) {
                  saleInvoiceMapper.closeSalesOrder(request.getSaleOrderId(), userId);
                }

                //  Close Quotation
                if (request.getQuotationId() != null && totalDeposit > 0) {
                    saleInvoiceMapper.closeQuotation(request.getQuotationId());
                }

                //! General Ledger
                GeneralLedger generalLedger = new GeneralLedger();
                generalLedger.setSaleInvoiceId(saleInvoice.getId());
                generalLedger.setDate(request.getInvoiceDate());
                generalLedger.setReference(code);
                generalLedger.setIsActive(1);
                generalLedger.setIsAdj(0);
                generalLedger.setIsSys(1);
                generalLedger.setCreatedBy(userId);
                helperMapper.insertGeneralLedger(generalLedger);

                //! General Ledger Detail (A/R)
                GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                generalLedgerDetail.setCompanyId(request.getCompanyId());
                if (request.getIsPos() != null && request.getIsPos() == 1){
                    generalLedgerDetail.setType("POS");
                } else {
                    generalLedgerDetail.setType("Invoice");
                }
                generalLedgerDetail.setDebit(totalAmount);
                generalLedgerDetail.setCredit(0D);
                generalLedgerDetail.setMemo("ICS: SO # " + code);
                generalLedgerDetail.setCustomerId(request.getOrganizationId());
                generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getWarehouseId()));
                helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                if (request.getTermConditions() != null && !request.getTermConditions().isEmpty()) {
                    SaleInvoiceTermCondition termCondition = new SaleInvoiceTermCondition();
                    for (int i = 0; i < request.getTermConditions().size(); i++) {
                        termCondition.setTermConditionTypeId(request.getTermConditions().get(i).getTermConditionTypeId());
                        termCondition.setTermConditionId(request.getTermConditions().get(i).getTermConditionId());
                        termCondition.setSaleInvoiceId(saleInvoice.getId());
                        saleInvoiceMapper.insertTermCondition(termCondition);
                    }
                }

                if (request.getInvoiceDeposits() != null && !request.getInvoiceDeposits().isEmpty()) {
                    //  Insert Sales Invoice Apply Deposit
                    SaleInvoiceApplyDeposit applyDeposit = new SaleInvoiceApplyDeposit();
                    for (int j = 0; j < request.getInvoiceDeposits().size(); j++) {
                        applyDeposit.setSaleInvoiceId(saleInvoice.getId());
                        applyDeposit.setSalesOrderApplyDepositId(request.getInvoiceDeposits().get(j));
                        saleInvoiceMapper.insertSaleOrderApplyDeposit(applyDeposit);

                        //!// General Ledger Detail (Deposit Invoice)
                        GeneralLedgerDetail generalLedgerDetailDeposit = new GeneralLedgerDetail();
                        generalLedgerDetailDeposit.setGeneralLedgerId(generalLedger.getId());
                        generalLedgerDetailDeposit.setChartAccountId(saleInvoiceMapper.getDepositChartAccountId(request.getInvoiceDeposits().get(j)));
                        generalLedgerDetailDeposit.setCompanyId(request.getCompanyId());
                        if (request.getIsPos() != null && request.getIsPos() == 1){
                            generalLedgerDetailDeposit.setType("POS");
                        } else {
                            generalLedgerDetailDeposit.setType("Invoice");
                        }
                        generalLedgerDetailDeposit.setDebit(saleInvoiceMapper.getDepositAmount(request.getInvoiceDeposits().get(j)));
                        generalLedgerDetailDeposit.setCredit(0D);
                        generalLedgerDetailDeposit.setMemo("ICS: SO # " + code);
                        generalLedgerDetailDeposit.setCustomerId(request.getOrganizationId());
                        generalLedgerDetailDeposit.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getWarehouseId()));
                        helperMapper.insertGeneralLedgerDetail(generalLedgerDetailDeposit);

                    }
                }

                //!// General Ledger Detail (Total Discount)
                if (saleInvoice.getDiscountAmount() > 0) {
                    GeneralLedgerDetail generalLedgerDetailDiscount = new GeneralLedgerDetail();
                    generalLedgerDetailDiscount.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetailDiscount.setChartAccountId(helperMapper.getDiscountChartAccountId(11L));
                    generalLedgerDetailDiscount.setCompanyId(request.getCompanyId());
                    if (request.getIsPos() != null && request.getIsPos() == 1){
                        generalLedgerDetailDiscount.setType("POS");
                    } else {
                        generalLedgerDetailDiscount.setType("Invoice");
                    }
                    generalLedgerDetailDiscount.setDebit(saleInvoice.getDiscountAmount());
                    generalLedgerDetailDiscount.setCredit(0D);
                    generalLedgerDetailDiscount.setMemo("ICS: SO # " + code + "Discount");
                    generalLedgerDetailDiscount.setCustomerId(request.getOrganizationId());
                    generalLedgerDetailDiscount.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getWarehouseId()));
                    helperMapper.insertGeneralLedgerDetail(generalLedgerDetailDiscount);
                }

                //!// General Ledger Detail (Total VAT)
                if (saleInvoice.getTotalVat() > 0) {
                    GeneralLedgerDetail generalLedgerDetailVat = new GeneralLedgerDetail();
                    generalLedgerDetailVat.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetailVat.setChartAccountId(helperMapper.getVatChartAccountId(request.getVatSettingId()));
                    generalLedgerDetailVat.setCompanyId(request.getCompanyId());
                    generalLedgerDetailVat.setType("VAT");
                    generalLedgerDetailVat.setVendorId(request.getOrganizationId());
                    generalLedgerDetailVat.setDebit(0D);
                    generalLedgerDetailVat.setCredit(saleInvoice.getTotalVat());
                    generalLedgerDetailVat.setMemo("ICS: SO # " + code);
                    generalLedgerDetailVat.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getWarehouseId()));
                    helperMapper.insertGeneralLedgerDetail(generalLedgerDetailVat);
                }

                List<SaleInvoiceDetailRequest> details = request.getDetails();

                if (details != null && !details.isEmpty()) {
                    Integer checkSettingOption = helperMapper.checkSettingOption();
                    for (SaleInvoiceDetailRequest detailRequest : details) {
                        if (detailRequest.getType() == 1) {
                            Long conversion = helperMapper.getSmallValUom(detailRequest.getItemId()) / detailRequest.getConversion();
                            SaleInvoiceDetail detail = new SaleInvoiceDetail();
                            detail.setSaleInvoiceId(saleInvoice.getId());
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
                            detail.setNote(detailRequest.getMemo());
                            saleInvoiceMapper.insertDetail(detail);

                            Long smallValUom = helperMapper.getSmallValUom(detail.getItemId());
                            System.out.println(smallValUom);
                            long qtyValue = detail.getQty() != null ? detail.getQty() : 0L;
                            long qtyFree = detail.getQtyFree() != null ? detail.getQtyFree() : 0L;
                            long totalQty = qtyValue + qtyFree;
                            long conversionValue = detail.getConversion() != null && detail.getConversion() != 0 ? detail.getConversion() : 1L;
                            long safeSmallValUom = (smallValUom == null || smallValUom == 0) ? 1L : smallValUom;
                            double baseQtyDenominator = (double) safeSmallValUom / conversionValue;
                            double qty = baseQtyDenominator != 0 ? totalQty / baseQtyDenominator : 0D;
                            System.out.println(qty);
                            long smallQty = totalQty * conversionValue;
                            System.out.println(smallQty);
                            InventoryValuation inventoryValuation = new InventoryValuation();

                            if (checkSettingOption != null && checkSettingOption == 1) {
                                //! Inventory Valuation
                                inventoryValuation.setSaleInvoiceId(saleInvoice.getId());
                                inventoryValuation.setCompanyId(request.getCompanyId());
                                if (request.getIsPos() != null && request.getIsPos() == 1){
                                    inventoryValuation.setType("POS");
                                } else {
                                    inventoryValuation.setType("Invoice");
                                }
                                inventoryValuation.setDate(saleInvoice.getInvoiceDate());
                                inventoryValuation.setProductId(detailRequest.getItemId());
                                inventoryValuation.setSmallQty((double) smallQty * -1);
                                inventoryValuation.setQty(qty * -1);
                                inventoryValuation.setIsVarCost(1);
                                inventoryValuation.setIsActive(1);
                                System.out.println(inventoryValuation);
                                helperMapper.insertInventoryValuation(inventoryValuation);
                            }

                            //! Get Product Data
                            ProductCodeNameResponse data = helperMapper.getProductCodeName(detailRequest.getItemId());
                            // Calculate Qty, Location, Lot, Expired Date
                            List<StockOrder> stockOrderResponses = helperMapper.getStockExisting(saleInvoice.getId(), detail.getItemId(), request.getWarehouseId(), locationStatus);
                            if (!stockOrderResponses.isEmpty()) {
                                Long latestSmallQty = smallQty;
                                for (StockOrder stockOrderResponse : stockOrderResponses) {

                                    if (latestSmallQty > stockOrderResponse.getQty()) {
                                        smallQty = stockOrderResponse.getQty();
                                    } else {
                                        smallQty = latestSmallQty;
                                    }

                                    // Insert Stock Order
                                    StockOrder stockOrder = new StockOrder();
                                    stockOrder.setSaleInvoiceId(saleInvoice.getId());
                                    stockOrder.setProductId(detail.getItemId());
                                    stockOrder.setLocationGroupId(request.getWarehouseId());
                                    stockOrder.setLocationId(stockOrderResponse.getLocationId());
                                    stockOrder.setLotsNumber(stockOrderResponse.getLotsNumber());
                                    stockOrder.setExpiredDate(stockOrderResponse.getExpiredDate());
                                    stockOrder.setDate(saleInvoice.getInvoiceDate());
                                    stockOrder.setQty(smallQty);
                                    saleInvoiceMapper.insertStockOrder(stockOrder);

                                    // Update Global Total Order
                                    GlobalStock globalStock = new GlobalStock();
                                    globalStock.setSalesInvoiceId(saleInvoice.getId());
                                    globalStock.setProductId(detail.getItemId());
                                    globalStock.setLocationId(stockOrderResponse.getLocationId());
                                    globalStock.setWarehouseId(request.getWarehouseId());
                                    globalStock.setLotsNumber(stockOrderResponse.getLotsNumber() != null
                                            ? stockOrderResponse.getLotsNumber()
                                            : "0");
                                    globalStock.setExpiredDate(stockOrderResponse.getExpiredDate() != null
                                            ? stockOrderResponse.getExpiredDate()
                                            : "0000-00-00");
                                    globalStock.setCreatedBy(userId);
                                    globalStock.setTotalOrder(smallQty);
                                    inventory.insertOrder(globalStock, 1L);
                                    latestSmallQty = latestSmallQty - smallQty;
                                }
                            }

                            //! General Ledger Detail (Product Income)
                            generalLedgerDetail.setChartAccountId(helperMapper.getProductIncomeChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setProductId(detailRequest.getItemId());
                            if (request.getIsPos() != null && request.getIsPos() == 1){
                                generalLedgerDetail.setType("POS");
                            } else {
                                generalLedgerDetail.setType("Invoice");
                            }
                            generalLedgerDetail.setDebit(0D);
                            generalLedgerDetail.setInventoryValuationIsDebit(null);
                            generalLedgerDetail.setCredit(detailRequest.getTotalPrice());
                            generalLedgerDetail.setMemo("ICS: SO # " + code +" "+ data.getName());
                            generalLedgerDetail.setCustomerId(request.getOrganizationId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getWarehouseId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            //! General Ledger Detail (Product Discount)
                            if (detailRequest.getDiscountAmount() > 0) {
                                generalLedgerDetail.setChartAccountId(helperMapper.getDiscountChartAccountId(11L));
                                generalLedgerDetail.setCompanyId(request.getCompanyId());
                                generalLedgerDetail.setProductId(detailRequest.getItemId());
                                if (request.getIsPos() != null && request.getIsPos() == 1){
                                    generalLedgerDetail.setType("POS");
                                } else {
                                    generalLedgerDetail.setType("Invoice");
                                }
                                generalLedgerDetail.setDebit(detailRequest.getDiscountAmount());
                                generalLedgerDetail.setCredit(0D);
                                generalLedgerDetail.setMemo("ICS: SO # " + code + " " + data.getName() + " " + " Discount");
                                generalLedgerDetail.setCustomerId(request.getOrganizationId());
                                generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getWarehouseId()));
                                helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                            }

                            //! General Ledger Detail (Inventory)
                            generalLedgerDetail.setChartAccountId(helperMapper.getProductInventoryChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setProductId(detailRequest.getItemId());
                            generalLedgerDetail.setInventoryValuationId(inventoryValuation.getId());
                            generalLedgerDetail.setInventoryValuationIsDebit(0);
                            if (request.getIsPos() != null && request.getIsPos() == 1){
                                generalLedgerDetail.setType("POS");
                            } else {
                                generalLedgerDetail.setType("Invoice");
                            }
                            generalLedgerDetail.setDebit(0D);
                            generalLedgerDetail.setCredit(0D);
                            generalLedgerDetail.setMemo("ICS: Inventory Adjustment For SO # " + code + " " + data.getName());
                            generalLedgerDetail.setCustomerId(request.getOrganizationId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getWarehouseId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            //! General Ledger Detail COGS
                            generalLedgerDetail.setChartAccountId(helperMapper.getProductCOGSChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setProductId(detailRequest.getItemId());
                            generalLedgerDetail.setInventoryValuationId(inventoryValuation.getId());
                            generalLedgerDetail.setInventoryValuationIsDebit(1);
                            if (request.getIsPos() != null && request.getIsPos() == 1){
                                generalLedgerDetail.setType("POS");
                            } else {
                                generalLedgerDetail.setType("Invoice");
                            }
                            generalLedgerDetail.setDebit(0D);
                            generalLedgerDetail.setCredit(0D);
                            generalLedgerDetail.setMemo("ICS: SO # " + code + " " + data.getName());
                            generalLedgerDetail.setCustomerId(request.getOrganizationId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getWarehouseId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                        } else if (detailRequest.getType() == 2) {
                            SaleInvoiceServices service = new SaleInvoiceServices();
                            service.setSaleInvoiceId(saleInvoice.getId());
                            service.setItemId(detailRequest.getItemId());
                            service.setPoNo(detailRequest.getPoNo());
                            service.setQty(detailRequest.getQty());
                            service.setQtyFree(detailRequest.getFoc());
                            service.setConversion(detailRequest.getConversion());
                            if (detailRequest.getMonth() != null) {
                                service.setMonth(detailRequest.getMonth());
                            } else {
                                service.setMonth(1L);
                            }
                            if (detailRequest.getStartDate() != null && detailRequest.getMonth() > 1){
                                service.setStartDate(detailRequest.getStartDate());
                            } else {
                                service.setStartDate("0000-00-00");
                            }
                            service.setDiscountId(detailRequest.getDiscountId());
                            service.setDiscountAmount(detailRequest.getDiscountAmount());
                            service.setDiscountPercent(detailRequest.getDiscountPercent());
                            service.setUnitPrice(detailRequest.getUnitPrice());
                            service.setTotalPrice(detailRequest.getTotalPrice());
                            service.setNote(detailRequest.getMemo());
                            saleInvoiceMapper.insertService(service);

                            //! Get Service Data
                            ProductCodeNameResponse data = helperMapper.getServiceName(detailRequest.getItemId());

                            //! General Ledger Detail (Service)
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            if (detailRequest.getStartDate() != null && detailRequest.getMonth() > 1){
                                generalLedgerDetail.setChartAccountId(helperMapper.getServiceUnearnChartAccountId(detailRequest.getItemId()));
                            } else {
                                generalLedgerDetail.setChartAccountId(helperMapper.getServiceChartAccountId(detailRequest.getItemId()));
                            }
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            generalLedgerDetail.setServiceId(detailRequest.getItemId());
                            if (request.getIsPos() != null && request.getIsPos() == 1){
                                generalLedgerDetail.setType("POS");
                            } else {
                                generalLedgerDetail.setType("Invoice");
                            }
                            generalLedgerDetail.setDebit(0D);
                            generalLedgerDetail.setCredit(detailRequest.getTotalPrice());
                            generalLedgerDetail.setMemo("ICS: SO # " + code + " " + data.getName());
                            generalLedgerDetail.setCustomerId(request.getOrganizationId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getWarehouseId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            //! General Ledger Detail (Service Discount)
                            if (service.getDiscountAmount() > 0) {
                                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                                generalLedgerDetail.setChartAccountId(helperMapper.getDiscountChartAccountId(11L));
                                generalLedgerDetail.setCompanyId(request.getCompanyId());
                                generalLedgerDetail.setServiceId(detailRequest.getItemId());
                                if (request.getIsPos() != null && request.getIsPos() == 1){
                                    generalLedgerDetail.setType("POS");
                                } else {
                                    generalLedgerDetail.setType("Invoice");
                                }
                                generalLedgerDetail.setDebit(detailRequest.getDiscountAmount());
                                generalLedgerDetail.setCredit(0D);
                                generalLedgerDetail.setMemo("ICS: SO # " + code + " " + data.getName() + " Discount");
                                generalLedgerDetail.setCustomerId(request.getOrganizationId());
                                generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getWarehouseId()));
                                helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                            }

                        } else if (detailRequest.getType() == 3) {
                            SaleInvoiceMisc misc = new SaleInvoiceMisc();
                            misc.setSaleInvoiceId(saleInvoice.getId());
                            misc.setItemName(detailRequest.getItemName());
                            misc.setPoNo(detailRequest.getPoNo());
                            misc.setQty(detailRequest.getQty());
                            misc.setQtyFree(detailRequest.getFoc());
                            misc.setConversion(detailRequest.getConversion());
                            misc.setUomId(detailRequest.getUomId());
                            misc.setDiscountId(detailRequest.getDiscountId());
                            misc.setDiscountAmount(detailRequest.getDiscountAmount());
                            misc.setDiscountPercent(detailRequest.getDiscountPercent());
                            misc.setUnitPrice(detailRequest.getUnitPrice());
                            misc.setTotalPrice(detailRequest.getTotalPrice());
                            misc.setNote(detailRequest.getMemo());
                            misc.setDepositCoaId(detailRequest.getDepositTo());
                            saleInvoiceMapper.insertMisc(misc);

                            //! General Ledger Detail (Misc)
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            if (detailRequest.getDepositTo() != null){
                                generalLedgerDetail.setChartAccountId(misc.getDepositCoaId());
                            }else {
                                generalLedgerDetail.setChartAccountId(helperMapper.getMiscChartAccountId());
                            }
                            generalLedgerDetail.setCompanyId(request.getCompanyId());
                            if (request.getIsPos() != null && request.getIsPos() == 1){
                                generalLedgerDetail.setType("POS");
                            } else {
                                generalLedgerDetail.setType("Invoice");
                            }
                            generalLedgerDetail.setDebit(0D);
                            generalLedgerDetail.setCredit(detailRequest.getTotalPrice());
                            generalLedgerDetail.setMemo("ICS: SO # " + code + " " + misc.getItemName());
                            generalLedgerDetail.setCustomerId(request.getOrganizationId());
                            generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getWarehouseId()));
                            helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            //! General Ledger Detail (Misc Discount)
                            if (misc.getDiscountAmount() > 0) {
                                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                                generalLedgerDetail.setChartAccountId(helperMapper.getDiscountChartAccountId(11L));
                                generalLedgerDetail.setCompanyId(request.getCompanyId());
                                if (request.getIsPos() != null && request.getIsPos() == 1){
                                    generalLedgerDetail.setType("POS");
                                } else {
                                    generalLedgerDetail.setType("Invoice");
                                }
                                generalLedgerDetail.setDebit(misc.getDiscountAmount());
                                generalLedgerDetail.setCredit(0D);
                                generalLedgerDetail.setMemo("ICS: SO # " + code + " " + misc.getItemName() + " Discount");
                                generalLedgerDetail.setCustomerId(request.getOrganizationId());
                                generalLedgerDetail.setClassId(helperMapper.getClassId(request.getCompanyId(), request.getWarehouseId()));
                                helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                            }
                        }
                    }
                }

                String title;

                if (request.getType() != null && request.getType() == 1 && saleInvoice.getIsPos() == 0){
                    title = "Sales / Invoice iAES";
                } else if (saleInvoice.getIsPos() != null && saleInvoice.getIsPos() == 1) {
                    title = "Sales / Invoice POS";
                } else {
                    title = "Sales / Invoice EBi";
                }

                List<SaleInvoiceResponse> data = saleInvoiceMapper.getOne(saleInvoice.getId(), userId);

                // COMPARE AMOUNT ITEMS SALES INVOICE WITH SALES ORDER
                Long totalInvoice = saleInvoiceMapper.countItems(saleInvoice.getId());
                if (totalInvoice == null) {
                    totalInvoice = 0L;
                }

                Long totalSo = 0L;
                if (request.getSaleOrderId() != null){
                    totalSo = saleOrderMapper.countItems(request.getSaleOrderId());
                }

                if (totalInvoice >= totalSo && saleInvoice.getIsDeposit() == 0){
                    // CHECK AMOUNT PRODUCTS EXISTING IN SALES INVOICE
                    Long checkInvoiceProduct = helperMapper.checkInvoiceProduct(saleInvoice.getId());
                    if (checkInvoiceProduct == null){
                        checkInvoiceProduct = 0L;
                    }
                    if (checkInvoiceProduct > 0){
                        // WAITING FOR DELIVERY
                        helperMapper.updateStatus("orders", "status",  3L, userId, request.getSaleOrderId());
                    } else {
                        // AUTO DELIVERY
                        helperMapper.updateStatus("orders", "status",  4L, userId, request.getSaleOrderId());
                    }
                } else {
                    helperMapper.updateStatus("orders", "status",  2L, userId, request.getSaleOrderId());
                }

                if (data.get(0).getSoId() != null){
                    String message = "\uD83D\uDCB0 <b><u>" + title + "</u></b>" +
                            "\n\n<b>Date: " + CheckNull.safe(data.get(0).getSoDate()) + "</b>" +
                            "\n<b>SO ID: " + CheckNull.safe(data.get(0).getSoNo()) + "</b>" +
                            "\n\n<b>" + CheckNull.safe(data.get(0).getOrganizationName()) + "</b>" +
                            "\nTel: " + CheckNull.safe(data.get(0).getOrganizationTelephone()) +
                            "\n<i>Attn: " + CheckNull.safe(data.get(0).getOrganizationContactName()) + "</i>" +
                            "\n<i>Tel: " + CheckNull.safe(data.get(0).getOrganizationContactTelephone()) + "</i>" +
                            "\n\nTotal Item: <b>" + CheckNull.safe(String.valueOf(totalInvoice)) + "</b>" +
                            "\nTotal Amount: <b>" + CheckNull.safe(data.get(0).getCurrencySymbol()) + " " + CheckNull.safe(String.valueOf(data.get(0).getTotalAmount())) + "</b>" +
                            "\n\nMemo: \n" + CheckNull.safe(data.get(0).getNote()) +
                            "\n\n<pre>If you have any questions concerning this sales order contact:\n" +
                            CheckNull.safe(data.get(0).getCreatedByUser()) + "</pre>";

                    telegramUtils.pushTelegram(
                            message,
                            environment.getProperty("telegram.chatId.sales-invoice"),
                            environment.getProperty("telegram.botToken.sales-invoice"),
                            null
                    );
                }

                if (saleInvoice.getIsPos() != null && saleInvoice.getIsPos() == 1) {
                    String payDate = request.getInvoiceDate() != null ? request.getInvoiceDate() : formattedNow;

                    SalesInvoicePayRequest payRequest = new SalesInvoicePayRequest();
                    payRequest.setSaleInvoiceId(saleInvoice.getId());
                    payRequest.setExchangeRateId(request.getExchangeRateId());
                    payRequest.setBalance(request.getBalance());
                    payRequest.setBalanceOther(0D);
                    payRequest.setTotalAmount(totalAmount);
                    payRequest.setCurrencyCenterId(saleInvoice.getCurrencyId());
                    payRequest.setChartAccountId(request.getChartAccountId());
                    payRequest.setPaidUsd(request.getPaidAmount());
                    payRequest.setPaidOther(request.getPaidKhr());
                    payRequest.setChange(request.getChangeAmount());
                    payRequest.setChangeOther(request.getChangeAmountKhr());
                    payRequest.setDate(payDate);
                    payRequest.setAging(payDate);

                    ResponseMessage<BaseResult> payResponse = this.pay(payRequest, httpServletRequest);
                    BaseResult payResult = payResponse != null ? payResponse.getBody() : null;
                    if (payResult == null || Boolean.FALSE.equals(payResult.getStatus())) {
                        return ResponseMessageUtils.makeResponse(false, messageService.message("Payment failed.", false, saleInvoice.getId()));
                    }

                    DeliveryRequest deliveryRequest = new DeliveryRequest();
                    deliveryRequest.setCompanyId(request.getCompanyId());
                    deliveryRequest.setDate(payDate);
                    deliveryRequest.setNote(request.getNote());
                    deliveryRequest.setWarehouseId(request.getWarehouseId());
                    deliveryRequest.setCustomerId(request.getOrganizationId());
                    deliveryRequest.setSalesInvoiceIds(Collections.singletonList(saleInvoice.getId()));
                    ResponseMessage<BaseResult> deliveryResponse = deliveryService.insert(deliveryRequest, null, httpServletRequest);

                    BaseResult deliveryResult = deliveryResponse != null ? deliveryResponse.getBody() : null;
                    if (deliveryResult == null || Boolean.FALSE.equals(deliveryResult.getStatus()) || deliveryResult.getId() == null) {
                        return ResponseMessageUtils.makeResponse(false, messageService.message("Insert delivery failed.", false, saleInvoice.getId()));
                    }

                    ResponseMessage<BaseResult> pickResponse = deliveryService.pick(deliveryResult.getId(), 1, httpServletRequest);
                    BaseResult pickResult = pickResponse != null ? pickResponse.getBody() : null;
                    if (pickResult == null || Boolean.FALSE.equals(pickResult.getStatus())) {
                        return ResponseMessageUtils.makeResponse(false, messageService.message("Stock deduction failed.", false, saleInvoice.getId()));
                    }
                }

                if (request.getIsUpdate() == null || request.getIsUpdate() != 1) {
                    checkMembership(request.getOrganizationId(), saleInvoice.getId(), totalAmount);
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-invoice/add", null, null, "Sales / Invoice", "Sales / Invoice (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true, saleInvoice.getId()));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-invoice/add", line, error.toString(), "Sales / Invoice", "Sales / Invoice (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    private void checkMembership(Long customerId, Long salesId, Double totalAmount) {
        if (customerId == null || salesId == null || totalAmount == null) {
            return;
        }

        MembershipCard membership = membershipCardMapper.getActiveByCustomerId(customerId);
        if (membership == null || membership.getId() == null || membership.getMembershipTypeId() == null) {
            return;
        }

        MembershipCardLevel membershipType = membershipCardMapper.getActiveMembershipCardType(membership.getMembershipTypeId());
        if (membershipType == null || membershipType.getAmount() == null || membershipType.getAmount() <= 0
                || membershipType.getPoint() == null) {
            return;
        }

        Double oldPoint = membership.getCurrentPoint() != null ? membership.getCurrentPoint() : 0D;
        Double oldTotalPoint = membership.getTotalPoint() != null ? membership.getTotalPoint() : 0D;
        Double pointReceive = ((long) (totalAmount / membershipType.getAmount())) * membershipType.getPoint();
        Double totalPoint = ((long) oldPoint.doubleValue()) + pointReceive;
        Double lifetimePoint = ((long) oldTotalPoint.doubleValue()) + pointReceive;
        membershipCardMapper.updateMembershipPoints(membership.getId(), totalPoint, pointReceive);
        membershipCardMapper.insertMembershipCardLog(membership.getId(), salesId, totalAmount, oldPoint, pointReceive, totalPoint);

        Long upgradedMembershipTypeId = membershipCardMapper.getUpgradedMembershipTypeId(membership.getMembershipTypeId(), lifetimePoint);
        if (upgradedMembershipTypeId != null) {
            membershipCardMapper.updateMembershipType(membership.getId(), upgradedMembershipTypeId);
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(SaleInvoiceUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        Long userId = userService.getUserAuth().getId();
        if (request.getIsUpdateView() == null){
            request.setIsUpdate(1);
            return this.insert(request, bindingResult, httpServletRequest);
        } else {
            // Check Data
            SaleInvoice saleInvoice = new SaleInvoice();
            saleInvoice.setId(request.getId());
            saleInvoice.setOrganizationId(request.getOrganizationId());
            saleInvoice.setOrganizationContactId(request.getOrganizationContactId());
            saleInvoice.setOrganizationPoNo(request.getOrganizationPoNo());
            saleInvoice.setModifiedBy(userId);
            Boolean result = saleInvoiceMapper.update(saleInvoice);
            if (result){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales / Invoice (Void)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<SaleInvoiceResponse> data = saleInvoiceMapper.getOne(id, userId);
            helperMapper.deleteGeneralLedger("sales_order_id", id, userId);
            Boolean result = helperMapper.archive("sales_orders", "status", 0, id, userId);

            if (result) {
                String title;
                if (data.get(0).getType() == 1){
                    title = "Sales / Invoice iAES";
                } else {
                    title = "Sales / Invoice EBi";
                }
                Long amountItem = saleInvoiceMapper.countItems(id);

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
                        "\n\n<b>Status: Void</b>\n\n" +
                        "<i>Void By: " + CheckNull.safe(data.get(0).getModifiedBy()) + "</i>\n\n" +
                        "\n\n<pre>If you have any questions concerning this quote contact:\n" +
                        CheckNull.safe(data.get(0).getCreatedBy()) + "</pre>";

                telegramUtils.pushTelegram(
                        message,
                        environment.getProperty("telegram.chatId.sales-invoice"),
                        environment.getProperty("telegram.botToken.sales-invoice"),
                        null
                );

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-invoice/delete/{id}",null,null,"Sales / Invoice","Sales / Invoice (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-invoice/delete/{id}",line, error.toString(),"Sales / Invoice","Sales / Invoice (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> close(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales / Invoice (Approve)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<SaleInvoiceResponse> data = saleInvoiceMapper.getOne(id, userId);

            Boolean result;

            // CHECK AMOUNT PRODUCTS EXISTING IN SALES INVOICE
            Long checkInvoiceProduct = helperMapper.checkInvoiceProduct(id);
            if (checkInvoiceProduct == null){
                checkInvoiceProduct = 0L;
            }

            if (checkInvoiceProduct == 0){
                result = helperMapper.updateStatus("sales_orders", "status",  2L, userId, id);
            } else {
                result = helperMapper.updateStatus("sales_orders", "status",  data.get(0).getStatus(), userId, id);
            }
            if (result) {
                String title;
                if (data.get(0).getType() == 1){
                    title = "Sales / Invoice iAES";
                } else {
                    title = "Sales / Invoice EBi";
                }
                Long amountItem = saleInvoiceMapper.countItems(id);

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
                        "<i>Void By: " + CheckNull.safe(data.get(0).getModifiedBy()) + "</i>\n\n" +
                        "\n\n<pre>If you have any questions concerning this quote contact:\n" +
                        CheckNull.safe(data.get(0).getCreatedBy()) + "</pre>";

                telegramUtils.pushTelegram(
                        message,
                        environment.getProperty("telegram.chatId.sales-invoice"),
                        environment.getProperty("telegram.botToken.sales-invoice"),
                        null
                );
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-invoice/delete/{id}",null,null,"Sales / Invoice","Sales / Invoice (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-invoice/delete/{id}",line, error.toString(),"Sales / Invoice","Sales / Invoice (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> closeRecurrence(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales / Invoice (Approve)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = helperMapper.updateStatus("sales_orders", "recurrence", 0L, userId, id);

            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-invoice/close-recurrence/{id}", null, null, "Sales / Invoice", "Sales / Invoice (Approve)", "Close Recurrence", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-invoice/close-recurrence/{id}", line, error.toString(), "Sales / Invoice", "Sales / Invoice (Approve)", "Close Recurrence", 2, "Error", startDuration, endDuration, httpServletRequest);
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
                if (permissionMapper.checkPermission(userId, "Sales / Invoice (Approve)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            }

            Boolean result = saleInvoiceMapper.approve(request.getId(), request.getStatus(), userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-invoice/approve",null,null,"Sales / Invoice","Sales / Invoice (Approve)","Approve",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-invoice/approve",line, error.toString(),"Sales / Invoice","Sales / Invoice (Approve)","Approve",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> pay(SalesInvoicePayRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales / Invoice (Aging)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            SalesInvoiceReceipt receipt = new SalesInvoiceReceipt();
            receipt.setSalesInvoiceId(request.getSaleInvoiceId());
            receipt.setExchangeRateId(request.getExchangeRateId());
            receipt.setBalance(request.getBalance());
            receipt.setBalanceOther(request.getBalanceOther());
            receipt.setTotalAmount(request.getTotalAmount());
            receipt.setCurrencyCenterId(request.getCurrencyCenterId());
            receipt.setChartAccountId(request.getChartAccountId());
            receipt.setAmountUsd(request.getPaidUsd());
            receipt.setAmountOther(request.getPaidOther());
            receipt.setDate(request.getDate());
            receipt.setChange(request.getChange());
            receipt.setChangeOther(request.getChangeOther());
            receipt.setAging(request.getAging());
            receipt.setCreatedBy(userId);
            Boolean result = saleInvoiceMapper.insertReceipt(receipt);

            if (result) {
                //! Get the reference code
                String code = (generateCode.generateAutoCode("sales_order_receipts", "receipt_code", 7, "INR", true, null));
                helperMapper.updateCode("sales_order_receipts", "receipt_code", code, receipt.getId());
                receipt.setReceiptCode(code);

                saleInvoiceMapper.updateBalance(request.getSaleInvoiceId(), request.getBalance(), 3L);

                List<SaleInvoiceResponse> responses = saleInvoiceMapper.getOne(request.getSaleInvoiceId(), userId);
                if (responses!= null && !responses.isEmpty() && responses.get(0).getIsPos() != 1) {
                    // Insert General Ledger
                    GeneralLedger generalLedger = new GeneralLedger();
                    generalLedger.setSaleInvoiceId(request.getSaleInvoiceId());
                    generalLedger.setSaleInvoiceReceiptId(receipt.getId());
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
                    generalLedgerDetail.setType("Payment");
                    generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                    generalLedgerDetail.setCustomerId(responses.get(0).getOrganizationId());
                    generalLedgerDetail.setCompanyId(responses.get(0).getCompanyId());
                    generalLedgerDetail.setClassId(helperMapper.getClassId(responses.get(0).getCompanyId(), responses.get(0).getPriceTypeId()));
                    generalLedgerDetail.setMemo("ICS: Payment for SO #  " + responses.get(0).getInvoiceNo());
                    generalLedgerDetail.setDebit(request.getPaidUsd());
                    generalLedgerDetail.setCredit(0D);
                    helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                    generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetail.setType("Payment");
                    generalLedgerDetail.setChartAccountId(responses.get(0).getChartAccountId());
                    generalLedgerDetail.setCustomerId(responses.get(0).getOrganizationId());
                    generalLedgerDetail.setCompanyId(responses.get(0).getCompanyId());
                    generalLedgerDetail.setClassId(helperMapper.getClassId(responses.get(0).getCompanyId(), responses.get(0).getPriceTypeId()));
                    generalLedgerDetail.setMemo("ICS: Payment for SO #  " + responses.get(0).getInvoiceNo());
                    generalLedgerDetail.setDebit(0D);
                    generalLedgerDetail.setCredit(request.getPaidUsd());
                    helperMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                }

                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-invoice/pay", null, null, "Sales Invoice", "Sales Invoice (Aging)", "Aging", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-invoice/pay", line, error.toString(), "Sales Invoice", "Sales Invoice (Aging)", "Aging", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    
    @Override
    public ResponseMessage<BaseResult> listReceipt(SalesInvoiceReceiptFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales / Invoice (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<SalesInvoiceReceiptResponse> responses = saleInvoiceMapper.listReceipt(filter);

            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-invoice/list-receipt/{id}", null, null, "Sales Invoice", "Sales Invoice (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-invoice/list-receipt/{id}", line, error.toString(), "Sales Invoice", "Sales Invoice (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> voidReceipt(SalesInvoiceReceiptFilter request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Sales / Invoice (Void)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            if (request == null || request.getId() == null) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-invoice/void-receipt/{id}", null, null, "Sales Invoice", "Sales Invoice (Void)", "Void", 2, "Error", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }

            SalesInvoiceReceiptResponse receipt = saleInvoiceMapper.getOneReceipt(request.getId());
            if (receipt == null || (receipt.getIsVoid() != null && receipt.getIsVoid() == 1)) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-invoice/void-receipt/{id}", null, null, "Sales Invoice", "Sales Invoice (Void)", "Void", 2, "Error", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }

            Boolean result = saleInvoiceMapper.voidReceipt(request, userId);
            if (result) {
                double rateToSell = receipt.getRateToSell() != null ? receipt.getRateToSell() : 0D;
                double paidOther = receipt.getPaidOther() != null ? receipt.getPaidOther() : 0D;
                double paidUsd = receipt.getPaidUsd() != null ? receipt.getPaidUsd() : 0D;
                double totalPaidOther = rateToSell > 0 ? paidOther / rateToSell : 0D;
                double totalAmount = paidUsd + totalPaidOther;

                Long salesInvoiceId = receipt.getSalesInvoiceId() != null ? receipt.getSalesInvoiceId() : request.getSalesInvoiceId();
                if (salesInvoiceId != null) {
                    saleInvoiceMapper.updateBalance(salesInvoiceId, totalAmount, 2L);
                }

                helperMapper.deleteGeneralLedger("sales_order_receipt_id", request.getId(), userId);
                if (salesInvoiceId != null) {
                    Long receivePaymentId = saleInvoiceMapper.getReceivePaymentId(salesInvoiceId, paidUsd);
                    if (receivePaymentId != null) {
                        helperMapper.archive("receive_payments", "is_active", 2, receivePaymentId, userId);
                    }
                }
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/sales-invoice/void-receipt/{id}", null, null, "Sales Invoice", "Sales Invoice (Void)", "Void", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sales-invoice/void-receipt/{id}", line, error.toString(), "Sales Invoice", "Sales Invoice (Void)", "Void", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
