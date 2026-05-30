package com.ut.nlSystemAPi.model.request.DeliveryNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeliveryNotePickOneDetailRequest {

    @ApiModelProperty(position = 7)
    private Long locationId;

    @ApiModelProperty(position = 8)
    private String lotsNumber;

    @ApiModelProperty(position = 8)
    private String expiredDate;

    @ApiModelProperty(position = 9)
    private Long qtyPick;

}

