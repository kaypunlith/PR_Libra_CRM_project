package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LocationPointResponse implements Serializable {

    @ApiModelProperty(position = 10)
    private Long id;

    @ApiModelProperty(position = 42)
    private String lats;

    @ApiModelProperty(position = 43)
    private String longs;

}
