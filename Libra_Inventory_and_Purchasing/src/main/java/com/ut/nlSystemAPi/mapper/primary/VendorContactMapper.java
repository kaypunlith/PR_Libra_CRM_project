package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.VendorContact;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.VendorContactFilter;
import com.ut.nlSystemAPi.model.response.VendorContact.VendorContactResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorContactMapper {

  List<VendorContactResponse> getList(@Param("filter") VendorContactFilter filter);

  Long countList(@Param("filter") VendorContactFilter filter);

  List<VendorContactResponse> getOne(@Param("id") Long id);

  Boolean insert(@Param("vendorContact") VendorContact vendorContact);

  Boolean update(@Param("vendorContact") VendorContact vendorContact);

  Boolean delete(@Param("id")  Long id,@Param("userId") Long userId);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

}