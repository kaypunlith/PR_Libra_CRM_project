package com.ut.nlSystemAPi.serviceImpl;

import com.ut.nlSystemAPi.helper.GenerateCode;
import com.ut.nlSystemAPi.helper.ResponseMessageUtils;
import com.ut.nlSystemAPi.mapper.primary.HelperMapper;
import com.ut.nlSystemAPi.mapper.primary.MembershipCardMapper;
import com.ut.nlSystemAPi.mapper.primary.PermissionMapper;
import com.ut.nlSystemAPi.model.MessageService;
import com.ut.nlSystemAPi.model.base.BaseResult;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.Pagination;
import com.ut.nlSystemAPi.model.base.ResponseMessage;
import com.ut.nlSystemAPi.model.entity.MembershipCard.MembershipCard;
import com.ut.nlSystemAPi.model.request.MembershipCard.MembershipCardRequest;
import com.ut.nlSystemAPi.model.request.MembershipCard.MembershipCardUpdateRequest;
import com.ut.nlSystemAPi.model.response.MembershipCard.MembershipCardResponse;
import com.ut.nlSystemAPi.service.ActivityLogService;
import com.ut.nlSystemAPi.service.MembershipCardService;
import com.ut.nlSystemAPi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.List;

@Service
public class MembershipCardServiceImpl implements MembershipCardService {

    @Autowired
    private MembershipCardMapper membershipCardMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private GenerateCode generateCode;
    
    @Autowired
    private HelperMapper helperMapper;

    @Override
    public ResponseMessage<BaseResult> getList(Filter filter, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Membership Card (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Pagination pagination = new Pagination();
            pagination.setPage(filter.getPage());
            pagination.setRowsPerPage(filter.getRowsPerPage());
            pagination.setTotal(membershipCardMapper.countList(filter));
            filter.setPage((filter.getPage() - 1) * filter.getRowsPerPage());

            List<MembershipCardResponse> responses = membershipCardMapper.getList(filter);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card/list", null, null, "Membership Card", "Membership Card (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, pagination, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card/list", line, error.toString(), "Membership Card", "Membership Card (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> getOne(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Membership Card (View)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            List<MembershipCardResponse> responses = membershipCardMapper.getOne(id);

            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card/find/{id}", null, null, "Membership Card", "Membership Card (View)", "View", 1, "Success", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Success", responses, true));
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card/find/{id}", line, error.toString(), "Membership Card", "Membership Card (View)", "View", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    @Transactional
    public ResponseMessage<BaseResult> insert(MembershipCardRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Membership Card (Add)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            MembershipCard membershipCard = toMembershipCard(request);
            membershipCard.setCreatedBy(userId);
            membershipCard.setIsActive(1);

            Boolean result = membershipCardMapper.insert(membershipCard);
            if (result) {
                //! Get the reference code
                String code = (generateCode.generateAutoCode("membership_cards", "card_id", 7, "NL", false, "is_active = 1"));
                helperMapper.updateCode("membership_cards", "card_id", code, membershipCard.getId());

                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/membership-card/add", null, null, "Membership Card", "Membership Card (Add)", "Add", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card/add", line, error.toString(), "Membership Card", "Membership Card (Add)", "Add", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    @Transactional
    public ResponseMessage<BaseResult> update(MembershipCardUpdateRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Membership Card (Edit)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            MembershipCard membershipCard = toMembershipCard(request);
            membershipCard.setId(request.getId());
            membershipCard.setModifiedBy(userId);

            Boolean result = membershipCardMapper.update(membershipCard);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/membership-card/update", null, null, "Membership Card", "Membership Card (Edit)", "Edit", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card/update", line, error.toString(), "Membership Card", "Membership Card (Edit)", "Edit", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    @Override
    public ResponseMessage<BaseResult> delete(Long id, HttpServletRequest httpServletRequest) throws UnknownHostException {
        LocalTime startDuration = LocalTime.now();
        Long line = 1033L;
        try {
            Long userId = userService.getUserAuth().getId();
            if (permissionMapper.checkPermission(userId, "Membership Card (Delete)") == 0) {
                return ResponseMessageUtils.makeResponseByPermission(true, messageService.message("No Permission access.", false));
            }

            Boolean result = membershipCardMapper.delete(id, userId);
            if (result) {
                LocalTime endDuration = LocalTime.now();
                activityLogService.insert("/membership-card/delete/{id}", null, null, "Membership Card", "Membership Card (Delete)", "Delete", 1, "Success", startDuration, endDuration, httpServletRequest);
                return ResponseMessageUtils.makeResponse(true, messageService.message("Success", true));
            } else {
                return ResponseMessageUtils.makeResponse(true, messageService.message("Fail", false));
            }
        } catch (Exception error) {
            LocalTime endDuration = LocalTime.now();
            activityLogService.insert("/membership-card/delete/{id}", line, error.toString(), "Membership Card", "Membership Card (Delete)", "Delete", 2, "Error", startDuration, endDuration, httpServletRequest);
            return ResponseMessageUtils.makeResponse(true, messageService.message("Error", null, false));
        }
    }

    private MembershipCard toMembershipCard(MembershipCardRequest request) {
        MembershipCard membershipCard = new MembershipCard();
        membershipCard.setMembershipTypeId(request.getMembershipCardLevelId());
        membershipCard.setCustomerId(request.getCustomerId());
        membershipCard.setCardDateStart(request.getCardDateStart());
        membershipCard.setCardDateEnd(request.getCardDateEnd());
        membershipCard.setCurrentPoint(request.getCurrentPoint() == null ? 0D : request.getCurrentPoint());
        membershipCard.setTotalPoint(request.getTotalPoint() == null ? 0D : request.getTotalPoint());
        membershipCard.setPeriod(request.getPeriod());
        return membershipCard;
    }
}
