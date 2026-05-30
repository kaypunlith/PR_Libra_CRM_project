package com.ut.nlSystemAPi.model.request.MembershipCard;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MembershipCardRequest {

    @ApiModelProperty(position = 2)
    private Long membershipCardLevelId;

    @ApiModelProperty(position = 3)
    private Long customerId;

    @ApiModelProperty(position = 4)
    private String cardDateStart;

    @ApiModelProperty(position = 5)
    private String cardDateEnd;

    @ApiModelProperty(position = 6)
    private Double currentPoint;

    @ApiModelProperty(position = 7)
    private Double totalPoint;

    @ApiModelProperty(position = 8)
    private Integer period;
}
