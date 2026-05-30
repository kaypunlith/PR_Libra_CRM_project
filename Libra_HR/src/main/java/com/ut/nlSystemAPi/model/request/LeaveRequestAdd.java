package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LeaveRequestAdd {

    @ApiModelProperty(position = 1)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String DateFrom;

    @ApiModelProperty(position = 2)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String DateTo;

    @ApiModelProperty(position = 3)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private Long employeeId;

    @ApiModelProperty(position = 4)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String description;

    @ApiModelProperty(position = 5)
    private Float numberOfDay;

    @ApiModelProperty(position = 4)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private Long leaveTypeId;

}
