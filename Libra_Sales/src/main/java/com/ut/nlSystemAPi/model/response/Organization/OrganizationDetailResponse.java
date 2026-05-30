package com.ut.nlSystemAPi.model.response.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 2)
    private String url;

    @ApiModelProperty(position = 3)
    private String title;

    @ApiModelProperty(position = 4)
    private String makingProcess;

    @ApiModelProperty(position = 4)
    private String created;

    @ApiModelProperty(position = 4)
    private List<OrganizationDetailResponse> negotiations;



}