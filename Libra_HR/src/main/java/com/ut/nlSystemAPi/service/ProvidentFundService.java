package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.ProvidentFundFilter;
import com.ut.nlSystemAPi.model.ProvidentFundListFilter;
import com.ut.nlSystemAPi.model.ProvidentFundUpdateStatus;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.ProvidentFundRequest;
import com.ut.nlSystemAPi.model.request.ProvidentFundRequestStatus;
import com.ut.nlSystemAPi.model.request.ProvidentFundRequestUpdate;
import com.ut.nlSystemAPi.model.response.EmployeeDepositFilter;

public interface ProvidentFundService {

	ResponseMessage<BaseResult> getOne(Long id);

	ResponseMessage<BaseResult> getList(ProvidentFundListFilter filter);

	ResponseMessage<BaseResult> insert(ProvidentFundRequest providentFundRequest);

	ResponseMessage<BaseResult> update(ProvidentFundRequestUpdate providentFundRequestUpdate);

	ResponseMessage<BaseResult> delete(Long id);

	ResponseMessage<BaseResult> getListProFund(ProvidentFundFilter filter);

	ResponseMessage<BaseResult> updateStatusAll(ProvidentFundUpdateStatus providentFundUpdateStatus);

	ResponseMessage<BaseResult> updateStatusByEmp(ProvidentFundRequestStatus providentFundRequestStatus);

	ResponseMessage<BaseResult> getListTitle();

	ResponseMessage<BaseResult> getEmployeeProvidentFund(EmployeeDepositFilter filter);
}