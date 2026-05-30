package com.ut.nlSystemAPi.model.entity.FreedomType;

import lombok.Data;

@Data
public class FreedomType {
    private Long id;
    private String name;
    private Long priceTypeId;
    private Long createdBy;
    private Long modifiedBy;
    private Integer isActive;
}
