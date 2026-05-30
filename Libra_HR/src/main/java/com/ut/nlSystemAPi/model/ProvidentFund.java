package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.response.ProvidentFundDetailResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProvidentFund extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String requestDate;

    @ApiModelProperty(position = 2)
    private String filterMonth;

    @ApiModelProperty(position = 3)
    private String title;

    @ApiModelProperty(position = 4)
    private Float amountRequest;

    @ApiModelProperty(position = 7, hidden = true)
    private Boolean valid;

    @ApiModelProperty(position = 7)
    private List<ProvidentFundDetailResponse> providentFundDetails;

    @ApiModelProperty(position = 8)
    private List<ProvidentFundDetailResponse> providentFundDetailList;

}
