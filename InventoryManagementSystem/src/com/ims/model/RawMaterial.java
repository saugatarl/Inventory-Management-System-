package com.ims.model;

public class RawMaterial extends InventoryItem {

    private String supplierName;
    private String unit;

    public RawMaterial(int id, String name, int quantity,
                       int minStockLevel, String supplierName, String unit) {
        super(id, name, quantity, minStockLevel);
        this.supplierName = supplierName;
        this.unit         = unit;
    }

    public String getSupplierName() { return supplierName; }
    public String getUnit()         { return unit; }

    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public void setUnit(String unit)                 { this.unit = unit; }

    @Override
    public String getCategory() {
        return "Raw Material";
    }
}