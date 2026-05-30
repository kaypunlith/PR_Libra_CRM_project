package com.ut.nlSystemAPi.mapper.primary;

public interface CodeCountMapper {

  Long countRecords(String table, String field, String pattern, String status);
}
