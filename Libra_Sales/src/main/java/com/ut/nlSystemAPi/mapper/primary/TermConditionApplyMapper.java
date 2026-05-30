package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.TermConditionApply.TermConditionApply;
import com.ut.nlSystemAPi.model.filter.TermConditionApplyFilter;
import com.ut.nlSystemAPi.model.response.TermConditionApply.TermConditionApplyResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TermConditionApplyMapper {

  List<TermConditionApplyResponse> getList(@Param("filter") TermConditionApplyFilter filter);

  Long countList(@Param("filter") TermConditionApplyFilter filter);

  List<TermConditionApplyResponse> getOne(@Param("id") Long id);

  Boolean insert(@Param("termConditionApply") TermConditionApply termConditionApply);

  Boolean update(@Param("termConditionApply") TermConditionApply termConditionApply);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

}