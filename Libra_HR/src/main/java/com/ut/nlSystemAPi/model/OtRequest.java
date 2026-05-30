package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OtRequest extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 1)
    private String dateForm;

    @ApiModelProperty(position = 1)
    private String dateTo;

    @ApiModelProperty(position = 1)
    private String duration;

    @ApiModelProperty(position = 1)
    private String reason;
}
