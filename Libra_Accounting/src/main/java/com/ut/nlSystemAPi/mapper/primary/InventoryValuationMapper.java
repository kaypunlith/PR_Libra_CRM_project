package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.helper.valuation.InventoryValuationCalcDTO;
import com.ut.nlSystemAPi.helper.valuation.InventoryValuationDTO;
import com.ut.nlSystemAPi.helper.valuation.ProductDTO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface InventoryValuationMapper {

    List<InventoryValuationCalcDTO> getInventoryValuationCalcs();

    List<InventoryValuationCalcDTO> getInventoryValuationCalcsFallback();

    void updateValuationCalcLock(@Param("productId") Long productId, @Param("date") LocalDate date, @Param("runningDate") LocalDateTime runningDate);

    InventoryValuationDTO getInitialInventoryValuation(@Param("productId") Long productId, @Param("calDate") LocalDate calDate);

    List<InventoryValuationDTO> getInventoryValuationsForCalculation(@Param("productId") Long productId, @Param("calDate") LocalDate calDate);

    ProductDTO getProductDefaultCost(@Param("productId") Long productId);

    void updateInventoryValuation(
            @Param("id") Long id,
            @Param("onHand") BigDecimal onHand,
            @Param("onHandSmall") BigDecimal onHandSmall,
            @Param("cost") BigDecimal cost,
            @Param("avgCost") BigDecimal avgCost,
            @Param("assetValue") BigDecimal assetValue
    );

    void updateGeneralLedgerCredit(@Param("inventoryValuationId") Long inventoryValuationId, @Param("credit") BigDecimal credit);

    void updateGeneralLedgerCogsCredit(@Param("inventoryValuationId") Long inventoryValuationId, @Param("credit") BigDecimal credit);

    void updateGeneralLedgerCogsDebit(@Param("inventoryValuationId") Long inventoryValuationId, @Param("debit") BigDecimal debit);

    void updateGeneralLedgerCogsZero(@Param("inventoryValuationId") Long inventoryValuationId);

    void deleteValuationCalc(@Param("productId") Long productId, @Param("date") LocalDate date);

}
