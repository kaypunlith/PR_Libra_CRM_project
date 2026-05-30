package com.ut.nlSystemAPi.model.response.Transportation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransportationResponse {

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

    @ApiModelProperty(position = 6)
    private String createdBy;

    @ApiModelProperty(position = 7)
    private String createdDate;

    @ApiModelProperty(position = 8)
    private String modifiedBy;

    @ApiModelProperty(position = 9)
    private String modifiedDate;
}
