package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class ReconcileReportResponse {
    @ApiModelProperty(position = 1)
    private String title;
    @ApiModelProperty(position = 2)

    private Double beginningBalanceAmount;

    @ApiModelProperty(position = 3)
    private Double totalAmount;

    @ApiModelProperty(position = 3)
    private Double totalBalance;

    @ApiModelProperty(position = 3)
    private Double endOfTotalAmount;

    @ApiModelProperty(position = 3)
    private Double endOfTotalBalance;

    @ApiModelProperty(position = 3)

    private List<ReconcileCheckPaymentDetail> reconcileCheckPaymentDetails;
    private List<ReconcileDepositsCreditsDetail> reconcileDepositsCreditsDetails;
    private List<ReconcileCheckPaymentTransactionDetail> reconcileCheckPaymentTransactionsDetails;
    private List<ReconcileDepositsCreditTransactionDetail> reconcileDepositsCreditTransactionDetails;

}
