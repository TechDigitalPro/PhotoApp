package com.cambe.cartooneffecteditor.bean;

public class FilterInfo {
    private boolean bFavourite = false;
    private boolean bSelected = false;
    private int filterType;

    public void setFilterType(int id) {
        this.filterType = id;
    }

    public int getFilterType() {
        return this.filterType;
    }

    public boolean isSelected() {
        return this.bSelected;
    }

    public void setSelected(boolean bSelected) {
        this.bSelected = bSelected;
    }

    public boolean isFavourite() {
        return this.bFavourite;
    }

    public void setFavourite(boolean bFavourite) {
        this.bFavourite = bFavourite;
    }
}
