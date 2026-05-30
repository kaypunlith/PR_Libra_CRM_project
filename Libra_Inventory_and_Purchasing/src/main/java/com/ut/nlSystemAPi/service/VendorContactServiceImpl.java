package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.VendorContactMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.VendorContactFilter;
import com.ut.nlSystemAPi.model.request.Login.VendorContact.VendorContactRequest;
import com.ut.nlSystemAPi.model.request.Login.VendorContact.VendorContactUpdateRequest;
import com.ut.nlSystemAPi.model.response.VendorContact.VendorContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class VendorContactServiceImpl implements VendorContactService {

    @Autowired
    private VendorContactMapper vendorContactMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(VendorContactFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(vendorContactMapper.countList(filter));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<VendorContactResponse> responses = vendorContactMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/list", null, null, "vendorManagement", "vendorManagement(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/list", line, error.toString(), "vendorManagement", "vendorManagement(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//      if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//      }

            List<VendorContactResponse> responses = vendorContactMapper.getOne(id);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/find/{id}", null, null, "System vendorManagement", "System vendorManagement (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/find/{id}", line, error.toString(), "System vendorManagement", "System vendorManagement (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(VendorContactRequest vendorContactRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Employee (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            // Check Duplicate
            if(vendorContactMapper.checkDuplicate(vendorContactRequest.getContactName(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            // Check Data
            VendorContact  vendorContact = new VendorContact();
            vendorContact.setVendorId(vendorContactRequest.getVendorId());
            vendorContact.setSex(vendorContactRequest.getSex());
            vendorContact.setContactName(vendorContactRequest.getContactName());
            vendorContact.setContactEmail(vendorContactRequest.getContactEmail());
            vendorContact.setContactTelephone(vendorContactRequest.getContactTelephone());
            vendorContact.setNote(vendorContactRequest.getNote());
            vendorContact.setIsActive(1);
            vendorContact.setCreatedBy(userId);
            Boolean result = vendorContactMapper.insert(vendorContact);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/vendorManagement/add", null, null, "vendorManagement", "vendorManagement (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/add", line, error.toString(), "vendorManagement", "vendorManagement (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(VendorContactUpdateRequest vendorContactUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            // Check Data
            VendorContact  vendorContact = new VendorContact();
            vendorContact.setId(vendorContactUpdateRequest.getId());
            vendorContact.setVendorId(vendorContactUpdateRequest.getVendorId());
            vendorContact.setSex(vendorContactUpdateRequest.getSex());
            vendorContact.setContactName(vendorContactUpdateRequest.getContactName());
            vendorContact.setContactEmail(vendorContactUpdateRequest.getContactEmail());
            vendorContact.setContactTelephone(vendorContactUpdateRequest.getContactTelephone());
            vendorContact.setNote(vendorContactUpdateRequest.getNote());
            vendorContact.setModifiedBy(userId);

            Boolean result = vendorContactMapper.update(vendorContact);

            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/vendorManagement/update", null, null, "vendorManagement", "vendorManagement (Update)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/vendorManagement/update", line, error.toString(), "vendorManagement", "vendorManagement (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//      if (permissionMapper.checkPermission(userId, "System Role (Delete)") == 0) {
//        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//      }

            Boolean result = vendorContactMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/vendorManagement/delete/{id}",null,null,"vendorManagement","vendorManagement (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/delete/{id}",line, error.toString(),"vendorManagement","vendorManagement (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


}
