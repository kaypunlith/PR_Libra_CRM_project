package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.StaffLoan;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.StaffLoan.StaffLoanResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffLoanMapper {

    List<StaffLoanResponse> getList(@Param("filter") Filter filter);

    int countList(@Param("filter") Filter filter);

    List<StaffLoanResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("staffLoan") StaffLoan staffLoan);

    Boolean update(@Param("staffLoan") StaffLoan staffLoan);

    Boolean updateStatus(@Param("staffLoan") StaffLoan staffLoan);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
