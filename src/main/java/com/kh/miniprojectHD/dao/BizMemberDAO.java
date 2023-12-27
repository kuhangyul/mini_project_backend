package com.kh.miniprojectHD.dao;

import com.kh.miniprojectHD.common.Common;
import com.kh.miniprojectHD.vo.BizMemberVO;
import com.kh.miniprojectHD.vo.MemberVO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BizMemberDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pStmt = null;
    // 로그인 체크
    public boolean loginCheck(String id, String pwd) {
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement(); // Statement 객체 얻기
            String sql ="SELECT * FROM B_MEMBER WHERE MEMBER_ID = "+ "'" + id + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) { // 읽은 데이타가 있으면 true
                String sqlId = rs.getString("MEMBER_ID"); // 쿼리문 수행 결과에서 ID값을 가져 옴
                String sqlPwd = rs.getString("PASSWORD");
                System.out.println("ID : " + sqlId);
                System.out.println("PWD : " + sqlPwd);
                if(id.equals(sqlId) && pwd.equals(sqlPwd)) {
                    Common.close(rs);
                    Common.close(stmt);
                    Common.close(conn);
                    return true;
                }
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //회원 조회
    public List<BizMemberVO> bizMemberSelect(String id) {
        List<BizMemberVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection(); //연결
            stmt = conn.createStatement(); //정적인 sql 사용
            System.out.println(id);
            String sql= "SELECT * FROM B_MEMBER WHERE MEMBER_ID = " + "'" + id + "'";
            rs = stmt.executeQuery(sql); //
            while(rs.next()){ //읽을 행이 있으면 참
                String memId = rs.getString("MEMBER_ID");
                String pwd = rs.getString("PASSWORD");
                String name = rs.getString("NAME");
                String eMail = rs.getString("EMAIL");
                String phoneNum = rs.getString("PHONE_NUM");
                Date joinDate = rs.getDate("JOIN_DATE");

                BizMemberVO vo = new BizMemberVO(memId,pwd,name,eMail,phoneNum,joinDate); //하나의 행(레코드)에 대한 정보저장을 위한 객체생성
                list.add(vo);

            }
            Common.close(rs); // 연결과 역순으로 해제
            Common.close(stmt);
            Common.close(conn);
        }catch(Exception e){
            e.printStackTrace();

        }
        return list;
    }

    public boolean bizMemberInsert(String id, String pwd, String name, String email, String phone){
        int result = 0;
        String sql = "INSERT INTO B_MEMBER(MEMBER_ID, PASSWORD, NAME, EMAIL, PHONE_NUM) VALUES(?,?,?,?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, id);
            pStmt.setString(2, pwd);
            pStmt.setString(3, name);
            pStmt.setString(4, email);
            pStmt.setString(5, phone);

            result = pStmt.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

        if(result == 1) return true;
        else return false;
    }

    public boolean regBizMemberCheck(String id) {
        boolean isNotReg = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM B_MEMBER WHERE MEMBER_ID = " + "'" + id +"'";
            rs = stmt.executeQuery(sql);
            if(rs.next()) isNotReg = false;
            else isNotReg = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isNotReg; // 가입 되어 있으면 false, 가입이 안되어 있으면 true
    }

    public String bizMemberId(String restId){
        String memberId = "";

        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT MEMBER_ID FROM RESTAURANT WHERE RESTAURANT_ID = " + "'" + restId +"'";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                memberId = rs.getString("MEMBER_ID");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);

        return memberId;
    }

    public String bizMemberEmail(String bizMemId){
        String bizMemberEmail = "";
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT EMAIL FROM B_MEMBER WHERE MEMBER_ID = " + "'" + bizMemId +"'";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                bizMemberEmail = rs.getString("EMAIL");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);

        return bizMemberEmail;
    }

}
