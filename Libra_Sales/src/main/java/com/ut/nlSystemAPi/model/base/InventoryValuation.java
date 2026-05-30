package com.ut.nlSystemAPi.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InventoryValuation extends BaseModel {

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long creditMemoId;

  @ApiModelProperty(position = 2)
  private Long creditMemoReceiptId;

  @ApiModelProperty(position = 2)
  private Long saleInvoiceId;

  @ApiModelProperty(position = 2)
  private Long saleInvoiceReceiptId;

  @ApiModelProperty(position = 3)
  private Long companyId;

  @ApiModelProperty(position = 4)
  private String type;

  @ApiModelProperty(position = 5)
  private String date;

  @ApiModelProperty(position = 6)
  private Long productId;

  @ApiModelProperty(position = 7)
  private Double smallQty;

  @ApiModelProperty(position = 8)
  private Double qty;

  @ApiModelProperty(position = 9)
  private Double cost;

  @ApiModelProperty(position = 10)
  private Integer isVarCost;

  @ApiModelProperty(position = 11)
  private Integer isActive;

}
