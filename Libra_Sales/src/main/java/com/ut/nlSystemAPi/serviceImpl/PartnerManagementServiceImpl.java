package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PartnerManagementMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.PartnerManagement.PartnerManagement;
import com.ut.nlSystemAPi.model.request.PartnerManagement.PartnerManagementRequest;
import com.ut.nlSystemAPi.model.request.PartnerManagement.PartnerManagementUpdateRequest;
import com.ut.nlSystemAPi.model.response.PartnerManagement.PartnerManagementResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.PartnerManagementService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class PartnerManagementServiceImpl implements PartnerManagementService {

    @Autowired
    private PartnerManagementMapper partnerManagementMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, Long classId, Long typeNetworkId, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            System.out.println("userId "+userId);
//            if (permissionMapper.checkPermission(userId, "Partner Management (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(partnerManagementMapper.countList(filter, classId, typeNetworkId, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<PartnerManagementResponse> responses = partnerManagementMapper.getList(filter, classId, typeNetworkId, userId);
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/partner-management/list", null, null, "Partner Management", "Partner Management (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/partner-management/list", line, error.toString(), "Partner Management", "Partner Management (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Partner Management (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<PartnerManagementResponse> responses = partnerManagementMapper.getOne(id);
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/partner-management/find/{id}", null, null, "Partner Management", "Partner Management (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/partner-management/find/{id}", line, error.toString(), "Partner Management", "Partner Management (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(PartnerManagementRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Partner Management (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (partnerManagementMapper.checkDuplicateTel(request.getKeyContactTel(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Key Contact Tel", false));
            }

            PartnerManagement partnerManagement = toEntity(request);
            partnerManagement.setCreatedBy(userId);
            partnerManagement.setIsActive(1);
            Boolean result = partnerManagementMapper.insert(partnerManagement);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/partner-management/add", null, null, "Partner Management", "Partner Management (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/partner-management/add", line, error.toString(), "Partner Management", "Partner Management (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(PartnerManagementUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Partner Management (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (partnerManagementMapper.checkDuplicateTel(request.getKeyContactTel(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Key Contact Tel", false));
            }

            PartnerManagement partnerManagement = toEntity(request);
            partnerManagement.setId(request.getId());
            partnerManagement.setModifiedBy(userId);
            Boolean result = partnerManagementMapper.update(partnerManagement);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/partner-management/update", null, null, "Partner Management", "Partner Management (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/partner-management/update", line, error.toString(), "Partner Management", "Partner Management (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Partner Management (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = partnerManagementMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/partner-management/delete/{id}", null, null, "Partner Management", "Partner Management (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            }
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/partner-management/delete/{id}", line, error.toString(), "Partner Management", "Partner Management (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private PartnerManagement toEntity(PartnerManagementRequest request) {
        PartnerManagement partnerManagement = new PartnerManagement();
        partnerManagement.setPartnerAreaExpertise(request.getPartnerAreaExpertise());
        partnerManagement.setPartnerName(request.getPartnerName());
        partnerManagement.setKeyContactName(request.getKeyContactName());
        partnerManagement.setKeyContactTel(request.getKeyContactTel());
        partnerManagement.setKeyContactEmail(request.getKeyContactEmail());
        partnerManagement.setWhy(request.getWhy());
        partnerManagement.setTypeNetworkId(request.getTypeNetworkId());
        partnerManagement.setClassId(request.getClassId());
        partnerManagement.setIndustryId(request.getIndustryId());
        partnerManagement.setPositionId(request.getPositionId());
        partnerManagement.setOrigin(request.getOrigin());
        partnerManagement.setRefBy(request.getRefBy());
        partnerManagement.setAnnualReview(request.getAnnualReview());
        partnerManagement.setRelationship(request.getRelationship());
        partnerManagement.setPersonInChargeId(request.getEmployeeId());
        return partnerManagement;
    }
}
