package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.CMTerm.CMTerm;
import com.ut.nlSystemAPi.model.response.CMTerm.CMTermResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CMTermMapper {

  List<CMTermResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<CMTermResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("cmTerm") CMTerm cmTerm);

  Boolean update(@Param("cmTerm") CMTerm cmTerm);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);


}