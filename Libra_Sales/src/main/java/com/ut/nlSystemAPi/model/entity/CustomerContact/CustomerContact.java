package com.ut.nlSystemAPi.model.entity.CustomerContact;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerContact extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long organizationId;

    @ApiModelProperty(position = 2)
    private Integer type;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 2)
    private String contactName;

    @ApiModelProperty(position = 3)
    private String gender;

    @ApiModelProperty(position = 4)
    private String photo;

    @ApiModelProperty(position = 6)
    private String position;

    @ApiModelProperty(position = 6)
    private String contactTelephone;

    @ApiModelProperty(position = 6)
    private String contactEmail;

    @ApiModelProperty(position = 7)
    private Long familyId;

    @ApiModelProperty(position = 8)
    private Long age;

    @ApiModelProperty(position = 9)
    private Long characterId;

    @ApiModelProperty(position = 10)
    private Long locationId;

    @ApiModelProperty(position = 13)
    private String note;

    @ApiModelProperty(position = 14)
    private String dob;
}