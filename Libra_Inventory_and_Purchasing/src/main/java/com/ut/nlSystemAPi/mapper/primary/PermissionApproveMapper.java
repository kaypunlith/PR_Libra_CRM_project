package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.PermissionApprove.DepartmentPermissionApproveResponse;
import com.ut.nlSystemAPi.model.response.PermissionApprove.PermissionApproveResponse;
import com.ut.nlSystemAPi.model.response.PermissionApprove.UserPermissionApproveResponse;
import com.ut.nlSystemAPi.model.response.Warehouse.LocationWarehouseResponse;
import com.ut.nlSystemAPi.model.response.Warehouse.UserWarehouseResponse;
import com.ut.nlSystemAPi.model.response.Warehouse.WarehouseResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionApproveMapper {

  List<PermissionApproveResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<PermissionApproveResponse> getOne(@Param("id") Long id);

  List<UserPermissionApproveResponse> getUserList(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("permissionApprove") PermissionApprove permissionApprove);

  Boolean insertUserPermissionApprove(@Param("userPermissionApprove") UserPermissionApprove userPermissionApprove);

  Boolean insertDepartmentPermissionApprove(@Param("departmentPermissionApprove") DepartmentPermissionApprove departmentPermissionApprove);

  Boolean deleteUserPermissionApprove(@Param("permissionApproveId") Long permissionApproveId);

  Boolean deleteDepartmentPermissionApprove(@Param("permissionApproveId") Long permissionApproveId);

  Boolean update(@Param("permissionApprove") PermissionApprove permissionApprove);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

}