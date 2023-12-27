package com.kh.miniprojectHD.controller;

import com.kh.miniprojectHD.dao.ReviewLikeDAO;
import com.kh.miniprojectHD.vo.MemberVO;
import com.kh.miniprojectHD.vo.ReviewLikeVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class ReviewLikeController {
    // 공감 리스트 조회
    @GetMapping("/review/liked")
    public ResponseEntity<List<ReviewLikeVO>> revLiked(@RequestParam String memberId){
        ReviewLikeDAO dao = new ReviewLikeDAO();
        MemberVO vo = new MemberVO();
        vo.setMemId(memberId);

        List<ReviewLikeVO> list = dao.revLikedSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 공감 등록
    @PostMapping("/restaurant/add/revLike")
    public ResponseEntity<Boolean> addRevLike(@RequestBody Map<String, String> likeData) {
        int getRevId = Integer.parseInt(likeData.get("revId"));
        String getMemberId = likeData.get("memberId");

        ReviewLikeDAO dao = new ReviewLikeDAO();
        boolean list = dao.addRevLike(getRevId, getMemberId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 공감 삭제
    @PostMapping("/restaurant/del/revLike")
    public ResponseEntity<Boolean> delRevLike(@RequestBody Map<String, String> delData) {
        int getRevId = Integer.parseInt(delData.get("revId"));
        String getMemberId = delData.get("memberId");

        ReviewLikeDAO dao = new ReviewLikeDAO();
        boolean list = dao.delRevLike(getRevId, getMemberId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
