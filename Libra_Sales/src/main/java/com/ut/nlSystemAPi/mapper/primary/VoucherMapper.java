package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Voucher.Voucher;
import com.ut.nlSystemAPi.model.filter.VoucherFilter;
import com.ut.nlSystemAPi.model.response.Voucher.VoucherResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherMapper {

    List<VoucherResponse> getList(@Param("filter") VoucherFilter filter);

    Long countList(@Param("filter") VoucherFilter filter);

    List<VoucherResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("voucherCode") String voucherCode, @Param("id") Long id);

    Boolean insert(@Param("voucher") Voucher voucher);

    Boolean update(@Param("voucher") Voucher voucher);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
