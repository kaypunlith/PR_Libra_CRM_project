package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Promotional.Promotional;
import com.ut.nlSystemAPi.model.entity.Promotional.PromotionalDetail;
import com.ut.nlSystemAPi.model.entity.Promotional.PromotionalSubDetail;
import com.ut.nlSystemAPi.model.filter.PromotionalFilter;
import com.ut.nlSystemAPi.model.response.Promotional.PromotionalDetailResponse;
import com.ut.nlSystemAPi.model.response.Promotional.PromotionalResponse;
import com.ut.nlSystemAPi.model.response.Promotional.PromotionalSubDetailResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionalMapper {

    List<PromotionalResponse> getList(@Param("filter") PromotionalFilter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") PromotionalFilter filter, @Param("userId") Long userId);

    List<PromotionalResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("promotional") Promotional promotional);

    Boolean update(@Param("promotional") Promotional promotional);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    List<PromotionalDetailResponse> getDetails(@Param("promotionalId") Long promotionalId);

    List<PromotionalSubDetailResponse> getSubDetails(@Param("promotionalDetailId") Long promotionalDetailId);

    Boolean insertDetail(@Param("detail") PromotionalDetail detail);

    Boolean insertSubDetail(@Param("subDetail") PromotionalSubDetail subDetail);

    Boolean deleteSubDetails(@Param("promotionalId") Long promotionalId);

    Boolean deleteDetails(@Param("promotionalId") Long promotionalId);
}
