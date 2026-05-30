package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class FixedAsset extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long glId;

  @ApiModelProperty(position = 3)
  private Long companyId;

  @ApiModelProperty(position = 3)
  private Long branchId;

  @ApiModelProperty(position = 4)
  private Long locationId;

  @ApiModelProperty(position = 5)
  private Long vendorId;

  @ApiModelProperty(position = 6)
  private Long assetAccount;

  @ApiModelProperty(position = 7)
  private Long accumulatedDepartment;

  @ApiModelProperty(position = 8)
  private Long deprExpense;

  @ApiModelProperty(position = 9)
  private String deprMethod;

  @ApiModelProperty(position = 10)
  private String date;

  @ApiModelProperty(position = 11)
  private String reference;

  @ApiModelProperty(position = 12)
  private String accountCode;

  @ApiModelProperty(position = 13)
  private String accountDescription;

  @ApiModelProperty(position = 14)
  private String name;

  @ApiModelProperty(position = 15)
  private String photoName;

  @ApiModelProperty(position = 16)
  private String purchaseOrderNumber;

  @ApiModelProperty(position = 17)
  private String serialNumber;

  @ApiModelProperty(position = 18)
  private String warrantyExpires;

  @ApiModelProperty(position = 19)
  private Double cost;

  @ApiModelProperty(position = 20)
  private String photo;

  @ApiModelProperty(position = 21)
  private Long isDepre;

  @ApiModelProperty(position = 22)
  private Double assetLife;

  @ApiModelProperty(position = 23)
  private Double salvageValue;

  @ApiModelProperty(position = 24)
  private Double businessUsePercentage;

  @ApiModelProperty(position = 25)
  private String description;

  @ApiModelProperty(position = 26)
  private String chequeNumber;

  @ApiModelProperty(position = 27)
  private Double exchangeRate;

  @ApiModelProperty(position = 28)
  private String bankNo;

  @ApiModelProperty(position = 29)
  private String fixedAssetCode;

  @ApiModelProperty(position = 30)
  private Double costRemain;

  @ApiModelProperty(position = 31)
  private Long isInUsed;

  @ApiModelProperty(position = 32)
  private Integer type;
}
