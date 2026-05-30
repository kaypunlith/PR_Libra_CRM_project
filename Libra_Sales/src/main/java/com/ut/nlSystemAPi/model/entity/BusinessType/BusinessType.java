package com.ut.nlSystemAPi.model.entity.BusinessType;

import lombok.Data;

@Data
public class BusinessType {
    private Long id;
    private String name;
    private Long createdBy;
    private Long modifiedBy;
    private Integer isActive;
}