package com.ut.nlSystemAPi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.DepositMapper;
import com.ut.nlSystemAPi.model.Deposit;
import com.ut.nlSystemAPi.model.DepositDetail;
import com.ut.nlSystemAPi.model.DepositFilter;
import com.ut.nlSystemAPi.model.DepositListByEmployees;
import com.ut.nlSystemAPi.model.DepositListFilter;
import com.ut.nlSystemAPi.model.DepositUpdateStatus;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.DepositRequest;
import com.ut.nlSystemAPi.model.request.DepositRequestStatus;
import com.ut.nlSystemAPi.model.request.DepositRequestStatusByEmp;
import com.ut.nlSystemAPi.model.request.DepositRequestUpdate;
import com.ut.nlSystemAPi.model.response.DepositHistory;
import com.ut.nlSystemAPi.model.response.DepositResponse;
import com.ut.nlSystemAPi.model.response.EmployeeDepositFilter;
import com.ut.nlSystemAPi.model.response.EmployeesResponse;
import com.ut.nlSystemAPi.model.response.TitleResponse;

@Service
public class DepositServiceImpl implements DepositService {

	@Autowired
	private DepositMapper depositMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	public ResponseMessage<BaseResult> getList(DepositListFilter filter) {
		List<DepositResponse> depositResponses = depositMapper.getList(filter);
		if (depositResponses != null && depositResponses.size() > 0) {
			for (DepositResponse deposits : depositResponses) {
				Long employeesId = deposits.getId();

				// * sum adjust beginning with deposit by months
				Float adjustBeginningAmount = depositMapper.getAdjustBeginningDeposit(employeesId, filter.getFilterMonth());

				Float depositAmount = deposits.getDepositAmount();

				deposits.setDepositAmount(depositAmount + adjustBeginningAmount);

				// * Combine history of deposit and request
				List<DepositHistory> depositHistoryList = depositMapper.listDepositHistory(employeesId, filter.getFilterMonth());
				List<DepositHistory> depositHistoryRequestList = depositMapper.listDepositRequestHistory(employeesId, filter.getFilterMonth());

				depositHistoryList.addAll(depositHistoryRequestList);
				deposits.setDepositHistoryList(depositHistoryList);
			}
		}
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", depositResponses, true));
	}

	// * get one
	public ResponseMessage<BaseResult> getOne(Long id) {
		List<Deposit> deposits = depositMapper.getOne(id);
		if (deposits != null && deposits.size() > 0) {
			for (Deposit deposit : deposits) {
				Long depositRequestId = deposit.getId();
				String filterMonth = deposit.getFilterMonth();
				// * List deposit request

				System.out.println("filterMonth " + filterMonth);
				deposit.setDepositDetailList(this.depositMapper.listDepositByEmp(depositRequestId, filterMonth));
				List<DepositListByEmployees> depositListByEmployees = deposit.getDepositDetailList();
				// * List employees history
				if (depositListByEmployees != null && depositListByEmployees.size() > 0) {
					for (DepositListByEmployees depositListByEmployee : depositListByEmployees) {
						Long employeesId = depositListByEmployee.getId(); // * employees id

						// * sum adjust beginning with deposit by months
						Float adjustBeginningAmount = depositMapper.getAdjustBeginningDeposit(employeesId, filterMonth);
						Float depositAmount = depositListByEmployee.getDepositAmount();
						depositListByEmployee.setDepositAmount(depositAmount + adjustBeginningAmount);

						// * combine history of deposit and request
						List<DepositHistory> depositHistoryList = depositMapper.listDepositHistory(employeesId, filterMonth);
						List<DepositHistory> depositHistoryRequestList = depositMapper.listDepositRequestHistory(employeesId, filterMonth);
						depositHistoryList.addAll(depositHistoryRequestList);

						depositListByEmployee.setDepositHistoryList(depositHistoryList);
					}
				}
			}
		}
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", deposits, true));
	}

	public ResponseMessage<BaseResult> getListProFund(DepositFilter filter) {
		Pagination pagination = new Pagination();
		pagination.setPage(filter.getPage());
		pagination.setRowsPerPage(filter.getRowsPerPage());

		if (filter.getPage() == 1) {
			int totalRows = depositMapper.countDeposit(filter);
			pagination.setTotal((long) totalRows);
		}

		filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

		List<Deposit> depositList = depositMapper.getListDeposit(filter);
		if (depositList != null && depositList.size() > 0) {
			for (Deposit deposit : depositList) {
				Long checkStatusEmp = depositMapper.checkStatusEmp(deposit.getId());
				if (checkStatusEmp == null || checkStatusEmp == 0) {
					deposit.setValid(Boolean.TRUE);
				} else {
					deposit.setValid(Boolean.FALSE);
				}
			}
		}

		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", depositList, pagination, true));
	}

	@Transactional
	public ResponseMessage<BaseResult> insert(DepositRequest depositRequest) {
		Long userId = userService.getUserAuth().getId();

		// * Check Data
		Deposit deposit = new Deposit();
		deposit.setRequestDate(depositRequest.getRequestDate());
		deposit.setFilterMonth(depositRequest.getFilterMonth());
		deposit.setTitle(depositRequest.getTitle());
		deposit.setCreatedBy(userId);

		if (depositRequest instanceof DepositRequestUpdate) {
			deposit.setModifiedBy(userId);
		}

		deposit.setIsActive(1);
		deposit.setStatus(1);

		Boolean result = depositMapper.insert(deposit);
		if (result) {
			// * Insert to Provident Fund Details
			if (!depositRequest.getDepositRequestDetails().isEmpty()) {
				for (int i = 0; i < depositRequest.getDepositRequestDetails().size(); i++) {
					// * Check Data
					DepositDetail depositDetail = new DepositDetail();
					depositDetail.setCreatedBy(userId);
					depositDetail.setIsActive(1);
					depositDetail.setStatus(2);
					depositDetail.setDepositRequestId(deposit.getId());
					depositDetail.setAmountRequest(depositRequest.getDepositRequestDetails().get(i).getAmountRequest());
					depositDetail.setEmployeesId(depositRequest.getDepositRequestDetails().get(i).getEmployeesId());
					depositDetail.setRemark(depositRequest.getDepositRequestDetails().get(i).getRemark());
					depositMapper.insertDepositDetail(depositDetail);
				}
			}
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	@Transactional
	public ResponseMessage<BaseResult> update(DepositRequestUpdate depositRequestUpdate) {
		Long userId = userService.getUserAuth().getId();

		// * Archive old record
		depositMapper.delete(depositRequestUpdate.getId(), userId);

		// * Create new record
		return this.insert(depositRequestUpdate);
	}

	public ResponseMessage<BaseResult> delete(Long id) {
		Long userId = userService.getUserAuth().getId();

		Boolean result = depositMapper.delete(id, userId);
		if (result) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	public ResponseMessage<BaseResult> updateStatusAll(DepositUpdateStatus depositUpdateStatus) {
		Long userId = userService.getUserAuth().getId();

		// * Check Data
		depositUpdateStatus.setModifiedBy(userId);
		depositUpdateStatus.setIsActive(1);

		Boolean result = depositMapper.updateStatusAll(depositUpdateStatus);
		if (result) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	public ResponseMessage<BaseResult> updateStatusByEmp(DepositRequestStatus depositRequestStatus) {

		Long userId = userService.getUserAuth().getId();
		// * check data
		depositRequestStatus.setModifiedBy(userId);

		Boolean result = depositMapper.updateStatusDepositDetail(depositRequestStatus);
		if (result) {
			// * Update to Provident Fund Details
			if (!depositRequestStatus.getDepositRequestUpdateStatus().isEmpty()) {
				for (int i = 0; i < depositRequestStatus.getDepositRequestUpdateStatus().size(); i++) {
					// * Check Data
					DepositRequestStatusByEmp depositRequestStatusByEmp = new DepositRequestStatusByEmp();
					depositRequestStatusByEmp.setModifiedBy(userId);
					depositRequestStatusByEmp.setId(depositRequestStatus.getDepositRequestUpdateStatus().get(i).getId());
					depositRequestStatusByEmp.setEmployeesId(depositRequestStatus.getDepositRequestUpdateStatus().get(i).getEmployeesId());
					depositRequestStatusByEmp.setStatus(depositRequestStatus.getDepositRequestUpdateStatus().get(i).getStatus());
					depositMapper.updateStatusByEmp(depositRequestStatusByEmp, depositRequestStatus.getDepositRequestId());
				}
			}
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	public ResponseMessage<BaseResult> getListTitle() {
		List<TitleResponse> titleResponses = depositMapper.getListTitle();
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", titleResponses, true));
	}

	public ResponseMessage<BaseResult> getEmployeeDeposit(EmployeeDepositFilter filter) {

		Pagination pagination = new Pagination();
		pagination.setPage(filter.getPage());
		pagination.setRowsPerPage(filter.getRowsPerPage());

		filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

		List<EmployeesResponse> employeesResponses = depositMapper.getEmployeeDeposit(filter);
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", employeesResponses, true));
	}

}
