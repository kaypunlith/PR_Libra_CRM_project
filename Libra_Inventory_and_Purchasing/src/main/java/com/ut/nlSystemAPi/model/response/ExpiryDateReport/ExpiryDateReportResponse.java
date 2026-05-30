package com.ut.nlSystemAPi.model.response.ExpiryDateReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ExpiryDateReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private String code;

    @ApiModelProperty(position = 1)
    private String upc;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 2)
    private String expiryDate;

    @ApiModelProperty(position = 2)
    private Long qty;

    @ApiModelProperty(position = 2)
    private String created;

    @ApiModelProperty(position = 2)
    private String days;

    @ApiModelProperty(position = 2)
    private String uomName;

    @ApiModelProperty(position = 2)
    private Long uomId;
}
