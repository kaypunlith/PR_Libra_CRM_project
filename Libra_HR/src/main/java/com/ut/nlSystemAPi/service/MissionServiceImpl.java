package com.ut.nlSystemAPi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.MissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.Mission;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.MissionFilter;
import com.ut.nlSystemAPi.model.request.MissionRequest;
import com.ut.nlSystemAPi.model.request.MissionUpdateRequest;
import com.ut.nlSystemAPi.model.request.MissionUpdateStatusRequest;
import com.ut.nlSystemAPi.model.response.MissionResponse;

@Service
public class MissionServiceImpl implements MissionService {

	@Autowired
	private MissionMapper missionMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	@Override
	public ResponseMessage<BaseResult> getList(MissionFilter filter) {
		Pagination pagination = new Pagination();
		pagination.setPage(filter.getPage());
		pagination.setRowsPerPage(filter.getRowsPerPage());

		if (filter.getPage() == 1) {
			int totalRows = missionMapper.count(filter);
			pagination.setTotal((long) totalRows);
		}

		filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

		List<MissionResponse> responses = missionMapper.getList(filter);
		for (MissionResponse mission : responses) {
			mission.setMeans(missionMapper.getMeans(mission.getId()));
			mission.setOrganizations(missionMapper.getCustomers(mission.getId()));
			mission.setDestinations(missionMapper.getDestinations(mission.getId()));
		}
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
	}

	@Override
	public ResponseMessage<BaseResult> getOne(Long id) {
		List<MissionResponse> response = missionMapper.getOne(id);
		if (!response.isEmpty()) {
			MissionResponse mission = response.get(0);
			// * Populate lists for edit
			mission.setMeans(missionMapper.getMeans(id));
			mission.setOrganizations(missionMapper.getCustomers(id));
			mission.setDestinations(missionMapper.getDestinations(id));
		}
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", response, true));
	}

	@Override
	@Transactional
	public ResponseMessage<BaseResult> insert(MissionRequest request) {
		Long userId = userService.getUserAuth().getId();

		Mission mission = new Mission();
		mission.setStartDate(request.getStartDate());
		mission.setEndDate(request.getEndDate());
		mission.setDescription(request.getDescription());
		mission.setCreatedBy(userId);
		if (request instanceof MissionUpdateRequest) {
			mission.setModifiedBy(userId);
		}

		// * Insert Parent WorkingTime first
		missionMapper.insertWorkingTime(mission);

		Boolean result = missionMapper.insert(mission);
		if (result && mission.getId() != null) {
			Long missionId = mission.getId();
			// * Insert pivot data
			if (request.getMeans() != null) {
				for (Long meanId : request.getMeans()) {
					missionMapper.insertMeans(missionId, meanId);
				}
			}
			if (request.getOrganizations() != null) {
				for (Long orgId : request.getOrganizations()) {
					missionMapper.insertCustomers(missionId, orgId);
				}
			}
			if (request.getDestinations() != null) {
				for (Long destId : request.getDestinations()) {
					missionMapper.insertDestinations(missionId, destId);
				}
			}
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	@Override
	@Transactional
	public ResponseMessage<BaseResult> update(MissionUpdateRequest request) {
		Long userId = userService.getUserAuth().getId();

		// * Archive old record
		missionMapper.delete(request.getId(), userId);

		// * Create new record
		return this.insert(request);
	}

	@Override
	public ResponseMessage<BaseResult> delete(Long id) {
		Long userId = userService.getUserAuth().getId();
		Boolean result = missionMapper.delete(id, userId);
		if (result) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	@Override
	public ResponseMessage<BaseResult> updateStatus(MissionUpdateStatusRequest request) {
		Long userId = userService.getUserAuth().getId();
		Mission mission = new Mission();
		mission.setId(request.getId());
		mission.setStatus(request.getStatus());
		mission.setModifiedBy(userId);

		Boolean result = missionMapper.updateStatus(mission);
		if (result) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}
}
