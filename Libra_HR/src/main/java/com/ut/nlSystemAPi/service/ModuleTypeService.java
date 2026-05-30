package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.ModuleTypeFilter;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;

public interface ModuleTypeService {

  ResponseMessage<BaseResult> getList(ModuleTypeFilter filter);

  ResponseMessage<BaseResult> getOne(Long id);

}