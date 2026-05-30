package com.ut.nlSystemAPi.mapper.freedom;

import com.ut.nlSystemAPi.mapper.primary.CodeCountMapper;
import com.ut.nlSystemAPi.model.Module;
import com.ut.nlSystemAPi.model.ModuleType;
import com.ut.nlSystemAPi.model.Role;
import com.ut.nlSystemAPi.model.RoleUser;
import com.ut.nlSystemAPi.model.Users.User;
import com.ut.nlSystemAPi.model.Users.UserGroupList;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.Dropdown.GroupApiDropdownResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.ModuleTypeDropdownResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.WarehouseDropDownResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.VendorDropdownResponse;
import com.ut.nlSystemAPi.model.response.Role.RoleResponse;
import com.ut.nlSystemAPi.model.response.User.UserResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FreedomMapper extends CodeCountMapper {

    Long countRecords(@Param("table") String table, @Param("field") String field, @Param("pattern") String pattern, @Param("status") String status);

    Long checkTableExists(@Param("tableName") String tableName);

    // Stock Tables
    Boolean createGroupTotals(@Param("customerId") Long customerId);
    Boolean createGroupTotalDetails(@Param("customerId") Long customerId);
    Boolean createInventories(@Param("customerId") Long customerId);
    Boolean createInventoryTotals(@Param("customerId") Long customerId);
    Boolean createInventoryTotalDetails(@Param("customerId") Long customerId);
    Boolean upsertCustomerWarehouse(@Param("w") Map<String, Object> warehouse);
    Boolean upsertCustomerLocation(@Param("l") Map<String, Object> location);
    Boolean upsertCustomerBranch(@Param("b") Map<String, Object> branch);
    Boolean deleteCustomerWarehouse(@Param("id") Long id, @Param("userId") Long userId);
    Boolean deleteCustomerLocation(@Param("id") Long id, @Param("userId") Long userId);
    Boolean deleteCustomerBranch(@Param("id") Long id, @Param("userId") Long userId);
    Map<String, Object> getCustomerBranchModuleCodes(@Param("id") Long id);

    // Customer
    Boolean insertCustomer(@Param("c") Map<String, Object> customer);
    Boolean updateCustomer(@Param("c") Map<String, Object> customer);
    Boolean deleteCustomer(@Param("id") Long id, @Param("userId") Long userId);
    Boolean deleteCustomerLocationGroups(@Param("locationGroupId") Long locationGroupId);
    Boolean insertCustomerLocationGroup(@Param("clg") Map<String, Object> customerLocationGroup);

    Boolean insertCustomerCompanies(@Param("org") Map<String, Object> org);
    Boolean insertCustomerCgroups(@Param("org") Map<String, Object> org);
    Boolean insertCustomerContact(@Param("org") Map<String, Object> org);
    Boolean insertCustomerContracts(@Param("org") Map<String, Object> org);
    Boolean insertCustomerTerminates(@Param("org") Map<String, Object> org);
    Boolean insertCustomerSurveys(@Param("org") Map<String, Object> org);
    Boolean insertDivisionInformation(@Param("div") Map<String, Object> div);
    Boolean insertDivisionInformationDetail(@Param("div") Map<String, Object> div);
    Boolean deleteCustomerCompanies(@Param("organizationId") Long organizationId);
    Boolean deleteCustomerCgroups(@Param("organizationId") Long organizationId);
    Boolean deleteCustomerContacts(@Param("organizationId") Long organizationId);
    Boolean deleteCustomerContracts(@Param("organizationId") Long organizationId);
    Boolean deleteCustomerTerminates(@Param("organizationId") Long organizationId);
    Boolean deleteCustomerSurveys(@Param("organizationId") Long organizationId);
    Boolean deleteDivisionInformation(@Param("organizationId") Long organizationId);
    Boolean deleteDivisionInformationDetail(@Param("organizationId") Long organizationId);

    // Customer Contact
    Boolean insertCustomerContactData(@Param("cc") Map<String, Object> customerContact);
    Boolean updateCustomerContactData(@Param("cc") Map<String, Object> customerContact);
    Boolean deleteCustomerContactData(@Param("id") Long id, @Param("userId") Long userId);
    Boolean insertCustomerContactDescription(@Param("ccd") Map<String, Object> description);
    Boolean deleteCustomerContactDescriptions(@Param("customerContactId") Long customerContactId);
    Boolean insertCustomerContactProgress(@Param("ccp") Map<String, Object> progress);
    Boolean deleteCustomerContactProgress(@Param("customerContactId") Long customerContactId);
    Boolean insertCustomerContactList(@Param("ccl") Map<String, Object> contactList);
    Boolean deleteCustomerContactLists(@Param("customerContactId") Long customerContactId);
    Boolean insertCustomerContactNps(@Param("nps") Map<String, Object> nps);
    Boolean applyCustomerContact(@Param("id") Long id);

    // Customer Group
    Boolean insertCustomerGroup(@Param("cg") Map<String, Object> customerGroup);
    Boolean updateCustomerGroup(@Param("cg") Map<String, Object> customerGroup);
    Boolean deleteCustomerGroup(@Param("id") Long id, @Param("userId") Long userId);
    Boolean insertCgroupCompany(@Param("cgc") Map<String, Object> cgroupCompany);
    Boolean deleteCgroupCompanies(@Param("cgroupId") Long cgroupId);
    Boolean insertCgroupEgroup(@Param("cge") Map<String, Object> cgroupEgroup);
    Boolean deleteCgroupEgroups(@Param("cgroupId") Long cgroupId);
    Boolean insertCgroupPriceType(@Param("cgp") Map<String, Object> cgroupPriceType);
    Boolean deleteCgroupPriceTypes(@Param("cgroupId") Long cgroupId);
    Boolean insertCustomerCgroup(@Param("ccg") Map<String, Object> customerCgroup);
    Boolean deleteCustomerCgroupsByCgroup(@Param("cgroupId") Long cgroupId);

    // Customer Zone
    Boolean insertCustomerZone(@Param("cz") Map<String, Object> zone);
    Boolean updateCustomerZone(@Param("cz") Map<String, Object> zone);
    Boolean deleteCustomerZone(@Param("id") Long id, @Param("userId") Long userId);
    Boolean insertCustomerZoneDetail(@Param("czd") Map<String, Object> detail);
    Boolean deleteCustomerZoneDetails(@Param("zoneId") Long zoneId);

    // Customer Market
    Boolean insertCustomerMarket(@Param("cm") Map<String, Object> market);
    Boolean updateCustomerMarket(@Param("cm") Map<String, Object> market);
    Boolean deleteCustomerMarket(@Param("id") Long id, @Param("userId") Long userId);

    // Business Type
    Boolean insertBusinessType(@Param("bt") Map<String, Object> businessType);
    Boolean updateBusinessType(@Param("bt") Map<String, Object> businessType);
    Boolean deleteBusinessType(@Param("id") Long id, @Param("userId") Long userId);

    // Freedom Type
    Boolean insertFreedomType(@Param("ft") Map<String, Object> freedomType);
    Boolean updateFreedomType(@Param("ft") Map<String, Object> freedomType);
    Boolean deleteFreedomType(@Param("id") Long id, @Param("userId") Long userId);

    // Business Activity
    Boolean insertBusinessActivity(@Param("ba") Map<String, Object> businessActivity);
    Boolean updateBusinessActivity(@Param("ba") Map<String, Object> businessActivity);
    Boolean deleteBusinessActivity(@Param("id") Long id, @Param("userId") Long userId);

    // Uoms
    Boolean insertUom(@Param("u") Map<String, Object> uom);
    Boolean updateUom(@Param("u") Map<String, Object> uom);
    Boolean deleteUom(@Param("id") Long id, @Param("userId") Long userId);

    // Uom Conversion
    Boolean insertUomConversion(@Param("uc") Map<String, Object> uomConversion);
    Boolean updateUomConversion(@Param("uc") Map<String, Object> uomConversion);
    Boolean archiveOtherUomConversions(@Param("id") Long id, @Param("userId") Long userId);
    Boolean archiveUomConversion(@Param("id") Long id, @Param("userId") Long userId);

    // Price Type
    Boolean insertPriceType(@Param("pt") Map<String, Object> priceType);
    Boolean updatePriceType(@Param("pt") Map<String, Object> priceType);
    Boolean deletePriceType(@Param("id") Long id, @Param("userId") Long userId);
    Boolean deletePriceTypeCompany(@Param("priceTypeId") Long priceTypeId);
    Boolean insertPriceTypeCompany(@Param("ptc") Map<String, Object> priceTypeCompany);
    Boolean archivePriceTypePos(@Param("companyId") Long companyId);
    Boolean insertPriceTypePos(@Param("ptp") Map<String, Object> posPriceType);
    Boolean orderingPriceType(@Param("id") Long id, @Param("ordering") Long ordering);

    // Payment Term
    Boolean insertPaymentTerm(@Param("pt") Map<String, Object> paymentTerm);
    Boolean updatePaymentTerm(@Param("pt") Map<String, Object> paymentTerm);
    Boolean deletePaymentTerm(@Param("id") Long id, @Param("userId") Long userId);

    // Product Group
    Boolean insertProductGroup(@Param("pg") Map<String, Object> productGroup);
    Boolean updateProductGroup(@Param("pg") Map<String, Object> productGroup);
    Boolean deleteProductGroup(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insertPgroupCompany(@Param("pc") Map<String, Object> pgroupCompany);
    Boolean deletePgroupCompany(@Param("pgroupId") Long pgroupId);

    Boolean insertUserPgroup(@Param("up") Map<String, Object> userPgroup);
    Boolean deleteUserPgroup(@Param("pgroupId") Long pgroupId);

    Boolean insertPgroupAccount(@Param("pa") Map<String, Object> pgroupAccount);
    Boolean deletePgroupAccount(@Param("pgroupId") Long pgroupId);

    Boolean deleteProductPgroupByPgroup(@Param("pgroupId") Long pgroupId);

    // Product Brand
    Boolean insertProductBrand(@Param("pb") Map<String, Object> productBrand);
    Boolean updateProductBrand(@Param("pb") Map<String, Object> productBrand);
    Boolean deleteProductBrand(@Param("id") Long id, @Param("userId") Long userId);

    // Product Group Price Setting
    Boolean insertPgroupPriceSetting(@Param("pps") Map<String, Object> pgroupPriceSetting);
    Boolean updatePgroupPriceSetting(@Param("pps") Map<String, Object> pgroupPriceSetting);
    Boolean logPgroupPriceSetting(@Param("pgroupId") Long pgroupId, @Param("userId") Long userId);
    Boolean deletePgroupPriceSetting(@Param("id") Long id, @Param("userId") Long userId);
    Boolean insertPgroupPrice(@Param("pp") Map<String, Object> pgroupPrice);
    Boolean archivePgroupPriceBySetting(@Param("pgroupPriceSettingId") Long pgroupPriceSettingId, @Param("userId") Long userId);
    Boolean deleteProductPriceByType(@Param("productId") Long productId, @Param("priceTypeId") Long priceTypeId);

    // Product
    Boolean insertProduct(@Param("p") Map<String, Object> product);
    Boolean updateProduct(@Param("p") Map<String, Object> product);
    Boolean updateProductCost(@Param("p") Map<String, Object> product);
    Boolean deleteProduct(@Param("id") Long id, @Param("userId") Long userId);
    Boolean updateProductActiveStatus(@Param("id") Long id, @Param("isActive") Integer isActive, @Param("userId") Long userId);

    Boolean insertProductPgroup(@Param("pp") Map<String, Object> pp);
    Boolean deleteProductPgroup(@Param("productId") Long productId);

    Boolean insertICSAccount(@Param("ics") Map<String, Object> ics);
    Boolean deleteICSAccount(@Param("productId") Long productId);

    Boolean insertProductCategory(@Param("pc") Map<String, Object> pc);
    Boolean deleteProductCategory(@Param("productId") Long productId);

    Boolean insertProductSku(@Param("ps") Map<String, Object> ps);
    Boolean deleteProductSku(@Param("productId") Long productId);

    Boolean insertProductPacket(@Param("pp") Map<String, Object> ppkt);
    Boolean deleteProductPacket(@Param("productId") Long productId);

    Boolean insertProductStockLevel(@Param("psl") Map<String, Object> psl);
    Boolean archiveProductStockLevel(@Param("productId") Long productId, @Param("userId") Long userId);

    Boolean deletePrice(@Param("productId") Long productId);
    Boolean addPrice(@Param("productPrice") Map<String, Object> price);
    Boolean addPriceHistory(@Param("productPriceHistory") Map<String, Object> priceHistory);

    // Purchase Bill (Delivery → Freedom)
    Boolean insertPurchaseBill(@Param("pb") Map<String, Object> purchaseBill);
    Boolean insertPurchaseBillDetail(@Param("pbd") Map<String, Object> detail);
    Boolean insertPurchaseBillService(@Param("pbs") Map<String, Object> service);
    Boolean insertPurchaseBillMisc(@Param("pbm") Map<String, Object> misc);
    Long getPurchaseBillIdByInvoiceCode(@Param("invoiceCode") String invoiceCode);
    Boolean archivePurchaseBillById(@Param("id") Long id, @Param("userId") Long userId);

    // Bill Return (Credit Memo → Freedom)
    Boolean insertBillReturn(@Param("br") Map<String, Object> billReturn);
    Boolean insertBillReturnDetail(@Param("brd") Map<String, Object> detail);
    Boolean insertBillReturnService(@Param("brs") Map<String, Object> service);
    Boolean insertBillReturnMisc(@Param("brm") Map<String, Object> misc);
    Long getBillReturnIdByPrCode(@Param("prCode") String prCode);
    Boolean archiveBillReturnById(@Param("id") Long id, @Param("userId") Long userId);

    // GL + Inventory for Purchase Bill / Bill Return
    Boolean insertGeneralLedger(@Param("gl") Map<String, Object> generalLedger);
    Boolean insertGeneralLedgerDetail(@Param("gld") Map<String, Object> detail);
    Boolean insertInventoryValuation(@Param("iv") Map<String, Object> valuation);

    List<VendorDropdownResponse> getListFreedomCustomer(@Param("filter") Filter filter);
    List<VendorDropdownResponse> getListFreedomWarehouse(@Param("filter") Filter filter);
    List<GroupApiDropdownResponse> getListFreedomGroupApi(@Param("filter") Filter filter);
    List<ModuleTypeDropdownResponse> getListFreedomModuleType(@Param("filter") Filter filter);
    List<VendorDropdownResponse> getCustomerLocationGroupCustomers(@Param("locationGroupId") Long locationGroupId);

    List<RoleResponse> getRoleFreedomList(@Param("filter") Filter filter);
    Long countRoleFreedomList(@Param("filter") Filter filter);
    List<RoleResponse> getRoleFreedomOne(@Param("id") Long id);
    List<RoleUser> getRoleFreedomUsers(@Param("id") Long id);
    Long checkRoleFreedomDuplicate(@Param("name") String name, @Param("id") Long id);
    Boolean insertRoleFreedom(@Param("role") Role role);
    Boolean updateRoleFreedom(@Param("role") Role role);
    Boolean deleteRoleFreedom(@Param("id") Long id);
    List<ModuleType> getRoleFreedomModule(@Param("userId") Long userId);
    List<ModuleType> getRoleFreedomModuleTypes(@Param("filter") Filter filter);
    List<Module> getRoleFreedomModuleByModuleTypeId(@Param("id") Long id);
    List<Module> getRoleFreedomModuleByRoleId(@Param("id") Long id, @Param("roleId") Long roleId);
    List<Module> getRoleFreedomModuleByUserId(@Param("id") Long id, @Param("userId") Long userId);
    Boolean insertRoleFreedomUser(@Param("roleId") Long roleId, @Param("userId") Long userId);
    Boolean deleteRoleFreedomUser(@Param("roleId") Long roleId);
    Boolean insertRoleFreedomPermission(@Param("roleId") Long roleId, @Param("moduleId") Long moduleId);
    Boolean deleteRoleFreedomPermission(@Param("roleId") Long roleId);

    List<UserResponse> getListUser(@Param("filter") Filter filter);
    Long countListUser(@Param("filter") Filter filter);
    List<User> getOneUser(@Param("id") Long id);
    List<UserGroupList> getOneUserGroupList(@Param("id") Long id);
    List<User> getOneByUsername(@Param("username") String username);
    List<UserResponse> getOneByUserId(@Param("userId") Long userId);
    Boolean insertUser(@Param("user") User user);
    Boolean updateUser(@Param("user") User user);
    Boolean editProfileUser(@Param("user") User user);
    Boolean deleteUser(@Param("id") Long id);
    Boolean insertSystemRoleUser(@Param("userId") Long userId, @Param("groupId") String groupId);
    Boolean deleteSystemRoleUser(@Param("userId") Long userId);
    List<Long> getUserLocationGroupIds(@Param("userId") Long userId);
    List<WarehouseDropDownResponse> getUserLocationGroups(@Param("userId") Long userId);
    List<Long> getUserCompanyIds(@Param("userId") Long userId);
    List<Long> getActiveCompanyIds();
    Boolean insertUserCompany(@Param("userId") Long userId, @Param("companyId") Long companyId);
    Boolean insertUserLocationGroup(@Param("userId") Long userId, @Param("locationGroupId") Long locationGroupId);
    Boolean deleteUserLocationGroup(@Param("userId") Long userId);
}
