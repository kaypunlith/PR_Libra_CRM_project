package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class PayBillsDetail extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 5)
  private Long payBillId;

  @ApiModelProperty(position = 5)
  private Long branchId;

  @ApiModelProperty(position = 5)
  private Long pvId;

  @ApiModelProperty(position = 5)
  private Long apAgingId;

  @ApiModelProperty(position = 5)
  private Long purchaseOrderId;

  @ApiModelProperty(position = 5)
  private Long chartAccountId;

  @ApiModelProperty(position = 5)
  private Long arId;

  @ApiModelProperty(position = 5)
  private Long glId;

  @ApiModelProperty(position = 5)
  private Long glDetailId;

  @ApiModelProperty(position = 3)
  private Long companyId;

  @ApiModelProperty(position = 5)
  private Long vendorId;

  @ApiModelProperty(position = 4)
  private String reference;

  @ApiModelProperty(position = 5)
  private Long locationId;

  @ApiModelProperty(position = 8)
  private Double amountDue;

  @ApiModelProperty(position = 8)
  private Double amountPaid;

  @ApiModelProperty(position = 8)
  private Double balance;

  @ApiModelProperty(position = 8)
  private String memo;

  @ApiModelProperty(position = 8)
  private String payDate;

  @ApiModelProperty(position = 8)
  private String pvCode;

  @ApiModelProperty(position = 8)
  private String dueDate;

  @ApiModelProperty(position = 8)
  private String description;

  @ApiModelProperty(position = 20)
  private Long userId;

}
