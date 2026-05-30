package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.VendorGroup;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.Dropdown.CompanyDropdownResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.VendorDropdownResponse;
import com.ut.nlSystemAPi.model.response.VendorContact.VendorContactResponse;
import com.ut.nlSystemAPi.model.response.VendorGroup.VendorGroupResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorGroupMapper {

  List<VendorGroupResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<VendorGroupResponse> getOne(@Param("id") Long id);

  Boolean insert(@Param("vendorGroup") VendorGroup vendorGroup);

  Boolean update(@Param("vendorGroup") VendorGroup vendorGroup);

  Boolean delete(@Param("id")  Long id,@Param("userId") Long userId);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  List<CompanyDropdownResponse> getVgroupCompany(@Param("vGroupId") Long vGroupId);

  Boolean insertVgroupCompany(@Param("vendorGroup") VendorGroup vendorGroup);

  Boolean deleteVgroupCompany(@Param("id") Long id);

  List<VendorDropdownResponse> getVendorVgroup(@Param("vGroupId") Long vGroupId);

  Boolean insertVendorVgroup(@Param("vendorGroup") VendorGroup vendorGroup);

  Boolean deleteVendorVgroup(@Param("id") Long id);

}