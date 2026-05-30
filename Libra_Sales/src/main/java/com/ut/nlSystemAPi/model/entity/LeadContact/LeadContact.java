package com.ut.nlSystemAPi.model.entity.LeadContact;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeadContact extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long leadId;

    @ApiModelProperty(position = 3)
    private String salutation;

    @ApiModelProperty(position = 4)
    private String contactName;

    @ApiModelProperty(position = 5)
    private String gender;

    @ApiModelProperty(position = 6)
    private String contactTelephone;

    @ApiModelProperty(position = 7)
    private String contactEmail;

    @ApiModelProperty(position = 8)
    private String position;

    @ApiModelProperty(position = 9)
    private Long personalizeIndexId;

    @ApiModelProperty(position = 10)
    private String role;

    @ApiModelProperty(position = 11)
    private String workFlow;

    @ApiModelProperty(position = 12)
    private String needs;

    @ApiModelProperty(position = 13)
    private String wants;

    @ApiModelProperty(position = 14)
    private String painPoint;

    @ApiModelProperty(position = 15)
    private String note;
}
