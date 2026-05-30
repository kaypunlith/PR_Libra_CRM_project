package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.LeaveRequestFilter;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.LeaveReportDetailFilter;
import com.ut.nlSystemAPi.model.request.LeaveRequestAdd;
import com.ut.nlSystemAPi.model.request.LeaveRequestUpdate;

public interface LeaveRequestService {

	ResponseMessage<BaseResult> getList(LeaveRequestFilter filter);

	ResponseMessage<BaseResult> getOne(Long id);

	ResponseMessage<BaseResult> getViewDetail(Long employeeId, String year);

	ResponseMessage<BaseResult> insert(LeaveRequestAdd leaveRequestAdd);

	ResponseMessage<BaseResult> update(LeaveRequestUpdate leaveRequestUpdate);

	ResponseMessage<BaseResult> delete(Long id);

	ResponseMessage<BaseResult> getReportDetail(LeaveReportDetailFilter filter);
}
