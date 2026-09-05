package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Quotation.QuotationLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationLogMapper {

    Boolean insertLog(QuotationLog log);

    List<QuotationLog> getLogsByQuotationId(Long quotationId);

}
