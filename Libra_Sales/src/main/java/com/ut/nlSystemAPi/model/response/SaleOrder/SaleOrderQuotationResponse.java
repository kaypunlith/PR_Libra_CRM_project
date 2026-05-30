package com.ut.nlSystemAPi.model.response.SaleOrder;

import com.ut.nlSystemAPi.model.response.TermCondition.TermConditionResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SaleOrderQuotationResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 3)
    private String code;

}