package com.ut.nlSystemAPi.model.response.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityStageDocumentResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long customerId;

    @ApiModelProperty(position = 3)
    private Long crmOpportunityId;

    @ApiModelProperty(position = 3)
    private String imageName;

    @ApiModelProperty(position = 4)
    private String imageUrl;

    @ApiModelProperty(position = 5)
    private String startDate;

    @ApiModelProperty(position = 6)
    private String endDate;

    @ApiModelProperty(position = 7)
    private String address;

    @ApiModelProperty(position = 8)
    private String created;

    @ApiModelProperty(position = 9)
    private String createdBy;

    @ApiModelProperty(position = 10)
    private String modified;

    @ApiModelProperty(position = 11)
    private String modifiedBy;
}
