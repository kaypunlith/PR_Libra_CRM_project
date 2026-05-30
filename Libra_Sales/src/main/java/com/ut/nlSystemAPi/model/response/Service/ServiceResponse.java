package com.ut.nlSystemAPi.model.response.Service;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String sku;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private Long companyId;

    @ApiModelProperty(position = 5)
    private String companyName;

    @ApiModelProperty(position = 6)
    private Long sectionId;

    @ApiModelProperty(position = 7)
    private String sectionName;

    @ApiModelProperty(position = 8)
    private Long uomId;

    @ApiModelProperty(position = 9)
    private String uomAbbr;

    @ApiModelProperty(position = 10)
    private Double unitPrice;

    @ApiModelProperty(position = 11)
    private String description;

    @ApiModelProperty(position = 12)
    private Long chartAccountId;

    @ApiModelProperty(position = 12)
    private String chartAccountName;

    @ApiModelProperty(position = 13)
    private String created;

    @ApiModelProperty(position = 14)
    private String createdBy;

    @ApiModelProperty(position = 15)
    private String modified;

    @ApiModelProperty(position = 16)
    private String modifiedBy;
}