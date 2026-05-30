package com.ut.nlSystemAPi.model.request.Quotation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuotationUpdateRequest extends QuotationRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}
