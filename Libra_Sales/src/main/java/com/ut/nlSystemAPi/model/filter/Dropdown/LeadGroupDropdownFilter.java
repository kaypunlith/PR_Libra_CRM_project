package com.ut.nlSystemAPi.model.filter.Dropdown;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LeadGroupDropdownFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long companyId;
}
