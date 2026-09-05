package com.ut.nlSystemAPi.model.request.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityStageDocumentRequest {

    @ApiModelProperty(position = 1)
    private Long customerId;

    @ApiModelProperty(position = 2)
    private Long crmOpportunityId;

    @ApiModelProperty(position = 2)
    private String imageName;

    @ApiModelProperty(position = 3)
    private String imageUrl;

    @ApiModelProperty(position = 4)
    private String startDate;

    @ApiModelProperty(position = 5)
    private String endDate;

    @ApiModelProperty(position = 6)
    private String address;

}
