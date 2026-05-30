package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.model.request.Login.Product.CalculateProductUnitPriceRequest;
import com.ut.nlSystemAPi.model.request.Login.Product.ProductActiveStatus;
import com.ut.nlSystemAPi.model.request.Login.Product.ProductEndOfLife;
import com.ut.nlSystemAPi.model.response.Product.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {

  List<ProductResponse> getList(@Param("filter") ProductFilter filter, @Param("userId") Long userId);

  Long countList(@Param("filter") ProductFilter filter, @Param("userId") Long userId);

  List<ProductDetailResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("product") Products product);

  List<CategoryResponse> getProductCategory(@Param("productId") Long productId);

  List<ProductICSResponse> getICSAccount(@Param("productId") Long productId);

  List<ProductSkuResponse> getProductSku(@Param("productId") Long productId);

  List<ProductPacketResponse> getProductPacket(@Param("productId") Long productId);

  List<PriceRequestInformationResponse> getPriceRequestInformation(@Param("productId") Long productId);

  List<ProductStockLevelResponse> getProductStockLevels(@Param("productId") Long productId);

  List<ProductPhotoResponse> getProductPhotos(@Param("productId") Long productId);

  Boolean insertProductImage(@Param("productImage") ProductImage productImage);

  Boolean deleteProductImage(@Param("productId") Long productId);

  Boolean insertProductCategory(@Param("productCategory") ProductCategory productCategory);

  Boolean insertICSAccount(@Param("productChartAccount") ProductChartAccount productChartAccount);

  Boolean insertProductPgroup(@Param("productPgroup") ProductPgroup productPgroup);

  Boolean insertProductSku(@Param("productSku") ProductSku productSku);

  Boolean insertProductPacket(@Param("productPacket") ProductPacket productPacket);

  Boolean insertProductStockLevel(@Param("productStockLevel") ProductStockLevel productStockLevel);

  Boolean deleteProductPacket(@Param("productId") Long productId);

  Boolean deleteProductCategory(@Param("productId") Long productId);

  Boolean deleteProductSku(@Param("productId") Long productId);

  Boolean deleteICSAccount(@Param("productId") Long productId);

  Boolean deleteProductPgroup(@Param("productId") Long productId);

  Boolean archiveProductStockLevel(@Param("productId") Long productId, @Param("userId") Long userId);

  Boolean update(@Param("product") Products product);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Boolean updateActiveStatus(@Param("status") ProductActiveStatus productActiveStatus, @Param("userId") Long userId);

  Long getConversion(@Param("uomId") Long uomId);

  List<ProductPriceResponse> getListPrice(@Param("filter") ProductPriceFilter filter, @Param("userId") Long userId);

  List<ProductPriceDetailResponse> getListPriceDetail(@Param("filter") ProductPriceFilter filter);

  Long countListPrice(@Param("filter") ProductPriceFilter filter);

  List<ProductPriceHistoryResponse> getListPriceHistory(@Param("filter") ProductPriceFilter filter);

  Long countListPriceHistory(@Param("filter") ProductPriceFilter filter);

  Boolean deletePrice(@Param("productId") Long productId);

  Boolean addPrice(@Param("productPrice") ProductPrice productPrice);

  Boolean addPriceHistory(@Param("productPriceHistory") ProductPriceHistory productPriceHistory);

  Boolean updateCost(@Param("product") Products product);

  List<String> getUomSku(@Param("filter") UomSkuFilter filter);

  Boolean isEndOfLife(@Param("request") ProductEndOfLife request);

  List<ProductGroupPriceSettingResponse> getProductGroupPrice(@Param("unitCost") Double unitCost, @Param("productGroupId") Long productGroupId);

  List<ProductPriceDetailHistoryResponse> calculateProductUnitPrice(@Param("request") UomsProductFilter request, @Param("uomId") Long uomId);

  Double getProductUnitCost(@Param("id") Long id);

}
