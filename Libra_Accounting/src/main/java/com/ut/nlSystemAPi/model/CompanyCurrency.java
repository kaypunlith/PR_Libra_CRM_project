package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class CompanyCurrency extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long companyId;

  @ApiModelProperty(position = 3)
  private String companyName;

  @ApiModelProperty(position = 4)
  private Long currencyFromId;

  @ApiModelProperty(position = 4)
  private String currencyFrom;

  @ApiModelProperty(position = 5)
  private Long currencyToId;

  @ApiModelProperty(position = 6)
  private String currencyTo;

  @ApiModelProperty(position = 7)
  private Long isPosDefault;

  @ApiModelProperty(position = 8)
  private Long branchId;

}
