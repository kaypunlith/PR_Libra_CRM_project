package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.TermPrivacy.TermPrivacy;
import com.ut.nlSystemAPi.model.response.TermPrivacy.TermPrivacyResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermPrivacyMapper {

    List<TermPrivacyResponse> getList(@Param("filter") Filter filter);

    Boolean update(@Param("termPrivacy") TermPrivacy termPrivacy);
}
