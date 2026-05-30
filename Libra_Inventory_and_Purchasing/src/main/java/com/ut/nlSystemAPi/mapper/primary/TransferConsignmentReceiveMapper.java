package com.ut.nlSystemAPi.mapper.primary;


import com.ut.nlSystemAPi.model.TransferConsignmentReceive;
import com.ut.nlSystemAPi.model.TransferConsignmentReceiveResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.TransferConsignmentFilter;
import com.ut.nlSystemAPi.model.request.Login.TransferConsignment.TransferConsignmentReceiveRequest;
import com.ut.nlSystemAPi.model.response.TransferConsignment.TransferConsignmentDetailResponse;
import com.ut.nlSystemAPi.model.response.TransferConsignmentReceive.TransferConsignmentReceiveResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferConsignmentReceiveMapper {

  List<TransferConsignmentReceiveResponse> getList(@Param("filter") TransferConsignmentFilter filter);

  Long countList(@Param("filter") Filter filter);

  List<TransferConsignmentReceiveResponse> getOne(@Param("id") Long id);

  Boolean updateRequestStock(@Param("id") Long id);

  Boolean receive(@Param("request") TransferConsignmentReceiveRequest request, @Param("userId") Long userId);

  Boolean insertReceive(@Param("receive") TransferConsignmentReceive receive, @Param("userId") Long userId);

  Boolean insertReceiveResult(@Param("receiveResult") TransferConsignmentReceiveResult receiveResult, @Param("userId") Long userId);

  List<TransferConsignmentDetailResponse> getTransferConsignmentDetail(@Param("transferConsignmentId") Long transferConsignmentId);

  List<TransferConsignmentDetailResponse> getDetail(@Param("transferConsignmentId") Long transferConsignmentId);

  String getLastCode(@Param("code") String code);

  Long getConversionByUomId(@Param("uomId") Long id);

}