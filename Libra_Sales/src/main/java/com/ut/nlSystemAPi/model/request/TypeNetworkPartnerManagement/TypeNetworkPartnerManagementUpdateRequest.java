package com.ut.nlSystemAPi.model.request.TypeNetworkPartnerManagement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TypeNetworkPartnerManagementUpdateRequest extends TypeNetworkPartnerManagementRequest {
    @ApiModelProperty(position = 0)
    private Long id;
}
