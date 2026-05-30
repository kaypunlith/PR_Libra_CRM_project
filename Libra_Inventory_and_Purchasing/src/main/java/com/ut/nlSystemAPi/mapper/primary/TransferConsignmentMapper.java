package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.GlobalStock;
import com.ut.nlSystemAPi.model.StockOrders;
import com.ut.nlSystemAPi.model.TransferConsignment;
import com.ut.nlSystemAPi.model.TransferConsignmentDetail;
import com.ut.nlSystemAPi.model.filter.ProductTransferConsignmentFilter;
import com.ut.nlSystemAPi.model.filter.TransferConsignmentFilter;
import com.ut.nlSystemAPi.model.response.TransferConsignment.TransferConsignmentLocationResponse;
import com.ut.nlSystemAPi.model.response.TransferConsignment.ProductConsignmentDetailResponse;
import com.ut.nlSystemAPi.model.response.TransferConsignment.TransferConsignmentDetailResponse;
import com.ut.nlSystemAPi.model.response.TransferConsignment.TransferConsignmentResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferConsignmentMapper {

  List<TransferConsignmentResponse> getList(@Param("filter") TransferConsignmentFilter filter, @Param("userId") Long userId);

  String getLastToCode();

  Long getConversionByUomId(@Param("uomId") Long uomId);

  Long countList(@Param("filter") TransferConsignmentFilter filter, @Param("userId") Long userId);

  Long countListProduct(@Param("filter") ProductTransferConsignmentFilter filter);

  List<ProductConsignmentDetailResponse> listProduct(@Param("filter") ProductTransferConsignmentFilter filter);

  List<TransferConsignmentResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("transferConsignment") TransferConsignment transferConsignment);

  List<TransferConsignmentDetailResponse> getTransferConsignmentDetail(@Param("transferConsignmentId") Long transferConsignmentId);

  Boolean insertTransferConsignmentDetail(@Param("transferConsignmentDetail") TransferConsignmentDetail transferConsignmentDetail);

  List<TransferConsignmentLocationResponse> getLocationProduct(@Param("locationGroupId") Long locationGroupId);

  Boolean deleteTransferConsignmentDetail(@Param("transferOrderId") Long transferOrderId);

  Boolean update(@Param("transferConsignment") TransferConsignment transferConsignment);

  Boolean archive(@Param("id") Long id, @Param("userId") Long userId);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  String getCode(@Param("id") Long id);

  Boolean approve(@Param("id") Long id, @Param("status") Long status, @Param("userId") Long userId);

  String getLastCode(@Param("code") String code);
  //! Stock
  Double getTotalQtyStock(@Param("departmentId") Long departmentId, @Param("tableName") String tableName, @Param("productId") Long productId);

  Double getTotalQtyStockInOrder(@Param("departmentId") Long departmentId, @Param("productId") Long productId);

  Double getLocationStock(@Param("tblName") String tblName, @Param("locationId") Long locationId, @Param("productId") Long productId);

  Boolean insertStockOrder(@Param("stockOrders") StockOrders stockOrders);

  //! Global Stock
  Boolean insertGroupTotal(@Param("stockOrders") GlobalStock stockOrders, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation);

  Boolean insertGroupTotalDetail(@Param("stockOrders") GlobalStock stockOrders, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation, @Param("fieldName") String fieldName);

  Boolean insertInventoryTotal(@Param("stockOrders") GlobalStock stockOrders, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation);

  Boolean insertInventoryTotalDetail(@Param("stockOrders") GlobalStock stockOrders, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation, @Param("fieldName") String fieldName);

  Boolean insertInventories(@Param("stockOrders") GlobalStock stockOrders, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation);

  Boolean insertInventoryTotalAll(@Param("stockOrders") GlobalStock stockOrders, @Param("typeOperation") Long typeOperation, @Param("fieldName") String fieldName);

  Boolean insertInventoriesAll(@Param("stockOrders") GlobalStock stockOrders, @Param("typeOperation") Long typeOperation);

}
