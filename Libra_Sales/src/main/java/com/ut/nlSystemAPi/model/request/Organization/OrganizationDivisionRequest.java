package com.ut.nlSystemAPi.model.request.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationDivisionRequest {

    @ApiModelProperty(position = 1)
    private String title;

    @ApiModelProperty(position = 2)
    private String makingProcess;

    @ApiModelProperty(position = 3)
    private List<Long> negotiations;

}