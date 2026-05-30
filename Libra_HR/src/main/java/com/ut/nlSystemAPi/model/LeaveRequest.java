package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class LeaveRequest extends BaseModel implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 3)
    private String dateFrom;

    @ApiModelProperty(position = 4)
    private String dateTo;

    @ApiModelProperty(position = 4)
    private Float numberOfDay;

    @ApiModelProperty(position = 5)
    private String note;

    @ApiModelProperty(position = 4)
    private Long leaveTypeId;
}
