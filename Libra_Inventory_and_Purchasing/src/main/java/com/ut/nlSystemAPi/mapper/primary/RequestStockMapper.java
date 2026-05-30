package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.filter.RequestStockFilter;
import com.ut.nlSystemAPi.model.request.Login.RequestStock.RequestStockApproveStatus;
import com.ut.nlSystemAPi.model.response.Dropdown.RequestStockDetailDropdownResponse;
import com.ut.nlSystemAPi.model.response.RequestStock.RequestStockDetailResponse;
import com.ut.nlSystemAPi.model.response.RequestStock.RequestStockResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestStockMapper {

    List<RequestStockResponse> getList(@Param("filter") RequestStockFilter filter, @Param("userId") Long userId);

    List<RequestStockDetailResponse> getRequestStockDetail(@Param("id") Long id);

    Long countList(@Param("filter") RequestStockFilter filter, @Param("userId") Long userId);

    List<RequestStockResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insert(@Param("requestStock") RequestStock requestStock);

    Boolean insertRequestStockDetail(@Param("requestStockDetail") RequestStockDetail requestStockDetail);

    String getCode(@Param("id") Long id);

    String getLastCode();

    Boolean archive(@Param("id") Long id,@Param("userId") Long userId);

    Boolean update(@Param("requestStock") RequestStock requestStock);

    Boolean updateApproveStatus(@Param("requestStockApprove") RequestStockApproveStatus requestStockApprove);

    Boolean deleteRequestDetail(@Param("id") Long id);

    Boolean delete(@Param("id")  Long id,@Param("userId") Long userId);
}