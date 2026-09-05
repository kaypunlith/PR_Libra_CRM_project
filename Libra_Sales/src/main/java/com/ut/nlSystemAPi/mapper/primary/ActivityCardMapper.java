package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.ActivityCard.ActivityCard;
import com.ut.nlSystemAPi.model.filter.ActivityCardFilter;
import com.ut.nlSystemAPi.model.response.ActivityCard.ActivityCardCustomerResponse;
import com.ut.nlSystemAPi.model.response.ActivityCard.ActivityCardResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityCardMapper {

    List<ActivityCardCustomerResponse> getList(@Param("filter") ActivityCardFilter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") ActivityCardFilter filter, @Param("userId") Long userId);

    List<ActivityCardResponse> getListReport(@Param("filter") ActivityCardFilter filter, @Param("userId") Long userId);

    Long countListReport(@Param("filter") ActivityCardFilter filter, @Param("userId") Long userId);

    List<ActivityCardResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insert(@Param("activityCard") ActivityCard activityCard);

    Boolean updateCustomerContactPosition(@Param("customerContactId") Long customerContactId, @Param("position") String position);

    Boolean delete(@Param("id") Long id);
}
