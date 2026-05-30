package com.ut.nlSystemAPi.model.response.VendorManagement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class VendorDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String symbol;

    @ApiModelProperty(position = 4)
    private Double exchangeRate;

}
