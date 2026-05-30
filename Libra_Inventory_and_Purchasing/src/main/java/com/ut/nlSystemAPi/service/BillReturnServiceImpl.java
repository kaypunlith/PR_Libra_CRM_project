package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.BillReturnFilter;
import com.ut.nlSystemAPi.model.filter.BillReturnReceiptFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.request.Login.BillReturn.*;
import com.ut.nlSystemAPi.model.response.BillReturn.BillReturnDetailResponse;
import com.ut.nlSystemAPi.model.response.BillReturn.BillReturnReceiptResponse;
import com.ut.nlSystemAPi.model.response.BillReturn.BillReturnResponse;
import com.ut.nlSystemAPi.model.response.BillReturn.VatSettingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class BillReturnServiceImpl implements BillReturnService {

    @Autowired
    private BillReturnMapper billReturnMapper;

    @Autowired
    private PurchaseBillMapper purchaseBillMapper;

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private TransferConsignmentMapper transferConsignmentMapper;

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
    private CodeMapper codeMapper;
    @Autowired
    private GenerateCode generateCode;

    public ResponseMessage<BaseResult> getList(BillReturnFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Bill Return (View)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }
             if (permissionMapper.checkPermission(userId, "Bill Return (View By User)") > 0) {
                 filter.setViewByUser(1L);
             }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(billReturnMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<BillReturnResponse> billReturnResponses = billReturnMapper.getList(filter, userId);

            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bill-return/list", null, null, "Bill Return", "Bill Return (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", billReturnResponses, pagination, true));
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bill-return/list", line, error.toString(), "Bill Return", "Bill Return (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Bill Return (View)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }

            List<BillReturnResponse> billReturnResponses = billReturnMapper.getOne(id, userId);

            if (!billReturnResponses.isEmpty()) {
                for (int i = 0; i < billReturnResponses.size(); i++) {
                    billReturnResponses.get(i).setDetails(billReturnMapper.getDetail(billReturnResponses.get(i).getId()));
                }
            }
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bill-return/find/{id}", null, null, "Bill Return", "Bill Return (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", billReturnResponses, true));
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bill-return/find/{id}", line, error.toString(), "Bill Return", "Bill Return (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(BillReturnRequest billReturnRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Bill Return (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<VatSettingResponse> vatSetting = billReturnMapper.getVatSetting(billReturnRequest.getVatSettingId());

            // Check data
            BillReturn billReturn = new BillReturn();
            billReturn.setCompanyId(billReturnRequest.getCompanyId());
            billReturn.setVendorId(billReturnRequest.getVendorId());
            billReturn.setWarehouseId(billReturnRequest.getWarehouseId());
            billReturn.setPurchaseBillId(billReturnRequest.getPurchaseBillId());
            billReturn.setLocationId(billReturnRequest.getLocationId());
            billReturn.setDate(billReturnRequest.getDate());
            billReturn.setNote(billReturnRequest.getNote());
            billReturn.setApId(billReturnRequest.getApId());
            billReturn.setSubTotal(billReturnRequest.getSubTotal());
            billReturn.setVatSettingId(billReturnRequest.getVatSettingId());
            billReturn.setTotal(billReturnRequest.getTotal());
            billReturn.setVatPercentage(vatSetting.get(0).getVatPercent());
            billReturn.setVatChartAccountId(vatSetting.get(0).getChartAccountId());
            billReturn.setCurrencyCenterId(purchaseBillMapper.getCurrencyCenterId(billReturnRequest.getCompanyId()));
            billReturn.setVatCalculate(purchaseBillMapper.getVatCalculate(billReturnRequest.getCompanyId()));
            billReturn.setTotalVat(billReturn.getSubTotal() * billReturn.getVatPercentage() / 100);
            billReturn.setBalance(billReturn.getSubTotal() + billReturn.getTotalVat());
            billReturn.setCreatedBy(userId);

            Boolean result = billReturnMapper.insert(billReturn);

            if (result) {

                //! Get the reference code
                String code = (generateCode.generateAutoCode("purchase_returns", "pr_code", 7, "PBR", true, "status != -1"));
                codeMapper.updateCode("purchase_returns", "pr_code", code, billReturn.getId());
                billReturn.setPrCode(codeMapper.getCode("purchase_returns", "pr_code", billReturn.getId()));

                // Insert General Ledger
                GeneralLedger generalLedger = new GeneralLedger();
                generalLedger.setPurchaseReturnId(billReturn.getId());
                generalLedger.setDate(billReturn.getDate());
                generalLedger.setReference(billReturnMapper.getPrCode(billReturn.getId()));
                generalLedger.setCreatedBy(userId);
                generalLedger.setIsSys(1);
                generalLedger.setIsActive(1);
                generalLedger.setIsAdj(0);
                billReturnMapper.insertGeneralLedger(generalLedger);


                // Insert General Ledger Detail
                GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                generalLedgerDetail.setType("Bill Return");
                generalLedgerDetail.setCompanyId(billReturn.getCompanyId());
                generalLedgerDetail.setChartAccountId(billReturn.getApId());
                generalLedgerDetail.setLocationId(billReturn.getLocationId());
                generalLedgerDetail.setVendorId(billReturn.getVendorId());
                generalLedgerDetail.setClassId(billReturnMapper.getClassByLocationGroupId(billReturn.getCompanyId(), billReturn.getWarehouseId()));
                generalLedgerDetail.setMemo("ICS:  Bill Return # " + billReturn.getPrCode());
                generalLedgerDetail.setDebit(billReturn.getBalance());
                generalLedgerDetail.setCredit(0D);
                billReturnMapper.insertGeneralLedgerDetail(generalLedgerDetail);


                if (billReturn.getTotalVat() > 0) {
                    // Insert General Ledger Detail
                    generalLedgerDetail.setType("VAT");
                    generalLedgerDetail.setChartAccountId(billReturn.getVatChartAccountId());
                    generalLedgerDetail.setDebit(0D);
                    generalLedgerDetail.setCredit(billReturn.getTotalVat());
                    generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetail.setCompanyId(billReturn.getCompanyId());
                    generalLedgerDetail.setLocationId(billReturn.getLocationId());
                    generalLedgerDetail.setVendorId(billReturn.getVendorId());
                    generalLedgerDetail.setClassId(billReturnMapper.getClassByLocationGroupId(billReturn.getCompanyId(), billReturn.getWarehouseId()));
                    generalLedgerDetail.setMemo("ICS:  Bill Return # " + billReturn.getPrCode());
                    billReturnMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                }
                List<BillReturnDetailRequest> details = billReturnRequest.getDetails();
                for (BillReturnDetailRequest detailRequest : details) {
                    if (detailRequest.getType() == 1) {
                        // Insert details
                        BillReturnDetail billReturnDetail = new BillReturnDetail();
                        billReturnDetail.setPurchaseReturnId(billReturn.getId());
                        billReturnDetail.setItemId(detailRequest.getItemId());
                        billReturnDetail.setNote(detailRequest.getNote());
                        String expiredDate = detailRequest.getExpiredDate();
                        if (expiredDate == null || expiredDate.isEmpty() || "0000-00-00".equals(expiredDate)) {
                            billReturnDetail.setExpiredDate(null);
                        } else {
                            billReturnDetail.setExpiredDate(expiredDate);
                        }
                        billReturnDetail.setQty(detailRequest.getQty());
                        billReturnDetail.setUomId(detailRequest.getUomId());
                        billReturnDetail.setTotalCost(detailRequest.getTotalCost());
                        billReturnDetail.setUnitCost(detailRequest.getUnitCost());
                        billReturnDetail.setSmallUom(billReturnMapper.getSmallValUom(detailRequest.getItemId()));
                        billReturnDetail.setConversion(billReturnDetail.getSmallUom() / detailRequest.getConversion());
                        billReturnMapper.insertDetail(billReturnDetail);

                    } else if (detailRequest.getType() == 2) {
                        // Insert Service
                        BillReturnServices billReturnServices = new BillReturnServices();
                        billReturnServices.setPurchaseReturnId(billReturn.getId());
                        billReturnServices.setItemId(detailRequest.getItemId());
                        billReturnServices.setNote(detailRequest.getNote());
                        billReturnServices.setQty(detailRequest.getQty());
                        billReturnServices.setTotalCost(detailRequest.getTotalCost());
                        billReturnServices.setUnitCost(detailRequest.getUnitCost());
                        billReturnMapper.insertService(billReturnServices);
                        String serviceName = purchaseBillMapper.getServiceName(detailRequest.getItemId());

                        // General Ledger Detail Service
                        generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                        generalLedgerDetail.setType("Bill Return");
                        generalLedgerDetail.setCompanyId(billReturn.getCompanyId());
                        generalLedgerDetail.setChartAccountId(purchaseBillMapper.getServiceChartAccountId(detailRequest.getItemId()));
                        generalLedgerDetail.setServiceId(detailRequest.getItemId());
                        generalLedgerDetail.setLocationId(billReturn.getLocationId());
                        generalLedgerDetail.setVendorId(billReturn.getVendorId());
                        generalLedgerDetail.setClassId(billReturnMapper.getClassByLocationGroupId(billReturn.getCompanyId(), billReturn.getWarehouseId()));
                        generalLedgerDetail.setMemo("ICS:  Bill Return # " + billReturn.getPrCode() + " - " + serviceName);
                        generalLedgerDetail.setDebit(0D);
                        generalLedgerDetail.setCredit(billReturnServices.getTotalCost());
                        billReturnMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                    } else {
                        // Insert Misc
                        BillReturnMisc billReturnMisc = new BillReturnMisc();
                        billReturnMisc.setPurchaseReturnId(billReturn.getId());
                        billReturnMisc.setDescription(detailRequest.getItemName());
                        billReturnMisc.setNote(detailRequest.getNote());
                        billReturnMisc.setQty(detailRequest.getQty());
                        billReturnMisc.setQtyUomId(detailRequest.getUomId());
                        billReturnMisc.setNote(detailRequest.getNote());
                        billReturnMisc.setUnitCost(detailRequest.getUnitCost());
                        billReturnMisc.setTotalCost(detailRequest.getTotalCost());
                        billReturnMapper.insertMisc(billReturnMisc);

                        // General Ledger Detail Misc
                        generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                        generalLedgerDetail.setType("Bill Return");
                        generalLedgerDetail.setCompanyId(billReturn.getCompanyId());
                        generalLedgerDetail.setChartAccountId(purchaseBillMapper.getMiscChartAccountId());
                        generalLedgerDetail.setServiceId(detailRequest.getItemId());
                        generalLedgerDetail.setLocationId(billReturn.getLocationId());
                        generalLedgerDetail.setVendorId(billReturn.getVendorId());
                        generalLedgerDetail.setClassId(billReturnMapper.getClassByLocationGroupId(billReturn.getCompanyId(), billReturn.getWarehouseId()));
                        generalLedgerDetail.setMemo("ICS:  Bill Return # " + billReturn.getPrCode() + " - " + billReturnMisc.getDescription());
                        generalLedgerDetail.setDebit(0D);
                        generalLedgerDetail.setCredit(billReturnMisc.getTotalCost());
                        billReturnMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                    }
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/bill-return/add", null, null, "Bill Return", "Bill Return (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bill-return/add", line, error.toString(), "Bill Return", "Bill Return (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(BillReturnUpdateRequest billReturnUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Bill Return (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<VatSettingResponse> vatSetting = billReturnMapper.getVatSetting(billReturnUpdateRequest.getVatSettingId());
            // Check data
            BillReturn billReturn = new BillReturn();
            billReturn.setPrCode(billReturnMapper.getPrCode(billReturnUpdateRequest.getId()));
            billReturn.setCompanyId(billReturnUpdateRequest.getCompanyId());
            billReturn.setPurchaseBillId(billReturnUpdateRequest.getPurchaseBillId());
            billReturn.setVendorId(billReturnUpdateRequest.getVendorId());
            billReturn.setWarehouseId(billReturnUpdateRequest.getWarehouseId());
            billReturn.setLocationId(billReturnUpdateRequest.getLocationId());
            billReturn.setDate(billReturnUpdateRequest.getDate());
            billReturn.setNote(billReturnUpdateRequest.getNote());
            billReturn.setApId(billReturnUpdateRequest.getApId());
            billReturn.setSubTotal(billReturnUpdateRequest.getSubTotal());
            billReturn.setVatSettingId(billReturnUpdateRequest.getVatSettingId());
            billReturn.setTotal(billReturnUpdateRequest.getTotal());
            billReturn.setVatPercentage(vatSetting.get(0).getVatPercent());
            billReturn.setVatChartAccountId(vatSetting.get(0).getChartAccountId());
            billReturn.setVatCalculate(0L);
            billReturn.setTotalVat(billReturn.getSubTotal() * billReturn.getVatPercentage() / 100);
            billReturn.setBalance(billReturn.getSubTotal() + billReturn.getTotalVat());
            billReturn.setModifiedBy(userId);
            billReturnMapper.archive(billReturnUpdateRequest.getId(), userId);

            Boolean result = billReturnMapper.update(billReturn);

            if (result) {
                // Insert General Ledger
                GeneralLedger generalLedger = new GeneralLedger();
                generalLedger.setPurchaseReturnId(billReturn.getId());
                generalLedger.setDate(billReturn.getDate());
                generalLedger.setReference(billReturnMapper.getPrCode(billReturn.getId()));
                generalLedger.setCreatedBy(userId);
                generalLedger.setIsSys(1);
                generalLedger.setIsActive(1);
                generalLedger.setIsAdj(0);
                billReturnMapper.insertGeneralLedger(generalLedger);

                // Insert General Ledger Detail
                GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                generalLedgerDetail.setType("Bill Return");
                generalLedgerDetail.setCompanyId(billReturn.getCompanyId());
                generalLedgerDetail.setChartAccountId(billReturn.getApId());
                generalLedgerDetail.setLocationId(billReturn.getLocationId());
                generalLedgerDetail.setVendorId(billReturn.getVendorId());
                generalLedgerDetail.setClassId(billReturnMapper.getClassByLocationGroupId(billReturn.getCompanyId(), billReturn.getWarehouseId()));
                generalLedgerDetail.setMemo("ICS:  Bill Return # " + billReturn.getPrCode());
                generalLedgerDetail.setDebit(billReturn.getBalance());
                generalLedgerDetail.setCredit(0D);
                billReturnMapper.insertGeneralLedgerDetail(generalLedgerDetail);


                if (billReturn.getTotalVat() > 0) {
                    // Insert General Ledger Detail
                    generalLedgerDetail.setType("VAT");
                    generalLedgerDetail.setChartAccountId(billReturn.getVatChartAccountId());
                    generalLedgerDetail.setDebit(0D);
                    generalLedgerDetail.setCredit(billReturn.getTotalVat());
                    generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetail.setCompanyId(billReturn.getCompanyId());
                    generalLedgerDetail.setLocationId(billReturn.getLocationId());
                    generalLedgerDetail.setVendorId(billReturn.getVendorId());
                    generalLedgerDetail.setClassId(billReturnMapper.getClassByLocationGroupId(billReturn.getCompanyId(), billReturn.getWarehouseId()));
                    generalLedgerDetail.setMemo("ICS:  Bill Return # " + billReturn.getPrCode());
                    billReturnMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                }

                billReturnMapper.deleteInventoryValuation(billReturnUpdateRequest.getId());
                billReturnMapper.deleteGeneralLedger(billReturnUpdateRequest.getId());

                List<BillReturnDetailRequest> details = billReturnUpdateRequest.getDetails();
                for (BillReturnDetailRequest detailRequest : details) {
                    if (detailRequest.getType() == 1) {
                        // Insert details
                        BillReturnDetail billReturnDetail = new BillReturnDetail();
                        billReturnDetail.setPurchaseReturnId(billReturn.getId());
                        billReturnDetail.setItemId(detailRequest.getItemId());
                        billReturnDetail.setNote(detailRequest.getNote());
                        String expiredDate = detailRequest.getExpiredDate();
                        if (expiredDate == null || expiredDate.isEmpty() || "0000-00-00".equals(expiredDate)) {
                            billReturnDetail.setExpiredDate(null);
                        } else {
                            billReturnDetail.setExpiredDate(expiredDate);
                        }
                        billReturnDetail.setQty(detailRequest.getQty());
                        billReturnDetail.setUomId(detailRequest.getUomId());
                        billReturnDetail.setTotalCost(detailRequest.getTotalCost());
                        billReturnDetail.setUnitCost(detailRequest.getUnitCost());
                        billReturnDetail.setSmallUom(billReturnMapper.getSmallValUom(detailRequest.getItemId()));
                        billReturnDetail.setConversion(billReturnDetail.getSmallUom() / detailRequest.getConversion());
                        billReturnMapper.insertDetail(billReturnDetail);

                    } else if (detailRequest.getType() == 2) {
                        // Insert Service
                        BillReturnServices billReturnServices = new BillReturnServices();
                        billReturnServices.setPurchaseReturnId(billReturn.getId());
                        billReturnServices.setItemId(detailRequest.getItemId());
                        billReturnServices.setNote(detailRequest.getNote());
                        billReturnServices.setQty(detailRequest.getQty());
                        billReturnServices.setTotalCost(detailRequest.getTotalCost());
                        billReturnServices.setUnitCost(detailRequest.getUnitCost());
                        billReturnMapper.insertService(billReturnServices);
                        String serviceName = purchaseBillMapper.getServiceName(detailRequest.getItemId());

                        // General Ledger Detail Service
                        generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                        generalLedgerDetail.setType("Bill Return");
                        generalLedgerDetail.setCompanyId(billReturn.getCompanyId());
                        generalLedgerDetail.setChartAccountId(purchaseBillMapper.getServiceChartAccountId(detailRequest.getItemId()));
                        generalLedgerDetail.setServiceId(detailRequest.getItemId());
                        generalLedgerDetail.setLocationId(billReturn.getLocationId());
                        generalLedgerDetail.setVendorId(billReturn.getVendorId());
                        generalLedgerDetail.setClassId(billReturnMapper.getClassByLocationGroupId(billReturn.getCompanyId(), billReturn.getWarehouseId()));
                        generalLedgerDetail.setMemo("ICS:  Bill Return # " + billReturn.getPrCode() + " - " + serviceName);
                        generalLedgerDetail.setDebit(0D);
                        generalLedgerDetail.setCredit(billReturnServices.getTotalCost());
                        billReturnMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                    } else {
                        // Insert Misc
                        BillReturnMisc billReturnMisc = new BillReturnMisc();
                        billReturnMisc.setPurchaseReturnId(billReturn.getId());
                        billReturnMisc.setDescription(detailRequest.getItemName());
                        billReturnMisc.setNote(detailRequest.getNote());
                        billReturnMisc.setQty(detailRequest.getQty());
                        billReturnMisc.setQtyUomId(detailRequest.getUomId());
                        billReturnMisc.setNote(detailRequest.getNote());
                        billReturnMisc.setUnitCost(detailRequest.getUnitCost());
                        billReturnMisc.setTotalCost(detailRequest.getTotalCost());
                        billReturnMapper.insertMisc(billReturnMisc);

                        // General Ledger Detail Misc
                        generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                        generalLedgerDetail.setType("Bill Return");
                        generalLedgerDetail.setCompanyId(billReturn.getCompanyId());
                        generalLedgerDetail.setChartAccountId(purchaseBillMapper.getMiscChartAccountId());
                        generalLedgerDetail.setServiceId(detailRequest.getItemId());
                        generalLedgerDetail.setLocationId(billReturn.getLocationId());
                        generalLedgerDetail.setVendorId(billReturn.getVendorId());
                        generalLedgerDetail.setClassId(billReturnMapper.getClassByLocationGroupId(billReturn.getCompanyId(), billReturn.getWarehouseId()));
                        generalLedgerDetail.setMemo("ICS:  Bill Return # " + billReturn.getPrCode() + " - " + billReturnMisc.getDescription());
                        generalLedgerDetail.setDebit(0D);
                        generalLedgerDetail.setCredit(billReturnMisc.getTotalCost());
                        billReturnMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                    }
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/bill-return/update", null, null, "Bill Return", "Bill Return (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bill-return/update", line, error.toString(), "Bill Return", "Bill Return (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Bill Return (Void)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }
             billReturnMapper.deleteGeneralLedger(id);
             billReturnMapper.deleteInventoryValuation(id);
             Boolean result = billReturnMapper.delete(id, userId);

            String sqlTrack = billReturnMapper.getSqlTrack();
            String orderDate = billReturnMapper.getOrderDate(id);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            boolean shouldUpdate = sqlTrack == null || sqlTrack.equals("0000-00-00");
            if (!shouldUpdate && orderDate != null) {
                    LocalDate trackDate = LocalDate.parse(sqlTrack, formatter);
                    LocalDate orderDateParsed = LocalDate.parse(orderDate, formatter);
                    shouldUpdate = !trackDate.isBefore(orderDateParsed);
            }

            if (shouldUpdate) {
                billReturnMapper.updateTrack(orderDate);
            }

            if (result) {
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/bill-return/delete/{id}", null, null, "Bill Return", "Bill Return (Void)", "Void", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bill-return/delete/{id}", line, error.toString(), "Bill Return", "Bill Return (Void)", "Void", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> pick(StatusFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Bill Return (Receive)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }

            Boolean result = billReturnMapper.updateStatus(filter, userId);
            if (result) {
                List<BillReturnResponse> responses = billReturnMapper.getOne(filter.getId(), userId);
                if (!responses.isEmpty()) {
                    List<BillReturnDetailResponse> details = billReturnMapper.getDetail(responses.get(0).getId());
                    Long calculateCog = purchaseBillMapper.getCalculateCog();
                    Long generalLedgerId = null;
                    if (calculateCog != null && calculateCog == 1) {
                        generalLedgerId = billReturnMapper.getActiveGeneralLedgerId(responses.get(0).getId());
                        if (generalLedgerId == null) {
                            GeneralLedger generalLedger = new GeneralLedger();
                            generalLedger.setPurchaseReturnId(responses.get(0).getId());
                            generalLedger.setDate(responses.get(0).getOrderDate());
                            generalLedger.setReference(responses.get(0).getPrCode());
                            generalLedger.setCreatedBy(userId);
                            generalLedger.setIsSys(1);
                            generalLedger.setIsActive(1);
                            generalLedger.setIsAdj(0);
                            billReturnMapper.insertGeneralLedger(generalLedger);
                            generalLedgerId = generalLedger.getId();
                        }
                    }
                 
                    for(BillReturnDetailResponse detail : details) {
                        if (detail.getType() == 1 && calculateCog != null && calculateCog == 1) {
                            Long smallUom = billReturnMapper.getSmallValUom(detail.getItemId());
                            InventoryValuation inventoryValuation = new InventoryValuation();
                            inventoryValuation.setPurchaseReturnId(responses.get(0).getId());
                            inventoryValuation.setType("Bill Return");
                            inventoryValuation.setReference(responses.get(0).getPrCode());
                            inventoryValuation.setCompanyId(responses.get(0).getCompanyId());
                            inventoryValuation.setDate(responses.get(0).getOrderDate());
                            inventoryValuation.setPid(detail.getItemId());
                            inventoryValuation.setSmallQty(-1D * (detail.getQty() * detail.getConversion()));
                            inventoryValuation.setQty(-1D * (detail.getQty() / (double) (smallUom / detail.getConversion())));
                            inventoryValuation.setPrice(detail.getUnitCost() * smallUom / detail.getConversion());
                            inventoryValuation.setIsVarCost(1);
                            billReturnMapper.insertInventoryValuation(inventoryValuation);

                            if (generalLedgerId != null) {
                                String productName = purchaseBillMapper.getProductName(detail.getItemId());

                                GeneralLedgerDetail inventoryDetail = new GeneralLedgerDetail();
                                inventoryDetail.setGeneralLedgerId(generalLedgerId);
                                inventoryDetail.setType("Bill Return");
                                inventoryDetail.setCompanyId(responses.get(0).getCompanyId());
                                inventoryDetail.setChartAccountId(purchaseBillMapper.getProductIncomeChartAccountId(detail.getItemId()));
                                inventoryDetail.setProductId(detail.getItemId());
                                inventoryDetail.setLocationId(responses.get(0).getLocationId());
                                inventoryDetail.setVendorId(responses.get(0).getVendorId());
                                inventoryDetail.setInventoryValuationId(inventoryValuation.getId());
                                inventoryDetail.setInventoryValuationIsDebit(0);
                                inventoryDetail.setClassId(billReturnMapper.getClassByLocationGroupId(responses.get(0).getCompanyId(), responses.get(0).getWarehouseId()));
                                inventoryDetail.setMemo("ICS:  Bill Return # " + responses.get(0).getPrCode() + " - " + productName);
                                inventoryDetail.setDebit(0D);
                                inventoryDetail.setCredit(detail.getTotalCost());
                                billReturnMapper.insertGeneralLedgerDetail(inventoryDetail);
                            }
                        }
                        GlobalStock globalStock  = new GlobalStock();
                        globalStock.setPurchaseReturnId(responses.get(0).getId());
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
                        globalStock.setTotalQty(detail.getQty() * detail.getConversion());
                        globalStock.setTotalPbc(detail.getQty() * detail.getConversion());
                        globalStock.setType("Bill Return");
                        globalStock.setCreatedBy(userId);
                        globalStock.setVendorId(responses.get(0).getVendorId());
                        globalStock.setUnitCost(detail.getUnitCost());
                        insertStock(globalStock, 3L);
                    }
                }
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/bill-return/pick/{id}", null, null, "Bill Return", "Bill Return (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bill-return/pick/{id}", line, error.toString(), "Bill Return", "Bill Return (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> pay(PayBillReturnRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Bill Return (Aging)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }

            BillReturnReceipt receipt = new BillReturnReceipt();
            receipt.setBillReturnId(request.getBillReturnId());
            receipt.setExchangeRateId(request.getExchangeRateId());
            receipt.setBalance(request.getBalance());
            receipt.setBalanceOther(request.getBalanceOther());
            receipt.setTotalAmount(request.getTotalAmount());
            receipt.setCurrencyCenterId(request.getCurrencyCenterId());
            receipt.setChartAccountId(request.getChartAccountId());
            receipt.setAmountUsd(request.getPaidUsd());
            receipt.setAmountOther(request.getPaidOther());
            receipt.setDate(request.getDate());
            if (Objects.equals(request.getAging(), "")) {
                receipt.setAging(null);
            } else {
                receipt.setAging(request.getAging());
            }
            receipt.setCreatedBy(userId);
            //! Get the reference code
            {
                String code = billReturnMapper.getLastCodeReceipt();
                String incrementedString;

                if(code != null){
                    incrementedString = GenerateCode.incrementNumericPart(code);
                }else{
                    incrementedString = generateReference("PRC");
                }
                receipt.setReceiptCode(incrementedString);
            }

            Boolean result = billReturnMapper.insertReceipt(receipt);
            if (result) {
                billReturnMapper.updateBalance(request.getBillReturnId(), request.getBalance(), 3L);
                List<BillReturnResponse> billReturnResponse = billReturnMapper.getOne(request.getBillReturnId(), userId);
                if (billReturnResponse!= null && !billReturnResponse.isEmpty()) {
                    // Insert General Ledger
                    GeneralLedger generalLedger = new GeneralLedger();
                    generalLedger.setPurchaseReturnId(request.getBillReturnId());
                    generalLedger.setPurchaseReturnReceiptId(receipt.getId());
                    generalLedger.setDate(request.getDate());
                    generalLedger.setReference(billReturnResponse.get(0).getPrCode());
                    generalLedger.setCreatedBy(userId);
                    generalLedger.setIsSys(1);
                    billReturnMapper.insertGeneralLedger(generalLedger);

                    // Insert General Ledger Detail
                    GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                    generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetail.setType("Bill Return Payment");
                    generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                    generalLedgerDetail.setVendorId(billReturnResponse.get(0).getVendorId());
                    generalLedgerDetail.setCompanyId(billReturnResponse.get(0).getCompanyId());
                    generalLedgerDetail.setClassId(billReturnMapper.getClassByLocationGroupId(billReturnResponse.get(0).getCompanyId(), billReturnResponse.get(0).getWarehouseId()));
                    generalLedgerDetail.setMemo("ICS:  Bill Return Payment For PR # " + billReturnResponse.get(0).getPrCode());
                    generalLedgerDetail.setDebit(request.getPaidUsd());
                    generalLedgerDetail.setCredit(0D);
                    billReturnMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                    generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                    generalLedgerDetail.setType("Bill Return Payment");
                    generalLedgerDetail.setChartAccountId(request.getChartAccountId());
                    generalLedgerDetail.setVendorId(billReturnResponse.get(0).getVendorId());
                    generalLedgerDetail.setCompanyId(billReturnResponse.get(0).getCompanyId());
                    generalLedgerDetail.setClassId(billReturnMapper.getClassByLocationGroupId(billReturnResponse.get(0).getCompanyId(), billReturnResponse.get(0).getWarehouseId()));
                    generalLedgerDetail.setMemo("ICS:  Bill Return Payment For PR # " + billReturnResponse.get(0).getPrCode());
                    generalLedgerDetail.setDebit(0D);
                    generalLedgerDetail.setCredit(request.getPaidUsd());
                    billReturnMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                }
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/bill-return/pay", null, null, "Bill Return", "Bill Return (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bill-return/pay", line, error.toString(), "Bill Return", "Bill Return (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> applyPb(PayBillReturnWithPbsRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            System.out.println(userId);
             if (permissionMapper.checkPermission(userId, "Bill Return (Aging)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }
            double totalAmountPb = 0D;
            boolean result = false;
            System.out.println(request);
            BillReturnWithPbsReceipt receipt = new BillReturnWithPbsReceipt();
            receipt.setBillReturnId(request.getBillReturnId());
            receipt.setApplyDate(request.getApplyDate());
            receipt.setCreatedBy(userId);
            List<PayBillReturnWithPbsDetailRequest> details = request.getDetails();
            if (!details.isEmpty()) {
                for (int i = 0; i < details.size(); i++) {
                    receipt.setPurchaseBillId(details.get(i).getPurchaseBillId());
                    receipt.setTotalCost(details.get(i).getPaid());
                    billReturnMapper.updateBalancePurchaseOrder(details.get(i).getPurchaseBillId(), details.get(i).getPaid(),1L, userId);
                    totalAmountPb += details.get(i).getPaid();
                    billReturnMapper.insertReceiptWithPbs(receipt);
                }
                result = true;
            }
                if (result) {
                    List<BillReturnResponse> billReturnResponse = billReturnMapper.getOne(request.getBillReturnId(), userId);
                    if (billReturnResponse!= null && !billReturnResponse.isEmpty()) {
                        BillReturn billReturn = new BillReturn();
                        billReturn.setId(billReturnResponse.get(0).getId());
                        billReturn.setBalance(billReturnResponse.get(0).getBalance() - totalAmountPb);
                        billReturn.setTotal(billReturnResponse.get(0).getTotal() + totalAmountPb);
                        billReturn.setModifiedBy(userId);
                        billReturnMapper.updateBalancePurchaseReturn(billReturn);
                    }
                    /* System Activity */
                    LocalTime endDuration = LocalTime.now();
                    activityLogService.insert("/bill-return/apply-with-pb", null, null, "Bill Return", "Bill Return (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
                } else {
                    return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
                }
        }catch(Exception error){
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bill-return/apply-with-pb", line, error.toString(), "Bill Return", "Bill Return (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> voidReceipt(BillReturnReceiptFilter request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            System.out.println(userId);
             if (permissionMapper.checkPermission(userId, "Bill Return (Void)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }
            Boolean result;

            if (request.getType() == 1){
                 billReturnMapper.updateBalance(request.getBillReturnId(), request.getBalance(), 2L);
                 result = billReturnMapper.voidReceipt(request, userId);
            } else {
                billReturnMapper.updateBalance(request.getBillReturnId(), request.getBalance(), 2L);
                billReturnMapper.updateBalancePurchaseOrder(request.getPurchaseBillId(), request.getBalance(), 2L, userId);
                result = billReturnMapper.voidReceiptApplyPb(request, userId);
            }

            if (result) {
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/bill-return/void-receipt/{id}", null, null, "Bill Return", "Bill Return (Void)", "Void", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }

        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bill-return/void-receipt/{id}", line, error.toString(), "Bill Return", "Bill Return (Void)", "Void", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listReceipt(BillReturnReceiptFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Bill Return (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<BillReturnReceiptResponse> responses;
            if (filter.getType() == 1){
                responses = billReturnMapper.listReceipt(filter);
            } else {
                responses = billReturnMapper.listReceiptApplyPb(filter);
            }
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bill-return/list-receipt/{id}", null, null, "Bill Return", "Bill Return (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bill-return/list-receipt/{id}", line, error.toString(), "Bill Return", "Bill Return (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public static String generateReference(String input) {
        LocalDate currentDate = LocalDate.now();
        String yearLastTwoDigits = String.valueOf(currentDate.getYear()).substring(2);
        String result = yearLastTwoDigits + input + "0000001";
        return result;
    }

    private void insertStock(GlobalStock globalStock, Long typeOperation) {
        System.out.println(globalStock);
        String fieldToUpdate = "total_pbc";

        //! Insert Inventory Totals (All)
        transferConsignmentMapper.insertInventoryTotalAll(globalStock, typeOperation, fieldToUpdate);

        //! Insert Inventories (All)
        transferConsignmentMapper.insertInventoriesAll(globalStock, typeOperation);

        //! Insert Group Total By Warehouse
        String tblGroupTotal = globalStock.getWarehouseId() + "_group_totals";
        transferConsignmentMapper.insertGroupTotal(globalStock, tblGroupTotal, typeOperation);


        //!Insert Group Total Details By Warehouse
        String tblGroupTotalDetail = globalStock.getWarehouseId() + "_group_total_details";
        transferConsignmentMapper.insertGroupTotalDetail(globalStock, tblGroupTotalDetail, typeOperation, fieldToUpdate);


        //! Insert Inventory Totals By Location
        String tblInventoryTotal = globalStock.getLocationId() + "_inventory_totals";
        transferConsignmentMapper.insertInventoryTotal(globalStock, tblInventoryTotal, typeOperation);


        //! Insert Inventory Total Details By Location
        String tblInventoryTotalDetail = globalStock.getLocationId() + "_inventory_total_details";
        transferConsignmentMapper.insertInventoryTotalDetail(globalStock, tblInventoryTotalDetail, typeOperation, fieldToUpdate);


        //! Insert Inventories By Location
        String tblInventories = globalStock.getLocationId() + "_inventories";
        transferConsignmentMapper.insertInventories(globalStock, tblInventories, typeOperation);

        System.out.println("Done");

    }
}
