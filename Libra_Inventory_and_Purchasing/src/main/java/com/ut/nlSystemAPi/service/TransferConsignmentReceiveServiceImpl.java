package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.GlobalStock;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.TransferConsignmentReceive;
import com.ut.nlSystemAPi.model.TransferConsignmentReceiveResult;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.TransferConsignmentFilter;
import com.ut.nlSystemAPi.model.request.Login.TransferConsignment.TransferConsignmentReceiveRequest;
import com.ut.nlSystemAPi.model.response.TransferConsignment.TransferConsignmentDetailResponse;
import com.ut.nlSystemAPi.model.response.TransferConsignmentReceive.TransferConsignmentReceiveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class TransferConsignmentReceiveServiceImpl implements TransferConsignmentReceiveService {

  @Autowired
  private TransferConsignmentReceiveMapper transferConsignmentReceiveMapper;

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

    public ResponseMessage<BaseResult> getList(TransferConsignmentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
//      if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//      }

      Pagination pagination = new Pagination();
      pagination.setPage(filter.getPage());
      pagination.setRowsPerPage(filter.getRowsPerPage());
      pagination.setTotal(transferConsignmentReceiveMapper.countList(filter));
      filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

      List<TransferConsignmentReceiveResponse> transferConsignmentReceiveResponses = transferConsignmentReceiveMapper.getList(filter);
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/warehouse/list",null,null,"System Role","System Role (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", transferConsignmentReceiveResponses, pagination, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/warehouse/list",line, error.toString(),"System Role","System Role (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
//      if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//      }

      List<TransferConsignmentReceiveResponse> transferConsignmentResponses = transferConsignmentReceiveMapper.getOne(id);

      if (transferConsignmentResponses.size() > 0){
        for(int i = 0; i < transferConsignmentResponses.size(); i++){
          List<TransferConsignmentDetailResponse> detailResponses = transferConsignmentReceiveMapper.getTransferConsignmentDetail(transferConsignmentResponses.get(i).getId());
          transferConsignmentResponses.get(i).setDetails(detailResponses);
        }
      }

      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/role/find/{id}",null,null,"System Role","System Role (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", transferConsignmentResponses, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/role/find/{id}",line, error.toString(),"System Role","System Role (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> receive(TransferConsignmentReceiveRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
//      if (permissionMapper.checkPermission(userId, "System Role (Delete)") == 0) {
//        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//      }

      List<TransferConsignmentReceiveResponse> transferConsignmentResponses = transferConsignmentReceiveMapper.getOne(request.getId());
      if (transferConsignmentResponses != null && !transferConsignmentResponses.isEmpty()){

        //  Insert Transfer Consignment Receive Result
        TransferConsignmentReceiveResult transferConsignmentReceiveResult = new TransferConsignmentReceiveResult();
        transferConsignmentReceiveResult.setTransferOrderId(request.getId());
        {
            //! Get the reference code
            String code = (generateCode.generateAutoCode("transfer_receive_results", "code", 7, "TR", true, ""));
            transferConsignmentReceiveResult.setTrNumber(code);
        }
        transferConsignmentReceiveResult.setTrDate(request.getTrDate());
        transferConsignmentReceiveMapper.insertReceiveResult(transferConsignmentReceiveResult, userId);

        for(int i = 0; i < transferConsignmentResponses.size(); i++){
          List<TransferConsignmentDetailResponse> detailResponses = transferConsignmentReceiveMapper.getDetail(transferConsignmentResponses.get(i).getId());
          if (detailResponses != null && !detailResponses.isEmpty()){
              for (TransferConsignmentDetailResponse detailRespons : detailResponses) {
                  // Insert Transfer Consignment Receive
                  TransferConsignmentReceive transferConsignmentReceive = new TransferConsignmentReceive();
                  transferConsignmentReceive.setTransferReceiveResultId(transferConsignmentReceiveResult.getId());
                  transferConsignmentReceive.setTransferOrderId(transferConsignmentResponses.get(i).getId());
                  transferConsignmentReceive.setTransferOrderDetailId(detailRespons.getId());
                  transferConsignmentReceive.setLotsNumber("0");
                  if (detailRespons.getExpiredDate() == null) {
                      transferConsignmentReceive.setExpiredDate("0000-00-00");
                  } else {
                      transferConsignmentReceive.setExpiredDate(detailRespons.getExpiredDate());
                  }
                  transferConsignmentReceive.setProductId(detailRespons.getProductId());
                  transferConsignmentReceive.setQty(detailRespons.getQtyTransfer());
                  transferConsignmentReceive.setUomId(detailRespons.getUomId());
                  transferConsignmentReceive.setConversion(detailRespons.getConversion());
                  transferConsignmentReceiveMapper.insertReceive(transferConsignmentReceive, userId);

                  // Update Inventory (Transfer Out)
                  GlobalStock globalStock = new GlobalStock();
                  globalStock.setTransferOrderId(detailRespons.getTransferOrderId());
                  globalStock.setProductId(detailRespons.getProductId());
                  globalStock.setLocationId(detailRespons.getLocationFromId());
                  globalStock.setWarehouseId(transferConsignmentResponses.get(i).getFromWarehouseId());
                  globalStock.setLotsNumber(detailRespons.getLotsNumber());
                  if (detailRespons.getExpiredDate() == null) {
                      globalStock.setExpiredDate("0000-00-00");
                  } else {
                      globalStock.setExpiredDate(detailRespons.getExpiredDate());
                  }
                  globalStock.setTotalToOut(detailRespons.getQtyTransfer() * detailRespons.getConversion());
                  globalStock.setTotalQty(detailRespons.getQtyTransfer() * detailRespons.getConversion());
                  globalStock.setTotalUpdate(detailRespons.getQtyTransfer() * detailRespons.getConversion());
                  globalStock.setConversion(detailRespons.getConversion());
                  globalStock.setType("Transfer Out");
                  globalStock.setCreatedBy(userId);
                  globalStock.setUnitCost(detailRespons.getUnitCost());
                  System.out.println(globalStock);
                  insertStock(globalStock, 2L);


                  // Update Inventory (Transfer In)
                  globalStock.setTransferOrderId(detailRespons.getTransferOrderId());
                  globalStock.setProductId(detailRespons.getProductId());
                  globalStock.setLocationId(detailRespons.getLocationToId());
                  globalStock.setWarehouseId(transferConsignmentResponses.get(i).getToWarehouseId());
                  globalStock.setLotsNumber(detailRespons.getLotsNumber());
                  if (detailRespons.getExpiredDate() == null) {
                      globalStock.setExpiredDate("0000-00-00");
                  } else {
                      globalStock.setExpiredDate(detailRespons.getExpiredDate());
                  }
                  globalStock.setTotalToIn(detailRespons.getQtyTransfer() * detailRespons.getConversion());
                  globalStock.setTotalQty(detailRespons.getQtyTransfer() * detailRespons.getConversion());
                  globalStock.setTotalUpdate(detailRespons.getQtyTransfer() * detailRespons.getConversion());
                  globalStock.setConversion(detailRespons.getConversion());
                  globalStock.setType("Transfer In");
                  globalStock.setCreatedBy(userId);
                  globalStock.setUnitCost(detailRespons.getUnitCost());
                  System.out.println(globalStock);
                  insertStock(globalStock, 1L);

              }
          }
        }
      }

      transferConsignmentReceiveMapper.updateRequestStock(request.getId());
      Boolean result = transferConsignmentReceiveMapper.receive(request, userId);

      if (result) {
        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/role/delete/{id}",null,null,"System Role","System Role (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/role/delete/{id}",line, error.toString(),"System Role","System Role (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  private void insertStock(GlobalStock globalStock, Long typeOperation) {
    String fieldToUpdate = typeOperation == 1 ? "total_to_in" : "total_to_out";

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