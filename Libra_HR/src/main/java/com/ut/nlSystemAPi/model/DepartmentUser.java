package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class DepartmentUser {

    @ApiModelProperty(position = 1)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private Long applyUser;

}
