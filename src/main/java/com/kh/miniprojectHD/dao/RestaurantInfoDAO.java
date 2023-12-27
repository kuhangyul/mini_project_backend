package com.kh.miniprojectHD.dao;

import com.kh.miniprojectHD.common.Common;
import com.kh.miniprojectHD.vo.MemberVO;
import com.kh.miniprojectHD.vo.RestaurantInfoVO;
import com.kh.miniprojectHD.vo.RestaurantVO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class RestaurantInfoDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 매장리스트 상세 정보 불러오기
    public List<RestaurantInfoVO> infoSelect(RestaurantVO restaurantVO){
        List<RestaurantInfoVO> list = new ArrayList<>();
        try{
            String sql = "SELECT RESTAURANT_ID,RESTAURANT_NOTICE,RESTAURANT_PHONE,RESTAURANT_INTRODUCE,RESTAURANT_HOURS,RESTAURANT_ADDR FROM RESTAURANT_INFO WHERE RESTAURANT_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,restaurantVO.getRestId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                String notice = rs.getString("RESTAURANT_NOTICE");
                String phone = rs.getString("RESTAURANT_PHONE");
                String introduce = rs.getString("RESTAURANT_INTRODUCE");
                String hours = rs.getString("RESTAURANT_HOURS");
                String addr = rs.getString("RESTAURANT_ADDR");
                String restId = rs.getString("RESTAURANT_ID");
                RestaurantInfoVO vo = new RestaurantInfoVO();
                vo.setRestNotice(notice);
                vo.setRestPhoneNum(phone);
                vo.setRestIntro(introduce);
                vo.setRestHours(hours);
                vo.setRestAddr(addr);
                vo.setRestId(restId);
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

    // 매장 상세 정보 불러오기
    public RestaurantInfoVO restInfoSelect(String restId){

        RestaurantInfoVO vo = null;
        try{
            String sql = "SELECT * FROM RESTAURANT_INFO WHERE RESTAURANT_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,restId);
            rs = pStmt.executeQuery();
            while (rs.next()){
                String id = rs.getString("RESTAURANT_ID");
                String restImgFileName = rs.getString("RESTAURANT_IMAGE_FILE_NAME");
                String restPhoneNum = rs.getString("RESTAURANT_PHONE");
                String restAddr = rs.getString("RESTAURANT_ADDR");
                String restNotice = rs.getString("RESTAURANT_NOTICE");
                String restHours = rs.getString("RESTAURANT_HOURS");
                String restIntro = rs.getString("RESTAURANT_INTRODUCE");

                vo  = new RestaurantInfoVO(id,restImgFileName,restPhoneNum,restAddr,restNotice,restHours,restIntro);
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    //매장 상세정보 업데이트

    public Boolean restInfoUpdate(RestaurantInfoVO vo) {
        String sql = " UPDATE RESTAURANT_INFO SET  RESTAURANT_IMAGE_FILE_NAME=?, RESTAURANT_PHONE=?, RESTAURANT_ADDR=?, RESTAURANT_NOTICE=?,RESTAURANT_HOURS=?,RESTAURANT_INTRODUCE=? WHERE RESTAURANT_ID =?";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, vo.getRestImgFileName());
            pStmt.setString(2, vo.getRestPhoneNum());
            pStmt.setString(3, vo.getRestAddr());
            pStmt.setString(4, vo.getRestNotice());
            pStmt.setString(5, vo.getRestHours());
            pStmt.setString(6, vo.getRestIntro());
            pStmt.setString(7, vo.getRestId());
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
    public Boolean restInfoInsert(RestaurantInfoVO vo) {
        String sql = " INSERT INTO RESTAURANT_INFO VALUES (?,?,?,?,?,?,?)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, vo.getRestId());
            pStmt.setString(2, vo.getRestImgFileName());
            pStmt.setString(3, vo.getRestPhoneNum());
            pStmt.setString(4, vo.getRestAddr());
            pStmt.setString(5, vo.getRestNotice());
            pStmt.setString(6, vo.getRestHours());
            pStmt.setString(7, vo.getRestIntro());
            pStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        return false;
    }

}
