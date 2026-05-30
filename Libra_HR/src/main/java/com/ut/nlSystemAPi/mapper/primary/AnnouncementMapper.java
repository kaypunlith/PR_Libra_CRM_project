package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.Announcement;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.AnnouncementResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementMapper {

    List<AnnouncementResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<AnnouncementResponse> getOne(@Param("id") Long id);

    Boolean insert(@Param("announcement") Announcement announcement);

    Boolean update(@Param("announcement") Announcement announcement);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
