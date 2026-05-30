package com.ut.nlSystemAPi.model.response.Report.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class AccountReceivableAgingReportResponse {

    @ApiModelProperty(position = 1)
    private Long organizationId;

    @ApiModelProperty(position = 2)
    private String organizationName;

    @ApiModelProperty(position = 3)
    private String className;

    @ApiModelProperty(position = 4)
    private List<AccountReceivableAgingReportDetailResponse> details;

}