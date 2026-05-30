package com.ut.nlSystemAPi.model.request.Login.TransferConsignment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class TransferConsignmentReceiveRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String trDate;

    @ApiModelProperty(position = 2)
    private String trNumber;

}
