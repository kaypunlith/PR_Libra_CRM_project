package com.ut.nlSystemAPi.mapper.primary;


import com.ut.nlSystemAPi.model.ARSchedule;
import com.ut.nlSystemAPi.model.ARScheduleDetails;
import com.ut.nlSystemAPi.model.base.FilterBase;
import com.ut.nlSystemAPi.model.filter.ARAPScheduleFilter;
import com.ut.nlSystemAPi.model.filter.ARAPScheduleWeekFilter;
import com.ut.nlSystemAPi.model.response.ARSchedule.ARScheduleResponse;
import com.ut.nlSystemAPi.model.response.ARSchedule.ARScheduleResponseDetails;
import com.ut.nlSystemAPi.model.response.ARSchedule.ARScheduleTotalResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ARScheduleMapper {

  List<ARScheduleResponse> getList(@Param("filter") ARAPScheduleFilter filter);

  List<ARScheduleResponseDetails> getDetails(@Param("arScheduleId") Long arScheduleId);

  Long countList(@Param("filter") ARAPScheduleFilter filter);

  List<ARScheduleResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("accountCode") String accountCode, @Param("id") Long id);

  Boolean insert(@Param("arSchedule") ARSchedule arSchedule);

  Boolean insertDetails(@Param("arScheduleDetails") ARScheduleDetails arScheduleDetails);

  Boolean deleteDetails(@Param("arScheduleId") Long arScheduleId);

  Boolean update(@Param("arSchedule") ARSchedule arSchedule);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  List<ARScheduleTotalResponse> getInvoiceBookingOverdue(@Param("filter") FilterBase filter);
  List<ARScheduleTotalResponse> getInvoiceBooked(@Param("filter") FilterBase filter);
  List<ARScheduleTotalResponse> getInvoiceBookedByUser(@Param("filter") FilterBase filter, @Param("userId") Long userId);

  List<ARScheduleTotalResponse> getTotalCustomer(@Param("filter") FilterBase filter);

  List<ARScheduleTotalResponse> getInvoiceBookedByWeek(@Param("filter") ARAPScheduleWeekFilter filter);

//  List<ARScheduleTotalResponse>  getInvoiceBookingThisWeek(@Param("filter") FilterBase filter);
}