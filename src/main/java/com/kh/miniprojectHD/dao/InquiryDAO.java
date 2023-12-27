package com.kh.miniprojectHD.dao;

import com.kh.miniprojectHD.common.Common;
import com.kh.miniprojectHD.vo.InquiryVO;
import com.kh.miniprojectHD.vo.MemberVO;
import com.kh.miniprojectHD.vo.RestMenuVO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class InquiryDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pStmt = null;

    //문의 조회

    public List<InquiryVO> inquirySelect(String id)  {
        List<InquiryVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection(); //연결
            stmt = conn.createStatement(); //정적인 sql 사용
            System.out.println(id);
            String sql = "SELECT INQUIRY_ID,I.MEMBER_ID ,R.RESTAURANT_ID ,R.RESTAURANT_NAME,INQUIRY_TITLE,INQUIRY_CONTENT,INQUIRY_ANSWER,INQUIRY_DATE,INQUIRY_IMAGE_FILE_NAME,INQUIRY_CONDITION "
            +"FROM RESTAURANT R JOIN INQUIRY I ON R.RESTAURANT_ID= I.RESTAURANT_ID WHERE I.MEMBER_ID = "+ "'" + id + "' ORDER BY INQUIRY_DATE DESC ,INQUIRY_ID DESC";

            rs = stmt.executeQuery(sql); //
            while(rs.next()){ //읽을 행이 있으면 참
                int inquiryId = rs.getInt("INQUIRY_ID");
               String memId = rs.getString("MEMBER_ID");
                String restId = rs.getString("RESTAURANT_ID");
                String restName = rs.getString("RESTAURANT_NAME");
                String inquiryTitle = rs.getString("INQUIRY_TITLE");
                String inquiryContent = rs.getString("INQUIRY_CONTENT");
                String inquiryAnswer = rs.getString("INQUIRY_ANSWER");
                Date inquiryDate = rs.getDate("INQUIRY_DATE");
                String inquiryImgFileName = rs.getString("INQUIRY_IMAGE_FILE_NAME");
                String inquiryStat = rs.getString("INQUIRY_CONDITION");
                InquiryVO vo = new InquiryVO(inquiryId,memId,restId,restName,inquiryTitle,inquiryContent,inquiryAnswer,inquiryDate,inquiryImgFileName,inquiryStat); //하나의 행(레코드)에 대한 정보저장을 위한 객체생성
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
    // 문의 등록
    public boolean addInquiry(String restId, String memId, String title, String content,String image) {
        int result = 0;
        String sql = "INSERT INTO INQUIRY(INQUIRY_ID,RESTAURANT_ID,MEMBER_ID,INQUIRY_TITLE,INQUIRY_CONTENT,INQUIRY_IMAGE_FILE_NAME) VALUES(SEQ_INQUIRY_ID.NEXTVAL,?,?,?,?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restId);
            pStmt.setString(2, memId);
            pStmt.setString(3, title);
            pStmt.setString(4, content);
            pStmt.setString(5, image);

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

    //사업자 ID로 문의조회

    public List<InquiryVO> businessInquiry(String id)  {
        List<InquiryVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection(); //연결
            stmt = conn.createStatement(); //정적인 sql 사용
            String sql = " SELECT * FROM INQUIRY WHERE RESTAURANT_ID = "+ "'" + id + "' ORDER BY INQUIRY_DATE DESC , INQUIRY_ID DESC";

            rs = stmt.executeQuery(sql); //
            while(rs.next()){ //읽을 행이 있으면 참
                int inquiryId = rs.getInt("INQUIRY_ID");
                String memId = rs.getString("MEMBER_ID");
                String restId = rs.getString("RESTAURANT_ID");

                String inquiryTitle = rs.getString("INQUIRY_TITLE");
                String inquiryContent = rs.getString("INQUIRY_CONTENT");
                String inquiryAnswer = rs.getString("INQUIRY_ANSWER");
                Date inquiryDate = rs.getDate("INQUIRY_DATE");
                String inquiryImgFileName = rs.getString("INQUIRY_IMAGE_FILE_NAME");
                String inquiryStat = rs.getString("INQUIRY_CONDITION");
                InquiryVO vo = new InquiryVO(inquiryId,memId,restId,null,inquiryTitle,inquiryContent,inquiryAnswer,inquiryDate,inquiryImgFileName,inquiryStat); //하나의 행(레코드)에 대한 정보저장을 위한 객체생성
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

    //문의 수정 및 답변
    public Boolean inquiryAnswerUpdate(InquiryVO vo) {
        try {
            System.out.println(vo.getInquiryStat());
            String sql = "UPDATE INQUIRY SET INQUIRY_TITLE=?, INQUIRY_CONTENT=?, INQUIRY_ANSWER=?,INQUIRY_IMAGE_FILE_NAME=?,INQUIRY_CONDITION=?  WHERE INQUIRY_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);

                pStmt.setString(1,vo.getInquiryTitle());
                pStmt.setString(2,vo.getInquiryContent());
                pStmt.setString(3,vo.getInquiryAnswer());
                pStmt.setString(4,vo.getInquiryImgFileName());
                pStmt.setString(5,vo.getInquiryStat());
                pStmt.setInt(6,vo.getInquiryId());
                pStmt.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        return false;

    }

    //문의 수정
    public Boolean inquiryUpdate(InquiryVO vo) {
        try {
            String sql = "UPDATE INQUIRY SET INQUIRY_TITLE=?, INQUIRY_CONTENT=?, INQUIRY_ANSWER=?,INQUIRY_IMAGE_FILE_NAME=?,INQUIRY_CONDITION=?  WHERE INQUIRY_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);

            pStmt.setString(1,vo.getInquiryTitle());
            pStmt.setString(2,vo.getInquiryContent());
            pStmt.setString(3,vo.getInquiryAnswer());
            pStmt.setString(4,vo.getInquiryImgFileName());
            pStmt.setString(5,vo.getInquiryStat());
            pStmt.setInt(6,vo.getInquiryId());
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
