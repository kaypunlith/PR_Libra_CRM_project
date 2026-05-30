package com.ut.nlSystemAPi.model.response.TransferConsignmentReceive;

import com.ut.nlSystemAPi.model.response.TransferConsignment.TransferConsignmentDetailResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TransferConsignmentReceiveResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String companyName;

    @ApiModelProperty(position = 2)
    private Long fromWarehouseId;

    @ApiModelProperty(position = 3)
    private String fromWarehouseName;

    @ApiModelProperty(position = 4)
    private Long toWarehouseId;

    @ApiModelProperty(position = 5)
    private String toWarehouseName;

    @ApiModelProperty(position = 6)
    private String toNumber;

    @ApiModelProperty(position = 7)
    private String toDate;

    @ApiModelProperty(position = 8)
    private String fulfillmentDate;

    @ApiModelProperty(position = 8)
    private String date;

    @ApiModelProperty(position = 8)
    private String code;

    @ApiModelProperty(position = 9)
    private Long status;

    @ApiModelProperty(position = 10)
    private Long type;

    @ApiModelProperty(position = 11)
    private String created;

    @ApiModelProperty(position = 12)
    private String createdBy;

    @ApiModelProperty(position = 13)
    private String modified;

    @ApiModelProperty(position = 14)
    private String modifiedBy;

    @ApiModelProperty(position = 15)
    private String memo;

    @ApiModelProperty(position = 16)
    private List<TransferConsignmentDetailResponse> details;

}
