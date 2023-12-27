package com.kh.miniprojectHD.controller;

import com.kh.miniprojectHD.dao.InquiryDAO;
import com.kh.miniprojectHD.dao.ReservationDAO;
import com.kh.miniprojectHD.dao.RestMenuDAO;
import com.kh.miniprojectHD.vo.InquiryVO;
import com.kh.miniprojectHD.vo.ReservationVO;
import com.kh.miniprojectHD.vo.RestMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class ReservationController {

    @Autowired
    private ReservationDAO dao;
    //Get : 예약 조회
    @GetMapping("/resv")
    public ResponseEntity<List<ReservationVO>> resvList (@RequestParam String name, String stat) {
        System.out.println(name + stat);
        List<ReservationVO> list = dao.resvSelect(name,stat);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //예약 추가
    @PostMapping("/restaurant/add/reservation")
    public ResponseEntity<Boolean> addRes(@RequestBody Map<String, String> resData){
        String getRestId = resData.get("restId");
        String getMemberId = resData.get("memberId");
        String getResDate = resData.get("resDate");
        String getResReq = resData.get("resReq");
        int getResSeat = Integer.parseInt(resData.get("resSeat"));
        int getResPeo = Integer.parseInt(resData.get("resPeo"));

        boolean list = dao.addRes(getRestId,getMemberId,getResDate,getResReq,getResSeat,getResPeo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    //예약 변경
    @PostMapping("/restaurant/update/reservation")
    public ResponseEntity<Boolean> updateRes(@RequestBody Map<String, String> resData){

        String getResDate = resData.get("resDate");
        String getResReq = resData.get("resReq");
        int getResSeat = Integer.parseInt(resData.get("resSeat"));
        int getResPeo = Integer.parseInt(resData.get("resPeo"));
        int getResId = Integer.parseInt(resData.get("resId"));

        boolean list = dao.updateRes(getResDate,getResReq,getResSeat,getResPeo,getResId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    //사업자 예약 조회
    @GetMapping("/business/resv")
    public ResponseEntity<List<ReservationVO>> businessResvList (@RequestParam String id,String stat) {
        List<ReservationVO> list = dao.businessResvSelect(id,stat);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    //사업자 예약 확정하기
    @PostMapping("/business/resv/stat/update")
    public ResponseEntity<Boolean> updateMenu(@RequestBody Map<String, ReservationVO[]> resvData) {
        ReservationVO[] resvList = resvData.get("vo");
        boolean result = dao.resvStatUpdate(resvList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //예약취소
    @PostMapping("/resv/delete")
    public ResponseEntity<Boolean> delResv(@RequestBody Map<String, String> resvData) {
        int resvId = Integer.parseInt(resvData.get("resvId"));
        boolean list = dao.resvDelete(resvId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
