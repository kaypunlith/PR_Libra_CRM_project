package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.BOQMapper;
import com.ut.nlSystemAPi.mapper.primary.HelperMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.base.*;
import com.ut.nlSystemAPi.model.entity.BOQ.BOQ;
import com.ut.nlSystemAPi.model.entity.BOQ.BOQDetail;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.request.BOQ.BOQRequest;
import com.ut.nlSystemAPi.model.request.BOQ.BOQUpdateRequest;
import com.ut.nlSystemAPi.model.response.BOQ.BOQResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.BOQService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class BOQServiceImpl implements BOQService {

    @Autowired
    private BOQMapper boqMapper;

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
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "BOQ (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(boqMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<BOQResponse> responses = boqMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/boq/list", null, null, "BOQ", "BOQ (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/boq/list", line, error.toString(), "BOQ", "BOQ (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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
              if (permissionMapper.checkPermission(userId, "BOQ (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            List<BOQResponse> responses = boqMapper.getOne(id);

            if (!responses.isEmpty()) {
                for (BOQResponse response : responses) {
                    response.setDetails(boqMapper.getListDetail(response.getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/boq/find/{id}", null, null, "BOQ", "BOQ (view)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/boq/find/{id}", line, error.toString(), "BOQ", "BOQ (view)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(BOQRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "BOQ (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            BOQ boq = new BOQ();
            boq.setCompanyId(request.getCompanyId());
            boq.setDate(request.getDate());
            boq.setOrganizationId(request.getOrganizationId());
            boq.setOrganizationContactId(request.getOrganizationContactId());
            boq.setNote(request.getNote());
            boq.setTotalCost(request.getTotalCost());
            boq.setTotalPrice(request.getTotalPrice());
            boq.setCreatedBy(userId);
            Boolean result = boqMapper.insert(boq);

            if (result) {

                //! Get the reference code
                String code = (generateCode.generateAutoCode("boqs", "code", 7, request.getModuleCode(), true, "status >= 0"));
                helperMapper.updateCode("boqs", "code", code, boq.getId());

                if (request.getDetails() != null && !request.getDetails().isEmpty()) {
                    BOQDetail boqDetail = new BOQDetail();
                    for (int i = 0; i < request.getDetails().size(); i++) {
                        boqDetail.setBoqId(boq.getId());
                        boqDetail.setType(request.getDetails().get(i).getType());
                        boqDetail.setItemId(request.getDetails().get(i).getItemId());
                        boqDetail.setItemName(request.getDetails().get(i).getItemName());
                        boqDetail.setConversion(request.getDetails().get(i).getConversion());
                        boqDetail.setTotalCost(request.getDetails().get(i).getTotalCost());
                        boqDetail.setUnitCost(request.getDetails().get(i).getUnitCost());
                        boqDetail.setQty(request.getDetails().get(i).getQty());
                        boqDetail.setUomId(request.getDetails().get(i).getUomId());
                        boqDetail.setUnitPrice(request.getDetails().get(i).getUnitPrice());
                        boqDetail.setTotalPrice(request.getDetails().get(i).getTotalPrice());
                        boqDetail.setRemark(request.getDetails().get(i).getRemark());
                        boqDetail.setCreatedBy(userId);
                        boqMapper.insertDetail(boqDetail);
                    }
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/boq/add", null, null, "BOQ", "BOQ (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/boq/add", line, error.toString(), "BOQ", "BOQ (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(BOQUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "BOQ (edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            if(helperMapper.checkDataExisting("boqs", request.getId()) == 0){
                    return ResponseMessageUtils.makeResponse(false, messageService.message("Data Cannot Be Found.", false));
            }

            // Check Data
            BOQ boq = new BOQ();
            boq.setCompanyId(request.getCompanyId());
            boq.setDate(request.getDate());
            boq.setOrganizationId(request.getOrganizationId());
            boq.setOrganizationContactId(request.getOrganizationContactId());
            boq.setNote(request.getNote());
            boq.setTotalCost(request.getTotalCost());
            boq.setTotalPrice(request.getTotalPrice());
            boq.setCreatedBy(userId);
            Boolean result = boqMapper.insert(boq);

            if (result) {
                helperMapper.archive("boqs", "status", -1, request.getId(), userId);
                //! Get the reference code
                String code = (helperMapper.getCurrentCode("boqs", "code", request.getId()));
                helperMapper.updateCode("boqs", "code", code, boq.getId());

                if (request.getDetails() != null && !request.getDetails().isEmpty()) {
                    BOQDetail boqDetail = new BOQDetail();
                    for (int i = 0; i < request.getDetails().size(); i++) {
                        boqDetail.setBoqId(boq.getId());
                        boqDetail.setType(request.getDetails().get(i).getType());
                        boqDetail.setItemId(request.getDetails().get(i).getItemId());
                        boqDetail.setItemName(request.getDetails().get(i).getItemName());
                        boqDetail.setConversion(request.getDetails().get(i).getConversion());
                        boqDetail.setTotalCost(request.getDetails().get(i).getTotalCost());
                        boqDetail.setUnitCost(request.getDetails().get(i).getUnitCost());
                        boqDetail.setQty(request.getDetails().get(i).getQty());
                        boqDetail.setUomId(request.getDetails().get(i).getUomId());
                        boqDetail.setUnitPrice(request.getDetails().get(i).getUnitPrice());
                        boqDetail.setTotalPrice(request.getDetails().get(i).getTotalPrice());
                        boqDetail.setRemark(request.getDetails().get(i).getRemark());
                        boqDetail.setCreatedBy(userId);
                        boqMapper.insertDetail(boqDetail);
                    }
                }

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/boq/update", null, null, "BOQ", "BOQ (edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/boq/update", line, error.toString(), "BOQ", "BOQ (edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "BOQ (delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = helperMapper.archive("boqs", "status", 0, id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/boq/delete/{id}",null,null,"BOQ","BOQ (delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/boq/delete/{id}",line, error.toString(),"BOQ","BOQ (delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
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
                if (permissionMapper.checkPermission(userId, "BOQ (approve)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            } else {
                if (permissionMapper.checkPermission(userId, "BOQ (disapprove)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            }

            Boolean result = boqMapper.approve(request.getId(), request.getStatus(), userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/boq/approve",null,null,"BOQ","BOQ (approve)","Approve",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/boq/approve",line, error.toString(),"BOQ","BOQ (approve)","Approve",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

}
