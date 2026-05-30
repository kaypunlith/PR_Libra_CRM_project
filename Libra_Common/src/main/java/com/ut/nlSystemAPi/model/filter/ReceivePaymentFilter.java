package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.FilterBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReceivePaymentFilter extends FilterBase{

    @ApiModelProperty(position = 5, hidden = true)
    private Long userId;

    @ApiModelProperty(position = 6)
    private Long groupId;

    @ApiModelProperty(position = 7)
    private Long organizationId;

    @ApiModelProperty(position = 8)
    private Long contactId;

    @ApiModelProperty(position = 9)
    private Long type;

    @ApiModelProperty(position = 9)
    private Long branchId;

}
