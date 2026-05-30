package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.TermPrivacyMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.TermPrivacy.TermPrivacy;
import com.ut.nlSystemAPi.model.request.TermPrivacy.TermPrivacyUpdateRequest;
import com.ut.nlSystemAPi.model.response.TermPrivacy.TermPrivacyResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.TermPrivacyService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class TermPrivacyServiceImpl implements TermPrivacyService {

    @Autowired
    private TermPrivacyMapper termPrivacyMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {

            List<TermPrivacyResponse> responses = termPrivacyMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-privacy/list", null, null, "Term Privacy", "Term Privacy (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-privacy/list", line, error.toString(), "Term Privacy", "Term Privacy (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(TermPrivacyUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            TermPrivacy termPrivacy = new TermPrivacy();
            termPrivacy.setDescriptionPrivacyKh(request.getDescriptionPrivacyKh());
            termPrivacy.setDescriptionPrivacyEn(request.getDescriptionPrivacyEn());
            termPrivacy.setDescriptionTermsKh(request.getDescriptionTermsKh());
            termPrivacy.setDescriptionTermsEn(request.getDescriptionTermsEn());
            termPrivacy.setModifiedBy(userId);

            Boolean result = termPrivacyMapper.update(termPrivacy);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/term-privacy/update", null, null, "Term Privacy", "Term Privacy (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/term-privacy/update", line, error.toString(), "Term Privacy", "Term Privacy (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
