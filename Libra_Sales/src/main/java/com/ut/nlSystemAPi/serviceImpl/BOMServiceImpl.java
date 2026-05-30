package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.BOMMapper;
import com.ut.nlSystemAPi.mapper.primary.HelperMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.base.*;
import com.ut.nlSystemAPi.model.entity.BOM.BOM;
import com.ut.nlSystemAPi.model.entity.BOM.BOMDetail;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.filter.BOMFilter;
import com.ut.nlSystemAPi.model.request.BOM.BOMRequest;
import com.ut.nlSystemAPi.model.request.BOM.BOMUpdateRequest;
import com.ut.nlSystemAPi.model.response.BOM.BOMResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.BOMService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class BOMServiceImpl implements BOMService {

    @Autowired
    private BOMMapper bomMapper;

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
    public ResponseMessage<BaseResult> getList(BOMFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "BOM (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(bomMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<BOMResponse> responses = bomMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bom/list", null, null, "BOM", "BOM (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bom/list", line, error.toString(), "BOM", "BOM (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "BOM (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<BOMResponse> responses = bomMapper.getOne(id);

            if (!responses.isEmpty()) {
                for (BOMResponse response : responses) {
                    response.setDetails(bomMapper.getListDetail(response.getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bom/find/{id}", null, null, "BOM", "BOM (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bom/find/{id}", line, error.toString(), "BOM", "BOM (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(BOMRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "BOM (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check Data
            BOM bom = new BOM();
            bom.setBoqId(request.getBoqId());
            bom.setCompanyId(request.getCompanyId());
            bom.setDate(request.getDate());
            bom.setOrganizationId(request.getOrganizationId());
            bom.setOrganizationContactId(request.getOrganizationContactId());
            bom.setNote(request.getNote());
            bom.setTotalCost(request.getTotalCost());
            bom.setTotalPrice(request.getTotalPrice());
            bom.setCreatedBy(userId);
            Boolean result = bomMapper.insert(bom);

            if (result) {

                //! Get the reference code
                String code = (generateCode.generateAutoCode("boms", "code", 7, request.getModuleCode(), true, "status >= 0"));
                helperMapper.updateCode("boms", "code", code, bom.getId());

                if (request.getDetails() != null && !request.getDetails().isEmpty()) {
                    BOMDetail bomDetail = new BOMDetail();
                    for (int i = 0; i < request.getDetails().size(); i++) {
                        bomDetail.setBomId(bom.getId());
                        bomDetail.setType(request.getDetails().get(i).getType());
                        bomDetail.setItemId(request.getDetails().get(i).getItemId());
                        bomDetail.setItemName(request.getDetails().get(i).getItemName());
                        bomDetail.setConversion(request.getDetails().get(i).getConversion());
                        bomDetail.setTotalCost(request.getDetails().get(i).getTotalCost());
                        bomDetail.setUnitCost(request.getDetails().get(i).getUnitCost());
                        bomDetail.setQty(request.getDetails().get(i).getQty());
                        bomDetail.setUomId(request.getDetails().get(i).getUomId());
                        bomDetail.setUnitPrice(request.getDetails().get(i).getUnitPrice());
                        bomDetail.setTotalPrice(request.getDetails().get(i).getTotalPrice());
                        bomDetail.setRemark(request.getDetails().get(i).getRemark());
                        bomDetail.setCreatedBy(userId);
                        bomMapper.insertDetail(bomDetail);
                    }
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/bom/add", null, null, "BOM", "BOM (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bom/add", line, error.toString(), "BOM", "BOM (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(BOMUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "BOM (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            BOM bom = new BOM();
            bom.setCompanyId(request.getCompanyId());
            bom.setDate(request.getDate());
            bom.setOrganizationId(request.getOrganizationId());
            bom.setOrganizationContactId(request.getOrganizationContactId());
            bom.setNote(request.getNote());
            bom.setTotalCost(request.getTotalCost());
            bom.setTotalPrice(request.getTotalPrice());
            bom.setCreatedBy(userId);
            Boolean result = bomMapper.insert(bom);

            if (result) {
                helperMapper.archive("boms", "status", -1, request.getId(), userId);
                //! Get the reference code
                String code = (helperMapper.getCurrentCode("boms", "code", request.getId()));
                helperMapper.updateCode("boms", "code", code, bom.getId());

                if (request.getDetails() != null && !request.getDetails().isEmpty()) {
                    BOMDetail bomDetail = new BOMDetail();
                    for (int i = 0; i < request.getDetails().size(); i++) {
                        bomDetail.setBomId(bom.getId());
                        bomDetail.setType(request.getDetails().get(i).getType());
                        bomDetail.setItemId(request.getDetails().get(i).getItemId());
                        bomDetail.setItemName(request.getDetails().get(i).getItemName());
                        bomDetail.setConversion(request.getDetails().get(i).getConversion());
                        bomDetail.setTotalCost(request.getDetails().get(i).getTotalCost());
                        bomDetail.setUnitCost(request.getDetails().get(i).getUnitCost());
                        bomDetail.setQty(request.getDetails().get(i).getQty());
                        bomDetail.setUomId(request.getDetails().get(i).getUomId());
                        bomDetail.setUnitPrice(request.getDetails().get(i).getUnitPrice());
                        bomDetail.setTotalPrice(request.getDetails().get(i).getTotalPrice());
                        bomDetail.setRemark(request.getDetails().get(i).getRemark());
                        bomDetail.setCreatedBy(userId);
                        bomMapper.insertDetail(bomDetail);
                    }
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/bom/update", null, null, "BOM", "BOM (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/bom/update", line, error.toString(), "BOM", "BOM (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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
            if (permissionMapper.checkPermission(userId, "BOM (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = helperMapper.archive("boms", "status", 0, id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/bom/delete/{id}",null,null,"BOM","BOM (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bom/delete/{id}",line, error.toString(),"BOM","BOM (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
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
                if (permissionMapper.checkPermission(userId, "BOM (Approve)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            } else {
                if (permissionMapper.checkPermission(userId, "BOM (Disapprove)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            }

            Boolean result = bomMapper.approve(request.getId(), request.getStatus(), userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/bom/approve",null,null,"BOM","BOM (Approve)","Approve",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/bom/approve",line, error.toString(),"BOM","BOM (Approve)","Approve",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

}
