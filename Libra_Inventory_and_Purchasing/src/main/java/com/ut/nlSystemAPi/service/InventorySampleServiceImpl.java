package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.GlobalStock;
import com.ut.nlSystemAPi.model.InventorySample;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.InventorySampleFilter;
import com.ut.nlSystemAPi.model.filter.InventorySampleProductFilter;
import com.ut.nlSystemAPi.model.request.Login.InventorySample.*;
import com.ut.nlSystemAPi.model.response.InventoryAdjustment.InventoryAdjustmentDetailsResponse;
import com.ut.nlSystemAPi.model.response.InventoryAdjustment.InventoryAdjustmentResponse;
import com.ut.nlSystemAPi.model.response.InventorySample.InventorySampleProductResponseDetail;
import com.ut.nlSystemAPi.model.response.InventorySample.InventorySampleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class InventorySampleServiceImpl implements InventorySampleService {

    @Autowired
    private InventorySampleMapper inventorySampleMapper;

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
    private TelegramMessageService telegramMessageService;

    public ResponseMessage<BaseResult> getList(InventorySampleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Inventory Sample (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }
            if (permissionMapper.checkPermission(userId, "Inventory Sample (View By User)") > 0) {
                filter.setViewByUser(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(inventorySampleMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<InventorySampleResponse> inventorySampleResponses = inventorySampleMapper.getList(filter, userId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-sample/list",null,null,"Inventory Sample","Inventory Sample (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", inventorySampleResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-sample/list",line, error.toString(),"Inventory Sample","Inventory Sample (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getListProductDetail(InventorySampleProductFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Inventory Sample (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(inventorySampleMapper.countProductList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<InventorySampleProductResponseDetail> inventorySampleProductResponseDetails = inventorySampleMapper.getListProductDetail(filter, userId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-sample/product-list",null,null,"Inventory Sample","Inventory Sample (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", inventorySampleProductResponseDetails, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("inventory-sample/product-list",line, error.toString(),"Inventory Sample","Inventory Sample (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Inventory Sample (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }
            List<InventorySampleResponse> inventorySampleResponses = inventorySampleMapper.getOne(id);

            if (!inventorySampleResponses.isEmpty()) {
                InventorySampleResponse response = inventorySampleResponses.get(0);
                List<InventorySampleProductResponseDetail> productDetails = inventorySampleMapper.getProductDetails(id);
                response.setInventorySampleProductRepsonseDetailList(productDetails);
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-sample/find/{id}", null, null, "Inventory Sample", "Inventory Sample (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", inventorySampleResponses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-sample/find/{id}", line, error.toString(), "Inventory Sample", "Inventory Sample (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(InventorySampleRequest inventorySampleRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {

            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Inventory Sample (Add)") == 0) {
                 return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if(inventorySampleMapper.checkDuplicate(inventorySampleRequest.getReference(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Reference", false));
            }

            InventorySample inventorySample = new InventorySample();

            inventorySample.setCompanyId(inventorySampleRequest.getCompanyId());
            inventorySample.setReference(inventorySampleRequest.getReference());
            inventorySample.setDate(inventorySampleRequest.getDate());
            inventorySample.setDescription(inventorySampleRequest.getDescription());
            inventorySample.setWarehouseId(inventorySampleRequest.getWarehouseId());
            inventorySample.setPgroupId(inventorySampleRequest.getPgroupId());
            inventorySample.setCreatedBy(userId);

            Boolean result = inventorySampleMapper.insert(inventorySample);

            if (result) {
                Long inventorySampleProductId = inventorySample.getId();
                if (inventorySampleRequest.getInventorySampleRequestDetail() != null) {
                    for (InventorySampleRequestDetail detail : inventorySampleRequest.getInventorySampleRequestDetail()) {
                        detail.setInventorySampleProductId(inventorySampleProductId);
                        detail.setProductId(detail.getProductId());
                        detail.setLocationId(detail.getLocationId());
                        detail.setExpiredDate(detail.getExpiredDate());
                        detail.setNewQty(detail.getNewQty());
                        detail.setAdjustQty(detail.getAdjustQty());
                        detail.setQtyOnHand(detail.getQtyOnHand());
                        inventorySampleMapper.insertInventorySampleProductDetails(detail);
                    }
                }

                // Log success and return response
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/inventory-sample/insert", null, null, "Inventory Sample", "Add", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-sample/insert", line, error.toString(), "Inventory Sample", "Add", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(InventorySampleRequestUpdate inventorySampleRequestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

             if (permissionMapper.checkPermission(userId, "Inventory Sample (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }

            // Check Duplicate
            if(inventorySampleMapper.checkDuplicate(inventorySampleRequestUpdate.getReference(), inventorySampleRequestUpdate.getId()) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Reference", false));
            }

            InventorySample inventorySample = new InventorySample();
            inventorySample.setCompanyId(inventorySampleRequestUpdate.getCompanyId());
            inventorySample.setReference(inventorySampleRequestUpdate.getReference());
            inventorySample.setDate(inventorySampleRequestUpdate.getDate());
            inventorySample.setDescription(inventorySampleRequestUpdate.getDescription());
            inventorySample.setWarehouseId(inventorySampleRequestUpdate.getWarehouseId());
            inventorySample.setPgroupId(inventorySampleRequestUpdate.getPgroupId());
            inventorySample.setModifiedBy(userId);
            inventorySampleMapper.archive(inventorySampleRequestUpdate.getId(), userId);
            Boolean result = inventorySampleMapper.update(inventorySample);

            if (result) {
                Long inventorySampleProductId = inventorySample.getId();
                if (inventorySampleRequestUpdate.getInventorySampleRequestDetail() != null) {
                    for (InventorySampleRequestDetail detail : inventorySampleRequestUpdate.getInventorySampleRequestDetail()) {
                        detail.setInventorySampleProductId(inventorySampleProductId);
                        detail.setProductId(detail.getProductId());
                        detail.setLocationId(detail.getLocationId());
                        detail.setExpiredDate(detail.getExpiredDate());
                        detail.setNewQty(detail.getNewQty());
                        detail.setAdjustQty(detail.getAdjustQty());
                        detail.setQtyOnHand(detail.getQtyOnHand());
                        inventorySampleMapper.insertInventorySampleProductDetails(detail);
                    }
                }

                // Log success and return response
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/inventory-sample/update", null, null, "Inventory Sample", "Edit", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-sample/update", line, error.toString(), "Inventory Sample", "Edit", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }


    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Inventory Sample (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            Boolean result = inventorySampleMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/inventory-sample/delete/{id}",null,null,"Inventory Sample","Inventory Sample (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-sample/delete/{id}",line, error.toString(),"Inventory Sample","Inventory Sample (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> updateStatus(InventorySampleStatusUpdateRequest statusUpdateRequest, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Inventory Sample (Receive)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            InventorySample inventorySample = new InventorySample();
            inventorySample.setId(statusUpdateRequest.getId());
            inventorySample.setStatus(statusUpdateRequest.getStatus());

            List<InventorySampleResponse> inventorySampleResponses = inventorySampleMapper.getOne(statusUpdateRequest.getId());

            if (!inventorySampleResponses.isEmpty()) {
                for (int i = 0; i < inventorySampleResponses.size(); i++) {
                    List<InventorySampleProductResponseDetail> detailResponses = inventorySampleMapper.getProductDetails(inventorySampleResponses.get(i).getId());
                    if (!detailResponses.isEmpty()) {
                        for (int j = 0; j < detailResponses.size(); j++) {
                            GlobalStock globalStock = new GlobalStock();
                            globalStock.setCycleProductId(detailResponses.get(j).getInventorySampleProductId());
                            globalStock.setCycleProductDetailId(detailResponses.get(j).getId());
                            globalStock.setProductId(detailResponses.get(j).getProductId());
                            globalStock.setLocationId(detailResponses.get(j).getLocationId());
                            globalStock.setWarehouseId(inventorySampleResponses.get(i).getWarehouseId());
                            globalStock.setLotsNumber(detailResponses.get(j).getLotsNumber());
                            if (detailResponses.get(j).getExpiredDate() == null) {
                                globalStock.setExpiredDate("0000-00-00");
                            } else {
                                globalStock.setExpiredDate(detailResponses.get(j).getExpiredDate());
                            }
                            globalStock.setTotalCycle(detailResponses.get(j).getAdjustQty());
                            globalStock.setTotalQty(detailResponses.get(j).getAdjustQty());
                            globalStock.setType("Inv Sample");
                            globalStock.setCreatedBy(userId);
                            globalStock.setUnitCost(detailResponses.get(j).getUnitCost());
                            insertStock(globalStock, 1L);
                        }
                    }
                }
            }
            Boolean result = inventorySampleMapper.updateInventorySampleStatus(inventorySample);

            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/inventory-sample/update-status", null, null, "Inventory Sample", "Inventory Sample (Receive)", "Receive", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Failed", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-sample/update-status", null, error.toString(), "Inventory Sample", "Inventory Sample (Receive)", "Receive", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    private void insertStock(GlobalStock globalStock, Long typeOperation) {
        String fieldToUpdate = "total_cycle";

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

        System.out.println("Done");

    }

}
