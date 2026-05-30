package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class CashFlowGroupResponse {

    @ApiModelProperty(position = 3)
    private Long groupId;

    @ApiModelProperty(position = 4)
    private String groupName;

    @ApiModelProperty(position = 5)
    private String typeName;

    @ApiModelProperty(position = 5)
    private Double totalAmount;

    @ApiModelProperty(position = 6)
    private List<ChartAccountResponse> chartAccountResponses;

    @ApiModelProperty(position = 1)
    private List<ChartAccountSubDetail> chartAccountSubDetails;

}
