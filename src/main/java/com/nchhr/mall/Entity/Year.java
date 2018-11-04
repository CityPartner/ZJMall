package com.nchhr.mall.Entity;

public class Year {
    private String Y_id;
    private String  year;

    public String getY_id() {
        return Y_id;
    }

    public void setY_id(String y_id) {
        Y_id = y_id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Year{" +
                "Y_id='" + Y_id + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}

