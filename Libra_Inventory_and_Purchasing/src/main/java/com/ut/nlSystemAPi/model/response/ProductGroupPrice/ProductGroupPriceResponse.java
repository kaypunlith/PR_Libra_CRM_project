package com.ut.nlSystemAPi.model.response.ProductGroupPrice;

import com.ut.nlSystemAPi.model.request.Login.ProductGroupPrice.PgroupPriceRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductGroupPriceResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String companyName;

    @ApiModelProperty(position = 4)
    private Long parentId;

    @ApiModelProperty(position = 4)
    private String parentName;

    @ApiModelProperty(position = 6)
    private Long pgroupId;

    @ApiModelProperty(position = 7)
    private String pgroupName;

    @ApiModelProperty(position = 8)
    private Long priceTypeId;

    @ApiModelProperty(position = 8)
    private String priceTypeName;

    @ApiModelProperty(position = 9)
    private Long setType;

    @ApiModelProperty(position = 10)
    private Long costMethod;

    @ApiModelProperty(position = 9)
    private Long applyToAllProduct;

    @ApiModelProperty(position = 9)
    private Long isActive;

    @ApiModelProperty(position = 10)
    List<PgroupPriceResponse> pgroupPriceResponses;

    @ApiModelProperty(position = 11)
    private String createdBy;

    @ApiModelProperty(position = 12)
    private String created;
}
