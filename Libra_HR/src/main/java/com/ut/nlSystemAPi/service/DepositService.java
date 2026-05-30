package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.DepositFilter;
import com.ut.nlSystemAPi.model.DepositListFilter;
import com.ut.nlSystemAPi.model.DepositUpdateStatus;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.*;
import com.ut.nlSystemAPi.model.response.EmployeeDepositFilter;

public interface DepositService {

	ResponseMessage<BaseResult> getOne(Long id);

	ResponseMessage<BaseResult> getList(DepositListFilter filter);

	ResponseMessage<BaseResult> insert(DepositRequest depositRequest);

	ResponseMessage<BaseResult> update(DepositRequestUpdate depositRequestUpdate);

	ResponseMessage<BaseResult> delete(Long id);

	ResponseMessage<BaseResult> getListProFund(DepositFilter filter);


	ResponseMessage<BaseResult> updateStatusAll(DepositUpdateStatus depositUpdateStatus);

	ResponseMessage<BaseResult> updateStatusByEmp(DepositRequestStatus depositRequestStatus);

	ResponseMessage<BaseResult> getListTitle();

	ResponseMessage<BaseResult> getEmployeeDeposit(EmployeeDepositFilter filter);
}