package com.ut.nlSystemAPi.model.response.Reconcile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ReconcileDetailResponse {

    @ApiModelProperty(position = 15)
    private Long id;

    @ApiModelProperty(position = 16)
    private String date;

    @ApiModelProperty(position = 17)
    private String reference;

    @ApiModelProperty(position = 17)
    private String name;

    @ApiModelProperty(position = 18)
    private Double debit;

    @ApiModelProperty(position = 19)
    private Double credit;
}
