package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.BudgetPlan.BudgetPlanRequest;
import com.ut.nlSystemAPi.model.request.Login.BudgetPlan.BudgetPlanUpdateRequest;
import com.ut.nlSystemAPi.service.BudgetPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/budget-plan")
@Api(tags = "28. Budget Plan", description = "Budget Plan Resource")
public class BudgetPlanController {

    @Autowired
    private BudgetPlanService budgetPlanService;

    @PostMapping("/list")
    @ApiOperation(value = "List Budget Plan by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getList(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return budgetPlanService.getList(filter, httpServletRequest);
    }

    @PostMapping("/table-list")
    @ApiOperation(value = "List Table Budget Plan by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getTableList(HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return budgetPlanService.getTableList(httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find Budget Plan by id", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return budgetPlanService.getOne(id, httpServletRequest);
    }

    @PostMapping("/view-dashboard/{id}")
    @ApiOperation(value = "Find Budget Plan by id", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> dashboardById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return budgetPlanService.getOneDashboard(id, httpServletRequest);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add Budget Plan setting", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody BudgetPlanRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return budgetPlanService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/update")
    @ApiOperation(value = "Add Budget Plan setting", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody BudgetPlanUpdateRequest updateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return budgetPlanService.update(updateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete Budget Plan by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return budgetPlanService.delete(id, httpServletRequest);
    }
}
