package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.request.GroupRequest;
import com.ut.nlSystemAPi.model.request.GroupUpdateRequest;
import com.ut.nlSystemAPi.model.response.GroupResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMapper {

    List<GroupResponse> getList(@Param("filter") Filter filter);

    List<GroupResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("groupRequest") GroupRequest groupRequest);

    Boolean update(@Param("groupUpdateRequest") GroupUpdateRequest groupUpdateRequest);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    List<GroupResponse> listFilterGroup(@Param("filter") Filter filter, @Param("userId") Long userId);

}

