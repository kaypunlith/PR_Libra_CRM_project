package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.Inventory;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.freedom.FreedomMapper;
import com.ut.nlSystemAPi.mapper.primary.DeliveryMapper;
import com.ut.nlSystemAPi.mapper.primary.HelperMapper;
import com.ut.nlSystemAPi.mapper.primary.OrganizationMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.SaleInvoiceMapper;
import com.ut.nlSystemAPi.model.base.*;
import com.ut.nlSystemAPi.model.entity.Delivery.Delivery;
import com.ut.nlSystemAPi.model.entity.Delivery.DeliveryDetail;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.request.Delivery.DeliveryRequest;
import com.ut.nlSystemAPi.model.filter.SaleInvoiceFilter;
import com.ut.nlSystemAPi.model.response.Delivery.DeliveryDetailResponse;
import com.ut.nlSystemAPi.model.response.Delivery.DeliveryResponse;
import com.ut.nlSystemAPi.model.response.SaleInvoice.SaleInvoiceDetailResponse;
import com.ut.nlSystemAPi.model.response.SaleInvoice.SaleInvoiceResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.DeliveryService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private GenerateCode generateCode;

    @Autowired
    private Inventory inventory;

    @Autowired
    private HelperMapper helperMapper;

    @Autowired
    private SaleInvoiceMapper saleInvoiceMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private FreedomMapper freedomMapper;

    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Delivery Note (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(deliveryMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<DeliveryResponse> responses = deliveryMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-note/list", null, null, "Delivery Note", "Delivery Note (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-note/list", line, error.toString(), "Delivery Note", "Delivery Note (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Delivery Note (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<DeliveryResponse> responses = deliveryMapper.getOne(id);
            if (!responses.isEmpty()) {
                for (DeliveryResponse response : responses) {
                    response.setDetails(deliveryMapper.getDetails(response.getId()));
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-note/find/{id}", null, null, "Delivery Note", "Delivery Note (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-note/find/{id}", line, error.toString(), "Delivery Note", "Delivery Note (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(DeliveryRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Delivery Note (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Delivery delivery = new Delivery();
            delivery.setCompanyId(request.getCompanyId());
            delivery.setDate(request.getDate());
            delivery.setNote(request.getNote());
            delivery.setWarehouseId(request.getWarehouseId());
            delivery.setCustomerGroupId(request.getCustomerGroupId());
            delivery.setCustomerId(request.getCustomerId());
            delivery.setDeliveryId(request.getDeliveryId());
            delivery.setStatus(1);
            delivery.setCreatedBy(userId);
            Boolean result = deliveryMapper.insert(delivery);
            if (result) {
                // GENERATE CODE
                String code = generateCode.generateAutoCode("deliveries", "code", 7, helperMapper.getCompanyModuleCode(request.getCompanyId(), "dn_code"), true, "status >= 0");
                helperMapper.updateCode("deliveries", "code", code, delivery.getId());
                if (request.getSalesInvoiceIds() != null) {
                    DeliveryDetail detail = new DeliveryDetail();
                    for (Long salesInvoiceId : request.getSalesInvoiceIds()) {
                        deliveryMapper.updateSalesInvoice(salesInvoiceId, delivery.getId());
                        List<SaleInvoiceDetailResponse> detailResponses = saleInvoiceMapper.getListDetail(salesInvoiceId);
                        if (!detailResponses.isEmpty()) {
                            for (SaleInvoiceDetailResponse detailResponse : detailResponses) {
                                detail.setDeliveryId(delivery.getId());
                                detail.setSalesInvoiceId(salesInvoiceId);
                                detail.setSalesInvoiceDetailId(detailResponse.getId());
                                detail.setProductId(detailResponse.getItemId());
                                detail.setLocationId(deliveryMapper.getLocationHavingStock(request.getWarehouseId()));
                                detail.setUomId(detailResponse.getUomId());
                                detail.setQty(detailResponse.getQty() + detailResponse.getQtyFree());
                                detail.setExpiredDate("0000-00-00");
                                deliveryMapper.insertDetail(detail);
                            }
                        }
                    }
                }

                LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-note/add", null, null, "Delivery Note", "Delivery Note (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true, delivery.getId()));
        } else {
            return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
        }
    } catch (Exception error) {
        LocalTime endDuration = LocalTime.now();
        activityLogService.insert("/delivery-note/add", line, error.toString(), "Delivery Note", "Delivery Note (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
        return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
    }

    @Override
    public ResponseMessage<BaseResult> pick(Long id, Integer isPos, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Delivery Note (Pick)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<DeliveryResponse> deliveries = deliveryMapper.getOne(id);

            if (deliveries.isEmpty()) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Data Cannot Be Found.", false));
            }
            DeliveryResponse delivery = deliveries.get(0);

            List<DeliveryDetailResponse> details = deliveryMapper.getDetails(id);
            if (details != null && !details.isEmpty()) {
                Integer locationStatus = helperMapper.checkLocationSetting(4);
                Set<Long> syncedInvoiceIds = new HashSet<>();
                for (DeliveryDetailResponse detail : details) {

                    if (detail.getSalesInvoiceId() != null) {
                        // UPDATE SALES INVOICE
                        deliveryMapper.updateStatusSalesInvoice(detail.getSalesInvoiceId());

                        if (!syncedInvoiceIds.contains(detail.getSalesInvoiceId())) {
                            syncFreedomPurchaseBillBySalesInvoiceId(detail.getSalesInvoiceId(), userId, delivery.getDate(), details);
                            syncedInvoiceIds.add(detail.getSalesInvoiceId());
                        }
                    }

                    // UPDATE STATUS SALES ORDER IF FULLY DELIVERED
                    if (detail.getSaleOrderId() != null) {
                        Long itemOrder = deliveryMapper.countItemOrder(detail.getSaleOrderId());
                        Long itemDelivery = deliveryMapper.countItemDelivery(detail.getSaleOrderId());
                        if (Objects.equals(itemOrder, itemDelivery)) {
                            deliveryMapper.updateStatusSalesOrder(detail.getSaleOrderId());
                        }
                    }

                    List<StockOrder> stockOrders = helperMapper.getStockOrder(detail.getSalesInvoiceId(), detail.getProductId(), delivery.getWarehouseId(), locationStatus);

                    if (stockOrders != null && !stockOrders.isEmpty()) {
                        for (StockOrder order : stockOrders) {
                            // STOCK OUT
                            GlobalStock globalStock = new GlobalStock();
                            globalStock.setSalesInvoiceId(detail.getSalesInvoiceId());
                            globalStock.setProductId(order.getProductId());
                            globalStock.setLocationId(order.getLocationId());
                            globalStock.setWarehouseId(order.getLocationGroupId());
                            globalStock.setLotsNumber(order.getLotsNumber() != null ? order.getLotsNumber() : "0");
                            globalStock.setExpiredDate(order.getExpiredDate());
                            globalStock.setTotalQty(order.getQty());
                            globalStock.setTotalOrder(order.getQty());
                            globalStock.setCreatedBy(userId);
                            globalStock.setCustomerId(delivery.getCustomerId());
                            String fieldToUpdate;
                            if (isPos != null && isPos == 1){
                            globalStock.setType("POS");
                            fieldToUpdate = "total_pos";
                            } else {
                            globalStock.setType("Sales");
                            fieldToUpdate = "total_so";
                            }
                            inventory.insertStock(globalStock, 2L, fieldToUpdate, null);
                            inventory.insertOrder(globalStock, 2L);
                        }
                    }
                    // CLEAR STOCK ORDER
                    deliveryMapper.updateStockOrders(detail.getSalesInvoiceId(), detail.getProductId(), delivery.getWarehouseId());
                }

            }

            deliveryMapper.updateStatus(id, 2, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-note/pick/{id}", null, null, "Delivery Note", "Delivery Note (Pick)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-note/pick/{id}", line, error.toString(), "Delivery Note", "Delivery Note (Pick)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> undo(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Delivery Note (Undo)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            List<DeliveryResponse> deliveries = deliveryMapper.getOne(id);
            if (deliveries.isEmpty()) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Data Cannot Be Found.", false));
            }

            DeliveryResponse delivery = deliveries.get(0);
            List<DeliveryDetailResponse> details = deliveryMapper.getDetails(id);

            if (details != null && !details.isEmpty()) {
                Integer locationStatus = helperMapper.checkLocationSetting(4);
                for (DeliveryDetailResponse detail : details) {
                    List<StockOrder> stockOrders = helperMapper.getStockOrder(detail.getSalesInvoiceId(), detail.getProductId(), delivery.getWarehouseId(), locationStatus);

                    if (stockOrders != null && !stockOrders.isEmpty()) {
                        for (StockOrder order : stockOrders) {
                            // STOCK OUT
                            GlobalStock globalStock = new GlobalStock();
                            globalStock.setSalesInvoiceId(detail.getSalesInvoiceId());
                            globalStock.setProductId(order.getProductId());
                            globalStock.setLocationId(order.getLocationId());
                            globalStock.setWarehouseId(order.getLocationGroupId());
                            globalStock.setLotsNumber(order.getLotsNumber() != null ? order.getLotsNumber() : "0");
                            globalStock.setExpiredDate(order.getExpiredDate() != null ? order.getExpiredDate() : "0000-00-00");
                            globalStock.setTotalQty(order.getQty());
                            globalStock.setTotalOrder(order.getQty());
                            globalStock.setType("Sales");
                            globalStock.setCreatedBy(userId);
                            globalStock.setCustomerId(delivery.getCustomerId());
                            inventory.insertStock(globalStock, 1L, "total_so", null);
                            inventory.insertOrder(globalStock, 1L);
                        }

//                        Long smallQty = (detail.getQty() + detail.getQtyFree()) * detail.getConversion();
//
//                        // Calculate Qty, Location, Lot, Expired Date
//                        List<StockOrder> stockOrderResponses = helperMapper.getGroupTotal(saleInvoice.getId(), detail.getItemId(), request.getWarehouseId(), locationStatus);
//
//                        // ROLL BACK STOCK ORDER
//                        StockOrder stockOrder = new StockOrder();
//                        stockOrder.setSaleInvoiceId(saleInvoice.getId());
//                        stockOrder.setProductId(detail.getItemId());
//                        stockOrder.setLocationGroupId(request.getWarehouseId());
//                        stockOrder.setLocationId(stockOrderResponse.getLocationId());
//                        stockOrder.setLotsNumber(stockOrderResponse.getLotsNumber());
//                        stockOrder.setExpiredDate(stockOrderResponse.getExpiredDate());
//                        stockOrder.setDate(saleInvoice.getInvoiceDate());
//                        stockOrder.setQty(smallQty);
//                        saleInvoiceMapper.insertStockOrder(stockOrder);
                    }
                }
            }

            deliveryMapper.updateStatus(id, 1, userId);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-note/undo/{id}", null, null, "Delivery Note", "Delivery Note (Undo)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-note/undo/{id}", line, error.toString(), "Delivery Note", "Delivery Note (Undo)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listInvoiceProduct(SaleInvoiceFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Sales / Invoice (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            if (permissionMapper.checkPermission(userId, "Sales / Invoice (View By User)") > 0) {
                filter.setViewByUser(1L);
            }
            filter.setIsDn(1);

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(saleInvoiceMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<SaleInvoiceResponse> responses = saleInvoiceMapper.getList(filter, userId);
            if (!responses.isEmpty()) {
                for (SaleInvoiceResponse response : responses) {
                    response.setDetails(saleInvoiceMapper.getListDetail(response.getId()));
                }
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-note/list-invoice-product", null, null, "Delivery Note", "Delivery Note (View Invoice Product)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-note/list-invoice-product", line, error.toString(), "Delivery Note", "Delivery Note (View Invoice Product)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> approve(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            //            if (permissionMapper.checkPermission(userId, "Delivery Note (Approve)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Long userId = userService.getUserAuth().getId();

            Boolean result = deliveryMapper.approve(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/delivery-note/approve/{id}", null, null, "Delivery Note", "Delivery Note (Approve)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/delivery-note/approve/{id}", line, error.toString(), "Delivery Note", "Delivery Note (Approve)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private void syncFreedomPurchaseBillBySalesInvoiceId(Long salesInvoiceId, Long userId, String deliveryDate, List<DeliveryDetailResponse> deliveryDetails) {
        try {

            if (salesInvoiceId == null) {
                return;
            }

            List<SaleInvoiceResponse> invoices = saleInvoiceMapper.getOne(salesInvoiceId, userId);

            if (invoices == null || invoices.isEmpty()) {
                return;
            }

            SaleInvoiceResponse invoice = invoices.get(0);
            if (invoice.getOrganizationId() == null) {
                return;
            }

            Map<Long, String> expiredDateByInvoiceDetailId = new HashMap<>();
            if (deliveryDetails != null) {
                for (DeliveryDetailResponse deliveryDetail : deliveryDetails) {
                    if (deliveryDetail == null) {
                        continue;
                    }
                    if (!Objects.equals(deliveryDetail.getSalesInvoiceId(), salesInvoiceId)) {
                        continue;
                    }
                    if (deliveryDetail.getSalesInvoiceDetailId() == null) {
                        continue;
                    }
                    expiredDateByInvoiceDetailId.put(deliveryDetail.getSalesInvoiceDetailId(), deliveryDetail.getExpiredDate());
                }
            }

            Integer isFreedom = organizationMapper.getIsFreedom(invoice.getOrganizationId());

            if (isFreedom == null || isFreedom != 1) {
                return;
            }

            String invoiceCodePb = invoice.getOrganizationPoNo() != null && !invoice.getOrganizationPoNo().trim().isEmpty()
                    ? invoice.getOrganizationPoNo()
                    : invoice.getInvoiceNo();

            String pbCode = generateCode.generateAutoCode(freedomMapper, "purchase_orders", "po_code", 7, "PB", true, "status >= 0");
            if (pbCode == null || pbCode.trim().isEmpty()) {
                pbCode = generateCode.generateAutoCode(freedomMapper, "purchase_orders", "po_code", 7, "PB", true, "status >= 0");
            }

            Map<String, Object> pb = new HashMap<>();
            pb.put("poType", 1);
            pb.put("purchaseRequestId", null);
            pb.put("exchangeRateId", null);
            pb.put("currencyCenterId", invoice.getCurrencyId());
            pb.put("vatChartAccountId", invoice.getVatChartAccountId());
            pb.put("vatCalculate", null);
            pb.put("pvRequestId", null);
            pb.put("companyId", invoice.getCompanyId());
            pb.put("vendorId", 1);
            pb.put("locationGroupId", invoice.getOrganizationId());
            pb.put("paymentTermId", invoice.getPaymentTermId());
            pb.put("locationId", null);
            pb.put("invoiceCode", invoiceCodePb);
            pb.put("note", invoice.getNote());
            pb.put("poCode", pbCode);
            pb.put("apId", invoice.getChartAccountId());
            pb.put("shipmentId", null);
            pb.put("orderDate", (deliveryDate != null && !deliveryDate.trim().isEmpty()) ? deliveryDate : LocalDate.now().toString());
            pb.put("invoiceDate", invoice.getInvoiceDate());
            pb.put("totalAmount", invoice.getSubTotal());
            pb.put("discountPercent", invoice.getDiscountPercent());
            pb.put("discountAmount", invoice.getDiscountAmount());
            pb.put("vatSettingId", invoice.getVatId());
            pb.put("totalDeposit", invoice.getTotalDeposit());
            pb.put("balance", invoice.getBalance());
            pb.put("totalVat", invoice.getTotalVat());
            pb.put("vatPercent", invoice.getVatPercent());
            pb.put("status", 1);
            pb.put("createdBy", userId);

            freedomMapper.insertPurchaseBill(pb);
            Long purchaseBillId = pb.get("id") instanceof Number ? ((Number) pb.get("id")).longValue() : null;
            if (purchaseBillId == null) {
                purchaseBillId = invoice.getId();
            }

            Long glId = insertFreedomGeneralLedger(pbCode, invoice.getInvoiceDate(), purchaseBillId, null, invoice.getCompanyId(), userId);
            Long classId = helperMapper.getClassId(invoice.getCompanyId(), invoice.getWarehouseId());
            Double totalVat = invoice.getTotalVat() != null ? invoice.getTotalVat() : 0D;
            Double discountAmount = invoice.getDiscountAmount() != null ? invoice.getDiscountAmount() : 0D;
            Double subTotal = invoice.getSubTotal() != null ? invoice.getSubTotal() : 0D;
            Double totalAmountPb = subTotal + totalVat - discountAmount;

            insertFreedomGLDetail(glId, invoice.getChartAccountId(), invoice.getCompanyId(), invoice.getWarehouseId(), "Purchase Bill",
                    0D, totalAmountPb, "Freedom PB " + pbCode, invoice.getCompanyId(), null, classId, null, null, null, null, userId);

            if (totalVat > 0) {
                Long vatChart = helperMapper.getVatChartAccountId(invoice.getVatId());
                insertFreedomGLDetail(glId, vatChart, invoice.getCompanyId(), invoice.getWarehouseId(), "VAT",
                        totalVat, 0D, "Freedom PB VAT " + pbCode, invoice.getCompanyId(), null, classId, null, null, null, null, userId);
            }

            List<SaleInvoiceDetailResponse> details = saleInvoiceMapper.getListDetail(salesInvoiceId);
            if (details != null) {
                for (SaleInvoiceDetailResponse detail : details) {
                    if (detail.getType() == null) {
                        continue;
                    }

                    int type = detail.getType().intValue();
                    Long qty = detail.getQty() != null ? detail.getQty() : 0L;
                    Long qtyFree = detail.getQtyFree() != null ? detail.getQtyFree() : 0L;
                    Double conversion = detail.getConversion() != null && detail.getConversion() != 0 ? detail.getConversion() : 1D;
                    Double unitPrice = detail.getUnitPrice() != null ? detail.getUnitPrice() : 0D;
                    Double lineTotal = detail.getTotalPrice() != null ? detail.getTotalPrice() : 0D;
                    Double lineDiscount = detail.getDiscountAmount() != null ? detail.getDiscountAmount() : 0D;

                    if (type == 1) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("purchaseOrderId", purchaseBillId);
                        map.put("productId", detail.getItemId());
                        map.put("note", detail.getNote());
                        map.put("expiredDate", expiredDateByInvoiceDetailId.get(detail.getId()));
                        map.put("qty", qty);
                        map.put("qtyFree", qtyFree);
                        map.put("uomId", detail.getUomId());
                        map.put("conversion", conversion);
                        map.put("unitCost", unitPrice);
                        map.put("totalCost", lineTotal);
                        map.put("discountId", detail.getDiscountId());
                        map.put("discountAmount", lineDiscount);
                        map.put("discountPercent", detail.getDiscountPercent());
                        freedomMapper.insertPurchaseBillDetail(map);

                        double qtyTotal = qty + qtyFree;
                        double smallQty = qtyTotal * conversion;
                        Long ivId = insertFreedomInventoryValuation(purchaseBillId, null, detail.getItemId(), invoice.getInvoiceDate(), invoice.getCompanyId(), smallQty, qtyTotal, unitPrice, pbCode);

                        Long invCoa = helperMapper.getProductInventoryChartAccountId(detail.getItemId());
                        insertFreedomGLDetail(glId, invCoa, invoice.getCompanyId(), invoice.getWarehouseId(), "Inventory",
                                lineTotal, 0D, "Freedom PB " + pbCode + " " + detail.getItemId(), invoice.getCompanyId(), null, classId, detail.getItemId(), null, ivId, 1, userId);

                        if (lineDiscount > 0) {
                            Long discountCoa = helperMapper.getDiscountChartAccountId(11L);
                            insertFreedomGLDetail(glId, discountCoa, invoice.getCompanyId(), invoice.getWarehouseId(), "Discount",
                                    0D, lineDiscount, "Freedom PB Discount " + pbCode + " " + detail.getItemId(), invoice.getCompanyId(), null, classId, detail.getItemId(), null, null, null, userId);
                        }
                    } else if (type == 2) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("purchaseOrderId", purchaseBillId);
                        map.put("serviceId", detail.getItemId());
                        map.put("note", detail.getNote());
                        map.put("qty", qty);
                        map.put("qtyFree", qtyFree);
                        map.put("unitCost", unitPrice);
                        map.put("totalCost", lineTotal);
                        map.put("discountId", detail.getDiscountId());
                        map.put("discountAmount", lineDiscount);
                        map.put("discountPercent", detail.getDiscountPercent());
                        freedomMapper.insertPurchaseBillService(map);

                        Long serviceCoa = helperMapper.getServiceChartAccountId(detail.getItemId());
                        insertFreedomGLDetail(glId, serviceCoa, invoice.getCompanyId(), invoice.getWarehouseId(), "Service",
                                lineTotal, 0D, "Freedom PB " + pbCode + " " + detail.getItemId(), invoice.getCompanyId(), null, classId, null, detail.getItemId(), null, null, userId);
                        if (lineDiscount > 0) {
                            Long discountCoa = helperMapper.getDiscountChartAccountId(11L);
                            insertFreedomGLDetail(glId, discountCoa, invoice.getCompanyId(), invoice.getWarehouseId(), "Discount",
                                    0D, lineDiscount, "Freedom PB Discount " + pbCode + " " + detail.getItemId(), invoice.getCompanyId(), null, classId, null, detail.getItemId(), null, null, userId);
                        }
                    } else if (type == 3) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("purchaseOrderId", purchaseBillId);
                        map.put("description", detail.getItemName());
                        map.put("qty", qty);
                        map.put("uomId", detail.getUomId());
                        map.put("qtyFree", qtyFree);
                        map.put("unitCost", unitPrice);
                        map.put("totalCost", lineTotal);
                        map.put("discountId", detail.getDiscountId());
                        map.put("discountPercent", detail.getDiscountPercent());
                        map.put("discountAmount", lineDiscount);
                        freedomMapper.insertPurchaseBillMisc(map);

                        Long miscCoa = detail.getDepositTo() != null ? detail.getDepositTo() : helperMapper.getMiscChartAccountId();
                        insertFreedomGLDetail(glId, miscCoa, invoice.getCompanyId(), invoice.getWarehouseId(), "Misc",
                                lineTotal, 0D, "Freedom PB " + pbCode + " " + detail.getItemName(), invoice.getCompanyId(), null, classId, null, null, null, null, userId);
                        if (lineDiscount > 0) {
                            Long discountCoa = helperMapper.getDiscountChartAccountId(11L);
                            insertFreedomGLDetail(glId, discountCoa, invoice.getCompanyId(), invoice.getWarehouseId(), "Discount",
                                    0D, lineDiscount, "Freedom PB Discount " + pbCode + " " + detail.getItemName(), invoice.getCompanyId(), null, classId, null, null, null, null, userId);
                        }
                    }
                }
            }
        } catch (Exception exception) {
            System.out.println("Skip Freedom purchase bill sync from delivery: " + exception.getMessage());
        }
    }

    private Long insertFreedomGeneralLedger(String reference, String date, Long purchaseOrderId, Long purchaseReturnId, Long companyId, Long userId) {
        Map<String, Object> gl = new HashMap<>();
        gl.put("reference", reference);
        gl.put("date", date);
        gl.put("purchaseOrderId", purchaseOrderId);
        gl.put("purchaseReturnId", purchaseReturnId);
        gl.put("createdBy", userId);
        freedomMapper.insertGeneralLedger(gl);
        Object idObj = gl.get("id");
        return idObj instanceof Number ? ((Number) idObj).longValue() : null;
    }

    private void insertFreedomGLDetail(Long glId, Long chartAccountId, Long companyId, Long locationId, String type, Double debit, Double credit, String memo, Long vendorId, Long customerId, Long classId, Long productId, Long serviceId, Long inventoryValuationId, Integer inventoryValuationIsDebit, Long userId) {
        Map<String, Object> gld = new HashMap<>();
        gld.put("generalLedgerId", glId);
        gld.put("chartAccountId", chartAccountId);
        gld.put("companyId", companyId);
        gld.put("locationId", locationId);
        gld.put("type", type);
        gld.put("debit", debit != null ? debit : 0D);
        gld.put("credit", credit != null ? credit : 0D);
        gld.put("memo", memo);
        gld.put("vendorId", vendorId);
        gld.put("customerId", customerId);
        gld.put("classId", classId);
        gld.put("productId", productId);
        gld.put("serviceId", serviceId);
        gld.put("inventoryValuationId", inventoryValuationId);
        gld.put("inventoryValuationIsDebit", inventoryValuationIsDebit);
        gld.put("createdBy", userId);
        freedomMapper.insertGeneralLedgerDetail(gld);
    }

    private Long insertFreedomInventoryValuation(Long purchaseOrderId, Long purchaseReturnId, Long productId, String date, Long companyId, Double smallQty, Double qty, Double cost, String reference) {
        Map<String, Object> iv = new HashMap<>();
        iv.put("purchaseOrderId", purchaseOrderId);
        iv.put("purchaseReturnId", purchaseReturnId);
        iv.put("date", date);
        iv.put("type", purchaseOrderId != null ? "Purchase Bill" : "Bill Return");
        iv.put("companyId", companyId);
        iv.put("productId", productId);
        iv.put("smallQty", smallQty);
        iv.put("qty", qty);
        iv.put("reference", reference);
        iv.put("cost", cost);
        freedomMapper.insertInventoryValuation(iv);
        Object idObj = iv.get("id");
        return idObj instanceof Number ? ((Number) idObj).longValue() : null;
    }
}
