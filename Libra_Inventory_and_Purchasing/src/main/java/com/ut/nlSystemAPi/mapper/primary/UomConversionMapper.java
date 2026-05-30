package com.ut.nlSystemAPi.mapper.primary;
import com.ut.nlSystemAPi.model.OtherUomConversion;
import com.ut.nlSystemAPi.model.UomConversion;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.UomConversion.UomConversionDetailResponse;
import com.ut.nlSystemAPi.model.response.UomConversion.UomConversionResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UomConversionMapper {

    List<UomConversionResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<UomConversionResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("uomConversion") UomConversion uomConversion);

    Boolean update(@Param("uomConversion") UomConversion uomConversion);

    Boolean insertOther(@Param("other") OtherUomConversion other);

    List<UomConversionDetailResponse> getSmallUomDetail(@Param("id") Long id);

    String getSmallUomById(@Param("id") Long id);

    Boolean deleteOther(@Param("id") Long id,@Param("userId") Long userId);

    Boolean delete(@Param("id")  Long id,@Param("userId") Long userId);

}