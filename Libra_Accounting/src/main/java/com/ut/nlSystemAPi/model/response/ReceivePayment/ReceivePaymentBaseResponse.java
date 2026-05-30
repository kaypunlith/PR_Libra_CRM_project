package com.ut.nlSystemAPi.model.response.ReceivePayment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReceivePaymentBaseResponse {

	@ApiModelProperty(position = 1)
	private Long id;

	@ApiModelProperty(position = 2)
	private String date;

	@ApiModelProperty(position = 3)
	private String reference;

	@ApiModelProperty(position = 7)
	private Double totalAmount;

	@ApiModelProperty(position = 8)
	private Double amountDue;

	@ApiModelProperty(position = 10)
	private String createdBy;

	@ApiModelProperty(position = 11)
	private String createdDate;

	@ApiModelProperty(position = 12)
	private String modifiedBy;

	@ApiModelProperty(position = 13)
	private String modifiedDate;

}
