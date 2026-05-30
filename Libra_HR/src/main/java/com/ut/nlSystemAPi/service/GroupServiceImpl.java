package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.GroupMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.GroupRequest;
import com.ut.nlSystemAPi.model.request.GroupUpdateRequest;
import com.ut.nlSystemAPi.model.response.GroupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

  @Autowired
  private GroupMapper groupMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private MessageService messageService;

  @Autowired
  private PermissionMapper permissionMapper;


    public ResponseMessage<BaseResult> getList(Filter filter) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Group (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        Pagination pagination = new Pagination();
        pagination.setPage(filter.getPage());
        pagination.setRowsPerPage(filter.getRowsPerPage());

        if (filter != null) {
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
        }

        List<GroupResponse> groupResponses = groupMapper.getList(filter);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", groupResponses, pagination, true));
    }

    public ResponseMessage<BaseResult> getOne(Long id) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Group (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        List<GroupResponse> groupResponses = groupMapper.getOne(id);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", groupResponses, true));
    }


    public ResponseMessage<BaseResult> insert(GroupRequest groupRequest) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Group (Add)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        // Check Data
        groupRequest.setCreatedBy(userId);
        groupRequest.setIsActive(1);

        Boolean result = groupMapper.insert(groupRequest);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> update(GroupUpdateRequest groupUpdateRequest) {
        //Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Group (Edit)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        //Check Data
        groupUpdateRequest.setIsActive(1);

        Boolean result = groupMapper.update(groupUpdateRequest);
        if (result) {

            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id) {
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId, "Group (Delete)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }
        Boolean result = groupMapper.delete(id, userId);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> listFilterGroup(Filter filter) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();

        Pagination pagination = new Pagination();
        pagination.setPage(filter.getPage());
        pagination.setRowsPerPage(filter.getRowsPerPage());

        if (filter != null) {
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
        }

        List<GroupResponse> groupResponses = groupMapper.listFilterGroup(filter, userId);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", groupResponses, true));
    }

}
