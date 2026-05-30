package com.ut.nlSystemAPi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.InspectionMapper;
import com.ut.nlSystemAPi.model.Inspection;
import com.ut.nlSystemAPi.model.InspectionScore;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.InspectionFilter;
import com.ut.nlSystemAPi.model.request.Inspection.InspectionRequest;
import com.ut.nlSystemAPi.model.request.Inspection.InspectionScoreRequest;
import com.ut.nlSystemAPi.model.request.Inspection.InspectionScoreUpdate;
import com.ut.nlSystemAPi.model.request.Inspection.InspectionUpdate;
import com.ut.nlSystemAPi.model.request.Inspection.SubInspectionRequest;
import com.ut.nlSystemAPi.model.response.Inspection.InspectionFindResponse;
import com.ut.nlSystemAPi.model.response.Inspection.InspectionGroupResponse;
import com.ut.nlSystemAPi.model.response.Inspection.InspectionListResponse;
import com.ut.nlSystemAPi.model.response.Inspection.InspectionSummaryResponse;
import com.ut.nlSystemAPi.model.response.Inspection.ProvinceResponse;
import com.ut.nlSystemAPi.model.response.Inspection.QuarterResponse;
import com.ut.nlSystemAPi.model.response.Inspection.SubInspectionResponse;
import com.ut.nlSystemAPi.model.response.Inspection.SubSubInspectionResponse;

@Service
public class InspectionServiceImpl implements InspectionService {

	@Autowired
	private InspectionMapper inspectionMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	public ResponseMessage<BaseResult> getList(InspectionFilter filter) {

		Pagination pagination = new Pagination();
		pagination.setPage(filter.getPage());
		pagination.setRowsPerPage(filter.getRowsPerPage());

		if (filter.getPage() == 1) {
			int totalRows = inspectionMapper.count(filter);
			pagination.setTotal((long) totalRows);
		}

		filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

		List<InspectionListResponse> moduleTypeList = inspectionMapper.getListInspection(filter);

		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", moduleTypeList, pagination, true));
	}

	public ResponseMessage<BaseResult> getListSummaryInspection(InspectionFilter filter) {

		Pagination pagination = new Pagination();
		pagination.setPage(filter.getPage());
		pagination.setRowsPerPage(filter.getRowsPerPage());
		filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

		List<InspectionSummaryResponse> inspectionSummaryResponses = inspectionMapper.listSummaryInspection(filter);
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", inspectionSummaryResponses, true));
	}

	public ResponseMessage<BaseResult> getOne(Long id) {

		// * Get Inspection Base Details

		List<InspectionFindResponse> inspectionFindResponses = inspectionMapper.getInspectionOne(id);
		if (inspectionFindResponses.isEmpty()) {
			return ResponseMessageUtils.makeResponse(false, messageService.message("Inspection not found", false));
		}
		InspectionFindResponse response = inspectionFindResponses.get(0);

		// * Calculate Rank
		Float servicePercentage = response.getServicePercentage() != null ? response.getServicePercentage() : 0F;
		Float environtmentPercentage = response.getEnvirontmentPercentage() != null ? response.getEnvirontmentPercentage() : 0F;
		Float productPercentage = response.getProductPercentage() != null ? response.getProductPercentage() : 0F;

		Float totalPercentage = servicePercentage + environtmentPercentage + productPercentage;
		if (totalPercentage > 94) {
			response.setRank("A");
		} else if (totalPercentage >= 90) {
			response.setRank("B");
		} else if (totalPercentage >= 80) {
			response.setRank("C");
		} else if (totalPercentage >= 70) {
			response.setRank("D");
		} else if (totalPercentage < 70) {
			response.setRank("F");
		}

		// * Inspection Group (Form Data Logic)
		List<InspectionGroupResponse> inspectionGroupResponses = inspectionMapper.getListInspectionGroup();
		for (InspectionGroupResponse group : inspectionGroupResponses) {
			Long inspectionGroupId = group.getId();
			// * Set Sub Inspection
			List<SubInspectionResponse> subInspectionResponses = inspectionMapper.getListSubInspection(inspectionGroupId);
			group.setSubInspectionResponses(subInspectionResponses);

			for (SubInspectionResponse sub : subInspectionResponses) {
				Long subInspectionId = sub.getId();

				List<SubSubInspectionResponse> subSubInspection = inspectionMapper.getListSubSubInspection(subInspectionId);
				// * Set Sub_Sub Inspection
				sub.setSubSubInspectionResponses(subSubInspection);

				// * Set score to sub inspection
				List<Long> staticScore = new ArrayList<>();
				if (sub.getScore() != null && sub.getScore() == 5) {
					staticScore.add(1L);
					staticScore.add(2L);
					staticScore.add(3L);
					staticScore.add(4L);
					staticScore.add(5L);
				} else if (sub.getScore() != null && sub.getScore() == 2) {
					staticScore.add(0L);
					staticScore.add(5L);
				}
				// * Sub Inspection Score
				Float subInspectionScore = inspectionMapper.sumInspectoinScroeDetail(id, subInspectionId);
				if (subInspectionScore == null) {
					subInspectionScore = 0F;
				}
				sub.setScore(subInspectionScore);
				sub.setStaticScore(staticScore);
				// * Sub Inspection Percentage
				if (sub.getFullScore() != null && sub.getFullScore() != 0) {
					sub.setTotalPercentage(Float.parseFloat(String.format("%.2f", ((sub.getScore() / sub.getFullScore()) * sub.getPercentage()))));
				} else {
					sub.setTotalPercentage(0F);
				}

				if (subSubInspection != null) {
					for (SubSubInspectionResponse subSub : subSubInspection) {
						// * Get sub sub inspection score
						Long subSubInspectionScore = inspectionMapper.getScoreDetailById(id, subSub.getId());
						subSub.setScore(subSubInspectionScore);
					}
				}
			}
		}

		// * Set Form Data to Response
		response.setInspectionGroupResponses(inspectionGroupResponses);

		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", Collections.singletonList(response), true));
	}

	public ResponseMessage<BaseResult> getInspectionOne(Long id) {
		List<InspectionFindResponse> inspectionFindResponses = inspectionMapper.getInspectionOne(id);
		if (!inspectionFindResponses.isEmpty()) {
			// * Calculate Rank
			Float servicePercentage = inspectionFindResponses.get(0).getServicePercentage() != null ? inspectionFindResponses.get(0).getServicePercentage() : 0F;
			Float environtmentPercentage = inspectionFindResponses.get(0).getEnvirontmentPercentage() != null ? inspectionFindResponses.get(0).getEnvirontmentPercentage() : 0F;
			Float productPercentage = inspectionFindResponses.get(0).getProductPercentage() != null ? inspectionFindResponses.get(0).getProductPercentage() : 0F;

			Float totalPercentage = servicePercentage + environtmentPercentage + productPercentage;
			if (totalPercentage > 94) {
				inspectionFindResponses.get(0).setRank("A");
			} else if (totalPercentage >= 90) {
				inspectionFindResponses.get(0).setRank("B");
			} else if (totalPercentage >= 80) {
				inspectionFindResponses.get(0).setRank("C");
			} else if (totalPercentage >= 70) {
				inspectionFindResponses.get(0).setRank("D");
			} else if (totalPercentage < 70) {
				inspectionFindResponses.get(0).setRank("F");
			}
		}
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", inspectionFindResponses, true));
	}

	public ResponseMessage<BaseResult> getListInspectionGroup() {
		// * Inspection Group
		List<InspectionGroupResponse> inspectionGroupResponses = inspectionMapper.getListInspectionGroup();
		for (InspectionGroupResponse group : inspectionGroupResponses) {
			Long inspectionGroupId = group.getId();
			// * Set Sub Inspection
			List<SubInspectionResponse> subInspectionResponses = inspectionMapper.getListSubInspection(inspectionGroupId);
			group.setSubInspectionResponses(subInspectionResponses);

			for (SubInspectionResponse sub : subInspectionResponses) {
				Long subInspectionId = sub.getId();
				List<SubSubInspectionResponse> subSubInspection = inspectionMapper.getListSubSubInspection(subInspectionId);
				// * Set Sub_Sub Inspection
				sub.setSubSubInspectionResponses(subSubInspection);

				// * Set score to sub inspection
				List<Long> staticScore = new ArrayList<>();
				if (sub.getScore() != null && sub.getScore() == 5) {
					staticScore.add(1L);
					staticScore.add(2L);
					staticScore.add(3L);
					staticScore.add(4L);
					staticScore.add(5L);
				} else if (sub.getScore() != null && sub.getScore() == 2) {
					staticScore.add(0L);
					staticScore.add(5L);
				}
				sub.setScore(0F);
				sub.setTotalPercentage(0F);

				// * Set Sub Sub Inspection Score 0
				for (SubSubInspectionResponse subSub : subSubInspection) {
					subSub.setScore(0L);
				}

				sub.setStaticScore(staticScore);
			}
		}
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", inspectionGroupResponses, true));
	}

	public ResponseMessage<BaseResult> getListProvince() {
		List<ProvinceResponse> provinceResponses = inspectionMapper.getListProvince();
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", provinceResponses, true));
	}

	public ResponseMessage<BaseResult> getListQuater() {
		List<QuarterResponse> quarterResponses = inspectionMapper.getListQuater();
		return ResponseMessageUtils.makeResponse(true, messageService.message("Success", quarterResponses, true));
	}

	public ResponseMessage<BaseResult> insert(InspectionRequest inspectionRequest) {
		Long userId = userService.getUserAuth().getId();

		// * Check require data
		// if (inspectionRequest.getInspectionScoreRequests().size() < 43) {
		// return ResponseMessageUtils.makeResponse(true,
		// messageService.message("Require must be select all.", false));
		// }

		// * Check Data
		Inspection inspection = new Inspection();
		inspection.setGroupId(inspectionRequest.getGroupId());
		inspection.setEmployeeId(inspectionRequest.getEmployeeId());
		inspection.setDepartmentId(inspectionRequest.getDepartmentId());
		inspection.setProvinceId(inspectionRequest.getProvinceId());
		inspection.setAssessmentDate(inspectionRequest.getAssessmentDate());
		inspection.setAssessmentNote(inspectionRequest.getAssessmentNote());
		inspection.setCreatedBy(userId);
		inspection.setIsActive(1);

		if (inspectionRequest instanceof InspectionUpdate) {
			inspection.setModifiedBy(userId);
		}

		Boolean result = inspectionMapper.insert(inspection);
		if (result) {
			if (!inspectionRequest.getInspectionScoreRequests().isEmpty()) {
				// * Check Data Before Insert to table inspection score
				for (InspectionScoreRequest scoreRequest : inspectionRequest.getInspectionScoreRequests()) {
					if (scoreRequest.getScore() < 0 || scoreRequest.getScore() > 5) {
						return ResponseMessageUtils.makeResponse(true, messageService.message("Invalid Score", false));
					}

					InspectionScore inspectionScore = new InspectionScore();
					inspectionScore.setInspectionId(inspection.getId());
					inspectionScore.setSubSubInspectionId(scoreRequest.getSubSubInspectionId());
					inspectionScore.setScore(scoreRequest.getScore());
					inspectionScore.setCreatedBy(userId);
					inspectionMapper.insertInspectoinDetail(inspectionScore);
				}

				// * Calculate Inspection
				calculateInspection(inspection.getId());
			}
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	public ResponseMessage<BaseResult> update(InspectionUpdate inspectionUpdate) {
		Long userId = userService.getUserAuth().getId();

		// * Archive old record
		inspectionMapper.delete(inspectionUpdate.getId(), userId);

		// * Create new record
		return this.insert(inspectionUpdate);
	}

	public ResponseMessage<BaseResult> delete(Long id) {
		Long userId = userService.getUserAuth().getId();

		Boolean result = inspectionMapper.delete(id, userId);
		if (result) {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
		} else {
			return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
		}
	}

	// ? Calculate Inspection
	void calculateInspection(Long inspectionId) {
		// * Service Percentage
		Float servicePercentage = 0F;
		// * Enviroment Percentage
		Float enviromentPercentage = 0F;
		// * Product Percentage
		Float productPercentage = 0F;

		// * Service Score
		Float serviceScore = 0F;
		// * Enviroment Score
		Float enviromentScore = 0F;
		// * Product Score
		Float productScore = 0F;

		// * Inspection Group
		List<InspectionGroupResponse> inspectionGroupResponses = inspectionMapper.getListInspectionGroup();
		for (int i = 0; i < inspectionGroupResponses.size(); i++) {
			InspectionGroupResponse group = inspectionGroupResponses.get(i);
			Long inspectionGroupId = group.getId();

			// * Get Sub Inspection
			List<SubInspectionResponse> subInspectionResponses = inspectionMapper.getListSubInspection(inspectionGroupId);
			for (SubInspectionResponse sub : subInspectionResponses) {
				// * Sub Inspection id
				Long subInspectionId = sub.getId();
				// * Sum Inspection Score
				Float sumInspectionScore = inspectionMapper.sumInspectoinScroeDetail(inspectionId, subInspectionId);
				// * Sub Inspection Percentage
				Float standartSubInspectionPercentage = sub.getPercentage();
				// * Count Sub Sub Inspection By Inspection
				Long standartScore = inspectionMapper.standartScore(subInspectionId);

				// * Calculate Percentage
				Float subInspectionPercentage = (sumInspectionScore / standartScore) * standartSubInspectionPercentage;

				// ? Check data before insert into table /*sub_inspection_form*/
				SubInspectionRequest subInspectionRequest = new SubInspectionRequest();
				subInspectionRequest.setInspectionId(inspectionId);
				subInspectionRequest.setSubInspectionId(subInspectionId);
				subInspectionRequest.setTotalScore(sumInspectionScore);
				subInspectionRequest.setPercentage(subInspectionPercentage);
				inspectionMapper.insertSubInspection(subInspectionRequest);

				// * Sum all service percentage
				if (i == 0) {
					servicePercentage += subInspectionRequest.getPercentage();
					serviceScore += subInspectionRequest.getTotalScore();
				}
				// * Sum all enviroment percentage
				if (i == 1) {
					enviromentPercentage += subInspectionRequest.getPercentage();
					enviromentScore += subInspectionRequest.getTotalScore();
				}
				// * Sum all product percentage
				if (i == 2) {
					productPercentage += subInspectionRequest.getPercentage();
					productScore += subInspectionRequest.getTotalScore();
				}
			}

			// * Update inspection
			InspectionScoreUpdate inspectionScoreUpdate = new InspectionScoreUpdate();
			inspectionScoreUpdate.setId(inspectionId);
			inspectionScoreUpdate.setServicePercentage(servicePercentage);
			inspectionScoreUpdate.setEnviromentPercentage(enviromentPercentage);
			inspectionScoreUpdate.setProductPercentage(productPercentage);
			inspectionScoreUpdate.setServiceScore(serviceScore);
			inspectionScoreUpdate.setEnviromentScore(enviromentScore);
			inspectionScoreUpdate.setProductScore(productScore);
			inspectionMapper.updateScores(inspectionScoreUpdate);
		}
	}
}
