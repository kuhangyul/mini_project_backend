package com.kh.miniprojectHD.controller;

import com.kh.miniprojectHD.dao.RestaurantDAO;
import com.kh.miniprojectHD.dao.ReviewDAO;
import com.kh.miniprojectHD.vo.InquiryVO;
import com.kh.miniprojectHD.vo.RestaurantVO;
import com.kh.miniprojectHD.vo.ReviewJoinVO;
import com.kh.miniprojectHD.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class ReviewController {
    @Autowired
    private ReviewDAO dao;
    //Get : 리뷰 조회
    @GetMapping("/review")
    public ResponseEntity<List<ReviewVO>> reviewList (@RequestParam String name) {
        List<ReviewVO> list = dao.reviewSelect(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 매장 페이지 리뷰 정보 불러오기
    @GetMapping("/restaurant/review")
    public ResponseEntity<List<ReviewJoinVO>> restaurantReview(@RequestParam String restaurantId){
        RestaurantVO vo = new RestaurantVO();
        vo.setRestId(restaurantId);

        List<ReviewJoinVO> list = dao.reviewSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 리뷰 추가
    @PostMapping("/restaurant/add/review")
    public ResponseEntity<Boolean> addReview(@RequestBody Map<String, String> reviewData) {
        String getRestId = reviewData.get("restId");
        String getMemberId = reviewData.get("memberId");
        String getTitle = reviewData.get("title");
        String getContent = reviewData.get("content");
        Double getRating = Double.parseDouble(reviewData.get("rating"));
        String getImage = reviewData.get("image");

        boolean list = dao.addReview(getRestId, getMemberId, getTitle, getContent, getRating,getImage);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 리뷰 상세 정보
    @GetMapping("/review/detail")
    public ResponseEntity<List<ReviewJoinVO>> reviewDetail(@RequestParam int reviewId) {
        ReviewVO vo = new ReviewVO();
        vo.setReviewId(reviewId);

        List<ReviewJoinVO> list = dao.detailSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PostMapping("/review/update")
    public ResponseEntity<Boolean> updateRev(@RequestBody Map<String, String> revData) {
        String getTitle = revData.get("title");
        String getContent = revData.get("content");
        Double getRating = Double.parseDouble(revData.get("rating"));
        String getImage = revData.get("image");
        int getId= Integer.parseInt(revData.get("reviewId"));

        boolean list = dao.updateReview(getTitle,getContent,getRating,getImage,getId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/review/delete")
    public ResponseEntity<Boolean> delRev(@RequestBody Map<String, String> revData) {
        int revId = Integer.parseInt(revData.get("reviewId"));

        boolean list = dao.revDelete(revId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
