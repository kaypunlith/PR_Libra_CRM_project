package com.ut.nlSystemAPi.controller;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.service.DropdownService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/dropdown")
@Api(tags = "10. Dropdown", description = "Dropdown Resource")
public class DropdownController {

    @Autowired
    private DropdownService dropdownService;

    @PostMapping("/nationality/list")
    @ApiOperation(value = "List zone by filter", notes = "List zone by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listNationality(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListNationality(filter, httpServletRequest);
    }

    @PostMapping("/group-api/list")
    @ApiOperation(value = "List group api by filter", notes = "List group api by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listGroupApi(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListGroupApi(filter, httpServletRequest);
    }

    @PostMapping("/user/list")
    @ApiOperation(value = "List user by filter", notes = "List user by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listUser(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListUser(filter, httpServletRequest);
    }
    @PostMapping("/company/list")
    @ApiOperation(value = "List company by filter", notes = "List company by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCompany(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCompany(filter, httpServletRequest);
    }

    @PostMapping("/chart-account-type/list")
    @ApiOperation(value = "List chart account type by filter", notes = "List chart account type by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listChartAccountType(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListChartAccountType(filter, httpServletRequest);
    }

    @PostMapping("/chart-account-group/list")
    @ApiOperation(value = "List chart account group by filter", notes = "List chart account group by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listChartAccountGroup(@RequestBody AccountTypeFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListChartAccountGroup(filter, httpServletRequest);
    }

    @PostMapping("/class/list")
    @ApiOperation(value = "List class by filter", notes = "List class by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listClass(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListClass(filter, httpServletRequest);
    }

    @PostMapping("/product/list")
    @ApiOperation(value = "List product by filter", notes = "List product by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listProduct(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListProduct(filter, httpServletRequest);
    }

    @PostMapping("/account-type/list")
    @ApiOperation(value = "List account type by filter", notes = "List account type by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listAccountType(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListAccountType(filter, httpServletRequest);
    }

    @PostMapping("/chart-account/list")
    @ApiOperation(value = "List chart of account by filter", notes = "List chart of account by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listChartAccount(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListChartAccount(filter, httpServletRequest);
    }

    @PostMapping("/chart-account-balance/list")
    @ApiOperation(value = "List chart of account by filter", notes = "List chart of account by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listChartAccountBalance(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListChartAccountBalance(filter, httpServletRequest);
    }

    @PostMapping("/province/list")
    @ApiOperation(value = "List province by filter", notes = "List province by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listProvince(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListProvince(filter, httpServletRequest);
    }

    @PostMapping("/module-type/list")
    @ApiOperation(value = "List module type dropdown by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listModuleType(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListModuleType(filter, httpServletRequest);
    }

    @PostMapping("/customer/list")
    @ApiOperation(value = "List customer by filter", notes = "isVat: 1: Non Vat, 2: Vat, 3: All", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCustomer(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCustomer(filter, httpServletRequest);
    }

    @PostMapping("/employee/list")
    @ApiOperation(value = "List employee by filter", notes = "List chart of account by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listEmployee(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListEmployee(filter, httpServletRequest);
    }

    @PostMapping("/vendor/list")
    @ApiOperation(value = "List vendor by filter", notes = "List chart of account by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listVendor(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListVendor(filter, httpServletRequest);
    }

    @PostMapping("/location/list")
    @ApiOperation(value = "List location by filter", notes = "List location by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listLocation(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListLocation(filter, httpServletRequest);
    }

    @PostMapping("/ar-schedule/invoice/list")
    @ApiOperation(value = "List ar schedule by filter", notes = "List chart of account by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listARInvoice(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListARInvoice(filter, httpServletRequest);
    }

    @PostMapping("/ap-schedule/invoice/list")
    @ApiOperation(value = "List ar schedule by filter", notes = "List chart of account by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listAPInvoice(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListAPInvoice(filter, httpServletRequest);
    }

    @PostMapping("/fix-asset/list")
    @ApiOperation(value = "List fix assets by filter", notes = "List fix assets by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listFixAsset(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListAsset(filter, httpServletRequest);
    }

    @PostMapping("/customer-group/list")
    @ApiOperation(value = "List customer group by filter", notes = "List customer group by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCustomerGroup(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCustomerGroup(filter, httpServletRequest);
    }

    @PostMapping("/customer-contact/list")
    @ApiOperation(value = "List customer contact by filter", notes = "List customer contact by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listCustomerContact(@RequestBody CustomerContactFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListCustomerContact(filter, httpServletRequest);
    }

    @PostMapping("/date-format/list")
    @ApiOperation(value = "List date format by filter", notes = "List dateformat  by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> dateFormat(@RequestBody CustomerContactFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getDateFormat(filter, httpServletRequest);
    }

    @PostMapping("/time-format/list")
    @ApiOperation(value = "List time format by filter", notes = "List time format  by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> timeFormat(@RequestBody CustomerContactFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getTimeFormat(filter, httpServletRequest);
    }

    @PostMapping("/reconcile/list")
    @ApiOperation(value = "List reconcile by filter", notes = "List reconcile  by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> reconcile(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getReconcile(filter, httpServletRequest);
    }

    @PostMapping("/branch/list")
    @ApiOperation(value = "List branch by filter", notes = "List branch  by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> branch(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getBranch(filter, httpServletRequest);
    }

    @PostMapping("/branch-type/list")
    @ApiOperation(value = "List branch type by filter", notes = "List branch  by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> branchType(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getBranchType(filter, httpServletRequest);
    }

    @PostMapping("/warehouse/list")
    @ApiOperation(value = "List warehouse by filter", notes = "List branch  by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> warehouse(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getWarehouse(filter, httpServletRequest);
    }

        @PostMapping("/countries/list")
        @ApiOperation(value = "List warehouse by filter", notes = "List branch  by filter", authorizations = {@Authorization(value = "Bearer")})
        public ResponseMessage<BaseResult> countries(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
            // Check Header Token
            if (UserAuthSession.getUserAuth() == null) {
                return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
            }
            return dropdownService.getCountries(filter, httpServletRequest);
        }

    @PostMapping("/exchange-rate/list")
    @ApiOperation(value = "List warehouse by filter", notes = "List branch  by filter", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> exchangeRate(@RequestBody Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getExchangeRate(filter, httpServletRequest);
    }

    @PostMapping("/list-apply-deposit-order")
    @ApiOperation(value = "List apply deposit by filter", notes = "1: Purchase Order, 2: Purchase Bill, 3: Quotation, 4: Sales, applyToType 1: vendor, 2: customer", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> listPurchaseOrder(@RequestBody PurchaseOrderFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getListApplyDeposit(filter, httpServletRequest);
    }

    @PostMapping("/getStatementEndingDate")
    @ApiOperation(value = "List apply deposit by filter", notes = "", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getStatementEndingDate(@RequestBody ReconcileEndingDateReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
        if (UserAuthSession.getUserAuth() == null) {
            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
        }
        return dropdownService.getStatementEndingDate(filter, httpServletRequest);
    }

    @PostMapping("/get-reference-code")
    @ApiOperation(value = "List apply deposit by filter", notes = "moduleId: 1. Journal Entry Code, 2. Receive Payment, 3. Receive Payment (Organization), 4. Receive Payment (Employee), 5. Pay bills, 6. Pay bills (Journal)", authorizations = {@Authorization(value = "Bearer")})
    public ResponseMessage<BaseResult> getReferenceCode(@RequestBody FindReferenceCodeFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        // Check Header Token
//        if (UserAuthSession.getUserAuth() == null) {
//            return ResponseMessageUtils.makeResponse(false, 401, "unauthorized", "No Permission to access");
//        }
        return dropdownService.getReferenceCode(filter, httpServletRequest);
    }

}
