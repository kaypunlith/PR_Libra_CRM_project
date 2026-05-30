package com.ut.nlSystemAPi.model.entity.MembershipCard;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MembershipCard extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long membershipTypeId;

    @ApiModelProperty(position = 4)
    private Long customerId;

    @ApiModelProperty(position = 5)
    private String cardId;

    @ApiModelProperty(position = 6)
    private String cardDateStart;

    @ApiModelProperty(position = 7)
    private String cardDateEnd;

    @ApiModelProperty(position = 8)
    private Double currentPoint;

    @ApiModelProperty(position = 9)
    private Double totalPoint;

    @ApiModelProperty(position = 10)
    private Integer period;
}
