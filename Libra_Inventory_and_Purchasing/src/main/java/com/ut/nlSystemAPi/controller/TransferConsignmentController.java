package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ProductTransferConsignmentFilter;
import com.ut.nlSystemAPi.model.filter.TransferConsignmentFilter;
import com.ut.nlSystemAPi.model.request.Login.TransferConsignment.TransferConsignmentRequest;
import com.ut.nlSystemAPi.model.request.Login.TransferConsignment.TransferConsignmentUpdateRequest;
import com.ut.nlSystemAPi.service.TransferConsignmentService;
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
@RequestMapping("/transfer-consignment")
@Api(tags = "25. Transfer/Consignment", description = "Transfer/Consignment Resource")
@Timed
public class TransferConsignmentController {

    @Autowired
    private TransferConsignmentService transferConsignmentService;

    @PostMapping("/list")
    @ApiOperation(value = "List transfer/consignment by filter", notes = "isForSale: 1 = Yes, 0 = No; isActive: 1 = Active, 2 = Inactive", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody TransferConsignmentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return transferConsignmentService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find transfer/consignment by id", notes = "isForSale: 1 = Yes, 0 = No; isActive: 1 = Active, 2 = Inactive", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return transferConsignmentService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new section", notes = "isForSale: 1 = Yes, 0 = No; isActive: 1 = Active, 2 = Inactive", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody TransferConsignmentRequest transferConsignmentRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return transferConsignmentService.insert(transferConsignmentRequest, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update transfer/consignment by id", notes = "isForSale: 1 = Yes, 0 = No; isActive: 1 = Active, 2 = Inactive", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody TransferConsignmentUpdateRequest transferConsignmentUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return transferConsignmentService.update(transferConsignmentUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete transfer/consignment by id", notes = "Delete section", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return transferConsignmentService.delete(id, httpServletRequest);
    }

    @PostMapping(value = "/list/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List products by barcode scan", notes = "List products by barcode scan", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listProduct(@RequestBody ProductTransferConsignmentFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        return transferConsignmentService.listProduct(filter, httpServletRequest);
    }

    @PostMapping("/approve/{id}/{isApprove}")
    @ApiOperation(value = "Approve transfer/consignment by id", notes = "IsApprove: 1 = Approve, 0 = Disapprove", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> approve(@PathVariable("id") Long id, @PathVariable("isApprove") Long isApprove, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return transferConsignmentService.approve(id, isApprove, httpServletRequest);
    }
}
