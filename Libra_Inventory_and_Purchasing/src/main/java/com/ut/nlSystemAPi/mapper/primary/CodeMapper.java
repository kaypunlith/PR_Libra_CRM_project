package com.ut.nlSystemAPi.mapper.primary;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeMapper extends CodeCountMapper {

    Boolean updateCode(@Param("tableName") String tableName, @Param("field") String field, @Param("code") String code, @Param("id") Long id);

    String getCode(@Param("tableName") String tableName, @Param("field") String field, @Param("id") Long id);
}
