package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.Announcement.Announcement;
import com.ut.nlSystemAPi.model.response.Announcement.AnnouncementCustomerResponse;
import com.ut.nlSystemAPi.model.response.Announcement.AnnouncementResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementMapper {

    List<AnnouncementResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<AnnouncementResponse> getOne(@Param("id") Long id);

    List<AnnouncementCustomerResponse> getCustomers(@Param("announcementId") Long announcementId);

    Boolean insert(@Param("announcement") Announcement announcement);

    Boolean insertUserApp(@Param("announcementId") Long announcementId, @Param("userAppId") Long userAppId);

    Boolean insertUserAppsByCustomerIds(@Param("announcementId") Long announcementId, @Param("customerIds") List<Long> customerIds);

    Boolean insertUserAppsByUserAppIds(@Param("announcementId") Long announcementId, @Param("userAppIds") List<Long> userAppIds);

    Boolean insertNotificationsByCustomerIds(@Param("announcementId") Long announcementId, @Param("customerIds") List<Long> customerIds, @Param("createdBy") Long createdBy);

    Boolean insertNotificationsByUserAppIds(@Param("announcementId") Long announcementId, @Param("userAppIds") List<Long> userAppIds, @Param("createdBy") Long createdBy);

    Boolean update(@Param("announcement") Announcement announcement);

    Boolean deleteUserApps(@Param("announcementId") Long announcementId);

    Boolean deleteNotifications(@Param("announcementId") Long announcementId, @Param("modifiedBy") Long modifiedBy);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
