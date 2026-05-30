package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String photo;

    @ApiModelProperty(position = 2)
    private String photoName;

    @ApiModelProperty(position = 2)
    private List<ProductPhotoResponse> photos;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String companyName;

    @ApiModelProperty(position = 3)
    private Long brandId;

    @ApiModelProperty(position = 3)
    private String brandName;

    @ApiModelProperty(position = 4)
    private Long parentId;

    @ApiModelProperty(position = 3)
    private String parentName;

    @ApiModelProperty(position = 5)
    private String name;

    @ApiModelProperty(position = 5)
    private String nameKh;

    @ApiModelProperty(position = 5)
    private Long groupId;

    @ApiModelProperty(position = 5)
    private Long isEndOfLife;

    @ApiModelProperty(position = 5)
    private Long isTransaction;

    @ApiModelProperty(position = 5)
    private String groupName;

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
    private Long smallUomId;

    @ApiModelProperty(position = 10)
    private Long uomId;

    @ApiModelProperty(position = 10)
    private String uomName;

    @ApiModelProperty(position = 11)
    private Double unitCost;

    @ApiModelProperty(position = 12)
    private String periodFrom;

    @ApiModelProperty(position = 13)
    private String periodTo;

    @ApiModelProperty(position = 16)
    private Long productReorderLevel;

    @ApiModelProperty(position = 21)
    private Long isExpiredDate;

    @ApiModelProperty(position = 21)
    private Long isPriceRequest;

    @ApiModelProperty(position = 18)
    private String sku;

    @ApiModelProperty(position = 19)
    private String spec;

    @ApiModelProperty(position = 20)
    private String description;

    @ApiModelProperty(position = 21)
    private String fileCatalog;

    @ApiModelProperty(position = 21)
    private String fileCatalogName;

    @ApiModelProperty(position = 22)
    private Double length;

    @ApiModelProperty(position = 23)
    private Double height;

    @ApiModelProperty(position = 24)
    private Double width;

    @ApiModelProperty(position = 25)
    private Long sizeUomId;

    @ApiModelProperty(position = 25)
    private String sizeUomName;

    @ApiModelProperty(position = 26)
    private Double m3;

    @ApiModelProperty(position = 27)
    private Double weight;

    @ApiModelProperty(position = 28)
    private Long weightUomId;

    @ApiModelProperty(position = 28)
    private String weightUomName;

    @ApiModelProperty(position = 29)
    private String vendorInfo;

    @ApiModelProperty(position = 30)
    private String luckyCode;

    @ApiModelProperty(position = 31)
    private List<CategoryResponse> categories;

    @ApiModelProperty(position = 32)
    private List<ProductICSResponse> ics;

    @ApiModelProperty(position = 33)
    private List<ProductSkuResponse> uomCode;

    @ApiModelProperty(position = 34)
    private List<ProductSkuResponse> batchCodeInformation;

    @ApiModelProperty(position = 35)
    private List<ProductPacketResponse> productPackages;

    @ApiModelProperty(position = 36)
    private List<ProductStockLevelResponse> stockLevels;

    @ApiModelProperty(position = 37)
    private List<ProductPriceResponse> productPrices;

    @ApiModelProperty(position = 36)
    private List<PriceRequestInformationResponse> priceRequestInformation;

    @ApiModelProperty(position = 32)
    private String created;

    @ApiModelProperty(position = 32)
    private String createdBy;

    @ApiModelProperty(position = 32)
    private String modified;

    @ApiModelProperty(position = 32)
    private String modifiedBy;

}
