package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.TransferConsignmentFilter;
import com.ut.nlSystemAPi.model.request.Login.TransferConsignment.TransferConsignmentReceiveRequest;
import com.ut.nlSystemAPi.service.TransferConsignmentReceiveService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/transfer-consignment-receive")
@Api(tags = "26. Transfer/Consignment Receive", description = "Transfer/Consignment Receive Resource")
@Timed
public class TransferConsignmentReceiveController {

    @Autowired
    private TransferConsignmentReceiveService transferConsignmentReceiveService;

    @PostMapping("/list")
    @ApiOperation(value = "List transfer/consignment by filter", notes = "Type: 1 = Transfer, 2 = Consignment; toType: 1 = Products, 2 = Assets, Status: 0 = Void, 1 = Issued, 2 = Fulfilled", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody TransferConsignmentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return transferConsignmentReceiveService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find transfer/consignment by id", notes = "Type: 1 = Transfer, 2 = Consignment; toType: 1 = Products, 2 = Assets, Status: 0 = Void, 1 = Issued, 2 = Fulfilled", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return transferConsignmentReceiveService.getOne(id, httpServletRequest);
    }

    @PostMapping("/receive")
    @ApiOperation(value = "Receive transfer/consignment", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> receive(@RequestBody TransferConsignmentReceiveRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return transferConsignmentReceiveService.receive(request, httpServletRequest);
    }

}