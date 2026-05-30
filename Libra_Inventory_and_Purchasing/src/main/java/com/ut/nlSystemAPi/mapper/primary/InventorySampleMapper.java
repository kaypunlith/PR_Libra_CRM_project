package com.ut.nlSystemAPi.mapper.primary;

import com.ut.nlSystemAPi.model.InventorySample;
import com.ut.nlSystemAPi.model.filter.InventorySampleFilter;
import com.ut.nlSystemAPi.model.filter.InventorySampleProductFilter;
import com.ut.nlSystemAPi.model.request.Login.InventorySample.InventorySampleRequest;
import com.ut.nlSystemAPi.model.request.Login.InventorySample.InventorySampleRequestDetail;
import com.ut.nlSystemAPi.model.request.Login.InventorySample.InventorySampleRequestUpdateDetail;
import com.ut.nlSystemAPi.model.response.InventorySample.InventorySampleProductResponseDetail;
import com.ut.nlSystemAPi.model.response.InventorySample.InventorySampleResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventorySampleMapper {

    List<InventorySampleResponse> getList(@Param("filter") InventorySampleFilter filter, @Param("userId") Long userId);

    Long countList(@Param("filter") InventorySampleFilter filter, @Param("userId") Long userId);

    Long countProductList(@Param("filter") InventorySampleProductFilter filter);

    List<InventorySampleResponse> getOne(@Param("id") Long id);

    List<InventorySampleProductResponseDetail> getListProductDetail(@Param("filter") InventorySampleProductFilter filter, @Param("userId") Long userId);

    List<InventorySampleProductResponseDetail> getProductDetails(@Param("inventorySampleProductId") Long inventorySampleProductId);

    Boolean insert(@Param("inventorySample") InventorySample inventorySample);

    Boolean update(@Param("inventorySample") InventorySample inventorySample);

    Boolean archive(@Param("id") Long id, @Param("userId") Long userId);

    Boolean insertInventorySampleProductDetails(@Param("inventorySample") InventorySampleRequestDetail inventorySample);

    Boolean updateInventorySampleStatus(@Param("inventorySample") InventorySample inventorySample);

    Boolean delete(@Param("id") Long id, @Param("userId") Long userId);

    Boolean deleteInventorySampleProductDetailsBySampleId(@Param("inventorySampleId") Long inventorySampleId);

    Long checkDuplicate(@Param("name") String name, @Param("id") Long id);

}
