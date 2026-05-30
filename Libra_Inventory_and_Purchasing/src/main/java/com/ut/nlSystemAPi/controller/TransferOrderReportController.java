package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.TransferOrderReportByItemFilter;
import com.ut.nlSystemAPi.model.filter.TransferOrderReportFilter;
import com.ut.nlSystemAPi.model.request.Login.UomsRequest;
import com.ut.nlSystemAPi.model.request.Login.UomsUpdateRequest;
import com.ut.nlSystemAPi.service.TransferOrderReportService;
import com.ut.nlSystemAPi.service.UomsService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/transfer-order-report")
@Api(tags = "40. Transfer Order Report", description = "Transfer Order Report Resource")
@Timed
public class TransferOrderReportController {
    @Autowired
    private TransferOrderReportService transferOrderReportService;

    @PostMapping("/list")
    @ApiOperation(value = "List transfer-orderReport by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listTransferOder(@RequestBody TransferOrderReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return transferOrderReportService.getList(filter, httpServletRequest);
    }

    @PostMapping("/item-list")
    @ApiOperation(value = "List transfer-orderReport by item by filter", notes = "View: 1 = Item Summary, 2 = Item Detail, 3 = Parent Summary;  Type: 1 = Product, 2 = Service, 3 = Miscellaneous;", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listTransferOderByItem(@RequestBody TransferOrderReportByItemFilter filterByItem, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return transferOrderReportService.getListByItem(filterByItem, httpServletRequest);
    }

}