package com.ut.nlSystemAPi.model.request.Login.PurchaseReceive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateReceiveRequest {
    @ApiModelProperty(position =1)
    private Long id;

    @ApiModelProperty(position =2)
    private Long type;

    @ApiModelProperty(position =3)
    private String code;
}
