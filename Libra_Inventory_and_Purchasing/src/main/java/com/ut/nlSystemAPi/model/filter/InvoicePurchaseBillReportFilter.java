package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InvoicePurchaseBillReportFilter extends Filter {

    @ApiModelProperty(position = 6)
    private String dateFrom;

    @ApiModelProperty(position = 7)
    private String dateTo;

    @ApiModelProperty(position = 8)
    private Long status;

    @ApiModelProperty(position = 9)
    private Long companyId;

    @ApiModelProperty(position = 10)
    private Long locationId;

    @ApiModelProperty(position = 11)
    private Long vendorId;

    @ApiModelProperty(position = 11)
    private Long productId;

    @ApiModelProperty(position = 11)
    private Long createBy;

    @ApiModelProperty(position = 11)
    private Long includeVat;

    @ApiModelProperty(position = 11)
    private Long paymentTermId;

    @ApiModelProperty(position = 11,hidden = true)
    private Long userId;

}
