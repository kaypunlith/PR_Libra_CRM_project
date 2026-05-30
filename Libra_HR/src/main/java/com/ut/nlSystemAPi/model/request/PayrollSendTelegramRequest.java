package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayrollSendTelegramRequest implements Serializable {

    @ApiModelProperty(position = 1)
    private String payDate;

    @ApiModelProperty(position = 2)
    private Long employeesId;


}
