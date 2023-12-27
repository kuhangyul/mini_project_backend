package com.kh.miniprojectHD.controller;

import com.kh.miniprojectHD.EmailService;
import com.kh.miniprojectHD.dao.BizMemberDAO;
import com.kh.miniprojectHD.dao.MemberDAO;
import com.kh.miniprojectHD.dao.ReservationDAO;
import com.kh.miniprojectHD.dao.RestaurantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class EmailController {
    @Autowired
    private EmailService es = new EmailService();

    //회원가입 시 이메일 인증 컨트롤러
    @PostMapping("/signup/email")
    public ResponseEntity<String> mailConfirm(@RequestBody Map<String, String> email) throws Exception {
        System.out.println("회원가입 이메일 인증코드 전송");
        String mail = email.get("email");
        String authCode = es.authCodeSendMessage(mail);
        return new ResponseEntity<>(authCode, HttpStatus.OK);
    }

    //사업자 회원가입 시 이메일 인증 컨트롤러
    @PostMapping("/bizSignup/email")
    public ResponseEntity<String> bizMailConfirm(@RequestBody Map<String, String> email) throws Exception {
        System.out.println("사업자 회원가입 이메일 인증코드 전송");
        String mail = email.get("email");
        String authCode = es.bizAuthCodeSendMessage(mail);
        return new ResponseEntity<>(authCode, HttpStatus.OK);
    }

    @PostMapping("/findId")
    public ResponseEntity<Boolean> mailFindId(@RequestBody Map<String, String> email) throws Exception {
        System.out.println("이메일 ID찾기 컨트롤러 작동");
        String mail = email.get("email");
        Boolean result = es.findIdSendMessage(mail);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/findPw")
    public ResponseEntity<Boolean> mailFindPw(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("PW변경 컨트롤러 작동");
        String mail = object.get("email");
        String id = object.get("id");
        Boolean result = es.findPwSendMessage(mail, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/restaurant/add/inquiry/sendMsg")
    public ResponseEntity<Boolean> mailSendMemberInquiry(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("문의 등록 메일 컨트롤러 작동");
        String restName = object.get("restName");
        String id = object.get("memberId");
        MemberDAO dao = new MemberDAO();
        String memberNickname = dao.memberNickname(id);
        String memberEmail = dao.memberEmail(id);
        Boolean result = es.inquirySendMessage(memberEmail, memberNickname, restName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/restaurant/add/inquiry/sendMsgBiz")
    public ResponseEntity<Boolean> mailSendBizMemberInquiry(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("사업자 회원에게 문의 등록 알림 메일 발송 컨트롤러 작동");
        String restName = object.get("restName");
        String restId = object.get("restId");
        String memId = object.get("memberId");
        MemberDAO dao = new MemberDAO();
        String memberNickname = dao.memberNickname(memId);
        BizMemberDAO bDao = new BizMemberDAO();
        String bizMemId = bDao.bizMemberId(restId);
        String bizMemEmail = bDao.bizMemberEmail(bizMemId);
        Boolean result = es.inquirySendBizMessage(bizMemEmail, bizMemId, memberNickname, restName);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PostMapping("/restaurant/inquiry/answerMsg")
    public ResponseEntity<Boolean> mailSendMemberInquiryAnswer(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("문의 답변 메일 컨트롤러 작동");
        String restId = object.get("restId");
        String id = object.get("memberId");
        MemberDAO dao = new MemberDAO();
        String memberNickname = dao.memberNickname(id);
        String memberEmail = dao.memberEmail(id);

        RestaurantDAO rDao = new RestaurantDAO();
        String restName = rDao.restName(restId);

        Boolean result = es.inquirySendAnswerMessage(memberEmail, memberNickname, restName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/restaurant/inquiry/answerMsgBiz")
    public ResponseEntity<Boolean> mailSendBizMemberInquiryAnswer(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("사업자 회원에게 문의 답변 메일 발송 컨트롤러 작동");

        String restId = object.get("restId");
        String memId = object.get("memberId");
        MemberDAO dao = new MemberDAO();
        String memberNickname = dao.memberNickname(memId);
        BizMemberDAO bDao = new BizMemberDAO();
        String bizMemId = bDao.bizMemberId(restId);
        String bizMemEmail = bDao.bizMemberEmail(bizMemId);

        RestaurantDAO rDao = new RestaurantDAO();
        String restName = rDao.restName(restId);

        Boolean result = es.inquirySendBizAnswerMessage(bizMemEmail, bizMemId, memberNickname, restName);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }


    //회원측 예약 이메일 컨트롤러
    @PostMapping("/reservation/email")
    public ResponseEntity<Boolean> mailSendMemberReservation(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("회원에게 예약등록 이메일 전송 컨트롤러 작동");
        String restId = object.get("restId");
        String memId = object.get("memberId");
        String date = object.get("resDate");
        MemberDAO dao = new MemberDAO();
        String memberNickname = dao.memberNickname(memId);
        String memberEmail = dao.memberEmail(memId);
        RestaurantDAO rDao = new RestaurantDAO();
        String restName = rDao.restName(restId);

        Boolean result = es.reservationSendMessage(memberEmail, memberNickname, restName, date);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/reservation/update/email")
    public ResponseEntity<Boolean> mailSendMemberReservationUpdate(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("회원에게 예약변경 이메일 전송 컨트롤러 작동");
        String restId = object.get("restId");
        String restName = object.get("restName");
        String memId = object.get("memberId");
        String date = object.get("resDate");
        String time = object.get("resTime");
        MemberDAO dao = new MemberDAO();
        String memberNickname = dao.memberNickname(memId);
        String memberEmail = dao.memberEmail(memId);
//        RestaurantDAO rDao = new RestaurantDAO();
//        String restName = rDao.restName(restId);

        Boolean result = es.reservationSendUpdateMessage(memberEmail, memberNickname, restName, date, time);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/reservation/confirm/email")
    public ResponseEntity<Boolean> mailSendMemberReservationConfirm(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("회원에게 예약확정 이메일 전송 컨트롤러 작동");
        String restId = object.get("restId");
        String memId = object.get("memberId");
        String date = object.get("resDate");
        String time = object.get("resTime");
        String resDate = date + " " + time;

        MemberDAO dao = new MemberDAO();
        String memberNickname = dao.memberNickname(memId);
        String memberEmail = dao.memberEmail(memId);

        RestaurantDAO rDao = new RestaurantDAO();
        String restName = rDao.restName(restId);

        Boolean result = es.reservationSendConfirmMessage(memberEmail, memberNickname, restName, resDate);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/reservation/cancel/email")
    public ResponseEntity<Boolean> mailSendMemberReservationCancel(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("회원에게 예약취소 이메일 전송 컨트롤러 작동");
        String restId = object.get("restId");
        String restName = object.get("restName");
        String memId = object.get("memberId");
        String date = object.get("resDate");
        String time = object.get("resTime");
        MemberDAO dao = new MemberDAO();
        String memberNickname = dao.memberNickname(memId);
        String memberEmail = dao.memberEmail(memId);
//        RestaurantDAO rDao = new RestaurantDAO();
//        String restName = rDao.restName(restId);

        Boolean result = es.reservationSendCancelMessage(memberEmail, memberNickname, restName, date, time);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/reservation/reject/email")
    public ResponseEntity<Boolean> mailSendMemberReservationReject(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("회원에게 예약요청 거절 이메일 전송 컨트롤러 작동");
        String restId = object.get("restId");
        String memId = object.get("memberId");
        String date = object.get("resDate");
        String time = object.get("resTime");
        String resDate = date + " " + time;
        String reason = object.get("reason");
        MemberDAO dao = new MemberDAO();
        String memberNickname = dao.memberNickname(memId);
        String memberEmail = dao.memberEmail(memId);
        RestaurantDAO rDao = new RestaurantDAO();
        String restName = rDao.restName(restId);

        Boolean result = es.reservationSendRejectMessage(memberEmail, memberNickname, restName, resDate, reason);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //사업자 측 예약 이메일 컨트롤러
    @PostMapping("/reservation/emailBiz")
    public ResponseEntity<Boolean> mailSendBizMemberReservation(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("사업자 회원에게 예약요청 알림 컨트롤러 작동");
        String restId = object.get("restId");
        String memId = object.get("memberId");
        String date = object.get("resDate");
        MemberDAO dao = new MemberDAO();
        String memberName = dao.memberName(memId);

        RestaurantDAO rDao = new RestaurantDAO();
        String restName = rDao.restName(restId);

        BizMemberDAO bDao = new BizMemberDAO();
        String bizMemId = bDao.bizMemberId(restId);
        String bizMemEmail = bDao.bizMemberEmail(bizMemId);

        Boolean result = es.reservationSendBizMessage(bizMemEmail, memberName, bizMemId, restName, date);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PostMapping("/reservation/update/emailBiz")
    public ResponseEntity<Boolean> mailSendBizMemberReservationUpdate(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("사업자 회원에게 예약변경 알림 컨트롤러 작동");
        String restId = object.get("restId");
        String restName = object.get("restName");
        String memId = object.get("memberId");
        String date = object.get("resDate");
        String time = object.get("resTime");
        String reservationId = object.get("resvId");
        MemberDAO dao = new MemberDAO();
        String memberName = dao.memberName(memId);

//        RestaurantDAO rDao = new RestaurantDAO();
//        String restName = rDao.restName(restId);

        BizMemberDAO bDao = new BizMemberDAO();
        String bizMemId = bDao.bizMemberId(restId);
        String bizMemEmail = bDao.bizMemberEmail(bizMemId);

        Boolean result = es.reservationSendBizUpdateMessage(bizMemEmail, memberName, bizMemId, restName, date, time,reservationId);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PostMapping("/reservation/confirm/emailBiz")
    public ResponseEntity<Boolean> mailSendBizMemberReservationConfirm(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("사업자 회원에게 예약확정 알림 컨트롤러 작동");
        String restId = object.get("restId");
        String memId = object.get("memberId");
        String date = object.get("resDate");
        String time = object.get("resTime");
        String reservationId = object.get("resvId");
        String resvDate = date + " " + time;
        MemberDAO dao = new MemberDAO();
        String memberName = dao.memberName(memId);

        RestaurantDAO rDao = new RestaurantDAO();
        String restName = rDao.restName(restId);

        BizMemberDAO bDao = new BizMemberDAO();
        String bizMemId = bDao.bizMemberId(restId);
        String bizMemEmail = bDao.bizMemberEmail(bizMemId);


        Boolean result = es.reservationSendBizConfirmMessage(bizMemEmail, memberName, bizMemId, restName, resvDate, reservationId);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PostMapping("/reservation/cancel/emailBiz")
    public ResponseEntity<Boolean> mailSendBizMemberReservationCancel(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("사업자 회원에게 예약취소 알림 컨트롤러 작동");
        String restId = object.get("restId");
        String restName = object.get("restName");
        String memId = object.get("memberId");
        String date = object.get("resDate");
        String time = object.get("resTime");
        String reservationId = object.get("resvId");
        MemberDAO dao = new MemberDAO();
        String memberName = dao.memberName(memId);

        BizMemberDAO bDao = new BizMemberDAO();
        String bizMemId = bDao.bizMemberId(restId);
        String bizMemEmail = bDao.bizMemberEmail(bizMemId);


        Boolean result = es.reservationSendBizCancelMessage(bizMemEmail, memberName, bizMemId, restName, date, time,reservationId);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PostMapping("/reservation/reject/emailBiz")
    public ResponseEntity<Boolean> mailSendBizMemberReservationReject(@RequestBody Map<String, String> object) throws Exception {
        System.out.println("사업자 회원에게 예약거절 알림 컨트롤러 작동");
        String restId = object.get("restId");
        String memId = object.get("memberId");
        String date = object.get("resDate");
        String time = object.get("resTime");
        String reservationId = object.get("resvId");
        String reason = object.get("reason");
        String resDate = date + " " + time;
        MemberDAO dao = new MemberDAO();
        String memberName = dao.memberName(memId);

        RestaurantDAO rDao = new RestaurantDAO();
        String restName = rDao.restName(restId);

        BizMemberDAO bDao = new BizMemberDAO();
        String bizMemId = bDao.bizMemberId(restId);
        String bizMemEmail = bDao.bizMemberEmail(bizMemId);


        Boolean result = es.reservationSendBizRejectMessage(bizMemEmail, memberName, bizMemId, restName, resDate, reservationId, reason);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }





}
