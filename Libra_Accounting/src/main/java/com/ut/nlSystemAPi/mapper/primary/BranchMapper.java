package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.BanksAccount;
import com.ut.nlSystemAPi.model.Branch;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.Branch.AvailableUserResponse;
import com.ut.nlSystemAPi.model.response.Branch.BankAccountResponse;
import com.ut.nlSystemAPi.model.response.Branch.BranchResponse;
import com.ut.nlSystemAPi.model.response.Branch.WarehouseResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchMapper {

    List<BranchResponse> getList(@Param("filter") Filter filter);

    List<BankAccountResponse> getBankAccount(@Param("branchId") Long branchId);

    List<AvailableUserResponse> getAvailableUsers(@Param("branchId") Long branchId);

    List<WarehouseResponse> getAvailableWareHouse(@Param("branchId") Long branchId);


    Long countList(@Param("filter") Filter filter);

    List<BranchResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("branch") Branch branch);

    Boolean insertUserAvailable(@Param("userAvailableId") Long userAvailableId,@Param("branchId") Long branchId,@Param("userId") Long userId);

    Boolean insertLocationAvailable(@Param("locationId") Long locationId,@Param("branchId") Long branchId,@Param("userId") Long userId);

    Boolean insertBranchAccount(@Param("branch") Branch branch);

    Boolean update(@Param("branch") Branch branch);


    Boolean updateBankAccount (@Param("branchId") Long  branchId,@Param("userId") Long userId);


    List<BranchResponse> getBranchAccount();

    Boolean  updateBranchAccount (@Param("branchId") Long  branchId,@Param("userId") Long userId);

    Boolean  updateUserAvailable (@Param("branchId") Long  branchId,@Param("userId") Long userId);

    Boolean  updateWorkLocationAvailable (@Param("branchId") Long  branchId,@Param("userId") Long userId);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
