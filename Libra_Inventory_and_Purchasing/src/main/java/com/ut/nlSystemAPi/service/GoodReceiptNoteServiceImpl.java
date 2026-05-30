package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.GoodReceiptNoteFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.request.Login.GoodReceiptNote.GoodReceiptNoteDetailRequest;
import com.ut.nlSystemAPi.model.request.Login.GoodReceiptNote.GoodReceiptNoteRequest;
import com.ut.nlSystemAPi.model.request.Login.GoodReceiptNote.GoodReceiptNoteUpdateRequest;
import com.ut.nlSystemAPi.model.response.GoodReceiptNote.*;
import com.ut.nlSystemAPi.model.response.InventoryAdjustment.InventoryAdjustmentDetailsResponse;
import com.ut.nlSystemAPi.model.response.InventoryAdjustment.InventoryAdjustmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class GoodReceiptNoteServiceImpl implements GoodReceiptNoteService {
    @Autowired
    private GoodReceiptNoteMapper goodReceiptNoteMapper;

    @Autowired
    private TransferConsignmentMapper transferConsignmentMapper;

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

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
    private CodeMapper codeMapper;
    @Autowired
    private PurchaseBillMapper purchaseBillMapper;

    @Override
    public ResponseMessage<BaseResult> getList(GoodReceiptNoteFilter goodReceiptNoteFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Goods Receive Notel (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(goodReceiptNoteFilter.getPage());
            pagination.setRowsPerPage(goodReceiptNoteFilter.getRowsPerPage());
            pagination.setTotal(goodReceiptNoteMapper.countList(goodReceiptNoteFilter, userId));

            goodReceiptNoteFilter.setPage((goodReceiptNoteFilter.getPage() - 1) * goodReceiptNoteFilter.getRowsPerPage());
            List<GoodReceiptNoteResponse> goodReceiptNoteResponses = goodReceiptNoteMapper.getList(goodReceiptNoteFilter, userId);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/good-receipt-note/list", null, null, "good-receipt-note", "Good Receipt Note (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", goodReceiptNoteResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/good-receipt-note/list", line, error.toString(), "good-receipt-note", "Good Receipt Note (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListVendor(GoodReceiptNoteFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Goods Receive Notel (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(goodReceiptNoteMapper.countList(filter, userId));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ListVendorResponse> goodReceiptNoteResponses = goodReceiptNoteMapper.getListVendor(filter);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/good-receipt-note/list", null, null, "good-receipt-note", "Good Receipt Note (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", goodReceiptNoteResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/good-receipt-note/list", line, error.toString(), "good-receipt-note", "Good Receipt Note (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Goods Receive Notel (View)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<GoodReceiptNoteResponse> goodReceiptNoteResponses = goodReceiptNoteMapper.getOne(id, userId);
            if(!goodReceiptNoteResponses.isEmpty()){
                for(int i=0;i<goodReceiptNoteResponses.size();i++){
                    List<GoodReceiptNoteDetailResponse> goodReceiptNoteDetailResponses = goodReceiptNoteMapper.getListReceiptDetail(goodReceiptNoteResponses.get(i).getId());
                    goodReceiptNoteResponses.get(i).setGoodReceiptNoteDetailResponses(goodReceiptNoteDetailResponses);
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/good-receipt-note/find/{id}", null, null, "Good Receipt Note", "Good Receipt Note (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", goodReceiptNoteResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/good-receipt-note/find/{id}", line, error.toString(), "Good Receipt Note", "Good Receipt Note (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(GoodReceiptNoteRequest goodReceiptNoteRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Goods Receive Notel (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            GoodReceiptNote goodReceiptNote = new GoodReceiptNote();
            goodReceiptNote.setCompanyId(goodReceiptNoteRequest.getCompanyId());
            goodReceiptNote.setLocationGroupId(goodReceiptNoteRequest.getLocationGroupId());
            goodReceiptNote.setLocationId(goodReceiptNoteRequest.getLocationId());
            goodReceiptNote.setVendorId(goodReceiptNoteRequest.getVendorId());
            goodReceiptNote.setPurchaseRequestId(goodReceiptNoteRequest.getPurchaseOrderId());
            goodReceiptNote.setDate(goodReceiptNoteRequest.getDate());
            goodReceiptNote.setNote(goodReceiptNoteRequest.getNote());
            goodReceiptNote.setCreatedBy(userId);
            goodReceiptNote.setStatus(1);

            Boolean result = goodReceiptNoteMapper.insert(goodReceiptNote);

            if (result) {

                //! Get the reference code
                String code = (generateCode.generateAutoCode("purchase_receive_results", "code", 7, "GRR", true, "status != -1"));
                codeMapper.updateCode("purchase_receive_results", "code", code, goodReceiptNote.getId());

                List<GoodReceiptNoteDetailRequest> goodReceiptNoteDetailRequests = goodReceiptNoteRequest.getGoodReceiptNoteDetailRequests();

                for (int i=0;i<goodReceiptNoteDetailRequests.size();i++){
                    // Calculate Qty Receive
                    Long qtyReceiveBefore = goodReceiptNoteMapper.getTotalQtyReceive(goodReceiptNoteDetailRequests.get(i).getProductId(),goodReceiptNoteRequest.getPurchaseOrderId());

                    if (qtyReceiveBefore == null) {
                        qtyReceiveBefore = 0L;
                    }
                    Long smallValUom = purchaseOrderMapper.getSmallValUom(goodReceiptNoteDetailRequests.get(i).getProductId());
                    GoodReceiptNoteDetail goodReceiptNoteDetail=new GoodReceiptNoteDetail();
                    goodReceiptNoteDetail.setPurchaseReceiveResultId(goodReceiptNote.getId());
                    goodReceiptNoteDetail.setProductId(goodReceiptNoteDetailRequests.get(i).getProductId());
                    goodReceiptNoteDetail.setQty(goodReceiptNoteDetailRequests.get(i).getQtyReceive());
                    goodReceiptNoteDetail.setQtyUomId(goodReceiptNoteDetailRequests.get(i).getUomId());
                    goodReceiptNoteDetail.setExpireDate(goodReceiptNoteDetailRequests.get(i).getExpireDate());
                    goodReceiptNoteDetail.setConversion(smallValUom / goodReceiptNoteDetailRequests.get(i).getConversion());
                    goodReceiptNoteDetail.setCreatedBy(userId);
                    goodReceiptNoteMapper.insertReceiptNoteDetail(goodReceiptNoteDetail);

                    Long totalQtyReceive = qtyReceiveBefore + goodReceiptNoteDetailRequests.get(i).getQtyReceive();

                    // Calculate Qty Order
                    Long qtyOrder = goodReceiptNoteDetailRequests.get(i).getQty();

                    if (qtyOrder != null) {
                        Long status = (totalQtyReceive.equals(qtyOrder)) ? 3L : 2L;
                        goodReceiptNoteMapper.updateStatusPurchaseOrder(status, goodReceiptNoteRequest.getPurchaseOrderId());
                    }

                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/good-receipt-note/add", null, null, "Good Receipt Note", "Good Receipt Note (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/good-receipt-note/add", line, error.toString(), "Good Receipt Note", "Good Receipt Note (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

  @Override
    public ResponseMessage<BaseResult> update(GoodReceiptNoteUpdateRequest goodReceiptNoteUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Goods Receive Notel (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            GoodReceiptNote goodReceiptNote = new GoodReceiptNote();
            goodReceiptNote.setCompanyId(goodReceiptNoteUpdateRequest.getCompanyId());
            goodReceiptNote.setCode(goodReceiptNoteMapper.getCode(goodReceiptNoteUpdateRequest.getId()));
            goodReceiptNote.setLocationGroupId(goodReceiptNoteUpdateRequest.getLocationGroupId());
            goodReceiptNote.setLocationId(goodReceiptNoteUpdateRequest.getLocationId());
            goodReceiptNote.setVendorId(goodReceiptNoteUpdateRequest.getVendorId());
            goodReceiptNote.setPurchaseRequestId(goodReceiptNoteUpdateRequest.getPurchaseOrderId());
            goodReceiptNote.setDate(goodReceiptNoteUpdateRequest.getDate());
            goodReceiptNote.setNote(goodReceiptNoteUpdateRequest.getNote());
            goodReceiptNote.setModifiedBy(userId);
            goodReceiptNoteMapper.archive(goodReceiptNoteUpdateRequest.getId(), userId);
            Boolean result = goodReceiptNoteMapper.update(goodReceiptNote);
            LocalTime endDuration = LocalTime.now();

            if (result) {
                List<GoodReceiptNoteDetailRequest> goodReceiptNoteDetailRequests = goodReceiptNoteUpdateRequest.getGoodReceiptNoteDetailRequests();

                for (int i=0;i<goodReceiptNoteDetailRequests.size();i++){
                    // Calculate Qty Receive
                    Long qtyReceiveBefore = goodReceiptNoteMapper.getTotalQtyReceive(goodReceiptNoteDetailRequests.get(i).getProductId(),goodReceiptNoteUpdateRequest.getPurchaseOrderId());

                    if (qtyReceiveBefore == null) {
                        qtyReceiveBefore = 0L;
                    }
                    Long smallValUom = purchaseOrderMapper.getSmallValUom(goodReceiptNoteDetailRequests.get(i).getProductId());
                    GoodReceiptNoteDetail goodReceiptNoteDetail=new GoodReceiptNoteDetail();
                    goodReceiptNoteDetail.setPurchaseReceiveResultId(goodReceiptNote.getId());
                    goodReceiptNoteDetail.setProductId(goodReceiptNoteDetailRequests.get(i).getProductId());
                    goodReceiptNoteDetail.setQty(goodReceiptNoteDetailRequests.get(i).getQtyReceive());
                    goodReceiptNoteDetail.setQtyUomId(goodReceiptNoteDetailRequests.get(i).getUomId());
                    goodReceiptNoteDetail.setExpireDate(goodReceiptNoteDetailRequests.get(i).getExpireDate());
                    goodReceiptNoteDetail.setConversion(smallValUom / goodReceiptNoteDetailRequests.get(i).getConversion());
                    goodReceiptNoteDetail.setCreatedBy(userId);
                    goodReceiptNoteMapper.insertReceiptNoteDetail(goodReceiptNoteDetail);

                    Long totalQtyReceive = qtyReceiveBefore + goodReceiptNoteDetailRequests.get(i).getQtyReceive();

                    // Calculate Qty Order
                    Long qtyOrder = goodReceiptNoteDetailRequests.get(i).getQty();

                    if (qtyOrder != null) {
                        Long status = (totalQtyReceive.equals(qtyOrder)) ? 3L : 2L;
                        goodReceiptNoteMapper.updateStatusPurchaseOrder(status, goodReceiptNoteUpdateRequest.getPurchaseOrderId());
                    }

                }
                activityLogService.insert("/good-receipt-note/update", null, null, "Goods Receive Notel", "Goods Receive Notel (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/good-receipt-note/update", line, error.toString(), "Goods Receive Notel", "Goods Receive Notel (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
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
              if (permissionMapper.checkPermission(userId, "Goods Receive Notel (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            Boolean result = goodReceiptNoteMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/good-receipt-note/delete/{id}",null,null,"good-receipt-note","Goods Receive Notel (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/good-receipt-note/delete/{id}",line, error.toString(),"good-receipt-note","Goods Receive Notel (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateApprove(StatusFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Goods Receive Notel (Approved)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            List<GoodReceiptNoteResponse> responses = goodReceiptNoteMapper.getOne(filter.getId(), userId);

            if (!responses.isEmpty()) {
                    List<GoodReceiptNoteDetailResponse> details = goodReceiptNoteMapper.getDetail(responses.get(0).getId());
                    if (!details.isEmpty()) {
                        for (GoodReceiptNoteDetailResponse detail : details) {
                            if (filter.getIsApproved() == 2) {
                                GlobalStock globalStock = new GlobalStock();
                                globalStock.setPurchaseReceiveResultId(responses.get(0).getId());
                                globalStock.setProductId(detail.getProductId());
                                globalStock.setLocationId(responses.get(0).getLocationId());
                                globalStock.setWarehouseId(responses.get(0).getWarehouseId());
                                globalStock.setLotsNumber(detail.getLotNo());
                                if (detail.getExpDate() == null) {
                                    globalStock.setExpiredDate("0000-00-00");
                                } else {
                                    globalStock.setExpiredDate(detail.getExpDate());
                                }
                                globalStock.setTotalQty(detail.getQty() * detail.getConversion());
                                globalStock.setTotalPb(detail.getQty() * detail.getConversion());
                                globalStock.setType("Good Receipt Note");
                                globalStock.setCreatedBy(userId);
                                globalStock.setUnitCost(detail.getUnitCost());
                                insertStock(globalStock, 1L);

                                Long calculateCog = purchaseBillMapper.getCalculateCog();
                                if (calculateCog != null && calculateCog == 1) {
                                    Long smallValUom = purchaseOrderMapper.getSmallValUom(detail.getProductId());
                                    Long conversion = detail.getConversion() == null || detail.getConversion() == 0 ? 1L : detail.getConversion();
                                    Double receiveCost = detail.getUnitCost() != null ? detail.getUnitCost() : 0D;
                                    String receiveDate = responses.get(0).getDate();
                                    String referenceCode = responses.get(0).getCode();

                                    double receivedQty = detail.getQty() != null ? detail.getQty() : 0D;
                                    double smallQty = receivedQty * conversion;
                                    double baseQtyDenominator = (double) (smallValUom == null || smallValUom == 0 ? 1L : smallValUom) / conversion;
                                    double qtyBase = baseQtyDenominator != 0 ? receivedQty / baseQtyDenominator : 0D;

                                    InventoryValuation inventoryValuation = new InventoryValuation();
                                    Long purchaseBillId = responses.get(0).getPurchaseBillId();

                                    inventoryValuation.setPurchaseBillId(purchaseBillId);
                                    inventoryValuation.setPurchaseBillDetailId(detail.getPurchaseOrderDetailId());
                                    inventoryValuation.setCompanyId(responses.get(0).getCompanyId());
                                    inventoryValuation.setType("Bill");
                                    inventoryValuation.setReference(referenceCode);
                                    inventoryValuation.setDate(receiveDate);
                                    inventoryValuation.setPid(detail.getProductId());
                                    inventoryValuation.setSmallQty(smallQty);
                                    inventoryValuation.setQty(qtyBase);
                                    inventoryValuation.setCost(receiveCost);

                                    purchaseBillMapper.insertInventoryValuation(inventoryValuation);
                                }
                            } else {
                                GlobalStock globalStock = new GlobalStock();
                                globalStock.setPurchaseReceiveResultId(responses.get(0).getId());
                                globalStock.setProductId(detail.getProductId());
                                globalStock.setLocationId(responses.get(0).getLocationId());
                                globalStock.setWarehouseId(responses.get(0).getWarehouseId());
                                globalStock.setLotsNumber(detail.getLotNo());
                                if (detail.getExpDate() == null) {
                                    globalStock.setExpiredDate("0000-00-00");
                                } else {
                                    globalStock.setExpiredDate(detail.getExpDate());
                                }
                                globalStock.setTotalQty(detail.getQty() * detail.getConversion());
                                globalStock.setTotalPb(detail.getQty() * detail.getConversion());
                                globalStock.setType("Good Receipt Note");
                                globalStock.setCreatedBy(userId);
                                globalStock.setVendorId(responses.get(0).getVendorId());
                                globalStock.setUnitCost(detail.getUnitCost());
                                insertStock(globalStock, 2L);
                            }
                        }
                    }
            }

            Boolean result = goodReceiptNoteMapper.updateApprove(filter, userId);

            if (result){
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/good-receipt-note/update-approve/{id}",null,null,"Goods Receive Notel","Goods Receive Notel (Approve)","Approve",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/good-receipt-note/update-approve/{id}",line, error.toString(),"Goods Receive Notel","Goods Receive Notel (Approve)","Approve",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private void insertStock(GlobalStock globalStock, Long typeOperation) {
        String fieldToUpdate = "total_pb";

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


        //! Insert Inventory Totals (All)
        transferConsignmentMapper.insertInventoryTotalAll(globalStock, typeOperation, fieldToUpdate);


        //! Insert Inventories (All)
        transferConsignmentMapper.insertInventoriesAll(globalStock, typeOperation);

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
        LocalDate currentDate = LocalDate.now();
        String yearLastTwoDigits = String.valueOf(currentDate.getYear()).substring(2);
        String result = yearLastTwoDigits + input + "0000001";
        return result;
    }


}
