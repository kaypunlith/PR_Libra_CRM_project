package com.ut.nlSystemAPi.model.notification;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NotificationRequest implements Serializable {

  @ApiModelProperty(position = 1, hidden = true)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long type;

  @ApiModelProperty(position = 3)
  private Long employeeAttendanceId;

  @ApiModelProperty(position = 4)
  private Long announcementId;

  @ApiModelProperty(position = 5)
  private Long leaveRequestId;

  @ApiModelProperty(position = 6)
  private Long leaveRequestStatus;

  @ApiModelProperty(position = 7)
  private Long userId;

  @ApiModelProperty(position = 8)
  private List<NotificationRecipientRequest> notificationRecipientRequests;
}