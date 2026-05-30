package com.ut.nlSystemAPi.model.entity.Zone;

import lombok.Data;

@Data
public class ZoneDetail {
    private Long id;
    private Long customerZoneId;
    private String lats;
    private String longs;
}
