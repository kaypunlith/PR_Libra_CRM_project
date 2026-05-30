package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.GeneralLedger;
import com.ut.nlSystemAPi.model.GeneralLedgerDetail;
import com.ut.nlSystemAPi.model.Reconcile;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.Reconcile.ReconcileResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReconcileMapper {

  List<ReconcileResponse> getReconcileDebitList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<ReconcileResponse> getReconcileCreditList(@Param("filter") Filter filter);

  Long countCreditList(@Param("filter") Filter filter);

  Boolean insert(@Param("reconcile") Reconcile reconcile);

  Boolean updateGeneralLedgerDetailIsReconcile(@Param("generalLedgerDetailId") Long generalLedgerDetailId, @Param("reconcileId") Long reconcileId);

  Boolean insertGeneralLedger(@Param("generalLedger") GeneralLedger generalLedger);

  Boolean insertGeneralLedgerDetail(@Param("generalLedgerDetail") GeneralLedgerDetail generalLedgerDetail);

  Boolean updateReconcileServiceChargeGlId(@Param("generalLedgerId") Long generalLedgerId, @Param("reconcileId") Long reconcileId);

  Boolean updateReconcileInterestedEarnedGlId(@Param("generalLedgerId") Long generalLedgerId, @Param("reconcileId") Long reconcileId);

  Boolean updateReconcileDiffGlId(@Param("generalLedgerId") Long generalLedgerId, @Param("reconcileId") Long reconcileId);

  Double getBeginingBalance(@Param("filter") Filter filter);
}