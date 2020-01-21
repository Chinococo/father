package com.example.father;

import java.util.ArrayList;

public class data {
    String date;
    String Vendor;
    ArrayList<item> item=new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }

    public ArrayList<com.example.father.item> getItem() {
        return item;
    }

    public void setItem(ArrayList<com.example.father.item> item) {
        this.item = item;
    }
}
