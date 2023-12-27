package com.kh.miniprojectHD.controller;


import com.kh.miniprojectHD.dao.InquiryDAO;
import com.kh.miniprojectHD.vo.InquiryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class InquiryController {

    @Autowired
    private InquiryDAO dao;
    //Get : 문의 조회
    @GetMapping("/inquiry")
    public ResponseEntity<List<InquiryVO>> inquiryList (@RequestParam String name) {
        List<InquiryVO> list = dao.inquirySelect(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //문의 추가
    @PostMapping("/restaurant/add/inquiry")
    public ResponseEntity<Boolean> addInquiry(@RequestBody Map<String, String> inquiryData){
        String getRestId = inquiryData.get("restId");
        String getMemberId = inquiryData.get("memberId");
        String getTitle = inquiryData.get("title");
        String getContent = inquiryData.get("content");
        String getImage = inquiryData.get("image");

//        EmailService es = new EmailService();
//        MemberDAO mDao = new MemberDAO();
//        String to = mDao.memberEmail(getMemberId);
//        String nickname = mDao.memberNickname(getMemberId);
//        es.inquirySendMessage(to, nickname, getRestName);

        boolean list = dao.addInquiry(getRestId, getMemberId, getTitle, getContent,getImage);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //사업자페이지 문의조회
    @GetMapping("/business/inquiry")
    public ResponseEntity<List<InquiryVO>> businessInquiryList (@RequestParam String id) {
        List<InquiryVO> list = dao.businessInquiry(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //사업자페이지 답변등록
    @PostMapping("/business/inquiry/answer/update")
    public ResponseEntity<Boolean> updateInquiryAnswer(@RequestBody Map<String, InquiryVO> inquiryData) {
        InquiryVO vo = inquiryData.get("vo");
        boolean result = dao.inquiryAnswerUpdate(vo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    //마이페이지 문의 수정
    @PostMapping("/inquiry/update")
    public ResponseEntity<Boolean> updateInquiry(@RequestBody Map<String, InquiryVO> inquiryData) {
        InquiryVO vo = inquiryData.get("vo");
        boolean result = dao.inquiryAnswerUpdate(vo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }




}
