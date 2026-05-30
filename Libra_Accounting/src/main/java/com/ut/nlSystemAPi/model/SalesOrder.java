package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SalesOrder {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(position = 1)
    private Long id;
    @ApiModelProperty(position = 2)
    private Long customerId;
    @ApiModelProperty(position = 3)
    private Double balance;
    @ApiModelProperty(position = 4)
    private Integer type;
    @ApiModelProperty(position = 5)
    private Integer status;
}
