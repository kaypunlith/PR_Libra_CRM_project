package com.ut.nlSystemAPi.model.entity.PayMethod;

import lombok.Data;

@Data
public class PayMethod {
    private Long id;
    private String name;
    private Long chartAccountId;
    private Long createdBy;
    private Long modifiedBy;
    private Integer isActive;
}
