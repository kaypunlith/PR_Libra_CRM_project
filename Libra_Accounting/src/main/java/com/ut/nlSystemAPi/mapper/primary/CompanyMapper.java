package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.Company;
import com.ut.nlSystemAPi.model.Taxation;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.Company.CompanyResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyMapper {

    List<CompanyResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<CompanyResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("company") Company company);

    Boolean update(@Param("company") Company company);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);



}
