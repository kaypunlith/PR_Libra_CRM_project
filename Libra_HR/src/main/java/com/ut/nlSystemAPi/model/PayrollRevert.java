package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayrollRevert extends BaseModel implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String payDate;

    @ApiModelProperty(position = 3)
    private Float currentSalary;

    @ApiModelProperty(position = 3)
    private Float oldSalary;

    @ApiModelProperty(position = 4)
    private Float increaseSalary;

    @ApiModelProperty(position = 5)
    private Float totalAmount;

}
