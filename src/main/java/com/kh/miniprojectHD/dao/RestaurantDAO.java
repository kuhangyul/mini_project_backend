package com.kh.miniprojectHD.dao;

import com.kh.miniprojectHD.common.Common;
import com.kh.miniprojectHD.vo.ReservationVO;
import com.kh.miniprojectHD.vo.RestJoinVO;
import com.kh.miniprojectHD.vo.RestaurantInfoVO;
import com.kh.miniprojectHD.vo.RestaurantVO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pStmt = null;

    //매장 이름 조회
    public RestaurantVO restNameSelect (String id) {
        RestaurantVO vo = null;
        try {
            conn = Common.getConnection(); //연결
            stmt = conn.createStatement(); //정적인 sql 사용
            String sql= "SELECT * FROM RESTAURANT WHERE RESTAURANT_ID = '"+id+"'";
            rs = stmt.executeQuery(sql); //
            while(rs.next()){ //읽을 행이 있으면 참
                String memId = rs.getString("MEMBER_ID");
                String restId = rs.getString("RESTAURANT_ID");
                String restName = rs.getString("RESTAURANT_NAME");
                Date restDate = rs.getDate("RESTAURANT_DATE");
                int isAvailable = rs.getInt("RESERVATION_POSSIBILITY");
                String category = rs.getString("RESTAURANT_CATEGORY");
                vo = new RestaurantVO(memId,restId,restName,restDate,isAvailable,category);

            }
            Common.close(rs); // 연결과 역순으로 해제
            Common.close(stmt);
            Common.close(conn);
        }catch(Exception e){
            e.printStackTrace();

        }
        return vo;
    }

    // 매장 리스트 상단 고정 매장 정보
    public List<RestJoinVO> rtSelect(RestaurantVO restaurantVO){
        List<RestJoinVO> list = new ArrayList<>();
        try{
            String sql = "SELECT RESTAURANT_NAME,RESTAURANT_PHONE,RESTAURANT_ADDR,TRUNC(AVG(RATING),1),RESTAURANT_IMAGE_FILE_NAME FROM RESTAURANT JOIN RESTAURANT_INFO ON RESTAURANT.RESTAURANT_ID = RESTAURANT_INFO.RESTAURANT_ID LEFT JOIN REVIEW ON RESTAURANT.RESTAURANT_ID = REVIEW.RESTAURANT_ID WHERE RESTAURANT.RESTAURANT_ID =? GROUP BY RESTAURANT_NAME,RESTAURANT_PHONE,RESTAURANT_ADDR,RESTAURANT_IMAGE_FILE_NAME";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restaurantVO.getRestId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                String name = rs.getString("RESTAURANT_NAME");
                String phone = rs.getString("RESTAURANT_PHONE");
                String addr = rs.getString("RESTAURANT_ADDR");
                double avgRating = rs.getDouble("TRUNC(AVG(RATING),1)");
                String image = rs.getString("RESTAURANT_IMAGE_FILE_NAME");
                RestJoinVO vo = new RestJoinVO();
                vo.setName(name);
                vo.setPhone(phone);
                vo.setAddr(addr);
                vo.setAvgRating(avgRating);
                vo.setImage(image);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //매장 이름 조회
    public RestaurantVO restSelect (String id) {
        RestaurantVO vo = null;
        try {
            conn = Common.getConnection(); //연결
            stmt = conn.createStatement(); //정적인 sql 사용
            String sql= "SELECT * FROM RESTAURANT WHERE MEMBER_ID = '"+id+"'";
            rs = stmt.executeQuery(sql); //
            while(rs.next()){ //읽을 행이 있으면 참
                String memId = rs.getString("MEMBER_ID");
                String restId = rs.getString("RESTAURANT_ID");
                String restName = rs.getString("RESTAURANT_NAME");
                Date restDate = rs.getDate("RESTAURANT_DATE");
                int isAvailable = rs.getInt("RESERVATION_POSSIBILITY");
                String category = rs.getString("RESTAURANT_CATEGORY");
                vo = new RestaurantVO(memId,restId,restName,restDate,isAvailable,category);

            }
            Common.close(rs); // 연결과 역순으로 해제
            Common.close(stmt);
            Common.close(conn);
        }catch(Exception e){
            e.printStackTrace();

        }
        return vo;
    }

    //매장 정보 업데이트
    public Boolean restUpdate(RestaurantVO vo) {
        String sql = " UPDATE RESTAURANT SET RESTAURANT_ID=?, RESTAURANT_NAME=?,RESERVATION_POSSIBILITY=?,RESTAURANT_CATEGORY=? WHERE MEMBER_ID=?";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, vo.getRestId());
            pStmt.setString(2, vo.getRestName());
            pStmt.setInt(3, vo.getIsAvailable());
            pStmt.setString(4, vo.getCategory());
            pStmt.setString(5, vo.getMemId());
            pStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        return false;
    }

    //매장 정보 등록
    public Boolean restInsert(RestaurantVO vo) {
        String sql = "INSERT INTO RESTAURANT VALUES (?, ?, ?, SYSDATE, ?, ?)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, vo.getRestId());
            pStmt.setString(2, vo.getMemId());
            pStmt.setString(3, vo.getRestName());
            pStmt.setInt(4, vo.getIsAvailable());
            pStmt.setString(5, vo.getCategory());
            pStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        return false;
    }

    public String restName(String id){
        System.out.println("레스토랑 아이디로 매장명 찾기");
        String restName = "";
        try {
            conn = Common.getConnection(); //연결
            stmt = conn.createStatement(); //정적인 sql 사용
            String sql= "SELECT RESTAURANT_NAME FROM RESTAURANT WHERE RESTAURANT_ID = '"+id+"'";
            rs = stmt.executeQuery(sql); //
            while(rs.next()){ //읽을 행이 있으면 참
                restName = rs.getString("RESTAURANT_NAME");
            }
            Common.close(rs); // 연결과 역순으로 해제
            Common.close(stmt);
            Common.close(conn);
        }catch(Exception e){
            e.printStackTrace();

        }
        return restName;
    }

}
