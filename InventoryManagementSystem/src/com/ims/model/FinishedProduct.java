package com.ims.model;

public class FinishedProduct extends InventoryItem {

    private double sellingPrice;
    private String unit;

    public FinishedProduct(int id, String name, int quantity,
                           int minStockLevel, double sellingPrice, String unit) {
        super(id, name, quantity, minStockLevel);
        this.sellingPrice = sellingPrice;
        this.unit         = unit;
    }

    // Getters
    public double getSellingPrice() { return sellingPrice; }
    public String getUnit()         { return unit; }

    // Setters
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
    public void setUnit(String unit)                 { this.unit = unit; }

    @Override
    public String getCategory() {
        return "Finished Product";
    }
}