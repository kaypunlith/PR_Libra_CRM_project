package com.ut.nlSystemAPi.mapper.primary;


import com.ut.nlSystemAPi.model.ClassAccount;
import com.ut.nlSystemAPi.model.Company;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.request.Login.ChartOfAccountRequest.AccountClosingDateRequest;
import com.ut.nlSystemAPi.model.response.ClassAccount.ClassAccountResponse;
import com.ut.nlSystemAPi.model.response.User.UserResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountClosingDateMapper {

  List<String> getList();

  Boolean insert(@Param("accountClosingDate") String accountClosingDate, @Param("userId") Long userId);


}