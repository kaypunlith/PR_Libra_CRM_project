package com.ut.nlSystemAPi.model.filter.Dropdown;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrganizationDropdownFilter extends Filter {

    @ApiModelProperty(position = 11)
    private Long organizationGroupId;

    @ApiModelProperty(position = 11)
    private Integer isAnnouncement;

    @ApiModelProperty(position = 12)
    private Long employeeId;

    @ApiModelProperty(position = 12)
    private Long IsOpportunity;

}
