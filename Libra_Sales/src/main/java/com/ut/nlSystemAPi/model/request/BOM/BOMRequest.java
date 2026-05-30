package com.ut.nlSystemAPi.model.request.BOM;

import com.ut.nlSystemAPi.model.request.BOQ.BOQDetailRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BOMRequest {
    @ApiModelProperty(position = 2)
    private Long boqId;

    @ApiModelProperty(position = 3)
    private String moduleCode;

    @ApiModelProperty(position = 3)
    private String date;

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
