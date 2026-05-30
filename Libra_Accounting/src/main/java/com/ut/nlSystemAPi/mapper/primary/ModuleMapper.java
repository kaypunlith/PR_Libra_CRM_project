package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.Module;
import com.ut.nlSystemAPi.model.filter.ModuleFilter;
import com.ut.nlSystemAPi.model.request.Login.Module.ModuleRequest;
import com.ut.nlSystemAPi.model.request.Login.Module.ModuleUpdateRequest;
import com.ut.nlSystemAPi.model.response.Module.ModuleResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleMapper {

  List<ModuleResponse> getList(@Param("filter") ModuleFilter filter);

  Long countList(@Param("filter") ModuleFilter filter);

  List<ModuleResponse> getOneModule(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("moduleTypeId") Long moduleTypeId, @Param("id") Long id);

  Boolean insert(@Param("request") ModuleRequest moduleRequest, @Param("userId") Long userId);

  Boolean update(@Param("request") ModuleUpdateRequest moduleUpdateRequest, @Param("userId") Long userId);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  List<Module> getOne(@Param("id") Long id);

  List<Module> getOneByRoleId(@Param("id") Long id, @Param("roleId") Long roleId);

  List<Module> getOneByUserId(@Param("id") Long id, @Param("userId") Long userId);

}
