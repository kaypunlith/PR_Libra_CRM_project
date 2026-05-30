package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ProjectEstimationColorMapper;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.ProjectEstimationColor.ProjectEstimationColor;
import com.ut.nlSystemAPi.model.filter.ProjectEstimationFilter;
import com.ut.nlSystemAPi.model.request.ProjectEstimationColor.ProjectEstimationPercentSettingColorRequest;
import com.ut.nlSystemAPi.model.request.ProjectEstimationColor.ProjectEstimationPercentSettingColorUpdateRequest;
import com.ut.nlSystemAPi.model.response.ProjectEstimationColor.ProjectEstimationColorResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.ProjectEstimationColorService;
import com.ut.nlSystemAPi.service.UserService;
import com.ut.nlSystemAPi.model.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class ProjectEstimationColorServiceImpl implements ProjectEstimationColorService {

    @Autowired
    private ProjectEstimationColorMapper projectEstimationColorMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(ProjectEstimationFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Project Costing Setting Color (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(projectEstimationColorMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ProjectEstimationColorResponse> responses = projectEstimationColorMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation-color/list", null, null, "Project Costing Setting Color", "Project Costing Setting Color (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation-color/list", line, error.toString(), "Project Costing Setting Color", "Project Costing Setting Color (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Project Costing Setting Color (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<ProjectEstimationColorResponse> responses = projectEstimationColorMapper.getOne(id);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation-color/find/{id}", null, null, "Project Costing Setting Color", "Project Costing Setting Color (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation-color/find/{id}", line, error.toString(), "Project Costing Setting Color", "Project Costing Setting Color (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ProjectEstimationPercentSettingColorRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Project Costing Setting Color (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

           if (projectEstimationColorMapper.checkDuplicate(request.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Color or Percent Range", false));
           }

            ProjectEstimationColor color = new ProjectEstimationColor();
            color.setColor(request.getName());
            color.setCode(request.getColor());
            color.setPercentFrom(request.getPercentFrom());
            color.setPercentTo(request.getPercentTo());
            color.setCreatedBy(userId);
            color.setIsActive(1);
            Boolean result = projectEstimationColorMapper.insert(color);

            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/project-estimation-color/add", null, null, "Project Costing Setting Color", "Project Costing Setting Color (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation-color/add", line, error.toString(), "Project Costing Setting Color", "Project Costing Setting Color (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(ProjectEstimationPercentSettingColorUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Project Costing Setting Color (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            if (projectEstimationColorMapper.checkDuplicate(request.getName(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Color or Percent Range", false));
            }

            ProjectEstimationColor color = new ProjectEstimationColor();
            color.setId(request.getId());
            color.setColor(request.getName());
            color.setCode(request.getColor());
            color.setPercentFrom(request.getPercentFrom());
            color.setPercentTo(request.getPercentTo());
            color.setModifiedBy(userId);
            Boolean result = projectEstimationColorMapper.update(color);

            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/project-estimation-color/update", null, null, "Project Costing Setting Color", "Project Costing Setting Color (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation-color/update", line, error.toString(), "Project Costing Setting Color", "Project Costing Setting Color (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Project Costing Setting Color (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = projectEstimationColorMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/project-estimation-color/delete/{id}", null, null, "Project Costing Setting Color", "Project Costing Setting Color (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation-color/delete/{id}", line, error.toString(), "Project Costing Setting Color", "Project Costing Setting Color (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}