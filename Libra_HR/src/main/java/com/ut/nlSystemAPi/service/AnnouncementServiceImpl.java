package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.AnnouncementMapper;
import com.ut.nlSystemAPi.model.Announcement;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.AnnouncementRequest;
import com.ut.nlSystemAPi.model.request.AnnouncementUpdateRequest;
import com.ut.nlSystemAPi.model.response.AnnouncementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private static final Integer ANNOUNCEMENT_TYPE = 1;

    @Autowired
    private AnnouncementMapper announcementMapper;

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
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(announcementMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<AnnouncementResponse> responses = announcementMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/list", null, null, "Announcement", "Announcement", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/list", line, error.toString(), "Announcement", "Announcement", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            List<AnnouncementResponse> responses = announcementMapper.getOne(id);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/find/{id}", null, null, "Announcement", "Announcement", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/find/{id}", line, error.toString(), "Announcement", "Announcement", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(AnnouncementRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            Announcement announcement = new Announcement();
            announcement.setTitle(request.getTitle());
            announcement.setFileName(request.getFileName());
            announcement.setFileUrl(request.getFileUrl());
            announcement.setContent(request.getContent());
            announcement.setSchedule(request.getSchedule());
            announcement.setIsPublic(request.getIsPublic() == null ? 0 : request.getIsPublic());
            announcement.setType(ANNOUNCEMENT_TYPE);
            announcement.setCreatedBy(userId);
            announcement.setIsActive(1);

            Boolean result = announcementMapper.insert(announcement);
            if (Boolean.TRUE.equals(result)) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/announcement/add", null, null, "Announcement", "Announcement", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/add", line, error.toString(), "Announcement", "Announcement", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(AnnouncementUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            Announcement announcement = new Announcement();
            announcement.setId(request.getId());
            announcement.setTitle(request.getTitle());
            announcement.setFileName(request.getFileName());
            announcement.setFileUrl(request.getFileUrl());
            announcement.setContent(request.getContent());
            announcement.setSchedule(request.getSchedule());
            announcement.setIsPublic(request.getIsPublic());
            announcement.setType(ANNOUNCEMENT_TYPE);
            announcement.setModifiedBy(userId);

            Boolean result = announcementMapper.update(announcement);
            if (Boolean.TRUE.equals(result)) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/announcement/update", null, null, "Announcement", "Announcement", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/update", line, error.toString(), "Announcement", "Announcement", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            Boolean result = announcementMapper.delete(id, userId);
            if (Boolean.TRUE.equals(result)) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/announcement/delete/{id}", null, null, "Announcement", "Announcement", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/announcement/delete/{id}", line, error.toString(), "Announcement", "Announcement", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
