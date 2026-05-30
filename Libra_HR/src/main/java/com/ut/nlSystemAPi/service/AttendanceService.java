package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.AttendanceFilter;
import com.ut.nlSystemAPi.model.request.AttendanceUpdateRequest;
import com.ut.nlSystemAPi.model.request.InsertAttendanceRequest;

public interface AttendanceService {

	// * get list of attendance
	ResponseMessage<BaseResult> getList(AttendanceFilter filter);

	ResponseMessage<BaseResult> insert(InsertAttendanceRequest request);

	ResponseMessage<BaseResult> getOne(Long id);

	ResponseMessage<BaseResult> update(AttendanceUpdateRequest request);

	ResponseMessage<BaseResult> delete(Long id);

}
