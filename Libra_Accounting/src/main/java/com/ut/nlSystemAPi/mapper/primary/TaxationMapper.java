package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.Taxation;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.TaxationFilter;
import com.ut.nlSystemAPi.model.response.Dropdown.ModuleTypeResponse;
import com.ut.nlSystemAPi.model.response.Taxation.TaxationResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxationMapper {

    List<TaxationResponse> getList(@Param("filter") TaxationFilter filter);

    Long countList(@Param("filter") TaxationFilter filter);

    List<TaxationResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("taxation") Taxation taxation);

    Boolean update(@Param("taxation") Taxation taxation);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    List<ModuleTypeResponse> getModuleTypeByModuleId(@Param("vatSettingId") Long vatSettingId);

    Boolean insertVatSettingRate(@Param("vatSettingId") Long vatSettingId, @Param("rate") Double rate, @Param("taxation") Taxation taxation);

    Boolean insertVatModule(@Param("vatSettingId") Long vatSettingId, @Param("moduleTypeId") Long moduleTypeId, @Param("taxation") Taxation taxation);

    Boolean deactivatePreviousVatSettingRate(@Param("vatSettingId") Long vatSettingId, @Param("modifiedBy") Long modifiedBy);

    Boolean deactivatePreviousVatModules(@Param("vatSettingId") Long vatSettingId);

    Boolean UpdateNullModules(@Param("vatSettingId") Long vatSettingId);

    List<Long> getVatSettingTypeSales();

}
