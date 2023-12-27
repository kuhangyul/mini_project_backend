package com.kh.miniprojectHD.controller;

import com.kh.miniprojectHD.dao.SearchDAO;
import com.kh.miniprojectHD.vo.RestListVO;
import com.kh.miniprojectHD.vo.ReviewVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class SearchController {
    @Autowired
    private SearchDAO dao = new SearchDAO();
    @PostMapping("/restaurantList")
    public ResponseEntity<List<RestListVO>> restList(@RequestBody Restaurant rst){

        List<RestListVO> list = dao.searchAndFilter(
                rst.getKeyword(),
                rst.getRegion(),
                rst.getCategory(),
                rst.getPrice(),
                rst.getRating()
        );
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/restaurantList")
    public ResponseEntity<List<RestListVO>> popularRest(@RequestParam("popular") String popular){
        System.out.println("인기매장 리스트 컨트롤러");
        List<RestListVO> list = dao.popularList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/weeklyTop3Rest")
    public ResponseEntity<List<RestListVO>> weeklyTop3Rest(){
        System.out.println("주간 탑3 레스토랑 리스트 컨트롤러");
        List<RestListVO> list = dao.weeklyTop3Rest();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/monthlyTop3Rest")
    public ResponseEntity<List<RestListVO>> monthlyTop3Rest(){
        System.out.println("월간 탑3 레스토랑 리스트 컨트롤러");
        List<RestListVO> list = dao.monthlyTop3Rest();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/weeklyTop3Review")
    public ResponseEntity<List<ReviewVO>> weeklyTop3Review(){
        System.out.println("주간 탑3 리뷰 리스트 컨트롤러");
        List<ReviewVO> list = dao.weeklyTop3Review();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/carouselPopularList")
    public ResponseEntity<List<RestListVO>> carouselPopularList(){
        System.out.println("인기매장 리스트 캐러셀 컨트롤러 작동");
        List<RestListVO> list = dao.carouselPopularList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/carouselReviewList")
    public ResponseEntity<List<ReviewVO>> carouselReviewList(){
        System.out.println("인기리뷰 리스트 캐러셀 컨트롤러 작동");
        List<ReviewVO> list = dao.carouselReviewList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Getter
    @Setter
    private static class Restaurant {
        private String[] keyword;
        private Map<String, String[]> region;
        private String[] category;
        private String[] price;
        private String rating;
    }

//    @Getter
//    @Setter
//    private static class searchKeyWord {
//        private String[] keyword;
//    }
}