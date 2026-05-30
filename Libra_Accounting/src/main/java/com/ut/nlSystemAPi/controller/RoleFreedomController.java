package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.model.RoleData;
import com.ut.nlSystemAPi.model.RoleDataUpdate;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.service.RoleFreedomService;
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
@RequestMapping("/role-freedom")
@Api(tags = "03. Role Freedom", description = "Role Freedom Resource")
@Timed
public class RoleFreedomController {

    @Autowired
    private RoleFreedomService roleFreedomService;

    @PostMapping("/list")
    @ApiOperation(value = "List role freedom by filter", notes = "List role freedom by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        return roleFreedomService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find role freedom by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        return roleFreedomService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new role freedom", notes = "Add new role freedom", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody RoleData roleData, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        return roleFreedomService.insert(roleData, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update role freedom by id", notes = "Update role freedom", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody RoleDataUpdate roleDataUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        return roleFreedomService.update(roleDataUpdate, bindingResult, httpServletRequest);
    }

    @PostMapping("/menu")
    @ApiOperation(value = "List menu role freedom", notes = "List menu role freedom", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> menu(HttpServletRequest httpServletRequest) throws UnknownHostException {
        return roleFreedomService.menu(httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete role freedom by id", notes = "Delete role freedom", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        return roleFreedomService.delete(id, httpServletRequest);
    }
}
