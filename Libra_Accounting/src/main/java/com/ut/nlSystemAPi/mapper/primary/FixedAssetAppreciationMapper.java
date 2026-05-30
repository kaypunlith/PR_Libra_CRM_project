package com.ut.nlSystemAPi.mapper.primary;
import com.ut.nlSystemAPi.model.FixedAssetAppreciation;
import com.ut.nlSystemAPi.model.PostToJournal;
import com.ut.nlSystemAPi.model.PostToJournalDetails;
import com.ut.nlSystemAPi.model.filter.FixedAssetAppreciationFilter;
import com.ut.nlSystemAPi.model.filter.PostToJournalFilter;
import com.ut.nlSystemAPi.model.request.Login.FixedAssetAppreciationRequest.FixedAssetAppreciationFileRequest;
import com.ut.nlSystemAPi.model.response.FixedAsset.FixedAssetResponse;
import com.ut.nlSystemAPi.model.response.PostToJournal.PostToJournalResponse;
import com.ut.nlSystemAPi.model.response.PostToJournal.PostToJournalResponseDetails;
import com.ut.nlSystemAPi.model.response.FixedAssetAppreciation.FixedAssetAppreciationFileResponse;
import com.ut.nlSystemAPi.model.response.FixedAssetAppreciation.FixedAssetAppreciationResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FixedAssetAppreciationMapper {

  List<FixedAssetAppreciationResponse> getList(@Param("filter") FixedAssetAppreciationFilter fixedAssetAppreciationFilter);

  Long countList(@Param("filter") FixedAssetAppreciationFilter fixedAssetAppreciationFilter);

  List<FixedAssetAppreciationResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("fixedAssetAppreciationCode") String accountCode, @Param("id") Long id);

  Boolean update(@Param("fixedAssetAppreciation") FixedAssetAppreciation fixedAssetAppreciation);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Boolean insert(@Param("fixedAssetAppreciation") FixedAssetAppreciation fixedAssetAppreciation);

  Boolean insertFixedAssetAppreciationFile(@Param("fixedAssetAppreciationFileRequest") FixedAssetAppreciationFileRequest fixedAssetAppreciationFileRequest, @Param("fixedAssetAppreciationId") Long fixedAssetAppreciationId);

  Boolean deleteFixedAssetAppreciationFile(@Param("fsId") Long fsId, @Param("userId") Long userId);

  List<FixedAssetAppreciationFileResponse> getFixedAssetAppreciationFile(@Param("fixedAssetAppreciationFileId") Long fixedAssetAppreciationFileId);

  List<PostToJournalResponse> getListPostToJournal(@Param("filter") PostToJournalFilter filter);
  List<PostToJournalResponse> addListPostToJournal(@Param("filter") PostToJournalFilter filter);
  Double getLastPostedAmount(@Param("filter") PostToJournalFilter filter, @Param("accountId") Long accountId);
  List<FixedAssetResponse> getPostToJournalDetails(@Param("filter") PostToJournalFilter filter, @Param("accountId") Long accountId, @Param("isDepreAcc") Long isDepreAcc);

  Double getCreditAmount(@Param("filter") PostToJournalFilter filter, @Param("accountId") Long accountId);
  Double getDebitAmount(@Param("filter") PostToJournalFilter filter, @Param("accountId") Long accountId);

  Boolean savePostToJournal(@Param("postToJournal") PostToJournal postToJournal);

  Boolean saveDetailPostToJournal(@Param("details") PostToJournalDetails details);

  Boolean updateAsset();

}