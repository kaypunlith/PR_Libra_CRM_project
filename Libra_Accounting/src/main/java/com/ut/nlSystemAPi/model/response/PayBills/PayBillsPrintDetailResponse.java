package com.ut.nlSystemAPi.model.response.PayBills;

import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentDetailResponse;
import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment.ReceivePaymentFileResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PayBillsPrintDetailResponse {

	@ApiModelProperty(position = 1)
	private String poCode;

	@ApiModelProperty(position = 1)
	private String invoiceCode;

	@ApiModelProperty(position = 1)
	private String description;

	@ApiModelProperty(position = 1)
	private String reference;

	@ApiModelProperty(position = 2)
	private Double totalAmount;

}
