package com.ut.nlSystemAPi.mapper.primary;


import com.ut.nlSystemAPi.model.ClassAccount;
import com.ut.nlSystemAPi.model.Company;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.ClassAccount.ClassAccountResponse;
import com.ut.nlSystemAPi.model.response.User.UserResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassAccountMapper {

  List<ClassAccountResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<Company> getCompanyByClassId(@Param("classId") Long classId);

  List<UserResponse> getUserByClassId(@Param("classId") Long classId);

  List<ClassAccountResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("classAccount") ClassAccount classAccount);

  Boolean insertCompanyId(@Param("classAccountId") Long classAccountId,  @Param("companyId") Long companyId);

  Boolean insertUserClass(@Param("classAccountId") Long classAccountId,  @Param("userId") Long userId);

  Boolean update(@Param("classAccount") ClassAccount classAccount);

  Boolean deleteClassCompany(@Param("classAccountId") Long classAccountId);

  Boolean deleteClassUser(@Param("classAccountId") Long classAccountId);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

}