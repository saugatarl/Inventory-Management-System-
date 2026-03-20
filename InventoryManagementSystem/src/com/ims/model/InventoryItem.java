package com.ims.model;

public abstract class InventoryItem {

    private int id;
    private String name;
    private int quantity;
    private int minStockLevel;

    public InventoryItem(int id, String name, int quantity, int minStockLevel) {
        this.id            = id;
        this.name          = name;
        this.quantity      = quantity;
        this.minStockLevel = minStockLevel;
    }

    // Getters
    public int    getId()            { return id; }
    public String getName()          { return name; }
    public int    getQuantity()      { return quantity; }
    public int    getMinStockLevel() { return minStockLevel; }

    // Setters
    public void setName(String name)      { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setMinStockLevel(int min) { this.minStockLevel = min; }

    // Abstract method — every child class MUST implement this
    public abstract String getCategory();
}