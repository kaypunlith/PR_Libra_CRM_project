package com.ut.nlSystemAPi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ReportBankPaidMapper;
import com.ut.nlSystemAPi.mapper.primary.ReportGroupPayrollMapper;
import com.ut.nlSystemAPi.mapper.primary.ReportManualPaidMapper;
import com.ut.nlSystemAPi.mapper.primary.ReportPaySlip;
import com.ut.nlSystemAPi.mapper.primary.ReportPayrollBalanceMapper;
import com.ut.nlSystemAPi.mapper.primary.ReportPayrollSummaryMapper;
import com.ut.nlSystemAPi.mapper.primary.ReportTotalManualPaidMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.response.DateResponse;
import com.ut.nlSystemAPi.model.response.OtherPayReportResponse;
import com.ut.nlSystemAPi.model.response.PaySlipResponse;
import com.ut.nlSystemAPi.model.response.PayrollBalanceList;
import com.ut.nlSystemAPi.model.response.PayrollDepartmentList;
import com.ut.nlSystemAPi.model.response.PayrollEmployeesList;
import com.ut.nlSystemAPi.model.response.PayrollReportFilter;
import com.ut.nlSystemAPi.model.response.PayrollSummary;
import com.ut.nlSystemAPi.model.response.ReportPayrollItem;
import com.ut.nlSystemAPi.model.response.ReportPayrollItemType;

@SuppressWarnings("all")
@Service
public class ReportPayrollServiceImpl implements ReportPayrollService {

  @Autowired
  private ReportPayrollSummaryMapper reportPayrollSummaryMapper;

  @Autowired
  private ReportManualPaidMapper reportManualPaidMapper;

  @Autowired
  private ReportBankPaidMapper reportBankPaidMapper;

  @Autowired
  private ReportGroupPayrollMapper reportGroupPayrollMapper;

  @Autowired
  private ReportPayrollBalanceMapper reportPayrollBalanceMapper;

  @Autowired
  private ReportTotalManualPaidMapper reportTotalManualPaidMapper;

  @Autowired
  private ReportPaySlip reportPaySlip;

  @Autowired
  private UserService userService;

  @Autowired
  private MessageService messageService;

  @Autowired
  private PermissionMapper permissionMapper;

    // Report Payroll Summary
    public ResponseMessage<BaseResult> getListSummary(PayrollReportFilter filter) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Report Payroll Summary (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        Pagination pagination = new Pagination();
        pagination.setPage(filter.getPage());
        pagination.setRowsPerPage(filter.getRowsPerPage());

        filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

        // get date by filter for title report
        DateResponse dateResponseList = reportPayrollSummaryMapper.getDate(filter);

        List<PayrollSummary> payrollSummaryList = reportPayrollSummaryMapper.getList(filter);
        if (payrollSummaryList != null && payrollSummaryList.size() > 0) {
            for (int i = 0; i < payrollSummaryList.size(); i++){
                Long departmentId = payrollSummaryList.get(i).getId();

                // amount By Manual
                Float amountByManual = reportPayrollSummaryMapper.amountByManual(departmentId, filter.getDate());
                // amount By ATM
                Float amountByATM = reportPayrollSummaryMapper.amountByATM(departmentId, filter.getDate());
                // Total amount of Manual and ATM
                Float totalAmount = (amountByManual + amountByATM);

                payrollSummaryList.get(i).setAmountManual(amountByManual);
                payrollSummaryList.get(i).setAmountAtm(amountByATM);

                // current salary
                payrollSummaryList.get(i).setCurrentMonth(totalAmount);

                // total amount by Date Previous Month
                String datePrevious = payrollSummaryList.get(i).getDatePreviousMonth();

//                System.out.println("--------------------------------------------");
//                System.out.println("datePrevious "+ datePrevious);
//                System.out.println("departmentId "+ departmentId);
//                System.out.println("departmentName" + payrollSummaryList.get(i).getDepartmentName());
//                System.out.println("--------------------------------------------");
                Float previousMonthAmount = reportPayrollSummaryMapper.lastMonthAmount(departmentId, datePrevious);
                payrollSummaryList.get(i).setPreviousMonth(previousMonthAmount);
//                payrollSummaryList.get(i).setPreviousMonth(10F);
                // different amount
                payrollSummaryList.get(i).setAmountDifferent(totalAmount - previousMonthAmount);
            }
        }
        return ResponseMessageUtils.makeResponse(true, messageService.messageResponseDateReport("Success", dateResponseList, payrollSummaryList, true));
    }

    // Report Bank Paid
    public ResponseMessage<BaseResult> getBankPaid(PayrollReportFilter filter) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Report Payroll Bank Paid (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        if (filter != null) {
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
        }

        List<PayrollDepartmentList> departmentLists = reportBankPaidMapper.listDepatment(filter, filter.getDate(), userId);
        if (departmentLists != null && departmentLists.size() > 0){
            for (int i = 0; i < departmentLists.size(); i++){
                Long departmentId = departmentLists.get(i).getId();
                departmentLists.get(i).setBankPaidList(reportBankPaidMapper.getListBankPaid(departmentId, filter.getDate()));
            }
        }

        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", departmentLists, true));
    }

    // Report Manual Paid
    public ResponseMessage<BaseResult> getManualPaid(PayrollReportFilter filter) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Report Payroll Manual Paid (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        if (filter != null) {
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
        }

        // List department
        List<PayrollDepartmentList> departmentLists = reportManualPaidMapper.listDepatment(filter, filter.getDate(), userId);
        if (departmentLists != null && departmentLists.size()> 0) {
            for (int i = 0; i < departmentLists.size(); i++){
                Long departmentId = departmentLists.get(i).getId();

                // List Employees
                departmentLists.get(i).setEmployeesList(reportManualPaidMapper.listEmployees(departmentId, filter.getDate()));
                List<PayrollEmployeesList> employeesLists = departmentLists.get(i).getEmployeesList();

                if (employeesLists != null && employeesLists.size() > 0){
                    for (int j = 0; j < employeesLists.size(); j++){
                        Long employeesId = employeesLists.get(j).getId();

                        if (employeesLists.get(j).getNote().isEmpty()){
                            employeesLists.get(j).setNote("");
                        }

                        // List Payroll Type
                        employeesLists.get(j).setPayrollTypes(reportManualPaidMapper.listPayrollType());
                        List<ReportPayrollItemType> payrollItemTypes = employeesLists.get(j).getPayrollTypes();

                        if (payrollItemTypes != null && payrollItemTypes.size() > 0){
                            for (int k = 0; k < payrollItemTypes.size(); k++){
                                Long payrollTypeId = payrollItemTypes.get(k).getId();
                                // List payroll Item
                                payrollItemTypes.get(k).setPayrollItemList(reportManualPaidMapper.listPayrollItem(payrollTypeId, employeesId));
                                List<ReportPayrollItem> payrollItemList = payrollItemTypes.get(k).getPayrollItemList();
                                if (payrollItemList != null && payrollItemList.size() > 0){
                                    for (int a = 0; a < payrollItemList.size(); a++){
                                        Long payrollItemId = payrollItemList.get(a).getId();
                                        Float amount = reportManualPaidMapper.getAmount(payrollItemId, employeesId, filter.getDate());
                                        if (amount == null) {
                                            payrollItemList.get(a).setAmount((float) 0);
                                        } else {
                                            payrollItemList.get(a).setAmount(amount);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", departmentLists, true));
    }

    // Report Group Payroll
    public ResponseMessage<BaseResult> getGroupPayroll(PayrollReportFilter filter) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Report Group Payroll (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        if (filter != null) {
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
        }

        // List department
        List<PayrollDepartmentList> departmentLists = reportGroupPayrollMapper.listDepartment(filter, userId);
        if (departmentLists != null && departmentLists.size()> 0) {
            for (int i = 0; i < departmentLists.size(); i++){
                Long departmentId = departmentLists.get(i).getId();

                // List Employees
                departmentLists.get(i).setEmployeesList(reportGroupPayrollMapper.listEmployees(departmentId, filter.getDate()));
                List<PayrollEmployeesList> employeesLists = departmentLists.get(i).getEmployeesList();

                if (employeesLists != null && employeesLists.size() > 0){
                    for (int j = 0; j < employeesLists.size(); j++){
                        Long employeesId = employeesLists.get(j).getId();

                        // check data
                        if (employeesLists.get(j).getNote().isEmpty()){
                            employeesLists.get(j).setNote("");
                        }

                        // List Payroll Type
                        employeesLists.get(j).setPayrollTypes(reportGroupPayrollMapper.listPayrollType());
                        List<ReportPayrollItemType> payrollItemTypes = employeesLists.get(j).getPayrollTypes();

                        if (payrollItemTypes != null && payrollItemTypes.size() > 0){
                            for (int k = 0; k < payrollItemTypes.size(); k++){
                                Long payrollTypeId = payrollItemTypes.get(k).getId();
                                // List payroll Item
                                payrollItemTypes.get(k).setPayrollItemList(reportGroupPayrollMapper.listPayrollItem(payrollTypeId, employeesId));
                                List<ReportPayrollItem> payrollItemList = payrollItemTypes.get(k).getPayrollItemList();
                                if (payrollItemList != null && payrollItemList.size() > 0){
                                    for (int a = 0; a < payrollItemList.size(); a++){
                                        Long payrollItemId = payrollItemList.get(a).getId();
                                        Float amount = reportGroupPayrollMapper.getAmount(payrollItemId, employeesId, filter.getDate());
                                        if (amount == null) {
                                            payrollItemList.get(a).setAmount((float) 0);
                                        } else {
                                            payrollItemList.get(a).setAmount(amount);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", departmentLists, true));
    }

    // Report Payroll Balance
    public ResponseMessage<BaseResult> getPayrollBalance(PayrollReportFilter filter) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
//        if(permissionMapper.checkPermission(userId,"Report Payroll Balance (View)") == 0){
//            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
//        }

        if (filter != null) {
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
        }

        // get date by filter for title report
        DateResponse dateResponseList = reportPayrollBalanceMapper.getDate(filter);

        // List department
        List<PayrollBalanceList> payrollBalanceLists = reportPayrollBalanceMapper.listPayrollBalance(filter);
        if (payrollBalanceLists != null && payrollBalanceLists.size()> 0) {
            for (int i = 0; i < payrollBalanceLists.size(); i++){

                Float totalAmount = reportPayrollBalanceMapper.totalAmount(filter.getDate());
                payrollBalanceLists.get(i).setTotal(totalAmount);
                double receiveSalary = payrollBalanceLists.get(i).getReceiveSalary();
                payrollBalanceLists.get(i).setGrandTotal(receiveSalary + totalAmount);

                // List Payroll Item Type
                payrollBalanceLists.get(i).setPayrollItemList(reportPayrollBalanceMapper.listPayrollItem());
                List<ReportPayrollItem> reportPayrollItems = payrollBalanceLists.get(i).getPayrollItemList();
                if (reportPayrollItems != null && reportPayrollItems.size() > 0) {
                    for (int j = 0; j < reportPayrollItems.size(); j++){
                        Float amount = reportPayrollBalanceMapper.getAmount(reportPayrollItems.get(j).getId(), filter.getDate());
                        reportPayrollItems.get(j).setAmount(amount);
                        // set total amount
                        payrollBalanceLists.get(i).setTotalAmountDeposit(reportPayrollItems.get(4).getAmount());
                        payrollBalanceLists.get(i).setTotalLoanAmount(reportPayrollItems.get(5).getAmount());
                        payrollBalanceLists.get(i).setDonate(reportPayrollItems.get(7).getAmount());
                        payrollBalanceLists.get(i).setAmountInstallment(reportPayrollItems.get(8).getAmount());
                        payrollBalanceLists.get(i).setProvidentFund(reportPayrollItems.get(9).getAmount());
                    }
                }
            }
        }

        return ResponseMessageUtils.makeResponse(true, messageService.messageResponseDate("Success",dateResponseList.getDate(), payrollBalanceLists, true));
    }

    // Total Report payroll Manual Paid
    public ResponseMessage<BaseResult> getTotalManualPaid(PayrollReportFilter filter) {
        // Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Report Payroll Manual Paid (View)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        if (filter != null) {
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
        }

        // get date by filter for title report
        DateResponse dateResponseList = reportTotalManualPaidMapper.getDate(filter);

        // List department
        List<PayrollBalanceList> payrollBalanceLists = reportTotalManualPaidMapper.listPayrollManualPaid(filter);
        if (payrollBalanceLists != null && payrollBalanceLists.size()> 0) {
            for (int i = 0; i < payrollBalanceLists.size(); i++){

                // List Payroll Item Type
                payrollBalanceLists.get(i).setPayrollItemList(reportTotalManualPaidMapper.listPayrollItem());
                List<ReportPayrollItem> reportPayrollItems = payrollBalanceLists.get(i).getPayrollItemList();
                if (reportPayrollItems != null && reportPayrollItems.size() > 0) {
                    for (int j = 0; j < reportPayrollItems.size(); j++){
                        Float amount = reportPayrollBalanceMapper.getAmount(reportPayrollItems.get(j).getId(), filter.getDate());
                        reportPayrollItems.get(j).setAmount(amount);
                    }
                }
            }
        }

        return ResponseMessageUtils.makeResponse(true, messageService.messageResponseDate("Success",dateResponseList.getDate(), payrollBalanceLists, true));
    }

    
    public ResponseMessage<BaseResult> getPayslip(PayrollReportFilter filter) {
        //! Check Permission
        Long userId   = userService.getUserAuth().getId();
        if(permissionMapper.checkPermission(userId,"Pay slip (Report)") == 0){
            return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
        }

        if (filter != null) {
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
        }
        //! get date by filter for title report
         List<PaySlipResponse> paySlipResponse = reportPaySlip.getReportPaySlip(filter);
         // Check if we have any employees
         if (paySlipResponse == null || paySlipResponse.isEmpty()) {
             return ResponseMessageUtils.makeResponse(true, messageService.message("Not Found data", paySlipResponse, true));
         }
         
         if(paySlipResponse.size()>0){
            for (int i = 0; i < paySlipResponse.size(); i++){
                filter.setEmployeeId(paySlipResponse.get(i).getEmployeeId());
                Float DayRate =  reportPaySlip.getDayRate(paySlipResponse.get(i).getEmployeeId());
                Float DayRateNight =  reportPaySlip.getDayRateNight(paySlipResponse.get(i).getEmployeeId());

                //!List OtherPay Report
                Long PayrollId = reportPaySlip.getPayrollId(filter);
                List<OtherPayReportResponse> otherPayReportResponses = reportPaySlip.getOtherPayReport(filter,PayrollId);
				
				float boneScan = 0f;
				float ctScan = 0f;  
				float mri = 0f;
				float roll = 0f;
				float smallSurg = 0f;
				float bloc = 0f;
				float others = 0f;
				if (otherPayReportResponses != null && !otherPayReportResponses.isEmpty()) {
					for (OtherPayReportResponse opr : otherPayReportResponses) {
						String key = opr.getName() == null ? "" : opr.getName().toUpperCase().replaceAll("[\\s-]", "");
						float amount = opr.getAmount() == null ? 0f : opr.getAmount();
						switch (key) {
							case "BONESCAN":
							case "BONUSSCAN": // handle possible label variant
								boneScan = amount;
								break;
							case "CTSCAN":
								ctScan = amount;
								break;
							case "MRI":
								mri = amount;
								break;
							case "ROLL":
								roll = amount;
								break;
							case "SMALLSURG":
							case "SMALLSURGERY":
								smallSurg = amount;
								break;
							case "BLOC":
								bloc = amount;
								break;
							default:
								others = amount;
						}
					}
				}
				paySlipResponse.get(i).setBoneScan(boneScan);
				paySlipResponse.get(i).setCtScan(ctScan);
				paySlipResponse.get(i).setMri(mri);
				paySlipResponse.get(i).setRoll(roll);
				paySlipResponse.get(i).setSmallSurg(smallSurg);
				paySlipResponse.get(i).setBloc(bloc);
				paySlipResponse.get(i).setOther(others);
  
                //! Select data 
                Float listSumDayMonFri = reportPaySlip.getListSumDayMonFri(filter);
                Float listSumDaySatSun = reportPaySlip.getListSumDaySatSun(filter);
                Float listSumNight = reportPaySlip.getListSumNight(filter);

                //! Add null checks for the values
                Float monFriValue = (listSumDayMonFri != null) ? listSumDayMonFri : 0f;
                Float satSunValue = (listSumDaySatSun != null) ? listSumDaySatSun : 0f;
                Float nightValue = (listSumNight != null) ? listSumNight : 0f;

                paySlipResponse.get(i).setTotalMonFri(monFriValue * (DayRate != null ? DayRate : 0f));
                paySlipResponse.get(i).setTotalSatSun(satSunValue * (DayRate != null ? DayRate : 0f));
                paySlipResponse.get(i).setTotalNight(nightValue * (DayRateNight != null ? DayRateNight : 0f));
				paySlipResponse.get(i).setBaseTotal((double)(paySlipResponse.get(i).getTotalMonFri()+paySlipResponse.get(i).getTotalSatSun()+paySlipResponse.get(i).getTotalNight()));
				//! Compute grand total including Other Pay columns
				double Alltotal = boneScan + ctScan + mri + roll + smallSurg + bloc + others;
				paySlipResponse.get(i).setAllTotal(paySlipResponse.get(i).getBaseTotal() + Alltotal);
             }
            
         }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success",paySlipResponse, true));
    }

}
