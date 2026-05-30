package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.BannerMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Banner.Banner;
import com.ut.nlSystemAPi.model.request.Banner.BannerRequest;
import com.ut.nlSystemAPi.model.request.Banner.BannerUpdateRequest;
import com.ut.nlSystemAPi.model.response.Banner.BannerResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.BannerService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

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
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Banner (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(bannerMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<BannerResponse> responses = bannerMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/banner/list", null, null, "Banner", "Banner (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/banner/list", line, error.toString(), "Banner", "Banner (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Banner (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            List<BannerResponse> responses = bannerMapper.getOne(id);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/banner/find/{id}", null, null, "Banner", "Banner (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/banner/find/{id}", line, error.toString(), "Banner", "Banner (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(BannerRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Banner (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Banner banner = new Banner();
            banner.setImageUrl(request.getImageUrl());
            banner.setImageName(request.getImageName());
            banner.setMainDescription(request.getMainDescription());
            banner.setSubDescription(request.getSubDescription());
            banner.setMainDescriptionKh(request.getMainDescriptionKh());
            banner.setSubDescriptionKh(request.getSubDescriptionKh());
            applyBannerTarget(banner, request.getPromotionId(), request.getProductId());
            banner.setCreatedBy(userId);
            banner.setIsActive(1);

            Boolean result = bannerMapper.insert(banner);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/banner/add", null, null, "Banner", "Banner (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/banner/add", line, error.toString(), "Banner", "Banner (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(BannerUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Banner (Edit)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Banner banner = new Banner();
            banner.setId(request.getId());
            banner.setImageUrl(request.getImageUrl());
            banner.setImageName(request.getImageName());
            banner.setMainDescription(request.getMainDescription());
            banner.setSubDescription(request.getSubDescription());
            banner.setMainDescriptionKh(request.getMainDescriptionKh());
            banner.setSubDescriptionKh(request.getSubDescriptionKh());
            applyBannerTarget(banner, request.getPromotionId(), request.getProductId());
            banner.setModifiedBy(userId);

            Boolean result = bannerMapper.update(banner);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/banner/update", null, null, "Banner", "Banner (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/banner/update", line, error.toString(), "Banner", "Banner (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Banner (Delete)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Boolean result = bannerMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/banner/delete/{id}", null, null, "Banner", "Banner (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/banner/delete/{id}", line, error.toString(), "Banner", "Banner (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private void applyBannerTarget(Banner banner, Long promotionId, Long productId) {
        Long normalizedPromotionId = normalizeId(promotionId);
        Long normalizedProductId = normalizeId(productId);

        if (normalizedProductId != null) {
            banner.setProductId(normalizedProductId);
            banner.setPromotionId(null);
            return;
        }

        if (normalizedPromotionId != null) {
            banner.setPromotionId(normalizedPromotionId);
            banner.setProductId(null);
        }
    }

    private Long normalizeId(Long id) {
        return id != null && id > 0 ? id : null;
    }

}
