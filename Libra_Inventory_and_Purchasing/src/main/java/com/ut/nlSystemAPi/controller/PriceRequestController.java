package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.PriceRequestFilter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.PriceRequest.*;
import com.ut.nlSystemAPi.model.request.Login.Product.ProductRequest;
import com.ut.nlSystemAPi.service.PriceRequestService;
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
@RequestMapping("/price-request")
@Api(tags = "21. Price Request", description = "Price Request Resource")
@Timed
public class PriceRequestController {
    @Autowired
    private PriceRequestService priceRequestService;

    @PostMapping("/list")
    @ApiOperation(value = "List price request by filter", notes =
            "status = 0 -> Void\n" +
            "status = 1 -> Open\n" +
            "status = 2 -> Closed\n" +
            "status = 3 -> Convert\n\n" +
            "ViewBy = 1 -> Detail\n" +
            "ViewBy = 2 -> Summary",
           authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody PriceRequestFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return priceRequestService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find price request by id", notes = "Find price request ", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return priceRequestService.getOne(id, httpServletRequest);
    }

    @PostMapping("/print/{customerId}")
    @ApiOperation(value = "Print price request", notes = "Print price request ", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> print(@PathVariable("customerId") Long customerId, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return priceRequestService.print(customerId, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new price request", notes = "Add new price request", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody PriceRequest_Request priceRequest_request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return priceRequestService.insert(priceRequest_request, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Price Request by id", notes = "Update Price Request", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody PriceRequest_RequestUpdate priceRequest_requestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return priceRequestService.update(priceRequest_requestUpdate, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete price request by id", notes = "Delete price request", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return priceRequestService.delete(id, httpServletRequest);
    }

    @PostMapping("/update-status")
    @ApiOperation(value = "Status: 1 = Open; 2 = Close", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateStatus(@RequestBody PriceRequestStatusUpdate priceRequestStatusUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return priceRequestService.updateStatus(priceRequestStatusUpdate, httpServletRequest);
    }

    @PostMapping("/send-to_telegram")
    @ApiOperation(value = "Send to Telegram by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> sendToTelegram(@RequestBody SendToTelegram sendToTelegram, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return priceRequestService.sendTelegram(sendToTelegram, httpServletRequest);
    }

    @PostMapping(value = "/convert-to-product", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Convert to product", notes = "isForSale: 1 = Yes, 0 = No; isActive: 1 = Active, 2 = Inactive; Created By/isPacket: 2 = Batch Code, 1 = Single Code; isExpiredDate: 1 = Yes, 0 = No; ChartAccountType: 1 = Inventory Asset Account, 2 = COGS Account, 8 = Sales Income", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> convert(@RequestBody ProductRequest productRequest,HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return priceRequestService.convert(productRequest, httpServletRequest);
    }

    @PostMapping(value = "/sum-list-approve")
    @ApiOperation(value = "Sum list approve convert to product", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> sumListApprove(HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return priceRequestService.sumListApprove(httpServletRequest);
    }

    @PostMapping(value = "/list-approve", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List approve convert to product", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listApprove(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return priceRequestService.listApprove(filter, httpServletRequest);
    }

    @PostMapping(value = "/approve", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Approve convert to product by price request id", notes = "Status: 1 = Approve, 0 = Disapprove;", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> approve(@RequestBody PriceRequestApproveRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return priceRequestService.approve(request, httpServletRequest);
    }

}
