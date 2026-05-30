package com.ut.nlSystemAPi.model.entity.Logistic;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogisticReceive extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String type;

    @ApiModelProperty(position = 3)
    private Long logisticId;

    @ApiModelProperty(position = 4)
    private Long logisticReceiveResultId;

    @ApiModelProperty(position = 5)
    private String saleInvoiceDetailId;

    @ApiModelProperty(position = 6)
    private String saleInvoiceId;

    @ApiModelProperty(position = 7)
    private Long itemId;

    @ApiModelProperty(position = 8)
    private Long qty;

    @ApiModelProperty(position = 9)
    private Long uomId;

    @ApiModelProperty(position = 10)
    private Long conversion;

    @ApiModelProperty(position = 11)
    private String deliveryDate;

}