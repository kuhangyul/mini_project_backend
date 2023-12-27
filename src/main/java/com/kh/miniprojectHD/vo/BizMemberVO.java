package com.kh.miniprojectHD.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class BizMemberVO {
    private String bizId;
    private String pwd;
    private String name;
    private String eMail;
    private String phoneNum;
    private Date joinDate;
}
