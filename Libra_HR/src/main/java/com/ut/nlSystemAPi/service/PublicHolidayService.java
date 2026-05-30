package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PublicHolidayFilter;
import com.ut.nlSystemAPi.model.request.PositionRequest;
import com.ut.nlSystemAPi.model.request.PositionUpdateRequest;
import com.ut.nlSystemAPi.model.request.PublicHolidayRequest;
import com.ut.nlSystemAPi.model.request.PublicHolidayUpdateRequest;

public interface PublicHolidayService {

    ResponseMessage<BaseResult> insert(PublicHolidayRequest publicHolidayRequest);

    ResponseMessage<BaseResult> getOne(Long id);

    ResponseMessage<BaseResult> getList(PublicHolidayFilter filter);

    ResponseMessage<BaseResult> update(PublicHolidayUpdateRequest publicHolidayUpdateRequest);

    ResponseMessage<BaseResult> delete(Long id);
}