package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.ExpenseRequest;
import com.ut.nlSystemAPi.model.ExpenseRequestDetail;
import com.ut.nlSystemAPi.model.ExpenseRequestServiceModel;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.filter.ExpenseRequestFilter;
import com.ut.nlSystemAPi.model.request.Login.ExpenseRequest.*;
import com.ut.nlSystemAPi.model.response.ExpenseRequest.ExpenseRequestApproveResponse;
import com.ut.nlSystemAPi.model.response.ExpenseRequest.ExpenseRequestDetailResponse;
import com.ut.nlSystemAPi.model.response.ExpenseRequest.ExpenseRequestResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRequestMapper {

   List<ExpenseRequestResponse> getList(@Param("filter") ExpenseRequestFilter filter, @Param("userId") Long userId);

   Long countList(@Param("filter") Filter filter, @Param("userId") Long userId);

   List<ExpenseRequestResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

   List<ExpenseRequestDetailResponse> getListReceiptDetail(@Param("id") Long id);

   Boolean updatePurchaseBill(@Param("pvRequestId") Long pvRequestId, @Param("id") Long id);

   Boolean updatePurchaseOrder(@Param("pvRequestId") Long pvRequestId, @Param("id") Long id);

   Boolean insert(@Param("expenseRequest") ExpenseRequest expenseRequest);

   Boolean insertPurchaseExpenseRequest(@Param("expenseRequest") ExpenseRequest expenseRequest);

   Boolean insertDeposit(@Param("depositApproveRequest") DepositApproveRequest depositApproveRequest);

   Boolean updateDeposit(@Param("depositApproveRequest") DepositApproveRequest depositApproveRequest);

   Boolean insertPvRequestDetail(@Param("expenseRequestDetail") ExpenseRequestDetail expenseRequestDetail);

   Boolean insertPvRequestService(@Param("expenseRequestServiceModel") ExpenseRequestServiceModel expenseRequestServiceModel);

   Boolean archive(@Param("id") Long id, @Param("userId") Long userId);

   Boolean updatePurchaseRequest(@Param("id") Long id, @Param("userId") Long userId);

   Boolean update(@Param("expenseRequest") ExpenseRequest expenseRequest);

   Boolean updateClose(@Param("closeRequest") CloseRequest closeRequest);

   Boolean updateApprove(@Param("approve") ApproveRequest approveRequest);

   Boolean updateStatus(@Param("status") Long status, @Param("id") Long id);

   Boolean delete(@Param("id")  Long id,@Param("userId") Long userid);

   Boolean deletePurchaseExpenseRequest(@Param("id") Long id);

   Boolean deletePvRequestDetail(@Param("id") Long id);

   Boolean deletePvRequestService(@Param("id") Long id);

   Long getCreatedBy(@Param("id") Long id);

   String getPvCode(@Param("id") Long id);

   String getLastCode();

   Double sumTotalAmountApprove(@Param("id") Long id);

   List<ExpenseRequestApproveResponse> getListApprove(@Param("id") Long id);

}