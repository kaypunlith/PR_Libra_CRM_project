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
public class JournalEntry extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 4)
  private Long id;

  @ApiModelProperty(position = 4)
  private String date;

  @ApiModelProperty(position = 11)
  private String reference;

  @ApiModelProperty(position = 11)
  private Long companyId;

  @ApiModelProperty(position = 11)
  private Long branchId;

  @ApiModelProperty(position = 11)
  private Long vendorId;

  @ApiModelProperty(position = 11)
  private Long applyDepositToId;

  @ApiModelProperty(position = 5)
  private String applyReferenceCode;

  @ApiModelProperty(position = 6)
  private String applyReferenceName;

  @ApiModelProperty(position = 11)
  private Long isRecurrence;

  @ApiModelProperty(position = 5)
  private Long isOrVoucher;

  @ApiModelProperty(position = 5)
  private Long isPvVoucher;

  @ApiModelProperty(position = 5)
  private Long payBillId;

  @ApiModelProperty(position = 5)
  private Long purchaseOrderId;

  @ApiModelProperty(position = 5)
  private Long pvId;

  @ApiModelProperty(position = 5)
  private Long apAgingId;


  @ApiModelProperty(position = 5)
  private Long isApprove;

  @ApiModelProperty(position = 11)
  private Long adj;

  @ApiModelProperty(position = 11)
  private Long isSys;

  @ApiModelProperty(position = 11)
  private Double exchangeRate;

  @ApiModelProperty(position = 11)
  private Double totalDeposit;

  @ApiModelProperty(position = 11)
  private Long depositType;

  @ApiModelProperty(position = 11)
  private String chequeNo;

  @ApiModelProperty(position = 11)
  private String note;


  @ApiModelProperty(position = 11)
  private List<JournalEntryFileRequest> file;

  @ApiModelProperty(position = 11)
  private List<JournalEntryDetail> journalEntryDetails;

}
