package com.ut.nlSystemAPi.model.response.LeadContact;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeadContactResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long leadId;

    @ApiModelProperty(position = 3)
    private String leadName;

    @ApiModelProperty(position = 4)
    private String salutation;

    @ApiModelProperty(position = 5)
    private String leadContactName;

    @ApiModelProperty(position = 6)
    private String gender;

    @ApiModelProperty(position = 7)
    private String contactTelephone;

    @ApiModelProperty(position = 8)
    private String contactEmail;

    @ApiModelProperty(position = 9)
    private String position;

    @ApiModelProperty(position = 10)
    private Long personalizeIndexId;

    @ApiModelProperty(position = 11)
    private String personalizeIndexName;

    @ApiModelProperty(position = 12)
    private String role;

    @ApiModelProperty(position = 13)
    private String workFlow;

    @ApiModelProperty(position = 14)
    private String needs;

    @ApiModelProperty(position = 15)
    private String wants;

    @ApiModelProperty(position = 16)
    private String painPoint;

    @ApiModelProperty(position = 17)
    private String note;

    @ApiModelProperty(position = 18)
    private String created;

    @ApiModelProperty(position = 19)
    private String createdBy;

    @ApiModelProperty(position = 20)
    private String modified;

    @ApiModelProperty(position = 21)
    private String modifiedBy;
}
