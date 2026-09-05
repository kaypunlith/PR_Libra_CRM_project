package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.entity.Organization.CustomerHistoryPrintTracking;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CustomerHistoryPrintTrackingMapper {
    void insert(CustomerHistoryPrintTracking tracking);
    
    void updateStatus(@Param("type") Integer type, @Param("referenceId") Long referenceId);
    
    CustomerHistoryPrintTracking findByTypeAndReferenceId(@Param("type") Integer type, @Param("referenceId") Long referenceId);
}
