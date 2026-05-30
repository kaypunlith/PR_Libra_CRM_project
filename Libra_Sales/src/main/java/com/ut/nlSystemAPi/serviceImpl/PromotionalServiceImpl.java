package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.PromotionalMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Promotional.Promotional;
import com.ut.nlSystemAPi.model.entity.Promotional.PromotionalDetail;
import com.ut.nlSystemAPi.model.entity.Promotional.PromotionalSubDetail;
import com.ut.nlSystemAPi.model.filter.PromotionalFilter;
import com.ut.nlSystemAPi.model.request.Promotional.PromotionalDetailRequest;
import com.ut.nlSystemAPi.model.request.Promotional.PromotionalRequest;
import com.ut.nlSystemAPi.model.request.Promotional.PromotionalSubDetailRequest;
import com.ut.nlSystemAPi.model.request.Promotional.PromotionalUpdateRequest;
import com.ut.nlSystemAPi.model.response.Promotional.PromotionalDetailResponse;
import com.ut.nlSystemAPi.model.response.Promotional.PromotionalResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.PromotionalService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class PromotionalServiceImpl implements PromotionalService {

    @Autowired
    private PromotionalMapper promotionalMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(PromotionalFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Promotional (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(promotionalMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<PromotionalResponse> responses = promotionalMapper.getList(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/promotional/list", null, null, "Promotional", "Promotional (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/promotional/list", line, error.toString(), "Promotional", "Promotional (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Promotional (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<PromotionalResponse> responses = promotionalMapper.getOne(id);
            if (!responses.isEmpty()) {
                List<PromotionalDetailResponse> details = promotionalMapper.getDetails(id);
                if (details != null && !details.isEmpty()) {
                    for (PromotionalDetailResponse detail : details) {
                        detail.setSubDetails(promotionalMapper.getSubDetails(detail.getId()));
                    }
                }
                responses.get(0).setDetails(details);
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/promotional/find/{id}", null, null, "Promotional", "Promotional (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/promotional/find/{id}", line, error.toString(), "Promotional", "Promotional (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(PromotionalRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Promotional (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Promotional promotional = new Promotional();
            promotional.setTitle(request.getTitle());
            promotional.setDescription(request.getDescription());
            promotional.setPromotionType(request.getPromotionType());
            promotional.setBranchId(request.getBranchId());
            promotional.setStartDate(request.getStartDate());
            promotional.setEndDate(request.getEndDate());
            promotional.setApplyTo(request.getApplyTo());
            promotional.setCreatedBy(userId);
            promotional.setIsActive(2);
            Boolean result = promotionalMapper.insert(promotional);

            if (result) {
                if (request.getDetails() != null && !request.getDetails().isEmpty()) {
                    for (PromotionalDetailRequest detailRequest : request.getDetails()) {
                        PromotionalDetail detail = new PromotionalDetail();
                        detail.setPromotionalId(promotional.getId());
                        detail.setUomId(detailRequest.getUomId());
                        detail.setProductId(detailRequest.getProductId());
                        detail.setQtyFree(detailRequest.getQtyFree());
                        detail.setDiscountQty(detailRequest.getDiscountQty());
                        detail.setDiscountPercent(detailRequest.getDiscountPercent());
                        detail.setDiscountAmount(detailRequest.getDiscountAmount());
                        detail.setPrice(detailRequest.getSpecialPrice());
                        promotionalMapper.insertDetail(detail);

                        if (detailRequest.getSubDetails() != null && !detailRequest.getSubDetails().isEmpty()) {
                            for (PromotionalSubDetailRequest subDetailRequest : detailRequest.getSubDetails()) {
                                PromotionalSubDetail subDetail = new PromotionalSubDetail();
                                subDetail.setPromotionalDetailId(detail.getId());
                                subDetail.setProductId(subDetailRequest.getProductId());
                                subDetail.setUomId(subDetailRequest.getUomId());
                                subDetail.setQty(subDetailRequest.getQty());
                                promotionalMapper.insertSubDetail(subDetail);
                            }
                        }
                    }
                }

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/promotional/add", null, null, "Promotional", "Promotional (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/promotional/add", line, error.toString(), "Promotional", "Promotional (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(PromotionalUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Promotional (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<PromotionalResponse> existing = promotionalMapper.getOne(request.getId());
            if (existing.isEmpty()) {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Data Cannot Be Found.", false));
            }

            Promotional promotional = new Promotional();
            promotional.setId(request.getId());
            promotional.setTitle(request.getTitle());
            promotional.setDescription(request.getDescription());
            promotional.setPromotionType(request.getPromotionType());
            promotional.setBranchId(request.getBranchId());
            promotional.setStartDate(request.getStartDate());
            promotional.setEndDate(request.getEndDate());
            promotional.setApplyTo(request.getApplyTo());
            promotional.setModifiedBy(userId);
            Boolean result = promotionalMapper.update(promotional);

            if (result) {
                promotionalMapper.deleteSubDetails(request.getId());
                promotionalMapper.deleteDetails(request.getId());

                if (request.getDetails() != null && !request.getDetails().isEmpty()) {
                    for (PromotionalDetailRequest detailRequest : request.getDetails()) {
                        PromotionalDetail detail = new PromotionalDetail();
                        detail.setPromotionalId(request.getId());
                        detail.setUomId(detailRequest.getUomId());
                        detail.setProductId(detailRequest.getProductId());
                        detail.setQtyFree(detailRequest.getQtyFree());
                        detail.setDiscountQty(detailRequest.getDiscountQty());
                        detail.setDiscountPercent(detailRequest.getDiscountPercent());
                        detail.setDiscountAmount(detailRequest.getDiscountAmount());
                        detail.setPrice(detailRequest.getSpecialPrice());
                        promotionalMapper.insertDetail(detail);

                        if (detailRequest.getSubDetails() != null && !detailRequest.getSubDetails().isEmpty()) {
                            for (PromotionalSubDetailRequest subDetailRequest : detailRequest.getSubDetails()) {
                                PromotionalSubDetail subDetail = new PromotionalSubDetail();
                                subDetail.setPromotionalDetailId(detail.getId());
                                subDetail.setProductId(subDetailRequest.getProductId());
                                subDetail.setUomId(subDetailRequest.getUomId());
                                subDetail.setQty(subDetailRequest.getQty());
                                promotionalMapper.insertSubDetail(subDetail);
                            }
                        }
                    }
                }

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/promotional/update", null, null, "Promotional", "Promotional (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/promotional/update", line, error.toString(), "Promotional", "Promotional (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Promotional (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = promotionalMapper.delete(id, userId);

            if (result) {
                promotionalMapper.deleteSubDetails(id);
                promotionalMapper.deleteDetails(id);
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/promotional/delete/{id}", null, null, "Promotional", "Promotional (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/promotional/delete/{id}", line, error.toString(), "Promotional", "Promotional (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }
}
