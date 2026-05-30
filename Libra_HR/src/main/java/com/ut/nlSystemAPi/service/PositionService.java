package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.PositionOrderingRequest;
import com.ut.nlSystemAPi.model.request.PositionRequest;
import com.ut.nlSystemAPi.model.request.PositionUpdateRequest;

public interface PositionService {

    ResponseMessage<BaseResult> insert(PositionRequest positionRequest);

    ResponseMessage<BaseResult> getOne(Long id);

    ResponseMessage<BaseResult> getList(Filter filter);

    ResponseMessage<BaseResult> update(PositionUpdateRequest positionUpdateRequest);

    ResponseMessage<BaseResult> updateOrdering(PositionOrderingRequest positionOrderingRequest);

    ResponseMessage<BaseResult> delete(Long id);
}
