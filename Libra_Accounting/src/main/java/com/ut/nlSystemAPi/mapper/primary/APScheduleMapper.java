package com.ut.nlSystemAPi.mapper.primary;


import com.ut.nlSystemAPi.model.APSchedule;
import com.ut.nlSystemAPi.model.APScheduleDetails;
import com.ut.nlSystemAPi.model.base.FilterBase;
import com.ut.nlSystemAPi.model.filter.ARAPScheduleFilter;
import com.ut.nlSystemAPi.model.response.APSchedule.APScheduleResponse;
import com.ut.nlSystemAPi.model.response.APSchedule.APScheduleResponseDetails;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBalance.APScheduleTopVendorTotalResponse;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBalance.APScheduleTopVendorTotalResponseDetails;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBalance.APScheduleVendorTotalResponse;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalBookingInformation.APScheduleTotalBookingInformationResponse;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBooked.APScheduleTopVendorBookedTotalResponse;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBooked.APScheduleTopVendorBookedTotalResponseDetails;
import com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBooked.APScheduleVendorBookedTotalResponse;
import com.ut.nlSystemAPi.model.response.ARSchedule.ARScheduleTotalResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface APScheduleMapper {

  List<APScheduleResponse> getList(@Param("filter") ARAPScheduleFilter filter);

  List<APScheduleResponseDetails> getDetails(@Param("apScheduleId") Long apScheduleId);

  Long countList(@Param("filter") ARAPScheduleFilter filter);

  List<APScheduleResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("accountCode") String accountCode, @Param("id") Long id);

  Boolean insert(@Param("apSchedule") APSchedule apSchedule);
  Boolean insertDetails(@Param("apScheduleDetails") APScheduleDetails apScheduleDetails);
  Boolean deleteDetails(@Param("apScheduleId") Long apScheduleId);

  Boolean update(@Param("apSchedule") APSchedule apSchedule);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  List<APScheduleTotalBookingInformationResponse> getPBBookingOverdue(@Param("filter") ARAPScheduleFilter filter, @Param("userId") Long userId);
  List<APScheduleTotalBookingInformationResponse> getPBBooked(@Param("filter") ARAPScheduleFilter filter);
  List<APScheduleTotalBookingInformationResponse> getPBBookedByUser(@Param("filter") ARAPScheduleFilter filter, @Param("userId") Long userId);
  List<APScheduleTotalBookingInformationResponse> getPBBookedByWeek(@Param("filter") ARAPScheduleFilter filter);

  //list vendor with amount
  List<APScheduleVendorTotalResponse> getVendorTotalList(@Param("filter") ARAPScheduleFilter filter);

  List<APScheduleTopVendorTotalResponse> getVendorTopTotalList(@Param("filter") ARAPScheduleFilter filter);
  List<APScheduleTopVendorTotalResponseDetails> getVendorTopTotalDetails(@Param("filter") ARAPScheduleFilter filter);
  List<APScheduleVendorBookedTotalResponse> getVendorBookedTotalList(@Param("filter") ARAPScheduleFilter filter);
  List<APScheduleTopVendorBookedTotalResponse> getVendorBookedTopTotalList(@Param("filter") FilterBase filter);
  List<APScheduleTopVendorBookedTotalResponseDetails> getVendorBookedTopTotalDetails(@Param("filter") ARAPScheduleFilter filter);

  List<APScheduleTotalBookingInformationResponse> getTotalVendorAmount(@Param("filter") FilterBase filter);

  List<APScheduleTotalBookingInformationResponse> getInvoiceBookingThisWeek(@Param("filter") FilterBase filter);
}