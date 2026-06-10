package com.ut.nlSystemAPi.model.response.LeadGroup;

import com.ut.nlSystemAPi.model.response.Lead.LeadDetailResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LeadGroupResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 2)
    private String companyName;

    @ApiModelProperty(position = 3)
    private String leadGroupName;

    @ApiModelProperty(position = 4)
    private String employeeGroupName;

    @ApiModelProperty(position = 5)
    private String created;

    @ApiModelProperty(position = 6)
    private String createdBy;

    @ApiModelProperty(position = 7)
    private String modified;

    @ApiModelProperty(position = 8)
    private String modifiedBy;

    @ApiModelProperty(position = 9)
    private List<LeadDetailResponse> companies;

    @ApiModelProperty(position = 10)
    private List<LeadDetailResponse> employeeGroups;

    @ApiModelProperty(position = 11)
    private List<LeadDetailResponse> leads;
}
