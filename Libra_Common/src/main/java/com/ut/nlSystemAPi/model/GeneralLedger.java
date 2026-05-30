package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class GeneralLedger extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private Long inventoryAdjustmentId;

  private Long saleInvoiceId;

  private Long saleInvoiceReceiptId;

  private Long creditMemoId;

  private Long creditMemoReceiptId;

  private Long purchaseOrderId;

  private Long purchaseReturnId;

  private Long purchaseReturnReceiptId;

  private Long landedCostId;

  private String date;

  private String reference;

  private Integer isSys;

  private Integer isAdj;

}
