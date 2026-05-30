package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.Department;
import com.ut.nlSystemAPi.model.DepartmentDetail;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.DepartmentListResponse;
import com.ut.nlSystemAPi.model.response.DepartmentResponse;
import com.ut.nlSystemAPi.model.response.LocationPointResponse;
import com.ut.nlSystemAPi.model.response.GroupResponse;
import com.ut.nlSystemAPi.model.response.UserDepartment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMapper {

    List<DepartmentResponse> getList(@Param("filter") Filter filter, @Param("userId") Long userId);

    List<UserDepartment> listUser(@Param("departmentId") Long departmentId);

    List<DepartmentResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("department") Department department);

    Boolean update(@Param("department") Department department);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insertDepartmentUser(@Param("departmentId") Long departmentId, @Param("userId") Long userId);

    Boolean deleteDepartmentUser(@Param("departmentId") Long departmentId);

    Department getDepartmentId(@Param("name") String name);

    List<GroupResponse> getGroupList(@Param("userId") Long userId);

    List<DepartmentListResponse> getDepartmentList(@Param("settingGroupId") Long settingGroupId , @Param("userId") Long userId);

    Boolean insertDepartmentDetail(@Param("departmentDetail") DepartmentDetail departmentDetail);

    List<LocationPointResponse> listDepartmentDetails(@Param("departmentId") Long departmentId);

    Boolean deleteDepartmentDetails(@Param("departmentId") Long departmentId);

}
