package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.TelegramNotificationRequest;
import com.ut.nlSystemAPi.model.request.TelegramNotificationUpdateRequest;

public interface TelegramNotificationService{

    ResponseMessage<BaseResult> insert(TelegramNotificationRequest telegramNotificationRequest, Long creator);

    ResponseMessage<BaseResult> getOne(Long id);

    ResponseMessage<BaseResult> getList(Filter filter);

    ResponseMessage<BaseResult> update(TelegramNotificationUpdateRequest telegramNotificationUpdateRequest);

    ResponseMessage<BaseResult> delete(Long id, Long userId);

}
