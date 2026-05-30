package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.Currency;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.Currency.CurrencyResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyMapper {

  List<CurrencyResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<CurrencyResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("currency") Currency currency);

  Boolean update(@Param("currency") Currency currency);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

}