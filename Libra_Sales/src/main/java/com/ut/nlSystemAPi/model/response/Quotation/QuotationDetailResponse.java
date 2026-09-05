package com.ut.nlSystemAPi.model.response.Quotation;

import com.ut.nlSystemAPi.model.response.Service.ServiceShiftResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuotationDetailResponse {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 2)
    private Long quotationId;

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 2)
    private String description;

    @ApiModelProperty(position = 3)
    private Long itemId;

    @ApiModelProperty(position = 4)
    private String itemName;

    @ApiModelProperty(position = 5)
    private String sku;

    @ApiModelProperty(position = 6)
    private String upc;

    @ApiModelProperty(position = 7)
    private Long qty;

    @ApiModelProperty(position = 8)
    private Long conversion;

    @ApiModelProperty(position = 9)
    private Long uomId;

    @ApiModelProperty(position = 10)
    private String uomName;

    @ApiModelProperty(position = 10)
    private String itemBrand;

    @ApiModelProperty(position = 11)
    private String uomAbbr;

    @ApiModelProperty(position = 12)
    private Double discountAmount;

    @ApiModelProperty(position = 12)
    private Double unitCost;

    @ApiModelProperty(position = 12)
    private Double estimateCost;

    @ApiModelProperty(position = 12)
    private Double unitPrice;

    @ApiModelProperty(position = 13)
    private Double totalPrice;

    @ApiModelProperty(position = 5)
    private String productPhotoUrl;

    @ApiModelProperty(position = 5)
    private String productPhotoName;

    @ApiModelProperty(position = 5)
    private String spec;

    @ApiModelProperty(position = 6)
    private List<ServiceShiftResponse> serviceShiftResponse;

}
