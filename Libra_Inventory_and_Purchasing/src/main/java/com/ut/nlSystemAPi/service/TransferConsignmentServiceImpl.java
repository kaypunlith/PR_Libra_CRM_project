package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.StockOrders;
import com.ut.nlSystemAPi.model.TransferConsignment;
import com.ut.nlSystemAPi.model.TransferConsignmentDetail;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ProductTransferConsignmentFilter;
import com.ut.nlSystemAPi.model.filter.TransferConsignmentFilter;
import com.ut.nlSystemAPi.model.request.Login.TransferConsignment.TransferConsignmentDetailRequest;
import com.ut.nlSystemAPi.model.request.Login.TransferConsignment.TransferConsignmentRequest;
import com.ut.nlSystemAPi.model.request.Login.TransferConsignment.TransferConsignmentUpdateRequest;
import com.ut.nlSystemAPi.model.response.TransferConsignment.TransferConsignmentLocationResponse;
import com.ut.nlSystemAPi.model.response.TransferConsignment.ProductConsignmentDetailResponse;
import com.ut.nlSystemAPi.model.response.TransferConsignment.TransferConsignmentDetailResponse;
import com.ut.nlSystemAPi.model.response.TransferConsignment.TransferConsignmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransferConsignmentServiceImpl implements TransferConsignmentService {

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
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private CodeMapper codeMapper;
    @Autowired
    private GenerateCode generateCode;

  public ResponseMessage<BaseResult> getList(TransferConsignmentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Transfer Order (View)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }
      if (permissionMapper.checkPermission(userId, "Transfer Order (View By User)") > 0) {
        filter.setViewByUser(1L);
      }

      Pagination pagination = new Pagination();
      pagination.setPage(filter.getPage());
      pagination.setRowsPerPage(filter.getRowsPerPage());
      pagination.setTotal(transferConsignmentMapper.countList(filter, userId));
      filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

      List<TransferConsignmentResponse> transferConsignmentResponses = transferConsignmentMapper.getList(filter, userId);
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/warehouse/list",null,null,"Transfer Order","Transfer Order (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", transferConsignmentResponses, pagination, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/warehouse/list",line, error.toString(),"Transfer Order","Transfer Order (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Transfer Order (View)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      List<TransferConsignmentResponse> transferConsignmentResponses = transferConsignmentMapper.getOne(id);

      if (transferConsignmentResponses.size() > 0){
        for(int i = 0; i < transferConsignmentResponses.size(); i++){
          List<TransferConsignmentDetailResponse> detailResponses = transferConsignmentMapper.getTransferConsignmentDetail(transferConsignmentResponses.get(i).getId());
          transferConsignmentResponses.get(i).setDetails(detailResponses);
        }
      }

      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/role/find/{id}",null,null,"Transfer Order","Transfer Order (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", transferConsignmentResponses, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/role/find/{id}",line, error.toString(),"Transfer Order","Transfer Order (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> insert(TransferConsignmentRequest transferConsignmentRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
     // Check Permission
     Long userId = userService.getUserAuth().getId();
     if (permissionMapper.checkPermission(userId, "Transfer Order (Add)") == 0) {
       return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
     }
      
          String tableName = transferConsignmentRequest.getFromWarehouseId() + "_group_totals";

          if(transferConsignmentRequest.getDetails().size() > 0){
            for (int i = 0; i < transferConsignmentRequest.getDetails().size(); i++) {
              Long productId = transferConsignmentRequest.getDetails().get(i).getProductId();

              Double totalQtyStock = transferConsignmentMapper.getTotalQtyStock(transferConsignmentRequest.getFromWarehouseId(), tableName, productId);

              // Total remaining stock
              if(totalQtyStock == null) {
                totalQtyStock = 0D;
              }

              // if stock smaller than 0 it no stock
              if(totalQtyStock < transferConsignmentRequest.getDetails().get(i).getQty()){
                return ResponseMessageUtils.makeResponseByPermission(false, messageService.message("Product no stock!", false));
              }
            }
          }

      TransferConsignment transferConsignment = new TransferConsignment();
      transferConsignment.setRequestStockId(transferConsignmentRequest.getRequestStockId());
      transferConsignment.setCompanyId(transferConsignmentRequest.getCompanyId());
      transferConsignment.setFromWarehouseId(transferConsignmentRequest.getFromWarehouseId());
      transferConsignment.setToWarehouseId(transferConsignmentRequest.getToWarehouseId());
      transferConsignment.setToDate(transferConsignmentRequest.getToDate());
      transferConsignment.setFulfillmentDate(transferConsignmentRequest.getFulfillmentDate());
      transferConsignment.setMemo(transferConsignmentRequest.getMemo());
      transferConsignment.setCreatedBy(userId);
      transferConsignment.setStatus(1);
      transferConsignment.setType(transferConsignmentRequest.getType());
      transferConsignment.setToType(transferConsignmentRequest.getToType());

      Boolean result = transferConsignmentMapper.insert(transferConsignment);

      if (result) {
        if (transferConsignment.getToType() == 1) {
          //! Get the reference code
          String code = (generateCode.generateAutoCode("transfer_orders", "to_code", 7, "TO", true, "(status >= 0 OR status = -2)"));
          codeMapper.updateCode("transfer_orders", "to_code", code, transferConsignment.getId());

        } else if (transferConsignment.getToType() == 2) {
          //! Get the reference code
          String code = (generateCode.generateAutoCode("transfer_orders", "to_code", 7, "FTO", false, "(status >= 0 OR status = -2)"));
          codeMapper.updateCode("transfer_orders", "to_code", code, transferConsignment.getId());
        }

        if (transferConsignmentRequest.getDetails() != null && !transferConsignmentRequest.getDetails().isEmpty()) {
          List<TransferConsignmentDetailRequest> transferConsignmentDetailRequests = transferConsignmentRequest.getDetails();

          for (int i = 0; i< transferConsignmentDetailRequests.size(); i++) {
            Long smallValUom = purchaseOrderMapper.getSmallValUom(transferConsignmentDetailRequests.get(i).getProductId());
            System.out.println(smallValUom);
            TransferConsignmentDetail transferConsignmentDetail = new TransferConsignmentDetail();
            transferConsignmentDetail.setTransferOrderId(transferConsignment.getId());
            transferConsignmentDetail.setPoNumber(transferConsignmentDetailRequests.get(i).getPoNumber());
            transferConsignmentDetail.setProductId(transferConsignmentDetailRequests.get(i).getProductId());
            transferConsignmentDetail.setExpiredDate(transferConsignmentDetailRequests.get(i).getExpiredDate());
            transferConsignmentDetail.setFromLocationId(transferConsignmentDetailRequests.get(i).getFromLocationId());
            transferConsignmentDetail.setToLocationId(transferConsignmentDetailRequests.get(i).getToLocationId());
            transferConsignmentDetail.setQty(transferConsignmentDetailRequests.get(i).getQty());
            transferConsignmentDetail.setUomId(transferConsignmentDetailRequests.get(i).getUomId());
            System.out.println(transferConsignmentDetailRequests.get(i).getConversion());
            transferConsignmentDetail.setConversion(smallValUom / transferConsignmentDetailRequests.get(i).getConversion());
            transferConsignmentMapper.insertTransferConsignmentDetail(transferConsignmentDetail);

            //! Insert stock order
            StockOrders stockOrders = new StockOrders();
            stockOrders.setTransferOrderId(transferConsignment.getId());
            stockOrders.setProductId(transferConsignmentDetail.getProductId());
            stockOrders.setWarehouseFromId(transferConsignment.getFromWarehouseId());
            stockOrders.setLocationFromId(transferConsignmentDetail.getFromLocationId());
            stockOrders.setLotsNumber(0L);
            stockOrders.setExpireDate(transferConsignmentDetail.getExpiredDate());
            stockOrders.setQty(transferConsignmentDetail.getQty());
            stockOrders.setConversion(transferConsignmentDetail.getConversion());
            stockOrders.setTotalOrder(transferConsignmentDetail.getQty());
            transferConsignmentMapper.insertStockOrder(stockOrders);
          }
        }

        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/warehouse/add",null,null,"Warehouse","Warehouse (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", null, false));
      }
   } catch (Exception error) {
     /*System Activity*/
     LocalTime endDuration = LocalTime.now();
     activityLogService.insert("/warehouse/add",line, error.toString(),"Warehouse","Warehouse (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
     return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
   }
  }

  public ResponseMessage<BaseResult> update(TransferConsignmentUpdateRequest transferConsignmentUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Transfer Order (Edit)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      // Check Data
      TransferConsignment transferConsignment = new TransferConsignment();
      transferConsignment.setRequestStockId(transferConsignmentUpdateRequest.getRequestStockId());
      transferConsignment.setToNumber(transferConsignmentMapper.getCode(transferConsignmentUpdateRequest.getId()));
      transferConsignment.setCompanyId(transferConsignmentUpdateRequest.getCompanyId());
      transferConsignment.setFromWarehouseId(transferConsignmentUpdateRequest.getFromWarehouseId());
      transferConsignment.setToWarehouseId(transferConsignmentUpdateRequest.getToWarehouseId());
      transferConsignment.setToDate(transferConsignmentUpdateRequest.getToDate());
      transferConsignment.setFulfillmentDate(transferConsignmentUpdateRequest.getFulfillmentDate());
      transferConsignment.setMemo(transferConsignmentUpdateRequest.getMemo());
      transferConsignment.setModifiedBy(userId);
      transferConsignment.setStatus(1);
      transferConsignment.setType(transferConsignmentUpdateRequest.getType());
      transferConsignment.setToType(transferConsignmentUpdateRequest.getToType());
      transferConsignmentMapper.archive(transferConsignmentUpdateRequest.getId(), userId);
      Boolean result = transferConsignmentMapper.update(transferConsignment);
      if (result) {
        if (transferConsignmentUpdateRequest.getDetails() != null && !transferConsignmentUpdateRequest.getDetails().isEmpty()) {
          List<TransferConsignmentDetailRequest> transferConsignmentDetailRequests = transferConsignmentUpdateRequest.getDetails();

          for (int i = 0; i< transferConsignmentDetailRequests.size(); i++) {
            Long smallValUom = purchaseOrderMapper.getSmallValUom(transferConsignmentDetailRequests.get(i).getProductId());
            System.out.println(smallValUom);
            TransferConsignmentDetail transferConsignmentDetail = new TransferConsignmentDetail();
            transferConsignmentDetail.setTransferOrderId(transferConsignment.getId());
            transferConsignmentDetail.setPoNumber(transferConsignmentDetailRequests.get(i).getPoNumber());
            transferConsignmentDetail.setProductId(transferConsignmentDetailRequests.get(i).getProductId());
            transferConsignmentDetail.setExpiredDate(transferConsignmentDetailRequests.get(i).getExpiredDate());
            transferConsignmentDetail.setFromLocationId(transferConsignmentDetailRequests.get(i).getFromLocationId());
            transferConsignmentDetail.setToLocationId(transferConsignmentDetailRequests.get(i).getToLocationId());
            transferConsignmentDetail.setQty(transferConsignmentDetailRequests.get(i).getQty());
            transferConsignmentDetail.setUomId(transferConsignmentDetailRequests.get(i).getUomId());
            System.out.println(transferConsignmentDetailRequests.get(i).getConversion());
            transferConsignmentDetail.setConversion(smallValUom / transferConsignmentDetailRequests.get(i).getConversion());
            transferConsignmentMapper.insertTransferConsignmentDetail(transferConsignmentDetail);

            //! Insert stock order
            StockOrders stockOrders = new StockOrders();
            stockOrders.setTransferOrderId(transferConsignment.getId());
            stockOrders.setProductId(transferConsignmentDetail.getProductId());
            stockOrders.setWarehouseFromId(transferConsignment.getFromWarehouseId());
            stockOrders.setLocationFromId(transferConsignmentDetail.getFromLocationId());
            stockOrders.setLotsNumber(0L);
            stockOrders.setExpireDate(transferConsignmentDetail.getExpiredDate());
            stockOrders.setQty(transferConsignmentDetail.getQty());
            stockOrders.setConversion(transferConsignmentDetail.getConversion());
            stockOrders.setTotalOrder(transferConsignmentDetail.getQty());
            transferConsignmentMapper.insertStockOrder(stockOrders);
          }
        }
        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/role/update",null,null,"Transfer Order","Transfer Order (Edit)","Edit",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/role/update",line, error.toString(),"Transfer Order","Transfer Order (Edit)","Edit",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Transfer Order (Delete)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      Boolean result = transferConsignmentMapper.delete(id, userId);
      if (result) {
        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/role/delete/{id}",null,null,"Transfer Order","Transfer Order (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/role/delete/{id}",line, error.toString(),"Transfer Order","Transfer Order (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  @Override
  public ResponseMessage<BaseResult> approve(Long id, Long isApprove, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Transfer Order (Edit)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      Boolean result = transferConsignmentMapper.approve(id, isApprove, userId);

      if (result) {
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/transfer-consignment/approve/{id}", null, null, "Transfer Order", "Transfer Order (Approve)", "Approve", 1, "Success", startDuration, endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/transfer-consignment/approve/{id}", line, error.toString(), "Transfer Order", "Transfer Order (Approve)", "Approve", 2, "Error", startDuration, endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  @Override
  public ResponseMessage<BaseResult> listProduct(ProductTransferConsignmentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
//             Check Permission
      Long userId = userService.getUserAuth().getId();
//      if (permissionMapper.checkPermission(userId, "Transfer Order (View)") == 0) {
//        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//      }
      Pagination pagination = new Pagination();
      pagination.setPage(filter.getPage());
      pagination.setRowsPerPage(filter.getRowsPerPage());
      pagination.setTotal(transferConsignmentMapper.countListProduct(filter));

      filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

      String tableName = filter.getDepartmentFromId() + "_group_totals";

      List<ProductConsignmentDetailResponse> responses = new ArrayList<>();

      List<ProductConsignmentDetailResponse> productResponses = transferConsignmentMapper.listProduct(filter);
      System.out.println(productResponses);
      //! Calculate QTY
      if(productResponses.size() > 0){
        for (int i = 0; i < productResponses.size(); i++) {
          Long productId = productResponses.get(i).getId();

          Double totalQtyStock = transferConsignmentMapper.getTotalQtyStock(filter.getDepartmentFromId(), tableName, productId);

          if(totalQtyStock == null) {
            totalQtyStock = 0D;
          }

          // if stock smaller than 0 it no stock
          if(totalQtyStock > 0){
            productResponses.get(i).setQty(totalQtyStock);
            responses.addAll(productResponses);
          }

          //! Find location and stock available QTY
          List<TransferConsignmentLocationResponse> transferConsignmentLocationResponseFrom = transferConsignmentMapper.getLocationProduct(filter.getDepartmentFromId());
          List<TransferConsignmentLocationResponse> transferConsignmentLocationResponseTo = transferConsignmentMapper.getLocationProduct(filter.getDepartmentToId());

          for (int j = 0; j < transferConsignmentLocationResponseFrom.size(); j++){
            Long locationId = transferConsignmentLocationResponseFrom.get(j).getId();
            String inventoryTotalTbl = locationId + "_inventory_totals";

            Double qtyStock = transferConsignmentMapper.getLocationStock(inventoryTotalTbl, locationId, productResponses.get(i).getId());
            if(qtyStock == null){
              transferConsignmentLocationResponseFrom.get(j).setStockQty(0D);
            }else{
              transferConsignmentLocationResponseFrom.get(j).setStockQty(qtyStock);
            }
          }
          productResponses.get(i).setLocationFromResponses(transferConsignmentLocationResponseFrom);
          productResponses.get(i).setLocationToResponses(transferConsignmentLocationResponseTo);

        }
      }

      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/product/list", null, null, "Product", "Product (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/product/list", line, error.toString(), "Product", "Product (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

}
