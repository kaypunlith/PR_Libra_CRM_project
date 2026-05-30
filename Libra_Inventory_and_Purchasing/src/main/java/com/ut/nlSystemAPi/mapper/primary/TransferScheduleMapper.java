package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.TransferSchedule;
import com.ut.nlSystemAPi.model.TransferScheduleDetail;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.TransferScheduleFilter;
import com.ut.nlSystemAPi.model.response.TransferScheduleResponse.TransferScheduleDetailResponse;
import com.ut.nlSystemAPi.model.response.TransferScheduleResponse.TransferScheduleResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferScheduleMapper {

  List<TransferScheduleResponse> getList(@Param("filter") TransferScheduleFilter filter, @Param("userId") Long userId);

  Long countList(@Param("filter") TransferScheduleFilter filter, @Param("userId") Long userId);

  List<TransferScheduleResponse> getOne(@Param("id") Long id);

  List<TransferScheduleDetailResponse> getTransferScheduleDetail(@Param("id") Long id);

  Boolean insert(@Param("transferSchedule") TransferSchedule transferSchedule);

  Boolean insertTransferScheduleDetail(@Param("transferScheduleDetail") TransferScheduleDetail transferScheduleDetail);

  Boolean update(@Param("transferSchedule") TransferSchedule transferSchedule);

  Boolean delete(@Param("id")  Long id, @Param("userId") Long userid);

  Boolean deleteTransferDetail(@Param("id")  Long id);

}