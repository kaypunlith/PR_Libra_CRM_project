package com.ut.nlSystemAPi.model.entity.Logistic;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LogisticReceiveResult extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String organizationContactId;

    @ApiModelProperty(position = 3)
    private String saleInvoiceId;

    @ApiModelProperty(position = 9)
    private String note;

    @ApiModelProperty(position = 9)
    private String date;

}