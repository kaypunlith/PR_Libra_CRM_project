package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.DeliverySchedule.DeliverySchedule;
import com.ut.nlSystemAPi.model.entity.DeliverySchedule.DeliveryScheduleDetail;
import com.ut.nlSystemAPi.model.filter.DeliveryScheduleFilter;
import com.ut.nlSystemAPi.model.response.DeliverySchedule.DeliveryScheduleDetailResponse;
import com.ut.nlSystemAPi.model.response.DeliverySchedule.DeliveryScheduleResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryScheduleMapper {

    List<DeliveryScheduleResponse> getList(@Param("filter") DeliveryScheduleFilter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") DeliveryScheduleFilter filter, @Param("userId") Long userId);

    List<DeliveryScheduleResponse> getOne(@Param("id") Long id);

    List<DeliveryScheduleDetailResponse> getDetail(@Param("id") Long id);

    Boolean insert(@Param("schedule") DeliverySchedule schedule);

    Boolean insertDetail(@Param("detail") DeliveryScheduleDetail detail);

    Boolean update(@Param("schedule") DeliverySchedule schedule);

    Boolean delete(@Param("id")  Long id, @Param("userId") Long userid);

    Boolean deleteDetail(@Param("id")  Long id);
}