package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.BOM.BOM;
import com.ut.nlSystemAPi.model.entity.BOM.BOMDetail;
import com.ut.nlSystemAPi.model.filter.BOMFilter;
import com.ut.nlSystemAPi.model.response.BOM.BOMDetailResponse;
import com.ut.nlSystemAPi.model.response.BOM.BOMResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BOMMapper {

  List<BOMResponse> getList(@Param("filter") BOMFilter filter);

  Long countList(@Param("filter") BOMFilter filter);

  List<BOMResponse> getOne(@Param("id") Long id);

  Boolean insert(@Param("bom") BOM bom);

  Boolean insertDetail(@Param("detail") BOMDetail bomDetail);

  List<BOMDetailResponse> getListDetail(@Param("bomId") Long bomId);

  Boolean approve(@Param("id") Long id, @Param("status") Long status, @Param("userId") Long userId);

}