package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.mapper.primary.ProductGroupMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.ProductGroup.ICSRequest;
import com.ut.nlSystemAPi.model.request.Login.ProductGroup.ProductGroupRequest;
import com.ut.nlSystemAPi.model.request.Login.ProductGroup.ProductGroupUpdateRequest;
import com.ut.nlSystemAPi.model.response.ProductGroup.*;
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
public class ProductGroupServiceImpl implements ProductGroupService {
    @Autowired
    private ProductGroupMapper productGroupMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product Group (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(productGroupMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<ProductGroupResponse> productGroupResponses  = productGroupMapper.getList(filter);
            if (productGroupResponses.size() > 0){
                for(int i = 0; i < productGroupResponses.size(); i++){
                    List<ICSResponse> icsResponses = productGroupMapper.getICSProduct(productGroupResponses.get(i).getId());
                    productGroupResponses.get(i).setICSResponse(icsResponses);

                }
            }
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group/list", null, null, "Product Group", "Product Group (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", productGroupResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group/list", line, error.toString(), "Product Group", "Product Group (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
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
              if (permissionMapper.checkPermission(userId, "Product Group (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            List<ProductGroupResponse> productGroupResponses  = productGroupMapper.getOne(id);
            if (productGroupResponses.size() > 0){
                for(int i = 0; i < productGroupResponses.size(); i++){
                    List<ICSResponse> icsResponses = productGroupMapper.getICSProduct(productGroupResponses.get(i).getId());
                    productGroupResponses.get(i).setICSResponse(icsResponses);

                    List<ProductPgroupResponse> productPgroupResponses = productGroupMapper.getProductPgroup(productGroupResponses.get(i).getId());
                    productGroupResponses.get(i).setProducts(productPgroupResponses);

                    List<UserPgroupResponse> userPgroupResponses = productGroupMapper.getUserPgroup(productGroupResponses.get(i).getId());
                    productGroupResponses.get(i).setUsers(userPgroupResponses);
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group/find/{id}", null, null, "Product Group", "Product Group (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", productGroupResponses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group/find/{id}", line, error.toString(), "Product Group", "Product Group (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(ProductGroupRequest productGroupRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product Group (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            // Check Duplicate
            if(productGroupMapper.checkDuplicate(productGroupRequest.getName(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            // Check Data
            ProductGroup productGroup = new ProductGroup();
            productGroup.setParentId(productGroupRequest.getParentId());
            productGroup.setName(productGroupRequest.getName());
            productGroup.setUserApply(productGroupRequest.getUserApply());
            productGroup.setIcsApplySub(productGroupRequest.getIcsApplySub());
            productGroup.setDescription(productGroupRequest.getDescription());
            productGroup.setCreatedBy(userId);
            productGroup.setIsActive(1);
            Boolean result = productGroupMapper.insert(productGroup);
            if (result) {

                PgroupCompany pgroupCompany = new PgroupCompany();
                pgroupCompany.setPgroupId(productGroup.getId());
                pgroupCompany.setCompanyId(productGroupRequest.getCompanyId());
                productGroupMapper.insertPgroupCompany(pgroupCompany);

                if (productGroupRequest.getUsers() != null && !productGroupRequest.getUsers().isEmpty()) {
                    List<Long> users = productGroupRequest.getUsers();
                    for (int i = 0; i< users.size(); i++) {
                        UserPgroup userPgroup = new UserPgroup();
                        userPgroup.setUserId(users.get(i));
                        userPgroup.setPgroupId(productGroup.getId());
                        productGroupMapper.insertUserPgroup(userPgroup);
                    }
                }

                if (productGroupRequest.getIcsRequests() != null && !productGroupRequest.getIcsRequests().isEmpty()) {
                    List<ICSRequest> icsRequests = productGroupRequest.getIcsRequests();
                    for (int i = 0; i< icsRequests.size(); i++) {
                        PgroupChartAcount pgroupChartAccount = new PgroupChartAcount();
                        pgroupChartAccount.setPgroupId(productGroup.getId());
                        pgroupChartAccount.setAccountTypeId(productGroupRequest.getIcsRequests().get(i).getChartAccountType()); //nested list
                        pgroupChartAccount.setChartAccountId(productGroupRequest.getIcsRequests().get(i).getChartAccountId());
                        productGroupMapper.insertICSAccount(pgroupChartAccount);
                    }
                }

                if (productGroupRequest.getProducts() != null && !productGroupRequest.getProducts().isEmpty()){
                    List<Long> products = productGroupRequest.getProducts();
                    for (int i = 0; i< products.size(); i++) {
                        ProductPgroup productPgroup = new ProductPgroup();
                        productPgroup.setPgroupId(productGroup.getId());
                        productPgroup.setProductId(products.get(i));
                        productGroupMapper.insertProductPgroup(productPgroup);
                    }
                }

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-group/add", null, null, "Product Group", "Product Group (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error){
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group/add", line, error.toString(), "Product Group", "Product Group (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> update(ProductGroupUpdateRequest productGroupUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Product Group (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }
            ProductGroup productGroup = new ProductGroup();
            productGroup.setId(productGroupUpdateRequest.getId());
            productGroup.setParentId(productGroupUpdateRequest.getParentId());
            productGroup.setName(productGroupUpdateRequest.getName());
            productGroup.setUserApply(productGroupUpdateRequest.getUserApply());
            productGroup.setIcsApplySub(productGroupUpdateRequest.getIcsApplySub());
            productGroup.setDescription(productGroupUpdateRequest.getDescription());
            productGroup.setModifiedBy(userId);
            productGroup.setIsActive(1);
            Boolean result = productGroupMapper.update(productGroup);
            LocalTime endDuration = LocalTime.now();

            if (result) {

                productGroupMapper.deletePgroupCompany(productGroupUpdateRequest.getId());
                PgroupCompany pgroupCompany = new PgroupCompany();
                pgroupCompany.setPgroupId(productGroup.getId());
                pgroupCompany.setCompanyId(productGroupUpdateRequest.getCompanyId());
                productGroupMapper.insertPgroupCompany(pgroupCompany);

                if (productGroupUpdateRequest.getUsers() != null && !productGroupUpdateRequest.getUsers().isEmpty()) {
                    List<Long> users = productGroupUpdateRequest.getUsers();
                    productGroupMapper.deleteUserPgroup(productGroupUpdateRequest.getId());
                    for (int i = 0; i< users.size(); i++) {
                        UserPgroup userPgroup = new UserPgroup();
                        userPgroup.setUserId(users.get(i));
                        userPgroup.setPgroupId(productGroup.getId());
                        productGroupMapper.insertUserPgroup(userPgroup);
                    }

                }

                if (productGroupUpdateRequest.getIcsRequests() != null && !productGroupUpdateRequest.getIcsRequests().isEmpty()) {
                    List<ICSRequest> icsRequests = productGroupUpdateRequest.getIcsRequests();
                    productGroupMapper.deleteICSAccount(productGroupUpdateRequest.getId());
                    for (int i = 0; i< icsRequests.size(); i++) {
                        PgroupChartAcount pgroupChartAccount = new PgroupChartAcount();
                        pgroupChartAccount.setPgroupId(productGroup.getId());
                        pgroupChartAccount.setAccountTypeId(productGroupUpdateRequest.getIcsRequests().get(i).getChartAccountType());
                        pgroupChartAccount.setChartAccountId(productGroupUpdateRequest.getIcsRequests().get(i).getChartAccountId());
                        productGroupMapper.insertICSAccount(pgroupChartAccount);
                    }
                }

                if (productGroupUpdateRequest.getProducts() != null && !productGroupUpdateRequest.getProducts().isEmpty()){
                    List<Long> products = productGroupUpdateRequest.getProducts();
                    productGroupMapper.deleteProductPgroup(productGroupUpdateRequest.getId());
                    for (int i = 0; i< products.size(); i++) {
                        ProductPgroup productPgroup = new ProductPgroup();
                        productPgroup.setPgroupId(productGroup.getId());
                        productPgroup.setProductId(products.get(i));
                        productGroupMapper.insertProductPgroup(productPgroup);
                    }
                }

                activityLogService.insert("/product-group/update", null, null, "Product Group", "Product Group (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/product-group/update", line, error.toString(), "Product Group", "Product Group (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getPriceType(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Product Group (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            List<PriceTypePgroupResponse> responses  = productGroupMapper.getPriceType(id, userId);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group/find-price-type/{id}", null, null, "Product Group", "Product Group (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group/find-price-type/{id}", line, error.toString(), "Product Group", "Product Group (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
           Long userId = userService.getUserAuth().getId();
              if (permissionMapper.checkPermission(userId, "Product Group (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
              }

            Boolean result = productGroupMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/product-group/delete/{id}",null,null,"Product Group","Product Group (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/product-group/delete/{id}",line, error.toString(),"Product Group","Product Group (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


    private Map<String, Object> toFreedomProductGroupMap(ProductGroup productGroup) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", productGroup.getId());
        map.put("parentId", productGroup.getParentId());
        map.put("name", productGroup.getName());
        map.put("userApply", productGroup.getUserApply());
        map.put("icsApplySub", productGroup.getIcsApplySub());
        map.put("description", productGroup.getDescription());
        map.put("createdBy", productGroup.getCreatedBy());
        map.put("modifiedBy", productGroup.getModifiedBy());
        map.put("isActive", productGroup.getIsActive());
        return map;
    }

    private Map<String, Object> toFreedomPgroupCompanyMap(PgroupCompany pgroupCompany) {
        Map<String, Object> map = new HashMap<>();
        map.put("pgroupId", pgroupCompany.getPgroupId());
        map.put("companyId", pgroupCompany.getCompanyId());
        return map;
    }

    private Map<String, Object> toFreedomUserPgroupMap(UserPgroup userPgroup) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userPgroup.getUserId());
        map.put("pgroupId", userPgroup.getPgroupId());
        return map;
    }

    private Map<String, Object> toFreedomPgroupAccountMap(PgroupChartAcount pgroupChartAcount) {
        Map<String, Object> map = new HashMap<>();
        map.put("pgroupId", pgroupChartAcount.getPgroupId());
        map.put("accountTypeId", pgroupChartAcount.getAccountTypeId());
        map.put("chartAccountId", pgroupChartAcount.getChartAccountId());
        return map;
    }

    private Map<String, Object> toFreedomProductPgroupMap(ProductPgroup productPgroup) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productPgroup.getProductId());
        map.put("pgroupId", productPgroup.getPgroupId());
        return map;
    }

}
