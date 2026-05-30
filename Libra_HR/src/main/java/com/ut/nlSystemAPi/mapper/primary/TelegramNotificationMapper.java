package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.TelegramNotification;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.DropdownResponse;
import com.ut.nlSystemAPi.model.response.TelegramNotificationResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelegramNotificationMapper {

  List<TelegramNotificationResponse> getList(@Param("filter") Filter filter);

  List<TelegramNotificationResponse> getOne(@Param("id") Long id);

  Long checkDuplicate(@Param("chatId") String chatId, @Param("id") Long id);

  Boolean insert(@Param("telegramNotification") TelegramNotification telegramNotification);

  Long countList(@Param("filter") Filter filter);

  Boolean update(@Param("telegramNotification") TelegramNotification telegramNotification);

  Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

  Boolean updateDepartment(@Param("id") Long id, @Param("telegramGroupId") Long telegramGroupId);

  Boolean deleteDepartment(@Param("id") Long id);

  List<DropdownResponse> getDepartment(@Param("id") Long id);

}
