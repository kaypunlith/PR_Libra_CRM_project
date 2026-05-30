package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.ProductGroup.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductGroupMapper {

    List<ProductGroupResponse> getList(@Param("filter") Filter filter);

    List<ICSResponse> getICSProduct(@Param("id") Long id);

    List<ProductPgroupResponse> getProductPgroup(@Param("id") Long id);

    List<UserPgroupResponse> getUserPgroup(@Param("id") Long id);

    Long countList(@Param("filter") Filter filter);

    List<ProductGroupResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("productGroup") ProductGroup productGroup);

    Boolean insertPgroupCompany(@Param("pgroupCompany") PgroupCompany pgroupCompany);

    Boolean insertUserPgroup(@Param("userPgroup") UserPgroup userPgroup);

    Boolean insertICSAccount(@Param("pgroupChartAcount") PgroupChartAcount pgroupChartAcount);

    Boolean insertProductPgroup(@Param("productPgroup") ProductPgroup productPgroup);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean update(@Param("productGroup") ProductGroup productGroup);

    Boolean deletePgroupCompany(@Param("id") Long id);

    Boolean deleteUserPgroup(@Param("id") Long id);

    Boolean deleteICSAccount(@Param("id") Long id);

    Boolean deleteProductPgroup(@Param("id") Long id);

    List<PriceTypePgroupResponse> getPriceType(@Param("id") Long id, @Param("userId") Long userId);

    Boolean delete(@Param("id")  Long id,@Param("userId") Long userId);


}