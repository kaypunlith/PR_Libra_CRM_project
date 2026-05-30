package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountClosingDateSettingDetail extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long generalLedgerId;

  @ApiModelProperty(position = 3)
  private Long companyId;

  @ApiModelProperty(position = 4)
  private Long branchId;

  @ApiModelProperty(position = 5)
  private Long accountType;

  @ApiModelProperty(position = 6)
  private Long chartAccountId;

  @ApiModelProperty(position = 7)
  private String type;

  @ApiModelProperty(position = 8)
  private BigDecimal debit;

  @ApiModelProperty(position = 9)
  private BigDecimal credit;

  @ApiModelProperty(position =10)
  private String memo;

  @ApiModelProperty(position = 11)
  private Long classId;

}
