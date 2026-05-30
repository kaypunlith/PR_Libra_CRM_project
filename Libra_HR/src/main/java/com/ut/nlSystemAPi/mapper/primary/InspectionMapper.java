package com.ut.nlSystemAPi.mapper.primary;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ut.nlSystemAPi.model.Inspection;
import com.ut.nlSystemAPi.model.InspectionScore;
import com.ut.nlSystemAPi.model.filter.InspectionFilter;
import com.ut.nlSystemAPi.model.request.Inspection.InspectionScoreUpdate;
import com.ut.nlSystemAPi.model.request.Inspection.SubInspectionRequest;
import com.ut.nlSystemAPi.model.response.Inspection.InspectionFindResponse;
import com.ut.nlSystemAPi.model.response.Inspection.InspectionGroupResponse;
import com.ut.nlSystemAPi.model.response.Inspection.InspectionListResponse;
import com.ut.nlSystemAPi.model.response.Inspection.InspectionSummaryResponse;
import com.ut.nlSystemAPi.model.response.Inspection.ProvinceResponse;
import com.ut.nlSystemAPi.model.response.Inspection.QuarterResponse;
import com.ut.nlSystemAPi.model.response.Inspection.SubInspectionResponse;
import com.ut.nlSystemAPi.model.response.Inspection.SubSubInspectionResponse;

@Repository
public interface InspectionMapper {

	// * List Inspection Home Screen
	List<InspectionListResponse> getListInspection(@Param("filter") InspectionFilter inspectionFilter);

	int count(@Param("filter") InspectionFilter filter);

	List<InspectionGroupResponse> getListInspectionGroup();

	List<ProvinceResponse> getListProvince();

	List<QuarterResponse> getListQuater();

	List<SubInspectionResponse> getListSubInspection(@Param("id") Long id);

	List<SubSubInspectionResponse> getListSubSubInspection(@Param("id") Long id);

	// * Insert into inspection
	Boolean insert(@Param("inspection") Inspection inspection);

	// * Update inspection
	Boolean updateScores(@Param("inspectionScoreUpdate") InspectionScoreUpdate inspectionScoreUpdate);

	// * Insert Sub Inspection
	Boolean insertSubInspection(@Param("subInspectionRequest") SubInspectionRequest subInspectionRequest);

	// * Delete Inspection Score
	Boolean deleteInspectionScore(@Param("inspectionId") Long inspectionId);

	Boolean insertInspectoinDetail(@Param("inspectionScore") InspectionScore inspectionScore);

	// * SUM Inspection score detail
	Float sumInspectoinScroeDetail(@Param("inspectionId") Long inspectionId,
			@Param("subInspectionId") Long subInspectionId);

	Long standartScore(@Param("subInspectionId") Long subInspectionId);

	Long getScoreDetailById(@Param("inspectionId") Long inspectionId,
			@Param("subSubInspectionId") Long subSubInspectionId);

	Long getSubInspectionPercentage(@Param("inspectionId") Long inspectionId,
			@Param("subSubInspectionId") Long subSubInspectionId);

	// * inspection get one
	List<InspectionFindResponse> getInspectionOne(@Param("id") Long id);

	// * Delete Inspection
	Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

	// * Summary Inspection
	List<InspectionSummaryResponse> listSummaryInspection(@Param("filter") InspectionFilter inspectionFilter);

}
