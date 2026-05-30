package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerContactFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long customerId;

    @ApiModelProperty(position = 11)
    private Long organizationId;
    
}

