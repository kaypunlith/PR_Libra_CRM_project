package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.Competitor.Competitor;
import com.ut.nlSystemAPi.model.response.Competitor.CompetitorDetailResponse;
import com.ut.nlSystemAPi.model.response.Competitor.CompetitorResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitorMapper {

    List<CompetitorResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<CompetitorResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("competitor") Competitor competitor);

    Boolean update(@Param("competitor") Competitor competitor);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    List<CompetitorDetailResponse> getMainProduct(@Param("competitorId") Long competitorId);

    List<CompetitorDetailResponse> getProductType(@Param("competitorId") Long competitorId);

    List<CompetitorDetailResponse> getBusinessType(@Param("competitorId") Long competitorId);

    List<CompetitorDetailResponse> getWeakness(@Param("competitorId") Long competitorId);

    List<CompetitorDetailResponse> getStrength(@Param("competitorId") Long competitorId);

    Boolean insertMainProduct(@Param("competitorId") Long competitorId, @Param("mainProductId") Long mainProductId, @Param("userId") Long userId);

    Boolean insertProductType(@Param("competitorId") Long competitorId, @Param("productTypeId") Long productTypeId, @Param("userId") Long userId);

    Boolean insertBusinessType(@Param("competitorId") Long competitorId, @Param("businessTypeId") Long businessTypeId, @Param("userId") Long userId);

    Boolean insertWeakness(@Param("competitorId") Long competitorId, @Param("weaknessId") Long weaknessId, @Param("userId") Long userId);

    Boolean insertStrength(@Param("competitorId") Long competitorId, @Param("strengthId") Long strengthId, @Param("userId") Long userId);

    Boolean deleteMainProduct(@Param("competitorId") Long competitorId, @Param("userId") Long userId);

    Boolean deleteProductType(@Param("competitorId") Long competitorId, @Param("userId") Long userId);

    Boolean deleteBusinessType(@Param("competitorId") Long competitorId, @Param("userId") Long userId);

    Boolean deleteWeakness(@Param("competitorId") Long competitorId, @Param("userId") Long userId);

    Boolean deleteStrength(@Param("competitorId") Long competitorId, @Param("userId") Long userId);

    String getEmployeeName(@Param("userId") Long userId);

    String getModifiedDate(@Param("id") Long id);
}