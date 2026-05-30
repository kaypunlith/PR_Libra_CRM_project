package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepartmentFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long id;

    @ApiModelProperty(position = 20)
    private Long telegramCheck;

}
