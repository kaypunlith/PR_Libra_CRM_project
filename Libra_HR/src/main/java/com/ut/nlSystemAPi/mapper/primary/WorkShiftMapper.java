package com.ut.nlSystemAPi.mapper.primary;

import java.util.List;

import com.ut.nlSystemAPi.model.request.WorkShiftRequest;
import com.ut.nlSystemAPi.model.response.WorkShiftDropDownResponse;
import com.ut.nlSystemAPi.model.response.WorkShiftTypeResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ut.nlSystemAPi.model.WorkShift;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.DayResponse;
import com.ut.nlSystemAPi.model.response.WorkShiftResponse;

@Repository
public interface WorkShiftMapper {

  List<WorkShiftResponse> getList(@Param("filter") Filter filter);

  List<WorkShiftResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("workShift") WorkShift workShift, @Param("creatorId") Long creatorId);

  Long getCount(@Param("filter") Filter filter);

  Boolean update(@Param("workShift") WorkShift workShift, @Param("modifierId") Long modifierId);

  Boolean delete (@Param("id") Long id, @Param("userId") Long userId);

  // * days
  Boolean insertWorkShiftDay(@Param("workShiftId") Long workShiftId,@Param("dayId") Long dayId, @Param("creatorId") Long creatorId);

  List<DayResponse> getListDays(@Param("workShiftId") Long workShiftId);

  Boolean deleteListDays(@Param("workShiftId") Long workShiftId, @Param("modifierId") Long modifierId);

  List<WorkShiftDropDownResponse> getListWithOutPermission(@Param("filter") Filter filter);

  List<WorkShiftTypeResponse> getListWorkShiftType(@Param("filter") Filter filter);


}
