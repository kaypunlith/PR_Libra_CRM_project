package com.ut.nlSystemAPi.model.request.Login.RequestStock;

import com.ut.nlSystemAPi.model.RequestStockDetail;
import com.ut.nlSystemAPi.model.request.Login.ProductPriceList.ProductPriceListDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RequestStockRequest {

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 4)
    private String memo;

    @ApiModelProperty(position = 6)
    private Long fromWarehouseId;

    @ApiModelProperty(position = 6)
    private Long toWarehouseId;

    @ApiModelProperty(position = 7)
    private List<RequestStockDetailRequest> requestStockDetails;

}

