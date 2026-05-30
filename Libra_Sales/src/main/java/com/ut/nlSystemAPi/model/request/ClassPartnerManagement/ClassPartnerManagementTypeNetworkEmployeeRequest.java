package com.ut.nlSystemAPi.model.request.ClassPartnerManagement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClassPartnerManagementTypeNetworkEmployeeRequest {
    @ApiModelProperty(position = 1)
    private Long typeNetworkId;

    @ApiModelProperty(position = 2)
    private List<Long> userIds;
}
