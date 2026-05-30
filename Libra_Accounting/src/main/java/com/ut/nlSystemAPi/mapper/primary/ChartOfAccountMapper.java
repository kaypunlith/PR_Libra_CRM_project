package com.ut.nlSystemAPi.mapper.primary;


import com.ut.nlSystemAPi.model.ChartOfAccount;
import com.ut.nlSystemAPi.model.filter.ChartAccountFilter;
import com.ut.nlSystemAPi.model.response.ChartOfAccount.ChartOfAccountResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.BranchChartAccountResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.CompanyChartAccountResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChartOfAccountMapper {

  List<ChartOfAccountResponse> getList(@Param("filter") ChartAccountFilter filter);

  List<ChartOfAccountResponse> getListData(@Param("filter") ChartAccountFilter filter, @Param("parentId") Long parentId);

  Long countList(@Param("filter") ChartAccountFilter filter);

  List<CompanyChartAccountResponse> getCompanyByAccountChartId(@Param("chartAccId") Long chartAccId);

  List<BranchChartAccountResponse> getBranchByAccountChartId(@Param("chartAccId") Long chartAccId);

  List<ChartOfAccountResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("accountCode") String accountCode, @Param("id") Long id);

  Boolean insert(@Param("chartOfAccount") ChartOfAccount chartOfAccount);

  Boolean insertCompanyId(@Param("chartOfAccountId") Long chartOfAccountId,  @Param("companyId") Long companyId);

  Boolean insertBranchId(@Param("chartOfAccountId") Long chartOfAccountId,  @Param("branchId") Long branchId);

  Boolean deleteChartAccountCompany(@Param("chartOfAccountId") Long chartOfAccountId);

  Boolean deleteChartAccountBranch(@Param("chartOfAccountId") Long chartOfAccountId);


  Boolean update(@Param("chartOfAccount") ChartOfAccount chartOfAccount);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Boolean updateStatus(@Param("id") Long id, @Param("statusId") Long statusId, @Param("userId") Long userId);

  Double getChartAccountBalance(@Param("chartOfAccountId") Long chartOfAccountId);

}