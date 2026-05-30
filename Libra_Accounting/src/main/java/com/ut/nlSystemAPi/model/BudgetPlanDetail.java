package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryFileRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class BudgetPlanDetail extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;
  @ApiModelProperty(position = 1)
  private Long budgetPlsId;

  @ApiModelProperty(position = 2)
  private Long chartAccountId;

  @ApiModelProperty(position = 3)
  private Double m1;

  @ApiModelProperty(position = 3)
  private Double m2;

  @ApiModelProperty(position = 3)
  private Double m3;

  @ApiModelProperty(position = 3)
  private Double m4;

  @ApiModelProperty(position = 3)
  private Double m5;

  @ApiModelProperty(position = 3)
  private Double m6;

  @ApiModelProperty(position = 3)
  private Double m7;

  @ApiModelProperty(position = 3)
  private Double m8;

  @ApiModelProperty(position = 3)
  private Double m9;

  @ApiModelProperty(position = 3)
  private Double m10;

  @ApiModelProperty(position = 3)
  private Double m11;

  @ApiModelProperty(position = 3)
  private Double m12;

}
