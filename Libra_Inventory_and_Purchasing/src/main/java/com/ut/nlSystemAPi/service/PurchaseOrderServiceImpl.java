package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PurchaseOrderFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.request.Login.PurchaseOrder.PurchaseOrderDetailRequest;
import com.ut.nlSystemAPi.model.request.Login.PurchaseOrder.PurchaseOrderRequest;
import com.ut.nlSystemAPi.model.request.Login.PurchaseOrder.PurchaseOrderUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.VendorManagement.VendorPhoto;
import com.ut.nlSystemAPi.model.response.PurchaseOrder.PurchaseOrderDetailResponse;
import com.ut.nlSystemAPi.model.response.PurchaseOrder.PurchaseOrderResponse;
import com.ut.nlSystemAPi.model.response.PurchaseOrder.SoNoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

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
    private CodeMapper codeMapper;
    @Autowired
    private GenerateCode generateCode;

    public ResponseMessage<BaseResult> getList(PurchaseOrderFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Purchase Order (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(purchaseOrderMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<PurchaseOrderResponse> purchaseOrderResponses = purchaseOrderMapper.getList(filter);
            System.out.println(purchaseOrderResponses);
            if (!purchaseOrderResponses.isEmpty()) {
                for (int i = 0; i < purchaseOrderResponses.size(); i++) {
                    VendorPhoto refDoc = purchaseOrderMapper.getRefDoc(purchaseOrderResponses.get(i).getId());
                    purchaseOrderResponses.get(i).setRefDoc(refDoc);
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-order/list", null, null, "Purchase Order", "Purchase Order (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", purchaseOrderResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-order/list", line, error.toString(), "Purchase Order", "Purchase Order (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Purchase Order (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<PurchaseOrderResponse> purchaseOrderResponses = purchaseOrderMapper.getOne(id);

            if (!purchaseOrderResponses.isEmpty()) {
                for (int i = 0; i < purchaseOrderResponses.size(); i++) {
                    VendorPhoto refDoc = purchaseOrderMapper.getRefDoc(purchaseOrderResponses.get(i).getId());
                    purchaseOrderResponses.get(i).setRefDoc(refDoc);

//                    List<SoNoResponse> soNo = purchaseOrderMapper.getOrder(purchaseOrderResponses.get(i).getId());
//                    purchaseOrderResponses.get(i).setSoNo(soNo);

                    List<PurchaseOrderDetailResponse> detailResponses = purchaseOrderMapper.getDetail(purchaseOrderResponses.get(i).getId());
                    purchaseOrderResponses.get(i).setDetails(detailResponses);
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-order/find/{id}", null, null, "Purchase Order", "Purchase Order (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", purchaseOrderResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-order/find/{id}", line, error.toString(), "Purchase Order", "Purchase Order (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(PurchaseOrderRequest purchaseOrderRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Purchase Order (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setCompanyId(purchaseOrderRequest.getCompanyId());
            purchaseOrder.setVendorId(purchaseOrderRequest.getVendorId());
            purchaseOrder.setOrderDate(purchaseOrderRequest.getPoDate());
            purchaseOrder.setDeliveryLeadTime(purchaseOrderRequest.getDeliveryLeadTime());
            purchaseOrder.setVendorId(purchaseOrderRequest.getVendorId());
            purchaseOrder.setVendorContactId(purchaseOrderRequest.getVendorContactId());
            purchaseOrder.setPaymentId(purchaseOrderRequest.getPaymentId());
            purchaseOrder.setExchangeRateId(purchaseOrderRequest.getExchangeRateId());
            purchaseOrder.setCurrencyCenterId(purchaseOrderRequest.getCurrencyCenterId());
            if (purchaseOrderRequest.getRefDoc() != null) {
                purchaseOrder.setRefDoc(purchaseOrderRequest.getRefDoc().getUrl());
                purchaseOrder.setRefDocName(purchaseOrderRequest.getRefDoc().getName());
            }
            purchaseOrder.setNote(purchaseOrderRequest.getNote());
            purchaseOrder.setFinalPlaceDeliveryId(purchaseOrderRequest.getDeliveryTo());
            purchaseOrder.setRefQuotation(purchaseOrderRequest.getRefSaleOrder());
            purchaseOrder.setPartOfDischargeId(purchaseOrderRequest.getPartOfDischargeId());
            purchaseOrder.setPartOfDischargeContactId(purchaseOrderRequest.getPartOfDischargeContactId());
            purchaseOrder.setShipmentId(purchaseOrderRequest.getShipmentId());
            purchaseOrder.setShipTo(purchaseOrderRequest.getShipTo());
            purchaseOrder.setContactShipTo(purchaseOrderRequest.getContactShipTo());
            purchaseOrder.setExpectedDeliveryDate(purchaseOrderRequest.getExpectedDeliveryDate());
            purchaseOrder.setTotalAmount(purchaseOrderRequest.getSubTotal());
            purchaseOrder.setVatSettingId(purchaseOrderRequest.getVatSettingId());
            purchaseOrder.setTotalVat(purchaseOrderRequest.getTotalVat());
            purchaseOrder.setVatPercentage(purchaseOrderRequest.getVatPercentage());
            purchaseOrder.setCreatedBy(userId);
            purchaseOrder.setIsClose(0);

            Boolean result = purchaseOrderMapper.insert(purchaseOrder);

            List<Long> soNo = purchaseOrderRequest.getSoNo();

            if (soNo != null && !soNo.isEmpty()) {
                PurchaseOrderOrder purchaseOrderOrder = new PurchaseOrderOrder();
                for (int i = 0; i < soNo.size(); i++) {
                    purchaseOrderOrder.setPurchaseOrderId(purchaseOrder.getId());
                    purchaseOrderOrder.setOrderId(soNo.get(i));
                    purchaseOrderMapper.insertOrder(purchaseOrderOrder);
                }
            }

            if (result) {

                //! Get the reference code
                String code = (generateCode.generateAutoCode("purchase_requests", "pr_code", 7, "PO", true, "status != -1"));
                codeMapper.updateCode("purchase_requests", "pr_code", code, purchaseOrder.getId());

                List<PurchaseOrderDetailRequest> details = purchaseOrderRequest.getDetails();
                if (details != null && !details.isEmpty()) {
                    for (int i = 0; i < details.size(); i++) {
                        if (details.get(i).getType() == 1) {
                            System.out.println("id: " + details.get(i).getItemId());
                            Long smallValUom = purchaseOrderMapper.getSmallValUom(details.get(i).getItemId());
                            System.out.println("small: " + smallValUom);
                            PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
                            purchaseOrderDetail.setPurchaseRequestId(purchaseOrder.getId());
                            purchaseOrderDetail.setProductId(details.get(i).getItemId());
                            purchaseOrderDetail.setUomId(details.get(i).getUomId());
                            purchaseOrderDetail.setConversion(smallValUom / details.get(i).getConversion());
                            System.out.println("conversion " + purchaseOrderDetail.getConversion());
                            purchaseOrderDetail.setNote(details.get(i).getNote());
                            purchaseOrderDetail.setQty(details.get(i).getQty());
                            purchaseOrderDetail.setUnitCost(details.get(i).getUnitCost());
                            purchaseOrderDetail.setTotalCost(details.get(i).getTotalCost());
                            purchaseOrderMapper.insertDetail(purchaseOrderDetail);

                        } else if (details.get(i).getType() == 2) {
                            PurchaseOrderServices purchaseOrderServices = new PurchaseOrderServices();
                            purchaseOrderServices.setPurchaseRequestId(purchaseOrder.getId());
                            purchaseOrderServices.setServiceId(details.get(i).getItemId());
                            purchaseOrderServices.setNote(details.get(i).getNote());
                            purchaseOrderServices.setQty(details.get(i).getQty());
                            purchaseOrderServices.setUnitCost(details.get(i).getUnitCost());
                            purchaseOrderServices.setTotalCost(details.get(i).getTotalCost());
                            purchaseOrderMapper.insertService(purchaseOrderServices);
                        }
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/purchase-order/add", null, null, "Warehouse", "Warehouse (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-order/add", line, error.toString(), "Warehouse", "Warehouse (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(PurchaseOrderUpdateRequest purchaseOrderUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Purchase Order (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setCompanyId(purchaseOrderUpdateRequest.getCompanyId());
            purchaseOrder.setPrCode(purchaseOrderMapper.getPrCode(purchaseOrderUpdateRequest.getId()));
            purchaseOrder.setVendorId(purchaseOrderUpdateRequest.getVendorId());
            purchaseOrder.setOrderDate(purchaseOrderUpdateRequest.getPoDate());
            purchaseOrder.setDeliveryLeadTime(purchaseOrderUpdateRequest.getDeliveryLeadTime());
            purchaseOrder.setVendorId(purchaseOrderUpdateRequest.getVendorId());
            purchaseOrder.setVendorContactId(purchaseOrderUpdateRequest.getVendorContactId());
            purchaseOrder.setPaymentId(purchaseOrderUpdateRequest.getPaymentId());
            purchaseOrder.setExchangeRateId(purchaseOrderUpdateRequest.getExchangeRateId());
            purchaseOrder.setCurrencyCenterId(purchaseOrderUpdateRequest.getCurrencyCenterId());
            if (purchaseOrderUpdateRequest.getRefDoc() != null) {
                purchaseOrder.setRefDoc(purchaseOrderUpdateRequest.getRefDoc().getUrl());
                purchaseOrder.setRefDocName(purchaseOrderUpdateRequest.getRefDoc().getName());
            }
            purchaseOrder.setNote(purchaseOrderUpdateRequest.getNote());
            purchaseOrder.setFinalPlaceDeliveryId(purchaseOrderUpdateRequest.getDeliveryTo());
            purchaseOrder.setRefQuotation(purchaseOrderUpdateRequest.getRefSaleOrder());
            purchaseOrder.setPartOfDischargeId(purchaseOrderUpdateRequest.getPartOfDischargeId());
            purchaseOrder.setPartOfDischargeContactId(purchaseOrderUpdateRequest.getPartOfDischargeContactId());
            purchaseOrder.setShipmentId(purchaseOrderUpdateRequest.getShipmentId());
            purchaseOrder.setShipTo(purchaseOrderUpdateRequest.getShipTo());
            purchaseOrder.setContactShipTo(purchaseOrderUpdateRequest.getContactShipTo());
            purchaseOrder.setExpectedDeliveryDate(purchaseOrderUpdateRequest.getExpectedDeliveryDate());
            purchaseOrder.setTotalAmount(purchaseOrderUpdateRequest.getSubTotal());
            purchaseOrder.setVatSettingId(purchaseOrderUpdateRequest.getVatSettingId());
            purchaseOrder.setTotalVat(purchaseOrderUpdateRequest.getTotalVat());
            purchaseOrder.setVatPercentage(purchaseOrderUpdateRequest.getVatPercentage());
            purchaseOrder.setModifiedBy(userId);
            purchaseOrderMapper.archive(purchaseOrderUpdateRequest.getId(), userId);
            Boolean result = purchaseOrderMapper.update(purchaseOrder);

            List<Long> soNo = purchaseOrderUpdateRequest.getSoNo();
            if (soNo != null && !soNo.isEmpty()) {
                PurchaseOrderOrder purchaseOrderOrder = new PurchaseOrderOrder();
                for (int i = 0; i < soNo.size(); i++) {
                    purchaseOrderOrder.setPurchaseOrderId(purchaseOrder.getId());
                    purchaseOrderOrder.setOrderId(soNo.get(i));
                    purchaseOrderMapper.insertOrder(purchaseOrderOrder);
                }
            }

            if (result) {
                List<PurchaseOrderDetailRequest> details = purchaseOrderUpdateRequest.getDetails();
                if (details != null && !details.isEmpty()) {
                    for (int i = 0; i < details.size(); i++) {
                        if (details.get(i).getType() == 1) {
                            System.out.println("id: " + details.get(i).getItemId());
                            Long smallValUom = purchaseOrderMapper.getSmallValUom(details.get(i).getItemId());
                            System.out.println("small: " + smallValUom);
                            PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
                            purchaseOrderDetail.setPurchaseRequestId(purchaseOrder.getId());
                            purchaseOrderDetail.setProductId(details.get(i).getItemId());
                            purchaseOrderDetail.setUomId(details.get(i).getUomId());
                            purchaseOrderDetail.setConversion(smallValUom / details.get(i).getConversion());
                            System.out.println("conversion " + purchaseOrderDetail.getConversion());
                            purchaseOrderDetail.setNote(details.get(i).getNote());
                            purchaseOrderDetail.setQty(details.get(i).getQty());
                            purchaseOrderDetail.setUnitCost(details.get(i).getUnitCost());
                            purchaseOrderDetail.setTotalCost(details.get(i).getTotalCost());
                            System.out.println(purchaseOrderDetail);
                            purchaseOrderMapper.insertDetail(purchaseOrderDetail);

                        } else if (details.get(i).getType() == 2) {
                            PurchaseOrderServices purchaseOrderServices = new PurchaseOrderServices();
                            purchaseOrderServices.setPurchaseRequestId(purchaseOrder.getId());
                            purchaseOrderServices.setServiceId(details.get(i).getItemId());
                            purchaseOrderServices.setNote(details.get(i).getNote());
                            purchaseOrderServices.setQty(details.get(i).getQty());
                            purchaseOrderServices.setUnitCost(details.get(i).getUnitCost());
                            purchaseOrderServices.setTotalCost(details.get(i).getTotalCost());
                            purchaseOrderMapper.insertService(purchaseOrderServices);
                        }
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/purchase-order/update", null, null, "Purchase Order", "Purchase Order (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-order/update", line, error.toString(), "Purchase Order", "Purchase Order (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Purchase Order (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = purchaseOrderMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/purchase-order/delete/{id}", null, null, "Purchase Order", "Purchase Order (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-order/delete/{id}", line, error.toString(), "Purchase Order", "Purchase Order (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "Purchase Order (Close)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = purchaseOrderMapper.closeStatus(filter, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/purchase-order/close-status", null, null, "Purchase Order", "Purchase Order (Close)", "Close", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-order/close-status", line, error.toString(), "Purchase Order", "Purchase Order (Close)", "Close", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}