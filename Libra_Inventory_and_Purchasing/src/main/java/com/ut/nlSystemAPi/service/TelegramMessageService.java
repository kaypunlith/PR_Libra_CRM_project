package com.ut.nlSystemAPi.service;

import com.ut.nlSystemAPi.helper.TelegramUtils;
import com.ut.nlSystemAPi.mapper.primary.PriceRequestMapper;
import com.ut.nlSystemAPi.model.PriceRequest;
import com.ut.nlSystemAPi.model.response.PriceRequest.PriceRequestResponseDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class TelegramMessageService {
    @Autowired
    private PriceRequestMapper priceRequestMapper;

    @Autowired
    private UserService userService;

    @Value("${telegram.botToken.price-request}")
    private String botToken;

    @Value("${telegram.chatId.price-request}")
    private String chatId;

    public void sendTelegramMessage(String botToken, String chatId, String message) throws UnsupportedEncodingException {
        TelegramUtils.sendHtmlMessage(message, chatId, botToken, null);
    }

    private String nullSafeString(String input) {
        return TelegramUtils.safeText(input);
    }

    public void sendPriceRequestNotification(String action, String userName, String remark, PriceRequest priceRequest) {
        if (priceRequest == null || priceRequest.getId() == null) {
            return;
        }

        List<PriceRequestResponseDetail> priceRequestResponseDetails = priceRequestMapper.getOne(priceRequest.getId());
        if (priceRequestResponseDetails == null || priceRequestResponseDetails.isEmpty()) {
            return;
        }

        PriceRequestResponseDetail detail = priceRequestResponseDetails.get(0);
        Long userId = userService.getUserAuth().getId();

        String percentage = nullSafeString(detail.getPercentageName());
        String priority = nullSafeString(detail.getPriorityName());
        String status = nullSafeString(detail.getPrStatusName());
        String employeeCreatedName = TelegramUtils.safeText(priceRequestMapper.getFullName(userId));

        String message = "<b>Price Request</b>\n" +
                "────────────────────────\n" +
                "<b>Date:</b> " + nullSafeString(priceRequest.getRequestDate()) + "\n" +
                "<b>Request No:</b> " + nullSafeString(priceRequest.getRequestCode()) + "\n" +
                "<b>Organization:</b> " + nullSafeString(priceRequest.getOrganizationName()) + "\n\n" +

                "<b>Contact:</b> " + nullSafeString(priceRequest.getCustomerContactName()) + "\n" +
                "<b>Model:</b> " + nullSafeString(priceRequest.getModelName()) + "\n" +
                "<b>Brand:</b> " + nullSafeString(priceRequest.getBrand()) + "\n" +
                "<b>Quantity:</b> " + TelegramUtils.safeText(priceRequest.getQty()) + "\n\n" +

                "<b>Percentage:</b> " + percentage + "\n" +
                "<b>Priority:</b> " + priority + "\n" +
                "<b>Expected Date:</b> " + nullSafeString(priceRequest.getDateExpected()) + "\n\n" +

                "<b>Status:</b> " + status + "\n" +
                "<b>Remark:</b> " + nullSafeString(remark) + "\n\n" +

                "<b>Memo:</b> " + nullSafeString(priceRequest.getNote()) + "\n\n" +
                "<b>" + nullSafeString(action) + " By:</b> " + nullSafeString(userName) + "\n\n" +

                "<i>Contact: " + employeeCreatedName + "</i>\n" +
                "────────────────────────";

        try {
            sendTelegramMessage(botToken, chatId, message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToTelegram(String action, String userName, String remark, PriceRequest priceRequest) {
        if (priceRequest == null || priceRequest.getId() == null) {
            return;
        }

        List<PriceRequestResponseDetail> priceRequestResponseDetails = priceRequestMapper.getOne(priceRequest.getId());
        if (priceRequestResponseDetails == null || priceRequestResponseDetails.isEmpty()) {
            return;
        }

        PriceRequestResponseDetail detail = priceRequestResponseDetails.get(0);
        Long userId = userService.getUserAuth().getId();

        String priority = nullSafeString(detail.getPriorityName());
        String employeeCreatedName = TelegramUtils.safeText(priceRequestMapper.getFullName(userId));
        String priceRequestName = nullSafeString(priceRequest.getPriceRequestName());

        String message = "<b>Price Request Status</b>\n\n" +
                "<b>Date:</b> " + nullSafeString(priceRequest.getRequestDate()) + "\n" +
                "<b>Request No:</b> " + nullSafeString(priceRequest.getRequestCode()) + "\n\n" +

                "<b>Priority:</b> " + priority + "\n" +
                "<b>សាររំលឹកពីប្រព័ន្ធ:</b> " + priceRequestName +
                "សូម <b>" + employeeCreatedName + "</b> ចេញ Quote អោយអតិថិជន <b>" + nullSafeString(priceRequest.getOrganizationName()) + "</b>\n\n" +

                "<b>Product Code:</b> " + nullSafeString(priceRequest.getModelName()) + "\n" +
                "<b>Quantity:</b> " + TelegramUtils.safeText(priceRequest.getQty()) + "\n\n" +

                "PR បានបិទហើយ។ ផលិតផលបាន" + nullSafeString(action) + "រួចរាល់និងមានតម្លៃ មានលក្ខខណគ្រប់គ្រាន់អាចផ្ញើរអោយអតិថិជន។\n\n" +

                "<b>អ្នកផ្តល់ដំណឹង:</b> " + employeeCreatedName + "\n\n" +

                "<i>Contact: " + employeeCreatedName + "</i>\n" +
                "────────────────────────";

        try {
            sendTelegramMessage(botToken, chatId, message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

