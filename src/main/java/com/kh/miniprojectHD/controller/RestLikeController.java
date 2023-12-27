package com.kh.miniprojectHD.controller;

import com.kh.miniprojectHD.dao.RestLikeDAO;
import com.kh.miniprojectHD.dao.ReviewDAO;
import com.kh.miniprojectHD.vo.RestLikeVO;
import com.kh.miniprojectHD.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class RestLikeController {
    @Autowired
    private RestLikeDAO dao;
    //Get : 찜가게 조회
    @GetMapping("/restLike")
    public ResponseEntity<List<RestLikeVO>> restLikeList (@RequestParam String name) {
        List<RestLikeVO> list = dao.restLikeSelect(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 찜 추가
    @PostMapping("/restaurant/add/restLike")
    public ResponseEntity<Boolean> addRestLike(@RequestBody Map<String, String> likeData) {
        String getRestId = likeData.get("restId");
        String getMemberId = likeData.get("memberId");
        String getName = likeData.get("restName");
        boolean list = dao.addRestLike(getRestId, getMemberId,getName);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 찜 삭제
    @PostMapping("/restaurant/del/restLike")
    public ResponseEntity<Boolean> delRestLike(@RequestBody Map<String, String> delData) {
        String getRestId = delData.get("restId");
        String getMemberId = delData.get("memberId");

        boolean list = dao.delRestLike(getRestId, getMemberId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //찜 개수 조회
    @GetMapping("/restLike/cnt")
    public ResponseEntity<Integer> likeCntSelect (@RequestParam String id) {
        Integer likeCnt = dao.likeCntSelect(id);
        return new ResponseEntity<>(likeCnt, HttpStatus.OK);
    }
}
