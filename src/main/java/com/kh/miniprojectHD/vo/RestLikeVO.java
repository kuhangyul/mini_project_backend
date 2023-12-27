package com.kh.miniprojectHD.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestLikeVO {
    private String restId;
    private String memId;
    private String restName;
    private double restRating;
    private int reservation;
}
