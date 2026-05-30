package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.filter.LandedCostFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.response.LandedCost.LandedCostDetailResponse;
import com.ut.nlSystemAPi.model.response.LandedCost.LandedCostPurchaseBillResponse;
import com.ut.nlSystemAPi.model.response.LandedCost.LandedCostResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandedCostMapper {

  List<LandedCostResponse> getList(@Param("filter") LandedCostFilter filter, @Param("userId") Long userId);

  Long countList(@Param("filter") LandedCostFilter filter, @Param("userId") Long userId);

  List<LandedCostResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("landedCost") LandedCost landedCost);

  Boolean deleteLandedCostPurchaseBill(@Param("landedCostId") Long landedCostId);

  Boolean insertLandedCostPurchaseBill(@Param("landedCostPurchaseBill") LandedCostPurchaseBill landedCostPurchaseBill);

  Boolean deleteGeneralLedger(@Param("landedCostId") Long landedCostId);

  Boolean insertGeneralLedger(@Param("generalLedger") GeneralLedger generalLedger);

  Boolean insertGeneralLedgerDetail(@Param("generalLedgerDetail") GeneralLedgerDetail generalLedgerDetail);

  Boolean archive(@Param("id") Long id, @Param("userId") Long userId);

  Boolean update(@Param("landedCost") LandedCost landedCost);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Boolean closeStatus(@Param("filter")StatusFilter filter, @Param("userId") Long userId);

  String getCode(@Param("id") Long id);

  String getLastCode();

  String getProductName(@Param("id") Long id);

  String getServiceName(@Param("id") Long id);

  String getLandedCostTypeName(@Param("id") Long id);

  Long getConversionByUomId(@Param("uomId") Long uomId);

  List<LandedCostDetailResponse> getDetail(@Param("id") Long id);

  List<LandedCostDetailResponse> getData(@Param("id") Long id);

  List<LandedCostPurchaseBillResponse> getLandedCostPurchaseBill(@Param("id") Long id);

  Boolean insertDetail(@Param("landedCostDetail") LandedCostDetail landedCostDetail);

  Boolean deleteDetail(@Param("landedCostId") Long landedCostId);

  Boolean insertService(@Param("landedCostServices") LandedCostServices landedCostServices);

  Boolean deleteService(@Param("landedCostId") Long landedCostId);

  Boolean updateInventoryValuation(@Param("newCost") Double newCost, @Param("purchaseBillDetailId") Long purchaseBillDetailId);

  String getOrderDate(@Param("purchaseBillId") Long purchaseBillId);

  Boolean insertInventoryValuationCals(@Param("orderDate") String orderDate, @Param("productId") Long productId);

  Boolean updateProductCost(@Param("unitCost") Double unitCost, @Param("productId") Long productId);

}