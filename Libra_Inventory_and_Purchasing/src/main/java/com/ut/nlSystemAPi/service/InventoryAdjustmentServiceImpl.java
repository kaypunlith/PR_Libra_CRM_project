package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.BaseFilter;
import com.ut.nlSystemAPi.model.filter.InventoryAdjustmentFilter;
import com.ut.nlSystemAPi.model.filter.QtyOnHandFilter;
import com.ut.nlSystemAPi.model.request.Login.InventoryAdjustment.InventoryAdjustmentDetailRequest;
import com.ut.nlSystemAPi.model.request.Login.InventoryAdjustment.InventoryAdjustmentRequest;
import com.ut.nlSystemAPi.model.request.Login.InventoryAdjustment.InventoryAdjustmentUpdateRequest;
import com.ut.nlSystemAPi.model.response.InventoryAdjustment.InventoryAdjustmentDetailsResponse;
import com.ut.nlSystemAPi.model.response.InventoryAdjustment.InventoryAdjustmentResponse;
import com.ut.nlSystemAPi.model.response.InventoryAdjustment.ProductInStockResponse;
import com.ut.nlSystemAPi.model.response.Warehouse.LocationWarehouseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class InventoryAdjustmentServiceImpl implements InventoryAdjustmentService {

  @Autowired
  private InventoryAdjustmentMapper inventoryAdjustmentMapper;

  @Autowired
  private TransferConsignmentMapper transferConsignmentMapper;

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

  @Autowired
  private PurchaseBillMapper purchaseBillMapper;

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
  private BillReturnMapper billReturnMapper;
  @Autowired
  private WarehouseMapper warehouseMapper;
    @Autowired
    private GenerateCode generateCode;
    @Autowired
    private CodeMapper codeMapper;
    @Autowired
    private Environment environment;
    @Autowired
    private ProductMapper productMapper;

    public ResponseMessage<BaseResult> getList(InventoryAdjustmentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Inventory Adjustment (View)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }
      if (permissionMapper.checkPermission(userId, "Inventory Adjustment (View By User)") > 0) {
        filter.setViewByUser(1L);
      }

      Pagination pagination = new Pagination();
      pagination.setPage(filter.getPage());
      pagination.setRowsPerPage(filter.getRowsPerPage());
      pagination.setTotal(inventoryAdjustmentMapper.countList(filter, userId));
      filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

      List<InventoryAdjustmentResponse> inventoryAdjustmentResponses = inventoryAdjustmentMapper.getList(filter, userId);
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/inventory-adjustment/list",null,null,"Inventory Adjustment","Inventory Adjustment (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", inventoryAdjustmentResponses, pagination, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/inventory-adjustment/list",line, error.toString(),"Inventory Adjustment","Inventory Adjustment (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
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
              if (permissionMapper.checkPermission(userId, "Inventory Adjustment (View)") == 0) {
                  return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            List<InventoryAdjustmentResponse> inventoryAdjustmentResponses = inventoryAdjustmentMapper.getOne(id);

            if (!inventoryAdjustmentResponses.isEmpty()) {
                for (int i = 0; i < inventoryAdjustmentResponses.size(); i++) {
                    inventoryAdjustmentResponses.get(i).setDetails(inventoryAdjustmentMapper.getInventoryAdjustmentDetail(inventoryAdjustmentResponses.get(i).getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-adjustment/find/{id}",null,null,"Inventory Adjustment","Inventory Adjustment (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", inventoryAdjustmentResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-adjustment/find/{id}",line, error.toString(),"Inventory Adjustment","Inventory Adjustment (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(InventoryAdjustmentRequest inventoryAdjustmentRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
     // Check Permission
     Long userId = userService.getUserAuth().getId();
     if (permissionMapper.checkPermission(userId, "Inventory Adjustment (Add)") == 0) {
       return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
     }

      InventoryAdjustment inventoryAdjustment = new InventoryAdjustment();
      inventoryAdjustment.setCompanyId(inventoryAdjustmentRequest.getCompanyId());
      inventoryAdjustment.setWarehouseId(inventoryAdjustmentRequest.getWarehouseId());
      inventoryAdjustment.setDate(inventoryAdjustmentRequest.getDate());
      inventoryAdjustment.setDepositTo(inventoryAdjustmentRequest.getDepositTo());
      inventoryAdjustment.setProductGroupId(inventoryAdjustmentRequest.getProductGroupId());
      inventoryAdjustment.setNote(inventoryAdjustmentRequest.getNote());
      inventoryAdjustment.setCpType(inventoryAdjustmentRequest.getCpType());
      inventoryAdjustment.setType(inventoryAdjustmentRequest.getType() != null ? inventoryAdjustmentRequest.getType() : 0L);
      inventoryAdjustment.setCreatedBy(userId);
      inventoryAdjustment.setStatus(1);

      Boolean result = inventoryAdjustmentMapper.insert(inventoryAdjustment);

      if (result) {
          //! Get the reference code
          String code = (generateCode.generateAutoCode("cycle_products", "reference", 7, "ADJ", true, "status > 0"));
          codeMapper.updateCode("cycle_products", "reference", code, inventoryAdjustment.getId());

          List<InventoryAdjustmentDetailRequest> inventoryAdjustmentDetailRequests = inventoryAdjustmentRequest.getDetails();
          for (int i = 0; i< inventoryAdjustmentDetailRequests.size(); i++) {
              InventoryAdjustmentDetail inventoryAdjustmentDetail = new InventoryAdjustmentDetail();
              inventoryAdjustmentDetail.setCycleProductId(inventoryAdjustment.getId());
              inventoryAdjustmentDetail.setProductId(inventoryAdjustmentRequest.getDetails().get(i).getProductId());
              inventoryAdjustmentDetail.setLocationId(inventoryAdjustmentRequest.getDetails().get(i).getLocationId());
              if (inventoryAdjustmentRequest.getDetails().get(i).getExpiredDate() != null) {
                  inventoryAdjustmentDetail.setExpiredDate(inventoryAdjustmentRequest.getDetails().get(i).getExpiredDate());
              } else {
                  inventoryAdjustmentDetail.setExpiredDate("0000-00-00");
              }
              inventoryAdjustmentDetail.setCurrentQty(inventoryAdjustmentRequest.getDetails().get(i).getCurrentQty());
              inventoryAdjustmentDetail.setLotNumber("0");
              inventoryAdjustmentDetail.setNewQty(inventoryAdjustmentRequest.getDetails().get(i).getNewQty());
              inventoryAdjustmentDetail.setQtyDifference(inventoryAdjustmentRequest.getDetails().get(i).getQtyDifference());
              inventoryAdjustmentMapper.insertInventoryAdjustmentDetail(inventoryAdjustmentDetail);
          }
        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/inventory-adjustment/add",null,null,"Inventory Adjustment","Inventory Adjustment (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", null, false));
      }
   } catch (Exception error) {
     /*System Activity*/
     LocalTime endDuration = LocalTime.now();
     activityLogService.insert("/inventory-adjustment/add",line, error.toString(),"Inventory Adjustment","Inventory Adjustment (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
     return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
   }
  }

  public ResponseMessage<BaseResult> update(InventoryAdjustmentUpdateRequest inventoryAdjustmentUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
      LocalTime startDuration = LocalTime.now();
      Long line = 1033L;
      try {
          // Check Permission
          Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Inventory Adjustment (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

          InventoryAdjustment inventoryAdjustment = new InventoryAdjustment();
          inventoryAdjustment.setCompanyId(inventoryAdjustmentUpdateRequest.getCompanyId());
          inventoryAdjustment.setReference(inventoryAdjustmentMapper.getCode(inventoryAdjustmentUpdateRequest.getId()));
          inventoryAdjustment.setCpType(inventoryAdjustmentMapper.getCpType(inventoryAdjustmentUpdateRequest.getId()));
          inventoryAdjustment.setWarehouseId(inventoryAdjustmentUpdateRequest.getWarehouseId());
          inventoryAdjustment.setDate(inventoryAdjustmentUpdateRequest.getDate());
          inventoryAdjustment.setDepositTo(inventoryAdjustmentUpdateRequest.getDepositTo());
          inventoryAdjustment.setProductGroupId(inventoryAdjustmentUpdateRequest.getProductGroupId());
          inventoryAdjustment.setNote(inventoryAdjustmentUpdateRequest.getNote());
          inventoryAdjustment.setModifiedBy(userId);
          Long adjType = inventoryAdjustmentUpdateRequest.getType() != null
                  ? inventoryAdjustmentUpdateRequest.getType()
                  : inventoryAdjustmentMapper.getAdjType(inventoryAdjustmentUpdateRequest.getId());
          inventoryAdjustment.setType(adjType);
          inventoryAdjustmentMapper.archive(inventoryAdjustmentUpdateRequest.getId(), userId);
          Boolean result = inventoryAdjustmentMapper.update(inventoryAdjustment);

          if (result) {
              List<InventoryAdjustmentDetailRequest> inventoryAdjustmentDetailRequests = inventoryAdjustmentUpdateRequest.getDetails();
              for (int i = 0; i< inventoryAdjustmentDetailRequests.size(); i++) {
                  InventoryAdjustmentDetail inventoryAdjustmentDetail = new InventoryAdjustmentDetail();
                  inventoryAdjustmentDetail.setCycleProductId(inventoryAdjustment.getId());
                  inventoryAdjustmentDetail.setProductId(inventoryAdjustmentUpdateRequest.getDetails().get(i).getProductId());
                  inventoryAdjustmentDetail.setLocationId(inventoryAdjustmentUpdateRequest.getDetails().get(i).getLocationId());
                  if (inventoryAdjustmentUpdateRequest.getDetails().get(i).getExpiredDate() != null) {
                      inventoryAdjustmentDetail.setExpiredDate(inventoryAdjustmentUpdateRequest.getDetails().get(i).getExpiredDate());
                  } else {
                      inventoryAdjustmentDetail.setExpiredDate("0000-00-00");
                  }
                  inventoryAdjustmentDetail.setCurrentQty(inventoryAdjustmentUpdateRequest.getDetails().get(i).getCurrentQty());
                  inventoryAdjustmentDetail.setLotNumber("0");
                  inventoryAdjustmentDetail.setNewQty(inventoryAdjustmentUpdateRequest.getDetails().get(i).getNewQty());
                  inventoryAdjustmentDetail.setQtyDifference(inventoryAdjustmentUpdateRequest.getDetails().get(i).getQtyDifference());
                  inventoryAdjustmentMapper.insertInventoryAdjustmentDetail(inventoryAdjustmentDetail);
              }
              /*System Activity*/
              LocalTime endDuration = LocalTime.now();
              activityLogService.insert("/inventory-adjustment/update",null,null,"Inventory Adjustment","Inventory Adjustment (Edit)","Edit",1,"Success",startDuration,endDuration, httpServletRequest);
              return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
          } else {
              return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", null, false));
          }
      } catch (Exception error) {
          /*System Activity*/
          LocalTime endDuration = LocalTime.now();
          activityLogService.insert("/inventory-adjustment/update",line, error.toString(),"Inventory Adjustment","Inventory Adjustment (Edit)","Edit",2,"Error",startDuration,endDuration, httpServletRequest);
          return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
      }
  }

  public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      // Check Permission
      Long userId = userService.getUserAuth().getId();
      if (permissionMapper.checkPermission(userId, "Inventory Adjustment (Delete)") == 0) {
        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
      }

      Boolean result = inventoryAdjustmentMapper.delete(id, userId);
      if (result) {
        /*System Activity*/
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/inventory-adjustment/delete/{id}",null,null,"Inventory Adjustment","Inventory Adjustment (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
      } else {
        return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
      }
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/inventory-adjustment/delete/{id}",line, error.toString(),"Inventory Adjustment","Inventory Adjustment (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

    @Override
    public ResponseMessage<BaseResult> updateStatus(BaseFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Inventory Adjustment (Approve)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<InventoryAdjustmentResponse> inventoryAdjustmentResponses = inventoryAdjustmentMapper.getOne(filter.getId());

            if (!inventoryAdjustmentResponses.isEmpty()) {
                for (InventoryAdjustmentResponse response : inventoryAdjustmentResponses) {
                        List<InventoryAdjustmentDetailsResponse> detailResponses = inventoryAdjustmentMapper.getInventoryAdjustmentDetail(response.getId());
                        Long calculateCog = purchaseBillMapper.getCalculateCog();
                        if (!detailResponses.isEmpty()) {
                            for (InventoryAdjustmentDetailsResponse detail : detailResponses) {

                                //  Override AdjustQty With Qty On Hand If Type = 2 ( Remove All )
                                if (Objects.equals(response.getType(), 2L)) {
                                    detail.setAdjustQty(detail.getQtyOnhand());
                                }

                                if (calculateCog == 1) {
                                    Long smallValUom = purchaseOrderMapper.getSmallValUom(detail.getProductId());
                                    // Insert Inventory Valuation
                                    InventoryValuation inventoryValuation = new InventoryValuation();
                                    inventoryValuation.setCycleProductId(detail.getInventoryAdjustmentId());
                                    inventoryValuation.setType("Inv Adj");
                                    inventoryValuation.setCompanyId(response.getCompanyId());
                                    inventoryValuation.setPid(detail.getProductId());
                                    inventoryValuation.setSmallQty((double)detail.getAdjustQty());
                                    inventoryValuation.setQty((double)detail.getAdjustQty() / smallValUom);
                                    inventoryValuation.setReference(response.getAdj());
                                    inventoryValuation.setIsVarCost(1);
                                    inventoryAdjustmentMapper.insertInventoryValuation(inventoryValuation);

                                    // Insert General Ledger
                                    GeneralLedger generalLedger = new GeneralLedger();
                                    generalLedger.setInventoryAdjustmentId(response.getId());
                                    generalLedger.setDate(response.getDate());
                                    generalLedger.setReference(response.getAdj());
                                    generalLedger.setCreatedBy(userId);
                                    generalLedger.setIsSys(1);
                                    generalLedger.setIsActive(1);
                                    generalLedger.setIsAdj(0);
                                    billReturnMapper.insertGeneralLedger(generalLedger);

                                    // General Ledger Detail (Inventory Asset)
                                    GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                                    generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                                    generalLedgerDetail.setChartAccountId(purchaseBillMapper.getProductIncomeChartAccountId(detail.getProductId()));
                                    generalLedgerDetail.setLocationId(detail.getLocationId());
                                    generalLedgerDetail.setCompanyId(response.getCompanyId());
                                    generalLedgerDetail.setProductId(detail.getProductId());
                                    generalLedgerDetail.setType("Inventory Adjust");
                                    generalLedgerDetail.setMemo("ICS: Cycle count adjustment for product # " + detail.getProductName());
                                    generalLedgerDetail.setClassId(billReturnMapper.getClassByLocationGroupId(response.getCompanyId(), response.getWarehouseId()));

                                    if (detail.getAdjustQty() > 0) {
                                        generalLedgerDetail.setInventoryValuationId(inventoryValuation.getId());
                                        generalLedgerDetail.setInventoryValuationIsDebit(1);
                                        generalLedgerDetail.setDebit(detail.getAdjustQty() * detail.getUnitCost());
                                        generalLedgerDetail.setCredit(0D);
                                    } else {
                                        generalLedgerDetail.setInventoryValuationId(inventoryValuation.getId());
                                        generalLedgerDetail.setInventoryValuationIsDebit(0);
                                        generalLedgerDetail.setDebit(0D);
                                        generalLedgerDetail.setCredit(detail.getAdjustQty() * detail.getUnitCost());
                                    }
                                    billReturnMapper.insertGeneralLedgerDetail(generalLedgerDetail);

                                    // General Ledger Detail (Deposit Account)
                                    generalLedgerDetail = new GeneralLedgerDetail();
                                    generalLedgerDetail.setGeneralLedgerId(generalLedger.getId());
                                    generalLedgerDetail.setChartAccountId(response.getDepositTo());
                                    generalLedgerDetail.setLocationId(detail.getLocationId());
                                    generalLedgerDetail.setCompanyId(response.getCompanyId());
                                    generalLedgerDetail.setProductId(detail.getProductId());
                                    generalLedgerDetail.setType("Inventory Adjust");
                                    generalLedgerDetail.setMemo("ICS: Cycle count adjustment for product # " + detail.getProductName());
                                    generalLedgerDetail.setClassId(billReturnMapper.getClassByLocationGroupId(response.getCompanyId(), response.getWarehouseId()));

                                    if (detail.getAdjustQty() > 0) {
                                        generalLedgerDetail.setInventoryValuationId(inventoryValuation.getId());
                                        generalLedgerDetail.setInventoryValuationIsDebit(0);
                                        generalLedgerDetail.setDebit(0D);
                                        generalLedgerDetail.setCredit(detail.getAdjustQty() * detail.getUnitCost());
                                    } else {
                                        generalLedgerDetail.setInventoryValuationId(inventoryValuation.getId());
                                        generalLedgerDetail.setInventoryValuationIsDebit(1);
                                        generalLedgerDetail.setDebit(detail.getAdjustQty() * detail.getUnitCost());
                                        generalLedgerDetail.setCredit(0D);
                                    }
                                    billReturnMapper.insertGeneralLedgerDetail(generalLedgerDetail);
                                }

                                GlobalStock globalStock = new GlobalStock();
                                globalStock.setCycleProductId(detail.getInventoryAdjustmentId());
                                globalStock.setCycleProductDetailId(detail.getId());
                                globalStock.setProductId(detail.getProductId());
                                globalStock.setLocationId(detail.getLocationId());
                                globalStock.setWarehouseId(response.getWarehouseId());
                                globalStock.setLotsNumber(detail.getLotsNumber());
                                if (detail.getExpiredDate() == null) {
                                    globalStock.setExpiredDate("0000-00-00");
                                } else {
                                    globalStock.setExpiredDate(detail.getExpiredDate());
                                }
                                globalStock.setTotalCycle(detail.getAdjustQty());
                                globalStock.setTotalQty(detail.getAdjustQty());
                                globalStock.setType("Inv Adj");
                                globalStock.setCreatedBy(userId);
                                globalStock.setUnitCost(detail.getUnitCost());
                                if (Objects.equals(response.getType(), 2L)) {
                                    resetStock(globalStock, response.getWarehouseId());
                                } else {
                                    insertStock(globalStock, 1L);
                                }
                            }
                    }
                }
            }

            Boolean result = inventoryAdjustmentMapper.updateStatus(filter, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/inventory-adjustment/update-status",null,null,"Inventory Adjustment","Inventory Adjustment (Approve)","Approve",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-adjustment/update-status",line, error.toString(),"Inventory Adjustment","Inventory Adjustment (Approve)","Approve",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> findQtyOnHand(QtyOnHandFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Inventory Adjustment (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            String tableName = filter.getLocationId()+"_inventory_totals";
            String tableNameDetail = filter.getLocationId()+"_inventory_total_details";
            String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Long totalQty;

            if (filter.getDate().compareTo(dateNow) < 0) {
                totalQty = inventoryAdjustmentMapper.findTotalQtyFromDetails(tableNameDetail, filter);
            } else {
                totalQty = inventoryAdjustmentMapper.findTotalQtyFromTotals(tableName, filter);
            }

            if (totalQty == null) {
                totalQty = 0L;
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-adjustment/find/qty-on-hand",null,null,"Inventory Adjustment","Inventory Adjustment (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", Collections.singletonList(totalQty),true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-adjustment/find/qty-on-hand",line, error.toString(),"Inventory Adjustment","Inventory Adjustment (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listProductInStock(Long warehouseId, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Inventory Adjustment (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            String tableName = warehouseId + "_group_totals";
            List<ProductInStockResponse> products = inventoryAdjustmentMapper.listProductInStock(tableName);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-adjustment/list-product-in-stock/{warehouseId}", null, null, "Inventory Adjustment", "Inventory Adjustment (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", products, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/inventory-adjustment/list-product-in-stock/{warehouseId}", line, error.toString(), "Inventory Adjustment", "Inventory Adjustment (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private void insertStock(GlobalStock globalStock, Long typeOperation) {
        String fieldToUpdate = "total_cycle";

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

    private void resetStock(GlobalStock globalStock, Long warehouseId) {
        String tblGroupTotal = warehouseId + "_group_totals";
        inventoryAdjustmentMapper.resetGroupTotals(tblGroupTotal);

        String tblGroupTotalDetail = warehouseId + "_group_total_details";
        inventoryAdjustmentMapper.resetGroupTotalDetails(tblGroupTotalDetail);

        List<LocationWarehouseResponse> locations = warehouseMapper.getLocationList(warehouseId);
        if (locations != null && !locations.isEmpty()) {
            for (LocationWarehouseResponse location : locations) {
                Long locationId = location.getId();
                String tblInventoryTotal = locationId + "_inventory_totals";
                inventoryAdjustmentMapper.resetInventoryTotals(tblInventoryTotal);

                String tblInventoryTotalDetail = locationId + "_inventory_total_details";
                inventoryAdjustmentMapper.resetInventoryTotalDetails(tblInventoryTotalDetail);

                String tblInventories = locationId + "_inventories";
                inventoryAdjustmentMapper.resetInventories(globalStock, tblInventories);
            }
        }

        inventoryAdjustmentMapper.resetInventoriesAll(globalStock);

        inventoryAdjustmentMapper.resetInventoryTotalsAll(warehouseId);
    }
}
