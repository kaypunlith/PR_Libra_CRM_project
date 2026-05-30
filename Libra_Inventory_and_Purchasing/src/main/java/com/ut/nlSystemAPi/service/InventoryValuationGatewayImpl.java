package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.valuation.InventoryValuationCalcDTO;
import com.ut.nlSystemAPi.helper.valuation.InventoryValuationDTO;
import com.ut.nlSystemAPi.helper.valuation.InventoryValuationGateway;
import com.ut.nlSystemAPi.helper.valuation.ProductDTO;
import com.ut.nlSystemAPi.mapper.primary.InventoryValuationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryValuationGatewayImpl implements InventoryValuationGateway {

    private final InventoryValuationMapper inventoryValuationMapper;

    @Autowired
    public InventoryValuationGatewayImpl(InventoryValuationMapper inventoryValuationMapper) {
        this.inventoryValuationMapper = inventoryValuationMapper;
    }

    @Override
    public List<InventoryValuationCalcDTO> getInventoryValuationCalcs() {
        return inventoryValuationMapper.getInventoryValuationCalcs();
    }

    @Override
    public List<InventoryValuationCalcDTO> getInventoryValuationCalcsFallback() {
        return inventoryValuationMapper.getInventoryValuationCalcsFallback();
    }

    @Override
    public void updateValuationCalcLock(Long productId, LocalDate date, LocalDateTime runningDate) {
        inventoryValuationMapper.updateValuationCalcLock(productId, date, runningDate);
    }

    @Override
    public InventoryValuationDTO getInitialInventoryValuation(Long productId, LocalDate calDate) {
        return inventoryValuationMapper.getInitialInventoryValuation(productId, calDate);
    }

    @Override
    public List<InventoryValuationDTO> getInventoryValuationsForCalculation(Long productId, LocalDate calDate) {
        return inventoryValuationMapper.getInventoryValuationsForCalculation(productId, calDate);
    }

    @Override
    public ProductDTO getProductDefaultCost(Long productId) {
        return inventoryValuationMapper.getProductDefaultCost(productId);
    }

    @Override
    public void updateInventoryValuation(Long id, BigDecimal onHand, BigDecimal onHandSmall, BigDecimal cost, BigDecimal avgCost, BigDecimal assetValue) {
        inventoryValuationMapper.updateInventoryValuation(id, onHand, onHandSmall, cost, avgCost, assetValue);
    }

    @Override
    public void updateGeneralLedgerCredit(Long inventoryValuationId, BigDecimal credit) {
        inventoryValuationMapper.updateGeneralLedgerCredit(inventoryValuationId, credit);
    }

    @Override
    public void updateGeneralLedgerCogsCredit(Long inventoryValuationId, BigDecimal credit) {
        inventoryValuationMapper.updateGeneralLedgerCogsCredit(inventoryValuationId, credit);
    }

    @Override
    public void updateGeneralLedgerCogsDebit(Long inventoryValuationId, BigDecimal debit) {
        inventoryValuationMapper.updateGeneralLedgerCogsDebit(inventoryValuationId, debit);
    }

    @Override
    public void updateGeneralLedgerCogsZero(Long inventoryValuationId) {
        inventoryValuationMapper.updateGeneralLedgerCogsZero(inventoryValuationId);
    }

    @Override
    public void deleteValuationCalc(Long productId, LocalDate date) {
        inventoryValuationMapper.deleteValuationCalc(productId, date);
    }
}
