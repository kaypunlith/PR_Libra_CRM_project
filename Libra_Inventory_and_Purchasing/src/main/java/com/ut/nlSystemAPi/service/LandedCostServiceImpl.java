package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.LandedCostFilter;
import com.ut.nlSystemAPi.model.filter.PurchaseBillFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.request.Login.LandedCost.LandedCostDetailRequest;
import com.ut.nlSystemAPi.model.request.Login.LandedCost.LandedCostRequest;
import com.ut.nlSystemAPi.model.request.Login.LandedCost.LandedCostUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.PurchaseBill.PurchaseBillDetailRequest;
import com.ut.nlSystemAPi.model.request.Login.PurchaseBill.PurchaseBillRequest;
import com.ut.nlSystemAPi.model.request.Login.PurchaseBill.PurchaseBillUpdateRequest;
import com.ut.nlSystemAPi.model.response.LandedCost.LandedCostDetailResponse;
import com.ut.nlSystemAPi.model.response.LandedCost.LandedCostPurchaseBillResponse;
import com.ut.nlSystemAPi.model.response.LandedCost.LandedCostResponse;
import com.ut.nlSystemAPi.model.response.PurchaseBill.PurchaseBillResponse;
import com.ut.nlSystemAPi.model.response.PurchaseOrder.PurchaseOrderDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class LandedCostServiceImpl implements LandedCostService {

    @Autowired
    private LandedCostMapper landedCostMapper;

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
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private PurchaseBillMapper purchaseBillMapper;

    @Autowired
    private GenerateCode generateCode;

    @Autowired
    private CodeMapper codeMapper;

    public ResponseMessage<BaseResult> getList(LandedCostFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Landed Cost (View)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }
             if (permissionMapper.checkPermission(userId, "Landed Cost (View By User)") > 0) {
                 filter.setViewByUser(1L);
             }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(landedCostMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<LandedCostResponse> landedCostResponses = landedCostMapper.getList(filter, userId);

            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost/list", null, null, "Landed Cost", "Landed Cost (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", landedCostResponses, pagination, true));
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost/list", line, error.toString(), "Landed Cost", "Landed Cost (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Landed Cost (View)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }

            List<LandedCostResponse> landedCostResponses = landedCostMapper.getOne(id);

            if (!landedCostResponses.isEmpty()){
                for(int i = 0; i < landedCostResponses.size(); i++){
                    List<LandedCostDetailResponse> detailResponses = landedCostMapper.getDetail(landedCostResponses.get(i).getId());
                    landedCostResponses.get(i).setDetails(detailResponses);
                }

                for (int i = 0; i < landedCostResponses.size(); i++) {
                    List<LandedCostPurchaseBillResponse> landedCostPurchaseBills = landedCostMapper.getLandedCostPurchaseBill(landedCostResponses.get(i).getId());
                    landedCostResponses.get(i).setPurchaseBills(landedCostPurchaseBills);
                }
            }

            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost/find/{id}", null, null, "Landed Cost", "Landed Cost (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", landedCostResponses, true));
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost/find/{id}", line, error.toString(), "Landed Cost", "Landed Cost (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(LandedCostRequest landedCostRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Landed Cost (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check data
            LandedCost landedCost = new LandedCost();
            landedCost.setCompanyId(landedCostRequest.getCompanyId());
            landedCost.setDate(landedCostRequest.getDate());
            landedCost.setVendorId(landedCostRequest.getPaidToId());
            landedCost.setLandedCostTypeId(landedCostRequest.getLandedCostTypeId());
            landedCost.setReference(landedCostRequest.getReference());
            landedCost.setSupplyId(landedCostRequest.getSupplyId());
            landedCost.setApId(landedCostRequest.getApId());
            landedCost.setNote(landedCostRequest.getNote());
            landedCost.setTotalAmount(landedCostRequest.getTotalAmount());
            landedCost.setCreatedBy(userId);
            landedCost.setStatus(1);

            Boolean result = landedCostMapper.insert(landedCost);
            if (result) {

                //! Get the reference code
                String code = (generateCode.generateAutoCode("landed_costs", "code", 7, "LC", true, "status != -1"));
                codeMapper.updateCode("landed_costs", "code", code, landedCost.getId());

                for (int i = 0; i < landedCostRequest.getPurchaseBills().size(); i++) {
                    LandedCostPurchaseBill landedCostPurchaseBill = new LandedCostPurchaseBill();
                    landedCostPurchaseBill.setLandedCostId(landedCost.getId());
                    landedCostPurchaseBill.setPurchaseBillId(landedCostRequest.getPurchaseBills().get(i));
                    landedCostMapper.insertLandedCostPurchaseBill(landedCostPurchaseBill);
                }

                landedCost.setCode(landedCostMapper.getCode(landedCost.getId()));

                // Insert General Ledger
                GeneralLedger generalLedger = new GeneralLedger();
                generalLedger.setLandedCostId(landedCost.getId());
                generalLedger.setDate(landedCost.getDate());
                generalLedger.setReference(landedCost.getCode());
                generalLedger.setCreatedBy(userId);
                generalLedger.setIsSys(1);
                landedCostMapper.insertGeneralLedger(generalLedger);

                // General Ledger Detail A/P
                GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                generalLedgerDetail.setType("Landed Cost");
                generalLedgerDetail.setCompanyId(landedCost.getCompanyId());
                generalLedgerDetail.setChartAccountId(landedCost.getApId());
                generalLedgerDetail.setMemo("ICS:  Landed Cost # " + landedCost.getCode() + " - Account Payable");
                generalLedgerDetail.setDebit(0D);
                generalLedgerDetail.setCredit(landedCost.getTotalAmount());
                generalLedgerDetail.setVendorId(landedCost.getVendorId());
                landedCostMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                String landedCostTypeName = landedCostMapper.getLandedCostTypeName(landedCost.getLandedCostTypeId());
                    List<LandedCostDetailRequest> details = landedCostRequest.getDetails();
                    for (LandedCostDetailRequest detailRequest : details) {
                        if (detailRequest.getType() == 1) {
                            Long smallValUom = purchaseOrderMapper.getSmallValUom(detailRequest.getItemId());
                            // Insert details
                            LandedCostDetail landedCostDetail = new LandedCostDetail();
                            landedCostDetail.setLandedCostId(landedCost.getId());
                            landedCostDetail.setPurchaseBillDetailId(detailRequest.getPurchaseBillDetailId());
                            landedCostDetail.setProductId(detailRequest.getItemId());
                            landedCostDetail.setQty(detailRequest.getQty());
                            landedCostDetail.setUomId(detailRequest.getUomId());
                            landedCostDetail.setLandedCost(detailRequest.getLandedCost());
                            landedCostDetail.setUnitCost(detailRequest.getUnitCost());
                            landedCostDetail.setConversion(smallValUom / detailRequest.getConversion());
                            landedCostMapper.insertDetail(landedCostDetail);

                            String productName = landedCostMapper.getProductName(landedCostDetail.getProductId());
                            if (detailRequest.getLandedCost() > 0) {
                                // General Ledger Detail (Product Asset)
                                generalLedgerDetail = new GeneralLedgerDetail();
                                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                                generalLedgerDetail.setType("Landed Cost");
                                generalLedgerDetail.setChartAccountId(purchaseBillMapper.getProductIncomeChartAccountId(detailRequest.getItemId()));
                                generalLedgerDetail.setCompanyId(landedCost.getCompanyId());
                                generalLedgerDetail.setVendorId(landedCost.getVendorId());
                                generalLedgerDetail.setProductId(detailRequest.getItemId());
                                generalLedgerDetail.setMemo("ICS: Landed Cost # " + landedCost.getCode() + " - " + landedCostTypeName + ": " + productName);
                                generalLedgerDetail.setDebit(detailRequest.getLandedCost());
                                generalLedgerDetail.setCredit(0D);
                                landedCostMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                            }
                        }

                        if (detailRequest.getType() == 2) {
                            // Insert services
                            LandedCostServices landedCostServices = new LandedCostServices();
                            landedCostServices.setLandedCostId(landedCost.getId());
                            landedCostServices.setVendorId(detailRequest.getVendorId());
                            landedCostServices.setServiceId(detailRequest.getItemId());
                            landedCostServices.setQty(detailRequest.getQty());
                            landedCostServices.setUomId(1L);
                            landedCostServices.setSmallValUom(1D);
                            landedCostServices.setLandedCost(detailRequest.getLandedCost());
                            landedCostServices.setUnitCost(detailRequest.getUnitCost());
                            landedCostServices.setConversion(1L);
                            landedCostMapper.insertService(landedCostServices);

                            String serviceName = landedCostMapper.getServiceName(landedCostServices.getServiceId());
                            // General Ledger Detail A/P
                            generalLedgerDetail = new GeneralLedgerDetail();
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            generalLedgerDetail.setType("Landed Cost");
                            generalLedgerDetail.setChartAccountId(landedCost.getApId());
                            generalLedgerDetail.setCompanyId(landedCost.getCompanyId());
                            generalLedgerDetail.setVendorId(landedCost.getVendorId());
                            generalLedgerDetail.setProductId(detailRequest.getItemId());
                            generalLedgerDetail.setMemo("ICS: Landed Cost # " + landedCost.getCode() + " - Account Payable" );
                            generalLedgerDetail.setDebit(0D);
                            generalLedgerDetail.setCredit(detailRequest.getLandedCost());
                            landedCostMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                            if (detailRequest.getLandedCost() > 0) {
                                // General Ledger Detail (Product Asset)
                                generalLedgerDetail = new GeneralLedgerDetail();
                                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                                generalLedgerDetail.setType("Landed Cost");
                                generalLedgerDetail.setChartAccountId(purchaseBillMapper.getServiceChartAccountId(detailRequest.getItemId()));
                                generalLedgerDetail.setCompanyId(landedCost.getCompanyId());
                                generalLedgerDetail.setVendorId(landedCost.getVendorId());
                                generalLedgerDetail.setProductId(detailRequest.getItemId());
                                generalLedgerDetail.setMemo("ICS: Landed Cost # " + landedCost.getCode() + " - " + landedCostTypeName + ": " + serviceName);
                                generalLedgerDetail.setDebit(detailRequest.getLandedCost());
                                generalLedgerDetail.setCredit(0D);
                                landedCostMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                            }
                        }
                    }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/landed-cost/add", null, null, "Landed Cost", "Landed Cost (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost/add", line, error.toString(), "Landed Cost", "Landed Cost (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(LandedCostUpdateRequest landedCostUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Landed Cost (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check data
            LandedCost landedCost = new LandedCost();
            landedCost.setCompanyId(landedCostUpdateRequest.getCompanyId());
            landedCost.setCode(landedCostMapper.getCode(landedCostUpdateRequest.getId()));
            landedCost.setDate(landedCostUpdateRequest.getDate());
            landedCost.setVendorId(landedCostUpdateRequest.getPaidToId());
            landedCost.setLandedCostTypeId(landedCostUpdateRequest.getLandedCostTypeId());
            landedCost.setReference(landedCostUpdateRequest.getReference());
            landedCost.setSupplyId(landedCostUpdateRequest.getSupplyId());
            landedCost.setApId(landedCostUpdateRequest.getApId());
            landedCost.setNote(landedCostUpdateRequest.getNote());
            landedCost.setTotalAmount(landedCostUpdateRequest.getTotalAmount());
            landedCost.setModifiedBy(userId);
            landedCost.setStatus(1);
            landedCostMapper.archive(landedCostUpdateRequest.getId(), userId);

            Boolean result = landedCostMapper.update(landedCost);

            if (result) {
                for (int i = 0; i < landedCostUpdateRequest.getPurchaseBills().size(); i++) {
                    LandedCostPurchaseBill landedCostPurchaseBill = new LandedCostPurchaseBill();
                    landedCostPurchaseBill.setLandedCostId(landedCost.getId());
                    landedCostPurchaseBill.setPurchaseBillId(landedCostUpdateRequest.getPurchaseBills().get(i));
                    landedCostMapper.insertLandedCostPurchaseBill(landedCostPurchaseBill);
                }

                landedCostMapper.deleteGeneralLedger(landedCostUpdateRequest.getId());

                // Insert General Ledger
                GeneralLedger generalLedger = new GeneralLedger();
                generalLedger.setLandedCostId(landedCost.getId());
                generalLedger.setDate(landedCost.getDate());
                generalLedger.setReference(landedCost.getCode());
                generalLedger.setCreatedBy(userId);
                generalLedger.setIsSys(1);
                landedCostMapper.insertGeneralLedger(generalLedger);

                // General Ledger Detail A/P
                GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                generalLedgerDetail.setType("Landed Cost");
                generalLedgerDetail.setCompanyId(landedCost.getCompanyId());
                generalLedgerDetail.setChartAccountId(landedCost.getApId());
                generalLedgerDetail.setMemo("ICS:  Landed Cost # " + landedCost.getCode() + " - Account Payable");
                generalLedgerDetail.setDebit(0D);
                generalLedgerDetail.setVendorId(landedCost.getVendorId());
                generalLedgerDetail.setCredit(landedCost.getTotalAmount());
                landedCostMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                String landedCostTypeName = landedCostMapper.getLandedCostTypeName(landedCost.getLandedCostTypeId());
                List<LandedCostDetailRequest> details = landedCostUpdateRequest.getDetails();
                for (LandedCostDetailRequest detailRequest : details) {
                    if (detailRequest.getType() == 1) {
                        Long smallValUom = purchaseOrderMapper.getSmallValUom(detailRequest.getItemId());
                        // Insert details
                        LandedCostDetail landedCostDetail = new LandedCostDetail();
                        landedCostDetail.setLandedCostId(landedCost.getId());
                        landedCostDetail.setPurchaseBillDetailId(detailRequest.getPurchaseBillDetailId());
                        landedCostDetail.setProductId(detailRequest.getItemId());
                        landedCostDetail.setQty(detailRequest.getQty());
                        landedCostDetail.setUomId(detailRequest.getUomId());
                        landedCostDetail.setLandedCost(detailRequest.getLandedCost());
                        landedCostDetail.setUnitCost(detailRequest.getUnitCost());
                        landedCostDetail.setConversion(smallValUom / detailRequest.getConversion());
                        landedCostMapper.insertDetail(landedCostDetail);

                        String productName = landedCostMapper.getProductName(landedCostDetail.getProductId());
                        if (detailRequest.getLandedCost() > 0) {
                            // General Ledger Detail (Product Asset)
                            generalLedgerDetail = new GeneralLedgerDetail();
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            generalLedgerDetail.setType("Landed Cost");
                            generalLedgerDetail.setChartAccountId(purchaseBillMapper.getProductIncomeChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setCompanyId(landedCost.getCompanyId());
                            generalLedgerDetail.setVendorId(landedCost.getVendorId());
                            generalLedgerDetail.setProductId(detailRequest.getItemId());
                            generalLedgerDetail.setMemo("ICS: Landed Cost # " + landedCost.getCode() + " - " + landedCostTypeName + ": " + productName);
                            generalLedgerDetail.setDebit(detailRequest.getLandedCost());
                            generalLedgerDetail.setCredit(0D);
                            landedCostMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                        }
                    }

                    if (detailRequest.getType() == 2) {
                        // Insert services
                        LandedCostServices landedCostServices = new LandedCostServices();
                        landedCostServices.setLandedCostId(landedCost.getId());
                        landedCostServices.setVendorId(detailRequest.getVendorId());
                        landedCostServices.setServiceId(detailRequest.getItemId());
                        landedCostServices.setQty(detailRequest.getQty());
                        landedCostServices.setUomId(1L);
                        landedCostServices.setSmallValUom(1D);
                        landedCostServices.setLandedCost(detailRequest.getLandedCost());
                        landedCostServices.setUnitCost(detailRequest.getUnitCost());
                        landedCostServices.setConversion(1L);
                        landedCostMapper.insertService(landedCostServices);

                        String serviceName = landedCostMapper.getServiceName(landedCostServices.getServiceId());
                        // General Ledger Detail A/P
                        generalLedgerDetail = new GeneralLedgerDetail();
                        generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                        generalLedgerDetail.setType("Landed Cost");
                        generalLedgerDetail.setChartAccountId(landedCost.getApId());
                        generalLedgerDetail.setCompanyId(landedCost.getCompanyId());
                        generalLedgerDetail.setVendorId(landedCost.getVendorId());
                        generalLedgerDetail.setProductId(detailRequest.getItemId());
                        generalLedgerDetail.setMemo("ICS: Landed Cost # " + landedCost.getCode() + " - Account Payable" );
                        generalLedgerDetail.setDebit(0D);
                        generalLedgerDetail.setCredit(detailRequest.getLandedCost());
                        landedCostMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                        if (detailRequest.getLandedCost() > 0) {
                            // General Ledger Detail (Product Asset)
                            generalLedgerDetail = new GeneralLedgerDetail();
                            generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                            generalLedgerDetail.setType("Landed Cost");
                            generalLedgerDetail.setChartAccountId(purchaseBillMapper.getServiceChartAccountId(detailRequest.getItemId()));
                            generalLedgerDetail.setCompanyId(landedCost.getCompanyId());
                            generalLedgerDetail.setVendorId(landedCost.getVendorId());
                            generalLedgerDetail.setProductId(detailRequest.getItemId());
                            generalLedgerDetail.setMemo("ICS: Landed Cost # " + landedCost.getCode() + " - " + landedCostTypeName + ": " + serviceName);
                            generalLedgerDetail.setDebit(detailRequest.getLandedCost());
                            generalLedgerDetail.setCredit(0D);
                            landedCostMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                        }
                    }
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/landed-cost/update", null, null, "Landed Cost ", "Landed Cost (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost/update", line, error.toString(), "Landed Cost ", "Landed Cost (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Landed Cost (Void)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }

            Boolean result = landedCostMapper.delete(id, userId);
            if (result) {
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/landed-cost/delete/{id}", null, null, "Landed Cost", "Landed Cost (Void)", "Void", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost/delete/{id}", line, error.toString(), "Landed Cost", "Landed Cost (Void)", "Void", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> closeStatus(StatusFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Landed Cost (Close)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }

            Boolean result = landedCostMapper.closeStatus(filter, userId);

            if (result) {
                List<LandedCostDetailResponse> data = landedCostMapper.getData(filter.getId());
                if (!data.isEmpty()) {
                    for (int i = 0; i < data.size(); i++) {
                        //Update Inventory Valuation
                        Double newCost = data.get(i).getLandedCost() / data.get(i).getQty();
                        landedCostMapper.updateInventoryValuation(newCost, data.get(i).getPurchaseBillDetailId());

                        // Recalculate Average Cost
                        String orderDate = landedCostMapper.getOrderDate(data.get(i).getPurchaseBillId());
                        landedCostMapper.insertInventoryValuationCals(orderDate, data.get(i).getItemId());

                        // Update Product Cost
                        Double unitCost = data.get(i).getLandedCost() / ((data.get(i).getSmallValUom() / data.get(i).getConversion()) * data.get(i).getQty());
                        landedCostMapper.updateProductCost(unitCost, data.get(i).getItemId());
                    }
                }
                /* System Activity */
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/landed-cost/close/{id}", null, null, "Landed Cost", "Landed Cost (Close)", "Close", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /* System Activity */
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/landed-cost/close/{id}", line, error.toString(), "Landed Cost", "Landed Cost (Close)", "Close", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}


