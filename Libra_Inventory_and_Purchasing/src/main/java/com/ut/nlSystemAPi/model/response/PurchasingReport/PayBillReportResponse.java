package com.ut.nlSystemAPi.model.response.PurchasingReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PayBillReportResponse {

    @ApiModelProperty(position = 1)
    private Long vendorId;

    @ApiModelProperty(position = 2)
    private String vendorName;

    @ApiModelProperty(position = 3)
    private Double grandTotalAmount;

    @ApiModelProperty(position = 4)
    private List<PayBillReportDetailResponse> details;

}
