package com.ut.nlSystemAPi.mapper.primary;
import com.ut.nlSystemAPi.model.entity.TermCondition.TermCondition;
import com.ut.nlSystemAPi.model.filter.TermConditionFilter;
import com.ut.nlSystemAPi.model.response.TermCondition.TermConditionResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TermConditionMapper {

  List<TermConditionResponse> getList(@Param("filter") TermConditionFilter filter);

  Long countList(@Param("filter") TermConditionFilter filter);

  List<TermConditionResponse> getOne(@Param("id") Long id);

  Boolean insert(@Param("termCondition") TermCondition termCondition);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean update(@Param("termCondition") TermCondition termCondition);

  Boolean delete(@Param("id")  Long id,@Param("userId") Long userId);

}