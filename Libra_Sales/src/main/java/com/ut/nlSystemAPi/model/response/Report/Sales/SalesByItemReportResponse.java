package com.ut.nlSystemAPi.model.response.Report.Sales;

import com.ut.nlSystemAPi.model.response.Report.ReportGrandTotalResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SalesByItemReportResponse {

    @ApiModelProperty(position = 3)
    private String type;

    @ApiModelProperty(position = 4)
    private String parentName;

    @ApiModelProperty(position = 5)
    private String itemName;

    @ApiModelProperty(position = 5)
    private String date;

    @ApiModelProperty(position = 5)
    private String code;

    @ApiModelProperty(position = 5)
    private String invoiceCode;

    @ApiModelProperty(position = 6)
    private String organizationName;

    @ApiModelProperty(position = 7)
    private String warehouseName;

    @ApiModelProperty(position = 8)
    private Double qty;

    @ApiModelProperty(position = 9)
    private Double qtyFree;

    @ApiModelProperty(position = 10)
    private Double conversion;

    @ApiModelProperty(position = 11)
    private String uomName;

    @ApiModelProperty(position = 12)
    private Double unitPrice;

    @ApiModelProperty(position = 13)
    private Double totalPrice;

    @ApiModelProperty(position = 14)
    private ReportGrandTotalResponse grandTotals;

    @ApiModelProperty(position = 15)
    private List<SalesByItemReportResponse> details;

}
