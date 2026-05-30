package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.freedom.FreedomMapper;
import com.ut.nlSystemAPi.mapper.primary.PriceTypeMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.entity.PriceType.PriceType;
import com.ut.nlSystemAPi.model.request.PriceType.OrderingRequest;
import com.ut.nlSystemAPi.model.request.PriceType.PriceTypeRequest;
import com.ut.nlSystemAPi.model.request.PriceType.PriceTypeUpdateRequest;
import com.ut.nlSystemAPi.model.response.PriceType.PriceTypeResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.PriceTypeService;
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
public class PriceTypeServiceImpl implements PriceTypeService {

    @Autowired
    private PriceTypeMapper priceTypeMapper;

    @Autowired
    private FreedomMapper freedomMapper;

    @Autowired
    private PermissionMapper permissionMapper;

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
            if (permissionMapper.checkPermission(userId, "Price Type (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(priceTypeMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<PriceTypeResponse> responses = priceTypeMapper.getList(filter, userId);
            if (!responses.isEmpty()) {
                for (PriceTypeResponse response : responses) {
                    Long checkPos = priceTypeMapper.checkPos(response.getId());
                    if (checkPos > 0) {
                        response.setApplyTo("POS");
                    } else {
                        response.setApplyTo("");
                    }
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-type/list", null, null, "Price Type", "Price Type (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-type/list", line, error.toString(), "Price Type", "Price Type (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Price Type (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            List<PriceTypeResponse> responses = priceTypeMapper.getOne(id, userId);

            if (!responses.isEmpty()) {
                for (PriceTypeResponse response : responses) {
                    Long checkPos = priceTypeMapper.checkPos(response.getId());
                    if (checkPos > 0) {
                        response.setApplyTo("POS");
                    } else {
                        response.setApplyTo("");
                    }
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-type/find/{id}", null, null, "Price Type", "Price Type (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-type/find/{id}", line, error.toString(), "Price Type", "Price Type (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(PriceTypeRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Price Type (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if(priceTypeMapper.checkDuplicate(request.getName(), request.getCompanyId(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            // Check Data
            PriceType priceType = new PriceType();
            priceType.setCompanyId(request.getCompanyId());
            priceType.setName(request.getName());
            priceType.setOrdering(request.getOrdering());
            priceType.setIsShowCatalogue(request.getIsShowCatalogue());
            priceType.setCreatedBy(userId);
            priceType.setIsActive(1);
            Boolean result = priceTypeMapper.insert(priceType);

            if (result) {
                freedomMapper.insertPriceType(toFreedomPriceTypeMap(priceType));
                priceTypeMapper.insertPriceTypeCompany(priceType);
                freedomMapper.insertPriceTypeCompany(toFreedomPriceTypeCompanyMap(priceType));

                if (request.getApplyTo() != null){
                    priceTypeMapper.archivePriceTypePos(request.getCompanyId());
                    freedomMapper.archivePriceTypePos(request.getCompanyId());
                    if (request.getApplyTo() == 1) {
                        priceTypeMapper.insertPriceTypePos(priceType, userId);
                        freedomMapper.insertPriceTypePos(toFreedomPriceTypePosMap(priceType, userId));
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/price-type/add", null, null, "Price Type", "Price Type (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-type/add", line, error.toString(), "Price Type", "Price Type (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(PriceTypeUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Price Type (edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if(priceTypeMapper.checkDuplicate(request.getName(), request.getCompanyId(), request.getId()) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            PriceType priceType = new PriceType();
            priceType.setId(request.getId());
            priceType.setCompanyId(request.getCompanyId());
            priceType.setName(request.getName());
            priceType.setOrdering(request.getOrdering());
            priceType.setIsShowCatalogue(request.getIsShowCatalogue());
            priceType.setModifiedBy(userId);

            Boolean result = priceTypeMapper.update(priceType);

            if (result) {
                freedomMapper.updatePriceType(toFreedomPriceTypeMap(priceType));

                priceTypeMapper.deletePriceTypeCompany(request.getId());
                freedomMapper.deletePriceTypeCompany(request.getId());

                priceTypeMapper.insertPriceTypeCompany(priceType);
                freedomMapper.insertPriceTypeCompany(toFreedomPriceTypeCompanyMap(priceType));

                if (request.getApplyTo() != null){
                    priceTypeMapper.archivePriceTypePos(request.getCompanyId());
                    freedomMapper.archivePriceTypePos(request.getCompanyId());
                    if (request.getApplyTo() == 1) {
                        priceTypeMapper.insertPriceTypePos(priceType, userId);
                        freedomMapper.insertPriceTypePos(toFreedomPriceTypePosMap(priceType, userId));
                    }
                }

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/price-type/update", null, null, "Price Type", "Price Type (edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/price-type/update", line, error.toString(), "Price Type", "Price Type (edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Price Type (delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = priceTypeMapper.delete(id, userId);

            if (result) {
                freedomMapper.deletePriceType(id, userId);
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/price-type/delete/{id}",null,null,"Price Type","Price Type (delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-type/delete/{id}",line, error.toString(),"Price Type","Price Type (delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> ordering(OrderingRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Price Type (edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            PriceType priceType = new PriceType();
            priceType.setId(request.getId());
            priceType.setOrdering(request.getOrdering());
            Boolean result = priceTypeMapper.ordering(priceType);
            if (result) {
                freedomMapper.orderingPriceType(priceType.getId(), priceType.getOrdering());
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/price-type/ordering", null, null, "Price Type", "Price Type (edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/price-type/ordering", line, error.toString(), "Price Type", "Price Type (edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    private Map<String, Object> toFreedomPriceTypeMap(PriceType priceType) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", priceType.getId());
        map.put("name", priceType.getName());
        map.put("ordering", priceType.getOrdering());
        map.put("isShowCatalogue", priceType.getIsShowCatalogue());
        map.put("createdBy", priceType.getCreatedBy());
        map.put("modifiedBy", priceType.getModifiedBy());
        map.put("isActive", priceType.getIsActive());
        return map;
    }

    private Map<String, Object> toFreedomPriceTypeCompanyMap(PriceType priceType) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", priceType.getCompanyId());
        map.put("priceTypeId", priceType.getId());
        return map;
    }

    private Map<String, Object> toFreedomPriceTypePosMap(PriceType priceType, Long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", priceType.getCompanyId());
        map.put("priceTypeId", priceType.getId());
        map.put("createdBy", userId);
        return map;
    }


}
