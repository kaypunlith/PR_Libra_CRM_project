package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.HelperMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ProjectEstimationMapper;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.base.StatusRequest;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.entity.ProjectEstimation.ProjectEstimation;
import com.ut.nlSystemAPi.model.entity.ProjectEstimation.ProjectEstimationDetail;
import com.ut.nlSystemAPi.model.filter.ProjectEstimationFilter;
import com.ut.nlSystemAPi.model.request.ProjectEstimation.ProjectEstimationDetailRequest;
import com.ut.nlSystemAPi.model.request.ProjectEstimation.ProjectEstimationRequest;
import com.ut.nlSystemAPi.model.request.ProjectEstimation.ProjectEstimationUpdateRequest;
import com.ut.nlSystemAPi.model.response.ProjectEstimation.ProjectEstimationResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.ProjectEstimationService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class ProjectEstimationServiceImpl implements ProjectEstimationService {

    @Autowired
    private ProjectEstimationMapper projectEstimationMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;
    @Autowired
    private GenerateCode generateCode;
    @Autowired
    private HelperMapper helperMapper;

    @Override
    public ResponseMessage<BaseResult> getList(ProjectEstimationFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Project Costing (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (permissionMapper.checkPermission(userId, "Project Costing (View By User)") > 0) {
                filter.setViewByUser(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(projectEstimationMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ProjectEstimationResponse> responses = projectEstimationMapper.getList(filter, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation/list", null, null, "Project Costing", "Project Costing (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation/list", line, error.toString(), "Project Costing", "Project Costing (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Project Costing (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<ProjectEstimationResponse> responses = projectEstimationMapper.getOne(id, userId);
            if (!responses.isEmpty()) {
                for (ProjectEstimationResponse response : responses) {
                    response.setDetails(projectEstimationMapper.getListDetail(response.getId()));
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation/find/{id}", null, null, "Project Costing", "Project Costing (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation/find/{id}", line, error.toString(), "Project Costing", "Project Costing (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ProjectEstimationRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Project Costing (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            ProjectEstimation projectEstimation = new ProjectEstimation();
            projectEstimation.setCompanyId(request.getCompanyId());
            projectEstimation.setCustomerId(request.getCustomerId());
            projectEstimation.setCustomerContactId(request.getCustomerContactId());
            projectEstimation.setEmployeeId(request.getProjectLeaderId());
            projectEstimation.setBoqId(request.getBoqId());
            projectEstimation.setDate(request.getDate());
            projectEstimation.setDuration(request.getDuration());
            projectEstimation.setNote(request.getNote());
            projectEstimation.setTotalSubAmount(request.getTotalEstimateCost());
            projectEstimation.setTotalAmount(request.getGrossProfitAmount());
            projectEstimation.setTotalPercent(request.getGrossProfitPercent());
            projectEstimation.setCreatedBy(userId);
            projectEstimation.setIsActive(1);
            Boolean result = projectEstimationMapper.insert(projectEstimation);

            if (result) {

                String code = generateCode.generateAutoCode("project_costings", "pc_code", 7, "PC", true, "status >= 0");
                helperMapper.updateCode("project_costings", "pc_code", code, projectEstimation.getId());

                if (request.getDetails() != null && !request.getDetails().isEmpty()) {
                    ProjectEstimationDetail detail = new ProjectEstimationDetail();
                    for (ProjectEstimationDetailRequest detailRequest : request.getDetails()) {
                        detail.setProjectEstimationId(projectEstimation.getId());
                        detail.setProjectEstimationTermId(detailRequest.getProjectEstimationTermId());
                        detail.setVendorId(detailRequest.getVendorId());
                        detail.setDescription(detailRequest.getDescription());
                        detail.setQty(detailRequest.getQty());
                        detail.setUomId(detailRequest.getUomId());
                        detail.setUnitPrice(detailRequest.getUnitPrice());
                        detail.setTotalPrice(detailRequest.getTotalPrice());
                        detail.setNote(detailRequest.getNote());
                        detail.setCreatedBy(userId);
                        projectEstimationMapper.insertDetail(detail);
                    }
                }

                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/project-estimation/add", null, null, "Project Costing", "Project Costing (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation/add", line, error.toString(), "Project Costing", "Project Costing (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(ProjectEstimationUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Project Costing (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            ProjectEstimation projectEstimation = new ProjectEstimation();
            projectEstimation.setCompanyId(request.getCompanyId());
            projectEstimation.setCustomerId(request.getCustomerId());
            projectEstimation.setCustomerContactId(request.getCustomerContactId());
            projectEstimation.setEmployeeId(request.getProjectLeaderId());
            projectEstimation.setBoqId(request.getBoqId());
            projectEstimation.setDate(request.getDate());
            projectEstimation.setDuration(request.getDuration());
            projectEstimation.setNote(request.getNote());
            projectEstimation.setTotalSubAmount(request.getTotalEstimateCost());
            projectEstimation.setTotalAmount(request.getGrossProfitAmount());
            projectEstimation.setTotalPercent(request.getGrossProfitPercent());
            projectEstimation.setCreatedBy(userId);
            projectEstimation.setIsActive(1);
            Boolean result = projectEstimationMapper.insert(projectEstimation);

            if (result) {
                helperMapper.archive("project_costings", "status", -1, request.getId(), userId);

                //! Get the reference code
                String code = (helperMapper.getCurrentCode("project_costings", "pc_code", request.getId()));
                helperMapper.updateCode("project_costings", "pc_code", code, projectEstimation.getId());

                if (request.getDetails() != null && !request.getDetails().isEmpty()) {
                    ProjectEstimationDetail detail = new ProjectEstimationDetail();
                    for (ProjectEstimationDetailRequest detailRequest : request.getDetails()) {
                        detail.setProjectEstimationId(projectEstimation.getId());
                        detail.setProjectEstimationTermId(detailRequest.getProjectEstimationTermId());
                        detail.setVendorId(detailRequest.getVendorId());
                        detail.setDescription(detailRequest.getDescription());
                        detail.setQty(detailRequest.getQty());
                        detail.setUomId(detailRequest.getUomId());
                        detail.setUnitPrice(detailRequest.getUnitPrice());
                        detail.setTotalPrice(detailRequest.getTotalPrice());
                        detail.setNote(detailRequest.getNote());
                        detail.setCreatedBy(userId);
                        projectEstimationMapper.insertDetail(detail);
                    }
                }

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/project-estimation/update", null, null, "Project Costing", "Project Costing (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation/update", line, error.toString(), "Project Costing", "Project Costing (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Project Costing (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = helperMapper.archive("project_costings", "status", 0, id, userId);

            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/project-estimation/delete/{id}", null, null, "Project Costing", "Project Costing (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation/delete/{id}", line, error.toString(), "Project Costing", "Project Costing (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> approve(StatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (request.getStatus() == 2) {
                if (permissionMapper.checkPermission(userId, "Project Costing (Approve)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            } else {
                if (permissionMapper.checkPermission(userId, "Project Costing (Disapprove)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            }

            Boolean result = projectEstimationMapper.approve(request.getId(), request.getStatus(), userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/project-estimation/approve",null,null,"Project Estimation","Project Costing (Approve)","Approve",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/project-estimation/approve",line, error.toString(),"Project Estimation","Project Costing (Approve)","Approve",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }
}