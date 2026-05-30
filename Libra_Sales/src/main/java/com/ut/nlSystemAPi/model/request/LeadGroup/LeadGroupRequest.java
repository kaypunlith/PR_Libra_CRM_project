package com.ut.nlSystemAPi.model.request.LeadGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LeadGroupRequest {

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private List<Long> employeeGroups;

    @ApiModelProperty(position = 4)
    private List<Long> leads;
}
