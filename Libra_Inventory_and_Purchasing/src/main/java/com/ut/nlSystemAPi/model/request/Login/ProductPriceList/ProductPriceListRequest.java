package com.ut.nlSystemAPi.model.request.Login.ProductPriceList;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductPriceListRequest {

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private Long organizationId;

    @ApiModelProperty(position = 3)
    private String title;

    @ApiModelProperty(position = 4)
    private String dateValidateTo;

    @ApiModelProperty(position = 5)
    private String dateValidateFrom;

    @ApiModelProperty(position = 6)
    private Long pgroupId;

    @ApiModelProperty(position = 7)
    private Long priceTypeId;

    @ApiModelProperty(position = 8)
    private List<ProductPriceListDetails> productPriceListDetails;









}
