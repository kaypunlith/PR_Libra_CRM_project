package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.CustomerContact.CustomerContact;
import com.ut.nlSystemAPi.model.filter.CustomerContactFilter;
import com.ut.nlSystemAPi.model.response.CustomerContact.CustomerContactDetailResponse;
import com.ut.nlSystemAPi.model.response.CustomerContact.CustomerContactResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerContactMapper {

    List<CustomerContactResponse> getList(@Param("filter") CustomerContactFilter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") CustomerContactFilter filter, @Param("employeeId") Long employeeId, @Param("userId") Long userId);

    Long getEmployeeId(@Param("userId") Long userId);

    List<CustomerContactResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("customerContact") CustomerContact customerContact);

    Boolean update(@Param("customerContact") CustomerContact customerContact);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insertDescription(@Param("customerContactId") Long customerContactId, @Param("typeId") Long typeId, @Param("description") String description);

    Boolean insertProgress(@Param("customerContactId") Long customerContactId, @Param("id") Long id, @Param("percent") Double percent);

    Boolean insertContactList(@Param("customerContactId") Long customerContactId, @Param("id") Long id);

    List<CustomerContactDetailResponse> getDescription(@Param("customerContactId") Long customerContactId);

    List<CustomerContactDetailResponse> getProgress(@Param("customerContactId") Long customerContactId);

    List<CustomerContactDetailResponse> getContactList(@Param("customerContactId") Long customerContactId);

    Boolean deleteDescription(@Param("customerContactId") Long customerContactId);

    Boolean deleteProgress(@Param("customerContactId") Long customerContactId);

    Boolean deleteContactList(@Param("customerContactId") Long customerContactId);

    Boolean insertNps(@Param("customerContactId") Long customerContactId, @Param("score") Long score, @Param("userId") Long userId);

    Boolean apply(@Param("id") Long id);
}
