package com.ut.nlSystemAPi.model.response.MembershipCard;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MembershipCardResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long membershipCardLevelId;

    @ApiModelProperty(position = 3)
    private String membershipCardLevelName;

    @ApiModelProperty(position = 4)
    private String membershipCardLevelColor;

    @ApiModelProperty(position = 5)
    private Long customerId;

    @ApiModelProperty(position = 6)
    private String customerName;

    @ApiModelProperty(position = 7)
    private String customerCode;

    @ApiModelProperty(position = 8)
    private String cardNo;

    @ApiModelProperty(position = 9)
    private String cardDateStart;

    @ApiModelProperty(position = 10)
    private String cardDateEnd;

    @ApiModelProperty(position = 11)
    private Double currentPoint;

    @ApiModelProperty(position = 12)
    private Double totalPoint;

    @ApiModelProperty(position = 13)
    private Integer period;

    @ApiModelProperty(position = 14)
    private String created;

    @ApiModelProperty(position = 15)
    private String createdBy;

    @ApiModelProperty(position = 16)
    private String modified;

    @ApiModelProperty(position = 17)
    private String modifiedBy;
}
