package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.freedom.FreedomMapper;
import com.ut.nlSystemAPi.mapper.primary.MarketMapper;
import com.ut.nlSystemAPi.mapper.primary.ModuleMapper;
import com.ut.nlSystemAPi.mapper.primary.ModuleTypeMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Market.Market;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.request.Market.MarketRequest;
import com.ut.nlSystemAPi.model.request.Market.MarketUpdateRequest;
import com.ut.nlSystemAPi.model.response.Market.MarketResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.MarketService;
import com.ut.nlSystemAPi.service.UserService;
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
public class MarketServiceImpl implements MarketService {

    @Autowired
    private MarketMapper marketMapper;

    @Autowired
    private FreedomMapper freedomMapper;

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
//            if (permissionMapper.checkPermission(userId, "Customer Market (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(marketMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<MarketResponse> responses = marketMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/market/list", null, null, "Customer Market", "Customer Market (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/market/list", line, error.toString(), "Customer Market", "Customer Market (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Customer Market (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            List<MarketResponse> responses = marketMapper.getOne(id);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/market/find/{id}", null, null, "Customer Market", "Customer Market (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/market/find/{id}", line, error.toString(), "Customer Market", "Customer Market (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(MarketRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Customer Market (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Market market = new Market();
            market.setName(request.getName());
            market.setZoneId(request.getZoneId());
            market.setCreatedBy(userId);
            market.setIsActive(1);
            Boolean result = marketMapper.insert(market);

            if (result) {
                freedomMapper.insertCustomerMarket(toFreedomCustomerMarketMap(market));
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/market/add", null, null, "Customer Market", "Customer Market (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/market/add", line, error.toString(), "Customer Market", "Customer Market (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(MarketUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Customer Market (Edit)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Market market = new Market();
            market.setId(request.getId());
            market.setName(request.getName());
            market.setZoneId(request.getZoneId());
            market.setModifiedBy(userId);
            Boolean result = marketMapper.update(market);

            if (result) {
                freedomMapper.updateCustomerMarket(toFreedomCustomerMarketMap(market));
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/market/update", null, null, "Customer Market", "Customer Market (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/market/update", line, error.toString(), "Customer Market", "Customer Market (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Customer Market (Delete)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Boolean result = marketMapper.delete(id, userId);
            if (result) {
                freedomMapper.deleteCustomerMarket(id, userId);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/market/delete/{id}", null, null, "Customer Market", "Customer Market (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/market/delete/{id}", line, error.toString(), "Customer Market", "Customer Market (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private Map<String, Object> toFreedomCustomerMarketMap(Market market) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", market.getId());
        map.put("name", market.getName());
        map.put("zoneId", market.getZoneId());
        map.put("createdBy", market.getCreatedBy());
        map.put("modifiedBy", market.getModifiedBy());
        map.put("isActive", market.getIsActive());
        return map;
    }
}
