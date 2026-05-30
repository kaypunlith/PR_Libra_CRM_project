package com.ut.nlSystemAPi.model.entity.Commission;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommissionDetail extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long commissionId;

    @ApiModelProperty(position = 2)
    private Long productId;

    @ApiModelProperty(position = 3)
    private Double qty;

    @ApiModelProperty(position = 4)
    private Long uomId;

    @ApiModelProperty(position = 5)
    private Double amount;

    @ApiModelProperty(position = 6)
    private Double percent;

    @ApiModelProperty(position = 7)
    private Double unitPrice;
}

