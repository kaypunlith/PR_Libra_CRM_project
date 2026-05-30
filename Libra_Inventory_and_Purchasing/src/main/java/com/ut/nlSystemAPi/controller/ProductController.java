package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ProductFilter;
import com.ut.nlSystemAPi.model.filter.ProductPriceFilter;
import com.ut.nlSystemAPi.model.filter.UomSkuFilter;
import com.ut.nlSystemAPi.model.request.Login.Product.*;
import com.ut.nlSystemAPi.service.ProductService;
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
import java.util.List;

@RestController
@RequestMapping("/product")
@Api(tags = "17. Product", description = "Product Resource")
@Timed
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/list")
    @ApiOperation(value = "List product by filter", notes = "Show: 2 = Batch Code, 1 = Package, 0 = Product; isActive: 1 = Active, 2 = Inactive; Can Search By UPC, SKU, Name;", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> list(@RequestBody ProductFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productService.getList(filter, httpServletRequest);
    }

    @PostMapping("/find/{id}")
    @ApiOperation(value = "Find product by id", notes = "isForSale: 1 = Yes, 0 = No; isActive: 1 = Active, 2 = Inactive; Created By/isPacket: 2 = Batch Code, 1 = Single Code; isExpiredDate: 1 = Yes, 0 = No; ICS: 1 = Inventory Asset Account, 2 = COGS Account, 8 = Sales Income", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productService.getOne(id, httpServletRequest);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add new product", notes = "isForSale: 1 = Yes, 0 = No; isActive: 1 = Active, 2 = Inactive; Created By/isPacket: 2 = Batch Code, 1 = Single Code; isExpiredDate: 1 = Yes, 0 = No; ChartAccountType: 1 = Inventory Asset Account, 2 = COGS Account, 8 = Sales Income", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> add(@RequestBody ProductRequest productRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productService.insert(productRequest, bindingResult, httpServletRequest);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update product by id", notes = "isForSale: 1 = Yes, 0 = No; isActive: 1 = Active, 2 = Inactive; Created By: 2 = Batch Code, 1 = Single Code; isExpiredDate: 1 = Yes, 0 = No; ICS: 1 = Inventory Asset Account, 2 = COGS Account, 8 = Sales Income", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> update(@RequestBody ProductUpdateRequest productUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productService.update(productUpdateRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete product by id", notes = "Delete product", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productService.delete(id, httpServletRequest);
    }

    @PostMapping("/is-active/update")
    @ApiOperation(value = "Update product active status by id", notes = "isActive: 1 = Active, 2 = Inactive;", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateActiveStatus(@RequestBody ProductActiveStatus productActiveStatus, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productService.updateActiveStatus(productActiveStatus, httpServletRequest);
    }

    @PostMapping("/list/price")
    @ApiOperation(value = "List product price by filter", notes = "SetType: 1 = Amount, 2 = Percent, 3 = Mark Up;", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPrice(@RequestBody ProductPriceFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productService.getListPrice(filter, httpServletRequest);
    }

    @PostMapping("/list/price-history")
    @ApiOperation(value = "List product price history by filter", notes = "SetType: 1 = Amount, 2 = Percent, 3 = Mark Up;", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPriceHistory(@RequestBody ProductPriceFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productService.getListPriceHistory(filter, httpServletRequest);
    }

    @PostMapping("/add/price")
    @ApiOperation(value = "List product price history by filter", notes = "SetType: 1 = Amount, 2 = Percent, 3 = Mark Up;", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> addPrice(@RequestBody List<ProductPriceRequest> productPriceRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productService.addPrice(productPriceRequest, bindingResult, httpServletRequest);
    }
    @PostMapping(value = "/update-est-cost", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update product by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> updateCost(@RequestBody ProductUpdateCostRequest productUpdateCostRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productService.updateCost(productUpdateCostRequest, bindingResult, httpServletRequest);
    }

    @PostMapping("/find/uom-sku")
    @ApiOperation(value = "Get sku by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> findUomSku(@RequestBody UomSkuFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productService.getUomSku(filter, httpServletRequest);
    }

    @PostMapping("/is-end-of-life")
    @ApiOperation(value = "Product end of life by id", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> isEndOfLife(@RequestBody ProductEndOfLife request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return productService.isEndOfLife(request, httpServletRequest);
    }

}
