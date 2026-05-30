package com.ut.nlSystemAPi.model.response.PriceRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Represents a summarized response for price request merges.
 */
@Data
public class PriceRequestResponseSummary {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String requestDate;

    @ApiModelProperty(position = 3)
    private String priceRequest;

    @ApiModelProperty(position = 4)
    private String requestBy;

    @ApiModelProperty(position = 5)
    private String organizationName;

    @ApiModelProperty(position = 6)
    private String brand;

    @ApiModelProperty(position = 6)
    private Long qty;

    @ApiModelProperty(position = 7)
    private String prStatusName;

    @ApiModelProperty(position = 8)
    private Long telegramPushAmount;

    @ApiModelProperty(position = 8)
    private Long isConvert;

    @ApiModelProperty(position = 9)
    private String note;

    @ApiModelProperty(position = 10)
    private String leadTimeName;

    @ApiModelProperty(position = 11)
    private Long ctp;

    @ApiModelProperty(position = 12)
    private Long status;

    @ApiModelProperty(position = 14)
    private String model;
}
