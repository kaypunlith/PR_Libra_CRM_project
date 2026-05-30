package com.ut.nlSystemAPi.mapper.primary;


import com.ut.nlSystemAPi.model.InvestmentAsset;
import com.ut.nlSystemAPi.model.PostToJournal;
import com.ut.nlSystemAPi.model.PostToJournalDetails;
import com.ut.nlSystemAPi.model.filter.InvestmentAssetFilter;

import com.ut.nlSystemAPi.model.filter.PostToJournalFilter;
import com.ut.nlSystemAPi.model.response.FixedAsset.FixedAssetResponse;
import com.ut.nlSystemAPi.model.response.PostToJournal.PostToJournalResponse;
import com.ut.nlSystemAPi.model.response.PostToJournal.PostToJournalResponseDetails;

import com.ut.nlSystemAPi.model.request.Login.FixedAssetRequest.FixedAssetFileRequest;
import com.ut.nlSystemAPi.model.request.Login.InvestmentAsset.InvestmentAssetFileRequest;
import com.ut.nlSystemAPi.model.response.ChartOfAccount.ChartOfAccountResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.CompanyChartAccountResponse;
import com.ut.nlSystemAPi.model.response.FixedAsset.FixedAssetFileResponse;
import com.ut.nlSystemAPi.model.response.InvestmentAsset.InvestmentAssetFileResponse;

import com.ut.nlSystemAPi.model.response.InvestmentAsset.InvestmentAssetResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentAssetMapper {

  List<InvestmentAssetResponse> getList(@Param("filter") InvestmentAssetFilter filter);

  Long countList(@Param("filter") InvestmentAssetFilter filter);

  List<InvestmentAssetResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("investmentAssetCode") String investmentAssetCode, @Param("id") Long id);

  Boolean insert(@Param("investmentAsset") InvestmentAsset investmentAsset);

  Boolean update(@Param("investmentAsset") InvestmentAsset investmentAsset);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);


  List<PostToJournalResponse> getListPostToJournal(@Param("filter") PostToJournalFilter filter);
  List<PostToJournalResponse> addListPostToJournal(@Param("filter") PostToJournalFilter filter);
  Double getLastPostedAmount(@Param("filter") PostToJournalFilter filter, @Param("accountId") Long accountId);
  List<FixedAssetResponse> getPostToJournalDetails(@Param("filter") PostToJournalFilter filter, @Param("accountId") Long accountId, @Param("isDepreAcc") Long isDepreAcc);

  Double getCreditAmount(@Param("filter") PostToJournalFilter filter, @Param("accountId") Long accountId);
  Double getDebitAmount(@Param("filter") PostToJournalFilter filter, @Param("accountId") Long accountId);

  Boolean savePostToJournal(@Param("postToJournal") PostToJournal postToJournal);
  Boolean saveDetailPostToJournal(@Param("details") PostToJournalDetails details);
  Boolean updateAsset();

  Boolean insertInvestmentAssetFile(@Param("investmentAssetFileRequest") InvestmentAssetFileRequest investmentAssetFileRequest, @Param("investmentAssetId") Long investmentAssetId);

  Boolean deleteInvestmentAssetFile(@Param("inId") Long inId, @Param("userId") Long userId);

  List<InvestmentAssetFileResponse> getInvestmentAssetFile(@Param("investmentAssetFileId") Long investmentAssetFileId);

}