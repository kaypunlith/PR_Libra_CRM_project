package com.ut.nlSystemAPi.model.request.CustomerContact;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerContactRequest {

    @ApiModelProperty(position = 1)
    private Long organizationId;

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

    @ApiModelProperty(position = 15)
    private List<CustomerContactDescriptionRequest> descriptions;

    @ApiModelProperty(position = 16)
    private List<CustomerContactProgressRequest> progress;

    @ApiModelProperty(position = 17)
    private List<Long> listDetailIds;

}