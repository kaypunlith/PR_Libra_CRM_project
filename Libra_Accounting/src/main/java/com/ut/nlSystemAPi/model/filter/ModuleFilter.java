package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ModuleFilter extends Filter {

    @ApiModelProperty(position = 101)
    private Long moduleTypeId;

}
