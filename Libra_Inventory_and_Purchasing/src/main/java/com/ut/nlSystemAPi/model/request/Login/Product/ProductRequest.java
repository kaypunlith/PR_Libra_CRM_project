package com.ut.nlSystemAPi.model.request.Login.Product;

import com.ut.nlSystemAPi.model.request.Login.VendorManagement.VendorPhoto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {

    @ApiModelProperty(position = 1)
    private Long priceRequestId;

    @ApiModelProperty(position = 2)
    private VendorPhoto photo;

    @ApiModelProperty(position = 2)
    private List<VendorPhoto> photos;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long brandId;

    @ApiModelProperty(position = 4)
    private Long parentId;

    @ApiModelProperty(position = 5)
    private String name;

    @ApiModelProperty(position = 5)
    private String nameKh;

    @ApiModelProperty(position = 6)
    private Integer isActive;

    @ApiModelProperty(position = 7)
    private Long isPacket;

    @ApiModelProperty(position = 8)
    private String color;

    @ApiModelProperty(position = 9)
    private String url;

    @ApiModelProperty(position = 9)
    private String upc;

    @ApiModelProperty(position = 10)
    private Long uomId;

    @ApiModelProperty(position = 11)
    private Double unitCost;

    @ApiModelProperty(position = 12)
    private String periodFrom;

    @ApiModelProperty(position = 13)
    private String periodTo;

    @ApiModelProperty(position = 15)
    private Long productGroupId;

    @ApiModelProperty(position = 16)
    private Long productRecorderLevel;

    @ApiModelProperty(position = 21)
    private Long isExpiredDate;

    @ApiModelProperty(position = 18)
    private String sku;

    @ApiModelProperty(position = 19)
    private String spec;

    @ApiModelProperty(position = 20)
    private String description;

    @ApiModelProperty(position = 21)
    private FileCatalog fileCatalog;

    @ApiModelProperty(position = 22)
    private Double length;

    @ApiModelProperty(position = 23)
    private Double height;

    @ApiModelProperty(position = 24)
    private Double width;

    @ApiModelProperty(position = 25)
    private Long sizeUomId;

    @ApiModelProperty(position = 26)
    private Double m3;

    @ApiModelProperty(position = 27)
    private Double weight;

    @ApiModelProperty(position = 28)
    private Long weightUomId;

    @ApiModelProperty(position = 29)
    private String vendorInfo;

    @ApiModelProperty(position = 30)
    private String luckyCode;

    @ApiModelProperty(position = 31)
    private List<Long> categories;

    @ApiModelProperty(position = 32)
    private List<ProductICSRequest> ics;

    @ApiModelProperty(position = 33)
    private List<UomCodeRequest> uomCode;

    @ApiModelProperty(position = 34)
    private List<ProductSkuRequest> batchCodeInformation;

    @ApiModelProperty(position = 35)
    private List<ProductPacketRequest> productPackage;

    @ApiModelProperty(position = 36)
    private List<ProductStockLevelRequest> stockLevels;

    @ApiModelProperty(position = 37)
    private List<ProductPriceRequest> productPrices;

}
