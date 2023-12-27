package com.kh.miniprojectHD.dao;

import com.kh.miniprojectHD.common.Common;
import com.kh.miniprojectHD.vo.InquiryVO;
import com.kh.miniprojectHD.vo.MemberVO;
import com.kh.miniprojectHD.vo.ReservationVO;
import com.kh.miniprojectHD.vo.RestMenuVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pStmt = null;

    //예약조회
    public List<ReservationVO> resvSelect(String id, String stat) {
        List<ReservationVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection(); //연결
            stmt = conn.createStatement(); //정적인 sql 사용
            System.out.println(id);
            String sql = "SELECT RESERVATION_ID, RE.MEMBER_ID, RE.RESTAURANT_ID, R.RESTAURANT_NAME, RESERVATION_DATE, APPLICATION_DATE, CONFIRM_DATE, RESERVATION_REQUEST, RESERVATION_SEAT, RESERVATION_PEOPLE, RESERVATION_CONDITION " +
                        "FROM RESERVATION RE JOIN RESTAURANT R ON RE.RESTAURANT_ID = R.RESTAURANT_ID " +
                        "WHERE RE.MEMBER_ID = '" + id + "' AND RESERVATION_CONDITION = '" + stat + "' ORDER BY RESERVATION_DATE DESC ,RESERVATION_ID DESC";
            rs = stmt.executeQuery(sql); //
            while(rs.next()){ //읽을 행이 있으면 참
                int resvId = rs.getInt("RESERVATION_ID");
                String memId = rs.getString("MEMBER_ID");
                String restId = rs.getString("RESTAURANT_ID");
                String restName = rs.getString("RESTAURANT_NAME");
                Date resvDate = rs.getDate("RESERVATION_DATE");
                Date applicationDate = rs.getDate("APPLICATION_DATE");
                Date confirmDate = rs.getDate("CONFIRM_DATE");
                String resvRequest = rs.getString("RESERVATION_REQUEST");
                int resvSeat = rs.getInt("RESERVATION_SEAT");
                int resvPeople = rs.getInt("RESERVATION_PEOPLE");
                String resvStat = rs.getString("RESERVATION_CONDITION");
                LocalDateTime resvDateTime = rs.getTimestamp("RESERVATION_DATE").toLocalDateTime();
                String ResvDateTime = resvDateTime.format(DateTimeFormatter.ofPattern("a hh:mm"));
                ReservationVO vo = new ReservationVO(resvId,memId,restId,restName,resvDate,ResvDateTime,applicationDate,confirmDate,resvRequest,resvSeat,resvPeople,resvStat);
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
    // 예약 추가
    public boolean addRes(String restId,String memId,String resDate,String resReq,int resSeat,int resPeo) {
        int result = 0;
        String sql = "INSERT INTO RESERVATION(RESERVATION_ID,RESTAURANT_ID,MEMBER_ID,RESERVATION_DATE,RESERVATION_REQUEST,RESERVATION_SEAT,RESERVATION_PEOPLE) VALUES(SEQ_RES_ID.NEXTVAL,?,?,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),?,?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restId);
            pStmt.setString(2, memId);
            pStmt.setString(3, resDate);
            pStmt.setString(4, resReq);
            pStmt.setInt(5, resSeat);
            pStmt.setInt(6, resPeo);

            result = pStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
    // 예약 변경
    public boolean updateRes(String resDate,String resReq,int resSeat,int resPeo,int resId) {
        int result = 0;
        String sql = "UPDATE RESERVATION SET RESERVATION_DATE = TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),RESERVATION_REQUEST =?,RESERVATION_SEAT =? ,RESERVATION_PEOPLE =? WHERE RESERVATION_ID=?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, resDate);
            pStmt.setString(2, resReq);
            pStmt.setInt(3, resSeat);
            pStmt.setInt(4, resPeo);
            pStmt.setInt(5, resId);

            result = pStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }

    //사업자 예약조회
    public List<ReservationVO> businessResvSelect(String id, String stat) {
        List<ReservationVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection(); //연결
            stmt = conn.createStatement(); //정적인 sql 사용
            System.out.println(id);
            String sql = "SELECT * FROM RESERVATION WHERE RESTAURANT_ID = '" + id + "' AND RESERVATION_CONDITION ='"+ stat + "'  ORDER BY RESERVATION_DATE DESC ,RESERVATION_ID DESC";
            rs = stmt.executeQuery(sql); //
            while(rs.next()){ //읽을 행이 있으면 참

                int resvId = rs.getInt("RESERVATION_ID");
                String memId = rs.getString("MEMBER_ID");
                String restId = rs.getString("RESTAURANT_ID");
                Date resvDate = rs.getDate("RESERVATION_DATE");
                Date applicationDate = rs.getDate("APPLICATION_DATE");
                Date confirmDate = rs.getDate("CONFIRM_DATE");
                String resvRequest = rs.getString("RESERVATION_REQUEST");
                int resvSeat = rs.getInt("RESERVATION_SEAT");
                int resvPeople = rs.getInt("RESERVATION_PEOPLE");
                String resvStat = rs.getString("RESERVATION_CONDITION");
                LocalDateTime resvDateTime = rs.getTimestamp("RESERVATION_DATE").toLocalDateTime();
                String ResvDateTime = resvDateTime.format(DateTimeFormatter.ofPattern("a hh:mm"));
                ReservationVO vo = new ReservationVO(resvId,memId,restId,null,resvDate,ResvDateTime,applicationDate,confirmDate,resvRequest,resvSeat,resvPeople,resvStat);
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

    //예약 상태 변경
    public boolean resvStatUpdate(ReservationVO[] vo) {
        String sql = "UPDATE RESERVATION SET RESERVATION_CONDITION =?  WHERE RESERVATION_ID=?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            for (ReservationVO resv : vo) {
                pStmt.setString(1,"예약확정");
                pStmt.setInt(2, resv.getResvId());
                pStmt.executeUpdate();
            }


            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

        return false;
    }

    //예약취소
    public Boolean resvDelete(int id) {
        try {
            String sql = "DELETE FROM RESERVATION WHERE RESERVATION_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1,id);
            pStmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        return false;

    }

    public String resvId(String memId, String restId, String date){
        System.out.println("예약번호 조회 컨트롤러");
        String resvId = "";

        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT RESERVATION_ID FROM RESERVATION WHERE MEMBER_ID = '"+ memId +"' AND RESTAURANT_ID = '"+restId+"' AND TO_CHAR(RESERVATION_DATE, 'YYYY-MM-DD') = '" + date + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                resvId = rs.getString("RESERVATION_ID");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);

        System.out.println(memId + restId + date + resvId);

        return resvId;
    }
}

