package com.ut.nlSystemAPi.model.request.Login.ProductPriceList;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ProductPriceListUpdateRequest {
    @ApiModelProperty(position = 3)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private Long organizationId;

    @ApiModelProperty(position = 5)
    private String title;

    @ApiModelProperty(position = 6)
    private String dateValidateTo;

    @ApiModelProperty(position = 7)
    private Long pgroupId;

    @ApiModelProperty(position = 8)
    private Long priceTypeId;

    @ApiModelProperty(position = 8)
    private String dateValidateFrom;

    @ApiModelProperty(position = 8)
    private List<ProductPriceListDetails> productPriceListDetails;


}
