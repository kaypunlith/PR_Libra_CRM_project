package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Quotation.*;
import com.ut.nlSystemAPi.model.filter.QuotationFilter;
import com.ut.nlSystemAPi.model.request.Quotation.CRMPercentRequest;
import com.ut.nlSystemAPi.model.request.Quotation.QuotationPaginationRequest;
import com.ut.nlSystemAPi.model.response.Quotation.*;
import com.ut.nlSystemAPi.model.response.TermCondition.TermConditionResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QuotationMapper {

  List<QuotationResponse> getList(@Param("filter") QuotationFilter filter, @Param("userId") Long userId);

  Long countList(@Param("filter") QuotationFilter filter, @Param("userId") Long userId);

  List<QuotationResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

  List<QuotationResponse> getOneIncludeArchived(@Param("id") Long id, @Param("userId") Long userId);

  Boolean insert(@Param("quotation") Quotation quotation);

  Boolean insertTermCondition(@Param("termCondition") QuotationTermCondition termCondition);

  List<TermConditionResponse> getTermCondition(@Param("quotationId") Long quotationId);

  Boolean insertDetail(@Param("detail") QuotationDetail detail);

  Boolean insertService(@Param("service") QuotationServices service);

  Boolean insertMisc(@Param("misc") QuotationMisc misc);

  List<QuotationDetailResponse> getListDetail(@Param("quotationId") Long quotationId);

  Boolean approve(@Param("id") Long id, @Param("status") Long status, @Param("userId") Long userId);

  Boolean close(@Param("id") Long id, @Param("status") Long status, @Param("userId") Long userId);

  Boolean updateCrm(@Param("request") CRMPercentRequest request);

  List<CRMPercentResponse> getCrm();

  Boolean updateShareOption(@Param("id") Long id, @Param("shareOption") Long shareOption, @Param("shareSaveOption") Long shareSaveOption);

  Long insertShareUser(@Param("params") Map<String, Object> params);

  Long insertShareExceptUser(@Param("params") Map<String, Object> params);

  QuotationCreator getCreator(@Param("id") Long id);

  Long countItems(@Param("quotationId") Long quotationId);

  List<StatusInformationResponse> getStatusInformation(@Param("quotationId") Long quotationId, @Param("latestRecord") Long latestRecord);

  List<StatusInformationResponse> getStatusInformationDetail(@Param("id") Long id);

  Boolean insertStatusInformation(@Param("statusInformation") QuotationStatusInformation statusInformation);

  Boolean insertStatusInformationDetail(@Param("statusInformationId") Long statusInformationId, @Param("reasonId") Long reasonId);

  List<QuotationDetailResponse> getUserShare(@Param("userIds") String userIds);

  List<QuotationProductInfoResponse> findProductInfo(@Param("productId") Long productId, @Param("userId") Long userId);

  Boolean updatePagination(@Param("request") QuotationPaginationRequest request);

}
