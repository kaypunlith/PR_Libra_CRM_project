package com.ut.nlSystemAPi.model.request.Quotation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuotationShareOptionRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long shareSaveOption;

    @ApiModelProperty(position = 3)
    private Long shareOption;

    @ApiModelProperty(position = 3)
    private List<Long> shareUser;

    @ApiModelProperty(position = 4)
    private List<Long> shareExceptUser;

}
