package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.BranchType;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.BranchType.BranchTypeResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchTypeMapper {

    List<BranchTypeResponse> getList(@Param("filter") Filter filter);

    Long countList(@Param("filter") Filter filter);

    List<BranchTypeResponse> getOne(@Param("id") Long id);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

    Boolean insert(@Param("branchType") BranchType branchType);

    Boolean update(@Param("branchType") BranchType branchType);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);



}
