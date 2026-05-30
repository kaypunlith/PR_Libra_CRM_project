package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.ProductPriceList;
import com.ut.nlSystemAPi.model.ProductPriceListDetail;
import com.ut.nlSystemAPi.model.filter.ProductPriceListFilter;
import com.ut.nlSystemAPi.model.filter.ProductPriceListProductDetailFilter;
import com.ut.nlSystemAPi.model.request.Login.ProductPriceList.ProductListApproveStatus;
import com.ut.nlSystemAPi.model.response.ProductPriceList.ProductPriceListDetailResponse;
import com.ut.nlSystemAPi.model.response.ProductPriceList.ProductPriceListResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPriceListMapper {

     List<ProductPriceListResponse> getList(@Param("filter") ProductPriceListFilter filter, @Param("userId") Long userId);

     List<ProductPriceListDetailResponse> getListProductDetailByFilter(@Param("filter") ProductPriceListProductDetailFilter filter);

     List<ProductPriceListResponse> getOne(@Param("id") Long id);

     List<ProductPriceListDetailResponse> getListDetail(@Param("id") Long id);

     Long countList(@Param("filter") ProductPriceListFilter filter, @Param("userId") Long userId);

     Long countListProductDetail(@Param("filter") ProductPriceListProductDetailFilter filter);

     Long countListDetail(@Param("productPriceListId") Long productPriceListId);

     Boolean insert(@Param("productPriceList") ProductPriceList productPriceList);

     Boolean insertPriceListDetails(@Param("productPriceListDetail") ProductPriceListDetail productPriceListDetail);

     Boolean update(@Param("productPriceList") ProductPriceList productPriceList);

     Boolean updateApproveStatus(@Param("productListApproveStatus") ProductListApproveStatus productListApproveStatus);

     Boolean updateIsClose(@Param("productListApproveStatus") ProductListApproveStatus productListApproveStatus);

     Boolean deleteProductPriceListDetail(@Param("id")  Long id);

     Boolean delete(@Param("id")  Long id,@Param("userId") Long userId);

     String getReLastReceivePaymentCode();
}