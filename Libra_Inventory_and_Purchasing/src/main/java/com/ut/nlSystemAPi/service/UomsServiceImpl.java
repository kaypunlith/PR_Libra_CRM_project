package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.freedom.FreedomMapper;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.UomsRequest;
import com.ut.nlSystemAPi.model.request.Login.UomsUpdateRequest;
import com.ut.nlSystemAPi.model.response.Uoms.UomsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UomsServiceImpl implements UomsService {
    @Autowired
    private UomsMapper uomsMapper;

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
//            if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(uomsMapper.countList(filter));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<UomsResponse> responses = uomsMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/uoms/list", null, null, "Uoms", "Uoms(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/uoms/list", line, error.toString(), "Uoms", "Uoms(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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

            List<UomsResponse> responses = uomsMapper.getOne(id);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/uoms/find/{id}", null, null, "System uoms", "System uoms (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/uoms/find/{id}", line, error.toString(), "System uoms", "System uoms (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(UomsRequest uomsRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Employee (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            // Check Duplicate
            if(uomsMapper.checkDuplicate(uomsRequest.getName(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            // Check Data
            Uoms uoms = new Uoms();
            uoms.setType(uomsRequest.getType());
            uoms.setName(uomsRequest.getName());
            uoms.setAbbr(uomsRequest.getAbbr());
            uoms.setDescription(uomsRequest.getDescription());
            uoms.setCreatedBy(userId);
            uoms.setIsActive(1);
            Boolean result = uomsMapper.insert(uoms);

            if (result) {
                freedomMapper.insertUom(toFreedomUomMap(uoms));
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/uoms/add", null, null, "Uoms", "Uoms (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/uoms/add", line, error.toString(), "Uoms", "Uoms (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> update(UomsUpdateRequest uomsUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            //           if (permissionMapper.checkPermission(userId, "Employee (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            // Check Duplicate
            if(uomsMapper.checkDuplicate(uomsUpdateRequest.getName(), uomsUpdateRequest.getId()) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            Uoms uoms = new Uoms();
            uoms.setId(uomsUpdateRequest.getId());
            uoms.setType(uomsUpdateRequest.getType());
            uoms.setName(uomsUpdateRequest.getName());
            uoms.setAbbr(uomsUpdateRequest.getAbbr());
            uoms.setDescription(uomsUpdateRequest.getDescription());
            uoms.setModifiedBy(userId);
            uoms.setIsActive(1);
            Boolean result = uomsMapper.update(uoms);

            if (result) {
                freedomMapper.updateUom(toFreedomUomMap(uoms));
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/uoms/update", null, null, "Uoms", "Uoms (Update)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/uoms/update", line, error.toString(), "Uoms", "Uoms (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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

            Boolean result = uomsMapper.delete(id, userId);
            if (result) {
                freedomMapper.deleteUom(id, userId);
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/uoms/delete/{id}",null,null,"Uoms","Uoms (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/uoms/delete/{id}",line, error.toString(),"Uoms","Uoms (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private Map<String, Object> toFreedomUomMap(Uoms uoms) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", uoms.getId());
        map.put("type", uoms.getType());
        map.put("name", uoms.getName());
        map.put("abbr", uoms.getAbbr());
        map.put("description", uoms.getDescription());
        map.put("createdBy", uoms.getCreatedBy());
        map.put("modifiedBy", uoms.getModifiedBy());
        map.put("isActive", uoms.getIsActive());
        return map;
    }


}
