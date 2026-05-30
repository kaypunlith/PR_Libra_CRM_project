package com.ut.nlSystemAPi.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WorkShiftUpdateRequest extends WorkShiftRequest {

    @ApiModelProperty(position = 11)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private Long id;
    

    
}
