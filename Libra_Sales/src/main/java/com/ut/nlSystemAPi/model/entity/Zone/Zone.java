package com.ut.nlSystemAPi.model.entity.Zone;

import lombok.Data;

@Data
public class Zone {
    private Long id;
    private String name;
    private String lats;
    private String longs;
    private String radius;
    private Long createdBy;
    private Long modifiedBy;
    private Integer isActive;
}
