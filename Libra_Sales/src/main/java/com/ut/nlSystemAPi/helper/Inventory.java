package com.ut.nlSystemAPi.helper;

import com.ut.nlSystemAPi.mapper.primary.HelperMapper;
import com.ut.nlSystemAPi.model.base.GlobalStock;
import org.springframework.stereotype.Service;

@Service
public class Inventory {

    private final HelperMapper helperMapper;

    public Inventory(HelperMapper helperMapper) {
        this.helperMapper = helperMapper;
    }

    public void insertStock(GlobalStock globalStock, Long typeOperation, String fieldToUpdate, String fieldToUpdateFoc) {

        //! Insert Group Total By Warehouse
        String tblGroupTotal = globalStock.getWarehouseId() + "_group_totals";
        helperMapper.insertGroupTotal(globalStock, tblGroupTotal, typeOperation);

        //!Insert Group Total Details By Warehouse
        String tblGroupTotalDetail = globalStock.getWarehouseId() + "_group_total_details";
        helperMapper.insertGroupTotalDetail(globalStock, tblGroupTotalDetail, typeOperation, fieldToUpdate, fieldToUpdateFoc);

        //! Insert Inventory Totals By Location
        String tblInventoryTotal = globalStock.getLocationId() + "_inventory_totals";
        helperMapper.insertInventoryTotal(globalStock, tblInventoryTotal, typeOperation);

        //! Insert Inventory Total Details By Location
        String tblInventoryTotalDetail = globalStock.getLocationId() + "_inventory_total_details";
        helperMapper.insertInventoryTotalDetail(globalStock, tblInventoryTotalDetail, typeOperation, fieldToUpdate);

        //! Insert Inventories By Location
        String tblInventories = globalStock.getLocationId() + "_inventories";
        helperMapper.insertInventories(globalStock, tblInventories, typeOperation);

        //! Insert Inventory Totals (All)
        helperMapper.insertInventoryTotalAll(globalStock, typeOperation, fieldToUpdate, fieldToUpdateFoc);

        //! Insert Inventories (All)
        helperMapper.insertInventoriesAll(globalStock, typeOperation);
    }

    public void insertOrder(GlobalStock globalStock, Long typeOperation) {

        //! Insert Group Total By Warehouse
        String tblGroupTotal = globalStock.getWarehouseId() + "_group_totals";
        helperMapper.insertGroupTotalOrder(globalStock, tblGroupTotal, typeOperation);

        //!Insert Group Total Details By Warehouse
        String tblGroupTotalDetail = globalStock.getWarehouseId() + "_group_total_details";
        helperMapper.insertGroupTotalDetailOrder(globalStock, tblGroupTotalDetail, typeOperation);

        //! Insert Inventory Totals By Location
        String tblInventoryTotal = globalStock.getLocationId() + "_inventory_totals";
        helperMapper.insertInventoryTotalOrder(globalStock, tblInventoryTotal, typeOperation);

        //! Insert Inventory Total Details By Location
        String tblInventoryTotalDetail = globalStock.getLocationId() + "_inventory_total_details";
        helperMapper.insertInventoryTotalDetailOrder(globalStock, tblInventoryTotalDetail, typeOperation);

        //! Insert Inventory Totals (All)
        helperMapper.insertInventoryTotalAllOrder(globalStock, typeOperation);

    }
}
