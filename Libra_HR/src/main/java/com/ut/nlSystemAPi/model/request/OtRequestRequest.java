package com.ut.nlSystemAPi.model.request;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OtRequestRequest extends BaseModel {

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
