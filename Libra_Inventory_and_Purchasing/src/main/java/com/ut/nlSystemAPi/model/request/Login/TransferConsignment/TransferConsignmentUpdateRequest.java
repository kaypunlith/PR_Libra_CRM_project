package com.ut.nlSystemAPi.model.request.Login.TransferConsignment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TransferConsignmentUpdateRequest extends TransferConsignmentRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}
