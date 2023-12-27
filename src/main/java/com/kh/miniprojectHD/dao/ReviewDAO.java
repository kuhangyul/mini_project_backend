package com.kh.miniprojectHD.dao;

import com.kh.miniprojectHD.common.Common;
import com.kh.miniprojectHD.vo.InquiryVO;
import com.kh.miniprojectHD.vo.RestaurantVO;
import com.kh.miniprojectHD.vo.ReviewJoinVO;
import com.kh.miniprojectHD.vo.ReviewVO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pStmt = null;

    //리뷰 조회

    public List<ReviewVO> reviewSelect(String id) {
        List<ReviewVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection(); //연결
            stmt = conn.createStatement(); //정적인 sql 사용
            System.out.println(id);
            String sql= "SELECT REVIEW_ID, RE.MEMBER_ID, R.RESTAURANT_ID, R.RESTAURANT_NAME, REVIEW_TITLE, REVIEW_CONTENT, REVIEW_IMAGE_FILE_NAME, REVIEW_DATE, RATING\n" +
                    "FROM REVIEW RE\n" +
                    "JOIN RESTAURANT R ON RE.RESTAURANT_ID = R.RESTAURANT_ID\n" +
                    "WHERE RE.MEMBER_ID ='" + id + "' ORDER BY REVIEW_DATE DESC , REVIEW_ID DESC";
            rs = stmt.executeQuery(sql); //
            while(rs.next()){
                int reviewId = rs.getInt("REVIEW_ID");
                String memId = rs.getString("MEMBER_ID");
                String restId = rs.getString("RESTAURANT_ID");
                String restName = rs.getString("RESTAURANT_NAME");
                String reviewTitle = rs.getString("REVIEW_TITLE");
                String reviewContent = rs.getString("REVIEW_CONTENT");
                String reviewFileName = rs.getString("REVIEW_IMAGE_FILE_NAME");
                Date reviewDate = rs.getDate("REVIEW_DATE");
                double rating = rs.getDouble("RATING");


                ReviewVO vo = new ReviewVO(reviewId,memId,restId,restName,reviewTitle,reviewContent,reviewFileName,reviewDate,rating, 0); //하나의 행(레코드)에 대한 정보저장을 위한 객체생성
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
    // 매장 페이지 리뷰 정보 불러오기
    public List<ReviewJoinVO> reviewSelect(RestaurantVO restaurantVO){
        List<ReviewJoinVO> list = new ArrayList<>();

        try{
            String sql ="SELECT M.NICKNAME,M.MEMBER_ID,R.REVIEW_ID,R.REVIEW_TITLE,R.REVIEW_CONTENT,R.RATING,R.REVIEW_DATE,COUNT(L.REVIEW_ID),REVIEW_IMAGE_FILE_NAME FROM REVIEW R JOIN MEMBER_INFO M ON R.MEMBER_ID = M.MEMBER_ID LEFT JOIN REVIEW_LIKE L ON R.REVIEW_ID = L.REVIEW_ID WHERE R.RESTAURANT_ID = ? GROUP BY M.NICKNAME,M.MEMBER_ID, R.REVIEW_ID, R.REVIEW_TITLE, R.REVIEW_CONTENT, R.RATING, R.REVIEW_DATE,REVIEW_IMAGE_FILE_NAME";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restaurantVO.getRestId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                String nickName = rs.getString("NICKNAME");
                String memberId = rs.getString("MEMBER_ID");
                int reviewId = rs.getInt("REVIEW_ID");
                String title = rs.getString("REVIEW_TITLE");
                String content = rs.getString("REVIEW_CONTENT");
                double reviewRating = rs.getDouble("RATING");
                Date reviewDate = rs.getDate("REVIEW_DATE");
                int likeCnt = rs.getInt("COUNT(L.REVIEW_ID)");
                String image = rs.getString("REVIEW_IMAGE_FILE_NAME");

                ReviewJoinVO vo = new ReviewJoinVO();
                vo.setNickName(nickName);
                vo.setMemId(memberId);
                vo.setReviewId(reviewId);
                vo.setReviewTitle(title);
                vo.setReviewContent(content);
                vo.setReviewRating(reviewRating);
                vo.setReviewDate(reviewDate);
                vo.setLikeCnt(likeCnt);
                vo.setReviewImage(image);
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

    // 리뷰 추가
    public boolean addReview(String restId, String memId, String title, String content, double rating,String image) {
        int result = 0;
        String sql = "INSERT INTO REVIEW(REVIEW_ID,RESTAURANT_ID,MEMBER_ID,REVIEW_TITLE,REVIEW_CONTENT,RATING,REVIEW_IMAGE_FILE_NAME) VALUES(SEQ_REVIEW_ID.NEXTVAL,?,?,?,?,?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restId);
            pStmt.setString(2, memId);
            pStmt.setString(3, title);
            pStmt.setString(4, content);
            pStmt.setDouble(5, rating);
            pStmt.setString(6, image);

            result = pStmt.executeUpdate();

            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
    // 리뷰 상세 정보
    public List<ReviewJoinVO> detailSelect(ReviewVO reviewVO){
        List<ReviewJoinVO> list = new ArrayList<>();

        try{
            String sql ="SELECT M.NICKNAME,M.MEMBER_ID,R.REVIEW_ID,R.REVIEW_TITLE,R.REVIEW_CONTENT,R.RATING,R.REVIEW_DATE,COUNT(L.REVIEW_ID),REVIEW_IMAGE_FILE_NAME,R.RESTAURANT_ID,RESERVATION_POSSIBILITY FROM REVIEW R JOIN MEMBER_INFO M ON R.MEMBER_ID = M.MEMBER_ID JOIN RESTAURANT REST ON REST.RESTAURANT_ID= R.RESTAURANT_ID LEFT JOIN REVIEW_LIKE L ON R.REVIEW_ID = L.REVIEW_ID WHERE R.REVIEW_ID = ? GROUP BY M.NICKNAME,M.MEMBER_ID, R.REVIEW_ID, R.REVIEW_TITLE, R.REVIEW_CONTENT, R.RATING, R.REVIEW_DATE,REVIEW_IMAGE_FILE_NAME,R.RESTAURANT_ID,RESERVATION_POSSIBILITY";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, reviewVO.getReviewId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                String nickName = rs.getString("NICKNAME");
                String memId=rs.getString("MEMBER_ID");
                int reviewId = rs.getInt("REVIEW_ID");
                String title = rs.getString("REVIEW_TITLE");
                String content = rs.getString("REVIEW_CONTENT");
                double reviewRating = rs.getDouble("RATING");
                Date reviewDate = rs.getDate("REVIEW_DATE");
                int likeCnt = rs.getInt("COUNT(L.REVIEW_ID)");
                String image = rs.getString("REVIEW_IMAGE_FILE_NAME");
                String restId = rs.getString("RESTAURANT_ID");
                int res = rs.getInt("RESERVATION_POSSIBILITY");

                ReviewJoinVO vo = new ReviewJoinVO();
                vo.setNickName(nickName);
                vo.setMemId(memId);
                vo.setReviewId(reviewId);
                vo.setReviewTitle(title);
                vo.setReviewContent(content);
                vo.setReviewRating(reviewRating);
                vo.setReviewDate(reviewDate);
                vo.setLikeCnt(likeCnt);
                vo.setReviewImage(image);
                vo.setRestId(restId);
                vo.setReservation(res);
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

    //리뷰 수정
    public boolean updateReview(String title, String content, double rating,String image,int revId) {
        int result = 0;
        String sql = "UPDATE REVIEW SET REVIEW_TITLE =?,REVIEW_CONTENT=?,RATING=?,REVIEW_IMAGE_FILE_NAME=? WHERE REVIEW_ID=?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, title);
            pStmt.setString(2, content);
            pStmt.setDouble(3, rating);
            pStmt.setString(4, image);
            pStmt.setInt(5, revId);

            result = pStmt.executeUpdate();

            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
    //리뷰 삭제
    public Boolean revDelete(int id) {
        try {
            String sql = "DELETE FROM REVIEW WHERE REVIEW_ID = ?";
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

}
