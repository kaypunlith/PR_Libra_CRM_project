package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProvidentFundRequest {

    @ApiModelProperty(position = 1)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String requestDate;

    @ApiModelProperty(position = 1)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String filterMonth;

    @ApiModelProperty(position = 2)
    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String title;

    @ApiModelProperty(position = 4)
    private List<ProvidentFundRequestDetail> providentFundDetails;
}
