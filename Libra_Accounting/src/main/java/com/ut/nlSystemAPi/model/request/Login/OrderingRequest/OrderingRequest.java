package com.ut.nlSystemAPi.model.request.Login.OrderingRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderingRequest {

    @ApiModelProperty(position = 1)
    private Long moduleId;

    @ApiModelProperty(position = 2)
    private Long listId;

    @ApiModelProperty(position = 3)
    private Long ordering;

}
