package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductTransferConsignmentFilter extends Filter {

    @ApiModelProperty(position = 5)
    private List<String> barCode;

    @ApiModelProperty(position = 7)
    private Long departmentFromId;

    @ApiModelProperty(position = 8)
    private Long departmentToId;

}
