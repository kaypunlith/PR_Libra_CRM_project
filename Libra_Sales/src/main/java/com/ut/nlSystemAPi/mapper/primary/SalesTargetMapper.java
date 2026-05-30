package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.SalesTarget.SalesTarget;
import com.ut.nlSystemAPi.model.entity.SalesTarget.SalesTargetEmployee;
import com.ut.nlSystemAPi.model.response.SalesTarget.SalesTargetEmployeeOrganizationResponse;
import com.ut.nlSystemAPi.model.response.SalesTarget.SalesTargetEmployeeResponse;
import com.ut.nlSystemAPi.model.response.SalesTarget.SalesTargetMarketResponse;
import com.ut.nlSystemAPi.model.response.SalesTarget.SalesTargetResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesTargetMapper {

    List<SalesTargetResponse> getList(@Param("filter") Filter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") Filter filter, @Param("userId") Long userId);

    List<SalesTargetResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("salesTarget") SalesTarget salesTarget);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    List<Long> getDetails(@Param("saleTargetId") Long saleTargetId);

    Boolean deleteDetails(@Param("value") Integer value, @Param("saleTargetId") Long saleTargetId, @Param("userId") Long userId);

    Boolean insertDetails(@Param("saleTargetId") Long saleTargetId, @Param("monthId") Long monthId, @Param("userId") Long userId);

    List<Long> getEmployeeTargetId(@Param("saleTargetId") Long saleTargetId);

    List<SalesTargetEmployeeResponse> getEmployeeTarget(@Param("saleTargetId") Long saleTargetId);

    List<SalesTargetEmployeeOrganizationResponse> getEmployeeTargetOrganization(@Param("saleTargetEmployeeId") Long saleTargetEmployeeId);

    List<SalesTargetMarketResponse> getCustomerMarkets(@Param("saleTargetId") Long saleTargetId);

    Boolean deleteEmployeeTarget(@Param("value") Integer value, @Param("saleTargetId") Long saleTargetId, @Param("userId") Long userId);

    Boolean insertEmployeeTarget(@Param("salesTargetEmployee") SalesTargetEmployee salesTargetEmployee);

    Boolean deleteEmployeeTargetOrganization(@Param("value") Integer value, @Param("saleTargetEmployeeId") Long saleTargetEmployeeId, @Param("userId") Long userId);

    Boolean insertEmployeeTargetOrganization(@Param("salesTargetEmployeeId") Long salesTargetEmployeeId, @Param("organizationId") Long organizationId, @Param("userId") Long userId);

    Boolean deleteCustomerMarkets(@Param("value") Integer value, @Param("saleTargetId") Long saleTargetId, @Param("userId") Long userId);

    Boolean insertCustomerMarket(@Param("saleTargetId") Long saleTargetId, @Param("marketId") Long marketId, @Param("userId") Long userId);

}
