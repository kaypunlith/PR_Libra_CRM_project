package com.ut.nlSystemAPi.model.entity.SupportTicket;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportTicket extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private String caseTitle;

    @ApiModelProperty(position = 4)
    private String summary;

    @ApiModelProperty(position = 5)
    private Long typeId;

    @ApiModelProperty(position = 6)
    private Long customerId;

    @ApiModelProperty(position = 7)
    private Long priorityId;

    @ApiModelProperty(position = 8)
    private Long assignedTo;

    @ApiModelProperty(position = 9)
    private Long customerContactId;
}
