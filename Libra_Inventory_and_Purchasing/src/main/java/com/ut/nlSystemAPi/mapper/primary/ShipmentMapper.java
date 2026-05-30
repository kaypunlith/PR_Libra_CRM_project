package com.ut.nlSystemAPi.mapper.primary;
import com.ut.nlSystemAPi.model.GoodReceiptNote;
import com.ut.nlSystemAPi.model.Shipment;
import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.response.Shipment.ShipementResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShipmentMapper {

  List<ShipementResponse> getList(@Param("filter") Filter filter);

  Long countList(@Param("filter") Filter filter);

  List<ShipementResponse> getOne(@Param("id") Long id);

  Boolean insert(@Param("shipment") Shipment shipment);

  Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

  Boolean update(@Param("shipment") Shipment shipment);

  Boolean delete(@Param("id") Long id ,@Param("userId") Long userid);


}