package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.filter.VendorFilter;
import com.ut.nlSystemAPi.model.request.Login.VendorManagement.VendorPhoto;
import com.ut.nlSystemAPi.model.response.Uoms.UomsResponse;
import com.ut.nlSystemAPi.model.response.VendorManagement.GeneralLedgerDetailResponse;
import com.ut.nlSystemAPi.model.response.VendorManagement.GeneralLedgerResponse;
import com.ut.nlSystemAPi.model.response.VendorManagement.VendorDetailResponse;
import com.ut.nlSystemAPi.model.response.VendorManagement.VendorManagementResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorManagementMapper {

  List<VendorManagementResponse> getList(@Param("filter") VendorFilter filter,@Param("tableName") String tableName, @Param("userId") Long userId);

  List<GeneralLedgerDetailResponse> getListGeneralLedgerDetail(@Param("filter") VendorFilter filter, @Param("userId") Long userId);

  Long countList(@Param("filter") VendorFilter filter, @Param("tableName") String tableName, @Param("userId") Long userId);

  List<VendorManagementResponse> getOne(@Param("id") Long id);

  Boolean insert(@Param("vendorManagement") VendorManagement vendorManagement);

  List<VendorDetailResponse> getVendorCompany(@Param("id") Long id);

  VendorPhoto getPhoto(@Param("id") Long id);

  Boolean insertVendorCompany(@Param("vendorCompany") VendorCompany vendorCompany);

  Boolean deleteVendorCompany(@Param("id") Long id);

  List<VendorDetailResponse> getVendorVgroup(@Param("id") Long id);

  Boolean insertVendorVgroup(@Param("vendorVgroup") VendorGroup vendorVgroup);

  Boolean deleteVendorVgroup(@Param("id") Long id);

  List<VendorDetailResponse> getVendorContact(@Param("id") Long id);

  Boolean insertVendorContact(@Param("vendorContact")VendorContact vendorContact);

  List<VendorDetailResponse> getVendorCurrencies(@Param("id") Long id);

  Boolean insertVendorCurrencies(@Param("vendorCurrencies") CurrencyInformation vendorCurrencies);

  Boolean deleteVendorCurrencies(@Param("id") Long id);

  Boolean update(@Param("vendorManagement") VendorManagement vendorManagement);

  Boolean delete(@Param("id")  Long id,@Param("userId") Long userId);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean createTable(@Param("tableName") String tableName);

  Boolean dropTable(@Param("tableName") String tableName);

  Boolean insertTable(@Param("generalLedgerDetail") GeneralLedgerDetail generalLedgerDetail, @Param("tableName")  String tableName);

}