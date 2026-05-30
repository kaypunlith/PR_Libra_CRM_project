package com.ut.nlSystemAPi.helper.valuation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InventoryValuationCalcDTO {
    private Long productId;
    private LocalDate date;
}
