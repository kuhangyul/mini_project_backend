package com.kh.miniprojectHD.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class ReviewVO {
    private int reviewId;
    private String memId;
    private String restId;
    private String restName;
    private String reviewTitle;
    private String reviewContent;
    private String reviewFileName;
    private Date reviewDate;
    private double rating;
    private int likes;
    public ReviewVO(){

    }
}
