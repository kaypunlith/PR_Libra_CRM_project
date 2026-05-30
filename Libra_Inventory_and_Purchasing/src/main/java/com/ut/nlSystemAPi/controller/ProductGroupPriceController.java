package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.CalculateTotalProductFilter;
import com.ut.nlSystemAPi.model.filter.ProductGroupPriceFilter;
import com.ut.nlSystemAPi.model.request.Login.ProductGroupPrice.ProductGroupPriceRequest;
import com.ut.nlSystemAPi.model.request.Login.ProductGroupPrice.ProductGroupPriceUpdateRequest;
import com.ut.nlSystemAPi.service.ProductGroupPriceService;
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
@RequestMapping("/product-group-price-setting")
@Api(tags = "19. Product Group Price Setting", description = "Product Group Price Setting Resource")
@Timed
public class ProductGroupPriceController {
    @Autowired
    private ProductGroupPriceService productGroupPriceService;

    @PostMapping("/list")
    @ApiOperation(value = "List product group price type by filter", notes = "List product group by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody ProductGroupPriceFilter productGroupPriceFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productGroupPriceService.getList(productGroupPriceFilter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find product group price by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productGroupPriceService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new product group price", notes = "SetAs: 2 = Percentage, 3 = Mark Up; CostMethod: 1 = Last Cost , 2 = Avg Cost; ApplyToAllProduct: 1 = False , 0 = True;", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody ProductGroupPriceRequest productGroupPriceRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productGroupPriceService.insert(productGroupPriceRequest, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update product group price by id", notes = "User Apply: 1 = Customize, 0 = All;", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody ProductGroupPriceUpdateRequest productGroupPriceUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productGroupPriceService.update(productGroupPriceUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete product group price by id", notes = "Delete product group price", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productGroupPriceService.delete(id, httpServletRequest);
    }

    @PostMapping("/calculate-total-product")
    @ApiOperation(value = "Calculate total product group price", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> calculateTotal(@RequestBody CalculateTotalProductFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productGroupPriceService.calculateTotal(filter, httpServletRequest);
    }

    @PostMapping("/list-total-product")
    @ApiOperation(value = "List total product group price", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listTotal(@RequestBody CalculateTotalProductFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productGroupPriceService.listTotal(filter, httpServletRequest);
    }

    @PostMapping("/list-clone/{id}")
    @ApiOperation(value = "List clone product group price", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listClone(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productGroupPriceService.listClone(id, httpServletRequest);
    }
}