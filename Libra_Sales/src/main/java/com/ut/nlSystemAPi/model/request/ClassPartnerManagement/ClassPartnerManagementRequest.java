package com.ut.nlSystemAPi.model.request.ClassPartnerManagement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClassPartnerManagementRequest {
    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private List<Long> employeeGroupIds;

    @ApiModelProperty(position = 3)
    private List<ClassPartnerManagementTypeNetworkEmployeeRequest> typeNetworkEmployees;
}
