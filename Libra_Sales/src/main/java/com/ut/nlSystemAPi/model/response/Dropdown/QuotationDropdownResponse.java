package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuotationDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 2)
    private Double amount;

    @ApiModelProperty(position = 3)
    private Long organizationId;

    @ApiModelProperty(position = 4)
    private String organizationName;

    @ApiModelProperty(position = 5)
    private Long companyId;

    @ApiModelProperty(position = 6)
    private String organizationCode;

    @ApiModelProperty(position = 7)
    private Long organizationContactId;

    @ApiModelProperty(position = 8)
    private String organizationContactName;

    @ApiModelProperty(position = 9)
    private Integer isApply;

    @ApiModelProperty(position = 10)
    private String label;

    @ApiModelProperty(position = 11)
    private List<QuotationDropdownDetailResponse> details;

}
