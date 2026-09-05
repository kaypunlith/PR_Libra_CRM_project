package com.ut.nlSystemAPi.model.request.Service;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ServiceRequest {

    @ApiModelProperty(position = 2)
    private String sku;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private Long companyId;

    @ApiModelProperty(position = 5)
    private Long sectionId;

    @ApiModelProperty(position = 5)
    private List<Long> serviceShiftId;


    @ApiModelProperty(position = 6)
    private Long uomId;

    @ApiModelProperty(position = 7)
    private Double unitPrice;

    @ApiModelProperty(position = 8)
    private String description;

    @ApiModelProperty(position = 9)
    private Long chartAccountId;

}