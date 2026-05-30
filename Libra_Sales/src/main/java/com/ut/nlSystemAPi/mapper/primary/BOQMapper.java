package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.BOQ.BOQ;
import com.ut.nlSystemAPi.model.entity.BOQ.BOQDetail;
import com.ut.nlSystemAPi.model.response.BOQ.BOQDetailResponse;
import com.ut.nlSystemAPi.model.response.BOQ.BOQResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BOQMapper {

  List<BOQResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<BOQResponse> getOne(@Param("id") Long id);

  Boolean insert(@Param("boq") BOQ boq);

  Boolean insertDetail(@Param("detail") BOQDetail boqDetail);

  List<BOQDetailResponse> getListDetail(@Param("boqId") Long boqId);

  Boolean approve(@Param("id") Long id, @Param("status") Long status, @Param("userId") Long userId);

}