package com.ut.nlSystemAPi.model.filter.Dropdown;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DropdownFilter extends Filter {

    @ApiModelProperty(position = 9)
    private Integer termConditionModules;

    @ApiModelProperty(position = 10)
    private String dateFrom;

    @ApiModelProperty(position = 11)
    private String dateTo;

    @ApiModelProperty(position = 11)
    private Long organizationGroupId;

}
