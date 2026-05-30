package com.ut.nlSystemAPi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.OtRequestMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.OtRequest;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.OtRequestFilter;
import com.ut.nlSystemAPi.model.request.OtRequestRequest;
import com.ut.nlSystemAPi.model.request.OtRequestUpdateRequest;
import com.ut.nlSystemAPi.model.request.OtRequestUpdateStatusRequest;
import com.ut.nlSystemAPi.model.response.OtRquestResponse;

@Service
public class OtRequestServiceImpl implements OtRequestService {

	@Autowired
	private OtRequestMapper otRequestMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	// * Get list
	public ResponseMessage<BaseResult> getList(OtRequestFilter filter) {
		Pagination pagination = new Pagination();
		pagination.setPage(filter.getPage());
		pagination.setRowsPerPage(filter.getRowsPerPage());

		// * Total count when filter
		if (filter.getPage() == 1) {
			int totalRows = otRequestMapper.count(filter);
			pagination.setTotal((long) totalRows);
		}

		// * Calculate offset
		filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

		List<OtRquestResponse> responses = otRequestMapper.getList(filter);
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
	}

	// * Get One
	public ResponseMessage<BaseResult> getOne(Long id) {
		List<OtRquestResponse> response = otRequestMapper.getOne(id);
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", response, true));
	}

	// * Insert
	public ResponseMessage<BaseResult> insert(OtRequestRequest request) {
		Long userId = userService.getUserAuth().getId();

		// * Check Data
		OtRequest otRequest = new OtRequest();
		otRequest.setEmployeeId(request.getEmployeeId());
		otRequest.setDateForm(request.getDateForm());
		otRequest.setDateTo(request.getDateTo());
		otRequest.setDuration(request.getDuration());
		otRequest.setReason(request.getReason());
		otRequest.setCreatedBy(userId);

		if (request instanceof OtRequestUpdateRequest) {
			otRequest.setModifiedBy(userId);
		}

		Boolean result = otRequestMapper.insert(otRequest);
		if (result) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	// * Update
	public ResponseMessage<BaseResult> update(OtRequestUpdateRequest otRequestUpdateRequest) {
		Long userId = userService.getUserAuth().getId();

		// * Archive old record
		this.otRequestMapper.delete(otRequestUpdateRequest.getId(), userId);

		// * Create new record
		return this.insert(otRequestUpdateRequest);
	}

	// * Update Status
	public ResponseMessage<BaseResult> updateStatus(OtRequestUpdateStatusRequest request) {
		Long userId = userService.getUserAuth().getId();
		OtRequest otRequest = new OtRequest();
		otRequest.setId(request.getId());
		otRequest.setStatus(request.getStatus());
		otRequest.setCreatedBy(userId);

		Boolean result = otRequestMapper.updateStatus(otRequest);
		if (result) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	// * Delete
	public ResponseMessage<BaseResult> delete(Long id) {
		Long userId = userService.getUserAuth().getId();
		Boolean result = otRequestMapper.delete(id, userId);
		if (result) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

}
