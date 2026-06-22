package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.DropdownMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.ModuleType;
import com.ut.nlSystemAPi.model.ModuleTypeFilter;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.*;
import com.ut.nlSystemAPi.model.response.Dropdown.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DropdownServiceImpl implements DropdownService{

    @Autowired
    private DropdownMapper dropdownMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getListNationality(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {

        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<NationalityDropdownResponse> dropdownResponses = dropdownMapper.getListNationality(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/nationality/list",null,null,"Nationality","Nationality (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/nationality/list",line, error.toString(),"Nationality","Nationality (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListGroupApi(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<GroupApiDropdownResponse> dropdownResponses = dropdownMapper.getListGroupApi(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/group-api/list",null,null,"Group Api","Group Api (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/group-api/list",line, error.toString(),"Group Api","Group Api (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListUser(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<UserDropdownResponse> dropdownResponses = dropdownMapper.getListUser(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user/list",null,null,"User","User (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user/list",line, error.toString(),"User","User (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCompany(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CompanyDropdownResponse> dropdownResponses = dropdownMapper.getListCompany(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company/list",null,null,"Company","Company (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/company/list",line, error.toString(),"Company","Company (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListChartAccountType(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ChartAccountTypeDropdownResponse> dropdownResponses = dropdownMapper.getListChartAccountType(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-type/list",null,null,"Chart of Account Type","Chart of Account Type (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-type/list",line, error.toString(),"Chart of Account Type","Chart of Account Type (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListChartAccountGroup(AccountTypeFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ChartAccountGroupDropdownResponse> dropdownResponses = dropdownMapper.getListChartAccountGroup(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-group/list",null,null,"Chart of Account Group","Chart of Account Group (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account-group/list",line, error.toString(),"Chart of Account Group","Chart of Account Group (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListClass(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ClassDropdownResponse> dropdownResponses = dropdownMapper.getListClass(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class/list",null,null,"Class","Class (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class/list",line, error.toString(),"Class","Class (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListProduct(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ProductDropdownResponse> dropdownResponses = dropdownMapper.getListProduct(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/list",null,null,"Product","Product (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/list",line, error.toString(),"Product","Product (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListAccountType(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<AccountTypeDropdownResponse> dropdownResponses = dropdownMapper.getListAccountType(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/account-type/list",null,null,"Account Type","Account Type (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/account-type/list",line, error.toString(),"Account Type","Account Type (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListChartAccount(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ChartAccountDropdownResponse> dropdownResponses = dropdownMapper.getListChartAccount(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account/list",null,null,"Chart of Account","Chart of Account (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account/list",line, error.toString(),"Chart of Account","Chart of Account (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListChartAccountBalance(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ChartAccountDropdownResponse> dropdownResponses = dropdownMapper.getListChartAccountBalance(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account/list",null,null,"Chart of Account","Chart of Account (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/chart-account/list",line, error.toString(),"Chart of Account","Chart of Account (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    

    @Override
    public ResponseMessage<BaseResult> getListProvince(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ProvinceDropdownResponse> dropdownResponses = dropdownMapper.getListProvince(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/province/list",null,null,"Province","Province (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/province/list",line, error.toString(),"Province","Province (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListModuleType(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ModuleTypeDropdownResponse> dropdownResponses = dropdownMapper.getListModuleType(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module-type/list",null,null,"Module Type","Module Group (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/module-type/list",line, error.toString(),"Module Type","Module Group (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCustomer(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CustomerDropdownResponse> dropdownResponses = new ArrayList<>();

            if(filter.getModuleId() != null && filter.getModuleId() == 1){
                // Receive Payment
                dropdownResponses = dropdownMapper.getListReceivedPaymentCustomer(filter);
            } else if(filter.getModuleId() != null && filter.getModuleId() == 2) {
                // Receive Payment org
                dropdownResponses = dropdownMapper.getListCustomer(filter);
            } else {
                // All
                dropdownResponses = dropdownMapper.getListCustomer(filter);
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/customer/list",null,null,"Drop Down","Drop Down (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/customer/list",line, error.toString(),"Chart of Account","Chart of Account (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListEmployee(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<EmployeeDropdownResponse> dropdownResponses = dropdownMapper.getListEmployee(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListVendor(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<VendorDropdownResponse> dropdownResponses = dropdownMapper.getListVendor(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/employee/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListARInvoice(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ARScheduleInvoiceDropdownResponse> dropdownResponses = dropdownMapper.getListARInvoice(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ar-schedule/invoice/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/ar-schedule/invoice/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListAPInvoice(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<APScheduleInvoiceDropdownResponse> dropdownResponses = dropdownMapper.getListAPInvoice(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("drop-down/ar-schedule/invoice/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("drop-down/ar-schedule/invoice/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListLocation(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<LocationDropdownResponse> dropdownResponses = dropdownMapper.getListLocation(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/location/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/location/invoice/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListAsset(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<AssetDropdownResponse> dropdownResponses = dropdownMapper.getListAsset(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/asset/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/asset/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCustomerGroup(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ZoneDropdownResponse> dropdownResponses = dropdownMapper.getListCustomerGroup(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/customer-group/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/customer-group/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListCustomerContact(CustomerContactFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ZoneDropdownResponse> dropdownResponses = dropdownMapper.getListCustomeContact(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/customer-contact/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dropdownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/customer-contact/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getDateFormat(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DateFormatResponse> dateFormatResponses = dropdownMapper.getListDateFormat(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/date-format/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dateFormatResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/date-format/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> getTimeFormat(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DateFormatResponse> dateFormatResponses = dropdownMapper.getListTimeFormat(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/time format/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dateFormatResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/time format/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getReconcile(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ReconcileDropDownResponse> reconcileDropDownResponses = dropdownMapper.getReconcile(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/reconcile/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", reconcileDropDownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/reconcile/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getBranch(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<BranchDropDownResponse> branchDropDownResponses = dropdownMapper.getBranch(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/branch/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", branchDropDownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/branch/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getBranchType(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<BranchTypeDropDownResponse> branchTypeDropDownResponses = dropdownMapper.getBranchType(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/branch-type/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", branchTypeDropDownResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/branch-type/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> getWarehouse(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<WarehouseDropDownResponse> warehouse = dropdownMapper.getWarehouse(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/warehouse/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", warehouse, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/warehouse/list",line, error.toString(),"Dropdown","Dropdown (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> getCountries(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<WarehouseDropDownResponse> warehouse = dropdownMapper.getCountries(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/countries/list",null,null,"Dropdown","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", warehouse, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/countries/list", line, error.toString(), "Dropdown", "Dropdown (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getExchangeRate(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ExchangeRateDropDownResponse> exchangeRate = dropdownMapper.getExchangeRate(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/exchangeRate/list",null,null,"exchangeRate","Dropdown (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", exchangeRate, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/exchangeRate/list", line, error.toString(), "Dropdown", "Dropdown (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListApplyDeposit(PurchaseOrderFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<PurchaseOrderDropdownResponse> responses = new ArrayList<>();

            if(filter.getType() == 1){
                pagination.setTotal(dropdownMapper.getCountPurchaseOrderList(filter));
                responses = dropdownMapper.getPurchaseOrderList(filter);

                // Populate purchase order details (items + services) from purchase requests
                if (!responses.isEmpty()) {
                    for (int i = 0; i < responses.size(); i++) {
                        responses.get(i).setDetails(
                                dropdownMapper.getPurchaseOrderDetail(responses.get(i).getId())
                        );
                    }
                }


            } else if(filter.getType() == 2){
                pagination.setTotal(dropdownMapper.getCountPurchaseBill(filter));
                responses = dropdownMapper.getPurchaseBillList(filter);
                // Populate purchase bill details (items + services)
                if (!responses.isEmpty()) {
                    for (int i = 0; i < responses.size(); i++) {
                        responses.get(i).setDetails(
                                dropdownMapper.getPurchaseBillDetail(responses.get(i).getId())
                        );
                    }
                }

            } else if(filter.getType() == 3){
                pagination.setTotal(dropdownMapper.getCountQuotation(filter));
                responses = dropdownMapper.getQuotationList(filter);
                // Populate quotation details (items + services + miscs + breaks)
                if (!responses.isEmpty()) {
                    for (int i = 0; i < responses.size(); i++) {
                        responses.get(i).setDetails(
                                dropdownMapper.getQuotationDetail(responses.get(i).getId())
                        );
                    }
                }

            } else {
                pagination.setTotal(dropdownMapper.getCountSalesOrder(filter));
                responses = dropdownMapper.getSalesOrderList(filter);

                if (!responses.isEmpty()) {
                    for (int i = 0; i < responses.size(); i++) {
                        responses.get(i).setDetails(
                                dropdownMapper.getSalesOrderDetail(responses.get(i).getId())
                        );
                    }
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/apply_deposit/list",null,null,"Apply Deposit","Dropdown (Apply Deposit)","Apply Deposit",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/apply_deposit/list", line, error.toString(), "Apply Deposit", "Dropdown (Apply Deposit)", "Apply Deposit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> getStatementEndingDate(ReconcileEndingDateReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            pagination.setTotal(dropdownMapper.getCountStatementEndingDate(filter));

            List<PurchaseOrderDropdownResponse> responses = dropdownMapper.getStatementEndingDate(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/getStatementEndingDate/list",null,null,"getStatementEndingDate","Dropdown (getStatementEndingDate)","getStatementEndingDate",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/getStatementEndingDate/list", line, error.toString(), "getStatementEndingDate", "Dropdown (getStatementEndingDate)", "getStatementEndingDate", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getReferenceCode(FindReferenceCodeFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {

            String branchCode = dropdownMapper.getBranchCodeByModule(filter);

            String response;
            if(branchCode != null){
                // get 2 string year
                LocalDate date = LocalDate.now();
                String lastTwoDigits = date.format(DateTimeFormatter.ofPattern("yy"));

                response = lastTwoDigits + branchCode;
            } else {
                response = "";
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/get-reference-code",null,null,"getStatementEndingDate","Dropdown (get-reference-code)","get-reference-code",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message(response, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/drop-down/get-reference-code", line, error.toString(), "getStatementEndingDate", "Dropdown (get-reference-code)", "get-reference-code", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}
