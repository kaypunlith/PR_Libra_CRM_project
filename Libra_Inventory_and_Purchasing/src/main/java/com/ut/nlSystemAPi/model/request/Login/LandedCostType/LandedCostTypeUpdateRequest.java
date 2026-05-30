package com.ut.nlSystemAPi.model.request.Login.LandedCostType;

import com.ut.nlSystemAPi.model.request.Login.LandedCost.LandedCostDetailRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LandedCostTypeUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String name;

}
