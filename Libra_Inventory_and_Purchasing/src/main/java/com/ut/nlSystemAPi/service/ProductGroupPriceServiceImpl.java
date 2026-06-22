package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ProductGroupPriceMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.CalculateTotalProductFilter;
import com.ut.nlSystemAPi.model.filter.ProductGroupPriceFilter;
import com.ut.nlSystemAPi.model.request.Login.ProductGroupPrice.PgroupPriceRequest;
import com.ut.nlSystemAPi.model.request.Login.ProductGroupPrice.ProductGroupPriceRequest;
import com.ut.nlSystemAPi.model.request.Login.ProductGroupPrice.ProductGroupPriceUpdateRequest;
import com.ut.nlSystemAPi.model.response.ProductGroupPrice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductGroupPriceServiceImpl implements ProductGroupPriceService {
    @Autowired
    private ProductGroupPriceMapper productGroupPriceMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(ProductGroupPriceFilter productGroupPriceFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product Group Price Setting (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(productGroupPriceFilter.getPage());
            pagination.setRowsPerPage(productGroupPriceFilter.getRowsPerPage());
            pagination.setTotal(productGroupPriceMapper.countList(productGroupPriceFilter));

            productGroupPriceFilter.setPage((productGroupPriceFilter.getPage() - 1) * productGroupPriceFilter.getRowsPerPage());
            List<ProductGroupPriceResponse> productGroupPriceResponses  = productGroupPriceMapper.getList(productGroupPriceFilter);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group-price-setting/list", null, null, "Product Group Price Setting ", "Product Group Price Setting (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", productGroupPriceResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group-price-setting/list", line, error.toString(), "Product Group Price Setting ", "Product Group Price Setting (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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
              if (permissionMapper.checkPermission(userId, "Product Group Price Setting (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            List<ProductGroupPriceResponse> productGroupPriceResponses = productGroupPriceMapper.getOne(id);
            if(productGroupPriceResponses.size()>0){
                for(int i=0;i<productGroupPriceResponses.size();i++){
                    List<PgroupPriceResponse> pgroupPriceResponse=productGroupPriceMapper.getPgroupPrice(productGroupPriceResponses.get(i).getId());
                    productGroupPriceResponses.get(i).setPgroupPriceResponses(pgroupPriceResponse);
                }

            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group-price-setting/find/{id}", null, null, "Product Group Price Setting ", "Product Group Price Setting  (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", productGroupPriceResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group-price-setting/find/{id}", line, error.toString(), "Product Group Price Setting ", "Product Group Price Setting  (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ProductGroupPriceRequest productGroupPriceRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product Group Price Setting (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

//           Check Duplicate Data
             if(productGroupPriceMapper.checkDuplicateData(productGroupPriceRequest) > 0){
                 productGroupPriceMapper.log(productGroupPriceRequest.getPgroupId(), userId);
             }

             // Check Data
             ProductGroupPrice productGroupPrice = new ProductGroupPrice();
             productGroupPrice.setPgroupId(productGroupPriceRequest.getPgroupId());
             productGroupPrice.setPriceTypeId(productGroupPriceRequest.getPriceTypeId());
             productGroupPrice.setSetType(productGroupPriceRequest.getSetType());
             productGroupPrice.setCostMethod(productGroupPriceRequest.getCostMethod());
             productGroupPrice.setApplyToAllProduct(productGroupPriceRequest.getApplyToAllProduct());
             productGroupPrice.setCreatedBy(userId);
             productGroupPrice.setIsActive(1);
             Boolean result = productGroupPriceMapper.insert(productGroupPrice);

            if (result) {

                List<PgroupPriceRequest> pgroupPriceRequests = productGroupPriceRequest.getPgroupPriceRequests();
                if (pgroupPriceRequests != null && !pgroupPriceRequests.isEmpty()) {
                    for(int i=0; i<pgroupPriceRequests.size(); i++){

                        PgroupPriceInformation pgroupPriceInformation = new PgroupPriceInformation();
                        pgroupPriceInformation.setPgroupPriceSettingId(productGroupPrice.getId());
                        pgroupPriceInformation.setPgroupId(productGroupPrice.getPgroupId());
                        pgroupPriceInformation.setPgroupPriceTypeid(productGroupPrice.getPriceTypeId());
                        pgroupPriceInformation.setFromCost(productGroupPriceRequest.getPgroupPriceRequests().get(i).getFromCost());
                        pgroupPriceInformation.setToCost(productGroupPriceRequest.getPgroupPriceRequests().get(i).getToCost());
                        pgroupPriceInformation.setCostMethod(productGroupPrice.getCostMethod());
                        pgroupPriceInformation.setSetType(productGroupPrice.getSetType());
                        if (productGroupPrice.getSetType() == 2) {
                            pgroupPriceInformation.setAddOn(0D);
                            pgroupPriceInformation.setPercent(productGroupPriceRequest.getPgroupPriceRequests().get(i).getValue());
                        } else if (productGroupPrice.getSetType() == 3) {
                            pgroupPriceInformation.setPercent(0D);
                            pgroupPriceInformation.setAddOn(productGroupPriceRequest.getPgroupPriceRequests().get(i).getValue());
                        }
                        pgroupPriceInformation.setCreateBy(userId);
                        productGroupPriceMapper.insertPgroupPriceInformation(pgroupPriceInformation);

                        if (productGroupPriceRequest.getApplyToAllProduct() == 0) {
                            List<Long> productIds = productGroupPriceMapper.getProductIdsByPgroupId(
                                    productGroupPriceRequest.getPgroupId(),
                                    productGroupPriceRequest.getPgroupPriceRequests().get(i).getFromCost(),
                                    productGroupPriceRequest.getPgroupPriceRequests().get(i).getToCost()
                            );
                            System.out.println(productIds);
                            if (productIds != null && !productIds.isEmpty()) {
                                for (int j = 0; j < productIds.size(); j++) {
                                    // Get unit cost for the main UOM
                                    Double unitCost = productGroupPriceMapper.getUnitCost(productIds.get(j));
                                    if (unitCost == null) {
                                        unitCost = 0.0; // Fallback if not found
                                    }

                                    // Delete old product price
                                    productGroupPriceMapper.deleteProductPrice(productIds.get(j), productGroupPriceRequest.getPriceTypeId());

                                    // Get main UOM
                                    Long mainUomId = productGroupPriceMapper.getMainUom(productIds.get(j));

                                    // Insert history for main UOM
                                    ProductPriceHistory productPriceHistory = new ProductPriceHistory();
                                    productPriceHistory.setProductId(productIds.get(j));
                                    productPriceHistory.setPriceTypeId(productGroupPriceRequest.getPriceTypeId());
                                    productPriceHistory.setUomId(mainUomId);
                                    productPriceHistory.setUnitCost(unitCost);
                                    if (productGroupPriceRequest.getSetType() == 2) {
                                        productPriceHistory.setAddOn(0D);
                                        productPriceHistory.setPercentage(pgroupPriceRequests.get(i).getValue());
                                    } else {
                                        productPriceHistory.setPercentage(0D);
                                        productPriceHistory.setAddOn(pgroupPriceRequests.get(i).getValue());
                                    }
                                    productPriceHistory.setSetType(productGroupPriceRequest.getSetType());
                                    productPriceHistory.setCreatedBy(userId);
                                    productGroupPriceMapper.insertProductPriceHistory(productPriceHistory);

                                    // Insert price for main UOM
                                    ProductPrice productPrice = new ProductPrice();
                                    productPrice.setProductId(productIds.get(j));
                                    productPrice.setUomId(mainUomId);
                                    productPrice.setPriceTypeId(productGroupPriceRequest.getPriceTypeId());
                                    if (productGroupPriceRequest.getSetType() == 2) {
                                        productPrice.setAddOn(0D);
                                        productPrice.setPercentage(pgroupPriceRequests.get(i).getValue());
                                    } else {
                                        productPrice.setPercentage(0D);
                                        productPrice.setAddOn(pgroupPriceRequests.get(i).getValue());
                                    }
                                    productPrice.setSetType(productGroupPriceRequest.getSetType());
                                    productPrice.setCreatedBy(userId);
                                    productGroupPriceMapper.insertProductPrice(productPrice);

                                    // Handle other UOMs
                                    List<Long> otherUoms = productGroupPriceMapper.getOtherUoms(mainUomId);
                                    System.out.println(otherUoms);
                                    if (otherUoms != null && !otherUoms.isEmpty()) {
                                        for (int l = 0; l < otherUoms.size(); l++) {
                                            // Get unit cost of product for other UOMs
                                            Double uomVal = productGroupPriceMapper.getUomConversionValue(
                                                    mainUomId, otherUoms.get(l)
                                            );
                                            if (uomVal == null || uomVal == 0.0) {
                                                uomVal = 1.0; // Fallback to avoid division by zero
                                            }
                                            Double convertedUnitCost = unitCost / uomVal;

                                            // Insert history for other UOM
                                            ProductPriceHistory otherUomHistory = new ProductPriceHistory();
                                            otherUomHistory.setProductId(productIds.get(j));
                                            otherUomHistory.setPriceTypeId(productGroupPriceRequest.getPriceTypeId());
                                            otherUomHistory.setUomId(otherUoms.get(l));
                                            otherUomHistory.setUnitCost(convertedUnitCost);
                                            if (productGroupPriceRequest.getSetType() == 2) {
                                                otherUomHistory.setAddOn(0D);
                                                otherUomHistory.setPercentage(pgroupPriceRequests.get(i).getValue());
                                            } else {
                                                otherUomHistory.setPercentage(0D);
                                                otherUomHistory.setPercentage(pgroupPriceRequests.get(i).getValue());
                                            }
                                            otherUomHistory.setSetType(productGroupPriceRequest.getSetType());
                                            otherUomHistory.setCreatedBy(userId);
                                            productGroupPriceMapper.insertProductPriceHistory(otherUomHistory);

                                            // Insert price for other UOM
                                            productPrice.setUomId(otherUoms.get(l));
                                            productGroupPriceMapper.insertProductPrice(productPrice);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-group-price-setting/add", null, null, "Product Group Price Setting ", "Product Group Price Setting  (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error){
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group-price-setting/add", line, error.toString(), "Product Group Price Setting ", "Product Group Price Setting (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(ProductGroupPriceUpdateRequest productGroupPriceUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product Group Price Setting (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            ProductGroupPrice productGroupPrice = new ProductGroupPrice();
            productGroupPrice.setId(productGroupPriceUpdateRequest.getId());
            productGroupPrice.setPgroupId(productGroupPriceUpdateRequest.getPgroupId());
            productGroupPrice.setPriceTypeId(productGroupPriceUpdateRequest.getPriceTypeId());
            productGroupPrice.setSetType(productGroupPriceUpdateRequest.getSetType());
            productGroupPrice.setCostMethod(productGroupPriceUpdateRequest.getCostMethod());
            productGroupPrice.setApplyToAllProduct(productGroupPriceUpdateRequest.getApplyToAllProduct());
            productGroupPrice.setModifiedBy(userId);

            Boolean result = productGroupPriceMapper.update(productGroupPrice);

            if (result) {
                productGroupPriceMapper.deletePgroupPriceInformation(productGroupPriceUpdateRequest.getId(), userId);
                List<PgroupPriceRequest> pgroupPriceRequests = productGroupPriceUpdateRequest.getPgroupPriceRequests();
                if (pgroupPriceRequests != null && !pgroupPriceRequests.isEmpty()) {
                    for(int i=0; i<pgroupPriceRequests.size(); i++){

                        PgroupPriceInformation pgroupPriceInformation = new PgroupPriceInformation();
                        pgroupPriceInformation.setPgroupPriceSettingId(productGroupPrice.getId());
                        pgroupPriceInformation.setPgroupId(productGroupPrice.getPgroupId());
                        pgroupPriceInformation.setPgroupPriceTypeid(productGroupPrice.getPriceTypeId());
                        pgroupPriceInformation.setFromCost(productGroupPriceUpdateRequest.getPgroupPriceRequests().get(i).getFromCost());
                        pgroupPriceInformation.setToCost(productGroupPriceUpdateRequest.getPgroupPriceRequests().get(i).getToCost());
                        pgroupPriceInformation.setCostMethod(productGroupPrice.getCostMethod());
                        pgroupPriceInformation.setSetType(productGroupPrice.getSetType());
                        if (productGroupPrice.getSetType() == 2) {
                            pgroupPriceInformation.setAddOn(0D);
                            pgroupPriceInformation.setPercent(productGroupPriceUpdateRequest.getPgroupPriceRequests().get(i).getValue());
                        } else if (productGroupPrice.getSetType() == 3) {
                            pgroupPriceInformation.setPercent(0D);
                            pgroupPriceInformation.setAddOn(productGroupPriceUpdateRequest.getPgroupPriceRequests().get(i).getValue());
                        }
                        pgroupPriceInformation.setCreateBy(userId);

                        productGroupPriceMapper.insertPgroupPriceInformation(pgroupPriceInformation);

                        if (productGroupPriceUpdateRequest.getApplyToAllProduct() == 0) {
                            List<Long> productIds = productGroupPriceMapper.getProductIdsByPgroupId(
                                    productGroupPriceUpdateRequest.getPgroupId(),
                                    productGroupPriceUpdateRequest.getPgroupPriceRequests().get(i).getFromCost(),
                                    productGroupPriceUpdateRequest.getPgroupPriceRequests().get(i).getToCost()
                            );
                            System.out.println(productIds);
                            if (productIds != null && !productIds.isEmpty()) {
                                for (int j = 0; j < productIds.size(); j++) {
                                    // Get unit cost for the main UOM
                                    Double unitCost = productGroupPriceMapper.getUnitCost(productIds.get(j));
                                    if (unitCost == null) {
                                        unitCost = 0.0; // Fallback if not found
                                    }

                                    // Delete old product price
                                    productGroupPriceMapper.deleteProductPrice(productIds.get(j), productGroupPriceUpdateRequest.getPriceTypeId());

                                    // Get main UOM
                                    Long mainUomId = productGroupPriceMapper.getMainUom(productIds.get(j));

                                    // Insert history for main UOM
                                    ProductPriceHistory productPriceHistory = new ProductPriceHistory();
                                    productPriceHistory.setProductId(productIds.get(j));
                                    productPriceHistory.setPriceTypeId(productGroupPriceUpdateRequest.getPriceTypeId());
                                    productPriceHistory.setUomId(mainUomId);
                                    productPriceHistory.setUnitCost(unitCost);
                                    if (productGroupPriceUpdateRequest.getSetType() == 2) {
                                        productPriceHistory.setAddOn(0D);
                                        productPriceHistory.setPercentage(pgroupPriceRequests.get(i).getValue());
                                    } else {
                                        productPriceHistory.setPercentage(0D);
                                        productPriceHistory.setAddOn(pgroupPriceRequests.get(i).getValue());
                                    }
                                    productPriceHistory.setSetType(productGroupPriceUpdateRequest.getSetType());
                                    productPriceHistory.setCreatedBy(userId);
                                    productGroupPriceMapper.insertProductPriceHistory(productPriceHistory);

                                    // Insert price for main UOM
                                    ProductPrice productPrice = new ProductPrice();
                                    productPrice.setProductId(productIds.get(j));
                                    productPrice.setUomId(mainUomId);
                                    productPrice.setPriceTypeId(productGroupPriceUpdateRequest.getPriceTypeId());
                                    if (productGroupPriceUpdateRequest.getSetType() == 2) {
                                        productPrice.setAddOn(0D);
                                        productPrice.setPercentage(pgroupPriceRequests.get(i).getValue());
                                    } else {
                                        productPrice.setPercentage(0D);
                                        productPrice.setAddOn(pgroupPriceRequests.get(i).getValue());
                                    }
                                    productPrice.setSetType(productGroupPriceUpdateRequest.getSetType());
                                    productPrice.setCreatedBy(userId);
                                    productGroupPriceMapper.insertProductPrice(productPrice);

                                    // Handle other UOMs
                                    List<Long> otherUoms = productGroupPriceMapper.getOtherUoms(mainUomId);
                                    System.out.println(otherUoms);
                                    if (otherUoms != null && !otherUoms.isEmpty()) {
                                        for (int l = 0; l < otherUoms.size(); l++) {
                                            // Get unit cost of product for other UOMs
                                            Double uomVal = productGroupPriceMapper.getUomConversionValue(
                                                    mainUomId, otherUoms.get(l)
                                            );
                                            if (uomVal == null || uomVal == 0.0) {
                                                uomVal = 1.0; // Fallback to avoid division by zero
                                            }
                                            Double convertedUnitCost = unitCost / uomVal;

                                            // Insert history for other UOM
                                            ProductPriceHistory otherUomHistory = new ProductPriceHistory();
                                            otherUomHistory.setProductId(productIds.get(j));
                                            otherUomHistory.setPriceTypeId(productGroupPriceUpdateRequest.getPriceTypeId());
                                            otherUomHistory.setUomId(otherUoms.get(l));
                                            otherUomHistory.setUnitCost(convertedUnitCost);
                                            if (productGroupPriceUpdateRequest.getSetType() == 2) {
                                                otherUomHistory.setAddOn(0D);
                                                otherUomHistory.setPercentage(pgroupPriceRequests.get(i).getValue());
                                            } else {
                                                otherUomHistory.setPercentage(0D);
                                                otherUomHistory.setPercentage(pgroupPriceRequests.get(i).getValue());
                                            }
                                            otherUomHistory.setSetType(productGroupPriceUpdateRequest.getSetType());
                                            otherUomHistory.setCreatedBy(userId);
                                            productGroupPriceMapper.insertProductPriceHistory(otherUomHistory);

                                            // Insert price for other UOM
                                            productPrice.setUomId(otherUoms.get(l));
                                            productGroupPriceMapper.insertProductPrice(productPrice);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-group-price-setting/update", null, null, "Product Group Price Setting", "Product Group Price Setting (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/product-group-price-setting/update", line, error.toString(), "Product Group Price Setting", "Product Group Price Setting (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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
              if (permissionMapper.checkPermission(userId, "Product Group Price Setting (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            Boolean result = productGroupPriceMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-group-price-setting/delete/{id}",null,null,"Product Group Price Setting","Product Group Price Setting (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group-price-setting/delete/{id}",line, error.toString(),"Product Group Price Setting","Product Group Price Setting (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private Map<String, Object> toFreedomPgroupPriceSettingMap(ProductGroupPrice productGroupPrice) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", productGroupPrice.getId());
        map.put("pgroupId", productGroupPrice.getPgroupId());
        map.put("priceTypeId", productGroupPrice.getPriceTypeId());
        map.put("setType", productGroupPrice.getSetType());
        map.put("costMethod", productGroupPrice.getCostMethod());
        map.put("applyToAllProduct", productGroupPrice.getApplyToAllProduct());
        map.put("createdBy", productGroupPrice.getCreatedBy());
        map.put("modifiedBy", productGroupPrice.getModifiedBy());
        map.put("isActive", productGroupPrice.getIsActive());
        return map;
    }

    private Map<String, Object> toFreedomPgroupPriceMap(PgroupPriceInformation pgroupPriceInformation) {
        Map<String, Object> map = new HashMap<>();
        map.put("pgroupId", pgroupPriceInformation.getPgroupId());
        map.put("priceTypeId", pgroupPriceInformation.getPgroupPriceTypeid());
        map.put("pgroupPriceSettingId", pgroupPriceInformation.getPgroupPriceSettingId());
        map.put("fromCost", pgroupPriceInformation.getFromCost());
        map.put("toCost", pgroupPriceInformation.getToCost());
        map.put("percent", pgroupPriceInformation.getPercent());
        map.put("addOn", pgroupPriceInformation.getAddOn());
        map.put("setType", pgroupPriceInformation.getSetType());
        map.put("costMethod", pgroupPriceInformation.getCostMethod());
        map.put("createdBy", pgroupPriceInformation.getCreateBy());
        return map;
    }

    private Map<String, Object> toFreedomProductPriceMap(ProductPrice price) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", price.getProductId());
        map.put("priceTypeId", price.getPriceTypeId());
        map.put("uomId", price.getUomId());
        map.put("amount", price.getAmount());
        map.put("amountBefore", price.getAmountBefore());
        map.put("percentage", price.getPercentage());
        map.put("addOn", price.getAddOn());
        map.put("setType", price.getSetType());
        map.put("createdBy", price.getCreatedBy());
        return map;
    }

    private Map<String, Object> toFreedomProductPriceHistoryMap(ProductPriceHistory history) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", history.getProductId());
        map.put("priceTypeId", history.getPriceTypeId());
        map.put("uomId", history.getUomId());
        map.put("amount", history.getAmount());
        map.put("percentage", history.getPercentage());
        map.put("addOn", history.getAddOn());
        map.put("setType", history.getSetType());
        map.put("createdBy", history.getCreatedBy());
        return map;
    }

    @Override
    public ResponseMessage<BaseResult> calculateTotal(CalculateTotalProductFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Product Group Price Setting (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            Long totalProduct = productGroupPriceMapper.calculateTotal(filter);
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-group-price-setting/calculate-total-product",null,null,"Product Group Price Setting","Product Group Price Setting (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", Collections.singletonList(totalProduct), true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group-price-setting/calculate-total-product",line, error.toString(),"Product Group Price Setting","Product Group Price Setting (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listTotal(CalculateTotalProductFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product Group Price Setting (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(productGroupPriceMapper.countListTotal(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<TotalProductResponse> totalProduct = productGroupPriceMapper.listTotal(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group-price-setting/list-total-product",null,null,"Product Group Price Setting","Product Group Price Setting (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", totalProduct, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group-price-setting/list-total-product",line, error.toString(),"Product Group Price Setting","Product Group Price Setting (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listClone(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            List<ProductGroupPriceResponse> productGroupPriceResponses = productGroupPriceMapper.getOne(id);

            if(productGroupPriceResponses.size()>0){

                for(int i=0;i<productGroupPriceResponses.size();i++){
                    
                    List<PgroupPriceResponse> pgroupPriceResponse = productGroupPriceMapper.listCloneDetail(productGroupPriceResponses.get(i).getId());

                    Long grandTotalProduct = 0L;
                    Double grandTotalValue = 0D;
                    Double grandTotalFromCost = 0D;
                    Double grandTotalToCost = 0D;

                    if(pgroupPriceResponse.size()>0){
                        for(int j=0;j<pgroupPriceResponse.size();j++){
                            //! Find detail price type
                            List<PgroupPriceTypeResponse> pgroupPriceTypeResponse = productGroupPriceMapper.listCloneType(productGroupPriceResponses.get(i).getPgroupId(), pgroupPriceResponse.get(j).getFromCost(), pgroupPriceResponse.get(j).getToCost());

                            //! Find price type grand total value
                            List<PgroupPriceTypeResponse> pgroupPriceTypeResponseGrandTotal = productGroupPriceMapper.listCloneTypeTotal(productGroupPriceResponses.get(i).getPgroupId(), pgroupPriceResponse.get(j).getFromCost(), pgroupPriceResponse.get(j).getToCost());

                            //! Set price type value
                            pgroupPriceResponse.get(j).setPriceTypeResponses(pgroupPriceTypeResponse);

                            //! Set price type grand total value
                            pgroupPriceResponse.get(0).setPriceTypeGrandTotalResponses(pgroupPriceTypeResponseGrandTotal);

                            //! Find grand total product & value
                            grandTotalProduct += pgroupPriceResponse.get(j).getTotalProduct();
                            grandTotalValue += pgroupPriceResponse.get(j).getValue();
                            grandTotalFromCost += pgroupPriceResponse.get(j).getFromCost();
                            grandTotalToCost += pgroupPriceResponse.get(j).getToCost();
                        }
                        grandTotalValue = grandTotalValue / pgroupPriceResponse.size();
                    }

                    //! Set grand total product & value
                    pgroupPriceResponse.get(0).setGrandTotalProduct(grandTotalProduct);
                    pgroupPriceResponse.get(0).setGrandTotalValue(grandTotalValue); 
                    pgroupPriceResponse.get(0).setGrandTotalFromCost(grandTotalFromCost);
                    pgroupPriceResponse.get(0).setGrandTotalToCost(grandTotalToCost);

                    productGroupPriceResponses.get(i).setPgroupPriceResponses(pgroupPriceResponse);
                }

            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group-price/list-clone",null,null,"product group","product group (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", productGroupPriceResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group-price-setting/list-clone",line, error.toString(),"Product Group Price Setting","Product Group Price Setting (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


}
