package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.helper.TelegramUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.PurchaseBillMapper;
import com.ut.nlSystemAPi.mapper.primary.PurchaseOrderMapper;
import com.ut.nlSystemAPi.mapper.primary.PurchaseReceiveMapper;
import com.ut.nlSystemAPi.mapper.primary.TransferConsignmentMapper;
import com.ut.nlSystemAPi.model.GlobalStock;
import com.ut.nlSystemAPi.model.InventoryValuation;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.PurchaseReceive;
import com.ut.nlSystemAPi.model.Telegram.TelegramResponse;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.PurchaseReceiveFilter;
import com.ut.nlSystemAPi.model.request.Login.PurchaseReceive.PurchaseReceiveSaveRequest;
import com.ut.nlSystemAPi.model.response.PurchaseReceive.PurchaseReceiveDetailResponse;
import com.ut.nlSystemAPi.model.response.PurchaseReceive.PurchaseReceiveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseReceiveServiceImpl implements PurchaseReceiveService {
    @Autowired
    private PurchaseReceiveMapper purchaseReceiveMapper;

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private PurchaseBillMapper purchaseBillMapper;

    @Autowired
    private TransferConsignmentMapper transferConsignmentMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    Environment environment;

    @Autowired
    private GenerateCode generateCode;

    @Override
    public ResponseMessage<BaseResult> getList(PurchaseReceiveFilter purchaseReceiveFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Purchase Receive (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            purchaseReceiveFilter.setUserId(userId);

            Pagination pagination = new Pagination();
            pagination.setPage(purchaseReceiveFilter.getPage());
            pagination.setRowsPerPage(purchaseReceiveFilter.getRowsPerPage());
            pagination.setTotal(purchaseReceiveMapper.countList(purchaseReceiveFilter));
            purchaseReceiveFilter.setPage((purchaseReceiveFilter.getPage() - 1) * purchaseReceiveFilter.getRowsPerPage());


            List<PurchaseReceiveResponse> purchaseReceiveResponses = purchaseReceiveMapper.getList(purchaseReceiveFilter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-receive/list", null, null, "Purchase Receive", "Purchase Receive (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", purchaseReceiveResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-receive/list", line, error.toString(), "Purchase Receive", "Purchase Receive(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Purchase Receive (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            List<PurchaseReceiveResponse> purchaseReceiveResponses = purchaseReceiveMapper.getOne(id, userId);
            if(!purchaseReceiveResponses.isEmpty()){
                for(int i = 0; i < purchaseReceiveResponses.size(); i++){
                    List<PurchaseReceiveDetailResponse> detail = purchaseReceiveMapper.getPurchaseReceiveDetail(purchaseReceiveResponses.get(i).getPurchaseOrderId(), purchaseReceiveResponses.get(i).getId());
                    purchaseReceiveResponses.get(i).setPurchaseReceiveDetailResponse(detail);
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-receive/find/{id}", null, null, "Purchase Receive", "Purchase Receive (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", purchaseReceiveResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-receive/find/{id}", line, error.toString(), "Purchase Receive", "Purchase Receive (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOneReceive(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Purchase Receive (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<PurchaseReceiveResponse> purchaseReceiveResponses = purchaseReceiveMapper.getOneReceive(id, userId);
                if(purchaseReceiveResponses.size() > 0){
                       for(int i = 0; i < purchaseReceiveResponses.size(); i++){
                           {
                               //! Get the reference code
                               String yearPrefix = String.valueOf(Year.now().getValue() % 100);
                               String code = (yearPrefix + "GRR");

                               purchaseReceiveResponses.get(i).setPurchaseReceiveCode(code);
                           }

                           List<PurchaseReceiveDetailResponse> purchaseOrderAlreadyReceive = purchaseReceiveMapper.getPurchaseReceiveDetailReceive(purchaseReceiveResponses.get(i).getPurchaseOrderId());

                           List<PurchaseReceiveDetailResponse> purchaseNotYetReceived = purchaseReceiveMapper.getPurchaseReceiveDetailNotYetReceived(purchaseReceiveResponses.get(i).getPurchaseOrderId());

                           List<PurchaseReceiveDetailResponse> purchaseOrderRemain = purchaseReceiveMapper.getPurchaseReceiveDetailRemain(purchaseReceiveResponses.get(i).getPurchaseOrderId());

                           List<PurchaseReceiveDetailResponse> combinedList = new ArrayList<>();
                           combinedList.addAll(purchaseOrderAlreadyReceive);
                           combinedList.addAll(purchaseOrderRemain);
                           combinedList.addAll(purchaseNotYetReceived);

                           purchaseReceiveResponses.get(i).setPurchaseReceiveDetailResponse(combinedList);
                       }

                }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-receive/find/{id}", null, null, "System purchase-receive", "System purchase-receive (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", purchaseReceiveResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-receive/find/{id}", line, error.toString(), "System purchase-receive", "System purchase-receive (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> save(PurchaseReceiveSaveRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Purchase Receive (Receive)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            boolean result = false;
            PurchaseReceive purchaseReceive = new PurchaseReceive();
            String code = null;

            if (!request.getDetails().isEmpty()) {
                //! Get the reference code
                code = (generateCode.generateAutoCode("purchase_receive_results", "code", 7, "GRR", true, ""));
                purchaseReceiveMapper.insertPurchaseReceiveResults(purchaseReceive, code, request.getDateReceiveApplyAll(), request.getPurchaseOrderId(), userId);
            }

            List<PurchaseReceiveResponse> responses = purchaseReceiveMapper.getOneReceive(request.getPurchaseOrderId(), userId);
            StringBuilder productInfo = new StringBuilder();
            Long calculateCog = purchaseBillMapper.getCalculateCog();

            if (!responses.isEmpty()) {
                List<PurchaseReceiveDetailResponse> detail = purchaseReceiveMapper.getPurchaseOrderDetail(responses.get(0).getPurchaseOrderId());

                if (!detail.isEmpty() && !request.getDetails().isEmpty()) {
                    for (int i = 0; i < detail.size(); i++) {
                        for (int j = 0; j < request.getDetails().size(); j++) {
                            // Calculate Qty Receive
                            Long qtyReceiveBefore = purchaseReceiveMapper.getTotalQtyReceive(detail.get(i).getProductId(), request.getPurchaseOrderId());
                            if (qtyReceiveBefore == null) {
                                qtyReceiveBefore = 0L;
                            }

                            if (request.getDetails().get(j).getPurchaseOrderDetailId().equals(detail.get(i).getPurchaseOrderDetailId()) && request.getDetails().get(j).getQtyReceive() > 0) {
                                Long smallValUom = purchaseOrderMapper.getSmallValUom(detail.get(i).getProductId());
                                purchaseReceive.setPurchaseReceiveResultId(purchaseReceive.getId());
                                purchaseReceive.setLocationGroupId(responses.get(0).getWarehouseId());
                                purchaseReceive.setPurchaseOrderId(request.getPurchaseOrderId());
                                purchaseReceive.setPurchaseOrderDetailId(request.getDetails().get(j).getPurchaseOrderDetailId());
                                purchaseReceive.setProductId(detail.get(i).getProductId());
                                purchaseReceive.setQty(request.getDetails().get(j).getQtyReceive());
                                purchaseReceive.setQtyUomId(detail.get(i).getUomId());
                                purchaseReceive.setConversion(detail.get(i).getConversion());
                                purchaseReceive.setReceivedDate(request.getDetails().get(j).getDateReceive());
                                if (request.getDetails().get(j).getExpiredDate() == null) {
                                    purchaseReceive.setDateExpired("0000-00-00");
                                } else {
                                    purchaseReceive.setDateExpired(request.getDetails().get(j).getExpiredDate());
                                }
                                purchaseReceive.setCreatedBy(userId);
                                purchaseReceiveMapper.insertPurchaseReceive(purchaseReceive);

                                purchaseReceiveMapper.updateProduct(detail.get(i).getProductId(), request.getDetails().get(j).getNewCost());

                                productInfo.append("• ")
                                        .append(detail.get(i).getCode())
                                        .append(" - ")
                                        .append(detail.get(i).getProductName())
                                        .append(", Qty: ")
                                        .append(request.getDetails().get(j).getQtyReceive())
                                        .append("\n");

                                GlobalStock globalStock = new GlobalStock();
                                globalStock.setPurchaseOrderId(detail.get(i).getPurchaseOrderId());
                                globalStock.setProductId(detail.get(i).getProductId());
                                globalStock.setVendorId(responses.get(0).getVendorId());
                                globalStock.setLocationId(responses.get(0).getLocationId());
                                globalStock.setWarehouseId(responses.get(0).getWarehouseId());
                                globalStock.setLotsNumber(detail.get(i).getLotNumber());
                                if (request.getDetails().get(j).getExpiredDate() == null) {
                                    globalStock.setExpiredDate("0000-00-00");
                                } else {
                                    globalStock.setExpiredDate(request.getDetails().get(j).getExpiredDate());
                                }
                                globalStock.setConversion(detail.get(i).getConversion());
                                globalStock.setTotalQty(request.getDetails().get(j).getQtyReceive() * detail.get(i).getConversion());
                                globalStock.setTotalPb(request.getDetails().get(j).getQtyReceive() * detail.get(i).getConversion());
                                globalStock.setType("Purchase Receive");
                                globalStock.setCreatedBy(userId);
                                globalStock.setUnitCost(detail.get(i).getUnitCost());
                                insertStock(globalStock, 1L);

                                if (calculateCog != null && calculateCog == 1) {
                                    smallValUom = purchaseOrderMapper.getSmallValUom(detail.get(i).getProductId());
                                    Long conversion = detail.get(i).getConversion() == null || detail.get(i).getConversion() == 0 ? 1L : detail.get(i).getConversion();
                                    Double receiveCost = request.getDetails().get(j).getNewCost() != null ? request.getDetails().get(j).getNewCost() : request.getDetails().get(j).getUnitCost();
                                    String receiveDate = request.getDetails().get(j).getDateReceive() != null ? request.getDetails().get(j).getDateReceive() : request.getDateReceiveApplyAll();
                                    String referenceCode = code != null ? code : responses.get(0).getPurchaseBillNo();

                                    double smallQty = request.getDetails().get(j).getQtyReceive() * conversion;
                                    double baseQtyDenominator = (double) (smallValUom == null || smallValUom == 0 ? 1L : smallValUom) / conversion;
                                    double qtyBase = baseQtyDenominator != 0 ? request.getDetails().get(j).getQtyReceive() / baseQtyDenominator : 0D;

                                    InventoryValuation inventoryValuation = new InventoryValuation();
                                    inventoryValuation.setPurchaseBillId(request.getPurchaseOrderId());
                                    inventoryValuation.setPurchaseBillDetailId(request.getDetails().get(j).getPurchaseOrderDetailId());
                                    inventoryValuation.setCompanyId(responses.get(0).getCompanyId());
                                    inventoryValuation.setType("Bill");
                                    inventoryValuation.setReference(referenceCode);
                                    inventoryValuation.setDate(receiveDate);
                                    inventoryValuation.setPid(detail.get(i).getProductId());
                                    inventoryValuation.setSmallQty(smallQty);
                                    inventoryValuation.setQty(qtyBase);
                                    inventoryValuation.setCost(receiveCost);

                                    purchaseBillMapper.insertInventoryValuation(inventoryValuation);
                                }
                            }
                        }
                    }

                    // Calculate status after all inserts
                    Long totalQtyReceive = 0L;
                    Long totalQtyOrder = 0L;
                    for (int i = 0; i < detail.size(); i++) {
                        Long qtyReceive = purchaseReceiveMapper.getTotalQtyReceive(detail.get(i).getProductId(), request.getPurchaseOrderId());
                        Long qtyOrder = purchaseReceiveMapper.getTotalQtyOrder(detail.get(i).getProductId(), request.getPurchaseOrderId());

                        totalQtyReceive += (qtyReceive != null ? qtyReceive : 0L);
                        totalQtyOrder += (qtyOrder != null ? qtyOrder : 0L);
                    }

                    Long status;
                    if (totalQtyReceive == 0L) {
                        status = 1L; // Not received
                    } else if (totalQtyReceive < totalQtyOrder) {
                        status = 2L; // Partially received
                    } else {
                        status = 3L; // Fully received (or over-received)
                    }
                    purchaseReceiveMapper.updateStatus(status, request.getPurchaseOrderId());

                    result = true;
                }
            }

            if (result) {
                String erApprovalMessage = "💵<b><u>Purchase Received</u></b>" +
                        "\n\n<b>Date: " + responses.get(0).getPurchaseReceiveDate() + "</b>" +
                        "\n<b>PB No: " + responses.get(0).getPurchaseBillNo() + "</b>" +
                        "\n\n<i>WH: " + responses.get(0).getWarehouseName() + "</i>" +
                        "\n\n" + productInfo.toString().trim() +
                        "\n\n<pre>If you have any questions concerning this approval contact: " +
                        purchaseReceiveMapper.getCreatedBy(userId) + "</pre>";

                Long messageId = pushTelegram(
                        erApprovalMessage,
                        environment.getProperty("telegram.chatId.purchase-receive"),
                        environment.getProperty("telegram.botToken.purchase-receive"),
                        purchaseReceiveMapper.getMessageId(request.getPurchaseOrderId())
                );
                purchaseReceiveMapper.updateMessageId(messageId, purchaseReceive.getId());
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/purchase-receive/save", null, null, "Purchase Receive", "Purchase Receive (Receive)", "Receive", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/purchase-receive/save", line, error.toString(), "Purchase Receive", "Purchase Receive (Receive)", "Receive", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public static String generateReference(String input) {
        LocalDate currentDate = LocalDate.now();
        String yearLastTwoDigits = String.valueOf(currentDate.getYear()).substring(2);
        String result = yearLastTwoDigits + input + "0000001";
        return result;
    }

    //! Push Telegram
    public Long pushTelegram(String sendMessage, String chatId, String apiToken, Long replyToMessageId) {
        return TelegramUtils.sendHtmlMessage(sendMessage, chatId, apiToken, replyToMessageId);
    }

    private void insertStock(GlobalStock globalStock, Long typeOperation) {
        String fieldToUpdate = "total_pb";

        //! Insert Inventory Totals (All)
        transferConsignmentMapper.insertInventoryTotalAll(globalStock, typeOperation, fieldToUpdate);

        //! Insert Inventories (All)
        transferConsignmentMapper.insertInventoriesAll(globalStock, typeOperation);

        //! Insert Group Total By Warehouse
        String tblGroupTotal = globalStock.getWarehouseId() + "_group_totals";
        transferConsignmentMapper.insertGroupTotal(globalStock, tblGroupTotal, typeOperation);

        //!Insert Group Total Details By Warehouse
        String tblGroupTotalDetail = globalStock.getWarehouseId() + "_group_total_details";
        transferConsignmentMapper.insertGroupTotalDetail(globalStock, tblGroupTotalDetail, typeOperation, fieldToUpdate);

        //! Insert Inventory Totals By Location
        String tblInventoryTotal = globalStock.getLocationId() + "_inventory_totals";
        transferConsignmentMapper.insertInventoryTotal(globalStock, tblInventoryTotal, typeOperation);

        //! Insert Inventory Total Details By Location
        String tblInventoryTotalDetail = globalStock.getLocationId() + "_inventory_total_details";
        transferConsignmentMapper.insertInventoryTotalDetail(globalStock, tblInventoryTotalDetail, typeOperation, fieldToUpdate);

        //! Insert Inventories By Location
        String tblInventories = globalStock.getLocationId() + "_inventories";
        transferConsignmentMapper.insertInventories(globalStock, tblInventories, typeOperation);

        System.out.println("Done");

    }

}
