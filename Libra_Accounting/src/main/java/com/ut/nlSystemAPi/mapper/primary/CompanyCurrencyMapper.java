package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.CompanyCurrency;
import com.ut.nlSystemAPi.model.Currency;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.CompanyCurrencyFilter;
import com.ut.nlSystemAPi.model.response.CompanyCurrency.CompanyCurrencyResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyCurrencyMapper {

  List<CompanyCurrencyResponse> getList(@Param("filter") CompanyCurrencyFilter filter);

  Long countList(@Param("filter") Filter filter);

  List<CompanyCurrencyResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("companyId") Long companyId, @Param("currencyId") Long currencyId, @Param("id") Long id);

  Boolean insert(@Param("companyCurrency") CompanyCurrency companyCurrency);

  Boolean update(@Param("companyCurrency") CompanyCurrency companyCurrency);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Long getCompanyIdByCompanyCurrencyId(@Param("companyCurrencyId") Long companyCurrencyId);

  Boolean resetPos();

  Boolean applyPos(@Param("id") Long id, @Param("userId") Long userId);

  Boolean updateCompanyPos(@Param("id") Long id, @Param("companyId") Long companyId, @Param("userId") Long userId);

}