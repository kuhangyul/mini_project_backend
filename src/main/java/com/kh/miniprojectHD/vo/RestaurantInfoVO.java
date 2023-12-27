package com.kh.miniprojectHD.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantInfoVO {
    private String restId;
    private String restImgFileName;
    private String restPhoneNum;
    private String restAddr;
    private String restNotice;
    private String restHours;
    private String restIntro;

    public RestaurantInfoVO(){

    }
}
