package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Service.Service;
import com.ut.nlSystemAPi.model.entity.ServiceTerminate.ServiceTerminate;
import com.ut.nlSystemAPi.model.entity.ServiceTerminate.ServiceTerminateDetail;
import com.ut.nlSystemAPi.model.filter.ServiceFilter;
import com.ut.nlSystemAPi.model.filter.ServiceTerminateFilter;
import com.ut.nlSystemAPi.model.response.Service.ServiceResponse;
import com.ut.nlSystemAPi.model.response.Service.ServiceShiftResponse;
import com.ut.nlSystemAPi.model.response.serviceTerminateResponse.ServiceTerminateDetailResponse;
import com.ut.nlSystemAPi.model.response.serviceTerminateResponse.ServiceTerminateResponse;
import com.ut.nlSystemAPi.service.ServiceTerminateService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceTerminateMapper {

    List<ServiceTerminateResponse> getList(@Param("filter") ServiceTerminateFilter filter);

    List<ServiceTerminateDetailResponse> getListCustomerQuotation(@Param("customerId") Long customerId);

    List<ServiceTerminateDetailResponse> getReductionsByCustomer(@Param("customerId") Long customerId);

    Long getQuotationIdByCustomer(@Param("customerId") Long customerId);

    Long countList(@Param("filter") ServiceTerminateFilter filter);

    List<Long> getServiceIdByServiceTerminate(@Param("id") Long id);

    List<ServiceTerminateResponse> getOne(@Param("id") Long id);

    List<ServiceTerminateDetailResponse> getServiceTerminate(@Param("serviceId") Long serviceId,@Param("serviceTerminateId") Long serviceTerminateId);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("service") ServiceTerminate service);


    Boolean insertServiceTerminateDetail(@Param("serviceShiftDetail") ServiceTerminateDetail serviceShiftDetail);

    Boolean update(@Param("service") ServiceTerminate service);


    Boolean deleteServiceTerminate(@Param("serviceId") Long serviceId);



    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}