package com.kh.miniprojectHD.controller;

import com.kh.miniprojectHD.dao.MemberDAO;
import com.kh.miniprojectHD.dao.RestMenuDAO;
import com.kh.miniprojectHD.dao.RestaurantDAO;
import com.kh.miniprojectHD.vo.RestMenuVO;
import com.kh.miniprojectHD.vo.RestaurantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class RestMenuController {


    // 메뉴 정보 불러오기
    @GetMapping("/restaurant/menu")
    public ResponseEntity<List<RestMenuVO>> restaurantMenu(@RequestParam String restaurantId){
        RestMenuDAO dao = new RestMenuDAO();
        RestaurantVO vo = new RestaurantVO();
        vo.setRestId(restaurantId);

        List<RestMenuVO> list = dao.menuSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 사업자 페이지 메뉴 정보 불러오기
    @GetMapping("/business/restaurant/menu")
    public ResponseEntity<List<RestMenuVO>>businessRestMenu(@RequestParam String restaurantId){
        RestMenuDAO dao = new RestMenuDAO();
        List<RestMenuVO> list = dao.bizMenuSelect(restaurantId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //메뉴 추가
    @PostMapping("/business/restaurant/menu/add")
    public ResponseEntity<Boolean> addMenu(@RequestBody Map<String, String> menuData) {
        String restId = menuData.get("restId");
        String menuName = menuData.get("menuName");
        int menuPrice =Integer.parseInt(menuData.get("menuPrice"));
        String menuDesc = menuData.get("menuDesc");
        String menuImgFileName = menuData.get("menuImgFileName");
        RestMenuDAO dao = new RestMenuDAO();
        boolean list = dao.addMenu(restId, menuName, menuPrice, menuDesc,menuImgFileName);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //메뉴 삭제
    @PostMapping("/business/restaurant/menu/delete")
    public ResponseEntity<Boolean> delMenu(@RequestBody Map<String, String> menuData) {
        int menuId = Integer.parseInt(menuData.get("menuId"));

        RestMenuDAO dao = new RestMenuDAO();
        boolean list = dao.menuDelete(menuId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //메뉴 수정
    @PostMapping("/business/restaurant/menu/update")
    public ResponseEntity<Boolean> updateMenu(@RequestBody Map<String, RestMenuVO[]> menuData) {
        RestMenuVO[] menuList = menuData.get("vo");
        RestMenuDAO dao = new RestMenuDAO();
        boolean list = dao.menuUpdate(menuList);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
