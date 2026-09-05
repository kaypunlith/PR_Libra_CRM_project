package com.ut.nlSystemAPi.model.response.ActivityCard;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActivityCardResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long customerId;

    @ApiModelProperty(position = 3)
    private String customerName;

    @ApiModelProperty(position = 4)
    private Long customerContactId;

    @ApiModelProperty(position = 5)
    private String contactName;

    @ApiModelProperty(position = 6)
    private String position;

    @ApiModelProperty(position = 7)
    private Long actionStatusId;

    @ApiModelProperty(position = 8)
    private String status;

    @ApiModelProperty(position = 9)
    private Long quotationId;

    @ApiModelProperty(position = 10)
    private String quotationCode;

    @ApiModelProperty(position = 11)
    private String date;

    @ApiModelProperty(position = 12)
    private Integer typeId;

    @ApiModelProperty(position = 13)
    private String typeName;

    @ApiModelProperty(position = 14)
    private String subject;

    @ApiModelProperty(position = 15)
    private String result;

    @ApiModelProperty(position = 16)
    private String other;

    @ApiModelProperty(position = 17)
    private String created;

    @ApiModelProperty(position = 18)
    private String createdBy;
}
