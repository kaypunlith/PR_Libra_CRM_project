package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.LogisticFilter;
import com.ut.nlSystemAPi.model.request.Logistic.LogisticDeliveryRequest;
import com.ut.nlSystemAPi.model.request.Logistic.LogisticMergeRequest;
import com.ut.nlSystemAPi.service.LogisticService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/logistic")
@Api(tags = "26. Logistic", description = "Logistic Resource")
@Timed
public class LogisticController {

    @Autowired
    private LogisticService logisticService;

    @PostMapping("/list")
    @ApiOperation(value = "List logistic by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody LogisticFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return logisticService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find Logistic by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return logisticService.getOne(id, httpServletRequest);
    }

    @PostMapping("/merge")
    @ApiOperation(value = "Merge/unmerge by id", notes = "OperationType: 1 = Merge, 2 = Unmerge; Type: 1 = Sales Invoice, 2 = Logistic (Details)", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> merge(@RequestBody LogisticMergeRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return logisticService.merge(request, httpServletRequest);
    }

    @PostMapping("/find-delivery/{id}/{type}")
    @ApiOperation(value = "Find delivery by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getOneDelivery(@PathVariable("id") Long id, @PathVariable("type") Long type, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return logisticService.getOneDelivery(id, type, httpServletRequest);
    }

    @PostMapping("/delivery")
    @ApiOperation(value = "Find receive by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delivery(@RequestBody LogisticDeliveryRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return logisticService.delivery(request, httpServletRequest);
    }

}