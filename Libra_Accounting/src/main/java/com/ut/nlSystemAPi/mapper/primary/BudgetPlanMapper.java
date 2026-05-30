package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.BudgetPlan;
import com.ut.nlSystemAPi.model.BudgetPlanDetail;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.BudgetPlan.*;
import com.ut.nlSystemAPi.model.response.Dropdown.ChartAccountTypeDropdownResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetPlanMapper {

  List<BudgetPlanResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<BudgetPlanResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean insert(@Param("budgetPlan") BudgetPlan budgetPlan);

  Boolean update(@Param("budgetPlan") BudgetPlan budgetPlan);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  //! Budget Plan Detail

  Boolean insertBudgetPlanDetail(@Param("budgetPlanDetail") BudgetPlanDetail budgetPlanDetail);

  List<BudgetPlanDetailResponse> getBudgetPlanDetail(@Param("budgetPlanId") Long budgetPlanId, @Param("chartAccId") Long chartAccId);

  BudgetPlanDetailResponse getBudgetPlanDashboard(@Param("budgetPlanId") Long budgetPlanId, @Param("chartAccId") Long chartAccId);

  Boolean deleteBudgetPlanDetail(@Param("id") Long id);

  Double getActualBudget(@Param("chartAccountId") Long chartAccountId, @Param("year") String year, @Param("month") String month);

  //! List Table
  List<BudgetPlanSubTableResponse> getChartAccGroupRevenue();

  List<BudgetPlanSubTableResponse> getChartAccGroupCostOfGoodsSold();

  List<BudgetPlanSubTableResponse> getChartAccGroupGrossProfit();

  List<BudgetPlanSubTableResponse> getChartAccGroupTotalExpenses();

  List<BudgetPlanSubTableResponse> getChartAccGroupTotalOtherRevenue();

  List<BudgetPlanSubTableResponse> getChartAccGroupTotalOtherExpenses();

  List<BudgetPlanSubTableResponse> getChartAccGroupEarningsTax();

  List<BudgetPlanSubTableResponse> getChartAccGroupEarningsBeforeTax();

  List<BudgetPlanSubTableResponse> getChartAccGroupProfitLoss();

  //! Account
  List<BudgetPlanSubSubTableResponse> getChartAccFind(@Param("accountGroupId") Long accountGroupId);

  List<BudgetPlanSubSubTableResponse> getChartAcc(@Param("accountGroupId") Long accountGroupId);

}