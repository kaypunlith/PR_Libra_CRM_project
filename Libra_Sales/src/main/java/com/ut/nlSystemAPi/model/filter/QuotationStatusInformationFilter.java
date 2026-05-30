package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuotationStatusInformationFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long quotationId;

}
