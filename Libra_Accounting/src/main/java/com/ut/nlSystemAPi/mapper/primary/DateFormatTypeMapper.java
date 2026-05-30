package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.BranchType;
import com.ut.nlSystemAPi.model.DateFormatType;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.BranchType.BranchTypeResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.DateFormatResponse;
import com.ut.nlSystemAPi.model.response.Dropdown.NationalityDropdownResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateFormatTypeMapper {

    Boolean UpdateDateFormatType(@Param("dateFormatType") DateFormatType dateFormatType);

    Boolean UpdateTimeFormatType(@Param("dateFormatType") DateFormatType dateFormatType);


    List<DateFormatResponse> getList(@Param("filter") Filter filter);
}
