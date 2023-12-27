package com.kh.miniprojectHD.controller;

import com.kh.miniprojectHD.dao.MemberDAO;
import com.kh.miniprojectHD.dao.RestaurantDAO;
import com.kh.miniprojectHD.dao.RestaurantInfoDAO;
import com.kh.miniprojectHD.vo.MemberVO;
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

public class RestaurantInfoController {
    @Autowired
    private RestaurantInfoDAO dao; //autowired로 의존성 주입
// 매장 상세 정보 불러오기
    @GetMapping("/restaurant/info")
    public ResponseEntity<List<RestaurantInfoVO>> restaurantInfo(@RequestParam String restaurantId){
        RestaurantVO vo = new RestaurantVO();
        vo.setRestId(restaurantId);

        List<RestaurantInfoVO> list = dao.infoSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/business/restaurantInfo")
    public ResponseEntity<RestaurantInfoVO> restaurantAllInfo(@RequestParam String restId){
        RestaurantInfoVO vo =  dao.restInfoSelect(restId);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }

    // POST : 매장 상세정보 업데이트
    @PostMapping("/business/restInfo/update")
    public ResponseEntity<Boolean> restInfoUpdate(@RequestBody Map<String, RestaurantInfoVO> data) {
        RestaurantInfoVO vo = data.get("vo");
        boolean result = dao.restInfoUpdate(vo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // POST : 매장 상세정보 등록
    @PostMapping("/business/restInfo/insert")
    public ResponseEntity<Boolean> restInfoInsert(@RequestBody Map<String, RestaurantInfoVO> data) {
        RestaurantInfoVO vo = data.get("vo");
        boolean result = dao.restInfoInsert(vo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
