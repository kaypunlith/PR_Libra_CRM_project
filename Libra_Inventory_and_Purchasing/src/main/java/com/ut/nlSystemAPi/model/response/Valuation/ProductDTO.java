package com.ut.nlSystemAPi.model.response.Valuation;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;
    private BigDecimal defaultCost;
}