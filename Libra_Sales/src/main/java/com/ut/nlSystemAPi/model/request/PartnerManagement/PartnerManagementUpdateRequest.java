package com.ut.nlSystemAPi.model.request.PartnerManagement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PartnerManagementUpdateRequest extends PartnerManagementRequest {
    @ApiModelProperty(position = 0)
    private Long id;
}
