package com.kh.miniprojectHD.dao;

import com.kh.miniprojectHD.common.Common;
import com.kh.miniprojectHD.vo.InquiryVO;
import com.kh.miniprojectHD.vo.RestLikeVO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestLikeDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pStmt = null;

    //찜 조회
    public List<RestLikeVO> restLikeSelect(String id) {
        List<RestLikeVO> list =new ArrayList<>();
        try {
            conn = Common.getConnection(); //연결
            stmt = conn.createStatement();
            String sql = "SELECT RL.RESTAURANT_ID, R.RESTAURANT_NAME, RL.MEMBER_ID, TRUNC(AVG(REVIEW.RATING), 1) AS RATING,RESERVATION_POSSIBILITY  "
                    + "FROM RESTAURANT R "
                    + "JOIN RESTAURANT_LIKE RL ON R.RESTAURANT_ID = RL.RESTAURANT_ID "
                    + "LEFT JOIN REVIEW ON R.RESTAURANT_ID = REVIEW.RESTAURANT_ID "
                    + "WHERE R.RESTAURANT_ID = RL.RESTAURANT_ID AND RL.MEMBER_ID = '" + id + "' "
                    + "GROUP BY RL.RESTAURANT_ID, R.RESTAURANT_NAME, RL.MEMBER_ID,RESERVATION_POSSIBILITY ";

            rs = stmt.executeQuery(sql); //
            while(rs.next()){ //읽을 행이 있으면 참
                String restId = rs.getString("RESTAURANT_ID");
                String restName = rs.getString("RESTAURANT_NAME");
                String memId = rs.getString("MEMBER_ID");
                double restRating = rs.getDouble("RATING");
                int reservation = rs.getInt("RESERVATION_POSSIBILITY");

                RestLikeVO vo = new RestLikeVO(restId,memId,restName,restRating,reservation);
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
    // 찜 등록
    public boolean addRestLike(String restId, String memId,String name) {
        int result = 0;
        String sql = "INSERT INTO RESTAURANT_LIKE(RESTAURANT_ID,MEMBER_ID,RESTAURANT_NAME) VALUES(?,?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restId);
            pStmt.setString(2, memId);
            pStmt.setString(3, name);

            result = pStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
    // 찜 삭제
    public boolean delRestLike(String restId,String memId) {
        int result = 0;
        String sql = "DELETE FROM RESTAURANT_LIKE WHERE RESTAURANT_ID = ? AND MEMBER_ID = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restId);
            pStmt.setString(2, memId);

            result = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }

    //찜 숫자 조회
    public int likeCntSelect(String id) {
        int likeCnt=0;
        try {
            conn = Common.getConnection(); //연결
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) AS CNT FROM RESTAURANT_LIKE  WHERE RESTAURANT_ID = '"+id +"'";
            rs = stmt.executeQuery(sql); //
            while(rs.next()){ //읽을 행이 있으면 참
                likeCnt = rs.getInt("CNT");
               return likeCnt;
            }
            Common.close(rs); // 연결과 역순으로 해제
            Common.close(stmt);
            Common.close(conn);
        }catch(Exception e){
            e.printStackTrace();

        }
        return likeCnt;
    }

}
