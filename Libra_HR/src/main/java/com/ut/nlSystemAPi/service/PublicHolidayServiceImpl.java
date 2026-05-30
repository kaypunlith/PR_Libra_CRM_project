package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.PublicHolidayMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.PublicHoliday;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PublicHolidayFilter;
import com.ut.nlSystemAPi.model.request.PublicHolidayRequest;
import com.ut.nlSystemAPi.model.request.PublicHolidayUpdateRequest;
import com.ut.nlSystemAPi.model.response.PublicHolidayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicHolidayServiceImpl implements PublicHolidayService {

  @Autowired
  private PublicHolidayMapper publicHolidayMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private MessageService messageService;

  @Autowired
  private PermissionMapper permissionMapper;


    public ResponseMessage<BaseResult> getList(PublicHolidayFilter filter) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
       if(permissionMapper.checkPermission(userId,"Public Holiday (View)") == 0){
           return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
       }

        Pagination pagination = new Pagination();
        pagination.setPage(filter.getPage());
        pagination.setRowsPerPage(filter.getRowsPerPage());

        if (filter != null) {
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
        }

        List<PublicHolidayResponse> publicHolidayResponses = publicHolidayMapper.getList(filter);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", publicHolidayResponses, true));
    }

    public ResponseMessage<BaseResult> getOne(Long id) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
       if(permissionMapper.checkPermission(userId,"Public Holiday (View)") == 0){
           return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
       }

        List<PublicHolidayResponse> publicHolidayResponses = publicHolidayMapper.getOne(id);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", publicHolidayResponses, true));
    }


    public ResponseMessage<BaseResult> insert(PublicHolidayRequest publicHolidayRequest) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
       if(permissionMapper.checkPermission(userId,"Public Holiday (Add)") == 0){
           return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
       }

        // Check Duplicate
        if(publicHolidayMapper.checkDuplicate(publicHolidayRequest.getName(), null) > 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate", false));
        }

        // Check Data
        PublicHoliday publicHoliday = new PublicHoliday();
        publicHoliday.setName(publicHolidayRequest.getName());
        publicHoliday.setDateFrom(publicHolidayRequest.getDateFrom());
        publicHoliday.setDateTo(publicHolidayRequest.getDateTo());
        publicHoliday.setCreatedBy(userId);

        Boolean result = publicHolidayMapper.insert(publicHoliday);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> update(PublicHolidayUpdateRequest publicHolidayUpdateRequest) {
        //Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Public Holiday (Edit)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        // Check Data
        PublicHoliday publicHoliday = new PublicHoliday();
        publicHoliday.setId(publicHolidayUpdateRequest.getId());
        publicHoliday.setName(publicHolidayUpdateRequest.getName());
        publicHoliday.setDateFrom(publicHolidayUpdateRequest.getDateFrom());
        publicHoliday.setDateTo(publicHolidayUpdateRequest.getDateTo());
        publicHoliday.setModifiedBy(userId);

        Boolean result = publicHolidayMapper.update(publicHoliday);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id) {
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId, "Public Holiday(Delete)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }
        Boolean result = publicHolidayMapper.delete(id, userId);
        if (result) {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    }


}
