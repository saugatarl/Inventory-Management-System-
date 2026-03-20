package com.ims.controller;

import com.ims.dao.RawMaterialDAO;
import com.ims.model.RawMaterial;
import com.ims.util.AlertHelper;
import java.util.List;

public class RawMaterialController {

    private RawMaterialDAO rawMaterialDAO = new RawMaterialDAO();

    public List<RawMaterial> getAllRawMaterials() {
        return rawMaterialDAO.getAllRawMaterials();
    }

    public List<RawMaterial> searchRawMaterials(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return rawMaterialDAO.getAllRawMaterials();
        }
        return rawMaterialDAO.searchRawMaterials(keyword.trim());
    }

    public boolean addRawMaterial(String name, String quantityStr,
                                   String minStockStr, String supplierName,
                                   String unit) {
        // Form validation
        if (name == null || name.trim().isEmpty()) {
            AlertHelper.showError("Item name cannot be empty.");
            return false;
        }

        if (supplierName == null || supplierName.trim().isEmpty()) {
            AlertHelper.showError("Supplier name cannot be empty.");
            return false;
        }

        if (unit == null || unit.trim().isEmpty()) {
            AlertHelper.showError("Unit cannot be empty.");
            return false;
        }

        int quantity;
        int minStock;

        try {
            quantity = Integer.parseInt(quantityStr.trim());
            if (quantity < 0) {
                AlertHelper.showError("Quantity cannot be negative.");
                return false;
            }
        } catch (NumberFormatException e) {
            AlertHelper.showError("Quantity must be a valid number.");
            return false;
        }

        try {
            minStock = Integer.parseInt(minStockStr.trim());
            if (minStock < 0) {
                AlertHelper.showError("Minimum stock cannot be negative.");
                return false;
            }
        } catch (NumberFormatException e) {
            AlertHelper.showError("Minimum stock must be a valid number.");
            return false;
        }

        RawMaterial rm = new RawMaterial(0, name.trim(), quantity,
                                          minStock, supplierName.trim(),
                                          unit.trim());
        boolean success = rawMaterialDAO.addRawMaterial(rm);

        if (success) {
            AlertHelper.showSuccess("Raw material added successfully.");
        } else {
            AlertHelper.showError("Failed to add raw material.");
        }

        return success;
    }

    public boolean updateRawMaterial(int id, String name, String quantityStr,
                                      String minStockStr, String supplierName,
                                      String unit) {
        // Form validation
        if (name == null || name.trim().isEmpty()) {
            AlertHelper.showError("Item name cannot be empty.");
            return false;
        }

        if (supplierName == null || supplierName.trim().isEmpty()) {
            AlertHelper.showError("Supplier name cannot be empty.");
            return false;
        }

        if (unit == null || unit.trim().isEmpty()) {
            AlertHelper.showError("Unit cannot be empty.");
            return false;
        }

        int quantity;
        int minStock;

        try {
            quantity = Integer.parseInt(quantityStr.trim());
            if (quantity < 0) {
                AlertHelper.showError("Quantity cannot be negative.");
                return false;
            }
        } catch (NumberFormatException e) {
            AlertHelper.showError("Quantity must be a valid number.");
            return false;
        }

        try {
            minStock = Integer.parseInt(minStockStr.trim());
            if (minStock < 0) {
                AlertHelper.showError("Minimum stock cannot be negative.");
                return false;
            }
        } catch (NumberFormatException e) {
            AlertHelper.showError("Minimum stock must be a valid number.");
            return false;
        }

        RawMaterial rm = new RawMaterial(id, name.trim(), quantity,
                                          minStock, supplierName.trim(),
                                          unit.trim());
        boolean success = rawMaterialDAO.updateRawMaterial(rm);

        if (success) {
            AlertHelper.showSuccess("Raw material updated successfully.");
        } else {
            AlertHelper.showError("Failed to update raw material.");
        }

        return success;
    }

    public boolean deleteRawMaterial(int id) {
        boolean success = rawMaterialDAO.deleteRawMaterial(id);

        if (success) {
            AlertHelper.showSuccess("Raw material deleted successfully.");
        } else {
            AlertHelper.showError("Failed to delete raw material.");
        }

        return success;
    }
}