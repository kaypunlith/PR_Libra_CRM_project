package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ActivityFilter;
import com.ut.nlSystemAPi.service.ActivityLogService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/system-activity")
@Api(tags = "05. System Activity", description = "System Activity Resource")
@Timed
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/list")
    @ApiOperation(value = "System Activity (List)", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success")
    public ResponseMessage<BaseResult> getList(@RequestBody ActivityFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        return activityLogService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find System Activity by id", notes = "statusCode: 400: Bad Request (Invalid Parameter); 401: Unauthorized (Token Expired or Invalid); 200: Success")
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        return activityLogService.getOne(id, httpServletRequest);
    }
}