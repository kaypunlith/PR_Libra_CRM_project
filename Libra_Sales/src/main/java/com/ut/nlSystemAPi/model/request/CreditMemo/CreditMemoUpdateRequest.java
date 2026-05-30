package com.ut.nlSystemAPi.model.request.CreditMemo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class CreditMemoUpdateRequest extends CreditMemoRequest{

    @ApiModelProperty(position = 1)
    private Long id;

}
