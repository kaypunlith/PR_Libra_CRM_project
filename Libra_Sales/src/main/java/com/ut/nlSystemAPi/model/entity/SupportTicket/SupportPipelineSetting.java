package com.ut.nlSystemAPi.model.entity.SupportTicket;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportPipelineSetting extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long typeId;

    @ApiModelProperty(position = 3)
    private String description;

    @ApiModelProperty(position = 4)
    private Integer resolutionNumber;

    @ApiModelProperty(position = 5)
    private Long baseOnId;
}
