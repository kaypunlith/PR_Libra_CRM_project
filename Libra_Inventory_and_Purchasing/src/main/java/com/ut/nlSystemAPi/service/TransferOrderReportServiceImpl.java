package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.TransferOrderReportMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.TransferOrderReportByItemFilter;
import com.ut.nlSystemAPi.model.filter.TransferOrderReportFilter;
import com.ut.nlSystemAPi.model.response.TransferOrderReport.TransferOrderReportByItemResponse;
import com.ut.nlSystemAPi.model.response.TransferOrderReport.TransferOrderReportByItemDetailResponse;
import com.ut.nlSystemAPi.model.response.TransferOrderReport.TransferOrderReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class TransferOrderReportServiceImpl implements TransferOrderReportService {
    @Autowired
    private TransferOrderReportMapper transferOrderReportMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(TransferOrderReportFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(transferOrderReportMapper.countList(filter));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<TransferOrderReportResponse> transferOrderReportResponses = transferOrderReportMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/TransferOrderReport/list", null, null, "transferOrderReport", "transferOrderReport(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", transferOrderReportResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/transferOrderReport/list", line, error.toString(), "transferOrderReport", "transferOrderReport(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListByItem(TransferOrderReportByItemFilter filterByItem, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filterByItem.getPage());
            pagination.setRowsPerPage(filterByItem.getRowsPerPage());
            filterByItem.setPage((filterByItem.getPage() - 1) * filterByItem.getRowsPerPage());
            List<?> ReportByItemResponses = null;
            if(filterByItem.getView() > 0){

                // Item Summary
                if (filterByItem.getView() == 2) {
                    List<TransferOrderReportByItemResponse> responses = transferOrderReportMapper.getListByItemSummary(filterByItem);
                    System.out.println(responses);
                    pagination.setTotal(transferOrderReportMapper.countListByItemSummary(filterByItem));
                    if (!responses.isEmpty()){
                        for (int i = 0; i < responses.size(); i++) {
                            System.out.println(responses.get(i).getParentId());
                            List<TransferOrderReportByItemDetailResponse> details = transferOrderReportMapper.getListByItemSummaryDetail(responses.get(i).getParentId(), filterByItem);
                            for (int j = 0; j < details.size(); j++) {
                                details.get(j).setTotalCost(details.get(j).getQty() * details.get(j).getUnitCost());
                            }
                            responses.get(i).setDetails(details);
                            details.get(0).setSubTotalQty(transferOrderReportMapper.getSubTotalCost(responses.get(i).getParentId(), filterByItem));
                        }
                        //! Get Grand Total
                        TransferOrderReportByItemDetailResponse grandTotalResponse = transferOrderReportMapper.getGrandTotalCost(filterByItem);
                        responses.get(0).setGrandTotalQty(grandTotalResponse.getQty());
                        responses.get(0).setGrandTotal(grandTotalResponse.getGrandDetailTotalCost());
                    }
                    ReportByItemResponses = responses;
                }

                // Item Detail
                else if(filterByItem.getView() == 3){
                    pagination.setTotal(Long.valueOf(transferOrderReportMapper.countListByItemDetail(filterByItem).size()));

                    List<TransferOrderReportByItemResponse> responses = transferOrderReportMapper.getListByItemDetail(filterByItem);

                    if (!responses.isEmpty()){
                        for (int i = 0; i < responses.size(); i++) {
                            if(responses.get(i) != null){
                                List<TransferOrderReportByItemDetailResponse> details = transferOrderReportMapper.getListByItemDetailDetail(responses.get(i).getParentId(), filterByItem);
                                responses.get(i).setDetails(details);
                                for (int j = 0; j < details.size(); j++) {
                                    details.get(j).setTotalCost((details.get(j).getQty() * details.get(j).getConversion()) * details.get(j).getUnitCost());
                                }
                                details.get(0).setSubTotalQty(transferOrderReportMapper.getSubTotalCost(responses.get(i).getParentId(), filterByItem));
                            }
                        }

                        //! Get Grand Total 
                        TransferOrderReportByItemDetailResponse grandTotalResponse = transferOrderReportMapper.getGrandTotalCost(filterByItem);
                        responses.get(0).setGrandTotalQty(grandTotalResponse.getQty());
                        responses.get(0).setGrandTotal(grandTotalResponse.getGrandDetailTotalCost());
                    }
                    ReportByItemResponses = responses;
                }

//                // Parent Summary
//                else if(filterByItem.getView() == 1){
//                    List<TransferOrderReportByItemResponse> responses = transferOrderReportMapper.getListByItemParentSummary(filterByItem);
//                    pagination.setTotal(transferOrderReportMapper.countListByParentSummary(filterByItem));
//                    responses.get(0).setGrandTotal(transferOrderReportMapper.sumGrandTotalParentSummary(filterByItem));
//                    ReportByItemResponses = responses;
//                }

            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/TransferOrderReportByItem/list", null, null, "TransferOrderReportByItem", "TransferOrderReportByItem(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", ReportByItemResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/TransferOrderReportByItem/list", line, error.toString(), "TransferOrderReportByItem", "TransferOrderReportByItem(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}
