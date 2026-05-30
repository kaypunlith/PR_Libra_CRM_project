package com.ut.nlSystemAPi.model.request.Login.ProductGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductGroupRequest {
    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private Long parentId;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private Long userApply;

    @ApiModelProperty(position = 5)
    private Long icsApplySub;

    @ApiModelProperty(position = 6)
    private List<Long> users;

    @ApiModelProperty(position = 7)
    private String description;

    @ApiModelProperty(position = 8)
    private List<ICSRequest> icsRequests;

    @ApiModelProperty(position = 9)
    private List<Long> products;

}
