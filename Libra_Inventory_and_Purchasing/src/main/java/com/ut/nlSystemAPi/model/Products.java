package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.request.Login.Product.ProductICSRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class Products extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String photo;

    @ApiModelProperty(position = 2)
    private String photoName;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long brandId;

    @ApiModelProperty(position = 3)
    private Long priceRequestId;

    @ApiModelProperty(position = 4)
    private Long parentId;

    @ApiModelProperty(position = 5)
    private String name;

    @ApiModelProperty(position = 5)
    private String nameKh;

    @ApiModelProperty(position = 6)
    private Long isPacket;

    @ApiModelProperty(position = 7)
    private String color;

    @ApiModelProperty(position = 8)
    private String url;

    @ApiModelProperty(position = 8)
    private String barcode;

    @ApiModelProperty(position = 9)
    private String code;

    @ApiModelProperty(position = 10)
    private Long priceUomId;

    @ApiModelProperty(position = 11)
    private Double unitCost;

    @ApiModelProperty(position = 11)
    private Double estimatedCost;

    @ApiModelProperty(position = 12)
    private String periodFrom;

    @ApiModelProperty(position = 13)
    private String periodTo;

    @ApiModelProperty(position = 14)
    private Long productGroupId;

    @ApiModelProperty(position = 15)
    private Long productRecorderLevel;

    @ApiModelProperty(position = 16)
    private Long isExpiredDate;

    @ApiModelProperty(position = 17)
    private String spec;

    @ApiModelProperty(position = 18)
    private String description;

    @ApiModelProperty(position = 19)
    private String fileCatalog;

    @ApiModelProperty(position = 19)
    private String fileCatalogName;

    @ApiModelProperty(position = 20)
    private Double length;

    @ApiModelProperty(position = 21)
    private Double height;

    @ApiModelProperty(position = 22)
    private Double width;

    @ApiModelProperty(position = 23)
    private Long sizeUomId;

    @ApiModelProperty(position = 24)
    private Double cubicMeter;

    @ApiModelProperty(position = 25)
    private Double weight;

    @ApiModelProperty(position = 26)
    private Long weightUomId;

    @ApiModelProperty(position = 27)
    private String note;

    @ApiModelProperty(position = 28)
    private String luckyCode;

    @ApiModelProperty(position = 29)
    private List<Long> categoryId;

    @ApiModelProperty(position = 30)
    private List<ProductICSRequest> productIcsRequests;
}
