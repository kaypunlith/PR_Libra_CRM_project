package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ModuleMapper;
import com.ut.nlSystemAPi.mapper.primary.ModuleTypeMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ZoneMapper;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.entity.Zone.Zone;
import com.ut.nlSystemAPi.model.entity.Zone.ZoneDetail;
import com.ut.nlSystemAPi.model.request.Zone.ZoneRequest;
import com.ut.nlSystemAPi.model.request.Zone.ZoneUpdateRequest;
import com.ut.nlSystemAPi.model.response.Zone.ZoneResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.UserService;
import com.ut.nlSystemAPi.service.ZoneService;
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
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneMapper zoneMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ModuleTypeMapper moduleTypeMapper;

    @Autowired
    private ModuleMapper moduleMapper;

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
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Customer Zone (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(zoneMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ZoneResponse> responses = zoneMapper.getList(filter);
            if (!responses.isEmpty()) {
                for (ZoneResponse response : responses) {
                    response.setPaths(zoneMapper.getDetails(response.getId()));
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/zone/list", null, null, "Customer Zone", "Customer Zone (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/zone/list", line, error.toString(), "Customer Zone", "Customer Zone (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Customer Zone (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            List<ZoneResponse> responses = zoneMapper.getOne(id);
            if (!responses.isEmpty()) {
                for (ZoneResponse response : responses) {
                    response.setPaths(zoneMapper.getDetails(response.getId()));
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/zone/find/{id}", null, null, "Customer Zone", "Customer Zone (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/zone/find/{id}", line, error.toString(), "Customer Zone", "Customer Zone (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ZoneRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Customer Zone (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Zone zone = new Zone();
            zone.setName(request.getName());
            zone.setLats(request.getLats());
            zone.setLongs(request.getLongs());
            zone.setRadius(request.getRadius());
            zone.setCreatedBy(userId);
            zone.setIsActive(1);
            Boolean result = zoneMapper.insert(zone);

            if (result) {
                if (request.getPaths() != null && !request.getPaths().isEmpty()) {
                    ZoneDetail detail = new ZoneDetail();
                    for (int i = 0; i < request.getPaths().size(); i++) {
                        detail.setCustomerZoneId(zone.getId());
                        detail.setLats(request.getPaths().get(i).getLats());
                        detail.setLongs(request.getPaths().get(i).getLongs());
                        zoneMapper.insertDetail(detail);
                    }
                }

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/zone/add", null, null, "Customer Zone", "Customer Zone (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/zone/add", line, error.toString(), "Customer Zone", "Customer Zone (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(ZoneUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Customer Zone (Edit)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Zone zone = new Zone();
            zone.setId(request.getId());
            zone.setName(request.getName());
            zone.setLats(request.getLats());
            zone.setLongs(request.getLongs());
            zone.setRadius(request.getRadius());
            zone.setModifiedBy(userId);
            Boolean result = zoneMapper.update(zone);

            if (result) {
                if (request.getPaths() != null && !request.getPaths().isEmpty()) {
                    ZoneDetail detail = new ZoneDetail();
                    zoneMapper.deleteDetails(zone.getId());
                    for (int i = 0; i < request.getPaths().size(); i++) {
                        detail.setCustomerZoneId(zone.getId());
                        detail.setLats(request.getPaths().get(i).getLats());
                        detail.setLongs(request.getPaths().get(i).getLongs());
                        zoneMapper.insertDetail(detail);
                    }
                }

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/zone/update", null, null, "Customer Zone", "Customer Zone (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/zone/update", line, error.toString(), "Customer Zone", "Customer Zone (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Customer Zone (Delete)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Boolean result = zoneMapper.delete(id, userId);
            if (result) {
                zoneMapper.deleteDetails(id);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/zone/delete/{id}", null, null, "Customer Zone", "Customer Zone (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/zone/delete/{id}", line, error.toString(), "Customer Zone", "Customer Zone (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private Map<String, Object> toFreedomCustomerZoneMap(Zone zone) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", zone.getId());
        map.put("name", zone.getName());
        map.put("lats", zone.getLats());
        map.put("longs", zone.getLongs());
        map.put("radius", zone.getRadius());
        map.put("createdBy", zone.getCreatedBy());
        map.put("modifiedBy", zone.getModifiedBy());
        map.put("isActive", zone.getIsActive());
        return map;
    }

    private Map<String, Object> toFreedomCustomerZoneDetailMap(ZoneDetail detail) {
        Map<String, Object> map = new HashMap<>();
        map.put("zoneId", detail.getCustomerZoneId());
        map.put("lats", detail.getLats());
        map.put("longs", detail.getLongs());
        return map;
    }
}
