package com.example.babyneeds.model;

public class Item
{

    private int id;
    private String itemName;
    private String itemColor;
    private int itemSize;
    private int itemQuantity;
    private String dateAdded;

    public Item() { }

    public Item(String itemName, String itemColor, int itemSize, int itemQuantity, String dateAdded)
    {
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.itemSize = itemSize;
        this.itemQuantity = itemQuantity;
        this.dateAdded = dateAdded;
    }

    public Item(int id, String itemName, String itemColor, int itemSize, int itemQuantity, String dateAdded)
    {
        this.id = id;
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.itemSize = itemSize;
        this.itemQuantity = itemQuantity;
        this.dateAdded = dateAdded;
    }

    public int getId() { return id;  }

    public void setId(int id) { this.id = id; }

    public String getItemName() { return itemName; }

    public void setItemName(String itemName) { this.itemName = itemName;  }

    public String getItemColor()  { return itemColor;  }

    public void setItemColor(String itemColor) { this.itemColor = itemColor; }

    public int getItemSize() { return itemSize;  }

    public void setItemSize(int itemSize) { this.itemSize = itemSize; }

    public int getItemQuantity() { return itemQuantity; }

    public void setItemQuantity(int itemQuantity) { this.itemQuantity = itemQuantity; }

    public String getDateAdded() { return dateAdded; }

    public void setDateAdded(String dateAdded) { this.dateAdded = dateAdded; }

}
