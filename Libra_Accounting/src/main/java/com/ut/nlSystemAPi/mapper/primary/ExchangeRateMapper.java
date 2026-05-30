package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.ExchangeRate;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.ExchangeRateFilter;
import com.ut.nlSystemAPi.model.response.ExchangeRate.ExchangeRateResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRateMapper {

  List<ExchangeRateResponse> getList(@Param("filter") ExchangeRateFilter filter);

  Long countList(@Param("filter") ExchangeRateFilter filter);

  List<ExchangeRateResponse> getOne(@Param("id") Long id);

  // Find

  List<ExchangeRateResponse> getExchangeRateList(@Param("filter") ExchangeRateFilter filter);

  Long countListExchangeRate(@Param("filter") ExchangeRateFilter filter);

  // Update
  Boolean insertExchangeRate (@Param("exchangeRate") ExchangeRate exchangeRate);

  Boolean updateExchangeRate (@Param("exchangeRate") ExchangeRate exchangeRate);

  Boolean updateCompanyCurrency (@Param("exchangeRate") ExchangeRate exchangeRate);

}