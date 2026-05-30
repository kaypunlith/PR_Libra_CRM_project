package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.filter.PurchaseBillFilter;
import com.ut.nlSystemAPi.model.response.PurchaseBill.PurchaseBillDetailResponse;
import com.ut.nlSystemAPi.model.response.PurchaseBill.PurchaseBillResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseBillMapper {

  List<PurchaseBillResponse> getList(@Param("filter") PurchaseBillFilter filter, @Param("userId") Long userId);

  Long countList(@Param("filter") PurchaseBillFilter filter, @Param("userId") Long userId);

  List<PurchaseBillResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("purchaseBill") PurchaseBill purchaseBill);

  Boolean insertGeneralLedger(@Param("generalLedger") GeneralLedger generalLedger);

  Boolean insertGeneralLedgerDetail(@Param("generalLedgerDetail") GeneralLedgerDetail generalLedgerDetail);

  Long getClassByLocationGroupId(@Param("companyId")Long companyId, @Param("locationGroupId") Long locationGroupId);

  Boolean update(@Param("purchaseBill") PurchaseBill purchaseBill);

  Boolean archive(@Param("id") Long id, @Param("userId") Long userId);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Boolean approve(@Param("id") Long id, @Param("status") Long status, @Param("userId") Long userId);

  Long getPurchaseRequestId(@Param("purchaseBillId") Long purchaseBillId);

  String getPoCode(@Param("id") Long id);

  String getLastCode(@Param("code") String code);

  Long getConversionByUomId(@Param("uomId") Long uomId);

  List<PurchaseBillDetailResponse> getDetail(@Param("id") Long id);

  Boolean insertDetail(@Param("purchaseBillDetail") PurchaseBillDetail purchaseBillDetail);

  Boolean deleteDetail(@Param("purchaseOrderId") Long purchaseOrderId);

  Boolean insertInventoryValuation(@Param("inventoryValuation") InventoryValuation inventoryValuation);

  Boolean deleteInventoryValuation(@Param("purchaseBillId") Long purchaseBillId);

  Boolean deleteGeneralLedger(@Param("purchaseBillId") Long purchaseBillId);

  Boolean insertService(@Param("purchaseBillServices") PurchaseBillServices purchaseBillServices);

  Boolean deleteService(@Param("purchaseOrderId") Long purchaseOrderId);

  Boolean insertMisc(@Param("purchaseBillMisc") PurchaseBillMisc purchaseBillMisc);

  Boolean deleteMisc(@Param("purchaseOrderId") Long purchaseOrderId);

  Long getERByPo(@Param("purchaseOrderId") Long purchaseOrderId);

  Boolean updateIsClosePo(@Param("isClose") Integer isClose, @Param("purchaseOrderId") Long purchaseOrderId);

  Boolean insertFile(@Param("purchaseOrderFile") PurchaseOrderFile purchaseOrderFile);

  Boolean deleteFiles(@Param("purchaseOrderId") Long purchaseOrderId);

  List<com.ut.nlSystemAPi.model.response.PurchaseBill.PurchaseBillFileResponse> getFiles(@Param("purchaseOrderId") Long purchaseOrderId);

  Long getCalculateCog();

  Long getProductIncomeChartAccountId(@Param("id") Long id);

  Long getProductCOGSChartAccountId(@Param("id") Long id);

  Long getServiceChartAccountId(@Param("id") Long id);

  Long getMiscChartAccountId();

  Long getDiscountChartAccountId();

  Long getVatChartAccountId(@Param("vatSettingId") Long vatSettingId);

  Long getCurrencyCenterId(@Param("companyId") Long companyId);

  Long getVatCalculate(@Param("companyId") Long companyId);

  String getProductName(@Param("id") Long id);

  String getServiceName(@Param("id") Long id);

  Long checkPoInGoodReceiptNote(@Param("purchaseOrderId") Long purchaseOrderId);

  Boolean autoFulfilled(@Param("purchaseBillId") Long purchaseBillId);

  List<Long> getPbRelatedPo(@Param("purchaseOrderId") Long purchaseOrderId);

  Long getQtyPo(@Param("purchaseOrderId") Long purchaseOrderId);

  Long getQtyPb(@Param("purchaseBillIds") List<Long> purchaseBillId);

  Boolean updateStatusPo(@Param("purchaseOrderId") Long purchaseOrderId);

  Boolean updateInventoryValuationPurchaseBillId(@Param("purchaseBillId") Long purchaseBillId, @Param("purchaseOrderId") Long purchaseOrderId);

}
