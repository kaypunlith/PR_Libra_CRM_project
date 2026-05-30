package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.helper.TelegramUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.Telegram.TelegramResponse;
import com.ut.nlSystemAPi.model.base.*;
import com.ut.nlSystemAPi.model.filter.PriceRequestFilter;
import com.ut.nlSystemAPi.model.request.Login.PriceRequest.*;
import com.ut.nlSystemAPi.model.request.Login.Product.ProductICSRequest;
import com.ut.nlSystemAPi.model.request.Login.Product.ProductPacketRequest;
import com.ut.nlSystemAPi.model.request.Login.Product.ProductRequest;
import com.ut.nlSystemAPi.model.request.Login.Product.ProductSkuRequest;
import com.ut.nlSystemAPi.model.response.PriceRequest.PriceRequestApproveResponse;
import com.ut.nlSystemAPi.model.response.PriceRequest.PriceRequestResponse;
import com.ut.nlSystemAPi.model.response.PriceRequest.PriceRequestResponseDetail;
import com.ut.nlSystemAPi.model.response.PriceRequest.PriceRequestResponseSummary;
import com.ut.nlSystemAPi.model.response.Product.ProductGroupPriceSettingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
public class PriceRequestServiceImpl implements PriceRequestService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PriceRequestMapper priceRequestMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ModuleTypeMapper moduleTypeMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TelegramMessageService telegramMessageService;

    @Autowired
    Environment environment;
    @Autowired
    private GenerateCode generateCode;
    @Autowired
    private CodeMapper codeMapper;

    public ResponseMessage<BaseResult> getList(PriceRequestFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Price Request (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            if (permissionMapper.checkPermission(userId, "Price Request (View By User)") > 0) {
                filter.setViewByUser(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());

            ResponseMessage<BaseResult> response;
            if (filter.getViewBy() != null && !filter.getViewBy().toString().isEmpty()) {
                if (filter.getViewBy() == 1) {
                    pagination.setTotal(priceRequestMapper.countList(filter, userId));
                    filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
                    List<PriceRequestResponse> standardList = priceRequestMapper.getList(filter, userId);
                    if (standardList != null && !standardList.isEmpty()) {
                        for (int i = 0; i < standardList.size(); i++) {
                            standardList.get(i).setQuotations(priceRequestMapper.getListQuotation(standardList.get(i).getProductId()));
                            standardList.get(i).setSaleOrders(priceRequestMapper.getListSaleOrder(standardList.get(i).getProductId()));
                        }
                    }
                    response = ResponseMessageUtils.makeResponse(true, messageService.message("Success", standardList, pagination, true));
                } else if (filter.getViewBy() == 2) {
                    pagination.setTotal(priceRequestMapper.countListSummary(filter));
                    filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
                    List<PriceRequestResponseSummary> summaryList = priceRequestMapper.getListSummary(filter);
                    response = ResponseMessageUtils.makeResponse(true, messageService.message("Success", summaryList, pagination, true));
                } else {
                    response = ResponseMessageUtils.makeResponse(false, messageService.message("Invalid", null, false));
                }
            } else {
                pagination.setTotal(priceRequestMapper.countList(filter, userId));
                filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
                List<PriceRequestResponse> standardList = priceRequestMapper.getList(filter, userId);
                response = ResponseMessageUtils.makeResponse(true, messageService.message("Success", standardList, pagination, true));
            }

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/list", null, null, "Price Request", "Price Request (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return response;
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/list", line, error.toString(), "Price Request", "Price Request (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Price Request (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            List<PriceRequestResponseDetail> priceRequestResponseDetails = priceRequestMapper.getOne(id);

            if (priceRequestResponseDetails != null && !priceRequestResponseDetails.isEmpty()) {
                for (int i = 0; i < priceRequestResponseDetails.size(); i++) {
                    priceRequestResponseDetails.get(i).setQuotations(priceRequestMapper.getListQuotation(priceRequestResponseDetails.get(i).getProductId()));
                    priceRequestResponseDetails.get(i).setSaleOrders(priceRequestMapper.getListSaleOrder(priceRequestResponseDetails.get(i).getProductId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/find/{id}",null,null,"Price Request","Price Request (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", priceRequestResponseDetails, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/find/{id}",line, error.toString(),"Price Request","Price Request (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }



    public ResponseMessage<BaseResult> print(Long customerId,HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Price Request (Print)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            List<PriceRequestResponseDetail> priceRequestResponseDetails = priceRequestMapper.print(customerId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/print/{customerId}",null,null,"Price Request","Price Request (Print)","Print",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", priceRequestResponseDetails, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/print/{customerId}",line, error.toString(),"Price Request","Price Request (Print)","Print",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    public ResponseMessage<BaseResult> insert(PriceRequest_Request priceRequest_request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Price Request (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Data
            PriceRequest priceRequest = new PriceRequest();
            priceRequest.setCompanyId(priceRequest_request.getCompanyId());
            priceRequest.setName(priceRequest_request.getName());
            priceRequest.setOrganizationName(priceRequest_request.getOrganizationName());
            priceRequest.setOrganizationId(priceRequest_request.getOrganizationId());
            priceRequest.setBrand(priceRequest_request.getBrand());
            priceRequest.setRequestDate(priceRequest_request.getRequestDate());
            priceRequest.setRequestById(priceRequest_request.getRequestById());
            priceRequest.setCustomerContactName(priceRequest_request.getCustomerContactName());
            priceRequest.setQty(priceRequest_request.getQty());
            priceRequest.setDateExpected(priceRequest_request.getDateExpected());
            priceRequest.setModelName(priceRequest_request.getModelName());
            priceRequest.setPercentageId(priceRequest_request.getPercentageId());
            priceRequest.setPriorityId(priceRequest_request.getPriorityId());
            priceRequest.setMisc(priceRequest_request.getMisc());
            priceRequest.setSpec(priceRequest_request.getSpec());
            priceRequest.setNote(priceRequest_request.getNote());
            priceRequest.setPrice(priceRequest_request.getPrice());
            priceRequest.setTermOfPayment(priceRequest_request.getTermOfPayment());
            priceRequest.setUrl(priceRequest_request.getUrl());
            priceRequest.setShipFromId(priceRequest_request.getShipFromId());
            priceRequest.setMadeInCountryId(priceRequest_request.getMadeInCountryId());
            priceRequest.setCurrencyCenterId(priceRequest_request.getCurrencyCenterId());
            priceRequest.setVendorName(priceRequest_request.getVendorName());
            priceRequest.setPrStatusId(priceRequest_request.getPrStatusId());
            priceRequest.setUomId(priceRequest_request.getUomId());
            priceRequest.setLeadTimeId(priceRequest_request.getLeadTimeId());
            if (priceRequest_request.getFilePdf() != null) {
                priceRequest.setFilePdf(priceRequest_request.getFilePdf().getUrl());
                priceRequest.setFilePdfName(priceRequest_request.getFilePdf().getName());
            }
            if (priceRequest_request.getFilePhoto() != null) {
                priceRequest.setFilePhoto(priceRequest_request.getFilePhoto().getUrl());
                priceRequest.setFilePhotoName(priceRequest_request.getFilePhoto().getName());
            }
            if (priceRequest_request.getPhoto1() != null) {
                priceRequest.setPhoto1(priceRequest_request.getPhoto1().getUrl());
                priceRequest.setPhoto1Name(priceRequest_request.getPhoto1().getName());
            }
            if (priceRequest_request.getPhoto2() != null) {
                priceRequest.setPhoto2(priceRequest_request.getPhoto2().getUrl());
                priceRequest.setPhoto2Name(priceRequest_request.getPhoto2().getName());
            }
            if (priceRequest_request.getPhoto3() != null) {
                priceRequest.setPhoto3(priceRequest_request.getPhoto3().getUrl());
                priceRequest.setPhoto3Name(priceRequest_request.getPhoto3().getName());
            }
            if (priceRequest_request.getPhoto4() != null) {
                priceRequest.setPhoto4(priceRequest_request.getPhoto4().getUrl());
                priceRequest.setPhoto4Name(priceRequest_request.getPhoto4().getName());
            }
            priceRequest.setCreatedBy(userId);

            Boolean result = priceRequestMapper.insert(priceRequest);

            if (result) {

                //! Get the reference code
                String code = (generateCode.generateAutoCode("price_requests", "request_code", 7, "", true, "status >= 0"));
                codeMapper.updateCode("price_requests", "request_code", code, priceRequest.getId());

                String createdBy = priceRequestMapper.getFullName(userId);

                telegramMessageService.sendPriceRequestNotification("Create",createdBy," Create ", priceRequest);

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/price-request/add",null,null,"Price Request","Price Request (Add)","Add",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/add",line, error.toString(),"Price Request","Price Request (Add)","Add",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    public ResponseMessage<BaseResult> update(PriceRequest_RequestUpdate priceRequest_requestUpdate, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Price Request (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            // Check Data
            PriceRequest priceRequest = new PriceRequest();
            priceRequest.setId(priceRequest_requestUpdate.getId());
            priceRequest.setCompanyId(priceRequest_requestUpdate.getCompanyId());
            priceRequest.setName(priceRequest_requestUpdate.getName());
            priceRequest.setOrganizationName(priceRequest_requestUpdate.getOrganizationName());
            priceRequest.setOrganizationId(priceRequest_requestUpdate.getOrganizationId());
            priceRequest.setBrand(priceRequest_requestUpdate.getBrand());
            priceRequest.setRequestDate(priceRequest_requestUpdate.getRequestDate());
            priceRequest.setRequestById(priceRequest_requestUpdate.getRequestById());
            priceRequest.setCustomerContactName(priceRequest_requestUpdate.getCustomerContactName());
            priceRequest.setQty(priceRequest_requestUpdate.getQty());
            priceRequest.setRequestCode(priceRequest_requestUpdate.getRequestCode());
            priceRequest.setDateExpected(priceRequest_requestUpdate.getDateExpected());
            priceRequest.setModelName(priceRequest_requestUpdate.getModelName());
            priceRequest.setPercentageId(priceRequest_requestUpdate.getPercentageId());
            priceRequest.setPriorityId(priceRequest_requestUpdate.getPriorityId());
            priceRequest.setMisc(priceRequest_requestUpdate.getMisc());
            priceRequest.setSpec(priceRequest_requestUpdate.getSpec());
            priceRequest.setNote(priceRequest_requestUpdate.getNote());
            priceRequest.setPrice(priceRequest_requestUpdate.getPrice());
            priceRequest.setTermOfPayment(priceRequest_requestUpdate.getTermOfPayment());
            priceRequest.setUrl(priceRequest_requestUpdate.getUrl());
            priceRequest.setShipFromId(priceRequest_requestUpdate.getShipFromId());
            priceRequest.setMadeInCountryId(priceRequest_requestUpdate.getMadeInCountryId());
            priceRequest.setCurrencyCenterId(priceRequest_requestUpdate.getCurrencyCenterId());
            priceRequest.setVendorName(priceRequest_requestUpdate.getVendorName());
            priceRequest.setPrStatusId(priceRequest_requestUpdate.getPrStatusId());
            priceRequest.setUomId(priceRequest_requestUpdate.getUomId());
            priceRequest.setLeadTimeId(priceRequest_requestUpdate.getLeadTimeId());
            if (priceRequest_requestUpdate.getFilePdf() != null) {
                priceRequest.setFilePdf(priceRequest_requestUpdate.getFilePdf().getUrl());
                priceRequest.setFilePdfName(priceRequest_requestUpdate.getFilePdf().getName());
            }
            if (priceRequest_requestUpdate.getFilePhoto() != null) {
                priceRequest.setFilePhoto(priceRequest_requestUpdate.getFilePhoto().getUrl());
                priceRequest.setFilePhotoName(priceRequest_requestUpdate.getFilePhoto().getName());
            }
            if (priceRequest_requestUpdate.getPhoto1() != null) {
                priceRequest.setPhoto1(priceRequest_requestUpdate.getPhoto1().getUrl());
                priceRequest.setPhoto1Name(priceRequest_requestUpdate.getPhoto1().getName());
            }
            if (priceRequest_requestUpdate.getPhoto2() != null) {
                priceRequest.setPhoto2(priceRequest_requestUpdate.getPhoto2().getUrl());
                priceRequest.setPhoto2Name(priceRequest_requestUpdate.getPhoto2().getName());
            }
            if (priceRequest_requestUpdate.getPhoto3() != null) {
                priceRequest.setPhoto3(priceRequest_requestUpdate.getPhoto3().getUrl());
                priceRequest.setPhoto3Name(priceRequest_requestUpdate.getPhoto3().getName());
            }
            if (priceRequest_requestUpdate.getPhoto4() != null) {
                priceRequest.setPhoto4(priceRequest_requestUpdate.getPhoto4().getUrl());
                priceRequest.setPhoto4Name(priceRequest_requestUpdate.getPhoto4().getName());
            }
            priceRequest.setModifiedBy(userId);

            //  Update Close
            if (priceRequest_requestUpdate.getStatus() != null) {
                PriceRequestStatusUpdate updateClose = new PriceRequestStatusUpdate();
                updateClose.setId(priceRequest_requestUpdate.getId());
                updateClose.setStatus(priceRequest_requestUpdate.getStatus());
                updateClose.setCloseBy(userId);
                updateClose.setCloseReasonId(priceRequest_requestUpdate.getCloseReasonId());
                priceRequestMapper.updateStatus(updateClose);
            }

            Boolean result = priceRequestMapper.update(priceRequest);


            String modifiedBy = priceRequestMapper.getFullName(userId);
            telegramMessageService.sendPriceRequestNotification("Update", modifiedBy, "Update", priceRequest);

            if (result) {

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/price-request/update",null,null,"Price Request","Price Request (Edit)","Edit",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/update",line, error.toString(),"Price Request","Price Request (Edit)","Edit",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Price Request (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            PriceRequest priceRequest = priceRequestMapper.getDataSendToTelegram(id);
            if (priceRequest == null) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Price Request not found.", false));
            }

            String deletedBy = priceRequestMapper.getFullName(userId);
            telegramMessageService.sendPriceRequestNotification("Delete", deletedBy, "Delete", priceRequest);

            // Proceed with the deletion
            Boolean result = priceRequestMapper.delete(id, userId);
            if (!result) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail to delete Price Request.", false));
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/delete/{id}", null, null, "Price Request", "Price Request (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/delete/{id}", line, error.toString(), "Price Request", "Price Request (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateStatus(PriceRequestStatusUpdate statusUpdate, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Price Request (Close)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            statusUpdate.setCloseBy(userId);

            PriceRequest priceRequest = priceRequestMapper.getDataSendToTelegram(statusUpdate.getId());
            if (priceRequest == null) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Price Request not found.", false));
            }

            String modifiedBy = priceRequestMapper.getFullName(userId);
            String statusAction = "";

            if (statusUpdate.getStatus() == 1) { // Open
                statusAction = "Open";
            } else if (statusUpdate.getStatus() == 2) { // Close
                statusAction = "Close";
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Unknown status change.", false));
            }


            telegramMessageService.sendPriceRequestNotification(statusAction, modifiedBy, statusAction, priceRequest);

            Boolean result = priceRequestMapper.updateStatus(statusUpdate);

            if (!result) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail to update Price Request status.", false));
            }

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/update-status", null, null, "Price Request", "Price Request (Close)", "Close", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/update-status", line, error.toString(), "Price Request", "Price Request (Close)", "Close", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> sendTelegram(SendToTelegram sendToTelegram, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Price Request (Send Mobile)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            PriceRequest priceRequest = priceRequestMapper.getDataSendToTelegram(sendToTelegram.getId());
            priceRequestMapper.insertPriceRequestMobile(priceRequest.getId(), userId);
            if (priceRequest == null) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Price Request not found.", false));
            }
            telegramMessageService.sendMessageToTelegram("បង្កើត", null, "",priceRequest );
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sent-to-telegram/find/{id}",null,null,"Sent To Telegram","Sent To Telegram (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/sent-to-telegram/find/{id}",line, error.toString(),"Send To Telegram","Sent To Telegram (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> convert(ProductRequest productRequest, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Price Request (Add)") == 0) {
               return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }


            Products product = new Products();
            product.setCompanyId(productRequest.getCompanyId());
            product.setParentId(productRequest.getParentId());
            if (productRequest.getPhoto() != null){
                product.setPhoto(productRequest.getPhoto().getUrl());
                product.setPhotoName(productRequest.getPhoto().getName());
            }
            product.setName(productRequest.getName());
            product.setProductGroupId(productRequest.getProductGroupId());
            product.setIsActive(productRequest.getIsActive());
            product.setColor(productRequest.getColor());
            product.setPeriodFrom(productRequest.getPeriodFrom());
            product.setPeriodTo(productRequest.getPeriodTo());
            product.setIsPacket(productRequest.getIsPacket());
            product.setBarcode(productRequest.getUpc());
            product.setUnitCost(productRequest.getUnitCost());
            product.setProductRecorderLevel(productRequest.getProductRecorderLevel());
            product.setIsExpiredDate(productRequest.getIsExpiredDate());
            product.setCode(productRequest.getSku());
            product.setPriceUomId(productRequest.getUomId());
            product.setSpec(productRequest.getSpec());
            if(productRequest.getFileCatalog() != null){
                product.setFileCatalog(productRequest.getFileCatalog().getUrl());
                product.setFileCatalogName(productRequest.getFileCatalog().getName());
            }
            product.setDescription(productRequest.getDescription());
            product.setWidth(productRequest.getWidth());
            product.setHeight(productRequest.getHeight());
            product.setLength(productRequest.getLength());
            product.setWeight(productRequest.getWeight());
            product.setCubicMeter(productRequest.getM3());
            product.setSizeUomId(productRequest.getSizeUomId());
            product.setWeightUomId(productRequest.getWeightUomId());
            product.setNote(productRequest.getVendorInfo());
            product.setCreatedBy(userId);
            product.setIsActive(3);
            Boolean result = productMapper.insert(product);
            if (result) {

                if (productRequest.getProductGroupId() != null) {
                    ProductPgroup productPgroup = new ProductPgroup();
                    productPgroup.setProductId(product.getId());
                    productPgroup.setPgroupId(productRequest.getProductGroupId());
                    productMapper.insertProductPgroup(productPgroup);

                    List<ProductGroupPriceSettingResponse> checkGroupPrice = productMapper.getProductGroupPrice(product.getUnitCost(), product.getProductGroupId());

                    if (!checkGroupPrice.isEmpty()) {
                        for (int i = 0; i < checkGroupPrice.size(); i++) {
                            ProductPrice productPrice = new ProductPrice();
                            productPrice.setProductId(product.getId());
                            productPrice.setPriceTypeId(checkGroupPrice.get(i).getPriceTypeId());
                            productPrice.setUomId(product.getPriceUomId());
                            productPrice.setAmount(checkGroupPrice.get(i).getAmount());
                            productPrice.setAmountBefore(checkGroupPrice.get(i).getAmountBefore());
                            productPrice.setPercentage(checkGroupPrice.get(i).getPercent());
                            productPrice.setAddOn(checkGroupPrice.get(i).getAddOn());
                            productPrice.setSetType(checkGroupPrice.get(i).getSetType());
                            productPrice.setCreatedBy(userId);
                            productMapper.addPrice(productPrice);

                            ProductPriceHistory productPriceHistory = new ProductPriceHistory();
                            productPriceHistory.setProductId(product.getId());
                            productPriceHistory.setPriceTypeId(checkGroupPrice.get(i).getPriceTypeId());
                            productPriceHistory.setUomId(product.getPriceUomId());
                            productPrice.setAmount(checkGroupPrice.get(i).getAmount());
                            productPrice.setAmountBefore(checkGroupPrice.get(i).getAmountBefore());
                            productPrice.setPercentage(checkGroupPrice.get(i).getPercent());
                            productPrice.setAddOn(checkGroupPrice.get(i).getAddOn());
                            productPrice.setSetType(checkGroupPrice.get(i).getSetType());
                            productPriceHistory.setCreatedBy(userId);
                            productMapper.addPriceHistory(productPriceHistory);
                        }
                    }
                }

                if (productRequest.getIcs() != null && !productRequest.getIcs().isEmpty()) {
                    List<ProductICSRequest> productIcsRequests = productRequest.getIcs();
                    for (int i = 0; i < productIcsRequests.size(); i++) {
                        ProductChartAccount productChartAccount = new ProductChartAccount();
                        productChartAccount.setProductId(product.getId());
                        productChartAccount.setAccountType(productRequest.getIcs().get(i).getAccountType());
                        productChartAccount.setChartAccountId(productRequest.getIcs().get(i).getChartAccountId());
                        productMapper.insertICSAccount(productChartAccount);
                    }
                }

                if (productRequest.getCategories() != null && !productRequest.getCategories().isEmpty()) {
                    List<Long> categories = productRequest.getCategories();
                    for (int i = 0; i < categories.size(); i++) {
                        ProductCategory productCategory = new ProductCategory();
                        productCategory.setProductId(product.getId());
                        productCategory.setCategoryId(categories.get(i));
                        productMapper.insertProductCategory(productCategory);
                    }
                }

                if (productRequest.getBatchCodeInformation() != null && !productRequest.getBatchCodeInformation().isEmpty()) {
                    List<ProductSkuRequest> productSkuRequests = productRequest.getBatchCodeInformation();
                    for (int i = 0; i < productSkuRequests.size(); i++) {
                        ProductSku productSku = new ProductSku();
                        productSku.setProductId(product.getId());
                        productSku.setCode(productRequest.getBatchCodeInformation().get(i).getCode());
                        productSku.setUomId(product.getPriceUomId());
                        productMapper.insertProductSku(productSku);
                    }
                }

                if (productRequest.getProductPackage() != null && !productRequest.getProductPackage().isEmpty()) {
                    List<ProductPacketRequest> productPacketRequests = productRequest.getProductPackage();
                    for (int i = 0; i < productPacketRequests.size(); i++) {
                        ProductPacket productPacket = new ProductPacket();
                        productPacket.setPacketId(productPacketRequests.get(i).getProductId());
                        productPacket.setProductId(product.getId());
                        productPacket.setQty(productRequest.getProductPackage().get(i).getQty());
                        productPacket.setQtyUomId(productRequest.getProductPackage().get(i).getUomId());
                        productPacket.setConversion(productRequest.getProductPackage().get(i).getConversion());
                        productMapper.insertProductPacket(productPacket);
                    }
                }

                priceRequestMapper.updateIsConvert(userId, product.getId(), productRequest.getPriceRequestId());


                List<PriceRequestResponseDetail> response = priceRequestMapper.getOne(productRequest.getPriceRequestId());
                String priceRequestPendingMessage = "📦 <b><u>Price Request Status</u></b>" +
                        "\n\n<b>Date: " + response.get(0).getRequestDate() + "</b>" +
                        "\n<b>Request No: " + response.get(0).getRequestCode() + "</b>" +
                        "\n\n<b>" + response.get(0).getOrganizationName() + "</b>" +
                        "\n\n<i>Attn: " + response.get(0).getCustomerContactName() + "</i>" +
                        "\n\nModel: " + response.get(0).getModelName() +
                        "\nBrand: " + response.get(0).getBrand() +
                        "\nQty: " + response.get(0).getQty() +
                        "\n\n<b>" + response.get(0).getPercentageName() + "</b>" +
                        "\n\n<b>" + response.get(0).getPriorityName() + "</b>" +
                        "\n\n<i>Date Expected: " + response.get(0).getDateExpected() + "</i>" +
                        "\n\n<b>បានបង្កើតជាផលិតផលរួចហើយ (Not Yet Approve)</b>" +
                        "\n\nMemo: " + response.get(0).getNote() +
                        "\n\n<i>Request Create By: " + response.get(0).getRequestByName() + "</i>" +
                        "\n\n<pre>If you have any questions concerning this status contact: " +
                        response.get(0).getRequestByName() + "</pre>";

                Long messageId = pushTelegram(
                        priceRequestPendingMessage,
                        environment.getProperty("telegram.chatId.price-request"),
                        environment.getProperty("telegram.botToken.price-request"),
                        priceRequestMapper.getMessageId(response.get(0).getId())
                );
                priceRequestMapper.updateMessageId(productRequest.getPriceRequestId(), messageId);
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/price-request/convert-to-product", null, null, "Price Request", "Price Request (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", null, false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/convert-to-product", line, error.toString(), "Price Request", "Price Request (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> sumListApprove(HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Price Request (Approved)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Long total  = priceRequestMapper.sumListApprove();

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/list", null, null, "Price Request", "Price Request (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", Collections.singletonList(total),  true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/list", line, error.toString(), "Price Request", "Price Request (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> listApprove(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Price Request (Approved)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(priceRequestMapper.countListApprove(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<PriceRequestApproveResponse> responses  = priceRequestMapper.getListApprove(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/list", null, null, "Price Request", "Price Request (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/list", line, error.toString(), "Price Request", "Price Request (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> approve(PriceRequestApproveRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (request.getStatus() == 1) {
                if (permissionMapper.checkPermission(userId, "Price Request (Approved)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            } else {
                if (permissionMapper.checkPermission(userId, "Price Request (Reject)") == 0) {
                    return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                }
            }


            Boolean result = priceRequestMapper.approve(request, userId);

            if(result) {
                if (request.getStatus() == 1) {
                    List<PriceRequestResponseDetail> response = priceRequestMapper.getOne(request.getId());
                    String priceRequestApprovalMessage = "📦 <b><u>Price Request Status</u></b>" +
                            "\n\n<b>Date: " + response.get(0).getRequestDate() + "</b> <i>(Modified)</i>" +
                            "\n<b>Request No: " + response.get(0).getRequestCode() + "</b>" +
                            "\n\n<b>" + response.get(0).getOrganizationName() + "</b>" +
                            "\n\n<i>Attn: " + response.get(0).getCustomerContactName() + "</i>" +
                            "\n\nModel: " + response.get(0).getModelName() +
                            "\nBrand: " + response.get(0).getBrand() +
                            "\nQty: " + response.get(0).getQty() +
                            "\n\n<b>បានបង្កើតជាផលិតផលរួចហើយ (Approve)</b>" +
                            "\n\nMemo: " + response.get(0).getNote() +
                            "\n\n<i>Approved By: " + response.get(0).getApprovedBy() + "</i>" +
                            "\n\n<pre>If you have any questions concerning this status contact: " +
                            response.get(0).getApprovedBy() + "</pre>";

                    Long messageId = pushTelegram(
                            priceRequestApprovalMessage,
                            environment.getProperty("telegram.chatId.price-request"),
                            environment.getProperty("telegram.botToken.price-request"),
                            priceRequestMapper.getMessageId(response.get(0).getId())
                    );
                    priceRequestMapper.updateMessageId(request.getId(), messageId);
                } else {
                    List<PriceRequestResponseDetail> response = priceRequestMapper.getOne(request.getId());
                    String priceRequestRejectionMessage = "📦 <b><u>Price Request Status</u></b>" +
                            "\n\n<b>Date: " + response.get(0).getRequestDate() + "</b> <i>(Modified)</i>" +
                            "\n<b>Request No: " + response.get(0).getRequestCode() + "</b>" +
                            "\n\n<b>" + response.get(0).getOrganizationName() + "</b>" +
                            "\n\n<i>Attn: " + response.get(0).getCustomerContactName() + "</i>" +
                            "\n\nModel: " + response.get(0).getModelName() +
                            "\nBrand: " + response.get(0).getBrand() +
                            "\nQty: " + response.get(0).getQty() +
                            "\n\n<b>Status: Reject</b>" +
                            "\n<b>Reason: " + request.getReason() + "</b>" +
                            "\n\n<i>Reject By: " + response.get(0).getRejectedBy() + "</i>" +
                            "\n\n<pre>If you have any questions concerning this status contact: " +
                            response.get(0).getRejectedBy() + "</pre>";

                    Long messageId = pushTelegram(
                            priceRequestRejectionMessage,
                            environment.getProperty("telegram.chatId.price-request"),
                            environment.getProperty("telegram.botToken.price-request"),
                            priceRequestMapper.getMessageId(response.get(0).getId())
                    );
                    priceRequestMapper.updateMessageId(request.getId(), messageId);
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/price-request/delete/{id}", null, null, "Price Request", "Price Request (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/price-request/delete/{id}", line, error.toString(), "Price Request", "Price Request (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    //! Push Telegram
    public Long pushTelegram(String sendMessage, String chatId, String apiToken, Long replyToMessageId) {
        return TelegramUtils.sendHtmlMessage(sendMessage, chatId, apiToken, replyToMessageId);
    }

}
