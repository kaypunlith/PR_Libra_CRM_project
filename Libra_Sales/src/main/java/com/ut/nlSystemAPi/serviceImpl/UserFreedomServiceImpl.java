package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.base.UserAuthSession;
import com.ut.nlSystemAPi.helper.FileUploadUtils;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.freedom.FreedomMapper;
import com.ut.nlSystemAPi.mapper.primary.ModuleMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.RoleMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.ModuleType;
import com.ut.nlSystemAPi.model.Users.User;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.User.EditProfileRequest;
import com.ut.nlSystemAPi.model.response.User.UserResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.UserFreedomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class UserFreedomServiceImpl implements UserFreedomService {

    @Autowired
    private FreedomMapper freedomMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserFreedomService userFreedomService;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    Environment environment;

    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userFreedomService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "User (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(freedomMapper.countListUser(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<UserResponse> userList = freedomMapper.getListUser(filter);
            if (userList != null && userList.size() > 0) {
                for (UserResponse userResponse : userList) {
                    userResponse.setUserRoleList(freedomMapper.getOneUserGroupList(userResponse.getId()));
                    userResponse.setFreedomWarehouse(freedomMapper.getUserLocationGroups(userResponse.getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user-freedom/list",null,null,"User","User (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", userList, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user-freedom/list",line, error.toString(),"User","User (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userFreedomService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "User (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<User> userList = freedomMapper.getOneUser(id);
            if (userList != null && userList.size() > 0) {
                for (User user : userList) {
                    user.setUserRoleList(freedomMapper.getOneUserGroupList(user.getId()));
                    user.setFreedomWarehouse(toLongArray(freedomMapper.getUserLocationGroupIds(user.getId())));
                    user.setWarehouseName(freedomMapper.getUserLocationGroups(user.getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user-freedom/find/{id}",null,null,"User","User (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", userList, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user-freedom/find/{id}",line, error.toString(),"User","User (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(User user, MultipartFile file, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userFreedomService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "User (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

//            String passwd = user.getPassword();
//            String pattern = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}";
//
//            if(passwd != null){
//                if(!passwd.matches(pattern)){
//                    return ResponseMessageUtils.makeResponse(true, messageService.message("Password must have at least one upperCase, number, 8 characters and special character", false));
//                }
//            }


            List<User> existingUserList = freedomMapper.getOneByUsername(user.getUsername());
            if (existingUserList.size() > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate User", false));
            }

            // password
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // upload Signature
            if (file != null) {
                String fileSignature = FileUploadUtils.saveFileUploaded(file, "logs/update-share/" + environment.getProperty("server.servlet.context-path"));
                user.setSignature(fileSignature);
            }

            // check data
            user.setCreatedBy(userId);
            user.setIsActive(1);
            Boolean result = freedomMapper.insertUser(user);
            if (result) {
                // check user role
                if (user.getUserGroup().length > 0) {
                    insertSystemRoleUser(user.getUserGroup(), user.getId());
                }
                upsertUserWarehouse(user.getId(), user.getFreedomWarehouse());
                ensureUserCompanies(user.getId(), userId);

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/user-freedom/add",null,null,"User","User (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user-freedom/add",line, error.toString(),"User","User (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(User user, MultipartFile file, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userFreedomService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "User (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

//            String passwd = user.getPassword();
//            String pattern = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}";
//
//            if(passwd != null) {
//                if(!passwd.matches(pattern)) {
//                    return ResponseMessageUtils.makeResponse(true, messageService.message("Password must have at least one upperCase, number, 8 characters and special character", false));
//                }
//            }

            if (user.getPassword() != null && !user.getPassword().equals("")) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            // upload Signature
            if (file != null) {
                String fileSignature = FileUploadUtils.saveFileUploaded(file, "logs/update-share/" + environment.getProperty("server.servlet.context-path"));
                user.setSignature(fileSignature);
            }

            // check Data
            user.setModifiedBy(getUserAuth().getId());
            user.setIsActive(1);

            Boolean result = freedomMapper.updateUser(user);
            if (result) {
                // check user role
                if (user.getUserGroup().length > 0) {
                    insertSystemRoleUser(user.getUserGroup(), user.getId());
                }
                upsertUserWarehouse(user.getId(), user.getFreedomWarehouse());
                ensureUserCompanies(user.getId(), userId);
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/user-freedom/update",null,null,"User","User (Edit)","Edit",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user-freedom/update",line, error.toString(),"User","User (Edit)","Edit",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> editProfile(EditProfileRequest editProfileRequest, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // check Data
            User user = new User();
            user.setEmployeeId(editProfileRequest.getEmployeeId());
            user.setPhoto(editProfileRequest.getPhoto());
            user.setPhotoType(editProfileRequest.getPhotoType());
            user.setEmployeeName(editProfileRequest.getEmployeeName());
            user.setEmployeeGender(editProfileRequest.getEmployeeGender());
            user.setEmployeeDob(editProfileRequest.getEmployeeDob());
            user.setEmployeePhoneNumber(editProfileRequest.getEmployeePhoneNumber());
            user.setEmployeeEmail(editProfileRequest.getEmployeeEmail());
            user.setProvinceId(editProfileRequest.getProvinceId());
            user.setDistrictId(editProfileRequest.getDistrictId());
            user.setCommuneId(editProfileRequest.getCommuneId());
            user.setVillageId(editProfileRequest.getVillageId());
            user.setModifiedBy(getUserAuth().getId());
            user.setIsActive(1);

            Boolean result = freedomMapper.editProfileUser(user);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/user-freedom/edit-profile",null,null,"User","","Edit Profile",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user-freedom/edit-profile",line, error.toString(),"User","","Edit Profile",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<String> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userFreedomService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "User (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            if (id == 1) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("User Admin Cannot Deleted!", false));
            }

            freedomMapper.deleteUserLocationGroup(id);
            Boolean result = freedomMapper.deleteUser(id);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/user-freedom/delete/{id}",null,null,"User","User (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user-freedom/delete/{id}",line, error.toString(),"User","User (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<UserResponse> me(HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userFreedomService.getUserAuth().getId();

            List<UserResponse> userList = freedomMapper.getOneByUserId(userId);

            if (userList != null && userList.size() > 0) {
                userList.get(0).setFreedomWarehouse(freedomMapper.getUserLocationGroups(userId));
                userList.get(0).setModuleTypeList(roleMapper.getModule(userId));
                List<ModuleType> moduleTypeList = userList.get(0).getModuleTypeList();

                if (moduleTypeList != null && moduleTypeList.size() > 0) {
                    for (ModuleType moduleType : moduleTypeList) {
                        Long moduleTypeId = moduleType.getId();
                        moduleType.setModuleList(moduleMapper.getOneByUserId(moduleTypeId, userId));
                    }
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user-freedom/me",null,null,"User","","Me",1,"Success",startDuration,endDuration, httpServletRequest);
            assert userList != null;
            return ResponseMessageUtils.makeSuccessResponse(userList.get(0));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user-freedom/me",line, error.toString(),"User","","Me",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<String> changePassword(String oldPassword, String newPassword, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            List<User> userList = freedomMapper.getOneUser(getUserAuth().getId());
            User user = userList.get(0);

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
            } else {
                return ResponseMessageUtils.makeResponse(false, "The password you entered is incorrect.");
            }

            user.setModifiedBy(getUserAuth().getId());
            user.setStatusLogin(2L);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user-freedom/change-password",null,null,"User","","Change Password",1,"Success",startDuration,endDuration, httpServletRequest);
            Boolean result = freedomMapper.updateUser(user);
            if (result) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/user-freedom/change-password",line, error.toString(),"User","","Change Password",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public User getUserAuth() {
        try {
            if (UserAuthSession.getUserAuth() != null) {
                List<User> userList = freedomMapper.getOneByUsername(UserAuthSession.getUserAuth().getUsername());
                return userList.get(0);
            } else {
                return new User();
            }
        } catch (Exception err) {
            return null;
        }
    }

    private void insertSystemRoleUser(String[] groupId, Long userId) {
        if (groupId.length > 0) {
            freedomMapper.deleteSystemRoleUser(userId);
            for (String s : groupId) {
                freedomMapper.insertSystemRoleUser(userId, s.replace("\"", ""));
            }
        }
    }

    private void upsertUserWarehouse(Long userId, Long[] freedomWarehouse) {
        freedomMapper.deleteUserLocationGroup(userId);
        if (freedomWarehouse != null && freedomWarehouse.length > 0) {
            for (Long locationGroupId : freedomWarehouse) {
                if (locationGroupId != null) {
                    freedomMapper.insertUserLocationGroup(userId, locationGroupId);
                }
            }
        }
    }

    private void ensureUserCompanies(Long userId, Long fallbackUserId) {
        List<Long> existingCompanyIds = freedomMapper.getUserCompanyIds(userId);
        if (existingCompanyIds != null && !existingCompanyIds.isEmpty()) {
            return;
        }

        List<Long> companyIds = freedomMapper.getUserCompanyIds(fallbackUserId);
        if (companyIds == null || companyIds.isEmpty()) {
            companyIds = freedomMapper.getActiveCompanyIds();
        }

        if (companyIds != null && !companyIds.isEmpty()) {
            for (Long companyId : companyIds) {
                if (companyId != null) {
                    freedomMapper.insertUserCompany(userId, companyId);
                }
            }
        }
    }

    private Long[] toLongArray(List<Long> values) {
        if (values == null || values.isEmpty()) {
            return new Long[0];
        }
        return values.toArray(new Long[0]);
    }

}
