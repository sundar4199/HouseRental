package com.example.navigationdrawer;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Rating {
    String comment;
    Integer val;

    public Rating(String comment, Integer val) {
        this.comment = comment;
        this.val = val;
    }

    public Rating() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }
}
