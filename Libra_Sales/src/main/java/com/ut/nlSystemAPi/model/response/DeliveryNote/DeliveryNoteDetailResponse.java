package com.ut.nlSystemAPi.model.response.DeliveryNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryNoteDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String poNo;

    @ApiModelProperty(position = 3)
    private String productName;

    @ApiModelProperty(position = 4)
    private String sku;

    @ApiModelProperty(position = 4)
    private String spec;

    @ApiModelProperty(position = 4)
    private String itemBrand;

    @ApiModelProperty(position = 4)
    private String uomAbbr;

    @ApiModelProperty(position = 4)
    private Double qty;

    @ApiModelProperty(position = 4)
    private Double qtyOrder;

    @ApiModelProperty(position = 4)
    private Double qtyFree;

    @ApiModelProperty(position = 4)
    private Double unitCost;

    @ApiModelProperty(position = 4)
    private Double smallValUom;

    @ApiModelProperty(position = 4)
    private Integer status;

}
