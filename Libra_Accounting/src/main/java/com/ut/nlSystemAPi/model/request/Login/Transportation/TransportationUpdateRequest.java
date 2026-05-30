package com.ut.nlSystemAPi.model.request.Login.Transportation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransportationUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String nameKh;

    @ApiModelProperty(position = 3)
    private String nameEn;

    @ApiModelProperty(position = 4)
    private String nameCn;

    @ApiModelProperty(position = 5)
    private Long ordering;
}
