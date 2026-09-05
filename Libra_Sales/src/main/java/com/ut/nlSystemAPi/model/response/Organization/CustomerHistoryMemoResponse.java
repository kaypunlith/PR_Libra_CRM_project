package com.ut.nlSystemAPi.model.response.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerHistoryMemoResponse {

    @ApiModelProperty(position = 4)
    private String status; // "Close Sales", "Increase", "Reduce", "Terminate", "Extra Service"

    @ApiModelProperty(position = 5, example = "1")
    private Integer qty;

    @ApiModelProperty(position = 6)
    private String memoStatus;

    @ApiModelProperty(position = 7)
    private String date;

    @ApiModelProperty(position = 8)
    private List<String> services;

    @ApiModelProperty(position = 9)
    private Long quotationId;

    @ApiModelProperty(position = 10)
    private Long terminateId;

    @ApiModelProperty(position = 11)
    private Integer printCount;
}
