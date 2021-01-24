package com.example.machinetask.model;

public class TestModel {

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int icon;
    public String name;

    public String getDetals() {
        return detals;
    }

    public void setDetals(String detals) {
        this.detals = detals;
    }

    public String detals;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int cost;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int count;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean check;

    public TestModel(int i, String name, String detals,int count,int cost,boolean check) {
        this.name = name;
        this.icon = i;
        this.check = check;
        this.cost = cost;
        this.count = count;
        this.detals = detals;
    }
}
