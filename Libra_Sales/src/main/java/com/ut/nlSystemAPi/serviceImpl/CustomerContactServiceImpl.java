package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.base.BaseResult;

import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;

import com.ut.nlSystemAPi.model.entity.CustomerContact.CustomerContact;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.filter.CustomerContactFilter;
import com.ut.nlSystemAPi.model.request.CustomerContact.*;
import com.ut.nlSystemAPi.model.response.CustomerContact.CustomerContactResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.UserService;
import com.ut.nlSystemAPi.service.CustomerContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerContactServiceImpl implements CustomerContactService {

    @Autowired
    private CustomerContactMapper customerContactMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ModuleTypeMapper moduleTypeMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private GenerateCode generateCode;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    public ResponseMessage<BaseResult> getList(CustomerContactFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer Contact (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Long employeeId = customerContactMapper.getEmployeeId(userId);

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(customerContactMapper.countList(filter, employeeId, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CustomerContactResponse> responses = customerContactMapper.getList(filter, employeeId, userId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/customer-contact/list", null, null, "Customer Contact", "Customer Contact (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/customer-contact/list", line, error.toString(), "Customer Contact", "Customer Contact (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer Contact (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<CustomerContactResponse> responses = customerContactMapper.getOne(id);

            if (!responses.isEmpty()){
                for (CustomerContactResponse response : responses) {
                    response.setDescriptions(customerContactMapper.getDescription(id));
                    response.setProgress(customerContactMapper.getProgress(id));
                    response.setContactLists(customerContactMapper.getContactList(id));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/customer-contact/find/{id}", null, null, "Customer Contact", "Customer Contact (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/customer-contact/find/{id}", line, error.toString(), "Customer Contact", "Customer Contact (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(CustomerContactRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer Contact (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            String code = generateCode.generateCustomerContactCode();

//            // Check Duplicate
//            if (customerContactMapper.checkDuplicate(request.g(), null) > 0) {
//                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
//            }

            // Check Data
            CustomerContact customerContact = new CustomerContact();
//            customerContact.setType(request.getType());
            customerContact.setOrganizationId(request.getOrganizationId());
            customerContact.setCode(code);
            customerContact.setGender(request.getGender());
            customerContact.setPhoto(request.getPhoto());
            customerContact.setContactName(request.getContactName());
            customerContact.setContactTelephone(request.getContactTelephone());
            customerContact.setContactEmail(request.getContactEmail());
            customerContact.setPosition(request.getPosition());
            customerContact.setFamilyId(request.getFamilyId());
            customerContact.setAge(request.getAge());
            customerContact.setLocationId(request.getLocationId());
            customerContact.setCharacterId(request.getCharacterId());
            customerContact.setNote(request.getNote());
            customerContact.setDob(request.getDob());
            customerContact.setCreatedBy(userId);

            Boolean result = customerContactMapper.insert(customerContact);

            if (result) {

                if (request.getDescriptions() != null && !request.getDescriptions().isEmpty()) {
                    for (CustomerContactDescriptionRequest descriptionRequest : request.getDescriptions()) {
                        customerContactMapper.insertDescription(customerContact.getId(), descriptionRequest.getTypeId(), descriptionRequest.getDescription());
                    }
                }

                if (request.getProgress() != null && !request.getProgress().isEmpty()) {
                    for (CustomerContactProgressRequest progressRequest : request.getProgress()) {
                        customerContactMapper.insertProgress(customerContact.getId(), progressRequest.getId(), progressRequest.getPercent());
                    }
                }

                if (request.getListDetailIds() != null) {
                    for (Long listDetailId : request.getListDetailIds()) {
                        customerContactMapper.insertContactList(customerContact.getId(), listDetailId);
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/customer-contact/add", null, null, "Customer Contact", "Customer Contact (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/customer-contact/add", line, error.toString(), "Customer Contact", "Customer Contact (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(CustomerContactUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer Contact (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

//            // Check Duplicate
//            if (customerContactMapper.checkDuplicate(request.getName(), request.getId()) > 0) {
//                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
//            }

            // Check Data
            CustomerContact customerContact = new CustomerContact();
            customerContact.setId(request.getId());
//            customerContact.setType(request.getType());
            customerContact.setOrganizationId(request.getOrganizationId());
            customerContact.setGender(request.getGender());
            customerContact.setPhoto(request.getPhoto());
            customerContact.setGender(request.getGender());
            customerContact.setContactName(request.getContactName());
            customerContact.setContactTelephone(request.getContactTelephone());
            customerContact.setContactEmail(request.getContactEmail());
            customerContact.setPosition(request.getPosition());
            customerContact.setFamilyId(request.getFamilyId());
            customerContact.setAge(request.getAge());
            customerContact.setLocationId(request.getLocationId());
            customerContact.setCharacterId(request.getCharacterId());
            customerContact.setNote(request.getNote());
            customerContact.setDob(request.getDob());
            customerContact.setModifiedBy(userId);
            Boolean result = customerContactMapper.update(customerContact);

            if (result) {

                customerContactMapper.deleteDescription(customerContact.getId());
                if (request.getDescriptions() != null && !request.getDescriptions().isEmpty()) {
                    for (CustomerContactDescriptionRequest descriptionRequest : request.getDescriptions()) {
                        customerContactMapper.insertDescription(customerContact.getId(), descriptionRequest.getTypeId(), descriptionRequest.getDescription());
                    }
                }

                customerContactMapper.deleteProgress(customerContact.getId());
                if (request.getProgress() != null && !request.getProgress().isEmpty()) {
                    for (CustomerContactProgressRequest progressRequest : request.getProgress()) {
                        customerContactMapper.insertProgress(customerContact.getId(), progressRequest.getId(), progressRequest.getPercent());
                    }
                }

                customerContactMapper.deleteContactList(customerContact.getId());
                if (request.getListDetailIds() != null) {
                    for (Long listDetailId : request.getListDetailIds()) {
                        customerContactMapper.insertContactList(customerContact.getId(), listDetailId);
                    }
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/customer-contact/update", null, null, "Customer Contact", "Customer Contact (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/customer-contact/update", line, error.toString(), "Customer Contact", "Customer Contact (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer Contact (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = customerContactMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/customer-contact/delete/{id}", null, null, "Customer Contact", "Customer Contact (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/customer-contact/delete/{id}", line, error.toString(), "Customer Contact", "Customer Contact (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> rate(CustomerContactRateRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer Contact (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = customerContactMapper.insertNps(request.getId(), request.getRating(), userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/customer-contact/update", null, null, "Customer Contact", "Customer Contact (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/customer-contact/update", line, error.toString(), "Customer Contact", "Customer Contact (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> apply(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Customer Contact (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = customerContactMapper.apply(id);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/customer-contact/update", null, null, "Customer Contact", "Customer Contact (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/customer-contact/update", line, error.toString(), "Customer Contact", "Customer Contact (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}
