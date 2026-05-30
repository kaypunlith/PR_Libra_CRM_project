package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.FilterBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReceivePaymentPrintFilter extends FilterBase{

    @ApiModelProperty(position = 5)
    private String reference;

    @ApiModelProperty(position = 6)
    private Long organizationId;

    @ApiModelProperty(position = 7)
    private String dateFrom;

    @ApiModelProperty(position = 8)
    private String dateTo;

}
