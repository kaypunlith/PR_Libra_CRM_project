package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Checklist.ChecklistSaveRequest;
import com.ut.nlSystemAPi.service.SupportTicketChecklistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/support-ticket-checklist")
@Api(tags = "68. Support Ticket Checklist", description = "Support Ticket Checklist Resource")
public class SupportTicketChecklistController {

    @Autowired
    private SupportTicketChecklistService supportTicketChecklistService;

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find support ticket checklist by support ticket id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> find(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return supportTicketChecklistService.find(id, httpServletRequest);
    }

    @PostMapping(value = "/save/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save support ticket checklist by support ticket id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> save(@PathVariable("id") Long id, @RequestBody ChecklistSaveRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return supportTicketChecklistService.save(id, request, httpServletRequest);
    }
}
