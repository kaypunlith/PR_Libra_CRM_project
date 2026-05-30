package com.ut.nlSystemAPi.model.request.Login.VendorGroup;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class VendorGroupUpdateRequest extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long typeId;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private List<Long> companies;

    @ApiModelProperty(position = 5)
    private List<Long> vendors;

}
