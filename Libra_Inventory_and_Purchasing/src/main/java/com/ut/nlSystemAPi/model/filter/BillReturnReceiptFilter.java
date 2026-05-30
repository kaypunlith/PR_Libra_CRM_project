package com.ut.nlSystemAPi.model.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BillReturnReceiptFilter{
    @ApiModelProperty(position = 10)
    private Long id;

    @ApiModelProperty(position = 11)
    private Long billReturnId;

    @ApiModelProperty(position = 12)
    private Long purchaseBillId;

    @ApiModelProperty(position = 13)
    private Long type;

    @ApiModelProperty(position = 14)
    private Double balance;

}
