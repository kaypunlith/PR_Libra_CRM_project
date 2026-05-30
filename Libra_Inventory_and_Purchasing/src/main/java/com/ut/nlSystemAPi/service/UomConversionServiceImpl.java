package com.ut.nlSystemAPi.service;

import ch.qos.logback.core.joran.spi.ElementPath;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.freedom.FreedomMapper;
import com.ut.nlSystemAPi.mapper.primary.UomConversionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.OtherUomConversion;
import com.ut.nlSystemAPi.model.UomConversion;
import com.ut.nlSystemAPi.model.Uoms;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.UomConverRequest.UomConversionRequest;
import com.ut.nlSystemAPi.model.request.Login.UomConverRequest.UomConversionUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.UomsRequest;
import com.ut.nlSystemAPi.model.request.Login.UomsUpdateRequest;
import com.ut.nlSystemAPi.model.response.UomConversion.UomConversionList;
import com.ut.nlSystemAPi.model.response.UomConversion.UomConversionResponse;
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
public class UomConversionServiceImpl implements UomConversionService {
    @Autowired
    private UomConversionMapper uomConversionMapper;

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
            pagination.setTotal(uomConversionMapper.countList(filter));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<UomConversionResponse> uomConversionResponses = uomConversionMapper.getList(filter);
            System.out.println(uomConversionResponses);
            //! find small uom
            if (uomConversionResponses.size() > 0){
                for(int i = 0; i < uomConversionResponses.size(); i++){
                    String smallUom = uomConversionMapper.getSmallUomById(uomConversionResponses.get(i).getId());
                    uomConversionResponses.get(i).setSmallUomName(smallUom);
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/uom conversion/list", null, null, "uom conversion", "uom conversion(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", uomConversionResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/uom conversion/list", line, error.toString(), "uom conversion", "uom conversion(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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

            List<UomConversionResponse> uomConversionResponses = uomConversionMapper.getOne(id);

            if (uomConversionResponses.size() > 0){
                for(int i = 0; i < uomConversionResponses.size(); i++){
                   uomConversionResponses.get(i).setOtherUoms(uomConversionMapper.getSmallUomDetail(uomConversionResponses.get(i).getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/uom conversion/find/{id}", null, null, "System uom conversion", "System uom conversion (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", uomConversionResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/uom conversion/find/{id}", line, error.toString(), "System uom conversion", "System uom conversion (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(UomConversionRequest uomConversionRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            // if (permissionMapper.checkPermission(userId, "Employee (Add)") == 0) {
            //     return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            // }

            // Check Data
            UomConversion uomConversion = new UomConversion();
            uomConversion.setMainUom(uomConversionRequest.getMainUom());
            uomConversion.setSmallUom(uomConversionRequest.getSmallUom());
            uomConversion.setValue(uomConversionRequest.getValue());
            uomConversion.setCreatedBy(userId);
            uomConversion.setIsSmallUom(1L);
            uomConversion.setIsActive(1);
            Boolean result = uomConversionMapper.insert(uomConversion);
            if (result) {
                freedomMapper.insertUomConversion(toFreedomUomConversionMap(uomConversion));
                List<UomConversionList> uomConversionLists = uomConversionRequest.getOtherUoms();
                if (!uomConversionLists.isEmpty()) {
                    for (UomConversionList uomConversionList : uomConversionLists) {
                        OtherUomConversion otherUomConversion = new OtherUomConversion(); // Create new model for insert into table
                        otherUomConversion.setMainUom(uomConversion.getMainUom());
                        otherUomConversion.setOtherUom(uomConversionList.getOtherUom());
                        otherUomConversion.setValue(uomConversionList.getValue());
                        otherUomConversion.setCreatedBy(userId);
                        otherUomConversion.setIsSmallUom(0L);
                        otherUomConversion.setIsActive(1);
                        uomConversionMapper.insertOther(otherUomConversion);
                        freedomMapper.insertUomConversion(toFreedomOtherUomConversionMap(otherUomConversion));
                    }
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/uom conversion/add", null, null, "uom conversion", "uom conversion (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            // System Activity
            activityLogService.insert("/uom conversion/update", line, error.toString(), "uom conversion", "uom conversion (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(UomConversionUpdateRequest uomConversionUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
             Long userId = userService.getUserAuth().getId();
             UomConversion uomConversion = new UomConversion();
             uomConversion.setId(uomConversionUpdateRequest.getId());
             uomConversion.setMainUom(uomConversionUpdateRequest.getMainUom());
             uomConversion.setSmallUom(uomConversionUpdateRequest.getSmallUom());
             uomConversion.setValue(uomConversionUpdateRequest.getValue());
             uomConversion.setModifiedBy(userId);
             Boolean result = uomConversionMapper.update(uomConversion);
            if (result) {
                freedomMapper.updateUomConversion(toFreedomUomConversionMap(uomConversion));
                List<UomConversionList> uomConversionLists = uomConversionUpdateRequest.getOtherUoms();
                if (!uomConversionLists.isEmpty()) {
                    uomConversionMapper.deleteOther(uomConversionUpdateRequest.getId(), userId);
                    freedomMapper.archiveOtherUomConversions(uomConversionUpdateRequest.getId(), userId);
                    for (UomConversionList uomConversionList : uomConversionLists) {
                        OtherUomConversion otherUomConversion = new OtherUomConversion(); // Create new model for insert into table
                        otherUomConversion.setMainUom(uomConversion.getMainUom());
                        otherUomConversion.setOtherUom(uomConversionList.getOtherUom());
                        otherUomConversion.setValue(uomConversionList.getValue());
                        otherUomConversion.setCreatedBy(userId);
                        otherUomConversion.setIsSmallUom(0L);
                        otherUomConversion.setIsActive(1);
                        uomConversionMapper.insertOther(otherUomConversion);
                        freedomMapper.insertUomConversion(toFreedomOtherUomConversionMap(otherUomConversion));
                    }
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/uom conversion/add", null, null, "uom conversion", "uom conversion (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            // System Activity
            activityLogService.insert("/uom conversion/update", line, error.toString(), "uom conversion", "uom conversion (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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

            Boolean result = uomConversionMapper.delete(id, userId);
            if (result) {
                freedomMapper.archiveUomConversion(id, userId);
                uomConversionMapper.deleteOther(id, userId);
                freedomMapper.archiveOtherUomConversions(id, userId);
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/uom conversion/delete/{id}",null,null,"uom conversion","uom conversion (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/uom conversion/delete/{id}",line, error.toString(),"uom conversion","uom conversion (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private Map<String, Object> toFreedomUomConversionMap(UomConversion uomConversion) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", uomConversion.getId());
        map.put("mainUom", uomConversion.getMainUom());
        map.put("toUom", uomConversion.getSmallUom());
        map.put("value", uomConversion.getValue());
        map.put("createdBy", uomConversion.getCreatedBy());
        map.put("modifiedBy", uomConversion.getModifiedBy());
        map.put("isSmallUom", uomConversion.getIsSmallUom());
        map.put("isActive", uomConversion.getIsActive());
        return map;
    }

    private Map<String, Object> toFreedomOtherUomConversionMap(OtherUomConversion otherUomConversion) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", otherUomConversion.getId());
        map.put("mainUom", otherUomConversion.getMainUom());
        map.put("toUom", otherUomConversion.getOtherUom());
        map.put("value", otherUomConversion.getValue());
        map.put("createdBy", otherUomConversion.getCreatedBy());
        map.put("modifiedBy", otherUomConversion.getModifiedBy());
        map.put("isSmallUom", otherUomConversion.getIsSmallUom());
        map.put("isActive", otherUomConversion.getIsActive());
        return map;
    }

}
