package com.ut.nlSystemAPi.model.entity.Opportunities;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityStageDocument extends BaseModel {

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

}
