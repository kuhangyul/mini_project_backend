package com.kh.miniprojectHD.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestMenuVO {
    private String restId;
    private String menuName;
    private int menuPrice;
    private String menuDesc;
    private String menuImgFileName;
    private int menuId;
    public RestMenuVO() {

    }
}
