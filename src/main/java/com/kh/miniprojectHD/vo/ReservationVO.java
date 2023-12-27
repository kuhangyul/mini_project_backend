package com.kh.miniprojectHD.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class ReservationVO {
    private int resvId;
    private String memId;
    private  String restId;
    private String restName;
    private Date resvDate;
    private String resvTime;
    private Date applicationDate;
    private Date confirmDate;
    private String resvRequest;
    private int resvSeat;
    private int resvPeople;
    private String resvStat;
}
