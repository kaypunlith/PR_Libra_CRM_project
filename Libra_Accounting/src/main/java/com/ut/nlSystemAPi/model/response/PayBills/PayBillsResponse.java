package com.ut.nlSystemAPi.model.response.PayBills;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayBillsResponse {

	@ApiModelProperty(position = 1)
	private Long id;

	@ApiModelProperty(position = 1)
	private Long moduleId;

	@ApiModelProperty(position = 2)
	private String date;

	@ApiModelProperty(position = 2)
	private String type;

	@ApiModelProperty(position = 2)
	private String prCode;

	@ApiModelProperty(position = 3)
	private String reference;

	@ApiModelProperty(position = 3)
	private String erNumber;

	@ApiModelProperty(position = 3)
	private Long locationId;

	@ApiModelProperty(position = 5)
	private String locationName;

	@ApiModelProperty(position = 5)
	private String vendorName;

	@ApiModelProperty(position = 5)
	private Long vendorId;

	@ApiModelProperty(position = 6)
	private Long chartAccountId;

	@ApiModelProperty(position = 5)
	private Long paymentTermId;

	@ApiModelProperty(position = 7)
	private Double totalAmount;

	@ApiModelProperty(position = 8)
	private Double amountDue;

	@ApiModelProperty(position = 9)
	private Double balance;

	@ApiModelProperty(position = 9)
	private Double erAmount;

	@ApiModelProperty(position = 6)
	private Long pvId;

	@ApiModelProperty(position = 10)
	private String pvCode;

	@ApiModelProperty(position = 10)
	private String pvAppAmt;

	@ApiModelProperty(position = 10)
	private String pvAmt;

	@ApiModelProperty(position = 10)
	private Long pvRequestId;

	@ApiModelProperty(position = 10)
	private Long landedCostId;

	@ApiModelProperty(position = 10)
	private String memo;

	@ApiModelProperty(position = 10)
	private String aging;

	@ApiModelProperty(position = 10)
	private Long branchId;

	@ApiModelProperty(position = 10)
	private String  branchName;

	@ApiModelProperty(position = 10)
	private String createdBy;

	@ApiModelProperty(position = 11)
	private String createdDate;

	@ApiModelProperty(position = 12)
	private String modifiedBy;

	@ApiModelProperty(position = 13)
	private String modifiedDate;

}
