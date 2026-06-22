package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ProductFilter;
import com.ut.nlSystemAPi.model.filter.ProductPriceFilter;
import com.ut.nlSystemAPi.model.filter.UomSkuFilter;
import com.ut.nlSystemAPi.model.request.Login.Product.*;
import com.ut.nlSystemAPi.model.request.Login.VendorManagement.VendorPhoto;
import com.ut.nlSystemAPi.model.response.Product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

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

    public ResponseMessage<BaseResult> getList(ProductFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Product (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            if (permissionMapper.checkPermission(userId, "Product (View By User)") > 0) {
                filter.setViewByUser(1L);
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(productMapper.countList(filter, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ProductResponse> productResponses = productMapper.getList(filter, userId);
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/list", null, null, "Product", "Product (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", productResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/list", line, error.toString(), "Product", "Product (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Product (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            List<ProductDetailResponse> productDetailResponses = productMapper.getOne(id, userId);

            if (productDetailResponses.size() > 0) {
                for (int i = 0; i < productDetailResponses.size(); i++) {
                    List<CategoryResponse> category = productMapper.getProductCategory(productDetailResponses.get(i).getId());
                    productDetailResponses.get(i).setCategories(category);
                }
            }

            if (productDetailResponses.size() > 0) {
                for (int i = 0; i < productDetailResponses.size(); i++) {
                    List<ProductICSResponse> ics = productMapper.getICSAccount(productDetailResponses.get(i).getId());
                    productDetailResponses.get(i).setIcs(ics);
                }
            }

            if (productDetailResponses.size() > 0) {
                for (int i = 0; i < productDetailResponses.size(); i++) {
                    List<ProductSkuResponse> productSkus = productMapper.getProductSku(productDetailResponses.get(i).getId());
                    productDetailResponses.get(i).setUomCode(productSkus);
                }
            }

            if (productDetailResponses.size() > 0) {
                for (int i = 0; i < productDetailResponses.size(); i++) {
                    List<ProductSkuResponse> productSkus = productMapper.getProductSku(productDetailResponses.get(i).getId());
                    productDetailResponses.get(i).setBatchCodeInformation(productSkus);
                }
            }

            if (productDetailResponses.size() > 0) {
                for (int i = 0; i < productDetailResponses.size(); i++) {
                    List<ProductPacketResponse> productPacket = productMapper.getProductPacket(productDetailResponses.get(i).getId());
                    productDetailResponses.get(i).setProductPackages(productPacket);
                }
            }

            if (productDetailResponses.size() > 0) {
                for (int i = 0; i < productDetailResponses.size(); i++) {
                    List<PriceRequestInformationResponse> priceRequestInformation = productMapper.getPriceRequestInformation(productDetailResponses.get(i).getId());
                    productDetailResponses.get(i).setPriceRequestInformation(priceRequestInformation);
                }
            }

            if (productDetailResponses.size() > 0) {
                for (int i = 0; i < productDetailResponses.size(); i++) {
                    List<ProductStockLevelResponse> stockLevels = productMapper.getProductStockLevels(productDetailResponses.get(i).getId());
                    productDetailResponses.get(i).setStockLevels(stockLevels);
                }
            }

            if (productDetailResponses.size() > 0) {
                for (int i = 0; i < productDetailResponses.size(); i++) {
                    List<ProductPhotoResponse> photos = productMapper.getProductPhotos(productDetailResponses.get(i).getId());
                    productDetailResponses.get(i).setPhotos(photos);
                }
            }

            if (productDetailResponses.size() > 0) {
                ProductPriceFilter priceFilter = new ProductPriceFilter();
                for (int i = 0; i < productDetailResponses.size(); i++) {
                    priceFilter.setProductId(productDetailResponses.get(i).getId());
                    List<ProductPriceResponse> priceTypes = productMapper.getListPrice(priceFilter, userId);
                    if (!priceTypes.isEmpty()) {
                        for (ProductPriceResponse priceType : priceTypes) {
                            priceFilter.setPriceTypeId(priceType.getPriceTypeId());
                            priceType.setDetails(productMapper.getListPriceDetail(priceFilter));
                            if (priceType.getDetails() != null && !priceType.getDetails().isEmpty()) {
                                priceType.setSetType(priceType.getDetails().get(0).getSetType());
                                priceType.getDetails().get(0).setSetType(null);
                            }
                        }
                    }
                    productDetailResponses.get(i).setProductPrices(priceTypes);
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/find/{id}", null, null, "Product", "Product (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", productDetailResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/find/{id}", line, error.toString(), "Product", "Product (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(ProductRequest productRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
             if (permissionMapper.checkPermission(userId, "Product (Add)") == 0) {
               return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
             }

            // Check Duplicate
            if(productMapper.checkDuplicate(productRequest.getSku(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate SKU", false));
            }

            Products product = new Products();
            product.setCompanyId(productRequest.getCompanyId());
            product.setBrandId(productRequest.getBrandId());
            product.setParentId(productRequest.getParentId());
            applyProductPrimaryPhoto(productRequest, product);
            product.setName(productRequest.getName());
            product.setNameKh(productRequest.getNameKh());
            product.setProductGroupId(productRequest.getProductGroupId());
            product.setIsActive(productRequest.getIsActive());
            product.setColor(productRequest.getColor());
            product.setUrl(productRequest.getUrl());
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
            product.setLuckyCode(productRequest.getLuckyCode());
            product.setCreatedBy(userId);
            Boolean result = productMapper.insert(product);
            if (result) {
                syncProductImages(product.getId(), productRequest.getPhotos());

                if (productRequest.getProductGroupId() != null) {
                    ProductPgroup productPgroup = new ProductPgroup();
                    productPgroup.setProductId(product.getId());
                    productPgroup.setPgroupId(productRequest.getProductGroupId());
                    productMapper.insertProductPgroup(productPgroup);

//                  Check If The Product Group Price Already Exist
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

                if (productRequest.getUomCode() != null && !productRequest.getUomCode().isEmpty()) {
                    List<UomCodeRequest> uomCodeRequests = productRequest.getUomCode();
                    for (int i = 0; i < uomCodeRequests.size(); i++) {
                        ProductSku productSku = new ProductSku();
                        productSku.setProductId(product.getId());
                        productSku.setCode(productRequest.getUomCode().get(i).getCode());
                        productSku.setUomId(productRequest.getUomCode().get(i).getUomId());
                        productMapper.insertProductSku(productSku);
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
                        productPacket.setProductId(product.getId());
                        productPacket.setPacketId(productPacketRequests.get(i).getProductId());
                        productPacket.setQty(productRequest.getProductPackage().get(i).getQty());
                        productPacket.setQtyUomId(productRequest.getProductPackage().get(i).getUomId());
                        productPacket.setConversion(productRequest.getProductPackage().get(i).getConversion());
                        productMapper.insertProductPacket(productPacket);
                    }
                }

                if (productRequest.getStockLevels() != null && !productRequest.getStockLevels().isEmpty()) {
                    List<ProductStockLevelRequest> stockLevelRequests = productRequest.getStockLevels();
                    for (ProductStockLevelRequest stockLevelRequest : stockLevelRequests) {
                        ProductStockLevel productStockLevel = new ProductStockLevel();
                        productStockLevel.setProductId(product.getId());
                        productStockLevel.setStockLevelId(stockLevelRequest.getStockLevelId());
                        productStockLevel.setQty(stockLevelRequest.getQty());
                        productStockLevel.setCreatedBy(userId);
                        productMapper.insertProductStockLevel(productStockLevel);
                    }
                }

                if (productRequest.getProductPrices() != null && !productRequest.getProductPrices().isEmpty()) {
                    productMapper.deletePrice(product.getId());
                    for (ProductPriceRequest priceRequest : productRequest.getProductPrices()) {
                        if (priceRequest.getDetails() != null && !priceRequest.getDetails().isEmpty()) {
                            for (ProductPriceDetailRequest detail : priceRequest.getDetails()) {
                                ProductPrice productPrice = new ProductPrice();
                                productPrice.setProductId(product.getId());
                                productPrice.setPriceTypeId(priceRequest.getPriceTypeId());
                                productPrice.setUomId(detail.getUomId() != null ? detail.getUomId() : product.getPriceUomId());
                                productPrice.setAmount(detail.getAmount());
                                productPrice.setAmountBefore(detail.getAmountBefore());
                                productPrice.setPercentage(detail.getPercentage());
                                productPrice.setAddOn(detail.getAddOn());
                                productPrice.setSetType(priceRequest.getSetType());
                                productPrice.setCreatedBy(userId);
                                productMapper.addPrice(productPrice);

                                ProductPriceHistory productPriceHistory = new ProductPriceHistory();
                                productPriceHistory.setProductId(product.getId());
                                productPriceHistory.setPriceTypeId(priceRequest.getPriceTypeId());
                                productPriceHistory.setUomId(productPrice.getUomId());
                                productPriceHistory.setAmount(detail.getAmount());
                                productPriceHistory.setPercentage(detail.getPercentage());
                                productPriceHistory.setAddOn(detail.getAddOn());
                                productPriceHistory.setSetType(priceRequest.getSetType());
                                productPriceHistory.setCreatedBy(userId);
                                productMapper.addPriceHistory(productPriceHistory);
                            }
                        }
                    }
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product/add", null, null, "Product", "Product (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", null, false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/add", line, error.toString(), "Product", "Product (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(ProductUpdateRequest productUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Product (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            // Check Duplicate
            if(productMapper.checkDuplicate(productUpdateRequest.getUpc(), productUpdateRequest.getId()) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate UPC", false));
            }

            // Check Data
            Products product = new Products();
            product.setId(productUpdateRequest.getId());
            product.setCompanyId(productUpdateRequest.getCompanyId());
            product.setBrandId(productUpdateRequest.getBrandId());
            product.setParentId(productUpdateRequest.getParentId());
            applyProductPrimaryPhoto(productUpdateRequest, product);
            product.setName(productUpdateRequest.getName());
            product.setNameKh(productUpdateRequest.getNameKh());
            product.setProductGroupId(productUpdateRequest.getProductGroupId());
            product.setIsActive(productUpdateRequest.getIsActive());
            product.setColor(productUpdateRequest.getColor());
            product.setUrl(productUpdateRequest.getUrl());
            product.setPeriodFrom(productUpdateRequest.getPeriodFrom());
            product.setPeriodTo(productUpdateRequest.getPeriodTo());
            product.setIsPacket(productUpdateRequest.getIsPacket());
            product.setBarcode(productUpdateRequest.getUpc());
            product.setUnitCost(productUpdateRequest.getUnitCost());
            product.setProductRecorderLevel(productUpdateRequest.getProductRecorderLevel());
            product.setIsExpiredDate(productUpdateRequest.getIsExpiredDate());
            product.setCode(productUpdateRequest.getSku());
            product.setPriceUomId(productUpdateRequest.getUomId());
            product.setSpec(productUpdateRequest.getSpec());
            if(productUpdateRequest.getFileCatalog() != null){
                product.setFileCatalog(productUpdateRequest.getFileCatalog().getUrl());
                product.setFileCatalogName(productUpdateRequest.getFileCatalog().getName());
            }
            product.setDescription(productUpdateRequest.getDescription());
            product.setWidth(productUpdateRequest.getWidth());
            product.setHeight(productUpdateRequest.getHeight());
            product.setLength(productUpdateRequest.getLength());
            product.setWeight(productUpdateRequest.getWeight());
            product.setCubicMeter(productUpdateRequest.getM3());
            product.setSizeUomId(productUpdateRequest.getSizeUomId());
            product.setWeightUomId(productUpdateRequest.getWeightUomId());
            product.setNote(productUpdateRequest.getVendorInfo());
            product.setLuckyCode(productUpdateRequest.getLuckyCode());
            product.setModifiedBy(userId);
            Boolean result = productMapper.update(product);

            if (result) {
                syncProductImages(product.getId(), productUpdateRequest.getPhotos());
                if (productUpdateRequest.getProductGroupId() != null) {
                    productMapper.deleteProductPgroup(product.getId());
                    ProductPgroup productPgroup = new ProductPgroup();
                    productPgroup.setProductId(product.getId());
                    productPgroup.setPgroupId(productUpdateRequest.getProductGroupId());
                    productMapper.insertProductPgroup(productPgroup);
                }

                if (productUpdateRequest.getIcs() != null && !productUpdateRequest.getIcs().isEmpty()) {
                    productMapper.deleteICSAccount(product.getId());
                    List<ProductICSRequest> productIcsRequests = productUpdateRequest.getIcs();
                    for (int i = 0; i < productIcsRequests.size(); i++) {
                        ProductChartAccount productChartAccount = new ProductChartAccount();
                        productChartAccount.setProductId(product.getId());
                        productChartAccount.setAccountType(productUpdateRequest.getIcs().get(i).getAccountType());
                        productChartAccount.setChartAccountId(productUpdateRequest.getIcs().get(i).getChartAccountId());
                        productMapper.insertICSAccount(productChartAccount);
                    }
                }

                if (productUpdateRequest.getCategories() != null && !productUpdateRequest.getCategories().isEmpty()) {
                    productMapper.deleteProductCategory(product.getId());
                    List<Long> categories = productUpdateRequest.getCategories();
                    for (int i = 0; i < categories.size(); i++) {
                        ProductCategory productCategory = new ProductCategory();
                        productCategory.setProductId(product.getId());
                        productCategory.setCategoryId(categories.get(i));
                        productMapper.insertProductCategory(productCategory);
                    }
                }

                if (productUpdateRequest.getUomCode() != null && !productUpdateRequest.getUomCode().isEmpty()) {
                    productMapper.deleteProductSku(product.getId());
                    List<UomCodeRequest> uomCodeRequests = productUpdateRequest.getUomCode();
                    for (int i = 0; i < uomCodeRequests.size(); i++) {
                        ProductSku productSku = new ProductSku();
                        productSku.setProductId(product.getId());
                        productSku.setCode(productUpdateRequest.getUomCode().get(i).getCode());
                        productSku.setUomId(productUpdateRequest.getUomCode().get(i).getUomId());
                        productMapper.insertProductSku(productSku);
                    }
                }

                if (productUpdateRequest.getBatchCodeInformation() != null && !productUpdateRequest.getBatchCodeInformation().isEmpty()) {
                    productMapper.deleteProductSku(product.getId());
                    List<ProductSkuRequest> productSkuRequests = productUpdateRequest.getBatchCodeInformation();
                    for (int i = 0; i < productSkuRequests.size(); i++) {
                        ProductSku productSku = new ProductSku();
                        productSku.setProductId(product.getId());
                        productSku.setCode(productUpdateRequest.getBatchCodeInformation().get(i).getCode());
                        productSku.setUomId(product.getPriceUomId());
                        productMapper.insertProductSku(productSku);
                    }
                }

                if (productUpdateRequest.getProductPackage() != null && !productUpdateRequest.getProductPackage().isEmpty()) {
                    productMapper.deleteProductPacket(product.getId());
                    List<ProductPacketRequest> productPacketRequests = productUpdateRequest.getProductPackage();
                    for (int i = 0; i < productPacketRequests.size(); i++) {
                        ProductPacket productPacket = new ProductPacket();
                        productPacket.setProductId(product.getId());
                        productPacket.setPacketId(productPacketRequests.get(i).getProductId());
                        productPacket.setQty(productUpdateRequest.getProductPackage().get(i).getQty());
                        productPacket.setQtyUomId(productUpdateRequest.getProductPackage().get(i).getUomId());
                        productPacket.setConversion(productUpdateRequest.getProductPackage().get(i).getConversion());
                        productMapper.insertProductPacket(productPacket);
                    }
                }

                productMapper.archiveProductStockLevel(product.getId(), userId);
                if (productUpdateRequest.getStockLevels() != null && !productUpdateRequest.getStockLevels().isEmpty()) {
                    List<ProductStockLevelRequest> stockLevelRequests = productUpdateRequest.getStockLevels();
                    for (ProductStockLevelRequest stockLevelRequest : stockLevelRequests) {
                        ProductStockLevel productStockLevel = new ProductStockLevel();
                        productStockLevel.setProductId(product.getId());
                        productStockLevel.setStockLevelId(stockLevelRequest.getStockLevelId());
                        productStockLevel.setQty(stockLevelRequest.getQty());
                        productStockLevel.setCreatedBy(userId);
                        productMapper.insertProductStockLevel(productStockLevel);
                    }
                }

                if (productUpdateRequest.getProductPrices() != null && !productUpdateRequest.getProductPrices().isEmpty()) {
                    productMapper.deletePrice(product.getId());
                    for (ProductPriceRequest priceRequest : productUpdateRequest.getProductPrices()) {
                        if (priceRequest.getDetails() != null && !priceRequest.getDetails().isEmpty()) {
                            for (ProductPriceDetailRequest detail : priceRequest.getDetails()) {
                                ProductPrice productPrice = new ProductPrice();
                                productPrice.setProductId(product.getId());
                                productPrice.setPriceTypeId(priceRequest.getPriceTypeId());
                                productPrice.setUomId(detail.getUomId() != null ? detail.getUomId() : product.getPriceUomId());
                                productPrice.setAmount(detail.getAmount());
                                productPrice.setAmountBefore(detail.getAmountBefore());
                                productPrice.setPercentage(detail.getPercentage());
                                productPrice.setAddOn(detail.getAddOn());
                                productPrice.setSetType(priceRequest.getSetType());
                                productPrice.setCreatedBy(userId);
                                productMapper.addPrice(productPrice);

                                ProductPriceHistory productPriceHistory = new ProductPriceHistory();
                                productPriceHistory.setProductId(product.getId());
                                productPriceHistory.setPriceTypeId(priceRequest.getPriceTypeId());
                                productPriceHistory.setUomId(productPrice.getUomId());
                                productPriceHistory.setAmount(detail.getAmount());
                                productPriceHistory.setPercentage(detail.getPercentage());
                                productPriceHistory.setAddOn(detail.getAddOn());
                                productPriceHistory.setSetType(priceRequest.getSetType());
                                productPriceHistory.setCreatedBy(userId);
                                productMapper.addPriceHistory(productPriceHistory);
                            }
                        }
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product/update", null, null, "Product", "Product (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", null, false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/update", line, error.toString(), "Product", "Product (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Product (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            Boolean result = productMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product/delete/{id}", null, null, "Product", "Product (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/delete/{id}", line, error.toString(), "Product", "Product (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateActiveStatus(ProductActiveStatus productActiveStatusRequest, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Product (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }
            ProductActiveStatus productActiveStatus = new ProductActiveStatus();
            productActiveStatus.setId(productActiveStatusRequest.getId());
            productActiveStatus.setIsActive(productActiveStatusRequest.getIsActive());
            Boolean result = productMapper.updateActiveStatus(productActiveStatus, userId);
            if (result) {
                Integer isActive = productActiveStatus.getIsActive() != null
                        ? productActiveStatus.getIsActive().intValue()
                        : null;
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product/delete/{id}", null, null, "Product", "Product (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/delete/{id}", line, error.toString(), "Product", "Product (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListPrice(ProductPriceFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Product (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(productMapper.countListPrice(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<ProductPriceResponse> responses = productMapper.getListPrice(filter, userId);
            if (!responses.isEmpty()) {
                for (int i = 0; i < responses.size(); i++) {
                filter.setPriceTypeId(responses.get(i).getPriceTypeId());
                responses.get(i).setDetails(productMapper.getListPriceDetail(filter));
                responses.get(i).setSetType(responses.get(i).getDetails().get(0).getSetType());
                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/list", null, null, "Product", "Product (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/list", line, error.toString(), "Product", "Product (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getListPriceHistory(ProductPriceFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Product (view)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(productMapper.countListPriceHistory(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ProductPriceHistoryResponse> responses = productMapper.getListPriceHistory(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/list", null, null, "Product", "Product (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/list", line, error.toString(), "Product", "Product (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> addPrice(List<ProductPriceRequest> productRequests, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            if (productRequests == null || productRequests.isEmpty()) {
                return ResponseMessageUtils.makeResponse(false, messageService.message("No product price request.", false));
            }

            boolean result = false;
            Map<Long, Long> defaultUomMap = new HashMap<>();
            Map<Long, Boolean> deletedByProductMap = new HashMap<>();

            for (ProductPriceRequest productRequest : productRequests) {
                Long productId = productRequest.getProductId();
                if (productId == null) {
                    continue;
                }

                if (!Boolean.TRUE.equals(deletedByProductMap.get(productId))) {
                    productMapper.deletePrice(productId);
                    deletedByProductMap.put(productId, true);
                }

                if (productRequest.getDetails() != null && !productRequest.getDetails().isEmpty()) {
                    Long defaultUomId = getDefaultProductUomId(productId, userId, defaultUomMap);
                    for (ProductPriceDetailRequest detail : productRequest.getDetails()) {
                        Long uomId = detail.getUomId() != null ? detail.getUomId() : defaultUomId;
                        if (uomId == null) {
                            continue;
                        }

                        ProductPrice productPrice = new ProductPrice();
                        productPrice.setProductId(productId);
                        productPrice.setPriceTypeId(productRequest.getPriceTypeId());
                        productPrice.setUomId(uomId);
                        productPrice.setAmount(detail.getAmount());
                        productPrice.setAmountBefore(detail.getAmountBefore());
                        productPrice.setPercentage(detail.getPercentage());
                        productPrice.setAddOn(detail.getAddOn());
                        productPrice.setSetType(productRequest.getSetType());
                        productPrice.setCreatedBy(userId);
                        productMapper.addPrice(productPrice);

                        ProductPriceHistory productPriceHistory = new ProductPriceHistory();
                        productPriceHistory.setProductId(productId);
                        productPriceHistory.setPriceTypeId(productRequest.getPriceTypeId());
                        productPriceHistory.setUomId(uomId);
                        productPriceHistory.setAmount(detail.getAmount());
                        productPriceHistory.setPercentage(detail.getPercentage());
                        productPriceHistory.setAddOn(detail.getAddOn());
                        productPriceHistory.setSetType(productRequest.getSetType());
                        productPriceHistory.setCreatedBy(userId);
                        productMapper.addPriceHistory(productPriceHistory);

                        result = true;
                    }
                }
            }

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/add/price", null, null, "Product", "Product (Edit)", "Edit", 1, result ? "Success" : "Partial Success", startDuration, endDuration, httpServletRequest);
            return result
                    ? ResponseMessageUtils.makeResponse(true, messageService.message("Success", true))
                    : ResponseMessageUtils.makeResponse(false, messageService.message("Fail", false));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/add/price", line, error.toString(), "Product", "Product (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> updateCost(ProductUpdateCostRequest productUpdateCostRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Product (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            // Check Data
            Products product = new Products();
            product.setId(productUpdateCostRequest.getId());
            product.setEstimatedCost(productUpdateCostRequest.getEstimatedCost());
            Boolean result = productMapper.updateCost(product);

            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product/update", null, null, "Product", "Product (Update)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", null, false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/update", line, error.toString(), "Product", "Product (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getUomSku(UomSkuFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product (view)") == 0) {
             return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            List<String> responses = productMapper.getUomSku(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/list", null, null, "Product", "Product (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/list", line, error.toString(), "Product", "Product (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> isEndOfLife(ProductEndOfLife request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = productMapper.isEndOfLife(request);

            if (result) {
                Map<String, Object> freedomProduct = new HashMap<>();
                freedomProduct.put("id", request.getId());
                freedomProduct.put("isEndOfLife", request.getIsEndOfLife());
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product/update", null, null, "Product", "Product (Update)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", null, false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product/update", line, error.toString(), "Product", "Product (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private void applyProductPrimaryPhoto(ProductRequest productRequest, Products product) {
        if (productRequest.getPhotos() != null && !productRequest.getPhotos().isEmpty() && productRequest.getPhotos().get(0) != null) {
            VendorPhoto primaryPhoto = productRequest.getPhotos().get(0);
            product.setPhoto(primaryPhoto.getUrl());
            product.setPhotoName(primaryPhoto.getName());
            return;
        }

        if (productRequest.getPhoto() != null) {
            product.setPhoto(productRequest.getPhoto().getUrl());
            product.setPhotoName(productRequest.getPhoto().getName());
        }
    }

    private void syncProductImages(Long productId, List<VendorPhoto> photos) {
        if (photos == null) {
            return;
        }

        productMapper.deleteProductImage(productId);
        for (int i = 1; i < photos.size(); i++) {
            VendorPhoto photo = photos.get(i);
            if (photo == null || photo.getUrl() == null) {
                continue;
            }

            ProductImage productImage = new ProductImage();
            productImage.setProductId(productId);
            productImage.setUrl(photo.getUrl());
            productImage.setName(photo.getName());
            productMapper.insertProductImage(productImage);
        }
    }

    private Map<String, Object> toFreedomProductMap(Products product) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", product.getId());
        map.put("photo", product.getPhoto());
        map.put("photoName", product.getPhotoName());
        map.put("companyId", product.getCompanyId());
        map.put("brandId", product.getBrandId());
        map.put("priceRequestId", product.getPriceRequestId());
        map.put("parentId", product.getParentId());
        map.put("name", product.getName());
        map.put("nameKh", product.getNameKh());
        map.put("color", product.getColor());
        map.put("url", product.getUrl());
        map.put("periodFrom", product.getPeriodFrom());
        map.put("periodTo", product.getPeriodTo());
        map.put("barcode", product.getBarcode());
        map.put("unitCost", product.getUnitCost());
        map.put("estimatedCost", product.getEstimatedCost());
        map.put("productRecorderLevel", product.getProductRecorderLevel());
        map.put("isExpiredDate", product.getIsExpiredDate());
        map.put("code", product.getCode());
        map.put("priceUomId", product.getPriceUomId());
        map.put("spec", product.getSpec());
        map.put("description", product.getDescription());
        map.put("fileCatalog", product.getFileCatalog());
        map.put("fileCatalogName", product.getFileCatalogName());
        map.put("width", product.getWidth());
        map.put("height", product.getHeight());
        map.put("length", product.getLength());
        map.put("sizeUomId", product.getSizeUomId());
        map.put("cubicMeter", product.getCubicMeter());
        map.put("weight", product.getWeight());
        map.put("weightUomId", product.getWeightUomId());
        map.put("note", product.getNote());
        map.put("luckyCode", product.getLuckyCode());
        map.put("isPacket", product.getIsPacket());
        map.put("createdBy", product.getCreatedBy());
        map.put("modifiedBy", product.getModifiedBy());
        map.put("isActive", product.getIsActive());
        return map;
    }

    private Map<String, Object> toFreedomProductPgroupMap(ProductPgroup pgroup) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", pgroup.getProductId());
        map.put("pgroupId", pgroup.getPgroupId());
        return map;
    }

    private Map<String, Object> toFreedomProductChartAccountMap(ProductChartAccount ics) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", ics.getProductId());
        map.put("accountType", ics.getAccountType());
        map.put("chartAccountId", ics.getChartAccountId());
        return map;
    }

    private Map<String, Object> toFreedomProductCategoryMap(ProductCategory category) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", category.getProductId());
        map.put("categoryId", category.getCategoryId());
        return map;
    }

    private Map<String, Object> toFreedomProductSkuMap(ProductSku sku) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", sku.getProductId());
        map.put("code", sku.getCode());
        map.put("uomId", sku.getUomId());
        return map;
    }

    private Map<String, Object> toFreedomProductPacketMap(ProductPacket packet) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", packet.getProductId());
        map.put("packetId", packet.getPacketId());
        map.put("qty", packet.getQty());
        map.put("qtyUomId", packet.getQtyUomId());
        map.put("conversion", packet.getConversion());
        return map;
    }

    private Map<String, Object> toFreedomProductStockLevelMap(ProductStockLevel stockLevel) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", stockLevel.getProductId());
        map.put("stockLevelId", stockLevel.getStockLevelId());
        map.put("qty", stockLevel.getQty());
        map.put("createdBy", stockLevel.getCreatedBy());
        return map;
    }

    private Map<String, Object> toFreedomProductPriceMap(ProductPrice price) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", price.getProductId());
        map.put("priceTypeId", price.getPriceTypeId());
        map.put("uomId", price.getUomId());
        map.put("amount", price.getAmount());
        map.put("amountBefore", price.getAmountBefore());
        map.put("percentage", price.getPercentage());
        map.put("addOn", price.getAddOn());
        map.put("setType", price.getSetType());
        map.put("createdBy", price.getCreatedBy());
        return map;
    }

    private Map<String, Object> toFreedomProductPriceHistoryMap(ProductPriceHistory history) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", history.getProductId());
        map.put("priceTypeId", history.getPriceTypeId());
        map.put("uomId", history.getUomId());
        map.put("amount", history.getAmount());
        map.put("percentage", history.getPercentage());
        map.put("addOn", history.getAddOn());
        map.put("setType", history.getSetType());
        map.put("createdBy", history.getCreatedBy());
        return map;
    }

    private Long getDefaultProductUomId(Long productId, Long userId, Map<Long, Long> cache) {
        if (productId == null) {
            return null;
        }
        if (cache.containsKey(productId)) {
            return cache.get(productId);
        }

        Long uomId = null;
        List<ProductDetailResponse> productDetails = productMapper.getOne(productId, userId);
        if (productDetails != null && !productDetails.isEmpty()) {
            uomId = productDetails.get(0).getUomId();
        }
        cache.put(productId, uomId);
        return uomId;
    }

}
