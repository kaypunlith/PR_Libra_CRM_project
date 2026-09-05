package com.ut.nlSystemAPi.model.filter.Dropdown;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OpportunityStageDropdownFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long pipelineId;

    @ApiModelProperty(position = 11)
    private Long stageId;
}
