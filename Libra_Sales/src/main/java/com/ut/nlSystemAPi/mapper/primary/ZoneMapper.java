package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.Zone.Zone;
import com.ut.nlSystemAPi.model.entity.Zone.ZoneDetail;
import com.ut.nlSystemAPi.model.response.Zone.ZoneDetailResponse;
import com.ut.nlSystemAPi.model.response.Zone.ZoneResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneMapper {

    List<ZoneResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<ZoneResponse> getOne(@Param("id") Long id);

    List<ZoneDetailResponse> getDetails(@Param("zoneId") Long zoneId);

    Boolean insert(@Param("zone") Zone zone);

    Boolean update(@Param("zone") Zone zone);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insertDetail(@Param("detail") ZoneDetail detail);

    Boolean deleteDetails(@Param("zoneId") Long zoneId);
}
