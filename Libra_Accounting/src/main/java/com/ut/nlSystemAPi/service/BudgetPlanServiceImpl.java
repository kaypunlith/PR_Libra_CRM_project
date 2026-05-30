package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.BudgetPlanMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.BudgetPlan.BudgetPlanRequest;
import com.ut.nlSystemAPi.model.request.Login.BudgetPlan.BudgetPlanUpdateRequest;
import com.ut.nlSystemAPi.model.response.BudgetPlan.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BudgetPlanServiceImpl implements BudgetPlanService {

    @Autowired
    private BudgetPlanMapper budgetPlanMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    Environment environment;

    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Budget Plan (P&L) (view)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(budgetPlanMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<BudgetPlanResponse> responses = budgetPlanMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/budget-plan/list",null,null,"Budget Plan (P&L)","Budget Plan (P&L) (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/budget-plan/list",line, error.toString(),"Budget Plan (P&L)","Budget Plan (P&L) (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getTableList(HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Budget Plan (P&L) (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<String> tableName = Arrays.asList("Total Revenue", "Cost of Goods Sold", "Gross Profit", "Total Expenses",
                    "Total Other Revenue", "Total Other Expenses", "Earnings Before Interest & Tax", "Earnings Before Tax",
                    "Profit/Loss for the Year");

            List<BudgetPlanTableResponse> responses = new ArrayList<>();

            //! 1 Revenue
            for(int h=0; h < tableName.size(); h++) {
                BudgetPlanTableResponse tableResponse = new BudgetPlanTableResponse();

                List<BudgetPlanSubTableResponse> subTableResponses = new ArrayList<>();

                //! Get chart account group
                tableResponse.setId((long) (h + 1));
                tableResponse.setName(tableName.get(h));

                if(tableName.get(h).equals("Total Revenue")){
                    // 1. Total Revenue
                    subTableResponses = budgetPlanMapper.getChartAccGroupRevenue();

                } else if(tableName.get(h).equals("Cost of Goods Sold")) {
                    // 2. Cost of Goods Sold
                    subTableResponses = budgetPlanMapper.getChartAccGroupCostOfGoodsSold();

                } else if(tableName.get(h).equals("Gross Profit")) {
                    // 3. Gross Profit
                    subTableResponses = budgetPlanMapper.getChartAccGroupGrossProfit();

                } else if(tableName.get(h).equals("Total Expenses")) {
                    // 4. Total Expenses
                    subTableResponses = budgetPlanMapper.getChartAccGroupTotalExpenses();

                } else if(tableName.get(h).equals("Total Other Revenue")) {
                    // 5. Total Other Revenue
                    subTableResponses = budgetPlanMapper.getChartAccGroupTotalOtherRevenue();

                } else if(tableName.get(h).equals("Total Other Expenses")) {
                    // 6. Total Other Expenses
                    subTableResponses = budgetPlanMapper.getChartAccGroupTotalOtherExpenses();

                } else if(tableName.get(h).equals("Earnings Before Interest & Tax")) {
                    // 7. Earnings Before Interest & Tax
                    subTableResponses = budgetPlanMapper.getChartAccGroupEarningsTax();

                } else if(tableName.get(h).equals("Earnings Before Tax")) {
                    // 8. Earnings Before Tax
                    subTableResponses = budgetPlanMapper.getChartAccGroupEarningsBeforeTax();

                } else if(tableName.get(h).equals("Profit/Loss for the Year")) {
                    // 9. Profit/Loss for the Year
                    subTableResponses = budgetPlanMapper.getChartAccGroupProfitLoss();
                }

                if (subTableResponses.size() > 0) {
                    for (int i = 0; i < subTableResponses.size(); i++) {
                        subTableResponses.get(i).setSubSubTableResponses(budgetPlanMapper.getChartAccFind(subTableResponses.get(i).getId()));
                    }
                    tableResponse.setSubTableResponses(subTableResponses);
                }
                responses.add(tableResponse);
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/budget-plan/table-list",null,null,"Budget Plan (P&L)","Budget Plan (P&L) (Table-list)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/budget-plan/table-list",line, error.toString(),"Budget Plan (P&L)","Budget Plan (P&L) (Table-list)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Budget Plan (P&L) (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<String> tableName = Arrays.asList("Total Revenue", "Cost of Goods Sold", "Gross Profit", "Total Expenses",
                    "Total Other Revenue", "Total Other Expenses", "Earnings Before Interest & Tax", "Earnings Before Tax",
                    "Profit/Loss for the Year");

            // Get budget plan
            List<BudgetPlanResponse> responses = budgetPlanMapper.getOne(id);

            List<BudgetPlanTableResponse> budgetPlanTableResponses = new ArrayList<>();

            for(int h=0; h < tableName.size(); h++){
                BudgetPlanTableResponse tableResponse = new BudgetPlanTableResponse();

                List<BudgetPlanSubTableResponse> subTableResponses = new ArrayList<>();

                //! Get chart account group
                tableResponse.setId((long) (h + 1));
                tableResponse.setName(tableName.get(h));

                if(tableName.get(h).equals("Total Revenue")){
                    // 1. Total Revenue
                    subTableResponses = budgetPlanMapper.getChartAccGroupRevenue();

                } else if(tableName.get(h).equals("Cost of Goods Sold")) {
                    // 2. Cost of Goods Sold
                    subTableResponses = budgetPlanMapper.getChartAccGroupCostOfGoodsSold();

                } else if(tableName.get(h).equals("Gross Profit")) {
                    // 3. Gross Profit
                    subTableResponses = budgetPlanMapper.getChartAccGroupGrossProfit();

                } else if(tableName.get(h).equals("Total Expenses")) {
                    // 4. Total Expenses
                    subTableResponses = budgetPlanMapper.getChartAccGroupTotalExpenses();

                } else if(tableName.get(h).equals("Total Other Revenue")) {
                    // 5. Total Other Revenue
                    subTableResponses = budgetPlanMapper.getChartAccGroupTotalOtherRevenue();

                } else if(tableName.get(h).equals("Total Other Expenses")) {
                    // 6. Total Other Expenses
                    subTableResponses = budgetPlanMapper.getChartAccGroupTotalOtherExpenses();

                } else if(tableName.get(h).equals("Earnings Before Interest & Tax")) {
                    // 7. Earnings Before Interest & Tax
                    subTableResponses = budgetPlanMapper.getChartAccGroupEarningsTax();

                } else if(tableName.get(h).equals("Earnings Before Tax")) {
                    // 8. Earnings Before Tax
                    subTableResponses = budgetPlanMapper.getChartAccGroupEarningsBeforeTax();

                } else if(tableName.get(h).equals("Profit/Loss for the Year")) {
                    // 9. Profit/Loss for the Year
                    subTableResponses = budgetPlanMapper.getChartAccGroupProfitLoss();
                }

                //! Find chart account
                if (subTableResponses.size() > 0) {
                    for (int i = 0; i < subTableResponses.size(); i++) {
                        List<BudgetPlanSubSubTableResponse> subSubTableResponses = budgetPlanMapper.getChartAccFind(subTableResponses.get(i).getId());
                        //! Get budget plan detail
                        if(subSubTableResponses.size() > 0){
                            for (int j = 0; j < subSubTableResponses.size(); j++){
                                List<BudgetPlanDetailResponse> budgetPlanDetailResponses = budgetPlanMapper.getBudgetPlanDetail(id, subSubTableResponses.get(j).getId());
                                if(budgetPlanDetailResponses.size() > 0){
                                    subSubTableResponses.get(j).setBudgetPlanDetailResponses(budgetPlanDetailResponses.get(0));
                                }
                            }
                        }
                        // Set budget plan detail
                        subTableResponses.get(i).setSubSubTableResponses(subSubTableResponses);
                    }
                    // Set data to sub table response
                    tableResponse.setSubTableResponses(subTableResponses);
                }
                // Set data to response table
                budgetPlanTableResponses.add(tableResponse);
            }
            // Set data to response
            responses.get(0).setBudgetPlanTableResponses(budgetPlanTableResponses);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/budget-plan/find/{id}",null,null,"Budget Plan (P&L)","Budget Plan (P&L) (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/budget-plan/find/{id}",line, error.toString(),"Budget Plan (P&L)","Budget Plan (P&L) (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> getOneDashboard(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Budget Plan (P&L) (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<String> tableName = Arrays.asList("Total Revenue", "Cost of Goods Sold", "Gross Profit", "Total Expenses", "Net Ordinary Income",
                    "Total Other Revenue", "Total Other Expenses", "Net Other Income", "Earnings Before Interest & Tax", "Earnings Before Tax",
                    "Profit/Loss for the Year");
            //Missing (Net Ordinary Income, Net Other Income)

            // Get budget plan
            List<BudgetPlanResponse> responses = budgetPlanMapper.getOne(id);

            List<BudgetPlanTableResponse> budgetPlanTableResponses = new ArrayList<>();

            for(int h=0; h < tableName.size(); h++){

                BudgetPlanTableResponse tableResponse = new BudgetPlanTableResponse();

                List<BudgetPlanSubTableResponse> subTableResponses = new ArrayList<>();

                //! Get chart account group
                tableResponse.setId((long) (h + 1));
                tableResponse.setName(tableName.get(h));

                if(tableName.get(h).equals("Total Revenue")){
                    // 1. Total Revenue
                    subTableResponses = budgetPlanMapper.getChartAccGroupRevenue();

                } else if(tableName.get(h).equals("Cost of Goods Sold")) {
                    // 2. Cost of Goods Sold
                    subTableResponses = budgetPlanMapper.getChartAccGroupCostOfGoodsSold();

                } else if(tableName.get(h).equals("Gross Profit")) {
                    // 3. Gross Profit
                    subTableResponses = budgetPlanMapper.getChartAccGroupGrossProfit();

                } else if(tableName.get(h).equals("Total Expenses")) {
                    // 4. Total Expenses
                    subTableResponses = budgetPlanMapper.getChartAccGroupTotalExpenses();

                } else if(tableName.get(h).equals("Total Other Revenue")) {
                    // 5. Total Other Revenue
                    subTableResponses = budgetPlanMapper.getChartAccGroupTotalOtherRevenue();

                } else if(tableName.get(h).equals("Total Other Expenses")) {
                    // 6. Total Other Expenses
                    subTableResponses = budgetPlanMapper.getChartAccGroupTotalOtherExpenses();

                } else if(tableName.get(h).equals("Earnings Before Interest & Tax")) {
                    // 7. Earnings Before Interest & Tax
                    subTableResponses = budgetPlanMapper.getChartAccGroupEarningsTax();

                } else if(tableName.get(h).equals("Earnings Before Tax")) {
                    // 8. Earnings Before Tax
                    subTableResponses = budgetPlanMapper.getChartAccGroupEarningsBeforeTax();

                } else if(tableName.get(h).equals("Profit/Loss for the Year")) {
                    // 9. Profit/Loss for the Year
                    subTableResponses = budgetPlanMapper.getChartAccGroupProfitLoss();
                }

                if (subTableResponses.size() > 0) {
                    for (int i = 0; i < subTableResponses.size(); i++) {

                        List<BudgetPlanSubSubTableResponse> subSubTableResponsesTemp  = new ArrayList<>();
                        List<BudgetPlanSubSubTableResponse> subSubTableResponses = budgetPlanMapper.getChartAcc(subTableResponses.get(i).getId());
                        //! Get budget plan detail
                        if(subSubTableResponses.size() > 0){
                            for (int j = 0; j < subSubTableResponses.size(); j++){
                                Double totalBudget;
                                Double totalActual;
                                List<BudgetPlanDetailResponse> budgetPlanDetailResponses = new ArrayList<>();
                                {
                                    Double budgetPlz = 0D;

                                    Long chartAccountId = subSubTableResponses.get(j).getId();
                                    String year = responses.get(0).getYear();

                                    BudgetPlanDetailResponse budgetPlanDetailResponseTmp = new BudgetPlanDetailResponse();

                                    BudgetPlanDetailResponse budgetPlanDetailResponse = budgetPlanMapper.getBudgetPlanDashboard(id, subSubTableResponses.get(j).getId());

                                    if(budgetPlanDetailResponse != null){
                                        budgetPlanDetailResponseTmp = budgetPlanDetailResponse;
                                    }

                                    // Month 1
                                    Double actualBudgetM1 = budgetPlanMapper.getActualBudget(chartAccountId, year, "1");
                                    budgetPlanDetailResponseTmp.setM1Actual(actualBudgetM1);

                                    // Month 2
                                    Double actualBudgetM2 = budgetPlanMapper.getActualBudget(chartAccountId, year, "2");
                                    budgetPlanDetailResponseTmp.setM2Actual(actualBudgetM2);

                                    // Month 3
                                    Double actualBudgetM3 = budgetPlanMapper.getActualBudget(chartAccountId, year, "3");
                                    budgetPlanDetailResponseTmp.setM3Actual(actualBudgetM3);


                                    // Month 4
                                    Double actualBudgetM4 = budgetPlanMapper.getActualBudget(chartAccountId, year, "4");
                                    budgetPlanDetailResponseTmp.setM4Actual(actualBudgetM4);

                                    // Month 5
                                    Double actualBudgetM5 = budgetPlanMapper.getActualBudget(chartAccountId, year, "5");
                                    budgetPlanDetailResponseTmp.setM5Actual(actualBudgetM5);

                                    // Month 6
                                    Double actualBudgetM6 = budgetPlanMapper.getActualBudget(chartAccountId, year, "6");
                                    budgetPlanDetailResponseTmp.setM6Actual(actualBudgetM6);

                                    // Month 7
                                    Double actualBudgetM7 = budgetPlanMapper.getActualBudget(chartAccountId, year, "7");
                                    budgetPlanDetailResponseTmp.setM7Actual(actualBudgetM7);

                                    // Month 8
                                    Double actualBudgetM8 = budgetPlanMapper.getActualBudget(chartAccountId, year, "8");
                                    budgetPlanDetailResponseTmp.setM8Actual(actualBudgetM8);

                                    // Month 9
                                    Double actualBudgetM9 = budgetPlanMapper.getActualBudget(chartAccountId, year, "9");
                                    budgetPlanDetailResponseTmp.setM9Actual(actualBudgetM9);

                                    // Month 10
                                    Double actualBudgetM10 = budgetPlanMapper.getActualBudget(chartAccountId, year, "10");
                                    budgetPlanDetailResponseTmp.setM10Actual(actualBudgetM10);

                                    // Month 11
                                    Double actualBudgetM11 = budgetPlanMapper.getActualBudget(chartAccountId, year, "11");
                                    budgetPlanDetailResponseTmp.setM11Actual(actualBudgetM11);

                                    // Month 12
                                    Double actualBudgetM12 = budgetPlanMapper.getActualBudget(chartAccountId, year, "12");
                                    budgetPlanDetailResponseTmp.setM12Actual(actualBudgetM12);


                                    if(budgetPlanDetailResponse != null){
                                        Double overBudget;
                                        budgetPlanDetailResponseTmp.setM1OverBudget(actualBudgetM1 - budgetPlanDetailResponse.getM1());
                                        overBudget = (actualBudgetM1 / budgetPlanDetailResponse.getM1()) * 100;
                                        budgetPlanDetailResponseTmp.setM1OverBudgetAvg((Double.isNaN(overBudget) || Double.isInfinite(overBudget)) ? 0.0 : overBudget);

                                        budgetPlanDetailResponseTmp.setM2OverBudget(actualBudgetM2 - budgetPlanDetailResponse.getM2());
                                        overBudget = (actualBudgetM2 / budgetPlanDetailResponse.getM2()) * 100;
                                        budgetPlanDetailResponseTmp.setM2OverBudgetAvg((Double.isNaN(overBudget) || Double.isInfinite(overBudget)) ? 0.0 : overBudget);

                                        budgetPlanDetailResponseTmp.setM3OverBudget(actualBudgetM3 - budgetPlanDetailResponse.getM3());
                                        overBudget = (actualBudgetM3 / budgetPlanDetailResponse.getM3()) * 100;
                                        budgetPlanDetailResponseTmp.setM3OverBudgetAvg((Double.isNaN(overBudget) || Double.isInfinite(overBudget)) ? 0.0 : overBudget);

                                        budgetPlanDetailResponseTmp.setM4OverBudget(actualBudgetM4 - budgetPlanDetailResponse.getM4());
                                        overBudget = (actualBudgetM4 / budgetPlanDetailResponse.getM4()) * 100;
                                        budgetPlanDetailResponseTmp.setM4OverBudgetAvg((Double.isNaN(overBudget) || Double.isInfinite(overBudget)) ? 0.0 : overBudget);

                                        budgetPlanDetailResponseTmp.setM5OverBudget(actualBudgetM5 - budgetPlanDetailResponse.getM5());
                                        overBudget = (actualBudgetM5 / budgetPlanDetailResponse.getM5()) * 100;
                                        budgetPlanDetailResponseTmp.setM5OverBudgetAvg((Double.isNaN(overBudget) || Double.isInfinite(overBudget)) ? 0.0 : overBudget);

                                        budgetPlanDetailResponseTmp.setM6OverBudget(actualBudgetM6 - budgetPlanDetailResponse.getM6());
                                        overBudget = (actualBudgetM6 / budgetPlanDetailResponse.getM6()) * 100;
                                        budgetPlanDetailResponseTmp.setM6OverBudgetAvg((Double.isNaN(overBudget) || Double.isInfinite(overBudget)) ? 0.0 : overBudget);

                                        budgetPlanDetailResponseTmp.setM7OverBudget(actualBudgetM7 - budgetPlanDetailResponse.getM7());
                                        overBudget = (actualBudgetM7 / budgetPlanDetailResponse.getM7()) * 100;
                                        budgetPlanDetailResponseTmp.setM7OverBudgetAvg((Double.isNaN(overBudget) || Double.isInfinite(overBudget)) ? 0.0 : overBudget);

                                        budgetPlanDetailResponseTmp.setM8OverBudget(actualBudgetM8 - budgetPlanDetailResponse.getM8());
                                        overBudget = (actualBudgetM8 / budgetPlanDetailResponse.getM8()) * 100;
                                        budgetPlanDetailResponseTmp.setM8OverBudgetAvg((Double.isNaN(overBudget) || Double.isInfinite(overBudget)) ? 0.0 : overBudget);

                                        budgetPlanDetailResponseTmp.setM9OverBudget(actualBudgetM9 - budgetPlanDetailResponse.getM9());
                                        overBudget = (actualBudgetM9 / budgetPlanDetailResponse.getM9()) * 100;
                                        budgetPlanDetailResponseTmp.setM9OverBudgetAvg((Double.isNaN(overBudget) || Double.isInfinite(overBudget)) ? 0.0 : overBudget);

                                        budgetPlanDetailResponseTmp.setM10OverBudget(actualBudgetM10 - budgetPlanDetailResponse.getM10());
                                        overBudget = (actualBudgetM10 / budgetPlanDetailResponse.getM10()) * 100;
                                        budgetPlanDetailResponseTmp.setM10OverBudgetAvg((Double.isNaN(overBudget) || Double.isInfinite(overBudget)) ? 0.0 : overBudget);

                                        budgetPlanDetailResponseTmp.setM11OverBudget(actualBudgetM11 - budgetPlanDetailResponse.getM11());
                                        overBudget = (actualBudgetM11 / budgetPlanDetailResponse.getM11()) * 100;
                                        budgetPlanDetailResponseTmp.setM11OverBudgetAvg((Double.isNaN(overBudget) || Double.isInfinite(overBudget)) ? 0.0 : overBudget);

                                        budgetPlanDetailResponseTmp.setM12OverBudget(actualBudgetM12 - budgetPlanDetailResponse.getM12());
                                        overBudget = (actualBudgetM12 / budgetPlanDetailResponse.getM12()) * 100;
                                        budgetPlanDetailResponseTmp.setM12OverBudgetAvg((Double.isNaN(overBudget) || Double.isInfinite(overBudget)) ? 0.0 : overBudget);

                                        budgetPlz = budgetPlanDetailResponse.getM1() + budgetPlanDetailResponse.getM2() + budgetPlanDetailResponse.getM3() +  budgetPlanDetailResponse.getM3() +
                                            budgetPlanDetailResponse.getM5() +  budgetPlanDetailResponse.getM6() +  budgetPlanDetailResponse.getM7() +
                                            budgetPlanDetailResponse.getM8() +  budgetPlanDetailResponse.getM9() +  budgetPlanDetailResponse.getM10() +
                                            budgetPlanDetailResponse.getM11() + budgetPlanDetailResponse.getM12();
                                    } else {
                                        budgetPlanDetailResponseTmp.setM1OverBudget(actualBudgetM1);
                                        budgetPlanDetailResponseTmp.setM1OverBudgetAvg(0D);

                                        budgetPlanDetailResponseTmp.setM2OverBudget(actualBudgetM2);
                                        budgetPlanDetailResponseTmp.setM2OverBudgetAvg(0D);

                                        budgetPlanDetailResponseTmp.setM3OverBudget(actualBudgetM3);
                                        budgetPlanDetailResponseTmp.setM3OverBudgetAvg(0D);

                                        budgetPlanDetailResponseTmp.setM4OverBudget(actualBudgetM4);
                                        budgetPlanDetailResponseTmp.setM4OverBudgetAvg(0D);

                                        budgetPlanDetailResponseTmp.setM5OverBudget(actualBudgetM5);
                                        budgetPlanDetailResponseTmp.setM5OverBudgetAvg(0D);

                                        budgetPlanDetailResponseTmp.setM6OverBudget(actualBudgetM6);
                                        budgetPlanDetailResponseTmp.setM6OverBudgetAvg(0D);

                                        budgetPlanDetailResponseTmp.setM7OverBudget(actualBudgetM7);
                                        budgetPlanDetailResponseTmp.setM7OverBudgetAvg(0D);

                                        budgetPlanDetailResponseTmp.setM8OverBudget(actualBudgetM8);
                                        budgetPlanDetailResponseTmp.setM8OverBudgetAvg(0D);

                                        budgetPlanDetailResponseTmp.setM9OverBudget(actualBudgetM9);
                                        budgetPlanDetailResponseTmp.setM9OverBudgetAvg(0D);

                                        budgetPlanDetailResponseTmp.setM10OverBudget(actualBudgetM10);
                                        budgetPlanDetailResponseTmp.setM10OverBudgetAvg(0D);

                                        budgetPlanDetailResponseTmp.setM11OverBudget(actualBudgetM11);
                                        budgetPlanDetailResponseTmp.setM11OverBudgetAvg(0D);

                                        budgetPlanDetailResponseTmp.setM12OverBudget(actualBudgetM12);
                                        budgetPlanDetailResponseTmp.setM12OverBudgetAvg(0D);

                                    }

                                    Double actualBudget = actualBudgetM1 + actualBudgetM2 + actualBudgetM3 + actualBudgetM4 + actualBudgetM5 + actualBudgetM6 + actualBudgetM7 + actualBudgetM8 + actualBudgetM9 + actualBudgetM10 + actualBudgetM11 + actualBudgetM12;

                                    totalBudget = budgetPlz;
                                    totalActual = actualBudget;
                                    budgetPlanDetailResponses.add(budgetPlanDetailResponseTmp);
                                }
                                // Set budget plan data
                                subSubTableResponses.get(j).setBudgetPlanDetailResponses(budgetPlanDetailResponses.get(0));

                                if(totalActual != 0 || totalBudget != 0) {
                                    subSubTableResponsesTemp.add(subSubTableResponses.get(j));
                                }
                            }
                        }
                        //Set sub sub data (chart account) Issues
                        subTableResponses.get(i).setSubSubTableResponses(subSubTableResponsesTemp);
                    }
                    // Set sub table (chart account group)
                    tableResponse.setSubTableResponses(subTableResponses);
                }
                // Set table data
                budgetPlanTableResponses.add(tableResponse);
            }
            // Set data to response
            responses.get(0).setBudgetPlanTableResponses(budgetPlanTableResponses);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/budget-plan/find/{id}",null,null,"Budget Plan (P&L)","Budget Plan (P&L) (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/budget-plan/find/{id}",line, error.toString(),"Budget Plan (P&L)","Budget Plan (P&L) (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(BudgetPlanRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Budget Plan (P&L) (add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if(budgetPlanMapper.checkDuplicate(request.getName(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate budget plan's name.", false));
            }

            BudgetPlan budgetPlan = new BudgetPlan();
            budgetPlan.setCompanyId(request.getCompanyId());
            budgetPlan.setBranchId(request.getBranchId());
            budgetPlan.setYear(request.getYear());
            budgetPlan.setName(request.getName());
            budgetPlan.setDescription(request.getDescription());

            budgetPlan.setCreatedBy(userId);
            budgetPlan.setIsActive(1);

            Boolean result = budgetPlanMapper.insert(budgetPlan);

            //! Insert budget plan detail
            if(request.getBudgetPlanDetailRequests().size() > 0){
                for (int i = 0; i < request.getBudgetPlanDetailRequests().size(); i++){
                    BudgetPlanDetail budgetPlanDetail = new BudgetPlanDetail();
                    budgetPlanDetail.setBudgetPlsId(budgetPlan.getId());
                    budgetPlanDetail.setChartAccountId(request.getBudgetPlanDetailRequests().get(i).getChartAccountId());
                    budgetPlanDetail.setM1(request.getBudgetPlanDetailRequests().get(i).getM1());
                    budgetPlanDetail.setM2(request.getBudgetPlanDetailRequests().get(i).getM2());
                    budgetPlanDetail.setM3(request.getBudgetPlanDetailRequests().get(i).getM3());
                    budgetPlanDetail.setM4(request.getBudgetPlanDetailRequests().get(i).getM4());
                    budgetPlanDetail.setM5(request.getBudgetPlanDetailRequests().get(i).getM5());
                    budgetPlanDetail.setM6(request.getBudgetPlanDetailRequests().get(i).getM6());
                    budgetPlanDetail.setM7(request.getBudgetPlanDetailRequests().get(i).getM7());
                    budgetPlanDetail.setM8(request.getBudgetPlanDetailRequests().get(i).getM8());
                    budgetPlanDetail.setM9(request.getBudgetPlanDetailRequests().get(i).getM9());
                    budgetPlanDetail.setM10(request.getBudgetPlanDetailRequests().get(i).getM10());
                    budgetPlanDetail.setM11(request.getBudgetPlanDetailRequests().get(i).getM11());
                    budgetPlanDetail.setM12(request.getBudgetPlanDetailRequests().get(i).getM12());

                    result = budgetPlanMapper.insertBudgetPlanDetail(budgetPlanDetail);
                }
            }

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/budget-plan/add",null,null,"Budget Plan (PL)","Budget Plan (PL) (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/budget-plan/add",line, error.toString(),"Budget Plan (PL)","Budget Plan (PL) (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    @Override
    public ResponseMessage<BaseResult> update(BudgetPlanUpdateRequest updateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Budget Plan (P&L) (edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if(budgetPlanMapper.checkDuplicate(updateRequest.getName(), updateRequest.getId()) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate budget plan's name.", false));
            }

            BudgetPlan budgetPlan = new BudgetPlan();
            budgetPlan.setId(updateRequest.getId());
            budgetPlan.setCompanyId(updateRequest.getCompanyId());
            budgetPlan.setBranchId(updateRequest.getBranchId());
            budgetPlan.setYear(updateRequest.getYear());
            budgetPlan.setName(updateRequest.getName());
            budgetPlan.setDescription(updateRequest.getDescription());

            budgetPlan.setCreatedBy(userId);
            budgetPlan.setIsActive(1);

            Boolean result = budgetPlanMapper.update(budgetPlan);

            if(result){
                //! Delete the old detail
                budgetPlanMapper.deleteBudgetPlanDetail(budgetPlan.getId());

                //! Insert budget plan detail
                if(updateRequest.getBudgetPlanDetailRequests().size() > 0){
                    for (int i = 0; i < updateRequest.getBudgetPlanDetailRequests().size(); i++){
                        BudgetPlanDetail budgetPlanDetail = new BudgetPlanDetail();
                        budgetPlanDetail.setBudgetPlsId(budgetPlan.getId());
                        budgetPlanDetail.setChartAccountId(updateRequest.getBudgetPlanDetailRequests().get(i).getChartAccountId());
                        budgetPlanDetail.setM1(updateRequest.getBudgetPlanDetailRequests().get(i).getM1());
                        budgetPlanDetail.setM2(updateRequest.getBudgetPlanDetailRequests().get(i).getM2());
                        budgetPlanDetail.setM3(updateRequest.getBudgetPlanDetailRequests().get(i).getM3());
                        budgetPlanDetail.setM4(updateRequest.getBudgetPlanDetailRequests().get(i).getM4());
                        budgetPlanDetail.setM5(updateRequest.getBudgetPlanDetailRequests().get(i).getM5());
                        budgetPlanDetail.setM6(updateRequest.getBudgetPlanDetailRequests().get(i).getM6());
                        budgetPlanDetail.setM7(updateRequest.getBudgetPlanDetailRequests().get(i).getM7());
                        budgetPlanDetail.setM8(updateRequest.getBudgetPlanDetailRequests().get(i).getM8());
                        budgetPlanDetail.setM9(updateRequest.getBudgetPlanDetailRequests().get(i).getM9());
                        budgetPlanDetail.setM10(updateRequest.getBudgetPlanDetailRequests().get(i).getM10());
                        budgetPlanDetail.setM11(updateRequest.getBudgetPlanDetailRequests().get(i).getM11());
                        budgetPlanDetail.setM12(updateRequest.getBudgetPlanDetailRequests().get(i).getM12());

                        result = budgetPlanMapper.insertBudgetPlanDetail(budgetPlanDetail);
                    }
                }
            }

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/budget-plan/update",null,null,"Budget Plan (PL)","Budget Plan (PL) (Update)","Update",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/budget-plan/update",line, error.toString(),"Budget Plan (PL)","Budget Plan (PL) (Update)","Update",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();

            if (permissionMapper.checkPermission(userId, "Budget Plan (P&L) (delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = budgetPlanMapper.delete(id, userId);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/budget-plan/delete/{id}", null, null, "Budget Plan (P&L)", "Budget Plan (P&L) (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/budget-plan/delete/{id}", line, error.toString(), "Budget Plan (P&L)", "Budget Plan (P&L) (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}