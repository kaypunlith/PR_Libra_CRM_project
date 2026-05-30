package com.ut.nlSystemAPi.model.request.Login.BranchType;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BranchTypeRequest extends BaseModel {

    @ApiModelProperty(position = 1)
    private String name;

}
