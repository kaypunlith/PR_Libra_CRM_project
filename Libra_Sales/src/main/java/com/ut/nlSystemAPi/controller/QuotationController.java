package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.base.StatusRequest;
import com.ut.nlSystemAPi.model.filter.QuotationFilter;
import com.ut.nlSystemAPi.model.request.Quotation.*;
import com.ut.nlSystemAPi.service.QuotationService;
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
@RequestMapping("/quotation")
@Api(tags = "20. Quotation", description = "Quotation Resource")
@Timed
public class QuotationController {

    @Autowired
    private QuotationService quotationService;

    @PostMapping("/list")
    @ApiOperation(value = "List quotation by filter", notes = "Type: 1: Trading, 2: Software", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody QuotationFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return quotationService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find quotation by id", notes = "Type: 1: Product, 2: Service, 3: Misc, 4: Break Point (Details)", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return quotationService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new quotation", notes = "Type: 1: Trading, 2: Software; ShareSaveOption: 1: Only this transaction, 2: All next transaction; ShareOption: 1: Only me, 2: Everyone, 3: User customize, 4: Everyone but except user; Type: 1: Product, 2: Service, 3: Misc, 4: Break Point (Details)", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody QuotationRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return quotationService.insert(request, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update quotation by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody QuotationUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return quotationService.update(request, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete quotation by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return quotationService.delete(id, httpServletRequest);
    }

    @PostMapping("/approve")
    @ApiOperation(value = "Approve quotation by id", notes = "Status: 1: Disapproved, 2: Approved", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> approve(@RequestBody StatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return quotationService.approve(request, httpServletRequest);
    }

    @PostMapping("/close")
    @ApiOperation(value = "Close quotation by id", notes = "Status: 1: Close, 0: Not Close", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> close(@RequestBody StatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return quotationService.close(request, httpServletRequest);
    }

    @PostMapping("/update-crm-percent")
    @ApiOperation(value = "Update crm percent quotation", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateCrm(@RequestBody CRMPercentRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return quotationService.updateCrm(request, httpServletRequest);
    }

    @PostMapping("/list-crm-percent")
    @ApiOperation(value = "List crm percent quotation", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getCrm(HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return quotationService.getCrm(httpServletRequest);
    }

    @PostMapping("/update-share-option")
    @ApiOperation(value = "Update share option", notes = "ShareSaveOption: 1: Only this transaction, 2: All next transaction; ShareOption: 1: Only me, 2: Everyone, 3: User customize, 4: Everyone but except user", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateShareOptions(@RequestBody QuotationShareOptionRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "Unauthorized", "No Permission to access");
        }
        return quotationService.updateShareOptions(request, httpServletRequest);
    }

    @PostMapping("/update-status-information")
    @ApiOperation(value = "Update status information", notes = "Need Name Request Only When The Status = Loss AND Reason = Competitor", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateStatusInformation(@RequestBody QuotationStatusInformationRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "Unauthorized", "No Permission to access");
        }
        return quotationService.updateStatusInformation(request, httpServletRequest);
    }

    @PostMapping("/list-status-information/{id}")
    @ApiOperation(value = "List status information", notes = "Need Name Request Only When The Status = Loss AND Reason = Competitor", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listStatusInformation(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "Unauthorized", "No Permission to access");
        }
        return quotationService.listStatusInformation(id, httpServletRequest);
    }

    @PostMapping("/find-product-info/{id}")
    @ApiOperation(value = "Find product price history in quotations", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findProductInfo(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return quotationService.findProductInfo(id, httpServletRequest);
    }

    @PostMapping("/update-pagination")
    @ApiOperation(value = "Update pagination", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updatePagination(@RequestBody QuotationPaginationRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return quotationService.updatePagination(request, httpServletRequest);
    }
}
