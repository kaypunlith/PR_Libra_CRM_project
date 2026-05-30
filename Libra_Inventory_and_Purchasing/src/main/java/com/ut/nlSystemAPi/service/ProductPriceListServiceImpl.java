package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.CodeMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ProductPriceListMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ProductPriceListFilter;
import com.ut.nlSystemAPi.model.filter.ProductPriceListProductDetailFilter;
import com.ut.nlSystemAPi.model.request.Login.ProductPriceList.ProductListApproveStatus;
import com.ut.nlSystemAPi.model.request.Login.ProductPriceList.ProductPriceListDetails;
import com.ut.nlSystemAPi.model.request.Login.ProductPriceList.ProductPriceListRequest;
import com.ut.nlSystemAPi.model.request.Login.ProductPriceList.ProductPriceListUpdateRequest;
import com.ut.nlSystemAPi.model.response.ProductPriceList.ProductPriceListDetailResponse;
import com.ut.nlSystemAPi.model.response.ProductPriceList.ProductPriceListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ProductPriceListServiceImpl implements ProductPriceListService {
    @Autowired
    private ProductPriceListMapper productPriceListMapper;

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
    private CodeMapper codeMapper;

    @Override
    public ResponseMessage<BaseResult> getList(ProductPriceListFilter productPriceListFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product Price List (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            if (permissionMapper.checkPermission(userId, "Product Price List (View By User)") > 0) {
                productPriceListFilter.setViewByUser(1L);
            }


            Pagination pagination = new Pagination();
            pagination.setPage(productPriceListFilter.getPage());
            pagination.setRowsPerPage(productPriceListFilter.getRowsPerPage());
            pagination.setTotal(productPriceListMapper.countList(productPriceListFilter, userId));
            productPriceListFilter.setPage((productPriceListFilter.getPage() - 1) * productPriceListFilter.getRowsPerPage());

            List<ProductPriceListResponse> productPriceListResponses  = productPriceListMapper.getList(productPriceListFilter, userId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-price-list/list", null, null, "Product Price List", "Product Price List (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", productPriceListResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-price-list/list", line, error.toString(), "Product Price List", "Product Price List (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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
              if (permissionMapper.checkPermission(userId, "Product Price List (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            List<ProductPriceListResponse> productPriceListResponses = productPriceListMapper.getOne(id);
            if (!productPriceListResponses.isEmpty()) {
                for (int i =0; i < productPriceListResponses.size(); i++) {
                    productPriceListResponses.get(i).setProductPriceListDetailResponses(productPriceListMapper.getListDetail(productPriceListResponses.get(i).getId()));
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-price-list/find/{id}", null, null, "Product Price List", "Product Price List (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", productPriceListResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-price-list/find/{id}", line, error.toString(), "Product Price List", "Product Price List (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ProductPriceListRequest productPriceListRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product Price List (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check Data
            ProductPriceList productPriceList = new ProductPriceList();
            productPriceList.setCompanyId(productPriceListRequest.getCompanyId());
            productPriceList.setCustomerId(productPriceListRequest.getOrganizationId());
            productPriceList.setTitle(productPriceListRequest.getTitle());
            productPriceList.setDateValidateFrom(productPriceListRequest.getDateValidateFrom());
            productPriceList.setDateValidateTo(productPriceListRequest.getDateValidateTo());
            productPriceList.setPgroupId(productPriceListRequest.getPgroupId());
            productPriceList.setPriceTypeId(productPriceListRequest.getPriceTypeId());
            productPriceList.setCreatedBy(userId);
            productPriceList.setIsApproved(0);
            productPriceList.setIsActive(1);

            Boolean result = productPriceListMapper.insert(productPriceList);
            if (result) {
                //! Get the reference code
                String code = (generateCode.generateAutoCode("product_price_lists", "ppl_code", 7, "PPL", true, "is_active > 0"));
                codeMapper.updateCode("product_price_lists", "ppl_code", code, productPriceList.getId());

                List<ProductPriceListDetails> productPriceListDetails = productPriceListRequest.getProductPriceListDetails();

                if (productPriceListDetails != null && !productPriceListDetails.isEmpty()){
                    for(int i=0;i<productPriceListDetails.size();i++){
                        ProductPriceListDetail productPriceListDetail = new ProductPriceListDetail();
                        productPriceListDetail.setProductPriceListId(productPriceList.getId());
                        productPriceListDetail.setProductId(productPriceListRequest.getProductPriceListDetails().get(i).getProductId());
                        productPriceListDetail.setNote(productPriceListRequest.getProductPriceListDetails().get(i).getNote());
                        productPriceListDetail.setUomId(productPriceListRequest.getProductPriceListDetails().get(i).getUomId());
                        productPriceListDetail.setPrice(productPriceListRequest.getProductPriceListDetails().get(i).getPrice());
                        productPriceListMapper.insertPriceListDetails(productPriceListDetail);
                    }
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-price-list/add", null, null, "Product Price List", "Product Price List (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error){
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-price-list/add", line, error.toString(), "Product Price List", "Product Price List (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> update(ProductPriceListUpdateRequest productPriceListUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product Price List (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check Data
            ProductPriceList productPriceList = new ProductPriceList();
            productPriceList.setId(productPriceListUpdateRequest.getId());
            productPriceList.setCompanyId(productPriceListUpdateRequest.getCompanyId());
            productPriceList.setCustomerId(productPriceListUpdateRequest.getOrganizationId());
            productPriceList.setTitle(productPriceListUpdateRequest.getTitle());
            productPriceList.setDateValidateFrom(productPriceListUpdateRequest.getDateValidateFrom());
            productPriceList.setDateValidateTo(productPriceListUpdateRequest.getDateValidateTo());
            productPriceList.setPgroupId(productPriceListUpdateRequest.getPgroupId());
            productPriceList.setPriceTypeId(productPriceListUpdateRequest.getPriceTypeId());
            productPriceList.setModifiedBy(userId);
            Boolean result = productPriceListMapper.update(productPriceList);

            if (result) {
                List<ProductPriceListDetails> productPriceListDetails = productPriceListUpdateRequest.getProductPriceListDetails();
                productPriceListMapper.deleteProductPriceListDetail(productPriceListUpdateRequest.getId());
                if (productPriceListDetails != null && !productPriceListDetails.isEmpty()){
                    for(int i=0;i<productPriceListDetails.size();i++){
                        ProductPriceListDetail productPriceListDetail = new ProductPriceListDetail();
                        productPriceListDetail.setProductPriceListId(productPriceList.getId());
                        productPriceListDetail.setProductId(productPriceListUpdateRequest.getProductPriceListDetails().get(i).getProductId());
                        productPriceListDetail.setNote(productPriceListUpdateRequest.getProductPriceListDetails().get(i).getNote());
                        productPriceListDetail.setUomId(productPriceListUpdateRequest.getProductPriceListDetails().get(i).getUomId());
                        productPriceListDetail.setPrice(productPriceListUpdateRequest.getProductPriceListDetails().get(i).getPrice());
                        productPriceListMapper.insertPriceListDetails(productPriceListDetail);
                    }
                }
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-price-list/update",null,null,"Product Price List","Product Price List (Edit)","Edit",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/product-group/update", line, error.toString(), "Product Price List", "Product Price List (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    //
    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
           Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Product Price List (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            Boolean result = productPriceListMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-price-list/delete/{id}",null,null,"Product Price List","Product Price List (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-price-list/delete/{id}",line, error.toString(),"Product Price List","Product Price List (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> updateApproveStatus(ProductListApproveStatus productListApproveStatus, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product Price List (Approve)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check Data
            ProductListApproveStatus productApprove = new ProductListApproveStatus();
            productApprove.setId(productListApproveStatus.getId());
            productApprove.setStatus(productListApproveStatus.getStatus());
            Boolean result = productPriceListMapper.updateApproveStatus(productApprove);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-price-list/update-is-approve",null,null,"Product Price List","Product Price List (Approve)","Approve",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }


        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/product-price-list/update-is-approve", line, error.toString(), "Product Price List", "Product Price List (Approve)", "Approve", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateIsClose(ProductListApproveStatus productListApproveStatus, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product Price List (Close)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check Data
            ProductListApproveStatus productApprove = new ProductListApproveStatus();
            productApprove.setId(productListApproveStatus.getId());
            productApprove.setStatus(productListApproveStatus.getStatus());
            Boolean result = productPriceListMapper.updateIsClose(productApprove);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-price-list/update-is-close",null,null,"Product Price List","Product Price List (Close)","Close",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/product-price-list/update-is-close", line, error.toString(), "Product Price List", "Product Price List (Close)", "Close", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListProductDetails(ProductPriceListProductDetailFilter productPriceListFilter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product Price List (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(productPriceListFilter.getPage());
            pagination.setRowsPerPage(productPriceListFilter.getRowsPerPage());
            pagination.setTotal(productPriceListMapper.countListProductDetail(productPriceListFilter));
            productPriceListFilter.setPage((productPriceListFilter.getPage() - 1) * productPriceListFilter.getRowsPerPage());

            List<ProductPriceListDetailResponse> productPriceListResponses  = productPriceListMapper.getListProductDetailByFilter(productPriceListFilter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-price-list/list-product-details", null, null, "Product Price List", "Product Price List (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", productPriceListResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-price-list/list-product-details", line, error.toString(), "Product Price List", "Product Price List (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    public static String incrementNumericPart(String input) {
        // Use a regular expression to split the input into prefix and numeric parts
        String prefix = input.replaceAll("\\d+$", ""); // Extract prefix part
        String numericPart = input.substring(prefix.length()); // Extract numeric part

        // Convert numeric part to an integer
        int number = Integer.parseInt(numericPart);

        // Increment the number
        number += 1;

        // Determine the number of digits in the original numeric part
        int numericPartLength = numericPart.length();

        // Format the incremented number with leading zeros
        String incrementedNumericPart = String.format("%0" + numericPartLength + "d", number);

        // Concatenate the prefix and the incremented numeric part
        return prefix + incrementedNumericPart;
    }

    public static String generateReference(String input) {
        LocalDate currentDate = LocalDate.now();
        String yearLastTwoDigits = String.valueOf(currentDate.getYear()).substring(2);
        String result = yearLastTwoDigits + input + "0000001";
        return result;
    }

}
