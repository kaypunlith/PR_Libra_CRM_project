package com.ut.nlSystemAPi.model.response.ProductGroupPrice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
@Data
public class PgroupPriceResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Double fromCost;

    @ApiModelProperty(position = 2)
    private Double grandTotalFromCost;

    @ApiModelProperty(position = 3)
    private Double toCost;

    @ApiModelProperty(position = 4)
    private Double grandTotalToCost;

    @ApiModelProperty(position = 4)
    private Long totalProduct;

    @ApiModelProperty(position = 4)
    private Long grandTotalProduct;

    @ApiModelProperty(position = 5)
    private Double value;

    @ApiModelProperty(position = 5)
    private Double grandTotalValue;

    @ApiModelProperty(position = 5)
    private Double GeneralOmronReseller_Pricex;

    @ApiModelProperty(position = 6)
    private Double EducationalInstitutionSellingPrice;

    @ApiModelProperty(position = 7)
    private Double GeneralCustomerSellingPrice;

    @ApiModelProperty(position = 8)
    private Double FactorySellingPrice;

    @ApiModelProperty(position = 9)
    private Double CatalogueDisplaySellingPrice;

    @ApiModelProperty(position = 10)
    private Double ContractorEngineeringBuilderSellingPrice;

    @ApiModelProperty(position = 11)
    private Double ExclusiveOmronResellerPrice;

    @ApiModelProperty(position = 12)
    private Double FactorySellingPricemnb;

    @ApiModelProperty(position = 5)
    private Double FactorySellingPrice35;

    @ApiModelProperty(position = 9)
    private List<PgroupPriceTypeResponse> priceTypeResponses;

    @ApiModelProperty(position = 10)
    private List<PgroupPriceTypeResponse> priceTypeGrandTotalResponses;

}
