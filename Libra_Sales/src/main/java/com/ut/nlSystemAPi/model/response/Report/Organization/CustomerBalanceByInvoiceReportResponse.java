package com.ut.nlSystemAPi.model.response.Report.Organization;

import com.ut.nlSystemAPi.model.response.Report.ReportGrandTotalResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerBalanceByInvoiceReportResponse {

    @ApiModelProperty(position = 1)
    private Long organizationId;

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String reference;

    @ApiModelProperty(position = 3)
    private String invoiceCode;

    @ApiModelProperty(position = 3)
    private String companyName;

    @ApiModelProperty(position = 4)
    private String organizationName;

    @ApiModelProperty(position = 5)
    private String type;

    @ApiModelProperty(position = 5)
    private String account;

    @ApiModelProperty(position = 6)
    private Double amount;

    @ApiModelProperty(position = 7)
    private Double balance;

    @ApiModelProperty(position = 8)
    private ReportGrandTotalResponse grandTotals;

    @ApiModelProperty(position = 9)
    private List<CustomerBalanceByInvoiceReportResponse> details;

}
