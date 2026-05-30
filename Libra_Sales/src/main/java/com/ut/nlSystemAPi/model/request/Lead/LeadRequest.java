package com.ut.nlSystemAPi.model.request.Lead;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LeadRequest {

    @ApiModelProperty(position = 1)
    private String photo;

    @ApiModelProperty(position = 2)
    private List<Long> companies;

    @ApiModelProperty(position = 3)
    private List<Long> leadGroups;

    @ApiModelProperty(position = 4)
    private String name;

    @ApiModelProperty(position = 5)
    private String nameKh;

    @ApiModelProperty(position = 6)
    private String lats;

    @ApiModelProperty(position = 7)
    private String longs;

    @ApiModelProperty(position = 8)
    private String telephone;

    @ApiModelProperty(position = 9)
    private String mobile;

    @ApiModelProperty(position = 10)
    private String alternateMobile;

    @ApiModelProperty(position = 11)
    private String email;

    @ApiModelProperty(position = 12)
    private List<Long> leadContacts;

    @ApiModelProperty(position = 13)
    private String address;

    @ApiModelProperty(position = 14)
    private List<LeadDivisionRequest> divisionInformation;
}
