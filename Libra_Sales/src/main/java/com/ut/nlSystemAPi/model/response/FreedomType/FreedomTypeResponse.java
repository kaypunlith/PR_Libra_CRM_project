package com.ut.nlSystemAPi.model.response.FreedomType;

import lombok.Data;

@Data
public class FreedomTypeResponse {
    private Long id;
    private String name;
    private Long priceTypeId;
    private String priceTypeName;
}
