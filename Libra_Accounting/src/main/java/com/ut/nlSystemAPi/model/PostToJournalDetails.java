package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostToJournalDetails extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;
  @ApiModelProperty(position = 1, hidden = true)
  private Long generalLedgerId;
  @ApiModelProperty(position = 1)
  private Long chartAccountId;
  @ApiModelProperty(position = 1)
  private Long companyId;
  @ApiModelProperty(position = 1)
  private Long branchId;
  @ApiModelProperty(position = 2)
  private Double debit;
  @ApiModelProperty(position = 3)
  private Double credit;
  @ApiModelProperty(position = 4)
  private String memo;
  @ApiModelProperty(position = 5)
  private String type;

}
