package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.filter.BillReturnFilter;
import com.ut.nlSystemAPi.model.filter.BillReturnReceiptFilter;
import com.ut.nlSystemAPi.model.filter.StatusFilter;
import com.ut.nlSystemAPi.model.response.BillReturn.BillReturnDetailResponse;
import com.ut.nlSystemAPi.model.response.BillReturn.BillReturnReceiptResponse;
import com.ut.nlSystemAPi.model.response.BillReturn.BillReturnResponse;
import com.ut.nlSystemAPi.model.response.BillReturn.VatSettingResponse;
import com.ut.nlSystemAPi.model.response.PurchaseBill.PurchaseBillDetailResponse;
import com.ut.nlSystemAPi.model.response.PurchaseBill.PurchaseBillResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillReturnMapper {

  List<BillReturnResponse> getList(@Param("filter") BillReturnFilter filter, @Param("userId") Long userId);

  Long countList(@Param("filter") BillReturnFilter filter, @Param("userId") Long userId);

  List<BillReturnResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("billReturn") BillReturn billReturn);

  Boolean archive(@Param("id") Long id, @Param("userId") Long userId);

  Boolean update(@Param("billReturn") BillReturn billReturn);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Boolean updateStatus(@Param("filter") StatusFilter filter, @Param("userId") Long userId);

  String getLastCode();

  String getLastCodeReceipt();

  Boolean insertReceipt(@Param("receipt") BillReturnReceipt receipt);

  Boolean updateBalancePurchaseReturn(@Param("billReturn") BillReturn billReturn);

  Boolean updateBalancePurchaseOrder(@Param("purchaseOrderId") Long purchaseOrderId, @Param("balance") Double balance, @Param("type") Long type, @Param("userId") Long userId);

  Boolean insertReceiptWithPbs(@Param("receipt") BillReturnWithPbsReceipt receipt);

  Boolean insertGeneralLedger(@Param("generalLedger") GeneralLedger generalLedger);

  Long getActiveGeneralLedgerId(@Param("purchaseReturnId") Long purchaseReturnId);

  Boolean deleteInventoryValuation(@Param("billReturnId") Long billReturnId);

  Boolean deleteGeneralLedger(@Param("billReturnId") Long billReturnId);

  Boolean insertInventoryValuation(@Param("inventoryValuation") InventoryValuation inventoryValuation);

  Boolean insertGeneralLedgerDetail(@Param("generalLedgerDetail") GeneralLedgerDetail generalLedgerDetail);

  Long getClassByLocationGroupId(@Param("companyId")Long companyId, @Param("locationGroupId") Long locationGroupId);

  List<BillReturnDetailResponse> getDetail(@Param("purchaseReturnId") Long purchaseReturnId);

  Boolean insertDetail(@Param("billReturnDetail") BillReturnDetail billReturnDetail);

  Boolean deleteDetail(@Param("purchaseReturnId") Long purchaseReturnId);

  Boolean insertService(@Param("billReturnServices") BillReturnServices billReturnServices);

  Boolean deleteService(@Param("purchaseReturnId") Long purchaseReturnId);

  Boolean insertMisc(@Param("billReturnMisc") BillReturnMisc billReturnMisc);

  Boolean deleteMisc(@Param("purchaseReturnId") Long purchaseReturnId);

  Long getConversionByUomId(@Param("uomId") Long uomId);

  List<VatSettingResponse> getVatSetting(@Param("vatSettingId") Long vatSettingId);

  Boolean insertTableInventories(@Param("tableName") String tableName, @Param("inventories") Inventories inventories);

  Boolean voidReceipt(@Param("request") BillReturnReceiptFilter request, @Param("userId") Long userId);

  Boolean voidReceiptApplyPb(@Param("request") BillReturnReceiptFilter request, @Param("userId") Long userId);

  Boolean updateBalance(@Param("billReturnId") Long id, @Param("balance") Double balance, @Param("type") Long type);

  List<BillReturnReceiptResponse> listReceipt(@Param("filter") BillReturnReceiptFilter filter);

  List<BillReturnReceiptResponse> listReceiptApplyPb(@Param("filter") BillReturnReceiptFilter filter);

  Long getSmallValUom(@Param("productId") Long productId);

  String getPrCode(@Param("id") Long id);

  String getSqlTrack();

  String getOrderDate(@Param("id") Long id);

  Boolean updateTrack(@Param("val") String val);

}
