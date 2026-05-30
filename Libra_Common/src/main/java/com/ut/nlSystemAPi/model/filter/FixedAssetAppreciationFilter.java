package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FixedAssetAppreciationFilter extends Filter {
    
    @ApiModelProperty(position = 5, hidden = true)
    private Long companyId;

    @ApiModelProperty(position = 5, hidden = true)
    private Long branchId;

    @ApiModelProperty(position = 6, hidden = true)
    private Long accountTypeId;

    @ApiModelProperty(position = 7, hidden = true)
    private Long accountGroupId;

    @ApiModelProperty(position = 8, hidden = true)
    private Long locationId;

    @ApiModelProperty(position = 9, hidden = true)
    private Long vendorId;

    @ApiModelProperty(position = 10, hidden = true)
    private Long type;

}

