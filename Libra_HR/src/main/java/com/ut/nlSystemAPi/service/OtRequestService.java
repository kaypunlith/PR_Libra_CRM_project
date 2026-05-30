package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.OtRequestFilter;
import com.ut.nlSystemAPi.model.request.*;

public interface OtRequestService {

	ResponseMessage<BaseResult> insert(OtRequestRequest otRequestRequest);

	ResponseMessage<BaseResult> getOne(Long id);

	ResponseMessage<BaseResult> getList(OtRequestFilter filter);

	ResponseMessage<BaseResult> update(OtRequestUpdateRequest request);

	ResponseMessage<BaseResult> updateStatus(OtRequestUpdateStatusRequest request);

	ResponseMessage<BaseResult> delete(Long id);
}