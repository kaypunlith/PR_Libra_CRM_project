package com.ut.nlSystemAPi.helper;

import com.ut.nlSystemAPi.mapper.primary.CodeCountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
public class GenerateCode {

  @Autowired
  private CodeCountMapper codeCountMapper;

  public static String incrementNumericPart(String input) {
    String prefix = input.replaceAll("\\d+$", "");
    String numericPart = input.substring(prefix.length());

    int number = Integer.parseInt(numericPart);
    number += 1;

    String incrementedNumericPart = String.format("%0" + numericPart.length() + "d", number);
    return prefix + incrementedNumericPart;
  }

  public static String generateReference(String input) {
    return input + "0000001";
  }

  public String generateAutoCode(CodeCountMapper mapper, String table, String field, int len, String charPrefix, boolean useYear, String status) {
    String yearPrefix = useYear ? String.valueOf(Year.now().getValue() % 100) : "";
    String condition = status != null && !status.isEmpty() ? " AND " + status : "";

    String pattern = "%" + yearPrefix + charPrefix + "%";
    long count = mapper.countRecords(table, field, pattern, condition);

    long nextNumber = count + 1;
    String numberPart = String.format("%0" + len + "d", nextNumber);

    return yearPrefix + charPrefix + numberPart;
  }

  public String generateAutoCode(String table, String field, int len, String charPrefix, boolean useYear, String status) {
    return generateAutoCode(codeCountMapper, table, field, len, charPrefix, useYear, status);
  }
}
