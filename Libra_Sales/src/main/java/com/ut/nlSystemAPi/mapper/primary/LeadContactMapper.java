package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.LeadContact.LeadContact;
import com.ut.nlSystemAPi.model.filter.LeadContactFilter;
import com.ut.nlSystemAPi.model.response.LeadContact.LeadContactResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadContactMapper {

    List<LeadContactResponse> getList(@Param("filter") LeadContactFilter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") LeadContactFilter filter, @Param("userId") Long userId);

    List<LeadContactResponse> getOne(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insert(@Param("leadContact") LeadContact leadContact);

    Boolean update(@Param("leadContact") LeadContact leadContact);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Long checkDuplicate(@Param("contactName") String contactName, @Param("id") Long id);
}
