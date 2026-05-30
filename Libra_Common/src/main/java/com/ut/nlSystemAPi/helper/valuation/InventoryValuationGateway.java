package com.ut.nlSystemAPi.helper.valuation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface InventoryValuationGateway {

    List<InventoryValuationCalcDTO> getInventoryValuationCalcs();

    List<InventoryValuationCalcDTO> getInventoryValuationCalcsFallback();

    void updateValuationCalcLock(Long productId, LocalDate date, LocalDateTime runningDate);

    InventoryValuationDTO getInitialInventoryValuation(Long productId, LocalDate calDate);

    List<InventoryValuationDTO> getInventoryValuationsForCalculation(Long productId, LocalDate calDate);

    ProductDTO getProductDefaultCost(Long productId);

    void updateInventoryValuation(Long id, BigDecimal onHand, BigDecimal onHandSmall, BigDecimal cost, BigDecimal avgCost, BigDecimal assetValue);

    void updateGeneralLedgerCredit(Long inventoryValuationId, BigDecimal credit);

    void updateGeneralLedgerCogsCredit(Long inventoryValuationId, BigDecimal credit);

    void updateGeneralLedgerCogsDebit(Long inventoryValuationId, BigDecimal debit);

    void updateGeneralLedgerCogsZero(Long inventoryValuationId);

    void deleteValuationCalc(Long productId, LocalDate date);
}
