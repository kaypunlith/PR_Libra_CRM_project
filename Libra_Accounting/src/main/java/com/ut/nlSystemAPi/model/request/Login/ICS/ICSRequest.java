package com.ut.nlSystemAPi.model.request.Login.ICS;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ICSRequest {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private List<ICSDetailRequest> detailRequests;

}
