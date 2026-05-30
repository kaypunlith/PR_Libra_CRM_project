package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.InspectionFilter;
import com.ut.nlSystemAPi.model.request.Inspection.InspectionRequest;
import com.ut.nlSystemAPi.model.request.Inspection.InspectionUpdate;

public interface InspectionService {

	ResponseMessage<BaseResult> getList(InspectionFilter filter);

	ResponseMessage<BaseResult> getListSummaryInspection(InspectionFilter filter);

	ResponseMessage<BaseResult> getOne(Long id);

	ResponseMessage<BaseResult> getInspectionOne(Long id);

	ResponseMessage<BaseResult> getListInspectionGroup();

	ResponseMessage<BaseResult> getListProvince();

	ResponseMessage<BaseResult> getListQuater();

	ResponseMessage<BaseResult> insert(InspectionRequest inspectionRequest);

	ResponseMessage<BaseResult> update(InspectionUpdate inspectionUpdate);

	ResponseMessage<BaseResult> delete(Long id);

}