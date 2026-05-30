package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganizationFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long companyId;

    @ApiModelProperty(position = 11)
    private Long organizationGroupId;

    @ApiModelProperty(position = 12)
    private Long createdBy;

    @ApiModelProperty(position = 13)
    private Integer activeStatus;

    @ApiModelProperty(position = 14)
    private Integer havingBalance;

}
