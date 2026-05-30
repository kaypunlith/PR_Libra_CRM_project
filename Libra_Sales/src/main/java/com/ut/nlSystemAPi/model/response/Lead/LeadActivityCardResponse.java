package com.ut.nlSystemAPi.model.response.Lead;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeadActivityCardResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private Long statusId;

    @ApiModelProperty(position = 4)
    private String status;

    @ApiModelProperty(position = 5)
    private Long leadContactId;

    @ApiModelProperty(position = 6)
    private String contactName;

    @ApiModelProperty(position = 7)
    private String position;

    @ApiModelProperty(position = 8)
    private String subject;

    @ApiModelProperty(position = 9)
    private String result;

    @ApiModelProperty(position = 10)
    private String other;
}
