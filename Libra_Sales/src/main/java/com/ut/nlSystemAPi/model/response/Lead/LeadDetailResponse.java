package com.ut.nlSystemAPi.model.response.Lead;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LeadDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private String title;

    @ApiModelProperty(position = 4)
    private String makingProcess;

    @ApiModelProperty(position = 5)
    private String url;

    @ApiModelProperty(position = 6)
    private String created;

    @ApiModelProperty(position = 7)
    private List<LeadDetailResponse> negotiationIssues;
}
