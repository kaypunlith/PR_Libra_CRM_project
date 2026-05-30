package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PurchaseReceiveFilter;
import com.ut.nlSystemAPi.model.request.Login.PurchaseReceive.PurchaseReceiveSaveRequest;
import com.ut.nlSystemAPi.service.PurchaseReceiveService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/purchase-receive")
@Api(tags = "32. Purchase Receive", description = "Purchase Receive Resource")
@Timed
public class PurchaseReceiveController {
    @Autowired
    private PurchaseReceiveService purchaseReceiveService;

    @PostMapping("/list")
    @ApiOperation(value = "List purchase receive by filter", notes = "Status: 1 = Issues, 2 = Partial, 3 = Fulfilled;", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody PurchaseReceiveFilter purchaseReceiveFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseReceiveService.getList(purchaseReceiveFilter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find receive by purchase bill id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseReceiveService.getOne(id, httpServletRequest);
    }

    @PostMapping("/find-receive/{id}")
    @ApiOperation(value = "Find receive by purchase bill id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findReceiveById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseReceiveService.getOneReceive(id, httpServletRequest);
    }

    @PostMapping("/save")
    @ApiOperation(value = "Save purchase receive", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> save(@RequestBody PurchaseReceiveSaveRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return purchaseReceiveService.save(request, httpServletRequest);
    }


}