package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.InvestmentAssetFilter;
import com.ut.nlSystemAPi.model.filter.PostToJournalFilter;
import com.ut.nlSystemAPi.model.request.Login.InvestmentAsset.InvestmentAssetRequest;
import com.ut.nlSystemAPi.model.request.Login.InvestmentAsset.InvestmentAssetUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.PostToJournal.PostToJournalRequest;
import com.ut.nlSystemAPi.service.InvestmentAssetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/investment-asset")
@Api(tags = "32. Investment Asset", description = "Investment Asset Resource")
public class InvestmentAssetController {

    @Autowired
    private InvestmentAssetService investmentAssetService;

    @PostMapping("/list")
    @ApiOperation(value = "List investment asset by filter", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listInvestmentAsset(@RequestBody InvestmentAssetFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return investmentAssetService.getListInvestmentAsset(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find investment asset by id", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return investmentAssetService.getOne(id, httpServletRequest);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add investment asset", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody InvestmentAssetRequest investmentAssetRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return investmentAssetService.insert(investmentAssetRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/update")
    @ApiOperation(value = "Update investment asset", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody InvestmentAssetUpdateRequest investmentUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return investmentAssetService.update(investmentUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete investment asset by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return investmentAssetService.delete(id, httpServletRequest);
    }

    @PostMapping("post-to-journal/list")
    @ApiOperation(value = "List post to journal by filter", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> postToJournalList(@RequestBody PostToJournalFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return investmentAssetService.getListPostToJournal(filter, httpServletRequest);
    }

    @PostMapping("post-to-journal/save")
    @ApiOperation(value = "save post to journal", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> savePostToJournalList(@RequestBody PostToJournalRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return investmentAssetService.savePostToJournal(request, bindingResult,httpServletRequest);
    }
}
