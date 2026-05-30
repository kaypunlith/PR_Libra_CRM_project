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

@RestController
@RequestMapping("/module-type")
@Api(tags = "Module Type")
@Timed
public class ModuleTypeController {

  @Autowired
  private ModuleTypeService moduleTypeService;

  @PostMapping("/list")
  @ApiOperation(value = "List module type by filter", notes = "List module type by filter", authorizations = {@Authorization(value = "Bearer")})
  public ResponseMessage<BaseResult> list(@RequestBody ModuleTypeFilter filter) {
    return moduleTypeService.getList(filter);
  }

  @PostMapping("/find/{id}")
  @ApiOperation(value = "Find module type by id", authorizations = {@Authorization(value = "Bearer")})
  public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id) {
    return moduleTypeService.getOne(id);
  }
}