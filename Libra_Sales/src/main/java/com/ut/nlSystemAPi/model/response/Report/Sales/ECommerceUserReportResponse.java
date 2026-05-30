package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ECommerceUserReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 6)
    private String phoneNumber;

    @ApiModelProperty(position = 7)
    private String email;

    @ApiModelProperty(position = 8)
    private String gender;

    @ApiModelProperty(position = 9)
    private String dob;

    @ApiModelProperty(position = 10)
    private String address;

    @ApiModelProperty(position = 11)
    private String createdDate;

}
