package com.ut.nlSystemAPi.model.response.PayBills;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayBillsDebitDataResponse {

	@ApiModelProperty(position = 1)
	private Long id;

	@ApiModelProperty(position = 2)
	private Long mainGlId;

	@ApiModelProperty(position = 3)
	private Long chartAccountId;

	@ApiModelProperty(position = 4)
	private Long companyId;

	@ApiModelProperty(position = 5)
	private Double debit;

	@ApiModelProperty(position = 6)
	private Long vendorId;

}
