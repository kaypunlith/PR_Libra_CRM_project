package com.ut.nlSystemAPi.model.request.DeliveryNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeliveryNoteRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private List<Long> details;

    @ApiModelProperty(position = 2)
    private Integer type;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private Long warehouseId;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private Long customerContactId;

    @ApiModelProperty(position = 5)
    private String shipTo;

    @ApiModelProperty(position = 6)
    private String note;

}
