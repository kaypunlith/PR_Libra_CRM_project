package com.ut.nlSystemAPi.model.response.Quotation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuotationProductInfoResponse {

    @ApiModelProperty(position = 1)
    private String code;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String uomAbbr;

    @ApiModelProperty(position = 4)
    private Double unitPrice;

    @ApiModelProperty(position = 5)
    private String productName;

    @ApiModelProperty(position = 5)
    private String productPhotoUrl;

    @ApiModelProperty(position = 5)
    private String productPhotoName;

    @ApiModelProperty(position = 5)
    private String spec;

    @ApiModelProperty(position = 5)
    private String sku;

    @ApiModelProperty(position = 5)
    private String upc;

    @ApiModelProperty(position = 5)
    private List<QuotationProductInfoResponse> details;
}

