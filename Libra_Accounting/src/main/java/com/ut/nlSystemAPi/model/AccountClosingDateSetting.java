package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountClosingDateSetting extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;


  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 1)
  private String date;

  @ApiModelProperty(position = 2)
  private Long companyId;

  @ApiModelProperty(position = 2)
  private Long branchId;

  @ApiModelProperty(position = 3)
  private String reference;

  @ApiModelProperty(position = 6)
  private Long isRetainedEarnings;

}
