package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReceivePaymentOrganization extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 5)
  private Long arId;

  @ApiModelProperty(position = 5)
  private Long glId;

  @ApiModelProperty(position = 3)
  private Long companyId;

  @ApiModelProperty(position = 3)
  private Long cGroupId;

  @ApiModelProperty(position = 3)
  private Long customerId;

  @ApiModelProperty(position = 3)
  private Long employeeId;

  @ApiModelProperty(position = 3)
  private Long branchId;

  @ApiModelProperty(position = 3)
  private Long depositTo;

  @ApiModelProperty(position = 2)
  private String date;

  @ApiModelProperty(position = 3)
  private String reference;

  @ApiModelProperty(position = 3)
  private String accountCode;

  @ApiModelProperty(position = 3)
  private String accountDescription;

  @ApiModelProperty(position = 6)
  private Long chartAccountId;

  @ApiModelProperty(position = 7)
  private String note;

  @ApiModelProperty(position = 8)
  private String chequeNumber;

  @ApiModelProperty(position = 8)
  private Double exchangeRate;

}
