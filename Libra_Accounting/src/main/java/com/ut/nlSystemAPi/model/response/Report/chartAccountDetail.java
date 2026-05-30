package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data

public class chartAccountDetail {

    @ApiModelProperty(position = 1)
    private  Long charAccountId;

    @ApiModelProperty(position = 1)
    private   String chartAccountName;

    @ApiModelProperty(position = 1)
    private   Double totalAmount;

    @ApiModelProperty(position = 1)
    private  Long parentId;

    @ApiModelProperty(position = 1)
    private  String parentName;

    @ApiModelProperty(position = 1)
    private List<ChartAccountSubDetail> chartAccountSubDetails;

}
