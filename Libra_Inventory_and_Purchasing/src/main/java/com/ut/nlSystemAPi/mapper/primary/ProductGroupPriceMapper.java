package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.CalculateTotalProductFilter;
import com.ut.nlSystemAPi.model.request.Login.ProductGroupPrice.ProductGroupPriceRequest;
import com.ut.nlSystemAPi.model.response.Product.ProductPriceResponse;
import com.ut.nlSystemAPi.model.response.ProductGroupPrice.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductGroupPriceMapper {

     List<ProductGroupPriceResponse> getList(@Param("filter") Filter filter);

     List<ProductGroupPriceResponse> getOne(@Param("id") Long id);

     List<PgroupPriceResponse> getPgroupPrice(@Param("id") Long id);

     List<PgroupPriceResponse> listCloneDetail(@Param("id") Long id);

     Long countList(@Param("filter") Filter filter);

     Boolean insert(@Param("productGroupPrice") ProductGroupPrice productGroupPrice);

     Boolean insertPgroupPriceInformation(@Param("pgroupPriceInformation") PgroupPriceInformation pgroupPriceInformation);

     Boolean update(@Param("productGroupPrice") ProductGroupPrice productGroupPrice);

     Boolean deletePgroupPriceInformation(@Param("id") Long id, @Param("userId") Long userId);

     Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

     Long calculateTotal(@Param("filter") CalculateTotalProductFilter filter);

     List<TotalProductResponse> listTotal(@Param("filter") CalculateTotalProductFilter filter);

     Long countListTotal(@Param("filter") CalculateTotalProductFilter filter);

     Long checkDuplicateData(@Param("request") ProductGroupPriceRequest request);

     Boolean log(@Param("id") Long id, @Param("userId") Long userId);

     List<Long> getProductIdsByPgroupId(@Param("pgroupId") Long pgroupId, @Param("fromCost") Double fromCost, @Param("toCost") Double toCost);

     List<ProductPriceResponseBackup> getBackupProductPrice(@Param("productId") Long productId, @Param("priceTypeId") Long priceTypeId);

     Boolean insertProductPriceHistory(@Param("productPriceHistory") ProductPriceHistory productPriceHistory);

     Boolean deleteProductPrice(@Param("productId") Long productId, @Param("priceTypeId") Long priceTypeId);

     Boolean insertProductPrice(@Param("productPrice") ProductPrice productPrice);

     Long getMainUom(@Param("productId") Long productId);

     List<Long> getOtherUoms(@Param("mainUomId") Long mainUomId);

     List<PgroupPriceTypeResponse> listCloneType(@Param("pgroupId") Long pgroupId, @Param("fromCost") Double fromCost, @Param("toCost") Double toCost);

     List<PgroupPriceTypeResponse> listCloneTypeTotal(@Param("pgroupId") Long pgroupId, @Param("fromCost") Double fromCost, @Param("toCost") Double toCost);

     Double getUnitCost(@Param("productId") Long productId);

     Double getUomConversionValue(@Param("fromUomId") Long fromUomId, @Param("toUomId") Long toUomId);

}