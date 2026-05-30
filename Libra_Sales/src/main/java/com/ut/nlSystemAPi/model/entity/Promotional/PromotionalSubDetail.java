package com.ut.nlSystemAPi.model.entity.Promotional;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PromotionalSubDetail extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long promotionalDetailId;

    @ApiModelProperty(position = 3)
    private Long productId;

    @ApiModelProperty(position = 4)
    private Long uomId;

    @ApiModelProperty(position = 5)
    private Double qty;
}
