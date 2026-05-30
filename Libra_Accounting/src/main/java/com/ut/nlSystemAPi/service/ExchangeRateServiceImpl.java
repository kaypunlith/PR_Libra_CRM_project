package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ExchangeRateMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.ExchangeRate;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ExchangeRateFilter;
import com.ut.nlSystemAPi.model.request.Login.ExchangeRate.ExchangeRateUpdateRequest;
import com.ut.nlSystemAPi.model.response.ExchangeRate.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    private ExchangeRateMapper exchangeRateMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    Environment environment;

    public ResponseMessage<BaseResult> getList(ExchangeRateFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Exchange Rate (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(exchangeRateMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ExchangeRateResponse> exchangeRateResponses = exchangeRateMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/exchange-rate/list",null,null,"Exchange Rate","Exchange Rate (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", exchangeRateResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/exchange-rate/list",line, error.toString(),"Exchange Rate","Exchange Rate (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(ExchangeRateFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Exchange Rate (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<ExchangeRateResponse> companyCurrency= exchangeRateMapper.getOne(filter.getId());

            if (companyCurrency.isEmpty()){
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("Date is not found.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setCompanyId(companyCurrency.get(0).getCompanyId());
            filter.setCurrencyId(companyCurrency.get(0).getCurrencyToId());

            pagination.setTotal(exchangeRateMapper.countListExchangeRate(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ExchangeRateResponse> exchangeRateResponses = exchangeRateMapper.getExchangeRateList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/exchange-rate/find/{id}",null,null,"Exchange Rate","Exchange Rate (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", exchangeRateResponses, pagination,true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/exchange-rate/find/{id}",line, error.toString(),"Exchange Rate","Exchange Rate (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(ExchangeRateUpdateRequest exchangeRateUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Exchange Rate (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //* Find company currency
            List<ExchangeRateResponse> exchangeRateResponses = exchangeRateMapper.getOne(exchangeRateUpdateRequest.getId());

            if (exchangeRateResponses.isEmpty()){
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("Can't update data.", false));
            }

            // Check Data
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setId(exchangeRateUpdateRequest.getId());
            exchangeRate.setCompanyId(exchangeRateResponses.get(0).getCompanyId());
            exchangeRate.setCurrencyCenterId(exchangeRateResponses.get(0).getCurrencyToId());
            exchangeRate.setRateForSell(exchangeRateUpdateRequest.getRateForSell());
            exchangeRate.setRateForChange(exchangeRateUpdateRequest.getRateForChange());
            exchangeRate.setRateForPurchase(exchangeRateUpdateRequest.getRateForPurchase());
            exchangeRate.setModifiedBy(userId);
            exchangeRate.setCreatedBy(userId);
            exchangeRate.setIsActive(1);

            exchangeRateMapper.updateExchangeRate(exchangeRate);

            // Insert exchange rate
            Boolean result = exchangeRateMapper.insertExchangeRate(exchangeRate);

            if (result) {
                // Update company currency
                exchangeRateMapper.updateCompanyCurrency(exchangeRate);

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/exchange-rate/update",null,null,"Exchange Rate","Exchange Rate (Update)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/exchange-rate/update",line, error.toString(),"Exchange Rate","Exchange Rate (Update)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}