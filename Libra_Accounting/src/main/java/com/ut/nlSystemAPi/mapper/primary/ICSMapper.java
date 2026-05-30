package com.ut.nlSystemAPi.mapper.primary;


import com.ut.nlSystemAPi.model.request.Login.ICS.ICSDetailRequest;
import com.ut.nlSystemAPi.model.request.Login.ICS.ICSRequest;
import com.ut.nlSystemAPi.model.response.ICS.ICSResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICSMapper {

  List<ICSResponse> getList();

  Boolean update(@Param("icsRequest") ICSDetailRequest icsRequest);

}