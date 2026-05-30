package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.CreditMemo.*;
import com.ut.nlSystemAPi.model.filter.CreditMemoReceiptFilter;
import com.ut.nlSystemAPi.model.filter.CreditMemoFilter;
import com.ut.nlSystemAPi.model.response.CreditMemo.CreditMemoDetailResponse;
import com.ut.nlSystemAPi.model.response.CreditMemo.CreditMemoReceiptResponse;
import com.ut.nlSystemAPi.model.response.CreditMemo.CreditMemoResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditMemoMapper {

    List<CreditMemoResponse> getList(@Param("filter") CreditMemoFilter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") CreditMemoFilter filter, @Param("userId") Long userId);

    List<CreditMemoResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

    List<CreditMemoDetailResponse> getListDetail(@Param("creditMemoId") Long creditMemoId);

    Boolean insert(@Param("creditMemo") CreditMemo creditMemo);

    Boolean insertDetail(@Param("detail") CreditMemoDetail detail);

    Boolean insertService(@Param("service") CreditMemoServices service);

    Boolean insertMisc(@Param("misc") CreditMemoMisc misc);

    Boolean insertReceipt(@Param("receipt") CreditMemoReceipt receipt);

    Boolean updateBalanceCreditMemo(@Param("creditMemo") CreditMemo creditMemo);

    Boolean updateBalanceSaleInvoice(@Param("salesInvoiceId") Long salesInvoiceId, @Param("balance") Double balance, @Param("type") Long type, @Param("userId") Long userId);

    Boolean insertReceiptWithInvoice(@Param("receipt") CreditMemoWithInvoiceReceipt receipt);

    Boolean updateBalance(@Param("creditMemoId") Long id, @Param("balance") Double balance, @Param("type") Long type);

    List<CreditMemoReceiptResponse> listReceipt(@Param("filter") CreditMemoReceiptFilter filter);

    List<CreditMemoReceiptResponse> listReceiptApplyInvoice(@Param("filter") CreditMemoReceiptFilter filter);

    Boolean voidReceipt(@Param("request") CreditMemoReceiptFilter request, @Param("userId") Long userId);

    Boolean voidReceiptApplyInvoice(@Param("request") CreditMemoReceiptFilter request, @Param("userId") Long userId);

}