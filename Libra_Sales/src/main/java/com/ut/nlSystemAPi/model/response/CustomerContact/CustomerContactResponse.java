package com.ut.nlSystemAPi.model.response.CustomerContact;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerContactResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long organizationId;

    @ApiModelProperty(position = 3)
    private String organizationName;

    @ApiModelProperty(position = 4)
    private String contactName;

    @ApiModelProperty(position = 5)
    private String position;

    @ApiModelProperty(position = 6)
    private String contactTelephone;

    @ApiModelProperty(position = 7)
    private String contactEmail;

    @ApiModelProperty(position = 8)
    private String note;

    @ApiModelProperty(position = 8)
    private String gender;

    @ApiModelProperty(position = 8)
    private String photo;

    @ApiModelProperty(position = 9)
    private Integer isApply;

    @ApiModelProperty(position = 10)
    private Long age;

    @ApiModelProperty(position = 11)
    private Long familyId;

    @ApiModelProperty(position = 12)
    private String familyName;

    @ApiModelProperty(position = 13)
    private Long locationId;

    @ApiModelProperty(position = 14)
    private String locationName;

    @ApiModelProperty(position = 15)
    private Long characterId;

    @ApiModelProperty(position = 16)
    private String characterName;

    @ApiModelProperty(position = 17)
    private String created;

    @ApiModelProperty(position = 18)
    private String createdBy;

    @ApiModelProperty(position = 19)
    private String modified;

    @ApiModelProperty(position = 20)
    private String modifiedBy;

    @ApiModelProperty(position = 21)
    private List<CustomerContactDetailResponse> descriptions;

    @ApiModelProperty(position = 22)
    private List<CustomerContactDetailResponse> progress;

    @ApiModelProperty(position = 23)
    private List<CustomerContactDetailResponse> contactLists;
}