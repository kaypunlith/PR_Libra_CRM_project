package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.GroupRequest;
import com.ut.nlSystemAPi.model.request.GroupUpdateRequest;

public interface GroupService {

    ResponseMessage<BaseResult> insert(GroupRequest groupRequest);

    ResponseMessage<BaseResult> getOne(Long id);

    ResponseMessage<BaseResult> getList(Filter filter);

    ResponseMessage<BaseResult> listFilterGroup(Filter filter);

    ResponseMessage<BaseResult> update(GroupUpdateRequest groupUpdateRequest);

    ResponseMessage<BaseResult> delete(Long id);
}
