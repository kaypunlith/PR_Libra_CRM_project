package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuditTrailReportResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String type;

    @ApiModelProperty(position = 3)
    private Long branchId;

    @ApiModelProperty(position = 4)
    private String branchName;

    @ApiModelProperty(position = 5)
    private String code;

    @ApiModelProperty(position = 5)
    private String date;

    @ApiModelProperty(position = 6)
    private String status;

    @ApiModelProperty(position = 6)
    private Long statusNum;

    @ApiModelProperty(position = 7)
    private String createdBy;

    @ApiModelProperty(position = 7)
    private String created;

    @ApiModelProperty(position = 8)
    private String modifiedBy;

    @ApiModelProperty(position = 8)
    private String modified;

    @ApiModelProperty(position = 9)
    private Double amount;
}
