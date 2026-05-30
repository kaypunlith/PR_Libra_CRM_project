package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TableShift extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 80)
    private Long employeeId;

    @ApiModelProperty(position = 90)
    private Long workShiftId;

    @ApiModelProperty(position = 100)
    private Long totalDay;

    @ApiModelProperty(position = 100)
    private Float paid;


    

}
