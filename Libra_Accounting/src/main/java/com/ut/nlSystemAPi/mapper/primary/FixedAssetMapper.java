package com.ut.nlSystemAPi.mapper.primary;


import java.util.List;


import com.ut.nlSystemAPi.model.PostToJournal;
import com.ut.nlSystemAPi.model.PostToJournalDetails;
import com.ut.nlSystemAPi.model.filter.PostToJournalFilter;
import com.ut.nlSystemAPi.model.response.PostToJournal.PostToJournalResponse;
import com.ut.nlSystemAPi.model.response.PostToJournal.PostToJournalResponseDetails;

import com.ut.nlSystemAPi.model.response.FixedAsset.FixedAssetFileResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ut.nlSystemAPi.model.FixedAsset;
import com.ut.nlSystemAPi.model.filter.FixedAssetFilter;
import com.ut.nlSystemAPi.model.request.Login.FixedAssetRequest.FixedAssetFileRequest;
import com.ut.nlSystemAPi.model.response.FixedAsset.FixedAssetResponse;


@Repository
public interface FixedAssetMapper {

  List<FixedAssetResponse> getList(@Param("filter") FixedAssetFilter fixedAssetFilter);

  Long countList(@Param("filter") FixedAssetFilter fixedAssetFilter);

  List<FixedAssetResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("fixedAssetCode") String accountCode, @Param("id") Long id);

  Boolean update(@Param("fixedAsset") FixedAsset fixedAsset);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Boolean insert(@Param("fixedAsset") FixedAsset fixedAsset);

  Boolean insertFixedAssetFile(@Param("fixedAssetFileRequest") FixedAssetFileRequest fixedAssetFileRequest, @Param("fixedAssetId") Long fixedAssetId);

  Boolean deleteFixedAssetFile(@Param("fsId") Long fsId, @Param("userId") Long userId);

  List<PostToJournalResponse> getListPostToJournal(@Param("filter") PostToJournalFilter filter);
  List<PostToJournalResponse> addListPostToJournal(@Param("filter") PostToJournalFilter filter);
  Double getLastPostedAmount(@Param("filter") PostToJournalFilter filter, @Param("accountId") Long accountId);
  List<FixedAssetResponse> getPostToJournalDetails(@Param("filter") PostToJournalFilter filter, @Param("accountId") Long accountId, @Param("isDepreAcc") Long isDepreAcc);

  Double getCreditAmount(@Param("filter") PostToJournalFilter filter, @Param("accountId") Long accountId);
  Double getDebitAmount(@Param("filter") PostToJournalFilter filter, @Param("accountId") Long accountId);

  Boolean savePostToJournal(@Param("postToJournal") PostToJournal postToJournal);
  Boolean saveDetailPostToJournal(@Param("details") PostToJournalDetails details);
  Boolean updateAsset();
  List<FixedAssetFileResponse> getFixedAssetFile(@Param("fixedAssetFileId") Long fixedAssetFileId);



}
