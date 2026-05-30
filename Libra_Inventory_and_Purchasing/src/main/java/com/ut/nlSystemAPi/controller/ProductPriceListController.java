package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ProductPriceListFilter;
import com.ut.nlSystemAPi.model.filter.ProductPriceListProductDetailFilter;
import com.ut.nlSystemAPi.model.request.Login.ProductPriceList.ProductListApproveStatus;
import com.ut.nlSystemAPi.model.request.Login.ProductPriceList.ProductPriceListRequest;
import com.ut.nlSystemAPi.model.request.Login.ProductPriceList.ProductPriceListUpdateRequest;
import com.ut.nlSystemAPi.service.ProductPriceListService;
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
@RequestMapping("/product-price-list")
@Api(tags = "20. Product Price List", description = "Product Price List Resource")
@Timed
public class ProductPriceListController {
    @Autowired
    private ProductPriceListService productPriceListService;

    @PostMapping("/list")
    @ApiOperation(value = "List product  price list type by filter", notes = "List product list price by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody ProductPriceListFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productPriceListService.getList(filter, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update product List price by id", notes = "organization:select from customer", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody ProductPriceListRequest productPriceListRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productPriceListService.insert(productPriceListRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find product group price by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productPriceListService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update product List price by id", notes = "organization:select from customer", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody ProductPriceListUpdateRequest productPriceListUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productPriceListService.update(productPriceListUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete product group price by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productPriceListService.delete(id, httpServletRequest);
    }

    @PostMapping("/update-is-approve")
    @ApiOperation(value = "Update is approve by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> ApproveStatus(@RequestBody ProductListApproveStatus productListApproveStatus, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productPriceListService.updateApproveStatus(productListApproveStatus, httpServletRequest);
    }

    @PostMapping("/update-is-close")
    @ApiOperation(value = "Update is approve by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> CloseStatus(@RequestBody ProductListApproveStatus productListApproveStatus, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productPriceListService.updateIsClose(productListApproveStatus, httpServletRequest);
    }

    @PostMapping("/list-product-details")
    @ApiOperation(value = "List product details by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listProductDetails(@RequestBody ProductPriceListProductDetailFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productPriceListService.getListProductDetails(filter, httpServletRequest);
    }

}