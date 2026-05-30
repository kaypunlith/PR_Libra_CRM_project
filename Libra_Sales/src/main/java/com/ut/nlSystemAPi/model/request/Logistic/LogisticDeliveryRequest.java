package com.ut.nlSystemAPi.model.request.Logistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LogisticDeliveryRequest {

    @ApiModelProperty(position = 1)
    private Integer type;

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long organizationGroupId;

    @ApiModelProperty(position = 4)
    private Long organizationId;

    @ApiModelProperty(position = 4)
    private List<Long> organizationContactId;

    @ApiModelProperty(position = 5)
    private String note;

    @ApiModelProperty(position = 5)
    private String date;

    @ApiModelProperty(position = 6)
    private List<LogisticDeliveryDetailRequest> details;

}