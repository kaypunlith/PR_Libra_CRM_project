package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateReceive extends BaseModel {
    @ApiModelProperty(position =1)
    private Long id;

    @ApiModelProperty(position =3)
    private Long purchaseOrderId;

    @ApiModelProperty(position =4)
    private Long type;

    @ApiModelProperty(position =5)
    private String code;

    @ApiModelProperty(position =6)
    private String date;

}
