package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.EmailSetting;
import com.ut.nlSystemAPi.model.base.SmsSetting;
import com.ut.nlSystemAPi.model.notification.ApnsProvider;
import org.springframework.stereotype.Repository;


@Repository
public interface SettingMapper {

  EmailSetting emailSetting();

  SmsSetting smsSetting();

  ApnsProvider apnsProvider();
}