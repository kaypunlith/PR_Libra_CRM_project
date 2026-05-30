package com.ut.nlSystemAPi.mapper.primary;


import com.ut.nlSystemAPi.model.AccountClosingDateSetting;
import com.ut.nlSystemAPi.model.AccountClosingDateSettingDetail;
import com.ut.nlSystemAPi.model.filter.ChartAccountFilter;
import com.ut.nlSystemAPi.model.filter.ChartAccountNetIncomeFilter;
import com.ut.nlSystemAPi.model.response.ChartOfAccount.ChartOfAccountResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountClosingDateSettingMapper {

  List<ChartOfAccountResponse> getList(@Param("filter") ChartAccountFilter filter);

  Long countList(@Param("filter") ChartAccountFilter filter);

  Long checkDuplicate(@Param("accountCode") String accountCode, @Param("id") Long id);

  Boolean insertGeneralLedger(@Param("accountClosingDateSetting") AccountClosingDateSetting accountClosingDateSetting);

  Boolean insertGeneralLedgerDetail(@Param("accountClosingDateSettingDetail") AccountClosingDateSettingDetail accountClosingDateSettingDetail);

  Double getDataIncome(@Param("filter") ChartAccountNetIncomeFilter filter);

  Double getDataCogs(@Param("filter") ChartAccountNetIncomeFilter filter);

  Double getDataExpense(@Param("filter") ChartAccountNetIncomeFilter filter);
}