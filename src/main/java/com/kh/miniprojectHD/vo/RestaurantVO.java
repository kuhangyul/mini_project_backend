package com.kh.miniprojectHD.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantVO {
    private String memId;
    private String restId;
    private String restName;
    private Date restDate;
    private int isAvailable;
    private String category;

    public RestaurantVO(){

    }

}
