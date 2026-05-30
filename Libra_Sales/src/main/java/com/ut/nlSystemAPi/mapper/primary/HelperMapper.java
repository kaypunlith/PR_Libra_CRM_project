package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.mapper.primary.CodeCountMapper;
import com.ut.nlSystemAPi.model.base.*;
import com.ut.nlSystemAPi.model.GeneralLedger;
import com.ut.nlSystemAPi.model.GeneralLedgerDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HelperMapper extends CodeCountMapper {

    Long countRecords(
            @Param("table") String table,
            @Param("field") String field,
            @Param("pattern") String pattern,
            @Param("status") String status
    );

    Boolean updateCode(@Param("tableName") String tableName, @Param("field") String field, @Param("code") String code, @Param("id") Long id);

    String getCurrentCode(@Param("tableName") String tableName, @Param("field") String field, @Param("id") Long id);

    String getCompanyModuleCode(@Param("companyId") Long companyId, @Param("field") String field);



    // Archive OR Delete Records
    Boolean archive(@Param("tableName") String tableName, @Param("field") String field, @Param("value") Integer value, @Param("id") Long id, @Param("userId") Long userId);

    Long getMessageId(@Param("tableName") String tableName, @Param("id") Long id);

    Boolean updateMessageId(@Param("tableName") String tableName, @Param("messageId") Long messageId, @Param("id") Long id);

    Long getCompanyCurrencyCenter(@Param("companyId") Long companyId);

    Integer getVatCalculate(@Param("companyId") Long companyId);

    Long checkDataExisting(@Param("tableName") String tableName, @Param("id") Long id);

    Integer checkLocationSetting(@Param("id") Integer id);

    Integer checkSettingOption();

    Boolean updateStatus(@Param("tableName") String tableName, @Param("field") String field, @Param("status") Long status, @Param("userId") Long userId, @Param("id") Long id);

    Long getSmallValUom(@Param("productId") Long productId);

    Double getUnitCost(@Param("productId") Long productId);

    List<StockOrder> getStockExisting(@Param("salesInvoiceId") Long salesInvoiceId, @Param("productId") Long productId, @Param("warehouseId") Long warehouseId, @Param("locationStatus") Integer locationStatus);

    List<StockOrder> getStockOrder(@Param("salesInvoiceId") Long salesInvoiceId, @Param("productId") Long productId, @Param("warehouseId") Long warehouseId, @Param("locationStatus") Integer locationStatus);





    //! Global Stock
    Boolean insertGroupTotal(@Param("stock") GlobalStock stock, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation);

    Boolean insertGroupTotalDetail(@Param("stock") GlobalStock stock, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation, @Param("fieldName") String fieldName, @Param("fieldNameFoc") String fieldNameFoc);

    Boolean insertInventoryTotal(@Param("stock") GlobalStock stock, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation);

    Boolean insertInventoryTotalDetail(@Param("stock") GlobalStock stock, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation, @Param("fieldName") String fieldName);

    Boolean insertInventories(@Param("stock") GlobalStock stock, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation);

    Boolean insertInventoryTotalAll(@Param("stock") GlobalStock stock, @Param("typeOperation") Long typeOperation, @Param("fieldName") String fieldName, @Param("fieldNameFoc") String fieldNameFoc);

    Boolean insertInventoriesAll(@Param("stock") GlobalStock stock, @Param("typeOperation") Long typeOperation);
    //!

    //! Global Order
    Boolean insertGroupTotalOrder(@Param("stock") GlobalStock stock, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation);

    Boolean insertGroupTotalDetailOrder(@Param("stock") GlobalStock stock, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation);

    Boolean insertInventoryTotalOrder(@Param("stock") GlobalStock stock, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation);

    Boolean insertInventoryTotalDetailOrder(@Param("stock") GlobalStock stock, @Param("tblName") String tblName, @Param("typeOperation") Long typeOperation);

    Boolean insertInventoryTotalAllOrder(@Param("stock") GlobalStock stock, @Param("typeOperation") Long typeOperation);
    //!




    Boolean insertGeneralLedger(@Param("generalLedger") GeneralLedger generalLedger);

    Boolean insertGeneralLedgerDetail(@Param("detail") GeneralLedgerDetail generalLedgerDetail);

    Boolean insertInventoryValuation(@Param("valuation") InventoryValuation valuation);


    Long getCmChartAccountId();

    Long getClassId(@Param("companyId") Long companyId, @Param("locationGroupId") Long locationGroupId);

    Long getProductIncomeChartAccountId(@Param("id") Long id);

    Long getProductInventoryChartAccountId(@Param("id") Long id);

    Long getProductCOGSChartAccountId(@Param("id") Long id);

    Long getServiceChartAccountId(@Param("id") Long id);

    Long getServiceUnearnChartAccountId(@Param("id") Long id);

    Long getMiscChartAccountId();

    Long getDiscountChartAccountId(@Param("id") Long id);

    Long getVatChartAccountId(@Param("vatSettingId") Long vatSettingId);

    Long getMarkUpChartAccountId(@Param("id") Long id);

    Long getVatSettingRateId(@Param("vatSettingId") Long vatSettingId);

    ProductCodeNameResponse getProductCodeName(@Param("productId") Long productId);

    ProductCodeNameResponse getServiceName(@Param("serviceId") Long serviceId);

    Long checkInvoiceProduct(@Param("saleInvoiceId") Long saleInvoiceId);

    Boolean deleteGeneralLedger(@Param("field") String field, @Param("id") Long id, @Param("userId") Long userId);

}

