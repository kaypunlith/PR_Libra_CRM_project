package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ProductBrandMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.ProductBrand;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.ProductBrand.ProductBrandRequest;
import com.ut.nlSystemAPi.model.request.Login.ProductBrand.ProductBrandUpdateRequest;
import com.ut.nlSystemAPi.model.response.ProductBrand.ProductBrandResponse;
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
public class ProductBrandServiceImpl implements ProductBrandService {

    @Autowired
    private ProductBrandMapper productBrandMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest)
            throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(productBrandMapper.countList(filter));

            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());
            List<ProductBrandResponse> responses = productBrandMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-brand/list", null, null, "Product Brand", "Product Brand (View)",
                    "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true,
                    messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-brand/list", line, error.toString(), "Product Brand",
                    "Product Brand (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest)
            throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            List<ProductBrandResponse> responses = productBrandMapper.getOne(id);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-brand/find/{id}", null, null, "Product Brand", "Product Brand (View)",
                    "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-brand/find/{id}", line, error.toString(), "Product Brand",
                    "Product Brand (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ProductBrandRequest request, BindingResult bindingResult,
            HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            if (productBrandMapper.checkDuplicate(request.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            ProductBrand productBrand = new ProductBrand();
            productBrand.setName(request.getName());
            productBrand.setCreatedBy(userId);
            productBrand.setIsActive(1);
            Boolean result = productBrandMapper.insert(productBrand);

            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-brand/add", null, null, "Product Brand", "Product Brand (Add)",
                        "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-brand/add", line, error.toString(), "Product Brand",
                    "Product Brand (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(ProductBrandUpdateRequest request, BindingResult bindingResult,
            HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            if (productBrandMapper.checkDuplicate(request.getName(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            ProductBrand productBrand = new ProductBrand();
            productBrand.setId(request.getId());
            productBrand.setName(request.getName());
            productBrand.setModifiedBy(userId);
            Boolean result = productBrandMapper.update(productBrand);

            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-brand/update", null, null, "Product Brand",
                        "Product Brand (Update)", "Update", 1, "Success", startDuration, endDuration,
                        httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-brand/update", line, error.toString(), "Product Brand",
                    "Product Brand (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest)
            throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();

            Boolean result = productBrandMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-brand/delete/{id}", null, null, "Product Brand",
                        "Product Brand (Delete)", "Delete", 1, "Success", startDuration, endDuration,
                        httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-brand/delete/{id}", line, error.toString(), "Product Brand",
                    "Product Brand (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private Map<String, Object> toFreedomProductBrandMap(ProductBrand productBrand) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", productBrand.getId());
        map.put("name", productBrand.getName());
        map.put("createdBy", productBrand.getCreatedBy());
        map.put("modifiedBy", productBrand.getModifiedBy());
        map.put("isActive", productBrand.getIsActive());
        return map;
    }
}
