package com.ut.nlSystemAPi.model.request.Logistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LogisticMergeDetailRequest {

    @ApiModelProperty(position = 1)
    private Integer type;

    @ApiModelProperty(position = 2)
    private Long id;

}