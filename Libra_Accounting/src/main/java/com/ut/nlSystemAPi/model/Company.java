package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.response.Dropdown.ModuleTypeResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Company extends BaseModel implements Serializable {

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 2)
    private String photo;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private String vatNo;

    @ApiModelProperty(position = 5)
    private String faxNumber;

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
