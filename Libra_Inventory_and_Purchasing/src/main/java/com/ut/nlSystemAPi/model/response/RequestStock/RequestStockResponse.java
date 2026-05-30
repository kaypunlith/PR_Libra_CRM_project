package com.ut.nlSystemAPi.model.response.RequestStock;

import com.ut.nlSystemAPi.model.RequestStockDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.List;
@Data
public class RequestStockResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 1)
    private String companyName;

    @ApiModelProperty(position = 2)
    private String requestDate;

    @ApiModelProperty(position = 3)
    private String requestNumber;

    @ApiModelProperty(position = 4)
    private Long fromWarehouseId;

    @ApiModelProperty(position = 4)
    private String fromWarehouseName;

    @ApiModelProperty(position = 5)
    private Long toWarehouseId;

    @ApiModelProperty(position = 5)
    private String toWarehouseName;

    @ApiModelProperty(position = 6)
    private String note;

    @ApiModelProperty(position = 6)
    private Long status;

    @ApiModelProperty(position = 7)
    private Long isApproved;

    @ApiModelProperty(position = 7)
    private Long isNotAllowDisapproved;

    @ApiModelProperty(position = 7)
    private Long isHavingTransfer;

    @ApiModelProperty(position = 8)
    private String createdBy;

    @ApiModelProperty(position = 8)
    private String created;

    @ApiModelProperty(position = 8)
    private String modifiedBy;

    @ApiModelProperty(position = 8)
    private String modified;

    @ApiModelProperty(position = 8)
    private String approvedBy;

    @ApiModelProperty(position = 8)
    private String approved;

    @ApiModelProperty(position = 9)
    private List<RequestStockDetailResponse> requestStockDetailResponses;

}
