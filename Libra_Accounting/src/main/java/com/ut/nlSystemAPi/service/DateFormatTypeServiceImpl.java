package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.BranchMapper;
import com.ut.nlSystemAPi.mapper.primary.DateFormatTypeMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.Branch;
import com.ut.nlSystemAPi.model.DateFormatType;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.Branch.BranchRequest;
import com.ut.nlSystemAPi.model.request.Login.Branch.BranchUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.DateFormatType.DateFormatTypeRequest;
import com.ut.nlSystemAPi.model.response.Branch.BranchResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.DateFormatResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.NationalityDropdownResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class DateFormatTypeServiceImpl implements DateFormatTypeService {

    @Autowired
    private DateFormatTypeMapper dateFormatTypeMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;



    @Override
    public ResponseMessage<BaseResult>insert(DateFormatTypeRequest dateFormatTypeRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
//
            DateFormatType dateFormatType = new DateFormatType();
            dateFormatType.setDateFormatId(dateFormatTypeRequest.getDateFormatId());
            dateFormatType.setTimeFormatId(dateFormatTypeRequest.getTimeFormatId());
            Boolean result = dateFormatTypeMapper.UpdateDateFormatType(dateFormatType);

            if (result) {
                   dateFormatTypeMapper.UpdateTimeFormatType(dateFormatType);
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/Date format Type/add", null, null, "Date format Type", "Date format Type (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            // System Activity for Error
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("Date format Type/add", 1033L, error.toString(), "Date format Type", "Date format Type (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {

        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DateFormatResponse> dateFormatResponses = dateFormatTypeMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/date-format-type/list",null,null,"date-format-type","date-format-type (view)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", dateFormatResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/date-format-type/list",line, error.toString(),"date-format-type","date-format-type (view)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
