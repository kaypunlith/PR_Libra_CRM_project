package com.ut.nlSystemAPi.model.request.ClassPartnerManagement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassPartnerManagementUpdateRequest extends ClassPartnerManagementRequest {
    @ApiModelProperty(position = 0)
    private Long id;
}
