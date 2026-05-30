package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PriceRequestInformationResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long productId;

    @ApiModelProperty(position = 3)
    private String requestCode;

    @ApiModelProperty(position = 4)
    private String requestDate;

    @ApiModelProperty(position = 5)
    private String requestBy;

    @ApiModelProperty(position = 6)
    private String convertBy;

    @ApiModelProperty(position = 7)
    private String closedDate;

    @ApiModelProperty(position = 8)
    private String closedBy;

    @ApiModelProperty(position = 9)
    private String leadTimeName;

    @ApiModelProperty(position = 10)
    private String madeInCountryName;

    @ApiModelProperty(position = 11)
    private String shipFromCountryName;

    @ApiModelProperty(position = 12)
    private String note;

}