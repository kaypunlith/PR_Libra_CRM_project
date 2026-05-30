package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.request.Login.ProductPriceList.ProductPriceListDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ProductPriceList extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private String validateThrough;

    @ApiModelProperty(position = 5)
    private Long companyId;

    @ApiModelProperty(position = 6)
    private Long customerId;

    @ApiModelProperty(position = 7)
    private String title;

    @ApiModelProperty(position =8)
    private String dateValidateTo;

    @ApiModelProperty(position = 9)
    private Long pgroupId;

    @ApiModelProperty(position = 10)
    private Long priceTypeId;

    @ApiModelProperty(position = 11)
    private String dateValidateFrom;

    @ApiModelProperty(position = 10)
    private Integer isApproved;

    @ApiModelProperty(position = 12)
    private List<ProductPriceListDetails> productPriceListDetails;
}
