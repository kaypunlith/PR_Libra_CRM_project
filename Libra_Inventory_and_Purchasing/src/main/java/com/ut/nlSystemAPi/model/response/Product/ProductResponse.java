package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ProductResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long groupId;

    @ApiModelProperty(position = 3)
    private String groupName;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String companyName;

    @ApiModelProperty(position = 3)
    private Long brandId;

    @ApiModelProperty(position = 4)
    private String brandName;

    @ApiModelProperty(position = 4)
    private String photo;

    @ApiModelProperty(position = 4)
    private String photoName;

    @ApiModelProperty(position = 4)
    private String fileCatalog;

    @ApiModelProperty(position = 4)
    private String fileCatalogName;

    @ApiModelProperty(position = 5)
    private String name;

    @ApiModelProperty(position = 5)
    private String nameKh;

    @ApiModelProperty(position = 5)
    private Long isEndOfLife;

    @ApiModelProperty(position = 6)
    private String upc;

    @ApiModelProperty(position = 7)
    private String sku;

    @ApiModelProperty(position = 8)
    private Long uomId;

    @ApiModelProperty(position = 9)
    private String url;

    @ApiModelProperty(position = 9)
    private String uomName;

    @ApiModelProperty(position = 10)
    private Double unitCost;

    @ApiModelProperty(position = 12)
    private Double estimateCost;

    @ApiModelProperty(position = 13)
    private String spec;

    @ApiModelProperty(position = 14)
    private String priceModified;

    @ApiModelProperty(position = 14)
    private String priceModifiedBy;

    @ApiModelProperty(position = 14)
    private String latestCostUpdate;

    @ApiModelProperty(position = 14)
    private String latestCostUpdateBy;

    @ApiModelProperty(position = 14)
    private String created;

    @ApiModelProperty(position = 15)
    private String createdBy;

    @ApiModelProperty(position = 16)
    private String modified;

    @ApiModelProperty(position = 17)
    private String modifiedBy;

    @ApiModelProperty(position = 18)
    private Integer isActive;

    @ApiModelProperty(position = 19)
    private String luckyCode;

}
