package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DropdownResponse {

    @ApiModelProperty(position = 1)
    private Long typeId;

    @ApiModelProperty(position = 2)
    private String typeName;

    @ApiModelProperty(position = 2)
    private String description;

    @ApiModelProperty(position = 3)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long userAppId;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 4)
    private Double percent;

    @ApiModelProperty(position = 5)
    private Integer isApply;

    @ApiModelProperty(position = 5)
    private Integer recurrence;

    @ApiModelProperty(position = 6)
    private Long paymentTermId;

    @ApiModelProperty(position = 7)
    private String paymentTermName;

    @ApiModelProperty(position = 8)
    private String photo;

    @ApiModelProperty(position = 6)
    private List<DropdownResponse> details;

}
