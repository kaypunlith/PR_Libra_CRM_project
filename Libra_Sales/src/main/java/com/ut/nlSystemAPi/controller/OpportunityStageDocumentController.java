package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Opportunities.OpportunityStageDocumentRequest;
import com.ut.nlSystemAPi.service.OpportunityStageDocumentService;
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
@RequestMapping("/opportunity-stage-document")
@Api(tags = "55. Opportunities Stage Document", description = "Opportunities Stage Document Resource")
@Timed
public class OpportunityStageDocumentController {

    @Autowired
    private OpportunityStageDocumentService opportunityStageDocumentService;

    @PostMapping("/list/{crmOpportunityId}")
    @ApiOperation(value = "List opportunity stage documents by stage id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@PathVariable("crmOpportunityId") Long crmOpportunityId, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return opportunityStageDocumentService.getList(crmOpportunityId, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new opportunity stage document", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody OpportunityStageDocumentRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return opportunityStageDocumentService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete opportunity stage document by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return opportunityStageDocumentService.delete(id, httpServletRequest);
    }
}
