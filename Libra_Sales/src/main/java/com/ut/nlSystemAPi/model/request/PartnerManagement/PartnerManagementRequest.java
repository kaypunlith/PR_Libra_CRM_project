package com.ut.nlSystemAPi.model.request.PartnerManagement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PartnerManagementRequest {
    @ApiModelProperty(position = 1)
    private String partnerAreaExpertise;

    @ApiModelProperty(position = 2)
    private String partnerName;

    @ApiModelProperty(position = 3)
    private String keyContactName;

    @ApiModelProperty(position = 4)
    private String keyContactTel;

    @ApiModelProperty(position = 5)
    private String keyContactEmail;

    @ApiModelProperty(position = 6)
    private String why;

    @ApiModelProperty(position = 7)
    private Long typeNetworkId;

    @ApiModelProperty(position = 8)
    private Long classId;

    @ApiModelProperty(position = 9)
    private Long industryId;

    @ApiModelProperty(position = 10)
    private Long positionId;

    @ApiModelProperty(position = 11)
    private String origin;

    @ApiModelProperty(position = 12)
    private String refBy;

    @ApiModelProperty(position = 13)
    private Integer annualReview;

    @ApiModelProperty(position = 14)
    private String relationship;

    @ApiModelProperty(position = 15)
    private Long employeeId;
}
