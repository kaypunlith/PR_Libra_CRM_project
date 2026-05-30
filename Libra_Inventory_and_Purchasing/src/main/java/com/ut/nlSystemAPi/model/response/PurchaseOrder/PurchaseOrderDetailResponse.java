package com.ut.nlSystemAPi.model.response.PurchaseOrder;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseOrderDetailResponse extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long itemId;

    @ApiModelProperty(position = 4)
    private String itemName;

    @ApiModelProperty(position = 4)
    private String itemCode;

    @ApiModelProperty(position = 4)
    private String sku;

    @ApiModelProperty(position = 4)
    private String upc;

    @ApiModelProperty(position = 5)
    private Long qty;

    @ApiModelProperty(position = 5)
    private Long conversion;

    @ApiModelProperty(position = 6)
    private Long uomId;

    @ApiModelProperty(position = 7)
    private String uomName;

    @ApiModelProperty(position = 7)
    private String uomAbbr;

    @ApiModelProperty(position = 8)
    private Double unitCost;

    @ApiModelProperty(position = 9)
    private Double totalCost;

    @ApiModelProperty(position = 10)
    private String note;
}
