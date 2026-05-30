package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class Reconcile extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 1)
  private Long companyId;

  @ApiModelProperty(position = 1)
  private Long branchId;

  @ApiModelProperty(position = 2)
  private String dateFrom;

  @ApiModelProperty(position = 3)
  private String dateTo;

  @ApiModelProperty(position = 4)
  private Long chartAccountId;


  @ApiModelProperty(position = 5)
  private Double serviceChargeAmount;

  @ApiModelProperty(position = 6)
  private String serviceChargeDate;

  @ApiModelProperty(position = 7)
  private Long serviceChargeAccountId;

  @ApiModelProperty(position = 8)
  private Long serviceChargeClassId;


  @ApiModelProperty(position = 9)
  private Double interestedEarnedAmount;

  @ApiModelProperty(position = 10)
  private String interestedEarnedDate;

  @ApiModelProperty(position = 11)
  private Long interestedEarnedAccountId;

  @ApiModelProperty(position = 12)
  private Long interestedEarnedClassId;


  @ApiModelProperty(position = 13)
  private Double diff;

  @ApiModelProperty(position = 14)
  private Double diffAccountId;

  @ApiModelProperty(position = 15)
  private Double diffClassId;


  @ApiModelProperty(position = 16)
  private Double endingBalance;

  @ApiModelProperty(position = 17)
  private Double clearedBalance;

}
