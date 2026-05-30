package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.ProductBrand;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.ProductBrand.ProductBrandResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductBrandMapper {

    List<ProductBrandResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<ProductBrandResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("productBrand") ProductBrand productBrand);

    Boolean update(@Param("productBrand") ProductBrand productBrand);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);
}
