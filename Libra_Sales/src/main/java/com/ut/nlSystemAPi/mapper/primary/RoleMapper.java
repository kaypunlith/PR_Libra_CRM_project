package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.ModuleType;
import com.ut.nlSystemAPi.model.Role;
import com.ut.nlSystemAPi.model.RoleUser;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.Role.RoleResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {

  List<RoleResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<RoleResponse> getOne(@Param("id") Long id);

  List<RoleUser> getUser(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("role") Role role);

  Boolean update(@Param("role") Role role);

  Boolean delete(@Param("userId") Long userId, @Param("id") Long id);

  List<ModuleType> getModule(@Param("userId") Long userId);

  List<ModuleType> getSettingModule(@Param("userId") Long userId);

  Boolean insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

  Boolean insertRoleUser(@Param("roleId") Long roleId, @Param("userId") Long userId);

  Boolean deleteRoleUser(@Param("roleId") Long roleId);

}

