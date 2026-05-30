package com.ut.nlSystemAPi.model.response;

import com.ut.nlSystemAPi.model.LeaveRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReportDepositRequest implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Float amount;

    @ApiModelProperty(position = 2)
    private String date;

}
