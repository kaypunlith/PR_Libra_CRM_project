package com.ut.nlSystemAPi.model.entity.OrganizationGroup;

import lombok.Data;

@Data
public class OrganizationGroup {
    private Long id;
    private String name;
    private Long createdBy;
    private Long modifiedBy;
    private Integer isActive;
}