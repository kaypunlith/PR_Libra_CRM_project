package com.ut.nlSystemAPi.model.response.Quotation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuotationCreator {

    @ApiModelProperty(position = 1)
    private Long createdBy;

    @ApiModelProperty(position = 2)
    private String created;

    @ApiModelProperty(position = 3)
    private String createdByName;

}
