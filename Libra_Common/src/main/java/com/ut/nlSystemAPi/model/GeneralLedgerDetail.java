package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class GeneralLedgerDetail extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long generalLedgerId;

  @ApiModelProperty(position = 3)
  private Long chartAccountId;

  @ApiModelProperty(position = 4)
  private Long companyId;

  @ApiModelProperty(position = 4)
  private Long branchId;

  @ApiModelProperty(position = 5)
  private String type;

  @ApiModelProperty(position = 5)
  private String date;

  @ApiModelProperty(position = 5)
  private Double debit;

  @ApiModelProperty(position = 5)
  private Double credit;

  @ApiModelProperty(position = 5)
  private String memo;

  @ApiModelProperty(position = 5)
  private Long classId;

  @ApiModelProperty(position = 6)
  private Long locationId;

  @ApiModelProperty(position = 7)
  private Long vendorId;

  @ApiModelProperty(position = 8)
  private Long productId;

  @ApiModelProperty(position = 9)
  private Long serviceId;

  @ApiModelProperty(position = 10)
  private Long customerId;

  @ApiModelProperty(position = 11)
  private Long employeeId;

  @ApiModelProperty(position = 5)
  private Long isReconcile;

  @ApiModelProperty(position = 5)
  private Long reconcileId;

  @ApiModelProperty(position = 12)
  private Long inventoryValuationId;

  @ApiModelProperty(position = 13)
  private Integer inventoryValuationIsDebit;
}
