package com.ut.nlSystemAPi.model.response.TransferConsignment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class ProductConsignmentDetailResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private String barCode;

    @ApiModelProperty(position = 5)
    private Double qty;

    @ApiModelProperty(position = 6)
    private Long groupId;

    @ApiModelProperty(position = 7)
    private String group;

    @ApiModelProperty(position = 8)
    private List<TransferConsignmentLocationResponse> locationFromResponses;

    @ApiModelProperty(position = 9)
    private List<TransferConsignmentLocationResponse> locationToResponses;
}
