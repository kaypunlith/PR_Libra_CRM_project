package com.ut.nlSystemAPi.model.notification;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NotificationRecipientRequest implements Serializable {

  @ApiModelProperty(position = 1, hidden = true)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long notificationId;

  @ApiModelProperty(position = 3)
  private Long recipientId;
}