package com.ut.nlSystemAPi.model.response.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportTicketLogResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Integer type;

    @ApiModelProperty(position = 3)
    private Integer status;

    @ApiModelProperty(position = 4)
    private String statusName;

    @ApiModelProperty(position = 5)
    private String title;

    @ApiModelProperty(position = 6)
    private String description;

    @ApiModelProperty(position = 7)
    private String borderColor;

    @ApiModelProperty(position = 8)
    private String created;

    @ApiModelProperty(position = 9)
    private String createdBy;
}
