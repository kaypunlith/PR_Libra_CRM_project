package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.MissionFilter;
import com.ut.nlSystemAPi.model.request.MissionRequest;
import com.ut.nlSystemAPi.model.request.MissionUpdateRequest;
import com.ut.nlSystemAPi.model.request.MissionUpdateStatusRequest;

public interface MissionService {
	ResponseMessage<BaseResult> getList(MissionFilter filter);

	ResponseMessage<BaseResult> getOne(Long id);

	ResponseMessage<BaseResult> insert(MissionRequest request);

	ResponseMessage<BaseResult> update(MissionUpdateRequest request);

	ResponseMessage<BaseResult> updateStatus(MissionUpdateStatusRequest request);

	ResponseMessage<BaseResult> delete(Long id);
}
