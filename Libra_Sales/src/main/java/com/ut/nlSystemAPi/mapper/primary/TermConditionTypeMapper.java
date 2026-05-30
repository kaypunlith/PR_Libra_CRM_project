package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.TermConditionType.TermConditionType;
import com.ut.nlSystemAPi.model.response.TermConditionType.TermConditionTypeResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermConditionTypeMapper {

  List<TermConditionTypeResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<TermConditionTypeResponse> getOne(@Param("id") Long id);

  Boolean insert(@Param("termConditionType") TermConditionType termConditionType);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean update(@Param("termConditionType") TermConditionType termConditionType);

  Boolean delete(@Param("id")  Long id,@Param("userId") Long userId);

}