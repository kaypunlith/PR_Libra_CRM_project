package com.ut.nlSystemAPi.mapper.primary;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ut.nlSystemAPi.model.ProvidentFund;
import com.ut.nlSystemAPi.model.ProvidentFundDetail;
import com.ut.nlSystemAPi.model.ProvidentFundFilter;
import com.ut.nlSystemAPi.model.response.ProvidentFundDetailResponse;
import com.ut.nlSystemAPi.model.ProvidentFundListFilter;
import com.ut.nlSystemAPi.model.ProvidentFundUpdateStatus;
import com.ut.nlSystemAPi.model.request.ProvidentFundRequestStatus;
import com.ut.nlSystemAPi.model.request.ProvidentFundRequestStatusByEmp;
import com.ut.nlSystemAPi.model.response.EmployeeDepositFilter;
import com.ut.nlSystemAPi.model.response.EmployeesResponse;
import com.ut.nlSystemAPi.model.response.ProvidentFundHistory;
import com.ut.nlSystemAPi.model.response.ProvidentFundResponse;
import com.ut.nlSystemAPi.model.response.TitleResponse;

@Repository
public interface ProvidentFundMapper {

	List<ProvidentFundResponse> getList(@Param("filter") ProvidentFundListFilter filter);

	Long count(@Param("filter") ProvidentFundListFilter filter);

	List<ProvidentFundHistory> listProFundHistory(@Param("employeesId") Long employeesId, @Param("filterMonth") String filterMonth);

	List<ProvidentFundResponse> getOne(@Param("id") Long id);

	Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

	Boolean insert(@Param("providentFund") ProvidentFund providentFund);

	Boolean update(@Param("providentFund") ProvidentFund providentFund);

	Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

	Boolean insertProFundDetail(@Param("providentFundDetail") ProvidentFundDetail providentFundDetail);

	Boolean deleteProFundDetail(@Param("providentFundId") Long providentFundId, @Param("userId") Long userId);

	List<ProvidentFund> getListProFund(@Param("filter") ProvidentFundFilter filter);

	int countProFund(@Param("filter") ProvidentFundFilter filter);

	List<ProvidentFundDetailResponse> listProFundByEmp(@Param("proFundId") Long proFundId, @Param("filterMonth") String filterMonth);

	Boolean updateStatusAll(@Param("providentFundUpdateStatus") ProvidentFundUpdateStatus providentFundUpdateStatus);

	Long checkStatusEmp(@Param("proFundId") Long proFundId);

	Boolean updateStatusByEmp(@Param("providentFundRequestStatusByEmp") ProvidentFundRequestStatusByEmp providentFundRequestStatusByEmp, @Param("proFundId") Long proFundId);

	Boolean updateStatusProFund(@Param("providentFundRequestStatus") ProvidentFundRequestStatus providentFundRequestStatus);

	List<TitleResponse> getListTitle();

	List<EmployeesResponse> getEmployeeProvidentFund(@Param("filter") EmployeeDepositFilter filter);

	Long getTotalofMonth(@Param("employeeId") Long employeeId, @Param("filterDate") String filterDate, @Param("lastDateRequest") String lastDateRequest);

	String getLastDateRequest(@Param("employeeId") Long employeeId);

}
