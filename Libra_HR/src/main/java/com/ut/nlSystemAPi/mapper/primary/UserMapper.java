package com.ut.nlSystemAPi.mapper.primary;


import com.ut.nlSystemAPi.model.Users.User;
import com.ut.nlSystemAPi.model.Users.UserDepartmentList;
import com.ut.nlSystemAPi.model.Users.UserGroupList;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.ApplyUserFilter;
import com.ut.nlSystemAPi.model.response.ApplyUserList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

  List<User> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<User> getOne(@Param("id") Long id);

  List<UserGroupList> getOneUserGroupList(@Param("id") Long id);

  List<User> getOneByUsername(@Param("username") String username);

  List<User> getOneByGroup(@Param("groupId") Long groupId);

  Boolean insertUserGroup(@Param("userId") Long userId, @Param("groupId") String groupId);

  Boolean insert(@Param("user") User user);

  Boolean update(@Param("user") User user);

  Boolean updateUser(@Param("user") User user);

  Boolean delete(@Param("id") Long id);

  Boolean insertSystemRoleUser(@Param("userId") Long userId, @Param("groupId") String groupId);

  Boolean deleteSystemRoleUser(@Param("userId") Long userID);

  // Apply to user department

  Boolean insertApplyUserDepartment(@Param("userId") Long userId, @Param("departmentId") String departmentId);

  Boolean updateUserApplyDepartment(@Param("userId") Long userId);

  Boolean deleteApplyUserDepartment(@Param("userId") Long userId);

  List<UserDepartmentList> getUserApplyDepartment(@Param("userId") Long userId);

  List<ApplyUserList> getListApplyUser(@Param("filter") ApplyUserFilter filter);

  void updateEmployeeId(@Param("employeeId") Long employeeId, @Param("userId") Long userId);

  // oauth2
  Long countAccessTokenByUsername(@Param("username") String username);

  Boolean deleteAccessTokenByUsername(@Param("username") String username);

}