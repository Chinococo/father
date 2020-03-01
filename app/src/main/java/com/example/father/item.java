package com.example.father;

public class item {
    String item_name;//單品名稱
    String count;//數量
    String unit_price;//單價
    String unit;//單位
    String sum;

    item(String item_name, String count, String unit_price, String unit, String sum) {
        this.item_name = item_name;
        this.count = count;
        this.unit_price = unit_price;
        this.unit = unit;
        this.sum = sum;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return item_name + "," + count + "," + unit_price + "," + unit + "," + sum;
    }
}
