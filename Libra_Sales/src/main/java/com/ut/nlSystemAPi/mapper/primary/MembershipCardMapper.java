package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.MembershipCard.MembershipCard;
import com.ut.nlSystemAPi.model.entity.MembershipCardLevel.MembershipCardLevel;
import com.ut.nlSystemAPi.model.response.MembershipCard.MembershipCardResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipCardMapper {

    List<MembershipCardResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<MembershipCardResponse> getOne(@Param("id") Long id);

    MembershipCard getActiveByCustomerId(@Param("customerId") Long customerId);

    MembershipCardLevel getActiveMembershipCardType(@Param("id") Long id);

    Long getUpgradedMembershipTypeId(@Param("membershipTypeId") Long membershipTypeId, @Param("totalPoint") Double totalPoint);

    Long checkDuplicate(@Param("cardId") String cardId, @Param("id") Long id);

    Boolean insert(@Param("membershipCard") MembershipCard membershipCard);

    Boolean update(@Param("membershipCard") MembershipCard membershipCard);

    Boolean updateMembershipPoints(@Param("id") Long id, @Param("currentPoint") Double currentPoint, @Param("pointReceive") Double pointReceive);

    Boolean insertMembershipCardLog(
            @Param("membershipCardId") Long membershipCardId,
            @Param("salesOrderId") Long salesOrderId,
            @Param("totalAmount") Double totalAmount,
            @Param("oldPoint") Double oldPoint,
            @Param("point") Double point,
            @Param("totalPoint") Double totalPoint);

    Boolean updateMembershipType(@Param("id") Long id, @Param("membershipTypeId") Long membershipTypeId);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
