package com.ut.nlSystemAPi.model.entity.Market;

import lombok.Data;

@Data
public class Market {
    private Long id;
    private String name;
    private Long zoneId;
    private Long createdBy;
    private Long modifiedBy;
    private Integer isActive;
}
