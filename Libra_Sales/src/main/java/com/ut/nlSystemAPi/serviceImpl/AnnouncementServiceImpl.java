package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.AnnouncementMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.Announcement.Announcement;
import com.ut.nlSystemAPi.model.request.Announcement.AnnouncementRequest;
import com.ut.nlSystemAPi.model.request.Announcement.AnnouncementUpdateRequest;
import com.ut.nlSystemAPi.model.response.Announcement.AnnouncementResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.AnnouncementService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private static final Integer ANNOUNCEMENT_TYPE = 2;

    @Autowired
    private AnnouncementMapper announcementMapper;

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
            if (permissionMapper.checkPermission(userId, "Announcement (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(announcementMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<AnnouncementResponse> responses = announcementMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/list", null, null, "Announcement", "Announcement (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/list", line, error.toString(), "Announcement", "Announcement (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Announcement (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<AnnouncementResponse> responses = announcementMapper.getOne(id);
            if (!responses.isEmpty()) {
                for (AnnouncementResponse response : responses) {
                    response.setCustomers(announcementMapper.getCustomers(response.getId()));
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/find/{id}", null, null, "Announcement", "Announcement (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/find/{id}", line, error.toString(), "Announcement", "Announcement (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    @Transactional
    public ResponseMessage<BaseResult> insert(AnnouncementRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Announcement (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Announcement announcement = new Announcement();
            announcement.setTitle(request.getTitle());
            announcement.setImage(request.getImage());
            announcement.setImageUrl(request.getImageUrl());
            announcement.setContent(request.getContent());
            announcement.setSchedule(request.getSchedule());
            announcement.setIsPublic(request.getIsPublic() == null ? 0 : request.getIsPublic());
            announcement.setType(ANNOUNCEMENT_TYPE);
            if (hasRecipients(request)) {
                announcement.setUserType(1);
            } else {
                announcement.setUserType(0);
            }
            announcement.setCreatedBy(userId);
            announcement.setIsActive(1);

            Boolean result = announcementMapper.insert(announcement);
            if (result) {
                saveUserApps(announcement.getId(), request, userId);

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/announcement/add", null, null, "Announcement", "Announcement (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/add", line, error.toString(), "Announcement", "Announcement (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    @Transactional
    public ResponseMessage<BaseResult> update(AnnouncementUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Announcement (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Announcement announcement = new Announcement();
            announcement.setId(request.getId());
            announcement.setTitle(request.getTitle());
            announcement.setImage(request.getImage());
            announcement.setImageUrl(request.getImageUrl());
            announcement.setContent(request.getContent());
            announcement.setSchedule(request.getSchedule());
            announcement.setIsPublic(request.getIsPublic());
            announcement.setType(ANNOUNCEMENT_TYPE);
            announcement.setModifiedBy(userId);

            Boolean result = announcementMapper.update(announcement);
            if (result) {
                if (hasRecipientInput(request)) {
                    announcementMapper.deleteUserApps(announcement.getId());
                    announcementMapper.deleteNotifications(announcement.getId(), userId);
                    saveUserApps(announcement.getId(), request, userId);
                }

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/announcement/update", null, null, "Announcement", "Announcement (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/update", line, error.toString(), "Announcement", "Announcement (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    @Transactional
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Announcement (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = announcementMapper.delete(id, userId);
            if (result) {
                announcementMapper.deleteUserApps(id);
                announcementMapper.deleteNotifications(id, userId);

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/announcement/delete/{id}", null, null, "Announcement", "Announcement (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/delete/{id}", line, error.toString(), "Announcement", "Announcement (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private boolean hasRecipients(AnnouncementRequest request) {
        return (request.getCustomerIds() != null && !request.getCustomerIds().isEmpty())
                || (request.getUserAppIds() != null && !request.getUserAppIds().isEmpty());
    }

    private boolean hasRecipientInput(AnnouncementRequest request) {
        return request.getCustomerIds() != null || request.getUserAppIds() != null;
    }

    private void saveUserApps(Long announcementId, AnnouncementRequest request, Long createdBy) {
        if (request.getCustomerIds() != null) {
            if (!request.getCustomerIds().isEmpty()) {
                announcementMapper.insertUserAppsByCustomerIds(announcementId, request.getCustomerIds());
                announcementMapper.insertNotificationsByCustomerIds(announcementId, request.getCustomerIds(), createdBy);
            }
            return;
        }

        if (request.getUserAppIds() != null && !request.getUserAppIds().isEmpty()) {
            announcementMapper.insertUserAppsByUserAppIds(announcementId, request.getUserAppIds());
            announcementMapper.insertNotificationsByUserAppIds(announcementId, request.getUserAppIds(), createdBy);
        }
    }
}
