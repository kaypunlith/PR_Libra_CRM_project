package com.ut.nlSystemAPi.mapper.primary;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ut.nlSystemAPi.model.Mission;
import com.ut.nlSystemAPi.model.filter.MissionFilter;
import com.ut.nlSystemAPi.model.response.DropdownResponse;
import com.ut.nlSystemAPi.model.response.MissionResponse;

@Repository
public interface MissionMapper {

	List<MissionResponse> getList(@Param("filter") MissionFilter filter);

	int count(@Param("filter") MissionFilter filter);

	List<MissionResponse> getOne(@Param("id") Long id);

	Boolean insertWorkingTime(Mission mission);

	Boolean insert(@Param("mission") Mission mission);

	Boolean update(@Param("mission") Mission mission);

	Boolean updateStatus(@Param("mission") Mission mission);

	Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

	// * Pivot table operations
	void insertMeans(@Param("missionId") Long missionId, @Param("meanId") Long meanId);

	void insertCustomers(@Param("missionId") Long missionId, @Param("customerId") Long customerId);

	void insertDestinations(@Param("missionId") Long missionId, @Param("destinationId") Long destinationId);

	// * Get list of IDs for edit mode
	List<DropdownResponse> getMeans(@Param("missionId") Long missionId);

	List<DropdownResponse> getCustomers(@Param("missionId") Long missionId);

	List<DropdownResponse> getDestinations(@Param("missionId") Long missionId);
}
