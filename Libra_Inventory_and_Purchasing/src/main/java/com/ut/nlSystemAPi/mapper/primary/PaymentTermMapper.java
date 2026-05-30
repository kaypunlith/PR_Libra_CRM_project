package com.ut.nlSystemAPi.mapper.primary;
import com.ut.nlSystemAPi.model.PaymentTerm;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.PaymentTerm.PaymentTermResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentTermMapper {

  List<PaymentTermResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<PaymentTermResponse> getOne(@Param("id") Long id);

  Boolean insert(@Param("paymentTerm") PaymentTerm paymentTerm);

  Boolean update(@Param("paymentTerm") PaymentTerm paymentTerm);

  Boolean delete(@Param("id")  Long id,@Param("userId") Long userid);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

}