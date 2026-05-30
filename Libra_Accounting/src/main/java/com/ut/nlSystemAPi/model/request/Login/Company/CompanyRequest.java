package com.ut.nlSystemAPi.model.request.Login.Company;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompanyRequest {

    @ApiModelProperty(position = 2)
    private String photo;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private String vatNo;

    @ApiModelProperty(position = 5)
    private String Fax;

    @ApiModelProperty(position = 5)
    private Long baseCurrencyId;

    @ApiModelProperty(position = 5)
    private String officeTelephone;

    @ApiModelProperty(position = 5)
    private String otherTelephone;

    @ApiModelProperty(position = 5)
    private String email;

    @ApiModelProperty(position = 5)
    private String address;

    @ApiModelProperty(position = 5)
    private String addressInKhmer;


}
