package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.model.ModuleTypeFilter;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.service.ModuleTypeService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/module-type")
@Api(tags = "04. Module Type", description = "Module type Resource")
@Timed
public class ModuleTypeController {

    @Autowired
    private ModuleTypeService moduleTypeService;

    @PostMapping("/list")
    @ApiOperation(value = "List module type by filter", notes = "List module type by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody ModuleTypeFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        return moduleTypeService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find module type by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        return moduleTypeService.getOne(id, httpServletRequest);
    }
}
