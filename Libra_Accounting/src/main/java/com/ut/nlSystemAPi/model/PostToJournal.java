package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.request.Login.PostToJournal.PostToJournalRequestDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostToJournal extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;
  @ApiModelProperty(position = 1)
  private Long id;
  @ApiModelProperty(position = 1)
  private String date;
  @ApiModelProperty(position = 2)
  private Long companyId;
  @ApiModelProperty(position = 3)
  private String reference;
  @ApiModelProperty(position = 4)
  private String note;
  @ApiModelProperty(position = 5)
  private Integer isApproved;
  @ApiModelProperty(position = 6)
  private Integer isDepreciated;
  @ApiModelProperty(position = 6)
  private Integer isAppreciaction;
  @ApiModelProperty(position = 6)
  private Integer isInvestment;
  @ApiModelProperty(position = 7)
  private List<PostToJournalDetails> details;

}
