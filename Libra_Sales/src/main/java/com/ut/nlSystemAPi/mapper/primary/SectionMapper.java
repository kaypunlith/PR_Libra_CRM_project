package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.Section.Section;
import com.ut.nlSystemAPi.model.response.Section.SectionResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionMapper {

    List<SectionResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<SectionResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("section") Section section);

    Boolean update(@Param("section") Section section);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean deleteSectionCompany(@Param("id") Long id);

    Boolean insertSectionCompany(@Param("section") Section section);
}