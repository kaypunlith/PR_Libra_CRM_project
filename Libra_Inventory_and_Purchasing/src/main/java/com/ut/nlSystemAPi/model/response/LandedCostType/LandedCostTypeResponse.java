package com.ut.nlSystemAPi.model.response.LandedCostType;

import com.ut.nlSystemAPi.model.response.LandedCost.LandedCostDetailResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LandedCostTypeResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private String companyName;

    @ApiModelProperty(position = 5)
    private String created;

    @ApiModelProperty(position = 6)
    private String createdBy;

    @ApiModelProperty(position = 7)
    private String modified;

    @ApiModelProperty(position = 8)
    private String modifiedBy;

}
