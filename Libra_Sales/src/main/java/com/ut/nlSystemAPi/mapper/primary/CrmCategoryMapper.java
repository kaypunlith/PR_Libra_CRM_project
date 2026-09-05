package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.CrmCategory.CrmCategory;
import com.ut.nlSystemAPi.model.response.CrmCategory.CrmCategoryResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmCategoryMapper {

    List<CrmCategoryResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<CrmCategoryResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("category") CrmCategory category);

    Boolean update(@Param("category") CrmCategory category);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
