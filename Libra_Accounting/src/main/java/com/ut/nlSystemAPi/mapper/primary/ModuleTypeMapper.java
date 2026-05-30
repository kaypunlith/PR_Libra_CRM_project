package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.ModuleType;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.request.Login.ModuleType.ModuleTypeRequest;
import com.ut.nlSystemAPi.model.request.Login.ModuleType.ModuleTypeUpdateRequest;
import com.ut.nlSystemAPi.model.response.ModuleType.ModuleTypeResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleTypeMapper {

  List<ModuleType> getList(@Param("filter") Filter filter);

  List<ModuleTypeResponse> getListResponse(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<ModuleType> getOne(@Param("id") Long id);

  List<ModuleTypeResponse> getOneResponse(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("request") ModuleTypeRequest moduleTypeRequest, @Param("userId") Long userId);

  Boolean update(@Param("request") ModuleTypeUpdateRequest moduleTypeUpdateRequest, @Param("userId") Long userId);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

}
