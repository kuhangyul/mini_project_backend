package com.kh.miniprojectHD.controller;

import com.kh.miniprojectHD.dao.BizMemberDAO;
import com.kh.miniprojectHD.vo.BizMemberVO;
import com.kh.miniprojectHD.vo.MemberVO;
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
public class BizMemberController {
    @Autowired
    private BizMemberDAO dao;
    //Get :회원조회
    @GetMapping("/business/member")
    public ResponseEntity<List<BizMemberVO>> bizMemberList(@RequestParam String name){
        List<BizMemberVO> list = dao.bizMemberSelect(name);
        return new ResponseEntity<>(list, HttpStatus.OK);

    }
    // POST : 로그인
    @PostMapping("/business/login")
    public ResponseEntity<Boolean> bizMemberLogin(@RequestBody Map<String, String> loginData) {
        String user = loginData.get("id");
        String pwd = loginData.get("pwd");
        boolean result = dao.loginCheck(user, pwd);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //신규 사업자회원가입
    @PostMapping("/newBizMember")
    public ResponseEntity<Boolean> bizMemberInsert(@RequestBody  bizMemberInfo bmi){
        System.out.println("사업자 회원가입 컨트롤러 작동");
//        boolean isTrue = dao.bizMemberInsert(bmi.getId(), bmi.getPwd(), bmi.getName(), bmi.getEmail(), bmi.getPhone());
        boolean isTrue = dao.bizMemberInsert(bmi.id, bmi.pwd, bmi.name, bmi.email, bmi.phone);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    //사업자 회원 중복체크
    @PostMapping("/checkBizMember")
    public ResponseEntity<Boolean> memberCheck(@RequestBody Map<String, String> id) {
        System.out.println("중복ID 체크 컨트롤러 작동");
        String checkId = id.get("id");
        boolean isTrue = dao.regBizMemberCheck(checkId);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    //JSON 파싱을 위한 잭슨 라이브러리
    @Getter
    @Setter
    private static class bizMemberInfo{
        private String id;
        private String pwd;
        private String name;
        private String email;
        private String phone;
    }

}
