package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.ActivityLogMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.ActivityLog;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.filter.ActivityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

  @Autowired
  private ActivityLogMapper activityLogMapper;

  @Autowired
  Environment environment;

  @Autowired
  private UserService userService;

  @Autowired
  private ActivityLogService activityLogService;

  @Autowired
  private MessageService messageService;

  @Autowired
  private PermissionMapper permissionMapper;

  @Override
  public void insert(String endpoint, Long line, String bug, String moduleName, String moduleId, String act, int status, String description,LocalTime startDuration, LocalTime endDuration, HttpServletRequest httpServletRequest) throws UnknownHostException {
    // Check User Id
    Long userId = userService.getUserAuth().getId();
    InetAddress addr = InetAddress.getLocalHost();
    String ip = addr.getHostAddress();
    String hostname = addr.getHostName();
    String operSys = System.getProperty("os.name").toLowerCase();
    String userAgent= httpServletRequest.getHeader("user-agent");
    String browserName = "";
    String  browserVer = "";

    if(userAgent.contains("Chrome")){ //checking if Chrome
      String substring=userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0];
      browserName=substring.split("/")[0];
    }
    else if(userAgent.contains("Firefox")){  //Checking if Firefox
      String substring=userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0];
      browserName=substring.split("/")[0];
    }

    Duration d = Duration.between(LocalTime.now(), LocalTime.parse("12:00:12"));

    /*GET module Id*/
    Long getModuleId = activityLogMapper.getModuleId("Payroll (View)");

    /*GET Username*/
    String getUsername = activityLogMapper.getFullName(userId);

    /*Get Duration*/
    Duration duration = Duration.between(startDuration, endDuration);
    Long durationProcess = duration.get(ChronoUnit.SECONDS);

    // Check Data
    ActivityLog activityLog = new ActivityLog();
    activityLog.setEndpoint(endpoint);
    activityLog.setLine(line);
    activityLog.setModule(moduleName);
    activityLog.setModuleId(getModuleId);
    activityLog.setAct(act);
    activityLog.setDescription(description);
    activityLog.setBug(bug);
    activityLog.setBrower(browserName);
    activityLog.setOperatingSystem(operSys);
    activityLog.setIp(ip);
    activityLog.setDuration(durationProcess);
    activityLog.setCreatedBy(userId);
    activityLog.setStatus(status);
    activityLog.setHostName(hostname);
    Boolean result = activityLogMapper.insert(activityLog);
  }

  public ResponseMessage<BaseResult> getList(ActivityFilter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {

      Pagination pagination = new Pagination();
      pagination.setPage(filter.getPage());
      pagination.setRowsPerPage(filter.getRowsPerPage());
      pagination.setTotal(activityLogMapper.countList(filter));
      filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

      List<ActivityLog> activityLogs = activityLogMapper.getList(filter);
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/system-activity/list",null,null,"System Activity","System Activity (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", activityLogs, pagination, true));
    } catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/system-activity/list",line, error.toString(),"System Activity","System Activity (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }

  public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
    LocalTime startDuration = LocalTime.now();
    Long line = 1033L;
    try {
      List<ActivityLog> activityLogs = activityLogMapper.getOne(id);
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/system-activity/find/{id}",null,null,"Costcenter","Costcenter (View)","View",1,"Success",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Success", activityLogs, true));
    }catch (Exception error) {
      /*System Activity*/
      LocalTime endDuration = LocalTime.now();
      activityLogService.insert("/system-activity/find/{id}",line, error.toString(),"Costcenter","Costcenter (View)","View",2,"Error",startDuration,endDuration, httpServletRequest);
      return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
    }
  }
}
