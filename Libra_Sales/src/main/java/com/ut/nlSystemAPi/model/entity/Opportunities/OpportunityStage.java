package com.ut.nlSystemAPi.model.entity.Opportunities;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityStage extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Integer applyWith;

    @ApiModelProperty(position = 3)
    private Long convertLead;

    @ApiModelProperty(position = 4)
    private Integer isUpload;

    @ApiModelProperty(position = 5)
    private Integer isPrint;

}
