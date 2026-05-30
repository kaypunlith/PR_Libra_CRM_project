package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PurchaseBillFilter;
import com.ut.nlSystemAPi.model.request.Login.PurchaseBill.PurchaseBillDetailRequest;
import com.ut.nlSystemAPi.model.request.Login.PurchaseBill.PurchaseBillFileRequest;
import com.ut.nlSystemAPi.model.request.Login.PurchaseBill.PurchaseBillRequest;
import com.ut.nlSystemAPi.model.request.Login.PurchaseBill.PurchaseBillUpdateRequest;
import com.ut.nlSystemAPi.model.response.PurchaseBill.PurchaseBillDetailResponse;
import com.ut.nlSystemAPi.model.response.PurchaseBill.PurchaseBillFileResponse;
import com.ut.nlSystemAPi.model.response.PurchaseBill.PurchaseBillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class PurchaseBillServiceImpl implements PurchaseBillService {

    @Autowired
    private PurchaseBillMapper purchaseBillMapper;

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

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

    @Autowired
    private GenerateCode generateCode;

    @Autowired
    private CodeMapper codeMapper;

    public ResponseMessage<BaseResult> getList(PurchaseBillFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Purchase Bill (View)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }
             if (permissionMapper.checkPermission(userId, "Purchase Bill (View By User)") > 0) {
                 filter.setViewByUser(1L);
             }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(purchaseBillMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<PurchaseBillResponse> purchaseBillResponses = purchaseBillMapper.getList(filter, userId);

            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-bill/list", null, null, "Purchase Bill", "Purchase Bill (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", purchaseBillResponses, pagination, true));
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-bill/list", line, error.toString(), "Purchase Bill", "Purchase Bill (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Purchase Bill (View)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }

            List<PurchaseBillResponse> purchaseBillResponses = purchaseBillMapper.getOne(id);

            if (!purchaseBillResponses.isEmpty()){
                for (PurchaseBillResponse purchaseBillResponse : purchaseBillResponses) {
                    List<PurchaseBillDetailResponse> detailResponses = purchaseBillMapper.getDetail(purchaseBillResponse.getId());
                    purchaseBillResponse.setDetails(detailResponses);
                    List<PurchaseBillFileResponse> fileResponses = purchaseBillMapper.getFiles(purchaseBillResponse.getId());
                    purchaseBillResponse.setFileAttachment(fileResponses);
                }
            }

            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-bill/find/{id}", null, null, "Purchase Bill", "Purchase Bill (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", purchaseBillResponses, true));
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-bill/find/{id}", line, error.toString(), "Purchase Bill", "Purchase Bill (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(PurchaseBillRequest purchaseBillRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Purchase Bill (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Long hasGRR = purchaseBillMapper.checkPoInGoodReceiptNote(purchaseBillRequest.getPoNo());
            if (hasGRR == null) {
                hasGRR = 0L;
            }

            // Check data
            PurchaseBill purchaseBill = new PurchaseBill();
            purchaseBill.setPoType(purchaseBillRequest.getPoType());
            purchaseBill.setCompanyId(purchaseBillRequest.getCompanyId());
            purchaseBill.setVendorId(purchaseBillRequest.getVendorId());
            purchaseBill.setWarehouseId(purchaseBillRequest.getWarehouseId());
            purchaseBill.setPaymentTermId(purchaseBillRequest.getPaymentTermId());
            purchaseBill.setCurrencyCenterId(purchaseBillMapper.getCurrencyCenterId(purchaseBillRequest.getCompanyId()));
            purchaseBill.setVatCalculate(purchaseBillMapper.getVatCalculate(purchaseBillRequest.getCompanyId()));
            purchaseBill.setVatChartAccountId(purchaseBillMapper.getVatChartAccountId(purchaseBillRequest.getVatSettingId()));
            purchaseBill.setExchangeRateId(purchaseBillRequest.getExchangeRateId());
            purchaseBill.setLocationId(purchaseBillRequest.getLocationId());
            purchaseBill.setInvoiceNo(purchaseBillRequest.getInvoiceNo());
            purchaseBill.setNote(purchaseBillRequest.getNote());
            purchaseBill.setPurchaseOrderId(purchaseBillRequest.getPoNo());
            purchaseBill.setPvRequestId(purchaseBillMapper.getERByPo(purchaseBillRequest.getPoNo()));
            purchaseBill.setApId(purchaseBillRequest.getApId());
            purchaseBill.setShipmentId(purchaseBillRequest.getShipmentId());
            purchaseBill.setPurchaseBillDate(purchaseBillRequest.getPurchaseBillDate());
            if (Objects.equals(purchaseBillRequest.getInvoiceDate(), "")) {
                purchaseBill.setInvoiceDate(null);
            } else {
                purchaseBill.setInvoiceDate(purchaseBillRequest.getInvoiceDate());
            }
            purchaseBill.setSubTotal(purchaseBillRequest.getSubTotal());
            purchaseBill.setDiscountPercent(purchaseBillRequest.getDiscountPercent());
            purchaseBill.setDiscountAmount(purchaseBillRequest.getDiscountAmount());
            purchaseBill.setVatSettingId(purchaseBillRequest.getVatSettingId());
            purchaseBill.setTotalVat(purchaseBillRequest.getTotalVat());
            double depositAmount = purchaseBillRequest.getDepositAmount() == null ? 0D : purchaseBillRequest.getDepositAmount();
            purchaseBill.setDepositAmount(depositAmount);
            purchaseBill.setBalance(purchaseBillRequest.getSubTotal() - purchaseBillRequest.getDiscountAmount() + purchaseBillRequest.getTotalVat() - depositAmount);
            purchaseBill.setVatPercentage(purchaseBillRequest.getVatPercentage());
            purchaseBill.setCreatedBy(userId);
            purchaseBill.setIsClose(0);

            purchaseBillMapper.updateIsClosePo(1, purchaseBillRequest.getPoNo());
            Boolean result = purchaseBillMapper.insert(purchaseBill);

            if (result) {
                //! Get the reference code
                String code = (generateCode.generateAutoCode("purchase_orders", "po_code", 7, "PB", true, "status != -1"));
                codeMapper.updateCode("purchase_orders", "po_code", code, purchaseBill.getId());
                purchaseBill.setPoCode(codeMapper.getCode("purchase_orders", "po_code", purchaseBill.getId()));

                // Insert General Ledger
                GeneralLedger generalLedger = new GeneralLedger();
                generalLedger.setPurchaseOrderId(purchaseBill.getId());
                generalLedger.setDate(purchaseBill.getPurchaseBillDate());
                generalLedger.setReference(purchaseBillMapper.getPoCode(purchaseBill.getId()));
                generalLedger.setCreatedBy(userId);
                generalLedger.setIsSys(1);
                purchaseBillMapper.insertGeneralLedger(generalLedger);

                // Insert General Ledger Detail
                GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                generalLedgerDetail.setType("Bill");
                generalLedgerDetail.setCompanyId(purchaseBill.getCompanyId());
                generalLedgerDetail.setChartAccountId(purchaseBill.getApId());
                generalLedgerDetail.setLocationId(purchaseBill.getLocationId());
                generalLedgerDetail.setVendorId(purchaseBill.getVendorId());
                generalLedgerDetail.setClassId(purchaseBillMapper.getClassByLocationGroupId(purchaseBill.getCompanyId(), purchaseBill.getWarehouseId()));
                generalLedgerDetail.setMemo("ICS:  Purchase Bill # " + purchaseBill.getPoCode());
                generalLedgerDetail.setDebit(0D);
                generalLedgerDetail.setCredit(purchaseBill.getBalance());
                purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                if (purchaseBillRequest.getDiscountAmount() > 0) {
                    // Insert General Ledger Detail
                    generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetail.setType("Total Discount");
                    generalLedgerDetail.setCompanyId(purchaseBill.getCompanyId());
                    generalLedgerDetail.setChartAccountId(purchaseBillMapper.getDiscountChartAccountId());
                    generalLedgerDetail.setLocationId(purchaseBill.getLocationId());
                    generalLedgerDetail.setVendorId(purchaseBill.getVendorId());
                    generalLedgerDetail.setClassId(purchaseBillMapper.getClassByLocationGroupId(purchaseBill.getCompanyId(), purchaseBill.getWarehouseId()));
                    generalLedgerDetail.setMemo("ICS:  Purchase Bill # " + purchaseBill.getPoCode() + " - " + "Total Discount");
                    generalLedgerDetail.setDebit(0D);
                    generalLedgerDetail.setCredit(purchaseBill.getDiscountAmount());
                    purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                }
                if (purchaseBillRequest.getFileAttachment() != null && !purchaseBillRequest.getFileAttachment().isEmpty()) {
                    for (PurchaseBillFileRequest attachment : purchaseBillRequest.getFileAttachment()) {
                        PurchaseOrderFile purchaseOrderFile = new PurchaseOrderFile();
                        purchaseOrderFile.setPurchaseOrderId(purchaseBill.getId());
                        purchaseOrderFile.setFileUrl(attachment.getUrl());
                        purchaseOrderFile.setFileName(attachment.getName());
                        purchaseBillMapper.insertFile(purchaseOrderFile);
                    }
                }

                if (purchaseBillRequest.getTotalVat() > 0) {
                    // Insert General Ledger Detail
                    generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetail.setType("VAT");
                    generalLedgerDetail.setCompanyId(purchaseBill.getCompanyId());
                    generalLedgerDetail.setChartAccountId(purchaseBillMapper.getVatChartAccountId(purchaseBill.getVatSettingId()));
                    generalLedgerDetail.setLocationId(purchaseBill.getLocationId());
                    generalLedgerDetail.setVendorId(purchaseBill.getVendorId());
                    generalLedgerDetail.setClassId(purchaseBillMapper.getClassByLocationGroupId(purchaseBill.getCompanyId(), purchaseBill.getWarehouseId()));
                    generalLedgerDetail.setMemo("ICS:  Purchase Bill # " + purchaseBill.getPoCode());
                    generalLedgerDetail.setDebit(purchaseBill.getTotalVat());
                    generalLedgerDetail.setCredit(0D);
                    purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                }
                    List<PurchaseBillDetailRequest> details = purchaseBillRequest.getDetails();
                    for (PurchaseBillDetailRequest detailRequest : details) {
                        if (detailRequest.getType() == 1) {
                            Long smallValUom = purchaseOrderMapper.getSmallValUom(detailRequest.getItemId());
                            // Calculate new cost
                            Long conversion = smallValUom / detailRequest.getConversion();
                            Double newCost = ((detailRequest.getTotalCost() - detailRequest.getDiscountAmount()) / (detailRequest.getQty() + detailRequest.getFoc())) * (smallValUom / conversion);
                            // Insert details
                            PurchaseBillDetail purchaseBillDetail = new PurchaseBillDetail();
                            purchaseBillDetail.setPurchaseBillId(purchaseBill.getId());
                            purchaseBillDetail.setItemId(detailRequest.getItemId());
                            purchaseBillDetail.setNote(detailRequest.getNote());
                            if (detailRequest.getExpiredDate().equals("0000-00-00")) {
                                purchaseBillDetail.setExpiredDate(null);
                            } else{
                                purchaseBillDetail.setExpiredDate(detailRequest.getExpiredDate());
                            }
                            purchaseBillDetail.setQty(detailRequest.getQty());
                            purchaseBillDetail.setFoc(detailRequest.getFoc());
                            purchaseBillDetail.setUomId(detailRequest.getUomId());
                            purchaseBillDetail.setTotalCost(detailRequest.getTotalCost());
                            purchaseBillDetail.setDiscountId(detailRequest.getDiscountId());
                            purchaseBillDetail.setDiscountAmount(detailRequest.getDiscountAmount());
                            purchaseBillDetail.setDiscountPercent(detailRequest.getDiscountPercent());
                            purchaseBillDetail.setUnitCost(detailRequest.getUnitCost());
                            purchaseBillDetail.setConversion(conversion);
                            purchaseBillDetail.setDefaultCost( detailRequest.getUnitCost() * (smallValUom / conversion) );
                            purchaseBillDetail.setNewCost(newCost);
                            purchaseBillMapper.insertDetail(purchaseBillDetail);

                            String productName = purchaseBillMapper.getProductName(detailRequest.getItemId());

                            // General Ledger Detail (Product Income)
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            generalLedgerDetail.setType("Bill");
                            generalLedgerDetail.setCompanyId(purchaseBill.getCompanyId());
                            generalLedgerDetail.setChartAccountId(purchaseBillMapper.getProductIncomeChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setLocationId(purchaseBill.getLocationId());
                            generalLedgerDetail.setVendorId(purchaseBill.getVendorId());
                            generalLedgerDetail.setProductId(detailRequest.getItemId());
                            generalLedgerDetail.setClassId(purchaseBillMapper.getClassByLocationGroupId(purchaseBill.getCompanyId(), purchaseBill.getWarehouseId()));
                            generalLedgerDetail.setMemo("ICS:  PB # " + purchaseBill.getPoCode() + " - " + productName);
                            generalLedgerDetail.setDebit(purchaseBillDetail.getTotalCost());
                            generalLedgerDetail.setCredit(0D);
                            purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            // General Ledger Detail (Product Discount)
                            if (purchaseBillDetail.getDiscountAmount() > 0) {
                                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                                generalLedgerDetail.setChartAccountId(purchaseBillMapper.getDiscountChartAccountId());
                                generalLedgerDetail.setMemo("ICS:  PB # " + purchaseBill.getPoCode() + " - " + productName + " Discount");
                                generalLedgerDetail.setDebit(0D);
                                generalLedgerDetail.setCredit(purchaseBillDetail.getDiscountAmount());
                                purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                            }

                        } else if (detailRequest.getType() == 2) {
                            // Insert Service
                            PurchaseBillServices purchaseBillServices = new PurchaseBillServices();
                            purchaseBillServices.setPurchaseBillId(purchaseBill.getId());
                            purchaseBillServices.setItemId(detailRequest.getItemId());
                            purchaseBillServices.setNote(detailRequest.getNote());
                            purchaseBillServices.setQty(detailRequest.getQty());
                            purchaseBillServices.setTotalCost(detailRequest.getTotalCost());
                            purchaseBillServices.setDiscountId(detailRequest.getDiscountId());
                            purchaseBillServices.setDiscountAmount(detailRequest.getDiscountAmount());
                            purchaseBillServices.setDiscountPercent(detailRequest.getDiscountPercent());
                            purchaseBillServices.setUnitCost(detailRequest.getUnitCost());
                            purchaseBillMapper.insertService(purchaseBillServices);
                            String serviceName = purchaseBillMapper.getServiceName(detailRequest.getItemId());
                            // General Ledger Detail (Service)
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            generalLedgerDetail.setType("Purchase Order Service");
                            generalLedgerDetail.setCompanyId(purchaseBill.getCompanyId());
                            generalLedgerDetail.setChartAccountId(purchaseBillMapper.getServiceChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setLocationId(purchaseBill.getLocationId());
                            generalLedgerDetail.setVendorId(purchaseBill.getVendorId());
                            generalLedgerDetail.setServiceId(detailRequest.getItemId());
                            generalLedgerDetail.setClassId(purchaseBillMapper.getClassByLocationGroupId(purchaseBill.getCompanyId(), purchaseBill.getWarehouseId()));
                            generalLedgerDetail.setMemo("ICS:  PB Service # " + purchaseBill.getPoCode() + " - " + serviceName);
                            generalLedgerDetail.setDebit(purchaseBillServices.getTotalCost());
                            generalLedgerDetail.setCredit(0D);
                            purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            // General Ledger Detail (Service Discount)
                            if (purchaseBillServices.getDiscountAmount() > 0) {
                                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                                generalLedgerDetail.setChartAccountId(purchaseBillMapper.getDiscountChartAccountId());
                                generalLedgerDetail.setMemo("ICS:  PB Service # " + purchaseBill.getPoCode() + " - " + serviceName + " Discount");
                                generalLedgerDetail.setDebit(0D);
                                generalLedgerDetail.setCredit(purchaseBillServices.getDiscountAmount());
                                purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                            }

                        } else {
                            // Insert misc
                            PurchaseBillMisc purchaseBillMisc = new PurchaseBillMisc();
                            purchaseBillMisc.setPurchaseBillId(purchaseBill.getId());
                            purchaseBillMisc.setDiscountId(detailRequest.getDiscountId());
                            purchaseBillMisc.setDescription(detailRequest.getNote());
                            purchaseBillMisc.setQty(detailRequest.getQty());
                            purchaseBillMisc.setUnitCost(detailRequest.getUnitCost());
                            purchaseBillMisc.setDiscountId(detailRequest.getDiscountId());
                            purchaseBillMisc.setDiscountAmount(detailRequest.getDiscountAmount());
                            purchaseBillMisc.setDiscountPercent(detailRequest.getDiscountPercent());
                            purchaseBillMisc.setTotalCost(detailRequest.getTotalCost());
                            purchaseBillMapper.insertMisc(purchaseBillMisc);

                            // Insert General Ledger Detail
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            generalLedgerDetail.setType("VAT");
                            generalLedgerDetail.setCompanyId(purchaseBill.getCompanyId());
                            generalLedgerDetail.setChartAccountId(purchaseBill.getApId());
                            generalLedgerDetail.setLocationId(purchaseBill.getLocationId());
                            generalLedgerDetail.setVendorId(purchaseBill.getVendorId());
                            generalLedgerDetail.setClassId(purchaseBillMapper.getClassByLocationGroupId(purchaseBill.getCompanyId(), purchaseBill.getWarehouseId()));
                            generalLedgerDetail.setMemo("ICS:  Purchase Bill # " + purchaseBill.getPoCode());
                            generalLedgerDetail.setDebit(purchaseBill.getTotalVat());
                            generalLedgerDetail.setCredit(0D);
                            purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                        }
                    }

                    List<Long> purchaseBillIds = purchaseBillMapper.getPbRelatedPo(purchaseBillRequest.getPoNo());
                    // Auto Fulfilled When Purchase Order All Items Matched In Purchase Bill
                    Long qtyPo = purchaseBillMapper.getQtyPo(purchaseBillRequest.getPoNo());

                    Long qtyPb = purchaseBillMapper.getQtyPb(purchaseBillIds);

                    if (Objects.equals(qtyPo, qtyPb)){
                        purchaseBillMapper.updateStatusPo(purchaseBillRequest.getPoNo());
                    }

                    // Auto Fulfilled When Purchase Order Already Receive In Good Receipt Note
                    if (hasGRR == 1){
                        purchaseBillMapper.autoFulfilled(purchaseBill.getId());
                    }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/purchase-bill/add", null, null, "Purchase Bill", "Purchase Bill (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-bill/add", line, error.toString(), "Purchase Bill", "Purchase Bill (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(PurchaseBillUpdateRequest purchaseBillUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Purchase Bill (Edit)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }

            Long hasGRR = purchaseBillMapper.checkPoInGoodReceiptNote(purchaseBillUpdateRequest.getPoNo());
            if (hasGRR == null) {
                hasGRR = 0L;
            }

            // Check Data
            PurchaseBill purchaseBill = new PurchaseBill();
            purchaseBill.setCompanyId(purchaseBillUpdateRequest.getCompanyId());
            purchaseBill.setPoCode(purchaseBillMapper.getPoCode(purchaseBillUpdateRequest.getId()));
            purchaseBill.setVendorId(purchaseBillUpdateRequest.getVendorId());
            purchaseBill.setWarehouseId(purchaseBillUpdateRequest.getWarehouseId());
            purchaseBill.setPaymentTermId(purchaseBillUpdateRequest.getPaymentTermId());
            purchaseBill.setCurrencyCenterId(purchaseBillMapper.getCurrencyCenterId(purchaseBillUpdateRequest.getCompanyId()));
            purchaseBill.setVatCalculate(purchaseBillMapper.getVatCalculate(purchaseBillUpdateRequest.getCompanyId()));
            purchaseBill.setVatChartAccountId(purchaseBillMapper.getVatChartAccountId(purchaseBillUpdateRequest.getVatSettingId()));
            purchaseBill.setExchangeRateId(purchaseBillUpdateRequest.getExchangeRateId());
            purchaseBill.setLocationId(purchaseBillUpdateRequest.getLocationId());
            purchaseBill.setInvoiceNo(purchaseBillUpdateRequest.getInvoiceNo());
            purchaseBill.setNote(purchaseBillUpdateRequest.getNote());
            purchaseBill.setPurchaseOrderId(purchaseBillUpdateRequest.getPoNo());
            purchaseBill.setPvRequestId(purchaseBillMapper.getERByPo(purchaseBillUpdateRequest.getPoNo()));
            purchaseBill.setApId(purchaseBillUpdateRequest.getApId());
            purchaseBill.setShipmentId(purchaseBillUpdateRequest.getShipmentId());
            purchaseBill.setPurchaseBillDate(purchaseBillUpdateRequest.getPurchaseBillDate());
            if (Objects.equals(purchaseBillUpdateRequest.getInvoiceDate(), "")) {
                purchaseBill.setInvoiceDate(null);
            } else {
                purchaseBill.setInvoiceDate(purchaseBillUpdateRequest.getInvoiceDate());
            }
            purchaseBill.setSubTotal(purchaseBillUpdateRequest.getSubTotal());
            purchaseBill.setDiscountAmount(purchaseBillUpdateRequest.getDiscountAmount());
            purchaseBill.setDiscountPercent(purchaseBillUpdateRequest.getDiscountPercent());
            purchaseBill.setVatSettingId(purchaseBillUpdateRequest.getVatSettingId());
            purchaseBill.setTotalVat(purchaseBillUpdateRequest.getTotalVat());
            Double depositAmount = purchaseBillUpdateRequest.getDepositAmount() == null ? 0D : purchaseBillUpdateRequest.getDepositAmount();
            purchaseBill.setDepositAmount(depositAmount);
            purchaseBill.setBalance(purchaseBillUpdateRequest.getSubTotal() - purchaseBillUpdateRequest.getDiscountAmount() + purchaseBillUpdateRequest.getTotalVat() - depositAmount);
            purchaseBill.setVatPercentage(purchaseBillUpdateRequest.getVatPercentage());
            purchaseBill.setModifiedBy(userId);
            Boolean archive = purchaseBillMapper.archive(purchaseBillUpdateRequest.getId(),userId);
            if (!archive){
                return ResponseMessageUtils.makeResponse(true, messageService.message("This Purchase Bill has been received.", false));
            }
            purchaseBillMapper.updateIsClosePo(0, purchaseBillUpdateRequest.getPoNo());
            Boolean result = purchaseBillMapper.update(purchaseBill);

            if (result) {
                purchaseBillMapper.deleteGeneralLedger(purchaseBillUpdateRequest.getId());
                purchaseBillMapper.deleteInventoryValuation(purchaseBillUpdateRequest.getId());
                if (purchaseBillUpdateRequest.getFileAttachment() != null && !purchaseBillUpdateRequest.getFileAttachment().isEmpty()) {
                    for (PurchaseBillFileRequest attachment : purchaseBillUpdateRequest.getFileAttachment()) {
                        PurchaseOrderFile purchaseOrderFile = new PurchaseOrderFile();
                        purchaseOrderFile.setPurchaseOrderId(purchaseBill.getId());
                        purchaseOrderFile.setFileUrl(attachment.getUrl());
                        purchaseOrderFile.setFileName(attachment.getName());
                        purchaseBillMapper.insertFile(purchaseOrderFile);
                    }
                }
                // Insert General Ledger
                GeneralLedger generalLedger = new GeneralLedger();
                generalLedger.setPurchaseOrderId(purchaseBill.getId());
                generalLedger.setDate(purchaseBill.getPurchaseBillDate());
                generalLedger.setReference(purchaseBillMapper.getPoCode(purchaseBill.getId()));
                generalLedger.setCreatedBy(userId);
                generalLedger.setIsSys(1);
                purchaseBillMapper.insertGeneralLedger(generalLedger);

                // Insert General Ledger Detail
                GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                generalLedgerDetail.setType("Bill");
                generalLedgerDetail.setCompanyId(purchaseBill.getCompanyId());
                generalLedgerDetail.setChartAccountId(purchaseBill.getApId());
                generalLedgerDetail.setLocationId(purchaseBill.getLocationId());
                generalLedgerDetail.setVendorId(purchaseBill.getVendorId());
                generalLedgerDetail.setClassId(purchaseBillMapper.getClassByLocationGroupId(purchaseBill.getCompanyId(), purchaseBill.getWarehouseId()));
                generalLedgerDetail.setMemo("ICS:  Purchase Bill # " + purchaseBill.getPoCode());
                generalLedgerDetail.setDebit(0D);
                generalLedgerDetail.setCredit(purchaseBill.getBalance());
                purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                if (purchaseBillUpdateRequest.getDiscountAmount() > 0) {
                    // Insert General Ledger Detail
                    generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetail.setType("Total Discount");
                    generalLedgerDetail.setCompanyId(purchaseBill.getCompanyId());
                    generalLedgerDetail.setChartAccountId(purchaseBillMapper.getDiscountChartAccountId());
                    generalLedgerDetail.setLocationId(purchaseBill.getLocationId());
                    generalLedgerDetail.setVendorId(purchaseBill.getVendorId());
                    generalLedgerDetail.setClassId(purchaseBillMapper.getClassByLocationGroupId(purchaseBill.getCompanyId(), purchaseBill.getWarehouseId()));
                    generalLedgerDetail.setMemo("ICS:  Purchase Bill # " + purchaseBill.getPoCode() + " - " + "Total Discount");
                    generalLedgerDetail.setDebit(0D);
                    generalLedgerDetail.setCredit(purchaseBill.getDiscountAmount());
                    purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                }

                if (purchaseBillUpdateRequest.getTotalVat() > 0) {
                    // Insert General Ledger Detail
                    generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetail.setType("VAT");
                    generalLedgerDetail.setCompanyId(purchaseBill.getCompanyId());
                    generalLedgerDetail.setChartAccountId(purchaseBillMapper.getVatChartAccountId(purchaseBill.getVatSettingId()));
                    generalLedgerDetail.setLocationId(purchaseBill.getLocationId());
                    generalLedgerDetail.setVendorId(purchaseBill.getVendorId());
                    generalLedgerDetail.setClassId(purchaseBillMapper.getClassByLocationGroupId(purchaseBill.getCompanyId(), purchaseBill.getWarehouseId()));
                    generalLedgerDetail.setMemo("ICS:  Purchase Bill # " + purchaseBill.getPoCode());
                    generalLedgerDetail.setDebit(purchaseBill.getTotalVat());
                    generalLedgerDetail.setCredit(0D);
                    purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                }
                List<PurchaseBillDetailRequest> details = purchaseBillUpdateRequest.getDetails();
                for (PurchaseBillDetailRequest detailRequest : details) {
                    if (detailRequest.getType() == 1) {
                        Long smallValUom = purchaseOrderMapper.getSmallValUom(detailRequest.getItemId());

                        Long conversion = smallValUom / detailRequest.getConversion();
                        Double newCost = ((detailRequest.getTotalCost() - detailRequest.getDiscountAmount()) / (detailRequest.getQty() + detailRequest.getFoc())) * (smallValUom / conversion);
                        // Insert details
                        PurchaseBillDetail purchaseBillDetail = new PurchaseBillDetail();
                        purchaseBillDetail.setPurchaseBillId(purchaseBill.getId());
                        purchaseBillDetail.setItemId(detailRequest.getItemId());
                        purchaseBillDetail.setNote(detailRequest.getNote());
                        if (detailRequest.getExpiredDate().equals("0000-00-00")) {
                            purchaseBillDetail.setExpiredDate(null);
                        } else{
                            purchaseBillDetail.setExpiredDate(detailRequest.getExpiredDate());
                        }
                        purchaseBillDetail.setQty(detailRequest.getQty());
                        purchaseBillDetail.setFoc(detailRequest.getFoc());
                        purchaseBillDetail.setUomId(detailRequest.getUomId());
                        purchaseBillDetail.setTotalCost(detailRequest.getTotalCost());
                        purchaseBillDetail.setDiscountId(detailRequest.getDiscountId());
                        purchaseBillDetail.setDiscountAmount(detailRequest.getDiscountAmount());
                        purchaseBillDetail.setDiscountPercent(detailRequest.getDiscountPercent());
                        purchaseBillDetail.setUnitCost(detailRequest.getUnitCost());
                        purchaseBillDetail.setConversion(conversion);
                        purchaseBillDetail.setDefaultCost( detailRequest.getUnitCost() * (smallValUom / conversion) );
                        purchaseBillDetail.setNewCost(newCost);
                        purchaseBillMapper.insertDetail(purchaseBillDetail);

                        String productName = purchaseBillMapper.getProductName(detailRequest.getItemId());

                        // General Ledger Detail (Product Income)
                        generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                        generalLedgerDetail.setType("Bill");
                        generalLedgerDetail.setCompanyId(purchaseBill.getCompanyId());
                        generalLedgerDetail.setChartAccountId(purchaseBillMapper.getProductIncomeChartAccountId(detailRequest.getItemId()));
                        generalLedgerDetail.setLocationId(purchaseBill.getLocationId());
                        generalLedgerDetail.setVendorId(purchaseBill.getVendorId());
                        generalLedgerDetail.setProductId(detailRequest.getItemId());
                        generalLedgerDetail.setClassId(purchaseBillMapper.getClassByLocationGroupId(purchaseBill.getCompanyId(), purchaseBill.getWarehouseId()));
                        generalLedgerDetail.setMemo("ICS:  PB # " + purchaseBill.getPoCode() + " - " + productName);
                        generalLedgerDetail.setDebit(purchaseBillDetail.getTotalCost());
                        generalLedgerDetail.setCredit(0D);
                        purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                        // General Ledger Detail (Product Discount)
                        if (purchaseBillDetail.getDiscountAmount() > 0) {
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            generalLedgerDetail.setChartAccountId(purchaseBillMapper.getDiscountChartAccountId());
                            generalLedgerDetail.setMemo("ICS:  PB # " + purchaseBill.getPoCode() + " - " + productName + " Discount");
                            generalLedgerDetail.setDebit(0D);
                            generalLedgerDetail.setCredit(purchaseBillDetail.getDiscountAmount());
                            purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                        }

                    } else if (detailRequest.getType() == 2) {
                        // Insert Service
                        PurchaseBillServices purchaseBillServices = new PurchaseBillServices();
                        purchaseBillServices.setPurchaseBillId(purchaseBill.getId());
                        purchaseBillServices.setItemId(detailRequest.getItemId());
                        purchaseBillServices.setNote(detailRequest.getNote());
                        purchaseBillServices.setQty(detailRequest.getQty());
                        purchaseBillServices.setTotalCost(detailRequest.getTotalCost());
                        purchaseBillServices.setDiscountId(detailRequest.getDiscountId());
                        purchaseBillServices.setDiscountAmount(detailRequest.getDiscountAmount());
                        purchaseBillServices.setDiscountPercent(detailRequest.getDiscountPercent());
                        purchaseBillServices.setUnitCost(detailRequest.getUnitCost());
                        purchaseBillMapper.insertService(purchaseBillServices);
                        String serviceName = purchaseBillMapper.getServiceName(detailRequest.getItemId());
                        // General Ledger Detail (Service)
                        generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                        generalLedgerDetail.setType("Purchase Order Service");
                        generalLedgerDetail.setCompanyId(purchaseBill.getCompanyId());
                        generalLedgerDetail.setChartAccountId(purchaseBillMapper.getServiceChartAccountId(detailRequest.getItemId()));
                        generalLedgerDetail.setLocationId(purchaseBill.getLocationId());
                        generalLedgerDetail.setVendorId(purchaseBill.getVendorId());
                        generalLedgerDetail.setServiceId(detailRequest.getItemId());
                        generalLedgerDetail.setClassId(purchaseBillMapper.getClassByLocationGroupId(purchaseBill.getCompanyId(), purchaseBill.getWarehouseId()));
                        generalLedgerDetail.setMemo("ICS:  PB Service # " + purchaseBill.getPoCode() + " - " + serviceName);
                        generalLedgerDetail.setDebit(purchaseBillServices.getTotalCost());
                        generalLedgerDetail.setCredit(0D);
                        purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                        // General Ledger Detail (Service Discount)
                        if (purchaseBillServices.getDiscountAmount() > 0) {
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            generalLedgerDetail.setChartAccountId(purchaseBillMapper.getDiscountChartAccountId());
                            generalLedgerDetail.setMemo("ICS:  PB Service # " + purchaseBill.getPoCode() + " - " + serviceName + " Discount");
                            generalLedgerDetail.setDebit(0D);
                            generalLedgerDetail.setCredit(purchaseBillServices.getDiscountAmount());
                            purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                        }

                    } else {
                        // Insert misc
                        PurchaseBillMisc purchaseBillMisc = new PurchaseBillMisc();
                        purchaseBillMisc.setPurchaseBillId(purchaseBill.getId());
                        purchaseBillMisc.setDiscountId(detailRequest.getDiscountId());
                        purchaseBillMisc.setDescription(detailRequest.getNote());
                        purchaseBillMisc.setQty(detailRequest.getQty());
                        purchaseBillMisc.setUnitCost(detailRequest.getUnitCost());
                        purchaseBillMisc.setDiscountId(detailRequest.getDiscountId());
                        purchaseBillMisc.setDiscountAmount(detailRequest.getDiscountAmount());
                        purchaseBillMisc.setDiscountPercent(detailRequest.getDiscountPercent());
                        purchaseBillMisc.setTotalCost(detailRequest.getTotalCost());
                        purchaseBillMapper.insertMisc(purchaseBillMisc);

                        // Insert General Ledger Detail
                        generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                        generalLedgerDetail.setType("VAT");
                        generalLedgerDetail.setCompanyId(purchaseBill.getCompanyId());
                        generalLedgerDetail.setChartAccountId(purchaseBill.getApId());
                        generalLedgerDetail.setLocationId(purchaseBill.getLocationId());
                        generalLedgerDetail.setVendorId(purchaseBill.getVendorId());
                        generalLedgerDetail.setClassId(purchaseBillMapper.getClassByLocationGroupId(purchaseBill.getCompanyId(), purchaseBill.getWarehouseId()));
                        generalLedgerDetail.setMemo("ICS:  Purchase Bill # " + purchaseBill.getPoCode());
                        generalLedgerDetail.setDebit(purchaseBill.getTotalVat());
                        generalLedgerDetail.setCredit(0D);
                        purchaseBillMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                    }
                }

                List<Long> purchaseBillIds = purchaseBillMapper.getPbRelatedPo(purchaseBillUpdateRequest.getPoNo());
                // Auto Fulfilled When Purchase Order All Items Matched In Purchase Bill
                Long qtyPo = purchaseBillMapper.getQtyPo(purchaseBillUpdateRequest.getPoNo());

                Long qtyPb = purchaseBillMapper.getQtyPb(purchaseBillIds);

                if (Objects.equals(qtyPo, qtyPb)){
                    purchaseBillMapper.updateStatusPo(purchaseBillUpdateRequest.getPoNo());
                }

                // Auto Fulfilled When Purchase Order Already Receive In Good Receipt Note
                if (hasGRR == 1){
                    purchaseBillMapper.autoFulfilled(purchaseBill.getId());
                }
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/purchase-bill/update", null, null, "Purchase Bill", "Purchase Bill (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-bill/update", line, error.toString(), "Purchase Bill", "Purchase Bill (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Purchase Bill (Void)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }
            purchaseBillMapper.deleteGeneralLedger(id);
            purchaseBillMapper.deleteInventoryValuation(id);
            Boolean result = purchaseBillMapper.delete(id, userId);
            if (result) {
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/purchase-bill/delete/{id}", null, null, "Purchase Bill", "Purchase Bill (Void)", "Void", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-bill/delete/{id}", line, error.toString(), "Purchase Bill", "Purchase Bill (Void)", "Void", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> approve(Long id, Long isApprove, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Purchase Bill (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = purchaseBillMapper.approve(id, isApprove, userId);

            if (result) {
                if (Objects.equals(isApprove, 2L)) {
                    Long purchaseOrderId = purchaseBillMapper.getPurchaseRequestId(id);
                    if (purchaseOrderId != null) {
                        Long hasGRR = purchaseBillMapper.checkPoInGoodReceiptNote(purchaseOrderId);
                        if (hasGRR != null && hasGRR == 1) {
                            purchaseBillMapper.updateInventoryValuationPurchaseBillId(id, purchaseOrderId);
                        }
                    }
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/purchase-bill/approve/{id}", null, null, "Purchase Bill", "Purchase Bill (Approve)", "Approve", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-bill/approve/{id}", line, error.toString(), "Purchase Bill", "Purchase Bill (Approve)", "Approve", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}

