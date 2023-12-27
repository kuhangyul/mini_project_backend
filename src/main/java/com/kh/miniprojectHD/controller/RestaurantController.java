package com.kh.miniprojectHD.controller;


import com.kh.miniprojectHD.dao.ReservationDAO;
import com.kh.miniprojectHD.dao.RestaurantDAO;
import com.kh.miniprojectHD.vo.ReservationVO;
import com.kh.miniprojectHD.vo.RestJoinVO;
import com.kh.miniprojectHD.vo.RestaurantInfoVO;
import com.kh.miniprojectHD.vo.RestaurantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class RestaurantController {
    @Autowired
    private RestaurantDAO dao;
    //Get : 문의 조회
    @GetMapping("/restName")
    public ResponseEntity<RestaurantVO> resvList (@RequestParam String name) {
        RestaurantVO vo = dao.restNameSelect(name);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }

    // 상단 고정 상세 정보
    @GetMapping("/restaurant")
    public ResponseEntity<List<RestJoinVO>> joinInfo (@RequestParam String restaurantId){

        RestaurantVO vo = new RestaurantVO();
        vo.setRestId(restaurantId);
        List<RestJoinVO> list = dao.rtSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    //레스토랑 정보 조회
    @GetMapping("/restaurant/select")
    public ResponseEntity<RestaurantVO> restSelect (@RequestParam String name) {
        RestaurantVO vo = dao.restSelect(name);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }

    // POST : 매장 정보 업데이트
    @PostMapping("/business/restaurant/update")
    public ResponseEntity<Boolean> restUpdate(@RequestBody Map<String, RestaurantVO> data) {
        RestaurantVO vo = data.get("vo");
        boolean result = dao.restUpdate(vo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // POST : 매장 정보 등록
    @PostMapping("/business/restaurant/insert")
    public ResponseEntity<Boolean> restInsert(@RequestBody Map<String, RestaurantVO> data) {
        RestaurantVO vo = data.get("vo");
        boolean result = dao.restInsert(vo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
