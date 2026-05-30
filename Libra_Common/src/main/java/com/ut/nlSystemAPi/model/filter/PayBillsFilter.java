package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.FilterBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PayBillsFilter extends FilterBase{

    @ApiModelProperty(position = 5, hidden = true)
    private Long userId;

    @ApiModelProperty(position = 5)
    private Long locationId;

    @ApiModelProperty(position = 6)
    private Long companyId;

    @ApiModelProperty(position = 7)
    private Long vendorId;

    @ApiModelProperty(position = 8)
    private Long branchId;

}
