package com.ut.nlSystemAPi.mapper.primary;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ut.nlSystemAPi.model.OtRequest;
import com.ut.nlSystemAPi.model.filter.OtRequestFilter;
import com.ut.nlSystemAPi.model.response.OtRquestResponse;

@Repository
public interface OtRequestMapper {

	List<OtRquestResponse> getList(@Param("filter") OtRequestFilter filter);

	int count(@Param("filter") OtRequestFilter filter);

	List<OtRquestResponse> getOne(@Param("id") Long id);

	Boolean insert(@Param("otRequest") OtRequest otRequest);

	Boolean updateStatus(@Param("otRequest") OtRequest otRequest);

	Boolean delete(@Param("id") Long id, @Param("userId") Long userId);
}
