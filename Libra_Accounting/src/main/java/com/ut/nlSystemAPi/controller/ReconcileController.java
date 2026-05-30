package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ReconcileFilter;
import com.ut.nlSystemAPi.model.request.Login.Reconcile.ReconcileRequest;
import com.ut.nlSystemAPi.service.ReconcileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/reconcile")
@Api(tags = "29. Reconcile", description = "Reconcile Resource")
public class ReconcileController {

    @Autowired
    private ReconcileService reconcileService;

    @PostMapping("/list-debit")
    @ApiOperation(value = "List Reconcile by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListDebit(@RequestBody ReconcileFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reconcileService.getListDebit(filter, httpServletRequest);
    }

    @PostMapping("/list-credit")
    @ApiOperation(value = "List Reconcile by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getListCredit(@RequestBody ReconcileFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reconcileService.getListCredit(filter, httpServletRequest);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add Reconcile setting", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody ReconcileRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return reconcileService.insert(request, bindingResult, httpServletRequest);
    }

}
