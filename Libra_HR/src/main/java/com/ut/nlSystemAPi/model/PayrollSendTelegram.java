package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayrollSendTelegram extends BaseModel implements Serializable {
    @ApiModelProperty(position = 2)
    private String payDate;

    @ApiModelProperty(position = 3)
    private Long employeesId;

    @ApiModelProperty(position = 5)
    private String fileImageName;

    @ApiModelProperty(position = 6)
    private String fileImageUrl;

}
