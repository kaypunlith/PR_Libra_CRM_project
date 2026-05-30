package com.ut.nlSystemAPi.model.entity.Quotation;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class Quotation extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Integer type;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private Long organizationId;

    @ApiModelProperty(position = 7)
    private Long currencyId;

    @ApiModelProperty(position = 7)
    private Long priceTypeId;

    @ApiModelProperty(position = 7)
    private Long vatSettingId;

    @ApiModelProperty(position = 10)
    private Long organizationContactId;

    @ApiModelProperty(position = 12)
    private Double vatPercent;

    @ApiModelProperty(position = 12)
    private Double totalVat;

    @ApiModelProperty(position = 17)
    private Integer vatCalculate;

    @ApiModelProperty(position = 12)
    private Double discountAmount;

    @ApiModelProperty(position = 12)
    private Double discountPercent;

    @ApiModelProperty(position = 12)
    private Double totalAmount;

    @ApiModelProperty(position = 12)
    private Double marginPercent;

    @ApiModelProperty(position = 17)
    private Long shareSaveOption;

    @ApiModelProperty(position = 18)
    private Long shareOption;

    @ApiModelProperty(position = 19)
    private String shareUser;

    @ApiModelProperty(position = 20)
    private String shareExceptUser;

    @ApiModelProperty(position = 21)
    private String note;

    @ApiModelProperty(position = 17)
    private Integer isApproved;

    @ApiModelProperty(position = 17)
    private Integer isApply;

    @ApiModelProperty(position = 23)
    private Integer isNoneVat;

}
