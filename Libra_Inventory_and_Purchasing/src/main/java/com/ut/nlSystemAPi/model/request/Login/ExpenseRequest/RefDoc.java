package com.ut.nlSystemAPi.model.request.Login.ExpenseRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RefDoc {

    @ApiModelProperty(position = 2)
    private String url;

    @ApiModelProperty(position = 3)
    private String name;

}
