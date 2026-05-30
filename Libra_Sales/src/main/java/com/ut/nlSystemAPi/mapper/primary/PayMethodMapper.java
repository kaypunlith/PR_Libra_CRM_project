package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.PayMethod.PayMethod;
import com.ut.nlSystemAPi.model.response.PayMethod.PayMethodResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayMethodMapper {

    List<PayMethodResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<PayMethodResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("payMethod") PayMethod payMethod);

    Boolean update(@Param("payMethod") PayMethod payMethod);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
