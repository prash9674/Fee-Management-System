package com.fee.management.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "catalogItems")
public class CatalogItem {
    @Id
    private String cid;
    private String courseName;
    private double fee;

    public CatalogItem(String cid, String courseName, double fee) {
        this.cid = cid;
        this.courseName = courseName;
        this.fee = fee;
    }



    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }


}
