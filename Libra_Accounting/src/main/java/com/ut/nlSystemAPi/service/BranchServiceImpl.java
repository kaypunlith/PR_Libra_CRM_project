package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.BranchMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.Branch;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.request.Login.Branch.BranchRequest;
import com.ut.nlSystemAPi.model.request.Login.Branch.BranchUpdateRequest;
import com.ut.nlSystemAPi.model.response.Branch.AvailableUserResponse;
import com.ut.nlSystemAPi.model.response.Branch.BankAccountResponse;
import com.ut.nlSystemAPi.model.response.Branch.BranchResponse;
import com.ut.nlSystemAPi.model.response.Branch.WarehouseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchMapper branchMapper;

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
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(branchMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<BranchResponse> branchResponses = branchMapper.getList(filter);
            if(branchResponses.size()>0){
                for(int i=0;i<branchResponses.size();i++){
                    List<BankAccountResponse> bankAccountResponses = branchMapper.getBankAccount(branchResponses.get(i).getId());
                    branchResponses.get(i).setBankAccountResponses(bankAccountResponses);
                }
            }

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branch/list", null, null, "branch", " branch (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", branchResponses, pagination, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branch/list", 1033L, error.toString(), "branch", "branch (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (View)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            List<BranchResponse> branchResponses  = branchMapper.getOne(id);
            if(branchResponses.size()>0){
                for(int i=0;i<branchResponses.size();i++){
                    List<BankAccountResponse> bankAccountResponses = branchMapper.getBankAccount(branchResponses.get(i).getId());
                    branchResponses.get(i).setBankAccountResponses(bankAccountResponses);

                    List<AvailableUserResponse> availableUserResponses = branchMapper.getAvailableUsers(branchResponses.get(i).getId());
                    branchResponses.get(i).setAvailableUserResponses(availableUserResponses);

                    List<WarehouseResponse> warehouseResponses = branchMapper.getAvailableWareHouse(branchResponses.get(i).getId());
                    branchResponses.get(i).setWarehouseResponses(warehouseResponses);

                }
            }

            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branch/find/" + id, null, null, "branch", "branch (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", branchResponses, true));
        } catch (Exception error) {
            // System Activity
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branch/find/" + id, 1033L, error.toString(), "branch", "branch (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> insert(BranchRequest branchRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (Add)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
//
            // Check for duplicate name
            if (branchMapper.checkDuplicate(branchRequest.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Branch Name!", false));
            }

            Branch branch = new Branch();
            branch.setCompanyId(branchRequest.getCompanyId());
            branch.setBranchTypeId(branchRequest.getBranchTypeId());
            branch.setCode(branchRequest.getCode());
            branch.setName(branchRequest.getName());
            branch.setNameKh(branchRequest.getNameKh());
            branch.setPhotoUrl(branchRequest.getPhotoUrl());
            branch.setPhotoName(branchRequest.getPhotoName());
            branch.setAbbr(branchRequest.getAbbr());
            branch.setTelephone(branchRequest.getTelephone());
            branch.setFax(branchRequest.getFax());
            branch.setEmail(branchRequest.getEmail());
            branch.setStartWorkHour(branchRequest.getStartWorkHour());
            branch.setEndWorkHour(branchRequest.getEndWorkHour());
            branch.setCountryId(branchRequest.getCountryId());
            branch.setLats(branchRequest.getLats());
            branch.setLongs(branchRequest.getLongs());
            branch.setAdjCode(branchRequest.getAdjCode());
            branch.setProductPriceListCode(branchRequest.getProductPriceListCode());
            branch.setPriceRequestNoCode(branchRequest.getPriceRequestNoCode());
            branch.setTransferCode(branchRequest.getTransferCode());
            branch.setTransferReceiveCode(branchRequest.getTransferReceiveCode());
            branch.setPurchaseOrderCode(branchRequest.getPurchaseOrderCode());
            branch.setExpenseRequestCode(branchRequest.getExpenseRequestCode());
            branch.setQuotationCode(branchRequest.getQuotationCode());
            branch.setSaleOrderCode(branchRequest.getSaleOrderCode());
            branch.setPurchaseBillReceiptCode(branchRequest.getPurchaseBillReceiptCode());
            branch.setInvoiceCode(branchRequest.getInvoiceCode());
            branch.setInvoiceReceiptCode(branchRequest.getInvoiceReceiptCode());
            branch.setDnCode(branchRequest.getDnCode());
            branch.setPurchaseOrderReceiptCode(branchRequest.getPurchaseOrderReceiptCode());
            branch.setCreditMemoCode(branchRequest.getCreditMemoCode());
            branch.setCreditReceiptCode(branchRequest.getCreditReceiptCode());
            branch.setPurchaseBillCode(branchRequest.getPurchaseBillCode());
            branch.setPurchaseBillReceiptCode(branchRequest.getPurchaseBillReceiptCode());
            branch.setBillReturnCode(branchRequest.getBillReturnCode());
            branch.setBillReceiptCode(branchRequest.getBillReceiptCode());
            branch.setBomCode(branchRequest.getBomCode());
            branch.setGoodReceiptNoteCode(branchRequest.getGoodReceiptNoteCode());
            branch.setLandedCostCode(branchRequest.getLandedCostCode());
            branch.setJournalEntryCode(branchRequest.getJournalEntryCode());
            branch.setAddress(branchRequest.getAddress());
            branch.setAddressKh(branchRequest.getAddressKh());
            branch.setRequestStockCode(branchRequest.getRequestStockCode());
            branch.setReceivePaymentCode(branchRequest.getReceivePaymentCode());
            branch.setReceivePaymentOrgCode(branchRequest.getReceivePaymentCode());
            branch.setReceivePaymentEmpCode(branchRequest.getReceivePaymentEmpCode());
            branch.setPurchaseBillReceiptCode(branchRequest.getPurchaseBillReceiptCode());
            branch.setPayBillCode(branchRequest.getPaybillsCode());
            branch.setPayJournalCode(branchRequest.getPaybillsJournalCode());
            branch.setCreatedBy(userId);

            Boolean result = branchMapper.insert(branch);
            for(int i=0;i<branchRequest.getBankAccounts().size();i++){
                branch.setId(branch.getId());
                branch.setAccountNumber(branchRequest.getBankAccounts().get(i).getAccountNumber());
                branch.setAccountHolder(branchRequest.getBankAccounts().get(i).getAccountHolder());
                branch.setBank(branchRequest.getBankAccounts().get(i).getBank());
                branch.setAddress(branchRequest.getBankAccounts().get(i).getAddress());
                branchMapper.insertBranchAccount(branch);
            }
            for(int i=0;i<branchRequest.getAvailableUser().size();i++) {
                branchMapper.insertUserAvailable(branchRequest.getAvailableUser().get(i),branch.getId(),userId);
            }
            List<Long> availableWareHouse = branchRequest.getAvailableWareHouse();
            for(int i=0;i<availableWareHouse.size();i++) {
                branchMapper.insertLocationAvailable(branchRequest.getAvailableWareHouse().get(i),branch.getId(),userId);
            }

            if (result) {
                // System Activity
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/branch/add", null, null, "branch", "branch (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            // System Activity for Error
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branch/add", 1033L, error.toString(), "branch", "branch (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> update(BranchUpdateRequest branchUpdateRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (Edit)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }
//            // Check Duplicate name
            if (branchMapper.checkDuplicate(branchUpdateRequest.getName(), branchUpdateRequest.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate name of  company", false));
            }
            // Create new Taxation object and set properties
            Branch branch = new Branch();
            branch.setId(branchUpdateRequest.getId());
            branch.setCompanyId(branchUpdateRequest.getCompanyId());
            branch.setBranchTypeId(branchUpdateRequest.getBranchTypeId());
            branch.setCode(branchUpdateRequest.getCode());
            branch.setName(branchUpdateRequest.getName());
            branch.setNameKh(branchUpdateRequest.getNameKh());
            branch.setAbbr(branchUpdateRequest.getAbbr());
            branch.setTelephone(branchUpdateRequest.getTelephone());
            branch.setFax(branchUpdateRequest.getFax());
            branch.setEmail(branchUpdateRequest.getEmail());
            branch.setStartWorkHour(branchUpdateRequest.getStartWorkHour());
            branch.setEndWorkHour(branchUpdateRequest.getEndWorkHour());
            branch.setCountryId(branchUpdateRequest.getCountryId());
            branch.setLats(branchUpdateRequest.getLats());
            branch.setLongs(branchUpdateRequest.getLongs());
            branch.setAdjCode(branchUpdateRequest.getAdjCode());
            branch.setProductPriceListCode(branchUpdateRequest.getProductPriceListCode());
            branch.setPriceRequestNoCode(branchUpdateRequest.getPriceRequestNoCode());
            branch.setTransferCode(branchUpdateRequest.getTransferCode());
            branch.setTransferReceiveCode(branchUpdateRequest.getTransferReceiveCode());
            branch.setPurchaseOrderCode(branchUpdateRequest.getPurchaseOrderCode());
            branch.setExpenseRequestCode(branchUpdateRequest.getExpenseRequestCode());
            branch.setQuotationCode(branchUpdateRequest.getQuotationCode());
            branch.setInvoiceCode(branchUpdateRequest.getInvoiceCode());
            branch.setInvoiceReceiptCode(branchUpdateRequest.getInvoiceReceiptCode());
            branch.setDnCode(branchUpdateRequest.getDnCode());
            branch.setCreditMemoCode(branchUpdateRequest.getCreditMemoCode());
            branch.setCreditReceiptCode(branchUpdateRequest.getCreditReceiptCode());
            branch.setPurchaseBillCode(branchUpdateRequest.getPurchaseBillCode());
            branch.setPurchaseBillReceiptCode(branchUpdateRequest.getPurchaseBillReceiptCode());
            branch.setBillReturnCode(branchUpdateRequest.getBillReturnCode());
            branch.setBillReceiptCode(branchUpdateRequest.getBillReceiptCode());
            branch.setBomCode(branchUpdateRequest.getBomCode());
            branch.setGoodReceiptNoteCode(branchUpdateRequest.getGoodReceiptNoteCode());
            branch.setLandedCostCode(branchUpdateRequest.getLandedCostCode());
            branch.setJournalEntryCode(branchUpdateRequest.getJournalEntryCode());
            branch.setReceivePaymentCode(branchUpdateRequest.getReceivePaymentCode());
            branch.setReceivePaymentOrgCode(branchUpdateRequest.getReceivePaymentCode());
            branch.setReceivePaymentEmpCode(branchUpdateRequest.getReceivePaymentEmpCode());
            branch.setPayBillCode(branchUpdateRequest.getPaybillsCode());
            branch.setPayJournalCode(branchUpdateRequest.getPaybillsJournalCode());
            branch.setCreatedBy(userId);

            boolean result = branchMapper.update(branch);

            System.out.println("UpdateEd");

            if (result) {
                if (branchUpdateRequest.getBankAccounts().size() > 0) {
                    branchMapper.updateBankAccount(branchUpdateRequest.getId(), userId);
                    for (int i = 0; i < branchUpdateRequest.getBankAccounts().size(); i++) {
                        //insert new Account branch
                        branch.setId(branchUpdateRequest.getId());
                        branch.setAccountNumber(branchUpdateRequest.getBankAccounts().get(i).getAccountNumber());
                        branch.setAccountHolder(branchUpdateRequest.getBankAccounts().get(i).getAccountHolder());
                        branch.setBank(branchUpdateRequest.getBankAccounts().get(i).getBank());
                        branch.setAddress(branchUpdateRequest.getBankAccounts().get(i).getAddress());
                        branchMapper.insertBranchAccount(branch);
                    }
                }
            }
            System.out.println("Testing");
            if (branchUpdateRequest.getAvailableUser().size() > 0) {
                System.out.println("branchUpdateRequest.getId(): " + branchUpdateRequest.getId());
                branchMapper.updateUserAvailable(branchUpdateRequest.getId(), userId);

                for (int i = 0; i < branchUpdateRequest.getAvailableUser().size(); i++) {
                    branchMapper.insertUserAvailable(branchUpdateRequest.getAvailableUser().get(i), branchUpdateRequest.getId(), userId);
                }
            }
            System.out.println("Testing02");
            System.out.println("branchUpdateRequest.getId(): " + branchUpdateRequest.getAvailableWareHouse().size());
            if (branchUpdateRequest.getAvailableWareHouse().size() > 0) {
                List<Long> availableWareHouse = branchUpdateRequest.getAvailableWareHouse();
                branchMapper.updateWorkLocationAvailable(branchUpdateRequest.getId(), userId);
                for (int i = 0; i < availableWareHouse.size(); i++) {
                    branchMapper.insertLocationAvailable(branchUpdateRequest.getAvailableWareHouse().get(i), branchUpdateRequest.getId(), userId);
                }

                System.out.println("Testing03");
                // System Activity
                LocalTime endDuration = LocalTime.now();
                 activityLogService.insert("/branch/update", null, null, "branch", "branch (Edit)", "Update", 1, "Success", startDuration, endDuration, httpServletRequest);
            }
        } catch (Exception error) {
            System.out.println("error: " + error.getMessage());
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branch/update", 1033L, error.toString(), "branch", "branch (Edit)", "Update", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error occurred during update", false));
        }
        return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
//            if (permissionMapper.checkPermission(userId, "VAT Setting (Delete)") == 0) {
//                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
//            }

            Boolean result = branchMapper.delete(id, userId);
            LocalTime endDuration = LocalTime.now();

            // System Activity
            activityLogService.insert("/branch/delete/{id}", null, null, "branch", "branch (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);

            if (result) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (RuntimeException error) {
            // System Activity for Error
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/branch/delete/{id}", 1033L, error.toString(), "branch", "branch(Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

}
