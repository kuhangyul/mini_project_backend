package com.kh.miniprojectHD.dao;

import com.kh.miniprojectHD.common.Common;
import com.kh.miniprojectHD.vo.RestMenuVO;
import com.kh.miniprojectHD.vo.RestaurantVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RestMenuDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 메뉴 정보 불러오기
    public List<RestMenuVO> menuSelect(RestaurantVO restaurantVO){
        List<RestMenuVO> list = new ArrayList<>();

        try{
            String sql ="SELECT MENU_NAME, MENU_PRICE, MENU_DESC,MENU_ID FROM R_MENU WHERE RESTAURANT_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restaurantVO.getRestId());
            rs = pStmt.executeQuery();
            while (rs.next()){

                String name = rs.getString("MENU_NAME");
                int price = rs.getInt("MENU_PRICE");
                String desc = rs.getString("MENU_DESC");
                int menuId = rs.getInt("MENU_ID");
                RestMenuVO vo = new RestMenuVO();
                vo.setMenuName(name);
                vo.setMenuPrice(price);
                vo.setMenuDesc(desc);
                vo.setMenuId(menuId);

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
    // 메뉴 정보 불러오기2
    public List<RestMenuVO> bizMenuSelect(String restId){
        List<RestMenuVO> list = new ArrayList<>();

        try{
            String sql ="SELECT * FROM R_MENU WHERE RESTAURANT_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restId);
            rs = pStmt.executeQuery();
            while (rs.next()){
                String retId = rs.getString("RESTAURANT_ID");
                String name = rs.getString("MENU_NAME");
                int price = rs.getInt("MENU_PRICE");
                String desc = rs.getString("MENU_DESC");
                String menuImg = rs.getString("MENU_IMAGE_FILE_NAME");
                int id = rs.getInt("MENU_ID");
                RestMenuVO vo = new RestMenuVO(retId,name,price,desc,menuImg,id);

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

    // 메뉴 등록
    public boolean addMenu(String restId,String menuName,int menuPrice,String menuDesc,String menuImgFileName) {
        int result = 0;
        String sql = "INSERT INTO R_MENU VALUES(?,?,?,?,?,MENU_NUM.NEXTVAL)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restId);
            pStmt.setString(2, menuName);
            pStmt.setInt(3, menuPrice);
            pStmt.setString(4, menuDesc);
            pStmt.setString(5, menuImgFileName);
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

    //메뉴삭제
    public Boolean menuDelete(int id) {
        try {
            String sql = "DELETE FROM R_MENU WHERE MENU_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1,id);
            System.out.println(id);
            pStmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        return false;

    }

    //메뉴수정
    public Boolean menuUpdate(RestMenuVO[] vo) {
        try {
            String sql = "UPDATE R_MENU SET RESTAURANT_ID=?, MENU_NAME=?, MENU_PRICE=?, MENU_DESC=?, MENU_IMAGE_FILE_NAME=? WHERE MENU_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);

            for (RestMenuVO menu : vo) {
                pStmt.setString(1,menu.getRestId());
                pStmt.setString(2,menu.getMenuName());
                pStmt.setInt(3,menu.getMenuPrice());
                pStmt.setString(4,menu.getMenuDesc());
                pStmt.setString(5,menu.getMenuImgFileName());
                pStmt.setInt(6,menu.getMenuId());
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
}
