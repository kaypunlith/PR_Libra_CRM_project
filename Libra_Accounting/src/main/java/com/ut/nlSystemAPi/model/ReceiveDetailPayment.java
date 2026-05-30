package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReceiveDetailPayment extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 5)
  private Long arId;

  @ApiModelProperty(position = 5)
  private Long receivePaymentId;

  @ApiModelProperty(position = 5)
  private Long saleOrderId;

  @ApiModelProperty(position = 5)
  private Long glId;

  @ApiModelProperty(position = 3)
  private Long companyId;

  @ApiModelProperty(position = 3)
  private Long customerId;

  @ApiModelProperty(position = 3)
  private Long employeeId;

  @ApiModelProperty(position = 8)
  private Double amountDue;

  @ApiModelProperty(position = 8)
  private Double amountPaid;

  @ApiModelProperty(position = 8)
  private Double balance;

  @ApiModelProperty(position = 8)
  private String memo;

  @ApiModelProperty(position = 8)
  private String description;
}
