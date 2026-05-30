package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProFundTotalYearList implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String year;

    @ApiModelProperty(position = 3)
    private String title;

    @ApiModelProperty(position = 4)
    private Float totalAmount;

}
