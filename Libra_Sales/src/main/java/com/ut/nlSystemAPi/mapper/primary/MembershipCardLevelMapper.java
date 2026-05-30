package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.entity.MembershipCardLevel.MembershipCardLevel;
import com.ut.nlSystemAPi.model.response.MembershipCardLevel.MembershipCardLevelResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipCardLevelMapper {

    List<MembershipCardLevelResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<MembershipCardLevelResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("membershipCardLevel") MembershipCardLevel membershipCardLevel);

    Boolean update(@Param("membershipCardLevel") MembershipCardLevel membershipCardLevel);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
