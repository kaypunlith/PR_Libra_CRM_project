package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.freedom.FreedomMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.Users.UserList;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.response.Role.RoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class RoleFreedomServiceImpl implements RoleFreedomService {

    @Autowired
    private FreedomMapper freedomMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (freedomMapper.checkRoleFreedomPermission(userId, "System Role (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(freedomMapper.countRoleFreedomList(filter));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<RoleResponse> roleList = freedomMapper.getRoleFreedomList(filter);
            if (!roleList.isEmpty()) {
                for (RoleResponse role : roleList) {
                    Long id = role.getId();
                    role.setUserList(freedomMapper.getRoleFreedomUsers(id));

                    ModuleTypeFilter moduleTypeFilter = new ModuleTypeFilter();
                    moduleTypeFilter.setPage(0);
                    moduleTypeFilter.setRowsPerPage(10000);
                    List<ModuleType> moduleTypeList = freedomMapper.getRoleFreedomModuleTypes(moduleTypeFilter);
                    if (moduleTypeList != null) {
                        for (ModuleType moduleType : moduleTypeList) {
                            Long moduleTypeId = moduleType.getId();
                            moduleType.setModuleList(freedomMapper.getRoleFreedomModuleByRoleId(moduleTypeId, id));
                        }
                    }
                    role.setModuleTypeList(moduleTypeList);
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/role-freedom/list", null, null, "System Role Freedom", "System Role Freedom (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", roleList, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/role-freedom/list", line, error.toString(), "System Role Freedom", "System Role Freedom (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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
//            if (freedomMapper.checkRoleFreedomPermission(userId, "System Role (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            List<RoleResponse> roles = freedomMapper.getRoleFreedomOne(id);
            if (!roles.isEmpty()) {
                Long roleId = roles.get(0).getId();
                roles.get(0).setUserList(freedomMapper.getRoleFreedomUsers(roleId));

                ModuleTypeFilter moduleTypeFilter = new ModuleTypeFilter();
                moduleTypeFilter.setPage(0);
                moduleTypeFilter.setRowsPerPage(10000);
                List<ModuleType> moduleTypeList = freedomMapper.getRoleFreedomModuleTypes(moduleTypeFilter);
                if (moduleTypeList != null) {
                    for (ModuleType moduleType : moduleTypeList) {
                        Long moduleTypeId = moduleType.getId();
                        moduleType.setModuleList(freedomMapper.getRoleFreedomModuleByRoleId(moduleTypeId, roleId));
                    }
                }
                roles.get(0).setModuleTypeList(moduleTypeList);
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/role-freedom/find/{id}", null, null, "System Role Freedom", "System Role Freedom (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", roles, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/role-freedom/find/{id}", line, error.toString(), "System Role Freedom", "System Role Freedom (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(RoleData roleData, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (freedomMapper.checkRoleFreedomPermission(userId, "System Role (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            // Check Validation
            if (bindingResult.hasErrors()) {
                return ResponseMessageUtils.makeResponse(true, bindingResult);
            }

            // Check Required
            if (roleData.getName() == null || roleData.getName().isEmpty()) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Required", false));
            }

            // Check Duplicate
            if (freedomMapper.checkRoleFreedomDuplicate(roleData.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate", false));
            }

            // Check Data
            Role role = new Role();
            role.setName(roleData.getName());
            role.setCreatedBy(userId);
            role.setIsActive(1);

            Boolean result = freedomMapper.insertRoleFreedom(role);
            if (result) {
                // Insert User and Module
                if (roleData.getUserList() != null) {
                    insertRoleUser(roleData.getUserList(), role.getId());
                }
                if (roleData.getModuleList() != null) {
                    insertRolePermission(roleData.getModuleList(), role.getId());
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/role-freedom/add", null, null, "System Role Freedom", "System Role Freedom (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/role-freedom/add", line, error.toString(), "System Role Freedom", "System Role Freedom (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(RoleDataUpdate roleDataUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (freedomMapper.checkRoleFreedomPermission(userId, "System Role (Edit)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            // Check Validation
            if (bindingResult.hasErrors()) {
                return ResponseMessageUtils.makeResponse(true, bindingResult);
            }

            // Check Required field
            if (roleDataUpdate.getId() == null || roleDataUpdate.getId() == 0 || roleDataUpdate.getName() == null || roleDataUpdate.getName().isEmpty()) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Required", false));
            }

            // Check Duplicate
            if (freedomMapper.checkRoleFreedomDuplicate(roleDataUpdate.getName(), roleDataUpdate.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate", false));
            }

            // Check Data
            Role role = new Role();
            role.setId(roleDataUpdate.getId());
            role.setName(roleDataUpdate.getName());
            role.setIsActive(1);
            role.setModifiedBy(userService.getUserAuth().getId());

            Boolean result = freedomMapper.updateRoleFreedom(role);
            if (result) {
                // Insert User and Module
                insertRoleUser(roleDataUpdate.getUserList(), role.getId());
                insertRolePermission(roleDataUpdate.getModuleList(), role.getId());

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/role-freedom/update", null, null, "System Role Freedom", "System Role Freedom (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/role-freedom/update", line, error.toString(), "System Role Freedom", "System Role Freedom (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
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
//            if (freedomMapper.checkRoleFreedomPermission(userId, "System Role (Delete)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Boolean result = freedomMapper.deleteRoleFreedom(id);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/role-freedom/delete/{id}", null, null, "System Role Freedom", "System Role Freedom (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/role-freedom/delete/{id}", line, error.toString(), "System Role Freedom", "System Role Freedom (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> menu(HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            List<ModuleType> roleList = freedomMapper.getRoleFreedomModule(userId);

            if (roleList != null && roleList.size() > 0) {
                for (ModuleType moduleType : roleList) {
                    Long moduleTypeId = moduleType.getId();
                    moduleType.setModuleList(freedomMapper.getRoleFreedomModuleByUserId(moduleTypeId, userId));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/role-freedom/menu", null, null, "System Role Freedom", "", "", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", roleList, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/role-freedom/menu", line, error.toString(), "System Role Freedom", "", "", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private void insertRoleUser(List<UserList> userLists, Long roleId) {
        if (userLists != null) {
            freedomMapper.deleteRoleFreedomUser(roleId);
            for (UserList userList : userLists) {
                freedomMapper.insertRoleFreedomUser(roleId, userList.getUserId());
            }
        }
    }

    private void insertRolePermission(List<ModuleList> moduleLists, Long roleId) {
        if (moduleLists != null) {
            freedomMapper.deleteRoleFreedomPermission(roleId);
            for (ModuleList moduleList : moduleLists) {
                freedomMapper.insertRoleFreedomPermission(roleId, moduleList.getModuleId());
            }
        }
    }
}
