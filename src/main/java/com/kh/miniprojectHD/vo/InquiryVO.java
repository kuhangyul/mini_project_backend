package com.kh.miniprojectHD.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class InquiryVO {
    private int inquiryId;

    private String memId;
    private String restId;
    private String restName;
    private String inquiryTitle;
    private String inquiryContent;
    private String inquiryAnswer;
    private Date inquiryDate;
    private String inquiryImgFileName;
    private String inquiryStat;
}
