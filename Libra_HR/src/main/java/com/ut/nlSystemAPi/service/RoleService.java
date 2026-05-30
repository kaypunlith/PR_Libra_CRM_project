//package com.ut.nlSystemAPi.service;
//
//import com.ut.nlSystemAPi.model.RoleData;
//import com.ut.nlSystemAPi.model.RoleDataUpdate;
//import com.ut.nlSystemAPi.model.base.BaseResult;
//import com.ut.nlSystemAPi.model.base.Filter;
//import com.ut.nlSystemAPi.model.base.ResponseMessage;
//import org.springframework.validation.BindingResult;
//
//public interface RoleService {
//
//  ResponseMessage<BaseResult> getList(Filter filter);
//
//  ResponseMessage<BaseResult> getOne(Long id);
//
//  ResponseMessage<BaseResult> menu();
//
//  ResponseMessage<BaseResult> insert(RoleData roleData, BindingResult bindingResult);
//
//  ResponseMessage<BaseResult> update(RoleDataUpdate roleDataUpdate, BindingResult bindingResult);
//
//  ResponseMessage<BaseResult> delete(Long id);
//
//}