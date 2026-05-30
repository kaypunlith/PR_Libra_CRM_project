package com.ut.nlSystemAPi.model.request.LeadContact;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeadContactRequest {

    @ApiModelProperty(position = 1)
    private Long leadId;

    @ApiModelProperty(position = 2)
    private String salutation;

    @ApiModelProperty(position = 3)
    private String contactName;

    @ApiModelProperty(position = 4)
    private String gender;

    @ApiModelProperty(position = 5)
    private String contactTelephone;

    @ApiModelProperty(position = 6)
    private String contactEmail;

    @ApiModelProperty(position = 7)
    private String position;

    @ApiModelProperty(position = 8)
    private Long personalizeIndexId;

    @ApiModelProperty(position = 9)
    private String role;

    @ApiModelProperty(position = 10)
    private String workFlow;

    @ApiModelProperty(position = 11)
    private String needs;

    @ApiModelProperty(position = 12)
    private String wants;

    @ApiModelProperty(position = 13)
    private String painPoint;

    @ApiModelProperty(position = 14)
    private String note;
}
