package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.DepartmentRequest;
import com.ut.nlSystemAPi.model.request.DepartmentUpdateRequest;

public interface DepartmentService {

    ResponseMessage<BaseResult> getOne(Long id);

    ResponseMessage<BaseResult> getList(Filter filter);

    ResponseMessage<BaseResult> insert(DepartmentRequest departmentRequest);

    ResponseMessage<BaseResult> update(DepartmentUpdateRequest departmentUpdateRequest);

    ResponseMessage<BaseResult> delete(Long id);

    ResponseMessage<BaseResult> getDepartmentList();
}