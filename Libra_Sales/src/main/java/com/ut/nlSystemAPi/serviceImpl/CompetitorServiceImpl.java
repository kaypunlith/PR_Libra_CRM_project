package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.helper.Telegram.CheckNull;
import com.ut.nlSystemAPi.helper.TelegramUtils;
import com.ut.nlSystemAPi.mapper.primary.*;
import com.ut.nlSystemAPi.model.base.*;
import com.ut.nlSystemAPi.model.entity.Competitor.Competitor;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.request.Competitor.CompetitorRequest;
import com.ut.nlSystemAPi.model.request.Competitor.CompetitorUpdateRequest;
import com.ut.nlSystemAPi.model.response.Competitor.CompetitorResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.UserService;
import com.ut.nlSystemAPi.service.CompetitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class CompetitorServiceImpl implements CompetitorService {

    @Autowired
    private CompetitorMapper competitorMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ModuleTypeMapper moduleTypeMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private TelegramUtils telegramUtils;

    @Autowired
    private Environment environment;

    @Autowired
    private HelperMapper helperMapper;

    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1036L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "UT Competitor (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(competitorMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<CompetitorResponse> responses = competitorMapper.getList(filter);

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor/list", null, null, "UT Competitor", "UT Competitor (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor/list", line, error.toString(), "UT Competitor", "UT Competitor (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1036L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "UT Competitor (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<CompetitorResponse> responses = competitorMapper.getOne(id);
            if (!responses.isEmpty()) {
                for (CompetitorResponse response : responses) {
                    response.setMainProduct(competitorMapper.getMainProduct(id));
                    response.setProductType(competitorMapper.getProductType(id));
                    response.setBusinessType(competitorMapper.getBusinessType(id));
                    response.setWeakness(competitorMapper.getWeakness(id));
                    response.setStrength(competitorMapper.getStrength(id));
                }
            }

            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor/find/{id}", null, null, "UT Competitor", "UT Competitor (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor/find/{id}", line, error.toString(), "UT Competitor", "UT Competitor (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> insert(CompetitorRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1036L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "UT Competitor (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if (competitorMapper.checkDuplicate(request.getName(), null) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            // Check Data
            Competitor competitor = new Competitor();
            competitor.setPriorityId(request.getPriorityId());
            competitor.setStatusId(request.getStatusId());
            competitor.setStageId(request.getStageId());
            competitor.setEmployeeAmountId(request.getEmployeeAmountId());
            competitor.setPartner(request.getPartner());
            competitor.setName(request.getName());
            competitor.setAddress(request.getAddress());
            competitor.setDescription(request.getDescription());
            competitor.setContact(request.getContact());
            competitor.setEmployeeInfo(request.getEmployeeInfo());
            competitor.setNumberOfCustomer(request.getNumberOfCustomer());
            competitor.setSalesRevenue(request.getSalesRevenue());
            competitor.setCreatedBy(userId);
            Boolean result = competitorMapper.insert(competitor);

            if (result) {

                if (request.getMainProducts() != null) {
                    for (Long mainProductId : request.getMainProducts()) {
                        competitorMapper.insertMainProduct(competitor.getId(), mainProductId, userId);
                    }
                }

                if (request.getProductTypes() != null) {
                    for (Long productTypeId : request.getProductTypes()) {
                        competitorMapper.insertProductType(competitor.getId(), productTypeId, userId);
                    }
                }

                if (request.getBusinessTypes() != null) {
                    for (Long businessTypeId : request.getBusinessTypes()) {
                        competitorMapper.insertBusinessType(competitor.getId(), businessTypeId, userId);
                    }
                }

                if (request.getWeaknesses() != null) {
                    for (Long weaknessId : request.getWeaknesses()) {
                        competitorMapper.insertWeakness(competitor.getId(), weaknessId, userId);
                    }
                }

                if (request.getStrengths() != null) {
                    for (Long strengthId : request.getStrengths()) {
                        competitorMapper.insertStrength(competitor.getId(), strengthId, userId);
                    }
                }

                String employeeName = competitorMapper.getEmployeeName(userId);

                String message = "\uD83E\uDD8D <b><u>Competitor</u></b>\n\n" +
                        "<i>Created Date: " + CheckNull.safe(competitor.getCreated())  + "</i>\n\n" +
                        "<b>Competitor: " +  CheckNull.safe(competitor.getName()) + "</b>\n\n" +
                        "<i>Description:</i>\n" +
                        "<i>" + CheckNull.safe(competitor.getDescription()) + "</i>\n\n" +
                        "<pre>Created By: " + CheckNull.safe(employeeName) + "</pre>";

                Long messageId = telegramUtils.pushTelegram(
                        message,
                        environment.getProperty("telegram.chatId.sales-order"),
                        environment.getProperty("telegram.botToken.sales-order"),
                        null
                );
                helperMapper.updateMessageId("ut_competitors", messageId, competitor.getId());

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/competitor/add", null, null, "UT Competitor", "UT Competitor (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor/add", line, error.toString(), "UT Competitor", "UT Competitor (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> update(CompetitorUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1036L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "UT Competitor (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            // Check Duplicate
            if (competitorMapper.checkDuplicate(request.getName(), request.getId()) > 0) {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Duplicate Name", false));
            }

            // Check Data
            Competitor competitor = new Competitor();
            competitor.setId(request.getId());
            competitor.setPriorityId(request.getPriorityId());
            competitor.setStatusId(request.getStatusId());
            competitor.setStageId(request.getStageId());
            competitor.setEmployeeAmountId(request.getEmployeeAmountId());
            competitor.setPartner(request.getPartner());
            competitor.setName(request.getName());
            competitor.setAddress(request.getAddress());
            competitor.setDescription(request.getDescription());
            competitor.setContact(request.getContact());
            competitor.setEmployeeInfo(request.getEmployeeInfo());
            competitor.setNumberOfCustomer(request.getNumberOfCustomer());
            competitor.setSalesRevenue(request.getSalesRevenue());
            competitor.setModifiedBy(userId);

            Boolean result = competitorMapper.update(competitor);

            if (result) {

                if (request.getMainProducts() != null) {
                    competitorMapper.deleteMainProduct(competitor.getId(), userId);
                    for (Long mainProductId : request.getMainProducts()) {
                        competitorMapper.insertMainProduct(competitor.getId(), mainProductId, userId);
                    }
                }

                if (request.getProductTypes() != null) {
                    competitorMapper.deleteProductType(competitor.getId(), userId);
                    for (Long productTypeId : request.getProductTypes()) {
                        competitorMapper.insertProductType(competitor.getId(), productTypeId, userId);
                    }
                }

                if (request.getBusinessTypes() != null) {
                    competitorMapper.deleteBusinessType(competitor.getId(), userId);
                    for (Long businessTypeId : request.getBusinessTypes()) {
                        competitorMapper.insertBusinessType(competitor.getId(), businessTypeId, userId);
                    }
                }

                if (request.getWeaknesses() != null) {
                    competitorMapper.deleteWeakness(competitor.getId(), userId);
                    for (Long weaknessId : request.getWeaknesses()) {
                        competitorMapper.insertWeakness(competitor.getId(), weaknessId, userId);
                    }
                }

                if (request.getStrengths() != null) {
                    competitorMapper.deleteStrength(competitor.getId(), userId);
                    for (Long strengthId : request.getStrengths()) {
                        competitorMapper.insertStrength(competitor.getId(), strengthId, userId);
                    }
                }

                String employeeName = competitorMapper.getEmployeeName(userId);

                String message = "\uD83E\uDD8D <b><u>Competitor</u></b>\n\n" +
                        "<i>Modified Date: " + CheckNull.safe(competitor.getModified())  + "</i>\n\n" +
                        "<b>Competitor: " +  CheckNull.safe(competitor.getName()) + "</b>\n\n" +
                        "<i>Description:</i>\n" +
                        "<i>" + CheckNull.safe(competitor.getDescription()) + "</i>\n\n" +
                        "<pre>Modified By: " + CheckNull.safe(employeeName) + "</pre>";

                Long messageId = telegramUtils.pushTelegram(
                        message,
                        environment.getProperty("telegram.chatId.sales-order"),
                        environment.getProperty("telegram.botToken.sales-order"),
                        helperMapper.getMessageId("ut_competitors", competitor.getId())
                );
                helperMapper.updateMessageId("ut_competitors", messageId, competitor.getId());

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/competitor/update", null, null, "UT Competitor", "UT Competitor (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor/update", line, error.toString(), "UT Competitor", "UT Competitor (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1036L;
        try {
            // Check Permission
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "UT Competitor (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<CompetitorResponse> competitor = competitorMapper.getOne(id);

            Boolean result = competitorMapper.delete(id, userId);

            if (result) {

                String employeeName = competitorMapper.getEmployeeName(userId);

                String modifiedDate = competitorMapper.getModifiedDate(id);

                String message = "\uD83E\uDD8D <b><u>Competitor</u></b>\n\n" +
                        "<i>Modified Date: " + CheckNull.safe(modifiedDate)  + "</i>\n\n" +
                        "<b>Competitor: " +  CheckNull.safe(competitor.get(0).getName()) + "</b>\n\n" +
                        "<i>Description:</i>\n" +
                        "<i>" + CheckNull.safe(competitor.get(0).getDescription()) + "</i>\n\n" +
                        "<b>Status: Deleted</b>\n"+
                        "<pre>Modified By: " + CheckNull.safe(employeeName) + "</pre>";

                Long messageId = telegramUtils.pushTelegram(
                        message,
                        environment.getProperty("telegram.chatId.sales-order"),
                        environment.getProperty("telegram.botToken.sales-order"),
                        helperMapper.getMessageId("ut_competitors", id)
                );
                helperMapper.updateMessageId("ut_competitors", messageId, id);

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/competitor/delete/{id}", null, null, "UT Competitor", "UT Competitor (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor/delete/{id}", line, error.toString(), "UT Competitor", "UT Competitor (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    public ResponseMessage<BaseResult> close(UpdateStatusRequest request, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1036L;
        try {
            Long userId = userService.getUserAuth().getId();

            List<CompetitorResponse> competitor = competitorMapper.getOne(request.getId());

            Boolean result = helperMapper.updateStatus("ut_competitors", "is_close", request.getStatus(), userId, request.getId());

            if (result) {

                String employeeName = competitorMapper.getEmployeeName(userId);

                String modifiedDate = competitorMapper.getModifiedDate(request.getId());

                String status = request.getStatus() != null && request.getStatus() == 1 ? "Close" : "Open";

                String message = "\uD83E\uDD8D <b><u>Competitor</u></b>\n\n" +
                        "<i>Modified Date: " + CheckNull.safe(modifiedDate)  + "</i>\n\n" +
                        "<b>Competitor: " +  CheckNull.safe(competitor.get(0).getName()) + "</b>\n\n" +
                        "<i>Description:</i>\n" +
                        "<i>" + CheckNull.safe(competitor.get(0).getDescription()) + "</i>\n\n" +
                        "<b>Status: " + status + "</b>\n"+
                        "<pre>Modified By: " + CheckNull.safe(employeeName) + "</pre>";

                Long messageId = telegramUtils.pushTelegram(
                        message,
                        environment.getProperty("telegram.chatId.sales-order"),
                        environment.getProperty("telegram.botToken.sales-order"),
                        helperMapper.getMessageId("ut_competitors", request.getId())
                );
                helperMapper.updateMessageId("ut_competitors", messageId, request.getId());

                /*System Activity*/
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/competitor/close", null, null, "UT Competitor", "UT Competitor (Close)", "Close", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            /*System Activity*/
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/competitor/close", line, error.toString(), "UT Competitor", "UT Competitor (Close)", "Close", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }
}