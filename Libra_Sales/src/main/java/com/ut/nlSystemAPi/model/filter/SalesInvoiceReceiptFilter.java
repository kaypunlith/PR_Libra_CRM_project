package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data

public class SalesInvoiceReceiptFilter extends Filter {

    @ApiModelProperty(position = 11)
    private Long id;

    @ApiModelProperty(position = 13)
    private Long salesInvoiceId;

    @ApiModelProperty(position = 15)
    private Double balance;

}
