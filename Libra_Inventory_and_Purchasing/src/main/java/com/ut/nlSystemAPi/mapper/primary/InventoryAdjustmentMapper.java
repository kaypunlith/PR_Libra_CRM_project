package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.GlobalStock;
import com.ut.nlSystemAPi.model.InventoryAdjustment;
import com.ut.nlSystemAPi.model.InventoryAdjustmentDetail;
import com.ut.nlSystemAPi.model.InventoryValuation;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.BaseFilter;
import com.ut.nlSystemAPi.model.filter.InventoryAdjustmentFilter;
import com.ut.nlSystemAPi.model.filter.QtyOnHandFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.response.InventoryAdjustment.InventoryAdjustmentDetailsResponse;
import com.ut.nlSystemAPi.model.response.InventoryAdjustment.InventoryAdjustmentResponse;
import com.ut.nlSystemAPi.model.response.InventoryAdjustment.ProductInStockResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryAdjustmentMapper {

  List<InventoryAdjustmentResponse> getList(@Param("filter") InventoryAdjustmentFilter filter, @Param("userId") Long userId);

  Long countList(@Param("filter") InventoryAdjustmentFilter filter, @Param("userId") Long userId);

  List<InventoryAdjustmentResponse> getOne(@Param("id") Long id);

  List<InventoryAdjustmentDetailsResponse> getInventoryAdjustmentDetail(@Param("inventoryAdjustmentId") Long inventoryAdjustmentId);

  Long checkDuplicate(@Param("code") String code, @Param("id") Long id);

  Boolean insert(@Param("inventoryAdjustment") InventoryAdjustment inventoryAdjustment);

  Boolean insertInventoryAdjustmentDetail(@Param("inventoryAdjustmentDetail") InventoryAdjustmentDetail inventoryAdjustmentDetail);

  Boolean deleteInventoryAdjustmentDetail(@Param("inventoryAdjustmentDetailId") Long inventoryAdjustmentDetailId);

  Boolean update(@Param("inventoryAdjustment") InventoryAdjustment inventoryAdjustment);

  Boolean archive(@Param("id") Long id, @Param("userId") Long userId);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Boolean updateStatus(@Param("filter") BaseFilter filter, @Param("userId") Long userId);

  String getCode(@Param("id") Long id);

  String getLastCode();

  Long getCpType(@Param("id") Long id);

  Long getAdjType(@Param("id") Long id);

  Long findTotalQtyFromDetails(@Param("tableNameDetail") String tableNameDetail, @Param("filter") QtyOnHandFilter filter);

  Long findTotalQtyFromTotals(@Param("tableName") String tableName,@Param("filter") QtyOnHandFilter filter);

  Boolean insertInventoryValuation(@Param("inventoryValuation") InventoryValuation inventoryValuation);

  List<ProductInStockResponse> listProductInStock(@Param("tableName") String tableName);

  Boolean resetGroupTotals(@Param("tableName") String tableName);

  Boolean resetGroupTotalDetails(@Param("tableName") String tableName);

  Boolean resetInventoryTotals(@Param("tableName") String tableName);

  Boolean resetInventoryTotalDetails(@Param("tableName") String tableName);

  Boolean resetInventories(@Param("globalStock") GlobalStock globalStock, @Param("tblName") String tblName);

  Boolean resetInventoriesAll(@Param("globalStock") GlobalStock globalStock);

  Boolean resetInventoryTotalsAll(@Param("warehouseId") Long warehouseId);

}
