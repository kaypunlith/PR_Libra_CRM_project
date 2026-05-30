package com.ut.nlSystemAPi.model.response.BranchType;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BranchTypeResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 2)
    private String created;

    @ApiModelProperty(position = 3)
    private String createdBy;

    @ApiModelProperty(position = 4)
    private String modified;

    @ApiModelProperty(position = 5)
    private String modifiedBy;
}
