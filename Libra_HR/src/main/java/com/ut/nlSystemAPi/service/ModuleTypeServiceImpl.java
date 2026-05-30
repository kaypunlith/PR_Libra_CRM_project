package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ModuleMapper;
import com.ut.nlSystemAPi.mapper.primary.ModuleTypeMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.ModuleType;
import com.ut.nlSystemAPi.model.ModuleTypeFilter;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleTypeServiceImpl implements ModuleTypeService {

  @Autowired
  private ModuleTypeMapper moduleTypeMapper;

  @Autowired
  private ModuleMapper moduleMapper;

  @Autowired
  private MessageService messageService;

  @Autowired
  private UserService userService;

  public ResponseMessage<BaseResult> getList(ModuleTypeFilter filter) {
    Long userId = userService.getUserAuth().getId();

    Pagination pagination = new Pagination();
    pagination.setPage(filter.getPage());
    pagination.setRowsPerPage(filter.getRowsPerPage());
    pagination.setTotal(moduleTypeMapper.countList(filter));

    if (filter != null) {
      filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
    }

    List<ModuleType> moduleTypeList = moduleTypeMapper.getList(filter);
    if(moduleTypeList != null){
      for(int i = 0; i < moduleTypeList.size(); i++){
        Long id = moduleTypeList.get(i).getId();
        if(filter.getRoleId() == null || filter.getRoleId() == 0 ){
          moduleTypeList.get(i).setModuleList(moduleMapper.getOne(id));
        }else{
          moduleTypeList.get(i).setModuleList(moduleMapper.getOneByRoleId(id, filter.getRoleId()));
        }
      }
    }
    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", moduleTypeList, true));
  }

  public ResponseMessage<BaseResult> getOne(Long id) {
    List<ModuleType> moduleType = moduleTypeMapper.getOne(id);
    if(moduleType != null){
      for(int i = 0; i < moduleType.size(); i++){
        moduleType.get(i).setModuleList(moduleMapper.getOne(id));
      }
    }
    return ResponseMessageUtils.makeResponse(true, messageService.message("Success", moduleType,true));
  }

}