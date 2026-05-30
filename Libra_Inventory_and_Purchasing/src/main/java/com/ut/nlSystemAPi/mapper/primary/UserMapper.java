package com.ut.nlSystemAPi.mapper.primary;


import com.ut.nlSystemAPi.model.Users.User;
import com.ut.nlSystemAPi.model.Users.UserBilling;
import com.ut.nlSystemAPi.model.Users.UserGroupList;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.User.UserResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

  List<UserResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<User> getOne(@Param("id") Long id);

  List<UserGroupList> getOneUserGroupList(@Param("id") Long id);

  List<User> getOneByUsername(@Param("username") String username);

  List<UserResponse> getOneByUserId(@Param("userId") Long userId);

  List<User> getOneByGroup(@Param("groupId") Long groupId);

  Boolean insert(@Param("user") User user);

  Boolean update(@Param("user") User user);

  Boolean editProfile(@Param("user") User user);

  Boolean delete(@Param("id") Long id);

  Boolean insertSystemRoleUser(@Param("userId") Long userId, @Param("groupId") String groupId);

  Boolean deleteSystemRoleUser(@Param("userId") Long userId);

  Long getEmployeeId(@Param("userId") Long userId);

  UserBilling getUserBilling(@Param("userId") Long userId);

}