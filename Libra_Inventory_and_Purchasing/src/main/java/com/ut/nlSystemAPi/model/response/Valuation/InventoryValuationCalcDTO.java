package com.ut.nlSystemAPi.model.response.Valuation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InventoryValuationCalcDTO {
    private Long productId;
    private LocalDate date;
}