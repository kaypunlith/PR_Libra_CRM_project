package com.ut.nlSystemAPi.model.response.Valuation;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InventoryValuationDTO {
    private Long id;
    private Integer isVarCost;
    private Integer isAdjustValue;
    private Long salesOrderId;
    private Long pid;
    private BigDecimal smallQty;
    private BigDecimal qty;
    private LocalDate date;
    private BigDecimal cost;
    private BigDecimal price;
    private BigDecimal onHand;
    private BigDecimal onHandSmall;
    private BigDecimal avgCost;
    private BigDecimal assetValue;
    private String smallValUom;
}
