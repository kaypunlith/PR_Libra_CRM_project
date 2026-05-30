package com.ut.nlSystemAPi.model.request.BOQ;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BOQRequest {

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 3)
    private String moduleCode;

    @ApiModelProperty(position = 4)
    private Long companyId;

    @ApiModelProperty(position = 6)
    private Long organizationId;

    @ApiModelProperty(position = 9)
    private Long organizationContactId;

    @ApiModelProperty(position = 11)
    private String note;

    @ApiModelProperty(position = 12)
    private Double totalCost;

    @ApiModelProperty(position = 13)
    private Double totalPrice;

    @ApiModelProperty(position = 14)
    private List<BOQDetailRequest> details;

}
