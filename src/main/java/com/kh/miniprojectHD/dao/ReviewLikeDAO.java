package com.kh.miniprojectHD.dao;

import com.kh.miniprojectHD.common.Common;
import com.kh.miniprojectHD.vo.MemberVO;
import com.kh.miniprojectHD.vo.ReviewLikeVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewLikeDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pStmt = null;

    public boolean addRevLike(int revId, String memId) {
        int result = 0;
        String sql = "INSERT INTO REVIEW_LIKE(REVIEW_ID,MEMBER_ID) VALUES(?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, revId);
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

    // 리뷰 공감 삭제
    public boolean delRevLike(int revId,String memId) {
        int result = 0;
        String sql = "DELETE FROM REVIEW_LIKE WHERE REVIEW_ID = ? AND MEMBER_ID = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, revId);
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
    // 리뷰 공감 리스트 조회
    public List<ReviewLikeVO> revLikedSelect(MemberVO memberVO){
        List<ReviewLikeVO> list = new ArrayList<>();

        try{
            String sql ="SELECT * FROM REVIEW_LIKE WHERE MEMBER_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, memberVO.getMemId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                int revId = rs.getInt("REVIEW_ID");
                String memId = rs.getString("MEMBER_ID");

                ReviewLikeVO vo = new ReviewLikeVO(revId,memId);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
