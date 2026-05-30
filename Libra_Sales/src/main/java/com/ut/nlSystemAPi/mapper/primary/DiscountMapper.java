package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.Discount.Discount;
import com.ut.nlSystemAPi.model.response.Discount.DiscountResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountMapper {

    List<DiscountResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    Boolean insert(@Param("discount") Discount discount);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}