package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Commission.Commission;
import com.ut.nlSystemAPi.model.entity.Commission.CommissionDetail;
import com.ut.nlSystemAPi.model.entity.Commission.CommissionEmployee;
import com.ut.nlSystemAPi.model.filter.CommissionFilter;
import com.ut.nlSystemAPi.model.response.Commission.CommissionDetailResponse;
import com.ut.nlSystemAPi.model.response.Commission.CommissionEmployeeResponse;
import com.ut.nlSystemAPi.model.response.Commission.CommissionResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommissionMapper {

    List<CommissionResponse> getList(@Param("filter") CommissionFilter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") CommissionFilter filter, @Param("userId") Long userId);

    List<CommissionResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("commission") Commission commission);

    Boolean update(@Param("commission") Commission commission);

    List<CommissionDetailResponse> getDetails(@Param("commissionId") Long commissionId);

    List<CommissionEmployeeResponse> getEmployees(@Param("commissionId") Long commissionId);

    Boolean insertDetail(@Param("detail") CommissionDetail detail);

    Boolean insertEmployee(@Param("employee") CommissionEmployee employee);

    Boolean deleteDetails(@Param("commissionId") Long commissionId);

    Boolean deleteEmployees(@Param("commissionId") Long commissionId);
}
