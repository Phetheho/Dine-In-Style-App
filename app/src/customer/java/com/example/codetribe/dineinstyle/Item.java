package com.example.codetribe.dineinstyle;

/**
 * Created by Codetribe on 2017/01/23.
 */

public class Item {

    private String itemNumber;
    private String itemName;
    private double itemPrice;


    public Item() {
    }

    public Item(String itemNumber, String itemName, double itemPrice) {
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }


}
