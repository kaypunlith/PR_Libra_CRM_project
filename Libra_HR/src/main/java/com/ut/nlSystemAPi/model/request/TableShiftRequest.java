package com.ut.nlSystemAPi.model.request;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TableShiftRequest extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long shift;

    @ApiModelProperty(position = 2)
    private Long totalDay;

    @ApiModelProperty(position = 2)
    private Float paid;


}
