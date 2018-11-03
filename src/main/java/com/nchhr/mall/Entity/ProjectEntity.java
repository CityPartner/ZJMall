package com.nchhr.mall.Entity;

//平台项目相关dao层
public class ProjectEntity {
    String project_id;
    String name;
    String funding;
    double discount_lowest;
    double discount_highest;

    @Override
    public String toString() {
        return "ProjectEntity{" +
                "project_id='" + project_id + '\'' +
                ", name='" + name + '\'' +
                ", funding='" + funding + '\'' +
                ", discount_lowest=" + discount_lowest +
                ", discount_highest=" + discount_highest +
                '}';
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunding() {
        return funding;
    }

    public void setFunding(String funding) {
        this.funding = funding;
    }

    public double getDiscount_lowest() {
        return discount_lowest;
    }

    public void setDiscount_lowest(double discount_lowest) {
        this.discount_lowest = discount_lowest;
    }

    public double getDiscount_highest() {
        return discount_highest;
    }

    public void setDiscount_highest(double discount_highest) {
        this.discount_highest = discount_highest;
    }

    public ProjectEntity(String project_id, String name, String funding, double discount_lowest, double discount_highest) {
        this.project_id = project_id;
        this.name = name;
        this.funding = funding;
        this.discount_lowest = discount_lowest;
        this.discount_highest = discount_highest;
    }

    public ProjectEntity() {
    }
}
