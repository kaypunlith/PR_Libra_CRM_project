package com.ut.nlSystemAPi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.LeaveRequestMapper;
import com.ut.nlSystemAPi.mapper.primary.NotificationMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.LeaveRequest;
import com.ut.nlSystemAPi.model.LeaveRequestFilter;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.Notification;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.LeaveReportDetailFilter;
import com.ut.nlSystemAPi.model.notification.NotificationRecipientRequest;
import com.ut.nlSystemAPi.model.notification.NotificationRequest;
import com.ut.nlSystemAPi.model.request.LeaveRequestAdd;
import com.ut.nlSystemAPi.model.request.LeaveRequestUpdate;
import com.ut.nlSystemAPi.model.response.LeaveReportDetailResponse;
import com.ut.nlSystemAPi.model.response.LeaveRequestDetailResponse;
import com.ut.nlSystemAPi.model.response.LeaveRequestResponse;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

	@Autowired
	private LeaveRequestMapper leaveRequestMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
	Environment environment;

	@Autowired
	NotificationMapper notificationMapper;

	@Autowired
	NotificationService notificationService;

	// * Get list
	public ResponseMessage<BaseResult> getList(LeaveRequestFilter filter) {
		// Check Permission
		Long userId = userService.getUserAuth().getId();
		if (permissionMapper.checkPermission(userId, "HR Leave Request (View)") == 0) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
		}

		Pagination pagination = new Pagination();
		pagination.setPage(filter.getPage());
		pagination.setRowsPerPage(filter.getRowsPerPage());

		// * Total count when filter
		if (filter.getPage() == 1) {
			int totalRows = leaveRequestMapper.count(filter);
			pagination.setTotal((long) totalRows);
		}

		// * Calculate offset
		filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

		List<LeaveRequestResponse> leaveRequestMapperList = leaveRequestMapper.getList(filter);

		if (filter.getYear() != null) {
			if (leaveRequestMapperList.size() > 0) {
				for (int i = 0; i < leaveRequestMapperList.size(); i++) {
					List<LeaveRequestDetailResponse> leaveRequestDetailResponses = leaveRequestMapper.getLeaveRequestDetail(String.valueOf(leaveRequestMapperList.get(i).getEmployeeId()), filter.getYear());
					leaveRequestMapperList.get(i).setLeaveRequestDetailResponses(leaveRequestDetailResponses);
				}
			}
		}

		if (leaveRequestMapperList.size() > 0) {
			if (leaveRequestMapperList.get(0).getId() == null) {
				leaveRequestMapperList = new ArrayList<>();
			}
		}
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", leaveRequestMapperList, pagination, true));
	}

	// * Get One
	public ResponseMessage<BaseResult> getOne(Long id) {
		// Check Permission
		Long userId = userService.getUserAuth().getId();
		if (permissionMapper.checkPermission(userId, "HR Leave Request (View)") == 0) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
		}

		List<LeaveRequestResponse> leaveRequestResponses = leaveRequestMapper.getOne(id);

		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", leaveRequestResponses, true));
	}

	// * Get View Detail
	public ResponseMessage<BaseResult> getViewDetail(Long employeeId, String year) {
		// Check Permission
		Long userId = userService.getUserAuth().getId();
		if (permissionMapper.checkPermission(userId, "HR Leave Request (View)") == 0) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
		}

		List<LeaveRequestResponse> leaveRequestResponses = leaveRequestMapper.getViewDetail(String.valueOf(employeeId), year);
		List<LeaveRequestDetailResponse> leaveRequestDetailResponses = leaveRequestMapper.getLeaveRequestDetail(String.valueOf(employeeId), year);

		if (leaveRequestResponses.size() > 0) {
			leaveRequestResponses.get(0).setLeaveRequestDetailResponses(leaveRequestDetailResponses);
		}
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", leaveRequestResponses, true));
	}

	// * Insert
	public ResponseMessage<BaseResult> insert(LeaveRequestAdd leaveRequestAdd) {
		// Check Permission
		Long userId = userService.getUserAuth().getId();
		if (permissionMapper.checkPermission(userId, "HR Leave Request (Add)") == 0) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
		}

		// * Check Data
		LeaveRequest leaveRequest = new LeaveRequest();
		leaveRequest.setDateFrom(leaveRequestAdd.getDateFrom());
		leaveRequest.setDateTo(leaveRequestAdd.getDateTo());
		leaveRequest.setEmployeeId(leaveRequestAdd.getEmployeeId());
		leaveRequest.setNote(leaveRequestAdd.getDescription());
		leaveRequest.setLeaveTypeId(leaveRequestAdd.getLeaveTypeId());
		leaveRequest.setNumberOfDay(leaveRequestAdd.getNumberOfDay());
		leaveRequest.setStatus(1);
		leaveRequest.setCreatedBy(userId);
		leaveRequest.setIsActive(1);

		if (leaveRequestAdd instanceof LeaveRequestUpdate) {
			leaveRequest.setModifiedBy(userId);
		}

		Boolean result = leaveRequestMapper.insert(leaveRequest);

		// ! Push one signal
		{
			List<String> deviceTokens = notificationMapper.getDeviceToken(leaveRequest.getEmployeeId(), null);

			if (deviceTokens != null && deviceTokens.size() > 0) {
				// ! Set Data
				Notification notification = new Notification();
				notification.setContent("Leave Approval");
				notification.setMessage("Your request has been approve");
				notification.setDeviceTokens(deviceTokens);
				notificationService.pushNotification(notification);
			}
		}

		// ! Save Notification
		{
			NotificationRequest notificationRequest = new NotificationRequest();
			notificationRequest.setType(2L);
			notificationRequest.setLeaveRequestId(leaveRequest.getId());
			notificationRequest.setLeaveRequestStatus(1L);
			notificationRequest.setUserId(leaveRequestMapper.getUerIdByEmployeeId(leaveRequest.getEmployeeId()));

			// ! Notification Recipients
			List<NotificationRecipientRequest> notificationRecipientRequests = new ArrayList<>();
			NotificationRecipientRequest notificationRecipientRequest = new NotificationRecipientRequest();
			notificationRecipientRequest.setRecipientId(leaveRequestMapper.getUerIdByEmployeeId(leaveRequest.getEmployeeId()));

			notificationRecipientRequests.add(notificationRecipientRequest);
			notificationRequest.setNotificationRecipientRequests(notificationRecipientRequests);
			notificationRequest.setLeaveRequestStatus(2L);

			notificationService.insertNotification(notificationRequest);

		}

		if (result) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	// * Update
	public ResponseMessage<BaseResult> update(LeaveRequestUpdate leaveRequestUpdate) {
		// Check Permission
		Long userId = userService.getUserAuth().getId();
		if (permissionMapper.checkPermission(userId, "HR Leave Request (Edit)") == 0) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
		}

		// * Archive old record
		this.leaveRequestMapper.delete(leaveRequestUpdate.getId(), userId);

		// * Create new record
		return this.insert(leaveRequestUpdate);
	}

	// * Delete
	public ResponseMessage<BaseResult> delete(Long id) {
		Long userId = userService.getUserAuth().getId();
		if (permissionMapper.checkPermission(userId, "HR Leave Request (Delete)") == 0) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
		}
		Boolean result = leaveRequestMapper.delete(id, userId);
		if (result) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	// * Get Report Detail
	public ResponseMessage<BaseResult> getReportDetail(LeaveReportDetailFilter filter) {
		// Check Permission
		Long userId = userService.getUserAuth().getId();
		if (permissionMapper.checkPermission(userId, "HR Leave Request (View)") == 0) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Authorization", false));
		}

		List<LeaveReportDetailResponse> leaveRequestDetailResponses = leaveRequestMapper.getReportDetail(filter);
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", leaveRequestDetailResponses, true));
	}

}
