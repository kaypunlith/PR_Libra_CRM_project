package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.OrganizationActivityCardFilter;
import com.ut.nlSystemAPi.model.filter.OrganizationFilter;
import com.ut.nlSystemAPi.model.request.Organization.OrganizationActivityCardRequest;
import com.ut.nlSystemAPi.model.request.Organization.OrganizationRequest;
import com.ut.nlSystemAPi.model.request.Organization.OrganizationUpdateRequest;
import com.ut.nlSystemAPi.model.response.Organization.OrganizationActivityCardResponse;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface OrganizationService {

    ResponseMessage<BaseResult> getList(OrganizationFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> insert(OrganizationRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> update(OrganizationUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> addActivityCard(OrganizationActivityCardRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

    ResponseMessage<BaseResult> getListActivityCard(OrganizationActivityCardFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;
}
