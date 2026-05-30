package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganizationDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long paymentTermId;

    @ApiModelProperty(position = 4)
    private String paymentTermName;

    @ApiModelProperty(position = 5)
    private String photo;

}
