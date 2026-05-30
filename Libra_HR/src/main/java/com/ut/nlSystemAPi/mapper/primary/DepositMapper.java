package com.ut.nlSystemAPi.mapper.primary;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ut.nlSystemAPi.model.Deposit;
import com.ut.nlSystemAPi.model.DepositDetail;
import com.ut.nlSystemAPi.model.DepositFilter;
import com.ut.nlSystemAPi.model.DepositListByEmployees;
import com.ut.nlSystemAPi.model.DepositListFilter;
import com.ut.nlSystemAPi.model.DepositUpdateStatus;
import com.ut.nlSystemAPi.model.request.DepositRequestStatus;
import com.ut.nlSystemAPi.model.request.DepositRequestStatusByEmp;
import com.ut.nlSystemAPi.model.response.DepositHistory;
import com.ut.nlSystemAPi.model.response.DepositResponse;
import com.ut.nlSystemAPi.model.response.EmployeeDepositFilter;
import com.ut.nlSystemAPi.model.response.EmployeesResponse;
import com.ut.nlSystemAPi.model.response.TitleResponse;

@Repository
public interface DepositMapper {

	List<DepositResponse> getList(@Param("filter") DepositListFilter filter);

	List<DepositHistory> listDepositHistory(@Param("employeesId") Long employeesId, @Param("filterMonth") String filterMonth);

	Float getAdjustBeginningDeposit(@Param("employeesId") Long employeesId, @Param("filterMonth") String filterMonth);

	List<DepositHistory> listDepositRequestHistory(@Param("employeesId") Long employeesId, @Param("filterMonth") String filterMonth);

	List<Deposit> getOne(@Param("id") Long id);

	Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

	Boolean insert(@Param("deposit") Deposit deposit);

	Boolean update(@Param("deposit") Deposit deposit);

	Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

	Boolean insertDepositDetail(@Param("depositDetail") DepositDetail depositDetail);

	Boolean deleteDepositDetail(@Param("depositRequestId") Long depositRequestId, @Param("userId") Long userId);

	List<Deposit> getListDeposit(@Param("filter") DepositFilter filter);

	int countDeposit(@Param("filter") DepositFilter filter);

	List<DepositListByEmployees> listDepositByEmp(@Param("depositRequestId") Long depositRequestId, @Param("filterMonth") String filterMonth);

	Long checkStatusEmp(@Param("depositRequestId") Long depositRequestId);

	Boolean updateStatusAll(@Param("depositUpdateStatus") DepositUpdateStatus depositUpdateStatus);

	// * Update Status By Emp
	Boolean updateStatusByEmp(@Param("depositRequestStatusByEmp") DepositRequestStatusByEmp depositRequestStatusByEmp, @Param("depositRequestId") Long depositRequestId);

	Boolean updateStatusDepositDetail(@Param("depositRequestStatus") DepositRequestStatus depositRequestStatus);

	List<TitleResponse> getListTitle();

	List<EmployeesResponse> getEmployeeDeposit(@Param("filter") EmployeeDepositFilter filter);

}
