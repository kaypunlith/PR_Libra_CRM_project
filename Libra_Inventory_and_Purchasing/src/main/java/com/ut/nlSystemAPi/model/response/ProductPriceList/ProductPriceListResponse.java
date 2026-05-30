package com.ut.nlSystemAPi.model.response.ProductPriceList;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductPriceListResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private String code;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 2)
    private String title;

    @ApiModelProperty(position = 3)
    private String validateThrough;

    @ApiModelProperty(position = 4)
    private Long companyId;

    @ApiModelProperty(position = 5)
    private String companyName;

    @ApiModelProperty(position = 6)
    private Long organizationId;

    @ApiModelProperty(position = 7)
    private String organizationName;

    @ApiModelProperty(position = 8)
    private Long productGroupId;

    @ApiModelProperty(position = 9)
    private String productGroupName;

    @ApiModelProperty(position = 10)
    private Long itemAmount;

    @ApiModelProperty(position = 11)
    private Long priceTypeId;

    @ApiModelProperty(position = 12)
    private String priceTypeName;

    @ApiModelProperty(position = 13)
    private String created;

    @ApiModelProperty(position = 14)
    private String createdBy;

    @ApiModelProperty(position = 15)
    private String modified;

    @ApiModelProperty(position = 16)
    private String modifiedBy;

    @ApiModelProperty(position = 17)
    private Long isApprove;

    @ApiModelProperty(position = 17)
    private Long isClose;

    @ApiModelProperty(position = 18)
    private List<ProductPriceListDetailResponse> productPriceListDetailResponses;


}
