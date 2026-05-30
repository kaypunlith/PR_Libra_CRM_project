package com.ut.nlSystemAPi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ProvidentFundMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.ProvidentFund;
import com.ut.nlSystemAPi.model.ProvidentFundDetail;
import com.ut.nlSystemAPi.model.ProvidentFundFilter;
import com.ut.nlSystemAPi.model.response.ProvidentFundDetailResponse;
import com.ut.nlSystemAPi.model.ProvidentFundListFilter;
import com.ut.nlSystemAPi.model.ProvidentFundUpdateStatus;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.ProvidentFundRequest;
import com.ut.nlSystemAPi.model.request.ProvidentFundRequestStatus;
import com.ut.nlSystemAPi.model.request.ProvidentFundRequestStatusByEmp;
import com.ut.nlSystemAPi.model.request.ProvidentFundRequestUpdate;
import com.ut.nlSystemAPi.model.response.EmployeeDepositFilter;
import com.ut.nlSystemAPi.model.response.EmployeesResponse;
import com.ut.nlSystemAPi.model.response.ProvidentFundHistory;
import com.ut.nlSystemAPi.model.response.ProvidentFundResponse;
import com.ut.nlSystemAPi.model.response.TitleResponse;

@Service
public class ProvidentFundServiceImpl implements ProvidentFundService {

	@Autowired
	private ProvidentFundMapper providentFundMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	public ResponseMessage<BaseResult> getList(ProvidentFundListFilter filter) {
		Pagination pagination = new Pagination();
		pagination.setPage(filter.getPage());
		pagination.setRowsPerPage(filter.getRowsPerPage());
		pagination.setTotal(providentFundMapper.count(filter));
		filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
		List<ProvidentFundResponse> responses = providentFundMapper.getList(filter);

		if (responses != null && !responses.isEmpty()) {
			for (ProvidentFundResponse response : responses) {
				String lastDateRequest = providentFundMapper.getLastDateRequest(response.getEmployeeId());
				Long totalOfMonth;
				if (lastDateRequest != null) {
					// * get total month provident fund
					totalOfMonth = providentFundMapper.getTotalofMonth(response.getEmployeeId(), filter.getFilterMonth(), lastDateRequest);
					response.setTotalOfMonth(totalOfMonth);
				} else {
					totalOfMonth = response.getTotalOfMonth();
				}
				// * check bonus half 10% of provident fund amount (employees has provident fund 24 months)
				if (response.getProvidentFundAmount() != null && totalOfMonth != null && totalOfMonth >= 24) {
					response.setBonusHaftProfundAmount(response.getProvidentFundAmount() * 0.1F);
				} else {
					response.setBonusHaftProfundAmount(0f);
				}

				// * combine history provident fund & request provident fund
				List<ProvidentFundHistory> historyList = providentFundMapper.listProFundHistory(response.getEmployeeId(), filter.getFilterMonth());
				if (historyList != null && !historyList.isEmpty()) {
					response.setProvidentFundHistoryList(historyList);
				}
			}
		}
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
	}

	public ResponseMessage<BaseResult> getOne(Long id) {
		List<ProvidentFundResponse> responses = providentFundMapper.getOne(id);

		if (responses != null && !responses.isEmpty()) {
			for (ProvidentFundResponse response : responses) {
				List<ProvidentFundDetailResponse> detailList = providentFundMapper.listProFundByEmp(response.getId(), response.getFilterMonth());

				if (detailList != null && !detailList.isEmpty()) {
					response.setProvidentFundDetailList(detailList);
					// * combine history provident fund & request provident fund
					for (ProvidentFundDetailResponse detail : detailList) {
						List<ProvidentFundHistory> historyList = providentFundMapper.listProFundHistory(detail.getEmployeeId(), response.getFilterMonth());
						if (historyList != null && !historyList.isEmpty()) {
							detail.setProvidentFundHistoryList(historyList);
						}
					}
				}
			}
		}
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
	}

	public ResponseMessage<BaseResult> getListProFund(ProvidentFundFilter filter) {
		Pagination pagination = new Pagination();
		pagination.setPage(filter.getPage());
		pagination.setRowsPerPage(filter.getRowsPerPage());

		if (filter.getPage() == 1) {
			int totalRows = providentFundMapper.countProFund(filter);
			pagination.setTotal((long) totalRows);
		}

		filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

		List<ProvidentFund> providentFundList = providentFundMapper.getListProFund(filter);
		if (providentFundList != null && !providentFundList.isEmpty()) {
			for (ProvidentFund providentFund : providentFundList) {
				Long checkStatusEmp = providentFundMapper.checkStatusEmp(providentFund.getId());
				if (checkStatusEmp == null || checkStatusEmp == 0) {
					providentFund.setValid(Boolean.TRUE);
				} else {
					providentFund.setValid(Boolean.FALSE);
				}
			}
		}
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", providentFundList, pagination, true));
	}

	@Transactional
	public ResponseMessage<BaseResult> insert(ProvidentFundRequest request) {
		Long userId = userService.getUserAuth().getId();

		// * Check Data
		ProvidentFund providentFund = new ProvidentFund();
		providentFund.setRequestDate(request.getRequestDate());
		providentFund.setFilterMonth(request.getFilterMonth());
		providentFund.setTitle(request.getTitle());
		providentFund.setCreatedBy(userId);

		// * If update
		if (request instanceof ProvidentFundRequestUpdate) {
			providentFund.setModifiedBy(userId);
		}

		providentFund.setIsActive(1);
		providentFund.setStatus(1);

		Boolean result = providentFundMapper.insert(providentFund);
		if (result) {
			// * Insert to Provident Fund Details
			if (request.getProvidentFundDetails() != null && !request.getProvidentFundDetails().isEmpty()) {
				for (int i = 0; i < request.getProvidentFundDetails().size(); i++) {
					// * Check Data
					ProvidentFundDetail detail = new ProvidentFundDetail();
					detail.setCreatedBy(userId);
					detail.setIsActive(1);
					detail.setStatus(2);
					detail.setProvidentFundId(providentFund.getId());
					detail.setAmountRequest(request.getProvidentFundDetails().get(i).getAmountRequest());
					detail.setEmployeeId(request.getProvidentFundDetails().get(i).getEmployeeId());
					detail.setTotalOfMonth(request.getProvidentFundDetails().get(i).getTotalOfMonth());
					detail.setBonusHaftProfundAmount(request.getProvidentFundDetails().get(i).getBonusHaftProfundAmount());
					providentFundMapper.insertProFundDetail(detail);
				}
			}
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	@Transactional
	public ResponseMessage<BaseResult> update(ProvidentFundRequestUpdate providentFundRequestUpdate) {
		Long userId = userService.getUserAuth().getId();

		// * Archive old record
		providentFundMapper.delete(providentFundRequestUpdate.getId(), userId);

		// * Create new record
		return this.insert(providentFundRequestUpdate);
	}

	public ResponseMessage<BaseResult> delete(Long id) {
		Long userId = userService.getUserAuth().getId();

		Boolean result = providentFundMapper.delete(id, userId);
		if (result) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	public ResponseMessage<BaseResult> updateStatusAll(ProvidentFundUpdateStatus providentFundUpdateStatus) {
		Long userId = userService.getUserAuth().getId();

		// * Check Data
		providentFundUpdateStatus.setModifiedBy(userId);
		providentFundUpdateStatus.setIsActive(1);

		Boolean result = providentFundMapper.updateStatusAll(providentFundUpdateStatus);
		if (result) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	public ResponseMessage<BaseResult> updateStatusByEmp(ProvidentFundRequestStatus providentFundRequestStatus) {
		Long userId = userService.getUserAuth().getId();

		// * Check data
		providentFundRequestStatus.setModifiedBy(userId);

		Boolean result = providentFundMapper.updateStatusProFund(providentFundRequestStatus);
		if (result) {
			// * Update to Provident Fund Details
			if (providentFundRequestStatus.getProFundUpdateStatus() != null && !providentFundRequestStatus.getProFundUpdateStatus().isEmpty()) {
				for (int i = 0; i < providentFundRequestStatus.getProFundUpdateStatus().size(); i++) {
					// * Check Data
					ProvidentFundRequestStatusByEmp providentFundRequestStatusByEmp = new ProvidentFundRequestStatusByEmp();
					providentFundRequestStatusByEmp.setModifiedBy(userId);
					providentFundRequestStatusByEmp.setId(providentFundRequestStatus.getProFundUpdateStatus().get(i).getId());
					providentFundRequestStatusByEmp.setEmployeesId(providentFundRequestStatus.getProFundUpdateStatus().get(i).getEmployeesId());
					providentFundRequestStatusByEmp.setStatus(providentFundRequestStatus.getProFundUpdateStatus().get(i).getStatus());
					providentFundMapper.updateStatusByEmp(providentFundRequestStatusByEmp, providentFundRequestStatus.getProvidentFundId());
				}
			}
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	public ResponseMessage<BaseResult> getListTitle() {
		List<TitleResponse> titleResponses = providentFundMapper.getListTitle();
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", titleResponses, true));
	}

	public ResponseMessage<BaseResult> getEmployeeProvidentFund(EmployeeDepositFilter filter) {
		Pagination pagination = new Pagination();
		pagination.setPage(filter.getPage());
		pagination.setRowsPerPage(filter.getRowsPerPage());

		filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

		List<EmployeesResponse> employeesResponses = providentFundMapper.getEmployeeProvidentFund(filter);

		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", employeesResponses, true));
	}

}
