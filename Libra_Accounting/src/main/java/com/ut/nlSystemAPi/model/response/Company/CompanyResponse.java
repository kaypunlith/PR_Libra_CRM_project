package com.ut.nlSystemAPi.model.response.Company;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompanyResponse {
    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 2)
    private String photo;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private String vatNumber;

    @ApiModelProperty(position = 5)
    private String faxNumber;

    @ApiModelProperty(position = 6)
    private Long currencyCenterId;

    @ApiModelProperty(position = 6)
    private String currencyCenterName;

    @ApiModelProperty(position = 7)
    private String officeTelephone;

    @ApiModelProperty(position = 8)
    private String otherTelephone;

    @ApiModelProperty(position = 9)
    private String email;

    @ApiModelProperty(position = 10)
    private String address;

    @ApiModelProperty(position = 11)
    private String addressInKhmer;

    @ApiModelProperty(position = 12)
    private String created;

    @ApiModelProperty(position = 13)
    private String createdBy;

    @ApiModelProperty(position = 14)
    private String modified;

    @ApiModelProperty(position = 15)
    private String modifiedBy;
}
