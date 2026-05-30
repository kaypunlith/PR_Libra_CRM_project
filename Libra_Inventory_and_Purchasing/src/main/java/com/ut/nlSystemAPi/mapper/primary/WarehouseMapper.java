package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.Role.RoleResponse;
import com.ut.nlSystemAPi.model.response.Warehouse.LocationWarehouseResponse;
import com.ut.nlSystemAPi.model.response.Warehouse.UserWarehouseResponse;
import com.ut.nlSystemAPi.model.response.Warehouse.WarehouseResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseMapper {

  List<WarehouseResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<WarehouseResponse> getOne(@Param("id") Long id);

  List<UserWarehouseResponse> getUserList(@Param("warehouseId") Long id);

  List<LocationWarehouseResponse> getLocationList(@Param("warehouseId") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("warehouse") Warehouse warehouse);

  Boolean insertUserWarehouse(@Param("userWarehouse") UserWarehouse userWarehouse);

  Boolean insertClassWarehouse(@Param("classWarehouse") ClassWarehouse classWarehouse);

  Boolean deleteUserWarehouse(@Param("warehouseId") Long warehouseId);

  Boolean deleteClassWarehouse(@Param("warehouseId") Long warehouseId);

  Boolean update(@Param("warehouse") Warehouse warehouse);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Boolean insertGroupTotal(@Param("locationGroupId") Long locationGroupId);

  Boolean insertGroupTotalDetail(@Param("locationGroupId") Long locationGroupId);

  Long checkTableExists(@Param("tableName") String tableName);

}