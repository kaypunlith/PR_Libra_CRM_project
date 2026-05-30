package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.VendorManagementMapper;
import com.ut.nlSystemAPi.model.*;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.VendorFilter;
import com.ut.nlSystemAPi.model.request.Login.VendorManagement.VendorManagementRequest;
import com.ut.nlSystemAPi.model.request.Login.VendorManagement.VendorManagementUpdateRequest;
import com.ut.nlSystemAPi.model.response.Uoms.UomsResponse;
import com.ut.nlSystemAPi.model.response.VendorManagement.GeneralLedgerDetailResponse;
import com.ut.nlSystemAPi.model.response.VendorManagement.VendorManagementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Service
public class VendorManagementServiceImpl implements VendorManagementService {
    @Autowired
    private VendorManagementMapper vendorManagementMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Override
    public ResponseMessage<BaseResult> getList(VendorFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Random random = new Random();
            int randomId = 1000 + random.nextInt(9000);
            String tableName = "general_ledger_detail_ven" + randomId;
            vendorManagementMapper.createTable(tableName);

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(vendorManagementMapper.countList(filter, tableName, userId));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());


            List<GeneralLedgerDetailResponse> detailResponses = vendorManagementMapper.getListGeneralLedgerDetail(filter, userId);

            if(!detailResponses.isEmpty()){
                for (GeneralLedgerDetailResponse response : detailResponses) {
                    GeneralLedgerDetail generalLedgerDetail = new GeneralLedgerDetail();
                    generalLedgerDetail.setGeneralLedgerId(response.getGeneralLedgerId());
                    generalLedgerDetail.setDate(response.getDate());
                    generalLedgerDetail.setChartAccountId(response.getVendorId());
                    generalLedgerDetail.setCompanyId(response.getCompanyId());
                    generalLedgerDetail.setVendorId(response.getVendorId());
                    generalLedgerDetail.setLocationId(response.getLocationId());
                    generalLedgerDetail.setCredit(response.getCredit());
                    generalLedgerDetail.setDebit(response.getDebit());
                    generalLedgerDetail.setCustomerId(response.getCustomerId());
                    generalLedgerDetail.setEmployeeId(response.getEmployeeId());
                    Boolean insert = vendorManagementMapper.insertTable(generalLedgerDetail, tableName);

                    if (!insert) {
                        return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
                    }
                }
            }

            List<VendorManagementResponse> vendorManagementResponses = vendorManagementMapper.getList(filter, tableName, userId);

            vendorManagementMapper.dropTable(tableName);


            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/list", null, null, "vendorManagement", "vendorManagement(View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", vendorManagementResponses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/list", line, error.toString(), "vendorManagement", "vendorManagement(View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(false, messageService.message("Error", null, false));
        }
    }

        @Override
        public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
            LocalTime startDuration = LocalTime.now();
            Long line = 1033L;
            try {
                // Check Permission
                Long userId = userService.getUserAuth().getId();
                //      if (permissionMapper.checkPermission(userId, "System Role (View)") == 0) {
                //        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
                //      }

                List<VendorManagementResponse> responses = vendorManagementMapper.getOne(id);
                if (responses.size() > 0) {
                    for (int i = 0; i < responses.size(); i++) {
                        responses.get(i).setPhoto(vendorManagementMapper.getPhoto(responses.get(i).getId()));

                        responses.get(i).setCompanies(vendorManagementMapper.getVendorCompany(responses.get(i).getId()));

                        responses.get(i).setContacts(vendorManagementMapper.getVendorContact(responses.get(i).getId()));

                        responses.get(i).setCurrencies(vendorManagementMapper.getVendorCurrencies(responses.get(i).getId()));

                        responses.get(i).setVendorGroups(vendorManagementMapper.getVendorVgroup(responses.get(i).getId()));
                    }
                }
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/vendorManagement/find/{id}", null, null, "System vendorManagement", "System vendorManagement (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
            } catch (Exception error) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/vendorManagement/find/{id}", line, error.toString(), "System vendorManagement", "System vendorManagement (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
            }
        }

    @Override
    public ResponseMessage<BaseResult> insert(VendorManagementRequest vendorManagementRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "Employee (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
            // Check Duplicate
            if(vendorManagementMapper.checkDuplicate(vendorManagementRequest.getName(), null) > 0){
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }
            // Check Data
            VendorManagement vendorManagement = new VendorManagement();
            vendorManagement.setVendorCode(vendorManagementRequest.getVendorCode());
            vendorManagement.setWorkTelephone(vendorManagementRequest.getWorkTelephone());
            vendorManagement.setEmailAddress(vendorManagementRequest.getEmailAddress());
            vendorManagement.setName(vendorManagementRequest.getName());
            vendorManagement.setContactTelephone(vendorManagementRequest.getOtherTelephone());
            vendorManagement.setAddress(vendorManagementRequest.getAddress());
            vendorManagement.setCountryId(vendorManagementRequest.getCountryId());
            vendorManagement.setFaxNumber(vendorManagementRequest.getFaxNumber());
            vendorManagement.setVatId(vendorManagementRequest.getVatId());
            vendorManagement.setPaymentTermId(vendorManagementRequest.getPaymentTermId());
            vendorManagement.setPhoto(vendorManagementRequest.getPhoto().getUrl());
            vendorManagement.setPhotoName(vendorManagementRequest.getPhoto().getName());
            vendorManagement.setIsActive(1);
            vendorManagement.setCreatedBy(userId);
            Boolean result = vendorManagementMapper.insert(vendorManagement);
            if (result) {
                List<Long> companies = vendorManagementRequest.getCompanies();
                for(int i=0;i<companies.size();i++){
                    VendorCompany vendorCompany = new VendorCompany();
                    vendorCompany.setCompanyId(vendorManagementRequest.getCompanies().get(i));
                    vendorCompany.setVendorId(vendorManagement.getId());
                    vendorManagementMapper.insertVendorCompany(vendorCompany);
                }

                List<Long> groups = vendorManagementRequest.getVendorGroups();
                for(int i=0;i<groups.size();i++){
                    VendorGroup vendorVgroup = new VendorGroup();
                    vendorVgroup.setId(vendorManagementRequest.getVendorGroups().get(i));
                    vendorVgroup.setVendorId(vendorManagement.getId());
                    vendorManagementMapper.insertVendorVgroup(vendorVgroup);
                }

                List<Long> contacts = vendorManagementRequest.getContacts();
                for(int i=0;i<contacts.size();i++){
                    VendorContact vendorContact = new VendorContact();
                    vendorContact.setId(vendorManagementRequest.getContacts().get(i));
                    vendorContact.setVendorId(vendorManagement.getId());
                    vendorManagementMapper.insertVendorContact(vendorContact);
                }

                List<Long> currencies = vendorManagementRequest.getCurrencies();
                for(int i=0;i<currencies.size();i++){
                    CurrencyInformation vendorCurrencies = new CurrencyInformation();
                    vendorCurrencies.setVendorId(vendorManagement.getId());
                    vendorCurrencies.setCurrencyId(vendorManagementRequest.getCurrencies().get(i));
                    vendorCurrencies.setIsActive(1);
                    vendorManagementMapper.insertVendorCurrencies(vendorCurrencies);
                }

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/vendorManagement/add", null, null, "vendorManagement", "vendorManagement (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/add", line, error.toString(), "vendorManagement", "vendorManagement (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
    @Override
    public ResponseMessage<BaseResult> update(VendorManagementUpdateRequest vendorManagementUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            VendorManagement  vendorManagement = new VendorManagement();
            vendorManagement.setId(vendorManagementUpdateRequest.getId());
            vendorManagement.setVendorCode(vendorManagementUpdateRequest.getVendorCode());
            vendorManagement.setWorkTelephone(vendorManagementUpdateRequest.getWorkTelephone());
            vendorManagement.setEmailAddress(vendorManagementUpdateRequest.getEmailAddress());
            vendorManagement.setName(vendorManagementUpdateRequest.getName());
            vendorManagement.setContactTelephone(vendorManagementUpdateRequest.getOtherTelephone());
            vendorManagement.setAddress(vendorManagementUpdateRequest.getAddress());
            vendorManagement.setCountryId(vendorManagementUpdateRequest.getCountryId());
            vendorManagement.setFaxNumber(vendorManagementUpdateRequest.getFaxNumber());
            vendorManagement.setVatId(vendorManagementUpdateRequest.getVatId());
            vendorManagement.setPaymentTermId(vendorManagementUpdateRequest.getPaymentTermId());
            vendorManagement.setPhoto(vendorManagementUpdateRequest.getPhoto().getUrl());
            vendorManagement.setPhotoName(vendorManagementUpdateRequest.getPhoto().getName());
            vendorManagement.setModifiedBy(userId);
            Boolean result = vendorManagementMapper.update(vendorManagement);
            LocalTime endDuration = LocalTime.now();

            if (result) {
                vendorManagementMapper.deleteVendorCompany(vendorManagementUpdateRequest.getId());
                List<Long> companies = vendorManagementUpdateRequest.getCompanies();
                for(int i=0;i<companies.size();i++){
                    VendorCompany vendorCompany=new VendorCompany();
                    vendorCompany.setCompanyId(vendorManagementUpdateRequest.getCompanies().get(i));
                    vendorCompany.setVendorId(vendorManagement.getId());
                    vendorManagementMapper.insertVendorCompany(vendorCompany);
                }

                vendorManagementMapper.deleteVendorVgroup(vendorManagementUpdateRequest.getId());
                List<Long> groups = vendorManagementUpdateRequest.getVendorGroups();
                for(int i=0;i<groups.size();i++){
                    VendorGroup vendorVgroup=new VendorGroup();
                    vendorVgroup.setId(vendorManagementUpdateRequest.getVendorGroups().get(i));
                    vendorVgroup.setVendorId(vendorManagement.getId());
                    vendorManagementMapper.insertVendorVgroup(vendorVgroup);
                }

                List<Long> contacts = vendorManagementUpdateRequest.getContacts();
                for(int i=0;i<contacts.size();i++){
                    VendorContact vendorContact = new VendorContact();
                    vendorContact.setId(vendorManagementUpdateRequest.getContacts().get(i));
                    vendorContact.setVendorId(vendorManagement.getId());
                    vendorManagementMapper.insertVendorContact(vendorContact);
                }

                vendorManagementMapper.deleteVendorCurrencies(vendorManagementUpdateRequest.getId());
                List<Long> currencies = vendorManagementUpdateRequest.getCurrencies();
                for(int i=0;i<currencies.size();i++){
                    CurrencyInformation vendorCurrencies = new CurrencyInformation();
                    vendorCurrencies.setVendorId(vendorManagement.getId());
                    vendorCurrencies.setCurrencyId(vendorManagementUpdateRequest.getCurrencies().get(i));
                    vendorCurrencies.setIsActive(1);
                    vendorManagementMapper.insertVendorCurrencies(vendorCurrencies);
                }
                activityLogService.insert("/vendorManagement/update", null, null, "vendorManagement", "vendorManagement (Update)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            /* System Activity */
            activityLogService.insert("/vendorManagement/update", line, error.toString(), "vendorManagement", "vendorManagement (Update)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
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
//      if (permissionMapper.checkPermission(userId, "System Role (Delete)") == 0) {
//        return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//      }

            Boolean result = vendorManagementMapper.delete(id, userId);
            if (result) {
                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/vendorManagement/delete/{id}",null,null,"vendorManagement","vendorManagement (Delete)","Delete",1,"Success",startDuration,endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/vendorManagement/delete/{id}",line, error.toString(),"vendorManagement","vendorManagement (Delete)","Delete",2,"Error",startDuration,endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }


}
