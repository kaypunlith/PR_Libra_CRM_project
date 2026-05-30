package com.ut.nlSystemAPi.model.response.ProductGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class ProductGroupResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String companyName;

    @ApiModelProperty(position = 4)
    private Long parentId;

    @ApiModelProperty(position = 5)
    private String parentName;

    @ApiModelProperty(position = 6)
    private String name;

    @ApiModelProperty(position = 7)
    private Long userApply;

    @ApiModelProperty(position = 8)
    private Long icsApplySub;

    @ApiModelProperty(position = 9)
    private List<UserPgroupResponse> users;

    @ApiModelProperty(position = 10)
    private String description;

    @ApiModelProperty(position = 11)
    private List<ICSResponse> ICSResponse;

    @ApiModelProperty(position = 12)
    private List<ProductPgroupResponse> products;

    @ApiModelProperty(position = 13)
    private String createdBy;

    @ApiModelProperty(position = 14)
    private String created;
}
