package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.InventorySampleFilter;
import com.ut.nlSystemAPi.model.filter.InventorySampleProductFilter;
import com.ut.nlSystemAPi.model.request.Login.InventorySample.InventorySampleRequest;
import com.ut.nlSystemAPi.model.request.Login.InventorySample.InventorySampleRequestUpdate;
import com.ut.nlSystemAPi.model.request.Login.InventorySample.InventorySampleStatusUpdateRequest;
import com.ut.nlSystemAPi.service.InventorySampleService;
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
@RequestMapping("/inventory-sample")
@Api(tags = "23. Inventory Sample", description = "Inventory Sample Resource")
@Timed
public class InventorySampleController {
    @Autowired
    private InventorySampleService inventorySampleService;

    @PostMapping("/list")
    @ApiOperation(value = "List inventory sample by filter", notes =
                    "status = 0 -> Void\n" +
                    "status = 1 -> Issued\n" +
                    "status = 2 -> Fulfilled"
            , authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody InventorySampleFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventorySampleService.getList(filter, httpServletRequest);
    }

    @PostMapping("/product-list")
    @ApiOperation(value = "List Product by filter", notes = "List Product by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody InventorySampleProductFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventorySampleService.getListProductDetail(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find inventory sample by id", notes = "Find inventory sample ", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventorySampleService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new price request", notes = "Add new price request", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody InventorySampleRequest inventorySampleRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventorySampleService.insert(inventorySampleRequest, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update inventory sample by id", notes = "Update inventory sample", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody InventorySampleRequestUpdate inventorySampleRequestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventorySampleService.update(inventorySampleRequestUpdate, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete inventory sample by id", notes = "Delete inventory sample", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventorySampleService.delete(id, httpServletRequest);
    }

    @PostMapping("/update-status")
    @ApiOperation(value = "Update inventory sample status ", notes = "status \n" +
            "0: Void\n" +
            "1: Issued\n" +
            "2: Fulfilled", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateStatus(@RequestBody InventorySampleStatusUpdateRequest statusUpdateRequest, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return inventorySampleService.updateStatus(statusUpdateRequest, httpServletRequest);
    }
}
