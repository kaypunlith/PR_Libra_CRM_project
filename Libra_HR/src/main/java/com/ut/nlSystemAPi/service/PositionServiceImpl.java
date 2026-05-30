package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.PositionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.PositionOrderingRequest;
import com.ut.nlSystemAPi.model.request.PositionRequest;
import com.ut.nlSystemAPi.model.request.PositionUpdateRequest;
import com.ut.nlSystemAPi.model.response.PositionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

  @Autowired
  private PositionMapper positionMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private MessageService messageService;

  @Autowired
  private PermissionMapper permissionMapper;


    public ResponseMessage<BaseResult> getList(Filter filter) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Position (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        Pagination pagination = new Pagination();
        pagination.setPage(filter.getPage());
        pagination.setRowsPerPage(filter.getRowsPerPage());
        filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

        List<PositionResponse> positionResponses = positionMapper.getList(filter);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", positionResponses, true));
    }

    public ResponseMessage<BaseResult> getOne(Long id) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Position (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        List<PositionResponse> positionResponses = positionMapper.getOne(id);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", positionResponses, true));
    }

    public ResponseMessage<BaseResult> insert(PositionRequest positionRequest) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Position (Add)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }
        // Check Duplicate
        if(positionMapper.checkDuplicate(positionRequest.getName(), null) > 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate", false));
        }

        // Check Data
        positionRequest.setCreatedBy(userId);
        positionRequest.setIsActive(1);

        Boolean result = positionMapper.insert(positionRequest);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> update(PositionUpdateRequest positionUpdateRequest) {
        //Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Position (Edit)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        // Check Data
        positionUpdateRequest.setModifiedBy(userId);
        positionUpdateRequest.setIsActive(1);

        Boolean result = positionMapper.update(positionUpdateRequest);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> updateOrdering(PositionOrderingRequest positionOrderingRequest) {
        //Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Position (Edit)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        // Check Data
        positionOrderingRequest.setModifiedBy(userId);
        Boolean result = positionMapper.updateOrdering(positionOrderingRequest);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id) {
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId, "Position (Delete)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }
        Boolean result = positionMapper.delete(id, userId);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

}
