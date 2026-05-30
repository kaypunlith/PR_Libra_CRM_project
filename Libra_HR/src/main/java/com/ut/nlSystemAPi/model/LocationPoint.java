package com.ut.nlSystemAPi.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LocationPoint implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 80)
    private String lats;

    @ApiModelProperty(position = 90)
    private String longs;

    

}
