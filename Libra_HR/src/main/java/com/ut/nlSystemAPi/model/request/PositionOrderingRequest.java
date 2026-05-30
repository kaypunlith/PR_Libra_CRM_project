package com.ut.nlSystemAPi.model.request;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PositionOrderingRequest extends BaseModel {

    @ApiModelProperty(position = 1)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private Long id;

    @ApiModelProperty(position = 2)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private Long ordering;
}
