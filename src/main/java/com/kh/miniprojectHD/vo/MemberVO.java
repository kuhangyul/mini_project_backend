package com.kh.miniprojectHD.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class MemberVO {
    private String memId;
    private String pwd;
    private String name;
    private String nickName;
    private String eMail;
    private String phoneNum;
    private String addr;
    private Date joinDate;
    private String imgFileName;

    public MemberVO(){

    }
}
