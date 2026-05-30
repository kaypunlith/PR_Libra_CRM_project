package com.ut.nlSystemAPi.model.entity.ClassPartnerManagement;

import lombok.Data;

@Data
public class ClassPartnerManagement {
    private Long id;
    private String name;
    private Long createdBy;
    private Long modifiedBy;
    private Integer isActive;
}
