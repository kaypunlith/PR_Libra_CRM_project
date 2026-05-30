package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryDetailRequest;
import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryFileRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class JournalEntryDetail extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 8)
  private Long generalLedgerId;

  @ApiModelProperty(position = 8)
  private String date;

  @ApiModelProperty(position = 8)
  private String reference;

  @ApiModelProperty(position = 8)
  private Long chartAccountId;

  @ApiModelProperty(position = 8)
  private String chartAccountCode;

  @ApiModelProperty(position = 8)
  private String chartAccountName;

  @ApiModelProperty(position = 8)
  private Long companyId;

  @ApiModelProperty(position = 8)
  private Long mainGlId;

  @ApiModelProperty(position = 13)
  private Double debit;

  @ApiModelProperty(position = 14)
  private Double credit;

  @ApiModelProperty(position = 13)
  private Double totalDebit;

  @ApiModelProperty(position = 14)
  private Double totalCredit;

  @ApiModelProperty(position = 9)
  private String type;

  @ApiModelProperty(position = 9)
  private String cheque ;

  @ApiModelProperty(position = 9)
  private Double exchangeRate;

  @ApiModelProperty(position = 9)
  private String memo;

  @ApiModelProperty(position = 17)
  private Long customerId;

  @ApiModelProperty(position = 17)
  private Long vendorId;

  @ApiModelProperty(position = 17)
  private Long employeeId;

  @ApiModelProperty(position = 11)
  private Long classId;

  @ApiModelProperty(position = 11)
  private Long branchId;


}
