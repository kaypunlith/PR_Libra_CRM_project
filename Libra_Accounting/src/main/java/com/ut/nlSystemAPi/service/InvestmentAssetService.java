package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ChartAccountFilter;
import com.ut.nlSystemAPi.model.filter.InvestmentAssetFilter;
import com.ut.nlSystemAPi.model.filter.PostToJournalFilter;
import com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest.ChartOfAccountRequest;
import com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest.ChartOfAccountUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.InvestmentAsset.InvestmentAssetRequest;
import com.ut.nlSystemAPi.model.request.Login.InvestmentAsset.InvestmentAssetUpdateRequest;
import com.ut.nlSystemAPi.model.request.Login.PostToJournal.PostToJournalRequest;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface InvestmentAssetService {

  ResponseMessage<BaseResult> getListInvestmentAsset(InvestmentAssetFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> insert(InvestmentAssetRequest investmentRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> update(InvestmentAssetUpdateRequest investmentAssetUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult>  getListPostToJournal(PostToJournalFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException;

  ResponseMessage<BaseResult> savePostToJournal(PostToJournalRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException;

}