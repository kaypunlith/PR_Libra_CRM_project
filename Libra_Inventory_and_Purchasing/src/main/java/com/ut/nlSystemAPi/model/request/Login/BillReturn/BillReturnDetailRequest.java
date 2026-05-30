package com.ut.nlSystemAPi.model.request.Login.BillReturn;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BillReturnDetailRequest extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 2)
    private Long itemId;

    @ApiModelProperty(position = 3)
    private String itemName;

    @ApiModelProperty(position = 3)
    private String note;

    @ApiModelProperty(position = 4)
    private String expiredDate;

    @ApiModelProperty(position = 5)
    private Double qty;

    @ApiModelProperty(position = 6)
    private Long uomId;

    @ApiModelProperty(position = 6)
    private Long conversion;

    @ApiModelProperty(position = 7)
    private Double totalCost;

    @ApiModelProperty(position = 8)
    private Double unitCost;

}
