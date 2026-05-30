package com.ut.nlSystemAPi.model.request.Login.ClassAccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClassAccountRequest {

    @ApiModelProperty(position = 1)
    private Long parentId;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String description;

    @ApiModelProperty(position = 4)
    private Long ordering;

    @ApiModelProperty(position = 5)
    private List<Long> companyId;

    @ApiModelProperty(position = 6)
    private List<Long> userId;

}
