package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeDropdownFilter extends Filter {

    @ApiModelProperty(position = 14)
    private Integer isSaleRep;

    @ApiModelProperty(position = 15)
    private Integer employeeGroupId;

}
