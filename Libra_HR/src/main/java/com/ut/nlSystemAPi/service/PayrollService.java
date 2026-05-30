package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.PayrollFilter;
import com.ut.nlSystemAPi.model.PayrollLock;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.OtherPayFilter;
import com.ut.nlSystemAPi.model.filter.ViewOtFilter;
import com.ut.nlSystemAPi.model.request.PayrollRequest;
import com.ut.nlSystemAPi.model.request.PayrollSendTelegramRequest;
import org.springframework.web.multipart.MultipartFile;

public interface PayrollService {

    ResponseMessage<BaseResult> getOne(Long id);

    ResponseMessage<BaseResult> viewHistoryBonus(ViewOtFilter filter);

    ResponseMessage<BaseResult> viewAttendance(ViewOtFilter filter);

    ResponseMessage<BaseResult> getList(PayrollFilter filter);

    ResponseMessage<BaseResult> getListViewOtherPay(OtherPayFilter filter);

    ResponseMessage<BaseResult> insert(PayrollRequest payrollRequest);

    ResponseMessage<BaseResult> payrollsSendTelegram(PayrollSendTelegramRequest request, MultipartFile file);

    ResponseMessage<BaseResult> update(PayrollRequest payrollRequest);

    ResponseMessage<BaseResult> delete(PayrollRequest payrollRequest);

    ResponseMessage<BaseResult> lock(PayrollLock payrollLock);
}
