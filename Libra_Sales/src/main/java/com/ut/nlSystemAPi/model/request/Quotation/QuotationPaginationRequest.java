package com.ut.nlSystemAPi.model.request.Quotation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class QuotationPaginationRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long page;

    @ApiModelProperty(position = 3)
    private Long itemPerPage;

}
