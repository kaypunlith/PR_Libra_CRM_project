package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.FixedAssetAppreciationFilter;
import com.ut.nlSystemAPi.model.filter.PostToJournalFilter;
import com.ut.nlSystemAPi.model.request.Login.FixedAssetAppreciationRequest.FixedAssetAppreciationRequest;
import com.ut.nlSystemAPi.model.request.Login.FixedAssetAppreciationRequest.FixedAssetAppreciationUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.PostToJournal.PostToJournalRequest;
import com.ut.nlSystemAPi.service.FixedAssetAppreciationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/fixed-asset-appreciation")
@Api(tags = "31. Fixed Asset Appreciation", description = "Fixed Asset Appreciation Resource")
public class FixedAssetAppreciationController {

    @Autowired
    private FixedAssetAppreciationService fixedAssetAppreciationService;

    @PostMapping("/list")
    @ApiOperation(value = "List fixed asset appreciation by filter", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody FixedAssetAppreciationFilter fixedAssetAppreciationFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return fixedAssetAppreciationService.getList(fixedAssetAppreciationFilter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find fixed asset appreciation by id", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return fixedAssetAppreciationService.getOne(id, httpServletRequest);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add fixed asset appreciation", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody FixedAssetAppreciationRequest fixedAssetAppreciationRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return fixedAssetAppreciationService.insert(fixedAssetAppreciationRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/update")
    @ApiOperation(value = "Update fixed asset appreciation", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody FixedAssetAppreciationUpdateRequest fixedAssetAppreciationUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return fixedAssetAppreciationService.update(fixedAssetAppreciationUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete fixed asset appreciation by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return fixedAssetAppreciationService.delete(id, httpServletRequest);
    }

    @PostMapping("post-to-journal/list")
    @ApiOperation(value = "List post to journal by filter", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> postToJournalList(@RequestBody PostToJournalFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return fixedAssetAppreciationService.getListPostToJournal(filter, httpServletRequest);
    }

    @PostMapping("post-to-journal/save")
    @ApiOperation(value = "save post to journal", notes = "StatusId (1: Active, 0: Inactive);", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> savePostToJournalList(@RequestBody PostToJournalRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return fixedAssetAppreciationService.savePostToJournal(request, bindingResult,httpServletRequest);
    }
}
