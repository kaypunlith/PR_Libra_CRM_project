package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ClassAccountMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.ClassAccount;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.ClassAccount.ClassAccountRequest;
import com.ut.nlSystemAPi.model.request.Login.ClassAccount.ClassAccountUpdateRequest;
import com.ut.nlSystemAPi.model.response.ClassAccount.ClassAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class ClassAccountServiceImpl implements ClassAccountService {

    @Autowired
    private ClassAccountMapper classAccountMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    Environment environment;

    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Class (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(classAccountMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ClassAccountResponse> classAccountResponses = classAccountMapper.getList(filter);

            if (classAccountResponses != null && classAccountResponses.size() > 0) {
                for (int i = 0; i < classAccountResponses.size(); i++){
                    classAccountResponses.get(i).setCompanyResponses(classAccountMapper.getCompanyByClassId(classAccountResponses.get(i).getId()));
                    classAccountResponses.get(i).setUserPermissionResponse(classAccountMapper.getUserByClassId(classAccountResponses.get(i).getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-account/list",null,null,"Class Account","Class Account (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", classAccountResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-account/list",line, error.toString(),"Class Account","Class Account (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
//
    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Class (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            //! Set Slide
            List<ClassAccountResponse> classAccountResponses= classAccountMapper.getOne(id);

            if (classAccountResponses != null && classAccountResponses.size() > 0) {
                for (int i = 0; i < classAccountResponses.size(); i++){
                    classAccountResponses.get(i).setCompanyResponses(classAccountMapper.getCompanyByClassId(classAccountResponses.get(i).getId()));
                    classAccountResponses.get(i).setUserPermissionResponse(classAccountMapper.getUserByClassId(classAccountResponses.get(i).getId()));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-account/find/{id}",null,null,"Class Account","Class Account (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", classAccountResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-account/find/{id}",line, error.toString(),"Class Account","Class Account (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ClassAccountRequest classAccountRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Class (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if(classAccountMapper.checkDuplicate(classAccountRequest.getName(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate class name.", false));
            }

            // Check Data
            ClassAccount classAccount = new ClassAccount();

            classAccount.setParentId(classAccountRequest.getParentId());
            classAccount.setName(classAccountRequest.getName());
            classAccount.setDescription(classAccountRequest.getDescription());
            classAccount.setOrdering(classAccountRequest.getOrdering());
            classAccount.setCompanyId(classAccountRequest.getCompanyId());
            classAccount.setUserId(classAccountRequest.getUserId());

            classAccount.setCreatedBy(userId);
            classAccount.setIsActive(1);

            // Insert
            Boolean result = classAccountMapper.insert(classAccount);

            if (result) {
                // Insert Companies
                for (int i = 0; i < classAccount.getCompanyId().size(); i++){
                    classAccountMapper.insertCompanyId(classAccount.getId(), classAccount.getCompanyId().get(i));
                }
                // Insert users
                for (int i = 0; i < classAccount.getUserId().size(); i++){
                    classAccountMapper.insertUserClass(classAccount.getId(), classAccount.getUserId().get(i));
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/class-account/add",null,null,"Class Account","Class Account (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-account/add",line, error.toString(),"Class Account","Class Account (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> update(ClassAccountUpdateRequest classAccountUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Class (edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if(classAccountMapper.checkDuplicate(classAccountUpdateRequest.getName(), classAccountUpdateRequest.getId()) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate class name.", false));
            }

            // Check Data
            ClassAccount classAccount = new ClassAccount();

            classAccount.setId(classAccountUpdateRequest.getId());
            classAccount.setParentId(classAccountUpdateRequest.getParentId());
            classAccount.setName(classAccountUpdateRequest.getName());
            classAccount.setDescription(classAccountUpdateRequest.getDescription());
            classAccount.setOrdering(classAccountUpdateRequest.getOrdering());
            classAccount.setCompanyId(classAccountUpdateRequest.getCompanyId());
            classAccount.setUserId(classAccountUpdateRequest.getUserId());

            classAccount.setModifiedBy(userId);
            classAccount.setIsActive(1);

            // Insert Chart Account
            Boolean result = classAccountMapper.update(classAccount);

            if (result) {
                // Delete Chart Account Company
                classAccountMapper.deleteClassCompany(classAccount.getId());

                classAccountMapper.deleteClassUser(classAccount.getId());

                // Insert Chart Account Company
                for (int i = 0; i < classAccount.getCompanyId().size(); i++){
                    classAccountMapper.insertCompanyId(classAccount.getId(), classAccount.getCompanyId().get(i));
                }

                // Insert users
                for (int i = 0; i < classAccount.getUserId().size(); i++){
                    classAccountMapper.insertUserClass(classAccount.getId(), classAccount.getUserId().get(i));
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/class-account/update",null,null,"Class Of Account","Class Of Account (Update)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-account/update",line, error.toString(),"Class Of Account","Class Of Account (Update)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
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

            if (permissionMapper.checkPermission(userId, "Class (delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = classAccountMapper.delete(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/class-account/delete/{id}", null, null, "Class Account", "Class Account (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/class-account/delete/{id}", line, error.toString(), "Class Account", "Class Account (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}