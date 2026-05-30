package com.ut.nlSystemAPi.mapper.primary;
import com.ut.nlSystemAPi.model.entity.PriceType.PriceType;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.PriceType.PriceTypeResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PriceTypeMapper {

  List<PriceTypeResponse> getList(@Param("filter") Filter filter, @Param("userId") Long userId);

  Long countList(@Param("filter") Filter filter, @Param("userId") Long userId);

  List<PriceTypeResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

  Boolean insert(@Param("priceType") PriceType priceType);

  Boolean update(@Param("priceType") PriceType priceType);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userid);

  Boolean deletePriceTypeCompany(@Param("id") Long id);

  Boolean insertPriceTypeCompany(@Param("priceType") PriceType priceType);

  Boolean archivePriceTypePos(@Param("companyId") Long companyId);

  Boolean insertPriceTypePos(@Param("priceType") PriceType priceType, @Param("userId") Long userId);

  Long checkDuplicate(@Param("name") String name, @Param("companyId") Long companyId, @Param("id") Long id);

  Boolean ordering(@Param("priceType") PriceType priceType);

  Long checkPos(@Param("id") Long id);

}