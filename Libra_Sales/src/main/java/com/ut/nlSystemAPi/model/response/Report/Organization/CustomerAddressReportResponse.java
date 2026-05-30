package com.ut.nlSystemAPi.model.response.Report.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerAddressReportResponse {

    @ApiModelProperty(position = 1)
    private Long groupId;

    @ApiModelProperty(position = 2)
    private String groupName;

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String address;

    @ApiModelProperty(position = 3)
    private String gender;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 3)
    private String telephone;

    @ApiModelProperty(position = 3)
    private String mobile;

    @ApiModelProperty(position = 3)
    private String telephoneOther;

    @ApiModelProperty(position = 3)
    private String fax;

    @ApiModelProperty(position = 3)
    private String email;

    @ApiModelProperty(position = 8)
    private List<CustomerAddressReportResponse> details;

}