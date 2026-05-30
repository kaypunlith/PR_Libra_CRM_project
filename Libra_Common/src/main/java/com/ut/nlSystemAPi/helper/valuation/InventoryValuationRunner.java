package com.ut.nlSystemAPi.helper.valuation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class InventoryValuationRunner {

    private InventoryValuationRunner() {
    }

    public static void runValuation(InventoryValuationGateway gateway) {
        runInventoryValuation(gateway);
    }

    public static void runInventoryValuation(InventoryValuationGateway gateway) {
        List<InventoryValuationCalcDTO> calcRecords = new ArrayList<>(gateway.getInventoryValuationCalcs());
        List<InventoryValuationCalcDTO> fallbackRecords = gateway.getInventoryValuationCalcsFallback();
        if (fallbackRecords != null && !fallbackRecords.isEmpty()) {
            Set<String> seen = new HashSet<>();
            for (InventoryValuationCalcDTO calc : calcRecords) {
                if (calc.getProductId() == null || calc.getDate() == null) {
                    continue;
                }
                seen.add(calc.getProductId() + ":" + calc.getDate());
            }
            for (InventoryValuationCalcDTO calc : fallbackRecords) {
                if (calc.getProductId() == null || calc.getDate() == null) {
                    continue;
                }
                String key = calc.getProductId() + ":" + calc.getDate();
                if (seen.add(key)) {
                    calcRecords.add(calc);
                }
            }
        }
        calcRecords.sort(Comparator.comparing(InventoryValuationCalcDTO::getDate, Comparator.nullsLast(Comparator.naturalOrder())));

        for (InventoryValuationCalcDTO calc : calcRecords) {
            // Update lock
            gateway.updateValuationCalcLock(calc.getProductId(), calc.getDate(), LocalDateTime.now());

            LocalDate calDate = calc.getDate().minusDays(1);
            Map<Long, BigDecimal> accTotalCost = new HashMap<>();
            Map<Long, BigDecimal> accTotalQty = new HashMap<>();
            Map<Long, BigDecimal> accTotalQtySmall = new HashMap<>();
            Map<Long, BigDecimal> oldAvgCost = new HashMap<>();

            // Get initial values
            InventoryValuationDTO initialValuation = gateway.getInitialInventoryValuation(calc.getProductId(), calDate);
            if (initialValuation != null) {
                Long pid = initialValuation.getPid();
                accTotalCost.put(pid, initialValuation.getAssetValue());
                accTotalQty.put(pid, initialValuation.getOnHand());
                accTotalQtySmall.put(pid, initialValuation.getOnHandSmall());
                oldAvgCost.put(pid, initialValuation.getAvgCost());
            }

            // List records to calculate average cost
            List<InventoryValuationDTO> valuations = gateway.getInventoryValuationsForCalculation(calc.getProductId(), calDate);
            System.out.println(valuations);
            for (InventoryValuationDTO valuation : valuations) {
                Long pid = valuation.getPid();

                // Initialize accumulators if not set
                accTotalCost.putIfAbsent(pid, BigDecimal.ZERO);
                accTotalQty.putIfAbsent(pid, BigDecimal.ZERO);
                accTotalQtySmall.putIfAbsent(pid, BigDecimal.ZERO);
                // Get default cost if no previous average cost
                if (!oldAvgCost.containsKey(pid)) {
                    ProductDTO product = gateway.getProductDefaultCost(pid);
                    oldAvgCost.put(pid, product != null ? product.getDefaultCost() : BigDecimal.ZERO);
                }

                BigDecimal cost;
                BigDecimal avgCost;
                BigDecimal assetVal;

                if (valuation.getIsAdjustValue() == 1) {
                    accTotalCost.put(pid, valuation.getAssetValue());
                    accTotalQty.put(pid, accTotalQty.get(pid).add(valuation.getQty()));
                    accTotalQtySmall.put(pid, accTotalQtySmall.get(pid).add(valuation.getSmallQty()));

                    cost = accTotalQty.get(pid).compareTo(BigDecimal.ZERO) != 0
                            ? accTotalCost.get(pid).divide(accTotalQty.get(pid), 9, RoundingMode.HALF_UP)
                            : BigDecimal.ZERO;

                    avgCost = cost;
                    assetVal = normalizeAssetValue(accTotalCost.get(pid));
                    BigDecimal onHand = accTotalQty.get(pid).setScale(9, RoundingMode.HALF_UP);
                    BigDecimal onHandSmall = accTotalQtySmall.get(pid).setScale(9, RoundingMode.HALF_UP);

                    gateway.updateInventoryValuation(
                            valuation.getId(),
                            onHand,
                            onHandSmall,
                            cost,
                            avgCost,
                            assetVal
                    );
                } else if (valuation.getIsVarCost() == 1) {
                    boolean hasSalesOrder = valuation.getSalesOrderId() != null;

                    BigDecimal glDetailVal = valuation.getQty().multiply(oldAvgCost.get(pid)).setScale(12, RoundingMode.HALF_UP);
                    if (hasSalesOrder) {
                        glDetailVal = glDetailVal.negate();
                    }
                    accTotalCost.put(pid, accTotalCost.get(pid).add(valuation.getQty().multiply(oldAvgCost.get(pid))));
                    accTotalQty.put(pid, accTotalQty.get(pid).add(valuation.getQty()));
                    accTotalQtySmall.put(pid, accTotalQtySmall.get(pid).add(valuation.getSmallQty()));

                    cost = oldAvgCost.get(pid).setScale(9, RoundingMode.HALF_UP);
                    avgCost = cost;

                    assetVal = normalizeAssetValue(accTotalCost.get(pid));
                    BigDecimal onHand = accTotalQty.get(pid).setScale(9, RoundingMode.HALF_UP);
                    BigDecimal onHandSmall = accTotalQtySmall.get(pid).setScale(9, RoundingMode.HALF_UP);

                    gateway.updateInventoryValuation(
                            valuation.getId(),
                            onHand,
                            onHandSmall,
                            cost,
                            avgCost,
                            assetVal
                    );

                    // Update general ledger details
                    gateway.updateGeneralLedgerCredit(valuation.getId(), glDetailVal);

                    if (valuation.getPrice() != null) {
                        BigDecimal cogs = valuation.getQty().multiply(valuation.getPrice())
                                .subtract(valuation.getQty().multiply(oldAvgCost.get(pid)))
                                .setScale(12, RoundingMode.HALF_UP);
                        System.out.println("if");
                        System.out.println(cogs);

                        if (cogs.compareTo(BigDecimal.ZERO) > 0) {
                            gateway.updateGeneralLedgerCogsCredit(valuation.getId(), cogs);
                        } else if (cogs.compareTo(BigDecimal.ZERO) < 0) {
                            gateway.updateGeneralLedgerCogsDebit(valuation.getId(), cogs.abs());
                        } else {
                            gateway.updateGeneralLedgerCogsZero(valuation.getId());
                        }
                    } else {
                        System.out.println(valuation.getQty());
                        BigDecimal cogs = valuation.getQty().multiply(oldAvgCost.get(pid)).setScale(12, RoundingMode.HALF_UP);
                        System.out.println("else");
                        System.out.println(cogs);
                        if (hasSalesOrder) {
                            cogs = cogs.negate();
                        }
                        gateway.updateGeneralLedgerCogsDebit(valuation.getId(), cogs);
                    }
                } else {
                    accTotalCost.put(pid, accTotalCost.get(pid).add(valuation.getQty().multiply(valuation.getCost())));
                    accTotalQty.put(pid, accTotalQty.get(pid).add(valuation.getQty()));
                    accTotalQtySmall.put(pid, accTotalQtySmall.get(pid).add(valuation.getSmallQty()));

                    avgCost = accTotalQty.get(pid).compareTo(BigDecimal.ZERO) != 0
                            ? accTotalCost.get(pid).divide(accTotalQty.get(pid), 9, RoundingMode.HALF_UP)
                            : BigDecimal.ZERO;
                    assetVal = normalizeAssetValue(accTotalCost.get(pid));

                    BigDecimal onHand = accTotalQty.get(pid).setScale(9, RoundingMode.HALF_UP);
                    BigDecimal onHandSmall = accTotalQtySmall.get(pid).setScale(9, RoundingMode.HALF_UP);

                    gateway.updateInventoryValuation(
                            valuation.getId(),
                            onHand,
                            onHandSmall,
                            valuation.getCost(),
                            avgCost,
                            assetVal
                    );
                }

                // Update old average cost
                if (accTotalCost.get(pid).compareTo(BigDecimal.ZERO) != 0 ||
                        accTotalQty.get(pid).compareTo(BigDecimal.ZERO) != 0) {
                    oldAvgCost.put(pid, accTotalQty.get(pid).compareTo(BigDecimal.ZERO) != 0
                            ? accTotalCost.get(pid).divide(accTotalQty.get(pid), 9, RoundingMode.HALF_UP)
                            : BigDecimal.ZERO);
                }
            }

            // Delete lock
            gateway.deleteValuationCalc(calc.getProductId(), calc.getDate());
        }
    }

    private static BigDecimal normalizeAssetValue(BigDecimal assetValue) {
        BigDecimal safeValue = assetValue == null ? BigDecimal.ZERO : assetValue;
        return safeValue.max(BigDecimal.ZERO).setScale(9, RoundingMode.HALF_UP);
    }
}
